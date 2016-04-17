// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn:
//            SchemeRegistryFactory, DefaultClientConnectionOperator, AbstractPooledConnAdapter, AbstractPoolEntry

public class SingleClientConnManager
    implements ClientConnectionManager
{
    protected class ConnAdapter extends AbstractPooledConnAdapter
    {

        final SingleClientConnManager this$0;

        protected ConnAdapter(PoolEntry poolentry, HttpRoute httproute)
        {
            this$0 = SingleClientConnManager.this;
            super(SingleClientConnManager.this, poolentry);
            markReusable();
            poolentry.route = httproute;
        }
    }

    protected class PoolEntry extends AbstractPoolEntry
    {

        protected void close()
            throws IOException
        {
            shutdownEntry();
            if(connection.isOpen())
                connection.close();
        }

        protected void shutdown()
            throws IOException
        {
            shutdownEntry();
            if(connection.isOpen())
                connection.shutdown();
        }

        final SingleClientConnManager this$0;

        protected PoolEntry()
        {
            this$0 = SingleClientConnManager.this;
            super(connOperator, null);
        }
    }


    public SingleClientConnManager()
    {
        this(SchemeRegistryFactory.createDefault());
    }

    public SingleClientConnManager(SchemeRegistry schemeregistry)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(schemeregistry, "Scheme registry");
        schemeRegistry = schemeregistry;
        connOperator = createConnectionOperator(schemeregistry);
        uniquePoolEntry = new PoolEntry();
        managedConn = null;
        lastReleaseTime = -1L;
        alwaysShutDown = false;
        isShutDown = false;
    }

    public SingleClientConnManager(HttpParams httpparams, SchemeRegistry schemeregistry)
    {
        this(schemeregistry);
    }

    protected final void assertStillUp()
        throws IllegalStateException
    {
        boolean flag;
        if(!isShutDown)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Manager is shut down");
    }

    public void closeExpiredConnections()
    {
        long l = connectionExpiresTime;
        if(System.currentTimeMillis() >= l)
            closeIdleConnections(0L, TimeUnit.MILLISECONDS);
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        assertStillUp();
        Args.notNull(timeunit, "Time unit");
        this;
        JVM INSTR monitorenter ;
        long l1;
        long l2;
        if(managedConn != null || !uniquePoolEntry.connection.isOpen())
            break MISSING_BLOCK_LABEL_67;
        l1 = System.currentTimeMillis() - timeunit.toMillis(l);
        l2 = lastReleaseTime;
        if(l2 > l1)
            break MISSING_BLOCK_LABEL_67;
        uniquePoolEntry.close();
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
        IOException ioexception;
        ioexception;
        log.debug("Problem closing idle connection.", ioexception);
          goto _L1
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeregistry)
    {
        return new DefaultClientConnectionOperator(schemeregistry);
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

    public ManagedClientConnection getConnection(HttpRoute httproute, Object obj)
    {
        Args.notNull(httproute, "Route");
        assertStillUp();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Get connection for route ").append(httproute).toString());
        this;
        JVM INSTR monitorenter ;
        Exception exception;
        boolean flag;
        boolean flag1;
        boolean flag2;
        ConnAdapter connadapter;
        IOException ioexception;
        RouteTracker routetracker;
        boolean flag3;
        if(managedConn == null)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
        flag1 = false;
        closeExpiredConnections();
        if(!uniquePoolEntry.connection.isOpen()) goto _L2; else goto _L1
_L1:
        routetracker = uniquePoolEntry.tracker;
        if(routetracker == null) goto _L4; else goto _L3
_L3:
        flag3 = routetracker.toRoute().equals(httproute);
        if(flag3) goto _L5; else goto _L4
_L4:
        flag2 = true;
_L9:
        if(!flag2)
            break MISSING_BLOCK_LABEL_140;
        flag1 = true;
        uniquePoolEntry.shutdown();
_L7:
        if(!flag1)
            break MISSING_BLOCK_LABEL_157;
        uniquePoolEntry = new PoolEntry();
        managedConn = new ConnAdapter(uniquePoolEntry, httproute);
        connadapter = managedConn;
        this;
        JVM INSTR monitorexit ;
        return connadapter;
        ioexception;
        log.debug("Problem shutting down connection.", ioexception);
        if(true) goto _L7; else goto _L6
_L6:
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L5:
        flag1 = false;
        flag2 = false;
        continue; /* Loop/switch isn't completed */
_L2:
        flag1 = true;
        flag2 = false;
        if(true) goto _L9; else goto _L8
_L8:
    }

    public SchemeRegistry getSchemeRegistry()
    {
        return schemeRegistry;
    }

    public void releaseConnection(ManagedClientConnection managedclientconnection, long l, TimeUnit timeunit)
    {
label0:
        {
            Args.check(managedclientconnection instanceof ConnAdapter, "Connection class mismatch, connection not obtained from this manager");
            assertStillUp();
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Releasing connection ").append(managedclientconnection).toString());
            synchronized((ConnAdapter)managedclientconnection)
            {
                if(connadapter.poolEntry != null)
                    break label0;
            }
            return;
        }
        boolean flag;
        if(connadapter.getManager() == this)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection not obtained from this manager");
        if(connadapter.isOpen() && (alwaysShutDown || !connadapter.isMarkedReusable()))
        {
            if(log.isDebugEnabled())
                log.debug("Released connection open but not reusable.");
            connadapter.shutdown();
        }
        connadapter.detach();
        this;
        JVM INSTR monitorenter ;
        managedConn = null;
        lastReleaseTime = System.currentTimeMillis();
        if(l <= 0L) goto _L2; else goto _L1
_L1:
        connectionExpiresTime = timeunit.toMillis(l) + lastReleaseTime;
_L3:
        this;
        JVM INSTR monitorexit ;
_L4:
        connadapter;
        JVM INSTR monitorexit ;
        return;
        exception;
        connadapter;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        connectionExpiresTime = 0x7fffffffffffffffL;
          goto _L3
        Exception exception4;
        exception4;
        this;
        JVM INSTR monitorexit ;
        throw exception4;
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug("Exception shutting down released connection.", ioexception);
        connadapter.detach();
        this;
        JVM INSTR monitorenter ;
        managedConn = null;
        lastReleaseTime = System.currentTimeMillis();
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_300;
        connectionExpiresTime = timeunit.toMillis(l) + lastReleaseTime;
_L5:
        this;
        JVM INSTR monitorexit ;
          goto _L4
        Exception exception3;
        exception3;
        this;
        JVM INSTR monitorexit ;
        throw exception3;
        connectionExpiresTime = 0x7fffffffffffffffL;
          goto _L5
        Exception exception1;
        exception1;
        connadapter.detach();
        this;
        JVM INSTR monitorenter ;
        managedConn = null;
        lastReleaseTime = System.currentTimeMillis();
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_357;
        connectionExpiresTime = timeunit.toMillis(l) + lastReleaseTime;
_L6:
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        connectionExpiresTime = 0x7fffffffffffffffL;
          goto _L6
        Exception exception2;
        exception2;
        this;
        JVM INSTR monitorexit ;
        throw exception2;
          goto _L3
    }

    public final ClientConnectionRequest requestConnection(final HttpRoute route, final Object state)
    {
        return new ClientConnectionRequest() {

            public void abortRequest()
            {
            }

            public ManagedClientConnection getConnection(long l, TimeUnit timeunit)
            {
                return SingleClientConnManager.this.getConnection(route, state);
            }

            final SingleClientConnManager this$0;
            final HttpRoute val$route;
            final Object val$state;

            
            {
                this$0 = SingleClientConnManager.this;
                route = httproute;
                state = obj;
                super();
            }
        };
    }

    protected void revokeConnection()
    {
        ConnAdapter connadapter = managedConn;
        if(connadapter == null)
            return;
        connadapter.detach();
        this;
        JVM INSTR monitorenter ;
        uniquePoolEntry.shutdown();
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        IOException ioexception;
        ioexception;
        log.debug("Problem while shutting down connection.", ioexception);
          goto _L1
    }

    public void shutdown()
    {
        isShutDown = true;
        this;
        JVM INSTR monitorenter ;
        if(uniquePoolEntry != null)
            uniquePoolEntry.shutdown();
        uniquePoolEntry = null;
        managedConn = null;
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
        IOException ioexception;
        ioexception;
        log.debug("Problem while shutting down manager.", ioexception);
        uniquePoolEntry = null;
        managedConn = null;
          goto _L1
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        uniquePoolEntry = null;
        managedConn = null;
        throw exception;
    }

    public static final String MISUSE_MESSAGE = "Invalid use of SingleClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    protected final boolean alwaysShutDown;
    protected final ClientConnectionOperator connOperator;
    protected volatile long connectionExpiresTime;
    protected volatile boolean isShutDown;
    protected volatile long lastReleaseTime;
    private final Log log;
    protected volatile ConnAdapter managedConn;
    protected final SchemeRegistry schemeRegistry;
    protected volatile PoolEntry uniquePoolEntry;
}
