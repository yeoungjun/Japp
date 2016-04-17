// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.apache.http.HttpHost;
import org.apache.http.util.Args;

public class HttpInetSocketAddress extends InetSocketAddress
{

    public HttpInetSocketAddress(HttpHost httphost1, InetAddress inetaddress, int i)
    {
        super(inetaddress, i);
        Args.notNull(httphost1, "HTTP host");
        httphost = httphost1;
    }

    public HttpHost getHttpHost()
    {
        return httphost;
    }

    public String toString()
    {
        return (new StringBuilder()).append(httphost.getHostName()).append(":").append(getPort()).toString();
    }

    private static final long serialVersionUID = 0xa3b3f5ec8d72910bL;
    private final HttpHost httphost;
}
