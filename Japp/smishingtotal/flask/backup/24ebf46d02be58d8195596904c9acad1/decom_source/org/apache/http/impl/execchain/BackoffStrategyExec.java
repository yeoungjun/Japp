// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import org.apache.http.HttpException;
import org.apache.http.client.BackoffManager;
import org.apache.http.client.ConnectionBackoffStrategy;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain

public class BackoffStrategyExec
    implements ClientExecChain
{

    public BackoffStrategyExec(ClientExecChain clientexecchain, ConnectionBackoffStrategy connectionbackoffstrategy, BackoffManager backoffmanager)
    {
        Args.notNull(clientexecchain, "HTTP client request executor");
        Args.notNull(connectionbackoffstrategy, "Connection backoff strategy");
        Args.notNull(backoffmanager, "Backoff manager");
        requestExecutor = clientexecchain;
        connectionBackoffStrategy = connectionbackoffstrategy;
        backoffManager = backoffmanager;
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        Args.notNull(httproute, "HTTP route");
        Args.notNull(httprequestwrapper, "HTTP request");
        Args.notNull(httpclientcontext, "HTTP context");
        CloseableHttpResponse closeablehttpresponse;
        try
        {
            closeablehttpresponse = requestExecutor.execute(httproute, httprequestwrapper, httpclientcontext, httpexecutionaware);
        }
        catch(Exception exception)
        {
            if(false)
                null.close();
            if(connectionBackoffStrategy.shouldBackoff(exception))
                backoffManager.backOff(httproute);
            if(exception instanceof RuntimeException)
                throw (RuntimeException)exception;
            if(exception instanceof HttpException)
                throw (HttpException)exception;
            if(exception instanceof IOException)
                throw (IOException)exception;
            else
                throw new UndeclaredThrowableException(exception);
        }
        if(connectionBackoffStrategy.shouldBackoff(closeablehttpresponse))
        {
            backoffManager.backOff(httproute);
            return closeablehttpresponse;
        } else
        {
            backoffManager.probe(httproute);
            return closeablehttpresponse;
        }
    }

    private final BackoffManager backoffManager;
    private final ConnectionBackoffStrategy connectionBackoffStrategy;
    private final ClientExecChain requestExecutor;
}
