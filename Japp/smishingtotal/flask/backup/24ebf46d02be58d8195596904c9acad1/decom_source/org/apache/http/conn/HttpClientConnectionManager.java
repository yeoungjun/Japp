// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.conn:
//            ConnectionRequest

public interface HttpClientConnectionManager
{

    public abstract void closeExpiredConnections();

    public abstract void closeIdleConnections(long l, TimeUnit timeunit);

    public abstract void connect(HttpClientConnection httpclientconnection, HttpRoute httproute, int i, HttpContext httpcontext)
        throws IOException;

    public abstract void releaseConnection(HttpClientConnection httpclientconnection, Object obj, long l, TimeUnit timeunit);

    public abstract ConnectionRequest requestConnection(HttpRoute httproute, Object obj);

    public abstract void routeComplete(HttpClientConnection httpclientconnection, HttpRoute httproute, HttpContext httpcontext)
        throws IOException;

    public abstract void shutdown();

    public abstract void upgrade(HttpClientConnection httpclientconnection, HttpRoute httproute, HttpContext httpcontext)
        throws IOException;
}
