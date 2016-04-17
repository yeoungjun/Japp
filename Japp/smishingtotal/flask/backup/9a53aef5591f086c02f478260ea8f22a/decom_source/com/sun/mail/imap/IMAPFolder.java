// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.*;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.imap.protocol.Status;
import com.sun.mail.imap.protocol.UID;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPStore, AppendUID, MessageLiteral, Utility, 
//            IMAPMessage, DefaultFolder, Rights, ACL

public class IMAPFolder extends Folder
    implements UIDFolder, ResponseHandler
{
    public static class FetchProfileItem extends javax.mail.FetchProfile.Item
    {

        public static final FetchProfileItem HEADERS = new FetchProfileItem("HEADERS");
        public static final FetchProfileItem SIZE = new FetchProfileItem("SIZE");


        protected FetchProfileItem(String s)
        {
            super(s);
        }
    }

    public static interface ProtocolCommand
    {

        public abstract Object doCommand(IMAPProtocol imapprotocol)
            throws ProtocolException;
    }


    protected IMAPFolder(ListInfo listinfo, IMAPStore imapstore)
    {
        this(listinfo.name, listinfo.separator, imapstore);
        if(listinfo.hasInferiors)
            type = 2 | type;
        if(listinfo.canOpen)
            type = 1 | type;
        exists = true;
        attributes = listinfo.attrs;
    }

    protected IMAPFolder(String s, char c, IMAPStore imapstore)
    {
        super(imapstore);
        exists = false;
        isNamespace = false;
        opened = false;
        reallyClosed = true;
        idleState = 0;
        total = -1;
        recent = -1;
        realTotal = -1;
        uidvalidity = -1L;
        uidnext = -1L;
        doExpungeNotification = true;
        cachedStatus = null;
        cachedStatusTime = 0L;
        debug = false;
        if(s == null)
            throw new NullPointerException("Folder name is null");
        fullName = s;
        separator = c;
        messageCacheLock = new Object();
        debug = imapstore.getSession().getDebug();
        connectionPoolDebug = imapstore.getConnectionPoolDebug();
        out = imapstore.getSession().getDebugOut();
        if(out == null)
            out = System.out;
        isNamespace = false;
        if(c != '\uFFFF' && c != 0)
        {
            int i = fullName.indexOf(c);
            if(i > 0 && i == -1 + fullName.length())
            {
                fullName = fullName.substring(0, i);
                isNamespace = true;
            }
        }
    }

    protected IMAPFolder(String s, char c, IMAPStore imapstore, boolean flag)
    {
        this(s, c, imapstore);
        isNamespace = flag;
    }

    private void checkClosed()
    {
        if(opened)
            throw new IllegalStateException("This operation is not allowed on an open folder");
        else
            return;
    }

    private void checkExists()
        throws MessagingException
    {
        if(!exists && !exists())
            throw new FolderNotFoundException(this, (new StringBuilder(String.valueOf(fullName))).append(" not found").toString());
        else
            return;
    }

    private void checkFlags(Flags flags)
        throws MessagingException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
            throw new AssertionError();
        if(mode != 2)
            throw new IllegalStateException((new StringBuilder("Cannot change flags on READ_ONLY folder: ")).append(fullName).toString());
        else
            return;
    }

    private void checkOpened()
        throws FolderClosedException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
            throw new AssertionError();
        if(!opened)
        {
            if(reallyClosed)
                throw new IllegalStateException("This operation is not allowed on a closed folder");
            else
                throw new FolderClosedException(this, "Lost folder connection to server");
        } else
        {
            return;
        }
    }

    private void checkRange(int i)
        throws MessagingException
    {
        if(i < 1)
            throw new IndexOutOfBoundsException();
        if(i > total)
        {
            synchronized(messageCacheLock)
            {
                keepConnectionAlive(false);
            }
            if(i > total)
                throw new IndexOutOfBoundsException();
        }
        return;
        JVM INSTR monitorenter ;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    private void cleanup(boolean flag)
    {
        releaseProtocol(flag);
        protocol = null;
        messageCache = null;
        uidTable = null;
        exists = false;
        attributes = null;
        opened = false;
        idleState = 0;
        notifyConnectionListeners(3);
    }

    private void close(boolean flag, boolean flag1)
        throws MessagingException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
            throw new AssertionError();
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        if(!opened && reallyClosed)
            throw new IllegalStateException("This operation is not allowed on a closed folder");
        break MISSING_BLOCK_LABEL_59;
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        reallyClosed = true;
        if(opened)
            break MISSING_BLOCK_LABEL_74;
        obj;
        JVM INSTR monitorexit ;
        return;
        waitIfIdle();
        if(!flag1) goto _L2; else goto _L1
_L1:
        if(debug)
            out.println((new StringBuilder("DEBUG: forcing folder ")).append(fullName).append(" to close").toString());
        if(protocol != null)
            protocol.disconnect();
_L3:
        if(opened)
            cleanup(true);
        obj;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(!((IMAPStore)store).isConnectionPoolFull())
            break MISSING_BLOCK_LABEL_243;
        if(debug)
            out.println("DEBUG: pool is full, not adding an Authenticated connection");
        if(!flag)
            break MISSING_BLOCK_LABEL_192;
        protocol.close();
        if(protocol != null)
            protocol.logout();
          goto _L3
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        Exception exception1;
        exception1;
        if(opened)
            cleanup(true);
        throw exception1;
        if(flag) goto _L5; else goto _L4
_L4:
        int i = mode;
        if(i != 2) goto _L5; else goto _L6
_L6:
        protocol.examine(fullName);
_L5:
        if(protocol == null) goto _L3; else goto _L7
_L7:
        protocol.close();
          goto _L3
        ProtocolException protocolexception1;
        protocolexception1;
        if(protocol == null) goto _L5; else goto _L8
_L8:
        protocol.disconnect();
          goto _L5
    }

    private Folder[] doList(final String pattern, final boolean subscribed)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkExists();
        if(isDirectory()) goto _L2; else goto _L1
_L1:
        Object aobj[] = new Folder[0];
_L6:
        this;
        JVM INSTR monitorexit ;
        return ((Folder []) (aobj));
_L2:
        final char c;
        ListInfo alistinfo[];
        c = getSeparator();
        alistinfo = (ListInfo[])doCommandIgnoreFailure(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                if(subscribed)
                    return imapprotocol.lsub("", (new StringBuilder(String.valueOf(fullName))).append(c).append(pattern).toString());
                else
                    return imapprotocol.list("", (new StringBuilder(String.valueOf(fullName))).append(c).append(pattern).toString());
            }

            final IMAPFolder this$0;
            private final char val$c;
            private final String val$pattern;
            private final boolean val$subscribed;

            
            {
                this$0 = IMAPFolder.this;
                subscribed = flag;
                c = c1;
                pattern = s;
                super();
            }
        });
        if(alistinfo != null)
            break MISSING_BLOCK_LABEL_65;
        aobj = new Folder[0];
        continue; /* Loop/switch isn't completed */
        int i = alistinfo.length;
        int j;
        j = 0;
        if(i <= 0)
            break MISSING_BLOCK_LABEL_123;
        boolean flag = alistinfo[0].name.equals((new StringBuilder(String.valueOf(fullName))).append(c).toString());
        j = 0;
        if(flag)
            j = 1;
        aobj = new IMAPFolder[alistinfo.length - j];
        int k = j;
