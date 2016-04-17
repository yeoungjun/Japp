// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.OutputStream;
import java.lang.reflect.*;
import org.apache.http.HttpEntity;

class RequestEntityExecHandler
    implements InvocationHandler
{

    RequestEntityExecHandler(HttpEntity httpentity)
    {
        consumed = false;
        original = httpentity;
    }

    public HttpEntity getOriginal()
    {
        return original;
    }

    public Object invoke(Object obj, Method method, Object aobj[])
        throws Throwable
    {
        Object obj1;
        try
        {
            if(method.equals(WRITE_TO_METHOD))
                consumed = true;
            obj1 = method.invoke(original, aobj);
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            Throwable throwable = invocationtargetexception.getCause();
            if(throwable != null)
                throw throwable;
            else
                throw invocationtargetexception;
        }
        return obj1;
    }

    public boolean isConsumed()
    {
        return consumed;
    }

    private static final Method WRITE_TO_METHOD;
    private boolean consumed;
    private final HttpEntity original;

    static 
    {
        try
        {
            WRITE_TO_METHOD = org/apache/http/HttpEntity.getMethod("writeTo", new Class[] {
                java/io/OutputStream
            });
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new Error(nosuchmethodexception);
        }
    }
}
