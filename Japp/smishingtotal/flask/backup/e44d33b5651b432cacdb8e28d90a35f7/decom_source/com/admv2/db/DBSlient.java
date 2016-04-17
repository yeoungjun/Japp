// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.admv2.db:
//            DBOpenHelper

public class DBSlient extends DBOpenHelper
{

    public DBSlient(Context context)
    {
        super(context);
    }

    public static String FormatTagNum(String s)
    {
        if(s.indexOf("+82") == 0)
            s = (new StringBuilder("0")).append(s.substring(3)).toString();
        else
        if(s.indexOf("+86") == 0)
            return s.substring(3);
        return s;
    }

    public void ClearDaySelient()
    {
        lock.lock();
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.delete("slient_table", "strftime('%j',createdate) + 15 = strftime('%j','now')", null);
        sqlitedatabase.close();
        lock.unlock();
    }

    public void addslient(String s)
    {
        lock.lock();
        String s1 = FormatTagNum(s);
        ExecSql((new StringBuilder("INSERT INTO slient_table(tagnum) VALUES('")).append(s1).append("')").toString());
        lock.unlock();
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        super.onCreate(sqlitedatabase);
    }

    public boolean slientCheck(String s)
    {
        lock.lock();
        String s1 = FormatTagNum(s);
        if(s1.length() == 3 && s1.indexOf("114") != -1)
            return true;
        if(s1.indexOf("+019") != -1)
            return true;
        if(s1.length() > 8)
            s1 = s1.substring(-8 + s1.length(), s1.length());
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        String as[] = new String[1];
        as[0] = (new StringBuilder("%")).append(s1).toString();
        Cursor cursor = sqlitedatabase.query("slient_table", null, "tagnum like ?", as, null, null, null);
        boolean flag = false;
        if(cursor != null)
        {
            int i = cursor.getCount();
            flag = false;
            if(i > 0)
                flag = true;
        }
        if(cursor != null)
            cursor.close();
        sqlitedatabase.close();
        lock.unlock();
        return flag;
    }

    private static final String Slient_Table = "slient_table";
    public static final String Str_Create_slient_Table = "CREATE TABLE slient_table(id integer PRIMARY KEY autoincrement,tagnum varchar(20),createdate datetime default(datetime('now','localtime')))";
    private static Lock lock = new ReentrantLock();

}
