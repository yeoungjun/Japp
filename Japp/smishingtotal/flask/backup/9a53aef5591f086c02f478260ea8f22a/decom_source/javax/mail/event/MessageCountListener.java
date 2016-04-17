// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import java.util.EventListener;

// Referenced classes of package javax.mail.event:
//            MessageCountEvent

public interface MessageCountListener
    extends EventListener
{

    public abstract void messagesAdded(MessageCountEvent messagecountevent);

    public abstract void messagesRemoved(MessageCountEvent messagecountevent);
}
