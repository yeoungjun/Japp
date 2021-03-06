// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import org.apache.http.auth.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.auth:
//            DigestScheme

public class DigestSchemeFactory
    implements AuthSchemeFactory, AuthSchemeProvider
{

    public DigestSchemeFactory()
    {
        this(null);
    }

    public DigestSchemeFactory(Charset charset1)
    {
        charset = charset1;
    }

    public AuthScheme create(HttpContext httpcontext)
    {
        return new DigestScheme(charset);
    }

    public AuthScheme newInstance(HttpParams httpparams)
    {
        return new DigestScheme();
    }

    private final Charset charset;
}
