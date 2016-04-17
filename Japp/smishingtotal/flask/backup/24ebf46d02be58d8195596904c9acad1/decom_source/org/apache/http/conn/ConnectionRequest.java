// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpClientConnection;
import org.apache.http.concurrent.Cancellable;

// Referenced classes of package org.apache.http.conn:
//            ConnectionPoolTimeoutException

public interface ConnectionRequest
    extends Cancellable
{

    public abstract HttpClientConnection get(long l, TimeUnit timeunit)
        throws InterruptedException, ExecutionException, ConnectionPoolTimeoutException;
}
