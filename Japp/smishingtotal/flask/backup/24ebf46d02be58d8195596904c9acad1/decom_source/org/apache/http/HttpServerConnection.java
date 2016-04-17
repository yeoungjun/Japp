// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.io.IOException;

// Referenced classes of package org.apache.http:
//            HttpConnection, HttpException, HttpEntityEnclosingRequest, HttpRequest, 
//            HttpResponse

public interface HttpServerConnection
    extends HttpConnection
{

    public abstract void flush()
        throws IOException;

    public abstract void receiveRequestEntity(HttpEntityEnclosingRequest httpentityenclosingrequest)
        throws HttpException, IOException;

    public abstract HttpRequest receiveRequestHeader()
        throws HttpException, IOException;

    public abstract void sendResponseEntity(HttpResponse httpresponse)
        throws HttpException, IOException;

    public abstract void sendResponseHeader(HttpResponse httpresponse)
        throws HttpException, IOException;
}
