// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.IMAPProtocol;
import javax.mail.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPFolder, IMAPStore

public class DefaultFolder extends IMAPFolder
{

    protected DefaultFolder(IMAPStore imapstore)
    {
        super("", '\uFFFF', imapstore);
        exists = true;
        type = 2;
    }

    public void appendMessages(Message amessage[])
        throws MessagingException
    {
        throw new MethodNotSupportedException("Cannot append to Default Folder");
    }

    public boolean delete(boolean flag)
        throws MessagingException
    {
        throw new MethodNotSupportedException("Cannot delete Default Folder");
    }

    public Message[] expunge()
        throws MessagingException
    {
        throw new MethodNotSupportedException("Cannot expunge Default Folder");
    }

    public Folder getFolder(String s)
        throws MessagingException
    {
        return new IMAPFolder(s, '\uFFFF', (IMAPStore)store);
    }

    public String getName()
    {
        return fullName;
    }

    public Folder getParent()
    {
        return null;
    }

    public boolean hasNewMessages()
        throws MessagingException
    {
        return false;
    }

    public Folder[] list(final String pattern)
        throws MessagingException
    {
        com.sun.mail.imap.protocol.ListInfo[] _tmp = (com.sun.mail.imap.protocol.ListInfo[])null;
        com.sun.mail.imap.protocol.ListInfo alistinfo[] = (com.sun.mail.imap.protocol.ListInfo[])doCommand(new IMAPFolder.ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.list("", pattern);
            }

            final DefaultFolder this$0;
            private final String val$pattern;

            
            {
                this$0 = DefaultFolder.this;
                pattern = s;
                super();
            }
        });
        Object aobj[];
        if(alistinfo == null)
        {
            aobj = new Folder[0];
        } else
        {
            aobj = new IMAPFolder[alistinfo.length];
            int i = 0;
            while(i < aobj.length) 
            {
                aobj[i] = new IMAPFolder(alistinfo[i], (IMAPStore)store);
                i++;
            }
        }
        return ((Folder []) (aobj));
    }

    public Folder[] listSubscribed(final String pattern)
        throws MessagingException
    {
        com.sun.mail.imap.protocol.ListInfo[] _tmp = (com.sun.mail.imap.protocol.ListInfo[])null;
        com.sun.mail.imap.protocol.ListInfo alistinfo[] = (com.sun.mail.imap.protocol.ListInfo[])doCommand(new IMAPFolder.ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.lsub("", pattern);
            }

            final DefaultFolder this$0;
            private final String val$pattern;

            
            {
                this$0 = DefaultFolder.this;
                pattern = s;
                super();
            }
        });
        Object aobj[];
        if(alistinfo == null)
        {
            aobj = new Folder[0];
        } else
        {
            aobj = new IMAPFolder[alistinfo.length];
            int i = 0;
            while(i < aobj.length) 
            {
                aobj[i] = new IMAPFolder(alistinfo[i], (IMAPStore)store);
                i++;
            }
        }
        return ((Folder []) (aobj));
    }

    public boolean renameTo(Folder folder)
        throws MessagingException
    {
        throw new MethodNotSupportedException("Cannot rename Default Folder");
    }
}
