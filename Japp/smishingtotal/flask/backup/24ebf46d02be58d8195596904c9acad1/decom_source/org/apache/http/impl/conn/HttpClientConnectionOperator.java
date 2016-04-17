// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultSchemePortResolver, SystemDefaultDnsResolver

class HttpClientConnectionOperator
{

    HttpClientConnectionOperator(Lookup lookup, SchemePortResolver schemeportresolver, DnsResolver dnsresolver)
    {
        Args.notNull(lookup, "Socket factory registry");
        socketFactoryRegistry = lookup;
        if(schemeportresolver == null)
            schemeportresolver = DefaultSchemePortResolver.INSTANCE;
        schemePortResolver = schemeportresolver;
        if(dnsresolver == null)
            dnsresolver = SystemDefaultDnsResolver.INSTANCE;
        dnsResolver = dnsresolver;
    }

    private Lookup getSocketFactoryRegistry(HttpContext httpcontext)
    {
        Lookup lookup = (Lookup)httpcontext.getAttribute("http.socket-factory-registry");
        if(lookup == null)
            lookup = socketFactoryRegistry;
        return lookup;
    }

    public void connect(ManagedHttpClientConnection managedhttpclientconnection, HttpHost httphost, InetSocketAddress inetsocketaddress, int i, SocketConfig socketconfig, HttpContext httpcontext)
        throws IOException
    {
        ConnectionSocketFactory connectionsocketfactory;
        java.net.InetAddress ainetaddress[];
        int j;
        int k;
        connectionsocketfactory = (ConnectionSocketFactory)getSocketFactoryRegistry(httpcontext).lookup(httphost.getSchemeName());
        if(connectionsocketfactory == null)
            throw new UnsupportedSchemeException((new StringBuilder()).append(httphost.getSchemeName()).append(" protocol is not supported").toString());
        ainetaddress = dnsResolver.resolve(httphost.getHostName());
        j = schemePortResolver.resolve(httphost);
        k = 0;
_L2:
        boolean flag;
        InetSocketAddress inetsocketaddress1;
        if(k >= ainetaddress.length)
            break MISSING_BLOCK_LABEL_282;
        java.net.InetAddress inetaddress = ainetaddress[k];
        Socket socket;
        Socket socket1;
        int l;
        if(k == -1 + ainetaddress.length)
            flag = true;
        else
            flag = false;
        socket = connectionsocketfactory.createSocket(httpcontext);
        socket.setReuseAddress(socketconfig.isSoReuseAddress());
        managedhttpclientconnection.bind(socket);
        inetsocketaddress1 = new InetSocketAddress(inetaddress, j);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connecting to ").append(inetsocketaddress1).toString());
        socket.setSoTimeout(socketconfig.getSoTimeout());
        socket1 = connectionsocketfactory.connectSocket(i, socket, httphost, inetsocketaddress1, inetsocketaddress, httpcontext);
        socket1.setTcpNoDelay(socketconfig.isTcpNoDelay());
        socket1.setKeepAlive(socketconfig.isSoKeepAlive());
        l = socketconfig.getSoLinger();
        if(l < 0)
            break MISSING_BLOCK_LABEL_274;
        boolean flag1;
        if(l > 0)
            flag1 = true;
        else
            flag1 = false;
        socket1.setSoLinger(flag1, l);
        managedhttpclientconnection.bind(socket1);
        return;
        SocketTimeoutException sockettimeoutexception;
        sockettimeoutexception;
        if(flag)
            throw new ConnectTimeoutException(sockettimeoutexception, httphost, ainetaddress);
        break MISSING_BLOCK_LABEL_361;
        ConnectException connectexception;
        connectexception;
        if(flag)
            if("Connection timed out".equals(connectexception.getMessage()))
                throw new ConnectTimeoutException(connectexception, httphost, ainetaddress);
            else
                throw new HttpHostConnectException(connectexception, httphost, ainetaddress);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connect to ").append(inetsocketaddress1).append(" timed out. ").append("Connection will be retried using another IP address").toString());
        k++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void upgrade(ManagedHttpClientConnection managedhttpclientconnection, HttpHost httphost, HttpContext httpcontext)
        throws IOException
    {
        ConnectionSocketFactory connectionsocketfactory = (ConnectionSocketFactory)getSocketFactoryRegistry(HttpClientContext.adapt(httpcontext)).lookup(httphost.getSchemeName());
        if(connectionsocketfactory == null)
            throw new UnsupportedSchemeException((new StringBuilder()).append(httphost.getSchemeName()).append(" protocol is not supported").toString());
        if(!(connectionsocketfactory instanceof LayeredConnectionSocketFactory))
        {
            throw new UnsupportedSchemeException((new StringBuilder()).append(httphost.getSchemeName()).append(" protocol does not support connection upgrade").toString());
        } else
        {
            LayeredConnectionSocketFactory layeredconnectionsocketfactory = (LayeredConnectionSocketFactory)connectionsocketfactory;
            Socket socket = managedhttpclientconnection.getSocket();
            int i = schemePortResolver.resolve(httphost);
            managedhttpclientconnection.bind(layeredconnectionsocketfactory.createLayeredSocket(socket, httphost.getHostName(), i, httpcontext));
            return;
        }
    }

    static final String SOCKET_FACTORY_REGISTRY = "http.socket-factory-registry";
    private final DnsResolver dnsResolver;
    private final Log log = LogFactory.getLog(org/apache/http/conn/HttpClientConnectionManager);
    private final SchemePortResolver schemePortResolver;
    private final Lookup socketFactoryRegistry;
}
