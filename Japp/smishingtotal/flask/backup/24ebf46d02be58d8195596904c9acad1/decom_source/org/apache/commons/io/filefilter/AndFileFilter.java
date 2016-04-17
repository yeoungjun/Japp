// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter, ConditionalFileFilter, IOFileFilter

public class AndFileFilter extends AbstractFileFilter
    implements ConditionalFileFilter, Serializable
{

    public AndFileFilter()
    {
        fileFilters = new ArrayList();
    }

    public AndFileFilter(List list)
    {
        if(list == null)
        {
            fileFilters = new ArrayList();
            return;
        } else
        {
            fileFilters = new ArrayList(list);
            return;
        }
    }

    public AndFileFilter(IOFileFilter iofilefilter, IOFileFilter iofilefilter1)
    {
        if(iofilefilter == null || iofilefilter1 == null)
        {
            throw new IllegalArgumentException("The filters must not be null");
        } else
        {
            fileFilters = new ArrayList(2);
            addFileFilter(iofilefilter);
            addFileFilter(iofilefilter1);
            return;
        }
    }

    public boolean accept(File file)
    {
        if(fileFilters.size() == 0)
            return false;
        for(Iterator iterator = fileFilters.iterator(); iterator.hasNext();)
            if(!((IOFileFilter)iterator.next()).accept(file))
                return false;

        return true;
    }

    public boolean accept(File file, String s)
    {
        if(fileFilters.size() == 0)
            return false;
        for(Iterator iterator = fileFilters.iterator(); iterator.hasNext();)
            if(!((IOFileFilter)iterator.next()).accept(file, s))
                return false;

        return true;
    }

    public void addFileFilter(IOFileFilter iofilefilter)
    {
        fileFilters.add(iofilefilter);
    }

    public List getFileFilters()
    {
        return Collections.unmodifiableList(fileFilters);
    }

    public boolean removeFileFilter(IOFileFilter iofilefilter)
    {
        return fileFilters.remove(iofilefilter);
    }

    public void setFileFilters(List list)
    {
        fileFilters.clear();
        fileFilters.addAll(list);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(super.toString());
        stringbuilder.append("(");
        if(fileFilters != null)
        {
            int i = 0;
            while(i < fileFilters.size()) 
            {
                if(i > 0)
                    stringbuilder.append(",");
                Object obj = fileFilters.get(i);
                String s;
                if(obj == null)
                    s = "null";
                else
                    s = obj.toString();
                stringbuilder.append(s);
                i++;
            }
        }
        stringbuilder.append(")");
        return stringbuilder.toString();
    }

    private final List fileFilters;
}
