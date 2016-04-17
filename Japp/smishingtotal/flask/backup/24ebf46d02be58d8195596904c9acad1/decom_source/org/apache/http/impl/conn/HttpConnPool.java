// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.*;

// Referenced classes of package org.apache.http.impl.conn:
//            HttpPoolEntry

class HttpConnPool extends AbstractConnPool
{
    static class InternalConnFactory
        implements ConnFactory
    {

        public volatile Object create(Object obj)
            throws IOException
        {
            return create((HttpRoute)obj);
        }

        public OperatedClientConnection create(HttpRoute httproute)
            throws IOException
        {
            return connOperator.createConnection();
        }

        private final ClientConnectionOperator connOperator;

        InternalConnFactory(ClientConnectionOperator clientconnectionoperator)
        {
            connOperator = clientconnectionoperator;
        }
    }


    public HttpConnPool(Log log1, ClientConnectionOperator clientconnectionoperator, int i, int j, long l, TimeUnit timeunit)
    {
        super(new InternalConnFactory(clientconnectionoperator), i, j);
        log = log1;
        timeToLive = l;
        tunit = timeunit;
    }

    protected HttpPoolEntry createEntry(HttpRoute httproute, OperatedClientConnection operatedclientconnection)
    {
        String s = Long.toString(COUNTER.getAndIncrement());
        return new HttpPoolEntry(log, s, httproute, operatedclientconnection, timeToLive, tunit);
    }

    protected volatile PoolEntry createEntry(Object obj, Object obj1)
    {
        return createEntry((HttpRoute)obj, (OperatedClientConnection)obj1);
    }

    private static final AtomicLong COUNTER = new AtomicLong();
    private final Log log;
    private final long timeToLive;
    private final TimeUnit tunit;

}
