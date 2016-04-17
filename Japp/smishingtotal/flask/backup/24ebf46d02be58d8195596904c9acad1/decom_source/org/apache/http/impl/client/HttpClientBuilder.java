// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.Closeable;
import java.net.ProxySelector;
import java.util.*;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.*;
import org.apache.http.config.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.conn.*;
import org.apache.http.impl.cookie.*;
import org.apache.http.impl.execchain.*;
import org.apache.http.protocol.*;
import org.apache.http.util.TextUtils;
import org.apache.http.util.VersionInfo;

// Referenced classes of package org.apache.http.impl.client:
//            DefaultConnectionKeepAliveStrategy, TargetAuthenticationStrategy, ProxyAuthenticationStrategy, DefaultUserTokenHandler, 
//            NoopUserTokenHandler, DefaultHttpRequestRetryHandler, DefaultRedirectStrategy, BasicCookieStore, 
//            SystemDefaultCredentialsProvider, InternalHttpClient, BasicCredentialsProvider, CloseableHttpClient

public class HttpClientBuilder
{

    protected HttpClientBuilder()
    {
        maxConnTotal = 0;
        maxConnPerRoute = 0;
    }

    public static HttpClientBuilder create()
    {
        return new HttpClientBuilder();
    }

    private static String[] split(String s)
    {
        if(TextUtils.isBlank(s))
            return null;
        else
            return s.split(" *, *");
    }

    protected void addCloseable(Closeable closeable)
    {
        if(closeable == null)
            return;
        if(closeables == null)
            closeables = new ArrayList();
        closeables.add(closeable);
    }

