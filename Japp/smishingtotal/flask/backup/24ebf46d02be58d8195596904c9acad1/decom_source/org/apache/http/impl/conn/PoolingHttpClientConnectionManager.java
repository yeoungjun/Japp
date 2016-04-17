// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.config.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.pool.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn:
//            CPool, HttpClientConnectionOperator, CPoolEntry, CPoolProxy, 
//            ManagedHttpClientConnectionFactory

public class PoolingHttpClientConnectionManager
    implements HttpClientConnectionManager, ConnPoolControl, Closeable
{
    static class ConfigData
    {

        public ConnectionConfig getConnectionConfig(HttpHost httphost)
        {
            return (ConnectionConfig)connectionConfigMap.get(httphost);
        }

        public ConnectionConfig getDefaultConnectionConfig()
        {
            return defaultConnectionConfig;
        }

        public SocketConfig getDefaultSocketConfig()
        {
            return defaultSocketConfig;
        }

        public SocketConfig getSocketConfig(HttpHost httphost)
        {
            return (SocketConfig)socketConfigMap.get(httphost);
        }

        public void setConnectionConfig(HttpHost httphost, ConnectionConfig connectionconfig)
        {
            connectionConfigMap.put(httphost, connectionconfig);
        }

        public void setDefaultConnectionConfig(ConnectionConfig connectionconfig)
        {
            defaultConnectionConfig = connectionconfig;
        }

        public void setDefaultSocketConfig(SocketConfig socketconfig)
        {
            defaultSocketConfig = socketconfig;
        }

        public void setSocketConfig(HttpHost httphost, SocketConfig socketconfig)
        {
            socketConfigMap.put(httphost, socketconfig);
        }

        private final Map connectionConfigMap = new ConcurrentHashMap();
        private volatile ConnectionConfig defaultConnectionConfig;
        private volatile SocketConfig defaultSocketConfig;
        private final Map socketConfigMap = new ConcurrentHashMap();

        ConfigData()
        {
        }
    }

    static class InternalConnectionFactory
        implements ConnFactory
    {

        public volatile Object create(Object obj)
            throws IOException
        {
            return create((HttpRoute)obj);
        }

        public ManagedHttpClientConnection create(HttpRoute httproute)
            throws IOException
        {
            HttpHost httphost = httproute.getProxyHost();
            ConnectionConfig connectionconfig = null;
            if(httphost != null)
                connectionconfig = configData.getConnectionConfig(httproute.getProxyHost());
            if(connectionconfig == null)
                connectionconfig = configData.getConnectionConfig(httproute.getTargetHost());
            if(connectionconfig == null)
                connectionconfig = configData.getDefaultConnectionConfig();
            if(connectionconfig == null)
                connectionconfig = ConnectionConfig.DEFAULT;
            return (ManagedHttpClientConnection)connFactory.create(httproute, connectionconfig);
        }

        private final ConfigData configData;
        private final HttpConnectionFactory connFactory;

        InternalConnectionFactory(ConfigData configdata, HttpConnectionFactory httpconnectionfactory)
        {
            if(configdata == null)
                configdata = new ConfigData();
            configData = configdata;
            if(httpconnectionfactory == null)
                httpconnectionfactory = ManagedHttpClientConnectionFactory.INSTANCE;
            connFactory = httpconnectionfactory;
        }
    }


    public PoolingHttpClientConnectionManager()
    {
        this(getDefaultRegistry());
    }

    public PoolingHttpClientConnectionManager(long l, TimeUnit timeunit)
    {
        this(getDefaultRegistry(), null, null, null, l, timeunit);
    }

    public PoolingHttpClientConnectionManager(Registry registry)
    {
        this(registry, null, null);
    }

    public PoolingHttpClientConnectionManager(Registry registry, DnsResolver dnsresolver)
    {
        this(registry, null, dnsresolver);
    }

    public PoolingHttpClientConnectionManager(Registry registry, HttpConnectionFactory httpconnectionfactory)
    {
        this(registry, httpconnectionfactory, null);
    }

    public PoolingHttpClientConnectionManager(Registry registry, HttpConnectionFactory httpconnectionfactory, DnsResolver dnsresolver)
    {
        this(registry, httpconnectionfactory, null, dnsresolver, -1L, TimeUnit.MILLISECONDS);
    }

    public PoolingHttpClientConnectionManager(Registry registry, HttpConnectionFactory httpconnectionfactory, SchemePortResolver schemeportresolver, DnsResolver dnsresolver, long l, TimeUnit timeunit)
    {
        log = LogFactory.getLog(getClass());
        configData = new ConfigData();
        pool = new CPool(new InternalConnectionFactory(configData, httpconnectionfactory), 2, 20, l, timeunit);
        connectionOperator = new HttpClientConnectionOperator(registry, schemeportresolver, dnsresolver);
    }

    public PoolingHttpClientConnectionManager(HttpConnectionFactory httpconnectionfactory)
    {
        this(getDefaultRegistry(), httpconnectionfactory, null);
    }

    PoolingHttpClientConnectionManager(CPool cpool, Lookup lookup, SchemePortResolver schemeportresolver, DnsResolver dnsresolver)
    {
        log = LogFactory.getLog(getClass());
        configData = new ConfigData();
        pool = cpool;
        connectionOperator = new HttpClientConnectionOperator(lookup, schemeportresolver, dnsresolver);
    }

    private String format(HttpRoute httproute, Object obj)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[route: ").append(httproute).append("]");
        if(obj != null)
            stringbuilder.append("[state: ").append(obj).append("]");
        return stringbuilder.toString();
    }

    private String format(CPoolEntry cpoolentry)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[id: ").append(cpoolentry.getId()).append("]");
        stringbuilder.append("[route: ").append(cpoolentry.getRoute()).append("]");
        Object obj = cpoolentry.getState();
        if(obj != null)
            stringbuilder.append("[state: ").append(obj).append("]");
        return stringbuilder.toString();
    }

    private String formatStats(HttpRoute httproute)
    {
        StringBuilder stringbuilder = new StringBuilder();
        PoolStats poolstats = pool.getTotalStats();
        PoolStats poolstats1 = pool.getStats(httproute);
        stringbuilder.append("[total kept alive: ").append(poolstats.getAvailable()).append("; ");
        stringbuilder.append("route allocated: ").append(poolstats1.getLeased() + poolstats1.getAvailable());
        stringbuilder.append(" of ").append(poolstats1.getMax()).append("; ");
        stringbuilder.append("total allocated: ").append(poolstats.getLeased() + poolstats.getAvailable());
        stringbuilder.append(" of ").append(poolstats.getMax()).append("]");
        return stringbuilder.toString();
    }

    private static Registry getDefaultRegistry()
    {
        return RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }

    public void close()
    {
        shutdown();
    }

    public void closeExpiredConnections()
    {
        log.debug("Closing expired connections");
        pool.closeExpired();
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Closing connections idle longer than ").append(l).append(" ").append(timeunit).toString());
        pool.closeIdle(l, timeunit);
    }

    public void connect(HttpClientConnection httpclientconnection, HttpRoute httproute, int i, HttpContext httpcontext)
        throws IOException
    {
        Args.notNull(httpclientconnection, "Managed Connection");
        Args.notNull(httproute, "HTTP route");
        httpclientconnection;
        JVM INSTR monitorenter ;
        ManagedHttpClientConnection managedhttpclientconnection = (ManagedHttpClientConnection)CPoolProxy.getPoolEntry(httpclientconnection).getConnection();
        httpclientconnection;
        JVM INSTR monitorexit ;
        Exception exception;
        HttpHost httphost;
        java.net.InetSocketAddress inetsocketaddress;
        SocketConfig socketconfig;
        if(httproute.getProxyHost() != null)
            httphost = httproute.getProxyHost();
        else
            httphost = httproute.getTargetHost();
        inetsocketaddress = httproute.getLocalSocketAddress();
        socketconfig = configData.getSocketConfig(httphost);
        if(socketconfig == null)
            socketconfig = configData.getDefaultSocketConfig();
        if(socketconfig == null)
            socketconfig = SocketConfig.DEFAULT;
        connectionOperator.connect(managedhttpclientconnection, httphost, inetsocketaddress, i, socketconfig, httpcontext);
        return;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected void finalize()
        throws Throwable
    {
        shutdown();
        super.finalize();
        return;
        Exception exception;
        exception;
        super.finalize();
        throw exception;
    }

    public ConnectionConfig getConnectionConfig(HttpHost httphost)
    {
        return configData.getConnectionConfig(httphost);
    }

    public ConnectionConfig getDefaultConnectionConfig()
    {
        return configData.getDefaultConnectionConfig();
    }

    public int getDefaultMaxPerRoute()
    {
        return pool.getDefaultMaxPerRoute();
    }

    public SocketConfig getDefaultSocketConfig()
    {
        return configData.getDefaultSocketConfig();
    }

    public volatile int getMaxPerRoute(Object obj)
    {
        return getMaxPerRoute((HttpRoute)obj);
    }

    public int getMaxPerRoute(HttpRoute httproute)
    {
        return pool.getMaxPerRoute(httproute);
    }

    public int getMaxTotal()
    {
        return pool.getMaxTotal();
    }

    public SocketConfig getSocketConfig(HttpHost httphost)
    {
        return configData.getSocketConfig(httphost);
    }

    public volatile PoolStats getStats(Object obj)
    {
        return getStats((HttpRoute)obj);
    }

    public PoolStats getStats(HttpRoute httproute)
    {
        return pool.getStats(httproute);
    }

    public PoolStats getTotalStats()
    {
        return pool.getTotalStats();
    }

    protected HttpClientConnection leaseConnection(Future future, long l, TimeUnit timeunit)
        throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException
    {
        CPoolEntry cpoolentry;
        try
        {
            cpoolentry = (CPoolEntry)future.get(l, timeunit);
        }
        catch(TimeoutException timeoutexception)
        {
            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        }
        if(cpoolentry == null)
            break MISSING_BLOCK_LABEL_28;
        if(!future.isCancelled())
            break MISSING_BLOCK_LABEL_49;
        throw new InterruptedException();
        boolean flag;
        HttpClientConnection httpclientconnection;
        if(cpoolentry.getConnection() != null)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Pool entry with no connection");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection leased: ").append(format(cpoolentry)).append(formatStats((HttpRoute)cpoolentry.getRoute())).toString());
        httpclientconnection = CPoolProxy.newProxy(cpoolentry);
        return httpclientconnection;
    }

    public void releaseConnection(HttpClientConnection httpclientconnection, Object obj, long l, TimeUnit timeunit)
    {
        Args.notNull(httpclientconnection, "Managed connection");
        httpclientconnection;
        JVM INSTR monitorenter ;
        CPoolEntry cpoolentry = CPoolProxy.detach(httpclientconnection);
        if(cpoolentry != null)
            break MISSING_BLOCK_LABEL_24;
        httpclientconnection;
        JVM INSTR monitorexit ;
        return;
        ManagedHttpClientConnection managedhttpclientconnection = (ManagedHttpClientConnection)cpoolentry.getConnection();
        if(!managedhttpclientconnection.isOpen()) goto _L2; else goto _L1
_L1:
        cpoolentry.setState(obj);
        if(timeunit == null) goto _L4; else goto _L3
_L3:
        cpoolentry.updateExpiry(l, timeunit);
        if(!log.isDebugEnabled()) goto _L2; else goto _L5
_L5:
        if(l <= 0L) goto _L7; else goto _L6
_L6:
        String s = (new StringBuilder()).append("for ").append((double)l / 1000D).append(" seconds").toString();
_L8:
        log.debug((new StringBuilder()).append("Connection ").append(format(cpoolentry)).append(" can be kept alive ").append(s).toString());
_L2:
        CPool cpool1 = pool;
        Exception exception;
        boolean flag1;
        if(managedhttpclientconnection.isOpen() && cpoolentry.isRouteComplete())
            flag1 = true;
        else
            flag1 = false;
        cpool1.release(cpoolentry, flag1);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection released: ").append(format(cpoolentry)).append(formatStats((HttpRoute)cpoolentry.getRoute())).toString());
        httpclientconnection;
        JVM INSTR monitorexit ;
        return;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        timeunit = TimeUnit.MILLISECONDS;
          goto _L3
_L7:
        s = "indefinitely";
          goto _L8
        Exception exception1;
        exception1;
        CPool cpool = pool;
        boolean flag;
        if(managedhttpclientconnection.isOpen() && cpoolentry.isRouteComplete())
            flag = true;
        else
            flag = false;
        cpool.release(cpoolentry, flag);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection released: ").append(format(cpoolentry)).append(formatStats((HttpRoute)cpoolentry.getRoute())).toString());
        throw exception1;
          goto _L3
    }

    public ConnectionRequest requestConnection(HttpRoute httproute, Object obj)
    {
        Args.notNull(httproute, "HTTP route");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection request: ").append(format(httproute, obj)).append(formatStats(httproute)).toString());
        return new ConnectionRequest() {

            public boolean cancel()
            {
                return future.cancel(true);
            }

            public HttpClientConnection get(long l, TimeUnit timeunit)
                throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException
            {
                return leaseConnection(future, l, timeunit);
            }

            final PoolingHttpClientConnectionManager this$0;
            final Future val$future;

            
            {
                this$0 = PoolingHttpClientConnectionManager.this;
                future = future1;
                super();
            }
        };
    }

    public void routeComplete(HttpClientConnection httpclientconnection, HttpRoute httproute, HttpContext httpcontext)
        throws IOException
    {
        Args.notNull(httpclientconnection, "Managed Connection");
        Args.notNull(httproute, "HTTP route");
        httpclientconnection;
        JVM INSTR monitorenter ;
        CPoolProxy.getPoolEntry(httpclientconnection).markRouteComplete();
        httpclientconnection;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setConnectionConfig(HttpHost httphost, ConnectionConfig connectionconfig)
    {
        configData.setConnectionConfig(httphost, connectionconfig);
    }

    public void setDefaultConnectionConfig(ConnectionConfig connectionconfig)
    {
        configData.setDefaultConnectionConfig(connectionconfig);
    }

    public void setDefaultMaxPerRoute(int i)
    {
        pool.setDefaultMaxPerRoute(i);
    }

    public void setDefaultSocketConfig(SocketConfig socketconfig)
    {
        configData.setDefaultSocketConfig(socketconfig);
    }

    public volatile void setMaxPerRoute(Object obj, int i)
    {
        setMaxPerRoute((HttpRoute)obj, i);
    }

    public void setMaxPerRoute(HttpRoute httproute, int i)
    {
        pool.setMaxPerRoute(httproute, i);
    }

    public void setMaxTotal(int i)
    {
        pool.setMaxTotal(i);
    }

    public void setSocketConfig(HttpHost httphost, SocketConfig socketconfig)
    {
        configData.setSocketConfig(httphost, socketconfig);
    }

    public void shutdown()
    {
        log.debug("Connection manager is shutting down");
        try
        {
            pool.shutdown();
        }
        catch(IOException ioexception)
        {
            log.debug("I/O exception shutting down connection manager", ioexception);
        }
        log.debug("Connection manager shut down");
    }

    public void upgrade(HttpClientConnection httpclientconnection, HttpRoute httproute, HttpContext httpcontext)
        throws IOException
    {
        Args.notNull(httpclientconnection, "Managed Connection");
        Args.notNull(httproute, "HTTP route");
        httpclientconnection;
        JVM INSTR monitorenter ;
        ManagedHttpClientConnection managedhttpclientconnection = (ManagedHttpClientConnection)CPoolProxy.getPoolEntry(httpclientconnection).getConnection();
        httpclientconnection;
        JVM INSTR monitorexit ;
        connectionOperator.upgrade(managedhttpclientconnection, httproute.getTargetHost(), httpcontext);
        return;
        Exception exception;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private final ConfigData configData;
    private final HttpClientConnectionOperator connectionOperator;
    private final Log log;
    private final CPool pool;
}
