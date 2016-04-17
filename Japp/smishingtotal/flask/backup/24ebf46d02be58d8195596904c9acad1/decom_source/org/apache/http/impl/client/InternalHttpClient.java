// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthState;
import org.apache.http.client.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.HttpClientParamConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpParamsNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.client:
//            CloseableHttpClient

class InternalHttpClient extends CloseableHttpClient
{

    public InternalHttpClient(ClientExecChain clientexecchain, HttpClientConnectionManager httpclientconnectionmanager, HttpRoutePlanner httprouteplanner, Lookup lookup, Lookup lookup1, CookieStore cookiestore, CredentialsProvider credentialsprovider, 
            RequestConfig requestconfig, List list)
    {
        Args.notNull(clientexecchain, "HTTP client exec chain");
        Args.notNull(httpclientconnectionmanager, "HTTP connection manager");
        Args.notNull(httprouteplanner, "HTTP route planner");
        execChain = clientexecchain;
        connManager = httpclientconnectionmanager;
        routePlanner = httprouteplanner;
        cookieSpecRegistry = lookup;
        authSchemeRegistry = lookup1;
        cookieStore = cookiestore;
        credentialsProvider = credentialsprovider;
        defaultConfig = requestconfig;
        closeables = list;
    }

    private HttpRoute determineRoute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        HttpHost httphost1 = httphost;
        if(httphost1 == null)
            httphost1 = (HttpHost)httprequest.getParams().getParameter("http.default-host");
        Asserts.notNull(httphost1, "Target host");
        return routePlanner.determineRoute(httphost1, httprequest, httpcontext);
    }

    private void setupContext(HttpClientContext httpclientcontext)
    {
        if(httpclientcontext.getAttribute("http.auth.target-scope") == null)
            httpclientcontext.setAttribute("http.auth.target-scope", new AuthState());
        if(httpclientcontext.getAttribute("http.auth.proxy-scope") == null)
            httpclientcontext.setAttribute("http.auth.proxy-scope", new AuthState());
        if(httpclientcontext.getAttribute("http.authscheme-registry") == null)
            httpclientcontext.setAttribute("http.authscheme-registry", authSchemeRegistry);
        if(httpclientcontext.getAttribute("http.cookiespec-registry") == null)
            httpclientcontext.setAttribute("http.cookiespec-registry", cookieSpecRegistry);
        if(httpclientcontext.getAttribute("http.cookie-store") == null)
            httpclientcontext.setAttribute("http.cookie-store", cookieStore);
        if(httpclientcontext.getAttribute("http.auth.credentials-provider") == null)
            httpclientcontext.setAttribute("http.auth.credentials-provider", credentialsProvider);
        if(httpclientcontext.getAttribute("http.request-config") == null)
            httpclientcontext.setAttribute("http.request-config", defaultConfig);
    }

    public void close()
    {
        connManager.shutdown();
        if(closeables != null)
        {
            for(Iterator iterator = closeables.iterator(); iterator.hasNext();)
            {
                Closeable closeable = (Closeable)iterator.next();
                try
                {
                    closeable.close();
                }
                catch(IOException ioexception)
                {
                    log.error(ioexception.getMessage(), ioexception);
                }
            }

        }
    }

    protected CloseableHttpResponse doExecute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        RequestConfig requestconfig;
        HttpParams httpparams;
        Args.notNull(httprequest, "HTTP request");
        boolean flag = httprequest instanceof HttpExecutionAware;
        HttpExecutionAware httpexecutionaware = null;
        if(flag)
            httpexecutionaware = (HttpExecutionAware)httprequest;
        HttpRequestWrapper httprequestwrapper;
        HttpClientContext httpclientcontext;
        boolean flag1;
        HttpRoute httproute;
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
        flag1 = httprequest instanceof Configurable;
        requestconfig = null;
        if(!flag1)
            break MISSING_BLOCK_LABEL_68;
        requestconfig = ((Configurable)httprequest).getConfig();
        if(requestconfig != null) goto _L2; else goto _L1
_L1:
        httpparams = httprequest.getParams();
        if(!(httpparams instanceof HttpParamsNames)) goto _L4; else goto _L3
_L3:
        if(!((HttpParamsNames)httpparams).getNames().isEmpty())
            requestconfig = HttpClientParamConfig.getRequestConfig(httpparams);
_L2:
        if(requestconfig == null)
            break MISSING_BLOCK_LABEL_126;
        httpclientcontext.setRequestConfig(requestconfig);
        setupContext(httpclientcontext);
        httproute = determineRoute(httphost, httprequestwrapper, httpclientcontext);
        return execChain.execute(httproute, httprequestwrapper, httpclientcontext, httpexecutionaware);
_L4:
        RequestConfig requestconfig1 = HttpClientParamConfig.getRequestConfig(httpparams);
        requestconfig = requestconfig1;
        if(true) goto _L2; else goto _L5
_L5:
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

            final InternalHttpClient this$0;

            
            {
                this$0 = InternalHttpClient.this;
                super();
            }
        };
    }

    public HttpParams getParams()
    {
        throw new UnsupportedOperationException();
    }

    private final Lookup authSchemeRegistry;
    private final List closeables;
    private final HttpClientConnectionManager connManager;
    private final Lookup cookieSpecRegistry;
    private final CookieStore cookieStore;
    private final CredentialsProvider credentialsProvider;
    private final RequestConfig defaultConfig;
    private final ClientExecChain execChain;
    private final Log log = LogFactory.getLog(getClass());
    private final HttpRoutePlanner routePlanner;

}
