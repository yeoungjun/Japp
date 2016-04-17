// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import javax.mail.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPMessage

public class IMAPNestedMessage extends IMAPMessage
{

    IMAPNestedMessage(IMAPMessage imapmessage, BODYSTRUCTURE bodystructure, ENVELOPE envelope, String s)
    {
        super(imapmessage._getSession());
        msg = imapmessage;
        bs = bodystructure;
        this.envelope = envelope;
        sectionId = s;
    }

    protected void checkExpunged()
        throws MessageRemovedException
    {
        msg.checkExpunged();
    }

    protected int getFetchBlockSize()
    {
        return msg.getFetchBlockSize();
    }

    protected Object getMessageCacheLock()
    {
        return msg.getMessageCacheLock();
    }

    protected IMAPProtocol getProtocol()
        throws ProtocolException, FolderClosedException
    {
        return msg.getProtocol();
    }

    protected int getSequenceNumber()
    {
        return msg.getSequenceNumber();
    }

    public int getSize()
        throws MessagingException
    {
        return bs.size;
    }

    public boolean isExpunged()
    {
        return msg.isExpunged();
    }

    protected boolean isREV1()
        throws FolderClosedException
    {
        return msg.isREV1();
    }

    public void setFlags(Flags flags, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        throw new MethodNotSupportedException("Cannot set flags on this nested message");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private IMAPMessage msg;
}
