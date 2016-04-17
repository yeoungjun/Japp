// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.util;

import java.io.*;
import javax.mail.internet.SharedInputStream;

public class SharedFileInputStream extends BufferedInputStream
    implements SharedInputStream
{
    static class SharedFile
    {

        public void close()
            throws IOException
        {
            this;
            JVM INSTR monitorenter ;
            int i;
            if(cnt <= 0)
                break MISSING_BLOCK_LABEL_32;
            i = -1 + cnt;
            cnt = i;
            if(i > 0)
                break MISSING_BLOCK_LABEL_32;
            in.close();
            this;
            JVM INSTR monitorexit ;
            return;
            Exception exception;
            exception;
            throw exception;
        }

        protected void finalize()
            throws Throwable
        {
            super.finalize();
            in.close();
        }

        public void forceClose()
            throws IOException
        {
            this;
            JVM INSTR monitorenter ;
            if(cnt <= 0) goto _L2; else goto _L1
_L1:
            cnt = 0;
            in.close();
_L4:
            this;
            JVM INSTR monitorexit ;
            return;
_L2:
            Exception exception;
            try
            {
                in.close();
            }
            catch(IOException ioexception) { }
            finally
            {
                this;
            }
            if(true) goto _L4; else goto _L3
_L3:
            JVM INSTR monitorexit ;
            throw exception;
        }

        public RandomAccessFile open()
        {
            cnt = 1 + cnt;
            return in;
        }

        private int cnt;
        private RandomAccessFile in;

        SharedFile(File file)
            throws IOException
        {
            in = new RandomAccessFile(file, "r");
        }

        SharedFile(String s)
            throws IOException
        {
            in = new RandomAccessFile(s, "r");
        }
    }


    public SharedFileInputStream(File file)
        throws IOException
    {
        this(file, defaultBufferSize);
    }

    public SharedFileInputStream(File file, int i)
        throws IOException
    {
        super(null);
        start = 0L;
        master = true;
        if(i <= 0)
        {
            throw new IllegalArgumentException("Buffer size <= 0");
        } else
        {
            init(new SharedFile(file), i);
            return;
        }
    }

    public SharedFileInputStream(String s)
        throws IOException
    {
        this(s, defaultBufferSize);
    }

    public SharedFileInputStream(String s, int i)
        throws IOException
    {
        super(null);
        start = 0L;
        master = true;
        if(i <= 0)
        {
            throw new IllegalArgumentException("Buffer size <= 0");
        } else
        {
            init(new SharedFile(s), i);
            return;
        }
    }

    private SharedFileInputStream(SharedFile sharedfile, long l, long l1, int i)
    {
        super(null);
        start = 0L;
        master = true;
        master = false;
        sf = sharedfile;
        in = sharedfile.open();
        start = l;
        bufpos = l;
        datalen = l1;
        bufsize = i;
        buf = new byte[i];
    }

    private void ensureOpen()
        throws IOException
    {
        if(in == null)
            throw new IOException("Stream closed");
        else
            return;
    }

    private void fill()
        throws IOException
    {
        if(markpos >= 0) goto _L2; else goto _L1
_L1:
        pos = 0;
        bufpos = bufpos + (long)count;
_L4:
        count = pos;
        in.seek(bufpos + (long)pos);
        int j = buf.length - pos;
        if((bufpos - start) + (long)pos + (long)j > datalen)
            j = (int)(datalen - ((bufpos - start) + (long)pos));
        int k = in.read(buf, pos, j);
        if(k > 0)
            count = k + pos;
        return;
_L2:
        if(pos >= buf.length)
            if(markpos > 0)
            {
                int l = pos - markpos;
                System.arraycopy(buf, markpos, buf, 0, l);
                pos = l;
                bufpos = bufpos + (long)markpos;
                markpos = 0;
            } else
            if(buf.length >= marklimit)
            {
                markpos = -1;
                pos = 0;
                bufpos = bufpos + (long)count;
            } else
            {
                int i = 2 * pos;
                if(i > marklimit)
                    i = marklimit;
                byte abyte0[] = new byte[i];
                System.arraycopy(buf, 0, abyte0, 0, pos);
                buf = abyte0;
            }
        if(true) goto _L4; else goto _L3
_L3:
    }

    private int in_available()
        throws IOException
    {
        return (int)((start + datalen) - (bufpos + (long)count));
    }

    private void init(SharedFile sharedfile, int i)
        throws IOException
    {
        sf = sharedfile;
        in = sharedfile.open();
        start = 0L;
        datalen = in.length();
        bufsize = i;
        buf = new byte[i];
    }

    private int read1(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = count - pos;
        if(k <= 0)
        {
            fill();
            k = count - pos;
            if(k <= 0)
                return -1;
        }
        int l;
        if(k < j)
            l = k;
        else
            l = j;
        System.arraycopy(buf, pos, abyte0, i, l);
        pos = l + pos;
        return l;
    }

    public int available()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        int j;
        ensureOpen();
        i = count - pos;
        j = in_available();
        int k = i + j;
        this;
        JVM INSTR monitorexit ;
        return k;
        Exception exception;
        exception;
        throw exception;
    }

    public void close()
        throws IOException
    {
        if(in == null)
            return;
        if(!master) goto _L2; else goto _L1
_L1:
        sf.forceClose();
_L4:
        sf = null;
        in = null;
        buf = null;
        return;
_L2:
        sf.close();
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        sf = null;
        in = null;
        buf = null;
        throw exception;
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        close();
    }

    public long getPosition()
    {
        if(in == null)
            throw new RuntimeException("Stream closed");
        else
            return (bufpos + (long)pos) - start;
    }

    public void mark(int i)
    {
        this;
        JVM INSTR monitorenter ;
        marklimit = i;
        markpos = pos;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean markSupported()
    {
        return true;
    }

    public InputStream newStream(long l, long l1)
    {
        if(in == null)
            throw new RuntimeException("Stream closed");
        if(l < 0L)
            throw new IllegalArgumentException("start < 0");
        if(l1 == -1L)
            l1 = datalen;
        return new SharedFileInputStream(sf, start + (long)(int)l, (int)(l1 - l), bufsize);
    }

    public int read()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        ensureOpen();
        if(pos < count) goto _L2; else goto _L1
_L1:
        int k;
        int l;
        fill();
        k = pos;
        l = count;
        if(k < l) goto _L2; else goto _L3
_L3:
        int j = -1;
_L5:
        this;
        JVM INSTR monitorexit ;
        return j;
_L2:
        byte byte0;
        byte abyte0[] = buf;
        int i = pos;
        pos = i + 1;
        byte0 = abyte0[i];
        j = byte0 & 0xff;
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception;
        exception;
        throw exception;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        ensureOpen();
        if((i | j | i + j | abyte0.length - (i + j)) < 0)
            throw new IndexOutOfBoundsException();
        break MISSING_BLOCK_LABEL_38;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(j != 0) goto _L2; else goto _L1
_L1:
        int k = 0;
_L4:
        this;
        JVM INSTR monitorexit ;
        return k;
_L2:
        k = read1(abyte0, i, j);
        if(k <= 0) goto _L4; else goto _L3
_L3:
        if(k >= j) goto _L4; else goto _L5
_L5:
        int l = read1(abyte0, i + k, j - k);
        if(l <= 0) goto _L4; else goto _L6
_L6:
        k += l;
          goto _L3
    }

    public void reset()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        ensureOpen();
        if(markpos < 0)
            throw new IOException("Resetting to invalid mark");
        break MISSING_BLOCK_LABEL_28;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        pos = markpos;
        this;
        JVM INSTR monitorexit ;
    }

    public long skip(long l)
        throws IOException
    {
        long l1 = 0L;
        this;
        JVM INSTR monitorenter ;
        ensureOpen();
        if(l > l1) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return l1;
_L2:
        long l2 = count - pos;
        if(l2 > l1) goto _L4; else goto _L3
_L3:
        fill();
        l2 = count - pos;
        if(l2 <= l1) goto _L1; else goto _L4
_L5:
        pos = (int)(l1 + (long)pos);
          goto _L1
        Exception exception;
        exception;
        throw exception;
_L7:
        l1 = l;
          goto _L5
_L4:
        if(l2 >= l) goto _L7; else goto _L6
_L6:
        l1 = l2;
          goto _L5
    }

    private static int defaultBufferSize = 2048;
    protected long bufpos;
    protected int bufsize;
    protected long datalen;
    protected RandomAccessFile in;
    private boolean master;
    private SharedFile sf;
    protected long start;

}
