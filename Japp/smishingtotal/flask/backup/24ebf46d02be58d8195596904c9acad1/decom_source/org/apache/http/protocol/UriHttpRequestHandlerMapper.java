// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import org.apache.http.HttpRequest;
import org.apache.http.RequestLine;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpRequestHandlerMapper, UriPatternMatcher, HttpRequestHandler

public class UriHttpRequestHandlerMapper
    implements HttpRequestHandlerMapper
{

    public UriHttpRequestHandlerMapper()
    {
        this(new UriPatternMatcher());
    }

    protected UriHttpRequestHandlerMapper(UriPatternMatcher uripatternmatcher)
    {
        matcher = (UriPatternMatcher)Args.notNull(uripatternmatcher, "Pattern matcher");
    }

    protected String getRequestPath(HttpRequest httprequest)
    {
        String s = httprequest.getRequestLine().getUri();
        int i = s.indexOf("?");
        if(i != -1)
        {
            s = s.substring(0, i);
        } else
        {
            int j = s.indexOf("#");
            if(j != -1)
                return s.substring(0, j);
        }
        return s;
    }

    public HttpRequestHandler lookup(HttpRequest httprequest)
    {
        Args.notNull(httprequest, "HTTP request");
        return (HttpRequestHandler)matcher.lookup(getRequestPath(httprequest));
    }

    public void register(String s, HttpRequestHandler httprequesthandler)
    {
        Args.notNull(s, "Pattern");
        Args.notNull(httprequesthandler, "Handler");
        matcher.register(s, httprequesthandler);
    }

    public void unregister(String s)
    {
        matcher.unregister(s);
    }

    private final UriPatternMatcher matcher;
}
