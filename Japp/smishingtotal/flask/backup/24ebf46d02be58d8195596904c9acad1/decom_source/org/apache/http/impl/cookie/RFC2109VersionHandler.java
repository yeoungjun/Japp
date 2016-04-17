// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.cookie:
//            AbstractCookieAttributeHandler

public class RFC2109VersionHandler extends AbstractCookieAttributeHandler
{

    public RFC2109VersionHandler()
    {
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for version attribute");
        if(s.trim().length() == 0)
            throw new MalformedCookieException("Blank value for version attribute");
        try
        {
            setcookie.setVersion(Integer.parseInt(s));
            return;
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new MalformedCookieException((new StringBuilder()).append("Invalid version: ").append(numberformatexception.getMessage()).toString());
        }
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(cookie, "Cookie");
        if(cookie.getVersion() < 0)
            throw new CookieRestrictionViolationException("Cookie version may not be negative");
        else
            return;
    }
}
