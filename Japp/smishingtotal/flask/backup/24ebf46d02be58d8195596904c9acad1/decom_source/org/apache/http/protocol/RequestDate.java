// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpDateGenerator, HttpContext

public class RequestDate
    implements HttpRequestInterceptor
{

    public RequestDate()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        if((httprequest instanceof HttpEntityEnclosingRequest) && !httprequest.containsHeader("Date"))
            httprequest.setHeader("Date", DATE_GENERATOR.getCurrentDate());
    }

    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

}
