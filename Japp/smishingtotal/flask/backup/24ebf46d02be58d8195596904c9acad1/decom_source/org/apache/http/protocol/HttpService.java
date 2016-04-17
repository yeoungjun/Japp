// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.protocol:
//            HttpProcessor, HttpRequestHandlerMapper, HttpRequestHandler, HttpContext, 
//            HttpExpectationVerifier, HttpRequestHandlerResolver

public class HttpService
{
    private static class HttpRequestHandlerResolverAdapter
        implements HttpRequestHandlerMapper
    {

        public HttpRequestHandler lookup(HttpRequest httprequest)
        {
            return resolver.lookup(httprequest.getRequestLine().getUri());
        }

        private final HttpRequestHandlerResolver resolver;

        public HttpRequestHandlerResolverAdapter(HttpRequestHandlerResolver httprequesthandlerresolver)
        {
            resolver = httprequesthandlerresolver;
        }
    }


    public HttpService(HttpProcessor httpprocessor, ConnectionReuseStrategy connectionreusestrategy, HttpResponseFactory httpresponsefactory)
    {
        params = null;
        processor = null;
        handlerMapper = null;
        connStrategy = null;
        responseFactory = null;
        expectationVerifier = null;
        setHttpProcessor(httpprocessor);
        setConnReuseStrategy(connectionreusestrategy);
        setResponseFactory(httpresponsefactory);
    }

    public HttpService(HttpProcessor httpprocessor, ConnectionReuseStrategy connectionreusestrategy, HttpResponseFactory httpresponsefactory, HttpRequestHandlerMapper httprequesthandlermapper)
    {
        this(httpprocessor, connectionreusestrategy, httpresponsefactory, httprequesthandlermapper, ((HttpExpectationVerifier) (null)));
    }

    public HttpService(HttpProcessor httpprocessor, ConnectionReuseStrategy connectionreusestrategy, HttpResponseFactory httpresponsefactory, HttpRequestHandlerMapper httprequesthandlermapper, HttpExpectationVerifier httpexpectationverifier)
    {
        params = null;
        processor = null;
        handlerMapper = null;
        connStrategy = null;
        responseFactory = null;
        expectationVerifier = null;
        processor = (HttpProcessor)Args.notNull(httpprocessor, "HTTP processor");
        if(connectionreusestrategy == null)
            connectionreusestrategy = DefaultConnectionReuseStrategy.INSTANCE;
        connStrategy = connectionreusestrategy;
        if(httpresponsefactory == null)
            httpresponsefactory = DefaultHttpResponseFactory.INSTANCE;
        responseFactory = httpresponsefactory;
        handlerMapper = httprequesthandlermapper;
        expectationVerifier = httpexpectationverifier;
    }

    public HttpService(HttpProcessor httpprocessor, ConnectionReuseStrategy connectionreusestrategy, HttpResponseFactory httpresponsefactory, HttpRequestHandlerResolver httprequesthandlerresolver, HttpParams httpparams)
    {
        this(httpprocessor, connectionreusestrategy, httpresponsefactory, ((HttpRequestHandlerMapper) (new HttpRequestHandlerResolverAdapter(httprequesthandlerresolver))), ((HttpExpectationVerifier) (null)));
        params = httpparams;
    }

    public HttpService(HttpProcessor httpprocessor, ConnectionReuseStrategy connectionreusestrategy, HttpResponseFactory httpresponsefactory, HttpRequestHandlerResolver httprequesthandlerresolver, HttpExpectationVerifier httpexpectationverifier, HttpParams httpparams)
    {
        this(httpprocessor, connectionreusestrategy, httpresponsefactory, ((HttpRequestHandlerMapper) (new HttpRequestHandlerResolverAdapter(httprequesthandlerresolver))), httpexpectationverifier);
        params = httpparams;
    }

    public HttpService(HttpProcessor httpprocessor, HttpRequestHandlerMapper httprequesthandlermapper)
    {
        this(httpprocessor, null, null, httprequesthandlermapper, ((HttpExpectationVerifier) (null)));
    }

    protected void doService(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpRequestHandlerMapper httprequesthandlermapper = handlerMapper;
        HttpRequestHandler httprequesthandler = null;
        if(httprequesthandlermapper != null)
            httprequesthandler = handlerMapper.lookup(httprequest);
        if(httprequesthandler != null)
        {
            httprequesthandler.handle(httprequest, httpresponse, httpcontext);
            return;
        } else
        {
            httpresponse.setStatusCode(501);
            return;
        }
    }

    public HttpParams getParams()
    {
        return params;
    }

