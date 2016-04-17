// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import org.apache.http.*;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultRoutePlanner

public class DefaultProxyRoutePlanner extends DefaultRoutePlanner
{

    public DefaultProxyRoutePlanner(HttpHost httphost)
    {
        this(httphost, null);
    }

    public DefaultProxyRoutePlanner(HttpHost httphost, SchemePortResolver schemeportresolver)
    {
        super(schemeportresolver);
        proxy = (HttpHost)Args.notNull(httphost, "Proxy host");
    }

    protected HttpHost determineProxy(HttpHost httphost, HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException
    {
        return proxy;
    }

    private final HttpHost proxy;
}
