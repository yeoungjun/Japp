// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;

import java.io.IOException;
import org.apache.http.protocol.HttpContext;

public interface HttpRequestRetryHandler
{

    public abstract boolean retryRequest(IOException ioexception, int i, HttpContext httpcontext);
}
