// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpParams;

public interface SchemeSocketFactory
{

    public abstract Socket connectSocket(Socket socket, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpParams httpparams)
        throws IOException, UnknownHostException, ConnectTimeoutException;

    public abstract Socket createSocket(HttpParams httpparams)
        throws IOException;

    public abstract boolean isSecure(Socket socket)
        throws IllegalArgumentException;
}
