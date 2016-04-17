// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// Referenced classes of package org.apache.http.client.utils:
//            Idn

public class JdkIdn
    implements Idn
{

    public JdkIdn()
        throws ClassNotFoundException
    {
        Class class1 = Class.forName("java.net.IDN");
        try
        {
            toUnicode = class1.getMethod("toUnicode", new Class[] {
                java/lang/String
            });
            return;
        }
        catch(SecurityException securityexception)
        {
            throw new IllegalStateException(securityexception.getMessage(), securityexception);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new IllegalStateException(nosuchmethodexception.getMessage(), nosuchmethodexception);
        }
    }

    public String toUnicode(String s)
    {
        String s1;
        try
        {
            s1 = (String)toUnicode.invoke(null, new Object[] {
                s
            });
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new IllegalStateException(illegalaccessexception.getMessage(), illegalaccessexception);
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            Throwable throwable = invocationtargetexception.getCause();
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
        return s1;
    }

    private final Method toUnicode;
}
