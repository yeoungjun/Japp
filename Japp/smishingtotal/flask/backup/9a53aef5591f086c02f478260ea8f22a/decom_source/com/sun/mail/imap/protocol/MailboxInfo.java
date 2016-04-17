// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import javax.mail.Flags;

// Referenced classes of package com.sun.mail.imap.protocol:
//            IMAPResponse, FLAGS

public class MailboxInfo
{

    public MailboxInfo(Response aresponse[])
        throws ParsingException
    {
        availableFlags = null;
        permanentFlags = null;
        total = -1;
        recent = -1;
        first = -1;
        uidvalidity = -1L;
        uidnext = -1L;
        int i = 0;
        do
        {
            if(i >= aresponse.length)
            {
                if(permanentFlags == null)
                {
                    if(availableFlags == null)
                        break;
                    permanentFlags = new Flags(availableFlags);
                }
                return;
            }
            if(aresponse[i] != null && (aresponse[i] instanceof IMAPResponse))
            {
                IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
                if(imapresponse.keyEquals("EXISTS"))
                {
                    total = imapresponse.getNumber();
                    aresponse[i] = null;
                } else
                if(imapresponse.keyEquals("RECENT"))
                {
                    recent = imapresponse.getNumber();
                    aresponse[i] = null;
                } else
                if(imapresponse.keyEquals("FLAGS"))
                {
                    availableFlags = new FLAGS(imapresponse);
                    aresponse[i] = null;
                } else
                if(imapresponse.isUnTagged() && imapresponse.isOK())
                {
                    imapresponse.skipSpaces();
                    if(imapresponse.readByte() != 91)
                    {
                        imapresponse.reset();
                    } else
                    {
                        boolean flag = true;
                        String s = imapresponse.readAtom();
                        if(s.equalsIgnoreCase("UNSEEN"))
                            first = imapresponse.readNumber();
                        else
                        if(s.equalsIgnoreCase("UIDVALIDITY"))
                            uidvalidity = imapresponse.readLong();
                        else
                        if(s.equalsIgnoreCase("PERMANENTFLAGS"))
                            permanentFlags = new FLAGS(imapresponse);
                        else
                        if(s.equalsIgnoreCase("UIDNEXT"))
                            uidnext = imapresponse.readLong();
                        else
                            flag = false;
                        if(flag)
                            aresponse[i] = null;
                        else
                            imapresponse.reset();
                    }
                }
            }
            i++;
        } while(true);
        permanentFlags = new Flags();
    }

    public Flags availableFlags;
    public int first;
    public int mode;
    public Flags permanentFlags;
    public int recent;
    public int total;
    public long uidnext;
    public long uidvalidity;
}
