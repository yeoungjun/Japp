// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.*;

// Referenced classes of package org.apache.http.protocol:
//            HttpContext

public interface HttpRequestHandler
{

    public abstract void handle(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws HttpException, IOException;
}
