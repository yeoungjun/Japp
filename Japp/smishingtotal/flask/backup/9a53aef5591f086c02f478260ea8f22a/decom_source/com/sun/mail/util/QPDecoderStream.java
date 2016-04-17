// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

// Referenced classes of package com.sun.mail.util:
//            ASCIIUtility

public class QPDecoderStream extends FilterInputStream
{

    public QPDecoderStream(InputStream inputstream)
    {
        super(new PushbackInputStream(inputstream, 2));
        ba = new byte[2];
        spaces = 0;
    }

    public int available()
        throws IOException
    {
        return in.available();
    }

    public boolean markSupported()
    {
        return false;
    }

    public int read()
        throws IOException
    {
        int i;
        if(spaces > 0)
        {
            spaces = -1 + spaces;
            i = 32;
        } else
        {
            i = in.read();
            int i1;
            if(i == 32)
                do
                {
                    i1 = in.read();
                    if(i1 != 32)
                        if(i1 == 13 || i1 == 10 || i1 == -1)
                        {
                            spaces = 0;
                            return i1;
                        } else
                        {
                            ((PushbackInputStream)in).unread(i1);
                            return 32;
                        }
                    spaces = 1 + spaces;
                } while(true);
            if(i == 61)
            {
                int j = in.read();
                if(j == 10)
                    return read();
                if(j == 13)
                {
                    int l = in.read();
                    if(l != 10)
                        ((PushbackInputStream)in).unread(l);
                    return read();
                }
                if(j == -1)
                    return -1;
                ba[0] = (byte)j;
                ba[1] = (byte)in.read();
                int k;
                try
                {
                    k = ASCIIUtility.parseInt(ba, 0, 2, 16);
                }
                catch(NumberFormatException numberformatexception)
                {
                    ((PushbackInputStream)in).unread(ba);
                    return i;
                }
                return k;
            }
        }
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = 0;
_L5:
        if(k < j) goto _L2; else goto _L1
_L1:
        return k;
_L2:
        int l;
        l = read();
        if(l != -1)
            break; /* Loop/switch isn't completed */
        if(k == 0)
            return -1;
        if(true) goto _L1; else goto _L3
_L3:
        abyte0[i + k] = (byte)l;
        k++;
        if(true) goto _L5; else goto _L4
_L4:
    }

    protected byte ba[];
    protected int spaces;
}