_L4:
        if(k >= alistinfo.length)
            break; /* Loop/switch isn't completed */
        aobj[k - j] = new IMAPFolder(alistinfo[k], (IMAPStore)store);
        k++;
        if(true) goto _L4; else goto _L3
_L3:
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception;
        exception;
        throw exception;
    }

    private int findName(ListInfo alistinfo[], String s)
    {
        do
        {
            int i;
            for(i = 0; i >= alistinfo.length || alistinfo[i].name.equals(s);)
            {
                if(i >= alistinfo.length)
                    i = 0;
                return i;
            }

            i++;
        } while(true);
    }

    private IMAPProtocol getProtocol()
        throws ProtocolException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(messageCacheLock))
        {
            throw new AssertionError();
        } else
        {
            waitIfIdle();
            return protocol;
        }
    }

    private Status getStatus()
        throws ProtocolException
    {
        int i;
        IMAPProtocol imapprotocol;
        i = ((IMAPStore)store).getStatusCacheTimeout();
        if(i > 0 && cachedStatus != null && System.currentTimeMillis() - cachedStatusTime < (long)i)
            return cachedStatus;
        imapprotocol = null;
        Status status;
        imapprotocol = getStoreProtocol();
        status = imapprotocol.status(fullName, null);
        if(i <= 0)
            break MISSING_BLOCK_LABEL_76;
        cachedStatus = status;
        cachedStatusTime = System.currentTimeMillis();
        releaseStoreProtocol(imapprotocol);
        return status;
        Exception exception;
        exception;
        releaseStoreProtocol(imapprotocol);
        throw exception;
    }

    private boolean isDirectory()
    {
        return (2 & type) != 0;
    }

    private void keepConnectionAlive(boolean flag)
        throws ProtocolException
    {
        IMAPProtocol imapprotocol;
        if(System.currentTimeMillis() - protocol.getTimestamp() > 1000L)
        {
            waitIfIdle();
            protocol.noop();
        }
        if(!flag || !((IMAPStore)store).hasSeparateStoreConnection())
            break MISSING_BLOCK_LABEL_89;
        imapprotocol = null;
        imapprotocol = ((IMAPStore)store).getStoreProtocol();
        if(System.currentTimeMillis() - imapprotocol.getTimestamp() > 1000L)
            imapprotocol.noop();
        ((IMAPStore)store).releaseStoreProtocol(imapprotocol);
        return;
        Exception exception;
        exception;
        ((IMAPStore)store).releaseStoreProtocol(imapprotocol);
        throw exception;
    }

    private void releaseProtocol(boolean flag)
    {
label0:
        {
            if(protocol != null)
            {
                protocol.removeResponseHandler(this);
                if(!flag)
                    break label0;
                ((IMAPStore)store).releaseProtocol(this, protocol);
            }
            return;
        }
        ((IMAPStore)store).releaseProtocol(this, null);
    }

    private void setACL(final ACL acl, final char mod)
        throws MessagingException
    {
        doOptionalCommand("ACL not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                imapprotocol.setACL(fullName, mod, acl);
                return null;
            }

            final IMAPFolder this$0;
            private final ACL val$acl;
            private final char val$mod;

            
            {
                this$0 = IMAPFolder.this;
                mod = c;
                acl = acl1;
                super();
            }
        });
    }

    private void throwClosedException(ConnectionException connectionexception)
        throws FolderClosedException, StoreClosedException
    {
        this;
        JVM INSTR monitorenter ;
        if(protocol != null && connectionexception.getProtocol() == protocol || protocol == null && !reallyClosed)
            throw new FolderClosedException(this, connectionexception.getMessage());
        break MISSING_BLOCK_LABEL_52;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        throw new StoreClosedException(store, connectionexception.getMessage());
    }

    public void addACL(ACL acl)
        throws MessagingException
    {
        setACL(acl, '\0');
    }

    public Message[] addMessages(Message amessage[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        MimeMessage amimemessage[];
        AppendUID aappenduid[];
        checkOpened();
        amimemessage = new MimeMessage[amessage.length];
        aappenduid = appendUIDMessages(amessage);
        int i = 0;
_L2:
        int j = aappenduid.length;
        if(i < j)
            break MISSING_BLOCK_LABEL_38;
        this;
        JVM INSTR monitorexit ;
        return amimemessage;
        AppendUID appenduid = aappenduid[i];
        if(appenduid == null)
            break MISSING_BLOCK_LABEL_84;
        long l;
        long l1;
        l = appenduid.uidvalidity;
        l1 = uidvalidity;
        Exception exception;
        if(l == l1)
            try
            {
                amimemessage[i] = getMessageByUID(appenduid.uid);
            }
            catch(MessagingException messagingexception) { }
            finally
            {
                this;
            }
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void addRights(ACL acl)
        throws MessagingException
    {
        setACL(acl, '+');
    }

    public void appendMessages(Message amessage[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        checkExists();
        i = ((IMAPStore)store).getAppendBufferSize();
        int j = 0;
_L2:
        int k = amessage.length;
        if(j < k)
            break MISSING_BLOCK_LABEL_34;
        this;
        JVM INSTR monitorexit ;
        return;
        Message message = amessage[j];
        Exception exception;
        MessageRemovedException messageremovedexception;
        IOException ioexception;
        int l;
        MessageLiteral messageliteral;
        Date date;
        Date date1;
        if(message.getSize() > i)
            l = 0;
        else
            l = i;
        messageliteral = new MessageLiteral(message, l);
        date = message.getReceivedDate();
        if(date != null)
            break MISSING_BLOCK_LABEL_84;
        date = message.getSentDate();
        date1 = date;
        doCommand(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                imapprotocol.append(fullName, f, dd, mos);
                return null;
            }

            final IMAPFolder this$0;
            private final Date val$dd;
            private final Flags val$f;
            private final MessageLiteral val$mos;

            
            {
                this$0 = IMAPFolder.this;
                f = flags;
                dd = date;
                mos = messageliteral;
                super();
            }
        });
        break MISSING_BLOCK_LABEL_135;
        ioexception;
        throw new MessagingException("IOException while appending messages", ioexception);
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        messageremovedexception;
        j++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public AppendUID[] appendUIDMessages(Message amessage[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        AppendUID aappenduid[];
        checkExists();
        i = ((IMAPStore)store).getAppendBufferSize();
        aappenduid = new AppendUID[amessage.length];
        int j = 0;
_L2:
        int k = amessage.length;
        if(j < k)
            break MISSING_BLOCK_LABEL_43;
        this;
        JVM INSTR monitorexit ;
        return aappenduid;
        Message message = amessage[j];
        Exception exception;
        MessageRemovedException messageremovedexception;
        IOException ioexception;
        int l;
        MessageLiteral messageliteral;
        Date date;
        Date date1;
        if(message.getSize() > i)
            l = 0;
        else
            l = i;
        messageliteral = new MessageLiteral(message, l);
        date = message.getReceivedDate();
        if(date != null)
            break MISSING_BLOCK_LABEL_93;
        date = message.getSentDate();
        date1 = date;
        aappenduid[j] = (AppendUID)doCommand(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.appenduid(fullName, f, dd, mos);
            }

            final IMAPFolder this$0;
            private final Date val$dd;
            private final Flags val$f;
            private final MessageLiteral val$mos;

            
            {
                this$0 = IMAPFolder.this;
                f = flags;
                dd = date;
                mos = messageliteral;
                super();
            }
        });
        break MISSING_BLOCK_LABEL_151;
        ioexception;
        throw new MessagingException("IOException while appending messages", ioexception);
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        messageremovedexception;
        j++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void close(boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        close(flag, false);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void copyMessages(Message amessage[], Folder folder)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        checkOpened();
        i = amessage.length;
        if(i != 0) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(folder.getStore() != store)
            break MISSING_BLOCK_LABEL_194;
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        com.sun.mail.imap.protocol.MessageSet amessageset[];
        imapprotocol = getProtocol();
        amessageset = Utility.toMessageSet(amessage, null);
        if(amessageset != null) goto _L4; else goto _L3
_L3:
        throw new MessageRemovedException("Messages have been removed");
        CommandFailedException commandfailedexception;
        commandfailedexception;
        if(commandfailedexception.getMessage().indexOf("TRYCREATE") != -1)
            throw new FolderNotFoundException(folder, (new StringBuilder(String.valueOf(folder.getFullName()))).append(" does not exist").toString());
          goto _L5
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        imapprotocol.copy(amessageset, folder.getFullName());
        obj;
        JVM INSTR monitorexit ;
          goto _L1
_L5:
        throw new MessagingException(commandfailedexception.getMessage(), commandfailedexception);
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        super.copyMessages(amessage, folder);
          goto _L1
    }

    public boolean create(final int type)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        final char sep;
        int i = type & 1;
        sep = '\0';
        if(i != 0)
            break MISSING_BLOCK_LABEL_17;
        sep = getSeparator();
        Object obj = doCommandIgnoreFailure(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                if((1 & type) == 0)
                {
                    imapprotocol.create((new StringBuilder(String.valueOf(fullName))).append(sep).toString());
                } else
                {
                    imapprotocol.create(fullName);
                    if((2 & type) != 0)
                    {
                        ListInfo alistinfo[] = imapprotocol.list("", fullName);
                        if(alistinfo != null && !alistinfo[0].hasInferiors)
                        {
                            imapprotocol.delete(fullName);
                            throw new ProtocolException("Unsupported type");
                        }
                    }
                }
                return Boolean.TRUE;
            }

            final IMAPFolder this$0;
            private final char val$sep;
            private final int val$type;

            
            {
                this$0 = IMAPFolder.this;
                type = i;
                sep = c;
                super();
            }
        });
        if(obj != null) goto _L2; else goto _L1
