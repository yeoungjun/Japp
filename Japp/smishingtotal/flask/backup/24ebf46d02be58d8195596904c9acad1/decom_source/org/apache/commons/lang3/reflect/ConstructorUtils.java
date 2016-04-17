// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.reflect;

import java.lang.reflect.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

// Referenced classes of package org.apache.commons.lang3.reflect:
//            MemberUtils

public class ConstructorUtils
{

    public ConstructorUtils()
    {
    }

    public static transient Constructor getAccessibleConstructor(Class class1, Class aclass[])
    {
        Constructor constructor;
        try
        {
            constructor = getAccessibleConstructor(class1.getConstructor(aclass));
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            return null;
        }
        return constructor;
    }

    public static Constructor getAccessibleConstructor(Constructor constructor)
    {
        if(MemberUtils.isAccessible(constructor) && Modifier.isPublic(constructor.getDeclaringClass().getModifiers()))
            return constructor;
        else
            return null;
    }

    public static transient Constructor getMatchingAccessibleConstructor(Class class1, Class aclass[])
    {
        Constructor constructor3;
        try
        {
            constructor3 = class1.getConstructor(aclass);
            MemberUtils.setAccessibleWorkaround(constructor3);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            Constructor constructor = null;
            Constructor aconstructor[] = class1.getConstructors();
            int i = aconstructor.length;
            for(int j = 0; j < i; j++)
            {
                Constructor constructor1 = aconstructor[j];
                if(!ClassUtils.isAssignable(aclass, constructor1.getParameterTypes(), true))
                    continue;
                Constructor constructor2 = getAccessibleConstructor(constructor1);
                if(constructor2 == null)
                    continue;
                MemberUtils.setAccessibleWorkaround(constructor2);
                if(constructor == null || MemberUtils.compareParameterTypes(constructor2.getParameterTypes(), constructor.getParameterTypes(), aclass) < 0)
                    constructor = constructor2;
            }

            return constructor;
        }
        return constructor3;
    }

    public static transient Object invokeConstructor(Class class1, Object aobj[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        Class aclass[] = new Class[aobj.length];
        for(int i = 0; i < aobj.length; i++)
            aclass[i] = aobj[i].getClass();

        return invokeConstructor(class1, aobj, aclass);
    }

    public static Object invokeConstructor(Class class1, Object aobj[], Class aclass[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        Constructor constructor = getMatchingAccessibleConstructor(class1, aclass);
        if(constructor == null)
            throw new NoSuchMethodException((new StringBuilder()).append("No such accessible constructor on object: ").append(class1.getName()).toString());
        else
            return constructor.newInstance(aobj);
    }

    public static transient Object invokeExactConstructor(Class class1, Object aobj[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        int i = aobj.length;
        Class aclass[] = new Class[i];
        for(int j = 0; j < i; j++)
            aclass[j] = aobj[j].getClass();

        return invokeExactConstructor(class1, aobj, aclass);
    }

    public static Object invokeExactConstructor(Class class1, Object aobj[], Class aclass[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        Constructor constructor = getAccessibleConstructor(class1, aclass);
        if(constructor == null)
            throw new NoSuchMethodException((new StringBuilder()).append("No such accessible constructor on object: ").append(class1.getName()).toString());
        else
            return constructor.newInstance(aobj);
    }
}
