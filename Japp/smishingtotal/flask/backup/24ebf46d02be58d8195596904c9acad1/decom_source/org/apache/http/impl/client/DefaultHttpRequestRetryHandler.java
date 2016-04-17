// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.*;
import javax.net.ssl.SSLException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            RequestWrapper

public class DefaultHttpRequestRetryHandler
    implements HttpRequestRetryHandler
{

    public DefaultHttpRequestRetryHandler()
    {
        this(3, false);
    }

    public DefaultHttpRequestRetryHandler(int i, boolean flag)
    {
        this(i, flag, ((Collection) (Arrays.asList(new Class[] {
            java/io/InterruptedIOException, java/net/UnknownHostException, java/net/ConnectException, javax/net/ssl/SSLException
        }))));
    }

    protected DefaultHttpRequestRetryHandler(int i, boolean flag, Collection collection)
    {
        retryCount = i;
        requestSentRetryEnabled = flag;
        nonRetriableClasses = new HashSet();
        Class class1;
        for(Iterator iterator = collection.iterator(); iterator.hasNext(); nonRetriableClasses.add(class1))
            class1 = (Class)iterator.next();

    }

    public int getRetryCount()
    {
        return retryCount;
    }

    protected boolean handleAsIdempotent(HttpRequest httprequest)
    {
        return !(httprequest instanceof HttpEntityEnclosingRequest);
    }

    public boolean isRequestSentRetryEnabled()
    {
        return requestSentRetryEnabled;
    }

    protected boolean requestIsAborted(HttpRequest httprequest)
    {
        HttpRequest httprequest1 = httprequest;
        if(httprequest instanceof RequestWrapper)
            httprequest1 = ((RequestWrapper)httprequest).getOriginal();
        return (httprequest1 instanceof HttpUriRequest) && ((HttpUriRequest)httprequest1).isAborted();
    }

    public boolean retryRequest(IOException ioexception, int i, HttpContext httpcontext)
    {
        Args.notNull(ioexception, "Exception parameter");
        Args.notNull(httpcontext, "HTTP context");
        break MISSING_BLOCK_LABEL_14;
        if(i <= retryCount && !nonRetriableClasses.contains(ioexception.getClass()))
        {
            for(Iterator iterator = nonRetriableClasses.iterator(); iterator.hasNext();)
                if(((Class)iterator.next()).isInstance(ioexception))
                    return false;

            HttpClientContext httpclientcontext = HttpClientContext.adapt(httpcontext);
            HttpRequest httprequest = httpclientcontext.getRequest();
            if(!requestIsAborted(httprequest))
            {
                if(handleAsIdempotent(httprequest))
                    return true;
                if(!httpclientcontext.isRequestSent() || requestSentRetryEnabled)
                    return true;
            }
        }
        return false;
    }

    public static final DefaultHttpRequestRetryHandler INSTANCE = new DefaultHttpRequestRetryHandler();
    private final Set nonRetriableClasses;
    private final boolean requestSentRetryEnabled;
    private final int retryCount;

}
