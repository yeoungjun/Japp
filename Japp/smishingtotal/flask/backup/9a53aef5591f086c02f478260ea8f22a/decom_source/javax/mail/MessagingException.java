// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


public class MessagingException extends Exception
{

    public MessagingException()
    {
        initCause(null);
    }

    public MessagingException(String s)
    {
        super(s);
        initCause(null);
    }

    public MessagingException(String s, Exception exception)
    {
        super(s);
        next = exception;
        initCause(null);
    }

    private final String superToString()
    {
        return super.toString();
    }

    public Throwable getCause()
    {
        this;
        JVM INSTR monitorenter ;
        Exception exception1 = next;
        this;
        JVM INSTR monitorexit ;
        return exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public Exception getNextException()
    {
        this;
        JVM INSTR monitorenter ;
        Exception exception1 = next;
        this;
        JVM INSTR monitorexit ;
        return exception1;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean setNextException(Exception exception)
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = this;
_L8:
        if((obj instanceof MessagingException) && ((MessagingException)obj).next != null) goto _L2; else goto _L1
_L1:
        if(!(obj instanceof MessagingException)) goto _L4; else goto _L3
_L3:
        ((MessagingException)obj).next = exception;
        boolean flag = true;
_L6:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        obj = ((MessagingException)obj).next;
        continue; /* Loop/switch isn't completed */
_L4:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception1;
        exception1;
        throw exception1;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public String toString()
    {
        this;
        JVM INSTR monitorenter ;
        String s;
        Exception exception1;
        s = super.toString();
        exception1 = next;
        if(exception1 != null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return s;
_L2:
        if(s == null)
            s = "";
        StringBuffer stringbuffer = new StringBuffer(s);
_L3:
        if(exception1 != null)
            break MISSING_BLOCK_LABEL_50;
        s = stringbuffer.toString();
        continue; /* Loop/switch isn't completed */
label0:
        {
            stringbuffer.append(";\n  nested exception is:\n\t");
            if(!(exception1 instanceof MessagingException))
                break label0;
            MessagingException messagingexception = (MessagingException)exception1;
            stringbuffer.append(messagingexception.superToString());
            exception1 = messagingexception.next;
        }
          goto _L3
        stringbuffer.append(exception1.toString());
        exception1 = null;
          goto _L3
        Exception exception;
        exception;
        throw exception;
        if(true) goto _L1; else goto _L4
_L4:
    }

    private static final long serialVersionUID = 0x96f4d3b738a7e02bL;
    private Exception next;
}
