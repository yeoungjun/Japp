// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            BasicPoolEntry

public class BasicPoolEntryRef extends WeakReference
{

    public BasicPoolEntryRef(BasicPoolEntry basicpoolentry, ReferenceQueue referencequeue)
    {
        super(basicpoolentry, referencequeue);
        Args.notNull(basicpoolentry, "Pool entry");
        route = basicpoolentry.getPlannedRoute();
    }

    public final HttpRoute getRoute()
    {
        return route;
    }

    private final HttpRoute route;
}
