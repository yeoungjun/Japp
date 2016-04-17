// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.cookie:
//            AbstractCookieAttributeHandler

public class BrowserCompatVersionAttributeHandler extends AbstractCookieAttributeHandler
{

    public BrowserCompatVersionAttributeHandler()
    {
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for version attribute");
        int j = Integer.parseInt(s);
        int i = j;
_L2:
        setcookie.setVersion(i);
        return;
        NumberFormatException numberformatexception;
        numberformatexception;
        i = 0;
        if(true) goto _L2; else goto _L1
_L1:
    }
}
