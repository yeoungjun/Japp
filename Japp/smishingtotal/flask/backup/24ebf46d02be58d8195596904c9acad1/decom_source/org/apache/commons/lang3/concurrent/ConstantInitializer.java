// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.ObjectUtils;

// Referenced classes of package org.apache.commons.lang3.concurrent:
//            ConcurrentInitializer, ConcurrentException

public class ConstantInitializer
    implements ConcurrentInitializer
{

    public ConstantInitializer(Object obj)
    {
        object = obj;
    }

    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(!(obj instanceof ConstantInitializer))
        {
            return false;
        } else
        {
            ConstantInitializer constantinitializer = (ConstantInitializer)obj;
            return ObjectUtils.equals(getObject(), constantinitializer.getObject());
        }
    }

    public Object get()
        throws ConcurrentException
    {
        return getObject();
    }

    public final Object getObject()
    {
        return object;
    }

    public int hashCode()
    {
        if(getObject() != null)
            return getObject().hashCode();
        else
            return 0;
    }

    public String toString()
    {
        Object aobj[] = new Object[2];
        aobj[0] = Integer.valueOf(System.identityHashCode(this));
        aobj[1] = String.valueOf(getObject());
        return String.format("ConstantInitializer@%d [ object = %s ]", aobj);
    }

    private static final String FMT_TO_STRING = "ConstantInitializer@%d [ object = %s ]";
    private final Object object;
}
