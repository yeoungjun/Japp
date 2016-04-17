// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.output;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

public class WriterOutputStream extends OutputStream
{

    public WriterOutputStream(Writer writer1)
    {
        this(writer1, Charset.defaultCharset(), 1024, false);
    }

    public WriterOutputStream(Writer writer1, String s)
    {
        this(writer1, s, 1024, false);
    }

    public WriterOutputStream(Writer writer1, String s, int i, boolean flag)
    {
        this(writer1, Charset.forName(s), i, flag);
    }

    public WriterOutputStream(Writer writer1, Charset charset)
    {
        this(writer1, charset, 1024, false);
    }

    public WriterOutputStream(Writer writer1, Charset charset, int i, boolean flag)
    {
        decoderIn = ByteBuffer.allocate(128);
        writer = writer1;
        decoder = charset.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        decoder.replaceWith("?");
        writeImmediately = flag;
        decoderOut = CharBuffer.allocate(i);
    }

    private void flushOutput()
        throws IOException
    {
        if(decoderOut.position() > 0)
        {
            writer.write(decoderOut.array(), 0, decoderOut.position());
            decoderOut.rewind();
        }
    }

    private void processInput(boolean flag)
        throws IOException
    {
        decoderIn.flip();
        CoderResult coderresult;
        do
        {
            coderresult = decoder.decode(decoderIn, decoderOut, flag);
            if(!coderresult.isOverflow())
                break;
            flushOutput();
        } while(true);
        if(coderresult.isUnderflow())
        {
            decoderIn.compact();
            return;
        } else
        {
            throw new IOException("Unexpected coder result");
        }
    }

    public void close()
        throws IOException
    {
        processInput(true);
        flushOutput();
        writer.close();
    }

    public void flush()
        throws IOException
    {
        flushOutput();
        writer.flush();
    }

    public void write(int i)
        throws IOException
    {
        byte abyte0[] = new byte[1];
        abyte0[0] = (byte)i;
        write(abyte0, 0, 1);
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        while(j > 0) 
        {
            int k = Math.min(j, decoderIn.remaining());
            decoderIn.put(abyte0, i, k);
            processInput(false);
            j -= k;
            i += k;
        }
        if(writeImmediately)
            flushOutput();
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final CharsetDecoder decoder;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
    private final boolean writeImmediately;
    private final Writer writer;
}
