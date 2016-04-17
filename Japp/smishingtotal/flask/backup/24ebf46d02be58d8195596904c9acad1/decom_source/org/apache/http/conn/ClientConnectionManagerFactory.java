// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.conn:
//            ClientConnectionManager

public interface ClientConnectionManagerFactory
{

    public abstract ClientConnectionManager newInstance(HttpParams httpparams, SchemeRegistry schemeregistry);
}
