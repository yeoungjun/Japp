// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.entity;

import org.apache.http.*;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.util.Args;

public class LaxContentLengthStrategy
    implements ContentLengthStrategy
{

    public LaxContentLengthStrategy()
    {
        this(-1);
    }

    public LaxContentLengthStrategy(int i)
    {
        implicitLen = i;
    }

    public long determineLength(HttpMessage httpmessage)
        throws HttpException
    {
        Header header;
        Args.notNull(httpmessage, "HTTP message");
        header = httpmessage.getFirstHeader("Transfer-Encoding");
        if(header == null) goto _L2; else goto _L1
_L1:
        long l;
        HeaderElement aheaderelement[];
        int j;
        try
        {
            aheaderelement = header.getElements();
        }
        catch(ParseException parseexception)
        {
            throw new ProtocolException((new StringBuilder()).append("Invalid Transfer-Encoding header value: ").append(header).toString(), parseexception);
        }
        j = aheaderelement.length;
        if(!"identity".equalsIgnoreCase(header.getValue())) goto _L4; else goto _L3
_L3:
        l = -1L;
_L6:
        return l;
_L4:
        return j <= 0 || !"chunked".equalsIgnoreCase(aheaderelement[j - 1].getName()) ? -1L : -2L;
_L2:
        Header aheader[];
        int i;
        if(httpmessage.getFirstHeader("Content-Length") == null)
            break MISSING_BLOCK_LABEL_199;
        l = -1L;
        aheader = httpmessage.getHeaders("Content-Length");
        i = -1 + aheader.length;
_L7:
        Header header1;
        if(i < 0)
            continue; /* Loop/switch isn't completed */
        header1 = aheader[i];
        long l1 = Long.parseLong(header1.getValue());
        l = l1;
        if(l >= 0L) goto _L6; else goto _L5
_L5:
        return -1L;
        NumberFormatException numberformatexception;
        numberformatexception;
        i--;
          goto _L7
        return (long)implicitLen;
    }

    public static final LaxContentLengthStrategy INSTANCE = new LaxContentLengthStrategy();
    private final int implicitLen;

}
