// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.receiver;

import android.content.*;
import android.util.Log;
import com.example.utils.SharePres;
import com.example.utils.SharePresSingle;

public class AppInstallReceiver extends BroadcastReceiver
{

    public AppInstallReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED"))
        {
            String s = SharePresSingle.getInstance().getString("packageName", null);
            String s1 = intent.getDataString().substring(1 + intent.getDataString().indexOf(":"));
            Log.i("test", (new StringBuilder("----------------------pkg1=")).append(s).append("  packageName=").append(s1).toString());
        }
    }
}
