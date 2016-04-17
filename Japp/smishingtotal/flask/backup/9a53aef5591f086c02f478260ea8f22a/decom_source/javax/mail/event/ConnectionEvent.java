// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;


// Referenced classes of package javax.mail.event:
//            MailEvent, ConnectionListener

public class ConnectionEvent extends MailEvent
{

    public ConnectionEvent(Object obj, int i)
    {
        super(obj);
        type = i;
    }

    public void dispatch(Object obj)
    {
        if(type == 1)
        {
            ((ConnectionListener)obj).opened(this);
        } else
        {
            if(type == 2)
            {
                ((ConnectionListener)obj).disconnected(this);
                return;
            }
            if(type == 3)
            {
                ((ConnectionListener)obj).closed(this);
                return;
            }
        }
    }

    public int getType()
    {
        return type;
    }

    public static final int CLOSED = 3;
    public static final int DISCONNECTED = 2;
    public static final int OPENED = 1;
    private static final long serialVersionUID = 0xe640029d6ec9f983L;
    protected int type;
}
