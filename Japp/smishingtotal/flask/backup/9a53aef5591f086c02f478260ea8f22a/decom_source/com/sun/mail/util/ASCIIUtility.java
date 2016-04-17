// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.*;

public class ASCIIUtility
{

    private ASCIIUtility()
    {
    }

    public static byte[] getBytes(InputStream inputstream)
        throws IOException
    {
        if(inputstream instanceof ByteArrayInputStream)
        {
            int j = inputstream.available();
            byte abyte1[] = new byte[j];
            inputstream.read(abyte1, 0, j);
            return abyte1;
        }
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.read(abyte0, 0, 1024);
            if(i == -1)
                return bytearrayoutputstream.toByteArray();
            bytearrayoutputstream.write(abyte0, 0, i);
        } while(true);
    }

    public static byte[] getBytes(String s)
    {
        char ac[] = s.toCharArray();
        int i = ac.length;
        byte abyte0[] = new byte[i];
        int j = 0;
        do
        {
            if(j >= i)
                return abyte0;
            int k = j + 1;
            abyte0[j] = (byte)ac[j];
            j = k;
        } while(true);
    }

    public static int parseInt(byte abyte0[], int i, int j)
        throws NumberFormatException
    {
        return parseInt(abyte0, i, j, 10);
    }

    public static int parseInt(byte abyte0[], int i, int j, int k)
        throws NumberFormatException
    {
        if(abyte0 == null)
            throw new NumberFormatException("null");
        int l = i;
        if(j > i)
        {
            int i1;
            boolean flag;
            int j1;
            int k1;
            if(abyte0[l] == 45)
            {
                flag = true;
                i1 = 0x80000000;
                l++;
            } else
            {
                i1 = 0x80000001;
                flag = false;
            }
            j1 = i1 / k;
            int l1;
            if(l < j)
            {
                k1 = l + 1;
                int l2 = Character.digit((char)abyte0[l], k);
                if(l2 < 0)
                    throw new NumberFormatException((new StringBuilder("illegal number: ")).append(toString(abyte0, i, j)).toString());
                l1 = -l2;
            } else
            {
                k1 = l;
                l1 = 0;
            }
            do
            {
                int i2;
                int j2;
                int k2;
                if(k1 >= j)
                    if(flag)
                    {
                        if(k1 > i + 1)
                            return l1;
                        else
                            throw new NumberFormatException("illegal number");
                    } else
                    {
                        return -l1;
                    }
                i2 = k1 + 1;
                j2 = Character.digit((char)abyte0[k1], k);
                if(j2 < 0)
                    throw new NumberFormatException("illegal number");
                if(l1 < j1)
                    throw new NumberFormatException("illegal number");
                k2 = l1 * k;
                if(k2 < i1 + j2)
                    throw new NumberFormatException("illegal number");
                l1 = k2 - j2;
                k1 = i2;
            } while(true);
        } else
        {
            throw new NumberFormatException("illegal number");
        }
    }

    public static long parseLong(byte abyte0[], int i, int j)
        throws NumberFormatException
    {
        return parseLong(abyte0, i, j, 10);
    }

    public static long parseLong(byte abyte0[], int i, int j, int k)
        throws NumberFormatException
    {
        if(abyte0 == null)
            throw new NumberFormatException("null");
        long l = 0L;
        int i1 = i;
        if(j > i)
        {
            long l1;
            boolean flag;
            long l2;
            int j1;
            if(abyte0[i1] == 45)
            {
                flag = true;
                l1 = 0x8000000000000000L;
                i1++;
            } else
            {
                l1 = 0x8000000000000001L;
                flag = false;
            }
            l2 = l1 / (long)k;
            if(i1 < j)
            {
                j1 = i1 + 1;
                int j2 = Character.digit((char)abyte0[i1], k);
                if(j2 < 0)
                    throw new NumberFormatException((new StringBuilder("illegal number: ")).append(toString(abyte0, i, j)).toString());
                l = -j2;
            } else
            {
                j1 = i1;
            }
            do
            {
                int k1;
                int i2;
                long l3;
                if(j1 >= j)
                    if(flag)
                    {
                        if(j1 > i + 1)
                            return l;
                        else
                            throw new NumberFormatException("illegal number");
                    } else
                    {
                        return -l;
                    }
                k1 = j1 + 1;
                i2 = Character.digit((char)abyte0[j1], k);
                if(i2 < 0)
                    throw new NumberFormatException("illegal number");
                if(l < l2)
                    throw new NumberFormatException("illegal number");
                l3 = l * (long)k;
                if(l3 < l1 + (long)i2)
                    throw new NumberFormatException("illegal number");
                l = l3 - (long)i2;
                j1 = k1;
            } while(true);
        } else
        {
            throw new NumberFormatException("illegal number");
        }
    }

    public static String toString(ByteArrayInputStream bytearrayinputstream)
    {
        int i = bytearrayinputstream.available();
        char ac[] = new char[i];
        byte abyte0[] = new byte[i];
        bytearrayinputstream.read(abyte0, 0, i);
        int j = 0;
        do
        {
            if(j >= i)
                return new String(ac);
            int k = j + 1;
            ac[j] = (char)(0xff & abyte0[j]);
            j = k;
        } while(true);
    }

    public static String toString(byte abyte0[], int i, int j)
    {
        int k = j - i;
        char ac[] = new char[k];
        int l = i;
        int i1 = 0;
        do
        {
            if(i1 >= k)
                return new String(ac);
            int j1 = i1 + 1;
            int k1 = l + 1;
            ac[i1] = (char)(0xff & abyte0[l]);
            l = k1;
            i1 = j1;
        } while(true);
    }
}
