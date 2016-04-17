// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;


// Referenced classes of package org.apache.http.client:
//            ClientProtocolException

public class HttpResponseException extends ClientProtocolException
{

    public HttpResponseException(int i, String s)
    {
        super(s);
        statusCode = i;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    private static final long serialVersionUID = 0x9c43f7f02a6bd533L;
    private final int statusCode;
}