_L1:
        boolean flag = false;
_L4:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        flag = exists();
        if(!flag)
            continue; /* Loop/switch isn't completed */
        notifyFolderListeners(1);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public boolean delete(boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkClosed();
        if(!flag) goto _L2; else goto _L1
_L1:
        Folder afolder[] = list();
        int i = 0;
_L8:
        if(i < afolder.length) goto _L3; else goto _L2
_L2:
        Object obj = doCommandIgnoreFailure(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                imapprotocol.delete(fullName);
                return Boolean.TRUE;
            }

            final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
        });
        boolean flag1 = false;
        if(obj != null) goto _L5; else goto _L4
_L4:
        this;
        JVM INSTR monitorexit ;
        return flag1;
_L3:
        afolder[i].delete(flag);
        i++;
        continue; /* Loop/switch isn't completed */
_L5:
        exists = false;
        attributes = null;
        notifyFolderListeners(2);
        flag1 = true;
        if(true) goto _L4; else goto _L6
_L6:
        Exception exception;
        exception;
        throw exception;
        if(true) goto _L8; else goto _L7
_L7:
    }

    public Object doCommand(ProtocolCommand protocolcommand)
        throws MessagingException
    {
        Object obj;
        try
        {
            obj = doProtocolCommand(protocolcommand);
        }
        catch(ConnectionException connectionexception)
        {
            throwClosedException(connectionexception);
            return null;
        }
        catch(ProtocolException protocolexception)
        {
            throw new MessagingException(protocolexception.getMessage(), protocolexception);
        }
        return obj;
    }

    public Object doCommandIgnoreFailure(ProtocolCommand protocolcommand)
        throws MessagingException
    {
        Object obj;
        try
        {
            obj = doProtocolCommand(protocolcommand);
        }
        catch(CommandFailedException commandfailedexception)
        {
            return null;
        }
        catch(ConnectionException connectionexception)
        {
            throwClosedException(connectionexception);
            return null;
        }
        catch(ProtocolException protocolexception)
        {
            throw new MessagingException(protocolexception.getMessage(), protocolexception);
        }
        return obj;
    }

    public Object doOptionalCommand(String s, ProtocolCommand protocolcommand)
        throws MessagingException
    {
        Object obj;
        try
        {
            obj = doProtocolCommand(protocolcommand);
        }
        catch(BadCommandException badcommandexception)
        {
            throw new MessagingException(s, badcommandexception);
        }
        catch(ConnectionException connectionexception)
        {
            throwClosedException(connectionexception);
            return null;
        }
        catch(ProtocolException protocolexception)
        {
            throw new MessagingException(protocolexception.getMessage(), protocolexception);
        }
        return obj;
    }

    protected Object doProtocolCommand(ProtocolCommand protocolcommand)
        throws ProtocolException
    {
        this;
        JVM INSTR monitorenter ;
        if(!opened || ((IMAPStore)store).hasSeparateStoreConnection())
            break MISSING_BLOCK_LABEL_64;
        Object obj2;
        synchronized(messageCacheLock)
        {
            obj2 = protocolcommand.doCommand(getProtocol());
        }
        this;
        JVM INSTR monitorexit ;
        return obj2;
        exception2;
        obj1;
        JVM INSTR monitorexit ;
        throw exception2;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        this;
        JVM INSTR monitorexit ;
        IMAPProtocol imapprotocol = null;
        Object obj;
        imapprotocol = getStoreProtocol();
        obj = protocolcommand.doCommand(imapprotocol);
        releaseStoreProtocol(imapprotocol);
        return obj;
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        throw exception1;
    }

    public boolean exists()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        (ListInfo[])null;
        if(!isNamespace || separator == 0) goto _L2; else goto _L1
_L1:
        final String lname = (new StringBuilder(String.valueOf(fullName))).append(separator).toString();
_L5:
        ListInfo alistinfo[] = (ListInfo[])doCommand(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.list("", lname);
            }

            final IMAPFolder this$0;
            private final String val$lname;

            
            {
                this$0 = IMAPFolder.this;
                lname = s;
                super();
            }
        });
        if(alistinfo == null) goto _L4; else goto _L3
_L3:
        int i;
        int j;
        i = findName(alistinfo, lname);
        fullName = alistinfo[i].name;
        separator = alistinfo[i].separator;
        j = fullName.length();
        if(separator == 0 || j <= 0)
            break MISSING_BLOCK_LABEL_157;
        if(fullName.charAt(j - 1) == separator)
            fullName = fullName.substring(0, j - 1);
        type = 0;
        if(alistinfo[i].hasInferiors)
            type = 2 | type;
        if(alistinfo[i].canOpen)
            type = 1 | type;
        exists = true;
        attributes = alistinfo[i].attrs;
_L6:
        boolean flag = exists;
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        lname = fullName;
          goto _L5
