// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.Closeable;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.config.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.conn:
//            HttpClientConnectionOperator, ManagedHttpClientConnectionFactory

public class BasicHttpClientConnectionManager
    implements HttpClientConnectionManager, Closeable
{

    public BasicHttpClientConnectionManager()
    {
        this(((Lookup) (getDefaultRegistry())), null, null, null);
    }

    public BasicHttpClientConnectionManager(Lookup lookup)
    {
        this(lookup, null, null, null);
    }

    public BasicHttpClientConnectionManager(Lookup lookup, HttpConnectionFactory httpconnectionfactory)
    {
        this(lookup, httpconnectionfactory, null, null);
    }

    public BasicHttpClientConnectionManager(Lookup lookup, HttpConnectionFactory httpconnectionfactory, SchemePortResolver schemeportresolver, DnsResolver dnsresolver)
    {
        log = LogFactory.getLog(getClass());
        connectionOperator = new HttpClientConnectionOperator(lookup, schemeportresolver, dnsresolver);
        if(httpconnectionfactory == null)
            httpconnectionfactory = ManagedHttpClientConnectionFactory.INSTANCE;
        connFactory = httpconnectionfactory;
        expiry = 0x7fffffffffffffffL;
        socketConfig = SocketConfig.DEFAULT;
        connConfig = ConnectionConfig.DEFAULT;
    }

    private void checkExpiry()
    {
        if(conn != null && System.currentTimeMillis() >= expiry)
        {
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Connection expired @ ").append(new Date(expiry)).toString());
            closeConnection();
        }
    }

    private void closeConnection()
    {
        if(conn == null) goto _L2; else goto _L1
_L1:
        log.debug("Closing connection");
        conn.close();
_L4:
        conn = null;
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug("I/O exception closing connection", ioexception);
        if(true) goto _L4; else goto _L3
_L3:
    }

    private static Registry getDefaultRegistry()
    {
        return RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
    }

    private void shutdownConnection()
    {
        if(conn == null) goto _L2; else goto _L1
_L1:
        log.debug("Shutting down connection");
        conn.shutdown();
_L4:
        conn = null;
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug("I/O exception shutting down connection", ioexception);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void close()
    {
        shutdown();
    }

    public void closeExpiredConnections()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = shutdown;
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(!leased)
            checkExpiry();
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        Args.notNull(timeunit, "Time unit");
        flag = shutdown;
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        long l1;
        if(leased)
            continue; /* Loop/switch isn't completed */
        l1 = timeunit.toMillis(l);
        if(l1 < 0L)
            l1 = 0L;
        long l2 = System.currentTimeMillis() - l1;
        if(updated <= l2)
            closeConnection();
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void connect(HttpClientConnection httpclientconnection, HttpRoute httproute, int i, HttpContext httpcontext)
        throws IOException
    {
        Args.notNull(httpclientconnection, "Connection");
        Args.notNull(httproute, "HTTP route");
        boolean flag;
        org.apache.http.HttpHost httphost;
        java.net.InetSocketAddress inetsocketaddress;
        if(httpclientconnection == conn)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection not obtained from this manager");
        if(httproute.getProxyHost() != null)
            httphost = httproute.getProxyHost();
        else
            httphost = httproute.getTargetHost();
        inetsocketaddress = httproute.getLocalSocketAddress();
        connectionOperator.connect(conn, httphost, inetsocketaddress, i, socketConfig, httpcontext);
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

    HttpClientConnection getConnection(HttpRoute httproute, Object obj)
    {
        boolean flag = true;
        this;
        JVM INSTR monitorenter ;
        boolean flag1;
        ManagedHttpClientConnection managedhttpclientconnection;
        if(!shutdown)
            flag1 = flag;
        else
            flag1 = false;
        Asserts.check(flag1, "Connection manager has been shut down");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Get connection for route ").append(httproute).toString());
        if(leased)
            flag = false;
        Asserts.check(flag, "Connection is still allocated");
        if(!LangUtils.equals(route, httproute) || !LangUtils.equals(state, obj))
            closeConnection();
        route = httproute;
        state = obj;
        checkExpiry();
        if(conn == null)
            conn = (ManagedHttpClientConnection)connFactory.create(httproute, connConfig);
        leased = true;
        managedhttpclientconnection = conn;
        this;
        JVM INSTR monitorexit ;
        return managedhttpclientconnection;
        Exception exception;
        exception;
        throw exception;
    }

    public ConnectionConfig getConnectionConfig()
    {
        this;
        JVM INSTR monitorenter ;
        ConnectionConfig connectionconfig = connConfig;
        this;
        JVM INSTR monitorexit ;
        return connectionconfig;
        Exception exception;
        exception;
        throw exception;
    }

    HttpRoute getRoute()
    {
        return route;
    }

    public SocketConfig getSocketConfig()
    {
        this;
        JVM INSTR monitorenter ;
        SocketConfig socketconfig = socketConfig;
        this;
        JVM INSTR monitorexit ;
        return socketconfig;
        Exception exception;
        exception;
        throw exception;
    }

    Object getState()
    {
        return state;
    }

    public void releaseConnection(HttpClientConnection httpclientconnection, Object obj, long l, TimeUnit timeunit)
    {
        this;
        JVM INSTR monitorenter ;
        ManagedHttpClientConnection managedhttpclientconnection;
        Args.notNull(httpclientconnection, "Connection");
        managedhttpclientconnection = conn;
        boolean flag;
        flag = false;
        if(httpclientconnection == managedhttpclientconnection)
            flag = true;
        Asserts.check(flag, "Connection not obtained from this manager");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Releasing connection ").append(httpclientconnection).toString());
        if(!shutdown) goto _L2; else goto _L1
_L1:
        shutdownConnection();
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        updated = System.currentTimeMillis();
        if(conn.isOpen())
            break; /* Loop/switch isn't completed */
        conn = null;
        route = null;
        conn = null;
        expiry = 0x7fffffffffffffffL;
_L7:
        leased = false;
        if(true) goto _L4; else goto _L3
        Exception exception;
        exception;
        throw exception;
_L3:
        state = obj;
        if(!log.isDebugEnabled()) goto _L6; else goto _L5
_L5:
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_265;
        String s = (new StringBuilder()).append("for ").append(l).append(" ").append(timeunit).toString();
_L8:
        log.debug((new StringBuilder()).append("Connection can be kept alive ").append(s).toString());
_L6:
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_273;
        expiry = updated + timeunit.toMillis(l);
          goto _L7
        Exception exception1;
        exception1;
        leased = false;
        throw exception1;
        s = "indefinitely";
          goto _L8
        expiry = 0x7fffffffffffffffL;
          goto _L7
    }

    public final ConnectionRequest requestConnection(final HttpRoute route, final Object state)
    {
        Args.notNull(route, "Route");
        return new ConnectionRequest() {

            public boolean cancel()
            {
                return false;
            }

            public HttpClientConnection get(long l, TimeUnit timeunit)
            {
                return getConnection(route, state);
            }

            final BasicHttpClientConnectionManager this$0;
            final HttpRoute val$route;
            final Object val$state;

            
            {
                this$0 = BasicHttpClientConnectionManager.this;
                route = httproute;
                state = obj;
                super();
            }
        };
    }

    public void routeComplete(HttpClientConnection httpclientconnection, HttpRoute httproute, HttpContext httpcontext)
        throws IOException
    {
    }

    public void setConnectionConfig(ConnectionConfig connectionconfig)
    {
        this;
        JVM INSTR monitorenter ;
        if(connectionconfig == null) goto _L2; else goto _L1
_L1:
        connConfig = connectionconfig;
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        connectionconfig = ConnectionConfig.DEFAULT;
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void setSocketConfig(SocketConfig socketconfig)
    {
        this;
        JVM INSTR monitorenter ;
        if(socketconfig == null) goto _L2; else goto _L1
_L1:
        socketConfig = socketconfig;
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        socketconfig = SocketConfig.DEFAULT;
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void shutdown()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = shutdown;
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        shutdown = true;
        shutdownConnection();
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void upgrade(HttpClientConnection httpclientconnection, HttpRoute httproute, HttpContext httpcontext)
        throws IOException
    {
        Args.notNull(httpclientconnection, "Connection");
        Args.notNull(httproute, "HTTP route");
        boolean flag;
        if(httpclientconnection == conn)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection not obtained from this manager");
        connectionOperator.upgrade(conn, httproute.getTargetHost(), httpcontext);
    }

    private ManagedHttpClientConnection conn;
    private ConnectionConfig connConfig;
    private final HttpConnectionFactory connFactory;
    private final HttpClientConnectionOperator connectionOperator;
    private long expiry;
    private boolean leased;
    private final Log log;
    private HttpRoute route;
    private volatile boolean shutdown;
    private SocketConfig socketConfig;
    private Object state;
    private long updated;
}
