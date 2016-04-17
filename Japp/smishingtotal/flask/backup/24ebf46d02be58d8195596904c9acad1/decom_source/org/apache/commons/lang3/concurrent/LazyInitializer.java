// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.concurrent;


// Referenced classes of package org.apache.commons.lang3.concurrent:
//            ConcurrentInitializer, ConcurrentException

public abstract class LazyInitializer
    implements ConcurrentInitializer
{

    public LazyInitializer()
    {
    }

    public Object get()
        throws ConcurrentException
    {
        Object obj;
        obj = object;
        if(obj != null)
            break MISSING_BLOCK_LABEL_39;
        this;
        JVM INSTR monitorenter ;
        Object obj1 = object;
        if(obj1 != null)
            break MISSING_BLOCK_LABEL_30;
        obj1 = initialize();
        object = obj1;
        this;
        JVM INSTR monitorexit ;
        return obj1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        return obj;
    }

    protected abstract Object initialize()
        throws ConcurrentException;

    private volatile Object object;
}
