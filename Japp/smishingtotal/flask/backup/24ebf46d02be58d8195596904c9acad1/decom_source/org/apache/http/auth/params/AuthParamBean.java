// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth.params;

import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.auth.params:
//            AuthParams

public class AuthParamBean extends HttpAbstractParamBean
{

    public AuthParamBean(HttpParams httpparams)
    {
        super(httpparams);
    }

    public void setCredentialCharset(String s)
    {
        AuthParams.setCredentialCharset(params, s);
    }
}
