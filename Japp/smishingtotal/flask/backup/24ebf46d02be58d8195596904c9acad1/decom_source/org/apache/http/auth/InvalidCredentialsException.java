// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;


// Referenced classes of package org.apache.http.auth:
//            AuthenticationException

public class InvalidCredentialsException extends AuthenticationException
{

    public InvalidCredentialsException()
    {
    }

    public InvalidCredentialsException(String s)
    {
        super(s);
    }

    public InvalidCredentialsException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0xbcea2b1f9f1b46d8L;
}
