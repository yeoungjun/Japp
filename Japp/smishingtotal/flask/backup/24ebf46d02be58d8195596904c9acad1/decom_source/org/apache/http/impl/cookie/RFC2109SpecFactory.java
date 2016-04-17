// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.*;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.impl.cookie:
//            RFC2109Spec

public class RFC2109SpecFactory
    implements CookieSpecFactory, CookieSpecProvider
{

    public RFC2109SpecFactory()
    {
        this(null, false);
    }

    public RFC2109SpecFactory(String as[], boolean flag)
    {
        datepatterns = as;
        oneHeader = flag;
    }

    public CookieSpec create(HttpContext httpcontext)
    {
        return new RFC2109Spec(datepatterns, oneHeader);
    }

    public CookieSpec newInstance(HttpParams httpparams)
    {
        if(httpparams != null)
        {
            Collection collection = (Collection)httpparams.getParameter("http.protocol.cookie-datepatterns");
            String as[] = null;
            if(collection != null)
                as = (String[])collection.toArray(new String[collection.size()]);
            return new RFC2109Spec(as, httpparams.getBooleanParameter("http.protocol.single-cookie-header", false));
        } else
        {
            return new RFC2109Spec();
        }
    }

    private final String datepatterns[];
    private final boolean oneHeader;
}
