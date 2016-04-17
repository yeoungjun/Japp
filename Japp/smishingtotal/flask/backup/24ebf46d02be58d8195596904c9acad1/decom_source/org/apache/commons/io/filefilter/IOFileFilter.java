// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.*;

public interface IOFileFilter
    extends FileFilter, FilenameFilter
{

    public abstract boolean accept(File file);

    public abstract boolean accept(File file, String s);
}
