// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import java.util.Locale;
import org.apache.http.HttpHost;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

public class AuthScope
{

    public AuthScope(String s, int i)
    {
        this(s, i, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(String s, int i, String s1)
    {
        this(s, i, s1, ANY_SCHEME);
    }

    public AuthScope(String s, int i, String s1, String s2)
    {
        String s3;
        String s4;
        if(s == null)
            s3 = ANY_HOST;
        else
            s3 = s.toLowerCase(Locale.ENGLISH);
        host = s3;
        if(i < 0)
            i = -1;
        port = i;
        if(s1 == null)
            s1 = ANY_REALM;
        realm = s1;
        if(s2 == null)
            s4 = ANY_SCHEME;
        else
            s4 = s2.toUpperCase(Locale.ENGLISH);
        scheme = s4;
    }

    public AuthScope(HttpHost httphost)
    {
        this(httphost, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(HttpHost httphost, String s, String s1)
    {
        this(httphost.getHostName(), httphost.getPort(), s, s1);
    }

    public AuthScope(AuthScope authscope)
    {
        Args.notNull(authscope, "Scope");
        host = authscope.getHost();
        port = authscope.getPort();
        realm = authscope.getRealm();
        scheme = authscope.getScheme();
    }

    public boolean equals(Object obj)
    {
        boolean flag = true;
        if(obj == null)
            flag = false;
        else
        if(obj != this)
        {
            if(!(obj instanceof AuthScope))
                return super.equals(obj);
            AuthScope authscope = (AuthScope)obj;
            if(!LangUtils.equals(host, authscope.host) || port != authscope.port || !LangUtils.equals(realm, authscope.realm) || !LangUtils.equals(scheme, authscope.scheme))
                return false;
        }
        return flag;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public String getRealm()
    {
        return realm;
    }

    public String getScheme()
    {
        return scheme;
    }

    public int hashCode()
    {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, host), port), realm), scheme);
    }

    public int match(AuthScope authscope)
    {
        int i;
        if(LangUtils.equals(scheme, authscope.scheme))
        {
            i = 0 + 1;
        } else
        {
            String s = scheme;
            String s1 = ANY_SCHEME;
            i = 0;
            if(s != s1)
            {
                String s2 = authscope.scheme;
                String s3 = ANY_SCHEME;
                i = 0;
                if(s2 != s3)
                    return -1;
            }
        }
        if(LangUtils.equals(realm, authscope.realm))
            i += 2;
        else
        if(realm != ANY_REALM && authscope.realm != ANY_REALM)
            return -1;
        if(port == authscope.port)
            i += 4;
        else
        if(port != -1 && authscope.port != -1)
            return -1;
        if(LangUtils.equals(host, authscope.host))
            i += 8;
        else
        if(host != ANY_HOST && authscope.host != ANY_HOST)
            return -1;
        return i;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        if(scheme != null)
        {
            stringbuilder.append(scheme.toUpperCase(Locale.ENGLISH));
            stringbuilder.append(' ');
        }
        if(realm != null)
        {
            stringbuilder.append('\'');
            stringbuilder.append(realm);
            stringbuilder.append('\'');
        } else
        {
            stringbuilder.append("<any realm>");
        }
        if(host != null)
        {
            stringbuilder.append('@');
            stringbuilder.append(host);
            if(port >= 0)
            {
                stringbuilder.append(':');
                stringbuilder.append(port);
            }
        }
        return stringbuilder.toString();
    }

    public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
    public static final String ANY_HOST = null;
    public static final int ANY_PORT = -1;
    public static final String ANY_REALM = null;
    public static final String ANY_SCHEME = null;
    private final String host;
    private final int port;
    private final String realm;
    private final String scheme;

}
