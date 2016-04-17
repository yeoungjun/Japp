// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class SubjectTerm extends StringTerm
{

    public SubjectTerm(String s)
    {
        super(s);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof SubjectTerm))
            return false;
        else
            return super.equals(obj);
    }

    public boolean match(Message message)
    {
        String s;
        try
        {
            s = message.getSubject();
        }
        catch(Exception exception)
        {
            return false;
        }
        if(s == null)
            return false;
        else
            return super.match(s);
    }

    private static final long serialVersionUID = 0x67d3df04e1e99fb8L;
}
