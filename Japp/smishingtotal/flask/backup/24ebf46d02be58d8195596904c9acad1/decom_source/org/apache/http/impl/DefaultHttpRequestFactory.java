// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import org.apache.http.*;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.util.Args;

public class DefaultHttpRequestFactory
    implements HttpRequestFactory
{

    public DefaultHttpRequestFactory()
    {
    }

    private static boolean isOneOf(String as[], String s)
    {
        int i = as.length;
        for(int j = 0; j < i; j++)
            if(as[j].equalsIgnoreCase(s))
                return true;

        return false;
    }

    public HttpRequest newHttpRequest(String s, String s1)
        throws MethodNotSupportedException
    {
        if(isOneOf(RFC2616_COMMON_METHODS, s))
            return new BasicHttpRequest(s, s1);
        if(isOneOf(RFC2616_ENTITY_ENC_METHODS, s))
            return new BasicHttpEntityEnclosingRequest(s, s1);
        if(isOneOf(RFC2616_SPECIAL_METHODS, s))
            return new BasicHttpRequest(s, s1);
        else
            throw new MethodNotSupportedException((new StringBuilder()).append(s).append(" method not supported").toString());
    }

    public HttpRequest newHttpRequest(RequestLine requestline)
        throws MethodNotSupportedException
    {
        Args.notNull(requestline, "Request line");
        String s = requestline.getMethod();
        if(isOneOf(RFC2616_COMMON_METHODS, s))
            return new BasicHttpRequest(requestline);
        if(isOneOf(RFC2616_ENTITY_ENC_METHODS, s))
            return new BasicHttpEntityEnclosingRequest(requestline);
        if(isOneOf(RFC2616_SPECIAL_METHODS, s))
            return new BasicHttpRequest(requestline);
        else
            throw new MethodNotSupportedException((new StringBuilder()).append(s).append(" method not supported").toString());
    }

    public static final DefaultHttpRequestFactory INSTANCE = new DefaultHttpRequestFactory();
    private static final String RFC2616_COMMON_METHODS[] = {
        "GET"
    };
    private static final String RFC2616_ENTITY_ENC_METHODS[] = {
        "POST", "PUT"
    };
    private static final String RFC2616_SPECIAL_METHODS[] = {
        "HEAD", "OPTIONS", "DELETE", "TRACE", "CONNECT"
    };

}
