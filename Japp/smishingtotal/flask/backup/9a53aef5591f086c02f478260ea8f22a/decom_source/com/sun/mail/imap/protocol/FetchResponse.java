// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.*;
import java.io.IOException;
import java.util.Vector;

// Referenced classes of package com.sun.mail.imap.protocol:
//            IMAPResponse, Item, ENVELOPE, FLAGS, 
//            INTERNALDATE, BODY, BODYSTRUCTURE, RFC822SIZE, 
//            RFC822DATA, UID

public class FetchResponse extends IMAPResponse
{

    public FetchResponse(Protocol protocol)
        throws IOException, ProtocolException
    {
        super(protocol);
        parse();
    }

    public FetchResponse(IMAPResponse imapresponse)
        throws IOException, ProtocolException
    {
        super(imapresponse);
        parse();
    }

    public static Item getItem(Response aresponse[], int i, Class class1)
    {
        int j;
        if(aresponse == null)
            return null;
        j = 0;
_L2:
        if(j >= aresponse.length)
            return null;
        if(aresponse[j] != null && (aresponse[j] instanceof FetchResponse) && ((FetchResponse)aresponse[j]).getNumber() == i)
            break; /* Loop/switch isn't completed */
_L3:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        FetchResponse fetchresponse = (FetchResponse)aresponse[j];
        int k = 0;
        while(k < fetchresponse.items.length) 
        {
            if(class1.isInstance(fetchresponse.items[k]))
                return fetchresponse.items[k];
            k++;
        }
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    private boolean match(char ac[])
    {
        int i = ac.length;
        int j = index;
        int k = 0;
        do
        {
            if(k >= i)
            {
                int _tmp = j;
                int _tmp1 = k;
                return true;
            }
            byte abyte0[] = buffer;
            int l = j + 1;
            char c = Character.toUpperCase((char)abyte0[j]);
            int i1 = k + 1;
            if(c != ac[k])
                return false;
            j = l;
            k = i1;
        } while(true);
    }

    private void parse()
        throws ParsingException
    {
        Vector vector;
        Object obj;
        skipSpaces();
        if(buffer[index] != 40)
            throw new ParsingException((new StringBuilder("error in FETCH parsing, missing '(' at index ")).append(index).toString());
        vector = new Vector();
        obj = null;
_L9:
        index = 1 + index;
        if(index >= size)
            throw new ParsingException((new StringBuilder("error in FETCH parsing, ran off end of buffer, size ")).append(size).toString());
        buffer[index];
        JVM INSTR lookupswitch 6: default 172
    //                   66: 330
    //                   69: 225
    //                   70: 260
    //                   73: 295
    //                   82: 432
    //                   85: 551;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L7:
        break MISSING_BLOCK_LABEL_551;
_L1:
        break; /* Loop/switch isn't completed */
_L3:
        break; /* Loop/switch isn't completed */
_L10:
        if(obj != null)
            vector.addElement(obj);
        if(buffer[index] == 41)
        {
            index = 1 + index;
            items = new Item[vector.size()];
            vector.copyInto(items);
            return;
        }
        if(true) goto _L9; else goto _L8
_L8:
        if(match(ENVELOPE.name))
        {
            index = index + ENVELOPE.name.length;
            obj = new ENVELOPE(this);
        }
          goto _L10
_L4:
        if(match(FLAGS.name))
        {
            index = index + FLAGS.name.length;
            obj = new FLAGS(this);
        }
          goto _L10
_L5:
        if(match(INTERNALDATE.name))
        {
            index = index + INTERNALDATE.name.length;
            obj = new INTERNALDATE(this);
        }
          goto _L10
_L2:
        if(match(BODY.name))
            if(buffer[4 + index] == 91)
            {
                index = index + BODY.name.length;
                obj = new BODY(this);
            } else
            {
                if(match(BODYSTRUCTURE.name))
                    index = index + BODYSTRUCTURE.name.length;
                else
                    index = index + BODY.name.length;
                obj = new BODYSTRUCTURE(this);
            }
          goto _L10
_L6:
        if(match(RFC822SIZE.name))
        {
            index = index + RFC822SIZE.name.length;
            obj = new RFC822SIZE(this);
        } else
        if(match(RFC822DATA.name))
        {
            index = index + RFC822DATA.name.length;
            if(match(HEADER))
                index = index + HEADER.length;
            else
            if(match(TEXT))
                index = index + TEXT.length;
            obj = new RFC822DATA(this);
        }
          goto _L10
        if(match(UID.name))
        {
            index = index + UID.name.length;
            obj = new UID(this);
        }
          goto _L10
    }

    public Item getItem(int i)
    {
        return items[i];
    }

    public Item getItem(Class class1)
    {
        int i = 0;
        do
        {
            if(i >= items.length)
                return null;
            if(class1.isInstance(items[i]))
                return items[i];
            i++;
        } while(true);
    }

    public int getItemCount()
    {
        return items.length;
    }

    private static final char HEADER[] = {
        '.', 'H', 'E', 'A', 'D', 'E', 'R'
    };
    private static final char TEXT[] = {
        '.', 'T', 'E', 'X', 'T'
    };
    private Item items[];

}
