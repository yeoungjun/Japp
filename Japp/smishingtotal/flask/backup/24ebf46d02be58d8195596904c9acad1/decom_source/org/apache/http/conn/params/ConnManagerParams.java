// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.params;

import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.conn.params:
//            ConnManagerPNames, ConnPerRoute

public final class ConnManagerParams
    implements ConnManagerPNames
{

    public ConnManagerParams()
    {
    }

    public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        ConnPerRoute connperroute = (ConnPerRoute)httpparams.getParameter("http.conn-manager.max-per-route");
        if(connperroute == null)
            connperroute = DEFAULT_CONN_PER_ROUTE;
        return connperroute;
    }

    public static int getMaxTotalConnections(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        return httpparams.getIntParameter("http.conn-manager.max-total", 20);
    }

    public static long getTimeout(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        return httpparams.getLongParameter("http.conn-manager.timeout", 0L);
    }

    public static void setMaxConnectionsPerRoute(HttpParams httpparams, ConnPerRoute connperroute)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setParameter("http.conn-manager.max-per-route", connperroute);
    }

    public static void setMaxTotalConnections(HttpParams httpparams, int i)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setIntParameter("http.conn-manager.max-total", i);
    }

    public static void setTimeout(HttpParams httpparams, long l)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setLongParameter("http.conn-manager.timeout", l);
    }

    private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new ConnPerRoute() {

        public int getMaxForRoute(HttpRoute httproute)
        {
            return 2;
        }

    };
    public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

}
