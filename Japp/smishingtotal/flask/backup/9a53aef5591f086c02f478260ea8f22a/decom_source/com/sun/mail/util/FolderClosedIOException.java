// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.util;

import java.io.IOException;
import javax.mail.Folder;

public class FolderClosedIOException extends IOException
{

    public FolderClosedIOException(Folder folder1)
    {
        this(folder1, null);
    }

    public FolderClosedIOException(Folder folder1, String s)
    {
        super(s);
        folder = folder1;
    }

    public Folder getFolder()
    {
        return folder;
    }

    private static final long serialVersionUID = 0x3b699a4df297b817L;
    private transient Folder folder;
}
