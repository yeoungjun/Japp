// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.execchain;

import java.io.*;
import java.net.SocketException;
import org.apache.http.HttpEntity;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.conn.EofSensorWatcher;
import org.apache.http.entity.HttpEntityWrapper;

// Referenced classes of package org.apache.http.impl.execchain:
//            ConnectionHolder

class ResponseEntityWrapper extends HttpEntityWrapper
    implements EofSensorWatcher
{

    public ResponseEntityWrapper(HttpEntity httpentity, ConnectionHolder connectionholder)
    {
        super(httpentity);
        connReleaseTrigger = connectionholder;
    }

    private void cleanup()
    {
        if(connReleaseTrigger != null)
            connReleaseTrigger.abortConnection();
    }

    public void consumeContent()
        throws IOException
    {
        releaseConnection();
    }

    public boolean eofDetected(InputStream inputstream)
        throws IOException
    {
        inputstream.close();
        releaseConnection();
        cleanup();
        return false;
        Exception exception;
        exception;
        cleanup();
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
        if(connReleaseTrigger == null)
            break MISSING_BLOCK_LABEL_28;
        if(connReleaseTrigger.isReusable())
            connReleaseTrigger.releaseConnection();
        cleanup();
        return;
        Exception exception;
        exception;
        cleanup();
        throw exception;
    }

    public boolean streamAbort(InputStream inputstream)
        throws IOException
    {
        cleanup();
        return false;
    }

    public boolean streamClosed(InputStream inputstream)
        throws IOException
    {
        if(connReleaseTrigger == null) goto _L2; else goto _L1
_L1:
        boolean flag1 = connReleaseTrigger.isReleased();
        if(flag1) goto _L2; else goto _L3
_L3:
        boolean flag = true;
_L7:
        inputstream.close();
        releaseConnection();
_L5:
        cleanup();
        return false;
_L2:
        flag = false;
        continue; /* Loop/switch isn't completed */
        SocketException socketexception;
        socketexception;
        if(!flag) goto _L5; else goto _L4
_L4:
        throw socketexception;
        Exception exception;
        exception;
        cleanup();
        throw exception;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        wrappedEntity.writeTo(outputstream);
        releaseConnection();
        cleanup();
        return;
        Exception exception;
        exception;
        cleanup();
        throw exception;
    }

    private final ConnectionHolder connReleaseTrigger;
}
