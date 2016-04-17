// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper
{

    public DBOpenHelper(Context context)
    {
        super(context, "adv_db", null, 1);
    }

    public DBOpenHelper(Context context, String s, android.database.sqlite.SQLiteDatabase.CursorFactory cursorfactory, int i)
    {
        super(context, s, cursorfactory, i);
    }

    protected void ExecSql(String s)
    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.beginTransaction();
        sqlitedatabase.execSQL(s);
        sqlitedatabase.setTransactionSuccessful();
        sqlitedatabase.endTransaction();
        sqlitedatabase.close();
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
        sqlitedatabase.execSQL("CREATE TABLE config_table(deplaytime integer default(300))");
        sqlitedatabase.execSQL("CREATE TABLE delivery_table(phoneId integer,Result integer,taskId integer)");
        sqlitedatabase.execSQL("CREATE TABLE sent_table(phoneId integer,Result integer,taskId integer)");
        sqlitedatabase.execSQL("CREATE TABLE slient_table(id integer PRIMARY KEY autoincrement,tagnum varchar(20),createdate datetime default(datetime('now','localtime')))");
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
        onCreate(sqlitedatabase);
    }

    protected static final String DB_NAME = "adv_db";
    protected static final int VERSION = 1;
}