_L4:
        exists = opened;
        attributes = null;
          goto _L6
        Exception exception;
        exception;
        throw exception;
          goto _L5
    }

    public Message[] expunge()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Message amessage[] = expunge(null);
        this;
        JVM INSTR monitorexit ;
        return amessage;
        Exception exception;
        exception;
        throw exception;
    }

    public Message[] expunge(Message amessage[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Vector vector;
        checkOpened();
        vector = new Vector();
        if(amessage == null)
            break MISSING_BLOCK_LABEL_42;
        FetchProfile fetchprofile = new FetchProfile();
        fetchprofile.add(javax.mail.UIDFolder.FetchProfileItem.UID);
        fetch(amessage, fetchprofile);
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        doExpungeNotification = false;
        IMAPProtocol imapprotocol = getProtocol();
        if(amessage == null) goto _L2; else goto _L1
_L1:
        imapprotocol.uidexpunge(Utility.toUIDSet(amessage));
_L5:
        doExpungeNotification = true;
        int i = 0;
_L7:
        if(i < messageCache.size()) goto _L4; else goto _L3
_L3:
        obj;
        JVM INSTR monitorexit ;
        Message amessage1[];
        total = messageCache.size();
        amessage1 = new Message[vector.size()];
        vector.copyInto(amessage1);
        if(amessage1.length > 0)
            notifyMessageRemovedListeners(true, amessage1);
        this;
        JVM INSTR monitorexit ;
        return amessage1;
_L2:
        imapprotocol.expunge();
          goto _L5
        CommandFailedException commandfailedexception;
        commandfailedexception;
        if(mode != 2)
            throw new IllegalStateException((new StringBuilder("Cannot expunge READ_ONLY folder: ")).append(fullName).toString());
        break MISSING_BLOCK_LABEL_211;
        Exception exception2;
        exception2;
        doExpungeNotification = true;
        throw exception2;
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        throw new MessagingException(commandfailedexception.getMessage(), commandfailedexception);
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L4:
        IMAPMessage imapmessage;
        imapmessage = (IMAPMessage)messageCache.elementAt(i);
        if(!imapmessage.isExpunged())
            break MISSING_BLOCK_LABEL_339;
        vector.addElement(imapmessage);
        messageCache.removeElementAt(i);
        if(uidTable == null) goto _L7; else goto _L6
_L6:
        long l = imapmessage.getUID();
        if(l == -1L) goto _L7; else goto _L8
_L8:
        uidTable.remove(new Long(l));
          goto _L7
        imapmessage.setMessageNumber(imapmessage.getSequenceNumber());
        i++;
          goto _L7
    }

    public void fetch(Message amessage[], FetchProfile fetchprofile)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkOpened();
        IMAPMessage.fetch(this, amessage, fetchprofile);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void forceClose()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        close(false, true);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public ACL[] getACL()
        throws MessagingException
    {
        return (ACL[])doOptionalCommand("ACL not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.getACL(fullName);
            }

            final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
        });
    }

    public String[] getAttributes()
        throws MessagingException
    {
        if(attributes == null)
            exists();
        return (String[])attributes.clone();
    }

    public int getDeletedMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(opened) goto _L2; else goto _L1
_L1:
        checkExists();
        int i = -1;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
_L2:
        Flags flags;
        flags = new Flags();
        flags.add(javax.mail.Flags.Flag.DELETED);
        synchronized(messageCacheLock)
        {
            i = getProtocol().search(new FlagTerm(flags, true)).length;
        }
        if(true) goto _L4; else goto _L3
