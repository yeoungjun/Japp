// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            BasicPoolEntryRef

public class BasicPoolEntry extends AbstractPoolEntry
{

    public BasicPoolEntry(ClientConnectionOperator clientconnectionoperator, HttpRoute httproute)
    {
        this(clientconnectionoperator, httproute, -1L, TimeUnit.MILLISECONDS);
    }

    public BasicPoolEntry(ClientConnectionOperator clientconnectionoperator, HttpRoute httproute, long l, TimeUnit timeunit)
    {
        super(clientconnectionoperator, httproute);
        Args.notNull(httproute, "HTTP route");
        created = System.currentTimeMillis();
        if(l > 0L)
            validUntil = created + timeunit.toMillis(l);
        else
            validUntil = 0x7fffffffffffffffL;
        expiry = validUntil;
    }

    public BasicPoolEntry(ClientConnectionOperator clientconnectionoperator, HttpRoute httproute, ReferenceQueue referencequeue)
    {
        super(clientconnectionoperator, httproute);
        Args.notNull(httproute, "HTTP route");
        created = System.currentTimeMillis();
        validUntil = 0x7fffffffffffffffL;
        expiry = validUntil;
    }

    protected final OperatedClientConnection getConnection()
    {
        return super.connection;
    }

    public long getCreated()
    {
        return created;
    }

    public long getExpiry()
    {
        return expiry;
    }

    protected final HttpRoute getPlannedRoute()
    {
        return super.route;
    }

    public long getUpdated()
    {
        return updated;
    }

    public long getValidUntil()
    {
        return validUntil;
    }

    protected final BasicPoolEntryRef getWeakRef()
    {
        return null;
    }

    public boolean isExpired(long l)
    {
        return l >= expiry;
    }

    protected void shutdownEntry()
    {
        super.shutdownEntry();
    }

    public void updateExpiry(long l, TimeUnit timeunit)
    {
        updated = System.currentTimeMillis();
        long l1;
        if(l > 0L)
            l1 = updated + timeunit.toMillis(l);
        else
            l1 = 0x7fffffffffffffffL;
        expiry = Math.min(validUntil, l1);
    }

    private final long created;
    private long expiry;
    private long updated;
    private final long validUntil;
}
