// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.IOException;
import java.io.OutputStream;

public class HexDump
{

    public HexDump()
    {
    }

    private static StringBuilder dump(StringBuilder stringbuilder, byte byte0)
    {
        for(int i = 0; i < 2; i++)
            stringbuilder.append(_hexcodes[0xf & byte0 >> _shifts[i + 6]]);

        return stringbuilder;
    }

    private static StringBuilder dump(StringBuilder stringbuilder, long l)
    {
        for(int i = 0; i < 8; i++)
            stringbuilder.append(_hexcodes[0xf & (int)(l >> _shifts[i])]);

        return stringbuilder;
    }

    public static void dump(byte abyte0[], long l, OutputStream outputstream, int i)
        throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException
    {
        if(i < 0 || i >= abyte0.length)
            throw new ArrayIndexOutOfBoundsException((new StringBuilder()).append("illegal index: ").append(i).append(" into array of length ").append(abyte0.length).toString());
        if(outputstream == null)
            throw new IllegalArgumentException("cannot write to nullstream");
        long l1 = l + (long)i;
        StringBuilder stringbuilder = new StringBuilder(74);
        for(int j = i; j < abyte0.length; j += 16)
        {
            int k = abyte0.length - j;
            if(k > 16)
                k = 16;
            dump(stringbuilder, l1).append(' ');
            int i1 = 0;
            while(i1 < 16) 
            {
                if(i1 < k)
                    dump(stringbuilder, abyte0[i1 + j]);
                else
                    stringbuilder.append("  ");
                stringbuilder.append(' ');
                i1++;
            }
            int j1 = 0;
            while(j1 < k) 
            {
                if(abyte0[j1 + j] >= 32 && abyte0[j1 + j] < 127)
                    stringbuilder.append((char)abyte0[j1 + j]);
                else
                    stringbuilder.append('.');
                j1++;
            }
            stringbuilder.append(EOL);
            outputstream.write(stringbuilder.toString().getBytes());
            outputstream.flush();
            stringbuilder.setLength(0);
            l1 += k;
        }

    }

    public static final String EOL = System.getProperty("line.separator");
    private static final char _hexcodes[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    private static final int _shifts[] = {
        28, 24, 20, 16, 12, 8, 4, 0
    };

}
