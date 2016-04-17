// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.impl.client:
//            AuthenticationStrategyAdaptor, HttpAuthenticator, DefaultRedirectStrategyAdaptor, RoutedRequest, 
//            RequestWrapper, EntityEnclosingRequestWrapper, TunnelRefusedException

public class DefaultRequestDirector
    implements RequestDirector
{

    public DefaultRequestDirector(Log log1, HttpRequestExecutor httprequestexecutor, ClientConnectionManager clientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, HttpRoutePlanner httprouteplanner, HttpProcessor httpprocessor, 
            HttpRequestRetryHandler httprequestretryhandler, RedirectStrategy redirectstrategy, AuthenticationHandler authenticationhandler, AuthenticationHandler authenticationhandler1, UserTokenHandler usertokenhandler, HttpParams httpparams)
    {
        this(LogFactory.getLog(org/apache/http/impl/client/DefaultRequestDirector), httprequestexecutor, clientconnectionmanager, connectionreusestrategy, connectionkeepalivestrategy, httprouteplanner, httpprocessor, httprequestretryhandler, redirectstrategy, ((AuthenticationStrategy) (new AuthenticationStrategyAdaptor(authenticationhandler))), ((AuthenticationStrategy) (new AuthenticationStrategyAdaptor(authenticationhandler1))), usertokenhandler, httpparams);
    }

    public DefaultRequestDirector(Log log1, HttpRequestExecutor httprequestexecutor, ClientConnectionManager clientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, HttpRoutePlanner httprouteplanner, HttpProcessor httpprocessor, 
            HttpRequestRetryHandler httprequestretryhandler, RedirectStrategy redirectstrategy, AuthenticationStrategy authenticationstrategy, AuthenticationStrategy authenticationstrategy1, UserTokenHandler usertokenhandler, HttpParams httpparams)
    {
        Args.notNull(log1, "Log");
        Args.notNull(httprequestexecutor, "Request executor");
        Args.notNull(clientconnectionmanager, "Client connection manager");
        Args.notNull(connectionreusestrategy, "Connection reuse strategy");
        Args.notNull(connectionkeepalivestrategy, "Connection keep alive strategy");
        Args.notNull(httprouteplanner, "Route planner");
        Args.notNull(httpprocessor, "HTTP protocol processor");
        Args.notNull(httprequestretryhandler, "HTTP request retry handler");
        Args.notNull(redirectstrategy, "Redirect strategy");
        Args.notNull(authenticationstrategy, "Target authentication strategy");
        Args.notNull(authenticationstrategy1, "Proxy authentication strategy");
        Args.notNull(usertokenhandler, "User token handler");
        Args.notNull(httpparams, "HTTP parameters");
        log = log1;
        authenticator = new HttpAuthenticator(log1);
        requestExec = httprequestexecutor;
        connManager = clientconnectionmanager;
        reuseStrategy = connectionreusestrategy;
        keepAliveStrategy = connectionkeepalivestrategy;
        routePlanner = httprouteplanner;
        httpProcessor = httpprocessor;
        retryHandler = httprequestretryhandler;
        redirectStrategy = redirectstrategy;
        targetAuthStrategy = authenticationstrategy;
        proxyAuthStrategy = authenticationstrategy1;
        userTokenHandler = usertokenhandler;
        params = httpparams;
        if(redirectstrategy instanceof DefaultRedirectStrategyAdaptor)
            redirectHandler = ((DefaultRedirectStrategyAdaptor)redirectstrategy).getHandler();
        else
            redirectHandler = null;
        if(authenticationstrategy instanceof AuthenticationStrategyAdaptor)
            targetAuthHandler = ((AuthenticationStrategyAdaptor)authenticationstrategy).getHandler();
        else
            targetAuthHandler = null;
        if(authenticationstrategy1 instanceof AuthenticationStrategyAdaptor)
            proxyAuthHandler = ((AuthenticationStrategyAdaptor)authenticationstrategy1).getHandler();
        else
            proxyAuthHandler = null;
        managedConn = null;
        execCount = 0;
        redirectCount = 0;
        targetAuthState = new AuthState();
        proxyAuthState = new AuthState();
        maxRedirects = params.getIntParameter("http.protocol.max-redirects", 100);
    }

    public DefaultRequestDirector(HttpRequestExecutor httprequestexecutor, ClientConnectionManager clientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, HttpRoutePlanner httprouteplanner, HttpProcessor httpprocessor, HttpRequestRetryHandler httprequestretryhandler, 
            RedirectHandler redirecthandler, AuthenticationHandler authenticationhandler, AuthenticationHandler authenticationhandler1, UserTokenHandler usertokenhandler, HttpParams httpparams)
    {
        this(LogFactory.getLog(org/apache/http/impl/client/DefaultRequestDirector), httprequestexecutor, clientconnectionmanager, connectionreusestrategy, connectionkeepalivestrategy, httprouteplanner, httpprocessor, httprequestretryhandler, ((RedirectStrategy) (new DefaultRedirectStrategyAdaptor(redirecthandler))), ((AuthenticationStrategy) (new AuthenticationStrategyAdaptor(authenticationhandler))), ((AuthenticationStrategy) (new AuthenticationStrategyAdaptor(authenticationhandler1))), usertokenhandler, httpparams);
    }

    private void abortConnection()
    {
        ManagedClientConnection managedclientconnection = managedConn;
        if(managedclientconnection == null) goto _L2; else goto _L1
_L1:
        managedConn = null;
        managedclientconnection.abortConnection();
_L4:
        managedclientconnection.releaseConnection();
_L2:
        return;
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug(ioexception.getMessage(), ioexception);
        if(true) goto _L4; else goto _L3
_L3:
        IOException ioexception1;
        ioexception1;
        log.debug("Error releasing connection", ioexception1);
        return;
    }

    private void tryConnect(RoutedRequest routedrequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpRoute httproute;
        RequestWrapper requestwrapper;
        int i;
        httproute = routedrequest.getRoute();
        requestwrapper = routedrequest.getRequest();
        i = 0;
_L4:
        httpcontext.setAttribute("http.request", requestwrapper);
        i++;
        if(managedConn.isOpen())
            break MISSING_BLOCK_LABEL_61;
        managedConn.open(httproute, httpcontext, params);
_L2:
        establishRoute(httproute, httpcontext);
        return;
        managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(params));
        if(true) goto _L2; else goto _L1
_L1:
        IOException ioexception;
        ioexception;
        try
        {
            managedConn.close();
        }
        catch(IOException ioexception1) { }
        if(!retryHandler.retryRequest(ioexception, i, httpcontext))
            break; /* Loop/switch isn't completed */
        if(log.isInfoEnabled())
        {
            log.info((new StringBuilder()).append("I/O exception (").append(ioexception.getClass().getName()).append(") caught when connecting to the target host: ").append(ioexception.getMessage()).toString());
            if(log.isDebugEnabled())
                log.debug(ioexception.getMessage(), ioexception);
            log.info("Retrying connect");
        }
        if(true) goto _L4; else goto _L3
_L3:
        throw ioexception;
    }

    private HttpResponse tryExecute(RoutedRequest routedrequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        RequestWrapper requestwrapper;
        HttpRoute httproute;
        Object obj;
        requestwrapper = routedrequest.getRequest();
        httproute = routedrequest.getRoute();
        obj = null;
_L2:
        execCount = 1 + execCount;
        requestwrapper.incrementExecCount();
        if(!requestwrapper.isRepeatable())
        {
            log.debug("Cannot retry non-repeatable request");
            if(obj != null)
                throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", ((Throwable) (obj)));
            else
                throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
        }
label0:
        {
            if(!managedConn.isOpen())
            {
                if(httproute.isTunnelled())
                    break label0;
                log.debug("Reopening the direct connection.");
                managedConn.open(httproute, httpcontext, params);
            }
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Attempt ").append(execCount).append(" to execute request").toString());
            return requestExec.execute(requestwrapper, managedConn, httpcontext);
        }
        log.debug("Proxied connection. Need to start over.");
        return null;
        IOException ioexception;
        ioexception;
        log.debug("Closing the connection.");
        try
        {
            managedConn.close();
        }
        catch(IOException ioexception1) { }
        if(retryHandler.retryRequest(ioexception, requestwrapper.getExecCount(), httpcontext))
        {
            if(log.isInfoEnabled())
                log.info((new StringBuilder()).append("I/O exception (").append(ioexception.getClass().getName()).append(") caught when processing request: ").append(ioexception.getMessage()).toString());
            if(log.isDebugEnabled())
                log.debug(ioexception.getMessage(), ioexception);
            log.info("Retrying request");
            obj = ioexception;
        } else
        {
            throw ioexception;
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    private RequestWrapper wrapRequest(HttpRequest httprequest)
        throws ProtocolException
    {
        if(httprequest instanceof HttpEntityEnclosingRequest)
            return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httprequest);
        else
            return new RequestWrapper(httprequest);
    }

    protected HttpRequest createConnectRequest(HttpRoute httproute, HttpContext httpcontext)
    {
        HttpHost httphost = httproute.getTargetHost();
        String s = httphost.getHostName();
        int i = httphost.getPort();
        if(i < 0)
            i = connManager.getSchemeRegistry().getScheme(httphost.getSchemeName()).getDefaultPort();
        StringBuilder stringbuilder = new StringBuilder(6 + s.length());
        stringbuilder.append(s);
        stringbuilder.append(':');
        stringbuilder.append(Integer.toString(i));
        return new BasicHttpRequest("CONNECT", stringbuilder.toString(), HttpProtocolParams.getVersion(params));
    }

    protected boolean createTunnelToProxy(HttpRoute httproute, int i, HttpContext httpcontext)
        throws HttpException, IOException
    {
        throw new HttpException("Proxy chains are not supported.");
    }

    protected boolean createTunnelToTarget(HttpRoute httproute, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpHost httphost = httproute.getProxyHost();
        HttpHost httphost1 = httproute.getTargetHost();
        HttpResponse httpresponse;
        do
        {
            do
            {
                if(!managedConn.isOpen())
                    managedConn.open(httproute, httpcontext, params);
                HttpRequest httprequest = createConnectRequest(httproute, httpcontext);
                httprequest.setParams(params);
                httpcontext.setAttribute("http.target_host", httphost1);
                httpcontext.setAttribute("http.proxy_host", httphost);
                httpcontext.setAttribute("http.connection", managedConn);
                httpcontext.setAttribute("http.request", httprequest);
                requestExec.preProcess(httprequest, httpProcessor, httpcontext);
                httpresponse = requestExec.execute(httprequest, managedConn, httpcontext);
                httpresponse.setParams(params);
                requestExec.postProcess(httpresponse, httpProcessor, httpcontext);
                if(httpresponse.getStatusLine().getStatusCode() < 200)
                    throw new HttpException((new StringBuilder()).append("Unexpected response to CONNECT request: ").append(httpresponse.getStatusLine()).toString());
            } while(!HttpClientParams.isAuthenticating(params));
            if(!authenticator.isAuthenticationRequested(httphost, httpresponse, proxyAuthStrategy, proxyAuthState, httpcontext) || !authenticator.authenticate(httphost, httpresponse, proxyAuthStrategy, proxyAuthState, httpcontext))
                break;
            if(reuseStrategy.keepAlive(httpresponse, httpcontext))
            {
                log.debug("Connection kept alive");
                EntityUtils.consume(httpresponse.getEntity());
            } else
            {
                managedConn.close();
            }
        } while(true);
        if(httpresponse.getStatusLine().getStatusCode() > 299)
        {
            HttpEntity httpentity = httpresponse.getEntity();
            if(httpentity != null)
                httpresponse.setEntity(new BufferedHttpEntity(httpentity));
            managedConn.close();
            throw new TunnelRefusedException((new StringBuilder()).append("CONNECT refused by proxy: ").append(httpresponse.getStatusLine()).toString(), httpresponse);
        } else
        {
            managedConn.markReusable();
            return false;
        }
    }

    protected HttpRoute determineRoute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        HttpRoutePlanner httprouteplanner = routePlanner;
        if(httphost == null)
            httphost = (HttpHost)httprequest.getParams().getParameter("http.default-host");
        return httprouteplanner.determineRoute(httphost, httprequest, httpcontext);
    }

    protected void establishRoute(HttpRoute httproute, HttpContext httpcontext)
        throws HttpException, IOException
    {
        BasicRouteDirector basicroutedirector = new BasicRouteDirector();
_L9:
        HttpRoute httproute1;
        int i;
        httproute1 = managedConn.getRoute();
        i = basicroutedirector.nextStep(httproute, httproute1);
        i;
        JVM INSTR tableswitch -1 5: default 76
    //                   -1 242
    //                   0 126
    //                   1 111
    //                   2 111
    //                   3 132
    //                   4 170
    //                   5 225;
           goto _L1 _L2 _L3 _L4 _L4 _L5 _L6 _L7
_L1:
        throw new IllegalStateException((new StringBuilder()).append("Unknown step indicator ").append(i).append(" from RouteDirector.").toString());
_L4:
        managedConn.open(httproute, httpcontext, params);
_L3:
        if(i > 0) goto _L9; else goto _L8
_L8:
        return;
_L5:
        boolean flag1 = createTunnelToTarget(httproute, httpcontext);
        log.debug("Tunnel to target created.");
        managedConn.tunnelTarget(flag1, params);
          goto _L3
_L6:
        int j = -1 + httproute1.getHopCount();
        boolean flag = createTunnelToProxy(httproute, j, httpcontext);
        log.debug("Tunnel to proxy created.");
        managedConn.tunnelProxy(httproute.getHopTarget(j), flag, params);
          goto _L3
_L7:
        managedConn.layerProtocol(httpcontext, params);
          goto _L3
          goto _L9
_L2:
        throw new HttpException((new StringBuilder()).append("Unable to establish route: planned = ").append(httproute).append("; current = ").append(httproute1).toString());
    }

    public HttpResponse execute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpHost httphost1;
        RoutedRequest routedrequest;
        boolean flag;
        HttpResponse httpresponse;
        RequestWrapper requestwrapper1;
        String s1;
        RoutedRequest routedrequest1;
        httpcontext.setAttribute("http.auth.target-scope", targetAuthState);
        httpcontext.setAttribute("http.auth.proxy-scope", proxyAuthState);
        httphost1 = httphost;
        RequestWrapper requestwrapper = wrapRequest(httprequest);
        requestwrapper.setParams(params);
        HttpRoute httproute = determineRoute(httphost1, requestwrapper, httpcontext);
        virtualHost = (HttpHost)requestwrapper.getParams().getParameter("http.virtual-host");
        if(virtualHost != null && virtualHost.getPort() == -1)
        {
            boolean flag1;
            ConnectionShutdownException connectionshutdownexception;
            InterruptedIOException interruptedioexception;
            HttpRoute httproute1;
            Object obj;
            String s;
            AuthState authstate;
            BasicScheme basicscheme;
            UsernamePasswordCredentials usernamepasswordcredentials;
            HttpParams httpparams;
            HttpRequestExecutor httprequestexecutor;
            HttpProcessor httpprocessor;
            long l;
            ClientConnectionRequest clientconnectionrequest;
            long l1;
            HttpHost httphost2;
            int i;
            if(httphost1 != null)
                httphost2 = httphost1;
            else
                httphost2 = httproute.getTargetHost();
            i = httphost2.getPort();
            if(i != -1)
            {
                HttpHost httphost3 = new HttpHost(virtualHost.getHostName(), i, virtualHost.getSchemeName());
                virtualHost = httphost3;
            }
        }
        routedrequest = new RoutedRequest(requestwrapper, httproute);
        flag = false;
        flag1 = false;
        httpresponse = null;
        if(flag1) goto _L2; else goto _L1
