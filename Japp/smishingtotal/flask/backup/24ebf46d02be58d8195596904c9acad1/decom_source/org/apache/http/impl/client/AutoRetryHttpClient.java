// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.impl.client:
//            DefaultHttpClient, DefaultServiceUnavailableRetryStrategy

public class AutoRetryHttpClient
    implements HttpClient
{

    public AutoRetryHttpClient()
    {
        this(((HttpClient) (new DefaultHttpClient())), ((ServiceUnavailableRetryStrategy) (new DefaultServiceUnavailableRetryStrategy())));
    }

    public AutoRetryHttpClient(HttpClient httpclient)
    {
        this(httpclient, ((ServiceUnavailableRetryStrategy) (new DefaultServiceUnavailableRetryStrategy())));
    }

    public AutoRetryHttpClient(HttpClient httpclient, ServiceUnavailableRetryStrategy serviceunavailableretrystrategy)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(httpclient, "HttpClient");
        Args.notNull(serviceunavailableretrystrategy, "ServiceUnavailableRetryStrategy");
        backend = httpclient;
        retryStrategy = serviceunavailableretrystrategy;
    }

    public AutoRetryHttpClient(ServiceUnavailableRetryStrategy serviceunavailableretrystrategy)
    {
        this(((HttpClient) (new DefaultHttpClient())), serviceunavailableretrystrategy);
    }

    public Object execute(HttpHost httphost, HttpRequest httprequest, ResponseHandler responsehandler)
        throws IOException
    {
        return execute(httphost, httprequest, responsehandler, null);
    }

    public Object execute(HttpHost httphost, HttpRequest httprequest, ResponseHandler responsehandler, HttpContext httpcontext)
        throws IOException
    {
        return responsehandler.handleResponse(execute(httphost, httprequest, httpcontext));
    }

    public Object execute(HttpUriRequest httpurirequest, ResponseHandler responsehandler)
        throws IOException
    {
        return execute(httpurirequest, responsehandler, ((HttpContext) (null)));
    }

    public Object execute(HttpUriRequest httpurirequest, ResponseHandler responsehandler, HttpContext httpcontext)
        throws IOException
    {
        return responsehandler.handleResponse(execute(httpurirequest, httpcontext));
    }

    public HttpResponse execute(HttpHost httphost, HttpRequest httprequest)
        throws IOException
    {
        return execute(httphost, httprequest, ((HttpContext) (null)));
    }

    public HttpResponse execute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException
    {
        int i = 1;
_L2:
        HttpResponse httpresponse = backend.execute(httphost, httprequest, httpcontext);
        long l;
        if(!retryStrategy.retryRequest(httpresponse, i, httpcontext))
            break; /* Loop/switch isn't completed */
        EntityUtils.consume(httpresponse.getEntity());
        l = retryStrategy.getRetryInterval();
        try
        {
            log.trace((new StringBuilder()).append("Wait for ").append(l).toString());
            Thread.sleep(l);
        }
        catch(InterruptedException interruptedexception)
        {
            try
            {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            }
            catch(RuntimeException runtimeexception)
            {
                try
                {
                    EntityUtils.consume(httpresponse.getEntity());
                }
                catch(IOException ioexception)
                {
                    log.warn("I/O error consuming response content", ioexception);
                }
            }
            throw runtimeexception;
        }
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        return httpresponse;
    }

    public HttpResponse execute(HttpUriRequest httpurirequest)
        throws IOException
    {
        return execute(httpurirequest, ((HttpContext) (null)));
    }

    public HttpResponse execute(HttpUriRequest httpurirequest, HttpContext httpcontext)
        throws IOException
    {
        URI uri = httpurirequest.getURI();
        return execute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()), ((HttpRequest) (httpurirequest)), httpcontext);
    }

    public ClientConnectionManager getConnectionManager()
    {
        return backend.getConnectionManager();
    }

    public HttpParams getParams()
    {
        return backend.getParams();
    }

    private final HttpClient backend;
    private final Log log;
    private final ServiceUnavailableRetryStrategy retryStrategy;
}
