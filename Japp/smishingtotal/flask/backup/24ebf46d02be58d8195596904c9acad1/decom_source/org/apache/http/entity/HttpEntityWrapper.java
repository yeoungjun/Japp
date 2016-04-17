// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity;

import java.io.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.Args;

public class HttpEntityWrapper
    implements HttpEntity
{

    public HttpEntityWrapper(HttpEntity httpentity)
    {
        wrappedEntity = (HttpEntity)Args.notNull(httpentity, "Wrapped entity");
    }

    public void consumeContent()
        throws IOException
    {
        wrappedEntity.consumeContent();
    }

    public InputStream getContent()
        throws IOException
    {
        return wrappedEntity.getContent();
    }

    public Header getContentEncoding()
    {
        return wrappedEntity.getContentEncoding();
    }

    public long getContentLength()
    {
        return wrappedEntity.getContentLength();
    }

    public Header getContentType()
    {
        return wrappedEntity.getContentType();
    }

    public boolean isChunked()
    {
        return wrappedEntity.isChunked();
    }

    public boolean isRepeatable()
    {
        return wrappedEntity.isRepeatable();
    }

    public boolean isStreaming()
    {
        return wrappedEntity.isStreaming();
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        wrappedEntity.writeTo(outputstream);
    }

    protected HttpEntity wrappedEntity;
}
