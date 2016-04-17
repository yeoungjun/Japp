// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

public class ReaderInputStream extends InputStream
{

    public ReaderInputStream(Reader reader1)
    {
        this(reader1, Charset.defaultCharset());
    }

    public ReaderInputStream(Reader reader1, String s)
    {
        this(reader1, s, 1024);
    }

    public ReaderInputStream(Reader reader1, String s, int i)
    {
        this(reader1, Charset.forName(s), i);
    }

    public ReaderInputStream(Reader reader1, Charset charset)
    {
        this(reader1, charset, 1024);
    }

    public ReaderInputStream(Reader reader1, Charset charset, int i)
    {
        encoderOut = ByteBuffer.allocate(128);
        reader = reader1;
        encoder = charset.newEncoder();
        encoderIn = CharBuffer.allocate(i);
        encoderIn.flip();
    }

    public void close()
        throws IOException
    {
        reader.close();
    }

    public int read()
        throws IOException
    {
        byte abyte0[] = new byte[1];
        if(read(abyte0) == -1)
            return -1;
        else
            return 0xff & abyte0[0];
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = 0;
label0:
        do
        {
            do
            {
                if(j <= 0)
                    break label0;
                if(encoderOut.position() <= 0)
                    break;
                encoderOut.flip();
                int j1 = Math.min(encoderOut.remaining(), j);
                encoderOut.get(abyte0, i, j1);
                i += j1;
                j -= j1;
                k += j1;
                encoderOut.compact();
            } while(true);
            if(!endOfInput && (lastCoderResult == null || lastCoderResult.isUnderflow()))
            {
                encoderIn.compact();
                int l = encoderIn.position();
                int i1 = reader.read(encoderIn.array(), l, encoderIn.remaining());
                if(i1 == -1)
                    endOfInput = true;
                else
                    encoderIn.position(l + i1);
                encoderIn.flip();
            }
            lastCoderResult = encoder.encode(encoderIn, encoderOut, endOfInput);
        } while(!endOfInput || encoderOut.position() != 0);
        if(k == 0 && endOfInput)
            k = -1;
        return k;
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private boolean endOfInput;
    private CoderResult lastCoderResult;
    private final Reader reader;
}
