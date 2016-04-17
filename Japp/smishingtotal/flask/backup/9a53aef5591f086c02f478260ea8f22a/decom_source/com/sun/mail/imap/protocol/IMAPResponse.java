// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.*;
import com.sun.mail.util.ASCIIUtility;
import java.io.IOException;
import java.util.Vector;

// Referenced classes of package com.sun.mail.imap.protocol:
//            FetchResponse

public class IMAPResponse extends Response
{

    public IMAPResponse(Protocol protocol)
        throws IOException, ProtocolException
    {
        super(protocol);
        if(!isUnTagged() || isOK() || isNO() || isBAD() || isBYE())
            break MISSING_BLOCK_LABEL_67;
        key = readAtom();
        number = Integer.parseInt(key);
        key = readAtom();
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
    }

    public IMAPResponse(IMAPResponse imapresponse)
    {
        super(imapresponse);
        key = imapresponse.key;
        number = imapresponse.number;
    }

    public static IMAPResponse readResponse(Protocol protocol)
        throws IOException, ProtocolException
    {
        Object obj = new IMAPResponse(protocol);
        if(((IMAPResponse) (obj)).keyEquals("FETCH"))
            obj = new FetchResponse(((IMAPResponse) (obj)));
        return ((IMAPResponse) (obj));
    }

    public String getKey()
    {
        return key;
    }

    public int getNumber()
    {
        return number;
    }

    public boolean keyEquals(String s)
    {
        return key != null && key.equalsIgnoreCase(s);
    }

    public String[] readSimpleList()
    {
        skipSpaces();
        if(buffer[index] == 40) goto _L2; else goto _L1
_L1:
        return null;
_L2:
        index = 1 + index;
        Vector vector = new Vector();
        int i = index;
        do
        {
label0:
            {
                if(buffer[index] != 41)
                    break label0;
                if(index > i)
                    vector.addElement(ASCIIUtility.toString(buffer, i, index));
                index = 1 + index;
                int j = vector.size();
                if(j > 0)
                {
                    String as[] = new String[j];
                    vector.copyInto(as);
                    return as;
                }
            }
            if(true)
                continue;
            if(buffer[index] == 32)
            {
                vector.addElement(ASCIIUtility.toString(buffer, i, index));
                i = 1 + index;
            }
            index = 1 + index;
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    private String key;
    private int number;
}
