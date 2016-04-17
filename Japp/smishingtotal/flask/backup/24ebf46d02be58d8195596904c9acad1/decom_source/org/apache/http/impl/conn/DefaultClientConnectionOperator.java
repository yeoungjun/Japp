// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.conn.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.conn:
//            SystemDefaultDnsResolver, DefaultClientConnection

public class DefaultClientConnectionOperator
    implements ClientConnectionOperator
{

    public DefaultClientConnectionOperator(SchemeRegistry schemeregistry)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(schemeregistry, "Scheme registry");
        schemeRegistry = schemeregistry;
        dnsResolver = new SystemDefaultDnsResolver();
    }

    public DefaultClientConnectionOperator(SchemeRegistry schemeregistry, DnsResolver dnsresolver)
    {
        log = LogFactory.getLog(getClass());
        Args.notNull(schemeregistry, "Scheme registry");
        Args.notNull(dnsresolver, "DNS resolver");
        schemeRegistry = schemeregistry;
        dnsResolver = dnsresolver;
    }

    private SchemeRegistry getSchemeRegistry(HttpContext httpcontext)
    {
        SchemeRegistry schemeregistry = (SchemeRegistry)httpcontext.getAttribute("http.scheme-registry");
        if(schemeregistry == null)
            schemeregistry = schemeRegistry;
        return schemeregistry;
    }

    public OperatedClientConnection createConnection()
    {
        return new DefaultClientConnection();
    }

    public void openConnection(OperatedClientConnection operatedclientconnection, HttpHost httphost, InetAddress inetaddress, HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        int j;
        boolean flag1;
        HttpInetSocketAddress httpinetsocketaddress;
        Args.notNull(operatedclientconnection, "Connection");
        Args.notNull(httphost, "Target host");
        Args.notNull(httpparams, "HTTP parameters");
        boolean flag;
        Scheme scheme;
        SchemeSocketFactory schemesocketfactory;
        InetAddress ainetaddress[];
        int i;
        InetAddress inetaddress1;
        Socket socket;
        InetSocketAddress inetsocketaddress;
        Socket socket1;
        if(!operatedclientconnection.isOpen())
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Connection must not be open");
        scheme = getSchemeRegistry(httpcontext).getScheme(httphost.getSchemeName());
        schemesocketfactory = scheme.getSchemeSocketFactory();
        ainetaddress = resolveHostname(httphost.getHostName());
        i = scheme.resolvePort(httphost.getPort());
        j = 0;
_L2:
        if(j >= ainetaddress.length)
            break MISSING_BLOCK_LABEL_272;
        inetaddress1 = ainetaddress[j];
        if(j == -1 + ainetaddress.length)
            flag1 = true;
        else
            flag1 = false;
        socket = schemesocketfactory.createSocket(httpparams);
        operatedclientconnection.opening(socket, httphost);
        httpinetsocketaddress = new HttpInetSocketAddress(httphost, inetaddress1, i);
        inetsocketaddress = null;
        if(inetaddress != null)
            inetsocketaddress = new InetSocketAddress(inetaddress, 0);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connecting to ").append(httpinetsocketaddress).toString());
        socket1 = schemesocketfactory.connectSocket(socket, httpinetsocketaddress, inetsocketaddress, httpparams);
        if(socket == socket1)
            break MISSING_BLOCK_LABEL_245;
        socket = socket1;
        operatedclientconnection.opening(socket, httphost);
        prepareSocket(socket, httpcontext, httpparams);
        operatedclientconnection.openCompleted(schemesocketfactory.isSecure(socket), httpparams);
        return;
        ConnectException connectexception;
        connectexception;
        if(flag1)
            throw connectexception;
        break MISSING_BLOCK_LABEL_305;
        ConnectTimeoutException connecttimeoutexception;
        connecttimeoutexception;
        if(flag1)
            throw connecttimeoutexception;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Connect to ").append(httpinetsocketaddress).append(" timed out. ").append("Connection will be retried using another IP address").toString());
        j++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    protected void prepareSocket(Socket socket, HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpparams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpparams));
        int i = HttpConnectionParams.getLinger(httpparams);
        if(i >= 0)
        {
            boolean flag;
            if(i > 0)
                flag = true;
            else
                flag = false;
            socket.setSoLinger(flag, i);
        }
    }

    protected InetAddress[] resolveHostname(String s)
        throws UnknownHostException
    {
        return dnsResolver.resolve(s);
    }

    public void updateSecureConnection(OperatedClientConnection operatedclientconnection, HttpHost httphost, HttpContext httpcontext, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(operatedclientconnection, "Connection");
        Args.notNull(httphost, "Target host");
        Args.notNull(httpparams, "Parameters");
        Asserts.check(operatedclientconnection.isOpen(), "Connection must be open");
        Scheme scheme = getSchemeRegistry(httpcontext).getScheme(httphost.getSchemeName());
        Asserts.check(scheme.getSchemeSocketFactory() instanceof LayeredConnectionSocketFactory, "Socket factory must implement SchemeLayeredSocketFactory");
        SchemeLayeredSocketFactory schemelayeredsocketfactory = (SchemeLayeredSocketFactory)scheme.getSchemeSocketFactory();
        Socket socket = schemelayeredsocketfactory.createLayeredSocket(operatedclientconnection.getSocket(), httphost.getHostName(), scheme.resolvePort(httphost.getPort()), httpparams);
        prepareSocket(socket, httpcontext, httpparams);
        operatedclientconnection.update(socket, httphost, schemelayeredsocketfactory.isSecure(socket), httpparams);
    }

    protected final DnsResolver dnsResolver;
    private final Log log;
    protected final SchemeRegistry schemeRegistry;
}
