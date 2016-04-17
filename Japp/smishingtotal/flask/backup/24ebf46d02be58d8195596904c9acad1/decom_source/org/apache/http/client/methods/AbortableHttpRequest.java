// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.io.IOException;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;

public interface AbortableHttpRequest
{

    public abstract void abort();

    public abstract void setConnectionRequest(ClientConnectionRequest clientconnectionrequest)
        throws IOException;

    public abstract void setReleaseTrigger(ConnectionReleaseTrigger connectionreleasetrigger)
        throws IOException;
}
