// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.io.HttpTransportMetrics;

public class HttpConnectionMetricsImpl
    implements HttpConnectionMetrics
{

    public HttpConnectionMetricsImpl(HttpTransportMetrics httptransportmetrics, HttpTransportMetrics httptransportmetrics1)
    {
        requestCount = 0L;
        responseCount = 0L;
        inTransportMetric = httptransportmetrics;
        outTransportMetric = httptransportmetrics1;
    }

    public Object getMetric(String s)
    {
        Map map = metricsCache;
        Object obj = null;
        if(map != null)
            obj = metricsCache.get(s);
        if(obj == null)
            if("http.request-count".equals(s))
            {
                obj = Long.valueOf(requestCount);
            } else
            {
                if("http.response-count".equals(s))
                    return Long.valueOf(responseCount);
                if("http.received-bytes-count".equals(s))
                    if(inTransportMetric != null)
                        return Long.valueOf(inTransportMetric.getBytesTransferred());
                    else
                        return null;
                if("http.sent-bytes-count".equals(s))
                    if(outTransportMetric != null)
                        return Long.valueOf(outTransportMetric.getBytesTransferred());
                    else
                        return null;
            }
        return obj;
    }

    public long getReceivedBytesCount()
    {
        if(inTransportMetric != null)
            return inTransportMetric.getBytesTransferred();
        else
            return -1L;
    }

    public long getRequestCount()
    {
        return requestCount;
    }

    public long getResponseCount()
    {
        return responseCount;
    }

    public long getSentBytesCount()
    {
        if(outTransportMetric != null)
            return outTransportMetric.getBytesTransferred();
        else
            return -1L;
    }

    public void incrementRequestCount()
    {
        requestCount = 1L + requestCount;
    }

    public void incrementResponseCount()
    {
        responseCount = 1L + responseCount;
    }

    public void reset()
    {
        if(outTransportMetric != null)
            outTransportMetric.reset();
        if(inTransportMetric != null)
            inTransportMetric.reset();
        requestCount = 0L;
        responseCount = 0L;
        metricsCache = null;
    }

    public void setMetric(String s, Object obj)
    {
        if(metricsCache == null)
            metricsCache = new HashMap();
        metricsCache.put(s, obj);
    }

    public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
    public static final String REQUEST_COUNT = "http.request-count";
    public static final String RESPONSE_COUNT = "http.response-count";
    public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
    private final HttpTransportMetrics inTransportMetric;
    private Map metricsCache;
    private final HttpTransportMetrics outTransportMetric;
    private long requestCount;
    private long responseCount;
}
