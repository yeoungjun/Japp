// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Referenced classes of package org.apache.commons.io:
//            IOUtils

public class LineIterator
    implements Iterator
{

    public LineIterator(Reader reader)
        throws IllegalArgumentException
    {
        finished = false;
        if(reader == null)
            throw new IllegalArgumentException("Reader must not be null");
        if(reader instanceof BufferedReader)
        {
            bufferedReader = (BufferedReader)reader;
            return;
        } else
        {
            bufferedReader = new BufferedReader(reader);
            return;
        }
    }

    public static void closeQuietly(LineIterator lineiterator)
    {
        if(lineiterator != null)
            lineiterator.close();
    }

    public void close()
    {
        finished = true;
        IOUtils.closeQuietly(bufferedReader);
        cachedLine = null;
    }

    public boolean hasNext()
    {
        if(cachedLine != null)
            return true;
        if(finished)
            return false;
_L2:
        String s;
        try
        {
            s = bufferedReader.readLine();
        }
        catch(IOException ioexception)
        {
            close();
            throw new IllegalStateException(ioexception);
        }
        if(s != null)
            continue; /* Loop/switch isn't completed */
        finished = true;
        return false;
        if(!isValidLine(s)) goto _L2; else goto _L1
_L1:
        cachedLine = s;
        return true;
    }

    protected boolean isValidLine(String s)
    {
        return true;
    }

    public volatile Object next()
    {
        return next();
    }

    public String next()
    {
        return nextLine();
    }

    public String nextLine()
    {
        if(!hasNext())
        {
            throw new NoSuchElementException("No more lines");
        } else
        {
            String s = cachedLine;
            cachedLine = null;
            return s;
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }

    private final BufferedReader bufferedReader;
    private String cachedLine;
    private boolean finished;
}
