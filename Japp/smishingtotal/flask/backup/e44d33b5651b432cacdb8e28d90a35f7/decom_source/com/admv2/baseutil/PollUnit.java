// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.baseutil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class PollUnit
{

    public PollUnit()
    {
    }

    public static void SetPoll(int i, int j, String s)
    {
        m_stime = i;
        m_seconds = j;
        m_action = s;
    }

    public static void StartPoll(Context context, Class class1)
    {
        startPollingService(context, m_stime, m_seconds, class1, m_action);
    }

    public static void StopPoll(Context context, Class class1)
    {
        stopPollingService(context, class1);
    }

    private static void startPollingService(Context context, int i, int j, Class class1, String s)
    {
        com/admv2/baseutil/PollUnit;
        JVM INSTR monitorenter ;
        AlarmManager alarmmanager = (AlarmManager)context.getSystemService("alarm");
        PendingIntent pendingintent = PendingIntent.getService(context, 0, new Intent(context, class1), 0x8000000);
        alarmmanager.setRepeating(2, SystemClock.elapsedRealtime() + (long)(i * 1000), j * 1000, pendingintent);
        com/admv2/baseutil/PollUnit;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private static void stopPollingService(Context context, Class class1)
    {
        com/admv2/baseutil/PollUnit;
        JVM INSTR monitorenter ;
        ((AlarmManager)context.getSystemService("alarm")).cancel(PendingIntent.getService(context, 0, new Intent(context, class1), 0x8000000));
        com/admv2/baseutil/PollUnit;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private static String m_action = "";
    private static int m_seconds = 0;
    private static int m_stime = 0;

}
