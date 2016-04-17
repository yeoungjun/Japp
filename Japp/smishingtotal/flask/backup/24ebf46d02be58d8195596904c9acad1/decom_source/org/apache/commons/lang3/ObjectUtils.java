// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.apache.commons.lang3.mutable.MutableInt;

// Referenced classes of package org.apache.commons.lang3:
//            Validate, ArrayUtils

public class ObjectUtils
{
    public static class Null
        implements Serializable
    {

        private Object readResolve()
        {
            return ObjectUtils.NULL;
        }

        private static final long serialVersionUID = 0x626e04ed40667ec5L;

        Null()
        {
        }
    }


    public ObjectUtils()
    {
    }

    public static Object clone(Object obj)
    {
        if(!(obj instanceof Cloneable)) goto _L2; else goto _L1
_L1:
        if(!obj.getClass().isArray()) goto _L4; else goto _L3
_L3:
        Class class1 = obj.getClass().getComponentType();
        if(class1.isPrimitive()) goto _L6; else goto _L5
_L5:
        Object obj2 = ((Object []) ((Object[])(Object[])obj)).clone();
_L7:
        return obj2;
_L6:
        int i = Array.getLength(obj);
        obj2 = Array.newInstance(class1, i);
        int j = i;
        do
        {
            int k = j - 1;
            if(j <= 0)
                continue; /* Loop/switch isn't completed */
            Array.set(obj2, k, Array.get(obj, k));
            j = k;
        } while(true);
_L4:
        Object obj1;
        try
        {
            obj1 = obj.getClass().getMethod("clone", new Class[0]).invoke(obj, new Object[0]);
        }
        catch(NoSuchMethodException nosuchmethodexception)
        {
            throw new CloneFailedException((new StringBuilder()).append("Cloneable type ").append(obj.getClass().getName()).append(" has no clone method").toString(), nosuchmethodexception);
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new CloneFailedException((new StringBuilder()).append("Cannot clone Cloneable type ").append(obj.getClass().getName()).toString(), illegalaccessexception);
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            throw new CloneFailedException((new StringBuilder()).append("Exception cloning Cloneable type ").append(obj.getClass().getName()).toString(), invocationtargetexception.getCause());
        }
        obj2 = obj1;
        if(true) goto _L7; else goto _L2
_L2:
        return null;
    }

    public static Object cloneIfPossible(Object obj)
    {
        Object obj1 = clone(obj);
        if(obj1 == null)
            return obj;
        else
            return obj1;
    }

    public static int compare(Comparable comparable, Comparable comparable1)
    {
        return compare(comparable, comparable1, false);
    }

    public static int compare(Comparable comparable, Comparable comparable1, boolean flag)
    {
        int i = 1;
        int j = -1;
        if(comparable == comparable1)
            i = 0;
        else
        if(comparable == null)
        {
            if(!flag)
                return j;
        } else
        if(comparable1 == null)
        {
            if(!flag)
                j = i;
            return j;
        } else
        {
            return comparable.compareTo(comparable1);
        }
        return i;
    }

    public static Object defaultIfNull(Object obj, Object obj1)
    {
        if(obj != null)
            return obj;
        else
            return obj1;
    }

    public static boolean equals(Object obj, Object obj1)
    {
        if(obj == obj1)
            return true;
        if(obj == null || obj1 == null)
            return false;
        else
            return obj.equals(obj1);
    }

    public static transient Object firstNonNull(Object aobj[])
    {
        if(aobj != null)
        {
            int i = aobj.length;
            for(int j = 0; j < i; j++)
            {
                Object obj = aobj[j];
                if(obj != null)
                    return obj;
            }

        }
        return null;
    }

    public static int hashCode(Object obj)
    {
        if(obj == null)
            return 0;
        else
            return obj.hashCode();
    }

    public static transient int hashCodeMulti(Object aobj[])
    {
        int i = 1;
        if(aobj != null)
        {
            int j = aobj.length;
            for(int k = 0; k < j; k++)
            {
                Object obj = aobj[k];
                i = i * 31 + hashCode(obj);
            }

        }
        return i;
    }

    public static String identityToString(Object obj)
    {
        if(obj == null)
        {
            return null;
        } else
        {
            StringBuffer stringbuffer = new StringBuffer();
            identityToString(stringbuffer, obj);
            return stringbuffer.toString();
        }
    }

    public static void identityToString(StringBuffer stringbuffer, Object obj)
    {
        if(obj == null)
        {
            throw new NullPointerException("Cannot get the toString of a null identity");
        } else
        {
            stringbuffer.append(obj.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(obj)));
            return;
        }
    }

    public static transient Comparable max(Comparable acomparable[])
    {
        Comparable comparable = null;
        if(acomparable != null)
        {
            int i = acomparable.length;
            for(int j = 0; j < i; j++)
            {
                Comparable comparable1 = acomparable[j];
                if(compare(comparable1, comparable, false) > 0)
                    comparable = comparable1;
            }

        }
        return comparable;
    }

    public static transient Comparable median(Comparable acomparable[])
    {
        Validate.notEmpty(acomparable);
        Validate.noNullElements(acomparable);
        TreeSet treeset = new TreeSet();
        Collections.addAll(treeset, acomparable);
        return (Comparable)treeset.toArray()[(-1 + treeset.size()) / 2];
    }

    public static transient Object median(Comparator comparator, Object aobj[])
    {
        Validate.notEmpty(aobj, "null/empty items", new Object[0]);
        Validate.noNullElements(aobj);
        Validate.notNull(comparator, "null comparator", new Object[0]);
        TreeSet treeset = new TreeSet(comparator);
        Collections.addAll(treeset, aobj);
        return treeset.toArray()[(-1 + treeset.size()) / 2];
    }

    public static transient Comparable min(Comparable acomparable[])
    {
        Comparable comparable = null;
        if(acomparable != null)
        {
            int i = acomparable.length;
            for(int j = 0; j < i; j++)
            {
                Comparable comparable1 = acomparable[j];
                if(compare(comparable1, comparable, true) < 0)
                    comparable = comparable1;
            }

        }
        return comparable;
    }

    public static transient Object mode(Object aobj[])
    {
        Object obj;
        if(ArrayUtils.isNotEmpty(aobj))
        {
            HashMap hashmap = new HashMap(aobj.length);
            int i = aobj.length;
            int j = 0;
            while(j < i) 
            {
                Object obj1 = aobj[j];
                MutableInt mutableint = (MutableInt)hashmap.get(obj1);
                if(mutableint == null)
                    hashmap.put(obj1, new MutableInt(1));
                else
                    mutableint.increment();
                j++;
            }
            obj = null;
            int k = 0;
            Iterator iterator = hashmap.entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                int l = ((MutableInt)entry.getValue()).intValue();
                if(l == k)
                    obj = null;
                else
                if(l > k)
                {
                    k = l;
                    obj = entry.getKey();
                }
            } while(true);
        } else
        {
            obj = null;
        }
        return obj;
    }

    public static boolean notEqual(Object obj, Object obj1)
    {
        return !equals(obj, obj1);
    }

    public static String toString(Object obj)
    {
        if(obj == null)
            return "";
        else
            return obj.toString();
    }

    public static String toString(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return obj.toString();
    }

    public static final Null NULL = new Null();

}
