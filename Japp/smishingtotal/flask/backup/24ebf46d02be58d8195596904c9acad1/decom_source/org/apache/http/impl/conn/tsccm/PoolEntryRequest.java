// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ConnectionPoolTimeoutException;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            BasicPoolEntry

public interface PoolEntryRequest
{

    public abstract void abortRequest();

    public abstract BasicPoolEntry getPoolEntry(long l, TimeUnit timeunit)
        throws InterruptedException, ConnectionPoolTimeoutException;
}
