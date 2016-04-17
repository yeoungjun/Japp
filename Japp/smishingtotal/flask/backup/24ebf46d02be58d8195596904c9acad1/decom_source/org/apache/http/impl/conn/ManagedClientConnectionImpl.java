// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.*;
import org.apache.http.conn.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn:
//            ConnectionShutdownException, HttpPoolEntry

class ManagedClientConnectionImpl
    implements ManagedClientConnection
{

    ManagedClientConnectionImpl(ClientConnectionManager clientconnectionmanager, ClientConnectionOperator clientconnectionoperator, HttpPoolEntry httppoolentry)
    {
        Args.notNull(clientconnectionmanager, "Connection manager");
        Args.notNull(clientconnectionoperator, "Connection operator");
        Args.notNull(httppoolentry, "HTTP pool entry");
        manager = clientconnectionmanager;
        operator = clientconnectionoperator;
        poolEntry = httppoolentry;
        reusable = false;
        duration = 0x7fffffffffffffffL;
    }

    private OperatedClientConnection ensureConnection()
    {
        HttpPoolEntry httppoolentry = poolEntry;
        if(httppoolentry == null)
            throw new ConnectionShutdownException();
        else
            return (OperatedClientConnection)httppoolentry.getConnection();
    }

    private HttpPoolEntry ensurePoolEntry()
    {
        HttpPoolEntry httppoolentry = poolEntry;
        if(httppoolentry == null)
            throw new ConnectionShutdownException();
        else
            return httppoolentry;
    }

    private OperatedClientConnection getConnection()
    {
        HttpPoolEntry httppoolentry = poolEntry;
        if(httppoolentry == null)
            return null;
        else
            return (OperatedClientConnection)httppoolentry.getConnection();
    }

    public void abortConnection()
    {
        this;
        JVM INSTR monitorenter ;
        if(poolEntry != null)
            break MISSING_BLOCK_LABEL_12;
        this;
        JVM INSTR monitorexit ;
        return;
        OperatedClientConnection operatedclientconnection;
        reusable = false;
        operatedclientconnection = (OperatedClientConnection)poolEntry.getConnection();
        Exception exception;
        try
        {
            operatedclientconnection.shutdown();
        }
        catch(IOException ioexception) { }
        manager.releaseConnection(this, duration, TimeUnit.MILLISECONDS);
        poolEntry = null;
        this;
        JVM INSTR monitorexit ;
        return;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void bind(Socket socket)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public void close()
        throws IOException
    {
        HttpPoolEntry httppoolentry = poolEntry;
        if(httppoolentry != null)
        {
            OperatedClientConnection operatedclientconnection = (OperatedClientConnection)httppoolentry.getConnection();
            httppoolentry.getTracker().reset();
            operatedclientconnection.close();
        }
    }

    HttpPoolEntry detach()
    {
        HttpPoolEntry httppoolentry = poolEntry;
        poolEntry = null;
        return httppoolentry;
    }

    public void flush()
        throws IOException
    {
        ensureConnection().flush();
    }

    public Object getAttribute(String s)
    {
        OperatedClientConnection operatedclientconnection = ensureConnection();
        if(operatedclientconnection instanceof HttpContext)
            return ((HttpContext)operatedclientconnection).getAttribute(s);
        else
            return null;
    }

    public String getId()
    {
        return null;
    }

    public InetAddress getLocalAddress()
    {
        return ensureConnection().getLocalAddress();
    }

    public int getLocalPort()
    {
        return ensureConnection().getLocalPort();
    }

    public ClientConnectionManager getManager()
    {
        return manager;
    }

    public HttpConnectionMetrics getMetrics()
    {
        return ensureConnection().getMetrics();
    }

    HttpPoolEntry getPoolEntry()
    {
        return poolEntry;
    }

    public InetAddress getRemoteAddress()
    {
        return ensureConnection().getRemoteAddress();
    }

    public int getRemotePort()
    {
        return ensureConnection().getRemotePort();
    }

    public HttpRoute getRoute()
    {
        return ensurePoolEntry().getEffectiveRoute();
    }

    public SSLSession getSSLSession()
    {
        Socket socket = ensureConnection().getSocket();
        boolean flag = socket instanceof SSLSocket;
        SSLSession sslsession = null;
        if(flag)
            sslsession = ((SSLSocket)socket).getSession();
        return sslsession;
    }

    public Socket getSocket()
    {
        return ensureConnection().getSocket();
    }

    public int getSocketTimeout()
    {
        return ensureConnection().getSocketTimeout();
    }

    public Object getState()
    {
        return ensurePoolEntry().getState();
    }

    public boolean isMarkedReusable()
    {
        return reusable;
    }

    public boolean isOpen()
    {
        OperatedClientConnection operatedclientconnection = getConnection();
        if(operatedclientconnection != null)
            return operatedclientconnection.isOpen();
        else
            return false;
    }

    public boolean isResponseAvailable(int i)
        throws IOException
    {
        return ensureConnection().isResponseAvailable(i);
    }

    public boolean isSecure()
    {
        return ensureConnection().isSecure();
    }

    public boolean isStale()
    {
        OperatedClientConnection operatedclientconnection = getConnection();
        if(operatedclientconnection != null)
            return operatedclientconnection.isStale();
        else
            return true;
    }

    public void layerProtocol(HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httpparams, "HTTP parameters");
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new ConnectionShutdownException();
        break MISSING_BLOCK_LABEL_31;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        RouteTracker routetracker;
        routetracker = poolEntry.getTracker();
        Asserts.notNull(routetracker, "Route tracker");
        Asserts.check(routetracker.isConnected(), "Connection not open");
        Asserts.check(routetracker.isTunnelled(), "Protocol layering without a tunnel not supported");
        boolean flag;
        HttpHost httphost;
        OperatedClientConnection operatedclientconnection;
        Exception exception1;
        if(!routetracker.isLayered())
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Multiple protocol layering not supported");
        httphost = routetracker.getTargetHost();
        operatedclientconnection = (OperatedClientConnection)poolEntry.getConnection();
        this;
        JVM INSTR monitorexit ;
        operator.updateSecureConnection(operatedclientconnection, httphost, httpcontext, httpparams);
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new InterruptedIOException();
        break MISSING_BLOCK_LABEL_151;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        poolEntry.getTracker().layerProtocol(operatedclientconnection.isSecure());
        this;
        JVM INSTR monitorexit ;
    }

    public void markReusable()
    {
        reusable = true;
    }

    public void open(HttpRoute httproute, HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httproute, "Route");
        Args.notNull(httpparams, "HTTP parameters");
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new ConnectionShutdownException();
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        RouteTracker routetracker;
        routetracker = poolEntry.getTracker();
        Asserts.notNull(routetracker, "Route tracker");
        boolean flag;
        OperatedClientConnection operatedclientconnection;
        HttpHost httphost;
        if(!routetracker.isConnected())
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection already open");
        operatedclientconnection = (OperatedClientConnection)poolEntry.getConnection();
        this;
        JVM INSTR monitorexit ;
        httphost = httproute.getProxyHost();
        ClientConnectionOperator clientconnectionoperator = operator;
        HttpHost httphost1;
        Exception exception1;
        if(httphost != null)
            httphost1 = httphost;
        else
            httphost1 = httproute.getTargetHost();
        clientconnectionoperator.openConnection(operatedclientconnection, httphost1, httproute.getLocalAddress(), httpcontext, httpparams);
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new InterruptedIOException();
        break MISSING_BLOCK_LABEL_163;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        RouteTracker routetracker1 = poolEntry.getTracker();
        if(httphost != null)
            break MISSING_BLOCK_LABEL_192;
        routetracker1.connectTarget(operatedclientconnection.isSecure());
_L2:
        this;
        JVM INSTR monitorexit ;
        return;
        routetracker1.connectProxy(httphost, operatedclientconnection.isSecure());
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void receiveResponseEntity(HttpResponse httpresponse)
        throws HttpException, IOException
    {
        ensureConnection().receiveResponseEntity(httpresponse);
    }

    public HttpResponse receiveResponseHeader()
        throws HttpException, IOException
    {
        return ensureConnection().receiveResponseHeader();
    }

    public void releaseConnection()
    {
        this;
        JVM INSTR monitorenter ;
        if(poolEntry != null)
            break MISSING_BLOCK_LABEL_12;
        this;
        JVM INSTR monitorexit ;
        return;
        manager.releaseConnection(this, duration, TimeUnit.MILLISECONDS);
        poolEntry = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public Object removeAttribute(String s)
    {
        OperatedClientConnection operatedclientconnection = ensureConnection();
        if(operatedclientconnection instanceof HttpContext)
            return ((HttpContext)operatedclientconnection).removeAttribute(s);
        else
            return null;
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest httpentityenclosingrequest)
        throws HttpException, IOException
    {
        ensureConnection().sendRequestEntity(httpentityenclosingrequest);
    }

    public void sendRequestHeader(HttpRequest httprequest)
        throws HttpException, IOException
    {
        ensureConnection().sendRequestHeader(httprequest);
    }

    public void setAttribute(String s, Object obj)
    {
        OperatedClientConnection operatedclientconnection = ensureConnection();
        if(operatedclientconnection instanceof HttpContext)
            ((HttpContext)operatedclientconnection).setAttribute(s, obj);
    }

    public void setIdleDuration(long l, TimeUnit timeunit)
    {
        if(l > 0L)
        {
            duration = timeunit.toMillis(l);
            return;
        } else
        {
            duration = -1L;
            return;
        }
    }

    public void setSocketTimeout(int i)
    {
        ensureConnection().setSocketTimeout(i);
    }

    public void setState(Object obj)
    {
        ensurePoolEntry().setState(obj);
    }

    public void shutdown()
        throws IOException
    {
        HttpPoolEntry httppoolentry = poolEntry;
        if(httppoolentry != null)
        {
            OperatedClientConnection operatedclientconnection = (OperatedClientConnection)httppoolentry.getConnection();
            httppoolentry.getTracker().reset();
            operatedclientconnection.shutdown();
        }
    }

    public void tunnelProxy(HttpHost httphost, boolean flag, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httphost, "Next proxy");
        Args.notNull(httpparams, "HTTP parameters");
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new ConnectionShutdownException();
        break MISSING_BLOCK_LABEL_39;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        OperatedClientConnection operatedclientconnection;
        RouteTracker routetracker = poolEntry.getTracker();
        Asserts.notNull(routetracker, "Route tracker");
        Asserts.check(routetracker.isConnected(), "Connection not open");
        operatedclientconnection = (OperatedClientConnection)poolEntry.getConnection();
        this;
        JVM INSTR monitorexit ;
        operatedclientconnection.update(null, httphost, flag, httpparams);
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new InterruptedIOException();
        break MISSING_BLOCK_LABEL_114;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        poolEntry.getTracker().tunnelProxy(httphost, flag);
        this;
        JVM INSTR monitorexit ;
    }

    public void tunnelTarget(boolean flag, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httpparams, "HTTP parameters");
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new ConnectionShutdownException();
        break MISSING_BLOCK_LABEL_31;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        RouteTracker routetracker;
        routetracker = poolEntry.getTracker();
        Asserts.notNull(routetracker, "Route tracker");
        Asserts.check(routetracker.isConnected(), "Connection not open");
        boolean flag1;
        HttpHost httphost;
        OperatedClientConnection operatedclientconnection;
        Exception exception1;
        if(!routetracker.isTunnelled())
            flag1 = true;
        else
            flag1 = false;
        Asserts.check(flag1, "Connection is already tunnelled");
        httphost = routetracker.getTargetHost();
        operatedclientconnection = (OperatedClientConnection)poolEntry.getConnection();
        this;
        JVM INSTR monitorexit ;
        operatedclientconnection.update(null, httphost, flag, httpparams);
        this;
        JVM INSTR monitorenter ;
        if(poolEntry == null)
            throw new InterruptedIOException();
        break MISSING_BLOCK_LABEL_139;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
        poolEntry.getTracker().tunnelTarget(flag);
        this;
        JVM INSTR monitorexit ;
    }

    public void unmarkReusable()
    {
        reusable = false;
    }

    private volatile long duration;
    private final ClientConnectionManager manager;
    private final ClientConnectionOperator operator;
    private volatile HttpPoolEntry poolEntry;
    private volatile boolean reusable;
}
