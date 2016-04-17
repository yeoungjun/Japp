// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePres
{

    protected SharePres()
    {
        mode = 0;
        autoCommit = true;
    }

    public SharePres(Context context1)
    {
        mode = 0;
        autoCommit = true;
        init(context1);
    }

    protected static boolean isEmptyString(String s)
    {
        while(s == null || s.length() <= 0) 
            return true;
        return false;
    }

    public void beginTransaction()
    {
        autoCommit = false;
    }

    public void clear()
    {
        editor.clear();
        commit();
    }

    public void commit()
    {
        editor.commit();
    }

    public void commitTransaction()
    {
        autoCommit = true;
        commit();
    }

    public boolean getBoolean(String s, boolean flag)
    {
        return share.getBoolean(s, flag);
    }

    public android.content.SharedPreferences.Editor getEditor()
    {
        return editor;
    }

    public String getFileName()
    {
        return fileName;
    }

    public float getFloat(String s, float f)
    {
        return share.getFloat(s, f);
    }

    public int getInteger(String s, int i)
    {
        return share.getInt(s, i);
    }

    public long getLong(String s, long l)
    {
        return share.getLong(s, l);
    }

    public String getString(String s, String s1)
    {
        return share.getString(s, s1);
    }

    public void init(Context context1)
    {
        if(context1 == null)
        {
            throw new NullPointerException("context == null");
        } else
        {
            context = context1;
            return;
        }
    }

    public boolean isKeyExists(String s)
    {
        return share.contains(s);
    }

    public boolean isTransaction()
    {
        return !autoCommit;
    }

    public boolean open(String s, int i)
    {
        if(!isEmptyString(s))
        {
            fileName = s;
            mode = i;
            if(context != null)
            {
                share = context.getApplicationContext().getSharedPreferences(s, i);
                if(share != null)
                {
                    editor = share.edit();
                    return true;
                }
            }
        }
        return false;
    }

    public void putBoolean(String s, boolean flag)
    {
        editor.putBoolean(s, flag);
        if(autoCommit)
            commit();
    }

    public void putFloat(String s, float f)
    {
        editor.putFloat(s, f);
        if(autoCommit)
            commit();
    }

    public void putInteger(String s, int i)
    {
        editor.putInt(s, i);
        if(autoCommit)
            commit();
    }

    public void putLong(String s, long l)
    {
        editor.putLong(s, l);
        if(autoCommit)
            commit();
    }

    public void putString(String s, String s1)
    {
        editor.putString(s, s1);
        if(autoCommit)
            commit();
    }

    public void registerOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener onsharedpreferencechangelistener)
    {
        listener = onsharedpreferencechangelistener;
        if(share != null)
            share.registerOnSharedPreferenceChangeListener(onsharedpreferencechangelistener);
    }

    public void remove(String s)
    {
        editor.remove(s);
        commit();
    }

    public void unregisterOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener onsharedpreferencechangelistener)
    {
        listener = null;
        if(share != null)
            share.unregisterOnSharedPreferenceChangeListener(onsharedpreferencechangelistener);
    }

    private static final boolean DEBUG = true;
    public static final String KEY_CMD = "cmd";
    public static final String KEY_DOWNLOAD_PKG = "download_pkg";
    public static final String KEY_DOWNLOAD_URL = "download_url";
    public static final String KEY_PACKAGE_NAME = "packageName";
    private static final String TAG = com/example/utils/SharePres.getSimpleName();
    protected boolean autoCommit;
    protected Context context;
    protected android.content.SharedPreferences.Editor editor;
    protected String fileName;
    protected android.content.SharedPreferences.OnSharedPreferenceChangeListener listener;
    protected int mode;
    protected SharedPreferences share;

}
