// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.File;
import java.io.IOException;

public class FileExistsException extends IOException
{

    public FileExistsException()
    {
    }

    public FileExistsException(File file)
    {
        super((new StringBuilder()).append("File ").append(file).append(" exists").toString());
    }

    public FileExistsException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 1L;
}
