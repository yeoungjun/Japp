// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import java.io.IOException;
import java.io.OutputStream;

class LengthCounter extends OutputStream
{

    public LengthCounter(int i)
    {
        size = 0;
        buf = new byte[8192];
        maxsize = i;
    }

    public byte[] getBytes()
    {
        return buf;
    }

    public int getSize()
    {
        return size;
    }

    public void write(int i)
    {
        int j = 1 + size;
        if(buf != null)
            if(j > maxsize && maxsize >= 0)
                buf = null;
            else
            if(j > buf.length)
            {
                byte abyte0[] = new byte[Math.max(buf.length << 1, j)];
                System.arraycopy(buf, 0, abyte0, 0, size);
                buf = abyte0;
                buf[size] = (byte)i;
            } else
            {
                buf[size] = (byte)i;
            }
        size = j;
    }

    public void write(byte abyte0[])
        throws IOException
    {
        write(abyte0, 0, abyte0.length);
    }

    public void write(byte abyte0[], int i, int j)
    {
        if(i < 0 || i > abyte0.length || j < 0 || i + j > abyte0.length || i + j < 0)
            throw new IndexOutOfBoundsException();
        if(j == 0)
            return;
        int k = j + size;
        if(buf != null)
            if(k > maxsize && maxsize >= 0)
                buf = null;
            else
            if(k > buf.length)
            {
                byte abyte1[] = new byte[Math.max(buf.length << 1, k)];
                System.arraycopy(buf, 0, abyte1, 0, size);
                buf = abyte1;
                System.arraycopy(abyte0, i, buf, size, j);
            } else
            {
                System.arraycopy(abyte0, i, buf, size, j);
            }
        size = k;
    }

    private byte buf[];
    private int maxsize;
    private int size;
}
