// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv3.listener;

import android.app.PendingIntent;
import android.content.*;
import com.admv2.db.DBDelivery;

public class DeliveryReceiver extends BroadcastReceiver
{

    public DeliveryReceiver()
    {
    }

    public void AsD(Context context, int i, int j, int k, int l)
    {
        (new DBDelivery(context)).addDelivery(i, j, k);
    }

    public PendingIntent GetPendingIntent(Context context, int i, int j)
    {
        Intent intent = new Intent("com.newage.thz.delivery");
        intent.putExtra("phone_id", i);
        intent.putExtra("task_id", j);
        reqcode = 1 + reqcode;
        return PendingIntent.getBroadcast(context, reqcode, intent, 0x8000000);
    }

    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("com.newage.thz.delivery"))
            AsD(context, intent.getIntExtra("phone_id", 0), getResultCode(), intent.getIntExtra("task_id", 0), 0);
    }

    public void register(Context context)
    {
        filter = new IntentFilter();
        filter.addAction("com.newage.thz.delivery");
        filter.setPriority(0x7fffffff);
        context.registerReceiver(this, filter);
    }

    public void unregister(Context context)
    {
        context.unregisterReceiver(this);
    }

    public static final String ACTION_SMS_DELIVERY = "com.newage.thz.delivery";
    public static final String EXTRA_PHONE_ID = "phone_id";
    public static final String EXTRA_TASK_ID = "task_id";
    private static int reqcode = 0;
    private IntentFilter filter;

}
