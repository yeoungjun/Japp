// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;


// Referenced classes of package org.apache.http.client:
//            RedirectException

public class CircularRedirectException extends RedirectException
{

    public CircularRedirectException()
    {
    }

    public CircularRedirectException(String s)
    {
        super(s);
    }

    public CircularRedirectException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0x5ec9428de010dedbL;
}
