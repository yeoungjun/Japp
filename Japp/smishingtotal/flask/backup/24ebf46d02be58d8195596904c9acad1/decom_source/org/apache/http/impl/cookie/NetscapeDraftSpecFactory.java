// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.cookie:
//            NetscapeDraftSpec

public class NetscapeDraftSpecFactory
    implements CookieSpecFactory, CookieSpecProvider
{

    public NetscapeDraftSpecFactory()
    {
        this(null);
    }

    public NetscapeDraftSpecFactory(String as[])
    {
        datepatterns = as;
    }

    public CookieSpec create(HttpContext httpcontext)
    {
        return new NetscapeDraftSpec(datepatterns);
    }

    public CookieSpec newInstance(HttpParams httpparams)
    {
        if(httpparams != null)
        {
            Collection collection = (Collection)httpparams.getParameter("http.protocol.cookie-datepatterns");
            String as[] = null;
            if(collection != null)
                as = (String[])collection.toArray(new String[collection.size()]);
            return new NetscapeDraftSpec(as);
        } else
        {
            return new NetscapeDraftSpec();
        }
    }

    private final String datepatterns[];
}
