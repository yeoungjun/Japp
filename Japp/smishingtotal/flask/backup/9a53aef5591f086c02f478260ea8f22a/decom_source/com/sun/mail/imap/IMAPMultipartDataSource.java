// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import java.util.Vector;
import javax.mail.*;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;

// Referenced classes of package com.sun.mail.imap:
//            IMAPBodyPart, IMAPMessage

public class IMAPMultipartDataSource extends MimePartDataSource
    implements MultipartDataSource
{

    protected IMAPMultipartDataSource(MimePart mimepart, BODYSTRUCTURE abodystructure[], String s, IMAPMessage imapmessage)
    {
        super(mimepart);
        parts = new Vector(abodystructure.length);
        int i = 0;
        do
        {
            if(i >= abodystructure.length)
                return;
            Vector vector = parts;
            BODYSTRUCTURE bodystructure = abodystructure[i];
            String s1;
            if(s == null)
                s1 = Integer.toString(i + 1);
            else
                s1 = (new StringBuilder(String.valueOf(s))).append(".").append(Integer.toString(i + 1)).toString();
            vector.addElement(new IMAPBodyPart(bodystructure, s1, imapmessage));
            i++;
        } while(true);
    }

    public BodyPart getBodyPart(int i)
        throws MessagingException
    {
        return (BodyPart)parts.elementAt(i);
    }

    public int getCount()
    {
        return parts.size();
    }

    private Vector parts;
}
