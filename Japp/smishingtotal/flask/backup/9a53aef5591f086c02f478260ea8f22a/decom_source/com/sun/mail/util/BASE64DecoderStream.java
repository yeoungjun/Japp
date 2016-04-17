// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class BASE64DecoderStream extends FilterInputStream
{

    public BASE64DecoderStream(InputStream inputstream)
    {
        super(inputstream);
        buffer = new byte[3];
        bufsize = 0;
        index = 0;
        input_buffer = new byte[8190];
        input_pos = 0;
        input_len = 0;
        ignoreErrors = false;
        String s;
        boolean flag;
        boolean flag1;
        try
        {
            s = System.getProperty("mail.mime.base64.ignoreerrors");
        }
        catch(SecurityException securityexception)
        {
            return;
        }
        flag = false;
        if(s == null)
            break MISSING_BLOCK_LABEL_78;
        flag1 = s.equalsIgnoreCase("false");
        flag = false;
        if(!flag1)
            flag = true;
        ignoreErrors = flag;
        return;
    }

    public BASE64DecoderStream(InputStream inputstream, boolean flag)
    {
        super(inputstream);
        buffer = new byte[3];
        bufsize = 0;
        index = 0;
        input_buffer = new byte[8190];
        input_pos = 0;
        input_len = 0;
        ignoreErrors = false;
        ignoreErrors = flag;
    }

    private int decode(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = i;
_L2:
        if(j < 3)
            return i - k;
        int l = 0;
        int i1 = 0;
        do
        {
label0:
            {
                if(l < 4)
                    break label0;
                abyte0[i + 2] = (byte)(i1 & 0xff);
                int i3 = i1 >> 8;
                abyte0[i + 1] = (byte)(i3 & 0xff);
                abyte0[i] = (byte)(0xff & i3 >> 8);
                j -= 3;
                i += 3;
            }
            if(true)
                continue;
            int j1 = getByte();
            if(j1 == -1 || j1 == -2)
            {
                boolean flag;
                int k1;
                int l1;
                int i2;
                if(j1 == -1)
                {
                    if(l == 0)
                        return i - k;
                    if(!ignoreErrors)
                        throw new IOException((new StringBuilder("Error in encoded stream: needed 4 valid base64 characters but only got ")).append(l).append(" before EOF").append(recentChars()).toString());
                    flag = true;
                } else
                {
                    if(l < 2 && !ignoreErrors)
                        throw new IOException((new StringBuilder("Error in encoded stream: needed at least 2 valid base64 characters, but only got ")).append(l).append(" before padding character (=)").append(recentChars()).toString());
                    if(l == 0)
                        return i - k;
                    flag = false;
                }
                k1 = l - 1;
                if(k1 == 0)
                    k1 = 1;
                l1 = l + 1;
                i2 = i1 << 6;
                do
                {
                    if(l1 >= 4)
                    {
                        int k2 = i2 >> 8;
                        if(k1 == 2)
                            abyte0[i + 1] = (byte)(k2 & 0xff);
                        abyte0[i] = (byte)(0xff & k2 >> 8);
                        return (i + k1) - k;
                    }
                    if(!flag)
                    {
                        int j2 = getByte();
                        if(j2 == -1)
                        {
                            if(!ignoreErrors)
                                throw new IOException((new StringBuilder("Error in encoded stream: hit EOF while looking for padding characters (=)")).append(recentChars()).toString());
                        } else
                        if(j2 != -2 && !ignoreErrors)
                            throw new IOException((new StringBuilder("Error in encoded stream: found valid base64 character after a padding character (=)")).append(recentChars()).toString());
                    }
                    i2 <<= 6;
                    l1++;
                } while(true);
            }
            int l2 = i1 << 6;
            l++;
            i1 = l2 | j1;
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static byte[] decode(byte abyte0[])
    {
        int i = 3 * (abyte0.length / 4);
        if(i == 0)
            return abyte0;
        if(abyte0[-1 + abyte0.length] == 61)
        {
            i--;
            if(abyte0[-2 + abyte0.length] == 61)
                i--;
        }
        byte abyte1[] = new byte[i];
        int j = 0;
        int k = abyte0.length;
        int l = 0;
        do
        {
            if(k <= 0)
                return abyte1;
            int i1 = 3;
            byte abyte2[] = pem_convert_array;
            int j1 = l + 1;
            int k1 = abyte2[0xff & abyte0[l]] << 6;
            byte abyte3[] = pem_convert_array;
            int l1 = j1 + 1;
            int i2 = (k1 | abyte3[0xff & abyte0[j1]]) << 6;
            int j2;
            int k2;
            int l2;
            if(abyte0[l1] != 61)
            {
                byte abyte5[] = pem_convert_array;
                j2 = l1 + 1;
                i2 |= abyte5[0xff & abyte0[l1]];
            } else
            {
                i1--;
                j2 = l1;
            }
            k2 = i2 << 6;
            if(abyte0[j2] != 61)
            {
                byte abyte4[] = pem_convert_array;
                int i3 = j2 + 1;
                k2 |= abyte4[0xff & abyte0[j2]];
                j2 = i3;
            } else
            {
                i1--;
            }
            if(i1 > 2)
                abyte1[j + 2] = (byte)(k2 & 0xff);
            l2 = k2 >> 8;
            if(i1 > 1)
                abyte1[j + 1] = (byte)(l2 & 0xff);
            abyte1[j] = (byte)(0xff & l2 >> 8);
            j += i1;
            k -= 4;
            l = j2;
        } while(true);
    }

    private int getByte()
        throws IOException
    {
        byte byte0;
        do
        {
            if(input_pos >= input_len)
            {
                try
                {
                    input_len = in.read(input_buffer);
                }
                catch(EOFException eofexception)
                {
                    return -1;
                }
                if(input_len <= 0)
                    return -1;
                input_pos = 0;
            }
            byte abyte0[] = input_buffer;
            int i = input_pos;
            input_pos = i + 1;
            int j = 0xff & abyte0[i];
            if(j == 61)
                return -2;
            byte0 = pem_convert_array[j];
        } while(byte0 == -1);
        return byte0;
    }

    private String recentChars()
    {
        String s1;
        int j;
        int i = 10;
        String s = "";
        if(input_pos <= i)
            i = input_pos;
        if(i <= 0) goto _L2; else goto _L1
_L1:
        s1 = (new StringBuilder(String.valueOf(s))).append(", the ").append(i).append(" most recent characters were: \"").toString();
        j = input_pos - i;
_L6:
        if(j < input_pos) goto _L4; else goto _L3
_L3:
        s = (new StringBuilder(String.valueOf(s1))).append("\"").toString();
_L2:
        return s;
_L4:
        {
            char c = (char)(0xff & input_buffer[j]);
            switch(c)
            {
            case 11: // '\013'
            case 12: // '\f'
            default:
                if(c >= ' ' && c < '\177')
                    s1 = (new StringBuilder(String.valueOf(s1))).append(c).toString();
                else
                    s1 = (new StringBuilder(String.valueOf(s1))).append("\\").append(c).toString();
                break;

            case 9: // '\t'
                break MISSING_BLOCK_LABEL_230;

            case 10: // '\n'
                break MISSING_BLOCK_LABEL_207;

            case 13: // '\r'
                break; /* Loop/switch isn't completed */
            }
        }
        j++;
        if(true) goto _L6; else goto _L5
_L5:
        s1 = (new StringBuilder(String.valueOf(s1))).append("\\r").toString();
        break MISSING_BLOCK_LABEL_178;
        s1 = (new StringBuilder(String.valueOf(s1))).append("\\n").toString();
        break MISSING_BLOCK_LABEL_178;
        s1 = (new StringBuilder(String.valueOf(s1))).append("\\t").toString();
        break MISSING_BLOCK_LABEL_178;
    }

    public int available()
        throws IOException
    {
        return (3 * in.available()) / 4 + (bufsize - index);
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
            bufsize = decode(buffer, 0, buffer.length);
            if(bufsize <= 0)
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
_L3:
        k1 = read();
        if(k1 != -1) goto _L2; else goto _L1
_L1:
        if(i1 == k)
        {
            i1;
            return -1;
        } else
        {
            int j1 = i1 - k;
            i1;
            return j1;
        }
_L2:
        l1 = i1 + 1;
        abyte0[i1] = (byte)k1;
        j--;
        i1 = l1;
_L4:
        if(j > 0) goto _L3; else goto _L1
        int k = i;
        int i1;
        int k1;
        int l1;
        do
        {
            if(index >= bufsize || j <= 0)
            {
                if(index >= bufsize)
                {
                    index = 0;
                    bufsize = 0;
                }
                int l = 3 * (j / 3);
                if(l > 0)
                {
                    int i2 = decode(abyte0, i, l);
                    i += i2;
                    j -= i2;
                    int j2;
                    byte abyte1[];
                    int k2;
                    if(i2 != l)
                        if(i == k)
                            return -1;
                        else
                            return i - k;
                }
                break;
            }
            j2 = i + 1;
            abyte1 = buffer;
            k2 = index;
            index = k2 + 1;
            abyte0[i] = abyte1[k2];
            j--;
            i = j2;
        } while(true);
        i1 = i;
          goto _L4
    }

    private static final char pem_array[];
    private static final byte pem_convert_array[];
    private byte buffer[];
    private int bufsize;
    private boolean ignoreErrors;
    private int index;
    private byte input_buffer[];
    private int input_len;
    private int input_pos;

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
            '8', '9', '+', '/'
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
