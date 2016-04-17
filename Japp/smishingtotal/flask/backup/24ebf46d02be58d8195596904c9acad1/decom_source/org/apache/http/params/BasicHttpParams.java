// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.params;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Referenced classes of package org.apache.http.params:
//            AbstractHttpParams, HttpParams

public class BasicHttpParams extends AbstractHttpParams
    implements Serializable, Cloneable
{

    public BasicHttpParams()
    {
    }

    public void clear()
    {
        parameters.clear();
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        BasicHttpParams basichttpparams = (BasicHttpParams)super.clone();
        copyParams(basichttpparams);
        return basichttpparams;
    }

    public HttpParams copy()
    {
        HttpParams httpparams;
        try
        {
            httpparams = (HttpParams)clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new UnsupportedOperationException("Cloning not supported");
        }
        return httpparams;
    }

    public void copyParams(HttpParams httpparams)
    {
        java.util.Map.Entry entry;
        for(Iterator iterator = parameters.entrySet().iterator(); iterator.hasNext(); httpparams.setParameter((String)entry.getKey(), entry.getValue()))
            entry = (java.util.Map.Entry)iterator.next();

    }

    public Set getNames()
    {
        return new HashSet(parameters.keySet());
    }

    public Object getParameter(String s)
    {
        return parameters.get(s);
    }

    public boolean isParameterSet(String s)
    {
        return getParameter(s) != null;
    }

    public boolean isParameterSetLocally(String s)
    {
        return parameters.get(s) != null;
    }

    public boolean removeParameter(String s)
    {
        if(parameters.containsKey(s))
        {
            parameters.remove(s);
            return true;
        } else
        {
            return false;
        }
    }

    public HttpParams setParameter(String s, Object obj)
    {
        if(s == null)
            return this;
        if(obj != null)
        {
            parameters.put(s, obj);
            return this;
        } else
        {
            parameters.remove(s);
            return this;
        }
    }

    public void setParameters(String as[], Object obj)
    {
        int i = as.length;
        for(int j = 0; j < i; j++)
            setParameter(as[j], obj);

    }

    private static final long serialVersionUID = 0x9da80e1f568bbef1L;
    private final Map parameters = new ConcurrentHashMap();
}
