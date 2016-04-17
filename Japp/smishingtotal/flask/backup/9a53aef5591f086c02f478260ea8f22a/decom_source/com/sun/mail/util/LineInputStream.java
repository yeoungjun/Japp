// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class LineInputStream extends FilterInputStream
{

    public LineInputStream(InputStream inputstream)
    {
        super(inputstream);
        lineBuffer = null;
    }

    public String readLine()
        throws IOException
    {
        Object obj;
        char ac[];
        int i;
        int j;
        obj = in;
        ac = lineBuffer;
        if(ac == null)
        {
            ac = new char[128];
            lineBuffer = ac;
        }
        i = ac.length;
        j = 0;
_L2:
        int k;
        k = ((InputStream) (obj)).read();
        break MISSING_BLOCK_LABEL_37;
        if(k != -1 && k != 10)
        {
label0:
            {
                if(k != 13)
                    break label0;
                int i1 = ((InputStream) (obj)).read();
                if(i1 == 13)
                    i1 = ((InputStream) (obj)).read();
                if(i1 != 10)
                {
                    if(!(obj instanceof PushbackInputStream))
                    {
                        PushbackInputStream pushbackinputstream = new PushbackInputStream(((InputStream) (obj)));
                        in = pushbackinputstream;
                        obj = pushbackinputstream;
                    }
                    ((PushbackInputStream)obj).unread(i1);
                }
            }
        }
        int l;
        if(k == -1 && j == 0)
            return null;
        else
            return String.copyValueOf(ac, 0, j);
        if(--i < 0)
        {
            ac = new char[j + 128];
            i = -1 + (ac.length - j);
            System.arraycopy(lineBuffer, 0, ac, 0, j);
            lineBuffer = ac;
        }
        l = j + 1;
        ac[j] = (char)k;
        j = l;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private char lineBuffer[];
}