_L1:
        requestwrapper1 = routedrequest.getRequest();
        httproute1 = routedrequest.getRoute();
        obj = httpcontext.getAttribute("http.user-token");
        if(managedConn != null)
            break MISSING_BLOCK_LABEL_331;
        clientconnectionrequest = connManager.requestConnection(httproute1, obj);
        if(httprequest instanceof AbortableHttpRequest)
            ((AbortableHttpRequest)httprequest).setConnectionRequest(clientconnectionrequest);
        l1 = HttpClientParams.getConnectionManagerTimeout(params);
        managedConn = clientconnectionrequest.getConnection(l1, TimeUnit.MILLISECONDS);
        if(HttpConnectionParams.isStaleCheckingEnabled(params) && managedConn.isOpen())
        {
            log.debug("Stale connection check");
            if(managedConn.isStale())
            {
                log.debug("Stale connection detected");
                managedConn.close();
            }
        }
        if(httprequest instanceof AbortableHttpRequest)
            ((AbortableHttpRequest)httprequest).setReleaseTrigger(managedConn);
        tryConnect(routedrequest, httpcontext);
        s = requestwrapper1.getURI().getUserInfo();
        if(s == null)
            break MISSING_BLOCK_LABEL_408;
        authstate = targetAuthState;
        basicscheme = new BasicScheme();
        usernamepasswordcredentials = new UsernamePasswordCredentials(s);
        authstate.update(basicscheme, usernamepasswordcredentials);
        if(virtualHost == null) goto _L4; else goto _L3
