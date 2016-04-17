// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.concurrent;


// Referenced classes of package org.apache.commons.lang3.concurrent:
//            ConcurrentUtils

public class ConcurrentException extends Exception
{

    protected ConcurrentException()
    {
    }

    public ConcurrentException(String s, Throwable throwable)
    {
        super(s, ConcurrentUtils.checkedException(throwable));
    }

    public ConcurrentException(Throwable throwable)
    {
        super(ConcurrentUtils.checkedException(throwable));
    }

    private static final long serialVersionUID = 0x5be89589f59f3c52L;
}
