// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.util.Vector;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageChangedListener;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.mail.search.SearchTerm;

// Referenced classes of package javax.mail:
//            EventQueue, MessagingException, FolderNotFoundException, MessageRemovedException, 
//            Message, Store, URLName, MethodNotSupportedException, 
//            FetchProfile, Flags

public abstract class Folder
{
    static class TerminatorEvent extends MailEvent
    {

        public void dispatch(Object obj)
        {
            Thread.currentThread().interrupt();
        }

        private static final long serialVersionUID = 0x3442ac84f29e98b5L;

        TerminatorEvent()
        {
            super(new Object());
        }
    }


    protected Folder(Store store1)
    {
        mode = -1;
        connectionListeners = null;
        folderListeners = null;
        messageCountListeners = null;
        messageChangedListeners = null;
        qLock = new Object();
        store = store1;
    }

    private void queueEvent(MailEvent mailevent, Vector vector)
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

    public void addFolderListener(FolderListener folderlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(folderListeners == null)
            folderListeners = new Vector();
        folderListeners.addElement(folderlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addMessageChangedListener(MessageChangedListener messagechangedlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(messageChangedListeners == null)
            messageChangedListeners = new Vector();
        messageChangedListeners.addElement(messagechangedlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addMessageCountListener(MessageCountListener messagecountlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(messageCountListeners == null)
            messageCountListeners = new Vector();
        messageCountListeners.addElement(messagecountlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public abstract void appendMessages(Message amessage[])
        throws MessagingException;

    public abstract void close(boolean flag)
        throws MessagingException;

    public void copyMessages(Message amessage[], Folder folder)
        throws MessagingException
    {
        if(!folder.exists())
        {
            throw new FolderNotFoundException((new StringBuilder(String.valueOf(folder.getFullName()))).append(" does not exist").toString(), folder);
        } else
        {
            folder.appendMessages(amessage);
            return;
        }
    }

    public abstract boolean create(int i)
        throws MessagingException;

    public abstract boolean delete(boolean flag)
        throws MessagingException;

    public abstract boolean exists()
        throws MessagingException;

    public abstract Message[] expunge()
        throws MessagingException;

    public void fetch(Message amessage[], FetchProfile fetchprofile)
        throws MessagingException
    {
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        terminateQueue();
    }

    public int getDeletedMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = isOpen();
        if(flag) goto _L2; else goto _L1
_L1:
        int i = -1;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
_L2:
        i = 0;
        int j = getMessageCount();
        int k = 1;
_L6:
        if(k > j) goto _L4; else goto _L3
_L3:
        boolean flag1 = getMessage(k).isSet(Flags.Flag.DELETED);
        if(flag1)
            i++;
_L7:
        k++;
        if(true) goto _L6; else goto _L5
_L5:
          goto _L4
        Exception exception;
        exception;
        throw exception;
        MessageRemovedException messageremovedexception;
        messageremovedexception;
          goto _L7
    }

    public abstract Folder getFolder(String s)
        throws MessagingException;

    public abstract String getFullName();

    public abstract Message getMessage(int i)
        throws MessagingException;

    public abstract int getMessageCount()
        throws MessagingException;

    public Message[] getMessages()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(!isOpen())
            throw new IllegalStateException("Folder not open");
        break MISSING_BLOCK_LABEL_24;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        int i;
        Message amessage[];
        i = getMessageCount();
        amessage = new Message[i];
        int j = 1;
_L2:
        int k;
        if(j > i)
            return amessage;
        k = j - 1;
        amessage[k] = getMessage(j);
        j++;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Message[] getMessages(int i, int j)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int k = 1 + (j - i);
        Message amessage[] = new Message[k];
        int l = i;
_L2:
        int i1;
        if(l > j)
            return amessage;
        i1 = l - i;
        amessage[i1] = getMessage(l);
        l++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        throw exception;
    }

    public Message[] getMessages(int ai[])
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        Message amessage[];
        i = ai.length;
        amessage = new Message[i];
        int j = 0;
_L2:
        if(j >= i)
            return amessage;
        amessage[j] = getMessage(ai[j]);
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        Exception exception;
        exception;
        throw exception;
    }

    public int getMode()
    {
        if(!isOpen())
            throw new IllegalStateException("Folder not open");
        else
            return mode;
    }

    public abstract String getName();

    public int getNewMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = isOpen();
        if(flag) goto _L2; else goto _L1
_L1:
        int i = -1;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
_L2:
        i = 0;
        int j = getMessageCount();
        int k = 1;
_L6:
        if(k > j) goto _L4; else goto _L3
_L3:
        boolean flag1 = getMessage(k).isSet(Flags.Flag.RECENT);
        if(flag1)
            i++;
_L7:
        k++;
        if(true) goto _L6; else goto _L5
_L5:
          goto _L4
        Exception exception;
        exception;
        throw exception;
        MessageRemovedException messageremovedexception;
        messageremovedexception;
          goto _L7
    }

    public abstract Folder getParent()
        throws MessagingException;

    public abstract Flags getPermanentFlags();

    public abstract char getSeparator()
        throws MessagingException;

    public Store getStore()
    {
        return store;
    }

    public abstract int getType()
        throws MessagingException;

    public URLName getURLName()
        throws MessagingException
    {
        URLName urlname = getStore().getURLName();
        String s = getFullName();
        StringBuffer stringbuffer = new StringBuffer();
        getSeparator();
        if(s != null)
            stringbuffer.append(s);
        return new URLName(urlname.getProtocol(), urlname.getHost(), urlname.getPort(), stringbuffer.toString(), urlname.getUsername(), null);
    }

    public int getUnreadMessageCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = isOpen();
        if(flag) goto _L2; else goto _L1
_L1:
        int i = -1;
_L4:
        this;
        JVM INSTR monitorexit ;
        return i;
_L2:
        i = 0;
        int j = getMessageCount();
        int k = 1;
_L6:
        if(k > j) goto _L4; else goto _L3
_L3:
        boolean flag1 = getMessage(k).isSet(Flags.Flag.SEEN);
        if(!flag1)
            i++;
_L7:
        k++;
        if(true) goto _L6; else goto _L5
_L5:
          goto _L4
        Exception exception;
        exception;
        throw exception;
        MessageRemovedException messageremovedexception;
        messageremovedexception;
          goto _L7
    }

    public abstract boolean hasNewMessages()
        throws MessagingException;

    public abstract boolean isOpen();

    public boolean isSubscribed()
    {
        return true;
    }

    public Folder[] list()
        throws MessagingException
    {
        return list("%");
    }

    public abstract Folder[] list(String s)
        throws MessagingException;

    public Folder[] listSubscribed()
        throws MessagingException
    {
        return listSubscribed("%");
    }

    public Folder[] listSubscribed(String s)
        throws MessagingException
    {
        return list(s);
    }

    protected void notifyConnectionListeners(int i)
    {
        if(connectionListeners != null)
            queueEvent(new ConnectionEvent(this, i), connectionListeners);
        if(i == 3)
            terminateQueue();
    }

    protected void notifyFolderListeners(int i)
    {
        if(folderListeners != null)
            queueEvent(new FolderEvent(this, this, i), folderListeners);
        store.notifyFolderListeners(i, this);
    }

    protected void notifyFolderRenamedListeners(Folder folder)
    {
        if(folderListeners != null)
            queueEvent(new FolderEvent(this, this, folder, 3), folderListeners);
        store.notifyFolderRenamedListeners(this, folder);
    }

    protected void notifyMessageAddedListeners(Message amessage[])
    {
        if(messageCountListeners == null)
        {
            return;
        } else
        {
            queueEvent(new MessageCountEvent(this, 1, false, amessage), messageCountListeners);
            return;
        }
    }

    protected void notifyMessageChangedListeners(int i, Message message)
    {
        if(messageChangedListeners == null)
        {
            return;
        } else
        {
            queueEvent(new MessageChangedEvent(this, i, message), messageChangedListeners);
            return;
        }
    }

    protected void notifyMessageRemovedListeners(boolean flag, Message amessage[])
    {
        if(messageCountListeners == null)
        {
            return;
        } else
        {
            queueEvent(new MessageCountEvent(this, 2, flag, amessage), messageCountListeners);
            return;
        }
    }

    public abstract void open(int i)
        throws MessagingException;

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

    public void removeFolderListener(FolderListener folderlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(folderListeners != null)
            folderListeners.removeElement(folderlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeMessageChangedListener(MessageChangedListener messagechangedlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(messageChangedListeners != null)
            messageChangedListeners.removeElement(messagechangedlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeMessageCountListener(MessageCountListener messagecountlistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(messageCountListeners != null)
            messageCountListeners.removeElement(messagecountlistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public abstract boolean renameTo(Folder folder)
        throws MessagingException;

    public Message[] search(SearchTerm searchterm)
        throws MessagingException
    {
        return search(searchterm, getMessages());
    }

    public Message[] search(SearchTerm searchterm, Message amessage[])
        throws MessagingException
    {
        Vector vector = new Vector();
        int i = 0;
        do
        {
            if(i >= amessage.length)
            {
                Message amessage1[] = new Message[vector.size()];
                vector.copyInto(amessage1);
                return amessage1;
            }
            try
            {
                if(amessage[i].match(searchterm))
                    vector.addElement(amessage[i]);
            }
            catch(MessageRemovedException messageremovedexception) { }
            i++;
        } while(true);
    }

    public void setFlags(int i, int j, Flags flags, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int k = i;
        do
        {
            if(k > j)
                return;
            Exception exception;
            try
            {
                getMessage(k).setFlags(flags, flag);
            }
            catch(MessageRemovedException messageremovedexception) { }
            finally
            {
                this;
            }
            k++;
        } while(true);
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setFlags(int ai[], Flags flags, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
_L2:
        int j = ai.length;
        if(i < j)
            break MISSING_BLOCK_LABEL_19;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        try
        {
            getMessage(ai[i]).setFlags(flags, flag);
        }
        catch(MessageRemovedException messageremovedexception) { }
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

    public void setFlags(Message amessage[], Flags flags, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
_L2:
        int j = amessage.length;
        if(i < j)
            break MISSING_BLOCK_LABEL_19;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        try
        {
            amessage[i].setFlags(flags, flag);
        }
        catch(MessageRemovedException messageremovedexception) { }
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

    public void setSubscribed(boolean flag)
        throws MessagingException
    {
        throw new MethodNotSupportedException();
    }

    public String toString()
    {
        String s = getFullName();
        if(s != null)
            return s;
        else
            return super.toString();
    }

    public static final int HOLDS_FOLDERS = 2;
    public static final int HOLDS_MESSAGES = 1;
    public static final int READ_ONLY = 1;
    public static final int READ_WRITE = 2;
    private volatile Vector connectionListeners;
    private volatile Vector folderListeners;
    private volatile Vector messageChangedListeners;
    private volatile Vector messageCountListeners;
    protected int mode;
    private EventQueue q;
    private Object qLock;
    protected Store store;
}
