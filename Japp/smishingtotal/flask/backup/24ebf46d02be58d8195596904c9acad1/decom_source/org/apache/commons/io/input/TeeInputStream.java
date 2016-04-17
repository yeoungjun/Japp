// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.*;

// Referenced classes of package org.apache.commons.io.input:
//            ProxyInputStream

public class TeeInputStream extends ProxyInputStream
{

    public TeeInputStream(InputStream inputstream, OutputStream outputstream)
    {
        this(inputstream, outputstream, false);
    }

    public TeeInputStream(InputStream inputstream, OutputStream outputstream, boolean flag)
    {
        super(inputstream);
        branch = outputstream;
        closeBranch = flag;
    }

    public void close()
        throws IOException
    {
        super.close();
        if(closeBranch)
            branch.close();
        return;
        Exception exception;
        exception;
        if(closeBranch)
            branch.close();
        throw exception;
    }

    public int read()
        throws IOException
    {
        int i = super.read();
        if(i != -1)
            branch.write(i);
        return i;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        int i = super.read(abyte0);
        if(i != -1)
            branch.write(abyte0, 0, i);
        return i;
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        int k = super.read(abyte0, i, j);
        if(k != -1)
            branch.write(abyte0, i, k);
        return k;
    }

    private final OutputStream branch;
    private final boolean closeBranch;
}
