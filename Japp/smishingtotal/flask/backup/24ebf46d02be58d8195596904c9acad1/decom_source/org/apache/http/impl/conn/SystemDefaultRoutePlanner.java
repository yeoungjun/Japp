// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.net.*;
import java.util.List;
import org.apache.http.*;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultRoutePlanner

public class SystemDefaultRoutePlanner extends DefaultRoutePlanner
{

    public SystemDefaultRoutePlanner(ProxySelector proxyselector)
    {
        this(null, proxyselector);
    }

    public SystemDefaultRoutePlanner(SchemePortResolver schemeportresolver, ProxySelector proxyselector)
    {
        super(schemeportresolver);
        if(proxyselector == null)
            proxyselector = ProxySelector.getDefault();
        proxySelector = proxyselector;
    }

    private Proxy chooseProxy(List list)
    {
        Proxy proxy;
        int i;
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
        JVM INSTR tableswitch 1 2: default 64
    //                   1 70
    //                   2 70;
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

    private String getHost(InetSocketAddress inetsocketaddress)
    {
        if(inetsocketaddress.isUnresolved())
            return inetsocketaddress.getHostName();
        else
            return inetsocketaddress.getAddress().getHostAddress();
    }

    protected HttpHost determineProxy(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        URI uri;
        Proxy proxy;
        java.net.Proxy.Type type;
        java.net.Proxy.Type type1;
        HttpHost httphost1;
        try
        {
            uri = new URI(httphost.toURI());
        }
        catch(URISyntaxException urisyntaxexception)
        {
            throw new HttpException((new StringBuilder()).append("Cannot convert host to URI: ").append(httphost).toString(), urisyntaxexception);
        }
        proxy = chooseProxy(proxySelector.select(uri));
        type = proxy.type();
        type1 = java.net.Proxy.Type.HTTP;
        httphost1 = null;
        if(type == type1)
        {
            if(!(proxy.address() instanceof InetSocketAddress))
                throw new HttpException((new StringBuilder()).append("Unable to handle non-Inet proxy address: ").append(proxy.address()).toString());
            InetSocketAddress inetsocketaddress = (InetSocketAddress)proxy.address();
            httphost1 = new HttpHost(getHost(inetsocketaddress), inetsocketaddress.getPort());
        }
        return httphost1;
    }

    private final ProxySelector proxySelector;
}
