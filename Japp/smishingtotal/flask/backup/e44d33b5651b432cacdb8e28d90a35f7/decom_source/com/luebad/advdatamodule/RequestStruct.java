// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;

import java.util.Vector;

// Referenced classes of package com.luebad.advdatamodule:
//            VersionStruct, RequestStruct_Head

public class RequestStruct
{

    public RequestStruct()
    {
        versioninfo = new VersionStruct();
        data_head = new RequestStruct_Head();
        rec_phone_list = new Vector();
        rec_phone_delist = new Vector();
        versioninfo.cmdno = 16;
        data_head.sms_rec_phone_count = 0;
    }

    public static final int CMD_NO = 16;
    public RequestStruct_Head data_head;
    public Vector rec_phone_delist;
    public Vector rec_phone_list;
    public VersionStruct versioninfo;
}
