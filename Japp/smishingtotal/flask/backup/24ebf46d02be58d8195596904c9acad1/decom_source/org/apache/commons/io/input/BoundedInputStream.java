// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class BoundedInputStream extends InputStream
{

    public BoundedInputStream(InputStream inputstream)
    {
        this(inputstream, -1L);
    }

    public BoundedInputStream(InputStream inputstream, long l)
    {
        pos = 0L;
        mark = -1L;
        propagateClose = true;
        max = l;
        in = inputstream;
    }

    public int available()
        throws IOException
    {
        if(max >= 0L && pos >= max)
            return 0;
        else
            return in.available();
    }

    public void close()
        throws IOException
    {
        if(propagateClose)
            in.close();
    }

    public boolean isPropagateClose()
    {
        return propagateClose;
    }

    public void mark(int i)
    {
        this;
        JVM INSTR monitorenter ;
        in.mark(i);
        mark = pos;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean markSupported()
    {
        return in.markSupported();
    }

    public int read()
        throws IOException
    {
        if(max >= 0L && pos == max)
        {
            return -1;
        } else
        {
            int i = in.read();
            pos = 1L + pos;
            return i;
        }
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        if(max >= 0L && pos >= max)
            return -1;
        long l;
        int k;
        if(max >= 0L)
            l = Math.min(j, max - pos);
        else
            l = j;
        k = in.read(abyte0, i, (int)l);
        if(k == -1)
        {
            return -1;
        } else
        {
            pos = pos + (long)k;
            return k;
        }
    }

    public void reset()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        in.reset();
        pos = mark;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setPropagateClose(boolean flag)
    {
        propagateClose = flag;
    }

    public long skip(long l)
        throws IOException
    {
        long l1;
        long l2;
        if(max >= 0L)
            l1 = Math.min(l, max - pos);
        else
            l1 = l;
        l2 = in.skip(l1);
        pos = l2 + pos;
        return l2;
    }

    public String toString()
    {
        return in.toString();
    }

    private final InputStream in;
    private long mark;
    private final long max;
    private long pos;
    private boolean propagateClose;
}
