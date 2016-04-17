// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ParsingException;
import javax.mail.Flags;

// Referenced classes of package com.sun.mail.imap.protocol:
//            Item, IMAPResponse

public class FLAGS extends Flags
    implements Item
{

    public FLAGS(IMAPResponse imapresponse)
        throws ParsingException
    {
        String as[];
        msgno = imapresponse.getNumber();
        imapresponse.skipSpaces();
        as = imapresponse.readSimpleList();
        if(as == null) goto _L2; else goto _L1
_L1:
        int i = 0;
_L12:
        if(i < as.length) goto _L3; else goto _L2
_L2:
        return;
_L3:
        String s;
        s = as[i];
        if(s.length() < 2 || s.charAt(0) != '\\')
            break MISSING_BLOCK_LABEL_264;
        Character.toUpperCase(s.charAt(1));
        JVM INSTR lookupswitch 6: default 128
    //                   42: 254
    //                   65: 234
    //                   68: 160
    //                   70: 244
    //                   82: 150
    //                   83: 140;
           goto _L4 _L5 _L6 _L7 _L8 _L9 _L10
_L10:
        break; /* Loop/switch isn't completed */
_L4:
        add(s);
_L13:
        i++;
        if(true) goto _L12; else goto _L11
_L11:
        add(javax.mail.Flags.Flag.SEEN);
          goto _L13
_L9:
        add(javax.mail.Flags.Flag.RECENT);
          goto _L13
_L7:
        if(s.length() >= 3)
        {
            char c = s.charAt(2);
            if(c == 'e' || c == 'E')
                add(javax.mail.Flags.Flag.DELETED);
            else
            if(c == 'r' || c == 'R')
                add(javax.mail.Flags.Flag.DRAFT);
        } else
        {
            add(s);
        }
          goto _L13
_L6:
        add(javax.mail.Flags.Flag.ANSWERED);
          goto _L13
_L8:
        add(javax.mail.Flags.Flag.FLAGGED);
          goto _L13
_L5:
        add(javax.mail.Flags.Flag.USER);
          goto _L13
        add(s);
          goto _L13
    }

    static final char name[] = {
        'F', 'L', 'A', 'G', 'S'
    };
    private static final long serialVersionUID = 0x617d1827c5428feL;
    public int msgno;

}
