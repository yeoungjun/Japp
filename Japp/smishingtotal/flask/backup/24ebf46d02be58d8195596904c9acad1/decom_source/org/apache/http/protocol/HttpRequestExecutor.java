// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpContext, HttpProcessor

public class HttpRequestExecutor
{

    public HttpRequestExecutor()
    {
        this(3000);
    }

    public HttpRequestExecutor(int i)
    {
        waitForContinue = Args.positive(i, "Wait for continue time");
    }

    private static void closeConnection(HttpClientConnection httpclientconnection)
    {
        try
        {
            httpclientconnection.close();
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    protected boolean canResponseHaveBody(HttpRequest httprequest, HttpResponse httpresponse)
    {
        int i;
        if(!"HEAD".equalsIgnoreCase(httprequest.getRequestLine().getMethod()))
            if((i = httpresponse.getStatusLine().getStatusCode()) >= 200 && i != 204 && i != 304 && i != 205)
                return true;
        return false;
    }

    protected HttpResponse doReceiveResponse(HttpRequest httprequest, HttpClientConnection httpclientconnection, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpclientconnection, "Client connection");
        Args.notNull(httpcontext, "HTTP context");
        HttpResponse httpresponse = null;
        for(int i = 0; httpresponse == null || i < 200; i = httpresponse.getStatusLine().getStatusCode())
        {
            httpresponse = httpclientconnection.receiveResponseHeader();
            if(canResponseHaveBody(httprequest, httpresponse))
                httpclientconnection.receiveResponseEntity(httpresponse);
        }

        return httpresponse;
    }

    protected HttpResponse doSendRequest(HttpRequest httprequest, HttpClientConnection httpclientconnection, HttpContext httpcontext)
        throws IOException, HttpException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpclientconnection, "Client connection");
        Args.notNull(httpcontext, "HTTP context");
        httpcontext.setAttribute("http.connection", httpclientconnection);
        httpcontext.setAttribute("http.request_sent", Boolean.FALSE);
        httpclientconnection.sendRequestHeader(httprequest);
        boolean flag = httprequest instanceof HttpEntityEnclosingRequest;
        HttpResponse httpresponse = null;
        if(flag)
        {
            boolean flag1 = true;
            ProtocolVersion protocolversion = httprequest.getRequestLine().getProtocolVersion();
            boolean flag2 = ((HttpEntityEnclosingRequest)httprequest).expectContinue();
            httpresponse = null;
            if(flag2)
            {
                boolean flag3 = protocolversion.lessEquals(HttpVersion.HTTP_1_0);
                httpresponse = null;
                if(!flag3)
                {
                    httpclientconnection.flush();
                    boolean flag4 = httpclientconnection.isResponseAvailable(waitForContinue);
                    httpresponse = null;
                    if(flag4)
                    {
                        httpresponse = httpclientconnection.receiveResponseHeader();
                        if(canResponseHaveBody(httprequest, httpresponse))
                            httpclientconnection.receiveResponseEntity(httpresponse);
                        int i = httpresponse.getStatusLine().getStatusCode();
                        if(i < 200)
                        {
                            if(i != 100)
                                throw new ProtocolException((new StringBuilder()).append("Unexpected response: ").append(httpresponse.getStatusLine()).toString());
                            httpresponse = null;
                        } else
                        {
                            flag1 = false;
                        }
                    }
                }
            }
            if(flag1)
                httpclientconnection.sendRequestEntity((HttpEntityEnclosingRequest)httprequest);
        }
        httpclientconnection.flush();
        httpcontext.setAttribute("http.request_sent", Boolean.TRUE);
        return httpresponse;
    }

    public HttpResponse execute(HttpRequest httprequest, HttpClientConnection httpclientconnection, HttpContext httpcontext)
        throws IOException, HttpException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpclientconnection, "Client connection");
        Args.notNull(httpcontext, "HTTP context");
        HttpResponse httpresponse;
        HttpResponse httpresponse1;
        try
        {
            httpresponse = doSendRequest(httprequest, httpclientconnection, httpcontext);
        }
        catch(IOException ioexception)
        {
            closeConnection(httpclientconnection);
            throw ioexception;
        }
        catch(HttpException httpexception)
        {
            closeConnection(httpclientconnection);
            throw httpexception;
        }
        catch(RuntimeException runtimeexception)
        {
            closeConnection(httpclientconnection);
            throw runtimeexception;
        }
        if(httpresponse != null)
            break MISSING_BLOCK_LABEL_48;
        httpresponse1 = doReceiveResponse(httprequest, httpclientconnection, httpcontext);
        httpresponse = httpresponse1;
        return httpresponse;
    }

    public void postProcess(HttpResponse httpresponse, HttpProcessor httpprocessor, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        Args.notNull(httpprocessor, "HTTP processor");
        Args.notNull(httpcontext, "HTTP context");
        httpcontext.setAttribute("http.response", httpresponse);
        httpprocessor.process(httpresponse, httpcontext);
    }

    public void preProcess(HttpRequest httprequest, HttpProcessor httpprocessor, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpprocessor, "HTTP processor");
        Args.notNull(httpcontext, "HTTP context");
        httpcontext.setAttribute("http.request", httprequest);
        httpprocessor.process(httprequest, httpcontext);
    }

    public static final int DEFAULT_WAIT_FOR_CONTINUE = 3000;
    private final int waitForContinue;
}
