// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.params.HttpClientParamConfig;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.execchain.TunnelRefusedException;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.impl.client:
//            ProxyAuthenticationStrategy, BasicCredentialsProvider

public class ProxyClient
{

    public ProxyClient()
    {
        this(null, null, null);
    }

    public ProxyClient(RequestConfig requestconfig)
    {
        this(null, null, requestconfig);
    }

    public ProxyClient(HttpConnectionFactory httpconnectionfactory, ConnectionConfig connectionconfig, RequestConfig requestconfig)
    {
        HttpRequestInterceptor ahttprequestinterceptor[];
        if(httpconnectionfactory == null)
            httpconnectionfactory = ManagedHttpClientConnectionFactory.INSTANCE;
        connFactory = httpconnectionfactory;
        if(connectionconfig == null)
            connectionconfig = ConnectionConfig.DEFAULT;
        connectionConfig = connectionconfig;
        if(requestconfig == null)
            requestconfig = RequestConfig.DEFAULT;
        requestConfig = requestconfig;
        ahttprequestinterceptor = new HttpRequestInterceptor[2];
        ahttprequestinterceptor[0] = new RequestClientConnControl();
        ahttprequestinterceptor[1] = new RequestUserAgent();
        httpProcessor = new ImmutableHttpProcessor(ahttprequestinterceptor);
        requestExec = new HttpRequestExecutor();
        proxyAuthStrategy = new ProxyAuthenticationStrategy();
        authenticator = new HttpAuthenticator();
        proxyAuthState = new AuthState();
        authSchemeRegistry = new AuthSchemeRegistry();
        authSchemeRegistry.register("Basic", new BasicSchemeFactory());
        authSchemeRegistry.register("Digest", new DigestSchemeFactory());
        authSchemeRegistry.register("NTLM", new NTLMSchemeFactory());
        authSchemeRegistry.register("negotiate", new SPNegoSchemeFactory());
        authSchemeRegistry.register("Kerberos", new KerberosSchemeFactory());
        reuseStrategy = new DefaultConnectionReuseStrategy();
    }

    public ProxyClient(HttpParams httpparams)
    {
        this(null, HttpParamConfig.getConnectionConfig(httpparams), HttpClientParamConfig.getRequestConfig(httpparams));
    }

    public AuthSchemeRegistry getAuthSchemeRegistry()
    {
        return authSchemeRegistry;
    }

    public HttpParams getParams()
    {
        return new BasicHttpParams();
    }

    public Socket tunnel(HttpHost httphost, HttpHost httphost1, Credentials credentials)
        throws IOException, HttpException
    {
        Args.notNull(httphost, "Proxy host");
        Args.notNull(httphost1, "Target host");
        Args.notNull(credentials, "Credentials");
        HttpHost httphost2 = httphost1;
        if(httphost2.getPort() <= 0)
            httphost2 = new HttpHost(httphost2.getHostName(), 80, httphost2.getSchemeName());
        HttpRoute httproute = new HttpRoute(httphost2, requestConfig.getLocalAddress(), httphost, false, org.apache.http.conn.routing.RouteInfo.TunnelType.TUNNELLED, org.apache.http.conn.routing.RouteInfo.LayerType.PLAIN);
        ManagedHttpClientConnection managedhttpclientconnection = (ManagedHttpClientConnection)connFactory.create(httproute, connectionConfig);
        BasicHttpContext basichttpcontext = new BasicHttpContext();
        BasicHttpRequest basichttprequest = new BasicHttpRequest("CONNECT", httphost2.toHostString(), HttpVersion.HTTP_1_1);
        BasicCredentialsProvider basiccredentialsprovider = new BasicCredentialsProvider();
        basiccredentialsprovider.setCredentials(new AuthScope(httphost), credentials);
        basichttpcontext.setAttribute("http.target_host", httphost1);
        basichttpcontext.setAttribute("http.connection", managedhttpclientconnection);
        basichttpcontext.setAttribute("http.request", basichttprequest);
        basichttpcontext.setAttribute("http.route", httproute);
        basichttpcontext.setAttribute("http.auth.proxy-scope", proxyAuthState);
        basichttpcontext.setAttribute("http.auth.credentials-provider", basiccredentialsprovider);
        basichttpcontext.setAttribute("http.authscheme-registry", authSchemeRegistry);
        basichttpcontext.setAttribute("http.request-config", requestConfig);
        requestExec.preProcess(basichttprequest, httpProcessor, basichttpcontext);
        HttpResponse httpresponse;
        do
        {
            if(!managedhttpclientconnection.isOpen())
                managedhttpclientconnection.bind(new Socket(httphost.getHostName(), httphost.getPort()));
            authenticator.generateAuthResponse(basichttprequest, proxyAuthState, basichttpcontext);
            httpresponse = requestExec.execute(basichttprequest, managedhttpclientconnection, basichttpcontext);
            if(httpresponse.getStatusLine().getStatusCode() < 200)
                throw new HttpException((new StringBuilder()).append("Unexpected response to CONNECT request: ").append(httpresponse.getStatusLine()).toString());
            if(!authenticator.isAuthenticationRequested(httphost, httpresponse, proxyAuthStrategy, proxyAuthState, basichttpcontext) || !authenticator.handleAuthChallenge(httphost, httpresponse, proxyAuthStrategy, proxyAuthState, basichttpcontext))
                break;
            if(reuseStrategy.keepAlive(httpresponse, basichttpcontext))
                EntityUtils.consume(httpresponse.getEntity());
            else
                managedhttpclientconnection.close();
            basichttprequest.removeHeaders("Proxy-Authorization");
        } while(true);
        if(httpresponse.getStatusLine().getStatusCode() > 299)
        {
            org.apache.http.HttpEntity httpentity = httpresponse.getEntity();
            if(httpentity != null)
                httpresponse.setEntity(new BufferedHttpEntity(httpentity));
            managedhttpclientconnection.close();
            throw new TunnelRefusedException((new StringBuilder()).append("CONNECT refused by proxy: ").append(httpresponse.getStatusLine()).toString(), httpresponse);
        } else
        {
            return managedhttpclientconnection.getSocket();
        }
    }

    private final AuthSchemeRegistry authSchemeRegistry;
    private final HttpAuthenticator authenticator;
    private final HttpConnectionFactory connFactory;
    private final ConnectionConfig connectionConfig;
    private final HttpProcessor httpProcessor;
    private final AuthState proxyAuthState;
    private final ProxyAuthenticationStrategy proxyAuthStrategy;
    private final RequestConfig requestConfig;
    private final HttpRequestExecutor requestExec;
    private final ConnectionReuseStrategy reuseStrategy;
}
