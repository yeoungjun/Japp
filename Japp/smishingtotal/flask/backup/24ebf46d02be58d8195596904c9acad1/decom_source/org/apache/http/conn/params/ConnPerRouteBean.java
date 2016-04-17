// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.params;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.conn.params:
//            ConnPerRoute

public final class ConnPerRouteBean
    implements ConnPerRoute
{

    public ConnPerRouteBean()
    {
        this(2);
    }

    public ConnPerRouteBean(int i)
    {
        maxPerHostMap = new ConcurrentHashMap();
        setDefaultMaxPerRoute(i);
    }

    public int getDefaultMax()
    {
        return defaultMax;
    }

    public int getDefaultMaxPerRoute()
    {
        return defaultMax;
    }

    public int getMaxForRoute(HttpRoute httproute)
    {
        Args.notNull(httproute, "HTTP route");
        Integer integer = (Integer)maxPerHostMap.get(httproute);
        if(integer != null)
            return integer.intValue();
        else
            return defaultMax;
    }

    public void setDefaultMaxPerRoute(int i)
    {
        Args.positive(i, "Defautl max per route");
        defaultMax = i;
    }

    public void setMaxForRoute(HttpRoute httproute, int i)
    {
        Args.notNull(httproute, "HTTP route");
        Args.positive(i, "Max per route");
        maxPerHostMap.put(httproute, Integer.valueOf(i));
    }

    public void setMaxForRoutes(Map map)
    {
        if(map == null)
        {
            return;
        } else
        {
            maxPerHostMap.clear();
            maxPerHostMap.putAll(map);
            return;
        }
    }

    public String toString()
    {
        return maxPerHostMap.toString();
    }

    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
    private volatile int defaultMax;
    private final ConcurrentHashMap maxPerHostMap;
}
