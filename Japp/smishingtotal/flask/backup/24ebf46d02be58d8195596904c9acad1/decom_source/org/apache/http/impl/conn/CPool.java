// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.*;

// Referenced classes of package org.apache.http.impl.conn:
//            CPoolEntry

class CPool extends AbstractConnPool
{

    public CPool(ConnFactory connfactory, int i, int j, long l, TimeUnit timeunit)
    {
        super(connfactory, i, j);
        timeToLive = l;
        tunit = timeunit;
    }

    protected CPoolEntry createEntry(HttpRoute httproute, ManagedHttpClientConnection managedhttpclientconnection)
    {
        String s = Long.toString(COUNTER.getAndIncrement());
        return new CPoolEntry(log, s, httproute, managedhttpclientconnection, timeToLive, tunit);
    }

    protected volatile PoolEntry createEntry(Object obj, Object obj1)
    {
        return createEntry((HttpRoute)obj, (ManagedHttpClientConnection)obj1);
    }

    private static final AtomicLong COUNTER = new AtomicLong();
    private final Log log = LogFactory.getLog(org/apache/http/impl/conn/CPool);
    private final long timeToLive;
    private final TimeUnit tunit;

}
