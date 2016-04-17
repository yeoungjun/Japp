// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import javax.mail.Folder;
import javax.mail.Message;

// Referenced classes of package javax.mail.event:
//            MailEvent, MessageCountListener

public class MessageCountEvent extends MailEvent
{

    public MessageCountEvent(Folder folder, int i, boolean flag, Message amessage[])
    {
        super(folder);
        type = i;
        removed = flag;
        msgs = amessage;
    }

    public void dispatch(Object obj)
    {
        if(type == 1)
        {
            ((MessageCountListener)obj).messagesAdded(this);
            return;
        } else
        {
            ((MessageCountListener)obj).messagesRemoved(this);
            return;
        }
    }

    public Message[] getMessages()
    {
        return msgs;
    }

    public int getType()
    {
        return type;
    }

    public boolean isRemoved()
    {
        return removed;
    }

    public static final int ADDED = 1;
    public static final int REMOVED = 2;
    private static final long serialVersionUID = 0x98a6dca313f58b67L;
    protected transient Message msgs[];
    protected boolean removed;
    protected int type;
}
