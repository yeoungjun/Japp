// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.pool;


// Referenced classes of package org.apache.http.pool:
//            PoolEntry

public interface PoolEntryCallback
{

    public abstract void process(PoolEntry poolentry);
}
