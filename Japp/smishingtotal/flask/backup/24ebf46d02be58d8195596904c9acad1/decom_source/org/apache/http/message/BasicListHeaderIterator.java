// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

public class BasicListHeaderIterator
    implements HeaderIterator
{

    public BasicListHeaderIterator(List list, String s)
    {
        allHeaders = (List)Args.notNull(list, "Header list");
        headerName = s;
        currentIndex = findNext(-1);
        lastIndex = -1;
    }

    protected boolean filterHeader(int i)
    {
        if(headerName == null)
        {
            return true;
        } else
        {
            String s = ((Header)allHeaders.get(i)).getName();
            return headerName.equalsIgnoreCase(s);
        }
    }

    protected int findNext(int i)
    {
        int j = i;
        if(j >= -1)
        {
            int k = -1 + allHeaders.size();
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
            lastIndex = i;
            currentIndex = findNext(i);
            return (Header)allHeaders.get(i);
        }
    }

    public void remove()
        throws UnsupportedOperationException
    {
        boolean flag;
        if(lastIndex >= 0)
            flag = true;
        else
            flag = false;
        Asserts.check(flag, "No header to remove");
        allHeaders.remove(lastIndex);
        lastIndex = -1;
        currentIndex = -1 + currentIndex;
    }

    protected final List allHeaders;
    protected int currentIndex;
    protected String headerName;
    protected int lastIndex;
}
