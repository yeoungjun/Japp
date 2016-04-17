// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn:
//            SchemeRegistryFactory, SystemDefaultDnsResolver, HttpConnPool, HttpPoolEntry, 
//            DefaultClientConnectionOperator, ManagedClientConnectionImpl

public class PoolingClientConnectionManager
    implements ClientConnectionManager, ConnPoolControl
{

    public PoolingClientConnectionManager()
    {
        this(SchemeRegistryFactory.createDefault());
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeregistry)
    {
        this(schemeregistry, -1L, TimeUnit.MILLISECONDS);
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeregistry, long l, TimeUnit timeunit)
    {
        this(schemeregistry, l, timeunit, ((DnsResolver) (new SystemDefaultDnsResolver())));
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeregistry, long l, TimeUnit timeunit, DnsResolver dnsresolver)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(schemeregistry, "Scheme registry");
        Args.notNull(dnsresolver, "DNS resolver");
        schemeRegistry = schemeregistry;
        dnsResolver = dnsresolver;
        operator = createConnectionOperator(schemeregistry);
        pool = new HttpConnPool(log, operator, 2, 20, l, timeunit);
    }

    public PoolingClientConnectionManager(SchemeRegistry schemeregistry, DnsResolver dnsresolver)
    {
        this(schemeregistry, -1L, TimeUnit.MILLISECONDS, dnsresolver);
    }

    private String format(HttpRoute httproute, Object obj)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[route: ").append(httproute).append("]");
        if(obj != null)
            stringbuilder.append("[state: ").append(obj).append("]");
        return stringbuilder.toString();
    }

    private String format(HttpPoolEntry httppoolentry)
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[id: ").append(httppoolentry.getId()).append("]");
        stringbuilder.append("[route: ").append(httppoolentry.getRoute()).append("]");
        Object obj = httppoolentry.getState();
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

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeregistry)
    {
        return new DefaultClientConnectionOperator(schemeregistry, dnsResolver);
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

    public int getDefaultMaxPerRoute()
    {
        return pool.getDefaultMaxPerRoute();
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

    public SchemeRegistry getSchemeRegistry()
    {
        return schemeRegistry;
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

    ManagedClientConnection leaseConnection(Future future, long l, TimeUnit timeunit)
        throws InterruptedException, ConnectionPoolTimeoutException
    {
        HttpPoolEntry httppoolentry;
        boolean flag;
        ManagedClientConnectionImpl managedclientconnectionimpl;
        try
        {
            httppoolentry = (HttpPoolEntry)future.get(l, timeunit);
        }
        catch(ExecutionException executionexception)
        {
            Object obj = executionexception.getCause();
            if(obj == null)
                obj = executionexception;
            log.error("Unexpected exception leasing connection from pool", ((Throwable) (obj)));
            throw new InterruptedException();
        }
        catch(TimeoutException timeoutexception)
        {
            throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        }
        if(httppoolentry == null)
            break MISSING_BLOCK_LABEL_28;
        if(!future.isCancelled())
            break MISSING_BLOCK_LABEL_75;
        throw new InterruptedException();
        if(httppoolentry.getConnection() != null)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Pool entry with no connection");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection leased: ").append(format(httppoolentry)).append(formatStats((HttpRoute)httppoolentry.getRoute())).toString());
        managedclientconnectionimpl = new ManagedClientConnectionImpl(this, operator, httppoolentry);
        return managedclientconnectionimpl;
    }

    public void releaseConnection(ManagedClientConnection managedclientconnection, long l, TimeUnit timeunit)
    {
        ManagedClientConnectionImpl managedclientconnectionimpl;
        HttpPoolEntry httppoolentry;
        Args.check(managedclientconnection instanceof ManagedClientConnectionImpl, "Connection class mismatch, connection not obtained from this manager");
        managedclientconnectionimpl = (ManagedClientConnectionImpl)managedclientconnection;
        boolean flag;
        if(managedclientconnectionimpl.getManager() == this)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection not obtained from this manager");
        managedclientconnectionimpl;
        JVM INSTR monitorenter ;
        httppoolentry = managedclientconnectionimpl.detach();
        if(httppoolentry != null)
            break MISSING_BLOCK_LABEL_61;
        managedclientconnectionimpl;
        JVM INSTR monitorexit ;
        return;
        boolean flag1;
        if(!managedclientconnectionimpl.isOpen())
            break MISSING_BLOCK_LABEL_86;
        flag1 = managedclientconnectionimpl.isMarkedReusable();
        if(flag1)
            break MISSING_BLOCK_LABEL_86;
        managedclientconnectionimpl.shutdown();
_L7:
        if(!managedclientconnectionimpl.isMarkedReusable()) goto _L2; else goto _L1
_L1:
        if(timeunit == null) goto _L4; else goto _L3
_L3:
        TimeUnit timeunit1 = timeunit;
_L8:
        httppoolentry.updateExpiry(l, timeunit1);
        if(!log.isDebugEnabled()) goto _L2; else goto _L5
_L5:
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_351;
        String s = (new StringBuilder()).append("for ").append(l).append(" ").append(timeunit).toString();
_L9:
        log.debug((new StringBuilder()).append("Connection ").append(format(httppoolentry)).append(" can be kept alive ").append(s).toString());
_L2:
        pool.release(httppoolentry, managedclientconnectionimpl.isMarkedReusable());
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection released: ").append(format(httppoolentry)).append(formatStats((HttpRoute)httppoolentry.getRoute())).toString());
        managedclientconnectionimpl;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        managedclientconnectionimpl;
        JVM INSTR monitorexit ;
        throw exception;
        IOException ioexception;
        ioexception;
        if(!log.isDebugEnabled()) goto _L7; else goto _L6
_L6:
        log.debug("I/O exception shutting down released connection", ioexception);
          goto _L7
        Exception exception1;
        exception1;
        pool.release(httppoolentry, managedclientconnectionimpl.isMarkedReusable());
        throw exception1;
_L4:
        timeunit1 = TimeUnit.MILLISECONDS;
          goto _L8
        s = "indefinitely";
          goto _L9
    }

    public ClientConnectionRequest requestConnection(HttpRoute httproute, Object obj)
    {
        Args.notNull(httproute, "HTTP route");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection request: ").append(format(httproute, obj)).append(formatStats(httproute)).toString());
        return new ClientConnectionRequest() {

            public void abortRequest()
            {
                future.cancel(true);
            }

            public ManagedClientConnection getConnection(long l, TimeUnit timeunit)
                throws InterruptedException, ConnectionPoolTimeoutException
            {
                return leaseConnection(future, l, timeunit);
            }

            final PoolingClientConnectionManager this$0;
            final Future val$future;

            
            {
                this$0 = PoolingClientConnectionManager.this;
                future = future1;
                super();
            }
        };
    }

    public void setDefaultMaxPerRoute(int i)
    {
        pool.setDefaultMaxPerRoute(i);
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

    private final DnsResolver dnsResolver;
    private final Log log;
    private final ClientConnectionOperator operator;
    private final HttpConnPool pool;
    private final SchemeRegistry schemeRegistry;
}
