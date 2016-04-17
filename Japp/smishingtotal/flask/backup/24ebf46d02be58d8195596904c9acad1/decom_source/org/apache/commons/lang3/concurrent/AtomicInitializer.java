// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

// Referenced classes of package org.apache.commons.lang3.concurrent:
//            ConcurrentInitializer, ConcurrentException

public abstract class AtomicInitializer
    implements ConcurrentInitializer
{

    public AtomicInitializer()
    {
    }

    public Object get()
        throws ConcurrentException
    {
        Object obj = reference.get();
        if(obj == null)
        {
            obj = initialize();
            if(!reference.compareAndSet(null, obj))
                obj = reference.get();
        }
        return obj;
    }

    protected abstract Object initialize()
        throws ConcurrentException;

    private final AtomicReference reference = new AtomicReference();
}
