// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            BasicAuthCache

class AuthenticationStrategyAdaptor
    implements AuthenticationStrategy
{

    public AuthenticationStrategyAdaptor(AuthenticationHandler authenticationhandler)
    {
        handler = authenticationhandler;
    }

    private boolean isCachable(AuthScheme authscheme)
    {
        String s;
        if(authscheme != null && authscheme.isComplete())
            if((s = authscheme.getSchemeName()).equalsIgnoreCase("Basic") || s.equalsIgnoreCase("Digest"))
                return true;
        return false;
    }

    public void authFailed(HttpHost httphost, AuthScheme authscheme, HttpContext httpcontext)
    {
        AuthCache authcache = (AuthCache)httpcontext.getAttribute("http.auth.auth-cache");
        if(authcache == null)
            return;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Removing from cache '").append(authscheme.getSchemeName()).append("' auth scheme for ").append(httphost).toString());
        authcache.remove(httphost);
    }

    public void authSucceeded(HttpHost httphost, AuthScheme authscheme, HttpContext httpcontext)
    {
        Object obj = (AuthCache)httpcontext.getAttribute("http.auth.auth-cache");
        if(isCachable(authscheme))
        {
            if(obj == null)
            {
                obj = new BasicAuthCache();
                httpcontext.setAttribute("http.auth.auth-cache", obj);
            }
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Caching '").append(authscheme.getSchemeName()).append("' auth scheme for ").append(httphost).toString());
            ((AuthCache) (obj)).put(httphost, authscheme);
        }
    }

    public Map getChallenges(HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        return handler.getChallenges(httpresponse, httpcontext);
    }

    public AuthenticationHandler getHandler()
    {
        return handler;
    }

    public boolean isAuthenticationRequested(HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
    {
        return handler.isAuthenticationRequested(httpresponse, httpcontext);
    }

    public Queue select(Map map, HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        LinkedList linkedlist;
        CredentialsProvider credentialsprovider;
        Args.notNull(map, "Map of auth challenges");
        Args.notNull(httphost, "Host");
        Args.notNull(httpresponse, "HTTP response");
        Args.notNull(httpcontext, "HTTP context");
        linkedlist = new LinkedList();
        credentialsprovider = (CredentialsProvider)httpcontext.getAttribute("http.auth.credentials-provider");
        if(credentialsprovider != null) goto _L2; else goto _L1
_L1:
        log.debug("Credentials provider not set in the context");
_L4:
        return linkedlist;
_L2:
        AuthScheme authscheme = handler.selectScheme(map, httpresponse, httpcontext);
        authscheme.processChallenge((Header)map.get(authscheme.getSchemeName().toLowerCase(Locale.US)));
        org.apache.http.auth.Credentials credentials = credentialsprovider.getCredentials(new AuthScope(httphost.getHostName(), httphost.getPort(), authscheme.getRealm(), authscheme.getSchemeName()));
        if(credentials != null)
        {
            linkedlist.add(new AuthOption(authscheme, credentials));
            return linkedlist;
        }
        continue; /* Loop/switch isn't completed */
        AuthenticationException authenticationexception;
        authenticationexception;
        if(log.isWarnEnabled())
        {
            log.warn(authenticationexception.getMessage(), authenticationexception);
            return linkedlist;
        }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private final AuthenticationHandler handler;
    private final Log log = LogFactory.getLog(getClass());
}
