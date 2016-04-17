// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpConnection;
import org.apache.http.auth.*;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.protocol.HttpContext;

public class DefaultUserTokenHandler
    implements UserTokenHandler
{

    public DefaultUserTokenHandler()
    {
    }

    private static Principal getAuthPrincipal(AuthState authstate)
    {
        AuthScheme authscheme = authstate.getAuthScheme();
        if(authscheme != null && authscheme.isComplete() && authscheme.isConnectionBased())
        {
            Credentials credentials = authstate.getCredentials();
            if(credentials != null)
                return credentials.getUserPrincipal();
        }
        return null;
    }

    public Object getUserToken(HttpContext httpcontext)
    {
        HttpClientContext httpclientcontext = HttpClientContext.adapt(httpcontext);
        AuthState authstate = httpclientcontext.getTargetAuthState();
        Principal principal = null;
        if(authstate != null)
        {
            principal = getAuthPrincipal(authstate);
            if(principal == null)
                principal = getAuthPrincipal(httpclientcontext.getProxyAuthState());
        }
        if(principal == null)
        {
            HttpConnection httpconnection = httpclientcontext.getConnection();
            if(httpconnection.isOpen() && (httpconnection instanceof ManagedHttpClientConnection))
            {
                SSLSession sslsession = ((ManagedHttpClientConnection)httpconnection).getSSLSession();
                if(sslsession != null)
                    principal = sslsession.getLocalPrincipal();
            }
        }
        return principal;
    }

    public static final DefaultUserTokenHandler INSTANCE = new DefaultUserTokenHandler();

}
