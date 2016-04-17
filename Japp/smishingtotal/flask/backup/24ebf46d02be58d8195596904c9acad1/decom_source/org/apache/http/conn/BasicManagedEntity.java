// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.*;
import java.net.SocketException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

// Referenced classes of package org.apache.http.conn:
//            ConnectionReleaseTrigger, EofSensorWatcher, ManagedClientConnection, EofSensorInputStream

public class BasicManagedEntity extends HttpEntityWrapper
    implements ConnectionReleaseTrigger, EofSensorWatcher
{

    public BasicManagedEntity(HttpEntity httpentity, ManagedClientConnection managedclientconnection, boolean flag)
    {
        super(httpentity);
        Args.notNull(managedclientconnection, "Connection");
        managedConn = managedclientconnection;
        attemptReuse = flag;
    }

    private void ensureConsumed()
        throws IOException
    {
        if(managedConn == null)
            return;
        if(!attemptReuse) goto _L2; else goto _L1
_L1:
        EntityUtils.consume(wrappedEntity);
        managedConn.markReusable();
_L4:
        releaseManagedConnection();
        return;
_L2:
        managedConn.unmarkReusable();
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        releaseManagedConnection();
        throw exception;
    }

    public void abortConnection()
        throws IOException
    {
        if(managedConn == null)
            break MISSING_BLOCK_LABEL_21;
        managedConn.abortConnection();
        managedConn = null;
        return;
        Exception exception;
        exception;
        managedConn = null;
        throw exception;
    }

    public void consumeContent()
        throws IOException
    {
        ensureConsumed();
    }

    public boolean eofDetected(InputStream inputstream)
        throws IOException
    {
        if(managedConn != null)
        {
            if(!attemptReuse)
                break MISSING_BLOCK_LABEL_33;
            inputstream.close();
            managedConn.markReusable();
        }
_L2:
        releaseManagedConnection();
        return false;
        managedConn.unmarkReusable();
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        releaseManagedConnection();
        throw exception;
    }

    public InputStream getContent()
        throws IOException
    {
        return new EofSensorInputStream(wrappedEntity.getContent(), this);
    }

    public boolean isRepeatable()
    {
        return false;
    }

    public void releaseConnection()
        throws IOException
    {
        ensureConsumed();
    }

    protected void releaseManagedConnection()
        throws IOException
    {
        if(managedConn == null)
            break MISSING_BLOCK_LABEL_21;
        managedConn.releaseConnection();
        managedConn = null;
        return;
        Exception exception;
        exception;
        managedConn = null;
        throw exception;
    }

    public boolean streamAbort(InputStream inputstream)
        throws IOException
    {
        if(managedConn != null)
            managedConn.abortConnection();
        return false;
    }

    public boolean streamClosed(InputStream inputstream)
        throws IOException
    {
        boolean flag;
        if(managedConn == null)
            break MISSING_BLOCK_LABEL_37;
        if(!attemptReuse)
            break MISSING_BLOCK_LABEL_59;
        flag = managedConn.isOpen();
        inputstream.close();
        managedConn.markReusable();
_L2:
        releaseManagedConnection();
        return false;
        SocketException socketexception;
        socketexception;
        if(!flag) goto _L2; else goto _L1
_L1:
        throw socketexception;
        Exception exception;
        exception;
        releaseManagedConnection();
        throw exception;
        managedConn.unmarkReusable();
          goto _L2
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        super.writeTo(outputstream);
        ensureConsumed();
    }

    protected final boolean attemptReuse;
    protected ManagedClientConnection managedConn;
}
