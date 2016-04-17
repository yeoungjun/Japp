// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.entity;

import java.io.*;
import java.util.zip.*;

public class DeflateInputStream extends InputStream
{
    static class DeflateStream extends InflaterInputStream
    {

        public void close()
            throws IOException
        {
            if(closed)
            {
                return;
            } else
            {
                closed = true;
                inf.end();
                super.close();
                return;
            }
        }

        private boolean closed;

        public DeflateStream(InputStream inputstream, Inflater inflater)
        {
            super(inputstream, inflater);
            closed = false;
        }
    }


    public DeflateInputStream(InputStream inputstream)
        throws IOException
    {
        byte abyte0[];
        PushbackInputStream pushbackinputstream;
        int i;
        byte abyte1[];
        Inflater inflater;
        abyte0 = new byte[6];
        pushbackinputstream = new PushbackInputStream(inputstream, abyte0.length);
        i = pushbackinputstream.read(abyte0);
        if(i == -1)
            throw new IOException("Unable to read the response");
        abyte1 = new byte[1];
        inflater = new Inflater();
_L8:
        int j = inflater.inflate(abyte1);
        if(j != 0) goto _L2; else goto _L1
_L1:
        DataFormatException dataformatexception;
        if(inflater.finished())
            throw new IOException("Unable to read the response");
          goto _L3
_L6:
        pushbackinputstream.unread(abyte0, 0, i);
        sourceStream = new DeflateStream(pushbackinputstream, new Inflater(true));
        inflater.end();
        return;
_L3:
        if(!inflater.needsDictionary()) goto _L4; else goto _L2
_L2:
        Exception exception;
        if(j != -1)
            break; /* Loop/switch isn't completed */
        try
        {
            throw new IOException("Unable to read the response");
        }
        // Misplaced declaration of an exception variable
        catch(DataFormatException dataformatexception) { }
        finally
        {
            inflater.end();
        }
        if(true) goto _L6; else goto _L5
        throw exception;
_L4:
        if(!inflater.needsInput()) goto _L8; else goto _L7
_L7:
        inflater.setInput(abyte0);
          goto _L8
_L5:
        pushbackinputstream.unread(abyte0, 0, i);
        sourceStream = new DeflateStream(pushbackinputstream, new Inflater());
        inflater.end();
        return;
    }

    public int available()
        throws IOException
    {
        return sourceStream.available();
    }

    public void close()
        throws IOException
    {
        sourceStream.close();
    }

    public void mark(int i)
    {
        sourceStream.mark(i);
    }

    public boolean markSupported()
    {
        return sourceStream.markSupported();
    }

    public int read()
        throws IOException
    {
        return sourceStream.read();
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return sourceStream.read(abyte0);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        return sourceStream.read(abyte0, i, j);
    }

    public void reset()
        throws IOException
    {
        sourceStream.reset();
    }

    public long skip(long l)
        throws IOException
    {
        return sourceStream.skip(l);
    }

    private InputStream sourceStream;
}
