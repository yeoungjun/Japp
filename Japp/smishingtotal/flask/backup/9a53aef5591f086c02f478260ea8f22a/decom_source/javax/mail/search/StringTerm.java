// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;


// Referenced classes of package javax.mail.search:
//            SearchTerm

public abstract class StringTerm extends SearchTerm
{

    protected StringTerm(String s)
    {
        pattern = s;
        ignoreCase = true;
    }

    protected StringTerm(String s, boolean flag)
    {
        pattern = s;
        ignoreCase = flag;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof StringTerm) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        StringTerm stringterm;
        stringterm = (StringTerm)obj;
        if(!ignoreCase)
            continue; /* Loop/switch isn't completed */
        if(!stringterm.pattern.equalsIgnoreCase(pattern) || stringterm.ignoreCase != ignoreCase) goto _L1; else goto _L3
_L3:
        return true;
        if(!stringterm.pattern.equals(pattern) || stringterm.ignoreCase != ignoreCase) goto _L1; else goto _L4
_L4:
        return true;
    }

    public boolean getIgnoreCase()
    {
        return ignoreCase;
    }

    public String getPattern()
    {
        return pattern;
    }

    public int hashCode()
    {
        if(ignoreCase)
            return pattern.hashCode();
        else
            return -1 ^ pattern.hashCode();
    }

    protected boolean match(String s)
    {
        int i = s.length() - pattern.length();
        int j = 0;
        do
        {
            if(j > i)
                return false;
            if(s.regionMatches(ignoreCase, j, pattern, 0, pattern.length()))
                return true;
            j++;
        } while(true);
    }

    private static final long serialVersionUID = 0x11ae4e90f062d98dL;
    protected boolean ignoreCase;
    protected String pattern;
}
