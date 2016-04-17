// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.*;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class MagicNumberFileFilter extends AbstractFileFilter
    implements Serializable
{

    public MagicNumberFileFilter(String s)
    {
        this(s, 0L);
    }

    public MagicNumberFileFilter(String s, long l)
    {
        if(s == null)
            throw new IllegalArgumentException("The magic number cannot be null");
        if(s.length() == 0)
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        if(l < 0L)
        {
            throw new IllegalArgumentException("The offset cannot be negative");
        } else
        {
            magicNumbers = s.getBytes();
            byteOffset = l;
            return;
        }
    }

    public MagicNumberFileFilter(byte abyte0[])
    {
        this(abyte0, 0L);
    }

    public MagicNumberFileFilter(byte abyte0[], long l)
    {
        if(abyte0 == null)
            throw new IllegalArgumentException("The magic number cannot be null");
        if(abyte0.length == 0)
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        if(l < 0L)
        {
            throw new IllegalArgumentException("The offset cannot be negative");
        } else
        {
            magicNumbers = new byte[abyte0.length];
            System.arraycopy(abyte0, 0, magicNumbers, 0, abyte0.length);
            byteOffset = l;
            return;
        }
    }

    public boolean accept(File file)
    {
        Object obj;
        if(file == null || !file.isFile() || !file.canRead())
            break MISSING_BLOCK_LABEL_78;
        obj = null;
        byte abyte0[];
        RandomAccessFile randomaccessfile;
        abyte0 = new byte[magicNumbers.length];
        randomaccessfile = new RandomAccessFile(file, "r");
        int i;
        int j;
        randomaccessfile.seek(byteOffset);
        i = randomaccessfile.read(abyte0);
        j = magicNumbers.length;
        if(i == j)
            break MISSING_BLOCK_LABEL_80;
        IOUtils.closeQuietly(randomaccessfile);
        return false;
        boolean flag = Arrays.equals(magicNumbers, abyte0);
        IOUtils.closeQuietly(randomaccessfile);
        return flag;
        IOException ioexception;
        ioexception;
_L4:
        IOUtils.closeQuietly(((java.io.Closeable) (obj)));
        return false;
        Exception exception;
        exception;
_L2:
        IOUtils.closeQuietly(((java.io.Closeable) (obj)));
        throw exception;
        exception;
        obj = randomaccessfile;
        if(true) goto _L2; else goto _L1
_L1:
        IOException ioexception1;
        ioexception1;
        obj = randomaccessfile;
        if(true) goto _L4; else goto _L3
_L3:
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(super.toString());
        stringbuilder.append("(");
        stringbuilder.append(new String(magicNumbers));
        stringbuilder.append(",");
        stringbuilder.append(byteOffset);
        stringbuilder.append(")");
        return stringbuilder.toString();
    }

    private static final long serialVersionUID = 0xf8660f93da39d554L;
    private final long byteOffset;
    private final byte magicNumbers[];
}
