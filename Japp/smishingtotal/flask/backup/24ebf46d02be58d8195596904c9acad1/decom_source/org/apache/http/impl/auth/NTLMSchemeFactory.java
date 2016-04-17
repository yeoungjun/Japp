// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.http.auth.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.auth:
//            NTLMScheme

public class NTLMSchemeFactory
    implements AuthSchemeFactory, AuthSchemeProvider
{

    public NTLMSchemeFactory()
    {
    }

    public AuthScheme create(HttpContext httpcontext)
    {
        return new NTLMScheme();
    }

    public AuthScheme newInstance(HttpParams httpparams)
    {
        return new NTLMScheme();
    }
}
