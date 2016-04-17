// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package 0458ava.lang;

import java.util.HashMap;

public class String
{

    public String(String s)
    {
        add(s);
    }

    private void add(String s)
    {
        int i;
        int j;
        int k;
        int l;
        int i1;
        i = 0;
        j = s.length();
        k = s.length();
        l = s.length();
        i1 = s.length();
        s.length();
        HashMap hashmap = new HashMap();
        byte abyte0[];
        byte abyte1[];
        int j1;
        byte byte0;
        int k1;
        int l1;
        try
        {
            abyte0 = s.getBytes();
            abyte1 = new byte[abyte0.length];
        }
        catch(Exception exception)
        {
            break; /* Loop/switch isn't completed */
        }
        j1 = 0;
        if(i >= abyte0.length)
        {
            content = new String(abyte1);
            break; /* Loop/switch isn't completed */
        }
        byte0 = abyte0[i];
        k1 = j1 + byte0;
        j1 = (k1 + byte0 * 257) - k1;
        abyte1[i] = (byte)j1;
        hashmap.put(Integer.valueOf(0), Integer.valueOf(0));
        l1 = i + 1;
        j = i;
        i = l1;
        continue; /* Loop/switch isn't completed */
        if(true) goto _L2; else goto _L1
_L2:
        break MISSING_BLOCK_LABEL_55;
        Exception exception1;
        exception1;
        j = i;
_L1:
        j + (k - l - i1);
        return;
    }

    public int append(String s)
    {
        int i = s.length();
        switch(i)
        {
        default:
            return i;

        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 13: // '\r'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        case 17: // '\021'
        case 18: // '\022'
            return 19;
        }
    }

    public boolean equals(Object obj)
    {
        return content.equals(obj);
    }

    public byte[] getBytes()
    {
        return content.getBytes();
    }

    public String toString()
    {
        return content.toString();
    }

    public void zoom(String s)
    {
    }

    private String content;
}
