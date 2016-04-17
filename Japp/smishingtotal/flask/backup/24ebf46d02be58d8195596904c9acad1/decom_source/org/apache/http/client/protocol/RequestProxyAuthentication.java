// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthState;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.protocol:
//            RequestAuthenticationBase

public class RequestProxyAuthentication extends RequestAuthenticationBase
{

    public RequestProxyAuthentication()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpcontext, "HTTP context");
        if(!httprequest.containsHeader("Proxy-Authorization"))
        {
            HttpRoutedConnection httproutedconnection = (HttpRoutedConnection)httpcontext.getAttribute("http.connection");
            if(httproutedconnection == null)
            {
                log.debug("HTTP connection not set in the context");
                return;
            }
            if(!httproutedconnection.getRoute().isTunnelled())
            {
                AuthState authstate = (AuthState)httpcontext.getAttribute("http.auth.proxy-scope");
                if(authstate == null)
                {
                    log.debug("Proxy auth state not set in the context");
                    return;
                }
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Proxy auth state: ").append(authstate.getState()).toString());
                process(authstate, httprequest, httpcontext);
                return;
            }
        }
    }
}
