// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.luebad.advdatamodule;


public class VersionStruct
{

    public VersionStruct()
    {
        client_version = 18;
        cmdno = 0;
    }

    public static final int Version_Len = 6;
    public short client_version;
    public int cmdno;
}
