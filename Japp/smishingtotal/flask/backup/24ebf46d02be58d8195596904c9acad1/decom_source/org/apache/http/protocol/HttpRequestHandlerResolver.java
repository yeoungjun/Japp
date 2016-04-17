// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;


// Referenced classes of package org.apache.http.protocol:
//            HttpRequestHandler

public interface HttpRequestHandlerResolver
{

    public abstract HttpRequestHandler lookup(String s);
}
