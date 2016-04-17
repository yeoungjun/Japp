// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.http.HttpClientConnection;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.conn.HttpClientConnectionManager;

class ConnectionHolder
    implements ConnectionReleaseTrigger, Cancellable, Closeable
{

    public ConnectionHolder(Log log1, HttpClientConnectionManager httpclientconnectionmanager, HttpClientConnection httpclientconnection)
    {
        log = log1;
        manager = httpclientconnectionmanager;
        managedConn = httpclientconnection;
    }

    public void abortConnection()
    {
label0:
        {
            synchronized(managedConn)
            {
                if(!released)
                    break label0;
            }
            return;
        }
        released = true;
        managedConn.shutdown();
        log.debug("Connection discarded");
        manager.releaseConnection(managedConn, null, 0L, TimeUnit.MILLISECONDS);
_L1:
        httpclientconnection;
        JVM INSTR monitorexit ;
        return;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug(ioexception.getMessage(), ioexception);
        manager.releaseConnection(managedConn, null, 0L, TimeUnit.MILLISECONDS);
          goto _L1
        Exception exception1;
        exception1;
        manager.releaseConnection(managedConn, null, 0L, TimeUnit.MILLISECONDS);
        throw exception1;
    }

    public boolean cancel()
    {
        boolean flag = released;
        log.debug("Cancelling request execution");
        abortConnection();
        return !flag;
    }

    public void close()
        throws IOException
    {
        abortConnection();
    }

    public boolean isReleased()
    {
        return released;
    }

    public boolean isReusable()
    {
        return reusable;
    }

    public void markNonReusable()
    {
        reusable = false;
    }

    public void markReusable()
    {
        reusable = true;
    }

    public void releaseConnection()
    {
label0:
        {
            synchronized(managedConn)
            {
                if(!released)
                    break label0;
            }
            return;
        }
        released = true;
        if(!reusable) goto _L2; else goto _L1
_L1:
        manager.releaseConnection(managedConn, state, validDuration, tunit);
_L3:
        httpclientconnection;
        JVM INSTR monitorexit ;
        return;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        managedConn.close();
        log.debug("Connection discarded");
        manager.releaseConnection(managedConn, null, 0L, TimeUnit.MILLISECONDS);
          goto _L3
        IOException ioexception;
        ioexception;
        if(log.isDebugEnabled())
            log.debug(ioexception.getMessage(), ioexception);
        manager.releaseConnection(managedConn, null, 0L, TimeUnit.MILLISECONDS);
          goto _L3
        Exception exception1;
        exception1;
        manager.releaseConnection(managedConn, null, 0L, TimeUnit.MILLISECONDS);
        throw exception1;
    }

    public void setState(Object obj)
    {
        state = obj;
    }

    public void setValidFor(long l, TimeUnit timeunit)
    {
        synchronized(managedConn)
        {
            validDuration = l;
            tunit = timeunit;
        }
        return;
        exception;
        httpclientconnection;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private final Log log;
    private final HttpClientConnection managedConn;
    private final HttpClientConnectionManager manager;
    private volatile boolean released;
    private volatile boolean reusable;
    private volatile Object state;
    private volatile TimeUnit tunit;
    private volatile long validDuration;
}
