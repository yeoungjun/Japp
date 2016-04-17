// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.client:
//            FutureRequestExecutionMetrics

class HttpRequestTaskCallable
    implements Callable
{

    HttpRequestTaskCallable(HttpClient httpclient1, HttpUriRequest httpurirequest, HttpContext httpcontext, ResponseHandler responsehandler, FutureCallback futurecallback, FutureRequestExecutionMetrics futurerequestexecutionmetrics)
    {
        started = -1L;
        ended = -1L;
        httpclient = httpclient1;
        responseHandler = responsehandler;
        request = httpurirequest;
        context = httpcontext;
        callback = futurecallback;
        metrics = futurerequestexecutionmetrics;
    }

    public Object call()
        throws Exception
    {
        if(cancelled.get())
            break MISSING_BLOCK_LABEL_229;
        metrics.getActiveConnections().incrementAndGet();
        started = System.currentTimeMillis();
        Object obj;
        metrics.getScheduledConnections().decrementAndGet();
        obj = httpclient.execute(request, responseHandler, context);
        ended = System.currentTimeMillis();
        metrics.getSuccessfulConnections().increment(started);
        if(callback != null)
            callback.completed(obj);
        metrics.getRequests().increment(started);
        metrics.getTasks().increment(started);
        metrics.getActiveConnections().decrementAndGet();
        return obj;
        Exception exception1;
        exception1;
        metrics.getFailedConnections().increment(started);
        ended = System.currentTimeMillis();
        if(callback != null)
            callback.failed(exception1);
        throw exception1;
        Exception exception;
        exception;
        metrics.getRequests().increment(started);
        metrics.getTasks().increment(started);
        metrics.getActiveConnections().decrementAndGet();
        throw exception;
        throw new IllegalStateException((new StringBuilder()).append("call has been cancelled for request ").append(request.getURI()).toString());
    }

    public void cancel()
    {
        cancelled.set(true);
        if(callback != null)
            callback.cancelled();
    }

    public long getEnded()
    {
        return ended;
    }

    public long getScheduled()
    {
        return scheduled;
    }

    public long getStarted()
    {
        return started;
    }

    private final FutureCallback callback;
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private final HttpContext context;
    private long ended;
    private final HttpClient httpclient;
    private final FutureRequestExecutionMetrics metrics;
    private final HttpUriRequest request;
    private final ResponseHandler responseHandler;
    private final long scheduled = System.currentTimeMillis();
    private long started;
}
