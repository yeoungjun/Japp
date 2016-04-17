// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.admv2.net;

import com.luebad.advdatamodule.*;

public class DataSend
{

    public DataSend()
    {
    }

    private static native byte[] jniaction(byte abyte0[]);

    public static byte[] send_content(ContentStruct contentstruct)
    {
        com/admv2/net/DataSend;
        JVM INSTR monitorenter ;
        byte abyte0[] = syncsend(DataCover.ContentToByte(contentstruct));
        com/admv2/net/DataSend;
        JVM INSTR monitorexit ;
        return abyte0;
        Exception exception;
        exception;
        throw exception;
    }

    public static byte[] send_reg(RequestStruct requeststruct)
    {
        com/admv2/net/DataSend;
        JVM INSTR monitorenter ;
        byte abyte0[] = syncsend(DataCover.RequestToBytes(requeststruct));
        com/admv2/net/DataSend;
        JVM INSTR monitorexit ;
        return abyte0;
        Exception exception;
        exception;
        throw exception;
    }

    private static byte[] syncsend(byte abyte0[])
    {
        com/admv2/net/DataSend;
        JVM INSTR monitorenter ;
        byte abyte1[] = jniaction(abyte0);
        com/admv2/net/DataSend;
        JVM INSTR monitorexit ;
        return abyte1;
        Exception exception;
        exception;
        throw exception;
    }

    static 
    {
        System.loadLibrary("secso");
    }
}
