// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;


public final class RawBitmap
{

    public RawBitmap(int i, int j, int k, int l, int i1, int j1, int k1, 
            Object obj)
    {
        width = i;
        height = j;
        stride = k;
        bits = l;
        rMask = i1;
        gMask = j1;
        bMask = k1;
        buffer = obj;
    }

    public RawBitmap(int ai[], Object obj)
    {
        width = ai[0];
        height = ai[1];
        stride = ai[2];
        bits = ai[3];
        rMask = ai[4];
        gMask = ai[5];
        bMask = ai[6];
        buffer = obj;
    }

    public int[] getHeader()
    {
        int ai[] = new int[7];
        ai[0] = width;
        ai[1] = height;
        ai[2] = stride;
        ai[3] = bits;
        ai[4] = rMask;
        ai[5] = gMask;
        ai[6] = bMask;
        return ai;
    }

    public final int bMask;
    public final int bits;
    public final Object buffer;
    public final int gMask;
    public final int height;
    public final int rMask;
    public final int stride;
    public final int width;
}
