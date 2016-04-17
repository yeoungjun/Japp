// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import org.apache.http.ProtocolException;

public class MalformedChallengeException extends ProtocolException
{

    public MalformedChallengeException()
    {
    }

    public MalformedChallengeException(String s)
    {
        super(s);
    }

    public MalformedChallengeException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0xb4dfe7ec18440fcL;
}
