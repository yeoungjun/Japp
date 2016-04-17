// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie;

import java.util.Locale;
import org.apache.http.util.Args;

public final class CookieOrigin
{

    public CookieOrigin(String s, int i, String s1, boolean flag)
    {
        Args.notBlank(s, "Host");
        Args.notNegative(i, "Port");
        Args.notNull(s1, "Path");
        host = s.toLowerCase(Locale.ENGLISH);
        port = i;
        if(s1.trim().length() != 0)
            path = s1;
        else
            path = "/";
        secure = flag;
    }

    public String getHost()
    {
        return host;
    }

    public String getPath()
    {
        return path;
    }

    public int getPort()
    {
        return port;
    }

    public boolean isSecure()
    {
        return secure;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append('[');
        if(secure)
            stringbuilder.append("(secure)");
        stringbuilder.append(host);
        stringbuilder.append(':');
        stringbuilder.append(Integer.toString(port));
        stringbuilder.append(path);
        stringbuilder.append(']');
        return stringbuilder.toString();
    }

    private final String host;
    private final String path;
    private final int port;
    private final boolean secure;
}
