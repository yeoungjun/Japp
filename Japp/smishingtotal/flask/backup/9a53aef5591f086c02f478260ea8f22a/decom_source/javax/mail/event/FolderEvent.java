// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.event;

import javax.mail.Folder;

// Referenced classes of package javax.mail.event:
//            MailEvent, FolderListener

public class FolderEvent extends MailEvent
{

    public FolderEvent(Object obj, Folder folder1, int i)
    {
        this(obj, folder1, folder1, i);
    }

    public FolderEvent(Object obj, Folder folder1, Folder folder2, int i)
    {
        super(obj);
        folder = folder1;
        newFolder = folder2;
        type = i;
    }

    public void dispatch(Object obj)
    {
        if(type == 1)
        {
            ((FolderListener)obj).folderCreated(this);
        } else
        {
            if(type == 2)
            {
                ((FolderListener)obj).folderDeleted(this);
                return;
            }
            if(type == 3)
            {
                ((FolderListener)obj).folderRenamed(this);
                return;
            }
        }
    }

    public Folder getFolder()
    {
        return folder;
    }

    public Folder getNewFolder()
    {
        return newFolder;
    }

    public int getType()
    {
        return type;
    }

    public static final int CREATED = 1;
    public static final int DELETED = 2;
    public static final int RENAMED = 3;
    private static final long serialVersionUID = 0x493fb076540416e3L;
    protected transient Folder folder;
    protected transient Folder newFolder;
    protected int type;
}
