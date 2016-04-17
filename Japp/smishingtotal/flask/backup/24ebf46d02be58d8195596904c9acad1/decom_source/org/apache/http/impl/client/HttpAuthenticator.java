// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.commons.logging.Log;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.protocol.HttpContext;

public class HttpAuthenticator extends org.apache.http.impl.auth.HttpAuthenticator
{

    public HttpAuthenticator()
    {
    }

    public HttpAuthenticator(Log log)
    {
        super(log);
    }

    public boolean authenticate(HttpHost httphost, HttpResponse httpresponse, AuthenticationStrategy authenticationstrategy, AuthState authstate, HttpContext httpcontext)
    {
        return handleAuthChallenge(httphost, httpresponse, authenticationstrategy, authstate, httpcontext);
    }
}
