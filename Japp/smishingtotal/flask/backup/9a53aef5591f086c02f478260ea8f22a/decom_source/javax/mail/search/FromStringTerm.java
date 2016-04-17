// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressStringTerm

public final class FromStringTerm extends AddressStringTerm
{

    public FromStringTerm(String s)
    {
        super(s);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof FromStringTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        javax.mail.Address aaddress[];
        try
        {
            aaddress = message.getFrom();
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

    private static final long serialVersionUID = 0x5081bebf4a6fab34L;
}
