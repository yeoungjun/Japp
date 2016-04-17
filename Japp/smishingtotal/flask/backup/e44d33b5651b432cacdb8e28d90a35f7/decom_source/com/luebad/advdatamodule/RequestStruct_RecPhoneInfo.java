// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;


public class RequestStruct_RecPhoneInfo
{

    public RequestStruct_RecPhoneInfo()
    {
        recphoneid = 0;
        recresult = 0;
        sms_task_id = -1;
    }

    public static final int Request_RecPhoneInfo_Len = 12;
    public int recphoneid;
    public int recresult;
    public int sms_task_id;
}
