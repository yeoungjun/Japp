// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.util.Vector;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;

// Referenced classes of package javax.mail:
//            Service, MessagingException, Folder, Session, 
//            URLName

public abstract class Store extends Service
{

    protected Store(Session session, URLName urlname)
    {
        super(session, urlname);
        storeListeners = null;
        folderListeners = null;
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

    public void addStoreListener(StoreListener storelistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(storeListeners == null)
            storeListeners = new Vector();
        storeListeners.addElement(storelistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public abstract Folder getDefaultFolder()
        throws MessagingException;

    public abstract Folder getFolder(String s)
        throws MessagingException;

    public abstract Folder getFolder(URLName urlname)
        throws MessagingException;

    public Folder[] getPersonalNamespaces()
        throws MessagingException
    {
        Folder afolder[] = new Folder[1];
        afolder[0] = getDefaultFolder();
        return afolder;
    }

    public Folder[] getSharedNamespaces()
        throws MessagingException
    {
        return new Folder[0];
    }

    public Folder[] getUserNamespaces(String s)
        throws MessagingException
    {
        return new Folder[0];
    }

    protected void notifyFolderListeners(int i, Folder folder)
    {
        if(folderListeners == null)
        {
            return;
        } else
        {
            queueEvent(new FolderEvent(this, folder, i), folderListeners);
            return;
        }
    }

    protected void notifyFolderRenamedListeners(Folder folder, Folder folder1)
    {
        if(folderListeners == null)
        {
            return;
        } else
        {
            queueEvent(new FolderEvent(this, folder, folder1, 3), folderListeners);
            return;
        }
    }

    protected void notifyStoreListeners(int i, String s)
    {
        if(storeListeners == null)
        {
            return;
        } else
        {
            queueEvent(new StoreEvent(this, i, s), storeListeners);
            return;
        }
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

    public void removeStoreListener(StoreListener storelistener)
    {
        this;
        JVM INSTR monitorenter ;
        if(storeListeners != null)
            storeListeners.removeElement(storelistener);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private volatile Vector folderListeners;
    private volatile Vector storeListeners;
}
