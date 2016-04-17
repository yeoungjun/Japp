// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.cookie:
//            AbstractCookieAttributeHandler

public class BasicSecureHandler extends AbstractCookieAttributeHandler
{

    public BasicSecureHandler()
    {
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        return !cookie.isSecure() || cookieorigin.isSecure();
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        setcookie.setSecure(true);
    }
}
