// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneUtils
{

    private CloneUtils()
    {
    }

    public static Object clone(Object obj)
        throws CloneNotSupportedException
    {
        return cloneObject(obj);
    }

    public static Object cloneObject(Object obj)
        throws CloneNotSupportedException
    {
        if(obj == null)
            return null;
        if(obj instanceof Cloneable)
        {
            Class class1 = obj.getClass();
            Method method;
            Object obj1;
            try
            {
                method = class1.getMethod("clone", (Class[])null);
            }
            catch(NoSuchMethodException nosuchmethodexception)
            {
                throw new NoSuchMethodError(nosuchmethodexception.getMessage());
            }
            try
            {
                obj1 = method.invoke(obj, (Object[])null);
            }
            catch(InvocationTargetException invocationtargetexception)
            {
                Throwable throwable = invocationtargetexception.getCause();
                if(throwable instanceof CloneNotSupportedException)
                    throw (CloneNotSupportedException)throwable;
                else
                    throw new Error("Unexpected exception", throwable);
            }
            catch(IllegalAccessException illegalaccessexception)
            {
                throw new IllegalAccessError(illegalaccessexception.getMessage());
            }
            return obj1;
        } else
        {
            throw new CloneNotSupportedException();
        }
    }
}
