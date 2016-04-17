// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;

import org.apache.http.ProtocolException;

public class NonRepeatableRequestException extends ProtocolException
{

    public NonRepeatableRequestException()
    {
    }

    public NonRepeatableRequestException(String s)
    {
        super(s);
    }

    public NonRepeatableRequestException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0x125c1cf89b2bea0L;
}
