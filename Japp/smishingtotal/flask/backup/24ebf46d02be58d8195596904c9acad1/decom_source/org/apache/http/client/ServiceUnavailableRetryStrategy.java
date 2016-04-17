// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public interface ServiceUnavailableRetryStrategy
{

    public abstract long getRetryInterval();

    public abstract boolean retryRequest(HttpResponse httpresponse, int i, HttpContext httpcontext);
}
