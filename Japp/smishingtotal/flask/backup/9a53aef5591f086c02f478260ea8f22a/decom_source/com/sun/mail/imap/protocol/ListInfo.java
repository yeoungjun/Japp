// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import java.util.Vector;

// Referenced classes of package com.sun.mail.imap.protocol:
//            IMAPResponse, BASE64MailboxDecoder

public class ListInfo
{

    public ListInfo(IMAPResponse imapresponse)
        throws ParsingException
    {
        String as[];
        Vector vector;
        name = null;
        separator = '/';
        hasInferiors = true;
        canOpen = true;
        changeState = 3;
        as = imapresponse.readSimpleList();
        vector = new Vector();
        if(as == null) goto _L2; else goto _L1
_L1:
        int i = 0;
_L5:
        if(i < as.length) goto _L3; else goto _L2
_L2:
        attrs = new String[vector.size()];
        vector.copyInto(attrs);
        imapresponse.skipSpaces();
        if(imapresponse.readByte() == 34)
        {
            char c = (char)imapresponse.readByte();
            separator = c;
            if(c == '\\')
                separator = (char)imapresponse.readByte();
            imapresponse.skip(1);
        } else
        {
            imapresponse.skip(2);
        }
        imapresponse.skipSpaces();
        name = imapresponse.readAtomString();
        name = BASE64MailboxDecoder.decode(name);
        return;
_L3:
        if(!as[i].equalsIgnoreCase("\\Marked"))
            break; /* Loop/switch isn't completed */
        changeState = 1;
_L6:
        vector.addElement(as[i]);
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        if(as[i].equalsIgnoreCase("\\Unmarked"))
            changeState = 2;
        else
        if(as[i].equalsIgnoreCase("\\Noselect"))
            canOpen = false;
        else
        if(as[i].equalsIgnoreCase("\\Noinferiors"))
            hasInferiors = false;
          goto _L6
        if(true) goto _L5; else goto _L7
_L7:
    }

    public static final int CHANGED = 1;
    public static final int INDETERMINATE = 3;
    public static final int UNCHANGED = 2;
    public String attrs[];
    public boolean canOpen;
    public int changeState;
    public boolean hasInferiors;
    public String name;
    public char separator;
}
