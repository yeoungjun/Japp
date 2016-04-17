// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl:
//            SocketHttpServerConnection

public class DefaultHttpServerConnection extends SocketHttpServerConnection
{

    public DefaultHttpServerConnection()
    {
    }

    public void bind(Socket socket, HttpParams httpparams)
        throws IOException
    {
        boolean flag = true;
        Args.notNull(socket, "Socket");
        Args.notNull(httpparams, "HTTP parameters");
        assertNotOpen();
        socket.setTcpNoDelay(httpparams.getBooleanParameter("http.tcp.nodelay", flag));
        socket.setSoTimeout(httpparams.getIntParameter("http.socket.timeout", 0));
        socket.setKeepAlive(httpparams.getBooleanParameter("http.socket.keepalive", false));
        int i = httpparams.getIntParameter("http.socket.linger", -1);
        if(i >= 0)
        {
            boolean flag1;
            if(i > 0)
                flag1 = flag;
            else
                flag1 = false;
            socket.setSoLinger(flag1, i);
        }
        if(i >= 0)
        {
            if(i <= 0)
                flag = false;
            socket.setSoLinger(flag, i);
        }
        super.bind(socket, httpparams);
    }
}
