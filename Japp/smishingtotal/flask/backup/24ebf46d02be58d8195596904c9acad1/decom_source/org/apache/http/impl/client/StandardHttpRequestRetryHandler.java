// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpRequest;
import org.apache.http.RequestLine;

// Referenced classes of package org.apache.http.impl.client:
//            DefaultHttpRequestRetryHandler

public class StandardHttpRequestRetryHandler extends DefaultHttpRequestRetryHandler
{

    public StandardHttpRequestRetryHandler()
    {
        this(3, false);
    }

    public StandardHttpRequestRetryHandler(int i, boolean flag)
    {
        super(i, flag);
        idempotentMethods = new ConcurrentHashMap();
        idempotentMethods.put("GET", Boolean.TRUE);
        idempotentMethods.put("HEAD", Boolean.TRUE);
        idempotentMethods.put("PUT", Boolean.TRUE);
        idempotentMethods.put("DELETE", Boolean.TRUE);
        idempotentMethods.put("OPTIONS", Boolean.TRUE);
        idempotentMethods.put("TRACE", Boolean.TRUE);
    }

    protected boolean handleAsIdempotent(HttpRequest httprequest)
    {
        String s = httprequest.getRequestLine().getMethod().toUpperCase(Locale.US);
        Boolean boolean1 = (Boolean)idempotentMethods.get(s);
        return boolean1 != null && boolean1.booleanValue();
    }

    private final Map idempotentMethods;
}
