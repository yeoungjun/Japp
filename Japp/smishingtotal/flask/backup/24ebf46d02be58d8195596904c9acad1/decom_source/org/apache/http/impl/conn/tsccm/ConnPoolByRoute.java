// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.*;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            AbstractConnPool, BasicPoolEntry, RouteSpecificPool, WaitingThreadAborter, 
//            WaitingThread, PoolEntryRequest

public class ConnPoolByRoute extends AbstractConnPool
{

    public ConnPoolByRoute(ClientConnectionOperator clientconnectionoperator, ConnPerRoute connperroute, int i)
    {
        this(clientconnectionoperator, connperroute, i, -1L, TimeUnit.MILLISECONDS);
    }

    public ConnPoolByRoute(ClientConnectionOperator clientconnectionoperator, ConnPerRoute connperroute, int i, long l, TimeUnit timeunit)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(clientconnectionoperator, "Connection operator");
        Args.notNull(connperroute, "Connections per route");
        poolLock = super.poolLock;
        leasedConnections = super.leasedConnections;
        operator = clientconnectionoperator;
        connPerRoute = connperroute;
        maxTotalConnections = i;
        freeConnections = createFreeConnQueue();
        waitingThreads = createWaitingThreadQueue();
        routeToPool = createRouteToPoolMap();
        connTTL = l;
        connTTLTimeUnit = timeunit;
    }

    public ConnPoolByRoute(ClientConnectionOperator clientconnectionoperator, HttpParams httpparams)
    {
        this(clientconnectionoperator, ConnManagerParams.getMaxConnectionsPerRoute(httpparams), ConnManagerParams.getMaxTotalConnections(httpparams));
    }

    private void closeConnection(BasicPoolEntry basicpoolentry)
    {
        OperatedClientConnection operatedclientconnection;
        operatedclientconnection = basicpoolentry.getConnection();
        if(operatedclientconnection == null)
            break MISSING_BLOCK_LABEL_15;
        operatedclientconnection.close();
        return;
        IOException ioexception;
        ioexception;
        log.debug("I/O error closing connection", ioexception);
        return;
    }

    public void closeExpiredConnections()
    {
        long l;
        log.debug("Closing expired connections");
        l = System.currentTimeMillis();
        poolLock.lock();
        Iterator iterator = freeConnections.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            BasicPoolEntry basicpoolentry = (BasicPoolEntry)iterator.next();
            if(basicpoolentry.isExpired(l))
            {
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Closing connection expired @ ").append(new Date(basicpoolentry.getExpiry())).toString());
                iterator.remove();
                deleteEntry(basicpoolentry);
            }
        } while(true);
        break MISSING_BLOCK_LABEL_145;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
        poolLock.unlock();
        return;
    }

    public void closeIdleConnections(long l, TimeUnit timeunit)
    {
        long l2;
        long l1 = 0L;
        Args.notNull(timeunit, "Time unit");
        if(l > l1)
            l1 = l;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Closing connections idle longer than ").append(l1).append(" ").append(timeunit).toString());
        l2 = System.currentTimeMillis() - timeunit.toMillis(l1);
        poolLock.lock();
        Iterator iterator = freeConnections.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            BasicPoolEntry basicpoolentry = (BasicPoolEntry)iterator.next();
            if(basicpoolentry.getUpdated() <= l2)
            {
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Closing connection last used @ ").append(new Date(basicpoolentry.getUpdated())).toString());
                iterator.remove();
                deleteEntry(basicpoolentry);
            }
        } while(true);
        break MISSING_BLOCK_LABEL_216;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
        poolLock.unlock();
        return;
    }

    protected BasicPoolEntry createEntry(RouteSpecificPool routespecificpool, ClientConnectionOperator clientconnectionoperator)
    {
        BasicPoolEntry basicpoolentry;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Creating new connection [").append(routespecificpool.getRoute()).append("]").toString());
        basicpoolentry = new BasicPoolEntry(clientconnectionoperator, routespecificpool.getRoute(), connTTL, connTTLTimeUnit);
        poolLock.lock();
        routespecificpool.createdEntry(basicpoolentry);
        numConnections = 1 + numConnections;
        leasedConnections.add(basicpoolentry);
        poolLock.unlock();
        return basicpoolentry;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    protected Queue createFreeConnQueue()
    {
        return new LinkedList();
    }

    protected Map createRouteToPoolMap()
    {
        return new HashMap();
    }

    protected Queue createWaitingThreadQueue()
    {
        return new LinkedList();
    }

    public void deleteClosedConnections()
    {
        poolLock.lock();
        Iterator iterator = freeConnections.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            BasicPoolEntry basicpoolentry = (BasicPoolEntry)iterator.next();
            if(!basicpoolentry.getConnection().isOpen())
            {
                iterator.remove();
                deleteEntry(basicpoolentry);
            }
        } while(true);
        break MISSING_BLOCK_LABEL_76;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
        poolLock.unlock();
        return;
    }

    protected void deleteEntry(BasicPoolEntry basicpoolentry)
    {
        HttpRoute httproute;
        httproute = basicpoolentry.getPlannedRoute();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Deleting connection [").append(httproute).append("][").append(basicpoolentry.getState()).append("]").toString());
        poolLock.lock();
        closeConnection(basicpoolentry);
        RouteSpecificPool routespecificpool = getRoutePool(httproute, true);
        routespecificpool.deleteEntry(basicpoolentry);
        numConnections = -1 + numConnections;
        if(routespecificpool.isUnused())
            routeToPool.remove(httproute);
        poolLock.unlock();
        return;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    protected void deleteLeastUsedEntry()
    {
        poolLock.lock();
        BasicPoolEntry basicpoolentry = (BasicPoolEntry)freeConnections.remove();
        if(basicpoolentry == null) goto _L2; else goto _L1
_L1:
        deleteEntry(basicpoolentry);
_L4:
        poolLock.unlock();
        return;
_L2:
        if(!log.isDebugEnabled()) goto _L4; else goto _L3
_L3:
        log.debug("No free connection to delete");
          goto _L4
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    public void freeEntry(BasicPoolEntry basicpoolentry, boolean flag, long l, TimeUnit timeunit)
    {
        HttpRoute httproute;
        httproute = basicpoolentry.getPlannedRoute();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Releasing connection [").append(httproute).append("][").append(basicpoolentry.getState()).append("]").toString());
        poolLock.lock();
        if(!shutdown)
            break MISSING_BLOCK_LABEL_97;
        closeConnection(basicpoolentry);
        poolLock.unlock();
        return;
        RouteSpecificPool routespecificpool;
        leasedConnections.remove(basicpoolentry);
        routespecificpool = getRoutePool(httproute, true);
        if(!flag) goto _L2; else goto _L1
_L1:
        if(routespecificpool.getCapacity() < 0) goto _L2; else goto _L3
_L3:
        if(!log.isDebugEnabled()) goto _L5; else goto _L4
_L4:
        if(l <= 0L) goto _L7; else goto _L6
_L6:
        String s = (new StringBuilder()).append("for ").append(l).append(" ").append(timeunit).toString();
_L8:
        log.debug((new StringBuilder()).append("Pooling connection [").append(httproute).append("][").append(basicpoolentry.getState()).append("]; keep alive ").append(s).toString());
_L5:
        routespecificpool.freeEntry(basicpoolentry);
        basicpoolentry.updateExpiry(l, timeunit);
        freeConnections.add(basicpoolentry);
_L9:
        notifyWaitingThread(routespecificpool);
        poolLock.unlock();
        return;
_L7:
        s = "indefinitely";
          goto _L8
_L2:
        closeConnection(basicpoolentry);
        routespecificpool.dropEntry();
        numConnections = -1 + numConnections;
          goto _L9
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
          goto _L8
    }

    public int getConnectionsInPool()
    {
        poolLock.lock();
        int i = numConnections;
        poolLock.unlock();
        return i;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    public int getConnectionsInPool(HttpRoute httproute)
    {
        poolLock.lock();
        RouteSpecificPool routespecificpool = getRoutePool(httproute, false);
        int i;
        i = 0;
        if(routespecificpool == null)
            break MISSING_BLOCK_LABEL_33;
        int j = routespecificpool.getEntryCount();
        i = j;
        poolLock.unlock();
        return i;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    protected BasicPoolEntry getEntryBlocking(HttpRoute httproute, Object obj, long l, TimeUnit timeunit, WaitingThreadAborter waitingthreadaborter)
        throws ConnectionPoolTimeoutException, InterruptedException
    {
        Date date;
        BasicPoolEntry basicpoolentry;
        int i = l != 0L;
        date = null;
        if(i > 0)
            date = new Date(System.currentTimeMillis() + timeunit.toMillis(l));
        basicpoolentry = null;
        poolLock.lock();
        RouteSpecificPool routespecificpool = getRoutePool(httproute, true);
        WaitingThread waitingthread = null;
_L1:
        if(basicpoolentry != null)
            break MISSING_BLOCK_LABEL_199;
        boolean flag;
        BasicPoolEntry basicpoolentry1;
        if(!shutdown)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection pool shut down");
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("[").append(httproute).append("] total kept alive: ").append(freeConnections.size()).append(", total issued: ").append(leasedConnections.size()).append(", total allocated: ").append(numConnections).append(" out of ").append(maxTotalConnections).toString());
        basicpoolentry1 = getFreeEntry(routespecificpool, obj);
        basicpoolentry = basicpoolentry1;
        if(basicpoolentry == null)
            break MISSING_BLOCK_LABEL_217;
        poolLock.unlock();
        return basicpoolentry;
        Exception exception;
        boolean flag1;
        Exception exception1;
        boolean flag2;
        if(routespecificpool.getCapacity() > 0)
            flag1 = true;
        else
            flag1 = false;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Available capacity: ").append(routespecificpool.getCapacity()).append(" out of ").append(routespecificpool.getMaxEntries()).append(" [").append(httproute).append("][").append(obj).append("]").toString());
        if(!flag1)
            break MISSING_BLOCK_LABEL_343;
        if(numConnections >= maxTotalConnections)
            break MISSING_BLOCK_LABEL_343;
        basicpoolentry = createEntry(routespecificpool, operator);
          goto _L1
        if(!flag1)
            break MISSING_BLOCK_LABEL_387;
        if(freeConnections.isEmpty())
            break MISSING_BLOCK_LABEL_387;
        deleteLeastUsedEntry();
        routespecificpool = getRoutePool(httproute, true);
        basicpoolentry = createEntry(routespecificpool, operator);
          goto _L1
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Need to wait for connection [").append(httproute).append("][").append(obj).append("]").toString());
        if(waitingthread != null)
            break MISSING_BLOCK_LABEL_472;
        waitingthread = newWaitingThread(poolLock.newCondition(), routespecificpool);
        waitingthreadaborter.setWaitingThread(waitingthread);
        routespecificpool.queueThread(waitingthread);
        waitingThreads.add(waitingthread);
        flag2 = waitingthread.await(date);
        routespecificpool.removeThread(waitingthread);
        waitingThreads.remove(waitingthread);
        if(flag2 || date == null) goto _L1; else goto _L2
_L2:
        if(date.getTime() > System.currentTimeMillis()) goto _L1; else goto _L3
_L3:
        throw new ConnectionPoolTimeoutException("Timeout waiting for connection from pool");
        exception;
        poolLock.unlock();
        throw exception;
        exception1;
        routespecificpool.removeThread(waitingthread);
        waitingThreads.remove(waitingthread);
        throw exception1;
    }

    protected BasicPoolEntry getFreeEntry(RouteSpecificPool routespecificpool, Object obj)
    {
        BasicPoolEntry basicpoolentry;
        boolean flag;
        basicpoolentry = null;
        poolLock.lock();
        flag = false;
_L2:
        if(flag)
            break; /* Loop/switch isn't completed */
        basicpoolentry = routespecificpool.allocEntry(obj);
        if(basicpoolentry == null)
            break MISSING_BLOCK_LABEL_221;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Getting free connection [").append(routespecificpool.getRoute()).append("][").append(obj).append("]").toString());
        freeConnections.remove(basicpoolentry);
        if(basicpoolentry.isExpired(System.currentTimeMillis()))
        {
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Closing expired free connection [").append(routespecificpool.getRoute()).append("][").append(obj).append("]").toString());
            closeConnection(basicpoolentry);
            routespecificpool.dropEntry();
            numConnections = -1 + numConnections;
            continue; /* Loop/switch isn't completed */
        }
        break MISSING_BLOCK_LABEL_204;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
        leasedConnections.add(basicpoolentry);
        flag = true;
        continue; /* Loop/switch isn't completed */
        flag = true;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("No free connections [").append(routespecificpool.getRoute()).append("][").append(obj).append("]").toString());
        if(true) goto _L2; else goto _L1
_L1:
        poolLock.unlock();
        return basicpoolentry;
    }

    protected Lock getLock()
    {
        return poolLock;
    }

    public int getMaxTotalConnections()
    {
        return maxTotalConnections;
    }

    protected RouteSpecificPool getRoutePool(HttpRoute httproute, boolean flag)
    {
        poolLock.lock();
        RouteSpecificPool routespecificpool = (RouteSpecificPool)routeToPool.get(httproute);
        if(routespecificpool != null || !flag)
            break MISSING_BLOCK_LABEL_53;
        routespecificpool = newRouteSpecificPool(httproute);
        routeToPool.put(httproute, routespecificpool);
        poolLock.unlock();
        return routespecificpool;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    protected void handleLostEntry(HttpRoute httproute)
    {
        poolLock.lock();
        RouteSpecificPool routespecificpool = getRoutePool(httproute, true);
        routespecificpool.dropEntry();
        if(routespecificpool.isUnused())
            routeToPool.remove(httproute);
        numConnections = -1 + numConnections;
        notifyWaitingThread(routespecificpool);
        poolLock.unlock();
        return;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute httproute)
    {
        return new RouteSpecificPool(httproute, connPerRoute);
    }

    protected WaitingThread newWaitingThread(Condition condition, RouteSpecificPool routespecificpool)
    {
        return new WaitingThread(condition, routespecificpool);
    }

    protected void notifyWaitingThread(RouteSpecificPool routespecificpool)
    {
        poolLock.lock();
        if(routespecificpool == null) goto _L2; else goto _L1
_L1:
        if(!routespecificpool.hasThread()) goto _L2; else goto _L3
_L3:
        WaitingThread waitingthread;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Notifying thread waiting on pool [").append(routespecificpool.getRoute()).append("]").toString());
        waitingthread = routespecificpool.nextThread();
_L4:
        if(waitingthread == null)
            break MISSING_BLOCK_LABEL_85;
        waitingthread.wakeup();
        poolLock.unlock();
        return;
_L2:
label0:
        {
            if(waitingThreads.isEmpty())
                break label0;
            if(log.isDebugEnabled())
                log.debug("Notifying thread waiting on any pool");
            waitingthread = (WaitingThread)waitingThreads.remove();
        }
          goto _L4
        boolean flag = log.isDebugEnabled();
        waitingthread = null;
        if(!flag) goto _L4; else goto _L5
_L5:
        log.debug("Notifying no-one, there are no waiting threads");
        waitingthread = null;
          goto _L4
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    public PoolEntryRequest requestPoolEntry(HttpRoute httproute, Object obj)
    {
        return new PoolEntryRequest() {

            public void abortRequest()
            {
                poolLock.lock();
                aborter.abort();
                poolLock.unlock();
                return;
                Exception exception;
                exception;
                poolLock.unlock();
                throw exception;
            }

            public BasicPoolEntry getPoolEntry(long l, TimeUnit timeunit)
                throws InterruptedException, ConnectionPoolTimeoutException
            {
                return getEntryBlocking(route, state, l, timeunit, aborter);
            }

            final ConnPoolByRoute this$0;
            final WaitingThreadAborter val$aborter;
            final HttpRoute val$route;
            final Object val$state;

            
            {
                this$0 = ConnPoolByRoute.this;
                aborter = waitingthreadaborter;
                route = httproute;
                state = obj;
                super();
            }
        };
    }

    public void setMaxTotalConnections(int i)
    {
        poolLock.lock();
        maxTotalConnections = i;
        poolLock.unlock();
        return;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
    }

    public void shutdown()
    {
        poolLock.lock();
        boolean flag = shutdown;
        if(flag)
        {
            poolLock.unlock();
            return;
        }
        shutdown = true;
        BasicPoolEntry basicpoolentry1;
        for(Iterator iterator = leasedConnections.iterator(); iterator.hasNext(); closeConnection(basicpoolentry1))
        {
            basicpoolentry1 = (BasicPoolEntry)iterator.next();
            iterator.remove();
        }

        break MISSING_BLOCK_LABEL_90;
        Exception exception;
        exception;
        poolLock.unlock();
        throw exception;
        BasicPoolEntry basicpoolentry;
        for(Iterator iterator1 = freeConnections.iterator(); iterator1.hasNext(); closeConnection(basicpoolentry))
        {
            basicpoolentry = (BasicPoolEntry)iterator1.next();
            iterator1.remove();
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Closing connection [").append(basicpoolentry.getPlannedRoute()).append("][").append(basicpoolentry.getState()).append("]").toString());
        }

        WaitingThread waitingthread;
        for(Iterator iterator2 = waitingThreads.iterator(); iterator2.hasNext(); waitingthread.wakeup())
        {
            waitingthread = (WaitingThread)iterator2.next();
            iterator2.remove();
        }

        routeToPool.clear();
        poolLock.unlock();
        return;
    }

    protected final ConnPerRoute connPerRoute;
    private final long connTTL;
    private final TimeUnit connTTLTimeUnit;
    protected final Queue freeConnections;
    protected final Set leasedConnections;
    private final Log log;
    protected volatile int maxTotalConnections;
    protected volatile int numConnections;
    protected final ClientConnectionOperator operator;
    private final Lock poolLock;
    protected final Map routeToPool;
    protected volatile boolean shutdown;
    protected final Queue waitingThreads;

}
