// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class MessageIDTerm extends StringTerm
{

    public MessageIDTerm(String s)
    {
        super(s);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof MessageIDTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        String as[];
        try
        {
            as = message.getHeader("Message-ID");
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

    private static final long serialVersionUID = 0xe2905a280b6be385L;
}
