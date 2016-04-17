// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.lang.reflect.*;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnection;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.conn:
//            ConnectionShutdownException, CPoolEntry

class CPoolProxy
    implements InvocationHandler
{

    CPoolProxy(CPoolEntry cpoolentry)
    {
        poolEntry = cpoolentry;
    }

    public static CPoolEntry detach(HttpClientConnection httpclientconnection)
    {
        return getHandler(httpclientconnection).detach();
    }

    private static CPoolProxy getHandler(HttpClientConnection httpclientconnection)
    {
        InvocationHandler invocationhandler = Proxy.getInvocationHandler(httpclientconnection);
        if(!org/apache/http/impl/conn/CPoolProxy.isInstance(invocationhandler))
            throw new IllegalStateException((new StringBuilder()).append("Unexpected proxy handler class: ").append(invocationhandler).toString());
        else
            return (CPoolProxy)org/apache/http/impl/conn/CPoolProxy.cast(invocationhandler);
    }

    public static CPoolEntry getPoolEntry(HttpClientConnection httpclientconnection)
    {
        CPoolEntry cpoolentry = getHandler(httpclientconnection).getPoolEntry();
        if(cpoolentry == null)
            throw new ConnectionShutdownException();
        else
            return cpoolentry;
    }

    public static HttpClientConnection newProxy(CPoolEntry cpoolentry)
    {
        return (HttpClientConnection)Proxy.newProxyInstance(org/apache/http/impl/conn/CPoolProxy.getClassLoader(), new Class[] {
            org/apache/http/conn/ManagedHttpClientConnection, org/apache/http/protocol/HttpContext
        }, new CPoolProxy(cpoolentry));
    }

    public void close()
        throws IOException
    {
        CPoolEntry cpoolentry = poolEntry;
        if(cpoolentry != null)
            cpoolentry.closeConnection();
    }

    CPoolEntry detach()
    {
        CPoolEntry cpoolentry = poolEntry;
        poolEntry = null;
        return cpoolentry;
    }

    HttpClientConnection getConnection()
    {
        CPoolEntry cpoolentry = poolEntry;
        if(cpoolentry == null)
            return null;
        else
            return (HttpClientConnection)cpoolentry.getConnection();
    }

    CPoolEntry getPoolEntry()
    {
        return poolEntry;
    }

    public Object invoke(Object obj, Method method, Object aobj[])
        throws Throwable
    {
        if(method.equals(CLOSE_METHOD))
        {
            close();
            return null;
        }
        if(method.equals(SHUTDOWN_METHOD))
        {
            shutdown();
            return null;
        }
        if(method.equals(IS_OPEN_METHOD))
            return Boolean.valueOf(isOpen());
        if(method.equals(IS_STALE_METHOD))
            return Boolean.valueOf(isStale());
        HttpClientConnection httpclientconnection = getConnection();
        if(httpclientconnection == null)
            throw new ConnectionShutdownException();
        Object obj1;
        try
        {
            obj1 = method.invoke(httpclientconnection, aobj);
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            Throwable throwable = invocationtargetexception.getCause();
            if(throwable != null)
                throw throwable;
            else
                throw invocationtargetexception;
        }
        return obj1;
    }

    public boolean isOpen()
    {
        CPoolEntry cpoolentry = poolEntry;
        boolean flag = false;
        if(cpoolentry != null)
        {
            boolean flag1 = cpoolentry.isClosed();
            flag = false;
            if(!flag1)
                flag = true;
        }
        return flag;
    }

    public boolean isStale()
    {
        HttpClientConnection httpclientconnection = getConnection();
        if(httpclientconnection != null)
            return httpclientconnection.isStale();
        else
            return true;
    }

    public void shutdown()
        throws IOException
    {
        CPoolEntry cpoolentry = poolEntry;
        if(cpoolentry != null)
            cpoolentry.shutdownConnection();
    }

    private static final Method CLOSE_METHOD;
    private static final Method IS_OPEN_METHOD;
    private static final Method IS_STALE_METHOD;
    private static final Method SHUTDOWN_METHOD;
    private volatile CPoolEntry poolEntry;

    static 
    {
        try
        {
            CLOSE_METHOD = org/apache/http/HttpConnection.getMethod("close", new Class[0]);
            SHUTDOWN_METHOD = org/apache/http/HttpConnection.getMethod("shutdown", new Class[0]);
            IS_OPEN_METHOD = org/apache/http/HttpConnection.getMethod("isOpen", new Class[0]);
            IS_STALE_METHOD = org/apache/http/HttpConnection.getMethod("isStale", new Class[0]);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new Error(nosuchmethodexception);
        }
    }
}
