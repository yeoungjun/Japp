// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpContext

public class BasicHttpContext
    implements HttpContext
{

    public BasicHttpContext()
    {
        this(null);
    }

    public BasicHttpContext(HttpContext httpcontext)
    {
        map = null;
        parentContext = httpcontext;
    }

    public void clear()
    {
        if(map != null)
            map.clear();
    }

    public Object getAttribute(String s)
    {
        Args.notNull(s, "Id");
        Map map1 = map;
        Object obj = null;
        if(map1 != null)
            obj = map.get(s);
        if(obj == null && parentContext != null)
            obj = parentContext.getAttribute(s);
        return obj;
    }

    public Object removeAttribute(String s)
    {
        Args.notNull(s, "Id");
        if(map != null)
            return map.remove(s);
        else
            return null;
    }

    public void setAttribute(String s, Object obj)
    {
        Args.notNull(s, "Id");
        if(map == null)
            map = new HashMap();
        map.put(s, obj);
    }

    public String toString()
    {
        if(map != null)
            return map.toString();
        else
            return "{}";
    }

    private Map map;
    private final HttpContext parentContext;
}
