// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;

// Referenced classes of package org.apache.http.client.methods:
//            HttpEntityEnclosingRequestBase

public class HttpPatch extends HttpEntityEnclosingRequestBase
{

    public HttpPatch()
    {
    }

    public HttpPatch(String s)
    {
        setURI(URI.create(s));
    }

    public HttpPatch(URI uri)
    {
        setURI(uri);
    }

    public String getMethod()
    {
        return "PATCH";
    }

    public static final String METHOD_NAME = "PATCH";
}
