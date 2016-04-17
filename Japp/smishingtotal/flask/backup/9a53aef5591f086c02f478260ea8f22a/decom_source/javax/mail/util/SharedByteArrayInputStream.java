// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.mail.internet.SharedInputStream;

public class SharedByteArrayInputStream extends ByteArrayInputStream
    implements SharedInputStream
{

    public SharedByteArrayInputStream(byte abyte0[])
    {
        super(abyte0);
        start = 0;
    }

    public SharedByteArrayInputStream(byte abyte0[], int i, int j)
    {
        super(abyte0, i, j);
        start = 0;
        start = i;
    }

    public long getPosition()
    {
        return (long)(pos - start);
    }

    public InputStream newStream(long l, long l1)
    {
        if(l < 0L)
            throw new IllegalArgumentException("start < 0");
        if(l1 == -1L)
            l1 = count - start;
        return new SharedByteArrayInputStream(buf, start + (int)l, (int)(l1 - l));
    }

    protected int start;
}
