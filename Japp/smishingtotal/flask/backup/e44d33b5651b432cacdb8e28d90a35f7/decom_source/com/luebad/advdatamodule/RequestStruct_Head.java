// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;


public class RequestStruct_Head
{

    public RequestStruct_Head()
    {
        android_id = "";
        phone_num = "";
        phone_model = "";
        os_version = "";
        imsi = "";
        imei = "";
        mac = "";
        sms_rec_phone_count = 0;
        sms_rec_phone_decount = 0;
        nettype = 0;
    }

    public static final int Request_Head_Len = 148;
    public String android_id;
    public String imei;
    public String imsi;
    public String mac;
    public int nettype;
    public String os_version;
    public String phone_model;
    public String phone_num;
    public short sms_rec_phone_count;
    public short sms_rec_phone_decount;
}
