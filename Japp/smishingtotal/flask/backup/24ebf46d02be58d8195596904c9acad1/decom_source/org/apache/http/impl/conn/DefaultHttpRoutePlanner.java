// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import org.apache.http.*;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

public class DefaultHttpRoutePlanner
    implements HttpRoutePlanner
{

    public DefaultHttpRoutePlanner(SchemeRegistry schemeregistry)
    {
        Args.notNull(schemeregistry, "Scheme registry");
        schemeRegistry = schemeregistry;
    }

    public HttpRoute determineRoute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        Args.notNull(httprequest, "HTTP request");
        HttpRoute httproute = ConnRouteParams.getForcedRoute(httprequest.getParams());
        if(httproute != null)
            return httproute;
        Asserts.notNull(httphost, "Target host");
        java.net.InetAddress inetaddress = ConnRouteParams.getLocalAddress(httprequest.getParams());
        HttpHost httphost1 = ConnRouteParams.getDefaultProxy(httprequest.getParams());
        Scheme scheme;
        boolean flag;
        HttpRoute httproute1;
        try
        {
            scheme = schemeRegistry.getScheme(httphost.getSchemeName());
        }
        catch(IllegalStateException illegalstateexception)
        {
            throw new HttpException(illegalstateexception.getMessage());
        }
        flag = scheme.isLayered();
        if(httphost1 == null)
            httproute1 = new HttpRoute(httphost, inetaddress, flag);
        else
            httproute1 = new HttpRoute(httphost, inetaddress, httphost1, flag);
        return httproute1;
    }

    protected final SchemeRegistry schemeRegistry;
}
