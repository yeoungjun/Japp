// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.conn.scheme:
//            SocketFactory, SchemeSocketFactory

class SocketFactoryAdaptor
    implements SocketFactory
{

    SocketFactoryAdaptor(SchemeSocketFactory schemesocketfactory)
    {
        factory = schemesocketfactory;
    }

    public Socket connectSocket(Socket socket, String s, int i, InetAddress inetaddress, int j, HttpParams httpparams)
        throws IOException, UnknownHostException, ConnectTimeoutException
    {
label0:
        {
            InetSocketAddress inetsocketaddress;
            if(inetaddress == null)
            {
                inetsocketaddress = null;
                if(j <= 0)
                    break label0;
            }
            InetSocketAddress inetsocketaddress1;
            if(j <= 0)
                j = 0;
            inetsocketaddress = new InetSocketAddress(inetaddress, j);
        }
        inetsocketaddress1 = new InetSocketAddress(InetAddress.getByName(s), i);
        return factory.connectSocket(socket, inetsocketaddress1, inetsocketaddress, httpparams);
    }

    public Socket createSocket()
        throws IOException
    {
        BasicHttpParams basichttpparams = new BasicHttpParams();
        return factory.createSocket(basichttpparams);
    }

    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(this == obj)
            return true;
        if(obj instanceof SocketFactoryAdaptor)
            return factory.equals(((SocketFactoryAdaptor)obj).factory);
        else
            return factory.equals(obj);
    }

    public SchemeSocketFactory getFactory()
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

    private final SchemeSocketFactory factory;
}
