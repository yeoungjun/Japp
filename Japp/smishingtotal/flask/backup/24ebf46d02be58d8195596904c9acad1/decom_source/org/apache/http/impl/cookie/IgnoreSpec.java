// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Collections;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;

// Referenced classes of package org.apache.http.impl.cookie:
//            CookieSpecBase

public class IgnoreSpec extends CookieSpecBase
{

    public IgnoreSpec()
    {
    }

    public List formatCookies(List list)
    {
        return Collections.emptyList();
    }

    public int getVersion()
    {
        return 0;
    }

    public Header getVersionHeader()
    {
        return null;
    }

    public List parse(Header header, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        return Collections.emptyList();
    }
}
