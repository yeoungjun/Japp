// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import java.io.Serializable;

public class Bean
    implements Serializable
{

    public Bean()
    {
        downloadPath = null;
        packeage = null;
        version = null;
    }

    public String toString()
    {
        return (new StringBuilder("Bean [downloadPath=")).append(downloadPath).append(", packeage=").append(packeage).append(", version=").append(version).append("]").toString();
    }

    public String downloadPath;
    public String packeage;
    public Integer version;
}
