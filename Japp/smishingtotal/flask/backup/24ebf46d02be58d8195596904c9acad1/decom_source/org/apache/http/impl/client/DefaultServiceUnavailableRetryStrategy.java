// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

public class DefaultServiceUnavailableRetryStrategy
    implements ServiceUnavailableRetryStrategy
{

    public DefaultServiceUnavailableRetryStrategy()
    {
        this(1, 1000);
    }

    public DefaultServiceUnavailableRetryStrategy(int i, int j)
    {
        Args.positive(i, "Max retries");
        Args.positive(j, "Retry interval");
        maxRetries = i;
        retryInterval = j;
    }

    public long getRetryInterval()
    {
        return retryInterval;
    }

    public boolean retryRequest(HttpResponse httpresponse, int i, HttpContext httpcontext)
    {
        return i <= maxRetries && httpresponse.getStatusLine().getStatusCode() == 503;
    }

    private final int maxRetries;
    private final long retryInterval;
}
