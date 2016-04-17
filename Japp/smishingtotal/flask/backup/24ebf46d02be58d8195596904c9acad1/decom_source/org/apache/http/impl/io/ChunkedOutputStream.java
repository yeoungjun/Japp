// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class ChunkedOutputStream extends OutputStream
{

    public ChunkedOutputStream(int i, SessionOutputBuffer sessionoutputbuffer)
    {
        cachePosition = 0;
        wroteLastChunk = false;
        closed = false;
        cache = new byte[i];
        out = sessionoutputbuffer;
    }

    public ChunkedOutputStream(SessionOutputBuffer sessionoutputbuffer)
        throws IOException
    {
        this(2048, sessionoutputbuffer);
    }

    public ChunkedOutputStream(SessionOutputBuffer sessionoutputbuffer, int i)
        throws IOException
    {
        this(i, sessionoutputbuffer);
    }

    public void close()
        throws IOException
    {
        if(!closed)
        {
            closed = true;
            finish();
            out.flush();
        }
    }

    public void finish()
        throws IOException
    {
        if(!wroteLastChunk)
        {
            flushCache();
            writeClosingChunk();
            wroteLastChunk = true;
        }
    }

    public void flush()
        throws IOException
    {
        flushCache();
        out.flush();
    }

    protected void flushCache()
        throws IOException
    {
        if(cachePosition > 0)
        {
            out.writeLine(Integer.toHexString(cachePosition));
            out.write(cache, 0, cachePosition);
            out.writeLine("");
            cachePosition = 0;
        }
    }

    protected void flushCacheWithAppend(byte abyte0[], int i, int j)
        throws IOException
    {
        out.writeLine(Integer.toHexString(j + cachePosition));
        out.write(cache, 0, cachePosition);
        out.write(abyte0, i, j);
        out.writeLine("");
        cachePosition = 0;
    }

    public void write(int i)
        throws IOException
    {
        if(closed)
            throw new IOException("Attempted write to closed stream.");
        cache[cachePosition] = (byte)i;
        cachePosition = 1 + cachePosition;
        if(cachePosition == cache.length)
            flushCache();
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        if(closed)
            throw new IOException("Attempted write to closed stream.");
        if(j >= cache.length - cachePosition)
        {
            flushCacheWithAppend(abyte0, i, j);
            return;
        } else
        {
            System.arraycopy(abyte0, i, cache, cachePosition, j);
            cachePosition = j + cachePosition;
            return;
        }
    }

    protected void writeClosingChunk()
        throws IOException
    {
        out.writeLine("0");
        out.writeLine("");
    }

    private final byte cache[];
    private int cachePosition;
    private boolean closed;
    private final SessionOutputBuffer out;
    private boolean wroteLastChunk;
}
