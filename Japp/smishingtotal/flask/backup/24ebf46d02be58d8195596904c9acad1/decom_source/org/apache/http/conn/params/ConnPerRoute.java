// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.params;

import org.apache.http.conn.routing.HttpRoute;

public interface ConnPerRoute
{

    public abstract int getMaxForRoute(HttpRoute httproute);
}
