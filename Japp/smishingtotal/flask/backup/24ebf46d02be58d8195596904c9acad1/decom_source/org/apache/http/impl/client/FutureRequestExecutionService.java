// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.client:
//            FutureRequestExecutionMetrics, HttpRequestFutureTask, HttpRequestTaskCallable

public class FutureRequestExecutionService
    implements Closeable
{

    public FutureRequestExecutionService(HttpClient httpclient1, ExecutorService executorservice)
    {
        httpclient = httpclient1;
        executorService = executorservice;
    }

    public void close()
        throws IOException
    {
        closed.set(true);
        executorService.shutdownNow();
        if(httpclient instanceof Closeable)
            ((Closeable)httpclient).close();
    }

    public HttpRequestFutureTask execute(HttpUriRequest httpurirequest, HttpContext httpcontext, ResponseHandler responsehandler)
    {
        return execute(httpurirequest, httpcontext, responsehandler, null);
    }

    public HttpRequestFutureTask execute(HttpUriRequest httpurirequest, HttpContext httpcontext, ResponseHandler responsehandler, FutureCallback futurecallback)
    {
        if(closed.get())
        {
            throw new IllegalStateException("Close has been called on this httpclient instance.");
        } else
        {
            metrics.getScheduledConnections().incrementAndGet();
            HttpRequestFutureTask httprequestfuturetask = new HttpRequestFutureTask(httpurirequest, new HttpRequestTaskCallable(httpclient, httpurirequest, httpcontext, responsehandler, futurecallback, metrics));
            executorService.execute(httprequestfuturetask);
            return httprequestfuturetask;
        }
    }

    public FutureRequestExecutionMetrics metrics()
    {
        return metrics;
    }

    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final ExecutorService executorService;
    private final HttpClient httpclient;
    private final FutureRequestExecutionMetrics metrics = new FutureRequestExecutionMetrics();
}
