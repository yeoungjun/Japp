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

public class DBConfig extends DBOpenHelper
{

    public DBConfig(Context context)
    {
        super(context);
    }

    public int GetDeplay()
    {
        lock.lock();
        String as[] = {
            "deplaytime"
        };
        int i = 300;
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor cursor = sqlitedatabase.query("config_table", as, null, null, null, null, null);
        if(cursor != null && cursor.getCount() > 0)
            i = cursor.getColumnIndex("deplaytime");
        else
            ExecSql((new StringBuilder("INSERT INTO config_table(deplaytime)  VALUES('")).append(i).append("')").toString());
        if(cursor != null)
            cursor.close();
        sqlitedatabase.close();
        lock.unlock();
        return i;
    }

    public void SetDeplay(int i)
    {
        lock.lock();
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor cursor = sqlitedatabase.query("config_table", null, null, null, null, null, null);
        String s;
        if(cursor != null && cursor.getCount() > 0)
            s = (new StringBuilder("UPDATE config_table SET deplaytime = ")).append(i).toString();
        else
            s = (new StringBuilder("INSERT INTO config_table(deplaytime)  VALUES('")).append(i).append("')").toString();
        ExecSql(s);
        if(cursor != null)
            cursor.close();
        sqlitedatabase.close();
        lock.unlock();
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        super.onCreate(sqlitedatabase);
    }

    private static final String Config_Table = "config_table";
    public static final String Str_Create_Config_Table = "CREATE TABLE config_table(deplaytime integer default(300))";
    private static Lock lock = new ReentrantLock();

}
