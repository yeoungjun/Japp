// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.search;

import java.util.Date;

// Referenced classes of package javax.mail.search:
//            ComparisonTerm

public abstract class DateTerm extends ComparisonTerm
{

    protected DateTerm(int i, Date date1)
    {
        comparison = i;
        date = date1;
    }

    public boolean equals(Object obj)
    {
        while(!(obj instanceof DateTerm) || !((DateTerm)obj).date.equals(date) || !super.equals(obj)) 
            return false;
        return true;
    }

    public int getComparison()
    {
        return comparison;
    }

    public Date getDate()
    {
        return new Date(date.getTime());
    }

    public int hashCode()
    {
        return date.hashCode() + super.hashCode();
    }

    protected boolean match(Date date1)
    {
        comparison;
        JVM INSTR tableswitch 1 6: default 44
    //                   1 46
    //                   2 70
    //                   3 79
    //                   4 88
    //                   5 101
    //                   6 110;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7
_L1:
        return false;
_L2:
        if(date1.before(date) || date1.equals(date))
            return true;
        continue; /* Loop/switch isn't completed */
_L3:
        return date1.before(date);
_L4:
        return date1.equals(date);
_L5:
        if(!date1.equals(date))
            return true;
        if(true) goto _L1; else goto _L6
_L6:
        return date1.after(date);
_L7:
        if(date1.after(date) || date1.equals(date))
            return true;
        if(true) goto _L1; else goto _L8
_L8:
    }

    private static final long serialVersionUID = 0x42e013da6884266bL;
    protected Date date;
}
