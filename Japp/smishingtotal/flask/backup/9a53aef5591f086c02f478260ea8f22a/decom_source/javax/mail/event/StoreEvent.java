// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import javax.mail.Store;

// Referenced classes of package javax.mail.event:
//            MailEvent, StoreListener

public class StoreEvent extends MailEvent
{

    public StoreEvent(Store store, int i, String s)
    {
        super(store);
        type = i;
        message = s;
    }

    public void dispatch(Object obj)
    {
        ((StoreListener)obj).notification(this);
    }

    public String getMessage()
    {
        return message;
    }

    public int getMessageType()
    {
        return type;
    }

    public static final int ALERT = 1;
    public static final int NOTICE = 2;
    private static final long serialVersionUID = 0x1ae7a9da6074bb02L;
    protected String message;
    protected int type;
}
