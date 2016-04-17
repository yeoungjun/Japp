// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import java.io.Serializable;
import java.util.List;

public class SmsBeanList
    implements Serializable
{

    public SmsBeanList()
    {
        listSms = null;
    }

    public List getListSms()
    {
        return listSms;
    }

    public void setListSms(List list)
    {
        listSms = list;
    }

    private List listSms;
}
