// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import org.apache.http.util.Args;

public class BasicDomainHandler
    implements CookieAttributeHandler
{

    public BasicDomainHandler()
    {
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        String s = cookieorigin.getHost();
        String s1 = cookie.getDomain();
        if(s1 != null)
        {
            if(s.equals(s1))
                return true;
            if(!s1.startsWith("."))
                s1 = (new StringBuilder()).append('.').append(s1).toString();
            if(s.endsWith(s1) || s.equals(s1.substring(1)))
                return true;
        }
        return false;
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for domain attribute");
        if(s.trim().length() == 0)
        {
            throw new MalformedCookieException("Blank value for domain attribute");
        } else
        {
            setcookie.setDomain(s);
            return;
        }
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        String s = cookieorigin.getHost();
        String s1 = cookie.getDomain();
        if(s1 == null)
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
        if(s.contains("."))
        {
            if(!s.endsWith(s1))
            {
                if(s1.startsWith("."))
                    s1 = s1.substring(1, s1.length());
                if(!s.equals(s1))
                    throw new CookieRestrictionViolationException((new StringBuilder()).append("Illegal domain attribute \"").append(s1).append("\". Domain of origin: \"").append(s).append("\"").toString());
            }
        } else
        if(!s.equals(s1))
            throw new CookieRestrictionViolationException((new StringBuilder()).append("Illegal domain attribute \"").append(s1).append("\". Domain of origin: \"").append(s).append("\"").toString());
    }
}
