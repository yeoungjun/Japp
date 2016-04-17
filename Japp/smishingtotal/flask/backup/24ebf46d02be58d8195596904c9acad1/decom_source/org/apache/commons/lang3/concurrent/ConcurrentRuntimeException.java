// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.concurrent;


// Referenced classes of package org.apache.commons.lang3.concurrent:
//            ConcurrentUtils

public class ConcurrentRuntimeException extends RuntimeException
{

    protected ConcurrentRuntimeException()
    {
    }

    public ConcurrentRuntimeException(String s, Throwable throwable)
    {
        super(s, ConcurrentUtils.checkedException(throwable));
    }

    public ConcurrentRuntimeException(Throwable throwable)
    {
        super(ConcurrentUtils.checkedException(throwable));
    }

    private static final long serialVersionUID = 0xa4a763ad0810310aL;
}
