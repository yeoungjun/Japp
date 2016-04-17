// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import org.apache.http.Consts;
import org.apache.http.io.*;
import org.apache.http.params.HttpParams;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.io:
//            HttpTransportMetricsImpl

public abstract class AbstractSessionInputBuffer
    implements SessionInputBuffer, BufferInfo
{

    public AbstractSessionInputBuffer()
    {
    }

    private int appendDecoded(CharArrayBuffer chararraybuffer, ByteBuffer bytebuffer)
        throws IOException
    {
        if(!bytebuffer.hasRemaining())
            return 0;
        if(decoder == null)
        {
            decoder = charset.newDecoder();
            decoder.onMalformedInput(onMalformedCharAction);
            decoder.onUnmappableCharacter(onUnmappableCharAction);
        }
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
        if(ascii)
            chararraybuffer.append(linebuffer, 0, i);
        else
            i = appendDecoded(chararraybuffer, ByteBuffer.wrap(linebuffer.buffer(), 0, i));
        linebuffer.clear();
        return i;
    }

    private int lineFromReadBuffer(CharArrayBuffer chararraybuffer, int i)
        throws IOException
    {
        int j = bufferpos;
        int k = i;
        bufferpos = k + 1;
        if(k > j && buffer[k - 1] == 13)
            k--;
        int l = k - j;
        if(ascii)
        {
            chararraybuffer.append(buffer, j, l);
            return l;
        } else
        {
            return appendDecoded(chararraybuffer, ByteBuffer.wrap(buffer, j, l));
        }
    }

    private int locateLF()
    {
        for(int i = bufferpos; i < bufferlen; i++)
            if(buffer[i] == 10)
                return i;

        return -1;
    }

    public int available()
    {
        return capacity() - length();
    }

    public int capacity()
    {
        return buffer.length;
    }

    protected HttpTransportMetricsImpl createTransportMetrics()
    {
        return new HttpTransportMetricsImpl();
    }

    protected int fillBuffer()
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
        int k = instream.read(buffer, i, j);
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

    protected boolean hasBufferedData()
    {
        return bufferpos < bufferlen;
    }

    protected void init(InputStream inputstream, int i, HttpParams httpparams)
    {
        Args.notNull(inputstream, "Input stream");
        Args.notNegative(i, "Buffer size");
        Args.notNull(httpparams, "HTTP parameters");
        instream = inputstream;
        buffer = new byte[i];
        bufferpos = 0;
        bufferlen = 0;
        linebuffer = new ByteArrayBuffer(i);
        String s = (String)httpparams.getParameter("http.protocol.element-charset");
        Charset charset1;
        CodingErrorAction codingerroraction;
        CodingErrorAction codingerroraction1;
        if(s != null)
            charset1 = Charset.forName(s);
        else
            charset1 = Consts.ASCII;
        charset = charset1;
        ascii = charset.equals(Consts.ASCII);
        decoder = null;
        maxLineLen = httpparams.getIntParameter("http.connection.max-line-length", -1);
        minChunkLimit = httpparams.getIntParameter("http.connection.min-chunk-limit", 512);
        metrics = createTransportMetrics();
        codingerroraction = (CodingErrorAction)httpparams.getParameter("http.malformed.input.action");
        if(codingerroraction == null)
            codingerroraction = CodingErrorAction.REPORT;
        onMalformedCharAction = codingerroraction;
        codingerroraction1 = (CodingErrorAction)httpparams.getParameter("http.unmappable.input.action");
        if(codingerroraction1 == null)
            codingerroraction1 = CodingErrorAction.REPORT;
        onUnmappableCharAction = codingerroraction1;
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
            int l = instream.read(abyte0, i, j);
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
        int i1 = (k + 1) - bufferpos;
        linebuffer.append(buffer, bufferpos, i1);
        bufferpos = k + 1;
_L7:
        if(maxLineLen > 0 && linebuffer.length() >= maxLineLen)
            throw new IOException("Maximum line length limit exceeded");
        continue; /* Loop/switch isn't completed */
_L4:
        if(hasBufferedData())
        {
            int l = bufferlen - bufferpos;
            linebuffer.append(buffer, bufferpos, l);
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

    private boolean ascii;
    private byte buffer[];
    private int bufferlen;
    private int bufferpos;
    private CharBuffer cbuf;
    private Charset charset;
    private CharsetDecoder decoder;
    private InputStream instream;
    private ByteArrayBuffer linebuffer;
    private int maxLineLen;
    private HttpTransportMetricsImpl metrics;
    private int minChunkLimit;
    private CodingErrorAction onMalformedCharAction;
    private CodingErrorAction onUnmappableCharAction;
}
