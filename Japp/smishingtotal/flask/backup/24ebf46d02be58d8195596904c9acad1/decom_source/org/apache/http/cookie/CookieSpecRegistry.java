// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.config.Lookup;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.cookie:
//            CookieSpecFactory, CookieSpec, CookieSpecProvider

public final class CookieSpecRegistry
    implements Lookup
{

    public CookieSpecRegistry()
    {
    }

    public CookieSpec getCookieSpec(String s)
        throws IllegalStateException
    {
        return getCookieSpec(s, null);
    }

    public CookieSpec getCookieSpec(String s, HttpParams httpparams)
        throws IllegalStateException
    {
        Args.notNull(s, "Name");
        CookieSpecFactory cookiespecfactory = (CookieSpecFactory)registeredSpecs.get(s.toLowerCase(Locale.ENGLISH));
        if(cookiespecfactory != null)
            return cookiespecfactory.newInstance(httpparams);
        else
            throw new IllegalStateException((new StringBuilder()).append("Unsupported cookie spec: ").append(s).toString());
    }

    public List getSpecNames()
    {
        return new ArrayList(registeredSpecs.keySet());
    }

    public volatile Object lookup(String s)
    {
        return lookup(s);
    }

    public CookieSpecProvider lookup(final String name)
    {
        return new CookieSpecProvider() {

            public CookieSpec create(HttpContext httpcontext)
            {
                HttpRequest httprequest = (HttpRequest)httpcontext.getAttribute("http.request");
                return getCookieSpec(name, httprequest.getParams());
            }

            final CookieSpecRegistry this$0;
            final String val$name;

            
            {
                this$0 = CookieSpecRegistry.this;
                name = s;
                super();
            }
        };
    }

    public void register(String s, CookieSpecFactory cookiespecfactory)
    {
        Args.notNull(s, "Name");
        Args.notNull(cookiespecfactory, "Cookie spec factory");
        registeredSpecs.put(s.toLowerCase(Locale.ENGLISH), cookiespecfactory);
    }

    public void setItems(Map map)
    {
        if(map == null)
        {
            return;
        } else
        {
            registeredSpecs.clear();
            registeredSpecs.putAll(map);
            return;
        }
    }

    public void unregister(String s)
    {
        Args.notNull(s, "Id");
        registeredSpecs.remove(s.toLowerCase(Locale.ENGLISH));
    }

    private final ConcurrentHashMap registeredSpecs = new ConcurrentHashMap();
}