_L3:
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public Folder getFolder(String s)
        throws MessagingException
    {
        if(attributes != null && !isDirectory())
        {
            throw new MessagingException("Cannot contain subfolders");
        } else
        {
            char c = getSeparator();
            return new IMAPFolder((new StringBuilder(String.valueOf(fullName))).append(c).append(s).toString(), c, (IMAPStore)store);
        }
    }

    public String getFullName()
    {
        this;
        JVM INSTR monitorenter ;
        String s = fullName;
        this;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    public Message getMessage(int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Message message;
        checkOpened();
        checkRange(i);
        message = (Message)messageCache.elementAt(i - 1);
        this;
        JVM INSTR monitorexit ;
        return message;
        Exception exception;
        exception;
        throw exception;
    }

    IMAPMessage getMessageBySeqNumber(int i)
    {
        int j = i - 1;
_L6:
        if(j < total) goto _L2; else goto _L1
_L1:
        IMAPMessage imapmessage = null;
_L4:
        return imapmessage;
_L2:
        imapmessage = (IMAPMessage)messageCache.elementAt(j);
        if(imapmessage.getSequenceNumber() == i) goto _L4; else goto _L3
_L3:
        j++;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public Message getMessageByUID(long l)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkOpened();
        IMAPMessage imapmessage = null;
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        Long long1 = new Long(l);
        if(uidTable == null) goto _L2; else goto _L1
_L1:
        imapmessage = (IMAPMessage)uidTable.get(long1);
        if(imapmessage == null) goto _L4; else goto _L3
_L3:
        obj;
        JVM INSTR monitorexit ;
        IMAPMessage imapmessage1 = imapmessage;
_L6:
        this;
        JVM INSTR monitorexit ;
        return imapmessage1;
_L2:
        uidTable = new Hashtable();
_L4:
        UID uid = getProtocol().fetchSequenceNumber(l);
        if(uid == null)
            break MISSING_BLOCK_LABEL_137;
        if(uid.seqnum <= total)
        {
            imapmessage = getMessageBySeqNumber(uid.seqnum);
            imapmessage.setUID(uid.uid);
            uidTable.put(long1, imapmessage);
        }
        obj;
        JVM INSTR monitorexit ;
        imapmessage1 = imapmessage;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public int getMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(opened) goto _L2; else goto _L1
_L1:
        checkExists();
        int i = getStatus().total;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
        BadCommandException badcommandexception;
        badcommandexception;
        IMAPProtocol imapprotocol = null;
        imapprotocol = getStoreProtocol();
        MailboxInfo mailboxinfo = imapprotocol.examine(fullName);
        imapprotocol.close();
        i = mailboxinfo.total;
        releaseStoreProtocol(imapprotocol);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        ProtocolException protocolexception2;
        protocolexception2;
        throw new MessagingException(protocolexception2.getMessage(), protocolexception2);
        Exception exception2;
        exception2;
        releaseStoreProtocol(imapprotocol);
        throw exception2;
        ConnectionException connectionexception1;
        connectionexception1;
        throw new StoreClosedException(store, connectionexception1.getMessage());
        ProtocolException protocolexception1;
        protocolexception1;
        throw new MessagingException(protocolexception1.getMessage(), protocolexception1);
        JVM INSTR monitorenter ;
_L2:
        synchronized(messageCacheLock)
        {
            keepConnectionAlive(true);
            i = total;
        }
        if(true) goto _L4; else goto _L3
_L3:
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public Message[] getMessagesByUID(long l, long l1)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkOpened();
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        UID auid[];
        Message amessage[];
        if(uidTable == null)
            uidTable = new Hashtable();
        auid = getProtocol().fetchSequenceNumbers(l, l1);
        amessage = new Message[auid.length];
        int i = 0;
_L1:
        if(i < auid.length)
            break MISSING_BLOCK_LABEL_71;
        obj;
        JVM INSTR monitorexit ;
        this;
        JVM INSTR monitorexit ;
        return amessage;
        IMAPMessage imapmessage = getMessageBySeqNumber(auid[i].seqnum);
        imapmessage.setUID(auid[i].uid);
        amessage[i] = imapmessage;
        uidTable.put(new Long(auid[i].uid), imapmessage);
        i++;
          goto _L1
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public Message[] getMessagesByUID(long al[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkOpened();
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        long al1[] = al;
        if(uidTable == null) goto _L2; else goto _L1
_L1:
        Vector vector = new Vector();
        int i = 0;
_L17:
        if(i < al.length) goto _L4; else goto _L3
_L3:
        int j;
        j = vector.size();
        al1 = new long[j];
        int k = 0;
          goto _L5
_L14:
        if(al1.length <= 0) goto _L7; else goto _L6
_L6:
        UID auid[] = getProtocol().fetchSequenceNumbers(al1);
        int i1 = 0;
_L12:
        if(i1 < auid.length) goto _L8; else goto _L7
_L7:
        Message amessage[] = new Message[al.length];
        int l = 0;
_L13:
        if(l < al.length) goto _L10; else goto _L9
_L9:
        obj;
        JVM INSTR monitorexit ;
        this;
        JVM INSTR monitorexit ;
        return amessage;
_L4:
        Hashtable hashtable = uidTable;
        Long long1 = new Long(al[i]);
        if(!hashtable.containsKey(long1))
            vector.addElement(long1);
          goto _L11
_L15:
        al1[k] = ((Long)vector.elementAt(k)).longValue();
        k++;
        continue; /* Loop/switch isn't completed */
_L2:
        uidTable = new Hashtable();
        break; /* Loop/switch isn't completed */
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L8:
        IMAPMessage imapmessage = getMessageBySeqNumber(auid[i1].seqnum);
        imapmessage.setUID(auid[i1].uid);
        uidTable.put(new Long(auid[i1].uid), imapmessage);
        i1++;
          goto _L12
_L10:
        amessage[l] = (Message)uidTable.get(new Long(al[l]));
        l++;
          goto _L13
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L5:
        if(k < j) goto _L15; else goto _L14
_L11:
        i++;
        if(true) goto _L17; else goto _L16
_L16:
    }

    public String getName()
    {
        this;
        JVM INSTR monitorenter ;
        String s = name;
        if(s != null)
            break MISSING_BLOCK_LABEL_35;
        Exception exception;
        String s1;
        try
        {
            name = fullName.substring(1 + fullName.lastIndexOf(getSeparator()));
        }
        catch(MessagingException messagingexception) { }
        s1 = name;
        this;
        JVM INSTR monitorexit ;
        return s1;
        exception;
        throw exception;
    }

    public int getNewMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(opened) goto _L2; else goto _L1
_L1:
        checkExists();
        int i = getStatus().recent;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
        BadCommandException badcommandexception;
        badcommandexception;
        IMAPProtocol imapprotocol = null;
        imapprotocol = getStoreProtocol();
        MailboxInfo mailboxinfo = imapprotocol.examine(fullName);
        imapprotocol.close();
        i = mailboxinfo.recent;
        releaseStoreProtocol(imapprotocol);
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
        ProtocolException protocolexception2;
        protocolexception2;
        throw new MessagingException(protocolexception2.getMessage(), protocolexception2);
        Exception exception2;
        exception2;
        releaseStoreProtocol(imapprotocol);
        throw exception2;
        ConnectionException connectionexception1;
        connectionexception1;
        throw new StoreClosedException(store, connectionexception1.getMessage());
        ProtocolException protocolexception1;
        protocolexception1;
        throw new MessagingException(protocolexception1.getMessage(), protocolexception1);
        JVM INSTR monitorenter ;
_L2:
        synchronized(messageCacheLock)
        {
            keepConnectionAlive(true);
            i = recent;
        }
        if(true) goto _L4; else goto _L3
_L3:
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public Folder getParent()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        char c;
        int i;
        c = getSeparator();
        i = fullName.lastIndexOf(c);
        if(i == -1) goto _L2; else goto _L1
_L1:
        IMAPFolder imapfolder = new IMAPFolder(fullName.substring(0, i), c, (IMAPStore)store);
        Object obj = imapfolder;
_L4:
        this;
        JVM INSTR monitorexit ;
        return ((Folder) (obj));
_L2:
        DefaultFolder defaultfolder = new DefaultFolder((IMAPStore)store);
        obj = defaultfolder;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public Flags getPermanentFlags()
    {
        this;
        JVM INSTR monitorenter ;
        Flags flags = (Flags)permanentFlags.clone();
        this;
        JVM INSTR monitorexit ;
        return flags;
        Exception exception;
        exception;
        throw exception;
    }

    public Quota[] getQuota()
        throws MessagingException
    {
        return (Quota[])doOptionalCommand("QUOTA not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.getQuotaRoot(fullName);
            }

            final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
        });
    }

    public char getSeparator()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        ListInfo alistinfo[];
        if(separator != '\uFFFF')
            break MISSING_BLOCK_LABEL_49;
        (ListInfo[])null;
        alistinfo = (ListInfo[])doCommand(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                if(imapprotocol.isREV1())
                    return imapprotocol.list(fullName, "");
                else
                    return imapprotocol.list("", fullName);
            }

            final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
        });
        if(alistinfo == null)
            break MISSING_BLOCK_LABEL_58;
        separator = alistinfo[0].separator;
_L1:
        char c = separator;
        this;
        JVM INSTR monitorexit ;
        return c;
        separator = '/';
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    protected IMAPProtocol getStoreProtocol()
        throws ProtocolException
    {
        this;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        if(connectionPoolDebug)
            out.println("DEBUG: getStoreProtocol() - borrowing a connection");
        imapprotocol = ((IMAPStore)store).getStoreProtocol();
        this;
        JVM INSTR monitorexit ;
        return imapprotocol;
        Exception exception;
        exception;
        throw exception;
    }

    public int getType()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(!opened)
            break MISSING_BLOCK_LABEL_30;
        if(attributes == null)
            exists();
_L1:
        int i = type;
        this;
        JVM INSTR monitorexit ;
        return i;
        checkExists();
          goto _L1
        Exception exception;
        exception;
        throw exception;
    }

    public long getUID(Message message)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(message.getFolder() != this)
            throw new NoSuchElementException("Message does not belong to this folder");
        break MISSING_BLOCK_LABEL_26;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        IMAPMessage imapmessage;
        long l;
        checkOpened();
        imapmessage = (IMAPMessage)message;
        l = imapmessage.getUID();
        long l1 = l;
        if(l1 == -1L) goto _L2; else goto _L1
_L1:
        long l2 = l1;
_L4:
        this;
        JVM INSTR monitorexit ;
        return l2;
_L2:
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        UID uid;
        IMAPProtocol imapprotocol = getProtocol();
        imapmessage.checkExpunged();
        uid = imapprotocol.fetchUID(imapmessage.getSequenceNumber());
        if(uid == null)
            break MISSING_BLOCK_LABEL_147;
        l1 = uid.uid;
        imapmessage.setUID(l1);
        if(uidTable == null)
            uidTable = new Hashtable();
        uidTable.put(new Long(l1), imapmessage);
        obj;
        JVM INSTR monitorexit ;
        l2 = l1;
        if(true) goto _L4; else goto _L3
