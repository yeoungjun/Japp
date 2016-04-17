// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;


// Referenced classes of package javax.mail.search:
//            SearchTerm

public abstract class ComparisonTerm extends SearchTerm
{

    public ComparisonTerm()
    {
    }

    public boolean equals(Object obj)
    {
        while(!(obj instanceof ComparisonTerm) || ((ComparisonTerm)obj).comparison != comparison) 
            return false;
        return true;
    }

    public int hashCode()
    {
        return comparison;
    }

    public static final int EQ = 3;
    public static final int GE = 6;
    public static final int GT = 5;
    public static final int LE = 1;
    public static final int LT = 2;
    public static final int NE = 4;
    private static final long serialVersionUID = 0x14370cafcc71f144L;
    protected int comparison;
}
