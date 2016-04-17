// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.impl.auth:
//            NegotiateScheme, SpnegoTokenGenerator

public class NegotiateSchemeFactory
    implements AuthSchemeFactory
{

    public NegotiateSchemeFactory()
    {
        this(null, false);
    }

    public NegotiateSchemeFactory(SpnegoTokenGenerator spnegotokengenerator)
    {
        this(spnegotokengenerator, false);
    }

    public NegotiateSchemeFactory(SpnegoTokenGenerator spnegotokengenerator, boolean flag)
    {
        spengoGenerator = spnegotokengenerator;
        stripPort = flag;
    }

    public SpnegoTokenGenerator getSpengoGenerator()
    {
        return spengoGenerator;
    }

    public boolean isStripPort()
    {
        return stripPort;
    }

    public AuthScheme newInstance(HttpParams httpparams)
    {
        return new NegotiateScheme(spengoGenerator, stripPort);
    }

    private final SpnegoTokenGenerator spengoGenerator;
    private final boolean stripPort;
}
