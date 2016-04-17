// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.http.*;
import org.apache.http.auth.AuthState;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.protocol:
//            RequestAuthenticationBase

public class RequestTargetAuthentication extends RequestAuthenticationBase
{

    public RequestTargetAuthentication()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpcontext, "HTTP context");
        while(httprequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT") || httprequest.containsHeader("Authorization")) 
            return;
        AuthState authstate = (AuthState)httpcontext.getAttribute("http.auth.target-scope");
        if(authstate == null)
        {
            log.debug("Target auth state not set in the context");
            return;
        }
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Target auth state: ").append(authstate.getState()).toString());
        process(authstate, httprequest, httpcontext);
    }
}
