// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import java.util.List;
import org.apache.http.*;

// Referenced classes of package org.apache.http.protocol:
//            HttpProcessor, HttpRequestInterceptorList, HttpResponseInterceptorList, HttpContext

public final class ImmutableHttpProcessor
    implements HttpProcessor
{

    public ImmutableHttpProcessor(List list, List list1)
    {
        if(list != null)
            requestInterceptors = (HttpRequestInterceptor[])list.toArray(new HttpRequestInterceptor[list.size()]);
        else
            requestInterceptors = new HttpRequestInterceptor[0];
        if(list1 != null)
        {
            responseInterceptors = (HttpResponseInterceptor[])list1.toArray(new HttpResponseInterceptor[list1.size()]);
            return;
        } else
        {
            responseInterceptors = new HttpResponseInterceptor[0];
            return;
        }
    }

    public ImmutableHttpProcessor(HttpRequestInterceptorList httprequestinterceptorlist, HttpResponseInterceptorList httpresponseinterceptorlist)
    {
        if(httprequestinterceptorlist != null)
        {
            int k = httprequestinterceptorlist.getRequestInterceptorCount();
            requestInterceptors = new HttpRequestInterceptor[k];
            for(int l = 0; l < k; l++)
                requestInterceptors[l] = httprequestinterceptorlist.getRequestInterceptor(l);

        } else
        {
            requestInterceptors = new HttpRequestInterceptor[0];
        }
        if(httpresponseinterceptorlist != null)
        {
            int i = httpresponseinterceptorlist.getResponseInterceptorCount();
            responseInterceptors = new HttpResponseInterceptor[i];
            for(int j = 0; j < i; j++)
                responseInterceptors[j] = httpresponseinterceptorlist.getResponseInterceptor(j);

        } else
        {
            responseInterceptors = new HttpResponseInterceptor[0];
        }
    }

    public transient ImmutableHttpProcessor(HttpRequestInterceptor ahttprequestinterceptor[])
    {
        this(ahttprequestinterceptor, ((HttpResponseInterceptor []) (null)));
    }

    public ImmutableHttpProcessor(HttpRequestInterceptor ahttprequestinterceptor[], HttpResponseInterceptor ahttpresponseinterceptor[])
    {
        if(ahttprequestinterceptor != null)
        {
            int j = ahttprequestinterceptor.length;
            requestInterceptors = new HttpRequestInterceptor[j];
            System.arraycopy(ahttprequestinterceptor, 0, requestInterceptors, 0, j);
        } else
        {
            requestInterceptors = new HttpRequestInterceptor[0];
        }
        if(ahttpresponseinterceptor != null)
        {
            int i = ahttpresponseinterceptor.length;
            responseInterceptors = new HttpResponseInterceptor[i];
            System.arraycopy(ahttpresponseinterceptor, 0, responseInterceptors, 0, i);
            return;
        } else
        {
            responseInterceptors = new HttpResponseInterceptor[0];
            return;
        }
    }

    public transient ImmutableHttpProcessor(HttpResponseInterceptor ahttpresponseinterceptor[])
    {
        this(((HttpRequestInterceptor []) (null)), ahttpresponseinterceptor);
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, HttpException
    {
        HttpRequestInterceptor ahttprequestinterceptor[] = requestInterceptors;
        int i = ahttprequestinterceptor.length;
        for(int j = 0; j < i; j++)
            ahttprequestinterceptor[j].process(httprequest, httpcontext);

    }

    public void process(HttpResponse httpresponse, HttpContext httpcontext)
        throws IOException, HttpException
    {
        HttpResponseInterceptor ahttpresponseinterceptor[] = responseInterceptors;
        int i = ahttpresponseinterceptor.length;
        for(int j = 0; j < i; j++)
            ahttpresponseinterceptor[j].process(httpresponse, httpcontext);

    }

    private final HttpRequestInterceptor requestInterceptors[];
    private final HttpResponseInterceptor responseInterceptors[];
}
