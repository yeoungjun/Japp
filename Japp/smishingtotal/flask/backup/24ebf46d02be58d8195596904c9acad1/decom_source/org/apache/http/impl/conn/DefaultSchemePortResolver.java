// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import org.apache.http.HttpHost;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.util.Args;

public class DefaultSchemePortResolver
    implements SchemePortResolver
{

    public DefaultSchemePortResolver()
    {
    }

    public int resolve(HttpHost httphost)
        throws UnsupportedSchemeException
    {
        Args.notNull(httphost, "HTTP host");
        int i = httphost.getPort();
        if(i > 0)
            return i;
        String s = httphost.getSchemeName();
        if(s.equalsIgnoreCase("http"))
            return 80;
        if(s.equalsIgnoreCase("https"))
            return 443;
        else
            throw new UnsupportedSchemeException((new StringBuilder()).append(s).append(" protocol is not supported").toString());
    }

    public static final DefaultSchemePortResolver INSTANCE = new DefaultSchemePortResolver();

}