    protected void handleException(HttpException httpexception, HttpResponse httpresponse)
    {
        String s;
        ByteArrayEntity bytearrayentity;
        if(httpexception instanceof MethodNotSupportedException)
            httpresponse.setStatusCode(501);
        else
        if(httpexception instanceof UnsupportedHttpVersionException)
            httpresponse.setStatusCode(505);
        else
        if(httpexception instanceof ProtocolException)
            httpresponse.setStatusCode(400);
        else
            httpresponse.setStatusCode(500);
        s = httpexception.getMessage();
        if(s == null)
            s = httpexception.toString();
        bytearrayentity = new ByteArrayEntity(EncodingUtils.getAsciiBytes(s));
        bytearrayentity.setContentType("text/plain; charset=US-ASCII");
        httpresponse.setEntity(bytearrayentity);
    }

    public void handleRequest(HttpServerConnection httpserverconnection, HttpContext httpcontext)
        throws IOException, HttpException
    {
        httpcontext.setAttribute("http.connection", httpserverconnection);
        HttpRequest httprequest;
        boolean flag;
        httprequest = httpserverconnection.receiveRequestHeader();
        flag = httprequest instanceof HttpEntityEnclosingRequest;
        HttpResponse httpresponse = null;
        if(!flag) goto _L2; else goto _L1
_L1:
        if(!((HttpEntityEnclosingRequest)httprequest).expectContinue()) goto _L4; else goto _L3
_L3:
        HttpExpectationVerifier httpexpectationverifier;
        httpresponse = responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, 100, httpcontext);
        httpexpectationverifier = expectationVerifier;
        if(httpexpectationverifier == null)
            break MISSING_BLOCK_LABEL_87;
        expectationVerifier.verify(httprequest, httpresponse, httpcontext);
_L6:
        if(httpresponse.getStatusLine().getStatusCode() >= 200) goto _L2; else goto _L5
_L5:
        httpserverconnection.sendResponseHeader(httpresponse);
        httpserverconnection.flush();
        httpresponse = null;
        httpserverconnection.receiveRequestEntity((HttpEntityEnclosingRequest)httprequest);
_L2:
        httpcontext.setAttribute("http.request", httprequest);
        if(httpresponse != null)
            break MISSING_BLOCK_LABEL_187;
        httpresponse = responseFactory.newHttpResponse(HttpVersion.HTTP_1_1, 200, httpcontext);
        processor.process(httprequest, httpcontext);
        doService(httprequest, httpresponse, httpcontext);
        if(httprequest instanceof HttpEntityEnclosingRequest)
            EntityUtils.consume(((HttpEntityEnclosingRequest)httprequest).getEntity());
_L7:
        httpcontext.setAttribute("http.response", httpresponse);
        processor.process(httpresponse, httpcontext);
        httpserverconnection.sendResponseHeader(httpresponse);
        httpserverconnection.sendResponseEntity(httpresponse);
        httpserverconnection.flush();
        if(!connStrategy.keepAlive(httpresponse, httpcontext))
            httpserverconnection.close();
        return;
        HttpException httpexception1;
        httpexception1;
        httpresponse = responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, httpcontext);
        handleException(httpexception1, httpresponse);
          goto _L6
        HttpException httpexception;
        httpexception;
        httpresponse = responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, httpcontext);
        handleException(httpexception, httpresponse);
          goto _L7
_L4:
        httpserverconnection.receiveRequestEntity((HttpEntityEnclosingRequest)httprequest);
        httpresponse = null;
          goto _L2
    }

    public void setConnReuseStrategy(ConnectionReuseStrategy connectionreusestrategy)
    {
        Args.notNull(connectionreusestrategy, "Connection reuse strategy");
        connStrategy = connectionreusestrategy;
    }

    public void setExpectationVerifier(HttpExpectationVerifier httpexpectationverifier)
    {
        expectationVerifier = httpexpectationverifier;
    }

    public void setHandlerResolver(HttpRequestHandlerResolver httprequesthandlerresolver)
    {
        handlerMapper = new HttpRequestHandlerResolverAdapter(httprequesthandlerresolver);
    }

    public void setHttpProcessor(HttpProcessor httpprocessor)
    {
        Args.notNull(httpprocessor, "HTTP processor");
        processor = httpprocessor;
    }

    public void setParams(HttpParams httpparams)
    {
        params = httpparams;
    }

    public void setResponseFactory(HttpResponseFactory httpresponsefactory)
    {
        Args.notNull(httpresponsefactory, "Response factory");
        responseFactory = httpresponsefactory;
    }

    private volatile ConnectionReuseStrategy connStrategy;
    private volatile HttpExpectationVerifier expectationVerifier;
    private volatile HttpRequestHandlerMapper handlerMapper;
    private volatile HttpParams params;
    private volatile HttpProcessor processor;
    private volatile HttpResponseFactory responseFactory;
}
