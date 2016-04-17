// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.smtp;

import javax.mail.Session;
import javax.mail.URLName;

// Referenced classes of package com.sun.mail.smtp:
//            SMTPTransport

public class SMTPSSLTransport extends SMTPTransport
{

    public SMTPSSLTransport(Session session, URLName urlname)
    {
        super(session, urlname, "smtps", 465, true);
    }
}
