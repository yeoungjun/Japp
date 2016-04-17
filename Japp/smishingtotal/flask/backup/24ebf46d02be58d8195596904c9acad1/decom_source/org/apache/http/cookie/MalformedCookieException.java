// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie;

import org.apache.http.ProtocolException;

public class MalformedCookieException extends ProtocolException
{

    public MalformedCookieException()
    {
    }

    public MalformedCookieException(String s)
    {
        super(s);
    }

    public MalformedCookieException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0xa314efed1ef72bf7L;
}
