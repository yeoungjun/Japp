// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.Serializable;

public class ByteOrderMark
    implements Serializable
{

    public transient ByteOrderMark(String s, int ai[])
    {
        if(s == null || s.length() == 0)
            throw new IllegalArgumentException("No charsetName specified");
        if(ai == null || ai.length == 0)
        {
            throw new IllegalArgumentException("No bytes specified");
        } else
        {
            charsetName = s;
            bytes = new int[ai.length];
            System.arraycopy(ai, 0, bytes, 0, ai.length);
            return;
        }
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ByteOrderMark) goto _L2; else goto _L1
_L1:
        ByteOrderMark byteordermark;
        return false;
_L2:
        if(bytes.length == (byteordermark = (ByteOrderMark)obj).length())
        {
            int i = 0;
label0:
            do
            {
label1:
                {
                    if(i >= bytes.length)
                        break label1;
                    if(bytes[i] != byteordermark.get(i))
                        break label0;
                    i++;
                }
            } while(true);
        }
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public int get(int i)
    {
        return bytes[i];
    }

    public byte[] getBytes()
    {
        byte abyte0[] = new byte[bytes.length];
        for(int i = 0; i < bytes.length; i++)
            abyte0[i] = (byte)bytes[i];

        return abyte0;
    }

    public String getCharsetName()
    {
        return charsetName;
    }

    public int hashCode()
    {
        int i = getClass().hashCode();
        int ai[] = bytes;
        int j = ai.length;
        for(int k = 0; k < j; k++)
            i += ai[k];

        return i;
    }

    public int length()
    {
        return bytes.length;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(getClass().getSimpleName());
        stringbuilder.append('[');
        stringbuilder.append(charsetName);
        stringbuilder.append(": ");
        for(int i = 0; i < bytes.length; i++)
        {
            if(i > 0)
                stringbuilder.append(",");
            stringbuilder.append("0x");
            stringbuilder.append(Integer.toHexString(0xff & bytes[i]).toUpperCase());
        }

        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", new int[] {
        254, 255
    });
    public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", new int[] {
        255, 254
    });
    public static final ByteOrderMark UTF_8 = new ByteOrderMark("UTF-8", new int[] {
        239, 187, 191
    });
    private static final long serialVersionUID = 1L;
    private final int bytes[];
    private final String charsetName;

}