_L3:
        httphost1 = virtualHost;
_L16:
        if(httphost1 != null)
            break MISSING_BLOCK_LABEL_433;
        httphost1 = httproute1.getTargetHost();
        requestwrapper1.resetHeaders();
        rewriteRequestURI(requestwrapper1, httproute1);
        httpcontext.setAttribute("http.target_host", httphost1);
        httpcontext.setAttribute("http.route", httproute1);
        httpcontext.setAttribute("http.connection", managedConn);
        requestExec.preProcess(requestwrapper1, httpProcessor, httpcontext);
        httpresponse = tryExecute(routedrequest, httpcontext);
        if(httpresponse == null)
            break MISSING_BLOCK_LABEL_169;
        BasicManagedEntity basicmanagedentity;
        TunnelRefusedException tunnelrefusedexception;
        URI uri;
        InterruptedException interruptedexception;
        try
        {
            httpparams = params;
            httpresponse.setParams(httpparams);
            httprequestexecutor = requestExec;
            httpprocessor = httpProcessor;
            httprequestexecutor.postProcess(httpresponse, httpprocessor, httpcontext);
            flag = reuseStrategy.keepAlive(httpresponse, httpcontext);
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
            abortConnection();
            throw httpexception;
        }
        catch(IOException ioexception)
        {
            abortConnection();
            throw ioexception;
        }
        catch(RuntimeException runtimeexception)
        {
            abortConnection();
            throw runtimeexception;
        }
        if(!flag) goto _L6; else goto _L5
