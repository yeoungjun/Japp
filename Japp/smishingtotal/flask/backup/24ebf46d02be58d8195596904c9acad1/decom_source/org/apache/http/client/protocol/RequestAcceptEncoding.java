// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;

public class RequestAcceptEncoding
    implements HttpRequestInterceptor
{

    public RequestAcceptEncoding()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        if(!httprequest.containsHeader("Accept-Encoding"))
            httprequest.addHeader("Accept-Encoding", "gzip,deflate");
    }
}
