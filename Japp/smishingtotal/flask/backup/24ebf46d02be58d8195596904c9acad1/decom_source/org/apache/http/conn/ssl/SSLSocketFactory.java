// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import org.apache.http.HttpHost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpInetSocketAddress;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.TextUtils;

// Referenced classes of package org.apache.http.conn.ssl:
//            AllowAllHostnameVerifier, BrowserCompatHostnameVerifier, StrictHostnameVerifier, SSLContexts, 
//            SSLContextBuilder, SSLInitializationException, X509HostnameVerifier, TrustStrategy

public class SSLSocketFactory
    implements LayeredConnectionSocketFactory, SchemeLayeredSocketFactory, LayeredSchemeSocketFactory, LayeredSocketFactory
{

    public SSLSocketFactory(String s, KeyStore keystore, String s1, KeyStore keystore1, SecureRandom securerandom, HostNameResolver hostnameresolver)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        SSLContextBuilder sslcontextbuilder = SSLContexts.custom().useProtocol(s).setSecureRandom(securerandom);
        char ac[];
        if(s1 != null)
            ac = s1.toCharArray();
        else
            ac = null;
        this(sslcontextbuilder.loadKeyMaterial(keystore, ac).loadTrustMaterial(keystore1).build(), hostnameresolver);
    }

    public SSLSocketFactory(String s, KeyStore keystore, String s1, KeyStore keystore1, SecureRandom securerandom, TrustStrategy truststrategy, X509HostnameVerifier x509hostnameverifier)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        SSLContextBuilder sslcontextbuilder = SSLContexts.custom().useProtocol(s).setSecureRandom(securerandom);
        char ac[];
        if(s1 != null)
            ac = s1.toCharArray();
        else
            ac = null;
        this(sslcontextbuilder.loadKeyMaterial(keystore, ac).loadTrustMaterial(keystore1, truststrategy).build(), x509hostnameverifier);
    }

    public SSLSocketFactory(String s, KeyStore keystore, String s1, KeyStore keystore1, SecureRandom securerandom, X509HostnameVerifier x509hostnameverifier)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        SSLContextBuilder sslcontextbuilder = SSLContexts.custom().useProtocol(s).setSecureRandom(securerandom);
        char ac[];
        if(s1 != null)
            ac = s1.toCharArray();
        else
            ac = null;
        this(sslcontextbuilder.loadKeyMaterial(keystore, ac).loadTrustMaterial(keystore1).build(), x509hostnameverifier);
    }

    public SSLSocketFactory(KeyStore keystore)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        this(SSLContexts.custom().loadTrustMaterial(keystore).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keystore, String s)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        SSLContextBuilder sslcontextbuilder = SSLContexts.custom();
        char ac[];
        if(s != null)
            ac = s.toCharArray();
        else
            ac = null;
        this(sslcontextbuilder.loadKeyMaterial(keystore, ac).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keystore, String s, KeyStore keystore1)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        SSLContextBuilder sslcontextbuilder = SSLContexts.custom();
        char ac[];
        if(s != null)
            ac = s.toCharArray();
        else
            ac = null;
        this(sslcontextbuilder.loadKeyMaterial(keystore, ac).loadTrustMaterial(keystore1).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sslcontext)
    {
        this(sslcontext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sslcontext, HostNameResolver hostnameresolver)
    {
        socketfactory = sslcontext.getSocketFactory();
        hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        nameResolver = hostnameresolver;
        supportedProtocols = null;
        supportedCipherSuites = null;
    }

    public SSLSocketFactory(SSLContext sslcontext, X509HostnameVerifier x509hostnameverifier)
    {
        this(((SSLContext)Args.notNull(sslcontext, "SSL context")).getSocketFactory(), null, null, x509hostnameverifier);
    }

    public SSLSocketFactory(SSLContext sslcontext, String as[], String as1[], X509HostnameVerifier x509hostnameverifier)
    {
        this(((SSLContext)Args.notNull(sslcontext, "SSL context")).getSocketFactory(), as, as1, x509hostnameverifier);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sslsocketfactory, X509HostnameVerifier x509hostnameverifier)
    {
        this(sslsocketfactory, null, null, x509hostnameverifier);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sslsocketfactory, String as[], String as1[], X509HostnameVerifier x509hostnameverifier)
    {
        Args.notNull(sslsocketfactory, "SSL socket factory");
        socketfactory = sslsocketfactory;
        supportedProtocols = as;
        supportedCipherSuites = as1;
        hostnameVerifier = x509hostnameverifier;
        nameResolver = null;
    }

    public SSLSocketFactory(TrustStrategy truststrategy)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        this(SSLContexts.custom().loadTrustMaterial(null, truststrategy).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(TrustStrategy truststrategy, X509HostnameVerifier x509hostnameverifier)
        throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
        this(SSLContexts.custom().loadTrustMaterial(null, truststrategy).build(), x509hostnameverifier);
    }

    public static SSLSocketFactory getSocketFactory()
        throws SSLInitializationException
    {
        return new SSLSocketFactory(SSLContexts.createDefault(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public static SSLSocketFactory getSystemSocketFactory()
        throws SSLInitializationException
    {
        return new SSLSocketFactory((javax.net.ssl.SSLSocketFactory)javax.net.ssl.SSLSocketFactory.getDefault(), split(System.getProperty("https.protocols")), split(System.getProperty("https.cipherSuites")), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
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

    public Socket connectSocket(Socket socket, String s, int i, InetAddress inetaddress, int j, HttpParams httpparams)
        throws IOException, UnknownHostException, ConnectTimeoutException
    {
label0:
        {
            InetAddress inetaddress1;
            InetSocketAddress inetsocketaddress;
            if(nameResolver != null)
                inetaddress1 = nameResolver.resolve(s);
            else
                inetaddress1 = InetAddress.getByName(s);
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
        return connectSocket(socket, ((InetSocketAddress) (new HttpInetSocketAddress(new HttpHost(s, i), inetaddress1, i))), inetsocketaddress, httpparams);
    }

    public Socket connectSocket(Socket socket, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpParams httpparams)
        throws IOException, UnknownHostException, ConnectTimeoutException
    {
        Args.notNull(inetsocketaddress, "Remote address");
        Args.notNull(httpparams, "HTTP parameters");
        HttpHost httphost;
        if(inetsocketaddress instanceof HttpInetSocketAddress)
            httphost = ((HttpInetSocketAddress)inetsocketaddress).getHttpHost();
        else
            httphost = new HttpHost(inetsocketaddress.getHostName(), inetsocketaddress.getPort(), "https");
        return connectSocket(HttpConnectionParams.getConnectionTimeout(httpparams), socket, httphost, inetsocketaddress, inetsocketaddress1, ((HttpContext) (null)));
    }

    public Socket createLayeredSocket(Socket socket, String s, int i, HttpParams httpparams)
        throws IOException, UnknownHostException
    {
        return createLayeredSocket(socket, s, i, (HttpContext)null);
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

    public Socket createLayeredSocket(Socket socket, String s, int i, boolean flag)
        throws IOException, UnknownHostException
    {
        return createLayeredSocket(socket, s, i, (HttpContext)null);
    }

    public Socket createSocket()
        throws IOException
    {
        return createSocket((HttpContext)null);
    }

    public Socket createSocket(Socket socket, String s, int i, boolean flag)
        throws IOException, UnknownHostException
    {
        return createLayeredSocket(socket, s, i, flag);
    }

    public Socket createSocket(HttpParams httpparams)
        throws IOException
    {
        return createSocket((HttpContext)null);
    }

    public Socket createSocket(HttpContext httpcontext)
        throws IOException
    {
        SSLSocket sslsocket = (SSLSocket)socketfactory.createSocket();
        internalPrepareSocket(sslsocket);
        return sslsocket;
    }

    public X509HostnameVerifier getHostnameVerifier()
    {
        return hostnameVerifier;
    }

    public boolean isSecure(Socket socket)
        throws IllegalArgumentException
    {
        Args.notNull(socket, "Socket");
        Asserts.check(socket instanceof SSLSocket, "Socket not created by this factory");
        boolean flag;
        if(!socket.isClosed())
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "Socket is closed");
        return true;
    }

    protected void prepareSocket(SSLSocket sslsocket)
        throws IOException
    {
    }

    public void setHostnameVerifier(X509HostnameVerifier x509hostnameverifier)
    {
        Args.notNull(x509hostnameverifier, "Hostname verifier");
        hostnameVerifier = x509hostnameverifier;
    }

    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    public static final String TLS = "TLS";
    private volatile X509HostnameVerifier hostnameVerifier;
    private final HostNameResolver nameResolver;
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    private final String supportedCipherSuites[];
    private final String supportedProtocols[];

}
