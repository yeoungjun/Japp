// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.conn.params:
//            ConnRoutePNames

public class ConnRouteParams
    implements ConnRoutePNames
{

    private ConnRouteParams()
    {
    }

    public static HttpHost getDefaultProxy(HttpParams httpparams)
    {
        Args.notNull(httpparams, "Parameters");
        HttpHost httphost = (HttpHost)httpparams.getParameter("http.route.default-proxy");
        if(httphost != null && NO_HOST.equals(httphost))
            httphost = null;
        return httphost;
    }

    public static HttpRoute getForcedRoute(HttpParams httpparams)
    {
        Args.notNull(httpparams, "Parameters");
        HttpRoute httproute = (HttpRoute)httpparams.getParameter("http.route.forced-route");
        if(httproute != null && NO_ROUTE.equals(httproute))
            httproute = null;
        return httproute;
    }

    public static InetAddress getLocalAddress(HttpParams httpparams)
    {
        Args.notNull(httpparams, "Parameters");
        return (InetAddress)httpparams.getParameter("http.route.local-address");
    }

    public static void setDefaultProxy(HttpParams httpparams, HttpHost httphost)
    {
        Args.notNull(httpparams, "Parameters");
        httpparams.setParameter("http.route.default-proxy", httphost);
    }

    public static void setForcedRoute(HttpParams httpparams, HttpRoute httproute)
    {
        Args.notNull(httpparams, "Parameters");
        httpparams.setParameter("http.route.forced-route", httproute);
    }

    public static void setLocalAddress(HttpParams httpparams, InetAddress inetaddress)
    {
        Args.notNull(httpparams, "Parameters");
        httpparams.setParameter("http.route.local-address", inetaddress);
    }

    public static final HttpHost NO_HOST;
    public static final HttpRoute NO_ROUTE;

    static 
    {
        NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
        NO_ROUTE = new HttpRoute(NO_HOST);
    }
}
