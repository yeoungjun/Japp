// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.URI;
import java.util.*;

public class RedirectLocations extends AbstractList
{

    public RedirectLocations()
    {
    }

    public void add(int i, Object obj)
    {
        all.add(i, (URI)obj);
        unique.add((URI)obj);
    }

    public void add(URI uri)
    {
        unique.add(uri);
        all.add(uri);
    }

    public boolean contains(Object obj)
    {
        return unique.contains(obj);
    }

    public boolean contains(URI uri)
    {
        return unique.contains(uri);
    }

    public volatile Object get(int i)
    {
        return get(i);
    }

    public URI get(int i)
    {
        return (URI)all.get(i);
    }

    public List getAll()
    {
        return new ArrayList(all);
    }

    public volatile Object remove(int i)
    {
        return remove(i);
    }

    public URI remove(int i)
    {
        URI uri = (URI)all.remove(i);
        unique.remove(uri);
        if(all.size() != unique.size())
            unique.addAll(all);
        return uri;
    }

    public boolean remove(URI uri)
    {
        boolean flag = unique.remove(uri);
        if(flag)
        {
            Iterator iterator = all.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                if(((URI)iterator.next()).equals(uri))
                    iterator.remove();
            } while(true);
        }
        return flag;
    }

    public Object set(int i, Object obj)
    {
        URI uri = (URI)all.set(i, (URI)obj);
        unique.remove(uri);
        unique.add((URI)obj);
        if(all.size() != unique.size())
            unique.addAll(all);
        return uri;
    }

    public int size()
    {
        return all.size();
    }

    private final List all = new ArrayList();
    private final Set unique = new HashSet();
}
