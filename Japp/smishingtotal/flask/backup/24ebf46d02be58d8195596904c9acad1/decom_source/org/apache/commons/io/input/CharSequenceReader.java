// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.Reader;
import java.io.Serializable;

public class CharSequenceReader extends Reader
    implements Serializable
{

    public CharSequenceReader(CharSequence charsequence)
    {
        if(charsequence == null)
            charsequence = "";
        charSequence = charsequence;
    }

    public void close()
    {
        idx = 0;
        mark = 0;
    }

    public void mark(int i)
    {
        mark = idx;
    }

    public boolean markSupported()
    {
        return true;
    }

    public int read()
    {
        if(idx >= charSequence.length())
        {
            return -1;
        } else
        {
            CharSequence charsequence = charSequence;
            int i = idx;
            idx = i + 1;
            return charsequence.charAt(i);
        }
    }

    public int read(char ac[], int i, int j)
    {
        if(idx < charSequence.length()) goto _L2; else goto _L1
_L1:
        int k = -1;
_L4:
        return k;
_L2:
        int l;
        if(ac == null)
            throw new NullPointerException("Character array is missing");
        if(j < 0 || i + j > ac.length)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("Array Size=").append(ac.length).append(", offset=").append(i).append(", length=").append(j).toString());
        k = 0;
        l = 0;
_L6:
        if(l >= j) goto _L4; else goto _L3
_L3:
        int i1 = read();
        if(i1 == -1) goto _L4; else goto _L5
_L5:
        ac[i + l] = (char)i1;
        k++;
        l++;
          goto _L6
    }

    public void reset()
    {
        idx = mark;
    }

    public long skip(long l)
    {
        if(l < 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Number of characters to skip is less than zero: ").append(l).toString());
        if(idx >= charSequence.length())
        {
            return -1L;
        } else
        {
            int i = (int)Math.min(charSequence.length(), l + (long)idx);
            int j = i - idx;
            idx = i;
            return (long)j;
        }
    }

    public String toString()
    {
        return charSequence.toString();
    }

    private final CharSequence charSequence;
    private int idx;
    private int mark;
}
