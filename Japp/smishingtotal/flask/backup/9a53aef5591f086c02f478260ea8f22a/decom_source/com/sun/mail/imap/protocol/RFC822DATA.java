// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import java.io.ByteArrayInputStream;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse

public class RFC822DATA
    implements Item
{

    public RFC822DATA(FetchResponse fetchresponse)
        throws ParsingException
    {
        msgno = fetchresponse.getNumber();
        fetchresponse.skipSpaces();
        data = fetchresponse.readByteArray();
    }

    public ByteArray getByteArray()
    {
        return data;
    }

    public ByteArrayInputStream getByteArrayInputStream()
    {
        if(data != null)
            return data.toByteArrayInputStream();
        else
            return null;
    }

    static final char name[] = {
        'R', 'F', 'C', '8', '2', '2'
    };
    public ByteArray data;
    public int msgno;

}
