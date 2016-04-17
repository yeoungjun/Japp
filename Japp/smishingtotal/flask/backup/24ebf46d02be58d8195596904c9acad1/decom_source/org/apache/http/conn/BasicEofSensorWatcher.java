// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.conn:
//            EofSensorWatcher, ManagedClientConnection

public class BasicEofSensorWatcher
    implements EofSensorWatcher
{

    public BasicEofSensorWatcher(ManagedClientConnection managedclientconnection, boolean flag)
    {
        Args.notNull(managedclientconnection, "Connection");
        managedConn = managedclientconnection;
        attemptReuse = flag;
    }

    public boolean eofDetected(InputStream inputstream)
        throws IOException
    {
        if(attemptReuse)
        {
            inputstream.close();
            managedConn.markReusable();
        }
        managedConn.releaseConnection();
        return false;
        Exception exception;
        exception;
        managedConn.releaseConnection();
        throw exception;
    }

    public boolean streamAbort(InputStream inputstream)
        throws IOException
    {
        managedConn.abortConnection();
        return false;
    }

    public boolean streamClosed(InputStream inputstream)
        throws IOException
    {
        if(attemptReuse)
        {
            inputstream.close();
            managedConn.markReusable();
        }
        managedConn.releaseConnection();
        return false;
        Exception exception;
        exception;
        managedConn.releaseConnection();
        throw exception;
    }

    protected final boolean attemptReuse;
    protected final ManagedClientConnection managedConn;
}
