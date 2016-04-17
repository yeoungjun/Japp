// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Folder

public class FolderNotFoundException extends MessagingException
{

    public FolderNotFoundException()
    {
    }

    public FolderNotFoundException(String s, Folder folder1)
    {
        super(s);
        folder = folder1;
    }

    public FolderNotFoundException(Folder folder1)
    {
        folder = folder1;
    }

    public FolderNotFoundException(Folder folder1, String s)
    {
        super(s);
        folder = folder1;
    }

    public Folder getFolder()
    {
        return folder;
    }

    private static final long serialVersionUID = 0x68f0e358302dafbL;
    private transient Folder folder;
}
