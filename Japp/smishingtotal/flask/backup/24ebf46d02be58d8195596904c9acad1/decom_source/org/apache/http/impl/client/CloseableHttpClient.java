// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

public abstract class CloseableHttpClient
    implements HttpClient, Closeable
{

    public CloseableHttpClient()
    {
    }

    private static HttpHost determineTarget(HttpUriRequest httpurirequest)
        throws ClientProtocolException
    {
        URI uri = httpurirequest.getURI();
        boolean flag = uri.isAbsolute();
        HttpHost httphost = null;
        if(flag)
        {
            httphost = URIUtils.extractHost(uri);
            if(httphost == null)
                throw new ClientProtocolException((new StringBuilder()).append("URI does not specify a valid host name: ").append(uri).toString());
        }
        return httphost;
    }

    protected abstract CloseableHttpResponse doExecute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException;

    public Object execute(HttpHost httphost, HttpRequest httprequest, ResponseHandler responsehandler)
        throws IOException, ClientProtocolException
    {
        return execute(httphost, httprequest, responsehandler, null);
    }

    public Object execute(HttpHost httphost, HttpRequest httprequest, ResponseHandler responsehandler, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        Args.notNull(responsehandler, "Response handler");
        CloseableHttpResponse closeablehttpresponse = execute(httphost, httprequest, httpcontext);
        Object obj;
        try
        {
            obj = responsehandler.handleResponse(closeablehttpresponse);
        }
        catch(Exception exception)
        {
            org.apache.http.HttpEntity httpentity = closeablehttpresponse.getEntity();
            try
            {
                EntityUtils.consume(httpentity);
            }
            catch(Exception exception1)
            {
                log.warn("Error consuming content after an exception.", exception1);
            }
            if(exception instanceof RuntimeException)
                throw (RuntimeException)exception;
            if(exception instanceof IOException)
                throw (IOException)exception;
            else
                throw new UndeclaredThrowableException(exception);
        }
        EntityUtils.consume(closeablehttpresponse.getEntity());
        return obj;
    }

    public Object execute(HttpUriRequest httpurirequest, ResponseHandler responsehandler)
        throws IOException, ClientProtocolException
    {
        return execute(httpurirequest, responsehandler, ((HttpContext) (null)));
    }

    public Object execute(HttpUriRequest httpurirequest, ResponseHandler responsehandler, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        return execute(determineTarget(httpurirequest), ((HttpRequest) (httpurirequest)), responsehandler, httpcontext);
    }

    public volatile HttpResponse execute(HttpHost httphost, HttpRequest httprequest)
        throws IOException, ClientProtocolException
    {
        return execute(httphost, httprequest);
    }

    public volatile HttpResponse execute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        return execute(httphost, httprequest, httpcontext);
    }

    public volatile HttpResponse execute(HttpUriRequest httpurirequest)
        throws IOException, ClientProtocolException
    {
        return execute(httpurirequest);
    }

    public volatile HttpResponse execute(HttpUriRequest httpurirequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        return execute(httpurirequest, httpcontext);
    }

    public CloseableHttpResponse execute(HttpHost httphost, HttpRequest httprequest)
        throws IOException, ClientProtocolException
    {
        return doExecute(httphost, httprequest, (HttpContext)null);
    }

    public CloseableHttpResponse execute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        return doExecute(httphost, httprequest, httpcontext);
    }

    public CloseableHttpResponse execute(HttpUriRequest httpurirequest)
        throws IOException, ClientProtocolException
    {
        return execute(httpurirequest, (HttpContext)null);
    }

    public CloseableHttpResponse execute(HttpUriRequest httpurirequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        Args.notNull(httpurirequest, "HTTP request");
        return doExecute(determineTarget(httpurirequest), httpurirequest, httpcontext);
    }

    private final Log log = LogFactory.getLog(getClass());
}
