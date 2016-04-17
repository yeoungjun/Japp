// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.smtp;

import javax.mail.Address;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;

public class SMTPSendFailedException extends SendFailedException
{

    public SMTPSendFailedException(String s, int i, String s1, Exception exception, Address aaddress[], Address aaddress1[], Address aaddress2[])
    {
        super(s1, exception, aaddress, aaddress1, aaddress2);
        cmd = s;
        rc = i;
    }

    public String getCommand()
    {
        return cmd;
    }

    public int getReturnCode()
    {
        return rc;
    }

    private static final long serialVersionUID = 0x6fb43a627ef24a1eL;
    protected InternetAddress addr;
    protected String cmd;
    protected int rc;
}
