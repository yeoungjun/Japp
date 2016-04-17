// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.util.Vector;
import javax.mail.event.MailEvent;

class EventQueue
    implements Runnable
{
    static class QueueElement
    {

        MailEvent event;
        QueueElement next;
        QueueElement prev;
        Vector vector;

        QueueElement(MailEvent mailevent, Vector vector1)
        {
            next = null;
            prev = null;
            event = null;
            vector = null;
            event = mailevent;
            vector = vector1;
        }
    }


    public EventQueue()
    {
        head = null;
        tail = null;
        qThread = new Thread(this, "JavaMail-EventQueue");
        qThread.setDaemon(true);
        qThread.start();
    }

    private QueueElement dequeue()
        throws InterruptedException
    {
        this;
        JVM INSTR monitorenter ;
_L3:
        if(tail == null) goto _L2; else goto _L1
_L1:
        QueueElement queueelement;
        queueelement = tail;
        tail = queueelement.prev;
        if(tail != null)
            break MISSING_BLOCK_LABEL_60;
        head = null;
_L4:
        queueelement.next = null;
        queueelement.prev = null;
        this;
        JVM INSTR monitorexit ;
        return queueelement;
_L2:
        wait();
          goto _L3
        Exception exception;
        exception;
        throw exception;
        tail.next = null;
          goto _L4
    }

    public void enqueue(MailEvent mailevent, Vector vector)
    {
        this;
        JVM INSTR monitorenter ;
        QueueElement queueelement;
        queueelement = new QueueElement(mailevent, vector);
        if(head != null)
            break MISSING_BLOCK_LABEL_36;
        head = queueelement;
        tail = queueelement;
_L1:
        notifyAll();
        this;
        JVM INSTR monitorexit ;
        return;
        queueelement.next = head;
        head.prev = queueelement;
        head = queueelement;
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    public void run()
    {
_L1:
        QueueElement queueelement;
        MailEvent mailevent;
        Vector vector;
        int i;
        int j;
        Throwable throwable;
        boolean flag;
        try
        {
            queueelement = dequeue();
        }
        catch(InterruptedException interruptedexception)
        {
            return;
        }
        if(queueelement == null)
            return;
        mailevent = queueelement.event;
        vector = queueelement.vector;
        i = 0;
_L3:
        j = vector.size();
        if(i < j) goto _L2; else goto _L1
_L2:
        mailevent.dispatch(vector.elementAt(i));
_L4:
        i++;
          goto _L3
        throwable;
        flag = throwable instanceof InterruptedException;
        if(flag)
            return;
          goto _L4
    }

    void stop()
    {
        if(qThread != null)
        {
            qThread.interrupt();
            qThread = null;
        }
    }

    private QueueElement head;
    private Thread qThread;
    private QueueElement tail;
}
