// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.*;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.Namespaces;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.mail.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPFolder, DefaultFolder

public class IMAPStore extends Store
    implements QuotaAwareStore, ResponseHandler
{
    static class ConnectionPool
    {

        private static final int ABORTING = 2;
        private static final int IDLE = 1;
        private static final int RUNNING;
        private Vector authenticatedConnections;
        private long clientTimeoutInterval;
        private boolean debug;
        private Vector folders;
        private IMAPProtocol idleProtocol;
        private int idleState;
        private long lastTimePruned;
        private int poolSize;
        private long pruningInterval;
        private boolean separateStoreConnection;
        private long serverTimeoutInterval;
        private boolean storeConnectionInUse;























        ConnectionPool()
        {
            authenticatedConnections = new Vector();
            separateStoreConnection = false;
            storeConnectionInUse = false;
            clientTimeoutInterval = 45000L;
            serverTimeoutInterval = 0x1b7740L;
            poolSize = 1;
            pruningInterval = 60000L;
            debug = false;
            idleState = 0;
        }
    }


    public IMAPStore(Session session, URLName urlname)
    {
        this(session, urlname, "imap", 143, false);
    }

    protected IMAPStore(Session session, URLName urlname, String s, int i, boolean flag)
    {
        super(session, urlname);
        name = "imap";
        defaultPort = 143;
        isSSL = false;
        port = -1;
        blksize = 16384;
        statusCacheTimeout = 1000;
        appendBufferSize = -1;
        minIdleTime = 10;
        disableAuthLogin = false;
        disableAuthPlain = false;
        enableStartTLS = false;
        enableSASL = false;
        forcePasswordRefresh = false;
        enableImapEvents = false;
        connected = false;
        pool = new ConnectionPool();
        if(urlname != null)
            s = urlname.getProtocol();
        name = s;
        defaultPort = i;
        isSSL = flag;
        pool.lastTimePruned = System.currentTimeMillis();
        debug = session.getDebug();
        out = session.getDebugOut();
        if(out == null)
            out = System.out;
        String s1 = session.getProperty((new StringBuilder("mail.")).append(s).append(".connectionpool.debug").toString());
        if(s1 != null && s1.equalsIgnoreCase("true"))
            pool.debug = true;
        String s2 = session.getProperty((new StringBuilder("mail.")).append(s).append(".partialfetch").toString());
        String s4;
        String s5;
        String s6;
        String s7;
        String s8;
        String s9;
        String s10;
        String s11;
        String s12;
        String s13;
        String s14;
        String s15;
        String s16;
        String s17;
        String s18;
        String s19;
        String s20;
        int j;
        int k;
        int l;
        if(s2 != null && s2.equalsIgnoreCase("false"))
        {
            blksize = -1;
            if(debug)
                out.println("DEBUG: mail.imap.partialfetch: false");
        } else
        {
            String s3 = session.getProperty((new StringBuilder("mail.")).append(s).append(".fetchsize").toString());
            if(s3 != null)
                blksize = Integer.parseInt(s3);
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.fetchsize: ")).append(blksize).toString());
        }
        s4 = session.getProperty((new StringBuilder("mail.")).append(s).append(".statuscachetimeout").toString());
        if(s4 != null)
        {
            statusCacheTimeout = Integer.parseInt(s4);
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.statuscachetimeout: ")).append(statusCacheTimeout).toString());
        }
        s5 = session.getProperty((new StringBuilder("mail.")).append(s).append(".appendbuffersize").toString());
        if(s5 != null)
        {
            appendBufferSize = Integer.parseInt(s5);
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.appendbuffersize: ")).append(appendBufferSize).toString());
        }
        s6 = session.getProperty((new StringBuilder("mail.")).append(s).append(".minidletime").toString());
        if(s6 != null)
        {
            minIdleTime = Integer.parseInt(s6);
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.minidletime: ")).append(minIdleTime).toString());
        }
        s7 = session.getProperty((new StringBuilder("mail.")).append(s).append(".connectionpoolsize").toString());
        if(s7 == null)
            break MISSING_BLOCK_LABEL_593;
        l = Integer.parseInt(s7);
        Vector vector;
        StringTokenizer stringtokenizer;
        String s21;
        NumberFormatException numberformatexception;
        NumberFormatException numberformatexception1;
        if(l > 0)
            try
            {
                pool.poolSize = l;
            }
            catch(NumberFormatException numberformatexception2) { }
        if(pool.debug)
            out.println((new StringBuilder("DEBUG: mail.imap.connectionpoolsize: ")).append(pool.poolSize).toString());
        s8 = session.getProperty((new StringBuilder("mail.")).append(s).append(".connectionpooltimeout").toString());
        if(s8 == null)
            break MISSING_BLOCK_LABEL_686;
        k = Integer.parseInt(s8);
        if(k > 0)
            try
            {
                pool.clientTimeoutInterval = (long)k;
            }
            // Misplaced declaration of an exception variable
            catch(NumberFormatException numberformatexception1) { }
        if(pool.debug)
            out.println((new StringBuilder("DEBUG: mail.imap.connectionpooltimeout: ")).append(pool.clientTimeoutInterval).toString());
        s9 = session.getProperty((new StringBuilder("mail.")).append(s).append(".servertimeout").toString());
        if(s9 == null)
            break MISSING_BLOCK_LABEL_779;
        j = Integer.parseInt(s9);
        if(j > 0)
            try
            {
                pool.serverTimeoutInterval = (long)j;
            }
            // Misplaced declaration of an exception variable
            catch(NumberFormatException numberformatexception) { }
        if(pool.debug)
            out.println((new StringBuilder("DEBUG: mail.imap.servertimeout: ")).append(pool.serverTimeoutInterval).toString());
        s10 = session.getProperty((new StringBuilder("mail.")).append(s).append(".separatestoreconnection").toString());
        if(s10 != null && s10.equalsIgnoreCase("true"))
        {
            if(pool.debug)
                out.println("DEBUG: dedicate a store connection");
            pool.separateStoreConnection = true;
        }
        s11 = session.getProperty((new StringBuilder("mail.")).append(s).append(".proxyauth.user").toString());
        if(s11 != null)
        {
            proxyAuthUser = s11;
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.proxyauth.user: ")).append(proxyAuthUser).toString());
        }
        s12 = session.getProperty((new StringBuilder("mail.")).append(s).append(".auth.login.disable").toString());
        if(s12 != null && s12.equalsIgnoreCase("true"))
        {
            if(debug)
                out.println("DEBUG: disable AUTH=LOGIN");
            disableAuthLogin = true;
        }
        s13 = session.getProperty((new StringBuilder("mail.")).append(s).append(".auth.plain.disable").toString());
        if(s13 != null && s13.equalsIgnoreCase("true"))
        {
            if(debug)
                out.println("DEBUG: disable AUTH=PLAIN");
            disableAuthPlain = true;
        }
        s14 = session.getProperty((new StringBuilder("mail.")).append(s).append(".starttls.enable").toString());
        if(s14 != null && s14.equalsIgnoreCase("true"))
        {
            if(debug)
                out.println("DEBUG: enable STARTTLS");
            enableStartTLS = true;
        }
        s15 = session.getProperty((new StringBuilder("mail.")).append(s).append(".sasl.enable").toString());
        if(s15 != null && s15.equalsIgnoreCase("true"))
        {
            if(debug)
                out.println("DEBUG: enable SASL");
            enableSASL = true;
        }
        if(!enableSASL) goto _L2; else goto _L1
_L1:
        s20 = session.getProperty((new StringBuilder("mail.")).append(s).append(".sasl.mechanisms").toString());
        if(s20 == null || s20.length() <= 0) goto _L2; else goto _L3
_L3:
        if(debug)
            out.println((new StringBuilder("DEBUG: SASL mechanisms allowed: ")).append(s20).toString());
        vector = new Vector(5);
        stringtokenizer = new StringTokenizer(s20, " ,");
_L7:
        if(stringtokenizer.hasMoreTokens()) goto _L5; else goto _L4
_L4:
        saslMechanisms = new String[vector.size()];
        vector.copyInto(saslMechanisms);
_L2:
        s16 = session.getProperty((new StringBuilder("mail.")).append(s).append(".sasl.authorizationid").toString());
        if(s16 != null)
        {
            authorizationID = s16;
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.sasl.authorizationid: ")).append(authorizationID).toString());
        }
        s17 = session.getProperty((new StringBuilder("mail.")).append(s).append(".sasl.realm").toString());
        if(s17 != null)
        {
            saslRealm = s17;
            if(debug)
                out.println((new StringBuilder("DEBUG: mail.imap.sasl.realm: ")).append(saslRealm).toString());
        }
        s18 = session.getProperty((new StringBuilder("mail.")).append(s).append(".forcepasswordrefresh").toString());
        if(s18 != null && s18.equalsIgnoreCase("true"))
        {
            if(debug)
                out.println("DEBUG: enable forcePasswordRefresh");
            forcePasswordRefresh = true;
        }
        s19 = session.getProperty((new StringBuilder("mail.")).append(s).append(".enableimapevents").toString());
        if(s19 != null && s19.equalsIgnoreCase("true"))
        {
            if(debug)
                out.println("DEBUG: enable IMAP events");
            enableImapEvents = true;
        }
        return;
_L5:
        s21 = stringtokenizer.nextToken();
        if(s21.length() > 0)
            vector.addElement(s21);
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void checkConnected()
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
            throw new AssertionError();
        if(!connected)
        {
            super.setConnected(false);
            throw new IllegalStateException("Not connected");
        } else
        {
            return;
        }
    }

    private void cleanup()
    {
        cleanup(false);
    }

    private void cleanup(boolean flag)
    {
        Vector vector;
        if(debug)
            out.println((new StringBuilder("DEBUG: IMAPStore cleanup, force ")).append(flag).toString());
        vector = null;
_L5:
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        if(pool.folders == null)
            break MISSING_BLOCK_LABEL_121;
        boolean flag1 = false;
        vector = pool.folders;
        pool.folders = null;
_L1:
        connectionpool;
        JVM INSTR monitorexit ;
        if(flag1)
        {
            synchronized(pool)
            {
                emptyConnectionPool(flag);
            }
            connected = false;
            notifyConnectionListeners(3);
            if(debug)
                out.println("DEBUG: IMAPStore cleanup done");
            return;
        }
        break MISSING_BLOCK_LABEL_134;
        flag1 = true;
          goto _L1
        Exception exception;
        exception;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception;
        int i;
        int j;
        i = 0;
        j = vector.size();
_L3:
        if(i >= j)
            continue; /* Loop/switch isn't completed */
        IMAPFolder imapfolder = (IMAPFolder)vector.elementAt(i);
        if(flag)
        {
            try
            {
                if(debug)
                    out.println("DEBUG: force folder to close");
                imapfolder.forceClose();
            }
            catch(MessagingException messagingexception) { }
            catch(IllegalStateException illegalstateexception) { }
            break MISSING_BLOCK_LABEL_231;
        }
        if(debug)
            out.println("DEBUG: close folder");
        imapfolder.close(false);
        break MISSING_BLOCK_LABEL_231;
        exception1;
        connectionpool1;
        JVM INSTR monitorexit ;
        throw exception1;
        i++;
        if(true) goto _L3; else goto _L2
_L2:
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void emptyConnectionPool(boolean flag)
    {
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        int i = -1 + pool.authenticatedConnections.size();
_L2:
        if(i >= 0)
            break MISSING_BLOCK_LABEL_59;
        pool.authenticatedConnections.removeAllElements();
        connectionpool;
        JVM INSTR monitorexit ;
        if(pool.debug)
            out.println("DEBUG: removed all authenticated connections");
        return;
        IMAPProtocol imapprotocol;
        imapprotocol = (IMAPProtocol)pool.authenticatedConnections.elementAt(i);
        imapprotocol.removeResponseHandler(this);
        Exception exception;
        if(flag)
        {
            try
            {
                imapprotocol.disconnect();
            }
            catch(ProtocolException protocolexception) { }
            finally { }
            break MISSING_BLOCK_LABEL_112;
        }
        imapprotocol.logout();
        break MISSING_BLOCK_LABEL_112;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception;
        i--;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private Namespaces getNamespaces()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkConnected();
        IMAPProtocol imapprotocol = null;
        Namespaces namespaces1 = namespaces;
        if(namespaces1 != null)
            break MISSING_BLOCK_LABEL_43;
        imapprotocol = getStoreProtocol();
        namespaces = imapprotocol.namespace();
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_43;
        cleanup();
_L2:
        Namespaces namespaces2 = namespaces;
        this;
        JVM INSTR monitorexit ;
        return namespaces2;
        BadCommandException badcommandexception;
        badcommandexception;
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null) goto _L2; else goto _L1
_L1:
        cleanup();
          goto _L2
        Exception exception;
        exception;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throw new StoreClosedException(this, connectionexception.getMessage());
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_108;
        cleanup();
        throw exception1;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    private void login(IMAPProtocol imapprotocol, String s, String s1)
        throws ProtocolException
    {
        if(enableStartTLS && imapprotocol.hasCapability("STARTTLS"))
        {
            imapprotocol.startTLS();
            imapprotocol.capability();
        }
        if(!imapprotocol.isAuthenticated())
        {
            imapprotocol.getCapabilities().put("__PRELOGIN__", "");
            String s2;
            if(authorizationID != null)
                s2 = authorizationID;
            else
            if(proxyAuthUser != null)
                s2 = proxyAuthUser;
            else
                s2 = s;
            if(enableSASL)
                imapprotocol.sasllogin(saslMechanisms, saslRealm, s2, s, s1);
            if(!imapprotocol.isAuthenticated())
                if(imapprotocol.hasCapability("AUTH=PLAIN") && !disableAuthPlain)
                    imapprotocol.authplain(s2, s, s1);
                else
                if((imapprotocol.hasCapability("AUTH-LOGIN") || imapprotocol.hasCapability("AUTH=LOGIN")) && !disableAuthLogin)
                    imapprotocol.authlogin(s, s1);
                else
                if(!imapprotocol.hasCapability("LOGINDISABLED"))
                    imapprotocol.login(s, s1);
                else
                    throw new ProtocolException("No login methods supported!");
            if(proxyAuthUser != null)
                imapprotocol.proxyauth(proxyAuthUser);
            if(imapprotocol.hasCapability("__PRELOGIN__"))
                try
                {
                    imapprotocol.capability();
                    return;
                }
                catch(ConnectionException connectionexception)
                {
                    throw connectionexception;
                }
                catch(ProtocolException protocolexception)
                {
                    return;
                }
        }
    }

    private Folder[] namespaceToFolders(com.sun.mail.imap.protocol.Namespaces.Namespace anamespace[], String s)
    {
        Folder afolder[] = new Folder[anamespace.length];
        int i = 0;
        do
        {
            if(i >= afolder.length)
                return afolder;
            String s1 = anamespace[i].prefix;
            char c;
            boolean flag;
            if(s == null)
            {
                int j = s1.length();
                if(j > 0 && s1.charAt(j - 1) == anamespace[i].delimiter)
                    s1 = s1.substring(0, j - 1);
            } else
            {
                s1 = (new StringBuilder(String.valueOf(s1))).append(s).toString();
            }
            c = anamespace[i].delimiter;
            if(s == null)
                flag = true;
            else
                flag = false;
            afolder[i] = new IMAPFolder(s1, c, this, flag);
            i++;
        } while(true);
    }

    private void timeoutConnections()
    {
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        if(System.currentTimeMillis() - pool.lastTimePruned <= pool.pruningInterval || pool.authenticatedConnections.size() <= 1) goto _L2; else goto _L1
_L1:
        int i;
        if(pool.debug)
        {
            out.println((new StringBuilder("DEBUG: checking for connections to prune: ")).append(System.currentTimeMillis() - pool.lastTimePruned).toString());
            out.println((new StringBuilder("DEBUG: clientTimeoutInterval: ")).append(pool.clientTimeoutInterval).toString());
        }
        i = -1 + pool.authenticatedConnections.size();
_L6:
        if(i > 0) goto _L4; else goto _L3
_L3:
        pool.lastTimePruned = System.currentTimeMillis();
_L2:
        connectionpool;
        JVM INSTR monitorexit ;
        return;
_L4:
        IMAPProtocol imapprotocol;
        imapprotocol = (IMAPProtocol)pool.authenticatedConnections.elementAt(i);
        if(pool.debug)
            out.println((new StringBuilder("DEBUG: protocol last used: ")).append(System.currentTimeMillis() - imapprotocol.getTimestamp()).toString());
        if(System.currentTimeMillis() - imapprotocol.getTimestamp() <= pool.clientTimeoutInterval)
            break MISSING_BLOCK_LABEL_277;
        if(pool.debug)
        {
            out.println("DEBUG: authenticated connection timed out");
            out.println("DEBUG: logging out the connection");
        }
        imapprotocol.removeResponseHandler(this);
        pool.authenticatedConnections.removeElementAt(i);
        Exception exception;
        try
        {
            imapprotocol.logout();
        }
        catch(ProtocolException protocolexception) { }
        i--;
        if(true) goto _L6; else goto _L5
_L5:
        exception;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void waitIfIdle()
        throws ProtocolException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(pool))
            throw new AssertionError();
        while(pool.idleState != 0) 
        {
            if(pool.idleState == 1)
            {
                pool.idleProtocol.idleAbort();
                pool.idleState = 2;
            }
            try
            {
                pool.wait();
            }
            catch(InterruptedException interruptedexception) { }
        }
    }

    boolean allowReadOnlySelect()
    {
        String s = session.getProperty((new StringBuilder("mail.")).append(name).append(".allowreadonlyselect").toString());
        return s != null && s.equalsIgnoreCase("true");
    }

    public void close()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = super.isConnected();
        if(flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        IMAPProtocol imapprotocol = null;
        boolean flag1;
        synchronized(pool)
        {
            flag1 = pool.authenticatedConnections.isEmpty();
        }
        if(!flag1)
            break MISSING_BLOCK_LABEL_127;
        boolean flag2 = pool.debug;
        imapprotocol = null;
        if(!flag2)
            break MISSING_BLOCK_LABEL_71;
        out.println("DEBUG: close() - no connections ");
        cleanup();
        releaseStoreProtocol(null);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        exception2;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception2;
        ProtocolException protocolexception;
        protocolexception;
        cleanup();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        throw exception1;
        imapprotocol = getStoreProtocol();
        synchronized(pool)
        {
            pool.authenticatedConnections.removeElement(imapprotocol);
        }
        imapprotocol.logout();
        releaseStoreProtocol(imapprotocol);
        if(true) goto _L1; else goto _L3
_L3:
        exception3;
        connectionpool1;
        JVM INSTR monitorexit ;
        throw exception3;
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        close();
    }

    int getAppendBufferSize()
    {
        return appendBufferSize;
    }

    boolean getConnectionPoolDebug()
    {
        return pool.debug;
    }

    public Folder getDefaultFolder()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        DefaultFolder defaultfolder;
        checkConnected();
        defaultfolder = new DefaultFolder(this);
        this;
        JVM INSTR monitorexit ;
        return defaultfolder;
        Exception exception;
        exception;
        throw exception;
    }

    int getFetchBlockSize()
    {
        return blksize;
    }

    public Folder getFolder(String s)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        IMAPFolder imapfolder;
        checkConnected();
        imapfolder = new IMAPFolder(s, '\uFFFF', this);
        this;
        JVM INSTR monitorexit ;
        return imapfolder;
        Exception exception;
        exception;
        throw exception;
    }

    public Folder getFolder(URLName urlname)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        IMAPFolder imapfolder;
        checkConnected();
        imapfolder = new IMAPFolder(urlname.getFile(), '\uFFFF', this);
        this;
        JVM INSTR monitorexit ;
        return imapfolder;
        Exception exception;
        exception;
        throw exception;
    }

    int getMinIdleTime()
    {
        return minIdleTime;
    }

    public Folder[] getPersonalNamespaces()
        throws MessagingException
    {
        Namespaces namespaces1 = getNamespaces();
        if(namespaces1 == null || namespaces1.personal == null)
            return super.getPersonalNamespaces();
        else
            return namespaceToFolders(namespaces1.personal, null);
    }

    IMAPProtocol getProtocol(IMAPFolder imapfolder)
        throws MessagingException
    {
        IMAPProtocol imapprotocol = null;
_L10:
        if(imapprotocol != null)
            return imapprotocol;
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        if(!pool.authenticatedConnections.isEmpty() && (pool.authenticatedConnections.size() != 1 || !pool.separateStoreConnection && !pool.storeConnectionInUse)) goto _L2; else goto _L1
_L1:
        if(debug)
            out.println("DEBUG: no connections in the pool, creating a new one");
        boolean flag = forcePasswordRefresh;
        if(!flag) goto _L4; else goto _L3
_L3:
        InetAddress inetaddress1 = InetAddress.getByName(host);
        InetAddress inetaddress = inetaddress1;
_L8:
        PasswordAuthentication passwordauthentication = session.requestPasswordAuthentication(inetaddress, port, name, null, user);
        if(passwordauthentication == null) goto _L4; else goto _L5
_L5:
        user = passwordauthentication.getUserName();
        password = passwordauthentication.getPassword();
_L4:
        IMAPProtocol imapprotocol1 = new IMAPProtocol(name, host, port, session.getDebug(), session.getDebugOut(), session.getProperties(), isSSL);
        login(imapprotocol1, user, password);
_L9:
        if(imapprotocol1 != null) goto _L7; else goto _L6
_L6:
        throw new MessagingException("connection failure");
_L11:
        Exception exception;
        imapprotocol;
        throw exception;
        unknownhostexception;
        inetaddress = null;
          goto _L8
        exception1;
        imapprotocol1 = imapprotocol;
_L13:
        Exception exception3;
        Exception exception4;
        if(imapprotocol1 != null)
            try
            {
                imapprotocol1.disconnect();
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception2) { }
            finally { }
        imapprotocol1 = null;
          goto _L9
_L2:
        if(debug)
            out.println((new StringBuilder("DEBUG: connection available -- size: ")).append(pool.authenticatedConnections.size()).toString());
        imapprotocol1 = (IMAPProtocol)pool.authenticatedConnections.lastElement();
        pool.authenticatedConnections.removeElement(imapprotocol1);
        l = System.currentTimeMillis() - imapprotocol1.getTimestamp();
        l1 = pool.serverTimeoutInterval;
        if(l <= l1)
            break MISSING_BLOCK_LABEL_361;
        imapprotocol1.noop();
        imapprotocol1.removeResponseHandler(this);
_L7:
        timeoutConnections();
        if(imapfolder == null)
            break MISSING_BLOCK_LABEL_410;
        if(pool.folders == null)
            pool.folders = new Vector();
        pool.folders.addElement(imapfolder);
        connectionpool;
        JVM INSTR monitorexit ;
        imapprotocol = imapprotocol1;
          goto _L10
        protocolexception;
        imapprotocol1.removeResponseHandler(this);
        imapprotocol1.disconnect();
_L12:
        connectionpool;
        JVM INSTR monitorexit ;
        imapprotocol = null;
          goto _L10
        exception;
          goto _L11
        exception4;
          goto _L12
        exception3;
          goto _L13
    }

    public Quota[] getQuota(String s)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkConnected();
        (Quota[])null;
        IMAPProtocol imapprotocol = null;
        Quota aquota[];
        imapprotocol = getStoreProtocol();
        aquota = imapprotocol.getQuotaRoot(s);
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_43;
        cleanup();
        this;
        JVM INSTR monitorexit ;
        return aquota;
        BadCommandException badcommandexception;
        badcommandexception;
        throw new MessagingException("QUOTA not supported", badcommandexception);
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_80;
        cleanup();
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throw new StoreClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    Session getSession()
    {
        return session;
    }

    public Folder[] getSharedNamespaces()
        throws MessagingException
    {
        Namespaces namespaces1 = getNamespaces();
        if(namespaces1 == null || namespaces1.shared == null)
            return super.getSharedNamespaces();
        else
            return namespaceToFolders(namespaces1.shared, null);
    }

    int getStatusCacheTimeout()
    {
        return statusCacheTimeout;
    }

    IMAPProtocol getStoreProtocol()
        throws ProtocolException
    {
        IMAPProtocol imapprotocol = null;
_L8:
        if(imapprotocol != null)
            return imapprotocol;
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        waitIfIdle();
        if(!pool.authenticatedConnections.isEmpty()) goto _L2; else goto _L1
_L1:
        if(pool.debug)
            out.println("DEBUG: getStoreProtocol() - no connections in the pool, creating a new one");
        IMAPProtocol imapprotocol1 = new IMAPProtocol(name, host, port, session.getDebug(), session.getDebugOut(), session.getProperties(), isSSL);
        login(imapprotocol1, user, password);
_L5:
        if(imapprotocol1 != null) goto _L4; else goto _L3
_L3:
        throw new ConnectionException("failed to create new store connection");
_L10:
        Exception exception;
        imapprotocol;
        throw exception;
        exception3;
        imapprotocol1 = imapprotocol;
_L11:
        InterruptedException interruptedexception;
        Exception exception1;
        if(imapprotocol1 != null)
            try
            {
                imapprotocol1.logout();
            }
            // Misplaced declaration of an exception variable
            catch(Exception exception2) { }
            finally { }
        imapprotocol1 = null;
          goto _L5
_L4:
        imapprotocol1.addResponseHandler(this);
        pool.authenticatedConnections.addElement(imapprotocol1);
_L9:
        flag = pool.storeConnectionInUse;
        if(!flag) goto _L7; else goto _L6
_L6:
        imapprotocol1 = null;
        try
        {
            pool.wait();
        }
        // Misplaced declaration of an exception variable
        catch(InterruptedException interruptedexception)
        {
            imapprotocol1 = null;
        }
        timeoutConnections();
        connectionpool;
        JVM INSTR monitorexit ;
        imapprotocol = imapprotocol1;
          goto _L8
_L2:
        if(pool.debug)
            out.println((new StringBuilder("DEBUG: getStoreProtocol() - connection available -- size: ")).append(pool.authenticatedConnections.size()).toString());
        imapprotocol1 = (IMAPProtocol)pool.authenticatedConnections.firstElement();
          goto _L9
_L7:
        pool.storeConnectionInUse = true;
        if(pool.debug)
            out.println("DEBUG: getStoreProtocol() -- storeConnectionInUse");
        break MISSING_BLOCK_LABEL_195;
          goto _L8
        exception;
          goto _L10
        exception1;
          goto _L11
    }

    public Folder[] getUserNamespaces(String s)
        throws MessagingException
    {
        Namespaces namespaces1 = getNamespaces();
        if(namespaces1 == null || namespaces1.otherUsers == null)
            return super.getUserNamespaces(s);
        else
            return namespaceToFolders(namespaces1.otherUsers, s);
    }

    public void handleResponse(Response response)
    {
        if(response.isOK() || response.isNO() || response.isBAD() || response.isBYE())
            handleResponseCode(response);
        if(response.isBYE())
        {
            if(debug)
                out.println("DEBUG: IMAPStore connection dead");
            if(connected)
                cleanup(response.isSynthetic());
        }
    }

    void handleResponseCode(Response response)
    {
        String s = response.getRest();
        boolean flag = s.startsWith("[");
        boolean flag1 = false;
        if(flag)
        {
            int i = s.indexOf(']');
            flag1 = false;
            if(i > 0)
            {
                boolean flag2 = s.substring(0, i + 1).equalsIgnoreCase("[ALERT]");
                flag1 = false;
                if(flag2)
                    flag1 = true;
            }
            s = s.substring(i + 1).trim();
        }
        if(flag1)
            notifyStoreListeners(1, s);
        else
        if(response.isUnTagged() && s.length() > 0)
        {
            notifyStoreListeners(2, s);
            return;
        }
    }

    public boolean hasCapability(String s)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol = null;
        boolean flag;
        imapprotocol = getStoreProtocol();
        flag = imapprotocol.hasCapability(s);
        releaseStoreProtocol(imapprotocol);
        this;
        JVM INSTR monitorexit ;
        return flag;
        ProtocolException protocolexception;
        protocolexception;
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_36;
        cleanup();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        Exception exception;
        exception;
        releaseStoreProtocol(imapprotocol);
        throw exception;
        Exception exception1;
        exception1;
        this;
        JVM INSTR monitorexit ;
        throw exception1;
    }

    boolean hasSeparateStoreConnection()
    {
        return pool.separateStoreConnection;
    }

    public void idle()
        throws MessagingException
    {
        IMAPProtocol imapprotocol;
        imapprotocol = null;
        if(!$assertionsDisabled && Thread.holdsLock(pool))
            throw new AssertionError();
        this;
        JVM INSTR monitorenter ;
        checkConnected();
        this;
        JVM INSTR monitorexit ;
        ConnectionPool connectionpool1 = pool;
        connectionpool1;
        JVM INSTR monitorenter ;
        imapprotocol = getStoreProtocol();
        if(pool.idleState != 0) goto _L2; else goto _L1
_L1:
        imapprotocol.idleStart();
        pool.idleState = 1;
        pool.idleProtocol = imapprotocol;
_L9:
        Response response = imapprotocol.readIdleResponse();
        ConnectionPool connectionpool3 = pool;
        connectionpool3;
        JVM INSTR monitorenter ;
        if(response == null) goto _L4; else goto _L3
_L3:
        if(imapprotocol.processIdleResponse(response)) goto _L5; else goto _L4
_L4:
        pool.idleState = 0;
        pool.notifyAll();
        connectionpool3;
        JVM INSTR monitorexit ;
        int i = getMinIdleTime();
        long l;
        if(i <= 0)
            break MISSING_BLOCK_LABEL_149;
        l = i;
        Thread.sleep(l);
_L10:
        synchronized(pool)
        {
            pool.idleProtocol = null;
        }
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol == null)
            cleanup();
