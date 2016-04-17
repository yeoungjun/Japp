// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

// Referenced classes of package com.sun.mail.util:
//            LineInputStream

public class UUDecoderStream extends FilterInputStream
{

    public UUDecoderStream(InputStream inputstream)
    {
        super(inputstream);
        bufsize = 0;
        index = 0;
        gotPrefix = false;
        gotEnd = false;
        lin = new LineInputStream(inputstream);
        buffer = new byte[45];
    }

    private boolean decode()
        throws IOException
    {
        if(gotEnd)
            return false;
        bufsize = 0;
        String s;
        do
        {
            s = lin.readLine();
            if(s == null)
                throw new IOException("Missing End");
            if(s.regionMatches(true, 0, "end", 0, 3))
            {
                gotEnd = true;
                return false;
            }
        } while(s.length() == 0);
        char c = s.charAt(0);
        if(c < ' ')
            throw new IOException("Buffer format error");
        int i = 0x3f & c - 32;
        if(i == 0)
        {
            String s1 = lin.readLine();
            if(s1 == null || !s1.regionMatches(true, 0, "end", 0, 3))
            {
                throw new IOException("Missing End");
            } else
            {
                gotEnd = true;
                return false;
            }
        }
        int j = (5 + i * 8) / 6;
        if(s.length() < j + 1)
            throw new IOException("Short buffer error");
        int k = 1;
        do
        {
            byte byte1;
            do
            {
                if(bufsize >= i)
                    return true;
                int l = k + 1;
                byte byte0 = (byte)(0x3f & -32 + s.charAt(k));
                k = l + 1;
                byte1 = (byte)(0x3f & -32 + s.charAt(l));
                byte abyte0[] = buffer;
                int i1 = bufsize;
                bufsize = i1 + 1;
                abyte0[i1] = (byte)(0xfc & byte0 << 2 | 3 & byte1 >>> 4);
                if(bufsize < i)
                {
                    byte byte4 = byte1;
                    int l1 = k + 1;
                    byte1 = (byte)(0x3f & -32 + s.charAt(k));
                    byte abyte2[] = buffer;
                    int i2 = bufsize;
                    bufsize = i2 + 1;
                    abyte2[i2] = (byte)(0xf0 & byte4 << 4 | 0xf & byte1 >>> 2);
                    k = l1;
                }
            } while(bufsize >= i);
            byte byte2 = byte1;
            int j1 = k + 1;
            byte byte3 = (byte)(0x3f & -32 + s.charAt(k));
            byte abyte1[] = buffer;
            int k1 = bufsize;
            bufsize = k1 + 1;
            abyte1[k1] = (byte)(0xc0 & byte2 << 6 | byte3 & 0x3f);
            k = j1;
        } while(true);
    }

    private void readPrefix()
        throws IOException
    {
        if(gotPrefix)
            return;
        String s;
        do
        {
            s = lin.readLine();
            if(s == null)
                throw new IOException("UUDecoder error: No Begin");
        } while(!s.regionMatches(true, 0, "begin", 0, 5));
        try
        {
            mode = Integer.parseInt(s.substring(6, 9));
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new IOException((new StringBuilder("UUDecoder error: ")).append(numberformatexception.toString()).toString());
        }
        name = s.substring(10);
        gotPrefix = true;
    }

    public int available()
        throws IOException
    {
        return (3 * in.available()) / 4 + (bufsize - index);
    }

    public int getMode()
        throws IOException
    {
        readPrefix();
        return mode;
    }

    public String getName()
        throws IOException
    {
        readPrefix();
        return name;
    }

    public boolean markSupported()
    {
        return false;
    }

    public int read()
        throws IOException
    {
        if(index >= bufsize)
        {
            readPrefix();
            if(!decode())
                return -1;
            index = 0;
        }
        byte abyte0[] = buffer;
        int i = index;
        index = i + 1;
        return 0xff & abyte0[i];
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

    private byte buffer[];
    private int bufsize;
    private boolean gotEnd;
    private boolean gotPrefix;
    private int index;
    private LineInputStream lin;
    private int mode;
    private String name;
}
