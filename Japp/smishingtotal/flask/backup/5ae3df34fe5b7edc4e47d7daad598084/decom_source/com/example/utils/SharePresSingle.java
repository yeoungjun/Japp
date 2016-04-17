// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.example.utils;


// Referenced classes of package com.example.utils:
//            SharePres

public class SharePresSingle
{

    private SharePresSingle()
    {
    }

    public static SharePres getInstance()
    {
        com/example/utils/SharePresSingle;
        JVM INSTR monitorenter ;
        SharePres sharepres;
        if(instance == null)
            instance = new SharePres();
        sharepres = instance;
        com/example/utils/SharePresSingle;
        JVM INSTR monitorexit ;
        return sharepres;
        Exception exception;
        exception;
        throw exception;
    }

    private static SharePres instance = null;

}
