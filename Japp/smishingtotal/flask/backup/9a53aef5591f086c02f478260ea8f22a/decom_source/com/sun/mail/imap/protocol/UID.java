// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse

public class UID
    implements Item
{

    public UID(FetchResponse fetchresponse)
        throws ParsingException
    {
        seqnum = fetchresponse.getNumber();
        fetchresponse.skipSpaces();
        uid = fetchresponse.readLong();
    }

    static final char name[] = {
        'U', 'I', 'D'
    };
    public int seqnum;
    public long uid;

}
