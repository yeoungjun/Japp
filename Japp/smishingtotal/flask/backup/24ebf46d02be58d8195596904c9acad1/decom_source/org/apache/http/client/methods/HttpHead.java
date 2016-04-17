// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;

// Referenced classes of package org.apache.http.client.methods:
//            HttpRequestBase

public class HttpHead extends HttpRequestBase
{

    public HttpHead()
    {
    }

    public HttpHead(String s)
    {
        setURI(URI.create(s));
    }

    public HttpHead(URI uri)
    {
        setURI(uri);
    }

    public String getMethod()
    {
        return "HEAD";
    }

    public static final String METHOD_NAME = "HEAD";
}
