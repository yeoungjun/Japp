// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpContext

public class RequestContent
    implements HttpRequestInterceptor
{

    public RequestContent()
    {
        this(false);
    }

    public RequestContent(boolean flag)
    {
        overwrite = flag;
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        if(httprequest instanceof HttpEntityEnclosingRequest)
        {
            ProtocolVersion protocolversion;
            HttpEntity httpentity;
            if(overwrite)
            {
                httprequest.removeHeaders("Transfer-Encoding");
                httprequest.removeHeaders("Content-Length");
            } else
            {
                if(httprequest.containsHeader("Transfer-Encoding"))
                    throw new ProtocolException("Transfer-encoding header already present");
                if(httprequest.containsHeader("Content-Length"))
                    throw new ProtocolException("Content-Length header already present");
            }
            protocolversion = httprequest.getRequestLine().getProtocolVersion();
            httpentity = ((HttpEntityEnclosingRequest)httprequest).getEntity();
            if(httpentity == null)
            {
                httprequest.addHeader("Content-Length", "0");
            } else
            {
                if(httpentity.isChunked() || httpentity.getContentLength() < 0L)
                {
                    if(protocolversion.lessEquals(HttpVersion.HTTP_1_0))
                        throw new ProtocolException((new StringBuilder()).append("Chunked transfer encoding not allowed for ").append(protocolversion).toString());
                    httprequest.addHeader("Transfer-Encoding", "chunked");
                } else
                {
                    httprequest.addHeader("Content-Length", Long.toString(httpentity.getContentLength()));
                }
                if(httpentity.getContentType() != null && !httprequest.containsHeader("Content-Type"))
                    httprequest.addHeader(httpentity.getContentType());
                if(httpentity.getContentEncoding() != null && !httprequest.containsHeader("Content-Encoding"))
                {
                    httprequest.addHeader(httpentity.getContentEncoding());
                    return;
                }
            }
        }
    }

    private final boolean overwrite;
}
