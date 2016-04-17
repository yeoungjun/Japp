// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public final class NotTerm extends SearchTerm
{

    public NotTerm(SearchTerm searchterm)
    {
        term = searchterm;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof NotTerm))
            return false;
        else
            return ((NotTerm)obj).term.equals(term);
    }

    public SearchTerm getTerm()
    {
        return term;
    }

    public int hashCode()
    {
        return term.hashCode() << 1;
    }

    public boolean match(Message message)
    {
        return !term.match(message);
    }

    private static final long serialVersionUID = 0x63420cc8aadc1008L;
    protected SearchTerm term;
}
