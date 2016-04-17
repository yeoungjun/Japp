// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.IOException;

public class IOExceptionWithCause extends IOException
{

    public IOExceptionWithCause(String s, Throwable throwable)
    {
        super(s);
        initCause(throwable);
    }

    public IOExceptionWithCause(Throwable throwable)
    {
        String s;
        if(throwable == null)
            s = null;
        else
            s = throwable.toString();
        super(s);
        initCause(throwable);
    }

    private static final long serialVersionUID = 1L;
}
