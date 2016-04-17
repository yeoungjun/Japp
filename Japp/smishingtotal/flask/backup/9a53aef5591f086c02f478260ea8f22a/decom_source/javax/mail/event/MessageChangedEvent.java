// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import javax.mail.Message;

// Referenced classes of package javax.mail.event:
//            MailEvent, MessageChangedListener

public class MessageChangedEvent extends MailEvent
{

    public MessageChangedEvent(Object obj, int i, Message message)
    {
        super(obj);
        msg = message;
        type = i;
    }

    public void dispatch(Object obj)
    {
        ((MessageChangedListener)obj).messageChanged(this);
    }

    public Message getMessage()
    {
        return msg;
    }

    public int getMessageChangeType()
    {
        return type;
    }

    public static final int ENVELOPE_CHANGED = 2;
    public static final int FLAGS_CHANGED = 1;
    private static final long serialVersionUID = 0xbaf55870be7af17cL;
    protected transient Message msg;
    protected int type;
}
