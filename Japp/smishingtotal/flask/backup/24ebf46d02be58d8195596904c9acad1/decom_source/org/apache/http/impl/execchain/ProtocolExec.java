// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.execchain:
//            ClientExecChain

public class ProtocolExec
    implements ClientExecChain
{

    public ProtocolExec(ClientExecChain clientexecchain, HttpProcessor httpprocessor)
    {
        Args.notNull(clientexecchain, "HTTP client request executor");
        Args.notNull(httpprocessor, "HTTP protocol processor");
        requestExecutor = clientexecchain;
        httpProcessor = httpprocessor;
    }

    public CloseableHttpResponse execute(HttpRoute httproute, HttpRequestWrapper httprequestwrapper, HttpClientContext httpclientcontext, HttpExecutionAware httpexecutionaware)
        throws IOException, HttpException
    {
        HttpRequest httprequest;
        Args.notNull(httproute, "HTTP route");
        Args.notNull(httprequestwrapper, "HTTP request");
        Args.notNull(httpclientcontext, "HTTP context");
        httprequest = httprequestwrapper.getOriginal();
        if(!(httprequest instanceof HttpUriRequest)) goto _L2; else goto _L1
_L1:
        URI uri = ((HttpUriRequest)httprequest).getURI();
_L8:
        HttpHost httphost;
        httprequestwrapper.setURI(uri);
        rewriteRequestURI(httprequestwrapper, httproute);
        httphost = (HttpHost)httprequestwrapper.getParams().getParameter("http.virtual-host");
        if(httphost != null && httphost.getPort() == -1)
        {
            int i = httproute.getTargetHost().getPort();
            if(i != -1)
            {
                HttpHost httphost2 = new HttpHost(httphost.getHostName(), i, httphost.getSchemeName());
                httphost = httphost2;
            }
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Using virtual host").append(httphost).toString());
        }
        if(httphost == null) goto _L4; else goto _L3
_L3:
        HttpHost httphost1 = httphost;
_L6:
        CloseableHttpResponse closeablehttpresponse;
        if(httphost1 == null)
            httphost1 = httproute.getTargetHost();
        if(uri != null)
        {
            String s2 = uri.getUserInfo();
            if(s2 != null)
            {
                Object obj = httpclientcontext.getCredentialsProvider();
                if(obj == null)
                {
                    obj = new BasicCredentialsProvider();
                    httpclientcontext.setCredentialsProvider(((CredentialsProvider) (obj)));
                }
                AuthScope authscope = new AuthScope(httphost1);
                UsernamePasswordCredentials usernamepasswordcredentials = new UsernamePasswordCredentials(s2);
                ((CredentialsProvider) (obj)).setCredentials(authscope, usernamepasswordcredentials);
            }
        }
        httpclientcontext.setAttribute("http.target_host", httphost1);
        httpclientcontext.setAttribute("http.route", httproute);
        httpclientcontext.setAttribute("http.request", httprequestwrapper);
        httpProcessor.process(httprequestwrapper, httpclientcontext);
        closeablehttpresponse = requestExecutor.execute(httproute, httprequestwrapper, httpclientcontext, httpexecutionaware);
        httpclientcontext.setAttribute("http.response", closeablehttpresponse);
        httpProcessor.process(closeablehttpresponse, httpclientcontext);
        return closeablehttpresponse;
_L2:
        String s = httprequest.getRequestLine().getUri();
        URI uri1;
        try
        {
            uri1 = URI.create(s);
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            boolean flag = log.isDebugEnabled();
            uri = null;
            if(flag)
            {
                log.debug((new StringBuilder()).append("Unable to parse '").append(s).append("' as a valid URI; ").append("request URI and Host header may be inconsistent").toString(), illegalargumentexception);
                uri = null;
            }
            continue; /* Loop/switch isn't completed */
        }
        uri = uri1;
        continue; /* Loop/switch isn't completed */
_L4:
        httphost1 = null;
        if(uri != null)
        {
            boolean flag1 = uri.isAbsolute();
            httphost1 = null;
            if(flag1)
            {
                String s1 = uri.getHost();
                httphost1 = null;
                if(s1 != null)
                    httphost1 = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
            }
        }
        if(true) goto _L6; else goto _L5
_L5:
        RuntimeException runtimeexception;
        runtimeexception;
        closeablehttpresponse.close();
        throw runtimeexception;
        IOException ioexception;
        ioexception;
        closeablehttpresponse.close();
        throw ioexception;
        HttpException httpexception;
        httpexception;
        closeablehttpresponse.close();
        throw httpexception;
        if(true) goto _L8; else goto _L7
_L7:
    }

    void rewriteRequestURI(HttpRequestWrapper httprequestwrapper, HttpRoute httproute)
        throws ProtocolException
    {
        URI uri;
        URI uri1;
        URI uri2;
        try
        {
            uri = httprequestwrapper.getURI();
        }
        catch(URISyntaxException urisyntaxexception)
        {
            throw new ProtocolException((new StringBuilder()).append("Invalid URI: ").append(httprequestwrapper.getRequestLine().getUri()).toString(), urisyntaxexception);
        }
        if(uri == null)
            break MISSING_BLOCK_LABEL_134;
        if(httproute.getProxyHost() == null || httproute.isTunnelled()) goto _L2; else goto _L1
_L1:
        if(uri.isAbsolute()) goto _L4; else goto _L3
_L3:
        uri2 = URIUtils.rewriteURI(uri, httproute.getTargetHost(), true);
_L5:
        httprequestwrapper.setURI(uri2);
        return;
_L4:
        uri2 = URIUtils.rewriteURI(uri);
          goto _L5
_L2:
label0:
        {
            if(!uri.isAbsolute())
                break label0;
            uri2 = URIUtils.rewriteURI(uri, null, true);
        }
          goto _L5
        uri1 = URIUtils.rewriteURI(uri);
        uri2 = uri1;
          goto _L5
    }

    private final HttpProcessor httpProcessor;
    private final Log log = LogFactory.getLog(getClass());
    private final ClientExecChain requestExecutor;
}
