// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie;


// Referenced classes of package org.apache.http.cookie:
//            MalformedCookieException

public class CookieRestrictionViolationException extends MalformedCookieException
{

    public CookieRestrictionViolationException()
    {
    }

    public CookieRestrictionViolationException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0x664be3b3ef7da655L;
}
