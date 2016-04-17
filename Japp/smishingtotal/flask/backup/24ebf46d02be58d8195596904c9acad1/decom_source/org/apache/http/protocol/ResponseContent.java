// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpContext

public class ResponseContent
    implements HttpResponseInterceptor
{

    public ResponseContent()
    {
        this(false);
    }

    public ResponseContent(boolean flag)
    {
        overwrite = flag;
    }

    public void process(HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpEntity httpentity;
        Args.notNull(httpresponse, "HTTP response");
        ProtocolVersion protocolversion;
        if(overwrite)
        {
            httpresponse.removeHeaders("Transfer-Encoding");
            httpresponse.removeHeaders("Content-Length");
        } else
        {
            if(httpresponse.containsHeader("Transfer-Encoding"))
                throw new ProtocolException("Transfer-encoding header already present");
            if(httpresponse.containsHeader("Content-Length"))
                throw new ProtocolException("Content-Length header already present");
        }
        protocolversion = httpresponse.getStatusLine().getProtocolVersion();
        httpentity = httpresponse.getEntity();
        if(httpentity == null) goto _L2; else goto _L1
_L1:
        long l = httpentity.getContentLength();
        if(httpentity.isChunked() && !protocolversion.lessEquals(HttpVersion.HTTP_1_0))
            httpresponse.addHeader("Transfer-Encoding", "chunked");
        else
        if(l >= 0L)
            httpresponse.addHeader("Content-Length", Long.toString(httpentity.getContentLength()));
_L6:
        if(httpentity.getContentType() != null && !httpresponse.containsHeader("Content-Type"))
            httpresponse.addHeader(httpentity.getContentType());
        if(httpentity.getContentEncoding() != null && !httpresponse.containsHeader("Content-Encoding"))
            httpresponse.addHeader(httpentity.getContentEncoding());
_L4:
        return;
_L2:
        int i = httpresponse.getStatusLine().getStatusCode();
        if(i == 204 || i == 304 || i == 205) goto _L4; else goto _L3
_L3:
        httpresponse.addHeader("Content-Length", "0");
        return;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private final boolean overwrite;
}
