// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;

import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.auth.*;
import org.apache.http.protocol.HttpContext;

public interface AuthenticationHandler
{

    public abstract Map getChallenges(HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException;

    public abstract boolean isAuthenticationRequested(HttpResponse httpresponse, HttpContext httpcontext);

    public abstract AuthScheme selectScheme(Map map, HttpResponse httpresponse, HttpContext httpcontext)
        throws AuthenticationException;
}
