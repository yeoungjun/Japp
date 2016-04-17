// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public class NoConnectionReuseStrategy
    implements ConnectionReuseStrategy
{

    public NoConnectionReuseStrategy()
    {
    }

    public boolean keepAlive(HttpResponse httpresponse, HttpContext httpcontext)
    {
        return false;
    }

    public static final NoConnectionReuseStrategy INSTANCE = new NoConnectionReuseStrategy();

}