_L3:
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public long getUIDNext()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(!opened) goto _L2; else goto _L1
_L1:
        long l = uidnext;
_L4:
        this;
        JVM INSTR monitorexit ;
        return l;
_L2:
        IMAPProtocol imapprotocol = null;
        Status status1;
        imapprotocol = getStoreProtocol();
        String as[] = {
            "UIDNEXT"
        };
        status1 = imapprotocol.status(fullName, as);
        Status status = status1;
        releaseStoreProtocol(imapprotocol);
_L5:
        l = status.uidnext;
        if(true) goto _L4; else goto _L3
_L3:
        BadCommandException badcommandexception;
        badcommandexception;
        throw new MessagingException("Cannot obtain UIDNext", badcommandexception);
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throwClosedException(connectionexception);
        releaseStoreProtocol(imapprotocol);
        status = null;
          goto _L5
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public long getUIDValidity()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(!opened) goto _L2; else goto _L1
_L1:
        long l = uidvalidity;
_L4:
        this;
        JVM INSTR monitorexit ;
        return l;
_L2:
        IMAPProtocol imapprotocol = null;
        Status status1;
        imapprotocol = getStoreProtocol();
        String as[] = {
            "UIDVALIDITY"
        };
        status1 = imapprotocol.status(fullName, as);
        Status status = status1;
        releaseStoreProtocol(imapprotocol);
_L5:
        l = status.uidvalidity;
        if(true) goto _L4; else goto _L3
