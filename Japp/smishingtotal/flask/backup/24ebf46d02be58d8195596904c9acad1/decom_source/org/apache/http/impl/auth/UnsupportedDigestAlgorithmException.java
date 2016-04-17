// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;


public class UnsupportedDigestAlgorithmException extends RuntimeException
{

    public UnsupportedDigestAlgorithmException()
    {
    }

    public UnsupportedDigestAlgorithmException(String s)
    {
        super(s);
    }

    public UnsupportedDigestAlgorithmException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0x46f4ccb3aea9246L;
}
