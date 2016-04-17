// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

// Referenced classes of package org.apache.http.impl.execchain:
//            ResponseEntityWrapper, ConnectionHolder

class ResponseProxyHandler
    implements InvocationHandler
{

    ResponseProxyHandler(HttpResponse httpresponse, ConnectionHolder connectionholder)
    {
        original = httpresponse;
        connHolder = connectionholder;
        HttpEntity httpentity = httpresponse.getEntity();
        if(httpentity != null && httpentity.isStreaming() && connectionholder != null)
            original.setEntity(new ResponseEntityWrapper(httpentity, connectionholder));
    }

    public void close()
        throws IOException
    {
        if(connHolder != null)
            connHolder.abortConnection();
    }

    public Object invoke(Object obj, Method method, Object aobj[])
        throws Throwable
    {
        if(method.equals(CLOSE_METHOD))
        {
            close();
            return null;
        }
        Object obj1;
        try
        {
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

    private static final Method CLOSE_METHOD;
    private final ConnectionHolder connHolder;
    private final HttpResponse original;

    static 
    {
        try
        {
            CLOSE_METHOD = java/io/Closeable.getMethod("close", new Class[0]);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new Error(nosuchmethodexception);
        }
    }
}
