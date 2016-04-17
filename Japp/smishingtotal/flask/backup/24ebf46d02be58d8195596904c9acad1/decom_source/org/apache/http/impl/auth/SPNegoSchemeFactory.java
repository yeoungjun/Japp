// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.http.auth.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.auth:
//            SPNegoScheme

public class SPNegoSchemeFactory
    implements AuthSchemeFactory, AuthSchemeProvider
{

    public SPNegoSchemeFactory()
    {
        this(false);
    }

    public SPNegoSchemeFactory(boolean flag)
    {
        stripPort = flag;
    }

    public AuthScheme create(HttpContext httpcontext)
    {
        return new SPNegoScheme(stripPort);
    }

    public boolean isStripPort()
    {
        return stripPort;
    }

    public AuthScheme newInstance(HttpParams httpparams)
    {
        return new SPNegoScheme(stripPort);
    }

    private final boolean stripPort;
}
