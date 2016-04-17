// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.*;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.client:
//            AuthenticationStrategyImpl

public class ProxyAuthenticationStrategy extends AuthenticationStrategyImpl
{

    public ProxyAuthenticationStrategy()
    {
        super(407, "Proxy-Authenticate");
    }

    public volatile void authFailed(HttpHost httphost, AuthScheme authscheme, HttpContext httpcontext)
    {
        super.authFailed(httphost, authscheme, httpcontext);
    }

    public volatile void authSucceeded(HttpHost httphost, AuthScheme authscheme, HttpContext httpcontext)
    {
        super.authSucceeded(httphost, authscheme, httpcontext);
    }

    public volatile Map getChallenges(HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        return super.getChallenges(httphost, httpresponse, httpcontext);
    }

    Collection getPreferredAuthSchemes(RequestConfig requestconfig)
    {
        return requestconfig.getProxyPreferredAuthSchemes();
    }

    public volatile boolean isAuthenticationRequested(HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
    {
        return super.isAuthenticationRequested(httphost, httpresponse, httpcontext);
    }

    public volatile Queue select(Map map, HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        return super.select(map, httphost, httpresponse, httpcontext);
    }

    public static final ProxyAuthenticationStrategy INSTANCE = new ProxyAuthenticationStrategy();

}
