// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class BasicPathHandler
    implements CookieAttributeHandler
{

    public BasicPathHandler()
    {
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
label0:
        {
            Args.notNull(cookie, "Cookie");
            Args.notNull(cookieorigin, "Cookie origin");
            String s = cookieorigin.getPath();
            String s1 = cookie.getPath();
            if(s1 == null)
                s1 = "/";
            if(s1.length() > 1 && s1.endsWith("/"))
                s1 = s1.substring(0, -1 + s1.length());
            boolean flag = s.startsWith(s1);
            if(flag && s.length() != s1.length() && !s1.endsWith("/"))
            {
                if(s.charAt(s1.length()) != '/')
                    break label0;
                flag = true;
            }
            return flag;
        }
        return false;
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(TextUtils.isBlank(s))
            s = "/";
        setcookie.setPath(s);
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        if(!match(cookie, cookieorigin))
            throw new CookieRestrictionViolationException((new StringBuilder()).append("Illegal path attribute \"").append(cookie.getPath()).append("\". Path of origin: \"").append(cookieorigin.getPath()).append("\"").toString());
        else
            return;
    }
}
