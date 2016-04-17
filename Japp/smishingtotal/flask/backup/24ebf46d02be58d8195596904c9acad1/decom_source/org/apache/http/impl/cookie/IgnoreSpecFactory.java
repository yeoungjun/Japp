// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import org.apache.http.cookie.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.cookie:
//            IgnoreSpec

public class IgnoreSpecFactory
    implements CookieSpecFactory, CookieSpecProvider
{

    public IgnoreSpecFactory()
    {
    }

    public CookieSpec create(HttpContext httpcontext)
    {
        return new IgnoreSpec();
    }

    public CookieSpec newInstance(HttpParams httpparams)
    {
        return new IgnoreSpec();
    }
}
