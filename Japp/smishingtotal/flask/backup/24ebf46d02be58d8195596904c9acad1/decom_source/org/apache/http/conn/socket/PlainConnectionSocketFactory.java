// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.HttpHost;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.conn.socket:
//            ConnectionSocketFactory

public class PlainConnectionSocketFactory
    implements ConnectionSocketFactory
{

    public PlainConnectionSocketFactory()
    {
    }

    public static PlainConnectionSocketFactory getSocketFactory()
    {
        return INSTANCE;
    }

    public Socket connectSocket(int i, Socket socket, HttpHost httphost, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpContext httpcontext)
        throws IOException
    {
        Socket socket1;
        if(socket != null)
            socket1 = socket;
        else
            socket1 = createSocket(httpcontext);
        if(inetsocketaddress1 != null)
            socket1.bind(inetsocketaddress1);
        try
        {
            socket1.connect(inetsocketaddress, i);
        }
        catch(IOException ioexception)
        {
            try
            {
                socket1.close();
            }
            catch(IOException ioexception1) { }
            throw ioexception;
        }
        return socket1;
    }

    public Socket createSocket(HttpContext httpcontext)
        throws IOException
    {
        return new Socket();
    }

    public static final PlainConnectionSocketFactory INSTANCE = new PlainConnectionSocketFactory();

}
