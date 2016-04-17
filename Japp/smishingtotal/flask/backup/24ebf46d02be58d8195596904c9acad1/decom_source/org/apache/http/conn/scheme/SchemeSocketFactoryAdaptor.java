// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.conn.scheme:
//            SchemeSocketFactory, SocketFactory

class SchemeSocketFactoryAdaptor
    implements SchemeSocketFactory
{

    SchemeSocketFactoryAdaptor(SocketFactory socketfactory)
    {
        factory = socketfactory;
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpParams httpparams)
        throws IOException, UnknownHostException, ConnectTimeoutException
    {
        String s = inetsocketaddress.getHostName();
        int i = inetsocketaddress.getPort();
        java.net.InetAddress inetaddress = null;
        int j = 0;
        if(inetsocketaddress1 != null)
        {
            inetaddress = inetsocketaddress1.getAddress();
            j = inetsocketaddress1.getPort();
        }
        return factory.connectSocket(socket, s, i, inetaddress, j, httpparams);
    }

    public Socket createSocket(HttpParams httpparams)
        throws IOException
    {
        return factory.createSocket();
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(obj instanceof SchemeSocketFactoryAdaptor)
            return factory.equals(((SchemeSocketFactoryAdaptor)obj).factory);
        else
            return factory.equals(obj);
    }

    public SocketFactory getFactory()
    {
        return factory;
    }

    public int hashCode()
    {
        return factory.hashCode();
    }

    public boolean isSecure(Socket socket)
        throws IllegalArgumentException
    {
        return factory.isSecure(socket);
    }

    private final SocketFactory factory;
}
