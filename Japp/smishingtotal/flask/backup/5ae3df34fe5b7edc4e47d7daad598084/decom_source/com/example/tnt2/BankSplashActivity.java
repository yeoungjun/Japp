// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.tnt2;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.view.MotionEvent;
import java.util.Timer;
import java.util.TimerTask;

// Referenced classes of package com.example.tnt2:
//            MainActivity

public class BankSplashActivity extends Activity
{

    public BankSplashActivity()
    {
        myHandler = new Handler() {

            public void handleMessage(Message message)
            {
                message.what;
                JVM INSTR tableswitch 1 1: default 24
            //                           1 30;
                   goto _L1 _L2
_L1:
                super.handleMessage(message);
                return;
_L2:
                Intent intent = new Intent();
                intent.setClass(BankSplashActivity.this, com/example/tnt2/MainActivity);
                startActivity(intent);
                finish();
                if(true) goto _L1; else goto _L3
_L3:
            }

            final BankSplashActivity this$0;

            
            {
                this$0 = BankSplashActivity.this;
                super();
            }
        };
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(0x7f030007);
        task = new TimerTask() {

            public void run()
            {
                Message message = new Message();
                message.what = 1;
                myHandler.sendMessage(message);
            }

            final BankSplashActivity this$0;

            
            {
                this$0 = BankSplashActivity.this;
                super();
            }
        };
        timer = new Timer(true);
        timer.schedule(task, 4000L);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        motionevent.getAction();
        return true;
    }

    Handler myHandler;
    TimerTask task;
    Timer timer;
}
