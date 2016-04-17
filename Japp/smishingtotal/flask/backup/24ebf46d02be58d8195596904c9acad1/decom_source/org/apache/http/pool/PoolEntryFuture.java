// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.pool;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.Args;

abstract class PoolEntryFuture
    implements Future
{

    PoolEntryFuture(Lock lock1, FutureCallback futurecallback)
    {
        lock = lock1;
        condition = lock1.newCondition();
        callback = futurecallback;
    }

    public boolean await(Date date)
        throws InterruptedException
    {
        lock.lock();
        if(cancelled)
            throw new InterruptedException("Operation interrupted");
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
        if(date == null) goto _L2; else goto _L1
_L1:
        boolean flag = condition.awaitUntil(date);
_L3:
        if(cancelled)
            throw new InterruptedException("Operation interrupted");
        break MISSING_BLOCK_LABEL_84;
_L2:
        condition.await();
        flag = true;
          goto _L3
        lock.unlock();
        return flag;
    }

    public boolean cancel(boolean flag)
    {
        lock.lock();
        boolean flag1 = completed;
        if(flag1)
        {
            lock.unlock();
            return false;
        }
        completed = true;
        cancelled = true;
        if(callback != null)
            callback.cancelled();
        condition.signalAll();
        lock.unlock();
        return true;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    public Object get()
        throws InterruptedException, ExecutionException
    {
        Object obj;
        try
        {
            obj = get(0L, TimeUnit.MILLISECONDS);
        }
        catch(TimeoutException timeoutexception)
        {
            throw new ExecutionException(timeoutexception);
        }
        return obj;
    }

    public Object get(long l, TimeUnit timeunit)
        throws InterruptedException, ExecutionException, TimeoutException
    {
        Args.notNull(timeunit, "Time unit");
        lock.lock();
        Object obj1;
        if(!completed)
            break MISSING_BLOCK_LABEL_41;
        obj1 = result;
        lock.unlock();
        return obj1;
        Object obj;
        result = getPoolEntry(l, timeunit);
        completed = true;
        if(callback != null)
            callback.completed(result);
        obj = result;
        lock.unlock();
        return obj;
        IOException ioexception;
        ioexception;
        completed = true;
        result = null;
        if(callback != null)
            callback.failed(ioexception);
        throw new ExecutionException(ioexception);
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    protected abstract Object getPoolEntry(long l, TimeUnit timeunit)
        throws IOException, InterruptedException, TimeoutException;

    public boolean isCancelled()
    {
        return cancelled;
    }

    public boolean isDone()
    {
        return completed;
    }

    public void wakeup()
    {
        lock.lock();
        condition.signalAll();
        lock.unlock();
        return;
        Exception exception;
        exception;
        lock.unlock();
        throw exception;
    }

    private final FutureCallback callback;
    private volatile boolean cancelled;
    private volatile boolean completed;
    private final Condition condition;
    private final Lock lock;
    private Object result;
}
