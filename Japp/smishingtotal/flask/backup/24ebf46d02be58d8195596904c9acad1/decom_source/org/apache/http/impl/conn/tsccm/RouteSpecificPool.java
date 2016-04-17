// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            BasicPoolEntry, WaitingThread

public class RouteSpecificPool
{

    public RouteSpecificPool(HttpRoute httproute, int i)
    {
        log = LogFactory.getLog(getClass());
        route = httproute;
        maxEntries = i;
        connPerRoute = new ConnPerRoute() {

            public int getMaxForRoute(HttpRoute httproute1)
            {
                return maxEntries;
            }

            final RouteSpecificPool this$0;

            
            {
                this$0 = RouteSpecificPool.this;
                super();
            }
        };
        freeEntries = new LinkedList();
        waitingThreads = new LinkedList();
        numEntries = 0;
    }

    public RouteSpecificPool(HttpRoute httproute, ConnPerRoute connperroute)
    {
        log = LogFactory.getLog(getClass());
        route = httproute;
        connPerRoute = connperroute;
        maxEntries = connperroute.getMaxForRoute(httproute);
        freeEntries = new LinkedList();
        waitingThreads = new LinkedList();
        numEntries = 0;
    }

    public BasicPoolEntry allocEntry(Object obj)
    {
label0:
        {
            if(freeEntries.isEmpty())
                break label0;
            ListIterator listiterator = freeEntries.listIterator(freeEntries.size());
            BasicPoolEntry basicpoolentry1;
            do
            {
                if(!listiterator.hasPrevious())
                    break label0;
                basicpoolentry1 = (BasicPoolEntry)listiterator.previous();
            } while(basicpoolentry1.getState() != null && !LangUtils.equals(obj, basicpoolentry1.getState()));
            listiterator.remove();
            return basicpoolentry1;
        }
        if(getCapacity() == 0 && !freeEntries.isEmpty())
        {
            BasicPoolEntry basicpoolentry = (BasicPoolEntry)freeEntries.remove();
            basicpoolentry.shutdownEntry();
            OperatedClientConnection operatedclientconnection = basicpoolentry.getConnection();
            try
            {
                operatedclientconnection.close();
            }
            catch(IOException ioexception)
            {
                log.debug("I/O error closing connection", ioexception);
                return basicpoolentry;
            }
            return basicpoolentry;
        } else
        {
            return null;
        }
    }

    public void createdEntry(BasicPoolEntry basicpoolentry)
    {
        Args.check(route.equals(basicpoolentry.getPlannedRoute()), "Entry not planned for this pool");
        numEntries = 1 + numEntries;
    }

    public boolean deleteEntry(BasicPoolEntry basicpoolentry)
    {
        boolean flag = freeEntries.remove(basicpoolentry);
        if(flag)
            numEntries = -1 + numEntries;
        return flag;
    }

    public void dropEntry()
    {
        boolean flag;
        if(numEntries > 0)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "There is no entry that could be dropped");
        numEntries = -1 + numEntries;
    }

    public void freeEntry(BasicPoolEntry basicpoolentry)
    {
        if(numEntries < 1)
            throw new IllegalStateException((new StringBuilder()).append("No entry created for this pool. ").append(route).toString());
        if(numEntries <= freeEntries.size())
        {
            throw new IllegalStateException((new StringBuilder()).append("No entry allocated from this pool. ").append(route).toString());
        } else
        {
            freeEntries.add(basicpoolentry);
            return;
        }
    }

    public int getCapacity()
    {
        return connPerRoute.getMaxForRoute(route) - numEntries;
    }

    public final int getEntryCount()
    {
        return numEntries;
    }

    public final int getMaxEntries()
    {
        return maxEntries;
    }

    public final HttpRoute getRoute()
    {
        return route;
    }

    public boolean hasThread()
    {
        return !waitingThreads.isEmpty();
    }

    public boolean isUnused()
    {
        return numEntries < 1 && waitingThreads.isEmpty();
    }

    public WaitingThread nextThread()
    {
        return (WaitingThread)waitingThreads.peek();
    }

    public void queueThread(WaitingThread waitingthread)
    {
        Args.notNull(waitingthread, "Waiting thread");
        waitingThreads.add(waitingthread);
    }

    public void removeThread(WaitingThread waitingthread)
    {
        if(waitingthread == null)
        {
            return;
        } else
        {
            waitingThreads.remove(waitingthread);
            return;
        }
    }

    protected final ConnPerRoute connPerRoute;
    protected final LinkedList freeEntries;
    private final Log log;
    protected final int maxEntries;
    protected int numEntries;
    protected final HttpRoute route;
    protected final Queue waitingThreads;
}
