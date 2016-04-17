// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.net.*;
import java.util.List;
import org.apache.http.*;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

public class ProxySelectorRoutePlanner
    implements HttpRoutePlanner
{

    public ProxySelectorRoutePlanner(SchemeRegistry schemeregistry, ProxySelector proxyselector)
    {
        Args.notNull(schemeregistry, "SchemeRegistry");
        schemeRegistry = schemeregistry;
        proxySelector = proxyselector;
    }

    protected Proxy chooseProxy(List list, HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
    {
        Proxy proxy;
        int i;
        Args.notEmpty(list, "List of proxies");
        proxy = null;
        i = 0;
_L6:
        if(proxy != null || i >= list.size()) goto _L2; else goto _L1
_L1:
        Proxy proxy1 = (Proxy)list.get(i);
        static class _cls1
        {

            static final int $SwitchMap$java$net$Proxy$Type[];

            static 
            {
                $SwitchMap$java$net$Proxy$Type = new int[java.net.Proxy.Type.values().length];
                try
                {
                    $SwitchMap$java$net$Proxy$Type[java.net.Proxy.Type.DIRECT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$java$net$Proxy$Type[java.net.Proxy.Type.HTTP.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$java$net$Proxy$Type[java.net.Proxy.Type.SOCKS.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2)
                {
                    return;
                }
            }
        }

        _cls1..SwitchMap.java.net.Proxy.Type[proxy1.type().ordinal()];
        JVM INSTR tableswitch 1 2: default 76
    //                   1 82
    //                   2 82;
           goto _L3 _L4 _L4
_L3:
        i++;
        continue; /* Loop/switch isn't completed */
_L4:
        proxy = proxy1;
        if(true) goto _L3; else goto _L2
_L2:
        if(proxy == null)
            proxy = Proxy.NO_PROXY;
        return proxy;
        if(true) goto _L6; else goto _L5
_L5:
    }

    protected HttpHost determineProxy(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        ProxySelector proxyselector = proxySelector;
        if(proxyselector == null)
            proxyselector = ProxySelector.getDefault();
        if(proxyselector != null)
        {
            URI uri;
            Proxy proxy;
            try
            {
                uri = new URI(httphost.toURI());
            }
            catch(URISyntaxException urisyntaxexception)
            {
                throw new HttpException((new StringBuilder()).append("Cannot convert host to URI: ").append(httphost).toString(), urisyntaxexception);
            }
            proxy = chooseProxy(proxyselector.select(uri), httphost, httprequest, httpcontext);
            if(proxy.type() == java.net.Proxy.Type.HTTP)
                if(!(proxy.address() instanceof InetSocketAddress))
                {
                    throw new HttpException((new StringBuilder()).append("Unable to handle non-Inet proxy address: ").append(proxy.address()).toString());
                } else
                {
                    InetSocketAddress inetsocketaddress = (InetSocketAddress)proxy.address();
                    return new HttpHost(getHost(inetsocketaddress), inetsocketaddress.getPort());
                }
        }
        return null;
    }

    public HttpRoute determineRoute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        Args.notNull(httprequest, "HTTP request");
        HttpRoute httproute = ConnRouteParams.getForcedRoute(httprequest.getParams());
        if(httproute != null)
            return httproute;
        Asserts.notNull(httphost, "Target host");
        InetAddress inetaddress = ConnRouteParams.getLocalAddress(httprequest.getParams());
        HttpHost httphost1 = determineProxy(httphost, httprequest, httpcontext);
        boolean flag = schemeRegistry.getScheme(httphost.getSchemeName()).isLayered();
        HttpRoute httproute1;
        if(httphost1 == null)
            httproute1 = new HttpRoute(httphost, inetaddress, flag);
        else
            httproute1 = new HttpRoute(httphost, inetaddress, httphost1, flag);
        return httproute1;
    }

    protected String getHost(InetSocketAddress inetsocketaddress)
    {
        if(inetsocketaddress.isUnresolved())
            return inetsocketaddress.getHostName();
        else
            return inetsocketaddress.getAddress().getHostAddress();
    }

    public ProxySelector getProxySelector()
    {
        return proxySelector;
    }

    public void setProxySelector(ProxySelector proxyselector)
    {
        proxySelector = proxyselector;
    }

    protected ProxySelector proxySelector;
    protected final SchemeRegistry schemeRegistry;
}
