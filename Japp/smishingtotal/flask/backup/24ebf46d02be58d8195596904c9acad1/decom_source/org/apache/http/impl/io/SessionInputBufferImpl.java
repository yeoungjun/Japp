// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import org.apache.http.MessageConstraintException;
import org.apache.http.config.MessageConstraints;
import org.apache.http.io.*;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.io:
//            HttpTransportMetricsImpl

public class SessionInputBufferImpl
    implements SessionInputBuffer, BufferInfo
{

    public SessionInputBufferImpl(HttpTransportMetricsImpl httptransportmetricsimpl, int i)
    {
        this(httptransportmetricsimpl, i, i, null, null);
    }

    public SessionInputBufferImpl(HttpTransportMetricsImpl httptransportmetricsimpl, int i, int j, MessageConstraints messageconstraints, CharsetDecoder charsetdecoder)
    {
        Args.notNull(httptransportmetricsimpl, "HTTP transport metrcis");
        Args.positive(i, "Buffer size");
        metrics = httptransportmetricsimpl;
        buffer = new byte[i];
        bufferpos = 0;
        bufferlen = 0;
        if(j < 0)
            j = 512;
        minChunkLimit = j;
        if(messageconstraints == null)
            messageconstraints = MessageConstraints.DEFAULT;
        constraints = messageconstraints;
        linebuffer = new ByteArrayBuffer(i);
        decoder = charsetdecoder;
    }

    private int appendDecoded(CharArrayBuffer chararraybuffer, ByteBuffer bytebuffer)
        throws IOException
    {
        if(!bytebuffer.hasRemaining())
            return 0;
        if(cbuf == null)
            cbuf = CharBuffer.allocate(1024);
        decoder.reset();
        int i;
        for(i = 0; bytebuffer.hasRemaining(); i += handleDecodingResult(decoder.decode(bytebuffer, cbuf, true), chararraybuffer, bytebuffer));
        int j = i + handleDecodingResult(decoder.flush(cbuf), chararraybuffer, bytebuffer);
        cbuf.clear();
        return j;
    }

    private int handleDecodingResult(CoderResult coderresult, CharArrayBuffer chararraybuffer, ByteBuffer bytebuffer)
        throws IOException
    {
        if(coderresult.isError())
            coderresult.throwException();
        cbuf.flip();
        int i = cbuf.remaining();
        for(; cbuf.hasRemaining(); chararraybuffer.append(cbuf.get()));
        cbuf.compact();
        return i;
    }

    private int lineFromLineBuffer(CharArrayBuffer chararraybuffer)
        throws IOException
    {
        int i = linebuffer.length();
        if(i > 0)
        {
            if(linebuffer.byteAt(i - 1) == 10)
                i--;
            if(i > 0 && linebuffer.byteAt(i - 1) == 13)
                i--;
        }
        if(decoder == null)
            chararraybuffer.append(linebuffer, 0, i);
        else
            i = appendDecoded(chararraybuffer, ByteBuffer.wrap(linebuffer.buffer(), 0, i));
        linebuffer.clear();
        return i;
    }

    private int lineFromReadBuffer(CharArrayBuffer chararraybuffer, int i)
        throws IOException
    {
        int j = i;
        int k = bufferpos;
        bufferpos = j + 1;
        if(j > k && buffer[j - 1] == 13)
            j--;
        int l = j - k;
        if(decoder == null)
        {
            chararraybuffer.append(buffer, k, l);
            return l;
        } else
        {
            return appendDecoded(chararraybuffer, ByteBuffer.wrap(buffer, k, l));
        }
    }

    private int locateLF()
    {
        for(int i = bufferpos; i < bufferlen; i++)
            if(buffer[i] == 10)
                return i;

        return -1;
    }

    private int streamRead(byte abyte0[], int i, int j)
        throws IOException
    {
        Asserts.notNull(instream, "Input stream");
        return instream.read(abyte0, i, j);
    }

    public int available()
    {
        return capacity() - length();
    }

    public void bind(InputStream inputstream)
    {
        instream = inputstream;
    }

    public int capacity()
    {
        return buffer.length;
    }

    public void clear()
    {
        bufferpos = 0;
        bufferlen = 0;
    }

    public int fillBuffer()
        throws IOException
    {
        if(bufferpos > 0)
        {
            int l = bufferlen - bufferpos;
            if(l > 0)
                System.arraycopy(buffer, bufferpos, buffer, 0, l);
            bufferpos = 0;
            bufferlen = l;
        }
        int i = bufferlen;
        int j = buffer.length - i;
        int k = streamRead(buffer, i, j);
        if(k == -1)
        {
            return -1;
        } else
        {
            bufferlen = i + k;
            metrics.incrementBytesTransferred(k);
            return k;
        }
    }

    public HttpTransportMetrics getMetrics()
    {
        return metrics;
    }

    public boolean hasBufferedData()
    {
        return bufferpos < bufferlen;
    }

    public boolean isBound()
    {
        return instream != null;
    }

    public boolean isDataAvailable(int i)
        throws IOException
    {
        return hasBufferedData();
    }

    public int length()
    {
        return bufferlen - bufferpos;
    }

    public int read()
        throws IOException
    {
        while(!hasBufferedData()) 
            if(fillBuffer() == -1)
                return -1;
        byte abyte0[] = buffer;
        int i = bufferpos;
        bufferpos = i + 1;
        return 0xff & abyte0[i];
    }

    public int read(byte abyte0[])
        throws IOException
    {
        if(abyte0 == null)
            return 0;
        else
            return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        if(abyte0 == null)
            return 0;
        if(hasBufferedData())
        {
            int i1 = Math.min(j, bufferlen - bufferpos);
            System.arraycopy(buffer, bufferpos, abyte0, i, i1);
            bufferpos = i1 + bufferpos;
            return i1;
        }
        if(j > minChunkLimit)
        {
            int l = streamRead(abyte0, i, j);
            if(l > 0)
                metrics.incrementBytesTransferred(l);
            return l;
        }
        while(!hasBufferedData()) 
            if(fillBuffer() == -1)
                return -1;
        int k = Math.min(j, bufferlen - bufferpos);
        System.arraycopy(buffer, bufferpos, abyte0, i, k);
        bufferpos = k + bufferpos;
        return k;
    }

    public int readLine(CharArrayBuffer chararraybuffer)
        throws IOException
    {
        int i;
        int j;
        boolean flag;
        i = -1;
        Args.notNull(chararraybuffer, "Char array buffer");
        j = 0;
        flag = true;
_L11:
        if(!flag) goto _L2; else goto _L1
_L1:
        int k = locateLF();
        if(k == i) goto _L4; else goto _L3
_L3:
        if(!linebuffer.isEmpty()) goto _L6; else goto _L5
_L5:
        i = lineFromReadBuffer(chararraybuffer, k);
_L9:
        return i;
_L6:
        flag = false;
        int j1 = (k + 1) - bufferpos;
        linebuffer.append(buffer, bufferpos, j1);
        bufferpos = k + 1;
_L7:
        int l = constraints.getMaxLineLength();
        if(l > 0 && linebuffer.length() >= l)
            throw new MessageConstraintException("Maximum line length limit exceeded");
        continue; /* Loop/switch isn't completed */
_L4:
        if(hasBufferedData())
        {
            int i1 = bufferlen - bufferpos;
            linebuffer.append(buffer, bufferpos, i1);
            bufferpos = bufferlen;
        }
        j = fillBuffer();
        if(j == i)
            flag = false;
        if(true) goto _L7; else goto _L2
_L2:
        if(j == i && linebuffer.isEmpty()) goto _L9; else goto _L8
_L8:
        return lineFromLineBuffer(chararraybuffer);
        if(true) goto _L11; else goto _L10
_L10:
    }

    public String readLine()
        throws IOException
    {
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(64);
        if(readLine(chararraybuffer) != -1)
            return chararraybuffer.toString();
        else
            return null;
    }

    private final byte buffer[];
    private int bufferlen;
    private int bufferpos;
    private CharBuffer cbuf;
    private final MessageConstraints constraints;
    private final CharsetDecoder decoder;
    private InputStream instream;
    private final ByteArrayBuffer linebuffer;
    private final HttpTransportMetricsImpl metrics;
    private final int minChunkLimit;
}
