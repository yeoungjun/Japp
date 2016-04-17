// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.IdleConnectionHandler;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            PoolEntryRequest, BasicPoolEntry

public abstract class AbstractConnPool
{

    protected AbstractConnPool()
    {
        leasedConnections = new HashSet();
        idleConnHandler = new IdleConnectionHandler();
    }

    protected void closeConnection(OperatedClientConnection operatedclientconnection)
    {
        if(operatedclientconnection == null)
            break MISSING_BLOCK_LABEL_10;
        operatedclientconnection.close();
        return;
        IOException ioexception;
        ioexception;
        log.debug("I/O error closing connection", ioexception);
        return;
    }

    public void closeExpiredConnections()
    {
        poolLock.lock();
        idleConnHandler.closeExpiredConnections();
        poolLock.unlock();
        return;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        Args.notNull(timeunit, "Time unit");
        poolLock.lock();
        idleConnHandler.closeIdleConnections(timeunit.toMillis(l));
        poolLock.unlock();
        return;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    public abstract void deleteClosedConnections();

    public void enableConnectionGC()
        throws IllegalStateException
    {
    }

    public abstract void freeEntry(BasicPoolEntry basicpoolentry, boolean flag, long l, TimeUnit timeunit);

    public final BasicPoolEntry getEntry(HttpRoute httproute, Object obj, long l, TimeUnit timeunit)
        throws ConnectionPoolTimeoutException, InterruptedException
    {
        return requestPoolEntry(httproute, obj).getPoolEntry(l, timeunit);
    }

    protected abstract void handleLostEntry(HttpRoute httproute);

    public void handleReference(Reference reference)
    {
    }

    public abstract PoolEntryRequest requestPoolEntry(HttpRoute httproute, Object obj);

    public void shutdown()
    {
        poolLock.lock();
        boolean flag = isShutDown;
        if(flag)
        {
            poolLock.unlock();
            return;
        }
        BasicPoolEntry basicpoolentry;
        for(Iterator iterator = leasedConnections.iterator(); iterator.hasNext(); closeConnection(basicpoolentry.getConnection()))
        {
            basicpoolentry = (BasicPoolEntry)iterator.next();
            iterator.remove();
        }

        break MISSING_BLOCK_LABEL_88;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
        idleConnHandler.removeAll();
        isShutDown = true;
        poolLock.unlock();
        return;
    }

    protected IdleConnectionHandler idleConnHandler;
    protected volatile boolean isShutDown;
    protected Set issuedConnections;
    protected Set leasedConnections;
    private final Log log = LogFactory.getLog(getClass());
    protected int numConnections;
    protected final Lock poolLock = new ReentrantLock();
    protected ReferenceQueue refQueue;
}
