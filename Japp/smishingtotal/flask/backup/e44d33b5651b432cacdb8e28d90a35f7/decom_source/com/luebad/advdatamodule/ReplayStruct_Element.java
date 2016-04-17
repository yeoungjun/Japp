// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;


public class ReplayStruct_Element
{

    public ReplayStruct_Element()
    {
        tag = 1;
        len = 0;
        values = null;
    }

    public void Reset()
    {
        tag = 1;
        len = 0;
        values = null;
    }

    public int len;
    public short tag;
    public byte values[];
}
