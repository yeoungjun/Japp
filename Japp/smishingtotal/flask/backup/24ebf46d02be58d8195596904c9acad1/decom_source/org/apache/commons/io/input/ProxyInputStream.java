// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.*;

public abstract class ProxyInputStream extends FilterInputStream
{

    public ProxyInputStream(InputStream inputstream)
    {
        super(inputstream);
    }

    protected void afterRead(int i)
        throws IOException
    {
    }

    public int available()
        throws IOException
    {
        int i;
        try
        {
            i = super.available();
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return 0;
        }
        return i;
    }

    protected void beforeRead(int i)
        throws IOException
    {
    }

    public void close()
        throws IOException
    {
        try
        {
            in.close();
            return;
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
        }
    }

    protected void handleIOException(IOException ioexception)
        throws IOException
    {
        throw ioexception;
    }

    public void mark(int i)
    {
        this;
        JVM INSTR monitorenter ;
        in.mark(i);
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
        int i = 1;
        int j;
        try
        {
            beforeRead(1);
            j = in.read();
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return -1;
        }
        if(j == -1)
            i = -1;
        afterRead(i);
        return j;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        if(abyte0 == null)
            break MISSING_BLOCK_LABEL_31;
        int i = abyte0.length;
_L1:
        int j;
        beforeRead(i);
        j = in.read(abyte0);
        afterRead(j);
        return j;
        i = 0;
          goto _L1
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        return -1;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k;
        try
        {
            beforeRead(j);
            k = in.read(abyte0, i, j);
            afterRead(k);
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return -1;
        }
        return k;
    }

    public void reset()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        in.reset();
_L2:
        this;
        JVM INSTR monitorexit ;
        return;
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        throw exception;
    }

    public long skip(long l)
        throws IOException
    {
        long l1;
        try
        {
            l1 = in.skip(l);
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return 0L;
        }
        return l1;
    }
}
