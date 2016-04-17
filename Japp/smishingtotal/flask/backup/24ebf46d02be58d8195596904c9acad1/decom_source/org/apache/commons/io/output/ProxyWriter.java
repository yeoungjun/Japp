// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.output;

import java.io.*;

public class ProxyWriter extends FilterWriter
{

    public ProxyWriter(Writer writer)
    {
        super(writer);
    }

    protected void afterWrite(int i)
        throws IOException
    {
    }

    public Writer append(char c)
        throws IOException
    {
        try
        {
            beforeWrite(1);
            out.append(c);
            afterWrite(1);
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return this;
        }
        return this;
    }

    public Writer append(CharSequence charsequence)
        throws IOException
    {
        int i;
        i = 0;
        if(charsequence == null)
            break MISSING_BLOCK_LABEL_13;
        i = charsequence.length();
        beforeWrite(i);
        out.append(charsequence);
        afterWrite(i);
        return this;
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        return this;
    }

    public Writer append(CharSequence charsequence, int i, int j)
        throws IOException
    {
        int k = j - i;
        try
        {
            beforeWrite(k);
            out.append(charsequence, i, j);
            afterWrite(j - i);
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
            return this;
        }
        return this;
    }

    public volatile Appendable append(char c)
        throws IOException
    {
        return append(c);
    }

    public volatile Appendable append(CharSequence charsequence)
        throws IOException
    {
        return append(charsequence);
    }

    public volatile Appendable append(CharSequence charsequence, int i, int j)
        throws IOException
    {
        return append(charsequence, i, j);
    }

    protected void beforeWrite(int i)
        throws IOException
    {
    }

    public void close()
        throws IOException
    {
        try
        {
            out.close();
            return;
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
        }
    }

    public void flush()
        throws IOException
    {
        try
        {
            out.flush();
            return;
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
        }
    }

    protected void handleIOException(IOException ioexception)
        throws IOException
    {
        throw ioexception;
    }

    public void write(int i)
        throws IOException
    {
        try
        {
            beforeWrite(1);
            out.write(i);
            afterWrite(1);
            return;
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
        }
    }

    public void write(String s)
        throws IOException
    {
        int i;
        i = 0;
        if(s == null)
            break MISSING_BLOCK_LABEL_11;
        i = s.length();
        beforeWrite(i);
        out.write(s);
        afterWrite(i);
        return;
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        return;
    }

    public void write(String s, int i, int j)
        throws IOException
    {
        try
        {
            beforeWrite(j);
            out.write(s, i, j);
            afterWrite(j);
            return;
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
        }
    }

    public void write(char ac[])
        throws IOException
    {
        int i;
        i = 0;
        if(ac == null)
            break MISSING_BLOCK_LABEL_9;
        i = ac.length;
        beforeWrite(i);
        out.write(ac);
        afterWrite(i);
        return;
        IOException ioexception;
        ioexception;
        handleIOException(ioexception);
        return;
    }

    public void write(char ac[], int i, int j)
        throws IOException
    {
        try
        {
            beforeWrite(j);
            out.write(ac, i, j);
            afterWrite(j);
            return;
        }
        catch(IOException ioexception)
        {
            handleIOException(ioexception);
        }
    }
}
