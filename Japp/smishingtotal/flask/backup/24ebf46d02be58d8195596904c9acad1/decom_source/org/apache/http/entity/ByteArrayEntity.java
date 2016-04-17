// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity;

import java.io.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.entity:
//            AbstractHttpEntity, ContentType

public class ByteArrayEntity extends AbstractHttpEntity
    implements Cloneable
{

    public ByteArrayEntity(byte abyte0[])
    {
        this(abyte0, null);
    }

    public ByteArrayEntity(byte abyte0[], int i, int j)
    {
        this(abyte0, i, j, null);
    }

    public ByteArrayEntity(byte abyte0[], int i, int j, ContentType contenttype)
    {
        Args.notNull(abyte0, "Source byte array");
        if(i < 0 || i > abyte0.length || j < 0 || i + j < 0 || i + j > abyte0.length)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("off: ").append(i).append(" len: ").append(j).append(" b.length: ").append(abyte0.length).toString());
        content = abyte0;
        b = abyte0;
        off = i;
        len = j;
        if(contenttype != null)
            setContentType(contenttype.toString());
    }

    public ByteArrayEntity(byte abyte0[], ContentType contenttype)
    {
        Args.notNull(abyte0, "Source byte array");
        content = abyte0;
        b = abyte0;
        off = 0;
        len = b.length;
        if(contenttype != null)
            setContentType(contenttype.toString());
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public InputStream getContent()
    {
        return new ByteArrayInputStream(b, off, len);
    }

    public long getContentLength()
    {
        return (long)len;
    }

    public boolean isRepeatable()
    {
        return true;
    }

    public boolean isStreaming()
    {
        return false;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        Args.notNull(outputstream, "Output stream");
        outputstream.write(b, off, len);
        outputstream.flush();
    }

    private final byte b[];
    protected final byte content[];
    private final int len;
    private final int off;
}
