// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.client.RedirectException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain, Proxies

public class RedirectExec
    implements ClientExecChain
{

    public RedirectExec(ClientExecChain clientexecchain, HttpRoutePlanner httprouteplanner, RedirectStrategy redirectstrategy)
    {
        Args.notNull(clientexecchain, "HTTP client request executor");
        Args.notNull(httprouteplanner, "HTTP route planner");
        Args.notNull(redirectstrategy, "HTTP redirect strategy");
        requestExecutor = clientexecchain;
        routePlanner = httprouteplanner;
        redirectStrategy = redirectstrategy;
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        HttpRoute httproute1;
        HttpRequestWrapper httprequestwrapper1;
        int j;
        CloseableHttpResponse closeablehttpresponse;
        Args.notNull(httproute, "HTTP route");
        Args.notNull(httprequestwrapper, "HTTP request");
        Args.notNull(httpclientcontext, "HTTP context");
        List list = httpclientcontext.getRedirectLocations();
        if(list != null)
            list.clear();
        RequestConfig requestconfig = httpclientcontext.getRequestConfig();
        int i;
        RuntimeException runtimeexception;
        if(requestconfig.getMaxRedirects() > 0)
            i = requestconfig.getMaxRedirects();
        else
            i = 50;
        httproute1 = httproute;
        httprequestwrapper1 = httprequestwrapper;
        j = 0;
_L4:
        closeablehttpresponse = requestExecutor.execute(httproute1, httprequestwrapper1, httpclientcontext, httpexecutionaware);
        if(!requestconfig.isRedirectsEnabled() || !redirectStrategy.isRedirected(httprequestwrapper1, closeablehttpresponse, httpclientcontext))
            break MISSING_BLOCK_LABEL_538;
        if(j < i)
            break MISSING_BLOCK_LABEL_171;
        throw new RedirectException((new StringBuilder()).append("Maximum redirects (").append(i).append(") exceeded").toString());
        runtimeexception;
        closeablehttpresponse.close();
        throw runtimeexception;
        j++;
        java.net.URI uri;
        HttpHost httphost;
        org.apache.http.client.methods.HttpUriRequest httpurirequest = redirectStrategy.getRedirect(httprequestwrapper1, closeablehttpresponse, httpclientcontext);
        if(!httpurirequest.headerIterator().hasNext())
            httpurirequest.setHeaders(httprequestwrapper.getOriginal().getAllHeaders());
        httprequestwrapper1 = HttpRequestWrapper.wrap(httpurirequest);
        if(httprequestwrapper1 instanceof HttpEntityEnclosingRequest)
            Proxies.enhanceEntity((HttpEntityEnclosingRequest)httprequestwrapper1);
        uri = httprequestwrapper1.getURI();
        httphost = URIUtils.extractHost(uri);
        if(httphost != null)
            break MISSING_BLOCK_LABEL_303;
        throw new ProtocolException((new StringBuilder()).append("Redirect URI does not specify a valid host name: ").append(uri).toString());
        IOException ioexception1;
        ioexception1;
        closeablehttpresponse.close();
        throw ioexception1;
        AuthState authstate;
        if(httproute1.getTargetHost().equals(httphost))
            break MISSING_BLOCK_LABEL_392;
        authstate = httpclientcontext.getTargetAuthState();
        if(authstate == null)
            break MISSING_BLOCK_LABEL_343;
        log.debug("Resetting target auth state");
        authstate.reset();
        AuthState authstate1 = httpclientcontext.getProxyAuthState();
        if(authstate1 == null)
            break MISSING_BLOCK_LABEL_392;
        AuthScheme authscheme = authstate1.getAuthScheme();
        if(authscheme == null)
            break MISSING_BLOCK_LABEL_392;
        if(authscheme.isConnectionBased())
        {
            log.debug("Resetting proxy auth state");
            authstate1.reset();
        }
        httproute1 = routePlanner.determineRoute(httphost, httprequestwrapper1, httpclientcontext);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Redirecting to '").append(uri).append("' via ").append(httproute1).toString());
        EntityUtils.consume(closeablehttpresponse.getEntity());
        closeablehttpresponse.close();
        continue; /* Loop/switch isn't completed */
        HttpException httpexception;
        httpexception;
        EntityUtils.consume(closeablehttpresponse.getEntity());
        closeablehttpresponse.close();
_L2:
        throw httpexception;
        IOException ioexception;
        ioexception;
        log.debug("I/O error while releasing connection", ioexception);
        closeablehttpresponse.close();
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        closeablehttpresponse.close();
        throw exception;
        return closeablehttpresponse;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private final Log log = LogFactory.getLog(getClass());
    private final RedirectStrategy redirectStrategy;
    private final ClientExecChain requestExecutor;
    private final HttpRoutePlanner routePlanner;
}
