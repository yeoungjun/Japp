// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.cookie.*;
import org.apache.http.util.Args;

public class RFC2965DomainAttributeHandler
    implements CookieAttributeHandler
{

    public RFC2965DomainAttributeHandler()
    {
    }

    public boolean domainMatch(String s, String s1)
    {
        return s.equals(s1) || s1.startsWith(".") && s.endsWith(s1);
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        String s = cookieorigin.getHost().toLowerCase(Locale.ENGLISH);
        for(String s1 = cookie.getDomain(); !domainMatch(s, s1) || s.substring(0, s.length() - s1.length()).indexOf('.') != -1;)
            return false;

        return true;
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for domain attribute");
        if(s.trim().length() == 0)
            throw new MalformedCookieException("Blank value for domain attribute");
        String s1 = s.toLowerCase(Locale.ENGLISH);
        if(!s.startsWith("."))
            s1 = (new StringBuilder()).append('.').append(s1).toString();
        setcookie.setDomain(s1);
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        String s = cookieorigin.getHost().toLowerCase(Locale.ENGLISH);
        if(cookie.getDomain() == null)
            throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
        String s1 = cookie.getDomain().toLowerCase(Locale.ENGLISH);
        if((cookie instanceof ClientCookie) && ((ClientCookie)cookie).containsAttribute("domain"))
        {
            if(!s1.startsWith("."))
                throw new CookieRestrictionViolationException((new StringBuilder()).append("Domain attribute \"").append(cookie.getDomain()).append("\" violates RFC 2109: domain must start with a dot").toString());
            int i = s1.indexOf('.', 1);
            if((i < 0 || i == -1 + s1.length()) && !s1.equals(".local"))
                throw new CookieRestrictionViolationException((new StringBuilder()).append("Domain attribute \"").append(cookie.getDomain()).append("\" violates RFC 2965: the value contains no embedded dots ").append("and the value is not .local").toString());
            if(!domainMatch(s, s1))
                throw new CookieRestrictionViolationException((new StringBuilder()).append("Domain attribute \"").append(cookie.getDomain()).append("\" violates RFC 2965: effective host name does not ").append("domain-match domain attribute.").toString());
            if(s.substring(0, s.length() - s1.length()).indexOf('.') != -1)
                throw new CookieRestrictionViolationException((new StringBuilder()).append("Domain attribute \"").append(cookie.getDomain()).append("\" violates RFC 2965: ").append("effective host minus domain may not contain any dots").toString());
        } else
        if(!cookie.getDomain().equals(s))
            throw new CookieRestrictionViolationException((new StringBuilder()).append("Illegal domain attribute: \"").append(cookie.getDomain()).append("\".").append("Domain of origin: \"").append(s).append("\"").toString());
    }
}
