// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.*;
import java.nio.CharBuffer;

public abstract class ProxyReader extends FilterReader
{

    public ProxyReader(Reader reader)
    {
        super(reader);
    }

    protected void afterRead(int i)
        throws IOException
    {
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
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        in.mark(i);
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

    public int read(CharBuffer charbuffer)
        throws IOException
    {
        if(charbuffer == null)
            break MISSING_BLOCK_LABEL_33;
        int i = charbuffer.length();
_L1:
        int j;
        beforeRead(i);
        j = in.read(charbuffer);
        afterRead(j);
        return j;
        i = 0;
          goto _L1
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        return -1;
    }

    public int read(char ac[])
        throws IOException
    {
        if(ac == null)
            break MISSING_BLOCK_LABEL_31;
        int i = ac.length;
_L1:
        int j;
        beforeRead(i);
        j = in.read(ac);
        afterRead(j);
        return j;
        i = 0;
          goto _L1
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        return -1;
    }

    public int read(char ac[], int i, int j)
        throws IOException
    {
        int k;
        try
        {
            beforeRead(j);
            k = in.read(ac, i, j);
            afterRead(k);
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return -1;
        }
        return k;
    }

    public boolean ready()
        throws IOException
    {
        boolean flag;
        try
        {
            flag = in.ready();
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return false;
        }
        return flag;
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
