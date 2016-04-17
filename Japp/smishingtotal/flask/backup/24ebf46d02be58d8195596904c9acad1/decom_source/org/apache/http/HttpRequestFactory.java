// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;


// Referenced classes of package org.apache.http:
//            MethodNotSupportedException, HttpRequest, RequestLine

public interface HttpRequestFactory
{

    public abstract HttpRequest newHttpRequest(String s, String s1)
        throws MethodNotSupportedException;

    public abstract HttpRequest newHttpRequest(RequestLine requestline)
        throws MethodNotSupportedException;
}
