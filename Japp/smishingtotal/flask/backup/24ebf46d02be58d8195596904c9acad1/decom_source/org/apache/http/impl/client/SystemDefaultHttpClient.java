// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.ProxySelector;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.conn.*;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.impl.client:
//            DefaultHttpClient

public class SystemDefaultHttpClient extends DefaultHttpClient
{

    public SystemDefaultHttpClient()
    {
        super(null, null);
    }

    public SystemDefaultHttpClient(HttpParams httpparams)
    {
        super(null, httpparams);
    }

    protected ClientConnectionManager createClientConnectionManager()
    {
        PoolingClientConnectionManager poolingclientconnectionmanager = new PoolingClientConnectionManager(SchemeRegistryFactory.createSystemDefault());
        if("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true")))
        {
            int i = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
            poolingclientconnectionmanager.setDefaultMaxPerRoute(i);
            poolingclientconnectionmanager.setMaxTotal(i * 2);
        }
        return poolingclientconnectionmanager;
    }

    protected ConnectionReuseStrategy createConnectionReuseStrategy()
    {
        if("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true")))
            return new DefaultConnectionReuseStrategy();
        else
            return new NoConnectionReuseStrategy();
    }

    protected HttpRoutePlanner createHttpRoutePlanner()
    {
        return new ProxySelectorRoutePlanner(getConnectionManager().getSchemeRegistry(), ProxySelector.getDefault());
    }
}
