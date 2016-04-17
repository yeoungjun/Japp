// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.cookie:
//            AbstractCookieAttributeHandler

public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler
{

    public BasicMaxAgeHandler()
    {
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for max-age attribute");
        int i;
        try
        {
            i = Integer.parseInt(s);
        }
        catch(NumberFormatException numberformatexception)
        {
            throw new MalformedCookieException((new StringBuilder()).append("Invalid max-age attribute: ").append(s).toString());
        }
        if(i < 0)
        {
            throw new MalformedCookieException((new StringBuilder()).append("Negative max-age attribute: ").append(s).toString());
        } else
        {
            setcookie.setExpiryDate(new Date(System.currentTimeMillis() + 1000L * (long)i));
            return;
        }
    }
}
