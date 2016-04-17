// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;

// Referenced classes of package org.apache.http.conn:
//            ManagedClientConnection, ClientConnectionRequest

public interface ClientConnectionManager
{

    public abstract void closeExpiredConnections();

    public abstract void closeIdleConnections(long l, TimeUnit timeunit);

    public abstract SchemeRegistry getSchemeRegistry();

    public abstract void releaseConnection(ManagedClientConnection managedclientconnection, long l, TimeUnit timeunit);

    public abstract ClientConnectionRequest requestConnection(HttpRoute httproute, Object obj);

    public abstract void shutdown();
}