_L7:
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        ProtocolException protocolexception;
        Exception exception1;
        ConnectionException connectionexception;
        BadCommandException badcommandexception;
        Exception exception3;
        Exception exception5;
        InterruptedException interruptedexception1;
        try
        {
            pool.wait();
        }
        catch(InterruptedException interruptedexception) { }
        connectionpool1;
        JVM INSTR monitorexit ;
        synchronized(pool)
        {
            pool.idleProtocol = null;
        }
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null) goto _L7; else goto _L6
_L6:
        cleanup();
        return;
        exception4;
        connectionpool2;
        JVM INSTR monitorexit ;
        throw exception4;
        exception3;
        connectionpool1;
        JVM INSTR monitorexit ;
        throw exception3;
        badcommandexception;
        throw new MessagingException("IDLE not supported", badcommandexception);
        exception1;
        synchronized(pool)
        {
            pool.idleProtocol = null;
        }
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol == null)
            cleanup();
        throw exception1;
_L5:
        connectionpool3;
        JVM INSTR monitorexit ;
        if(!enableImapEvents || !response.isUnTagged()) goto _L9; else goto _L8
_L8:
        notifyStoreListeners(1000, response.toString());
          goto _L9
        connectionexception;
        throw new StoreClosedException(this, connectionexception.getMessage());
        exception5;
        connectionpool3;
        JVM INSTR monitorexit ;
        throw exception5;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        exception2;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception2;
        exception6;
        connectionpool4;
        JVM INSTR monitorexit ;
        throw exception6;
        interruptedexception1;
          goto _L10
    }

    public boolean isConnected()
    {
        boolean flag = false;
        this;
        JVM INSTR monitorenter ;
        if(connected) goto _L2; else goto _L1
_L1:
        super.setConnected(false);
_L3:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        IMAPProtocol imapprotocol = null;
        imapprotocol = getStoreProtocol();
        imapprotocol.noop();
        releaseStoreProtocol(imapprotocol);
_L4:
        boolean flag1 = super.isConnected();
        flag = flag1;
          goto _L3
        ProtocolException protocolexception;
        protocolexception;
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_58;
        cleanup();
        releaseStoreProtocol(imapprotocol);
          goto _L4
        Exception exception;
        exception;
        throw exception;
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        throw exception1;
          goto _L3
    }

    boolean isConnectionPoolFull()
    {
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        if(pool.debug)
            out.println((new StringBuilder("DEBUG: current size: ")).append(pool.authenticatedConnections.size()).append("   pool size: ").append(pool.poolSize).toString());
        Exception exception;
        boolean flag;
        if(pool.authenticatedConnections.size() >= pool.poolSize)
            flag = true;
        else
            flag = false;
        connectionpool;
        JVM INSTR monitorexit ;
        return flag;
        exception;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception;
    }

    protected boolean protocolConnect(String s, int i, String s1, String s2)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(s != null && s2 != null && s1 != null) goto _L2; else goto _L1
