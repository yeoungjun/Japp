// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.apache.http.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class RequestDefaultHeaders
    implements HttpRequestInterceptor
{

    public RequestDefaultHeaders()
    {
        this(null);
    }

    public RequestDefaultHeaders(Collection collection)
    {
        defaultHeaders = collection;
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        if(!httprequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Collection collection = (Collection)httprequest.getParams().getParameter("http.default-headers");
        if(collection == null)
            collection = defaultHeaders;
        if(collection != null)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) 
                httprequest.addHeader((Header)iterator.next());
        }
        if(true) goto _L1; else goto _L3
_L3:
    }

    private final Collection defaultHeaders;
}
