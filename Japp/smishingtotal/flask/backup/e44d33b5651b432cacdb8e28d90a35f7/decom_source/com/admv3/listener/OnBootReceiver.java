// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv3.listener;

import android.content.*;
import com.admv.service.MainService;

public class OnBootReceiver extends BroadcastReceiver
{

    public OnBootReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        rdeEn(context, new Intent(context, com/admv/service/MainService));
    }

    public void rdeEn(Context context, Intent intent)
    {
        intent.addFlags(0x10000000);
        context.startService(new Intent(context, com/admv/service/MainService));
    }
}
