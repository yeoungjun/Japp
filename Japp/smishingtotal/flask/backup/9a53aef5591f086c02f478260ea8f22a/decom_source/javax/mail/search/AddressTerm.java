// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Address;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public abstract class AddressTerm extends SearchTerm
{

    protected AddressTerm(Address address1)
    {
        address = address1;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof AddressTerm))
            return false;
        else
            return ((AddressTerm)obj).address.equals(address);
    }

    public Address getAddress()
    {
        return address;
    }

    public int hashCode()
    {
        return address.hashCode();
    }

    protected boolean match(Address address1)
    {
        return address1.equals(address);
    }

    private static final long serialVersionUID = 0x1bd4a1b9715ebffcL;
    protected Address address;
}
