// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import java.util.Date;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            DateTerm

public final class ReceivedDateTerm extends DateTerm
{

    public ReceivedDateTerm(int i, Date date)
    {
        super(i, date);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof ReceivedDateTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        Date date;
        try
        {
            date = message.getReceivedDate();
        }
        catch(Exception exception)
        {
            return false;
        }
        if(date == null)
            return false;
        else
            return super.match(date);
    }

    private static final long serialVersionUID = 0xd9be404778824fbeL;
}