_L3:
        BadCommandException badcommandexception;
        badcommandexception;
        throw new MessagingException("Cannot obtain UIDValidity", badcommandexception);
        Exception exception1;
        exception1;
        releaseStoreProtocol(imapprotocol);
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throwClosedException(connectionexception);
        releaseStoreProtocol(imapprotocol);
        status = null;
          goto _L5
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public int getUnreadMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(opened) goto _L2; else goto _L1
_L1:
        checkExists();
        int i = getStatus().unseen;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
        BadCommandException badcommandexception;
        badcommandexception;
        i = -1;
        continue; /* Loop/switch isn't completed */
        ConnectionException connectionexception1;
        connectionexception1;
        throw new StoreClosedException(store, connectionexception1.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception1;
        protocolexception1;
        throw new MessagingException(protocolexception1.getMessage(), protocolexception1);
_L2:
        Flags flags;
        flags = new Flags();
        flags.add(javax.mail.Flags.Flag.SEEN);
        synchronized(messageCacheLock)
        {
            i = getProtocol().search(new FlagTerm(flags, false)).length;
        }
        if(true) goto _L4; else goto _L3
_L3:
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public void handleResponse(Response response)
    {
        if(!$assertionsDisabled && !Thread.holdsLock(messageCacheLock))
            throw new AssertionError();
        if(response.isOK() || response.isNO() || response.isBAD() || response.isBYE())
            ((IMAPStore)store).handleResponseCode(response);
        if(!response.isBYE()) goto _L2; else goto _L1
_L1:
        if(opened)
            cleanup(false);
_L4:
        return;
_L2:
        IMAPResponse imapresponse;
        if(response.isOK() || !response.isUnTagged())
            continue; /* Loop/switch isn't completed */
        if(!(response instanceof IMAPResponse))
        {
            out.println((new StringBuilder("UNEXPECTED RESPONSE : ")).append(response.toString()).toString());
            out.println("CONTACT javamail@sun.com");
            return;
        }
        imapresponse = (IMAPResponse)response;
        if(!imapresponse.keyEquals("EXISTS"))
            break; /* Loop/switch isn't completed */
        int j = imapresponse.getNumber();
        if(j > realTotal)
        {
            int k = j - realTotal;
            Message amessage[] = new Message[k];
            int l = 0;
            do
            {
                if(l >= k)
                {
                    notifyMessageAddedListeners(amessage);
                    return;
                }
                int i1 = 1 + total;
                total = i1;
                int j1 = 1 + realTotal;
                realTotal = j1;
                IMAPMessage imapmessage3 = new IMAPMessage(this, i1, j1);
                amessage[l] = imapmessage3;
                messageCache.addElement(imapmessage3);
                l++;
            } while(true);
        }
        if(true) goto _L4; else goto _L3
_L3:
        IMAPMessage imapmessage1;
        int i;
        if(!imapresponse.keyEquals("EXPUNGE"))
            break MISSING_BLOCK_LABEL_386;
        imapmessage1 = getMessageBySeqNumber(imapresponse.getNumber());
        imapmessage1.setExpunged(true);
        i = imapmessage1.getMessageNumber();
_L6:
label0:
        {
            if(i < total)
                break label0;
            realTotal = -1 + realTotal;
            if(doExpungeNotification)
            {
                notifyMessageRemovedListeners(false, new Message[] {
                    imapmessage1
                });
                return;
            }
        }
        if(true) goto _L4; else goto _L5
_L5:
        IMAPMessage imapmessage2 = (IMAPMessage)messageCache.elementAt(i);
        if(!imapmessage2.isExpunged())
            imapmessage2.setSequenceNumber(-1 + imapmessage2.getSequenceNumber());
        i++;
          goto _L6
        FetchResponse fetchresponse;
        Flags flags;
        if(!imapresponse.keyEquals("FETCH"))
            continue; /* Loop/switch isn't completed */
        if(!$assertionsDisabled && !(imapresponse instanceof FetchResponse))
            throw new AssertionError("!ir instanceof FetchResponse");
        fetchresponse = (FetchResponse)imapresponse;
        flags = (Flags)fetchresponse.getItem(javax/mail/Flags);
        if(flags == null) goto _L4; else goto _L7
_L7:
        IMAPMessage imapmessage = getMessageBySeqNumber(fetchresponse.getNumber());
        if(imapmessage == null) goto _L4; else goto _L8
_L8:
        imapmessage._setFlags(flags);
        notifyMessageChangedListeners(1, imapmessage);
        return;
        if(!imapresponse.keyEquals("RECENT")) goto _L4; else goto _L9
_L9:
        recent = imapresponse.getNumber();
        return;
    }

    void handleResponses(Response aresponse[])
    {
        int i = 0;
        do
        {
            if(i >= aresponse.length)
                return;
            if(aresponse[i] != null)
                handleResponse(aresponse[i]);
            i++;
        } while(true);
    }

    public boolean hasNewMessages()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(!opened) goto _L2; else goto _L1
_L1:
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        keepConnectionAlive(true);
        int i = recent;
        boolean flag;
        flag = false;
        if(i > 0)
            flag = true;
        obj;
        JVM INSTR monitorexit ;
_L4:
        this;
        JVM INSTR monitorexit ;
        return flag;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L2:
        Boolean boolean1;
        checkExists();
        boolean1 = (Boolean)doCommandIgnoreFailure(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                ListInfo alistinfo[] = imapprotocol.list("", fullName);
                if(alistinfo != null)
                {
                    if(alistinfo[0].changeState == 1)
                        return Boolean.TRUE;
                    if(alistinfo[0].changeState == 2)
                        return Boolean.FALSE;
                }
                if(getStatus().recent > 0)
                    return Boolean.TRUE;
                else
                    return Boolean.FALSE;
            }

            final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
        });
        flag = false;
        if(boolean1 == null)
            continue; /* Loop/switch isn't completed */
        boolean flag1 = boolean1.booleanValue();
        flag = flag1;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public void idle()
        throws MessagingException
    {
        if(!$assertionsDisabled && Thread.holdsLock(this))
            throw new AssertionError();
        this;
        JVM INSTR monitorenter ;
        checkOpened();
        if(((Boolean)doOptionalCommand("IDLE not supported", new ProtocolCommand() {

        public Object doCommand(IMAPProtocol imapprotocol)
            throws ProtocolException
        {
            if(idleState == 0)
            {
                imapprotocol.idleStart();
                idleState = 1;
                return Boolean.TRUE;
            }
            try
            {
                messageCacheLock.wait();
            }
            catch(InterruptedException interruptedexception1) { }
            return Boolean.FALSE;
        }

        final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
    })).booleanValue())
            break MISSING_BLOCK_LABEL_54;
        this;
        JVM INSTR monitorexit ;
        return;
        this;
        JVM INSTR monitorexit ;
_L2:
        Response response = protocol.readIdleResponse();
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        if(response == null)
            break MISSING_BLOCK_LABEL_95;
        if(protocol != null && protocol.processIdleResponse(response))
            break MISSING_BLOCK_LABEL_146;
        idleState = 0;
        messageCacheLock.notifyAll();
        obj;
        JVM INSTR monitorexit ;
        int i = ((IMAPStore)store).getMinIdleTime();
        Exception exception;
        ProtocolException protocolexception;
        ConnectionException connectionexception;
        Exception exception1;
        if(i > 0)
        {
            long l = i;
            try
            {
                Thread.sleep(l);
                return;
            }
            catch(InterruptedException interruptedexception)
            {
                return;
            }
        } else
        {
            return;
        }
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        obj;
        JVM INSTR monitorexit ;
        continue; /* Loop/switch isn't completed */
        exception1;
        obj;
        JVM INSTR monitorexit ;
        try
        {
            throw exception1;
        }
        // Misplaced declaration of an exception variable
        catch(ConnectionException connectionexception)
        {
            throwClosedException(connectionexception);
        }
        // Misplaced declaration of an exception variable
        catch(ProtocolException protocolexception)
        {
            throw new MessagingException(protocolexception.getMessage(), protocolexception);
        }
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean isOpen()
    {
        this;
        JVM INSTR monitorenter ;
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        boolean flag = opened;
        if(!flag)
            break MISSING_BLOCK_LABEL_25;
        Exception exception;
        Exception exception1;
        boolean flag1;
        try
        {
            keepConnectionAlive(false);
        }
        catch(ProtocolException protocolexception) { }
        obj;
        JVM INSTR monitorexit ;
        flag1 = opened;
        this;
        JVM INSTR monitorexit ;
        return flag1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean isSubscribed()
    {
        this;
        JVM INSTR monitorenter ;
        ListInfo alistinfo[] = (ListInfo[])null;
        if(!isNamespace || separator == 0) goto _L2; else goto _L1
_L1:
        String s = (new StringBuilder(String.valueOf(fullName))).append(separator).toString();
        final String lname = s;
_L5:
        Exception exception;
        boolean flag;
        try
        {
            alistinfo = (ListInfo[])doProtocolCommand(new ProtocolCommand() {

                public Object doCommand(IMAPProtocol imapprotocol)
                    throws ProtocolException
                {
                    return imapprotocol.lsub("", lname);
                }

                final IMAPFolder this$0;
                private final String val$lname;

            
            {
                this$0 = IMAPFolder.this;
                lname = s;
                super();
            }
            });
        }
        catch(ProtocolException protocolexception) { }
        finally
        {
            this;
        }
        if(alistinfo == null) goto _L4; else goto _L3
_L3:
        flag = alistinfo[findName(alistinfo, lname)].canOpen;
_L7:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        lname = fullName;
          goto _L5
_L4:
        flag = false;
        if(true) goto _L7; else goto _L6
_L6:
        JVM INSTR monitorexit ;
        throw exception;
    }

    public Folder[] list(String s)
        throws MessagingException
    {
        return doList(s, false);
    }

    public Rights[] listRights(final String name)
        throws MessagingException
    {
        return (Rights[])doOptionalCommand("ACL not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.listRights(fullName, name);
            }

            final IMAPFolder this$0;
            private final String val$name;

            
            {
                this$0 = IMAPFolder.this;
                name = s;
                super();
            }
        });
    }

    public Folder[] listSubscribed(String s)
        throws MessagingException
    {
        return doList(s, true);
    }

    public Rights myRights()
        throws MessagingException
    {
        return (Rights)doOptionalCommand("ACL not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                return imapprotocol.myRights(fullName);
            }

            final IMAPFolder this$0;

            
            {
                this$0 = IMAPFolder.this;
                super();
            }
        });
    }

    public void open(int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkClosed();
        protocol = ((IMAPStore)store).getProtocol(this);
        Object obj = null;
        Object obj1 = messageCacheLock;
        obj1;
        JVM INSTR monitorenter ;
        protocol.addResponseHandler(this);
        if(i != 1) goto _L2; else goto _L1
_L1:
        MailboxInfo mailboxinfo2 = protocol.examine(fullName);
        MailboxInfo mailboxinfo1 = mailboxinfo2;
_L12:
        if(mailboxinfo1.mode == i) goto _L4; else goto _L3
_L3:
        if(i != 2) goto _L6; else goto _L5
_L5:
        if(mailboxinfo1.mode != 1 || !((IMAPStore)store).allowReadOnlySelect()) goto _L6; else goto _L4
_L4:
        opened = true;
        reallyClosed = false;
        mode = mailboxinfo1.mode;
        availableFlags = mailboxinfo1.availableFlags;
        permanentFlags = mailboxinfo1.permanentFlags;
        int j = mailboxinfo1.total;
        realTotal = j;
        total = j;
        recent = mailboxinfo1.recent;
        uidvalidity = mailboxinfo1.uidvalidity;
        uidnext = mailboxinfo1.uidnext;
        messageCache = new Vector(total);
        int k = 0;
_L15:
        if(k < total) goto _L8; else goto _L7
_L7:
        obj1;
        JVM INSTR monitorexit ;
_L13:
        if(obj == null) goto _L10; else goto _L9
_L9:
        checkExists();
        if((1 & type) == 0)
            throw new MessagingException("folder cannot contain messages");
          goto _L11
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L2:
        MailboxInfo mailboxinfo = protocol.select(fullName);
        mailboxinfo1 = mailboxinfo;
          goto _L12
        CommandFailedException commandfailedexception;
        commandfailedexception;
        releaseProtocol(true);
        protocol = null;
        obj = commandfailedexception;
        obj1;
        JVM INSTR monitorexit ;
          goto _L13
        Exception exception1;
        exception1;
        obj1;
        JVM INSTR monitorexit ;
        throw exception1;
        ProtocolException protocolexception;
        protocolexception;
        ProtocolException protocolexception2;
        Exception exception4;
        ProtocolException protocolexception3;
        try
        {
            protocol.logout();
        }
        catch(ProtocolException protocolexception1) { }
        finally { }
        releaseProtocol(false);
        protocol = null;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L6:
        protocol.close();
        releaseProtocol(true);
_L14:
        protocol = null;
        throw new ReadOnlyFolderException(this, "Cannot open in desired mode");
        protocolexception2;
        protocol.logout();
        releaseProtocol(false);
          goto _L14
        protocolexception3;
        releaseProtocol(false);
          goto _L14
        exception4;
        releaseProtocol(false);
        throw exception4;
_L8:
        messageCache.addElement(new IMAPMessage(this, k + 1, k + 1));
        k++;
          goto _L15
_L11:
        throw new MessagingException(((CommandFailedException) (obj)).getMessage(), ((Exception) (obj)));
_L10:
        exists = true;
        attributes = null;
        type = 1;
        notifyConnectionListeners(1);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception3;
        exception3;
          goto _L14
    }

    protected void releaseStoreProtocol(IMAPProtocol imapprotocol)
    {
        this;
        JVM INSTR monitorenter ;
        if(imapprotocol != protocol)
            ((IMAPStore)store).releaseStoreProtocol(imapprotocol);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeACL(final String name)
        throws MessagingException
    {
        doOptionalCommand("ACL not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                imapprotocol.deleteACL(fullName, name);
                return null;
            }

            final IMAPFolder this$0;
            private final String val$name;

            
            {
                this$0 = IMAPFolder.this;
                name = s;
                super();
            }
        });
    }

    public void removeRights(ACL acl)
        throws MessagingException
    {
        setACL(acl, '-');
    }

    public boolean renameTo(final Folder f)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkClosed();
        checkExists();
        if(f.getStore() != store)
            throw new MessagingException("Can't rename across Stores");
        break MISSING_BLOCK_LABEL_37;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        Object obj = doCommandIgnoreFailure(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                imapprotocol.rename(fullName, f.getFullName());
                return Boolean.TRUE;
            }

            final IMAPFolder this$0;
            private final Folder val$f;

            
            {
                this$0 = IMAPFolder.this;
                f = folder;
                super();
            }
        });
        boolean flag = false;
        if(obj != null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        exists = false;
        attributes = null;
        notifyFolderRenamedListeners(f);
        flag = true;
        if(true) goto _L1; else goto _L3
_L3:
    }

    public Message[] search(SearchTerm searchterm)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkOpened();
        Object aobj[] = (Message[])null;
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        int ai[] = getProtocol().search(searchterm);
        if(ai == null) goto _L2; else goto _L1
