// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.*;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.auth.HttpAuthenticator;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.*;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain, TunnelRefusedException, Proxies, RequestAbortedException, 
//            ConnectionHolder

public class MainClientExec
    implements ClientExecChain
{

    public MainClientExec(HttpRequestExecutor httprequestexecutor, HttpClientConnectionManager httpclientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, AuthenticationStrategy authenticationstrategy, AuthenticationStrategy authenticationstrategy1, UserTokenHandler usertokenhandler)
    {
        Args.notNull(httprequestexecutor, "HTTP request executor");
        Args.notNull(httpclientconnectionmanager, "Client connection manager");
        Args.notNull(connectionreusestrategy, "Connection reuse strategy");
        Args.notNull(connectionkeepalivestrategy, "Connection keep alive strategy");
        Args.notNull(authenticationstrategy, "Target authentication strategy");
        Args.notNull(authenticationstrategy1, "Proxy authentication strategy");
        Args.notNull(usertokenhandler, "User token handler");
        HttpRequestInterceptor ahttprequestinterceptor[] = new HttpRequestInterceptor[2];
        ahttprequestinterceptor[0] = new RequestClientConnControl();
        ahttprequestinterceptor[1] = new RequestUserAgent();
        proxyHttpProcessor = new ImmutableHttpProcessor(ahttprequestinterceptor);
        requestExecutor = httprequestexecutor;
        connManager = httpclientconnectionmanager;
        reuseStrategy = connectionreusestrategy;
        keepAliveStrategy = connectionkeepalivestrategy;
        targetAuthStrategy = authenticationstrategy;
        proxyAuthStrategy = authenticationstrategy1;
        userTokenHandler = usertokenhandler;
    }

    private boolean createTunnelToProxy(HttpRoute httproute, int i, HttpClientContext httpclientcontext)
        throws HttpException
    {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean createTunnelToTarget(AuthState authstate, HttpClientConnection httpclientconnection, HttpRoute httproute, HttpRequest httprequest, HttpClientContext httpclientcontext)
        throws HttpException, IOException
    {
        RequestConfig requestconfig = httpclientcontext.getRequestConfig();
        int i = requestconfig.getConnectTimeout();
        HttpHost httphost = httproute.getTargetHost();
        HttpHost httphost1 = httproute.getProxyHost();
        BasicHttpRequest basichttprequest = new BasicHttpRequest("CONNECT", httphost.toHostString(), httprequest.getProtocolVersion());
        requestExecutor.preProcess(basichttprequest, proxyHttpProcessor, httpclientcontext);
        HttpResponse httpresponse;
        do
        {
            do
            {
                if(!httpclientconnection.isOpen())
                {
                    HttpClientConnectionManager httpclientconnectionmanager = connManager;
                    int j;
                    if(i > 0)
                        j = i;
                    else
                        j = 0;
                    httpclientconnectionmanager.connect(httpclientconnection, httproute, j, httpclientcontext);
                }
                basichttprequest.removeHeaders("Proxy-Authorization");
                authenticator.generateAuthResponse(basichttprequest, authstate, httpclientcontext);
                httpresponse = requestExecutor.execute(basichttprequest, httpclientconnection, httpclientcontext);
                if(httpresponse.getStatusLine().getStatusCode() < 200)
                    throw new HttpException((new StringBuilder()).append("Unexpected response to CONNECT request: ").append(httpresponse.getStatusLine()).toString());
            } while(!requestconfig.isAuthenticationEnabled());
            if(!authenticator.isAuthenticationRequested(httphost1, httpresponse, proxyAuthStrategy, authstate, httpclientcontext) || !authenticator.handleAuthChallenge(httphost1, httpresponse, proxyAuthStrategy, authstate, httpclientcontext))
                break;
            if(reuseStrategy.keepAlive(httpresponse, httpclientcontext))
            {
                log.debug("Connection kept alive");
                EntityUtils.consume(httpresponse.getEntity());
            } else
            {
                httpclientconnection.close();
            }
        } while(true);
        if(httpresponse.getStatusLine().getStatusCode() > 299)
        {
            HttpEntity httpentity = httpresponse.getEntity();
            if(httpentity != null)
                httpresponse.setEntity(new BufferedHttpEntity(httpentity));
            httpclientconnection.close();
            throw new TunnelRefusedException((new StringBuilder()).append("CONNECT refused by proxy: ").append(httpresponse.getStatusLine()).toString(), httpresponse);
        } else
        {
            return false;
        }
    }

    private boolean needAuthentication(AuthState authstate, AuthState authstate1, HttpRoute httproute, HttpResponse httpresponse, HttpClientContext httpclientcontext)
    {
        if(httpclientcontext.getRequestConfig().isAuthenticationEnabled())
        {
            HttpHost httphost = httpclientcontext.getTargetHost();
            if(httphost == null)
                httphost = httproute.getTargetHost();
            if(httphost.getPort() < 0)
                httphost = new HttpHost(httphost.getHostName(), httproute.getTargetHost().getPort(), httphost.getSchemeName());
            if(authenticator.isAuthenticationRequested(httphost, httpresponse, targetAuthStrategy, authstate, httpclientcontext))
                return authenticator.handleAuthChallenge(httphost, httpresponse, targetAuthStrategy, authstate, httpclientcontext);
            HttpHost httphost1 = httproute.getProxyHost();
            if(authenticator.isAuthenticationRequested(httphost1, httpresponse, proxyAuthStrategy, authstate1, httpclientcontext))
            {
                if(httphost1 == null)
                    httphost1 = httproute.getTargetHost();
                return authenticator.handleAuthChallenge(httphost1, httpresponse, proxyAuthStrategy, authstate1, httpclientcontext);
            }
        }
        return false;
    }

    void establishRoute(AuthState authstate, HttpClientConnection httpclientconnection, HttpRoute httproute, HttpRequest httprequest, HttpClientContext httpclientcontext)
        throws HttpException, IOException
    {
        int i;
        RouteTracker routetracker;
        i = httpclientcontext.getRequestConfig().getConnectTimeout();
        routetracker = new RouteTracker(httproute);
_L10:
        HttpRoute httproute1;
        int j;
        httproute1 = routetracker.toRoute();
        j = routeDirector.nextStep(httproute, httproute1);
        j;
        JVM INSTR tableswitch -1 5: default 84
    //                   -1 323
    //                   0 362
    //                   1 119
    //                   2 168
    //                   3 215
    //                   4 250
    //                   5 298;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8
_L3:
        break MISSING_BLOCK_LABEL_362;
_L5:
        break; /* Loop/switch isn't completed */
_L1:
        throw new IllegalStateException((new StringBuilder()).append("Unknown step indicator ").append(j).append(" from RouteDirector.").toString());
_L4:
        HttpClientConnectionManager httpclientconnectionmanager1 = connManager;
        int i1;
        if(i > 0)
            i1 = i;
        else
            i1 = 0;
        httpclientconnectionmanager1.connect(httpclientconnection, httproute, i1, httpclientcontext);
        routetracker.connectTarget(httproute.isSecure());
_L11:
        if(j <= 0)
            return;
        if(true) goto _L10; else goto _L9
_L9:
        HttpClientConnectionManager httpclientconnectionmanager = connManager;
        int l;
        if(i > 0)
            l = i;
        else
            l = 0;
        httpclientconnectionmanager.connect(httpclientconnection, httproute, l, httpclientcontext);
        routetracker.connectProxy(httproute.getProxyHost(), false);
          goto _L11
_L6:
        boolean flag1 = createTunnelToTarget(authstate, httpclientconnection, httproute, httprequest, httpclientcontext);
        log.debug("Tunnel to target created.");
        routetracker.tunnelTarget(flag1);
          goto _L11
_L7:
        int k = -1 + httproute1.getHopCount();
        boolean flag = createTunnelToProxy(httproute, k, httpclientcontext);
        log.debug("Tunnel to proxy created.");
        routetracker.tunnelProxy(httproute.getHopTarget(k), flag);
          goto _L11
_L8:
        connManager.upgrade(httpclientconnection, httproute, httpclientcontext);
        routetracker.layerProtocol(httproute.isSecure());
          goto _L11
_L2:
        throw new HttpException((new StringBuilder()).append("Unable to establish route: planned = ").append(httproute).append("; current = ").append(httproute1).toString());
        connManager.routeComplete(httpclientconnection, httproute, httpclientcontext);
          goto _L11
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        AuthState authstate;
        AuthState authstate1;
        Object obj;
        RequestConfig requestconfig;
        HttpClientConnection httpclientconnection;
        Args.notNull(httproute, "HTTP route");
        Args.notNull(httprequestwrapper, "HTTP request");
        Args.notNull(httpclientcontext, "HTTP context");
        authstate = httpclientcontext.getTargetAuthState();
        if(authstate == null)
        {
            authstate = new AuthState();
            httpclientcontext.setAttribute("http.auth.target-scope", authstate);
        }
        authstate1 = httpclientcontext.getProxyAuthState();
        if(authstate1 == null)
        {
            authstate1 = new AuthState();
            httpclientcontext.setAttribute("http.auth.proxy-scope", authstate1);
        }
        if(httprequestwrapper instanceof HttpEntityEnclosingRequest)
            Proxies.enhanceEntity((HttpEntityEnclosingRequest)httprequestwrapper);
        obj = httpclientcontext.getUserToken();
        ConnectionRequest connectionrequest = connManager.requestConnection(httproute, obj);
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
        ConnectionShutdownException connectionshutdownexception;
        InterruptedIOException interruptedioexception;
        try
        {
            i = requestconfig.getConnectionRequestTimeout();
        }
        catch(InterruptedException interruptedexception)
        {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", interruptedexception);
        }
        catch(ExecutionException executionexception)
        {
            Object obj1 = executionexception.getCause();
            if(obj1 == null)
                obj1 = executionexception;
            throw new RequestAbortedException("Request execution failed", ((Throwable) (obj1)));
        }
        if(i > 0)
            l = i;
        else
            l = 0L;
        httpclientconnection = connectionrequest.get(l, TimeUnit.MILLISECONDS);
        break MISSING_BLOCK_LABEL_196;
_L14:
        if(j <= 1)
            break MISSING_BLOCK_LABEL_407;
        if(!Proxies.isRepeatable(httprequestwrapper))
            throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
        if(httpexecutionaware == null)
            break MISSING_BLOCK_LABEL_443;
        if(httpexecutionaware.isAborted())
            throw new RequestAbortedException("Request aborted");
        if(httpclientconnection.isOpen())
            break MISSING_BLOCK_LABEL_493;
        log.debug((new StringBuilder()).append("Opening connection ").append(httproute).toString());
        establishRoute(authstate1, httpclientconnection, httproute, httprequestwrapper, httpclientcontext);
        k = requestconfig.getSocketTimeout();
        if(k < 0)
            break MISSING_BLOCK_LABEL_514;
        httpclientconnection.setSocketTimeout(k);
        if(httpexecutionaware == null) goto _L2; else goto _L1
_L1:
        if(httpexecutionaware.isAborted())
            throw new RequestAbortedException("Request aborted");
          goto _L2
        tunnelrefusedexception;
        if(log.isDebugEnabled())
            log.debug(tunnelrefusedexception.getMessage());
        httpresponse = tunnelrefusedexception.getResponse();
_L11:
        if(obj != null)
            break MISSING_BLOCK_LABEL_611;
        obj = userTokenHandler.getUserToken(httpclientcontext);
        httpclientcontext.setAttribute("http.user-token", obj);
        if(obj == null)
            break MISSING_BLOCK_LABEL_623;
        connectionholder.setState(obj);
        httpentity1 = httpresponse.getEntity();
        if(httpentity1 == null) goto _L4; else goto _L3
_L3:
        if(httpentity1.isStreaming()) goto _L5; else goto _L4
_L4:
        connectionholder.releaseConnection();
        return Proxies.enhanceResponse(httpresponse, null);
_L2:
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Executing request ").append(httprequestwrapper.getRequestLine()).toString());
        if(!httprequestwrapper.containsHeader("Authorization"))
        {
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Target auth state: ").append(authstate.getState()).toString());
            authenticator.generateAuthResponse(httprequestwrapper, authstate, httpclientcontext);
        }
        if(!httprequestwrapper.containsHeader("Proxy-Authorization") && !httproute.isTunnelled())
        {
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Proxy auth state: ").append(authstate1.getState()).toString());
            authenticator.generateAuthResponse(httprequestwrapper, authstate1, httpclientcontext);
        }
        httpresponse = requestExecutor.execute(httprequestwrapper, httpclientconnection, httpclientcontext);
        if(!reuseStrategy.keepAlive(httpresponse, httpclientcontext)) goto _L7; else goto _L6
_L6:
        l1 = keepAliveStrategy.getKeepAliveDuration(httpresponse, httpclientcontext);
        if(!log.isDebugEnabled()) goto _L9; else goto _L8
_L8:
        if(l1 <= 0L)
            break MISSING_BLOCK_LABEL_1182;
        String s = (new StringBuilder()).append("for ").append(l1).append(" ").append(TimeUnit.MILLISECONDS).toString();
_L17:
        log.debug((new StringBuilder()).append("Connection can be kept alive ").append(s).toString());
_L9:
        connectionholder.setValidFor(l1, TimeUnit.MILLISECONDS);
        connectionholder.markReusable();
_L15:
        if(!needAuthentication(authstate, authstate1, httproute, httpresponse, httpclientcontext)) goto _L11; else goto _L10
_L10:
        httpentity = httpresponse.getEntity();
        if(!connectionholder.isReusable()) goto _L13; else goto _L12
_L12:
        EntityUtils.consume(httpentity);
_L16:
        httprequestwrapper.removeHeaders("Authorization");
        httprequestwrapper.removeHeaders("Proxy-Authorization");
        j++;
          goto _L14
_L7:
        connectionholder.markNonReusable();
          goto _L15
_L13:
        httpclientconnection.close();
        if(authstate1.getState() == AuthProtocolState.SUCCESS && authstate1.getAuthScheme() != null && authstate1.getAuthScheme().isConnectionBased())
        {
            log.debug("Resetting proxy auth state");
            authstate1.reset();
        }
        if(authstate.getState() == AuthProtocolState.SUCCESS && authstate.getAuthScheme() != null && authstate.getAuthScheme().isConnectionBased())
        {
            log.debug("Resetting target auth state");
            authstate.reset();
        }
          goto _L16
_L5:
        closeablehttpresponse = Proxies.enhanceResponse(httpresponse, connectionholder);
        return closeablehttpresponse;
        {
            httpclientcontext.setAttribute("http.connection", httpclientconnection);
            if(requestconfig.isStaleConnectionCheckEnabled() && httpclientconnection.isOpen())
            {
                log.debug("Stale connection check");
                if(httpclientconnection.isStale())
                {
                    log.debug("Stale connection detected");
                    httpclientconnection.close();
                }
            }
            ConnectionHolder connectionholder = new ConnectionHolder(log, connManager, httpclientconnection);
            int j;
            int k;
            HttpResponse httpresponse;
            HttpEntity httpentity;
            HttpEntity httpentity1;
            CloseableHttpResponse closeablehttpresponse;
            long l1;
            TunnelRefusedException tunnelrefusedexception;
            if(httpexecutionaware != null)
                try
                {
                    httpexecutionaware.setCancellable(connectionholder);
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
            j = 1;
        }
          goto _L14
        s = "indefinitely";
          goto _L17
    }

    private final HttpAuthenticator authenticator = new HttpAuthenticator();
    private final HttpClientConnectionManager connManager;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log = LogFactory.getLog(getClass());
    private final AuthenticationStrategy proxyAuthStrategy;
    private final HttpProcessor proxyHttpProcessor;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
    private final HttpRouteDirector routeDirector = new BasicRouteDirector();
    private final AuthenticationStrategy targetAuthStrategy;
    private final UserTokenHandler userTokenHandler;
}
