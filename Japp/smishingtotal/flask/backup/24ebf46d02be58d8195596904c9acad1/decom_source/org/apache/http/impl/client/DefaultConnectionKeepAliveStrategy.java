// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.*;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class DefaultConnectionKeepAliveStrategy
    implements ConnectionKeepAliveStrategy
{

    public DefaultConnectionKeepAliveStrategy()
    {
    }

    public long getKeepAliveDuration(HttpResponse httpresponse, HttpContext httpcontext)
    {
        BasicHeaderElementIterator basicheaderelementiterator;
        Args.notNull(httpresponse, "HTTP response");
        basicheaderelementiterator = new BasicHeaderElementIterator(httpresponse.headerIterator("Keep-Alive"));
_L2:
        String s1;
        if(!basicheaderelementiterator.hasNext())
            break MISSING_BLOCK_LABEL_90;
        HeaderElement headerelement = basicheaderelementiterator.nextElement();
        String s = headerelement.getName();
        s1 = headerelement.getValue();
        if(s1 == null || !s.equalsIgnoreCase("timeout"))
            continue; /* Loop/switch isn't completed */
        long l = Long.parseLong(s1);
        return l * 1000L;
        return -1L;
        NumberFormatException numberformatexception;
        numberformatexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static final DefaultConnectionKeepAliveStrategy INSTANCE = new DefaultConnectionKeepAliveStrategy();

}
