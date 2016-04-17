// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.conn.scheme:
//            Scheme

public final class SchemeRegistry
{

    public SchemeRegistry()
    {
    }

    public final Scheme get(String s)
    {
        Args.notNull(s, "Scheme name");
        return (Scheme)registeredSchemes.get(s);
    }

    public final Scheme getScheme(String s)
    {
        Scheme scheme = get(s);
        if(scheme == null)
            throw new IllegalStateException((new StringBuilder()).append("Scheme '").append(s).append("' not registered.").toString());
        else
            return scheme;
    }

    public final Scheme getScheme(HttpHost httphost)
    {
        Args.notNull(httphost, "Host");
        return getScheme(httphost.getSchemeName());
    }

    public final List getSchemeNames()
    {
        return new ArrayList(registeredSchemes.keySet());
    }

    public final Scheme register(Scheme scheme)
    {
        Args.notNull(scheme, "Scheme");
        return (Scheme)registeredSchemes.put(scheme.getName(), scheme);
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

    public final Scheme unregister(String s)
    {
        Args.notNull(s, "Scheme name");
        return (Scheme)registeredSchemes.remove(s);
    }

    private final ConcurrentHashMap registeredSchemes = new ConcurrentHashMap();
}
