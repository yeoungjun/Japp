// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.InterruptedIOException;

public class RequestAbortedException extends InterruptedIOException
{

    public RequestAbortedException(String s)
    {
        super(s);
    }

    public RequestAbortedException(String s, Throwable throwable)
    {
        super(s);
        if(throwable != null)
            initCause(throwable);
    }

    private static final long serialVersionUID = 0x4506aa3106436180L;
}
