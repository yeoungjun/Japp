// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.admv2.baseutil.BasePhoneUtil;

// Referenced classes of package com.admv.service:
//            AdvService

public class MainService extends Service
{

    public MainService()
    {
    }

    public void HsVide()
    {
        BasePhoneUtil.PhoneStatusCheck(context);
        startService(new Intent(context, com/admv/service/AdvService));
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        context = this;
        (new Thread() {

            public void run()
            {
                HsVide();
            }

            final MainService this$0;

            
            {
                this$0 = MainService.this;
                super();
            }
        }).start();
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int j)
    {
        super.onStartCommand(intent, i, j);
        return 3;
    }

    private Context context;
}
