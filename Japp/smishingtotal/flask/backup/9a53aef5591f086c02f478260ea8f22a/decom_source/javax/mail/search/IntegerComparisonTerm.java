// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;


// Referenced classes of package javax.mail.search:
//            ComparisonTerm

public abstract class IntegerComparisonTerm extends ComparisonTerm
{

    protected IntegerComparisonTerm(int i, int j)
    {
        comparison = i;
        number = j;
    }

    public boolean equals(Object obj)
    {
        while(!(obj instanceof IntegerComparisonTerm) || ((IntegerComparisonTerm)obj).number != number || !super.equals(obj)) 
            return false;
        return true;
    }

    public int getComparison()
    {
        return comparison;
    }

    public int getNumber()
    {
        return number;
    }

    public int hashCode()
    {
        return number + super.hashCode();
    }

    protected boolean match(int i)
    {
        boolean flag = true;
        comparison;
        JVM INSTR tableswitch 1 6: default 44
    //                   1 48
    //                   2 58
    //                   3 68
    //                   4 78
    //                   5 88
    //                   6 98;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        flag = false;
_L9:
        return flag;
_L2:
        if(i > number)
            return false;
        continue; /* Loop/switch isn't completed */
_L3:
        if(i >= number)
            return false;
        continue; /* Loop/switch isn't completed */
_L4:
        if(i != number)
            return false;
        continue; /* Loop/switch isn't completed */
_L5:
        if(i == number)
            return false;
        continue; /* Loop/switch isn't completed */
_L6:
        if(i <= number)
            return false;
        continue; /* Loop/switch isn't completed */
_L7:
        if(i < number)
            return false;
        if(true) goto _L9; else goto _L8
_L8:
    }

    private static final long serialVersionUID = 0x9f5c6cda0679f7ecL;
    protected int number;
}
