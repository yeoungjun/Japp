// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

public class ConnRouteParamBean extends HttpAbstractParamBean
{

    public ConnRouteParamBean(HttpParams httpparams)
    {
        super(httpparams);
    }

    public void setDefaultProxy(HttpHost httphost)
    {
        params.setParameter("http.route.default-proxy", httphost);
    }

    public void setForcedRoute(HttpRoute httproute)
    {
        params.setParameter("http.route.forced-route", httproute);
    }

    public void setLocalAddress(InetAddress inetaddress)
    {
        params.setParameter("http.route.local-address", inetaddress);
    }
}
