// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import javax.mail.*;

// Referenced classes of package javax.mail.event:
//            MailEvent, TransportListener

public class TransportEvent extends MailEvent
{

    public TransportEvent(Transport transport, int i, Address aaddress[], Address aaddress1[], Address aaddress2[], Message message)
    {
        super(transport);
        type = i;
        validSent = aaddress;
        validUnsent = aaddress1;
        invalid = aaddress2;
        msg = message;
    }

    public void dispatch(Object obj)
    {
        if(type == 1)
        {
            ((TransportListener)obj).messageDelivered(this);
            return;
        }
        if(type == 2)
        {
            ((TransportListener)obj).messageNotDelivered(this);
            return;
        } else
        {
            ((TransportListener)obj).messagePartiallyDelivered(this);
            return;
        }
    }

    public Address[] getInvalidAddresses()
    {
        return invalid;
    }

    public Message getMessage()
    {
        return msg;
    }

    public int getType()
    {
        return type;
    }

    public Address[] getValidSentAddresses()
    {
        return validSent;
    }

    public Address[] getValidUnsentAddresses()
    {
        return validUnsent;
    }

    public static final int MESSAGE_DELIVERED = 1;
    public static final int MESSAGE_NOT_DELIVERED = 2;
    public static final int MESSAGE_PARTIALLY_DELIVERED = 3;
    private static final long serialVersionUID = 0xbe5c30558af3da4fL;
    protected transient Address invalid[];
    protected transient Message msg;
    protected int type;
    protected transient Address validSent[];
    protected transient Address validUnsent[];
}
