// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.pool;

import java.io.IOException;

public interface ConnFactory
{

    public abstract Object create(Object obj)
        throws IOException;
}
