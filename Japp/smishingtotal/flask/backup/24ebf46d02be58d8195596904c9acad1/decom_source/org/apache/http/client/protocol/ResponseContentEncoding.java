// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.*;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.protocol.HttpContext;

public class ResponseContentEncoding
    implements HttpResponseInterceptor
{

    public ResponseContentEncoding()
    {
    }

    public void process(HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpEntity httpentity = httpresponse.getEntity();
        if(httpentity == null || httpentity.getContentLength() == 0L) goto _L2; else goto _L1
_L1:
        Header header = httpentity.getContentEncoding();
        if(header == null) goto _L2; else goto _L3
_L3:
        HeaderElement headerelement;
        String s;
        HeaderElement aheaderelement[] = header.getElements();
        int i = aheaderelement.length;
        boolean flag = false;
        if(i < 0)
        {
            headerelement = aheaderelement[0];
            s = headerelement.getName().toLowerCase(Locale.US);
            if("gzip".equals(s) || "x-gzip".equals(s))
            {
                httpresponse.setEntity(new GzipDecompressingEntity(httpresponse.getEntity()));
                flag = true;
            } else
            {
                if(!"deflate".equals(s))
                    continue;
                httpresponse.setEntity(new DeflateDecompressingEntity(httpresponse.getEntity()));
                flag = true;
            }
        }
_L5:
        if(flag)
        {
            httpresponse.removeHeaders("Content-Length");
            httpresponse.removeHeaders("Content-Encoding");
            httpresponse.removeHeaders("Content-MD5");
        }
_L2:
        do
            return;
        while("identity".equals(s));
        throw new HttpException((new StringBuilder()).append("Unsupported Content-Coding: ").append(headerelement.getName()).toString());
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static final String UNCOMPRESSED = "http.client.response.uncompressed";
}