    public final HttpClientBuilder addInterceptorFirst(HttpRequestInterceptor httprequestinterceptor)
    {
        if(httprequestinterceptor == null)
            return this;
        if(requestFirst == null)
            requestFirst = new LinkedList();
        requestFirst.addFirst(httprequestinterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpResponseInterceptor httpresponseinterceptor)
    {
        if(httpresponseinterceptor == null)
            return this;
        if(responseFirst == null)
            responseFirst = new LinkedList();
        responseFirst.addFirst(httpresponseinterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpRequestInterceptor httprequestinterceptor)
    {
        if(httprequestinterceptor == null)
            return this;
        if(requestLast == null)
            requestLast = new LinkedList();
        requestLast.addLast(httprequestinterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpResponseInterceptor httpresponseinterceptor)
    {
        if(httpresponseinterceptor == null)
            return this;
        if(responseLast == null)
            responseLast = new LinkedList();
        responseLast.addLast(httpresponseinterceptor);
        return this;
    }

    public CloseableHttpClient build()
    {
        HttpRequestExecutor httprequestexecutor = requestExec;
        if(httprequestexecutor == null)
            httprequestexecutor = new HttpRequestExecutor();
        Object obj = connManager;
        Object obj1;
        Object obj5;
        ClientExecChain clientexecchain;
        HttpProcessor httpprocessor1;
        if(obj == null)
        {
            Object obj15 = sslSocketFactory;
            Object obj2;
            Object obj3;
            Object obj4;
            Iterator iterator3;
            VersionInfo versioninfo;
            PoolingHttpClientConnectionManager poolinghttpclientconnectionmanager;
            if(obj15 == null)
                if(sslcontext != null)
                {
                    SSLContext sslcontext2 = sslcontext;
                    X509HostnameVerifier x509hostnameverifier2 = hostnameVerifier;
                    obj15 = new SSLConnectionSocketFactory(sslcontext2, x509hostnameverifier2);
                } else
                if(systemProperties)
                {
                    SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
                    String as[] = split(System.getProperty("https.protocols"));
                    String as1[] = split(System.getProperty("https.cipherSuites"));
                    X509HostnameVerifier x509hostnameverifier1 = hostnameVerifier;
                    obj15 = new SSLConnectionSocketFactory(sslsocketfactory, as, as1, x509hostnameverifier1);
                } else
                {
                    SSLContext sslcontext1 = SSLContexts.createDefault();
                    X509HostnameVerifier x509hostnameverifier = hostnameVerifier;
                    obj15 = new SSLConnectionSocketFactory(sslcontext1, x509hostnameverifier);
                }
            poolinghttpclientconnectionmanager = new PoolingHttpClientConnectionManager(RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", obj15).build());
            if(defaultSocketConfig != null)
                poolinghttpclientconnectionmanager.setDefaultSocketConfig(defaultSocketConfig);
            if(defaultConnectionConfig != null)
                poolinghttpclientconnectionmanager.setDefaultConnectionConfig(defaultConnectionConfig);
            if(systemProperties && "true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true")))
            {
                int i = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
                poolinghttpclientconnectionmanager.setDefaultMaxPerRoute(i);
                poolinghttpclientconnectionmanager.setMaxTotal(i * 2);
            }
            if(maxConnTotal > 0)
                poolinghttpclientconnectionmanager.setMaxTotal(maxConnTotal);
            if(maxConnPerRoute > 0)
                poolinghttpclientconnectionmanager.setDefaultMaxPerRoute(maxConnPerRoute);
            obj = poolinghttpclientconnectionmanager;
        }
        obj1 = reuseStrategy;
        if(obj1 == null)
            if(systemProperties)
            {
                if("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true")))
                    obj1 = DefaultConnectionReuseStrategy.INSTANCE;
                else
                    obj1 = NoConnectionReuseStrategy.INSTANCE;
            } else
            {
                obj1 = DefaultConnectionReuseStrategy.INSTANCE;
            }
        obj2 = keepAliveStrategy;
        if(obj2 == null)
            obj2 = DefaultConnectionKeepAliveStrategy.INSTANCE;
        obj3 = targetAuthStrategy;
        if(obj3 == null)
            obj3 = TargetAuthenticationStrategy.INSTANCE;
        obj4 = proxyAuthStrategy;
        if(obj4 == null)
            obj4 = ProxyAuthenticationStrategy.INSTANCE;
        obj5 = userTokenHandler;
        if(obj5 == null)
            if(!connectionStateDisabled)
                obj5 = DefaultUserTokenHandler.INSTANCE;
            else
                obj5 = NoopUserTokenHandler.INSTANCE;
        clientexecchain = decorateMainExec(new MainClientExec(httprequestexecutor, ((HttpClientConnectionManager) (obj)), ((ConnectionReuseStrategy) (obj1)), ((ConnectionKeepAliveStrategy) (obj2)), ((AuthenticationStrategy) (obj3)), ((AuthenticationStrategy) (obj4)), ((UserTokenHandler) (obj5))));
        httpprocessor1 = httpprocessor;
        if(httpprocessor1 == null)
        {
            String s = userAgent;
            HttpProcessorBuilder httpprocessorbuilder;
            if(s == null)
            {
                if(systemProperties)
                    s = System.getProperty("http.agent");
                if(s == null)
                {
                    versioninfo = VersionInfo.loadVersionInfo("org.apache.http.client", org/apache/http/impl/client/HttpClientBuilder.getClassLoader());
                    String s1;
                    if(versioninfo != null)
                        s1 = versioninfo.getRelease();
                    else
                        s1 = "UNAVAILABLE";
                    s = (new StringBuilder()).append("Apache-HttpClient/").append(s1).append(" (java 1.5)").toString();
                }
            }
            httpprocessorbuilder = HttpProcessorBuilder.create();
            if(requestFirst != null)
                for(iterator3 = requestFirst.iterator(); iterator3.hasNext(); httpprocessorbuilder.addFirst((HttpRequestInterceptor)iterator3.next()));
            if(responseFirst != null)
            {
                for(Iterator iterator2 = responseFirst.iterator(); iterator2.hasNext(); httpprocessorbuilder.addFirst((HttpResponseInterceptor)iterator2.next()));
            }
            HttpRequestInterceptor ahttprequestinterceptor[] = new HttpRequestInterceptor[6];
            ahttprequestinterceptor[0] = new RequestDefaultHeaders(defaultHeaders);
            ahttprequestinterceptor[1] = new RequestContent();
            ahttprequestinterceptor[2] = new RequestTargetHost();
            ahttprequestinterceptor[3] = new RequestClientConnControl();
            ahttprequestinterceptor[4] = new RequestUserAgent(s);
            ahttprequestinterceptor[5] = new RequestExpectContinue();
            httpprocessorbuilder.addAll(ahttprequestinterceptor);
            if(!cookieManagementDisabled)
                httpprocessorbuilder.add(new RequestAddCookies());
            if(!contentCompressionDisabled)
                httpprocessorbuilder.add(new RequestAcceptEncoding());
            if(!authCachingDisabled)
                httpprocessorbuilder.add(new RequestAuthCache());
            if(!cookieManagementDisabled)
                httpprocessorbuilder.add(new ResponseProcessCookies());
            if(!contentCompressionDisabled)
                httpprocessorbuilder.add(new ResponseContentEncoding());
            if(requestLast != null)
            {
                for(Iterator iterator1 = requestLast.iterator(); iterator1.hasNext(); httpprocessorbuilder.addLast((HttpRequestInterceptor)iterator1.next()));
            }
            if(responseLast != null)
            {
                for(Iterator iterator = responseLast.iterator(); iterator.hasNext(); httpprocessorbuilder.addLast((HttpResponseInterceptor)iterator.next()));
            }
            httpprocessor1 = httpprocessorbuilder.build();
        }
        ProtocolExec protocolexec = new ProtocolExec(clientexecchain, httpprocessor1);
        Object obj6 = decorateProtocolExec(protocolexec);
        if(!automaticRetriesDisabled)
        {
            Object obj14 = retryHandler;
            if(obj14 == null)
                obj14 = DefaultHttpRequestRetryHandler.INSTANCE;
            RetryExec retryexec = new RetryExec(((ClientExecChain) (obj6)), ((HttpRequestRetryHandler) (obj14)));
            obj6 = retryexec;
        }
        Object obj7 = routePlanner;
        Object obj11;
        RequestConfig requestconfig;
        ArrayList arraylist;
        if(obj7 == null)
        {
            Object obj13 = schemePortResolver;
            if(obj13 == null)
                obj13 = DefaultSchemePortResolver.INSTANCE;
            ServiceUnavailableRetryStrategy serviceunavailableretrystrategy;
            ServiceUnavailableRetryExec serviceunavailableretryexec;
            BackoffManager backoffmanager;
            ConnectionBackoffStrategy connectionbackoffstrategy;
            Object obj8;
            Object obj9;
            Object obj10;
            List list;
            BackoffStrategyExec backoffstrategyexec;
            Object obj12;
            RedirectExec redirectexec;
            if(proxy != null)
                obj7 = new DefaultProxyRoutePlanner(proxy, ((SchemePortResolver) (obj13)));
            else
            if(systemProperties)
            {
                ProxySelector proxyselector = ProxySelector.getDefault();
                obj7 = new SystemDefaultRoutePlanner(((SchemePortResolver) (obj13)), proxyselector);
            } else
            {
                obj7 = new DefaultRoutePlanner(((SchemePortResolver) (obj13)));
            }
        }
        if(!redirectHandlingDisabled)
        {
            obj12 = redirectStrategy;
            if(obj12 == null)
                obj12 = DefaultRedirectStrategy.INSTANCE;
            redirectexec = new RedirectExec(((ClientExecChain) (obj6)), ((HttpRoutePlanner) (obj7)), ((RedirectStrategy) (obj12)));
            obj6 = redirectexec;
        }
        serviceunavailableretrystrategy = serviceUnavailStrategy;
        if(serviceunavailableretrystrategy != null)
        {
            serviceunavailableretryexec = new ServiceUnavailableRetryExec(((ClientExecChain) (obj6)), serviceunavailableretrystrategy);
            obj6 = serviceunavailableretryexec;
        }
        backoffmanager = backoffManager;
        connectionbackoffstrategy = connectionBackoffStrategy;
        if(backoffmanager != null && connectionbackoffstrategy != null)
        {
            backoffstrategyexec = new BackoffStrategyExec(((ClientExecChain) (obj6)), connectionbackoffstrategy, backoffmanager);
            obj6 = backoffstrategyexec;
        }
        obj8 = authSchemeRegistry;
        if(obj8 == null)
            obj8 = RegistryBuilder.create().register("Basic", new BasicSchemeFactory()).register("Digest", new DigestSchemeFactory()).register("NTLM", new NTLMSchemeFactory()).register("negotiate", new SPNegoSchemeFactory()).register("Kerberos", new KerberosSchemeFactory()).build();
        obj9 = cookieSpecRegistry;
        if(obj9 == null)
            obj9 = RegistryBuilder.create().register("best-match", new BestMatchSpecFactory()).register("standard", new RFC2965SpecFactory()).register("compatibility", new BrowserCompatSpecFactory()).register("netscape", new NetscapeDraftSpecFactory()).register("ignoreCookies", new IgnoreSpecFactory()).register("rfc2109", new RFC2109SpecFactory()).register("rfc2965", new RFC2965SpecFactory()).build();
        obj10 = cookieStore;
        if(obj10 == null)
            obj10 = new BasicCookieStore();
        obj11 = credentialsProvider;
        if(obj11 == null)
            if(systemProperties)
                obj11 = new SystemDefaultCredentialsProvider();
            else
                obj11 = new BasicCredentialsProvider();
        if(defaultRequestConfig != null)
            requestconfig = defaultRequestConfig;
        else
            requestconfig = RequestConfig.DEFAULT;
        if(closeables != null)
        {
            list = closeables;
            arraylist = new ArrayList(list);
        } else
        {
            arraylist = null;
        }
        return new InternalHttpClient(((ClientExecChain) (obj6)), ((HttpClientConnectionManager) (obj)), ((HttpRoutePlanner) (obj7)), ((Lookup) (obj9)), ((Lookup) (obj8)), ((CookieStore) (obj10)), ((CredentialsProvider) (obj11)), requestconfig, arraylist);
    }

