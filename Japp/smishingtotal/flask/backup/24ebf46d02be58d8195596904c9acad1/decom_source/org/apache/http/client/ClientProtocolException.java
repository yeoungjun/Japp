// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;

import java.io.IOException;

public class ClientProtocolException extends IOException
{

    public ClientProtocolException()
    {
    }

    public ClientProtocolException(String s)
    {
        super(s);
    }

    public ClientProtocolException(String s, Throwable throwable)
    {
        super(s);
        initCause(throwable);
    }

    public ClientProtocolException(Throwable throwable)
    {
        initCause(throwable);
    }

    private static final long serialVersionUID = 0xb254ea47b43e6ea7L;
}
