// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressStringTerm

public final class RecipientStringTerm extends AddressStringTerm
{

    public RecipientStringTerm(javax.mail.Message.RecipientType recipienttype, String s)
    {
        super(s);
        type = recipienttype;
    }

    public boolean equals(Object obj)
    {
        while(!(obj instanceof RecipientStringTerm) || !((RecipientStringTerm)obj).type.equals(type) || !super.equals(obj)) 
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
        javax.mail.Address aaddress[];
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

    private static final long serialVersionUID = 0x8ce759387e0d95dfL;
    private javax.mail.Message.RecipientType type;
}
