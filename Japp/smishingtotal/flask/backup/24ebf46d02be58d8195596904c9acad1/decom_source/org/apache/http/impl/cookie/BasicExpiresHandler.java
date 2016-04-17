// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.client.utils.DateUtils;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.cookie:
//            AbstractCookieAttributeHandler

public class BasicExpiresHandler extends AbstractCookieAttributeHandler
{

    public BasicExpiresHandler(String as[])
    {
        Args.notNull(as, "Array of date patterns");
        datepatterns = as;
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        Args.notNull(setcookie, "Cookie");
        if(s == null)
            throw new MalformedCookieException("Missing value for expires attribute");
        java.util.Date date = DateUtils.parseDate(s, datepatterns);
        if(date == null)
        {
            throw new MalformedCookieException((new StringBuilder()).append("Unable to parse expires attribute: ").append(s).toString());
        } else
        {
            setcookie.setExpiryDate(date);
            return;
        }
    }

    private final String datepatterns[];
}
