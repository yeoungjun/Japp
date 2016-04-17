// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.params.HttpClientParamConfig;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.cookie.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            CloseableHttpClient, DefaultRequestDirector, DefaultConnectionKeepAliveStrategy, BasicCookieStore, 
//            BasicCredentialsProvider, DefaultHttpRequestRetryHandler, DefaultProxyAuthenticationHandler, ProxyAuthenticationStrategy, 
//            DefaultRedirectHandler, DefaultTargetAuthenticationHandler, TargetAuthenticationStrategy, DefaultUserTokenHandler, 
//            ClientParamsStack, CloseableHttpResponseProxy, DefaultRedirectStrategy, AuthenticationStrategyAdaptor, 
//            DefaultRedirectStrategyAdaptor

public abstract class AbstractHttpClient extends CloseableHttpClient
{

    protected AbstractHttpClient(ClientConnectionManager clientconnectionmanager, HttpParams httpparams)
    {
        defaultParams = httpparams;
        connManager = clientconnectionmanager;
    }

    private HttpProcessor getProtocolProcessor()
    {
        this;
        JVM INSTR monitorenter ;
        BasicHttpProcessor basichttpprocessor;
        int i;
        HttpRequestInterceptor ahttprequestinterceptor[];
        if(protocolProcessor != null)
            break MISSING_BLOCK_LABEL_109;
        basichttpprocessor = getHttpProcessor();
        i = basichttpprocessor.getRequestInterceptorCount();
        ahttprequestinterceptor = new HttpRequestInterceptor[i];
        int j = 0;
_L2:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        ahttprequestinterceptor[j] = basichttpprocessor.getRequestInterceptor(j);
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        int k;
        HttpResponseInterceptor ahttpresponseinterceptor[];
        k = basichttpprocessor.getResponseInterceptorCount();
        ahttpresponseinterceptor = new HttpResponseInterceptor[k];
        int l = 0;
_L4:
        if(l >= k)
            break; /* Loop/switch isn't completed */
        ahttpresponseinterceptor[l] = basichttpprocessor.getResponseInterceptor(l);
        l++;
        if(true) goto _L4; else goto _L3
_L3:
        protocolProcessor = new ImmutableHttpProcessor(ahttprequestinterceptor, ahttpresponseinterceptor);
        ImmutableHttpProcessor immutablehttpprocessor = protocolProcessor;
        this;
        JVM INSTR monitorexit ;
        return immutablehttpprocessor;
        Exception exception;
        exception;
        throw exception;
    }

    public void addRequestInterceptor(HttpRequestInterceptor httprequestinterceptor)
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().addInterceptor(httprequestinterceptor);
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addRequestInterceptor(HttpRequestInterceptor httprequestinterceptor, int i)
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().addInterceptor(httprequestinterceptor, i);
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addResponseInterceptor(HttpResponseInterceptor httpresponseinterceptor)
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().addInterceptor(httpresponseinterceptor);
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addResponseInterceptor(HttpResponseInterceptor httpresponseinterceptor, int i)
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().addInterceptor(httpresponseinterceptor, i);
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void clearRequestInterceptors()
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().clearRequestInterceptors();
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void clearResponseInterceptors()
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().clearResponseInterceptors();
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void close()
    {
        getConnectionManager().shutdown();
    }

    protected AuthSchemeRegistry createAuthSchemeRegistry()
    {
        AuthSchemeRegistry authschemeregistry = new AuthSchemeRegistry();
        authschemeregistry.register("Basic", new BasicSchemeFactory());
        authschemeregistry.register("Digest", new DigestSchemeFactory());
        authschemeregistry.register("NTLM", new NTLMSchemeFactory());
        authschemeregistry.register("negotiate", new SPNegoSchemeFactory());
        authschemeregistry.register("Kerberos", new KerberosSchemeFactory());
        return authschemeregistry;
    }

