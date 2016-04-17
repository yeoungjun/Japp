// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            IntegerComparisonTerm

public final class MessageNumberTerm extends IntegerComparisonTerm
{

    public MessageNumberTerm(int i)
    {
        super(3, i);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof MessageNumberTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        int i;
        try
        {
            i = message.getMessageNumber();
        }
        catch(Exception exception)
        {
            return false;
        }
        return super.match(i);
    }

    private static final long serialVersionUID = 0xb557bacf76ae80bcL;
}