_L5:
        l = keepAliveStrategy.getKeepAliveDuration(httpresponse, httpcontext);
        if(!log.isDebugEnabled()) goto _L8; else goto _L7
_L7:
        if(l <= 0L) goto _L10; else goto _L9
_L9:
        s1 = (new StringBuilder()).append("for ").append(l).append(" ").append(TimeUnit.MILLISECONDS).toString();
_L18:
        log.debug((new StringBuilder()).append("Connection can be kept alive ").append(s1).toString());
_L8:
        managedConn.setIdleDuration(l, TimeUnit.MILLISECONDS);
_L6:
        routedrequest1 = handleResponse(routedrequest, httpresponse, httpcontext);
        if(routedrequest1 != null) goto _L12; else goto _L11
_L11:
        flag1 = true;
_L19:
        if(managedConn == null)
            break MISSING_BLOCK_LABEL_169;
        if(obj != null)
            break MISSING_BLOCK_LABEL_731;
        obj = userTokenHandler.getUserToken(httpcontext);
        httpcontext.setAttribute("http.user-token", obj);
        if(obj == null)
            break MISSING_BLOCK_LABEL_169;
        managedConn.setState(obj);
        break MISSING_BLOCK_LABEL_169;
        interruptedexception;
        Thread.currentThread().interrupt();
        throw new InterruptedIOException();
        tunnelrefusedexception;
        if(log.isDebugEnabled())
            log.debug(tunnelrefusedexception.getMessage());
        httpresponse = tunnelrefusedexception.getResponse();
