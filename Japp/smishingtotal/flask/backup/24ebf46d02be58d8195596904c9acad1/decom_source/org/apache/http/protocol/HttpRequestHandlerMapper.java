// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import org.apache.http.HttpRequest;

// Referenced classes of package org.apache.http.protocol:
//            HttpRequestHandler

public interface HttpRequestHandlerMapper
{

    public abstract HttpRequestHandler lookup(HttpRequest httprequest);
}
