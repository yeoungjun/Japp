// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import javax.mail.Session;
import javax.mail.URLName;

// Referenced classes of package com.sun.mail.imap:
//            IMAPStore

public class IMAPSSLStore extends IMAPStore
{

    public IMAPSSLStore(Session session, URLName urlname)
    {
        super(session, urlname, "imaps", 993, true);
    }
}
