// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;


// Referenced classes of package org.apache.harmony.awt.datatransfer:
//            DTK

public class DataTransferThread extends Thread
{

    public DataTransferThread(DTK dtk1)
    {
        super("AWT-DataTransferThread");
        setDaemon(true);
        dtk = dtk1;
    }

    public void run()
    {
        this;
        JVM INSTR monitorenter ;
        dtk.initDragAndDrop();
        notifyAll();
        this;
        JVM INSTR monitorexit ;
        dtk.runEventLoop();
        return;
        Exception exception;
        exception;
        notifyAll();
        throw exception;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    public void start()
    {
        this;
        JVM INSTR monitorenter ;
        super.start();
        wait();
        this;
        JVM INSTR monitorexit ;
        return;
        InterruptedException interruptedexception;
        interruptedexception;
        throw new RuntimeException(interruptedexception);
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private final DTK dtk;
}
