// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.builder;

import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

// Referenced classes of package org.apache.commons.lang3.builder:
//            Builder, IDKey, ReflectionToStringBuilder

public class EqualsBuilder
    implements Builder
{

    public EqualsBuilder()
    {
        isEquals = true;
    }

    static Pair getRegisterPair(Object obj, Object obj1)
    {
        return Pair.of(new IDKey(obj), new IDKey(obj1));
    }

    static Set getRegistry()
    {
        return (Set)REGISTRY.get();
    }

    static boolean isRegistered(Object obj, Object obj1)
    {
        Set set = getRegistry();
        Pair pair = getRegisterPair(obj, obj1);
        Pair pair1 = Pair.of(pair.getLeft(), pair.getRight());
        return set != null && (set.contains(pair) || set.contains(pair1));
    }

    private static void reflectionAppend(Object obj, Object obj1, Class class1, EqualsBuilder equalsbuilder, boolean flag, String as[])
    {
        if(isRegistered(obj, obj1))
            return;
        Field afield[];
        register(obj, obj1);
        afield = class1.getDeclaredFields();
        AccessibleObject.setAccessible(afield, true);
        int i = 0;
_L1:
        Field field;
        if(i >= afield.length || !equalsbuilder.isEquals)
            break MISSING_BLOCK_LABEL_154;
        field = afield[i];
        if(ArrayUtils.contains(as, field.getName()) || field.getName().indexOf('$') != -1)
            break MISSING_BLOCK_LABEL_126;
        if(flag)
            break MISSING_BLOCK_LABEL_94;
        if(Modifier.isTransient(field.getModifiers()))
            break MISSING_BLOCK_LABEL_126;
        boolean flag1 = Modifier.isStatic(field.getModifiers());
        if(flag1)
            break MISSING_BLOCK_LABEL_126;
        equalsbuilder.append(field.get(obj), field.get(obj1));
        i++;
          goto _L1
        IllegalAccessException illegalaccessexception;
        illegalaccessexception;
        throw new InternalError("Unexpected IllegalAccessException");
        Exception exception;
        exception;
        unregister(obj, obj1);
        throw exception;
        unregister(obj, obj1);
        return;
    }

    public static boolean reflectionEquals(Object obj, Object obj1, Collection collection)
    {
        return reflectionEquals(obj, obj1, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }

    public static boolean reflectionEquals(Object obj, Object obj1, boolean flag)
    {
        return reflectionEquals(obj, obj1, flag, null, new String[0]);
    }

    public static transient boolean reflectionEquals(Object obj, Object obj1, boolean flag, Class class1, String as[])
    {
        Class class2;
        Class class3;
        if(obj == obj1)
            return true;
        if(obj == null || obj1 == null)
            return false;
        class2 = obj.getClass();
        class3 = obj1.getClass();
        if(!class2.isInstance(obj1)) goto _L2; else goto _L1
_L1:
        Class class4;
        class4 = class2;
        if(!class3.isInstance(obj))
            class4 = class3;
_L4:
        EqualsBuilder equalsbuilder = new EqualsBuilder();
        reflectionAppend(obj, obj1, class4, equalsbuilder, flag, as);
        while(class4.getSuperclass() != null && class4 != class1) 
            try
            {
                class4 = class4.getSuperclass();
                reflectionAppend(obj, obj1, class4, equalsbuilder, flag, as);
            }
            catch(IllegalArgumentException illegalargumentexception)
            {
                return false;
            }
        break MISSING_BLOCK_LABEL_147;
_L2:
        if(!class3.isInstance(obj))
            break; /* Loop/switch isn't completed */
        class4 = class3;
        if(!class2.isInstance(obj1))
            class4 = class2;
        if(true) goto _L4; else goto _L3
_L3:
        return false;
        return equalsbuilder.isEquals();
    }

    public static transient boolean reflectionEquals(Object obj, Object obj1, String as[])
    {
        return reflectionEquals(obj, obj1, false, null, as);
    }

    static void register(Object obj, Object obj1)
    {
        org/apache/commons/lang3/builder/EqualsBuilder;
        JVM INSTR monitorenter ;
        if(getRegistry() == null)
            REGISTRY.set(new HashSet());
        org/apache/commons/lang3/builder/EqualsBuilder;
        JVM INSTR monitorexit ;
        getRegistry().add(getRegisterPair(obj, obj1));
        return;
        Exception exception;
        exception;
        org/apache/commons/lang3/builder/EqualsBuilder;
        JVM INSTR monitorexit ;
        throw exception;
    }

    static void unregister(Object obj, Object obj1)
    {
        Set set = getRegistry();
        if(set == null)
            break MISSING_BLOCK_LABEL_61;
        set.remove(getRegisterPair(obj, obj1));
        org/apache/commons/lang3/builder/EqualsBuilder;
        JVM INSTR monitorenter ;
        Set set1 = getRegistry();
        if(set1 == null)
            break MISSING_BLOCK_LABEL_49;
        if(set1.isEmpty())
            REGISTRY.remove();
        org/apache/commons/lang3/builder/EqualsBuilder;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        org/apache/commons/lang3/builder/EqualsBuilder;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public EqualsBuilder append(byte byte0, byte byte1)
    {
        if(!isEquals)
            return this;
        boolean flag;
        if(byte0 == byte1)
            flag = true;
        else
            flag = false;
        isEquals = flag;
        return this;
    }

    public EqualsBuilder append(char c, char c1)
    {
        if(!isEquals)
            return this;
        boolean flag;
        if(c == c1)
            flag = true;
        else
            flag = false;
        isEquals = flag;
        return this;
    }

    public EqualsBuilder append(double d, double d1)
    {
        if(!isEquals)
            return this;
        else
            return append(Double.doubleToLongBits(d), Double.doubleToLongBits(d1));
    }

    public EqualsBuilder append(float f, float f1)
    {
        if(!isEquals)
            return this;
        else
            return append(Float.floatToIntBits(f), Float.floatToIntBits(f1));
    }

    public EqualsBuilder append(int i, int j)
    {
        if(!isEquals)
            return this;
        boolean flag;
        if(i == j)
            flag = true;
        else
            flag = false;
        isEquals = flag;
        return this;
    }

    public EqualsBuilder append(long l, long l1)
    {
        if(!isEquals)
            return this;
        boolean flag;
        if(l == l1)
            flag = true;
        else
            flag = false;
        isEquals = flag;
        return this;
    }

    public EqualsBuilder append(Object obj, Object obj1)
    {
        while(!isEquals || obj == obj1) 
            return this;
        if(obj == null || obj1 == null)
        {
            setEquals(false);
            return this;
        }
        if(!obj.getClass().isArray())
        {
            isEquals = obj.equals(obj1);
            return this;
        }
        if(obj.getClass() != obj1.getClass())
        {
            setEquals(false);
            return this;
        }
        if(obj instanceof long[])
        {
            append((long[])(long[])obj, (long[])(long[])obj1);
            return this;
        }
        if(obj instanceof int[])
        {
            append((int[])(int[])obj, (int[])(int[])obj1);
            return this;
        }
        if(obj instanceof short[])
        {
            append((short[])(short[])obj, (short[])(short[])obj1);
            return this;
        }
        if(obj instanceof char[])
        {
            append((char[])(char[])obj, (char[])(char[])obj1);
            return this;
        }
        if(obj instanceof byte[])
        {
            append((byte[])(byte[])obj, (byte[])(byte[])obj1);
            return this;
        }
        if(obj instanceof double[])
        {
            append((double[])(double[])obj, (double[])(double[])obj1);
            return this;
        }
        if(obj instanceof float[])
        {
            append((float[])(float[])obj, (float[])(float[])obj1);
            return this;
        }
        if(obj instanceof boolean[])
        {
            append((boolean[])(boolean[])obj, (boolean[])(boolean[])obj1);
            return this;
        } else
        {
            append((Object[])(Object[])obj, (Object[])(Object[])obj1);
            return this;
        }
    }

    public EqualsBuilder append(short word0, short word1)
    {
        if(!isEquals)
            return this;
        boolean flag;
        if(word0 == word1)
            flag = true;
        else
            flag = false;
        isEquals = flag;
        return this;
    }

    public EqualsBuilder append(boolean flag, boolean flag1)
    {
        if(!isEquals)
            return this;
        boolean flag2;
        if(flag == flag1)
            flag2 = true;
        else
            flag2 = false;
        isEquals = flag2;
        return this;
    }

    public EqualsBuilder append(byte abyte0[], byte abyte1[])
    {
        if(isEquals && abyte0 != abyte1)
        {
            if(abyte0 == null || abyte1 == null)
            {
                setEquals(false);
                return this;
            }
            if(abyte0.length != abyte1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < abyte0.length && isEquals) 
            {
                append(abyte0[i], abyte1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(char ac[], char ac1[])
    {
        if(isEquals && ac != ac1)
        {
            if(ac == null || ac1 == null)
            {
                setEquals(false);
                return this;
            }
            if(ac.length != ac1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < ac.length && isEquals) 
            {
                append(ac[i], ac1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(double ad[], double ad1[])
    {
        if(isEquals && ad != ad1)
        {
            if(ad == null || ad1 == null)
            {
                setEquals(false);
                return this;
            }
            if(ad.length != ad1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < ad.length && isEquals) 
            {
                append(ad[i], ad1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(float af[], float af1[])
    {
        if(isEquals && af != af1)
        {
            if(af == null || af1 == null)
            {
                setEquals(false);
                return this;
            }
            if(af.length != af1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < af.length && isEquals) 
            {
                append(af[i], af1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(int ai[], int ai1[])
    {
        if(isEquals && ai != ai1)
        {
            if(ai == null || ai1 == null)
            {
                setEquals(false);
                return this;
            }
            if(ai.length != ai1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < ai.length && isEquals) 
            {
                append(ai[i], ai1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(long al[], long al1[])
    {
        if(isEquals && al != al1)
        {
            if(al == null || al1 == null)
            {
                setEquals(false);
                return this;
            }
            if(al.length != al1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < al.length && isEquals) 
            {
                append(al[i], al1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(Object aobj[], Object aobj1[])
    {
        if(isEquals && aobj != aobj1)
        {
            if(aobj == null || aobj1 == null)
            {
                setEquals(false);
                return this;
            }
            if(aobj.length != aobj1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < aobj.length && isEquals) 
            {
                append(aobj[i], aobj1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(short aword0[], short aword1[])
    {
        if(isEquals && aword0 != aword1)
        {
            if(aword0 == null || aword1 == null)
            {
                setEquals(false);
                return this;
            }
            if(aword0.length != aword1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < aword0.length && isEquals) 
            {
                append(aword0[i], aword1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder append(boolean aflag[], boolean aflag1[])
    {
        if(isEquals && aflag != aflag1)
        {
            if(aflag == null || aflag1 == null)
            {
                setEquals(false);
                return this;
            }
            if(aflag.length != aflag1.length)
            {
                setEquals(false);
                return this;
            }
            int i = 0;
            while(i < aflag.length && isEquals) 
            {
                append(aflag[i], aflag1[i]);
                i++;
            }
        }
        return this;
    }

    public EqualsBuilder appendSuper(boolean flag)
    {
        if(!isEquals)
        {
            return this;
        } else
        {
            isEquals = flag;
            return this;
        }
    }

    public Boolean build()
    {
        return Boolean.valueOf(isEquals());
    }

    public volatile Object build()
    {
        return build();
    }

    public boolean isEquals()
    {
        return isEquals;
    }

    public void reset()
    {
        isEquals = true;
    }

    protected void setEquals(boolean flag)
    {
        isEquals = flag;
    }

    private static final ThreadLocal REGISTRY = new ThreadLocal();
    private boolean isEquals;

}
