// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultSchemePortResolver

public class DefaultRoutePlanner
    implements HttpRoutePlanner
{

    public DefaultRoutePlanner(SchemePortResolver schemeportresolver)
    {
        if(schemeportresolver == null)
            schemeportresolver = DefaultSchemePortResolver.INSTANCE;
        schemePortResolver = schemeportresolver;
    }

    protected HttpHost determineProxy(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        return null;
    }

    public HttpRoute determineRoute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        Args.notNull(httphost, "Target host");
        Args.notNull(httprequest, "Request");
        RequestConfig requestconfig = HttpClientContext.adapt(httpcontext).getRequestConfig();
        java.net.InetAddress inetaddress = requestconfig.getLocalAddress();
        HttpHost httphost1 = requestconfig.getProxy();
        if(httphost1 == null)
            httphost1 = determineProxy(httphost, httprequest, httpcontext);
        HttpHost httphost2;
        boolean flag;
        if(httphost.getPort() <= 0)
            try
            {
                httphost2 = new HttpHost(httphost.getHostName(), schemePortResolver.resolve(httphost), httphost.getSchemeName());
            }
            catch(UnsupportedSchemeException unsupportedschemeexception)
            {
                throw new HttpException(unsupportedschemeexception.getMessage());
            }
        else
            httphost2 = httphost;
        flag = httphost2.getSchemeName().equalsIgnoreCase("https");
        if(httphost1 == null)
            return new HttpRoute(httphost2, inetaddress, flag);
        else
            return new HttpRoute(httphost2, inetaddress, httphost1, flag);
    }

    private final SchemePortResolver schemePortResolver;
}
