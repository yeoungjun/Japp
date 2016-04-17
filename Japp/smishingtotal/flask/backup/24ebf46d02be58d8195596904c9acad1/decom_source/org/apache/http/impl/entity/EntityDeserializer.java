// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.entity;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.*;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;

public class EntityDeserializer
{

    public EntityDeserializer(ContentLengthStrategy contentlengthstrategy)
    {
        lenStrategy = (ContentLengthStrategy)Args.notNull(contentlengthstrategy, "Content length strategy");
    }

    public HttpEntity deserialize(SessionInputBuffer sessioninputbuffer, HttpMessage httpmessage)
        throws HttpException, IOException
    {
        Args.notNull(sessioninputbuffer, "Session input buffer");
        Args.notNull(httpmessage, "HTTP message");
        return doDeserialize(sessioninputbuffer, httpmessage);
    }

    protected BasicHttpEntity doDeserialize(SessionInputBuffer sessioninputbuffer, HttpMessage httpmessage)
        throws HttpException, IOException
    {
        BasicHttpEntity basichttpentity = new BasicHttpEntity();
        long l = lenStrategy.determineLength(httpmessage);
        org.apache.http.Header header;
        org.apache.http.Header header1;
        if(l == -2L)
        {
            basichttpentity.setChunked(true);
            basichttpentity.setContentLength(-1L);
            basichttpentity.setContent(new ChunkedInputStream(sessioninputbuffer));
        } else
        if(l == -1L)
        {
            basichttpentity.setChunked(false);
            basichttpentity.setContentLength(-1L);
            basichttpentity.setContent(new IdentityInputStream(sessioninputbuffer));
        } else
        {
            basichttpentity.setChunked(false);
            basichttpentity.setContentLength(l);
            basichttpentity.setContent(new ContentLengthInputStream(sessioninputbuffer, l));
        }
        header = httpmessage.getFirstHeader("Content-Type");
        if(header != null)
            basichttpentity.setContentType(header);
        header1 = httpmessage.getFirstHeader("Content-Encoding");
        if(header1 != null)
            basichttpentity.setContentEncoding(header1);
        return basichttpentity;
    }

    private final ContentLengthStrategy lenStrategy;
}
