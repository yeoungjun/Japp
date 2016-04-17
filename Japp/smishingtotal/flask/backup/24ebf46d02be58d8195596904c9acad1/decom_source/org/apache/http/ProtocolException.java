// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;


// Referenced classes of package org.apache.http:
//            HttpException

public class ProtocolException extends HttpException
{

    public ProtocolException()
    {
    }

    public ProtocolException(String s)
    {
        super(s);
    }

    public ProtocolException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    private static final long serialVersionUID = 0xe24081770b16ae3eL;
}
