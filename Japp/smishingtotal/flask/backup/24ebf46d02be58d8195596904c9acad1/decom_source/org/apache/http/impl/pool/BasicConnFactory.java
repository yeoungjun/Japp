// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.pool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.*;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultBHttpClientConnectionFactory;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.pool.ConnFactory;
import org.apache.http.util.Args;

public class BasicConnFactory
    implements ConnFactory
{

    public BasicConnFactory()
    {
        this(null, null, 0, SocketConfig.DEFAULT, ConnectionConfig.DEFAULT);
    }

    public BasicConnFactory(int i, SocketConfig socketconfig, ConnectionConfig connectionconfig)
    {
        this(null, null, i, socketconfig, connectionconfig);
    }

    public BasicConnFactory(SocketFactory socketfactory, SSLSocketFactory sslsocketfactory, int i, SocketConfig socketconfig, ConnectionConfig connectionconfig)
    {
        plainfactory = socketfactory;
        sslfactory = sslsocketfactory;
        connectTimeout = i;
        if(socketconfig == null)
            socketconfig = SocketConfig.DEFAULT;
        sconfig = socketconfig;
        if(connectionconfig == null)
            connectionconfig = ConnectionConfig.DEFAULT;
        connFactory = new DefaultBHttpClientConnectionFactory(connectionconfig);
    }

    public BasicConnFactory(SSLSocketFactory sslsocketfactory, HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP params");
        plainfactory = null;
        sslfactory = sslsocketfactory;
        connectTimeout = httpparams.getIntParameter("http.connection.timeout", 0);
        sconfig = HttpParamConfig.getSocketConfig(httpparams);
        connFactory = new DefaultBHttpClientConnectionFactory(HttpParamConfig.getConnectionConfig(httpparams));
    }

    public BasicConnFactory(SocketConfig socketconfig, ConnectionConfig connectionconfig)
    {
        this(null, null, 0, socketconfig, connectionconfig);
    }

    public BasicConnFactory(HttpParams httpparams)
    {
        this(((SSLSocketFactory) (null)), httpparams);
    }

    public volatile Object create(Object obj)
        throws IOException
    {
        return create((HttpHost)obj);
    }

    protected HttpClientConnection create(Socket socket, HttpParams httpparams)
        throws IOException
    {
        DefaultBHttpClientConnection defaultbhttpclientconnection = new DefaultBHttpClientConnection(httpparams.getIntParameter("http.socket.buffer-size", 8192));
        defaultbhttpclientconnection.bind(socket);
        return defaultbhttpclientconnection;
    }

    public HttpClientConnection create(HttpHost httphost)
        throws IOException
    {
        String s = httphost.getSchemeName();
        boolean flag = "http".equalsIgnoreCase(s);
        Socket socket = null;
        if(flag)
            if(plainfactory != null)
                socket = plainfactory.createSocket();
            else
                socket = new Socket();
        if("https".equalsIgnoreCase(s))
        {
            Object obj;
            if(sslfactory != null)
                obj = sslfactory;
            else
                obj = SSLSocketFactory.getDefault();
            socket = ((SocketFactory) (obj)).createSocket();
        }
        if(socket == null)
            throw new IOException((new StringBuilder()).append(s).append(" scheme is not supported").toString());
        String s1 = httphost.getHostName();
        int i = httphost.getPort();
        int j;
        if(i == -1)
            if(httphost.getSchemeName().equalsIgnoreCase("http"))
                i = 80;
            else
            if(httphost.getSchemeName().equalsIgnoreCase("https"))
                i = 443;
        socket.setSoTimeout(sconfig.getSoTimeout());
        socket.connect(new InetSocketAddress(s1, i), connectTimeout);
        socket.setTcpNoDelay(sconfig.isTcpNoDelay());
        j = sconfig.getSoLinger();
        if(j >= 0)
        {
            boolean flag1;
            if(j > 0)
                flag1 = true;
            else
                flag1 = false;
            socket.setSoLinger(flag1, j);
        }
        return (HttpClientConnection)connFactory.createConnection(socket);
    }

    private final HttpConnectionFactory connFactory;
    private final int connectTimeout;
    private final SocketFactory plainfactory;
    private final SocketConfig sconfig;
    private final SSLSocketFactory sslfactory;
}
