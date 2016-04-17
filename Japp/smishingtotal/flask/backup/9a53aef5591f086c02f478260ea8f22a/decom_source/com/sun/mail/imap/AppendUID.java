// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;


public class AppendUID
{

    public AppendUID(long l, long l1)
    {
        uidvalidity = -1L;
        uid = -1L;
        uidvalidity = l;
        uid = l1;
    }

    public long uid;
    public long uidvalidity;
}