_L1:
        PrintStream printstream;
        StringBuilder stringbuilder;
        if(!debug)
            break MISSING_BLOCK_LABEL_85;
        printstream = out;
        stringbuilder = (new StringBuilder("DEBUG: protocolConnect returning false, host=")).append(s).append(", user=").append(s1).append(", password=");
        boolean flag;
        String s3;
        if(s2 != null)
            s3 = "<non-null>";
        else
            s3 = "<null>";
        printstream.println(stringbuilder.append(s3).toString());
        flag = false;
_L5:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        if(i == -1) goto _L4; else goto _L3
_L3:
        port = i;
_L7:
        if(port == -1)
            port = defaultPort;
        boolean flag1;
        synchronized(pool)
        {
            flag1 = pool.authenticatedConnections.isEmpty();
        }
        if(!flag1)
            break MISSING_BLOCK_LABEL_301;
        IMAPProtocol imapprotocol = new IMAPProtocol(name, s, port, session.getDebug(), session.getDebugOut(), session.getProperties(), isSSL);
        if(debug)
            out.println((new StringBuilder("DEBUG: protocolConnect login, host=")).append(s).append(", user=").append(s1).append(", password=<non-null>").toString());
        login(imapprotocol, s1, s2);
        imapprotocol.addResponseHandler(this);
        host = s;
        user = s1;
        password = s2;
        synchronized(pool)
        {
            pool.authenticatedConnections.addElement(imapprotocol);
        }
        connected = true;
        flag = true;
          goto _L5