_L2:
        if(httpresponse == null) goto _L14; else goto _L13
_L13:
        if(httpresponse.getEntity() != null && httpresponse.getEntity().isStreaming()) goto _L15; else goto _L14
_L14:
        if(!flag)
            break MISSING_BLOCK_LABEL_889;
        managedConn.markReusable();
        releaseConnection();
        return httpresponse;
_L4:
        uri = requestwrapper1.getURI();
        if(uri.isAbsolute())
            httphost1 = URIUtils.extractHost(uri);
          goto _L16
_L12:
        if(!flag)
            break MISSING_BLOCK_LABEL_968;
        EntityUtils.consume(httpresponse.getEntity());
        managedConn.markReusable();
_L17:
        if(!routedrequest1.getRoute().equals(routedrequest.getRoute()))
            releaseConnection();
        break MISSING_BLOCK_LABEL_1160;
        managedConn.close();
        if(proxyAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0 && proxyAuthState.getAuthScheme() != null && proxyAuthState.getAuthScheme().isConnectionBased())
        {
            log.debug("Resetting proxy auth state");
            proxyAuthState.reset();
        }
        if(targetAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0 && targetAuthState.getAuthScheme() != null && targetAuthState.getAuthScheme().isConnectionBased())
        {
            log.debug("Resetting target auth state");
            targetAuthState.reset();
        }
          goto _L17
