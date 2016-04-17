// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.ConnectionBackoffStrategy;

public class NullBackoffStrategy
    implements ConnectionBackoffStrategy
{

    public NullBackoffStrategy()
    {
    }

    public boolean shouldBackoff(Throwable throwable)
    {
        return false;
    }

    public boolean shouldBackoff(HttpResponse httpresponse)
    {
        return false;
    }
}