_L1:
        aobj = new IMAPMessage[ai.length];
        int i = 0;
_L4:
        if(i < ai.length) goto _L3; else goto _L2
_L2:
        obj;
        JVM INSTR monitorexit ;
_L5:
        this;
        JVM INSTR monitorexit ;
        return ((Message []) (aobj));
_L3:
        aobj[i] = getMessageBySeqNumber(ai[i]);
        i++;
          goto _L4
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        CommandFailedException commandfailedexception;
        commandfailedexception;
        aobj = super.search(searchterm);
          goto _L5
        SearchException searchexception;
        searchexception;
        aobj = super.search(searchterm);
          goto _L5
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
          goto _L4
    }

    public Message[] search(SearchTerm searchterm, Message amessage[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        checkOpened();
        i = amessage.length;
        if(i != 0) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return amessage;
_L2:
        Object aobj[] = (Message[])null;
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        com.sun.mail.imap.protocol.MessageSet amessageset[];
        imapprotocol = getProtocol();
        amessageset = Utility.toMessageSet(amessage, null);
        if(amessageset != null)
            break MISSING_BLOCK_LABEL_87;
        throw new MessageRemovedException("Messages have been removed");
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        CommandFailedException commandfailedexception;
        commandfailedexception;
        Message amessage1[] = super.search(searchterm, amessage);
        amessage = amessage1;
          goto _L1
        int ai[] = imapprotocol.search(amessageset, searchterm);
        if(ai == null) goto _L4; else goto _L3
_L3:
        aobj = new IMAPMessage[ai.length];
        int j = 0;
_L6:
        if(j < ai.length) goto _L5; else goto _L4
_L4:
        obj;
        JVM INSTR monitorexit ;
        amessage = ((Message []) (aobj));
          goto _L1
_L5:
        aobj[j] = getMessageBySeqNumber(ai[j]);
        j++;
          goto _L6
        SearchException searchexception;
        searchexception;
        amessage = super.search(searchterm, amessage);
          goto _L1
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
          goto _L1
    }

    public void setFlags(Message amessage[], Flags flags, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        checkOpened();
        checkFlags(flags);
        i = amessage.length;
        if(i != 0) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        Object obj = messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        com.sun.mail.imap.protocol.MessageSet amessageset[];
        imapprotocol = getProtocol();
        amessageset = Utility.toMessageSet(amessage, null);
        if(amessageset != null)
            break MISSING_BLOCK_LABEL_92;
        throw new MessageRemovedException("Messages have been removed");
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(this, connectionexception.getMessage());
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        imapprotocol.storeFlags(amessageset, flags, flag);
        obj;
        JVM INSTR monitorexit ;
          goto _L1
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
          goto _L1
    }

    public void setQuota(final Quota quota)
        throws MessagingException
    {
        doOptionalCommand("QUOTA not supported", new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                imapprotocol.setQuota(quota);
                return null;
            }

            final IMAPFolder this$0;
            private final Quota val$quota;

            
            {
                this$0 = IMAPFolder.this;
                quota = quota1;
                super();
            }
        });
    }

    public void setSubscribed(final boolean subscribe)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        doCommandIgnoreFailure(new ProtocolCommand() {

            public Object doCommand(IMAPProtocol imapprotocol)
                throws ProtocolException
            {
                if(subscribe)
                    imapprotocol.subscribe(fullName);
                else
                    imapprotocol.unsubscribe(fullName);
                return null;
            }

            final IMAPFolder this$0;
            private final boolean val$subscribe;

            
            {
                this$0 = IMAPFolder.this;
                subscribe = flag;
                super();
            }
        });
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    void waitIfIdle()
        throws ProtocolException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(messageCacheLock))
            throw new AssertionError();
        while(idleState != 0) 
        {
            if(idleState == 1)
            {
                protocol.idleAbort();
                idleState = 2;
            }
            try
            {
                messageCacheLock.wait();
            }
            catch(InterruptedException interruptedexception) { }
        }
    }

    static final boolean $assertionsDisabled = false;
    private static final int ABORTING = 2;
    private static final int IDLE = 1;
    private static final int RUNNING = 0;
    protected static final char UNKNOWN_SEPARATOR = 65535;
    protected String attributes[];
    protected Flags availableFlags;
    private Status cachedStatus;
    private long cachedStatusTime;
    private boolean connectionPoolDebug;
    private boolean debug;
    private boolean doExpungeNotification;
    protected boolean exists;
    protected String fullName;
    private int idleState;
    protected boolean isNamespace;
    protected Vector messageCache;
    protected Object messageCacheLock;
    protected String name;
    private boolean opened;
    private PrintStream out;
    protected Flags permanentFlags;
    protected IMAPProtocol protocol;
    private int realTotal;
    private boolean reallyClosed;
    private int recent;
    protected char separator;
    private int total;
    protected int type;
    protected Hashtable uidTable;
    private long uidnext;
    private long uidvalidity;

    static 
    {
        boolean flag;
        if(!com/sun/mail/imap/IMAPFolder.desiredAssertionStatus())
            flag = true;
        else
            flag = false;
        $assertionsDisabled = flag;
    }



}
