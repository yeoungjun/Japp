// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpClientConnection;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn:
//            SchemeRegistryFactory, HttpPoolEntry, DefaultClientConnectionOperator, ManagedClientConnectionImpl

public class BasicClientConnectionManager
    implements ClientConnectionManager
{

    public BasicClientConnectionManager()
    {
        this(SchemeRegistryFactory.createDefault());
    }

    public BasicClientConnectionManager(SchemeRegistry schemeregistry)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(schemeregistry, "Scheme registry");
        schemeRegistry = schemeregistry;
        connOperator = createConnectionOperator(schemeregistry);
    }

    private void assertNotShutdown()
    {
        boolean flag;
        if(!shutdown)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection manager has been shut down");
    }

    private void shutdownConnection(HttpClientConnection httpclientconnection)
    {
        httpclientconnection.shutdown();
_L1:
        return;
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
        {
            log.debug("I/O exception shutting down connection", ioexception);
            return;
        }
          goto _L1
    }

    public void closeExpiredConnections()
    {
        this;
        JVM INSTR monitorenter ;
        assertNotShutdown();
        long l = System.currentTimeMillis();
        if(poolEntry != null && poolEntry.isExpired(l))
        {
            poolEntry.close();
            poolEntry.getTracker().reset();
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        Args.notNull(timeunit, "Time unit");
        this;
        JVM INSTR monitorenter ;
        long l1;
        assertNotShutdown();
        l1 = timeunit.toMillis(l);
        if(l1 < 0L)
            l1 = 0L;
        long l2 = System.currentTimeMillis() - l1;
        if(poolEntry != null && poolEntry.getUpdated() <= l2)
        {
            poolEntry.close();
            poolEntry.getTracker().reset();
        }
        this;
        JVM INSTR monitorexit ;
        return;
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

    ManagedClientConnection getConnection(HttpRoute httproute, Object obj)
    {
        Args.notNull(httproute, "Route");
        this;
        JVM INSTR monitorenter ;
        assertNotShutdown();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Get connection for route ").append(httproute).toString());
        Exception exception;
        boolean flag;
        long l;
        ManagedClientConnectionImpl managedclientconnectionimpl;
        if(conn == null)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.");
        if(poolEntry != null && !poolEntry.getPlannedRoute().equals(httproute))
        {
            poolEntry.close();
            poolEntry = null;
        }
        if(poolEntry == null)
        {
            String s = Long.toString(COUNTER.getAndIncrement());
            org.apache.http.conn.OperatedClientConnection operatedclientconnection = connOperator.createConnection();
            poolEntry = new HttpPoolEntry(log, s, httproute, operatedclientconnection, 0L, TimeUnit.MILLISECONDS);
        }
        l = System.currentTimeMillis();
        if(poolEntry.isExpired(l))
        {
            poolEntry.close();
            poolEntry.getTracker().reset();
        }
        conn = new ManagedClientConnectionImpl(this, connOperator, poolEntry);
        managedclientconnectionimpl = conn;
        this;
        JVM INSTR monitorexit ;
        return managedclientconnectionimpl;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public SchemeRegistry getSchemeRegistry()
    {
        return schemeRegistry;
    }

    public void releaseConnection(ManagedClientConnection managedclientconnection, long l, TimeUnit timeunit)
    {
label0:
        {
            Args.check(managedclientconnection instanceof ManagedClientConnectionImpl, "Connection class mismatch, connection not obtained from this manager");
            synchronized((ManagedClientConnectionImpl)managedclientconnection)
            {
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Releasing connection ").append(managedclientconnection).toString());
                if(managedclientconnectionimpl.getPoolEntry() != null)
                    break label0;
            }
            return;
        }
        boolean flag;
        if(managedclientconnectionimpl.getManager() == this)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection not obtained from this manager");
        this;
        JVM INSTR monitorenter ;
        if(!shutdown)
            break MISSING_BLOCK_LABEL_124;
        shutdownConnection(managedclientconnectionimpl);
        this;
        JVM INSTR monitorexit ;
        managedclientconnectionimpl;
        JVM INSTR monitorexit ;
        return;
        exception;
        managedclientconnectionimpl;
        JVM INSTR monitorexit ;
        throw exception;
        if(managedclientconnectionimpl.isOpen() && !managedclientconnectionimpl.isMarkedReusable())
            shutdownConnection(managedclientconnectionimpl);
        if(!managedclientconnectionimpl.isMarkedReusable()) goto _L2; else goto _L1
_L1:
        HttpPoolEntry httppoolentry = poolEntry;
        if(timeunit == null) goto _L4; else goto _L3
_L3:
        TimeUnit timeunit1 = timeunit;
_L8:
        httppoolentry.updateExpiry(l, timeunit1);
        if(!log.isDebugEnabled()) goto _L2; else goto _L5
_L5:
        if(l <= 0L) goto _L7; else goto _L6
_L6:
        String s = (new StringBuilder()).append("for ").append(l).append(" ").append(timeunit).toString();
_L9:
        log.debug((new StringBuilder()).append("Connection can be kept alive ").append(s).toString());
_L2:
        managedclientconnectionimpl.detach();
        conn = null;
        if(poolEntry.isClosed())
            poolEntry = null;
        this;
        JVM INSTR monitorexit ;
        managedclientconnectionimpl;
        JVM INSTR monitorexit ;
        return;
_L4:
        timeunit1 = TimeUnit.MILLISECONDS;
          goto _L8
_L7:
        s = "indefinitely";
          goto _L9
        Exception exception2;
        exception2;
        managedclientconnectionimpl.detach();
        conn = null;
        if(poolEntry.isClosed())
            poolEntry = null;
        throw exception2;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
          goto _L8
    }

    public final ClientConnectionRequest requestConnection(final HttpRoute route, final Object state)
    {
        return new ClientConnectionRequest() {

            public void abortRequest()
            {
            }

            public ManagedClientConnection getConnection(long l, TimeUnit timeunit)
            {
                return BasicClientConnectionManager.this.getConnection(route, state);
            }

            final BasicClientConnectionManager this$0;
            final HttpRoute val$route;
            final Object val$state;

            
            {
                this$0 = BasicClientConnectionManager.this;
                route = httproute;
                state = obj;
                super();
            }
        };
    }

    public void shutdown()
    {
        this;
        JVM INSTR monitorenter ;
        shutdown = true;
        if(poolEntry != null)
            poolEntry.close();
        poolEntry = null;
        conn = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        poolEntry = null;
        conn = null;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private static final AtomicLong COUNTER = new AtomicLong();
    public static final String MISUSE_MESSAGE = "Invalid use of BasicClientConnManager: connection still allocated.\nMake sure to release the connection before allocating another one.";
    private ManagedClientConnectionImpl conn;
    private final ClientConnectionOperator connOperator;
    private final Log log;
    private HttpPoolEntry poolEntry;
    private final SchemeRegistry schemeRegistry;
    private volatile boolean shutdown;

}
