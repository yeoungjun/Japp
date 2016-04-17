// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.io.*;

// Referenced classes of package org.apache.http:
//            Header

public interface HttpEntity
{

    public abstract void consumeContent()
        throws IOException;

    public abstract InputStream getContent()
        throws IOException, IllegalStateException;

    public abstract Header getContentEncoding();

    public abstract long getContentLength();

    public abstract Header getContentType();

    public abstract boolean isChunked();

    public abstract boolean isRepeatable();

    public abstract boolean isStreaming();

    public abstract void writeTo(OutputStream outputstream)
        throws IOException;
}
