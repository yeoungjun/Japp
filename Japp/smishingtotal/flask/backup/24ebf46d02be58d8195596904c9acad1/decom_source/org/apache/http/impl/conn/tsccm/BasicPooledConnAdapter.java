// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.AbstractPoolEntry;
import org.apache.http.impl.conn.AbstractPooledConnAdapter;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            ThreadSafeClientConnManager

public class BasicPooledConnAdapter extends AbstractPooledConnAdapter
{

    protected BasicPooledConnAdapter(ThreadSafeClientConnManager threadsafeclientconnmanager, AbstractPoolEntry abstractpoolentry)
    {
        super(threadsafeclientconnmanager, abstractpoolentry);
        markReusable();
    }

    protected void detach()
    {
        super.detach();
    }

    protected ClientConnectionManager getManager()
    {
        return super.getManager();
    }

    protected AbstractPoolEntry getPoolEntry()
    {
        return super.getPoolEntry();
    }
}
