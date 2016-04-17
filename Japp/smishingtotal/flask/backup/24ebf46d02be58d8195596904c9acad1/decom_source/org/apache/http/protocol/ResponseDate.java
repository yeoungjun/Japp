// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.protocol:
//            HttpDateGenerator, HttpContext

public class ResponseDate
    implements HttpResponseInterceptor
{

    public ResponseDate()
    {
    }

    public void process(HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        if(httpresponse.getStatusLine().getStatusCode() >= 200 && !httpresponse.containsHeader("Date"))
            httpresponse.setHeader("Date", DATE_GENERATOR.getCurrentDate());
    }

    private static final HttpDateGenerator DATE_GENERATOR = new HttpDateGenerator();

}
