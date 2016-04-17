// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import org.apache.http.protocol.HttpContext;

// Referenced classes of package org.apache.http.auth:
//            AuthScheme

public interface AuthSchemeProvider
{

    public abstract AuthScheme create(HttpContext httpcontext);
}
