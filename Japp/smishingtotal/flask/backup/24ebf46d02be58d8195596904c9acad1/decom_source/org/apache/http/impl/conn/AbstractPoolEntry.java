// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

public abstract class AbstractPoolEntry
{

    protected AbstractPoolEntry(ClientConnectionOperator clientconnectionoperator, HttpRoute httproute)
    {
        Args.notNull(clientconnectionoperator, "Connection operator");
        connOperator = clientconnectionoperator;
        connection = clientconnectionoperator.createConnection();
        route = httproute;
        tracker = null;
    }

    public Object getState()
    {
        return state;
    }

    public void layerProtocol(HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httpparams, "HTTP parameters");
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(tracker.isConnected(), "Connection not open");
        Asserts.check(tracker.isTunnelled(), "Protocol layering without a tunnel not supported");
        boolean flag;
        HttpHost httphost;
        if(!tracker.isLayered())
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Multiple protocol layering not supported");
        httphost = tracker.getTargetHost();
        connOperator.updateSecureConnection(connection, httphost, httpcontext, httpparams);
        tracker.layerProtocol(connection.isSecure());
    }

    public void open(HttpRoute httproute, HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httproute, "Route");
        Args.notNull(httpparams, "HTTP parameters");
        HttpHost httphost;
        HttpHost httphost1;
        RouteTracker routetracker;
        if(tracker != null)
        {
            ClientConnectionOperator clientconnectionoperator;
            OperatedClientConnection operatedclientconnection;
            boolean flag;
            if(!tracker.isConnected())
                flag = true;
            else
                flag = false;
            Asserts.check(flag, "Connection already open");
        }
        tracker = new RouteTracker(httproute);
        httphost = httproute.getProxyHost();
        clientconnectionoperator = connOperator;
        operatedclientconnection = connection;
        if(httphost != null)
            httphost1 = httphost;
        else
            httphost1 = httproute.getTargetHost();
        clientconnectionoperator.openConnection(operatedclientconnection, httphost1, httproute.getLocalAddress(), httpcontext, httpparams);
        routetracker = tracker;
        if(routetracker == null)
            throw new InterruptedIOException("Request aborted");
        if(httphost == null)
        {
            routetracker.connectTarget(connection.isSecure());
            return;
        } else
        {
            routetracker.connectProxy(httphost, connection.isSecure());
            return;
        }
    }

    public void setState(Object obj)
    {
        state = obj;
    }

    protected void shutdownEntry()
    {
        tracker = null;
        state = null;
    }

    public void tunnelProxy(HttpHost httphost, boolean flag, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httphost, "Next proxy");
        Args.notNull(httpparams, "Parameters");
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(tracker.isConnected(), "Connection not open");
        connection.update(null, httphost, flag, httpparams);
        tracker.tunnelProxy(httphost, flag);
    }

    public void tunnelTarget(boolean flag, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httpparams, "HTTP parameters");
        Asserts.notNull(tracker, "Route tracker");
        Asserts.check(tracker.isConnected(), "Connection not open");
        boolean flag1;
        if(!tracker.isTunnelled())
            flag1 = true;
        else
            flag1 = false;
        Asserts.check(flag1, "Connection is already tunnelled");
        connection.update(null, tracker.getTargetHost(), flag, httpparams);
        tracker.tunnelTarget(flag);
    }

    protected final ClientConnectionOperator connOperator;
    protected final OperatedClientConnection connection;
    protected volatile HttpRoute route;
    protected volatile Object state;
    protected volatile RouteTracker tracker;
}
