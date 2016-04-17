// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class QPEncoderStream extends FilterOutputStream
{

    public QPEncoderStream(OutputStream outputstream)
    {
        this(outputstream, 76);
    }

    public QPEncoderStream(OutputStream outputstream, int i)
    {
        super(outputstream);
        count = 0;
        gotSpace = false;
        gotCR = false;
        bytesPerLine = i - 1;
    }

    private void outputCRLF()
        throws IOException
    {
        out.write(13);
        out.write(10);
        count = 0;
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

    protected void output(int i, boolean flag)
        throws IOException
    {
        if(flag)
        {
            int k = 3 + count;
            count = k;
            if(k > bytesPerLine)
            {
                out.write(61);
                out.write(13);
                out.write(10);
                count = 3;
            }
            out.write(61);
            out.write(hex[i >> 4]);
            out.write(hex[i & 0xf]);
            return;
        }
        int j = 1 + count;
        count = j;
        if(j > bytesPerLine)
        {
            out.write(61);
            out.write(13);
            out.write(10);
            count = 1;
        }
        out.write(i);
    }

    public void write(int i)
        throws IOException
    {
        int j = i & 0xff;
        if(gotSpace)
        {
            if(j == 13 || j == 10)
                output(32, true);
            else
                output(32, false);
            gotSpace = false;
        }
        if(j == 13)
        {
            gotCR = true;
            outputCRLF();
            return;
        }
        if(j == 10)
        {
            if(!gotCR)
                outputCRLF();
        } else
        if(j == 32)
            gotSpace = true;
        else
        if(j < 32 || j >= 127 || j == 61)
            output(j, true);
        else
            output(j, false);
        gotCR = false;
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

    private static final char hex[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    private int bytesPerLine;
    private int count;
    private boolean gotCR;
    private boolean gotSpace;

}
