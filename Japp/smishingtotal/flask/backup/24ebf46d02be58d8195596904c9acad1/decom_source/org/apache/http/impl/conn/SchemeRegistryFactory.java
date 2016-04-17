// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.SSLSocketFactory;

public final class SchemeRegistryFactory
{

    public SchemeRegistryFactory()
    {
    }

    public static SchemeRegistry createDefault()
    {
        SchemeRegistry schemeregistry = new SchemeRegistry();
        schemeregistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeregistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        return schemeregistry;
    }

    public static SchemeRegistry createSystemDefault()
    {
        SchemeRegistry schemeregistry = new SchemeRegistry();
        schemeregistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeregistry.register(new Scheme("https", 443, SSLSocketFactory.getSystemSocketFactory()));
        return schemeregistry;
    }
}
