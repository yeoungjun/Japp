// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpContext

public class ResponseServer
    implements HttpResponseInterceptor
{

    public ResponseServer()
    {
        this(null);
    }

    public ResponseServer(String s)
    {
        originServer = s;
    }

    public void process(HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        if(!httpresponse.containsHeader("Server") && originServer != null)
            httpresponse.addHeader("Server", originServer);
    }

    private final String originServer;
}
