// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.builder;


final class IDKey
{

    public IDKey(Object obj)
    {
        id = System.identityHashCode(obj);
        value = obj;
    }

    public boolean equals(Object obj)
    {
        IDKey idkey;
        if(obj instanceof IDKey)
            if(id == (idkey = (IDKey)obj).id && value == idkey.value)
                return true;
        return false;
    }

    public int hashCode()
    {
        return id;
    }

    private final int id;
    private final Object value;
}
