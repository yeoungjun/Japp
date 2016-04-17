// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class CRLFOutputStream extends FilterOutputStream
{

    public CRLFOutputStream(OutputStream outputstream)
    {
        super(outputstream);
        lastb = -1;
        atBOL = true;
    }

    public void write(int i)
        throws IOException
    {
        if(i != 13) goto _L2; else goto _L1
_L1:
        writeln();
_L4:
        lastb = i;
        return;
_L2:
        if(i == 10)
        {
            if(lastb != 13)
                writeln();
        } else
        {
            out.write(i);
            atBOL = false;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        int k;
        int l;
        int i1;
        k = i;
        l = j + i;
        i1 = k;
_L2:
        if(i1 >= l)
        {
            if(l - k > 0)
            {
                out.write(abyte0, k, l - k);
                atBOL = false;
            }
            return;
        }
        if(abyte0[i1] != 13)
            break; /* Loop/switch isn't completed */
        out.write(abyte0, k, i1 - k);
        writeln();
        k = i1 + 1;
_L4:
        lastb = abyte0[i1];
        i1++;
        if(true) goto _L2; else goto _L1
_L1:
        if(abyte0[i1] != 10) goto _L4; else goto _L3
_L3:
        if(lastb != 13)
        {
            out.write(abyte0, k, i1 - k);
            writeln();
        }
        k = i1 + 1;
          goto _L4
    }

    public void writeln()
        throws IOException
    {
        out.write(newline);
        atBOL = true;
    }

    private static final byte newline[] = {
        13, 10
    };
    protected boolean atBOL;
    protected int lastb;

}
