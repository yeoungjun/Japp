// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.BackoffManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.ConnPoolControl;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            SystemClock, Clock

public class AIMDBackoffManager
    implements BackoffManager
{

    public AIMDBackoffManager(ConnPoolControl connpoolcontrol)
    {
        this(connpoolcontrol, ((Clock) (new SystemClock())));
    }

    AIMDBackoffManager(ConnPoolControl connpoolcontrol, Clock clock1)
    {
        coolDown = 5000L;
        backoffFactor = 0.5D;
        cap = 2;
        clock = clock1;
        connPerRoute = connpoolcontrol;
        lastRouteProbes = new HashMap();
        lastRouteBackoffs = new HashMap();
    }

    private int getBackedOffPoolSize(int i)
    {
        if(i <= 1)
            return 1;
        else
            return (int)Math.floor(backoffFactor * (double)i);
    }

    private Long getLastUpdate(Map map, HttpRoute httproute)
    {
        Long long1 = (Long)map.get(httproute);
        if(long1 == null)
            long1 = Long.valueOf(0L);
        return long1;
    }

    public void backOff(HttpRoute httproute)
    {
        int i;
        long l;
label0:
        {
            synchronized(connPerRoute)
            {
                i = connPerRoute.getMaxPerRoute(httproute);
                Long long1 = getLastUpdate(lastRouteBackoffs, httproute);
                l = clock.getCurrentTime();
                if(l - long1.longValue() >= coolDown)
                    break label0;
            }
            return;
        }
        connPerRoute.setMaxPerRoute(httproute, getBackedOffPoolSize(i));
        lastRouteBackoffs.put(httproute, Long.valueOf(l));
        connpoolcontrol;
        JVM INSTR monitorexit ;
        return;
        exception;
        connpoolcontrol;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void probe(HttpRoute httproute)
    {
        ConnPoolControl connpoolcontrol = connPerRoute;
        connpoolcontrol;
        JVM INSTR monitorenter ;
        int i;
        int j;
        i = connPerRoute.getMaxPerRoute(httproute);
        if(i < cap)
            break MISSING_BLOCK_LABEL_138;
        j = cap;
_L1:
        long l;
        Long long1 = getLastUpdate(lastRouteProbes, httproute);
        Long long2 = getLastUpdate(lastRouteBackoffs, httproute);
        l = clock.getCurrentTime();
        if(l - long1.longValue() < coolDown || l - long2.longValue() < coolDown)
            return;
        connPerRoute.setMaxPerRoute(httproute, j);
        lastRouteProbes.put(httproute, Long.valueOf(l));
        return;
        Exception exception;
        exception;
        connpoolcontrol;
        JVM INSTR monitorexit ;
        throw exception;
        j = i + 1;
          goto _L1
    }

    public void setBackoffFactor(double d)
    {
        boolean flag;
        if(d > 0.0D && d < 1.0D)
            flag = true;
        else
            flag = false;
        Args.check(flag, "Backoff factor must be 0.0 < f < 1.0");
        backoffFactor = d;
    }

    public void setCooldownMillis(long l)
    {
        Args.positive(coolDown, "Cool down");
        coolDown = l;
    }

    public void setPerHostConnectionCap(int i)
    {
        Args.positive(i, "Per host connection cap");
        cap = i;
    }

    private double backoffFactor;
    private int cap;
    private final Clock clock;
    private final ConnPoolControl connPerRoute;
    private long coolDown;
    private final Map lastRouteBackoffs;
    private final Map lastRouteProbes;
}
