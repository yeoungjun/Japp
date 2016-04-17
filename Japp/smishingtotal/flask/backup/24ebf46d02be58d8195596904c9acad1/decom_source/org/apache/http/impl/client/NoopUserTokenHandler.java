// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.client.UserTokenHandler;
import org.apache.http.protocol.HttpContext;

public class NoopUserTokenHandler
    implements UserTokenHandler
{

    public NoopUserTokenHandler()
    {
    }

    public Object getUserToken(HttpContext httpcontext)
    {
        return null;
    }

    public static final NoopUserTokenHandler INSTANCE = new NoopUserTokenHandler();

}
