// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.http.HttpHost;
import org.apache.http.protocol.HttpContext;

public interface ConnectionSocketFactory
{

    public abstract Socket connectSocket(int i, Socket socket, HttpHost httphost, InetSocketAddress inetsocketaddress, InetSocketAddress inetsocketaddress1, HttpContext httpcontext)
        throws IOException;

    public abstract Socket createSocket(HttpContext httpcontext)
        throws IOException;
}
