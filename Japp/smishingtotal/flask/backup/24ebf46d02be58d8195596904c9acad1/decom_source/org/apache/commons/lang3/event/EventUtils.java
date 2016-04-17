// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.event;

import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang3.reflect.MethodUtils;

public class EventUtils
{
    private static class EventBindingInvocationHandler
        implements InvocationHandler
    {

        private boolean hasMatchingParametersMethod(Method method)
        {
            return MethodUtils.getAccessibleMethod(target.getClass(), methodName, method.getParameterTypes()) != null;
        }

        public Object invoke(Object obj, Method method, Object aobj[])
            throws Throwable
        {
            if(eventTypes.isEmpty() || eventTypes.contains(method.getName()))
            {
                if(hasMatchingParametersMethod(method))
                    return MethodUtils.invokeMethod(target, methodName, aobj);
                else
                    return MethodUtils.invokeMethod(target, methodName, new Object[0]);
            } else
            {
                return null;
            }
        }

        private final Set eventTypes;
        private final String methodName;
        private final Object target;

        EventBindingInvocationHandler(Object obj, String s, String as[])
        {
            target = obj;
            methodName = s;
            eventTypes = new HashSet(Arrays.asList(as));
        }
    }


    public EventUtils()
    {
    }

    public static void addEventListener(Object obj, Class class1, Object obj1)
    {
        try
        {
            MethodUtils.invokeMethod(obj, (new StringBuilder()).append("add").append(class1.getSimpleName()).toString(), new Object[] {
                obj1
            });
            return;
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Class ").append(obj.getClass().getName()).append(" does not have a public add").append(class1.getSimpleName()).append(" method which takes a parameter of type ").append(class1.getName()).append(".").toString());
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Class ").append(obj.getClass().getName()).append(" does not have an accessible add").append(class1.getSimpleName()).append(" method which takes a parameter of type ").append(class1.getName()).append(".").toString());
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            throw new RuntimeException("Unable to add listener.", invocationtargetexception.getCause());
        }
    }

    public static transient void bindEventsToMethod(Object obj, String s, Object obj1, Class class1, String as[])
    {
        addEventListener(obj1, class1, class1.cast(Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] {
            class1
        }, new EventBindingInvocationHandler(obj, s, as))));
    }
}
