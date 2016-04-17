// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressTerm

public final class RecipientTerm extends AddressTerm
{

    public RecipientTerm(javax.mail.Message.RecipientType recipienttype, Address address)
    {
        super(address);
        type = recipienttype;
    }

    public boolean equals(Object obj)
    {
        while(!(obj instanceof RecipientTerm) || !((RecipientTerm)obj).type.equals(type) || !super.equals(obj)) 
            return false;
        return true;
    }

    public javax.mail.Message.RecipientType getRecipientType()
    {
        return type;
    }

    public int hashCode()
    {
        return type.hashCode() + super.hashCode();
    }

    public boolean match(Message message)
    {
        Address aaddress[];
        try
        {
            aaddress = message.getRecipients(type);
        }
        catch(Exception exception)
        {
            return false;
        }
        if(aaddress != null)
        {
            int i = 0;
            while(i < aaddress.length) 
            {
                if(super.match(aaddress[i]))
                    return true;
                i++;
            }
        }
        return false;
    }

    private static final long serialVersionUID = 0x5ae1a88c29bef694L;
    protected javax.mail.Message.RecipientType type;
}
