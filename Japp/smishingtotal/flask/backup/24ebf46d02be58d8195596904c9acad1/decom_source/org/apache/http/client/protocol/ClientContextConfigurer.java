// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.protocol:
//            ClientContext

public class ClientContextConfigurer
    implements ClientContext
{

    public ClientContextConfigurer(HttpContext httpcontext)
    {
        Args.notNull(httpcontext, "HTTP context");
        context = httpcontext;
    }

    public void setAuthSchemeRegistry(AuthSchemeRegistry authschemeregistry)
    {
        context.setAttribute("http.authscheme-registry", authschemeregistry);
    }

    public void setCookieSpecRegistry(CookieSpecRegistry cookiespecregistry)
    {
        context.setAttribute("http.cookiespec-registry", cookiespecregistry);
    }

    public void setCookieStore(CookieStore cookiestore)
    {
        context.setAttribute("http.cookie-store", cookiestore);
    }

    public void setCredentialsProvider(CredentialsProvider credentialsprovider)
    {
        context.setAttribute("http.auth.credentials-provider", credentialsprovider);
    }

    private final HttpContext context;
}
