// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.*;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            ConnPoolByRoute, BasicPooledConnAdapter, BasicPoolEntry, AbstractConnPool, 
//            PoolEntryRequest

public class ThreadSafeClientConnManager
    implements ClientConnectionManager
{

    public ThreadSafeClientConnManager()
    {
        this(SchemeRegistryFactory.createDefault());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeregistry)
    {
        this(schemeregistry, -1L, TimeUnit.MILLISECONDS);
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeregistry, long l, TimeUnit timeunit)
    {
        this(schemeregistry, l, timeunit, new ConnPerRouteBean());
    }

    public ThreadSafeClientConnManager(SchemeRegistry schemeregistry, long l, TimeUnit timeunit, ConnPerRouteBean connperroutebean)
    {
        Args.notNull(schemeregistry, "Scheme registry");
        log = LogFactory.getLog(getClass());
        schemeRegistry = schemeregistry;
        connPerRoute = connperroutebean;
        connOperator = createConnectionOperator(schemeregistry);
        pool = createConnectionPool(l, timeunit);
        connectionPool = pool;
    }

    public ThreadSafeClientConnManager(HttpParams httpparams, SchemeRegistry schemeregistry)
    {
        Args.notNull(schemeregistry, "Scheme registry");
        log = LogFactory.getLog(getClass());
        schemeRegistry = schemeregistry;
        connPerRoute = new ConnPerRouteBean();
        connOperator = createConnectionOperator(schemeregistry);
        pool = (ConnPoolByRoute)createConnectionPool(httpparams);
        connectionPool = pool;
    }

    public void closeExpiredConnections()
    {
        log.debug("Closing expired connections");
        pool.closeExpiredConnections();
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Closing connections idle longer than ").append(l).append(" ").append(timeunit).toString());
        pool.closeIdleConnections(l, timeunit);
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeregistry)
    {
        return new DefaultClientConnectionOperator(schemeregistry);
    }

    protected AbstractConnPool createConnectionPool(HttpParams httpparams)
    {
        return new ConnPoolByRoute(connOperator, httpparams);
    }

    protected ConnPoolByRoute createConnectionPool(long l, TimeUnit timeunit)
    {
        return new ConnPoolByRoute(connOperator, connPerRoute, 20, l, timeunit);
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

    public int getConnectionsInPool()
    {
        return pool.getConnectionsInPool();
    }

    public int getConnectionsInPool(HttpRoute httproute)
    {
        return pool.getConnectionsInPool(httproute);
    }

    public int getDefaultMaxPerRoute()
    {
        return connPerRoute.getDefaultMaxPerRoute();
    }

    public int getMaxForRoute(HttpRoute httproute)
    {
        return connPerRoute.getMaxForRoute(httproute);
    }

    public int getMaxTotal()
    {
        return pool.getMaxTotalConnections();
    }

    public SchemeRegistry getSchemeRegistry()
    {
        return schemeRegistry;
    }

    public void releaseConnection(ManagedClientConnection managedclientconnection, long l, TimeUnit timeunit)
    {
        BasicPooledConnAdapter basicpooledconnadapter;
        BasicPoolEntry basicpoolentry;
        Args.check(managedclientconnection instanceof BasicPooledConnAdapter, "Connection class mismatch, connection not obtained from this manager");
        basicpooledconnadapter = (BasicPooledConnAdapter)managedclientconnection;
        if(basicpooledconnadapter.getPoolEntry() != null)
        {
            boolean flag3;
            if(basicpooledconnadapter.getManager() == this)
                flag3 = true;
            else
                flag3 = false;
            Asserts.check(flag3, "Connection not obtained from this manager");
        }
        basicpooledconnadapter;
        JVM INSTR monitorenter ;
        basicpoolentry = (BasicPoolEntry)basicpooledconnadapter.getPoolEntry();
        if(basicpoolentry != null)
            break MISSING_BLOCK_LABEL_70;
        basicpooledconnadapter;
        JVM INSTR monitorexit ;
        return;
        if(basicpooledconnadapter.isOpen() && !basicpooledconnadapter.isMarkedReusable())
            basicpooledconnadapter.shutdown();
        boolean flag2 = basicpooledconnadapter.isMarkedReusable();
        if(!log.isDebugEnabled()) goto _L2; else goto _L1
_L1:
        if(!flag2) goto _L4; else goto _L3
_L3:
        log.debug("Released connection is reusable.");
_L2:
        basicpooledconnadapter.detach();
        pool.freeEntry(basicpoolentry, flag2, l, timeunit);
_L5:
        basicpooledconnadapter;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        basicpooledconnadapter;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        log.debug("Released connection is not reusable.");
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug("Exception shutting down released connection.", ioexception);
        boolean flag1;
        flag1 = basicpooledconnadapter.isMarkedReusable();
        if(!log.isDebugEnabled())
            break MISSING_BLOCK_LABEL_233;
        if(!flag1)
            break MISSING_BLOCK_LABEL_255;
        log.debug("Released connection is reusable.");
_L6:
        basicpooledconnadapter.detach();
        pool.freeEntry(basicpoolentry, flag1, l, timeunit);
          goto _L5
        log.debug("Released connection is not reusable.");
          goto _L6
        Exception exception1;
        exception1;
        boolean flag = basicpooledconnadapter.isMarkedReusable();
        if(!log.isDebugEnabled()) goto _L8; else goto _L7
_L7:
        if(!flag) goto _L10; else goto _L9
_L9:
        log.debug("Released connection is reusable.");
_L8:
        basicpooledconnadapter.detach();
        pool.freeEntry(basicpoolentry, flag, l, timeunit);
        throw exception1;
_L10:
        log.debug("Released connection is not reusable.");
        if(true) goto _L8; else goto _L11
_L11:
        if(true) goto _L2; else goto _L12
_L12:
    }

    public ClientConnectionRequest requestConnection(HttpRoute httproute, Object obj)
    {
        return new ClientConnectionRequest() {

            public void abortRequest()
            {
                poolRequest.abortRequest();
            }

            public ManagedClientConnection getConnection(long l, TimeUnit timeunit)
                throws InterruptedException, ConnectionPoolTimeoutException
            {
                Args.notNull(route, "Route");
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Get connection: ").append(route).append(", timeout = ").append(l).toString());
                BasicPoolEntry basicpoolentry = poolRequest.getPoolEntry(l, timeunit);
                return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, basicpoolentry);
            }

            final ThreadSafeClientConnManager this$0;
            final PoolEntryRequest val$poolRequest;
            final HttpRoute val$route;

            
            {
                this$0 = ThreadSafeClientConnManager.this;
                poolRequest = poolentryrequest;
                route = httproute;
                super();
            }
        };
    }

    public void setDefaultMaxPerRoute(int i)
    {
        connPerRoute.setDefaultMaxPerRoute(i);
    }

    public void setMaxForRoute(HttpRoute httproute, int i)
    {
        connPerRoute.setMaxForRoute(httproute, i);
    }

    public void setMaxTotal(int i)
    {
        pool.setMaxTotalConnections(i);
    }

    public void shutdown()
    {
        log.debug("Shutting down");
        pool.shutdown();
    }

    protected final ClientConnectionOperator connOperator;
    protected final ConnPerRouteBean connPerRoute;
    protected final AbstractConnPool connectionPool;
    private final Log log;
    protected final ConnPoolByRoute pool;
    protected final SchemeRegistry schemeRegistry;

}
