// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import java.io.Serializable;

public class SmsInfo
    implements Serializable
{

    public SmsInfo()
    {
    }

    public String getDate()
    {
        return date;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getSmsbody()
    {
        return smsbody;
    }

    public String getType()
    {
        return type;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setPhoneNumber(String s)
    {
        phoneNumber = s;
    }

    public void setSmsbody(String s)
    {
        smsbody = s;
    }

    public void setType(String s)
    {
        type = s;
    }

    private String date;
    private String name;
    private String phoneNumber;
    private String smsbody;
    private String type;
}
