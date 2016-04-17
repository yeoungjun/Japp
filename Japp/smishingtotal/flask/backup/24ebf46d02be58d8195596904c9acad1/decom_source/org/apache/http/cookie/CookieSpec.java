// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie;

import java.util.List;
import org.apache.http.Header;

// Referenced classes of package org.apache.http.cookie:
//            MalformedCookieException, Cookie, CookieOrigin

public interface CookieSpec
{

    public abstract List formatCookies(List list);

    public abstract int getVersion();

    public abstract Header getVersionHeader();

    public abstract boolean match(Cookie cookie, CookieOrigin cookieorigin);

    public abstract List parse(Header header, CookieOrigin cookieorigin)
        throws MalformedCookieException;

    public abstract void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException;
}
