// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.commons.io.ByteOrderMark;

// Referenced classes of package org.apache.commons.io.input:
//            ProxyInputStream

public class BOMInputStream extends ProxyInputStream
{

    public BOMInputStream(InputStream inputstream)
    {
        ByteOrderMark abyteordermark[] = new ByteOrderMark[1];
        abyteordermark[0] = ByteOrderMark.UTF_8;
        this(inputstream, false, abyteordermark);
    }

    public BOMInputStream(InputStream inputstream, boolean flag)
    {
        ByteOrderMark abyteordermark[] = new ByteOrderMark[1];
        abyteordermark[0] = ByteOrderMark.UTF_8;
        this(inputstream, flag, abyteordermark);
    }

    public transient BOMInputStream(InputStream inputstream, boolean flag, ByteOrderMark abyteordermark[])
    {
        super(inputstream);
        if(abyteordermark == null || abyteordermark.length == 0)
        {
            throw new IllegalArgumentException("No BOMs specified");
        } else
        {
            include = flag;
            boms = Arrays.asList(abyteordermark);
            return;
        }
    }

    public transient BOMInputStream(InputStream inputstream, ByteOrderMark abyteordermark[])
    {
        this(inputstream, false, abyteordermark);
    }

    private ByteOrderMark find()
    {
        for(Iterator iterator = boms.iterator(); iterator.hasNext();)
        {
            ByteOrderMark byteordermark = (ByteOrderMark)iterator.next();
            if(matches(byteordermark))
                return byteordermark;
        }

        return null;
    }

    private boolean matches(ByteOrderMark byteordermark)
    {
        if(byteordermark.length() == fbLength) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = 0;
label0:
        do
        {
label1:
            {
                if(i >= byteordermark.length())
                    break label1;
                if(byteordermark.get(i) != firstBytes[i])
                    break label0;
                i++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    private int readFirstBytes()
        throws IOException
    {
        getBOM();
        if(fbIndex < fbLength)
        {
            int ai[] = firstBytes;
            int i = fbIndex;
            fbIndex = i + 1;
            return ai[i];
        } else
        {
            return -1;
        }
    }

    public ByteOrderMark getBOM()
        throws IOException
    {
        if(firstBytes != null) goto _L2; else goto _L1
_L1:
        int j;
        int i = 0;
        for(Iterator iterator = boms.iterator(); iterator.hasNext();)
            i = Math.max(i, ((ByteOrderMark)iterator.next()).length());

        firstBytes = new int[i];
        j = 0;
_L7:
        if(j >= firstBytes.length) goto _L2; else goto _L3
_L3:
        firstBytes[j] = in.read();
        fbLength = 1 + fbLength;
        if(firstBytes[j] >= 0) goto _L4; else goto _L2
_L2:
        return byteOrderMark;
_L4:
        byteOrderMark = find();
        if(byteOrderMark == null)
            break; /* Loop/switch isn't completed */
        if(!include)
            fbLength = 0;
        if(true) goto _L2; else goto _L5
_L5:
        j++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public String getBOMCharsetName()
        throws IOException
    {
        getBOM();
        if(byteOrderMark == null)
            return null;
        else
            return byteOrderMark.getCharsetName();
    }

    public boolean hasBOM()
        throws IOException
    {
        return getBOM() != null;
    }

    public boolean hasBOM(ByteOrderMark byteordermark)
        throws IOException
    {
        if(!boms.contains(byteordermark))
            throw new IllegalArgumentException((new StringBuilder()).append("Stream not configure to detect ").append(byteordermark).toString());
        return byteOrderMark != null && getBOM().equals(byteordermark);
    }

    public void mark(int i)
    {
        this;
        JVM INSTR monitorenter ;
        markFbIndex = fbIndex;
        boolean flag;
        if(firstBytes == null)
            flag = true;
        else
            flag = false;
        markedAtStart = flag;
        in.mark(i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public int read()
        throws IOException
    {
        int i = readFirstBytes();
        if(i >= 0)
            return i;
        else
            return in.read();
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = 0;
        int l = 0;
        int i1 = i;
        do
        {
            if(j <= 0 || l < 0)
                break;
            l = readFirstBytes();
            if(l >= 0)
            {
                int k1 = i1 + 1;
                abyte0[i1] = (byte)(l & 0xff);
                j--;
                k++;
                i1 = k1;
            }
        } while(true);
        int j1 = in.read(abyte0, i1, j);
        if(j1 < 0)
        {
            if(k > 0)
                return k;
            else
                return -1;
        } else
        {
            return k + j1;
        }
    }

    public void reset()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        fbIndex = markFbIndex;
        if(markedAtStart)
            firstBytes = null;
        in.reset();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public long skip(long l)
        throws IOException
    {
        for(; l > 0L && readFirstBytes() >= 0; l--);
        return in.skip(l);
    }

    private final List boms;
    private ByteOrderMark byteOrderMark;
    private int fbIndex;
    private int fbLength;
    private int firstBytes[];
    private final boolean include;
    private int markFbIndex;
    private boolean markedAtStart;
}
