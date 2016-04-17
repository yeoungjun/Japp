// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

// Referenced classes of package org.apache.http.impl.client:
//            HttpClientBuilder, MinimalHttpClient, CloseableHttpClient

public class HttpClients
{

    private HttpClients()
    {
    }

    public static CloseableHttpClient createDefault()
    {
        return HttpClientBuilder.create().build();
    }

    public static CloseableHttpClient createMinimal()
    {
        return new MinimalHttpClient(new PoolingHttpClientConnectionManager());
    }

    public static CloseableHttpClient createMinimal(HttpClientConnectionManager httpclientconnectionmanager)
    {
        return new MinimalHttpClient(httpclientconnectionmanager);
    }

    public static CloseableHttpClient createSystem()
    {
        return HttpClientBuilder.create().useSystemProperties().build();
    }

    public static HttpClientBuilder custom()
    {
        return HttpClientBuilder.create();
    }
}
