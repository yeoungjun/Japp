// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn.tsccm;


// Referenced classes of package org.apache.http.impl.conn.tsccm:
//            WaitingThread

public class WaitingThreadAborter
{

    public WaitingThreadAborter()
    {
    }

    public void abort()
    {
        aborted = true;
        if(waitingThread != null)
            waitingThread.interrupt();
    }

    public void setWaitingThread(WaitingThread waitingthread)
    {
        waitingThread = waitingthread;
        if(aborted)
            waitingthread.interrupt();
    }

    private boolean aborted;
    private WaitingThread waitingThread;
}
