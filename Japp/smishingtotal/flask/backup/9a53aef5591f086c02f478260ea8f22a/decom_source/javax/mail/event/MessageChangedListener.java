// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import java.util.EventListener;

// Referenced classes of package javax.mail.event:
//            MessageChangedEvent

public interface MessageChangedListener
    extends EventListener
{

    public abstract void messageChanged(MessageChangedEvent messagechangedevent);
}
