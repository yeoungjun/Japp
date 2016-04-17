// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.*;
import java.util.Collection;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public abstract class DirectoryWalker
{
    public static class CancelException extends IOException
    {

        public int getDepth()
        {
            return depth;
        }

        public File getFile()
        {
            return file;
        }

        private static final long serialVersionUID = 0x12b2b63ef9f577f0L;
        private final int depth;
        private final File file;

        public CancelException(File file1, int i)
        {
            this("Operation Cancelled", file1, i);
        }

        public CancelException(String s, File file1, int i)
        {
            super(s);
            file = file1;
            depth = i;
        }
    }


    protected DirectoryWalker()
    {
        this(null, -1);
    }

    protected DirectoryWalker(FileFilter filefilter, int i)
    {
        filter = filefilter;
        depthLimit = i;
    }

    protected DirectoryWalker(IOFileFilter iofilefilter, IOFileFilter iofilefilter1, int i)
    {
        if(iofilefilter == null && iofilefilter1 == null)
        {
            filter = null;
        } else
        {
            if(iofilefilter == null)
                iofilefilter = TrueFileFilter.TRUE;
            if(iofilefilter1 == null)
                iofilefilter1 = TrueFileFilter.TRUE;
            filter = FileFilterUtils.or(new IOFileFilter[] {
                FileFilterUtils.makeDirectoryOnly(iofilefilter), FileFilterUtils.makeFileOnly(iofilefilter1)
            });
        }
        depthLimit = i;
    }

    private void walk(File file, int i, Collection collection)
        throws IOException
    {
        checkIfCancelled(file, i, collection);
        if(handleDirectory(file, i, collection))
        {
            handleDirectoryStart(file, i, collection);
            int j = i + 1;
            if(depthLimit < 0 || j <= depthLimit)
            {
                checkIfCancelled(file, i, collection);
                File afile[];
                File afile1[];
                if(filter == null)
                    afile = file.listFiles();
                else
                    afile = file.listFiles(filter);
                afile1 = filterDirectoryContents(file, i, afile);
                if(afile1 == null)
                {
                    handleRestricted(file, j, collection);
                } else
                {
                    int k = afile1.length;
                    int l = 0;
                    while(l < k) 
                    {
                        File file1 = afile1[l];
                        if(file1.isDirectory())
                        {
                            walk(file1, j, collection);
                        } else
                        {
                            checkIfCancelled(file1, j, collection);
                            handleFile(file1, j, collection);
                            checkIfCancelled(file1, j, collection);
                        }
                        l++;
                    }
                }
            }
            handleDirectoryEnd(file, i, collection);
        }
        checkIfCancelled(file, i, collection);
    }

    protected final void checkIfCancelled(File file, int i, Collection collection)
        throws IOException
    {
        if(handleIsCancelled(file, i, collection))
            throw new CancelException(file, i);
        else
            return;
    }

    protected File[] filterDirectoryContents(File file, int i, File afile[])
        throws IOException
    {
        return afile;
    }

    protected void handleCancelled(File file, Collection collection, CancelException cancelexception)
        throws IOException
    {
        throw cancelexception;
    }

    protected boolean handleDirectory(File file, int i, Collection collection)
        throws IOException
    {
        return true;
    }

    protected void handleDirectoryEnd(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected void handleDirectoryStart(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected void handleEnd(Collection collection)
        throws IOException
    {
    }

    protected void handleFile(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected boolean handleIsCancelled(File file, int i, Collection collection)
        throws IOException
    {
        return false;
    }

    protected void handleRestricted(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected void handleStart(File file, Collection collection)
        throws IOException
    {
    }

    protected final void walk(File file, Collection collection)
        throws IOException
    {
        if(file == null)
            throw new NullPointerException("Start Directory is null");
        try
        {
            handleStart(file, collection);
            walk(file, 0, collection);
            handleEnd(collection);
            return;
        }
        catch(CancelException cancelexception)
        {
            handleCancelled(file, collection, cancelexception);
        }
    }

    private final int depthLimit;
    private final FileFilter filter;
}
