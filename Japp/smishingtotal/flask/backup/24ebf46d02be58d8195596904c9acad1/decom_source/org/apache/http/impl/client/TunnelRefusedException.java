// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;

public class TunnelRefusedException extends HttpException
{

    public TunnelRefusedException(String s, HttpResponse httpresponse)
    {
        super(s);
        response = httpresponse;
    }

    public HttpResponse getResponse()
    {
        return response;
    }

    private static final long serialVersionUID = 0x8800ab6306e44455L;
    private final HttpResponse response;
}
