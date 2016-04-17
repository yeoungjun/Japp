// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.mutable;


// Referenced classes of package org.apache.commons.lang3.mutable:
//            Mutable

public class MutableInt extends Number
    implements Comparable, Mutable
{

    public MutableInt()
    {
    }

    public MutableInt(int i)
    {
        value = i;
    }

    public MutableInt(Number number)
    {
        value = number.intValue();
    }

    public MutableInt(String s)
        throws NumberFormatException
    {
        value = Integer.parseInt(s);
    }

    public void add(int i)
    {
        value = i + value;
    }

    public void add(Number number)
    {
        value = value + number.intValue();
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((MutableInt)obj);
    }

    public int compareTo(MutableInt mutableint)
    {
        int i = mutableint.value;
        if(value < i)
            return -1;
        return value != i ? 1 : 0;
    }

    public void decrement()
    {
        value = -1 + value;
    }

    public double doubleValue()
    {
        return (double)value;
    }

    public boolean equals(Object obj)
    {
        boolean flag = obj instanceof MutableInt;
        boolean flag1 = false;
        if(flag)
        {
            int i = value;
            int j = ((MutableInt)obj).intValue();
            flag1 = false;
            if(i == j)
                flag1 = true;
        }
        return flag1;
    }

    public float floatValue()
    {
        return (float)value;
    }

    public Integer getValue()
    {
        return Integer.valueOf(value);
    }

    public volatile Object getValue()
    {
        return getValue();
    }

    public int hashCode()
    {
        return value;
    }

    public void increment()
    {
        value = 1 + value;
    }

    public int intValue()
    {
        return value;
    }

    public long longValue()
    {
        return (long)value;
    }

    public void setValue(int i)
    {
        value = i;
    }

    public void setValue(Number number)
    {
        value = number.intValue();
    }

    public volatile void setValue(Object obj)
    {
        setValue((Number)obj);
    }

    public void subtract(int i)
    {
        value = value - i;
    }

    public void subtract(Number number)
    {
        value = value - number.intValue();
    }

    public Integer toInteger()
    {
        return Integer.valueOf(intValue());
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    private static final long serialVersionUID = 0x77401786b8L;
    private int value;
}
