// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.reflect;

import java.lang.reflect.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

// Referenced classes of package org.apache.commons.lang3.reflect:
//            MemberUtils

public class MethodUtils
{

    public MethodUtils()
    {
    }

    public static transient Method getAccessibleMethod(Class class1, String s, Class aclass[])
    {
        Method method;
        try
        {
            method = getAccessibleMethod(class1.getMethod(s, aclass));
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            return null;
        }
        return method;
    }

    public static Method getAccessibleMethod(Method method)
    {
        if(!MemberUtils.isAccessible(method))
        {
            method = null;
        } else
        {
            Class class1 = method.getDeclaringClass();
            if(!Modifier.isPublic(class1.getModifiers()))
            {
                String s = method.getName();
                Class aclass[] = method.getParameterTypes();
                method = getAccessibleMethodFromInterfaceNest(class1, s, aclass);
                if(method == null)
                    return getAccessibleMethodFromSuperclass(class1, s, aclass);
            }
        }
        return method;
    }

    private static transient Method getAccessibleMethodFromInterfaceNest(Class class1, String s, Class aclass[])
    {
        Method method = null;
_L9:
        if(class1 == null) goto _L2; else goto _L1
_L1:
        Class aclass1[];
        int i;
        aclass1 = class1.getInterfaces();
        i = 0;
_L7:
        if(i >= aclass1.length) goto _L4; else goto _L3
_L3:
        if(Modifier.isPublic(aclass1[i].getModifiers())) goto _L6; else goto _L5
_L5:
        i++;
          goto _L7
_L6:
        Method method1 = aclass1[i].getDeclaredMethod(s, aclass);
        method = method1;
_L10:
        if(method == null) goto _L8; else goto _L4
_L4:
        class1 = class1.getSuperclass();
          goto _L9
_L8:
        method = getAccessibleMethodFromInterfaceNest(aclass1[i], s, aclass);
        if(method == null) goto _L5; else goto _L4
_L2:
        return method;
        NoSuchMethodException nosuchmethodexception;
        nosuchmethodexception;
          goto _L10
    }

    private static transient Method getAccessibleMethodFromSuperclass(Class class1, String s, Class aclass[])
    {
        Class class2 = class1.getSuperclass();
        do
        {
label0:
            {
                Method method = null;
                if(class2 != null)
                {
                    if(!Modifier.isPublic(class2.getModifiers()))
                        break label0;
                    Method method1;
                    try
                    {
                        method1 = class2.getMethod(s, aclass);
                    }
                    catch(NoSuchMethodException nosuchmethodexception)
                    {
                        return null;
                    }
                    method = method1;
                }
                return method;
            }
            class2 = class2.getSuperclass();
        } while(true);
    }

    public static transient Method getMatchingAccessibleMethod(Class class1, String s, Class aclass[])
    {
        Method method2;
        try
        {
            method2 = class1.getMethod(s, aclass);
            MemberUtils.setAccessibleWorkaround(method2);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            java.lang.reflect.AccessibleObject accessibleobject = null;
            Method amethod[] = class1.getMethods();
            int i = amethod.length;
            for(int j = 0; j < i; j++)
            {
                Method method = amethod[j];
                if(!method.getName().equals(s) || !ClassUtils.isAssignable(aclass, method.getParameterTypes(), true))
                    continue;
                Method method1 = getAccessibleMethod(method);
                if(method1 != null && (accessibleobject == null || MemberUtils.compareParameterTypes(method1.getParameterTypes(), accessibleobject.getParameterTypes(), aclass) < 0))
                    accessibleobject = method1;
            }

            if(accessibleobject != null)
                MemberUtils.setAccessibleWorkaround(accessibleobject);
            return accessibleobject;
        }
        return method2;
    }

    public static transient Object invokeExactMethod(Object obj, String s, Object aobj[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        int i = aobj.length;
        Class aclass[] = new Class[i];
        for(int j = 0; j < i; j++)
            aclass[j] = aobj[j].getClass();

        return invokeExactMethod(obj, s, aobj, aclass);
    }

    public static Object invokeExactMethod(Object obj, String s, Object aobj[], Class aclass[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        Method method = getAccessibleMethod(obj.getClass(), s, aclass);
        if(method == null)
            throw new NoSuchMethodException((new StringBuilder()).append("No such accessible method: ").append(s).append("() on object: ").append(obj.getClass().getName()).toString());
        else
            return method.invoke(obj, aobj);
    }

    public static transient Object invokeExactStaticMethod(Class class1, String s, Object aobj[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        int i = aobj.length;
        Class aclass[] = new Class[i];
        for(int j = 0; j < i; j++)
            aclass[j] = aobj[j].getClass();

        return invokeExactStaticMethod(class1, s, aobj, aclass);
    }

    public static Object invokeExactStaticMethod(Class class1, String s, Object aobj[], Class aclass[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        Method method = getAccessibleMethod(class1, s, aclass);
        if(method == null)
            throw new NoSuchMethodException((new StringBuilder()).append("No such accessible method: ").append(s).append("() on class: ").append(class1.getName()).toString());
        else
            return method.invoke(null, aobj);
    }

    public static transient Object invokeMethod(Object obj, String s, Object aobj[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        int i = aobj.length;
        Class aclass[] = new Class[i];
        for(int j = 0; j < i; j++)
            aclass[j] = aobj[j].getClass();

        return invokeMethod(obj, s, aobj, aclass);
    }

    public static Object invokeMethod(Object obj, String s, Object aobj[], Class aclass[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        Method method = getMatchingAccessibleMethod(obj.getClass(), s, aclass);
        if(method == null)
            throw new NoSuchMethodException((new StringBuilder()).append("No such accessible method: ").append(s).append("() on object: ").append(obj.getClass().getName()).toString());
        else
            return method.invoke(obj, aobj);
    }

    public static transient Object invokeStaticMethod(Class class1, String s, Object aobj[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        int i = aobj.length;
        Class aclass[] = new Class[i];
        for(int j = 0; j < i; j++)
            aclass[j] = aobj[j].getClass();

        return invokeStaticMethod(class1, s, aobj, aclass);
    }

    public static Object invokeStaticMethod(Class class1, String s, Object aobj[], Class aclass[])
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        if(aobj == null)
            aobj = ArrayUtils.EMPTY_OBJECT_ARRAY;
        Method method = getMatchingAccessibleMethod(class1, s, aclass);
        if(method == null)
            throw new NoSuchMethodException((new StringBuilder()).append("No such accessible method: ").append(s).append("() on class: ").append(class1.getName()).toString());
        else
            return method.invoke(null, aobj);
    }
}
