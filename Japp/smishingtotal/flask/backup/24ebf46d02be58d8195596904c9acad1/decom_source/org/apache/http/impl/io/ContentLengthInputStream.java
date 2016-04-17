// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;

public class ContentLengthInputStream extends InputStream
{

    public ContentLengthInputStream(SessionInputBuffer sessioninputbuffer, long l)
    {
        pos = 0L;
        closed = false;
        in = null;
        in = (SessionInputBuffer)Args.notNull(sessioninputbuffer, "Session input buffer");
        contentLength = Args.notNegative(l, "Content length");
    }

    public int available()
        throws IOException
    {
        if(in instanceof BufferInfo)
            return Math.min(((BufferInfo)in).length(), (int)(contentLength - pos));
        else
            return 0;
    }

    public void close()
        throws IOException
    {
        if(closed)
            break MISSING_BLOCK_LABEL_40;
        byte abyte0[];
        if(pos >= contentLength)
            break MISSING_BLOCK_LABEL_35;
        abyte0 = new byte[2048];
        int i;
        do
            i = read(abyte0);
        while(i >= 0);
        closed = true;
        return;
        Exception exception;
        exception;
        closed = true;
        throw exception;
    }

    public int read()
        throws IOException
    {
        if(closed)
            throw new IOException("Attempted read from closed stream.");
        int i;
        if(pos >= contentLength)
        {
            i = -1;
        } else
        {
            i = in.read();
            if(i == -1)
            {
                if(pos < contentLength)
                    throw new ConnectionClosedException((new StringBuilder()).append("Premature end of Content-Length delimited message body (expected: ").append(contentLength).append("; received: ").append(pos).toString());
            } else
            {
                pos = 1L + pos;
                return i;
            }
        }
        return i;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        if(closed)
            throw new IOException("Attempted read from closed stream.");
        int l;
        if(pos >= contentLength)
        {
            l = -1;
        } else
        {
            int k = j;
            if(pos + (long)j > contentLength)
                k = (int)(contentLength - pos);
            l = in.read(abyte0, i, k);
            if(l == -1 && pos < contentLength)
                throw new ConnectionClosedException((new StringBuilder()).append("Premature end of Content-Length delimited message body (expected: ").append(contentLength).append("; received: ").append(pos).toString());
            if(l > 0)
            {
                pos = pos + (long)l;
                return l;
            }
        }
        return l;
    }

    public long skip(long l)
        throws IOException
    {
        if(l > 0L) goto _L2; else goto _L1
_L1:
        long l2 = 0L;
_L4:
        return l2;
_L2:
        byte abyte0[];
        long l1;
        abyte0 = new byte[2048];
        l1 = Math.min(l, contentLength - pos);
        l2 = 0L;
_L6:
        if(l1 <= 0L) goto _L4; else goto _L3
_L3:
        int i = read(abyte0, 0, (int)Math.min(2048L, l1));
        if(i == -1) goto _L4; else goto _L5
_L5:
        l2 += i;
        l1 -= i;
          goto _L6
    }

    private static final int BUFFER_SIZE = 2048;
    private boolean closed;
    private final long contentLength;
    private SessionInputBuffer in;
    private long pos;
}
