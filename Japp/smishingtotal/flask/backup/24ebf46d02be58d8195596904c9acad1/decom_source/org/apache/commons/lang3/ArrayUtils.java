// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.mutable.MutableInt;

public class ArrayUtils
{

    public ArrayUtils()
    {
    }

    private static Object add(Object obj, int i, Object obj1, Class class1)
    {
        if(obj == null)
            if(i != 0)
            {
                throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(i).append(", Length: 0").toString());
            } else
            {
                Object obj3 = Array.newInstance(class1, 1);
                Array.set(obj3, 0, obj1);
                return obj3;
            }
        int j = Array.getLength(obj);
        if(i > j || i < 0)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(i).append(", Length: ").append(j).toString());
        Object obj2 = Array.newInstance(class1, j + 1);
        System.arraycopy(obj, 0, obj2, 0, i);
        Array.set(obj2, i, obj1);
        if(i < j)
            System.arraycopy(obj, i, obj2, i + 1, j - i);
        return obj2;
    }

    public static byte[] add(byte abyte0[], byte byte0)
    {
        byte abyte1[] = (byte[])(byte[])copyArrayGrow1(abyte0, Byte.TYPE);
        abyte1[-1 + abyte1.length] = byte0;
        return abyte1;
    }

    public static byte[] add(byte abyte0[], int i, byte byte0)
    {
        return (byte[])(byte[])add(abyte0, i, Byte.valueOf(byte0), Byte.TYPE);
    }

    public static char[] add(char ac[], char c)
    {
        char ac1[] = (char[])(char[])copyArrayGrow1(ac, Character.TYPE);
        ac1[-1 + ac1.length] = c;
        return ac1;
    }

    public static char[] add(char ac[], int i, char c)
    {
        return (char[])(char[])add(ac, i, Character.valueOf(c), Character.TYPE);
    }

    public static double[] add(double ad[], double d)
    {
        double ad1[] = (double[])(double[])copyArrayGrow1(ad, Double.TYPE);
        ad1[-1 + ad1.length] = d;
        return ad1;
    }

    public static double[] add(double ad[], int i, double d)
    {
        return (double[])(double[])add(ad, i, Double.valueOf(d), Double.TYPE);
    }

    public static float[] add(float af[], float f)
    {
        float af1[] = (float[])(float[])copyArrayGrow1(af, Float.TYPE);
        af1[-1 + af1.length] = f;
        return af1;
    }

    public static float[] add(float af[], int i, float f)
    {
        return (float[])(float[])add(af, i, Float.valueOf(f), Float.TYPE);
    }

    public static int[] add(int ai[], int i)
    {
        int ai1[] = (int[])(int[])copyArrayGrow1(ai, Integer.TYPE);
        ai1[-1 + ai1.length] = i;
        return ai1;
    }

    public static int[] add(int ai[], int i, int j)
    {
        return (int[])(int[])add(ai, i, Integer.valueOf(j), Integer.TYPE);
    }

    public static long[] add(long al[], int i, long l)
    {
        return (long[])(long[])add(al, i, Long.valueOf(l), Long.TYPE);
    }

    public static long[] add(long al[], long l)
    {
        long al1[] = (long[])(long[])copyArrayGrow1(al, Long.TYPE);
        al1[-1 + al1.length] = l;
        return al1;
    }

    public static Object[] add(Object aobj[], int i, Object obj)
    {
        Class class1;
        if(aobj != null)
            class1 = ((Object) (aobj)).getClass().getComponentType();
        else
        if(obj != null)
            class1 = obj.getClass();
        else
            throw new IllegalArgumentException("Array and element cannot both be null");
        return (Object[])(Object[])add(((Object) (aobj)), i, obj, class1);
    }

    public static Object[] add(Object aobj[], Object obj)
    {
        Class class1;
        Object aobj1[];
        if(aobj != null)
            class1 = ((Object) (aobj)).getClass();
        else
        if(obj != null)
            class1 = obj.getClass();
        else
            throw new IllegalArgumentException("Arguments cannot both be null");
        aobj1 = (Object[])(Object[])copyArrayGrow1(((Object) (aobj)), class1);
        aobj1[-1 + aobj1.length] = obj;
        return aobj1;
    }

    public static short[] add(short aword0[], int i, short word0)
    {
        return (short[])(short[])add(aword0, i, Short.valueOf(word0), Short.TYPE);
    }

    public static short[] add(short aword0[], short word0)
    {
        short aword1[] = (short[])(short[])copyArrayGrow1(aword0, Short.TYPE);
        aword1[-1 + aword1.length] = word0;
        return aword1;
    }

    public static boolean[] add(boolean aflag[], int i, boolean flag)
    {
        return (boolean[])(boolean[])add(aflag, i, Boolean.valueOf(flag), Boolean.TYPE);
    }

    public static boolean[] add(boolean aflag[], boolean flag)
    {
        boolean aflag1[] = (boolean[])(boolean[])copyArrayGrow1(aflag, Boolean.TYPE);
        aflag1[-1 + aflag1.length] = flag;
        return aflag1;
    }

    public static transient byte[] addAll(byte abyte0[], byte abyte1[])
    {
        if(abyte0 == null)
            return clone(abyte1);
        if(abyte1 == null)
        {
            return clone(abyte0);
        } else
        {
            byte abyte2[] = new byte[abyte0.length + abyte1.length];
            System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
            System.arraycopy(abyte1, 0, abyte2, abyte0.length, abyte1.length);
            return abyte2;
        }
    }

    public static transient char[] addAll(char ac[], char ac1[])
    {
        if(ac == null)
            return clone(ac1);
        if(ac1 == null)
        {
            return clone(ac);
        } else
        {
            char ac2[] = new char[ac.length + ac1.length];
            System.arraycopy(ac, 0, ac2, 0, ac.length);
            System.arraycopy(ac1, 0, ac2, ac.length, ac1.length);
            return ac2;
        }
    }

    public static transient double[] addAll(double ad[], double ad1[])
    {
        if(ad == null)
            return clone(ad1);
        if(ad1 == null)
        {
            return clone(ad);
        } else
        {
            double ad2[] = new double[ad.length + ad1.length];
            System.arraycopy(ad, 0, ad2, 0, ad.length);
            System.arraycopy(ad1, 0, ad2, ad.length, ad1.length);
            return ad2;
        }
    }

    public static transient float[] addAll(float af[], float af1[])
    {
        if(af == null)
            return clone(af1);
        if(af1 == null)
        {
            return clone(af);
        } else
        {
            float af2[] = new float[af.length + af1.length];
            System.arraycopy(af, 0, af2, 0, af.length);
            System.arraycopy(af1, 0, af2, af.length, af1.length);
            return af2;
        }
    }

    public static transient int[] addAll(int ai[], int ai1[])
    {
        if(ai == null)
            return clone(ai1);
        if(ai1 == null)
        {
            return clone(ai);
        } else
        {
            int ai2[] = new int[ai.length + ai1.length];
            System.arraycopy(ai, 0, ai2, 0, ai.length);
            System.arraycopy(ai1, 0, ai2, ai.length, ai1.length);
            return ai2;
        }
    }

    public static transient long[] addAll(long al[], long al1[])
    {
        if(al == null)
            return clone(al1);
        if(al1 == null)
        {
            return clone(al);
        } else
        {
            long al2[] = new long[al.length + al1.length];
            System.arraycopy(al, 0, al2, 0, al.length);
            System.arraycopy(al1, 0, al2, al.length, al1.length);
            return al2;
        }
    }

    public static transient Object[] addAll(Object aobj[], Object aobj1[])
    {
        if(aobj == null)
            return clone(aobj1);
        if(aobj1 == null)
            return clone(aobj);
        Class class1 = ((Object) (aobj)).getClass().getComponentType();
        Object aobj2[] = (Object[])(Object[])Array.newInstance(class1, aobj.length + aobj1.length);
        System.arraycopy(((Object) (aobj)), 0, ((Object) (aobj2)), 0, aobj.length);
        try
        {
            System.arraycopy(((Object) (aobj1)), 0, ((Object) (aobj2)), aobj.length, aobj1.length);
        }
        catch(ArrayStoreException arraystoreexception)
        {
            Class class2 = ((Object) (aobj1)).getClass().getComponentType();
            if(!class1.isAssignableFrom(class2))
                throw new IllegalArgumentException((new StringBuilder()).append("Cannot store ").append(class2.getName()).append(" in an array of ").append(class1.getName()).toString(), arraystoreexception);
            else
                throw arraystoreexception;
        }
        return aobj2;
    }

    public static transient short[] addAll(short aword0[], short aword1[])
    {
        if(aword0 == null)
            return clone(aword1);
        if(aword1 == null)
        {
            return clone(aword0);
        } else
        {
            short aword2[] = new short[aword0.length + aword1.length];
            System.arraycopy(aword0, 0, aword2, 0, aword0.length);
            System.arraycopy(aword1, 0, aword2, aword0.length, aword1.length);
            return aword2;
        }
    }

    public static transient boolean[] addAll(boolean aflag[], boolean aflag1[])
    {
        if(aflag == null)
            return clone(aflag1);
        if(aflag1 == null)
        {
            return clone(aflag);
        } else
        {
            boolean aflag2[] = new boolean[aflag.length + aflag1.length];
            System.arraycopy(aflag, 0, aflag2, 0, aflag.length);
            System.arraycopy(aflag1, 0, aflag2, aflag.length, aflag1.length);
            return aflag2;
        }
    }

    public static byte[] clone(byte abyte0[])
    {
        if(abyte0 == null)
            return null;
        else
            return (byte[])abyte0.clone();
    }

    public static char[] clone(char ac[])
    {
        if(ac == null)
            return null;
        else
            return (char[])ac.clone();
    }

    public static double[] clone(double ad[])
    {
        if(ad == null)
            return null;
        else
            return (double[])ad.clone();
    }

    public static float[] clone(float af[])
    {
        if(af == null)
            return null;
        else
            return (float[])af.clone();
    }

    public static int[] clone(int ai[])
    {
        if(ai == null)
            return null;
        else
            return (int[])ai.clone();
    }

    public static long[] clone(long al[])
    {
        if(al == null)
            return null;
        else
            return (long[])al.clone();
    }

    public static Object[] clone(Object aobj[])
    {
        if(aobj == null)
            return null;
        else
            return (Object[])((Object []) (aobj)).clone();
    }

    public static short[] clone(short aword0[])
    {
        if(aword0 == null)
            return null;
        else
            return (short[])aword0.clone();
    }

    public static boolean[] clone(boolean aflag[])
    {
        if(aflag == null)
            return null;
        else
            return (boolean[])aflag.clone();
    }

    public static boolean contains(byte abyte0[], byte byte0)
    {
        return indexOf(abyte0, byte0) != -1;
    }

    public static boolean contains(char ac[], char c)
    {
        return indexOf(ac, c) != -1;
    }

    public static boolean contains(double ad[], double d)
    {
        return indexOf(ad, d) != -1;
    }

    public static boolean contains(double ad[], double d, double d1)
    {
        int i = indexOf(ad, d, 0, d1);
        boolean flag = false;
        if(i != -1)
            flag = true;
        return flag;
    }

    public static boolean contains(float af[], float f)
    {
        return indexOf(af, f) != -1;
    }

    public static boolean contains(int ai[], int i)
    {
        return indexOf(ai, i) != -1;
    }

    public static boolean contains(long al[], long l)
    {
        return indexOf(al, l) != -1;
    }

    public static boolean contains(Object aobj[], Object obj)
    {
        return indexOf(aobj, obj) != -1;
    }

    public static boolean contains(short aword0[], short word0)
    {
        return indexOf(aword0, word0) != -1;
    }

    public static boolean contains(boolean aflag[], boolean flag)
    {
        return indexOf(aflag, flag) != -1;
    }

    private static Object copyArrayGrow1(Object obj, Class class1)
    {
        if(obj != null)
        {
            int i = Array.getLength(obj);
            Object obj1 = Array.newInstance(obj.getClass().getComponentType(), i + 1);
            System.arraycopy(obj, 0, obj1, 0, i);
            return obj1;
        } else
        {
            return Array.newInstance(class1, 1);
        }
    }

    private static int[] extractIndices(HashSet hashset)
    {
        int ai[] = new int[hashset.size()];
        int i = 0;
        for(Iterator iterator = hashset.iterator(); iterator.hasNext();)
        {
            Integer integer = (Integer)iterator.next();
            int j = i + 1;
            ai[i] = integer.intValue();
            i = j;
        }

        return ai;
    }

    public static int getLength(Object obj)
    {
        if(obj == null)
            return 0;
        else
            return Array.getLength(obj);
    }

    public static int hashCode(Object obj)
    {
        return (new HashCodeBuilder()).append(obj).toHashCode();
    }

    public static int indexOf(byte abyte0[], byte byte0)
    {
        return indexOf(abyte0, byte0, 0);
    }

    public static int indexOf(byte abyte0[], byte byte0, int i)
    {
        if(abyte0 != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= abyte0.length)
                    break label1;
                if(byte0 == abyte0[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(char ac[], char c)
    {
        return indexOf(ac, c, 0);
    }

    public static int indexOf(char ac[], char c, int i)
    {
        if(ac != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= ac.length)
                    break label1;
                if(c == ac[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(double ad[], double d)
    {
        return indexOf(ad, d, 0);
    }

    public static int indexOf(double ad[], double d, double d1)
    {
        return indexOf(ad, d, 0, d1);
    }

    public static int indexOf(double ad[], double d, int i)
    {
        if(!isEmpty(ad)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= ad.length)
                    break label1;
                if(d == ad[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(double ad[], double d, int i, double d1)
    {
        if(!isEmpty(ad)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        double d2 = d - d1;
        double d3 = d + d1;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= ad.length)
                    break label1;
                if(ad[j] >= d2 && ad[j] <= d3)
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(float af[], float f)
    {
        return indexOf(af, f, 0);
    }

    public static int indexOf(float af[], float f, int i)
    {
        if(!isEmpty(af)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= af.length)
                    break label1;
                if(f == af[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(int ai[], int i)
    {
        return indexOf(ai, i, 0);
    }

    public static int indexOf(int ai[], int i, int j)
    {
        if(ai != null) goto _L2; else goto _L1
_L1:
        int k = -1;
_L4:
        return k;
_L2:
        if(j < 0)
            j = 0;
        k = j;
label0:
        do
        {
label1:
            {
                if(k >= ai.length)
                    break label1;
                if(i == ai[k])
                    break label0;
                k++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(long al[], long l)
    {
        return indexOf(al, l, 0);
    }

    public static int indexOf(long al[], long l, int i)
    {
        if(al != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= al.length)
                    break label1;
                if(l == al[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(Object aobj[], Object obj)
    {
        return indexOf(aobj, obj, 0);
    }

    public static int indexOf(Object aobj[], Object obj, int i)
    {
        if(aobj != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
label0:
        {
            if(i < 0)
                i = 0;
            if(obj == null)
            {
                j = i;
                do
                {
                    if(j >= aobj.length)
                        break label0;
                    if(aobj[j] == null)
                        continue; /* Loop/switch isn't completed */
                    j++;
                } while(true);
            }
            if(!((Object) (aobj)).getClass().getComponentType().isInstance(obj))
                break label0;
            j = i;
            do
            {
                if(j >= aobj.length)
                    break label0;
                if(obj.equals(aobj[j]))
                    break;
                j++;
            } while(true);
        }
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(short aword0[], short word0)
    {
        return indexOf(aword0, word0, 0);
    }

    public static int indexOf(short aword0[], short word0, int i)
    {
        if(aword0 != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= aword0.length)
                    break label1;
                if(word0 == aword0[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int indexOf(boolean aflag[], boolean flag)
    {
        return indexOf(aflag, flag, 0);
    }

    public static int indexOf(boolean aflag[], boolean flag, int i)
    {
        if(!isEmpty(aflag)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= aflag.length)
                    break label1;
                if(flag == aflag[j])
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static boolean isEmpty(byte abyte0[])
    {
        return abyte0 == null || abyte0.length == 0;
    }

    public static boolean isEmpty(char ac[])
    {
        return ac == null || ac.length == 0;
    }

    public static boolean isEmpty(double ad[])
    {
        return ad == null || ad.length == 0;
    }

    public static boolean isEmpty(float af[])
    {
        return af == null || af.length == 0;
    }

    public static boolean isEmpty(int ai[])
    {
        return ai == null || ai.length == 0;
    }

    public static boolean isEmpty(long al[])
    {
        return al == null || al.length == 0;
    }

    public static boolean isEmpty(Object aobj[])
    {
        return aobj == null || aobj.length == 0;
    }

    public static boolean isEmpty(short aword0[])
    {
        return aword0 == null || aword0.length == 0;
    }

    public static boolean isEmpty(boolean aflag[])
    {
        return aflag == null || aflag.length == 0;
    }

    public static boolean isEquals(Object obj, Object obj1)
    {
        return (new EqualsBuilder()).append(obj, obj1).isEquals();
    }

    public static boolean isNotEmpty(byte abyte0[])
    {
        return abyte0 != null && abyte0.length != 0;
    }

    public static boolean isNotEmpty(char ac[])
    {
        return ac != null && ac.length != 0;
    }

    public static boolean isNotEmpty(double ad[])
    {
        return ad != null && ad.length != 0;
    }

    public static boolean isNotEmpty(float af[])
    {
        return af != null && af.length != 0;
    }

    public static boolean isNotEmpty(int ai[])
    {
        return ai != null && ai.length != 0;
    }

    public static boolean isNotEmpty(long al[])
    {
        return al != null && al.length != 0;
    }

    public static boolean isNotEmpty(Object aobj[])
    {
        return aobj != null && aobj.length != 0;
    }

    public static boolean isNotEmpty(short aword0[])
    {
        return aword0 != null && aword0.length != 0;
    }

    public static boolean isNotEmpty(boolean aflag[])
    {
        return aflag != null && aflag.length != 0;
    }

    public static boolean isSameLength(byte abyte0[], byte abyte1[])
    {
        return (abyte0 != null || abyte1 == null || abyte1.length <= 0) && (abyte1 != null || abyte0 == null || abyte0.length <= 0) && (abyte0 == null || abyte1 == null || abyte0.length == abyte1.length);
    }

    public static boolean isSameLength(char ac[], char ac1[])
    {
        return (ac != null || ac1 == null || ac1.length <= 0) && (ac1 != null || ac == null || ac.length <= 0) && (ac == null || ac1 == null || ac.length == ac1.length);
    }

    public static boolean isSameLength(double ad[], double ad1[])
    {
        return (ad != null || ad1 == null || ad1.length <= 0) && (ad1 != null || ad == null || ad.length <= 0) && (ad == null || ad1 == null || ad.length == ad1.length);
    }

    public static boolean isSameLength(float af[], float af1[])
    {
        return (af != null || af1 == null || af1.length <= 0) && (af1 != null || af == null || af.length <= 0) && (af == null || af1 == null || af.length == af1.length);
    }

    public static boolean isSameLength(int ai[], int ai1[])
    {
        return (ai != null || ai1 == null || ai1.length <= 0) && (ai1 != null || ai == null || ai.length <= 0) && (ai == null || ai1 == null || ai.length == ai1.length);
    }

    public static boolean isSameLength(long al[], long al1[])
    {
        return (al != null || al1 == null || al1.length <= 0) && (al1 != null || al == null || al.length <= 0) && (al == null || al1 == null || al.length == al1.length);
    }

    public static boolean isSameLength(Object aobj[], Object aobj1[])
    {
        return (aobj != null || aobj1 == null || aobj1.length <= 0) && (aobj1 != null || aobj == null || aobj.length <= 0) && (aobj == null || aobj1 == null || aobj.length == aobj1.length);
    }

    public static boolean isSameLength(short aword0[], short aword1[])
    {
        return (aword0 != null || aword1 == null || aword1.length <= 0) && (aword1 != null || aword0 == null || aword0.length <= 0) && (aword0 == null || aword1 == null || aword0.length == aword1.length);
    }

    public static boolean isSameLength(boolean aflag[], boolean aflag1[])
    {
        return (aflag != null || aflag1 == null || aflag1.length <= 0) && (aflag1 != null || aflag == null || aflag.length <= 0) && (aflag == null || aflag1 == null || aflag.length == aflag1.length);
    }

    public static boolean isSameType(Object obj, Object obj1)
    {
        if(obj == null || obj1 == null)
            throw new IllegalArgumentException("The Array must not be null");
        else
            return obj.getClass().getName().equals(obj1.getClass().getName());
    }

    public static int lastIndexOf(byte abyte0[], byte byte0)
    {
        return lastIndexOf(abyte0, byte0, 0x7fffffff);
    }

    public static int lastIndexOf(byte abyte0[], byte byte0, int i)
    {
        if(abyte0 != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= abyte0.length)
            i = -1 + abyte0.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(byte0 == abyte0[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(char ac[], char c)
    {
        return lastIndexOf(ac, c, 0x7fffffff);
    }

    public static int lastIndexOf(char ac[], char c, int i)
    {
        if(ac != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= ac.length)
            i = -1 + ac.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(c == ac[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(double ad[], double d)
    {
        return lastIndexOf(ad, d, 0x7fffffff);
    }

    public static int lastIndexOf(double ad[], double d, double d1)
    {
        return lastIndexOf(ad, d, 0x7fffffff, d1);
    }

    public static int lastIndexOf(double ad[], double d, int i)
    {
        if(!isEmpty(ad)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= ad.length)
            i = -1 + ad.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(d == ad[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(double ad[], double d, int i, double d1)
    {
        if(!isEmpty(ad)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= ad.length)
            i = -1 + ad.length;
        double d2 = d - d1;
        double d3 = d + d1;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(ad[j] >= d2 && ad[j] <= d3)
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(float af[], float f)
    {
        return lastIndexOf(af, f, 0x7fffffff);
    }

    public static int lastIndexOf(float af[], float f, int i)
    {
        if(!isEmpty(af)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= af.length)
            i = -1 + af.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(f == af[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(int ai[], int i)
    {
        return lastIndexOf(ai, i, 0x7fffffff);
    }

    public static int lastIndexOf(int ai[], int i, int j)
    {
        if(ai != null) goto _L2; else goto _L1
_L1:
        int k = -1;
_L4:
        return k;
_L2:
        if(j < 0)
            return -1;
        if(j >= ai.length)
            j = -1 + ai.length;
        k = j;
label0:
        do
        {
label1:
            {
                if(k < 0)
                    break label1;
                if(i == ai[k])
                    break label0;
                k--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(long al[], long l)
    {
        return lastIndexOf(al, l, 0x7fffffff);
    }

    public static int lastIndexOf(long al[], long l, int i)
    {
        if(al != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= al.length)
            i = -1 + al.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(l == al[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(Object aobj[], Object obj)
    {
        return lastIndexOf(aobj, obj, 0x7fffffff);
    }

    public static int lastIndexOf(Object aobj[], Object obj, int i)
    {
        if(aobj != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
label0:
        {
            if(i < 0)
                return -1;
            if(i >= aobj.length)
                i = -1 + aobj.length;
            if(obj == null)
            {
                j = i;
                do
                {
                    if(j < 0)
                        break label0;
                    if(aobj[j] == null)
                        continue; /* Loop/switch isn't completed */
                    j--;
                } while(true);
            }
            if(!((Object) (aobj)).getClass().getComponentType().isInstance(obj))
                break label0;
            j = i;
            do
            {
                if(j < 0)
                    break label0;
                if(obj.equals(aobj[j]))
                    break;
                j--;
            } while(true);
        }
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(short aword0[], short word0)
    {
        return lastIndexOf(aword0, word0, 0x7fffffff);
    }

    public static int lastIndexOf(short aword0[], short word0, int i)
    {
        if(aword0 != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= aword0.length)
            i = -1 + aword0.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(word0 == aword0[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastIndexOf(boolean aflag[], boolean flag)
    {
        return lastIndexOf(aflag, flag, 0x7fffffff);
    }

    public static int lastIndexOf(boolean aflag[], boolean flag, int i)
    {
        if(!isEmpty(aflag)) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            return -1;
        if(i >= aflag.length)
            i = -1 + aflag.length;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(flag == aflag[j])
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static byte[] nullToEmpty(byte abyte0[])
    {
        if(abyte0 == null || abyte0.length == 0)
            abyte0 = EMPTY_BYTE_ARRAY;
        return abyte0;
    }

    public static char[] nullToEmpty(char ac[])
    {
        if(ac == null || ac.length == 0)
            ac = EMPTY_CHAR_ARRAY;
        return ac;
    }

    public static double[] nullToEmpty(double ad[])
    {
        if(ad == null || ad.length == 0)
            ad = EMPTY_DOUBLE_ARRAY;
        return ad;
    }

    public static float[] nullToEmpty(float af[])
    {
        if(af == null || af.length == 0)
            af = EMPTY_FLOAT_ARRAY;
        return af;
    }

    public static int[] nullToEmpty(int ai[])
    {
        if(ai == null || ai.length == 0)
            ai = EMPTY_INT_ARRAY;
        return ai;
    }

    public static long[] nullToEmpty(long al[])
    {
        if(al == null || al.length == 0)
            al = EMPTY_LONG_ARRAY;
        return al;
    }

    public static Boolean[] nullToEmpty(Boolean aboolean[])
    {
        if(aboolean == null || aboolean.length == 0)
            aboolean = EMPTY_BOOLEAN_OBJECT_ARRAY;
        return aboolean;
    }

    public static Byte[] nullToEmpty(Byte abyte[])
    {
        if(abyte == null || abyte.length == 0)
            abyte = EMPTY_BYTE_OBJECT_ARRAY;
        return abyte;
    }

    public static Character[] nullToEmpty(Character acharacter[])
    {
        if(acharacter == null || acharacter.length == 0)
            acharacter = EMPTY_CHARACTER_OBJECT_ARRAY;
        return acharacter;
    }

    public static Double[] nullToEmpty(Double adouble[])
    {
        if(adouble == null || adouble.length == 0)
            adouble = EMPTY_DOUBLE_OBJECT_ARRAY;
        return adouble;
    }

    public static Float[] nullToEmpty(Float afloat[])
    {
        if(afloat == null || afloat.length == 0)
            afloat = EMPTY_FLOAT_OBJECT_ARRAY;
        return afloat;
    }

    public static Integer[] nullToEmpty(Integer ainteger[])
    {
        if(ainteger == null || ainteger.length == 0)
            ainteger = EMPTY_INTEGER_OBJECT_ARRAY;
        return ainteger;
    }

    public static Long[] nullToEmpty(Long along[])
    {
        if(along == null || along.length == 0)
            along = EMPTY_LONG_OBJECT_ARRAY;
        return along;
    }

    public static Object[] nullToEmpty(Object aobj[])
    {
        if(aobj == null || aobj.length == 0)
            aobj = EMPTY_OBJECT_ARRAY;
        return aobj;
    }

    public static Short[] nullToEmpty(Short ashort[])
    {
        if(ashort == null || ashort.length == 0)
            ashort = EMPTY_SHORT_OBJECT_ARRAY;
        return ashort;
    }

    public static String[] nullToEmpty(String as[])
    {
        if(as == null || as.length == 0)
            as = EMPTY_STRING_ARRAY;
        return as;
    }

    public static short[] nullToEmpty(short aword0[])
    {
        if(aword0 == null || aword0.length == 0)
            aword0 = EMPTY_SHORT_ARRAY;
        return aword0;
    }

    public static boolean[] nullToEmpty(boolean aflag[])
    {
        if(aflag == null || aflag.length == 0)
            aflag = EMPTY_BOOLEAN_ARRAY;
        return aflag;
    }

    private static Object remove(Object obj, int i)
    {
        int j = getLength(obj);
        if(i < 0 || i >= j)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(i).append(", Length: ").append(j).toString());
        Object obj1 = Array.newInstance(obj.getClass().getComponentType(), j - 1);
        System.arraycopy(obj, 0, obj1, 0, i);
        if(i < j - 1)
            System.arraycopy(obj, i + 1, obj1, i, -1 + (j - i));
        return obj1;
    }

    public static byte[] remove(byte abyte0[], int i)
    {
        return (byte[])(byte[])remove(abyte0, i);
    }

    public static char[] remove(char ac[], int i)
    {
        return (char[])(char[])remove(ac, i);
    }

    public static double[] remove(double ad[], int i)
    {
        return (double[])(double[])remove(ad, i);
    }

    public static float[] remove(float af[], int i)
    {
        return (float[])(float[])remove(af, i);
    }

    public static int[] remove(int ai[], int i)
    {
        return (int[])(int[])remove(ai, i);
    }

    public static long[] remove(long al[], int i)
    {
        return (long[])(long[])remove(al, i);
    }

    public static Object[] remove(Object aobj[], int i)
    {
        return (Object[])(Object[])remove(((Object) (aobj)), i);
    }

    public static short[] remove(short aword0[], int i)
    {
        return (short[])(short[])remove(aword0, i);
    }

    public static boolean[] remove(boolean aflag[], int i)
    {
        return (boolean[])(boolean[])remove(aflag, i);
    }

    private static transient Object removeAll(Object obj, int ai[])
    {
        int i = getLength(obj);
        boolean flag = isNotEmpty(ai);
        int j = 0;
        if(flag)
        {
            Arrays.sort(ai);
            int l1 = ai.length;
            int i2 = i;
            do
            {
                if(--l1 < 0)
                    break;
                int j2 = ai[l1];
                if(j2 < 0 || j2 >= i)
                    throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(j2).append(", Length: ").append(i).toString());
                if(j2 < i2)
                {
                    j++;
                    i2 = j2;
                }
            } while(true);
        }
        Object obj1 = Array.newInstance(obj.getClass().getComponentType(), i - j);
        if(j < i)
        {
            int k = i;
            int l = i - j;
            for(int i1 = -1 + ai.length; i1 >= 0; i1--)
            {
                int j1 = ai[i1];
                if(k - j1 > 1)
                {
                    int k1 = -1 + (k - j1);
                    l -= k1;
                    System.arraycopy(obj, j1 + 1, obj1, l, k1);
                }
                k = j1;
            }

            if(k > 0)
                System.arraycopy(obj, 0, obj1, 0, k);
        }
        return obj1;
    }

    public static transient byte[] removeAll(byte abyte0[], int ai[])
    {
        return (byte[])(byte[])removeAll(abyte0, clone(ai));
    }

    public static transient char[] removeAll(char ac[], int ai[])
    {
        return (char[])(char[])removeAll(ac, clone(ai));
    }

    public static transient double[] removeAll(double ad[], int ai[])
    {
        return (double[])(double[])removeAll(ad, clone(ai));
    }

    public static transient float[] removeAll(float af[], int ai[])
    {
        return (float[])(float[])removeAll(af, clone(ai));
    }

    public static transient int[] removeAll(int ai[], int ai1[])
    {
        return (int[])(int[])removeAll(ai, clone(ai1));
    }

    public static transient long[] removeAll(long al[], int ai[])
    {
        return (long[])(long[])removeAll(al, clone(ai));
    }

    public static transient Object[] removeAll(Object aobj[], int ai[])
    {
        return (Object[])(Object[])removeAll(((Object) (aobj)), clone(ai));
    }

    public static transient short[] removeAll(short aword0[], int ai[])
    {
        return (short[])(short[])removeAll(aword0, clone(ai));
    }

    public static transient boolean[] removeAll(boolean aflag[], int ai[])
    {
        return (boolean[])(boolean[])removeAll(aflag, clone(ai));
    }

    public static byte[] removeElement(byte abyte0[], byte byte0)
    {
        int i = indexOf(abyte0, byte0);
        if(i == -1)
            return clone(abyte0);
        else
            return remove(abyte0, i);
    }

    public static char[] removeElement(char ac[], char c)
    {
        int i = indexOf(ac, c);
        if(i == -1)
            return clone(ac);
        else
            return remove(ac, i);
    }

    public static double[] removeElement(double ad[], double d)
    {
        int i = indexOf(ad, d);
        if(i == -1)
            return clone(ad);
        else
            return remove(ad, i);
    }

    public static float[] removeElement(float af[], float f)
    {
        int i = indexOf(af, f);
        if(i == -1)
            return clone(af);
        else
            return remove(af, i);
    }

    public static int[] removeElement(int ai[], int i)
    {
        int j = indexOf(ai, i);
        if(j == -1)
            return clone(ai);
        else
            return remove(ai, j);
    }

    public static long[] removeElement(long al[], long l)
    {
        int i = indexOf(al, l);
        if(i == -1)
            return clone(al);
        else
            return remove(al, i);
    }

    public static Object[] removeElement(Object aobj[], Object obj)
    {
        int i = indexOf(aobj, obj);
        if(i == -1)
            return clone(aobj);
        else
            return remove(aobj, i);
    }

    public static short[] removeElement(short aword0[], short word0)
    {
        int i = indexOf(aword0, word0);
        if(i == -1)
            return clone(aword0);
        else
            return remove(aword0, i);
    }

    public static boolean[] removeElement(boolean aflag[], boolean flag)
    {
        int i = indexOf(aflag, flag);
        if(i == -1)
            return clone(aflag);
        else
            return remove(aflag, i);
    }

    public static transient byte[] removeElements(byte abyte0[], byte abyte1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(abyte0) || isEmpty(abyte1))
            return clone(abyte0);
        HashMap hashmap = new HashMap(abyte1.length);
        int i = abyte1.length;
        int j = 0;
        while(j < i) 
        {
            Byte byte2 = Byte.valueOf(abyte1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(byte2);
            if(mutableint == null)
                hashmap.put(byte2, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Byte byte1;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        byte1 = (Byte)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(abyte0, byte1.byteValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(abyte0, extractIndices(hashset));
    }

    public static transient char[] removeElements(char ac[], char ac1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(ac) || isEmpty(ac1))
            return clone(ac);
        HashMap hashmap = new HashMap(ac1.length);
        int i = ac1.length;
        int j = 0;
        while(j < i) 
        {
            Character character1 = Character.valueOf(ac1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(character1);
            if(mutableint == null)
                hashmap.put(character1, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Character character;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        character = (Character)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(ac, character.charValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(ac, extractIndices(hashset));
    }

    public static transient double[] removeElements(double ad[], double ad1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(ad) || isEmpty(ad1))
            return clone(ad);
        HashMap hashmap = new HashMap(ad1.length);
        int i = ad1.length;
        int j = 0;
        while(j < i) 
        {
            Double double2 = Double.valueOf(ad1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(double2);
            if(mutableint == null)
                hashmap.put(double2, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Double double1;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        double1 = (Double)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(ad, double1.doubleValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(ad, extractIndices(hashset));
    }

    public static transient float[] removeElements(float af[], float af1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(af) || isEmpty(af1))
            return clone(af);
        HashMap hashmap = new HashMap(af1.length);
        int i = af1.length;
        int j = 0;
        while(j < i) 
        {
            Float float2 = Float.valueOf(af1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(float2);
            if(mutableint == null)
                hashmap.put(float2, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Float float1;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        float1 = (Float)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(af, float1.floatValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(af, extractIndices(hashset));
    }

    public static transient int[] removeElements(int ai[], int ai1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(ai) || isEmpty(ai1))
            return clone(ai);
        HashMap hashmap = new HashMap(ai1.length);
        int i = ai1.length;
        int j = 0;
        while(j < i) 
        {
            Integer integer1 = Integer.valueOf(ai1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(integer1);
            if(mutableint == null)
                hashmap.put(integer1, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Integer integer;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        integer = (Integer)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(ai, integer.intValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(ai, extractIndices(hashset));
    }

    public static transient long[] removeElements(long al[], long al1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(al) || isEmpty(al1))
            return clone(al);
        HashMap hashmap = new HashMap(al1.length);
        int i = al1.length;
        int j = 0;
        while(j < i) 
        {
            Long long2 = Long.valueOf(al1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(long2);
            if(mutableint == null)
                hashmap.put(long2, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Long long1;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        long1 = (Long)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(al, long1.longValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(al, extractIndices(hashset));
    }

    public static transient Object[] removeElements(Object aobj[], Object aobj1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(aobj) || isEmpty(aobj1))
            return clone(aobj);
        HashMap hashmap = new HashMap(aobj1.length);
        int i = aobj1.length;
        int j = 0;
        while(j < i) 
        {
            Object obj1 = aobj1[j];
            MutableInt mutableint = (MutableInt)hashmap.get(obj1);
            if(mutableint == null)
                hashmap.put(obj1, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Object obj;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_213;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        obj = entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(aobj, obj, k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(aobj, extractIndices(hashset));
    }

    public static transient short[] removeElements(short aword0[], short aword1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(aword0) || isEmpty(aword1))
            return clone(aword0);
        HashMap hashmap = new HashMap(aword1.length);
        int i = aword1.length;
        int j = 0;
        while(j < i) 
        {
            Short short2 = Short.valueOf(aword1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(short2);
            if(mutableint == null)
                hashmap.put(short2, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Short short1;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        short1 = (Short)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(aword0, short1.shortValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(aword0, extractIndices(hashset));
    }

    public static transient boolean[] removeElements(boolean aflag[], boolean aflag1[])
    {
        HashSet hashset;
        Iterator iterator;
        if(isEmpty(aflag) || isEmpty(aflag1))
            return clone(aflag);
        HashMap hashmap = new HashMap(aflag1.length);
        int i = aflag1.length;
        int j = 0;
        while(j < i) 
        {
            Boolean boolean2 = Boolean.valueOf(aflag1[j]);
            MutableInt mutableint = (MutableInt)hashmap.get(boolean2);
            if(mutableint == null)
                hashmap.put(boolean2, new MutableInt(1));
            else
                mutableint.increment();
            j++;
        }
        hashset = new HashSet();
        iterator = hashmap.entrySet().iterator();
_L2:
        Boolean boolean1;
        int k;
        int l;
        int i1;
        if(!iterator.hasNext())
            break MISSING_BLOCK_LABEL_222;
        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
        boolean1 = (Boolean)entry.getKey();
        k = 0;
        l = 0;
        i1 = ((MutableInt)entry.getValue()).intValue();
_L4:
        if(l >= i1) goto _L2; else goto _L1
_L1:
        int j1 = indexOf(aflag, boolean1.booleanValue(), k);
        if(j1 < 0) goto _L2; else goto _L3
_L3:
        int k1 = j1 + 1;
        hashset.add(Integer.valueOf(j1));
        l++;
        k = k1;
          goto _L4
        return removeAll(aflag, extractIndices(hashset));
    }

    public static void reverse(byte abyte0[])
    {
        if(abyte0 != null)
        {
            int i = 0;
            int j = -1 + abyte0.length;
            while(j > i) 
            {
                byte byte0 = abyte0[j];
                abyte0[j] = abyte0[i];
                abyte0[i] = byte0;
                j--;
                i++;
            }
        }
    }

    public static void reverse(char ac[])
    {
        if(ac != null)
        {
            int i = 0;
            int j = -1 + ac.length;
            while(j > i) 
            {
                char c = ac[j];
                ac[j] = ac[i];
                ac[i] = c;
                j--;
                i++;
            }
        }
    }

    public static void reverse(double ad[])
    {
        if(ad != null)
        {
            int i = 0;
            int j = -1 + ad.length;
            while(j > i) 
            {
                double d = ad[j];
                ad[j] = ad[i];
                ad[i] = d;
                j--;
                i++;
            }
        }
    }

    public static void reverse(float af[])
    {
        if(af != null)
        {
            int i = 0;
            int j = -1 + af.length;
            while(j > i) 
            {
                float f = af[j];
                af[j] = af[i];
                af[i] = f;
                j--;
                i++;
            }
        }
    }

    public static void reverse(int ai[])
    {
        if(ai != null)
        {
            int i = 0;
            int j = -1 + ai.length;
            while(j > i) 
            {
                int k = ai[j];
                ai[j] = ai[i];
                ai[i] = k;
                j--;
                i++;
            }
        }
    }

    public static void reverse(long al[])
    {
        if(al != null)
        {
            int i = 0;
            int j = -1 + al.length;
            while(j > i) 
            {
                long l = al[j];
                al[j] = al[i];
                al[i] = l;
                j--;
                i++;
            }
        }
    }

    public static void reverse(Object aobj[])
    {
        if(aobj != null)
        {
            int i = 0;
            int j = -1 + aobj.length;
            while(j > i) 
            {
                Object obj = aobj[j];
                aobj[j] = aobj[i];
                aobj[i] = obj;
                j--;
                i++;
            }
        }
    }

    public static void reverse(short aword0[])
    {
        if(aword0 != null)
        {
            int i = 0;
            int j = -1 + aword0.length;
            while(j > i) 
            {
                short word0 = aword0[j];
                aword0[j] = aword0[i];
                aword0[i] = word0;
                j--;
                i++;
            }
        }
    }

    public static void reverse(boolean aflag[])
    {
        if(aflag != null)
        {
            int i = 0;
            int j = -1 + aflag.length;
            while(j > i) 
            {
                boolean flag = aflag[j];
                aflag[j] = aflag[i];
                aflag[i] = flag;
                j--;
                i++;
            }
        }
    }

    public static byte[] subarray(byte abyte0[], int i, int j)
    {
        if(abyte0 == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > abyte0.length)
            j = abyte0.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_BYTE_ARRAY;
        } else
        {
            byte abyte1[] = new byte[k];
            System.arraycopy(abyte0, i, abyte1, 0, k);
            return abyte1;
        }
    }

    public static char[] subarray(char ac[], int i, int j)
    {
        if(ac == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > ac.length)
            j = ac.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_CHAR_ARRAY;
        } else
        {
            char ac1[] = new char[k];
            System.arraycopy(ac, i, ac1, 0, k);
            return ac1;
        }
    }

    public static double[] subarray(double ad[], int i, int j)
    {
        if(ad == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > ad.length)
            j = ad.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_DOUBLE_ARRAY;
        } else
        {
            double ad1[] = new double[k];
            System.arraycopy(ad, i, ad1, 0, k);
            return ad1;
        }
    }

    public static float[] subarray(float af[], int i, int j)
    {
        if(af == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > af.length)
            j = af.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_FLOAT_ARRAY;
        } else
        {
            float af1[] = new float[k];
            System.arraycopy(af, i, af1, 0, k);
            return af1;
        }
    }

    public static int[] subarray(int ai[], int i, int j)
    {
        if(ai == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > ai.length)
            j = ai.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_INT_ARRAY;
        } else
        {
            int ai1[] = new int[k];
            System.arraycopy(ai, i, ai1, 0, k);
            return ai1;
        }
    }

    public static long[] subarray(long al[], int i, int j)
    {
        if(al == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > al.length)
            j = al.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_LONG_ARRAY;
        } else
        {
            long al1[] = new long[k];
            System.arraycopy(al, i, al1, 0, k);
            return al1;
        }
    }

    public static Object[] subarray(Object aobj[], int i, int j)
    {
        if(aobj == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > aobj.length)
            j = aobj.length;
        int k = j - i;
        Class class1 = ((Object) (aobj)).getClass().getComponentType();
        if(k <= 0)
        {
            return (Object[])(Object[])Array.newInstance(class1, 0);
        } else
        {
            Object aobj1[] = (Object[])(Object[])Array.newInstance(class1, k);
            System.arraycopy(((Object) (aobj)), i, ((Object) (aobj1)), 0, k);
            return aobj1;
        }
    }

    public static short[] subarray(short aword0[], int i, int j)
    {
        if(aword0 == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > aword0.length)
            j = aword0.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_SHORT_ARRAY;
        } else
        {
            short aword1[] = new short[k];
            System.arraycopy(aword0, i, aword1, 0, k);
            return aword1;
        }
    }

    public static boolean[] subarray(boolean aflag[], int i, int j)
    {
        if(aflag == null)
            return null;
        if(i < 0)
            i = 0;
        if(j > aflag.length)
            j = aflag.length;
        int k = j - i;
        if(k <= 0)
        {
            return EMPTY_BOOLEAN_ARRAY;
        } else
        {
            boolean aflag1[] = new boolean[k];
            System.arraycopy(aflag, i, aflag1, 0, k);
            return aflag1;
        }
    }

    public static transient Object[] toArray(Object aobj[])
    {
        return aobj;
    }

    public static Map toMap(Object aobj[])
    {
        Object obj;
        if(aobj == null)
        {
            obj = null;
        } else
        {
            obj = new HashMap((int)(1.5D * (double)aobj.length));
            int i = 0;
            while(i < aobj.length) 
            {
                Object obj1 = aobj[i];
                if(obj1 instanceof java.util.Map.Entry)
                {
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj1;
                    ((Map) (obj)).put(entry.getKey(), entry.getValue());
                } else
                if(obj1 instanceof Object[])
                {
                    Object aobj1[] = (Object[])(Object[])obj1;
                    if(aobj1.length < 2)
                        throw new IllegalArgumentException((new StringBuilder()).append("Array element ").append(i).append(", '").append(obj1).append("', has a length less than 2").toString());
                    ((Map) (obj)).put(aobj1[0], aobj1[1]);
                } else
                {
                    throw new IllegalArgumentException((new StringBuilder()).append("Array element ").append(i).append(", '").append(obj1).append("', is neither of type Map.Entry nor an Array").toString());
                }
                i++;
            }
        }
        return ((Map) (obj));
    }

    public static Boolean[] toObject(boolean aflag[])
    {
        Boolean aboolean[];
        if(aflag == null)
        {
            aboolean = null;
        } else
        {
            if(aflag.length == 0)
                return EMPTY_BOOLEAN_OBJECT_ARRAY;
            aboolean = new Boolean[aflag.length];
            int i = 0;
            while(i < aflag.length) 
            {
                Boolean boolean1;
                if(aflag[i])
                    boolean1 = Boolean.TRUE;
                else
                    boolean1 = Boolean.FALSE;
                aboolean[i] = boolean1;
                i++;
            }
        }
        return aboolean;
    }

    public static Byte[] toObject(byte abyte0[])
    {
        Byte abyte[];
        if(abyte0 == null)
        {
            abyte = null;
        } else
        {
            if(abyte0.length == 0)
                return EMPTY_BYTE_OBJECT_ARRAY;
            abyte = new Byte[abyte0.length];
            int i = 0;
            while(i < abyte0.length) 
            {
                abyte[i] = Byte.valueOf(abyte0[i]);
                i++;
            }
        }
        return abyte;
    }

    public static Character[] toObject(char ac[])
    {
        Character acharacter[];
        if(ac == null)
        {
            acharacter = null;
        } else
        {
            if(ac.length == 0)
                return EMPTY_CHARACTER_OBJECT_ARRAY;
            acharacter = new Character[ac.length];
            int i = 0;
            while(i < ac.length) 
            {
                acharacter[i] = Character.valueOf(ac[i]);
                i++;
            }
        }
        return acharacter;
    }

    public static Double[] toObject(double ad[])
    {
        Double adouble[];
        if(ad == null)
        {
            adouble = null;
        } else
        {
            if(ad.length == 0)
                return EMPTY_DOUBLE_OBJECT_ARRAY;
            adouble = new Double[ad.length];
            int i = 0;
            while(i < ad.length) 
            {
                adouble[i] = Double.valueOf(ad[i]);
                i++;
            }
        }
        return adouble;
    }

    public static Float[] toObject(float af[])
    {
        Float afloat[];
        if(af == null)
        {
            afloat = null;
        } else
        {
            if(af.length == 0)
                return EMPTY_FLOAT_OBJECT_ARRAY;
            afloat = new Float[af.length];
            int i = 0;
            while(i < af.length) 
            {
                afloat[i] = Float.valueOf(af[i]);
                i++;
            }
        }
        return afloat;
    }

    public static Integer[] toObject(int ai[])
    {
        Integer ainteger[];
        if(ai == null)
        {
            ainteger = null;
        } else
        {
            if(ai.length == 0)
                return EMPTY_INTEGER_OBJECT_ARRAY;
            ainteger = new Integer[ai.length];
            int i = 0;
            while(i < ai.length) 
            {
                ainteger[i] = Integer.valueOf(ai[i]);
                i++;
            }
        }
        return ainteger;
    }

    public static Long[] toObject(long al[])
    {
        Long along[];
        if(al == null)
        {
            along = null;
        } else
        {
            if(al.length == 0)
                return EMPTY_LONG_OBJECT_ARRAY;
            along = new Long[al.length];
            int i = 0;
            while(i < al.length) 
            {
                along[i] = Long.valueOf(al[i]);
                i++;
            }
        }
        return along;
    }

    public static Short[] toObject(short aword0[])
    {
        Short ashort[];
        if(aword0 == null)
        {
            ashort = null;
        } else
        {
            if(aword0.length == 0)
                return EMPTY_SHORT_OBJECT_ARRAY;
            ashort = new Short[aword0.length];
            int i = 0;
            while(i < aword0.length) 
            {
                ashort[i] = Short.valueOf(aword0[i]);
                i++;
            }
        }
        return ashort;
    }

    public static byte[] toPrimitive(Byte abyte[])
    {
        byte abyte0[];
        if(abyte == null)
        {
            abyte0 = null;
        } else
        {
            if(abyte.length == 0)
                return EMPTY_BYTE_ARRAY;
            abyte0 = new byte[abyte.length];
            int i = 0;
            while(i < abyte.length) 
            {
                abyte0[i] = abyte[i].byteValue();
                i++;
            }
        }
        return abyte0;
    }

    public static byte[] toPrimitive(Byte abyte[], byte byte0)
    {
        byte abyte0[];
        if(abyte == null)
        {
            abyte0 = null;
        } else
        {
            if(abyte.length == 0)
                return EMPTY_BYTE_ARRAY;
            abyte0 = new byte[abyte.length];
            int i = 0;
            while(i < abyte.length) 
            {
                Byte byte1 = abyte[i];
                byte byte2;
                if(byte1 == null)
                    byte2 = byte0;
                else
                    byte2 = byte1.byteValue();
                abyte0[i] = byte2;
                i++;
            }
        }
        return abyte0;
    }

    public static char[] toPrimitive(Character acharacter[])
    {
        char ac[];
        if(acharacter == null)
        {
            ac = null;
        } else
        {
            if(acharacter.length == 0)
                return EMPTY_CHAR_ARRAY;
            ac = new char[acharacter.length];
            int i = 0;
            while(i < acharacter.length) 
            {
                ac[i] = acharacter[i].charValue();
                i++;
            }
        }
        return ac;
    }

    public static char[] toPrimitive(Character acharacter[], char c)
    {
        char ac[];
        if(acharacter == null)
        {
            ac = null;
        } else
        {
            if(acharacter.length == 0)
                return EMPTY_CHAR_ARRAY;
            ac = new char[acharacter.length];
            int i = 0;
            while(i < acharacter.length) 
            {
                Character character = acharacter[i];
                char c1;
                if(character == null)
                    c1 = c;
                else
                    c1 = character.charValue();
                ac[i] = c1;
                i++;
            }
        }
        return ac;
    }

    public static double[] toPrimitive(Double adouble[])
    {
        double ad[];
        if(adouble == null)
        {
            ad = null;
        } else
        {
            if(adouble.length == 0)
                return EMPTY_DOUBLE_ARRAY;
            ad = new double[adouble.length];
            int i = 0;
            while(i < adouble.length) 
            {
                ad[i] = adouble[i].doubleValue();
                i++;
            }
        }
        return ad;
    }

    public static double[] toPrimitive(Double adouble[], double d)
    {
        double ad[];
        if(adouble == null)
        {
            ad = null;
        } else
        {
            if(adouble.length == 0)
                return EMPTY_DOUBLE_ARRAY;
            ad = new double[adouble.length];
            int i = 0;
            while(i < adouble.length) 
            {
                Double double1 = adouble[i];
                double d1;
                if(double1 == null)
                    d1 = d;
                else
                    d1 = double1.doubleValue();
                ad[i] = d1;
                i++;
            }
        }
        return ad;
    }

    public static float[] toPrimitive(Float afloat[])
    {
        float af[];
        if(afloat == null)
        {
            af = null;
        } else
        {
            if(afloat.length == 0)
                return EMPTY_FLOAT_ARRAY;
            af = new float[afloat.length];
            int i = 0;
            while(i < afloat.length) 
            {
                af[i] = afloat[i].floatValue();
                i++;
            }
        }
        return af;
    }

    public static float[] toPrimitive(Float afloat[], float f)
    {
        float af[];
        if(afloat == null)
        {
            af = null;
        } else
        {
            if(afloat.length == 0)
                return EMPTY_FLOAT_ARRAY;
            af = new float[afloat.length];
            int i = 0;
            while(i < afloat.length) 
            {
                Float float1 = afloat[i];
                float f1;
                if(float1 == null)
                    f1 = f;
                else
                    f1 = float1.floatValue();
                af[i] = f1;
                i++;
            }
        }
        return af;
    }

    public static int[] toPrimitive(Integer ainteger[])
    {
        int ai[];
        if(ainteger == null)
        {
            ai = null;
        } else
        {
            if(ainteger.length == 0)
                return EMPTY_INT_ARRAY;
            ai = new int[ainteger.length];
            int i = 0;
            while(i < ainteger.length) 
            {
                ai[i] = ainteger[i].intValue();
                i++;
            }
        }
        return ai;
    }

    public static int[] toPrimitive(Integer ainteger[], int i)
    {
        int ai[];
        if(ainteger == null)
        {
            ai = null;
        } else
        {
            if(ainteger.length == 0)
                return EMPTY_INT_ARRAY;
            ai = new int[ainteger.length];
            int j = 0;
            while(j < ainteger.length) 
            {
                Integer integer = ainteger[j];
                int k;
                if(integer == null)
                    k = i;
                else
                    k = integer.intValue();
                ai[j] = k;
                j++;
            }
        }
        return ai;
    }

    public static long[] toPrimitive(Long along[])
    {
        long al[];
        if(along == null)
        {
            al = null;
        } else
        {
            if(along.length == 0)
                return EMPTY_LONG_ARRAY;
            al = new long[along.length];
            int i = 0;
            while(i < along.length) 
            {
                al[i] = along[i].longValue();
                i++;
            }
        }
        return al;
    }

    public static long[] toPrimitive(Long along[], long l)
    {
        long al[];
        if(along == null)
        {
            al = null;
        } else
        {
            if(along.length == 0)
                return EMPTY_LONG_ARRAY;
            al = new long[along.length];
            int i = 0;
            while(i < along.length) 
            {
                Long long1 = along[i];
                long l1;
                if(long1 == null)
                    l1 = l;
                else
                    l1 = long1.longValue();
                al[i] = l1;
                i++;
            }
        }
        return al;
    }

    public static short[] toPrimitive(Short ashort[])
    {
        short aword0[];
        if(ashort == null)
        {
            aword0 = null;
        } else
        {
            if(ashort.length == 0)
                return EMPTY_SHORT_ARRAY;
            aword0 = new short[ashort.length];
            int i = 0;
            while(i < ashort.length) 
            {
                aword0[i] = ashort[i].shortValue();
                i++;
            }
        }
        return aword0;
    }

    public static short[] toPrimitive(Short ashort[], short word0)
    {
        short aword0[];
        if(ashort == null)
        {
            aword0 = null;
        } else
        {
            if(ashort.length == 0)
                return EMPTY_SHORT_ARRAY;
            aword0 = new short[ashort.length];
            int i = 0;
            while(i < ashort.length) 
            {
                Short short1 = ashort[i];
                short word1;
                if(short1 == null)
                    word1 = word0;
                else
                    word1 = short1.shortValue();
                aword0[i] = word1;
                i++;
            }
        }
        return aword0;
    }

    public static boolean[] toPrimitive(Boolean aboolean[])
    {
        boolean aflag[];
        if(aboolean == null)
        {
            aflag = null;
        } else
        {
            if(aboolean.length == 0)
                return EMPTY_BOOLEAN_ARRAY;
            aflag = new boolean[aboolean.length];
            int i = 0;
            while(i < aboolean.length) 
            {
                aflag[i] = aboolean[i].booleanValue();
                i++;
            }
        }
        return aflag;
    }

    public static boolean[] toPrimitive(Boolean aboolean[], boolean flag)
    {
        boolean aflag[];
        if(aboolean == null)
        {
            aflag = null;
        } else
        {
            if(aboolean.length == 0)
                return EMPTY_BOOLEAN_ARRAY;
            aflag = new boolean[aboolean.length];
            int i = 0;
            while(i < aboolean.length) 
            {
                Boolean boolean1 = aboolean[i];
                boolean flag1;
                if(boolean1 == null)
                    flag1 = flag;
                else
                    flag1 = boolean1.booleanValue();
                aflag[i] = flag1;
                i++;
            }
        }
        return aflag;
    }

    public static String toString(Object obj)
    {
        return toString(obj, "{}");
    }

    public static String toString(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return (new ToStringBuilder(obj, ToStringStyle.SIMPLE_STYLE)).append(obj).toString();
    }

    public static final boolean EMPTY_BOOLEAN_ARRAY[] = new boolean[0];
    public static final Boolean EMPTY_BOOLEAN_OBJECT_ARRAY[] = new Boolean[0];
    public static final byte EMPTY_BYTE_ARRAY[] = new byte[0];
    public static final Byte EMPTY_BYTE_OBJECT_ARRAY[] = new Byte[0];
    public static final Character EMPTY_CHARACTER_OBJECT_ARRAY[] = new Character[0];
    public static final char EMPTY_CHAR_ARRAY[] = new char[0];
    public static final Class EMPTY_CLASS_ARRAY[] = new Class[0];
    public static final double EMPTY_DOUBLE_ARRAY[] = new double[0];
    public static final Double EMPTY_DOUBLE_OBJECT_ARRAY[] = new Double[0];
    public static final float EMPTY_FLOAT_ARRAY[] = new float[0];
    public static final Float EMPTY_FLOAT_OBJECT_ARRAY[] = new Float[0];
    public static final Integer EMPTY_INTEGER_OBJECT_ARRAY[] = new Integer[0];
    public static final int EMPTY_INT_ARRAY[] = new int[0];
    public static final long EMPTY_LONG_ARRAY[] = new long[0];
    public static final Long EMPTY_LONG_OBJECT_ARRAY[] = new Long[0];
    public static final Object EMPTY_OBJECT_ARRAY[] = new Object[0];
    public static final short EMPTY_SHORT_ARRAY[] = new short[0];
    public static final Short EMPTY_SHORT_OBJECT_ARRAY[] = new Short[0];
    public static final String EMPTY_STRING_ARRAY[] = new String[0];
    public static final int INDEX_NOT_FOUND = -1;

}
