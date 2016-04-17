// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.entity;

import java.io.*;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.Args;

public class GzipCompressingEntity extends HttpEntityWrapper
{

    public GzipCompressingEntity(HttpEntity httpentity)
    {
        super(httpentity);
    }

    public InputStream getContent()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public Header getContentEncoding()
    {
        return new BasicHeader("Content-Encoding", "gzip");
    }

    public long getContentLength()
    {
        return -1L;
    }

    public boolean isChunked()
    {
        return true;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        GZIPOutputStream gzipoutputstream;
        Args.notNull(outputstream, "Output stream");
        gzipoutputstream = new GZIPOutputStream(outputstream);
        wrappedEntity.writeTo(gzipoutputstream);
        gzipoutputstream.close();
        return;
        Exception exception;
        exception;
        gzipoutputstream.close();
        throw exception;
    }

    private static final String GZIP_CODEC = "gzip";
}
