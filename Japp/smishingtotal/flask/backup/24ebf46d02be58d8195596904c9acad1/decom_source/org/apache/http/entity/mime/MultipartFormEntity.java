// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime;

import java.io.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

// Referenced classes of package org.apache.http.entity.mime:
//            AbstractMultipartForm

class MultipartFormEntity
    implements HttpEntity
{

    MultipartFormEntity(AbstractMultipartForm abstractmultipartform, String s, long l)
    {
        multipart = abstractmultipartform;
        contentType = new BasicHeader("Content-Type", s);
        contentLength = l;
    }

    public void consumeContent()
        throws IOException, UnsupportedOperationException
    {
        if(isStreaming())
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        else
            return;
    }

    public InputStream getContent()
        throws IOException
    {
        throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
    }

    public Header getContentEncoding()
    {
        return null;
    }

    public long getContentLength()
    {
        return contentLength;
    }

    public Header getContentType()
    {
        return contentType;
    }

    AbstractMultipartForm getMultipart()
    {
        return multipart;
    }

    public boolean isChunked()
    {
        return !isRepeatable();
    }

    public boolean isRepeatable()
    {
        return contentLength != -1L;
    }

    public boolean isStreaming()
    {
        return !isRepeatable();
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        multipart.writeTo(outputstream);
    }

    private final long contentLength;
    private final Header contentType;
    private final AbstractMultipartForm multipart;
}
