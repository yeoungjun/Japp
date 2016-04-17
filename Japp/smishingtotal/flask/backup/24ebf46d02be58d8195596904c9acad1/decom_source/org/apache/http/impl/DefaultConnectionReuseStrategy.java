// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import org.apache.http.*;
import org.apache.http.message.BasicTokenIterator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class DefaultConnectionReuseStrategy
    implements ConnectionReuseStrategy
{

    public DefaultConnectionReuseStrategy()
    {
    }

    private boolean canResponseHaveBody(HttpResponse httpresponse)
    {
        int i = httpresponse.getStatusLine().getStatusCode();
        return i >= 200 && i != 204 && i != 304 && i != 205;
    }

    protected TokenIterator createTokenIterator(HeaderIterator headeriterator)
    {
        return new BasicTokenIterator(headeriterator);
    }

    public boolean keepAlive(HttpResponse httpresponse, HttpContext httpcontext)
    {
        ProtocolVersion protocolversion;
        Args.notNull(httpresponse, "HTTP response");
        Args.notNull(httpcontext, "HTTP context");
        protocolversion = httpresponse.getStatusLine().getProtocolVersion();
        Header header = httpresponse.getFirstHeader("Transfer-Encoding");
        if(header != null)
        {
            if(!"chunked".equalsIgnoreCase(header.getValue()))
                return false;
        } else
        if(canResponseHaveBody(httpresponse))
        {
            Header aheader[] = httpresponse.getHeaders("Content-Length");
            if(aheader.length == 1)
            {
                Header header1 = aheader[0];
                int i;
                try
                {
                    i = Integer.parseInt(header1.getValue());
                }
                catch(NumberFormatException numberformatexception)
                {
                    return false;
                }
                if(i < 0)
                    return false;
            } else
            {
                return false;
            }
        }
        HeaderIterator headeriterator = httpresponse.headerIterator("Connection");
        if(!headeriterator.hasNext())
            headeriterator = httpresponse.headerIterator("Proxy-Connection");
        if(!headeriterator.hasNext())
            break MISSING_BLOCK_LABEL_228;
        TokenIterator tokeniterator;
        boolean flag;
        String s;
        boolean flag1;
        try
        {
            tokeniterator = createTokenIterator(headeriterator);
        }
        catch(ParseException parseexception)
        {
            return false;
        }
        flag = false;
        if(!tokeniterator.hasNext())
            break; /* Loop/switch isn't completed */
        s = tokeniterator.nextToken();
        if("Close".equalsIgnoreCase(s))
            return false;
        flag1 = "Keep-Alive".equalsIgnoreCase(s);
        if(flag1)
            flag = true;
        if(true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_166;
_L1:
        if(flag)
            return true;
        return !protocolversion.lessEquals(HttpVersion.HTTP_1_0);
    }

    public static final DefaultConnectionReuseStrategy INSTANCE = new DefaultConnectionReuseStrategy();

}
