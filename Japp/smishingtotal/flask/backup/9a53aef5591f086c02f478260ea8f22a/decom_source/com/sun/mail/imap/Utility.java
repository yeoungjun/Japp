// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.UIDSet;
import java.util.Vector;
import javax.mail.Message;

// Referenced classes of package com.sun.mail.imap:
//            IMAPMessage

public final class Utility
{
    public static interface Condition
    {

        public abstract boolean test(IMAPMessage imapmessage);
    }


    private Utility()
    {
    }

    public static MessageSet[] toMessageSet(Message amessage[], Condition condition)
    {
        Vector vector;
        int i;
        vector = new Vector(1);
        i = 0;
_L3:
        IMAPMessage imapmessage;
        int j;
        MessageSet messageset;
        IMAPMessage imapmessage1;
        int k;
        if(i >= amessage.length)
            if(vector.isEmpty())
            {
                return null;
            } else
            {
                MessageSet amessageset[] = new MessageSet[vector.size()];
                vector.copyInto(amessageset);
                return amessageset;
            }
        imapmessage = (IMAPMessage)amessage[i];
        if(!imapmessage.isExpunged()) goto _L2; else goto _L1
_L1:
        i++;
          goto _L3
_L2:
        j = imapmessage.getSequenceNumber();
        if(condition != null && !condition.test(imapmessage)) goto _L1; else goto _L4
_L4:
        messageset = new MessageSet();
        messageset.start = j;
        i++;
_L7:
        if(i < amessage.length) goto _L6; else goto _L5
_L5:
        messageset.end = j;
        vector.addElement(messageset);
          goto _L1
_L6:
        imapmessage1 = (IMAPMessage)amessage[i];
        if(!imapmessage1.isExpunged())
        {
            k = imapmessage1.getSequenceNumber();
            if(condition == null || condition.test(imapmessage1))
            {
label0:
                {
                    if(k != j + 1)
                        break label0;
                    j = k;
                }
            }
        }
        i++;
          goto _L7
        i--;
          goto _L5
    }

    public static UIDSet[] toUIDSet(Message amessage[])
    {
        Vector vector;
        int i;
        vector = new Vector(1);
        i = 0;
_L3:
        IMAPMessage imapmessage;
        long l;
        UIDSet uidset;
        IMAPMessage imapmessage1;
        long l1;
        if(i >= amessage.length)
            if(vector.isEmpty())
            {
                return null;
            } else
            {
                UIDSet auidset[] = new UIDSet[vector.size()];
                vector.copyInto(auidset);
                return auidset;
            }
        imapmessage = (IMAPMessage)amessage[i];
        if(!imapmessage.isExpunged()) goto _L2; else goto _L1
_L1:
        i++;
          goto _L3
_L2:
        l = imapmessage.getUID();
        uidset = new UIDSet();
        uidset.start = l;
        i++;
_L6:
        if(i < amessage.length) goto _L5; else goto _L4
_L4:
        uidset.end = l;
        vector.addElement(uidset);
          goto _L1
_L5:
        imapmessage1 = (IMAPMessage)amessage[i];
        if(!imapmessage1.isExpunged())
        {
label0:
            {
                l1 = imapmessage1.getUID();
                if(l1 != 1L + l)
                    break label0;
                l = l1;
            }
        }
        i++;
          goto _L6
        i--;
          goto _L4
    }
}
