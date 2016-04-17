// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.protocol:
//            HttpClientContext

public class RequestAuthCache
    implements HttpRequestInterceptor
{

    public RequestAuthCache()
    {
    }

    private void doPreemptiveAuth(HttpHost httphost, AuthScheme authscheme, AuthState authstate, CredentialsProvider credentialsprovider)
    {
        String s = authscheme.getSchemeName();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Re-using cached '").append(s).append("' auth scheme for ").append(httphost).toString());
        org.apache.http.auth.Credentials credentials = credentialsprovider.getCredentials(new AuthScope(httphost, AuthScope.ANY_REALM, s));
        if(credentials != null)
        {
            if("BASIC".equalsIgnoreCase(authscheme.getSchemeName()))
                authstate.setState(AuthProtocolState.CHALLENGED);
            else
                authstate.setState(AuthProtocolState.SUCCESS);
            authstate.update(authscheme, credentials);
            return;
        } else
        {
            log.debug("No credentials for preemptive authentication");
            return;
        }
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpcontext, "HTTP context");
        HttpClientContext httpclientcontext = HttpClientContext.adapt(httpcontext);
        AuthCache authcache = httpclientcontext.getAuthCache();
        if(authcache == null)
        {
            log.debug("Auth cache not set in the context");
        } else
        {
            CredentialsProvider credentialsprovider = httpclientcontext.getCredentialsProvider();
            if(credentialsprovider == null)
            {
                log.debug("Credentials provider not set in the context");
                return;
            }
            RouteInfo routeinfo = httpclientcontext.getHttpRoute();
            HttpHost httphost = httpclientcontext.getTargetHost();
            if(httphost.getPort() < 0)
                httphost = new HttpHost(httphost.getHostName(), routeinfo.getTargetHost().getPort(), httphost.getSchemeName());
            AuthState authstate = httpclientcontext.getTargetAuthState();
            if(authstate != null && authstate.getState() == AuthProtocolState.UNCHALLENGED)
            {
                AuthScheme authscheme1 = authcache.get(httphost);
                if(authscheme1 != null)
                    doPreemptiveAuth(httphost, authscheme1, authstate, credentialsprovider);
            }
            HttpHost httphost1 = routeinfo.getProxyHost();
            AuthState authstate1 = httpclientcontext.getProxyAuthState();
            if(httphost1 != null && authstate1 != null && authstate1.getState() == AuthProtocolState.UNCHALLENGED)
            {
                AuthScheme authscheme = authcache.get(httphost1);
                if(authscheme != null)
                {
                    doPreemptiveAuth(httphost1, authscheme, authstate1, credentialsprovider);
                    return;
                }
            }
        }
    }

    private final Log log = LogFactory.getLog(getClass());
}
