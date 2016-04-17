// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ParsingException;
import java.io.ByteArrayInputStream;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, FetchResponse

public class BODY
    implements Item
{

    public BODY(FetchResponse fetchresponse)
        throws ParsingException
    {
        origin = 0;
        msgno = fetchresponse.getNumber();
        fetchresponse.skipSpaces();
        byte byte0;
        do
        {
            byte0 = fetchresponse.readByte();
            if(byte0 == 93)
            {
                if(fetchresponse.readByte() == 60)
                {
                    origin = fetchresponse.readNumber();
                    fetchresponse.skip(1);
                }
                data = fetchresponse.readByteArray();
                return;
            }
        } while(byte0 != 0);
        throw new ParsingException("BODY parse error: missing ``]'' at section end");
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
        'B', 'O', 'D', 'Y'
    };
    public ByteArray data;
    public int msgno;
    public int origin;
    public String section;

}
