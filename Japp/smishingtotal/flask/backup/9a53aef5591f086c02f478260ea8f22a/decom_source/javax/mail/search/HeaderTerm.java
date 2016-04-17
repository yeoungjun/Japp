// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import java.util.Locale;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class HeaderTerm extends StringTerm
{

    public HeaderTerm(String s, String s1)
    {
        super(s1);
        headerName = s;
    }

    public boolean equals(Object obj)
    {
        HeaderTerm headerterm;
        if(obj instanceof HeaderTerm)
            if((headerterm = (HeaderTerm)obj).headerName.equalsIgnoreCase(headerName) && super.equals(headerterm))
                return true;
        return false;
    }

    public String getHeaderName()
    {
        return headerName;
    }

    public int hashCode()
    {
        return headerName.toLowerCase(Locale.ENGLISH).hashCode() + super.hashCode();
    }

    public boolean match(Message message)
    {
        String as[];
        try
        {
            as = message.getHeader(headerName);
        }
        catch(Exception exception)
        {
            return false;
        }
        if(as != null)
        {
            int i = 0;
            while(i < as.length) 
            {
                if(super.match(as[i]))
                    return true;
                i++;
            }
        }
        return false;
    }

    private static final long serialVersionUID = 0x73c690dfba9d2142L;
    protected String headerName;
}
