// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.cookie.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.cookie:
//            BasicDomainHandler

public class NetscapeDomainHandler extends BasicDomainHandler
{

    public NetscapeDomainHandler()
    {
    }

    private static boolean isSpecialDomain(String s)
    {
        String s1 = s.toUpperCase(Locale.ENGLISH);
        return s1.endsWith(".COM") || s1.endsWith(".EDU") || s1.endsWith(".NET") || s1.endsWith(".GOV") || s1.endsWith(".MIL") || s1.endsWith(".ORG") || s1.endsWith(".INT");
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        String s = cookieorigin.getHost();
        String s1 = cookie.getDomain();
        if(s1 == null)
            return false;
        else
            return s.endsWith(s1);
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        super.validate(cookie, cookieorigin);
        String s = cookieorigin.getHost();
        String s1 = cookie.getDomain();
        if(s.contains("."))
        {
            int i = (new StringTokenizer(s1, ".")).countTokens();
            if(isSpecialDomain(s1))
            {
                if(i < 2)
                    throw new CookieRestrictionViolationException((new StringBuilder()).append("Domain attribute \"").append(s1).append("\" violates the Netscape cookie specification for ").append("special domains").toString());
            } else
            if(i < 3)
                throw new CookieRestrictionViolationException((new StringBuilder()).append("Domain attribute \"").append(s1).append("\" violates the Netscape cookie specification").toString());
        }
    }
}
