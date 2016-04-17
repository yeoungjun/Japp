// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.util.Map;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpRequestHandlerResolver, UriPatternMatcher, HttpRequestHandler

public class HttpRequestHandlerRegistry
    implements HttpRequestHandlerResolver
{

    public HttpRequestHandlerRegistry()
    {
    }

    public Map getHandlers()
    {
        return matcher.getObjects();
    }

    public HttpRequestHandler lookup(String s)
    {
        return (HttpRequestHandler)matcher.lookup(s);
    }

    public void register(String s, HttpRequestHandler httprequesthandler)
    {
        Args.notNull(s, "URI request pattern");
        Args.notNull(httprequesthandler, "Request handler");
        matcher.register(s, httprequesthandler);
    }

    public void setHandlers(Map map)
    {
        matcher.setObjects(map);
    }

    public void unregister(String s)
    {
        matcher.unregister(s);
    }

    private final UriPatternMatcher matcher = new UriPatternMatcher();
}
