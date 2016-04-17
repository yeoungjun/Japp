// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            IntegerComparisonTerm

public final class SizeTerm extends IntegerComparisonTerm
{

    public SizeTerm(int i, int j)
    {
        super(i, j);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof SizeTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        int i;
        try
        {
            i = message.getSize();
        }
        catch(Exception exception)
        {
            return false;
        }
        if(i == -1)
            return false;
        else
            return super.match(i);
    }

    private static final long serialVersionUID = 0xdc867bf3e6e591a3L;
}
