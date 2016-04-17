// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;

public class IdleConnectionHandler
{
    private static class TimeValues
    {

        private final long timeAdded;
        private final long timeExpires;



        TimeValues(long l, long l1, TimeUnit timeunit)
        {
            timeAdded = l;
            if(l1 > 0L)
            {
                timeExpires = l + timeunit.toMillis(l1);
                return;
            } else
            {
                timeExpires = 0x7fffffffffffffffL;
                return;
            }
        }
    }


    public IdleConnectionHandler()
    {
    }

    public void add(HttpConnection httpconnection, long l, TimeUnit timeunit)
    {
        long l1 = System.currentTimeMillis();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Adding connection at: ").append(l1).toString());
        connectionToTimes.put(httpconnection, new TimeValues(l1, l, timeunit));
    }

    public void closeExpiredConnections()
    {
        long l = System.currentTimeMillis();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Checking for expired connections, now: ").append(l).toString());
        Iterator iterator = connectionToTimes.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            HttpConnection httpconnection = (HttpConnection)entry.getKey();
            TimeValues timevalues = (TimeValues)entry.getValue();
            if(timevalues.timeExpires <= l)
            {
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Closing connection, expired @: ").append(timevalues.timeExpires).toString());
                try
                {
                    httpconnection.close();
                }
                catch(IOException ioexception)
                {
                    log.debug("I/O error closing connection", ioexception);
                }
            }
        } while(true);
    }

    public void closeIdleConnections(long l)
    {
        long l1 = System.currentTimeMillis() - l;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Checking for connections, idle timeout: ").append(l1).toString());
        Iterator iterator = connectionToTimes.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            HttpConnection httpconnection = (HttpConnection)entry.getKey();
            long l2 = ((TimeValues)entry.getValue()).timeAdded;
            if(l2 <= l1)
            {
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Closing idle connection, connection time: ").append(l2).toString());
                try
                {
                    httpconnection.close();
                }
                catch(IOException ioexception)
                {
                    log.debug("I/O error closing connection", ioexception);
                }
            }
        } while(true);
    }

    public boolean remove(HttpConnection httpconnection)
    {
        TimeValues timevalues = (TimeValues)connectionToTimes.remove(httpconnection);
        if(timevalues == null)
            log.warn("Removing a connection that never existed!");
        else
        if(System.currentTimeMillis() > timevalues.timeExpires)
            return false;
        return true;
    }

    public void removeAll()
    {
        connectionToTimes.clear();
    }

    private final Map connectionToTimes = new HashMap();
    private final Log log = LogFactory.getLog(getClass());
}
