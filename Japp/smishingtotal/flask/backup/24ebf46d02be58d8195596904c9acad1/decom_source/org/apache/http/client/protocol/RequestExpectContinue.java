// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.protocol:
//            HttpClientContext

public class RequestExpectContinue
    implements HttpRequestInterceptor
{

    public RequestExpectContinue()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        if(!httprequest.containsHeader("Expect") && (httprequest instanceof HttpEntityEnclosingRequest))
        {
            ProtocolVersion protocolversion = httprequest.getRequestLine().getProtocolVersion();
            HttpEntity httpentity = ((HttpEntityEnclosingRequest)httprequest).getEntity();
            if(httpentity != null && httpentity.getContentLength() != 0L && !protocolversion.lessEquals(HttpVersion.HTTP_1_0) && HttpClientContext.adapt(httpcontext).getRequestConfig().isExpectContinueEnabled())
                httprequest.addHeader("Expect", "100-continue");
        }
    }
}
