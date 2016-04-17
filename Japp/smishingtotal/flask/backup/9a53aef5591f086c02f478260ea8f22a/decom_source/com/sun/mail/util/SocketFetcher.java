// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketFetcher
{

    private SocketFetcher()
    {
    }

    private static void configureSSLSocket(Socket socket, Properties properties, String s)
    {
        if(socket instanceof SSLSocket)
        {
            SSLSocket sslsocket = (SSLSocket)socket;
            String s1 = properties.getProperty((new StringBuilder(String.valueOf(s))).append(".ssl.protocols").toString(), null);
            String s2;
            if(s1 != null)
                sslsocket.setEnabledProtocols(stringArray(s1));
            else
                sslsocket.setEnabledProtocols(new String[] {
                    "TLSv1"
                });
            s2 = properties.getProperty((new StringBuilder(String.valueOf(s))).append(".ssl.ciphersuites").toString(), null);
            if(s2 != null)
            {
                sslsocket.setEnabledCipherSuites(stringArray(s2));
                return;
            }
        }
    }

    private static Socket createSocket(InetAddress inetaddress, int i, String s, int j, int k, SocketFactory socketfactory, boolean flag)
        throws IOException
    {
        Socket socket;
        if(socketfactory != null)
            socket = socketfactory.createSocket();
        else
        if(flag)
            socket = SSLSocketFactory.getDefault().createSocket();
        else
            socket = new Socket();
        if(inetaddress != null)
            socket.bind(new InetSocketAddress(inetaddress, i));
        if(k >= 0)
        {
            socket.connect(new InetSocketAddress(s, j), k);
            return socket;
        } else
        {
            socket.connect(new InetSocketAddress(s, j));
            return socket;
        }
    }

    private static ClassLoader getContextClassLoader()
    {
        return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {

            public Object run()
            {
                ClassLoader classloader;
                try
                {
                    classloader = Thread.currentThread().getContextClassLoader();
                }
                catch(SecurityException securityexception)
                {
                    return null;
                }
                return classloader;
            }

        });
    }

    public static Socket getSocket(String s, int i, Properties properties, String s1)
        throws IOException
    {
        return getSocket(s, i, properties, s1, false);
    }

    public static Socket getSocket(String s, int i, Properties properties, String s1, boolean flag)
        throws IOException
    {
        String s3;
        int j;
        if(s1 == null)
            s1 = "socket";
        if(properties == null)
            properties = new Properties();
        String s2 = (new StringBuilder(String.valueOf(s1))).append(".connectiontimeout").toString();
        s3 = properties.getProperty(s2, null);
        j = -1;
        if(s3 == null)
            break MISSING_BLOCK_LABEL_68;
        int i2 = Integer.parseInt(s3);
        j = i2;
_L4:
        String s5;
        InetAddress inetaddress;
        String s9;
        int k;
        String s4 = (new StringBuilder(String.valueOf(s1))).append(".timeout").toString();
        s5 = properties.getProperty(s4, null);
        String s6 = (new StringBuilder(String.valueOf(s1))).append(".localaddress").toString();
        String s7 = properties.getProperty(s6, null);
        inetaddress = null;
        if(s7 != null)
            inetaddress = InetAddress.getByName(s7);
        String s8 = (new StringBuilder(String.valueOf(s1))).append(".localport").toString();
        s9 = properties.getProperty(s8, null);
        k = 0;
        if(s9 == null)
            break MISSING_BLOCK_LABEL_192;
        int l1 = Integer.parseInt(s9);
        k = l1;
_L5:
        boolean flag1;
        String s13;
        int l;
        Socket socket;
        String s10 = (new StringBuilder(String.valueOf(s1))).append(".socketFactory.fallback").toString();
        String s11 = properties.getProperty(s10, null);
        String s12;
        int i1;
        int j1;
        SocketFactory socketfactory;
        String s14;
        String s15;
        Socket socket1;
        int k1;
        if(s11 != null && s11.equalsIgnoreCase("false"))
            flag1 = false;
        else
            flag1 = true;
        s12 = (new StringBuilder(String.valueOf(s1))).append(".socketFactory.class").toString();
        s13 = properties.getProperty(s12, null);
        l = -1;
        socketfactory = getSocketFactory(s13);
        socket = null;
        if(socketfactory == null) goto _L2; else goto _L1
_L1:
        s14 = (new StringBuilder(String.valueOf(s1))).append(".socketFactory.port").toString();
        s15 = properties.getProperty(s14, null);
        if(s15 == null)
            break MISSING_BLOCK_LABEL_334;
        k1 = Integer.parseInt(s15);
        l = k1;
_L6:
        if(l == -1)
            l = i;
        socket1 = createSocket(inetaddress, k, s, l, j, socketfactory, flag);
        socket = socket1;
_L2:
        if(socket == null)
            socket = createSocket(inetaddress, k, s, i, j, null, flag);
        i1 = -1;
        if(s5 == null)
            break MISSING_BLOCK_LABEL_405;
        j1 = Integer.parseInt(s5);
        i1 = j1;
_L7:
        if(i1 >= 0)
            socket.setSoTimeout(i1);
        configureSSLSocket(socket, properties, s1);
        return socket;
        SocketTimeoutException sockettimeoutexception;
        sockettimeoutexception;
        throw sockettimeoutexception;
        Exception exception;
        exception;
        socket = null;
        if(flag1) goto _L2; else goto _L3
_L3:
        if(exception instanceof InvocationTargetException)
        {
            Throwable throwable = ((InvocationTargetException)exception).getTargetException();
            if(throwable instanceof Exception)
                exception = (Exception)throwable;
        }
        if(exception instanceof IOException)
        {
            throw (IOException)exception;
        } else
        {
            IOException ioexception = new IOException((new StringBuilder("Couldn't connect using \"")).append(s13).append("\" socket factory to host, port: ").append(s).append(", ").append(l).append("; Exception: ").append(exception).toString());
            ioexception.initCause(exception);
            throw ioexception;
        }
        NumberFormatException numberformatexception3;
        numberformatexception3;
          goto _L4
        NumberFormatException numberformatexception2;
        numberformatexception2;
        k = 0;
          goto _L5
        NumberFormatException numberformatexception1;
        numberformatexception1;
          goto _L6
        NumberFormatException numberformatexception;
        numberformatexception;
          goto _L7
    }

    private static SocketFactory getSocketFactory(String s)
        throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        ClassLoader classloader;
        Class class1;
        if(s == null || s.length() == 0)
            return null;
        classloader = getContextClassLoader();
        class1 = null;
        if(classloader == null)
            break MISSING_BLOCK_LABEL_33;
        Class class2 = classloader.loadClass(s);
        class1 = class2;
