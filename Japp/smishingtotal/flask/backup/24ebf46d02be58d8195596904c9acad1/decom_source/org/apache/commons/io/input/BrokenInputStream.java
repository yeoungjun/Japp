// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class BrokenInputStream extends InputStream
{

    public BrokenInputStream()
    {
        this(new IOException("Broken input stream"));
    }

    public BrokenInputStream(IOException ioexception)
    {
        exception = ioexception;
    }

    public int available()
        throws IOException
    {
        throw exception;
    }

    public void close()
        throws IOException
    {
        throw exception;
    }

    public int read()
        throws IOException
    {
        throw exception;
    }

    public void reset()
        throws IOException
    {
        throw exception;
    }

    public long skip(long l)
        throws IOException
    {
        throw exception;
    }

    private final IOException exception;
}
