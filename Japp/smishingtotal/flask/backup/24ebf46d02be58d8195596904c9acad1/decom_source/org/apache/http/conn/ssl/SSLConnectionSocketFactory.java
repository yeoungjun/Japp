// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.ssl.*;
import org.apache.http.HttpHost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

// Referenced classes of package org.apache.http.conn.ssl:
//            AllowAllHostnameVerifier, BrowserCompatHostnameVerifier, StrictHostnameVerifier, SSLInitializationException, 
//            SSLContexts, X509HostnameVerifier

public class SSLConnectionSocketFactory
    implements LayeredConnectionSocketFactory
{

    public SSLConnectionSocketFactory(SSLContext sslcontext)
    {
        this(sslcontext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLConnectionSocketFactory(SSLContext sslcontext, X509HostnameVerifier x509hostnameverifier)
    {
        this(((SSLContext)Args.notNull(sslcontext, "SSL context")).getSocketFactory(), null, null, x509hostnameverifier);
    }

    public SSLConnectionSocketFactory(SSLContext sslcontext, String as[], String as1[], X509HostnameVerifier x509hostnameverifier)
    {
        this(((SSLContext)Args.notNull(sslcontext, "SSL context")).getSocketFactory(), as, as1, x509hostnameverifier);
    }

    public SSLConnectionSocketFactory(SSLSocketFactory sslsocketfactory, X509HostnameVerifier x509hostnameverifier)
    {
        this(sslsocketfactory, null, null, x509hostnameverifier);
    }

    public SSLConnectionSocketFactory(SSLSocketFactory sslsocketfactory, String as[], String as1[], X509HostnameVerifier x509hostnameverifier)
    {
        Args.notNull(sslsocketfactory, "SSL socket factory");
        socketfactory = sslsocketfactory;
        supportedProtocols = as;
        supportedCipherSuites = as1;
        hostnameVerifier = x509hostnameverifier;
    }

    public static SSLConnectionSocketFactory getSocketFactory()
        throws SSLInitializationException
    {
        return new SSLConnectionSocketFactory(SSLContexts.createDefault(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public static SSLConnectionSocketFactory getSystemSocketFactory()
        throws SSLInitializationException
    {
        return new SSLConnectionSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault(), split(System.getProperty("https.protocols")), split(System.getProperty("https.cipherSuites")), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    private void internalPrepareSocket(SSLSocket sslsocket)
        throws IOException
    {
        if(supportedProtocols != null)
            sslsocket.setEnabledProtocols(supportedProtocols);
        if(supportedCipherSuites != null)
            sslsocket.setEnabledCipherSuites(supportedCipherSuites);
        prepareSocket(sslsocket);
    }

    private static String[] split(String s)
    {
        if(TextUtils.isBlank(s))
            return null;
        else
            return s.split(" *, *");
    }

    private void verifyHostname(SSLSocket sslsocket, String s)
        throws IOException
    {
        if(hostnameVerifier == null)
            break MISSING_BLOCK_LABEL_18;
        hostnameVerifier.verify(s, sslsocket);
        return;
        IOException ioexception;
        ioexception;
        try
        {
            sslsocket.close();
        }
        catch(Exception exception) { }
        throw ioexception;
    }

    public Socket connectSocket(int i, Socket socket, HttpHost httphost, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpContext httpcontext)
        throws IOException
    {
        Args.notNull(httphost, "HTTP host");
        Args.notNull(inetsocketaddress, "Remote address");
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
        if(socket1 instanceof SSLSocket)
        {
            SSLSocket sslsocket = (SSLSocket)socket1;
            sslsocket.startHandshake();
            verifyHostname(sslsocket, httphost.getHostName());
            return socket1;
        } else
        {
            return createLayeredSocket(socket1, httphost.getHostName(), inetsocketaddress.getPort(), httpcontext);
        }
    }

    public Socket createLayeredSocket(Socket socket, String s, int i, HttpContext httpcontext)
        throws IOException
    {
        SSLSocket sslsocket = (SSLSocket)socketfactory.createSocket(socket, s, i, true);
        internalPrepareSocket(sslsocket);
        sslsocket.startHandshake();
        verifyHostname(sslsocket, s);
        return sslsocket;
    }

    public Socket createSocket(HttpContext httpcontext)
        throws IOException
    {
        SSLSocket sslsocket = (SSLSocket)socketfactory.createSocket();
        internalPrepareSocket(sslsocket);
        return sslsocket;
    }

    protected void prepareSocket(SSLSocket sslsocket)
        throws IOException
    {
    }

    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    public static final String TLS = "TLS";
    private final X509HostnameVerifier hostnameVerifier;
    private final SSLSocketFactory socketfactory;
    private final String supportedCipherSuites[];
    private final String supportedProtocols[];

}
