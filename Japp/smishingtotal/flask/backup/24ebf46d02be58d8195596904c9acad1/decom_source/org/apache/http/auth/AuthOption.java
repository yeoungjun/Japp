// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.auth:
//            AuthScheme, Credentials

public final class AuthOption
{

    public AuthOption(AuthScheme authscheme, Credentials credentials)
    {
        Args.notNull(authscheme, "Auth scheme");
        Args.notNull(credentials, "User credentials");
        authScheme = authscheme;
        creds = credentials;
    }

    public AuthScheme getAuthScheme()
    {
        return authScheme;
    }

    public Credentials getCredentials()
    {
        return creds;
    }

    public String toString()
    {
        return authScheme.toString();
    }

    private final AuthScheme authScheme;
    private final Credentials creds;
}
