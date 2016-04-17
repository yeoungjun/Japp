// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.protocol:
//            HttpClientContext

public class RequestClientConnControl
    implements HttpRequestInterceptor
{

    public RequestClientConnControl()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        if(httprequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT"))
        {
            httprequest.setHeader("Proxy-Connection", "Keep-Alive");
        } else
        {
            RouteInfo routeinfo = HttpClientContext.adapt(httpcontext).getHttpRoute();
            if(routeinfo == null)
            {
                log.debug("Connection route not set in the context");
                return;
            }
            if((routeinfo.getHopCount() == 1 || routeinfo.isTunnelled()) && !httprequest.containsHeader("Connection"))
                httprequest.addHeader("Connection", "Keep-Alive");
            if(routeinfo.getHopCount() == 2 && !routeinfo.isTunnelled() && !httprequest.containsHeader("Proxy-Connection"))
            {
                httprequest.addHeader("Proxy-Connection", "Keep-Alive");
                return;
            }
        }
    }

    private static final String PROXY_CONN_DIRECTIVE = "Proxy-Connection";
    private final Log log = LogFactory.getLog(getClass());
}
