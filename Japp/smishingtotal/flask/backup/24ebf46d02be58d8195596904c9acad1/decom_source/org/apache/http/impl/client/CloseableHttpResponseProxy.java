// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.lang.reflect.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

class CloseableHttpResponseProxy
    implements InvocationHandler
{

    CloseableHttpResponseProxy(HttpResponse httpresponse)
    {
        original = httpresponse;
    }

    public static CloseableHttpResponse newProxy(HttpResponse httpresponse)
    {
        return (CloseableHttpResponse)Proxy.newProxyInstance(org/apache/http/impl/client/CloseableHttpResponseProxy.getClassLoader(), new Class[] {
            org/apache/http/client/methods/CloseableHttpResponse
        }, new CloseableHttpResponseProxy(httpresponse));
    }

    public void close()
        throws IOException
    {
        EntityUtils.consume(original.getEntity());
    }

    public Object invoke(Object obj, Method method, Object aobj[])
        throws Throwable
    {
        if(method.getName().equals("close"))
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

    private final HttpResponse original;
}
