// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.protocol.*;
import org.apache.http.util.Args;
import org.apache.http.util.VersionInfo;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain, RequestAbortedException, ConnectionHolder, Proxies

public class MinimalClientExec
    implements ClientExecChain
{

    public MinimalClientExec(HttpRequestExecutor httprequestexecutor, HttpClientConnectionManager httpclientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy)
    {
        Args.notNull(httprequestexecutor, "HTTP request executor");
        Args.notNull(httpclientconnectionmanager, "Client connection manager");
        Args.notNull(connectionreusestrategy, "Connection reuse strategy");
        Args.notNull(connectionkeepalivestrategy, "Connection keep alive strategy");
        HttpRequestInterceptor ahttprequestinterceptor[] = new HttpRequestInterceptor[4];
        ahttprequestinterceptor[0] = new RequestContent();
        ahttprequestinterceptor[1] = new RequestTargetHost();
        ahttprequestinterceptor[2] = new RequestClientConnControl();
        ahttprequestinterceptor[3] = new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", getClass()));
        httpProcessor = new ImmutableHttpProcessor(ahttprequestinterceptor);
        requestExecutor = httprequestexecutor;
        connManager = httpclientconnectionmanager;
        reuseStrategy = connectionreusestrategy;
        keepAliveStrategy = connectionkeepalivestrategy;
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        RequestConfig requestconfig;
        HttpClientConnection httpclientconnection;
        Args.notNull(httproute, "HTTP route");
        Args.notNull(httprequestwrapper, "HTTP request");
        Args.notNull(httpclientcontext, "HTTP context");
        ConnectionRequest connectionrequest = connManager.requestConnection(httproute, null);
        if(httpexecutionaware != null)
        {
            if(httpexecutionaware.isAborted())
            {
                connectionrequest.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpexecutionaware.setCancellable(connectionrequest);
        }
        requestconfig = httpclientcontext.getRequestConfig();
        int i;
        long l;
        TimeUnit timeunit;
        ConnectionShutdownException connectionshutdownexception;
        InterruptedIOException interruptedioexception;
        try
        {
            i = requestconfig.getConnectionRequestTimeout();
        }
        catch(InterruptedException interruptedexception)
        {
            Thread.currentThread().interrupt();
            RequestAbortedException requestabortedexception1 = new RequestAbortedException("Request aborted", interruptedexception);
            throw requestabortedexception1;
        }
        catch(ExecutionException executionexception)
        {
            Object obj = executionexception.getCause();
            if(obj == null)
                obj = executionexception;
            RequestAbortedException requestabortedexception = new RequestAbortedException("Request execution failed", ((Throwable) (obj)));
            throw requestabortedexception;
        }
        if(i > 0)
            l = i;
        else
            l = 0L;
        timeunit = TimeUnit.MILLISECONDS;
        httpclientconnection = connectionrequest.get(l, timeunit);
        int i1;
        ConnectionHolder connectionholder = new ConnectionHolder(log, connManager, httpclientconnection);
        if(httpexecutionaware == null)
            break MISSING_BLOCK_LABEL_263;
        int j;
        org.apache.http.HttpRequest httprequest;
        boolean flag;
        HttpHost httphost;
        URI uri;
        boolean flag1;
        String s;
        int k;
        String s1;
        HttpResponse httpresponse;
        HttpEntity httpentity;
        CloseableHttpResponse closeablehttpresponse;
        HttpClientConnectionManager httpclientconnectionmanager;
        try
        {
            if(httpexecutionaware.isAborted())
            {
                connectionholder.close();
                throw new RequestAbortedException("Request aborted");
            }
        }
        // Misplaced declaration of an exception variable
        catch(ConnectionShutdownException connectionshutdownexception)
        {
            interruptedioexception = new InterruptedIOException("Connection has been shut down");
            interruptedioexception.initCause(connectionshutdownexception);
            throw interruptedioexception;
        }
        catch(HttpException httpexception)
        {
            connectionholder.abortConnection();
            throw httpexception;
        }
        catch(IOException ioexception)
        {
            connectionholder.abortConnection();
            throw ioexception;
        }
        catch(RuntimeException runtimeexception)
        {
            connectionholder.abortConnection();
            throw runtimeexception;
        }
        httpexecutionaware.setCancellable(connectionholder);
        if(httpclientconnection.isOpen())
            break MISSING_BLOCK_LABEL_317;
        i1 = requestconfig.getConnectTimeout();
        httpclientconnectionmanager = connManager;
        if(i1 <= 0)
            i1 = 0;
        httpclientconnectionmanager.connect(httpclientconnection, httproute, i1, httpclientcontext);
        connManager.routeComplete(httpclientconnection, httproute, httpclientcontext);
        j = requestconfig.getSocketTimeout();
        if(j < 0)
            break MISSING_BLOCK_LABEL_338;
        httpclientconnection.setSocketTimeout(j);
        httprequest = httprequestwrapper.getOriginal();
        flag = httprequest instanceof HttpUriRequest;
        httphost = null;
        if(!flag)
            break MISSING_BLOCK_LABEL_422;
        uri = ((HttpUriRequest)httprequest).getURI();
        flag1 = uri.isAbsolute();
        httphost = null;
        if(!flag1)
            break MISSING_BLOCK_LABEL_422;
        s = uri.getHost();
        k = uri.getPort();
        s1 = uri.getScheme();
        httphost = new HttpHost(s, k, s1);
        if(httphost != null)
            break MISSING_BLOCK_LABEL_433;
        httphost = httproute.getTargetHost();
        httpclientcontext.setAttribute("http.target_host", httphost);
        httpclientcontext.setAttribute("http.request", httprequestwrapper);
        httpclientcontext.setAttribute("http.connection", httpclientconnection);
        httpclientcontext.setAttribute("http.route", httproute);
        httpProcessor.process(httprequestwrapper, httpclientcontext);
        httpresponse = requestExecutor.execute(httprequestwrapper, httpclientconnection, httpclientcontext);
        httpProcessor.process(httpresponse, httpclientcontext);
        if(!reuseStrategy.keepAlive(httpresponse, httpclientcontext))
            break MISSING_BLOCK_LABEL_579;
        connectionholder.setValidFor(keepAliveStrategy.getKeepAliveDuration(httpresponse, httpclientcontext), TimeUnit.MILLISECONDS);
        connectionholder.markReusable();
_L1:
        httpentity = httpresponse.getEntity();
        if(httpentity == null)
            break MISSING_BLOCK_LABEL_567;
        if(httpentity.isStreaming())
            break MISSING_BLOCK_LABEL_597;
        connectionholder.releaseConnection();
        return Proxies.enhanceResponse(httpresponse, null);
        connectionholder.markNonReusable();
          goto _L1
        closeablehttpresponse = Proxies.enhanceResponse(httpresponse, connectionholder);
        return closeablehttpresponse;
    }

    private final HttpClientConnectionManager connManager;
    private final HttpProcessor httpProcessor;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log = LogFactory.getLog(getClass());
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
}
