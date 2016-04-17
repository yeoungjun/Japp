// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.routing;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import org.apache.http.HttpHost;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

// Referenced classes of package org.apache.http.conn.routing:
//            RouteInfo

public final class HttpRoute
    implements RouteInfo, Cloneable
{

    public HttpRoute(HttpHost httphost)
    {
        this(httphost, null, Collections.emptyList(), false, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
    }

    private HttpRoute(HttpHost httphost, InetAddress inetaddress, List list, boolean flag, RouteInfo.TunnelType tunneltype, RouteInfo.LayerType layertype)
    {
        Args.notNull(httphost, "Target host");
        targetHost = httphost;
        localAddress = inetaddress;
        if(list != null && !list.isEmpty())
            proxyChain = new ArrayList(list);
        else
            proxyChain = null;
        if(tunneltype == RouteInfo.TunnelType.TUNNELLED)
        {
            boolean flag1;
            if(proxyChain != null)
                flag1 = true;
            else
                flag1 = false;
            Args.check(flag1, "Proxy required if tunnelled");
        }
        secure = flag;
        if(tunneltype == null)
            tunneltype = RouteInfo.TunnelType.PLAIN;
        tunnelled = tunneltype;
        if(layertype == null)
            layertype = RouteInfo.LayerType.PLAIN;
        layered = layertype;
    }

    public HttpRoute(HttpHost httphost, InetAddress inetaddress, HttpHost httphost1, boolean flag)
    {
        List list = Collections.singletonList(Args.notNull(httphost1, "Proxy host"));
        RouteInfo.TunnelType tunneltype;
        RouteInfo.LayerType layertype;
        if(flag)
            tunneltype = RouteInfo.TunnelType.TUNNELLED;
        else
            tunneltype = RouteInfo.TunnelType.PLAIN;
        if(flag)
            layertype = RouteInfo.LayerType.LAYERED;
        else
            layertype = RouteInfo.LayerType.PLAIN;
        this(httphost, inetaddress, list, flag, tunneltype, layertype);
    }

    public HttpRoute(HttpHost httphost, InetAddress inetaddress, HttpHost httphost1, boolean flag, RouteInfo.TunnelType tunneltype, RouteInfo.LayerType layertype)
    {
        List list;
        if(httphost1 != null)
            list = Collections.singletonList(httphost1);
        else
            list = null;
        this(httphost, inetaddress, list, flag, tunneltype, layertype);
    }

    public HttpRoute(HttpHost httphost, InetAddress inetaddress, boolean flag)
    {
        this(httphost, inetaddress, Collections.emptyList(), flag, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httphost, InetAddress inetaddress, HttpHost ahttphost[], boolean flag, RouteInfo.TunnelType tunneltype, RouteInfo.LayerType layertype)
    {
        List list;
        if(ahttphost != null)
            list = Arrays.asList(ahttphost);
        else
            list = null;
        this(httphost, inetaddress, list, flag, tunneltype, layertype);
    }

    public HttpRoute(HttpHost httphost, HttpHost httphost1)
    {
        this(httphost, null, httphost1, false);
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public final boolean equals(Object obj)
    {
        HttpRoute httproute;
        if(this != obj)
            if(obj instanceof HttpRoute)
            {
                if(secure != (httproute = (HttpRoute)obj).secure || tunnelled != httproute.tunnelled || layered != httproute.layered || !LangUtils.equals(targetHost, httproute.targetHost) || !LangUtils.equals(localAddress, httproute.localAddress) || !LangUtils.equals(proxyChain, httproute.proxyChain))
                    return false;
            } else
            {
                return false;
            }
        return true;
    }

    public final int getHopCount()
    {
        if(proxyChain != null)
            return 1 + proxyChain.size();
        else
            return 1;
    }

    public final HttpHost getHopTarget(int i)
    {
        Args.notNegative(i, "Hop index");
        int j = getHopCount();
        boolean flag;
        if(i < j)
            flag = true;
        else
            flag = false;
        Args.check(flag, "Hop index exceeds tracked route length");
        if(i < j - 1)
            return (HttpHost)proxyChain.get(i);
        else
            return targetHost;
    }

    public final RouteInfo.LayerType getLayerType()
    {
        return layered;
    }

    public final InetAddress getLocalAddress()
    {
        return localAddress;
    }

    public final InetSocketAddress getLocalSocketAddress()
    {
        if(localAddress != null)
            return new InetSocketAddress(localAddress, 0);
        else
            return null;
    }

    public final HttpHost getProxyHost()
    {
        if(proxyChain != null && !proxyChain.isEmpty())
            return (HttpHost)proxyChain.get(0);
        else
            return null;
    }

    public final HttpHost getTargetHost()
    {
        return targetHost;
    }

    public final RouteInfo.TunnelType getTunnelType()
    {
        return tunnelled;
    }

    public final int hashCode()
    {
        int i = LangUtils.hashCode(LangUtils.hashCode(17, targetHost), localAddress);
        if(proxyChain != null)
        {
            for(Iterator iterator = proxyChain.iterator(); iterator.hasNext();)
                i = LangUtils.hashCode(i, (HttpHost)iterator.next());

        }
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(i, secure), tunnelled), layered);
    }

    public final boolean isLayered()
    {
        return layered == RouteInfo.LayerType.LAYERED;
    }

    public final boolean isSecure()
    {
        return secure;
    }

    public final boolean isTunnelled()
    {
        return tunnelled == RouteInfo.TunnelType.TUNNELLED;
    }

    public final String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(50 + 30 * getHopCount());
        if(localAddress != null)
        {
            stringbuilder.append(localAddress);
            stringbuilder.append("->");
        }
        stringbuilder.append('{');
        if(tunnelled == RouteInfo.TunnelType.TUNNELLED)
            stringbuilder.append('t');
        if(layered == RouteInfo.LayerType.LAYERED)
            stringbuilder.append('l');
        if(secure)
            stringbuilder.append('s');
        stringbuilder.append("}->");
        if(proxyChain != null)
        {
            for(Iterator iterator = proxyChain.iterator(); iterator.hasNext(); stringbuilder.append("->"))
                stringbuilder.append((HttpHost)iterator.next());

        }
        stringbuilder.append(targetHost);
        return stringbuilder.toString();
    }

    private final RouteInfo.LayerType layered;
    private final InetAddress localAddress;
    private final List proxyChain;
    private final boolean secure;
    private final HttpHost targetHost;
    private final RouteInfo.TunnelType tunnelled;
}
