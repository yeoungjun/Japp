// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import com.sun.mail.iap.Response;
import java.util.Date;
import java.util.Vector;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse, IMAPAddress

public class ENVELOPE
    implements Item
{

    public ENVELOPE(FetchResponse fetchresponse)
        throws ParsingException
    {
        date = null;
        msgno = fetchresponse.getNumber();
        fetchresponse.skipSpaces();
        if(fetchresponse.readByte() != 40)
            throw new ParsingException("ENVELOPE parse error");
        String s = fetchresponse.readString();
        if(s != null)
            try
            {
                date = mailDateFormat.parse(s);
            }
            catch(Exception exception) { }
        subject = fetchresponse.readString();
        from = parseAddressList(fetchresponse);
        sender = parseAddressList(fetchresponse);
        replyTo = parseAddressList(fetchresponse);
        to = parseAddressList(fetchresponse);
        cc = parseAddressList(fetchresponse);
        bcc = parseAddressList(fetchresponse);
        inReplyTo = fetchresponse.readString();
        messageId = fetchresponse.readString();
        if(fetchresponse.readByte() != 41)
            throw new ParsingException("ENVELOPE parse error");
        else
            return;
    }

    private InternetAddress[] parseAddressList(Response response)
        throws ParsingException
    {
        response.skipSpaces();
        byte byte0 = response.readByte();
        if(byte0 == 40)
        {
            Vector vector = new Vector();
            do
            {
                IMAPAddress imapaddress = new IMAPAddress(response);
                if(!imapaddress.isEndOfGroup())
                    vector.addElement(imapaddress);
            } while(response.peekByte() != 41);
            response.skip(1);
            InternetAddress ainternetaddress[] = new InternetAddress[vector.size()];
            vector.copyInto(ainternetaddress);
            return ainternetaddress;
        }
        if(byte0 == 78 || byte0 == 110)
        {
            response.skip(2);
            return null;
        } else
        {
            throw new ParsingException("ADDRESS parse error");
        }
    }

    private static MailDateFormat mailDateFormat = new MailDateFormat();
    static final char name[] = {
        'E', 'N', 'V', 'E', 'L', 'O', 'P', 'E'
    };
    public InternetAddress bcc[];
    public InternetAddress cc[];
    public Date date;
    public InternetAddress from[];
    public String inReplyTo;
    public String messageId;
    public int msgno;
    public InternetAddress replyTo[];
    public InternetAddress sender[];
    public String subject;
    public InternetAddress to[];

}
