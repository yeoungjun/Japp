// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie;

import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.cookie:
//            CookieSpec

public interface CookieSpecProvider
{

    public abstract CookieSpec create(HttpContext httpcontext);
}
