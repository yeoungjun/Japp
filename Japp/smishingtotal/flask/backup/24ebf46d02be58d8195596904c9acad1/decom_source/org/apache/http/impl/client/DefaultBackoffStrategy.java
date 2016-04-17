// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ConnectionBackoffStrategy;

public class DefaultBackoffStrategy
    implements ConnectionBackoffStrategy
{

    public DefaultBackoffStrategy()
    {
    }

    public boolean shouldBackoff(Throwable throwable)
    {
        return (throwable instanceof SocketTimeoutException) || (throwable instanceof ConnectException);
    }

    public boolean shouldBackoff(HttpResponse httpresponse)
    {
        return httpresponse.getStatusLine().getStatusCode() == 503;
    }
}
