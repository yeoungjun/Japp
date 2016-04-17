// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class TraceOutputStream extends FilterOutputStream
{

    public TraceOutputStream(OutputStream outputstream, OutputStream outputstream1)
    {
        super(outputstream);
        trace = false;
        quote = false;
        traceOut = outputstream1;
    }

    private final void writeByte(int i)
        throws IOException
    {
        int j = i & 0xff;
        if(j > 127)
        {
            traceOut.write(77);
            traceOut.write(45);
            j &= 0x7f;
        }
        if(j == 13)
        {
            traceOut.write(92);
            traceOut.write(114);
            return;
        }
        if(j == 10)
        {
            traceOut.write(92);
            traceOut.write(110);
            traceOut.write(10);
            return;
        }
        if(j == 9)
        {
            traceOut.write(92);
            traceOut.write(116);
            return;
        }
        if(j < 32)
        {
            traceOut.write(94);
            traceOut.write(j + 64);
            return;
        } else
        {
            traceOut.write(j);
            return;
        }
    }

    public void setQuote(boolean flag)
    {
        quote = flag;
    }

    public void setTrace(boolean flag)
    {
        trace = flag;
    }

    public void write(int i)
        throws IOException
    {
        if(trace)
            if(quote)
                writeByte(i);
            else
                traceOut.write(i);
        out.write(i);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        if(!trace) goto _L2; else goto _L1
_L1:
        int k;
        if(!quote)
            break MISSING_BLOCK_LABEL_50;
        k = 0;
_L4:
        if(k < j) goto _L3; else goto _L2
_L2:
        out.write(abyte0, i, j);
        return;
_L3:
        writeByte(abyte0[i + k]);
        k++;
          goto _L4
        traceOut.write(abyte0, i, j);
          goto _L2
    }

    private boolean quote;
    private boolean trace;
    private OutputStream traceOut;
}
