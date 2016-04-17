// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain

public class ServiceUnavailableRetryExec
    implements ClientExecChain
{

    public ServiceUnavailableRetryExec(ClientExecChain clientexecchain, ServiceUnavailableRetryStrategy serviceunavailableretrystrategy)
    {
        Args.notNull(clientexecchain, "HTTP request executor");
        Args.notNull(serviceunavailableretrystrategy, "Retry strategy");
        requestExecutor = clientexecchain;
        retryStrategy = serviceunavailableretrystrategy;
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        int i = 1;
_L2:
        CloseableHttpResponse closeablehttpresponse;
        closeablehttpresponse = requestExecutor.execute(httproute, httprequestwrapper, httpclientcontext, httpexecutionaware);
        long l;
        InterruptedException interruptedexception;
        try
        {
            if(!retryStrategy.retryRequest(closeablehttpresponse, i, httpclientcontext))
                break; /* Loop/switch isn't completed */
            closeablehttpresponse.close();
            l = retryStrategy.getRetryInterval();
        }
        catch(RuntimeException runtimeexception)
        {
            closeablehttpresponse.close();
            throw runtimeexception;
        }
        if(l <= 0L)
            break MISSING_BLOCK_LABEL_95;
        log.trace((new StringBuilder()).append("Wait for ").append(l).toString());
        Thread.sleep(l);
        i++;
        if(true) goto _L2; else goto _L1
        interruptedexception;
        Thread.currentThread().interrupt();
        throw new InterruptedIOException();
_L1:
        return closeablehttpresponse;
    }

    private final Log log = LogFactory.getLog(getClass());
    private final ClientExecChain requestExecutor;
    private final ServiceUnavailableRetryStrategy retryStrategy;
}
