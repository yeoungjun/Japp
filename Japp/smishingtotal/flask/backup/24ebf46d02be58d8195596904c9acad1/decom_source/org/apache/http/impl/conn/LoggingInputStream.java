// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package org.apache.http.impl.conn:
//            Wire

class LoggingInputStream extends InputStream
{

    public LoggingInputStream(InputStream inputstream, Wire wire1)
    {
        in = inputstream;
        wire = wire1;
    }

    public int available()
        throws IOException
    {
        return in.available();
    }

    public void close()
        throws IOException
    {
        in.close();
    }

    public void mark(int i)
    {
        this;
        JVM INSTR monitorenter ;
        super.mark(i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean markSupported()
    {
        return false;
    }

    public int read()
        throws IOException
    {
        int i = in.read();
        if(i != -1)
            wire.input(i);
        return i;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        int i = in.read(abyte0);
        if(i != -1)
            wire.input(abyte0, 0, i);
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = in.read(abyte0, i, j);
        if(k != -1)
            wire.input(abyte0, i, k);
        return k;
    }

    public void reset()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        super.reset();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public long skip(long l)
        throws IOException
    {
        return super.skip(l);
    }

    private final InputStream in;
    private final Wire wire;
}
