// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpCoreContext, HttpContext

public class RequestTargetHost
    implements HttpRequestInterceptor
{

    public RequestTargetHost()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpCoreContext httpcorecontext;
        ProtocolVersion protocolversion;
        Args.notNull(httprequest, "HTTP request");
        httpcorecontext = HttpCoreContext.adapt(httpcontext);
        protocolversion = httprequest.getRequestLine().getProtocolVersion();
        break MISSING_BLOCK_LABEL_26;
        HttpHost httphost;
        while(true) 
        {
            do
                return;
            while(httprequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") && protocolversion.lessEquals(HttpVersion.HTTP_1_0) || httprequest.containsHeader("Host"));
            httphost = httpcorecontext.getTargetHost();
            if(httphost != null)
                break;
            org.apache.http.HttpConnection httpconnection = httpcorecontext.getConnection();
            if(httpconnection instanceof HttpInetConnection)
            {
                InetAddress inetaddress = ((HttpInetConnection)httpconnection).getRemoteAddress();
                int i = ((HttpInetConnection)httpconnection).getRemotePort();
                if(inetaddress != null)
                    httphost = new HttpHost(inetaddress.getHostName(), i);
            }
            if(httphost != null)
                break;
            if(!protocolversion.lessEquals(HttpVersion.HTTP_1_0))
                throw new ProtocolException("Target host missing");
        }
        httprequest.addHeader("Host", httphost.toHostString());
        return;
    }
}
