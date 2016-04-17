// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.util.*;

// Referenced classes of package org.apache.commons.lang3:
//            Validate

public class EnumUtils
{

    public EnumUtils()
    {
    }

    private static Class checkBitVectorable(Class class1)
    {
        Validate.notNull(class1, "EnumClass must be defined.", new Object[0]);
        Enum aenum[] = (Enum[])class1.getEnumConstants();
        boolean flag;
        boolean flag1;
        Object aobj[];
        if(aenum != null)
            flag = true;
        else
            flag = false;
        Validate.isTrue(flag, "%s does not seem to be an Enum type", new Object[] {
            class1
        });
        if(aenum.length <= 64)
            flag1 = true;
        else
            flag1 = false;
        aobj = new Object[3];
        aobj[0] = Integer.valueOf(aenum.length);
        aobj[1] = class1.getSimpleName();
        aobj[2] = Integer.valueOf(64);
        Validate.isTrue(flag1, "Cannot store %s %s values in %s bits", aobj);
        return class1;
    }

    public static long generateBitVector(Class class1, Iterable iterable)
    {
        checkBitVectorable(class1);
        Validate.notNull(iterable);
        long l = 0L;
        for(Iterator iterator = iterable.iterator(); iterator.hasNext();)
            l |= 1 << ((Enum)iterator.next()).ordinal();

        return l;
    }

    public static transient long generateBitVector(Class class1, Enum aenum[])
    {
        Validate.noNullElements(aenum);
        return generateBitVector(class1, ((Iterable) (Arrays.asList(aenum))));
    }

    public static Enum getEnum(Class class1, String s)
    {
        if(s == null)
            return null;
        Enum enum;
        try
        {
            enum = Enum.valueOf(class1, s);
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            return null;
        }
        return enum;
    }

    public static List getEnumList(Class class1)
    {
        return new ArrayList(Arrays.asList(class1.getEnumConstants()));
    }

    public static Map getEnumMap(Class class1)
    {
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        Enum aenum[] = (Enum[])class1.getEnumConstants();
        int i = aenum.length;
        for(int j = 0; j < i; j++)
        {
            Enum enum = aenum[j];
            linkedhashmap.put(enum.name(), enum);
        }

        return linkedhashmap;
    }

    public static boolean isValidEnum(Class class1, String s)
    {
        if(s == null)
            return false;
        try
        {
            Enum.valueOf(class1, s);
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            return false;
        }
        return true;
    }

    public static EnumSet processBitVector(Class class1, long l)
    {
        Enum aenum[] = (Enum[])checkBitVectorable(class1).getEnumConstants();
        EnumSet enumset = EnumSet.noneOf(class1);
        int i = aenum.length;
        for(int j = 0; j < i; j++)
        {
            Enum enum = aenum[j];
            if((l & (long)(1 << enum.ordinal())) != 0L)
                enumset.add(enum);
        }

        return enumset;
    }
}
