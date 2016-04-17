// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.DnsResolver;
import org.apache.http.util.Args;

public class InMemoryDnsResolver
    implements DnsResolver
{

    public InMemoryDnsResolver()
    {
    }

    public transient void add(String s, InetAddress ainetaddress[])
    {
        Args.notNull(s, "Host name");
        Args.notNull(ainetaddress, "Array of IP addresses");
        dnsMap.put(s, ainetaddress);
    }

    public InetAddress[] resolve(String s)
        throws UnknownHostException
    {
        InetAddress ainetaddress[] = (InetAddress[])dnsMap.get(s);
        if(log.isInfoEnabled())
            log.info((new StringBuilder()).append("Resolving ").append(s).append(" to ").append(Arrays.deepToString(ainetaddress)).toString());
        if(ainetaddress == null)
            throw new UnknownHostException((new StringBuilder()).append(s).append(" cannot be resolved").toString());
        else
            return ainetaddress;
    }

    private final Map dnsMap = new ConcurrentHashMap();
    private final Log log = LogFactory.getLog(org/apache/http/impl/conn/InMemoryDnsResolver);
}
