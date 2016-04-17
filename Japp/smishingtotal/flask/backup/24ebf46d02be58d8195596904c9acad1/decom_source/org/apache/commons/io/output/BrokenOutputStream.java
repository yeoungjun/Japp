// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class BrokenOutputStream extends OutputStream
{

    public BrokenOutputStream()
    {
        this(new IOException("Broken output stream"));
    }

    public BrokenOutputStream(IOException ioexception)
    {
        exception = ioexception;
    }

    public void close()
        throws IOException
    {
        throw exception;
    }

    public void flush()
        throws IOException
    {
        throw exception;
    }

    public void write(int i)
        throws IOException
    {
        throw exception;
    }

    private final IOException exception;
}
