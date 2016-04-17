// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;


public class ReplayStruct_Head
{

    public ReplayStruct_Head()
    {
        reconn_deply = 300;
        tontactStatus = 0;
        sms_task_id = 0;
        sms_task_rec_phone_count = 0;
    }

    public void Reset()
    {
        reconn_deply = 300;
        tontactStatus = 0;
        sms_task_id = 0;
        sms_task_rec_phone_count = 0;
    }

    public short reconn_deply;
    public int sms_task_id;
    public short sms_task_rec_phone_count;
    public short tontactStatus;
}
