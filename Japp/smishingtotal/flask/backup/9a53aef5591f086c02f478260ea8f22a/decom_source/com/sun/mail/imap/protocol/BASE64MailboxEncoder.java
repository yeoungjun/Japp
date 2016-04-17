// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import java.io.*;

public class BASE64MailboxEncoder
{

    public BASE64MailboxEncoder(Writer writer)
    {
        buffer = new byte[4];
        bufsize = 0;
        started = false;
        out = null;
        out = writer;
    }

    public static String encode(String s)
    {
        BASE64MailboxEncoder base64mailboxencoder;
        char ac[];
        int i;
        boolean flag;
        CharArrayWriter chararraywriter;
        int j;
        base64mailboxencoder = null;
        ac = s.toCharArray();
        i = ac.length;
        flag = false;
        chararraywriter = new CharArrayWriter(i);
        j = 0;
_L2:
        char c;
        if(j >= i)
        {
            if(base64mailboxencoder != null)
                base64mailboxencoder.flush();
            if(flag)
                s = chararraywriter.toString();
            return s;
        }
        c = ac[j];
        if(c < ' ' || c > '~')
            break; /* Loop/switch isn't completed */
        if(base64mailboxencoder != null)
            base64mailboxencoder.flush();
        if(c == '&')
        {
            flag = true;
            chararraywriter.write(38);
            chararraywriter.write(45);
        } else
        {
            chararraywriter.write(c);
        }
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        if(base64mailboxencoder == null)
        {
            base64mailboxencoder = new BASE64MailboxEncoder(chararraywriter);
            flag = true;
        }
        base64mailboxencoder.write(c);
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    protected void encode()
        throws IOException
    {
        if(bufsize == 1)
        {
            byte byte5 = buffer[0];
            out.write(pem_array[0x3f & byte5 >>> 2]);
            out.write(pem_array[0 + (0x30 & byte5 << 4)]);
        } else
        {
            if(bufsize == 2)
            {
                byte byte3 = buffer[0];
                byte byte4 = buffer[1];
                out.write(pem_array[0x3f & byte3 >>> 2]);
                out.write(pem_array[(0x30 & byte3 << 4) + (0xf & byte4 >>> 4)]);
                out.write(pem_array[0 + (0x3c & byte4 << 2)]);
                return;
            }
            byte byte0 = buffer[0];
            byte byte1 = buffer[1];
            byte byte2 = buffer[2];
            out.write(pem_array[0x3f & byte0 >>> 2]);
            out.write(pem_array[(0x30 & byte0 << 4) + (0xf & byte1 >>> 4)]);
            out.write(pem_array[(0x3c & byte1 << 2) + (3 & byte2 >>> 6)]);
            out.write(pem_array[byte2 & 0x3f]);
            if(bufsize == 4)
            {
                buffer[0] = buffer[3];
                return;
            }
        }
    }

    public void flush()
    {
        try
        {
            if(bufsize > 0)
            {
                encode();
                bufsize = 0;
            }
            if(started)
            {
                out.write(45);
                started = false;
            }
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public void write(int i)
    {
        try
        {
            if(!started)
            {
                started = true;
                out.write(38);
            }
            byte abyte0[] = buffer;
            int j = bufsize;
            bufsize = j + 1;
            abyte0[j] = (byte)(i >> 8);
            byte abyte1[] = buffer;
            int k = bufsize;
            bufsize = k + 1;
            abyte1[k] = (byte)(i & 0xff);
            if(bufsize >= 3)
            {
                encode();
                bufsize = -3 + bufsize;
            }
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    private static final char pem_array[] = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
        'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
        '8', '9', '+', ','
    };
    protected byte buffer[];
    protected int bufsize;
    protected Writer out;
    protected boolean started;

}
