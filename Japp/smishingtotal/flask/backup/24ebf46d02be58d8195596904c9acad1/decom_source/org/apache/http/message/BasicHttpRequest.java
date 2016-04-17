// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.message:
//            AbstractHttpMessage, BasicRequestLine

public class BasicHttpRequest extends AbstractHttpMessage
    implements HttpRequest
{

    public BasicHttpRequest(String s, String s1)
    {
        method = (String)Args.notNull(s, "Method name");
        uri = (String)Args.notNull(s1, "Request URI");
        requestline = null;
    }

    public BasicHttpRequest(String s, String s1, ProtocolVersion protocolversion)
    {
        this(((RequestLine) (new BasicRequestLine(s, s1, protocolversion))));
    }

    public BasicHttpRequest(RequestLine requestline1)
    {
        requestline = (RequestLine)Args.notNull(requestline1, "Request line");
        method = requestline1.getMethod();
        uri = requestline1.getUri();
    }

    public ProtocolVersion getProtocolVersion()
    {
        return getRequestLine().getProtocolVersion();
    }

    public RequestLine getRequestLine()
    {
        if(requestline == null)
            requestline = new BasicRequestLine(method, uri, HttpVersion.HTTP_1_1);
        return requestline;
    }

    public String toString()
    {
        return (new StringBuilder()).append(method).append(" ").append(uri).append(" ").append(headergroup).toString();
    }

    private final String method;
    private RequestLine requestline;
    private final String uri;
}
