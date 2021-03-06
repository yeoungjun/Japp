// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public final class AndTerm extends SearchTerm
{

    public AndTerm(SearchTerm searchterm, SearchTerm searchterm1)
    {
        terms = new SearchTerm[2];
        terms[0] = searchterm;
        terms[1] = searchterm1;
    }

    public AndTerm(SearchTerm asearchterm[])
    {
        terms = new SearchTerm[asearchterm.length];
        int i = 0;
        do
        {
            if(i >= asearchterm.length)
                return;
            terms[i] = asearchterm[i];
            i++;
        } while(true);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof AndTerm) goto _L2; else goto _L1
_L1:
        AndTerm andterm;
        return false;
_L2:
        if((andterm = (AndTerm)obj).terms.length != terms.length)
            continue;
        int i = 0;
        do
        {
            if(i >= terms.length)
                return true;
            if(!terms[i].equals(andterm.terms[i]))
                continue;
            i++;
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    public SearchTerm[] getTerms()
    {
        return (SearchTerm[])terms.clone();
    }

    public int hashCode()
    {
        int i = 0;
        int j = 0;
        do
        {
            if(j >= terms.length)
                return i;
            i += terms[j].hashCode();
            j++;
        } while(true);
    }

    public boolean match(Message message)
    {
        int i = 0;
        do
        {
            if(i >= terms.length)
                return true;
            if(!terms[i].match(message))
                return false;
            i++;
        } while(true);
    }

    private static final long serialVersionUID = 0xce45a6d2860d7572L;
    protected SearchTerm terms[];
}
