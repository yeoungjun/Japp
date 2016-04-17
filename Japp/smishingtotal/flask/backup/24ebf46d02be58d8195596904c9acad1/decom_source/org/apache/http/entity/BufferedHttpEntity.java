// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity;

import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.entity:
//            HttpEntityWrapper

public class BufferedHttpEntity extends HttpEntityWrapper
{

    public BufferedHttpEntity(HttpEntity httpentity)
        throws IOException
    {
        super(httpentity);
        if(!httpentity.isRepeatable() || httpentity.getContentLength() < 0L)
        {
            buffer = EntityUtils.toByteArray(httpentity);
            return;
        } else
        {
            buffer = null;
            return;
        }
    }

    public InputStream getContent()
        throws IOException
    {
        if(buffer != null)
            return new ByteArrayInputStream(buffer);
        else
            return super.getContent();
    }

    public long getContentLength()
    {
        if(buffer != null)
            return (long)buffer.length;
        else
            return super.getContentLength();
    }

    public boolean isChunked()
    {
        return buffer == null && super.isChunked();
    }

    public boolean isRepeatable()
    {
        return true;
    }

    public boolean isStreaming()
    {
        return buffer == null && super.isStreaming();
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        Args.notNull(outputstream, "Output stream");
        if(buffer != null)
        {
            outputstream.write(buffer);
            return;
        } else
        {
            super.writeTo(outputstream);
            return;
        }
    }

    private final byte buffer[];
}
