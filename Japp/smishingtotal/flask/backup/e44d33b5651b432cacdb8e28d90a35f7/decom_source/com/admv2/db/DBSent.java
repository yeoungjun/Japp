// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.luebad.advdatamodule.RequestStruct_RecPhoneInfo;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.admv2.db:
//            DBOpenHelper

public class DBSent extends DBOpenHelper
{

    public DBSent(Context context)
    {
        super(context);
    }

    public void addsent(int i, int j, int k)
    {
        lock.lock();
        ExecSql((new StringBuilder("INSERT INTO sent_table(phoneId,Result,taskId) VALUES(")).append(i).append(",").append(j).append(",").append(k).append(")").toString());
        lock.unlock();
    }

    public void clearSent(Vector vector)
    {
        lock.lock();
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        int i = 0;
        do
        {
            if(i >= vector.size())
            {
                sqlitedatabase.close();
                lock.unlock();
                return;
            }
            String as[] = new String[1];
            as[0] = Integer.toString(((RequestStruct_RecPhoneInfo)vector.get(i)).recphoneid);
            sqlitedatabase.delete("sent_table", "phoneId=?", as);
            i++;
        } while(true);
    }

    public short getSent(Vector vector)
    {
        SQLiteDatabase sqlitedatabase;
        Cursor cursor;
        short word0;
        lock.lock();
        vector.clear();
        sqlitedatabase = getReadableDatabase();
        cursor = sqlitedatabase.query("sent_table", new String[] {
            "phoneId", "Result", "taskId"
        }, null, null, null, null, null);
        word0 = 0;
        if(cursor == null) goto _L2; else goto _L1
_L1:
        int i;
        i = cursor.getCount();
        word0 = 0;
        if(i <= 0) goto _L2; else goto _L3
_L3:
        cursor.moveToFirst();
_L6:
        if(!cursor.isAfterLast()) goto _L4; else goto _L2
_L2:
        if(cursor != null)
            cursor.close();
        sqlitedatabase.close();
        lock.unlock();
        return word0;
_L4:
        RequestStruct_RecPhoneInfo requeststruct_recphoneinfo = new RequestStruct_RecPhoneInfo();
        requeststruct_recphoneinfo.recphoneid = cursor.getInt(0);
        requeststruct_recphoneinfo.recresult = cursor.getInt(1);
        requeststruct_recphoneinfo.sms_task_id = cursor.getInt(2);
        vector.add(requeststruct_recphoneinfo);
        word0++;
        cursor.moveToNext();
        if(true) goto _L6; else goto _L5
_L5:
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        super.onCreate(sqlitedatabase);
    }

    private static final String Sent_Table = "sent_table";
    private static Lock lock = new ReentrantLock();
    public static final String str_Create_Sent_Table = "CREATE TABLE sent_table(phoneId integer,Result integer,taskId integer)";

}
