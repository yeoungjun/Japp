// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.params;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

public class HttpClientParams
{

    private HttpClientParams()
    {
    }

    public static long getConnectionManagerTimeout(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        Long long1 = (Long)httpparams.getParameter("http.conn-manager.timeout");
        if(long1 != null)
            return long1.longValue();
        else
            return (long)HttpConnectionParams.getConnectionTimeout(httpparams);
    }

    public static String getCookiePolicy(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        String s = (String)httpparams.getParameter("http.protocol.cookie-policy");
        if(s == null)
            s = "best-match";
        return s;
    }

    public static boolean isAuthenticating(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        return httpparams.getBooleanParameter("http.protocol.handle-authentication", true);
    }

    public static boolean isRedirecting(HttpParams httpparams)
    {
        Args.notNull(httpparams, "HTTP parameters");
        return httpparams.getBooleanParameter("http.protocol.handle-redirects", true);
    }

    public static void setAuthenticating(HttpParams httpparams, boolean flag)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setBooleanParameter("http.protocol.handle-authentication", flag);
    }

    public static void setConnectionManagerTimeout(HttpParams httpparams, long l)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setLongParameter("http.conn-manager.timeout", l);
    }

    public static void setCookiePolicy(HttpParams httpparams, String s)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setParameter("http.protocol.cookie-policy", s);
    }

    public static void setRedirecting(HttpParams httpparams, boolean flag)
    {
        Args.notNull(httpparams, "HTTP parameters");
        httpparams.setBooleanParameter("http.protocol.handle-redirects", flag);
    }
}
