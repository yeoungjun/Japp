// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.conn.routing:
//            RouteInfo, HttpRoute

public final class RouteTracker
    implements RouteInfo, Cloneable
{

    public RouteTracker(HttpHost httphost, InetAddress inetaddress)
    {
        Args.notNull(httphost, "Target host");
        targetHost = httphost;
        localAddress = inetaddress;
        tunnelled = RouteInfo.TunnelType.PLAIN;
        layered = RouteInfo.LayerType.PLAIN;
    }

    public RouteTracker(HttpRoute httproute)
    {
        this(httproute.getTargetHost(), httproute.getLocalAddress());
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public final void connectProxy(HttpHost httphost, boolean flag)
    {
        Args.notNull(httphost, "Proxy host");
        boolean flag1;
        if(!connected)
            flag1 = true;
        else
            flag1 = false;
        Asserts.check(flag1, "Already connected");
        connected = true;
        proxyChain = (new HttpHost[] {
            httphost
        });
        secure = flag;
    }

    public final void connectTarget(boolean flag)
    {
        boolean flag1;
        if(!connected)
            flag1 = true;
        else
            flag1 = false;
        Asserts.check(flag1, "Already connected");
        connected = true;
        secure = flag;
    }

    public final boolean equals(Object obj)
    {
        if(obj != this)
        {
            if(!(obj instanceof RouteTracker))
                return false;
            RouteTracker routetracker = (RouteTracker)obj;
            if(connected != routetracker.connected || secure != routetracker.secure || tunnelled != routetracker.tunnelled || layered != routetracker.layered || !LangUtils.equals(targetHost, routetracker.targetHost) || !LangUtils.equals(localAddress, routetracker.localAddress) || !LangUtils.equals(proxyChain, routetracker.proxyChain))
                return false;
        }
        return true;
    }

    public final int getHopCount()
    {
label0:
        {
            boolean flag = connected;
            int i = 0;
            if(flag)
            {
                if(proxyChain != null)
                    break label0;
                i = 1;
            }
            return i;
        }
        return 1 + proxyChain.length;
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
            return proxyChain[i];
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

    public final HttpHost getProxyHost()
    {
        if(proxyChain == null)
            return null;
        else
            return proxyChain[0];
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
            HttpHost ahttphost[] = proxyChain;
            int j = ahttphost.length;
            for(int k = 0; k < j; k++)
                i = LangUtils.hashCode(i, ahttphost[k]);

        }
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(i, connected), secure), tunnelled), layered);
    }

    public final boolean isConnected()
    {
        return connected;
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

    public final void layerProtocol(boolean flag)
    {
        Asserts.check(connected, "No layered protocol unless connected");
        layered = RouteInfo.LayerType.LAYERED;
        secure = flag;
    }

    public void reset()
    {
        connected = false;
        proxyChain = null;
        tunnelled = RouteInfo.TunnelType.PLAIN;
        layered = RouteInfo.LayerType.PLAIN;
        secure = false;
    }

    public final HttpRoute toRoute()
    {
        if(!connected)
            return null;
        else
            return new HttpRoute(targetHost, localAddress, proxyChain, secure, tunnelled, layered);
    }

    public final String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(50 + 30 * getHopCount());
        stringbuilder.append("RouteTracker[");
        if(localAddress != null)
        {
            stringbuilder.append(localAddress);
            stringbuilder.append("->");
        }
        stringbuilder.append('{');
        if(connected)
            stringbuilder.append('c');
        if(tunnelled == RouteInfo.TunnelType.TUNNELLED)
            stringbuilder.append('t');
        if(layered == RouteInfo.LayerType.LAYERED)
            stringbuilder.append('l');
        if(secure)
            stringbuilder.append('s');
        stringbuilder.append("}->");
        if(proxyChain != null)
        {
            HttpHost ahttphost[] = proxyChain;
            int i = ahttphost.length;
            for(int j = 0; j < i; j++)
            {
                stringbuilder.append(ahttphost[j]);
                stringbuilder.append("->");
            }

        }
        stringbuilder.append(targetHost);
        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    public final void tunnelProxy(HttpHost httphost, boolean flag)
    {
        Args.notNull(httphost, "Proxy host");
        Asserts.check(connected, "No tunnel unless connected");
        Asserts.notNull(proxyChain, "No tunnel without proxy");
        HttpHost ahttphost[] = new HttpHost[1 + proxyChain.length];
        System.arraycopy(proxyChain, 0, ahttphost, 0, proxyChain.length);
        ahttphost[-1 + ahttphost.length] = httphost;
        proxyChain = ahttphost;
        secure = flag;
    }

    public final void tunnelTarget(boolean flag)
    {
        Asserts.check(connected, "No tunnel unless connected");
        Asserts.notNull(proxyChain, "No tunnel without proxy");
        tunnelled = RouteInfo.TunnelType.TUNNELLED;
        secure = flag;
    }

    private boolean connected;
    private RouteInfo.LayerType layered;
    private final InetAddress localAddress;
    private HttpHost proxyChain[];
    private boolean secure;
    private final HttpHost targetHost;
    private RouteInfo.TunnelType tunnelled;
}
