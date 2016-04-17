// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.io.IOException;
import java.net.*;
import org.apache.http.HttpInetConnection;
import org.apache.http.impl.io.SocketInputBuffer;
import org.apache.http.impl.io.SocketOutputBuffer;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl:
//            AbstractHttpClientConnection

public class SocketHttpClientConnection extends AbstractHttpClientConnection
    implements HttpInetConnection
{

    public SocketHttpClientConnection()
    {
        socket = null;
    }

    private static void formatAddress(StringBuilder stringbuilder, SocketAddress socketaddress)
    {
        if(socketaddress instanceof InetSocketAddress)
        {
            InetSocketAddress inetsocketaddress = (InetSocketAddress)socketaddress;
            Object obj;
            if(inetsocketaddress.getAddress() != null)
                obj = inetsocketaddress.getAddress().getHostAddress();
            else
                obj = inetsocketaddress.getAddress();
            stringbuilder.append(obj).append(':').append(inetsocketaddress.getPort());
            return;
        } else
        {
            stringbuilder.append(socketaddress);
            return;
        }
    }

    protected void assertNotOpen()
    {
        boolean flag;
        if(!open)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection is already open");
    }

    protected void assertOpen()
    {
        Asserts.check(open, "Connection is not open");
    }

    protected void bind(Socket socket1, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(socket1, "Socket");
        Args.notNull(httpparams, "HTTP parameters");
        socket = socket1;
        int i = httpparams.getIntParameter("http.socket.buffer-size", -1);
        init(createSessionInputBuffer(socket1, i, httpparams), createSessionOutputBuffer(socket1, i, httpparams), httpparams);
        open = true;
    }

    public void close()
        throws IOException
    {
        Socket socket1;
        if(!open)
            return;
        open = false;
        socket1 = socket;
        doFlush();
        Exception exception;
        try
        {
            socket1.shutdownOutput();
        }
        catch(IOException ioexception) { }
        try
        {
            socket1.shutdownInput();
        }
        catch(IOException ioexception1) { }
_L2:
        socket1.close();
        return;
        exception;
        socket1.close();
        throw exception;
        UnsupportedOperationException unsupportedoperationexception;
        unsupportedoperationexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected SessionInputBuffer createSessionInputBuffer(Socket socket1, int i, HttpParams httpparams)
        throws IOException
    {
        return new SocketInputBuffer(socket1, i, httpparams);
    }

    protected SessionOutputBuffer createSessionOutputBuffer(Socket socket1, int i, HttpParams httpparams)
        throws IOException
    {
        return new SocketOutputBuffer(socket1, i, httpparams);
    }

    public InetAddress getLocalAddress()
    {
        if(socket != null)
            return socket.getLocalAddress();
        else
            return null;
    }

    public int getLocalPort()
    {
        if(socket != null)
            return socket.getLocalPort();
        else
            return -1;
    }

    public InetAddress getRemoteAddress()
    {
        if(socket != null)
            return socket.getInetAddress();
        else
            return null;
    }

    public int getRemotePort()
    {
        if(socket != null)
            return socket.getPort();
        else
            return -1;
    }

    protected Socket getSocket()
    {
        return socket;
    }

    public int getSocketTimeout()
    {
        int i = -1;
        if(socket != null)
        {
            int j;
            try
            {
                j = socket.getSoTimeout();
            }
            catch(SocketException socketexception)
            {
                return i;
            }
            i = j;
        }
        return i;
    }

    public boolean isOpen()
    {
        return open;
    }

    public void setSocketTimeout(int i)
    {
        assertOpen();
        if(socket == null)
            break MISSING_BLOCK_LABEL_19;
        socket.setSoTimeout(i);
        return;
        SocketException socketexception;
        socketexception;
    }

    public void shutdown()
        throws IOException
    {
        open = false;
        Socket socket1 = socket;
        if(socket1 != null)
            socket1.close();
    }

    public String toString()
    {
        if(socket != null)
        {
            StringBuilder stringbuilder = new StringBuilder();
            SocketAddress socketaddress = socket.getRemoteSocketAddress();
            SocketAddress socketaddress1 = socket.getLocalSocketAddress();
            if(socketaddress != null && socketaddress1 != null)
            {
                formatAddress(stringbuilder, socketaddress1);
                stringbuilder.append("<->");
                formatAddress(stringbuilder, socketaddress);
            }
            return stringbuilder.toString();
        } else
        {
            return super.toString();
        }
    }

    private volatile boolean open;
    private volatile Socket socket;
}
