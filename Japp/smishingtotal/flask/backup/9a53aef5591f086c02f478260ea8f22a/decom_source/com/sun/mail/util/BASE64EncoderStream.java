// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class BASE64EncoderStream extends FilterOutputStream
{

    public BASE64EncoderStream(OutputStream outputstream)
    {
        this(outputstream, 76);
    }

    public BASE64EncoderStream(OutputStream outputstream, int i)
    {
        super(outputstream);
        bufsize = 0;
        count = 0;
        noCRLF = false;
        buffer = new byte[3];
        if(i == 0x7fffffff || i < 4)
        {
            noCRLF = true;
            i = 76;
        }
        int j = 4 * (i / 4);
        bytesPerLine = j;
        lineLimit = 3 * (j / 4);
        if(noCRLF)
        {
            outbuf = new byte[j];
            return;
        } else
        {
            outbuf = new byte[j + 2];
            outbuf[j] = 13;
            outbuf[j + 1] = 10;
            return;
        }
    }

    private void encode()
        throws IOException
    {
        int i = encodedSize(bufsize);
        out.write(encode(buffer, 0, bufsize, outbuf), 0, i);
        count = i + count;
        if(count >= bytesPerLine)
        {
            if(!noCRLF)
                out.write(newline);
            count = 0;
        }
    }

    public static byte[] encode(byte abyte0[])
    {
        if(abyte0.length == 0)
            return abyte0;
        else
            return encode(abyte0, 0, abyte0.length, null);
    }

    private static byte[] encode(byte abyte0[], int i, int j, byte abyte1[])
    {
        if(abyte1 == null)
            abyte1 = new byte[encodedSize(j)];
        int k = 0;
        int l = i;
        do
        {
            if(j < 3)
            {
                if(j == 1)
                {
                    int _tmp = l + 1;
                    int k4 = (0xff & abyte0[l]) << 4;
                    abyte1[k + 3] = 61;
                    abyte1[k + 2] = 61;
                    abyte1[k + 1] = (byte)pem_array[k4 & 0x3f];
                    int l4 = k4 >> 6;
                    abyte1[k + 0] = (byte)pem_array[l4 & 0x3f];
                    return abyte1;
                }
                break;
            }
            int i1 = l + 1;
            int j1 = (0xff & abyte0[l]) << 8;
            int k1 = i1 + 1;
            int l1 = (j1 | 0xff & abyte0[i1]) << 8;
            int i2 = k1 + 1;
            int j2 = l1 | 0xff & abyte0[k1];
            abyte1[k + 3] = (byte)pem_array[j2 & 0x3f];
            int k2 = j2 >> 6;
            abyte1[k + 2] = (byte)pem_array[k2 & 0x3f];
            int l2 = k2 >> 6;
            abyte1[k + 1] = (byte)pem_array[l2 & 0x3f];
            int i3 = l2 >> 6;
            abyte1[k + 0] = (byte)pem_array[i3 & 0x3f];
            j -= 3;
            k += 4;
            l = i2;
        } while(true);
        if(j == 2)
        {
            int j3 = l + 1;
            int k3 = (0xff & abyte0[l]) << 8;
            l = j3 + 1;
            int l3 = (k3 | 0xff & abyte0[j3]) << 2;
            abyte1[k + 3] = 61;
            abyte1[k + 2] = (byte)pem_array[l3 & 0x3f];
            int i4 = l3 >> 6;
            abyte1[k + 1] = (byte)pem_array[i4 & 0x3f];
            int j4 = i4 >> 6;
            abyte1[k + 0] = (byte)pem_array[j4 & 0x3f];
        }
        int _tmp1 = l;
        return abyte1;
    }

    private static int encodedSize(int i)
    {
        return 4 * ((i + 2) / 3);
    }

    public void close()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        flush();
        if(count > 0 && !noCRLF)
        {
            out.write(newline);
            out.flush();
        }
        out.close();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void flush()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        if(bufsize > 0)
        {
            encode();
            bufsize = 0;
        }
        out.flush();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void write(int i)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        byte abyte0[] = buffer;
        int j = bufsize;
        bufsize = j + 1;
        abyte0[j] = (byte)i;
        if(bufsize == 3)
        {
            encode();
            bufsize = 0;
        }
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int k;
        int l;
        k = i + j;
        l = i;
_L7:
        if(bufsize != 0 && l < k) goto _L2; else goto _L1
_L1:
        int j1 = 3 * ((bytesPerLine - count) / 4);
        if(l + j1 >= k)
            break MISSING_BLOCK_LABEL_307;
        int k1;
        byte abyte1[];
        k1 = encodedSize(j1);
        if(noCRLF)
            break MISSING_BLOCK_LABEL_101;
        abyte1 = outbuf;
        int k2 = k1 + 1;
        byte abyte2[];
        abyte1[k1] = 13;
        abyte2 = outbuf;
        k1 = k2 + 1;
        abyte2[k2] = 10;
        out.write(encode(abyte0, l, j1, outbuf), 0, k1);
        int l1 = l + j1;
        count = 0;
_L8:
        if(l1 + lineLimit < k) goto _L4; else goto _L3
_L3:
        if(l1 + 3 >= k)
            break MISSING_BLOCK_LABEL_214;
        int i2;
        int j2;
        i2 = 3 * ((k - l1) / 3);
        j2 = encodedSize(i2);
        out.write(encode(abyte0, l1, i2, outbuf), 0, j2);
        l1 += i2;
        count = j2 + count;
_L9:
        if(l1 < k) goto _L6; else goto _L5
_L5:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        int i1 = l + 1;
        write(abyte0[l]);
        l = i1;
          goto _L7
_L4:
        out.write(encode(abyte0, l1, lineLimit, outbuf));
        l1 += lineLimit;
          goto _L8
_L6:
        write(abyte0[l1]);
        l1++;
          goto _L9
        Exception exception;
        exception;
        l;
_L11:
        this;
        JVM INSTR monitorexit ;
        throw exception;
        exception;
        if(true) goto _L11; else goto _L10
_L10:
        l1 = l;
          goto _L8
    }

    private static byte newline[] = {
        13, 10
    };
    private static final char pem_array[] = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
        '8', '9', '+', '/'
    };
    private byte buffer[];
    private int bufsize;
    private int bytesPerLine;
    private int count;
    private int lineLimit;
    private boolean noCRLF;
    private byte outbuf[];

}
