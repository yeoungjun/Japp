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
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class ResponseAuthCache
    implements HttpResponseInterceptor
{

    public ResponseAuthCache()
    {
    }

    private void cache(AuthCache authcache, HttpHost httphost, AuthScheme authscheme)
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Caching '").append(authscheme.getSchemeName()).append("' auth scheme for ").append(httphost).toString());
        authcache.put(httphost, authscheme);
    }

    private boolean isCachable(AuthState authstate)
    {
        AuthScheme authscheme = authstate.getAuthScheme();
        String s;
        if(authscheme != null && authscheme.isComplete())
            if((s = authscheme.getSchemeName()).equalsIgnoreCase("Basic") || s.equalsIgnoreCase("Digest"))
                return true;
        return false;
    }

    private void uncache(AuthCache authcache, HttpHost httphost, AuthScheme authscheme)
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Removing from cache '").append(authscheme.getSchemeName()).append("' auth scheme for ").append(httphost).toString());
        authcache.remove(httphost);
    }

    public void process(HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Object obj;
        HttpHost httphost;
        AuthState authstate;
        Args.notNull(httpresponse, "HTTP request");
        Args.notNull(httpcontext, "HTTP context");
        obj = (AuthCache)httpcontext.getAttribute("http.auth.auth-cache");
        httphost = (HttpHost)httpcontext.getAttribute("http.target_host");
        authstate = (AuthState)httpcontext.getAttribute("http.auth.target-scope");
        if(httphost == null || authstate == null) goto _L2; else goto _L1
_L1:
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Target auth state: ").append(authstate.getState()).toString());
        if(!isCachable(authstate)) goto _L2; else goto _L3
_L3:
        SchemeRegistry schemeregistry = (SchemeRegistry)httpcontext.getAttribute("http.scheme-registry");
        if(httphost.getPort() < 0)
        {
            Scheme scheme = schemeregistry.getScheme(httphost);
            httphost = new HttpHost(httphost.getHostName(), scheme.resolvePort(httphost.getPort()), httphost.getSchemeName());
        }
        if(obj == null)
        {
            obj = new BasicAuthCache();
            httpcontext.setAttribute("http.auth.auth-cache", obj);
        }
        static class _cls1
        {

            static final int $SwitchMap$org$apache$http$auth$AuthProtocolState[];

            static 
            {
                $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];
                try
                {
                    $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.CHALLENGED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.FAILURE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1)
                {
                    return;
                }
            }
        }

        _cls1..SwitchMap.org.apache.http.auth.AuthProtocolState[authstate.getState().ordinal()];
        JVM INSTR tableswitch 1 2: default 232
    //                   1 381
    //                   2 397;
           goto _L2 _L4 _L5
_L2:
        HttpHost httphost1;
        AuthState authstate1;
        httphost1 = (HttpHost)httpcontext.getAttribute("http.proxy_host");
        authstate1 = (AuthState)httpcontext.getAttribute("http.auth.proxy-scope");
        if(httphost1 == null || authstate1 == null) goto _L7; else goto _L6
_L6:
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Proxy auth state: ").append(authstate1.getState()).toString());
        if(!isCachable(authstate1)) goto _L7; else goto _L8
_L8:
        if(obj == null)
        {
            obj = new BasicAuthCache();
            httpcontext.setAttribute("http.auth.auth-cache", obj);
        }
        _cls1..SwitchMap.org.apache.http.auth.AuthProtocolState[authstate1.getState().ordinal()];
        JVM INSTR tableswitch 1 2: default 380
    //                   1 413
    //                   2 427;
           goto _L7 _L9 _L10
_L7:
        return;
_L4:
        cache(((AuthCache) (obj)), httphost, authstate.getAuthScheme());
          goto _L2
_L5:
        uncache(((AuthCache) (obj)), httphost, authstate.getAuthScheme());
          goto _L2
_L9:
        cache(((AuthCache) (obj)), httphost1, authstate1.getAuthScheme());
        return;
_L10:
        uncache(((AuthCache) (obj)), httphost1, authstate1.getAuthScheme());
        return;
    }

    private final Log log = LogFactory.getLog(getClass());
}
