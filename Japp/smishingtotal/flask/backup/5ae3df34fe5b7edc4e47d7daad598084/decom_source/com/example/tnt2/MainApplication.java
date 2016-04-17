// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.example.utils.SharePres;
import com.example.utils.SharePresSingle;

public class MainApplication extends Application
{

    public MainApplication()
    {
        context = null;
    }

    public void init(Context context1)
    {
        Log.e(TAG, "init()");
        SharePresSingle.getInstance().init(context1);
        boolean flag = SharePresSingle.getInstance().open("share", 0);
        Log.e(TAG, (new StringBuilder("init():bOpenShare:")).append(flag).toString());
    }

    public void onCreate()
    {
        Log.e(TAG, "onCreate()");
        context = getApplicationContext();
        init(context);
        super.onCreate();
    }

    private static final String TAG = com/example/tnt2/MainApplication.getSimpleName();
    protected Context context;

}
