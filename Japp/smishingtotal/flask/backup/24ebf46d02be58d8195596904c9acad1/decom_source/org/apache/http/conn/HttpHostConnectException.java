// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Arrays;
import org.apache.http.HttpHost;

public class HttpHostConnectException extends ConnectException
{

    public transient HttpHostConnectException(IOException ioexception, HttpHost httphost, InetAddress ainetaddress[])
    {
        StringBuilder stringbuilder = (new StringBuilder()).append("Connect to ");
        String s;
        StringBuilder stringbuilder1;
        String s1;
        StringBuilder stringbuilder2;
        String s2;
        if(httphost != null)
            s = httphost.toHostString();
        else
            s = "remote host";
        stringbuilder1 = stringbuilder.append(s);
        if(ainetaddress != null && ainetaddress.length > 0)
            s1 = (new StringBuilder()).append(" ").append(Arrays.asList(ainetaddress)).toString();
        else
            s1 = "";
        stringbuilder2 = stringbuilder1.append(s1);
        if(ioexception != null && ioexception.getMessage() != null)
            s2 = (new StringBuilder()).append(" failed: ").append(ioexception.getMessage()).toString();
        else
            s2 = " refused";
        super(stringbuilder2.append(s2).toString());
        host = httphost;
        initCause(ioexception);
    }

    public HttpHostConnectException(HttpHost httphost, ConnectException connectexception)
    {
        this(((IOException) (connectexception)), httphost, null);
    }

    public HttpHost getHost()
    {
        return host;
    }

    private static final long serialVersionUID = 0xd3aaeae66ceb1d00L;
    private final HttpHost host;
}
