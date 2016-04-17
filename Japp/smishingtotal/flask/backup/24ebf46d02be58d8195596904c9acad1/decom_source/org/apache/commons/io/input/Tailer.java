// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

// Referenced classes of package org.apache.commons.io.input:
//            TailerListener

public class Tailer
    implements Runnable
{

    public Tailer(File file1, TailerListener tailerlistener)
    {
        this(file1, tailerlistener, 1000L);
    }

    public Tailer(File file1, TailerListener tailerlistener, long l)
    {
        this(file1, tailerlistener, 1000L, false);
    }

    public Tailer(File file1, TailerListener tailerlistener, long l, boolean flag)
    {
        run = true;
        file = file1;
        delay = l;
        end = flag;
        listener = tailerlistener;
        tailerlistener.init(this);
    }

    public static Tailer create(File file1, TailerListener tailerlistener)
    {
        return create(file1, tailerlistener, 1000L, false);
    }

    public static Tailer create(File file1, TailerListener tailerlistener, long l)
    {
        return create(file1, tailerlistener, l, false);
    }

    public static Tailer create(File file1, TailerListener tailerlistener, long l, boolean flag)
    {
        Tailer tailer = new Tailer(file1, tailerlistener, l, flag);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    private long readLines(RandomAccessFile randomaccessfile)
        throws IOException
    {
        for(String s = randomaccessfile.readLine(); s != null; s = randomaccessfile.readLine())
            listener.handle(s);

        return randomaccessfile.getFilePointer();
    }

    public long getDelay()
    {
        return delay;
    }

    public File getFile()
    {
        return file;
    }

    public void run()
    {
        long l;
        long l1;
        RandomAccessFile randomaccessfile;
        l = 0L;
        l1 = 0L;
        randomaccessfile = null;
_L1:
        boolean flag = run;
        if(!flag || randomaccessfile != null)
            break MISSING_BLOCK_LABEL_121;
        RandomAccessFile randomaccessfile1 = new RandomAccessFile(file, "r");
_L2:
        if(randomaccessfile1 != null)
            break MISSING_BLOCK_LABEL_84;
        Thread.sleep(delay);
        randomaccessfile = randomaccessfile1;
          goto _L1
        FileNotFoundException filenotfoundexception2;
        filenotfoundexception2;
        listener.fileNotFound();
        randomaccessfile1 = randomaccessfile;
          goto _L2
        InterruptedException interruptedexception1;
        interruptedexception1;
        randomaccessfile = randomaccessfile1;
          goto _L1
        if(!end)
            break MISSING_BLOCK_LABEL_116;
        l1 = file.length();
_L3:
        l = System.currentTimeMillis();
        randomaccessfile1.seek(l1);
        randomaccessfile = randomaccessfile1;
          goto _L1
        l1 = 0L;
          goto _L3
_L8:
        if(!run) goto _L5; else goto _L4
_L4:
        long l2 = file.length();
        if(l2 >= l1) goto _L7; else goto _L6
_L6:
        listener.fileRotated();
        RandomAccessFile randomaccessfile2 = randomaccessfile;
        randomaccessfile1 = new RandomAccessFile(file, "r");
        l1 = 0L;
        IOUtils.closeQuietly(randomaccessfile2);
        randomaccessfile = randomaccessfile1;
          goto _L8
        FileNotFoundException filenotfoundexception1;
        filenotfoundexception1;
        randomaccessfile1 = randomaccessfile;
_L17:
        listener.fileNotFound();
        randomaccessfile = randomaccessfile1;
          goto _L8
_L7:
        if(l2 <= l1) goto _L10; else goto _L9
_L9:
        long l4;
        l = System.currentTimeMillis();
        l4 = readLines(randomaccessfile);
        l1 = l4;
_L12:
        Thread.sleep(delay);
          goto _L8
        InterruptedException interruptedexception;
        interruptedexception;
          goto _L8
_L10:
        if(!FileUtils.isFileNewer(file, l)) goto _L12; else goto _L11
_L11:
        long l3;
        randomaccessfile.seek(0L);
        l = System.currentTimeMillis();
        l3 = readLines(randomaccessfile);
        l1 = l3;
          goto _L12
_L5:
        IOUtils.closeQuietly(randomaccessfile);
        randomaccessfile;
        return;
        Exception exception1;
        exception1;
        randomaccessfile1 = randomaccessfile;
_L16:
        listener.handle(exception1);
        IOUtils.closeQuietly(randomaccessfile1);
        return;
        Exception exception;
        exception;
        randomaccessfile1 = randomaccessfile;
_L14:
        IOUtils.closeQuietly(randomaccessfile1);
        throw exception;
        exception;
        if(true) goto _L14; else goto _L13
_L13:
        exception1;
        if(true) goto _L16; else goto _L15
_L15:
        FileNotFoundException filenotfoundexception;
        filenotfoundexception;
          goto _L17
    }

    public void stop()
    {
        run = false;
    }

    private final long delay;
    private final boolean end;
    private final File file;
    private final TailerListener listener;
    private volatile boolean run;
}
