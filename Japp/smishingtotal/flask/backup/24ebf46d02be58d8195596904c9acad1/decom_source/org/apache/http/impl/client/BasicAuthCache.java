// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.HashMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;
import org.apache.http.client.AuthCache;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.util.Args;

public class BasicAuthCache
    implements AuthCache
{

    public BasicAuthCache()
    {
        this(null);
    }

    public BasicAuthCache(SchemePortResolver schemeportresolver)
    {
        map = new HashMap();
        if(schemeportresolver == null)
            schemeportresolver = DefaultSchemePortResolver.INSTANCE;
        schemePortResolver = schemeportresolver;
    }

    public void clear()
    {
        map.clear();
    }

    public AuthScheme get(HttpHost httphost)
    {
        Args.notNull(httphost, "HTTP host");
        return (AuthScheme)map.get(getKey(httphost));
    }

    protected HttpHost getKey(HttpHost httphost)
    {
        if(httphost.getPort() <= 0)
        {
            int i;
            try
            {
                i = schemePortResolver.resolve(httphost);
            }
            catch(UnsupportedSchemeException unsupportedschemeexception)
            {
                return httphost;
            }
            httphost = new HttpHost(httphost.getHostName(), i, httphost.getSchemeName());
        }
        return httphost;
    }

    public void put(HttpHost httphost, AuthScheme authscheme)
    {
        Args.notNull(httphost, "HTTP host");
        map.put(getKey(httphost), authscheme);
    }

    public void remove(HttpHost httphost)
    {
        Args.notNull(httphost, "HTTP host");
        map.remove(getKey(httphost));
    }

    public String toString()
    {
        return map.toString();
    }

    private final HashMap map;
    private final SchemePortResolver schemePortResolver;
}
