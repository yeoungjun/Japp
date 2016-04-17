// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

// Referenced classes of package javax.mail.search:
//            StringTerm

public abstract class AddressStringTerm extends StringTerm
{

    protected AddressStringTerm(String s)
    {
        super(s, true);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof AddressStringTerm))
            return false;
        else
            return super.equals(obj);
    }

    protected boolean match(Address address)
    {
        if(address instanceof InternetAddress)
            return super.match(((InternetAddress)address).toUnicodeString());
        else
            return super.match(address.toString());
    }

    private static final long serialVersionUID = 0x2ad6978ecdebb490L;
}
