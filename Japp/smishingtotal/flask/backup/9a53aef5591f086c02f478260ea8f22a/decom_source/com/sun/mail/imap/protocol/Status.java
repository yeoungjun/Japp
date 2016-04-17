// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;

public class Status
{

    public Status(Response response)
        throws ParsingException
    {
        mbox = null;
        total = -1;
        recent = -1;
        uidnext = -1L;
        uidvalidity = -1L;
        unseen = -1;
        mbox = response.readAtomString();
        response.skipSpaces();
        if(response.readByte() != 40)
            throw new ParsingException("parse error in STATUS");
_L2:
        String s;
        s = response.readAtom();
        if(!s.equalsIgnoreCase("MESSAGES"))
            break; /* Loop/switch isn't completed */
        total = response.readNumber();
_L3:
        if(response.readByte() == 41)
            return;
        if(true) goto _L2; else goto _L1
_L1:
        if(s.equalsIgnoreCase("RECENT"))
            recent = response.readNumber();
        else
        if(s.equalsIgnoreCase("UIDNEXT"))
            uidnext = response.readLong();
        else
        if(s.equalsIgnoreCase("UIDVALIDITY"))
            uidvalidity = response.readLong();
        else
        if(s.equalsIgnoreCase("UNSEEN"))
            unseen = response.readNumber();
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    public static void add(Status status, Status status1)
    {
        if(status1.total != -1)
            status.total = status1.total;
        if(status1.recent != -1)
            status.recent = status1.recent;
        if(status1.uidnext != -1L)
            status.uidnext = status1.uidnext;
        if(status1.uidvalidity != -1L)
            status.uidvalidity = status1.uidvalidity;
        if(status1.unseen != -1)
            status.unseen = status1.unseen;
    }

    static final String standardItems[] = {
        "MESSAGES", "RECENT", "UNSEEN", "UIDNEXT", "UIDVALIDITY"
    };
    public String mbox;
    public int recent;
    public int total;
    public long uidnext;
    public long uidvalidity;
    public int unseen;

}
