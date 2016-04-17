// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.pool;

import java.util.*;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.pool:
//            PoolEntry, PoolEntryFuture

abstract class RouteSpecificPool
{

    RouteSpecificPool(Object obj)
    {
        route = obj;
    }

    public PoolEntry add(Object obj)
    {
        PoolEntry poolentry = createEntry(obj);
        leased.add(poolentry);
        return poolentry;
    }

    protected abstract PoolEntry createEntry(Object obj);

    public void free(PoolEntry poolentry, boolean flag)
    {
        Args.notNull(poolentry, "Pool entry");
        Asserts.check(leased.remove(poolentry), "Entry %s has not been leased from this pool", new Object[] {
            poolentry
        });
        if(flag)
            available.addFirst(poolentry);
    }

    public int getAllocatedCount()
    {
        return available.size() + leased.size();
    }

    public int getAvailableCount()
    {
        return available.size();
    }

    public PoolEntry getFree(Object obj)
    {
label0:
        {
label1:
            {
                if(available.isEmpty())
                    break label0;
                if(obj == null)
                    break label1;
                Iterator iterator1 = available.iterator();
                PoolEntry poolentry1;
                do
                {
                    if(!iterator1.hasNext())
                        break label1;
                    poolentry1 = (PoolEntry)iterator1.next();
                } while(!obj.equals(poolentry1.getState()));
                iterator1.remove();
                leased.add(poolentry1);
                return poolentry1;
            }
            Iterator iterator = available.iterator();
            PoolEntry poolentry;
            do
            {
                if(!iterator.hasNext())
                    break label0;
                poolentry = (PoolEntry)iterator.next();
            } while(poolentry.getState() != null);
            iterator.remove();
            leased.add(poolentry);
            return poolentry;
        }
        return null;
    }

    public PoolEntry getLastUsed()
    {
        if(!available.isEmpty())
            return (PoolEntry)available.getLast();
        else
            return null;
    }

    public int getLeasedCount()
    {
        return leased.size();
    }

    public int getPendingCount()
    {
        return pending.size();
    }

    public final Object getRoute()
    {
        return route;
    }

    public PoolEntryFuture nextPending()
    {
        return (PoolEntryFuture)pending.poll();
    }

    public void queue(PoolEntryFuture poolentryfuture)
    {
        if(poolentryfuture == null)
        {
            return;
        } else
        {
            pending.add(poolentryfuture);
            return;
        }
    }

    public boolean remove(PoolEntry poolentry)
    {
        Args.notNull(poolentry, "Pool entry");
        return available.remove(poolentry) || leased.remove(poolentry);
    }

    public void shutdown()
    {
        for(Iterator iterator = pending.iterator(); iterator.hasNext(); ((PoolEntryFuture)iterator.next()).cancel(true));
        pending.clear();
        for(Iterator iterator1 = available.iterator(); iterator1.hasNext(); ((PoolEntry)iterator1.next()).close());
        available.clear();
        for(Iterator iterator2 = leased.iterator(); iterator2.hasNext(); ((PoolEntry)iterator2.next()).close());
        leased.clear();
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[route: ");
        stringbuilder.append(route);
        stringbuilder.append("][leased: ");
        stringbuilder.append(leased.size());
        stringbuilder.append("][available: ");
        stringbuilder.append(available.size());
        stringbuilder.append("][pending: ");
        stringbuilder.append(pending.size());
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    public void unqueue(PoolEntryFuture poolentryfuture)
    {
        if(poolentryfuture == null)
        {
            return;
        } else
        {
            pending.remove(poolentryfuture);
            return;
        }
    }

    private final LinkedList available = new LinkedList();
    private final Set leased = new HashSet();
    private final LinkedList pending = new LinkedList();
    private final Object route;
}
