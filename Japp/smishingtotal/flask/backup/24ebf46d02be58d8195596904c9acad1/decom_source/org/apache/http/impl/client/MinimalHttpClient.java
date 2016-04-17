// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.execchain.MinimalClientExec;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            CloseableHttpClient, DefaultConnectionKeepAliveStrategy

class MinimalHttpClient extends CloseableHttpClient
{

    public MinimalHttpClient(HttpClientConnectionManager httpclientconnectionmanager)
    {
        connManager = (HttpClientConnectionManager)Args.notNull(httpclientconnectionmanager, "HTTP connection manager");
        requestExecutor = new MinimalClientExec(new HttpRequestExecutor(), httpclientconnectionmanager, DefaultConnectionReuseStrategy.INSTANCE, DefaultConnectionKeepAliveStrategy.INSTANCE);
    }

    public void close()
    {
        connManager.shutdown();
    }

    protected CloseableHttpResponse doExecute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        Args.notNull(httphost, "Target host");
        Args.notNull(httprequest, "HTTP request");
        boolean flag = httprequest instanceof HttpExecutionAware;
        HttpExecutionAware httpexecutionaware = null;
        if(flag)
            httpexecutionaware = (HttpExecutionAware)httprequest;
        HttpRequestWrapper httprequestwrapper;
        HttpClientContext httpclientcontext;
        HttpRoute httproute;
        boolean flag1;
        org.apache.http.client.config.RequestConfig requestconfig;
        try
        {
            httprequestwrapper = HttpRequestWrapper.wrap(httprequest);
        }
        catch(HttpException httpexception)
        {
            throw new ClientProtocolException(httpexception);
        }
        if(httpcontext == null)
            httpcontext = new BasicHttpContext();
        httpclientcontext = HttpClientContext.adapt(httpcontext);
        httproute = new HttpRoute(httphost);
        flag1 = httprequest instanceof Configurable;
        requestconfig = null;
        if(!flag1)
            break MISSING_BLOCK_LABEL_85;
        requestconfig = ((Configurable)httprequest).getConfig();
        if(requestconfig == null)
            break MISSING_BLOCK_LABEL_97;
        httpclientcontext.setRequestConfig(requestconfig);
        return requestExecutor.execute(httproute, httprequestwrapper, httpclientcontext, httpexecutionaware);
    }

    public ClientConnectionManager getConnectionManager()
    {
        return new ClientConnectionManager() {

            public void closeExpiredConnections()
            {
                connManager.closeExpiredConnections();
            }

            public void closeIdleConnections(long l, TimeUnit timeunit)
            {
                connManager.closeIdleConnections(l, timeunit);
            }

            public SchemeRegistry getSchemeRegistry()
            {
                throw new UnsupportedOperationException();
            }

            public void releaseConnection(ManagedClientConnection managedclientconnection, long l, TimeUnit timeunit)
            {
                throw new UnsupportedOperationException();
            }

            public ClientConnectionRequest requestConnection(HttpRoute httproute, Object obj)
            {
                throw new UnsupportedOperationException();
            }

            public void shutdown()
            {
                connManager.shutdown();
            }

            final MinimalHttpClient this$0;

            
            {
                this$0 = MinimalHttpClient.this;
                super();
            }
        };
    }

    public HttpParams getParams()
    {
        return params;
    }

    private final HttpClientConnectionManager connManager;
    private final HttpParams params = new BasicHttpParams();
    private final MinimalClientExec requestExecutor;

}
