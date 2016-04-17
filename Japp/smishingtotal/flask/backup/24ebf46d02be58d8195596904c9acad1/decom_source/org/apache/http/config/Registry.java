// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.config;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Referenced classes of package org.apache.http.config:
//            Lookup

public final class Registry
    implements Lookup
{

    Registry(Map map1)
    {
        map = new ConcurrentHashMap(map1);
    }

    public Object lookup(String s)
    {
        if(s == null)
            return null;
        else
            return map.get(s.toLowerCase(Locale.US));
    }

    public String toString()
    {
        return map.toString();
    }

    private final Map map;
}
