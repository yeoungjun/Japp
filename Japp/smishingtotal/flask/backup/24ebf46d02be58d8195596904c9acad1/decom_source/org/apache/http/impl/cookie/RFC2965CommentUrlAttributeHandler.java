// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;

public class RFC2965CommentUrlAttributeHandler
    implements CookieAttributeHandler
{

    public RFC2965CommentUrlAttributeHandler()
    {
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        return true;
    }

    public void parse(SetCookie setcookie, String s)
        throws MalformedCookieException
    {
        if(setcookie instanceof SetCookie2)
            ((SetCookie2)setcookie).setCommentURL(s);
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
    }
}
