// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;

abstract class RequestAuthenticationBase
    implements HttpRequestInterceptor
{

    public RequestAuthenticationBase()
    {
    }

    private Header authenticate(AuthScheme authscheme, Credentials credentials, HttpRequest httprequest, HttpContext httpcontext)
        throws AuthenticationException
    {
        Asserts.notNull(authscheme, "Auth scheme");
        if(authscheme instanceof ContextAwareAuthScheme)
            return ((ContextAwareAuthScheme)authscheme).authenticate(credentials, httprequest, httpcontext);
        else
            return authscheme.authenticate(credentials, httprequest);
    }

    private void ensureAuthScheme(AuthScheme authscheme)
    {
        Asserts.notNull(authscheme, "Auth scheme");
    }

    void process(AuthState authstate, HttpRequest httprequest, HttpContext httpcontext)
    {
        AuthScheme authscheme;
        Credentials credentials;
        authscheme = authstate.getAuthScheme();
        credentials = authstate.getCredentials();
        static class _cls1
        {

            static final int $SwitchMap$org$apache$http$auth$AuthProtocolState[];

            static 
            {
                $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];
                try
                {
                    $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.FAILURE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.SUCCESS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.CHALLENGED.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2)
                {
                    return;
                }
            }
        }

        _cls1..SwitchMap.org.apache.http.auth.AuthProtocolState[authstate.getState().ordinal()];
        JVM INSTR tableswitch 1 3: default 48
    //                   1 69
    //                   2 70
    //                   3 87;
           goto _L1 _L2 _L3 _L4
_L2:
        break; /* Loop/switch isn't completed */
_L1:
        if(authscheme == null)
            break; /* Loop/switch isn't completed */
        httprequest.addHeader(authenticate(authscheme, credentials, httprequest, httpcontext));
_L5:
        return;
_L3:
        ensureAuthScheme(authscheme);
        if(authscheme.isConnectionBased())
            return;
        continue; /* Loop/switch isn't completed */
_L4:
        Queue queue = authstate.getAuthOptions();
        if(queue != null)
        {
            while(!queue.isEmpty()) 
            {
                AuthOption authoption = (AuthOption)queue.remove();
                AuthScheme authscheme1 = authoption.getAuthScheme();
                Credentials credentials1 = authoption.getCredentials();
                authstate.update(authscheme1, credentials1);
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Generating response to an authentication challenge using ").append(authscheme1.getSchemeName()).append(" scheme").toString());
                try
                {
                    httprequest.addHeader(authenticate(authscheme1, credentials1, httprequest, httpcontext));
                    return;
                }
                catch(AuthenticationException authenticationexception1)
                {
                    if(log.isWarnEnabled())
                        log.warn((new StringBuilder()).append(authscheme1).append(" authentication error: ").append(authenticationexception1.getMessage()).toString());
                }
            }
        } else
        {
            ensureAuthScheme(authscheme);
            continue; /* Loop/switch isn't completed */
        }
          goto _L5
        AuthenticationException authenticationexception;
        authenticationexception;
        if(!log.isErrorEnabled()) goto _L5; else goto _L6
_L6:
        log.error((new StringBuilder()).append(authscheme).append(" authentication error: ").append(authenticationexception.getMessage()).toString());
        return;
        if(true) goto _L1; else goto _L7
_L7:
    }

    final Log log = LogFactory.getLog(getClass());
}
