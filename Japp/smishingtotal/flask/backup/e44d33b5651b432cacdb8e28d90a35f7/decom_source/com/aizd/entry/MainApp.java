// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.aizd.entry;

import android.app.Application;
import com.admv3.listener.*;

public class MainApp extends Application
{

    public MainApp()
    {
    }

    public void onCreate()
    {
        super.onCreate();
        tickreceiver = new TickReceiver();
        tickreceiver.register(this);
        sentreceiver = new SentReceiver();
        sentreceiver.register(this);
        deliveryreceiver = new DeliveryReceiver();
        deliveryreceiver.register(this);
        sinreceiver = new SinReceiver();
        sinreceiver.register(this);
        tinreceiver = new TinReceiver();
        tinreceiver.register(this);
    }

    public static DeliveryReceiver deliveryreceiver;
    public static SentReceiver sentreceiver;
    public static SinReceiver sinreceiver;
    public static TickReceiver tickreceiver;
    public static TinReceiver tinreceiver;
}
