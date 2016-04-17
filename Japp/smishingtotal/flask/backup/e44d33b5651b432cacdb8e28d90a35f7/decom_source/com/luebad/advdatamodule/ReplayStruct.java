// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;

import java.util.Vector;

// Referenced classes of package com.luebad.advdatamodule:
//            ReplayStruct_Head, ReplayStruct_Context

public class ReplayStruct
{

    public ReplayStruct()
    {
        data_head = new ReplayStruct_Head();
        data_context = new ReplayStruct_Context();
        rec_phone_list = new Vector();
    }

    public void Reset()
    {
        data_head.Reset();
        data_context.Reset();
        rec_phone_list.clear();
    }

    public ReplayStruct_Context data_context;
    public ReplayStruct_Head data_head;
    public Vector rec_phone_list;
}
