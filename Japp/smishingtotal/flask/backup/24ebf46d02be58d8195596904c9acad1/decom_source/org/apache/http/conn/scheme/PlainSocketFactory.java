// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.conn.scheme:
//            SocketFactory, SchemeSocketFactory, HostNameResolver

public class PlainSocketFactory
    implements SocketFactory, SchemeSocketFactory
{

    public PlainSocketFactory()
    {
        nameResolver = null;
    }

    public PlainSocketFactory(HostNameResolver hostnameresolver)
    {
        nameResolver = hostnameresolver;
    }

    public static PlainSocketFactory getSocketFactory()
    {
        return new PlainSocketFactory();
    }

    public Socket connectSocket(Socket socket, String s, int i, InetAddress inetaddress, int j, HttpParams httpparams)
        throws IOException, UnknownHostException, ConnectTimeoutException
    {
        InetAddress inetaddress1;
label0:
        {
            InetSocketAddress inetsocketaddress;
            if(inetaddress == null)
            {
                inetsocketaddress = null;
                if(j <= 0)
                    break label0;
            }
            if(j <= 0)
                j = 0;
            inetsocketaddress = new InetSocketAddress(inetaddress, j);
        }
        if(nameResolver != null)
            inetaddress1 = nameResolver.resolve(s);
        else
            inetaddress1 = InetAddress.getByName(s);
        return connectSocket(socket, new InetSocketAddress(inetaddress1, i), inetsocketaddress, httpparams);
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpParams httpparams)
        throws IOException, ConnectTimeoutException
    {
        Args.notNull(inetsocketaddress, "Remote address");
        Args.notNull(httpparams, "HTTP parameters");
        Socket socket1 = socket;
        if(socket1 == null)
            socket1 = createSocket();
        if(inetsocketaddress1 != null)
        {
            socket1.setReuseAddress(HttpConnectionParams.getSoReuseaddr(httpparams));
            socket1.bind(inetsocketaddress1);
        }
        int i = HttpConnectionParams.getConnectionTimeout(httpparams);
        int j = HttpConnectionParams.getSoTimeout(httpparams);
        try
        {
            socket1.setSoTimeout(j);
            socket1.connect(inetsocketaddress, i);
        }
        catch(SocketTimeoutException sockettimeoutexception)
        {
            throw new ConnectTimeoutException((new StringBuilder()).append("Connect to ").append(inetsocketaddress).append(" timed out").toString());
        }
        return socket1;
    }

    public Socket createSocket()
    {
        return new Socket();
    }

    public Socket createSocket(HttpParams httpparams)
    {
        return new Socket();
    }

    public final boolean isSecure(Socket socket)
    {
        return false;
    }

    private final HostNameResolver nameResolver;
}
