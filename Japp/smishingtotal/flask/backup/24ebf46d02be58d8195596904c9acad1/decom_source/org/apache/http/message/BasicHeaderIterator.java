// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.util.Args;

public class BasicHeaderIterator
    implements HeaderIterator
{

    public BasicHeaderIterator(Header aheader[], String s)
    {
        allHeaders = (Header[])Args.notNull(aheader, "Header array");
        headerName = s;
        currentIndex = findNext(-1);
    }

    protected boolean filterHeader(int i)
    {
        return headerName == null || headerName.equalsIgnoreCase(allHeaders[i].getName());
    }

    protected int findNext(int i)
    {
        int j = i;
        if(j >= -1)
        {
            int k = -1 + allHeaders.length;
            boolean flag;
            for(flag = false; !flag && j < k; flag = filterHeader(j))
                j++;

            if(flag)
                return j;
        }
        return -1;
    }

    public boolean hasNext()
    {
        return currentIndex >= 0;
    }

    public final Object next()
        throws NoSuchElementException
    {
        return nextHeader();
    }

    public Header nextHeader()
        throws NoSuchElementException
    {
        int i = currentIndex;
        if(i < 0)
        {
            throw new NoSuchElementException("Iteration already finished.");
        } else
        {
            currentIndex = findNext(i);
            return allHeaders[i];
        }
    }

    public void remove()
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Removing headers is not supported.");
    }

    protected final Header allHeaders[];
    protected int currentIndex;
    protected String headerName;
}
