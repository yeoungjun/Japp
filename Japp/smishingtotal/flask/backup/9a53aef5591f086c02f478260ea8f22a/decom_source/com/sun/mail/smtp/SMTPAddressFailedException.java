// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.smtp;

import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;

public class SMTPAddressFailedException extends SendFailedException
{

    public SMTPAddressFailedException(InternetAddress internetaddress, String s, int i, String s1)
    {
        super(s1);
        addr = internetaddress;
        cmd = s;
        rc = i;
    }

    public InternetAddress getAddress()
    {
        return addr;
    }

    public String getCommand()
    {
        return cmd;
    }

    public int getReturnCode()
    {
        return rc;
    }

    private static final long serialVersionUID = 0xb2b55b6465c5351L;
    protected InternetAddress addr;
    protected String cmd;
    protected int rc;
}
