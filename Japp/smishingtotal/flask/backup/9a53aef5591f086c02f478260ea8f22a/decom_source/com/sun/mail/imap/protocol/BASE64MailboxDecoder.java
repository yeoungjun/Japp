// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class BASE64MailboxDecoder
{

    public BASE64MailboxDecoder()
    {
    }

    protected static int base64decode(char ac[], int i, CharacterIterator characteriterator)
    {
        boolean flag;
        int j;
        flag = true;
        j = -1;
_L8:
        byte byte0 = (byte)characteriterator.next();
        if(byte0 != -1) goto _L2; else goto _L1
_L1:
        return i;
_L2:
        if(byte0 != 45)
            break; /* Loop/switch isn't completed */
        if(flag)
        {
            int j1 = i + 1;
            ac[i] = '&';
            return j1;
        }
        if(true) goto _L1; else goto _L3
_L3:
        byte byte1 = (byte)characteriterator.next();
        if(byte1 == -1 || byte1 == 45) goto _L1; else goto _L4
_L4:
        byte byte2 = pem_convert_array[byte0 & 0xff];
        byte byte3 = pem_convert_array[byte1 & 0xff];
        byte byte4 = (byte)(0xfc & byte2 << 2 | 3 & byte3 >>> 4);
        byte byte5;
        byte byte6;
        byte byte8;
        byte byte9;
        int k;
        int l;
        if(j != -1)
        {
            int i1 = i + 1;
            ac[i] = (char)(j << 8 | byte4 & 0xff);
            j = -1;
            i = i1;
        } else
        {
            j = byte4 & 0xff;
        }
        byte5 = (byte)characteriterator.next();
        flag = false;
        if(byte5 == 61)
            continue; /* Loop/switch isn't completed */
        if(byte5 == -1 || byte5 == 45) goto _L1; else goto _L5
_L5:
        byte6 = pem_convert_array[byte5 & 0xff];
        byte byte7 = (byte)(0xf0 & byte3 << 4 | 0xf & byte6 >>> 2);
        if(j != -1)
        {
            l = i + 1;
            ac[i] = (char)(j << 8 | byte7 & 0xff);
            j = -1;
            i = l;
        } else
        {
            j = byte7 & 0xff;
        }
        byte8 = (byte)characteriterator.next();
        flag = false;
        if(byte8 == 61)
            continue; /* Loop/switch isn't completed */
        if(byte8 == -1 || byte8 == 45) goto _L1; else goto _L6
_L6:
        byte9 = pem_convert_array[byte8 & 0xff];
        byte byte10 = (byte)(0xc0 & byte6 << 6 | byte9 & 0x3f);
        if(j != -1)
        {
            (char)(j << 8 | byte10 & 0xff);
            k = i + 1;
            ac[i] = (char)(j << 8 | byte10 & 0xff);
            j = -1;
            i = k;
            flag = false;
        } else
        {
            j = byte10 & 0xff;
            flag = false;
        }
        if(true) goto _L8; else goto _L7
_L7:
    }

    public static String decode(String s)
    {
        if(s != null && s.length() != 0) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        boolean flag = false;
        char ac[] = new char[s.length()];
        StringCharacterIterator stringcharacteriterator = new StringCharacterIterator(s);
        char c = stringcharacteriterator.first();
        int i = 0;
        do
        {
label0:
            {
                if(c != '\uFFFF')
                    break label0;
                if(flag)
                    return new String(ac, 0, i);
            }
            if(true)
                continue;
            int j;
            if(c == '&')
            {
                flag = true;
                j = base64decode(ac, i, stringcharacteriterator);
            } else
            {
                j = i + 1;
                ac[i] = c;
            }
            c = stringcharacteriterator.next();
            i = j;
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    static final char pem_array[];
    private static final byte pem_convert_array[];

    static 
    {
        int i;
        pem_array = (new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
            '8', '9', '+', ','
        });
        pem_convert_array = new byte[256];
        i = 0;
_L3:
        if(i < 255) goto _L2; else goto _L1
_L1:
        int j = 0;
_L4:
        if(j >= pem_array.length)
            return;
        break MISSING_BLOCK_LABEL_425;
_L2:
        pem_convert_array[i] = -1;
        i++;
          goto _L3
        pem_convert_array[pem_array[j]] = (byte)j;
        j++;
          goto _L4
    }
}
