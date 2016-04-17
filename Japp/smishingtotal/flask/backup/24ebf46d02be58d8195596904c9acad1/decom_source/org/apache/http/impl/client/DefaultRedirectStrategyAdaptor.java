// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.*;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.*;
import org.apache.http.protocol.HttpContext;

class DefaultRedirectStrategyAdaptor
    implements RedirectStrategy
{

    public DefaultRedirectStrategyAdaptor(RedirectHandler redirecthandler)
    {
        handler = redirecthandler;
    }

    public RedirectHandler getHandler()
    {
        return handler;
    }

    public HttpUriRequest getRedirect(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws ProtocolException
    {
        java.net.URI uri = handler.getLocationURI(httpresponse, httpcontext);
        if(httprequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD"))
            return new HttpHead(uri);
        else
            return new HttpGet(uri);
    }

    public boolean isRedirected(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws ProtocolException
    {
        return handler.isRedirectRequested(httpresponse, httpcontext);
    }

    private final RedirectHandler handler;
}
