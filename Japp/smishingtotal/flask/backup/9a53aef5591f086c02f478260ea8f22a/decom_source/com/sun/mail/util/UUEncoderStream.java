// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class UUEncoderStream extends FilterOutputStream
{

    public UUEncoderStream(OutputStream outputstream)
    {
        this(outputstream, "encoder.buf", 644);
    }

    public UUEncoderStream(OutputStream outputstream, String s)
    {
        this(outputstream, s, 644);
    }

    public UUEncoderStream(OutputStream outputstream, String s, int i)
    {
        super(outputstream);
        bufsize = 0;
        wrotePrefix = false;
        name = s;
        mode = i;
        buffer = new byte[45];
    }

    private void encode()
        throws IOException
    {
        int i = 0;
        out.write(32 + (0x3f & bufsize));
        do
        {
            if(i >= bufsize)
            {
                out.write(10);
                return;
            }
            byte abyte0[] = buffer;
            int j = i + 1;
            byte byte0 = abyte0[i];
            byte byte1;
            byte byte2;
            if(j < bufsize)
            {
                byte abyte1[] = buffer;
                i = j + 1;
                byte1 = abyte1[j];
                int k;
                int l;
                int i1;
                int j1;
                if(i < bufsize)
                {
                    byte abyte2[] = buffer;
                    int k1 = i + 1;
                    byte2 = abyte2[i];
                    i = k1;
                } else
                {
                    byte2 = 1;
                }
            } else
            {
                byte1 = 1;
                byte2 = 1;
                i = j;
            }
            k = 0x3f & byte0 >>> 2;
            l = 0x30 & byte0 << 4 | 0xf & byte1 >>> 4;
            i1 = 0x3c & byte1 << 2 | 3 & byte2 >>> 6;
            j1 = byte2 & 0x3f;
            out.write(k + 32);
            out.write(l + 32);
            out.write(i1 + 32);
            out.write(j1 + 32);
        } while(true);
    }

    private void writePrefix()
        throws IOException
    {
        if(!wrotePrefix)
        {
            PrintStream printstream = new PrintStream(out);
            printstream.println((new StringBuilder("begin ")).append(mode).append(" ").append(name).toString());
            printstream.flush();
            wrotePrefix = true;
        }
    }

    private void writeSuffix()
        throws IOException
    {
        PrintStream printstream = new PrintStream(out);
        printstream.println(" \nend");
        printstream.flush();
    }

    public void close()
        throws IOException
    {
        flush();
        out.close();
    }

    public void flush()
        throws IOException
    {
        if(bufsize > 0)
        {
            writePrefix();
            encode();
        }
        writeSuffix();
        out.flush();
    }

    public void setNameMode(String s, int i)
    {
        name = s;
        mode = i;
    }

    public void write(int i)
        throws IOException
    {
        byte abyte0[] = buffer;
        int j = bufsize;
        bufsize = j + 1;
        abyte0[j] = (byte)i;
        if(bufsize == 45)
        {
            writePrefix();
            encode();
            bufsize = 0;
        }
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = 0;
        do
        {
            if(k >= j)
                return;
            write(abyte0[i + k]);
            k++;
        } while(true);
    }

    private byte buffer[];
    private int bufsize;
    protected int mode;
    protected String name;
    private boolean wrotePrefix;
}
