// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package org.apache.http.impl.conn:
//            Wire

class LoggingOutputStream extends OutputStream
{

    public LoggingOutputStream(OutputStream outputstream, Wire wire1)
    {
        out = outputstream;
        wire = wire1;
    }

    public void close()
        throws IOException
    {
        out.close();
    }

    public void flush()
        throws IOException
    {
        out.flush();
    }

    public void write(int i)
        throws IOException
    {
        wire.output(i);
    }

    public void write(byte abyte0[])
        throws IOException
    {
        wire.output(abyte0);
        out.write(abyte0);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        wire.output(abyte0, i, j);
        out.write(abyte0, i, j);
    }

    private final OutputStream out;
    private final Wire wire;
}