_L4:
        String s4 = session.getProperty((new StringBuilder("mail.")).append(name).append(".port").toString());
        if(s4 == null) goto _L7; else goto _L6
_L6:
        port = Integer.parseInt(s4);
          goto _L7
        Exception exception;
        exception;
_L8:
        this;
        JVM INSTR monitorexit ;
        throw exception;
        exception1;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception1;
        CommandFailedException commandfailedexception;
        commandfailedexception;
        imapprotocol = null;
_L9:
        if(imapprotocol == null)
            break MISSING_BLOCK_LABEL_393;
        imapprotocol.disconnect();
        throw new AuthenticationFailedException(commandfailedexception.getResponse().getRest());
        exception;
          goto _L8
        exception2;
        connectionpool1;
        JVM INSTR monitorexit ;
        throw exception2;
        commandfailedexception;
          goto _L9
        ProtocolException protocolexception;
        protocolexception;
_L11:
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L10:
        IOException ioexception;
        throw new MessagingException(ioexception.getMessage(), ioexception);
        ioexception;
          goto _L10
        protocolexception;
          goto _L11
        ioexception;
          goto _L10
    }

    void releaseProtocol(IMAPFolder imapfolder, IMAPProtocol imapprotocol)
    {
        ConnectionPool connectionpool = pool;
        connectionpool;
        JVM INSTR monitorenter ;
        if(imapprotocol == null) goto _L2; else goto _L1
_L1:
        if(isConnectionPoolFull()) goto _L4; else goto _L3
_L3:
        imapprotocol.addResponseHandler(this);
        pool.authenticatedConnections.addElement(imapprotocol);
        if(debug)
            out.println((new StringBuilder("DEBUG: added an Authenticated connection -- size: ")).append(pool.authenticatedConnections.size()).toString());
_L2:
        if(pool.folders != null)
            pool.folders.removeElement(imapfolder);
        timeoutConnections();
        connectionpool;
        JVM INSTR monitorexit ;
        return;
_L4:
        if(debug)
            out.println("DEBUG: pool is full, not adding an Authenticated connection");
        try
        {
            imapprotocol.logout();
        }
        catch(ProtocolException protocolexception) { }
          goto _L2
        Exception exception;
        exception;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception;
    }

    void releaseStoreProtocol(IMAPProtocol imapprotocol)
    {
        if(imapprotocol == null)
            return;
        synchronized(pool)
        {
            pool.storeConnectionInUse = false;
            pool.notifyAll();
            if(pool.debug)
                out.println("DEBUG: releaseStoreProtocol()");
            timeoutConnections();
        }
        return;
        exception;
        connectionpool;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setPassword(String s)
    {
        this;
        JVM INSTR monitorenter ;
        password = s;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setQuota(Quota quota)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkConnected();
        IMAPProtocol imapprotocol = null;
        imapprotocol = getStoreProtocol();
        imapprotocol.setQuota(quota);
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_31;
        cleanup();
        this;
        JVM INSTR monitorexit ;
        return;
        BadCommandException badcommandexception;
        badcommandexception;
        throw new MessagingException("QUOTA not supported", badcommandexception);
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        if(imapprotocol != null)
            break MISSING_BLOCK_LABEL_64;
        cleanup();
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throw new StoreClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public void setUsername(String s)
    {
        this;
        JVM INSTR monitorenter ;
        user = s;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    static final boolean $assertionsDisabled = false;
    public static final int RESPONSE = 1000;
    private int appendBufferSize;
    private String authorizationID;
    private int blksize;
    private volatile boolean connected;
    private int defaultPort;
    private boolean disableAuthLogin;
    private boolean disableAuthPlain;
    private boolean enableImapEvents;
    private boolean enableSASL;
    private boolean enableStartTLS;
    private boolean forcePasswordRefresh;
    private String host;
    private boolean isSSL;
    private int minIdleTime;
    private String name;
    private Namespaces namespaces;
    private PrintStream out;
    private String password;
    private ConnectionPool pool;
    private int port;
    private String proxyAuthUser;
    private String saslMechanisms[];
    private String saslRealm;
    private int statusCacheTimeout;
    private String user;

    static 
    {
        boolean flag;
        if(!com/sun/mail/imap/IMAPStore.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }
}
