// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.config.Lookup;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.auth:
//            AuthSchemeFactory, AuthScheme, AuthSchemeProvider

public final class AuthSchemeRegistry
    implements Lookup
{

    public AuthSchemeRegistry()
    {
    }

    public AuthScheme getAuthScheme(String s, HttpParams httpparams)
        throws IllegalStateException
    {
        Args.notNull(s, "Name");
        AuthSchemeFactory authschemefactory = (AuthSchemeFactory)registeredSchemes.get(s.toLowerCase(Locale.ENGLISH));
        if(authschemefactory != null)
            return authschemefactory.newInstance(httpparams);
        else
            throw new IllegalStateException((new StringBuilder()).append("Unsupported authentication scheme: ").append(s).toString());
    }

    public List getSchemeNames()
    {
        return new ArrayList(registeredSchemes.keySet());
    }

    public volatile Object lookup(String s)
    {
        return lookup(s);
    }

    public AuthSchemeProvider lookup(final String name)
    {
        return new AuthSchemeProvider() {

            public AuthScheme create(HttpContext httpcontext)
            {
                HttpRequest httprequest = (HttpRequest)httpcontext.getAttribute("http.request");
                return getAuthScheme(name, httprequest.getParams());
            }

            final AuthSchemeRegistry this$0;
            final String val$name;

            
            {
                this$0 = AuthSchemeRegistry.this;
                name = s;
                super();
            }
        };
    }

    public void register(String s, AuthSchemeFactory authschemefactory)
    {
        Args.notNull(s, "Name");
        Args.notNull(authschemefactory, "Authentication scheme factory");
        registeredSchemes.put(s.toLowerCase(Locale.ENGLISH), authschemefactory);
    }

    public void setItems(Map map)
    {
        if(map == null)
        {
            return;
        } else
        {
            registeredSchemes.clear();
            registeredSchemes.putAll(map);
            return;
        }
    }

    public void unregister(String s)
    {
        Args.notNull(s, "Name");
        registeredSchemes.remove(s.toLowerCase(Locale.ENGLISH));
    }

    private final ConcurrentHashMap registeredSchemes = new ConcurrentHashMap();
}
