// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.HttpRequest;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.client.methods:
//            HttpExecutionAware, AbortableHttpRequest

public abstract class AbstractExecutionAwareRequest extends AbstractHttpMessage
    implements HttpExecutionAware, AbortableHttpRequest, Cloneable, HttpRequest
{

    protected AbstractExecutionAwareRequest()
    {
        abortLock = new ReentrantLock();
    }

    private void cancelExecution()
    {
        if(cancellable != null)
        {
            cancellable.cancel();
            cancellable = null;
        }
    }

    public void abort()
    {
        if(aborted)
            return;
        abortLock.lock();
        aborted = true;
        cancelExecution();
        abortLock.unlock();
        return;
        Exception exception;
        exception;
        abortLock.unlock();
        throw exception;
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        AbstractExecutionAwareRequest abstractexecutionawarerequest = (AbstractExecutionAwareRequest)super.clone();
        abstractexecutionawarerequest.headergroup = (HeaderGroup)CloneUtils.cloneObject(headergroup);
        abstractexecutionawarerequest.params = (HttpParams)CloneUtils.cloneObject(params);
        abstractexecutionawarerequest.abortLock = new ReentrantLock();
        abstractexecutionawarerequest.cancellable = null;
        abstractexecutionawarerequest.aborted = false;
        return abstractexecutionawarerequest;
    }

    public void completed()
    {
        abortLock.lock();
        cancellable = null;
        abortLock.unlock();
        return;
        Exception exception;
        exception;
        abortLock.unlock();
        throw exception;
    }

    public boolean isAborted()
    {
        return aborted;
    }

    public void reset()
    {
        abortLock.lock();
        cancelExecution();
        aborted = false;
        abortLock.unlock();
        return;
        Exception exception;
        exception;
        abortLock.unlock();
        throw exception;
    }

    public void setCancellable(Cancellable cancellable1)
    {
        if(aborted)
            return;
        abortLock.lock();
        cancellable = cancellable1;
        abortLock.unlock();
        return;
        Exception exception;
        exception;
        abortLock.unlock();
        throw exception;
    }

    public void setConnectionRequest(final ClientConnectionRequest connRequest)
    {
        if(aborted)
            return;
        abortLock.lock();
        cancellable = new Cancellable() {

            public boolean cancel()
            {
                connRequest.abortRequest();
                return true;
            }

            final AbstractExecutionAwareRequest this$0;
            final ClientConnectionRequest val$connRequest;

            
            {
                this$0 = AbstractExecutionAwareRequest.this;
                connRequest = clientconnectionrequest;
                super();
            }
        };
        abortLock.unlock();
        return;
        Exception exception;
        exception;
        abortLock.unlock();
        throw exception;
    }

    public void setReleaseTrigger(final ConnectionReleaseTrigger releaseTrigger)
    {
        if(aborted)
            return;
        abortLock.lock();
        cancellable = new Cancellable() {

            public boolean cancel()
            {
                try
                {
                    releaseTrigger.abortConnection();
                }
                catch(IOException ioexception)
                {
                    return false;
                }
                return true;
            }

            final AbstractExecutionAwareRequest this$0;
            final ConnectionReleaseTrigger val$releaseTrigger;

            
            {
                this$0 = AbstractExecutionAwareRequest.this;
                releaseTrigger = connectionreleasetrigger;
                super();
            }
        };
        abortLock.unlock();
        return;
        Exception exception;
        exception;
        abortLock.unlock();
        throw exception;
    }

    private Lock abortLock;
    private volatile boolean aborted;
    private volatile Cancellable cancellable;
}
