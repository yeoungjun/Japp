// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime;

import java.util.*;

// Referenced classes of package org.apache.http.entity.mime:
//            MinimalField

public class Header
    implements Iterable
{

    public Header()
    {
    }

    public void addField(MinimalField minimalfield)
    {
        if(minimalfield == null)
            return;
        String s = minimalfield.getName().toLowerCase(Locale.US);
        Object obj = (List)fieldMap.get(s);
        if(obj == null)
        {
            obj = new LinkedList();
            fieldMap.put(s, obj);
        }
        ((List) (obj)).add(minimalfield);
        fields.add(minimalfield);
    }

    public MinimalField getField(String s)
    {
        if(s != null)
        {
            String s1 = s.toLowerCase(Locale.US);
            List list = (List)fieldMap.get(s1);
            if(list != null && !list.isEmpty())
                return (MinimalField)list.get(0);
        }
        return null;
    }

    public List getFields()
    {
        return new ArrayList(fields);
    }

    public List getFields(String s)
    {
        if(s == null)
            return null;
        String s1 = s.toLowerCase(Locale.US);
        List list = (List)fieldMap.get(s1);
        if(list == null || list.isEmpty())
            return Collections.emptyList();
        else
            return new ArrayList(list);
    }

    public Iterator iterator()
    {
        return Collections.unmodifiableList(fields).iterator();
    }

    public int removeFields(String s)
    {
        if(s != null)
        {
            String s1 = s.toLowerCase(Locale.US);
            List list = (List)fieldMap.remove(s1);
            if(list != null && !list.isEmpty())
            {
                fields.removeAll(list);
                return list.size();
            }
        }
        return 0;
    }

    public void setField(MinimalField minimalfield)
    {
        if(minimalfield == null)
            return;
        String s = minimalfield.getName().toLowerCase(Locale.US);
        List list = (List)fieldMap.get(s);
        if(list == null || list.isEmpty())
        {
            addField(minimalfield);
            return;
        }
        list.clear();
        list.add(minimalfield);
        int i = -1;
        int j = 0;
        for(Iterator iterator1 = fields.iterator(); iterator1.hasNext(); j++)
        {
            if(!((MinimalField)iterator1.next()).getName().equalsIgnoreCase(minimalfield.getName()))
                continue;
            iterator1.remove();
            if(i == -1)
                i = j;
        }

        fields.add(i, minimalfield);
    }

    public String toString()
    {
        return fields.toString();
    }

    private final Map fieldMap = new HashMap();
    private final List fields = new LinkedList();
}
