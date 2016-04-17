// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.conn:
//            AbstractClientConnAdapter, AbstractPoolEntry, ConnectionShutdownException

public abstract class AbstractPooledConnAdapter extends AbstractClientConnAdapter
{

    protected AbstractPooledConnAdapter(ClientConnectionManager clientconnectionmanager, AbstractPoolEntry abstractpoolentry)
    {
        super(clientconnectionmanager, abstractpoolentry.connection);
        poolEntry = abstractpoolentry;
    }

    protected final void assertAttached()
    {
        if(poolEntry == null)
            throw new ConnectionShutdownException();
        else
            return;
    }

    protected void assertValid(AbstractPoolEntry abstractpoolentry)
    {
        if(isReleased() || abstractpoolentry == null)
            throw new ConnectionShutdownException();
        else
            return;
    }

    public void close()
        throws IOException
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        if(abstractpoolentry != null)
            abstractpoolentry.shutdownEntry();
        OperatedClientConnection operatedclientconnection = getWrappedConnection();
        if(operatedclientconnection != null)
            operatedclientconnection.close();
    }

    protected void detach()
    {
        this;
        JVM INSTR monitorenter ;
        poolEntry = null;
        super.detach();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public String getId()
    {
        return null;
    }

    protected AbstractPoolEntry getPoolEntry()
    {
        return poolEntry;
    }

    public HttpRoute getRoute()
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        if(abstractpoolentry.tracker == null)
            return null;
        else
            return abstractpoolentry.tracker.toRoute();
    }

    public Object getState()
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        return abstractpoolentry.getState();
    }

    public void layerProtocol(HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        abstractpoolentry.layerProtocol(httpcontext, httpparams);
    }

    public void open(HttpRoute httproute, HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        abstractpoolentry.open(httproute, httpcontext, httpparams);
    }

    public void setState(Object obj)
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        abstractpoolentry.setState(obj);
    }

    public void shutdown()
        throws IOException
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        if(abstractpoolentry != null)
            abstractpoolentry.shutdownEntry();
        OperatedClientConnection operatedclientconnection = getWrappedConnection();
        if(operatedclientconnection != null)
            operatedclientconnection.shutdown();
    }

    public void tunnelProxy(HttpHost httphost, boolean flag, HttpParams httpparams)
        throws IOException
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        abstractpoolentry.tunnelProxy(httphost, flag, httpparams);
    }

    public void tunnelTarget(boolean flag, HttpParams httpparams)
        throws IOException
    {
        AbstractPoolEntry abstractpoolentry = getPoolEntry();
        assertValid(abstractpoolentry);
        abstractpoolentry.tunnelTarget(flag, httpparams);
    }

    protected volatile AbstractPoolEntry poolEntry;
}
