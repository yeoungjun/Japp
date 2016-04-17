// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import org.apache.http.util.Args;

public class RFC2965VersionAttributeHandler
    implements CookieAttributeHandler
{

    public RFC2965VersionAttributeHandler()
    {
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        return true;
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for version attribute");
        int j = Integer.parseInt(s);
        int i = j;
_L1:
        NumberFormatException numberformatexception;
        if(i < 0)
        {
            throw new MalformedCookieException("Invalid cookie version.");
        } else
        {
            setcookie.setVersion(i);
            return;
        }
        numberformatexception;
        i = -1;
          goto _L1
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(cookie, "Cookie");
        if((cookie instanceof SetCookie2) && (cookie instanceof ClientCookie) && !((ClientCookie)cookie).containsAttribute("version"))
            throw new CookieRestrictionViolationException("Violates RFC 2965. Version attribute is required.");
        else
            return;
    }
}
