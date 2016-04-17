// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import java.util.Date;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            DateTerm

public final class SentDateTerm extends DateTerm
{

    public SentDateTerm(int i, Date date)
    {
        super(i, date);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof SentDateTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        Date date;
        try
        {
            date = message.getSentDate();
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

    private static final long serialVersionUID = 0x4e60db46f5385c7fL;
}
