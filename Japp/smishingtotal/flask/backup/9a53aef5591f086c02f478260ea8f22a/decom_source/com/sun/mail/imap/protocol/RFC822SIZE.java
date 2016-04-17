// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse

public class RFC822SIZE
    implements Item
{

    public RFC822SIZE(FetchResponse fetchresponse)
        throws ParsingException
    {
        msgno = fetchresponse.getNumber();
        fetchresponse.skipSpaces();
        size = fetchresponse.readNumber();
    }

    static final char name[] = {
        'R', 'F', 'C', '8', '2', '2', '.', 'S', 'I', 'Z', 
        'E'
    };
    public int msgno;
    public int size;

}
