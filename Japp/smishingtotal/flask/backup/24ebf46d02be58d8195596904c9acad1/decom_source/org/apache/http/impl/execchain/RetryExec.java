// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain, Proxies

public class RetryExec
    implements ClientExecChain
{

    public RetryExec(ClientExecChain clientexecchain, HttpRequestRetryHandler httprequestretryhandler)
    {
        Args.notNull(clientexecchain, "HTTP request executor");
        Args.notNull(httprequestretryhandler, "HTTP request retry handler");
        requestExecutor = clientexecchain;
        retryHandler = httprequestretryhandler;
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        org.apache.http.Header aheader[];
        int i;
        Args.notNull(httproute, "HTTP route");
        Args.notNull(httprequestwrapper, "HTTP request");
        Args.notNull(httpclientcontext, "HTTP context");
        aheader = httprequestwrapper.getAllHeaders();
        i = 1;
_L2:
        CloseableHttpResponse closeablehttpresponse = requestExecutor.execute(httproute, httprequestwrapper, httpclientcontext, httpexecutionaware);
        return closeablehttpresponse;
        IOException ioexception;
        ioexception;
        if(httpexecutionaware != null && httpexecutionaware.isAborted())
        {
            log.debug("Request has been aborted");
            throw ioexception;
        }
        if(retryHandler.retryRequest(ioexception, i, httpclientcontext))
        {
            if(log.isInfoEnabled())
                log.info((new StringBuilder()).append("I/O exception (").append(ioexception.getClass().getName()).append(") caught when processing request: ").append(ioexception.getMessage()).toString());
            if(log.isDebugEnabled())
                log.debug(ioexception.getMessage(), ioexception);
            if(!Proxies.isRepeatable(httprequestwrapper))
            {
                log.debug("Cannot retry non-repeatable request");
                throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity", ioexception);
            }
            httprequestwrapper.setHeaders(aheader);
            log.info("Retrying request");
            i++;
        } else
        {
            throw ioexception;
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    private final Log log = LogFactory.getLog(getClass());
    private final ClientExecChain requestExecutor;
    private final HttpRequestRetryHandler retryHandler;
}
