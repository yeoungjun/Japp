// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;

import java.util.Vector;

public class ReplayStruct_Context
{

    public ReplayStruct_Context()
    {
        type = 1;
        elemCount = 0;
        element = new Vector();
    }

    public void Reset()
    {
        type = 1;
        elemCount = 0;
        element.clear();
    }

    public short elemCount;
    public Vector element;
    public short type;
}
