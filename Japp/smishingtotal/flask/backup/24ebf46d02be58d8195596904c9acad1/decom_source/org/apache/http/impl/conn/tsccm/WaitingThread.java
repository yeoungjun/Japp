// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            RouteSpecificPool

public class WaitingThread
{

    public WaitingThread(Condition condition, RouteSpecificPool routespecificpool)
    {
        Args.notNull(condition, "Condition");
        cond = condition;
        pool = routespecificpool;
    }

    public boolean await(Date date)
        throws InterruptedException
    {
        if(waiter != null)
            throw new IllegalStateException((new StringBuilder()).append("A thread is already waiting on this object.\ncaller: ").append(Thread.currentThread()).append("\nwaiter: ").append(waiter).toString());
        if(aborted)
            throw new InterruptedException("Operation interrupted");
        waiter = Thread.currentThread();
        if(date == null) goto _L2; else goto _L1
_L1:
        boolean flag = cond.awaitUntil(date);
_L3:
        if(aborted)
            throw new InterruptedException("Operation interrupted");
        break MISSING_BLOCK_LABEL_126;
        Exception exception;
        exception;
        waiter = null;
        throw exception;
_L2:
        cond.await();
        flag = true;
          goto _L3
        waiter = null;
        return flag;
    }

    public final Condition getCondition()
    {
        return cond;
    }

    public final RouteSpecificPool getPool()
    {
        return pool;
    }

    public final Thread getThread()
    {
        return waiter;
    }

    public void interrupt()
    {
        aborted = true;
        cond.signalAll();
    }

    public void wakeup()
    {
        if(waiter == null)
        {
            throw new IllegalStateException("Nobody waiting on this object.");
        } else
        {
            cond.signalAll();
            return;
        }
    }

    private boolean aborted;
    private final Condition cond;
    private final RouteSpecificPool pool;
    private Thread waiter;
}
