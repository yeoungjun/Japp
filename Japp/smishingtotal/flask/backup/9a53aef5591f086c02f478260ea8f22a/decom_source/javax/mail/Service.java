// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

// Referenced classes of package javax.mail:
//            Session, EventQueue, MessagingException, AuthenticationFailedException, 
//            URLName, PasswordAuthentication

public abstract class Service
{
    static class TerminatorEvent extends MailEvent
    {

        public void dispatch(Object obj)
        {
            Thread.currentThread().interrupt();
        }

        private static final long serialVersionUID = 0x4ce9c033019effa0L;

        TerminatorEvent()
        {
            super(new Object());
        }
    }


    protected Service(Session session1, URLName urlname)
    {
        url = null;
        debug = false;
        connected = false;
        connectionListeners = null;
        qLock = new Object();
        session = session1;
        url = urlname;
        debug = session1.getDebug();
    }

    private void terminateQueue()
    {
        synchronized(qLock)
        {
            if(q != null)
            {
                Vector vector = new Vector();
                vector.setSize(1);
                q.enqueue(new TerminatorEvent(), vector);
                q = null;
            }
        }
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void addConnectionListener(ConnectionListener connectionlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(connectionListeners == null)
            connectionListeners = new Vector();
        connectionListeners.addElement(connectionlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void close()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        setConnected(false);
        notifyConnectionListeners(3);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void connect()
        throws MessagingException
    {
        connect(null, null, null);
    }

    public void connect(String s, int i, String s1, String s2)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(isConnected())
            throw new IllegalStateException("already connected");
        break MISSING_BLOCK_LABEL_26;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        URLName urlname = url;
        String s3;
        String s4;
        s3 = null;
        s4 = null;
        if(urlname == null) goto _L2; else goto _L1
_L1:
        s3 = url.getProtocol();
        if(s != null)
            break MISSING_BLOCK_LABEL_64;
        s = url.getHost();
        if(i != -1)
            break MISSING_BLOCK_LABEL_77;
        i = url.getPort();
        if(s1 != null) goto _L4; else goto _L3
_L3:
        s1 = url.getUsername();
        if(s2 != null)
            break MISSING_BLOCK_LABEL_103;
        s2 = url.getPassword();
_L20:
        s4 = url.getFile();
_L2:
        if(s3 == null)
            break MISSING_BLOCK_LABEL_185;
        if(s != null)
            break MISSING_BLOCK_LABEL_151;
        s = session.getProperty((new StringBuilder("mail.")).append(s3).append(".host").toString());
        if(s1 != null)
            break MISSING_BLOCK_LABEL_185;
        s1 = session.getProperty((new StringBuilder("mail.")).append(s3).append(".user").toString());
        if(s != null)
            break MISSING_BLOCK_LABEL_199;
        s = session.getProperty("mail.host");
        if(s1 != null)
            break MISSING_BLOCK_LABEL_217;
        String s5 = session.getProperty("mail.user");
        s1 = s5;
        if(s1 != null)
            break MISSING_BLOCK_LABEL_231;
        String s8 = System.getProperty("user.name");
        s1 = s8;
_L22:
        boolean flag = false;
        if(s2 != null) goto _L6; else goto _L5
_L5:
        URLName urlname1 = url;
        flag = false;
        if(urlname1 == null) goto _L6; else goto _L7
_L7:
        PasswordAuthentication passwordauthentication1;
        setURLName(new URLName(s3, s, i, s4, s1, null));
        passwordauthentication1 = session.getPasswordAuthentication(getURLName());
        if(passwordauthentication1 == null) goto _L9; else goto _L8
_L8:
        if(s1 != null) goto _L11; else goto _L10
_L10:
        String s6;
        s1 = passwordauthentication1.getUserName();
        s6 = passwordauthentication1.getPassword();
        s2 = s6;
_L6:
        AuthenticationFailedException authenticationfailedexception = null;
        boolean flag2 = protocolConnect(s, i, s1, s2);
        boolean flag1 = flag2;
_L24:
        if(flag1) goto _L13; else goto _L12
_L12:
        InetAddress inetaddress1 = InetAddress.getByName(s);
        InetAddress inetaddress = inetaddress1;
_L25:
        PasswordAuthentication passwordauthentication = session.requestPasswordAuthentication(inetaddress, i, s3, null, s1);
        if(passwordauthentication == null) goto _L13; else goto _L14
_L14:
        s1 = passwordauthentication.getUserName();
        s2 = passwordauthentication.getPassword();
        flag1 = protocolConnect(s, i, s1, s2);
_L13:
        if(flag1) goto _L16; else goto _L15
_L15:
        if(authenticationfailedexception == null) goto _L18; else goto _L17
_L17:
        throw authenticationfailedexception;
_L4:
        if(s2 != null) goto _L20; else goto _L19
_L19:
        String s9 = url.getUsername();
        if(s1.equals(s9))
            s2 = url.getPassword();
          goto _L20
        SecurityException securityexception;
        securityexception;
        if(!debug) goto _L22; else goto _L21
_L21:
        securityexception.printStackTrace(session.getDebugOut());
          goto _L22
_L11:
        boolean flag3;
        String s7 = passwordauthentication1.getUserName();
        flag3 = s1.equals(s7);
        flag = false;
        if(!flag3) goto _L6; else goto _L23
_L23:
        s2 = passwordauthentication1.getPassword();
        flag = false;
          goto _L6
_L18:
        throw new AuthenticationFailedException();
_L16:
        setURLName(new URLName(s3, s, i, s4, s1, s2));
        if(!flag)
            break MISSING_BLOCK_LABEL_551;
        session.setPasswordAuthentication(getURLName(), new PasswordAuthentication(s1, s2));
        setConnected(true);
        notifyConnectionListeners(1);
        this;
        JVM INSTR monitorexit ;
        return;
_L9:
        flag = true;
          goto _L6
        AuthenticationFailedException authenticationfailedexception1;
        authenticationfailedexception1;
        authenticationfailedexception = authenticationfailedexception1;
        flag1 = false;
          goto _L24
        UnknownHostException unknownhostexception;
        unknownhostexception;
        inetaddress = null;
          goto _L25
    }

    public void connect(String s, String s1)
        throws MessagingException
    {
        connect(null, s, s1);
    }

    public void connect(String s, String s1, String s2)
        throws MessagingException
    {
        connect(s, -1, s1, s2);
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        terminateQueue();
    }

    public URLName getURLName()
    {
        this;
        JVM INSTR monitorenter ;
        if(url == null || url.getPassword() == null && url.getFile() == null) goto _L2; else goto _L1
_L1:
        URLName urlname = new URLName(url.getProtocol(), url.getHost(), url.getPort(), null, url.getUsername(), null);
_L4:
        this;
        JVM INSTR monitorexit ;
        return urlname;
_L2:
        urlname = url;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public boolean isConnected()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = connected;
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    protected void notifyConnectionListeners(int i)
    {
        this;
        JVM INSTR monitorenter ;
        if(connectionListeners != null)
            queueEvent(new ConnectionEvent(this, i), connectionListeners);
        if(i != 3)
            break MISSING_BLOCK_LABEL_35;
        terminateQueue();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected boolean protocolConnect(String s, int i, String s1, String s2)
        throws MessagingException
    {
        return false;
    }

    protected void queueEvent(MailEvent mailevent, Vector vector)
    {
        synchronized(qLock)
        {
            if(q == null)
                q = new EventQueue();
        }
        Vector vector1 = (Vector)vector.clone();
        q.enqueue(mailevent, vector1);
        return;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void removeConnectionListener(ConnectionListener connectionlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(connectionListeners != null)
            connectionListeners.removeElement(connectionlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected void setConnected(boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        connected = flag;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected void setURLName(URLName urlname)
    {
        this;
        JVM INSTR monitorenter ;
        url = urlname;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public String toString()
    {
        URLName urlname = getURLName();
        if(urlname != null)
            return urlname.toString();
        else
            return super.toString();
    }

    private boolean connected;
    private Vector connectionListeners;
    protected boolean debug;
    private EventQueue q;
    private Object qLock;
    protected Session session;
    protected URLName url;
}
