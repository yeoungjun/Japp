// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.http.auth.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.auth:
//            KerberosScheme

public class KerberosSchemeFactory
    implements AuthSchemeFactory, AuthSchemeProvider
{

    public KerberosSchemeFactory()
    {
        this(false);
    }

    public KerberosSchemeFactory(boolean flag)
    {
        stripPort = flag;
    }

    public AuthScheme create(HttpContext httpcontext)
    {
        return new KerberosScheme(stripPort);
    }

    public boolean isStripPort()
    {
        return stripPort;
    }

    public AuthScheme newInstance(HttpParams httpparams)
    {
        return new KerberosScheme(stripPort);
    }

    private final boolean stripPort;
}
