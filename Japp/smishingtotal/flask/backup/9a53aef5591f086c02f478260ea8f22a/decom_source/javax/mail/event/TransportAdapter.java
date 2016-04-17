// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;


// Referenced classes of package javax.mail.event:
//            TransportListener, TransportEvent

public abstract class TransportAdapter
    implements TransportListener
{

    public TransportAdapter()
    {
    }

    public void messageDelivered(TransportEvent transportevent)
    {
    }

    public void messageNotDelivered(TransportEvent transportevent)
    {
    }

    public void messagePartiallyDelivered(TransportEvent transportevent)
    {
    }
}
