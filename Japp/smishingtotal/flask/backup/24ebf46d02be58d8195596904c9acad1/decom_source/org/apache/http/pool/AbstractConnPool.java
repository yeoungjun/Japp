// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.pool;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.pool:
//            ConnPool, ConnPoolControl, ConnFactory, PoolEntry, 
//            PoolEntryCallback, RouteSpecificPool, PoolEntryFuture, PoolStats

public abstract class AbstractConnPool
    implements ConnPool, ConnPoolControl
{

    public AbstractConnPool(ConnFactory connfactory, int i, int j)
    {
        connFactory = (ConnFactory)Args.notNull(connfactory, "Connection factory");
        defaultMaxPerRoute = Args.notNegative(i, "Max per route value");
        maxTotal = Args.notNegative(j, "Max total value");
    }

    private void enumEntries(Iterator iterator, PoolEntryCallback poolentrycallback)
    {
        do
        {
            if(!iterator.hasNext())
                break;
            PoolEntry poolentry = (PoolEntry)iterator.next();
            poolentrycallback.process(poolentry);
            if(poolentry.isClosed())
            {
                getPool(poolentry.getRoute()).remove(poolentry);
                iterator.remove();
            }
        } while(true);
    }

    private int getMax(Object obj)
    {
        Integer integer = (Integer)maxPerRoute.get(obj);
        if(integer != null)
            return integer.intValue();
        else
            return defaultMaxPerRoute;
    }

    private RouteSpecificPool getPool(final Object final_obj)
    {
        Object obj = (RouteSpecificPool)routeToPool.get(final_obj);
        if(obj == null)
        {
            obj = new RouteSpecificPool(final_obj) {

                protected PoolEntry createEntry(Object obj1)
                {
                    return AbstractConnPool.this.createEntry(route, obj1);
                }

                final AbstractConnPool this$0;
                final Object val$route;

            
            {
                this$0 = AbstractConnPool.this;
                route = obj1;
                super(final_obj);
            }
            };
            routeToPool.put(final_obj, obj);
        }
        return ((RouteSpecificPool) (obj));
    }

    private PoolEntry getPoolEntryBlocking(Object obj, Object obj1, long l, TimeUnit timeunit, PoolEntryFuture poolentryfuture)
        throws IOException, InterruptedException, TimeoutException
    {
        Date date;
        int i = l != 0L;
        date = null;
        if(i > 0)
            date = new Date(System.currentTimeMillis() + timeunit.toMillis(l));
        lock.lock();
        RouteSpecificPool routespecificpool = getPool(obj);
        PoolEntry poolentry = null;
_L12:
        if(poolentry != null)
            break; /* Loop/switch isn't completed */
        boolean flag;
        if(!isShutDown)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection pool shut down");
_L4:
        poolentry = routespecificpool.getFree(obj1);
        if(poolentry != null) goto _L2; else goto _L1
_L1:
        if(poolentry == null)
            break MISSING_BLOCK_LABEL_190;
        available.remove(poolentry);
        leased.add(poolentry);
        lock.unlock();
        return poolentry;
_L2:
        if(!poolentry.isClosed() && !poolentry.isExpired(System.currentTimeMillis())) goto _L1; else goto _L3
_L3:
        poolentry.close();
        available.remove(poolentry);
        routespecificpool.free(poolentry, false);
          goto _L4
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        int j;
        int k;
        j = getMax(obj);
        k = Math.max(0, (1 + routespecificpool.getAllocatedCount()) - j);
        if(k <= 0) goto _L6; else goto _L5
_L5:
        int k1 = 0;
_L10:
        if(k1 >= k) goto _L6; else goto _L7
_L7:
        PoolEntry poolentry3 = routespecificpool.getLastUsed();
        if(poolentry3 != null) goto _L8; else goto _L6
_L6:
        int j1;
        if(routespecificpool.getAllocatedCount() >= j)
            break; /* Loop/switch isn't completed */
        int i1 = leased.size();
        j1 = Math.max(maxTotal - i1, 0);
        if(j1 <= 0)
            break; /* Loop/switch isn't completed */
        PoolEntry poolentry1;
        if(available.size() > j1 - 1 && !available.isEmpty())
        {
            PoolEntry poolentry2 = (PoolEntry)available.removeLast();
            poolentry2.close();
            getPool(poolentry2.getRoute()).remove(poolentry2);
        }
        poolentry1 = routespecificpool.add(connFactory.create(obj));
        leased.add(poolentry1);
        lock.unlock();
        return poolentry1;
_L8:
        poolentry3.close();
        available.remove(poolentry3);
        routespecificpool.remove(poolentry3);
        k1++;
        if(true) goto _L10; else goto _L9
_L9:
        boolean flag1;
        routespecificpool.queue(poolentryfuture);
        pending.add(poolentryfuture);
        flag1 = poolentryfuture.await(date);
        routespecificpool.unqueue(poolentryfuture);
        pending.remove(poolentryfuture);
        if(flag1 || date == null) goto _L12; else goto _L11
_L11:
        if(date.getTime() > System.currentTimeMillis()) goto _L12; else goto _L13
_L13:
        throw new TimeoutException("Timeout waiting for connection");
        Exception exception1;
        exception1;
        routespecificpool.unqueue(poolentryfuture);
        pending.remove(poolentryfuture);
        throw exception1;
    }

    private void notifyPending(RouteSpecificPool routespecificpool)
    {
        PoolEntryFuture poolentryfuture = routespecificpool.nextPending();
        if(poolentryfuture != null)
            pending.remove(poolentryfuture);
        else
            poolentryfuture = (PoolEntryFuture)pending.poll();
        if(poolentryfuture != null)
            poolentryfuture.wakeup();
    }

    public void closeExpired()
    {
        enumAvailable(new PoolEntryCallback() {

            public void process(PoolEntry poolentry)
            {
                if(poolentry.isExpired(now))
                    poolentry.close();
            }

            final AbstractConnPool this$0;
            final long val$now;

            
            {
                this$0 = AbstractConnPool.this;
                now = l;
                super();
            }
        });
    }

    public void closeIdle(long l, TimeUnit timeunit)
    {
        Args.notNull(timeunit, "Time unit");
        long l1 = timeunit.toMillis(l);
        if(l1 < 0L)
            l1 = 0L;
        enumAvailable(new PoolEntryCallback() {

            public void process(PoolEntry poolentry)
            {
                if(poolentry.getUpdated() <= deadline)
                    poolentry.close();
            }

            final AbstractConnPool this$0;
            final long val$deadline;

            
            {
                this$0 = AbstractConnPool.this;
                deadline = l;
                super();
            }
        });
    }

    protected abstract PoolEntry createEntry(Object obj, Object obj1);

    protected void enumAvailable(PoolEntryCallback poolentrycallback)
    {
        lock.lock();
        enumEntries(available.iterator(), poolentrycallback);
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    protected void enumLeased(PoolEntryCallback poolentrycallback)
    {
        lock.lock();
        enumEntries(leased.iterator(), poolentrycallback);
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public int getDefaultMaxPerRoute()
    {
        lock.lock();
        int i = defaultMaxPerRoute;
        lock.unlock();
        return i;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public int getMaxPerRoute(Object obj)
    {
        Args.notNull(obj, "Route");
        lock.lock();
        int i = getMax(obj);
        lock.unlock();
        return i;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public int getMaxTotal()
    {
        lock.lock();
        int i = maxTotal;
        lock.unlock();
        return i;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public PoolStats getStats(Object obj)
    {
        Args.notNull(obj, "Route");
        lock.lock();
        PoolStats poolstats;
        RouteSpecificPool routespecificpool = getPool(obj);
        poolstats = new PoolStats(routespecificpool.getLeasedCount(), routespecificpool.getPendingCount(), routespecificpool.getAvailableCount(), getMax(obj));
        lock.unlock();
        return poolstats;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public PoolStats getTotalStats()
    {
        lock.lock();
        PoolStats poolstats = new PoolStats(leased.size(), pending.size(), available.size(), maxTotal);
        lock.unlock();
        return poolstats;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public boolean isShutdown()
    {
        return isShutDown;
    }

    public Future lease(Object obj, Object obj1)
    {
        return lease(obj, obj1, null);
    }

    public Future lease(Object obj, Object obj1, FutureCallback futurecallback)
    {
        Args.notNull(obj, "Route");
        boolean flag;
        if(!isShutDown)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection pool shut down");
        return new PoolEntryFuture(obj, obj1) {

            public volatile Object getPoolEntry(long l, TimeUnit timeunit)
                throws IOException, InterruptedException, TimeoutException
            {
                return getPoolEntry(l, timeunit);
            }

            public PoolEntry getPoolEntry(long l, TimeUnit timeunit)
                throws InterruptedException, TimeoutException, IOException
            {
                PoolEntry poolentry = getPoolEntryBlocking(route, state, l, timeunit, this);
                onLease(poolentry);
                return poolentry;
            }

            final AbstractConnPool this$0;
            final Object val$route;
            final Object val$state;

            
            {
                this$0 = AbstractConnPool.this;
                route = obj;
                state = obj1;
                super(final_lock1, final_futurecallback);
            }
        };
    }

    protected void onLease(PoolEntry poolentry)
    {
    }

    protected void onRelease(PoolEntry poolentry)
    {
    }

    public volatile void release(Object obj, boolean flag)
    {
        release((PoolEntry)obj, flag);
    }

    public void release(PoolEntry poolentry, boolean flag)
    {
        lock.lock();
        if(!leased.remove(poolentry)) goto _L2; else goto _L1
_L1:
        RouteSpecificPool routespecificpool;
        routespecificpool = getPool(poolentry.getRoute());
        routespecificpool.free(poolentry, flag);
        if(!flag)
            break MISSING_BLOCK_LABEL_79;
        if(isShutDown)
            break MISSING_BLOCK_LABEL_79;
        available.addFirst(poolentry);
        onRelease(poolentry);
_L3:
        notifyPending(routespecificpool);
_L2:
        lock.unlock();
        return;
        poolentry.close();
          goto _L3
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public void setDefaultMaxPerRoute(int i)
    {
        Args.notNegative(i, "Max per route value");
        lock.lock();
        defaultMaxPerRoute = i;
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public void setMaxPerRoute(Object obj, int i)
    {
        Args.notNull(obj, "Route");
        Args.notNegative(i, "Max per route value");
        lock.lock();
        maxPerRoute.put(obj, Integer.valueOf(i));
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public void setMaxTotal(int i)
    {
        Args.notNegative(i, "Max value");
        lock.lock();
        maxTotal = i;
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public void shutdown()
        throws IOException
    {
        if(isShutDown)
            return;
        isShutDown = true;
        lock.lock();
        for(Iterator iterator = available.iterator(); iterator.hasNext(); ((PoolEntry)iterator.next()).close());
        break MISSING_BLOCK_LABEL_66;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        for(Iterator iterator1 = leased.iterator(); iterator1.hasNext(); ((PoolEntry)iterator1.next()).close());
        for(Iterator iterator2 = routeToPool.values().iterator(); iterator2.hasNext(); ((RouteSpecificPool)iterator2.next()).shutdown());
        routeToPool.clear();
        leased.clear();
        available.clear();
        lock.unlock();
        return;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[leased: ");
        stringbuilder.append(leased);
        stringbuilder.append("][available: ");
        stringbuilder.append(available);
        stringbuilder.append("][pending: ");
        stringbuilder.append(pending);
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    private final LinkedList available = new LinkedList();
    private final ConnFactory connFactory;
    private volatile int defaultMaxPerRoute;
    private volatile boolean isShutDown;
    private final Set leased = new HashSet();
    private final Lock lock = new ReentrantLock();
    private final Map maxPerRoute = new HashMap();
    private volatile int maxTotal;
    private final LinkedList pending = new LinkedList();
    private final Map routeToPool = new HashMap();

}
