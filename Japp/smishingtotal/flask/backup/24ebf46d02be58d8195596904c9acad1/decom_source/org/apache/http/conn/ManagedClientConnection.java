// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.conn:
//            HttpRoutedConnection, ManagedHttpClientConnection, ConnectionReleaseTrigger

public interface ManagedClientConnection
    extends HttpClientConnection, HttpRoutedConnection, ManagedHttpClientConnection, ConnectionReleaseTrigger
{

    public abstract HttpRoute getRoute();

    public abstract SSLSession getSSLSession();

    public abstract Object getState();

    public abstract boolean isMarkedReusable();

    public abstract boolean isSecure();

    public abstract void layerProtocol(HttpContext httpcontext, HttpParams httpparams)
        throws IOException;

    public abstract void markReusable();

    public abstract void open(HttpRoute httproute, HttpContext httpcontext, HttpParams httpparams)
        throws IOException;

    public abstract void setIdleDuration(long l, TimeUnit timeunit);

    public abstract void setState(Object obj);

    public abstract void tunnelProxy(HttpHost httphost, boolean flag, HttpParams httpparams)
        throws IOException;

    public abstract void tunnelTarget(boolean flag, HttpParams httpparams)
        throws IOException;

    public abstract void unmarkReusable();
}
