// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import org.apache.http.io.HttpTransportMetrics;

public class HttpTransportMetricsImpl
    implements HttpTransportMetrics
{

    public HttpTransportMetricsImpl()
    {
        bytesTransferred = 0L;
    }

    public long getBytesTransferred()
    {
        return bytesTransferred;
    }

    public void incrementBytesTransferred(long l)
    {
        bytesTransferred = l + bytesTransferred;
    }

    public void reset()
    {
        bytesTransferred = 0L;
    }

    public void setBytesTransferred(long l)
    {
        bytesTransferred = l;
    }

    private long bytesTransferred;
}
