// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth.params;

import java.nio.charset.Charset;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

public final class AuthParams
{

    private AuthParams()
    {
    }

    public static String getCredentialCharset(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        String s = (String)httpparams.getParameter("http.auth.credential-charset");
        if(s == null)
            s = HTTP.DEF_PROTOCOL_CHARSET.name();
        return s;
    }

    public static void setCredentialCharset(HttpParams httpparams, String s)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setParameter("http.auth.credential-charset", s);
    }
}
