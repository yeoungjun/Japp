// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.http.conn.DnsResolver;

public class SystemDefaultDnsResolver
    implements DnsResolver
{

    public SystemDefaultDnsResolver()
    {
    }

    public InetAddress[] resolve(String s)
        throws UnknownHostException
    {
        return InetAddress.getAllByName(s);
    }

    public static final SystemDefaultDnsResolver INSTANCE = new SystemDefaultDnsResolver();

}
