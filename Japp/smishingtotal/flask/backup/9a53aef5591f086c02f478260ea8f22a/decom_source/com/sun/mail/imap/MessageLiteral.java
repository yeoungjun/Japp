// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.Literal;
import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.mail.Message;
import javax.mail.MessagingException;

// Referenced classes of package com.sun.mail.imap:
//            LengthCounter

class MessageLiteral
    implements Literal
{

    public MessageLiteral(Message message, int i)
        throws MessagingException, IOException
    {
        msgSize = -1;
        msg = message;
        LengthCounter lengthcounter = new LengthCounter(i);
        CRLFOutputStream crlfoutputstream = new CRLFOutputStream(lengthcounter);
        message.writeTo(crlfoutputstream);
        crlfoutputstream.flush();
        msgSize = lengthcounter.getSize();
        buf = lengthcounter.getBytes();
    }

    public int size()
    {
        return msgSize;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        if(buf != null)
        {
            outputstream.write(buf, 0, msgSize);
            return;
        }
        CRLFOutputStream crlfoutputstream = new CRLFOutputStream(outputstream);
        MessagingException messagingexception;
        try
        {
            msg.writeTo(crlfoutputstream);
            return;
        }
        // Misplaced declaration of an exception variable
        catch(MessagingException messagingexception) { }
        break MISSING_BLOCK_LABEL_40;
        messagingexception;
        throw new IOException((new StringBuilder("MessagingException while appending message: ")).append(messagingexception).toString());
    }

    private byte buf[];
    private Message msg;
    private int msgSize;
}
