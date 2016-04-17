// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.lang.reflect.Proxy;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;

// Referenced classes of package org.apache.http.impl.execchain:
//            RequestEntityExecHandler, ResponseProxyHandler, ConnectionHolder

class Proxies
{

    Proxies()
    {
    }

    static void enhanceEntity(HttpEntityEnclosingRequest httpentityenclosingrequest)
    {
        HttpEntity httpentity = httpentityenclosingrequest.getEntity();
        if(httpentity != null && !httpentity.isRepeatable() && !isEnhanced(httpentity))
            httpentityenclosingrequest.setEntity((HttpEntity)Proxy.newProxyInstance(org/apache/http/HttpEntity.getClassLoader(), new Class[] {
                org/apache/http/HttpEntity
            }, new RequestEntityExecHandler(httpentity)));
    }

    public static CloseableHttpResponse enhanceResponse(HttpResponse httpresponse, ConnectionHolder connectionholder)
    {
        return (CloseableHttpResponse)Proxy.newProxyInstance(org/apache/http/impl/execchain/ResponseProxyHandler.getClassLoader(), new Class[] {
            org/apache/http/client/methods/CloseableHttpResponse
        }, new ResponseProxyHandler(httpresponse, connectionholder));
    }

    static boolean isEnhanced(HttpEntity httpentity)
    {
        if(httpentity != null && Proxy.isProxyClass(httpentity.getClass()))
            return Proxy.getInvocationHandler(httpentity) instanceof RequestEntityExecHandler;
        else
            return false;
    }

    static boolean isRepeatable(HttpRequest httprequest)
    {
        HttpEntity httpentity;
label0:
        {
            if(httprequest instanceof HttpEntityEnclosingRequest)
            {
                httpentity = ((HttpEntityEnclosingRequest)httprequest).getEntity();
                if(httpentity != null && (!isEnhanced(httpentity) || ((RequestEntityExecHandler)Proxy.getInvocationHandler(httpentity)).isConsumed()))
                    break label0;
            }
            return true;
        }
        return httpentity.isRepeatable();
    }
}
