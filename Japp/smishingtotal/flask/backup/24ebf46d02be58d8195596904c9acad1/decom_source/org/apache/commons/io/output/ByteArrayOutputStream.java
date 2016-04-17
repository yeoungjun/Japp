// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.output;

import java.io.*;
import java.util.*;
import org.apache.commons.io.input.ClosedInputStream;

public class ByteArrayOutputStream extends OutputStream
{

    public ByteArrayOutputStream()
    {
        this(1024);
    }

    public ByteArrayOutputStream(int i)
    {
        buffers = new ArrayList();
        if(i < 0)
            throw new IllegalArgumentException((new StringBuilder()).append("Negative initial size: ").append(i).toString());
        this;
        JVM INSTR monitorenter ;
        needNewBuffer(i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    private void needNewBuffer(int i)
    {
        if(currentBufferIndex < -1 + buffers.size())
        {
            filledBufferSum = filledBufferSum + currentBuffer.length;
            currentBufferIndex = 1 + currentBufferIndex;
            currentBuffer = (byte[])buffers.get(currentBufferIndex);
            return;
        }
        int j;
        if(currentBuffer == null)
        {
            j = i;
            filledBufferSum = 0;
        } else
        {
            j = Math.max(currentBuffer.length << 1, i - filledBufferSum);
            filledBufferSum = filledBufferSum + currentBuffer.length;
        }
        currentBufferIndex = 1 + currentBufferIndex;
        currentBuffer = new byte[j];
        buffers.add(currentBuffer);
    }

    private InputStream toBufferedInputStream()
    {
        int i = count;
        if(i == 0)
            return new ClosedInputStream();
        ArrayList arraylist = new ArrayList(buffers.size());
        Iterator iterator = buffers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            byte abyte0[] = (byte[])iterator.next();
            int j = Math.min(abyte0.length, i);
            arraylist.add(new ByteArrayInputStream(abyte0, 0, j));
            i -= j;
        } while(i != 0);
        return new SequenceInputStream(Collections.enumeration(arraylist));
    }

    public static InputStream toBufferedInputStream(InputStream inputstream)
        throws IOException
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        bytearrayoutputstream.write(inputstream);
        return bytearrayoutputstream.toBufferedInputStream();
    }

    public void close()
        throws IOException
    {
    }

    public void reset()
    {
        this;
        JVM INSTR monitorenter ;
        count = 0;
        filledBufferSum = 0;
        currentBufferIndex = 0;
        currentBuffer = (byte[])buffers.get(currentBufferIndex);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public int size()
    {
        this;
        JVM INSTR monitorenter ;
        int i = count;
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public byte[] toByteArray()
    {
        this;
        JVM INSTR monitorenter ;
        int i = count;
        if(i != 0) goto _L2; else goto _L1
_L1:
        byte abyte0[] = EMPTY_BYTE_ARRAY;
_L3:
        this;
        JVM INSTR monitorexit ;
        return abyte0;
_L2:
        abyte0 = new byte[i];
        int j = 0;
        Iterator iterator = buffers.iterator();
_L4:
        int k;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        byte abyte1[] = (byte[])iterator.next();
        k = Math.min(abyte1.length, i);
        System.arraycopy(abyte1, 0, abyte0, j, k);
        j += k;
        i -= k;
        if(i != 0) goto _L4; else goto _L3
        Exception exception;
        exception;
        throw exception;
    }

    public String toString()
    {
        return new String(toByteArray());
    }

    public String toString(String s)
        throws UnsupportedEncodingException
    {
        return new String(toByteArray(), s);
    }

    public int write(InputStream inputstream)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int i = 0;
        int j;
        int k;
        j = count - filledBufferSum;
        k = inputstream.read(currentBuffer, j, currentBuffer.length - j);
_L2:
        if(k == -1)
            break; /* Loop/switch isn't completed */
        i += k;
        j += k;
        count = k + count;
        if(j != currentBuffer.length)
            break MISSING_BLOCK_LABEL_86;
        needNewBuffer(currentBuffer.length);
        j = 0;
        int l = inputstream.read(currentBuffer, j, currentBuffer.length - j);
        k = l;
        if(true) goto _L2; else goto _L1
_L1:
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public void write(int i)
    {
        this;
        JVM INSTR monitorenter ;
        int j;
        j = count - filledBufferSum;
        if(j != currentBuffer.length)
            break MISSING_BLOCK_LABEL_33;
        needNewBuffer(1 + count);
        j = 0;
        currentBuffer[j] = (byte)i;
        count = 1 + count;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void write(byte abyte0[], int i, int j)
    {
        if(i < 0 || i > abyte0.length || j < 0 || i + j > abyte0.length || i + j < 0)
            throw new IndexOutOfBoundsException();
        if(j == 0)
            return;
        this;
        JVM INSTR monitorenter ;
        int k = j + count;
        int l = j;
        int i1 = count - filledBufferSum;
_L2:
        if(l <= 0)
            break; /* Loop/switch isn't completed */
        int j1;
        j1 = Math.min(l, currentBuffer.length - i1);
        System.arraycopy(abyte0, (i + j) - l, currentBuffer, i1, j1);
        l -= j1;
        if(l <= 0)
            continue; /* Loop/switch isn't completed */
        needNewBuffer(k);
        i1 = 0;
        if(true) goto _L2; else goto _L1
_L1:
        count = k;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        Iterator iterator;
        i = count;
        iterator = buffers.iterator();
_L2:
        int j;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        byte abyte0[] = (byte[])iterator.next();
        j = Math.min(abyte0.length, i);
        outputstream.write(abyte0, 0, j);
        i -= j;
        if(i != 0) goto _L2; else goto _L1
_L1:
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private static final byte EMPTY_BYTE_ARRAY[] = new byte[0];
    private final List buffers;
    private int count;
    private byte currentBuffer[];
    private int currentBufferIndex;
    private int filledBufferSum;

}