    protected ClientConnectionManager createClientConnectionManager()
    {
        org.apache.http.conn.scheme.SchemeRegistry schemeregistry = SchemeRegistryFactory.createDefault();
        HttpParams httpparams = getParams();
        String s = (String)httpparams.getParameter("http.connection-manager.factory-class-name");
        ClientConnectionManagerFactory clientconnectionmanagerfactory = null;
        if(s != null)
            try
            {
                clientconnectionmanagerfactory = (ClientConnectionManagerFactory)Class.forName(s).newInstance();
            }
            catch(ClassNotFoundException classnotfoundexception)
            {
                throw new IllegalStateException((new StringBuilder()).append("Invalid class name: ").append(s).toString());
            }
            catch(IllegalAccessException illegalaccessexception)
            {
                throw new IllegalAccessError(illegalaccessexception.getMessage());
            }
            catch(InstantiationException instantiationexception)
            {
                throw new InstantiationError(instantiationexception.getMessage());
            }
        if(clientconnectionmanagerfactory != null)
            return clientconnectionmanagerfactory.newInstance(httpparams, schemeregistry);
        else
            return new BasicClientConnectionManager(schemeregistry);
    }

    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httprequestexecutor, ClientConnectionManager clientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, HttpRoutePlanner httprouteplanner, HttpProcessor httpprocessor, HttpRequestRetryHandler httprequestretryhandler, 
            RedirectHandler redirecthandler, AuthenticationHandler authenticationhandler, AuthenticationHandler authenticationhandler1, UserTokenHandler usertokenhandler, HttpParams httpparams)
    {
        return new DefaultRequestDirector(httprequestexecutor, clientconnectionmanager, connectionreusestrategy, connectionkeepalivestrategy, httprouteplanner, httpprocessor, httprequestretryhandler, redirecthandler, authenticationhandler, authenticationhandler1, usertokenhandler, httpparams);
    }

    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httprequestexecutor, ClientConnectionManager clientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, HttpRoutePlanner httprouteplanner, HttpProcessor httpprocessor, HttpRequestRetryHandler httprequestretryhandler, 
            RedirectStrategy redirectstrategy, AuthenticationHandler authenticationhandler, AuthenticationHandler authenticationhandler1, UserTokenHandler usertokenhandler, HttpParams httpparams)
    {
        return new DefaultRequestDirector(log, httprequestexecutor, clientconnectionmanager, connectionreusestrategy, connectionkeepalivestrategy, httprouteplanner, httpprocessor, httprequestretryhandler, redirectstrategy, authenticationhandler, authenticationhandler1, usertokenhandler, httpparams);
    }

    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httprequestexecutor, ClientConnectionManager clientconnectionmanager, ConnectionReuseStrategy connectionreusestrategy, ConnectionKeepAliveStrategy connectionkeepalivestrategy, HttpRoutePlanner httprouteplanner, HttpProcessor httpprocessor, HttpRequestRetryHandler httprequestretryhandler, 
            RedirectStrategy redirectstrategy, AuthenticationStrategy authenticationstrategy, AuthenticationStrategy authenticationstrategy1, UserTokenHandler usertokenhandler, HttpParams httpparams)
    {
        return new DefaultRequestDirector(log, httprequestexecutor, clientconnectionmanager, connectionreusestrategy, connectionkeepalivestrategy, httprouteplanner, httpprocessor, httprequestretryhandler, redirectstrategy, authenticationstrategy, authenticationstrategy1, usertokenhandler, httpparams);
    }

    protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy()
    {
        return new DefaultConnectionKeepAliveStrategy();
    }

    protected ConnectionReuseStrategy createConnectionReuseStrategy()
    {
        return new DefaultConnectionReuseStrategy();
    }

    protected CookieSpecRegistry createCookieSpecRegistry()
    {
        CookieSpecRegistry cookiespecregistry = new CookieSpecRegistry();
        cookiespecregistry.register("best-match", new BestMatchSpecFactory());
        cookiespecregistry.register("compatibility", new BrowserCompatSpecFactory());
        cookiespecregistry.register("netscape", new NetscapeDraftSpecFactory());
        cookiespecregistry.register("rfc2109", new RFC2109SpecFactory());
        cookiespecregistry.register("rfc2965", new RFC2965SpecFactory());
        cookiespecregistry.register("ignoreCookies", new IgnoreSpecFactory());
        return cookiespecregistry;
    }

    protected CookieStore createCookieStore()
    {
        return new BasicCookieStore();
    }

    protected CredentialsProvider createCredentialsProvider()
    {
        return new BasicCredentialsProvider();
    }

    protected HttpContext createHttpContext()
    {
        BasicHttpContext basichttpcontext = new BasicHttpContext();
        basichttpcontext.setAttribute("http.scheme-registry", getConnectionManager().getSchemeRegistry());
        basichttpcontext.setAttribute("http.authscheme-registry", getAuthSchemes());
        basichttpcontext.setAttribute("http.cookiespec-registry", getCookieSpecs());
        basichttpcontext.setAttribute("http.cookie-store", getCookieStore());
        basichttpcontext.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
        return basichttpcontext;
    }

    protected abstract HttpParams createHttpParams();

    protected abstract BasicHttpProcessor createHttpProcessor();

    protected HttpRequestRetryHandler createHttpRequestRetryHandler()
    {
        return new DefaultHttpRequestRetryHandler();
    }

    protected HttpRoutePlanner createHttpRoutePlanner()
    {
        return new DefaultHttpRoutePlanner(getConnectionManager().getSchemeRegistry());
    }

    protected AuthenticationHandler createProxyAuthenticationHandler()
    {
        return new DefaultProxyAuthenticationHandler();
    }

    protected AuthenticationStrategy createProxyAuthenticationStrategy()
    {
        return new ProxyAuthenticationStrategy();
    }

    protected RedirectHandler createRedirectHandler()
    {
        return new DefaultRedirectHandler();
    }

    protected HttpRequestExecutor createRequestExecutor()
    {
        return new HttpRequestExecutor();
    }

    protected AuthenticationHandler createTargetAuthenticationHandler()
    {
        return new DefaultTargetAuthenticationHandler();
    }

    protected AuthenticationStrategy createTargetAuthenticationStrategy()
    {
        return new TargetAuthenticationStrategy();
    }

    protected UserTokenHandler createUserTokenHandler()
    {
        return new DefaultUserTokenHandler();
    }

    protected HttpParams determineParams(HttpRequest httprequest)
    {
        return new ClientParamsStack(null, getParams(), httprequest.getParams(), null);
    }

    protected final CloseableHttpResponse doExecute(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws IOException, ClientProtocolException
    {
        Args.notNull(httprequest, "HTTP request");
        this;
        JVM INSTR monitorenter ;
        HttpContext httpcontext1 = createHttpContext();
        if(httpcontext != null) goto _L2; else goto _L1
_L1:
        Object obj = httpcontext1;
_L8:
        RequestDirector requestdirector;
        HttpRoutePlanner httprouteplanner;
        ConnectionBackoffStrategy connectionbackoffstrategy;
        BackoffManager backoffmanager;
        HttpParams httpparams = determineParams(httprequest);
        org.apache.http.client.config.RequestConfig requestconfig = HttpClientParamConfig.getRequestConfig(httpparams);
        ((HttpContext) (obj)).setAttribute("http.request-config", requestconfig);
        requestdirector = createClientRequestDirector(getRequestExecutor(), getConnectionManager(), getConnectionReuseStrategy(), getConnectionKeepAliveStrategy(), getRoutePlanner(), getProtocolProcessor(), getHttpRequestRetryHandler(), getRedirectStrategy(), getTargetAuthenticationStrategy(), getProxyAuthenticationStrategy(), getUserTokenHandler(), httpparams);
        httprouteplanner = getRoutePlanner();
        connectionbackoffstrategy = getConnectionBackoffStrategy();
        backoffmanager = getBackoffManager();
        this;
        JVM INSTR monitorexit ;
        if(connectionbackoffstrategy == null || backoffmanager == null) goto _L4; else goto _L3
_L3:
        Exception exception;
        DefaultedHttpContext defaultedhttpcontext;
        HttpHost httphost1;
        org.apache.http.conn.routing.HttpRoute httproute;
        RuntimeException runtimeexception;
        CloseableHttpResponse closeablehttpresponse1;
        if(httphost != null)
            httphost1 = httphost;
        else
            try
            {
                httphost1 = (HttpHost)determineParams(httprequest).getParameter("http.default-host");
            }
            catch(HttpException httpexception)
            {
                throw new ClientProtocolException(httpexception);
            }
        httproute = httprouteplanner.determineRoute(httphost1, httprequest, ((HttpContext) (obj)));
        closeablehttpresponse1 = CloseableHttpResponseProxy.newProxy(requestdirector.execute(httphost, httprequest, ((HttpContext) (obj))));
        if(!connectionbackoffstrategy.shouldBackoff(closeablehttpresponse1)) goto _L6; else goto _L5
_L5:
        backoffmanager.backOff(httproute);
        return closeablehttpresponse1;
_L2:
        defaultedhttpcontext = new DefaultedHttpContext(httpcontext, httpcontext1);
        obj = defaultedhttpcontext;
        continue; /* Loop/switch isn't completed */
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        runtimeexception;
        if(connectionbackoffstrategy.shouldBackoff(runtimeexception))
            backoffmanager.backOff(httproute);
        throw runtimeexception;
        Exception exception1;
        exception1;
        if(connectionbackoffstrategy.shouldBackoff(exception1))
            backoffmanager.backOff(httproute);
        if(exception1 instanceof HttpException)
            throw (HttpException)exception1;
        if(exception1 instanceof IOException)
            throw (IOException)exception1;
        else
            throw new UndeclaredThrowableException(exception1);
_L6:
        backoffmanager.probe(httproute);
        return closeablehttpresponse1;
_L4:
        CloseableHttpResponse closeablehttpresponse = CloseableHttpResponseProxy.newProxy(requestdirector.execute(httphost, httprequest, ((HttpContext) (obj))));
        return closeablehttpresponse;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public final AuthSchemeRegistry getAuthSchemes()
    {
        this;
        JVM INSTR monitorenter ;
        AuthSchemeRegistry authschemeregistry;
        if(supportedAuthSchemes == null)
            supportedAuthSchemes = createAuthSchemeRegistry();
        authschemeregistry = supportedAuthSchemes;
        this;
        JVM INSTR monitorexit ;
        return authschemeregistry;
        Exception exception;
        exception;
        throw exception;
    }

    public final BackoffManager getBackoffManager()
    {
        this;
        JVM INSTR monitorenter ;
        BackoffManager backoffmanager = backoffManager;
        this;
        JVM INSTR monitorexit ;
        return backoffmanager;
        Exception exception;
        exception;
        throw exception;
    }

    public final ConnectionBackoffStrategy getConnectionBackoffStrategy()
    {
        this;
        JVM INSTR monitorenter ;
        ConnectionBackoffStrategy connectionbackoffstrategy = connectionBackoffStrategy;
        this;
        JVM INSTR monitorexit ;
        return connectionbackoffstrategy;
        Exception exception;
        exception;
        throw exception;
    }

    public final ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy()
    {
        this;
        JVM INSTR monitorenter ;
        ConnectionKeepAliveStrategy connectionkeepalivestrategy;
        if(keepAliveStrategy == null)
            keepAliveStrategy = createConnectionKeepAliveStrategy();
        connectionkeepalivestrategy = keepAliveStrategy;
        this;
        JVM INSTR monitorexit ;
        return connectionkeepalivestrategy;
        Exception exception;
        exception;
        throw exception;
    }

    public final ClientConnectionManager getConnectionManager()
    {
        this;
        JVM INSTR monitorenter ;
        ClientConnectionManager clientconnectionmanager;
        if(connManager == null)
            connManager = createClientConnectionManager();
        clientconnectionmanager = connManager;
        this;
        JVM INSTR monitorexit ;
        return clientconnectionmanager;
        Exception exception;
        exception;
        throw exception;
    }

    public final ConnectionReuseStrategy getConnectionReuseStrategy()
    {
        this;
        JVM INSTR monitorenter ;
        ConnectionReuseStrategy connectionreusestrategy;
        if(reuseStrategy == null)
            reuseStrategy = createConnectionReuseStrategy();
        connectionreusestrategy = reuseStrategy;
        this;
        JVM INSTR monitorexit ;
        return connectionreusestrategy;
        Exception exception;
        exception;
        throw exception;
    }

    public final CookieSpecRegistry getCookieSpecs()
    {
        this;
        JVM INSTR monitorenter ;
        CookieSpecRegistry cookiespecregistry;
        if(supportedCookieSpecs == null)
            supportedCookieSpecs = createCookieSpecRegistry();
        cookiespecregistry = supportedCookieSpecs;
        this;
        JVM INSTR monitorexit ;
        return cookiespecregistry;
        Exception exception;
        exception;
        throw exception;
    }

    public final CookieStore getCookieStore()
    {
        this;
        JVM INSTR monitorenter ;
        CookieStore cookiestore;
        if(cookieStore == null)
            cookieStore = createCookieStore();
        cookiestore = cookieStore;
        this;
        JVM INSTR monitorexit ;
        return cookiestore;
        Exception exception;
        exception;
        throw exception;
    }

    public final CredentialsProvider getCredentialsProvider()
    {
        this;
        JVM INSTR monitorenter ;
        CredentialsProvider credentialsprovider;
        if(credsProvider == null)
            credsProvider = createCredentialsProvider();
        credentialsprovider = credsProvider;
        this;
        JVM INSTR monitorexit ;
        return credentialsprovider;
        Exception exception;
        exception;
        throw exception;
    }

    protected final BasicHttpProcessor getHttpProcessor()
    {
        this;
        JVM INSTR monitorenter ;
        BasicHttpProcessor basichttpprocessor;
        if(mutableProcessor == null)
            mutableProcessor = createHttpProcessor();
        basichttpprocessor = mutableProcessor;
        this;
        JVM INSTR monitorexit ;
        return basichttpprocessor;
        Exception exception;
        exception;
        throw exception;
    }

    public final HttpRequestRetryHandler getHttpRequestRetryHandler()
    {
        this;
        JVM INSTR monitorenter ;
        HttpRequestRetryHandler httprequestretryhandler;
        if(retryHandler == null)
            retryHandler = createHttpRequestRetryHandler();
        httprequestretryhandler = retryHandler;
        this;
        JVM INSTR monitorexit ;
        return httprequestretryhandler;
        Exception exception;
        exception;
        throw exception;
    }

    public final HttpParams getParams()
    {
        this;
        JVM INSTR monitorenter ;
        HttpParams httpparams;
        if(defaultParams == null)
            defaultParams = createHttpParams();
        httpparams = defaultParams;
        this;
        JVM INSTR monitorexit ;
        return httpparams;
        Exception exception;
        exception;
        throw exception;
    }

    public final AuthenticationHandler getProxyAuthenticationHandler()
    {
        this;
        JVM INSTR monitorenter ;
        AuthenticationHandler authenticationhandler = createProxyAuthenticationHandler();
        this;
        JVM INSTR monitorexit ;
        return authenticationhandler;
        Exception exception;
        exception;
        throw exception;
    }

    public final AuthenticationStrategy getProxyAuthenticationStrategy()
    {
        this;
        JVM INSTR monitorenter ;
        AuthenticationStrategy authenticationstrategy;
        if(proxyAuthStrategy == null)
            proxyAuthStrategy = createProxyAuthenticationStrategy();
        authenticationstrategy = proxyAuthStrategy;
        this;
        JVM INSTR monitorexit ;
        return authenticationstrategy;
        Exception exception;
        exception;
        throw exception;
    }

    public final RedirectHandler getRedirectHandler()
    {
        this;
        JVM INSTR monitorenter ;
        RedirectHandler redirecthandler = createRedirectHandler();
        this;
        JVM INSTR monitorexit ;
        return redirecthandler;
        Exception exception;
        exception;
        throw exception;
    }

    public final RedirectStrategy getRedirectStrategy()
    {
        this;
        JVM INSTR monitorenter ;
        RedirectStrategy redirectstrategy;
        if(redirectStrategy == null)
            redirectStrategy = new DefaultRedirectStrategy();
        redirectstrategy = redirectStrategy;
        this;
        JVM INSTR monitorexit ;
        return redirectstrategy;
        Exception exception;
        exception;
        throw exception;
    }

    public final HttpRequestExecutor getRequestExecutor()
    {
        this;
        JVM INSTR monitorenter ;
        HttpRequestExecutor httprequestexecutor;
        if(requestExec == null)
            requestExec = createRequestExecutor();
        httprequestexecutor = requestExec;
        this;
        JVM INSTR monitorexit ;
        return httprequestexecutor;
        Exception exception;
        exception;
        throw exception;
    }

    public HttpRequestInterceptor getRequestInterceptor(int i)
    {
        this;
        JVM INSTR monitorenter ;
        HttpRequestInterceptor httprequestinterceptor = getHttpProcessor().getRequestInterceptor(i);
        this;
        JVM INSTR monitorexit ;
        return httprequestinterceptor;
        Exception exception;
        exception;
        throw exception;
    }

    public int getRequestInterceptorCount()
    {
        this;
        JVM INSTR monitorenter ;
        int i = getHttpProcessor().getRequestInterceptorCount();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public HttpResponseInterceptor getResponseInterceptor(int i)
    {
        this;
        JVM INSTR monitorenter ;
        HttpResponseInterceptor httpresponseinterceptor = getHttpProcessor().getResponseInterceptor(i);
        this;
        JVM INSTR monitorexit ;
        return httpresponseinterceptor;
        Exception exception;
        exception;
        throw exception;
    }

    public int getResponseInterceptorCount()
    {
        this;
        JVM INSTR monitorenter ;
        int i = getHttpProcessor().getResponseInterceptorCount();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public final HttpRoutePlanner getRoutePlanner()
    {
        this;
        JVM INSTR monitorenter ;
        HttpRoutePlanner httprouteplanner;
        if(routePlanner == null)
            routePlanner = createHttpRoutePlanner();
        httprouteplanner = routePlanner;
        this;
        JVM INSTR monitorexit ;
        return httprouteplanner;
        Exception exception;
        exception;
        throw exception;
    }

    public final AuthenticationHandler getTargetAuthenticationHandler()
    {
        this;
        JVM INSTR monitorenter ;
        AuthenticationHandler authenticationhandler = createTargetAuthenticationHandler();
        this;
        JVM INSTR monitorexit ;
        return authenticationhandler;
        Exception exception;
        exception;
        throw exception;
    }

    public final AuthenticationStrategy getTargetAuthenticationStrategy()
    {
        this;
        JVM INSTR monitorenter ;
        AuthenticationStrategy authenticationstrategy;
        if(targetAuthStrategy == null)
            targetAuthStrategy = createTargetAuthenticationStrategy();
        authenticationstrategy = targetAuthStrategy;
        this;
        JVM INSTR monitorexit ;
        return authenticationstrategy;
        Exception exception;
        exception;
        throw exception;
    }

    public final UserTokenHandler getUserTokenHandler()
    {
        this;
        JVM INSTR monitorenter ;
        UserTokenHandler usertokenhandler;
        if(userTokenHandler == null)
            userTokenHandler = createUserTokenHandler();
        usertokenhandler = userTokenHandler;
        this;
        JVM INSTR monitorexit ;
        return usertokenhandler;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeRequestInterceptorByClass(Class class1)
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().removeRequestInterceptorByClass(class1);
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeResponseInterceptorByClass(Class class1)
    {
        this;
        JVM INSTR monitorenter ;
        getHttpProcessor().removeResponseInterceptorByClass(class1);
        protocolProcessor = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setAuthSchemes(AuthSchemeRegistry authschemeregistry)
    {
        this;
        JVM INSTR monitorenter ;
        supportedAuthSchemes = authschemeregistry;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setBackoffManager(BackoffManager backoffmanager)
    {
        this;
        JVM INSTR monitorenter ;
        backoffManager = backoffmanager;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionbackoffstrategy)
    {
        this;
        JVM INSTR monitorenter ;
        connectionBackoffStrategy = connectionbackoffstrategy;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setCookieSpecs(CookieSpecRegistry cookiespecregistry)
    {
        this;
        JVM INSTR monitorenter ;
        supportedCookieSpecs = cookiespecregistry;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setCookieStore(CookieStore cookiestore)
    {
        this;
        JVM INSTR monitorenter ;
        cookieStore = cookiestore;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setCredentialsProvider(CredentialsProvider credentialsprovider)
    {
        this;
        JVM INSTR monitorenter ;
        credsProvider = credentialsprovider;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setHttpRequestRetryHandler(HttpRequestRetryHandler httprequestretryhandler)
    {
        this;
        JVM INSTR monitorenter ;
        retryHandler = httprequestretryhandler;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionkeepalivestrategy)
    {
        this;
        JVM INSTR monitorenter ;
        keepAliveStrategy = connectionkeepalivestrategy;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setParams(HttpParams httpparams)
    {
        this;
        JVM INSTR monitorenter ;
        defaultParams = httpparams;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setProxyAuthenticationHandler(AuthenticationHandler authenticationhandler)
    {
        this;
        JVM INSTR monitorenter ;
        proxyAuthStrategy = new AuthenticationStrategyAdaptor(authenticationhandler);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setProxyAuthenticationStrategy(AuthenticationStrategy authenticationstrategy)
    {
        this;
        JVM INSTR monitorenter ;
        proxyAuthStrategy = authenticationstrategy;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setRedirectHandler(RedirectHandler redirecthandler)
    {
        this;
        JVM INSTR monitorenter ;
        redirectStrategy = new DefaultRedirectStrategyAdaptor(redirecthandler);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setRedirectStrategy(RedirectStrategy redirectstrategy)
    {
        this;
        JVM INSTR monitorenter ;
        redirectStrategy = redirectstrategy;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setReuseStrategy(ConnectionReuseStrategy connectionreusestrategy)
    {
        this;
        JVM INSTR monitorenter ;
        reuseStrategy = connectionreusestrategy;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setRoutePlanner(HttpRoutePlanner httprouteplanner)
    {
        this;
        JVM INSTR monitorenter ;
        routePlanner = httprouteplanner;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setTargetAuthenticationHandler(AuthenticationHandler authenticationhandler)
    {
        this;
        JVM INSTR monitorenter ;
        targetAuthStrategy = new AuthenticationStrategyAdaptor(authenticationhandler);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setTargetAuthenticationStrategy(AuthenticationStrategy authenticationstrategy)
    {
        this;
        JVM INSTR monitorenter ;
        targetAuthStrategy = authenticationstrategy;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setUserTokenHandler(UserTokenHandler usertokenhandler)
    {
        this;
        JVM INSTR monitorenter ;
        userTokenHandler = usertokenhandler;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private BackoffManager backoffManager;
    private ClientConnectionManager connManager;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private CookieStore cookieStore;
    private CredentialsProvider credsProvider;
    private HttpParams defaultParams;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log = LogFactory.getLog(getClass());
    private BasicHttpProcessor mutableProcessor;
    private ImmutableHttpProcessor protocolProcessor;
    private AuthenticationStrategy proxyAuthStrategy;
    private RedirectStrategy redirectStrategy;
    private HttpRequestExecutor requestExec;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionReuseStrategy reuseStrategy;
    private HttpRoutePlanner routePlanner;
    private AuthSchemeRegistry supportedAuthSchemes;
    private CookieSpecRegistry supportedCookieSpecs;
    private AuthenticationStrategy targetAuthStrategy;
    private UserTokenHandler userTokenHandler;
}
