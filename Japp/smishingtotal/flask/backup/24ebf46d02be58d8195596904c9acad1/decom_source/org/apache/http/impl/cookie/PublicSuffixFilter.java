// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.*;
import org.apache.http.client.utils.Punycode;
import org.apache.http.cookie.*;

public class PublicSuffixFilter
    implements CookieAttributeHandler
{

    public PublicSuffixFilter(CookieAttributeHandler cookieattributehandler)
    {
        wrapped = cookieattributehandler;
    }

    private boolean isForPublicSuffix(Cookie cookie)
    {
        String s1;
        String s = cookie.getDomain();
        if(s.startsWith("."))
            s = s.substring(1);
        s1 = Punycode.toUnicode(s);
        break MISSING_BLOCK_LABEL_27;
_L2:
        do
            return false;
        while(exceptions != null && exceptions.contains(s1) || suffixes == null);
        do
        {
            if(suffixes.contains(s1))
                return true;
            if(s1.startsWith("*."))
                s1 = s1.substring(2);
            int i = s1.indexOf('.');
            if(i == -1)
                continue; /* Loop/switch isn't completed */
            s1 = (new StringBuilder()).append("*").append(s1.substring(i)).toString();
            if(s1.length() <= 0)
                return false;
        } while(true);
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        if(isForPublicSuffix(cookie))
            return false;
        else
            return wrapped.match(cookie, cookieorigin);
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        wrapped.parse(setcookie, s);
    }

    public void setExceptions(Collection collection)
    {
        exceptions = new HashSet(collection);
    }

    public void setPublicSuffixes(Collection collection)
    {
        suffixes = new HashSet(collection);
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        wrapped.validate(cookie, cookieorigin);
    }

    private Set exceptions;
    private Set suffixes;
    private final CookieAttributeHandler wrapped;
}
