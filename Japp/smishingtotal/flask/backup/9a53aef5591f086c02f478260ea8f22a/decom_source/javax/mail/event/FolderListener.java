// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import java.util.EventListener;

// Referenced classes of package javax.mail.event:
//            FolderEvent

public interface FolderListener
    extends EventListener
{

    public abstract void folderCreated(FolderEvent folderevent);

    public abstract void folderDeleted(FolderEvent folderevent);

    public abstract void folderRenamed(FolderEvent folderevent);
}
