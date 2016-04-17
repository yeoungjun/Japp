// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.pool.PoolEntry;

class HttpPoolEntry extends PoolEntry
{

    public HttpPoolEntry(Log log1, String s, HttpRoute httproute, OperatedClientConnection operatedclientconnection, long l, TimeUnit timeunit)
    {
        super(s, httproute, operatedclientconnection, l, timeunit);
        log = log1;
        tracker = new RouteTracker(httproute);
    }

    public void close()
    {
        OperatedClientConnection operatedclientconnection = (OperatedClientConnection)getConnection();
        try
        {
            operatedclientconnection.close();
            return;
        }
        catch(IOException ioexception)
        {
            log.debug("I/O error closing connection", ioexception);
        }
    }

    HttpRoute getEffectiveRoute()
    {
        return tracker.toRoute();
    }

    HttpRoute getPlannedRoute()
    {
        return (HttpRoute)getRoute();
    }

    RouteTracker getTracker()
    {
        return tracker;
    }

    public boolean isClosed()
    {
        return !((OperatedClientConnection)getConnection()).isOpen();
    }

    public boolean isExpired(long l)
    {
        boolean flag = super.isExpired(l);
        if(flag && log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection ").append(this).append(" expired @ ").append(new Date(getExpiry())).toString());
        return flag;
    }

    private final Log log;
    private final RouteTracker tracker;
}
