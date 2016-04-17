// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.concurrent.FutureTask;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;

// Referenced classes of package org.apache.http.impl.client:
//            HttpRequestTaskCallable

public class HttpRequestFutureTask extends FutureTask
{

    public HttpRequestFutureTask(HttpUriRequest httpurirequest, HttpRequestTaskCallable httprequesttaskcallable)
    {
        super(httprequesttaskcallable);
        request = httpurirequest;
        callable = httprequesttaskcallable;
    }

    public boolean cancel(boolean flag)
    {
        callable.cancel();
        if(flag)
            request.abort();
        return super.cancel(flag);
    }

    public long endedTime()
    {
        if(isDone())
            return callable.getEnded();
        else
            throw new IllegalStateException("Task is not done yet");
    }

    public long requestDuration()
    {
        if(isDone())
            return endedTime() - startedTime();
        else
            throw new IllegalStateException("Task is not done yet");
    }

    public long scheduledTime()
    {
        return callable.getScheduled();
    }

    public long startedTime()
    {
        return callable.getStarted();
    }

    public long taskDuration()
    {
        if(isDone())
            return endedTime() - scheduledTime();
        else
            throw new IllegalStateException("Task is not done yet");
    }

    public String toString()
    {
        return request.getRequestLine().getUri();
    }

    private final HttpRequestTaskCallable callable;
    private final HttpUriRequest request;
}
