// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Folder

public class FolderClosedException extends MessagingException
{

    public FolderClosedException(Folder folder1)
    {
        this(folder1, null);
    }

    public FolderClosedException(Folder folder1, String s)
    {
        super(s);
        folder = folder1;
    }

    public Folder getFolder()
    {
        return folder;
    }

    private static final long serialVersionUID = 0x176c8d33ac752d2bL;
    private transient Folder folder;
}