_L15:
        basicmanagedentity = new BasicManagedEntity(httpresponse.getEntity(), managedConn, flag);
        httpresponse.setEntity(basicmanagedentity);
        return httpresponse;
_L10:
        s1 = "indefinitely";
          goto _L18
        routedrequest = routedrequest1;
          goto _L19
    }

    protected RoutedRequest handleResponse(RoutedRequest routedrequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpRoute httproute;
        RequestWrapper requestwrapper;
        HttpParams httpparams;
        httproute = routedrequest.getRoute();
        requestwrapper = routedrequest.getRequest();
        httpparams = requestwrapper.getParams();
        if(!HttpClientParams.isAuthenticating(httpparams)) goto _L2; else goto _L1
_L1:
        HttpHost httphost1;
        httphost1 = (HttpHost)httpcontext.getAttribute("http.target_host");
        if(httphost1 == null)
            httphost1 = httproute.getTargetHost();
        if(httphost1.getPort() < 0)
        {
            Scheme scheme = connManager.getSchemeRegistry().getScheme(httphost1);
            HttpHost httphost3 = new HttpHost(httphost1.getHostName(), scheme.getDefaultPort(), httphost1.getSchemeName());
            httphost1 = httphost3;
        }
        if(!authenticator.isAuthenticationRequested(httphost1, httpresponse, targetAuthStrategy, targetAuthState, httpcontext) || !authenticator.authenticate(httphost1, httpresponse, targetAuthStrategy, targetAuthState, httpcontext)) goto _L4; else goto _L3
_L3:
        return routedrequest;
_L4:
        HttpHost httphost2;
        httphost2 = httproute.getProxyHost();
        if(!authenticator.isAuthenticationRequested(httphost2, httpresponse, proxyAuthStrategy, proxyAuthState, httpcontext))
            break; /* Loop/switch isn't completed */
        if(httphost2 == null)
            httphost2 = httproute.getTargetHost();
        if(authenticator.authenticate(httphost2, httpresponse, proxyAuthStrategy, proxyAuthState, httpcontext)) goto _L3; else goto _L2
_L2:
        if(HttpClientParams.isRedirecting(httpparams) && redirectStrategy.isRedirected(requestwrapper, httpresponse, httpcontext))
        {
            if(redirectCount >= maxRedirects)
                throw new RedirectException((new StringBuilder()).append("Maximum redirects (").append(maxRedirects).append(") exceeded").toString());
            redirectCount = 1 + redirectCount;
            virtualHost = null;
            HttpUriRequest httpurirequest = redirectStrategy.getRedirect(requestwrapper, httpresponse, httpcontext);
            httpurirequest.setHeaders(requestwrapper.getOriginal().getAllHeaders());
            URI uri = httpurirequest.getURI();
            HttpHost httphost = URIUtils.extractHost(uri);
            if(httphost == null)
                throw new ProtocolException((new StringBuilder()).append("Redirect URI does not specify a valid host name: ").append(uri).toString());
            if(!httproute.getTargetHost().equals(httphost))
            {
                log.debug("Resetting target auth state");
                targetAuthState.reset();
                AuthScheme authscheme = proxyAuthState.getAuthScheme();
                if(authscheme != null && authscheme.isConnectionBased())
                {
                    log.debug("Resetting proxy auth state");
                    proxyAuthState.reset();
                }
            }
            RequestWrapper requestwrapper1 = wrapRequest(httpurirequest);
            requestwrapper1.setParams(httpparams);
            HttpRoute httproute1 = determineRoute(httphost, requestwrapper1, httpcontext);
            RoutedRequest routedrequest1 = new RoutedRequest(requestwrapper1, httproute1);
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Redirecting to '").append(uri).append("' via ").append(httproute1).toString());
            return routedrequest1;
        } else
        {
            return null;
        }
    }

    protected void releaseConnection()
    {
        try
        {
            managedConn.releaseConnection();
        }
        catch(IOException ioexception)
        {
            log.debug("IOException releasing connection", ioexception);
        }
        managedConn = null;
    }

    protected void rewriteRequestURI(RequestWrapper requestwrapper, HttpRoute httproute)
        throws ProtocolException
    {
        URI uri = requestwrapper.getURI();
        if(httproute.getProxyHost() == null || httproute.isTunnelled()) goto _L2; else goto _L1
_L1:
        if(uri.isAbsolute()) goto _L4; else goto _L3
_L3:
        URI uri2 = URIUtils.rewriteURI(uri, httproute.getTargetHost(), true);
_L5:
        requestwrapper.setURI(uri2);
        return;
_L4:
        URI uri1;
        try
        {
            uri2 = URIUtils.rewriteURI(uri);
        }
        catch(URISyntaxException urisyntaxexception)
        {
            throw new ProtocolException((new StringBuilder()).append("Invalid URI: ").append(requestwrapper.getRequestLine().getUri()).toString(), urisyntaxexception);
        }
          goto _L5
_L2:
label0:
        {
            if(!uri.isAbsolute())
                break label0;
            uri2 = URIUtils.rewriteURI(uri, null, true);
        }
          goto _L5
        uri1 = URIUtils.rewriteURI(uri);
        uri2 = uri1;
          goto _L5
    }

    private final HttpAuthenticator authenticator;
    protected final ClientConnectionManager connManager;
    private int execCount;
    protected final HttpProcessor httpProcessor;
    protected final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log;
    protected ManagedClientConnection managedConn;
    private final int maxRedirects;
    protected final HttpParams params;
    protected final AuthenticationHandler proxyAuthHandler;
    protected final AuthState proxyAuthState;
    protected final AuthenticationStrategy proxyAuthStrategy;
    private int redirectCount;
    protected final RedirectHandler redirectHandler;
    protected final RedirectStrategy redirectStrategy;
    protected final HttpRequestExecutor requestExec;
    protected final HttpRequestRetryHandler retryHandler;
    protected final ConnectionReuseStrategy reuseStrategy;
    protected final HttpRoutePlanner routePlanner;
    protected final AuthenticationHandler targetAuthHandler;
    protected final AuthState targetAuthState;
    protected final AuthenticationStrategy targetAuthStrategy;
    protected final UserTokenHandler userTokenHandler;
    private HttpHost virtualHost;
}
