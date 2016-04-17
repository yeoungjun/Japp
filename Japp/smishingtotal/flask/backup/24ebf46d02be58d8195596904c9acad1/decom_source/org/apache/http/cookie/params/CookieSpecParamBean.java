// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.cookie.params;

import java.util.Collection;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

public class CookieSpecParamBean extends HttpAbstractParamBean
{

    public CookieSpecParamBean(HttpParams httpparams)
    {
        super(httpparams);
    }

    public void setDatePatterns(Collection collection)
    {
        params.setParameter("http.protocol.cookie-datepatterns", collection);
    }

    public void setSingleHeader(boolean flag)
    {
        params.setBooleanParameter("http.protocol.single-cookie-header", flag);
    }
}