_L2:
        if(class1 == null)
            class1 = Class.forName(s);
        return (SocketFactory)class1.getMethod("getDefault", new Class[0]).invoke(new Object(), new Object[0]);
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        class1 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static Socket startTLS(Socket socket)
        throws IOException
    {
        return startTLS(socket, new Properties(), "socket");
    }

    public static Socket startTLS(Socket socket, Properties properties, String s)
        throws IOException
    {
        String s1 = socket.getInetAddress().getHostName();
        int i = socket.getPort();
        SocketFactory socketfactory;
        SSLSocketFactory sslsocketfactory;
        Socket socket1;
        try
        {
            socketfactory = getSocketFactory(properties.getProperty((new StringBuilder(String.valueOf(s))).append(".socketFactory.class").toString(), null));
        }
        catch(Exception exception)
        {
            if(exception instanceof InvocationTargetException)
            {
                Throwable throwable = ((InvocationTargetException)exception).getTargetException();
                if(throwable instanceof Exception)
                    exception = (Exception)throwable;
            }
            if(exception instanceof IOException)
            {
                throw (IOException)exception;
            } else
            {
                IOException ioexception = new IOException((new StringBuilder("Exception in startTLS: host ")).append(s1).append(", port ").append(i).append("; Exception: ").append(exception).toString());
                ioexception.initCause(exception);
                throw ioexception;
            }
        }
        if(socketfactory == null)
            break MISSING_BLOCK_LABEL_85;
        if(!(socketfactory instanceof SSLSocketFactory))
            break MISSING_BLOCK_LABEL_85;
        sslsocketfactory = (SSLSocketFactory)socketfactory;
_L1:
        socket1 = sslsocketfactory.createSocket(socket, s1, i, true);
        configureSSLSocket(socket1, properties, s);
        return socket1;
        sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
          goto _L1
    }

    private static String[] stringArray(String s)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s);
        ArrayList arraylist = new ArrayList();
        do
        {
            if(!stringtokenizer.hasMoreTokens())
                return (String[])arraylist.toArray(new String[arraylist.size()]);
            arraylist.add(stringtokenizer.nextToken());
        } while(true);
    }
}
