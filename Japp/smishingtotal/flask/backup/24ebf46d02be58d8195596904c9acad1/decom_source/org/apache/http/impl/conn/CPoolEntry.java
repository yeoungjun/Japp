// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.http.HttpClientConnection;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.PoolEntry;

class CPoolEntry extends PoolEntry
{

    public CPoolEntry(Log log1, String s, HttpRoute httproute, ManagedHttpClientConnection managedhttpclientconnection, long l, TimeUnit timeunit)
    {
        super(s, httproute, managedhttpclientconnection, l, timeunit);
        log = log1;
    }

    public void close()
    {
        try
        {
            closeConnection();
            return;
        }
        catch(IOException ioexception)
        {
            log.debug("I/O error closing connection", ioexception);
        }
    }

    public void closeConnection()
        throws IOException
    {
        ((HttpClientConnection)getConnection()).close();
    }

    public boolean isClosed()
    {
        return !((HttpClientConnection)getConnection()).isOpen();
    }

    public boolean isExpired(long l)
    {
        boolean flag = super.isExpired(l);
        if(flag && log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connection ").append(this).append(" expired @ ").append(new Date(getExpiry())).toString());
        return flag;
    }

    public boolean isRouteComplete()
    {
        return routeComplete;
    }

    public void markRouteComplete()
    {
        routeComplete = true;
    }

    public void shutdownConnection()
        throws IOException
    {
        ((HttpClientConnection)getConnection()).shutdown();
    }

    private final Log log;
    private volatile boolean routeComplete;
}
