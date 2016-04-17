// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.impl.client:
//            DefaultHttpClient, EntityEnclosingRequestWrapper, RequestWrapper

public class DecompressingHttpClient
    implements HttpClient
{

    public DecompressingHttpClient()
    {
        this(((HttpClient) (new DefaultHttpClient())));
    }

    public DecompressingHttpClient(HttpClient httpclient)
    {
        this(httpclient, ((HttpRequestInterceptor) (new RequestAcceptEncoding())), ((HttpResponseInterceptor) (new ResponseContentEncoding())));
    }

    DecompressingHttpClient(HttpClient httpclient, HttpRequestInterceptor httprequestinterceptor, HttpResponseInterceptor httpresponseinterceptor)
    {
        backend = httpclient;
        acceptEncodingInterceptor = httprequestinterceptor;
        contentEncodingInterceptor = httpresponseinterceptor;
    }

    public Object execute(HttpHost httphost, HttpRequest httprequest, ResponseHandler responsehandler)
        throws IOException, ClientProtocolException
    {
        return execute(httphost, httprequest, responsehandler, null);
    }

    public Object execute(HttpHost httphost, HttpRequest httprequest, ResponseHandler responsehandler, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        HttpResponse httpresponse = execute(httphost, httprequest, httpcontext);
        Object obj = responsehandler.handleResponse(httpresponse);
        org.apache.http.HttpEntity httpentity1 = httpresponse.getEntity();
        if(httpentity1 != null)
            EntityUtils.consume(httpentity1);
        return obj;
        Exception exception;
        exception;
        org.apache.http.HttpEntity httpentity = httpresponse.getEntity();
        if(httpentity != null)
            EntityUtils.consume(httpentity);
        throw exception;
    }

    public Object execute(HttpUriRequest httpurirequest, ResponseHandler responsehandler)
        throws IOException, ClientProtocolException
    {
        return execute(getHttpHost(httpurirequest), ((HttpRequest) (httpurirequest)), responsehandler);
    }

    public Object execute(HttpUriRequest httpurirequest, ResponseHandler responsehandler, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        return execute(getHttpHost(httpurirequest), ((HttpRequest) (httpurirequest)), responsehandler, httpcontext);
    }

    public HttpResponse execute(HttpHost httphost, HttpRequest httprequest)
        throws IOException, ClientProtocolException
    {
        return execute(httphost, httprequest, (HttpContext)null);
    }

    public HttpResponse execute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        Object obj;
        Object obj1;
        HttpResponse httpresponse;
        if(httpcontext != null)
            obj = httpcontext;
        else
            try
            {
                obj = new BasicHttpContext();
            }
            catch(HttpException httpexception)
            {
                throw new ClientProtocolException(httpexception);
            }
        if(!(httprequest instanceof HttpEntityEnclosingRequest))
            break MISSING_BLOCK_LABEL_141;
        obj1 = new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httprequest);
_L1:
        acceptEncodingInterceptor.process(((HttpRequest) (obj1)), ((HttpContext) (obj)));
        httpresponse = backend.execute(httphost, ((HttpRequest) (obj1)), ((HttpContext) (obj)));
        contentEncodingInterceptor.process(httpresponse, ((HttpContext) (obj)));
        if(Boolean.TRUE.equals(((HttpContext) (obj)).getAttribute("http.client.response.uncompressed")))
        {
            httpresponse.removeHeaders("Content-Length");
            httpresponse.removeHeaders("Content-Encoding");
            httpresponse.removeHeaders("Content-MD5");
        }
        return httpresponse;
        obj1 = new RequestWrapper(httprequest);
          goto _L1
        HttpException httpexception1;
        httpexception1;
        EntityUtils.consume(httpresponse.getEntity());
        throw httpexception1;
        IOException ioexception;
        ioexception;
        EntityUtils.consume(httpresponse.getEntity());
        throw ioexception;
        RuntimeException runtimeexception;
        runtimeexception;
        EntityUtils.consume(httpresponse.getEntity());
        throw runtimeexception;
    }

    public HttpResponse execute(HttpUriRequest httpurirequest)
        throws IOException, ClientProtocolException
    {
        return execute(getHttpHost(httpurirequest), ((HttpRequest) (httpurirequest)), (HttpContext)null);
    }

    public HttpResponse execute(HttpUriRequest httpurirequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        return execute(getHttpHost(httpurirequest), ((HttpRequest) (httpurirequest)), httpcontext);
    }

    public ClientConnectionManager getConnectionManager()
    {
        return backend.getConnectionManager();
    }

    public HttpClient getHttpClient()
    {
        return backend;
    }

    HttpHost getHttpHost(HttpUriRequest httpurirequest)
    {
        return URIUtils.extractHost(httpurirequest.getURI());
    }

    public HttpParams getParams()
    {
        return backend.getParams();
    }

    private final HttpRequestInterceptor acceptEncodingInterceptor;
    private final HttpClient backend;
    private final HttpResponseInterceptor contentEncodingInterceptor;
}
