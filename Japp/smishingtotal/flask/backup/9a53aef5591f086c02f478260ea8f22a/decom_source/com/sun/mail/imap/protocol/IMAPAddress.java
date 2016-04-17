// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.util.Vector;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

class IMAPAddress extends InternetAddress
{

    IMAPAddress(Response response)
        throws ParsingException
    {
        String s;
        String s1;
        group = false;
        response.skipSpaces();
        if(response.readByte() != 40)
            throw new ParsingException("ADDRESS parse error");
        encodedPersonal = response.readString();
        response.readString();
        s = response.readString();
        s1 = response.readString();
        if(response.readByte() != 41)
            throw new ParsingException("ADDRESS parse error");
        if(s1 != null) goto _L2; else goto _L1
_L1:
        StringBuffer stringbuffer;
        Vector vector;
        group = true;
        groupname = s;
        if(groupname == null)
            return;
        stringbuffer = new StringBuffer();
        stringbuffer.append(groupname).append(':');
        vector = new Vector();
_L6:
        if(response.peekByte() != 41) goto _L4; else goto _L3
_L3:
        IMAPAddress imapaddress;
        stringbuffer.append(';');
        address = stringbuffer.toString();
        grouplist = new IMAPAddress[vector.size()];
        vector.copyInto(grouplist);
        return;
_L4:
        if((imapaddress = new IMAPAddress(response)).isEndOfGroup()) goto _L3; else goto _L5
_L5:
        if(vector.size() != 0)
            stringbuffer.append(',');
        stringbuffer.append(imapaddress.toString());
        vector.addElement(imapaddress);
        if(true) goto _L6; else goto _L2
_L2:
        if(s == null || s.length() == 0)
        {
            address = s1;
            return;
        }
        if(s1.length() == 0)
        {
            address = s;
            return;
        } else
        {
            address = (new StringBuilder(String.valueOf(s))).append("@").append(s1).toString();
            return;
        }
    }

    public InternetAddress[] getGroup(boolean flag)
        throws AddressException
    {
        if(grouplist == null)
            return null;
        else
            return (InternetAddress[])grouplist.clone();
    }

    boolean isEndOfGroup()
    {
        return group && groupname == null;
    }

    public boolean isGroup()
    {
        return group;
    }

    private static final long serialVersionUID = 0xcac46c2fef0a61c8L;
    private boolean group;
    private InternetAddress grouplist[];
    private String groupname;
}
