// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;

// Referenced classes of package org.apache.http.client.methods:
//            HttpRequestBase

public class HttpDelete extends HttpRequestBase
{

    public HttpDelete()
    {
    }

    public HttpDelete(String s)
    {
        setURI(URI.create(s));
    }

    public HttpDelete(URI uri)
    {
        setURI(uri);
    }

    public String getMethod()
    {
        return "DELETE";
    }

    public static final String METHOD_NAME = "DELETE";
}
