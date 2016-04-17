// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.*;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.util.Args;

public abstract class AbstractCookieSpec
    implements CookieSpec
{

    public AbstractCookieSpec()
    {
    }

    protected CookieAttributeHandler findAttribHandler(String s)
    {
        return (CookieAttributeHandler)attribHandlerMap.get(s);
    }

    protected CookieAttributeHandler getAttribHandler(String s)
    {
        CookieAttributeHandler cookieattributehandler = findAttribHandler(s);
        if(cookieattributehandler == null)
            throw new IllegalStateException((new StringBuilder()).append("Handler not registered for ").append(s).append(" attribute.").toString());
        else
            return cookieattributehandler;
    }

    protected Collection getAttribHandlers()
    {
        return attribHandlerMap.values();
    }

    public void registerAttribHandler(String s, CookieAttributeHandler cookieattributehandler)
    {
        Args.notNull(s, "Attribute name");
        Args.notNull(cookieattributehandler, "Attribute handler");
        attribHandlerMap.put(s, cookieattributehandler);
    }

    private final Map attribHandlerMap = new HashMap(10);
}
