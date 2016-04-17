// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.output;

import java.io.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class LockableFileWriter extends Writer
{

    public LockableFileWriter(File file)
        throws IOException
    {
        this(file, false, null);
    }

    public LockableFileWriter(File file, String s)
        throws IOException
    {
        this(file, s, false, null);
    }

    public LockableFileWriter(File file, String s, boolean flag, String s1)
        throws IOException
    {
        File file1 = file.getAbsoluteFile();
        if(file1.getParentFile() != null)
            FileUtils.forceMkdir(file1.getParentFile());
        if(file1.isDirectory())
            throw new IOException("File specified is a directory");
        if(s1 == null)
            s1 = System.getProperty("java.io.tmpdir");
        File file2 = new File(s1);
        FileUtils.forceMkdir(file2);
        testLockDir(file2);
        lockFile = new File(file2, (new StringBuilder()).append(file1.getName()).append(".lck").toString());
        createLock();
        out = initWriter(file1, s, flag);
    }

    public LockableFileWriter(File file, boolean flag)
        throws IOException
    {
        this(file, flag, null);
    }

    public LockableFileWriter(File file, boolean flag, String s)
        throws IOException
    {
        this(file, null, flag, s);
    }

    public LockableFileWriter(String s)
        throws IOException
    {
        this(s, false, null);
    }

    public LockableFileWriter(String s, boolean flag)
        throws IOException
    {
        this(s, flag, null);
    }

    public LockableFileWriter(String s, boolean flag, String s1)
        throws IOException
    {
        this(new File(s), flag, s1);
    }

    private void createLock()
        throws IOException
    {
        org/apache/commons/io/output/LockableFileWriter;
        JVM INSTR monitorenter ;
        if(!lockFile.createNewFile())
            throw new IOException((new StringBuilder()).append("Can't write file, lock ").append(lockFile.getAbsolutePath()).append(" exists").toString());
        break MISSING_BLOCK_LABEL_57;
        Exception exception;
        exception;
        org/apache/commons/io/output/LockableFileWriter;
        JVM INSTR monitorexit ;
        throw exception;
        lockFile.deleteOnExit();
        org/apache/commons/io/output/LockableFileWriter;
        JVM INSTR monitorexit ;
    }

    private Writer initWriter(File file, String s, boolean flag)
        throws IOException
    {
        boolean flag1;
        Object obj;
        flag1 = file.exists();
        obj = null;
        if(s != null)
            break MISSING_BLOCK_LABEL_26;
        return new FileWriter(file.getAbsolutePath(), flag);
        FileOutputStream fileoutputstream = new FileOutputStream(file.getAbsolutePath(), flag);
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(fileoutputstream, s);
        return outputstreamwriter;
        IOException ioexception;
        ioexception;
_L4:
        IOUtils.closeQuietly(null);
        IOUtils.closeQuietly(((java.io.OutputStream) (obj)));
        FileUtils.deleteQuietly(lockFile);
        if(!flag1)
            FileUtils.deleteQuietly(file);
        throw ioexception;
        RuntimeException runtimeexception;
        runtimeexception;
_L2:
        IOUtils.closeQuietly(null);
        IOUtils.closeQuietly(((java.io.OutputStream) (obj)));
        FileUtils.deleteQuietly(lockFile);
        if(!flag1)
            FileUtils.deleteQuietly(file);
        throw runtimeexception;
        runtimeexception;
        obj = fileoutputstream;
        if(true) goto _L2; else goto _L1
_L1:
        ioexception;
        obj = fileoutputstream;
        if(true) goto _L4; else goto _L3
_L3:
    }

    private void testLockDir(File file)
        throws IOException
    {
        if(!file.exists())
            throw new IOException((new StringBuilder()).append("Could not find lockDir: ").append(file.getAbsolutePath()).toString());
        if(!file.canWrite())
            throw new IOException((new StringBuilder()).append("Could not write to lockDir: ").append(file.getAbsolutePath()).toString());
        else
            return;
    }

    public void close()
        throws IOException
    {
        out.close();
        lockFile.delete();
        return;
        Exception exception;
        exception;
        lockFile.delete();
        throw exception;
    }

    public void flush()
        throws IOException
    {
        out.flush();
    }

    public void write(int i)
        throws IOException
    {
        out.write(i);
    }

    public void write(String s)
        throws IOException
    {
        out.write(s);
    }

    public void write(String s, int i, int j)
        throws IOException
    {
        out.write(s, i, j);
    }

    public void write(char ac[])
        throws IOException
    {
        out.write(ac);
    }

    public void write(char ac[], int i, int j)
        throws IOException
    {
        out.write(ac, i, j);
    }

    private static final String LCK = ".lck";
    private final File lockFile;
    private final Writer out;
}
