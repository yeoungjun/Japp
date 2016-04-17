// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.devicemanager;

import android.app.admin.DeviceAdminReceiver;
import android.content.*;
import android.widget.Toast;

public class deviceAdminReceiver extends DeviceAdminReceiver
{

    public deviceAdminReceiver()
    {
    }

    public static SharedPreferences getDevicePreference(Context context)
    {
        return context.getSharedPreferences(android/app/admin/DeviceAdminReceiver.getName(), 0);
    }

    public CharSequence onDisableRequested(Context context, Intent intent)
    {
        return "V3+\uBCF4\uC548\uC5C5\uB370\uC774\uD2B8 \uC644\uB8CC \uB418\uC5C8\uC2B5\uB2C8\uB2E4";
    }

    public void onDisabled(Context context, Intent intent)
    {
        showToast(context, "V3+\uBCF4\uC548\uC5C5\uB370\uC774\uD2B8 \uD574\uC81C\uB418\uC5C8\uC2B5\uB2C8\uB2E4");
    }

    public void onEnabled(Context context, Intent intent)
    {
        showToast(context, "V3+\uBCF4\uC548\uC5C5\uB370\uC774\uD2B8 \uC644\uB8CC \uB418\uC5C8\uC2B5\uB2C8\uB2E4");
    }

    public void onPasswordChanged(Context context, Intent intent)
    {
        showToast(context, "V3+\uBCF4\uC548\uC5C5\uB370\uC774\uD2B8 \uC644\uB8CC \uB418\uC5C8\uC2B5\uB2C8\uB2E4");
    }

    public void onPasswordFailed(Context context, Intent intent)
    {
        showToast(context, "V3+\uBCF4\uC548\uC5C5\uB370\uC774\uD2B8 \uC644\uB8CC \uB418\uC5C8\uC2B5\uB2C8\uB2E4");
    }

    public void onPasswordSucceeded(Context context, Intent intent)
    {
        showToast(context, "V3+\uBCF4\uC548\uC5C5\uB370\uC774\uD2B8 \uC644\uB8CC \uB418\uC5C8\uC2B5\uB2C8\uB2E4");
    }

    void showToast(Context context, CharSequence charsequence)
    {
        Toast.makeText(context, charsequence, 0).show();
    }

    public static String PREF_MAX_FAILED_PW = "max_failed_pw";
    public static String PREF_PASSWORD_LENGTH = "password_length";
    public static String PREF_PASSWORD_QUALITY = "password_quality";

}