    protected ClientExecChain decorateMainExec(ClientExecChain clientexecchain)
    {
        return clientexecchain;
    }

    protected ClientExecChain decorateProtocolExec(ClientExecChain clientexecchain)
    {
        return clientexecchain;
    }

    public final HttpClientBuilder disableAuthCaching()
    {
        authCachingDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableAutomaticRetries()
    {
        automaticRetriesDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableConnectionState()
    {
        connectionStateDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableContentCompression()
    {
        contentCompressionDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableCookieManagement()
    {
        cookieManagementDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableRedirectHandling()
    {
        redirectHandlingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setBackoffManager(BackoffManager backoffmanager)
    {
        backoffManager = backoffmanager;
        return this;
    }

    public final HttpClientBuilder setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionbackoffstrategy)
    {
        connectionBackoffStrategy = connectionbackoffstrategy;
        return this;
    }

    public final HttpClientBuilder setConnectionManager(HttpClientConnectionManager httpclientconnectionmanager)
    {
        connManager = httpclientconnectionmanager;
        return this;
    }

    public final HttpClientBuilder setConnectionReuseStrategy(ConnectionReuseStrategy connectionreusestrategy)
    {
        reuseStrategy = connectionreusestrategy;
        return this;
    }

    public final HttpClientBuilder setDefaultAuthSchemeRegistry(Lookup lookup)
    {
        authSchemeRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setDefaultConnectionConfig(ConnectionConfig connectionconfig)
    {
        defaultConnectionConfig = connectionconfig;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieSpecRegistry(Lookup lookup)
    {
        cookieSpecRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieStore(CookieStore cookiestore)
    {
        cookieStore = cookiestore;
        return this;
    }

    public final HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider credentialsprovider)
    {
        credentialsProvider = credentialsprovider;
        return this;
    }

    public final HttpClientBuilder setDefaultHeaders(Collection collection)
    {
        defaultHeaders = collection;
        return this;
    }

    public final HttpClientBuilder setDefaultRequestConfig(RequestConfig requestconfig)
    {
        defaultRequestConfig = requestconfig;
        return this;
    }

    public final HttpClientBuilder setDefaultSocketConfig(SocketConfig socketconfig)
    {
        defaultSocketConfig = socketconfig;
        return this;
    }

    public final HttpClientBuilder setHostnameVerifier(X509HostnameVerifier x509hostnameverifier)
    {
        hostnameVerifier = x509hostnameverifier;
        return this;
    }

    public final HttpClientBuilder setHttpProcessor(HttpProcessor httpprocessor1)
    {
        httpprocessor = httpprocessor1;
        return this;
    }

    public final HttpClientBuilder setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionkeepalivestrategy)
    {
        keepAliveStrategy = connectionkeepalivestrategy;
        return this;
    }

    public final HttpClientBuilder setMaxConnPerRoute(int i)
    {
        maxConnPerRoute = i;
        return this;
    }

    public final HttpClientBuilder setMaxConnTotal(int i)
    {
        maxConnTotal = i;
        return this;
    }

    public final HttpClientBuilder setProxy(HttpHost httphost)
    {
        proxy = httphost;
        return this;
    }

    public final HttpClientBuilder setProxyAuthenticationStrategy(AuthenticationStrategy authenticationstrategy)
    {
        proxyAuthStrategy = authenticationstrategy;
        return this;
    }

    public final HttpClientBuilder setRedirectStrategy(RedirectStrategy redirectstrategy)
    {
        redirectStrategy = redirectstrategy;
        return this;
    }

    public final HttpClientBuilder setRequestExecutor(HttpRequestExecutor httprequestexecutor)
    {
        requestExec = httprequestexecutor;
        return this;
    }

    public final HttpClientBuilder setRetryHandler(HttpRequestRetryHandler httprequestretryhandler)
    {
        retryHandler = httprequestretryhandler;
        return this;
    }

    public final HttpClientBuilder setRoutePlanner(HttpRoutePlanner httprouteplanner)
    {
        routePlanner = httprouteplanner;
        return this;
    }

    public final HttpClientBuilder setSSLSocketFactory(LayeredConnectionSocketFactory layeredconnectionsocketfactory)
    {
        sslSocketFactory = layeredconnectionsocketfactory;
        return this;
    }

    public final HttpClientBuilder setSchemePortResolver(SchemePortResolver schemeportresolver)
    {
        schemePortResolver = schemeportresolver;
        return this;
    }

    public final HttpClientBuilder setServiceUnavailableRetryStrategy(ServiceUnavailableRetryStrategy serviceunavailableretrystrategy)
    {
        serviceUnavailStrategy = serviceunavailableretrystrategy;
        return this;
    }

    public final HttpClientBuilder setSslcontext(SSLContext sslcontext1)
    {
        sslcontext = sslcontext1;
        return this;
    }

    public final HttpClientBuilder setTargetAuthenticationStrategy(AuthenticationStrategy authenticationstrategy)
    {
        targetAuthStrategy = authenticationstrategy;
        return this;
    }

    public final HttpClientBuilder setUserAgent(String s)
    {
        userAgent = s;
        return this;
    }

    public final HttpClientBuilder setUserTokenHandler(UserTokenHandler usertokenhandler)
    {
        userTokenHandler = usertokenhandler;
        return this;
    }

    public final HttpClientBuilder useSystemProperties()
    {
        systemProperties = true;
        return this;
    }

    private boolean authCachingDisabled;
    private Lookup authSchemeRegistry;
    private boolean automaticRetriesDisabled;
    private BackoffManager backoffManager;
    private List closeables;
    private HttpClientConnectionManager connManager;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private boolean connectionStateDisabled;
    private boolean contentCompressionDisabled;
    private boolean cookieManagementDisabled;
    private Lookup cookieSpecRegistry;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider;
    private ConnectionConfig defaultConnectionConfig;
    private Collection defaultHeaders;
    private RequestConfig defaultRequestConfig;
    private SocketConfig defaultSocketConfig;
    private X509HostnameVerifier hostnameVerifier;
    private HttpProcessor httpprocessor;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private int maxConnPerRoute;
    private int maxConnTotal;
    private HttpHost proxy;
    private AuthenticationStrategy proxyAuthStrategy;
    private boolean redirectHandlingDisabled;
    private RedirectStrategy redirectStrategy;
    private HttpRequestExecutor requestExec;
    private LinkedList requestFirst;
    private LinkedList requestLast;
    private LinkedList responseFirst;
    private LinkedList responseLast;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionReuseStrategy reuseStrategy;
    private HttpRoutePlanner routePlanner;
    private SchemePortResolver schemePortResolver;
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private LayeredConnectionSocketFactory sslSocketFactory;
    private SSLContext sslcontext;
    private boolean systemProperties;
    private AuthenticationStrategy targetAuthStrategy;
    private String userAgent;
    private UserTokenHandler userTokenHandler;
}
