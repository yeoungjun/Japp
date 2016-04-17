// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class TraceInputStream extends FilterInputStream
{

    public TraceInputStream(InputStream inputstream, OutputStream outputstream)
    {
        super(inputstream);
        trace = false;
        quote = false;
        traceOut = outputstream;
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

    public int read()
        throws IOException
    {
        int i;
label0:
        {
            i = in.read();
            if(trace && i != -1)
            {
                if(!quote)
                    break label0;
                writeByte(i);
            }
            return i;
        }
        traceOut.write(i);
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = in.read(abyte0, i, j);
        if(!trace || k == -1) goto _L2; else goto _L1
_L1:
        if(!quote) goto _L4; else goto _L3
_L3:
        int l = 0;
_L6:
        if(l < k) goto _L5; else goto _L2
_L2:
        return k;
_L5:
        writeByte(abyte0[i + l]);
        l++;
        if(true) goto _L6; else goto _L4
_L4:
        traceOut.write(abyte0, i, k);
        return k;
    }

    public void setQuote(boolean flag)
    {
        quote = flag;
    }

    public void setTrace(boolean flag)
    {
        trace = flag;
    }

    private boolean quote;
    private boolean trace;
    private OutputStream traceOut;
}
