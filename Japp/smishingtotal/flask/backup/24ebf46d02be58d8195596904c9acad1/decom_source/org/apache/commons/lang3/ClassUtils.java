// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

// Referenced classes of package org.apache.commons.lang3:
//            StringUtils, ArrayUtils, JavaVersion, SystemUtils

public class ClassUtils
{

    public ClassUtils()
    {
    }

    private static void addAbbreviation(String s, String s1)
    {
        abbreviationMap.put(s, s1);
        reverseAbbreviationMap.put(s1, s);
    }

    public static List convertClassNamesToClasses(List list)
    {
        Object obj;
        if(list == null)
        {
            obj = null;
        } else
        {
            obj = new ArrayList(list.size());
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) 
            {
                String s = (String)iterator.next();
                try
                {
                    ((List) (obj)).add(Class.forName(s));
                }
                catch(Exception exception)
                {
                    ((List) (obj)).add(null);
                }
            }
        }
        return ((List) (obj));
    }

    public static List convertClassesToClassNames(List list)
    {
        Object obj;
        if(list == null)
        {
            obj = null;
        } else
        {
            obj = new ArrayList(list.size());
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) 
            {
                Class class1 = (Class)iterator.next();
                if(class1 == null)
                    ((List) (obj)).add(null);
                else
                    ((List) (obj)).add(class1.getName());
            }
        }
        return ((List) (obj));
    }

    public static List getAllInterfaces(Class class1)
    {
        if(class1 == null)
        {
            return null;
        } else
        {
            LinkedHashSet linkedhashset = new LinkedHashSet();
            getAllInterfaces(class1, ((HashSet) (linkedhashset)));
            return new ArrayList(linkedhashset);
        }
    }

    private static void getAllInterfaces(Class class1, HashSet hashset)
    {
        for(; class1 != null; class1 = class1.getSuperclass())
        {
            Class aclass[] = class1.getInterfaces();
            int i = aclass.length;
            for(int j = 0; j < i; j++)
            {
                Class class2 = aclass[j];
                if(hashset.add(class2))
                    getAllInterfaces(class2, hashset);
            }

        }

    }

    public static List getAllSuperclasses(Class class1)
    {
        Object obj;
        if(class1 == null)
        {
            obj = null;
        } else
        {
            obj = new ArrayList();
            Class class2 = class1.getSuperclass();
            while(class2 != null) 
            {
                ((List) (obj)).add(class2);
                class2 = class2.getSuperclass();
            }
        }
        return ((List) (obj));
    }

    private static String getCanonicalName(String s)
    {
        String s1 = StringUtils.deleteWhitespace(s);
        if(s1 != null) goto _L2; else goto _L1
_L1:
        s1 = null;
_L4:
        return s1;
_L2:
        int i;
        i = 0;
        for(; s1.startsWith("["); s1 = s1.substring(1))
            i++;

        if(i < 1) goto _L4; else goto _L3
_L3:
        if(!s1.startsWith("L")) goto _L6; else goto _L5
_L5:
        StringBuilder stringbuilder;
        int j;
        int k;
        if(s1.endsWith(";"))
            k = -1 + s1.length();
        else
            k = s1.length();
        s1 = s1.substring(1, k);
_L8:
        stringbuilder = new StringBuilder(s1);
        for(j = 0; j < i; j++)
            stringbuilder.append("[]");

        break; /* Loop/switch isn't completed */
_L6:
        if(s1.length() > 0)
            s1 = (String)reverseAbbreviationMap.get(s1.substring(0, 1));
        if(true) goto _L8; else goto _L7
_L7:
        return stringbuilder.toString();
    }

    public static Class getClass(ClassLoader classloader, String s)
        throws ClassNotFoundException
    {
        return getClass(classloader, s, true);
    }

    public static Class getClass(ClassLoader classloader, String s, boolean flag)
        throws ClassNotFoundException
    {
        Class class2;
        if(abbreviationMap.containsKey(s))
            return Class.forName((new StringBuilder()).append("[").append((String)abbreviationMap.get(s)).toString(), flag, classloader).getComponentType();
        class2 = Class.forName(toCanonicalName(s), flag, classloader);
        return class2;
        ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        int i;
        i = s.lastIndexOf('.');
        if(i == -1)
            break MISSING_BLOCK_LABEL_128;
        Class class1 = getClass(classloader, (new StringBuilder()).append(s.substring(0, i)).append('$').append(s.substring(i + 1)).toString(), flag);
        return class1;
        ClassNotFoundException classnotfoundexception1;
        classnotfoundexception1;
        throw classnotfoundexception;
    }

    public static Class getClass(String s)
        throws ClassNotFoundException
    {
        return getClass(s, true);
    }

    public static Class getClass(String s, boolean flag)
        throws ClassNotFoundException
    {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        ClassLoader classloader1;
        if(classloader == null)
            classloader1 = org/apache/commons/lang3/ClassUtils.getClassLoader();
        else
            classloader1 = classloader;
        return getClass(classloader1, s, flag);
    }

    public static String getPackageCanonicalName(Class class1)
    {
        if(class1 == null)
            return "";
        else
            return getPackageCanonicalName(class1.getName());
    }

    public static String getPackageCanonicalName(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return getPackageCanonicalName(obj.getClass().getName());
    }

    public static String getPackageCanonicalName(String s)
    {
        return getPackageName(getCanonicalName(s));
    }

    public static String getPackageName(Class class1)
    {
        if(class1 == null)
            return "";
        else
            return getPackageName(class1.getName());
    }

    public static String getPackageName(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return getPackageName(obj.getClass());
    }

    public static String getPackageName(String s)
    {
        if(s == null || s.length() == 0)
            return "";
        for(; s.charAt(0) == '['; s = s.substring(1));
        if(s.charAt(0) == 'L' && s.charAt(-1 + s.length()) == ';')
            s = s.substring(1);
        int i = s.lastIndexOf('.');
        if(i == -1)
            return "";
        else
            return s.substring(0, i);
    }

    public static transient Method getPublicMethod(Class class1, String s, Class aclass[])
        throws SecurityException, NoSuchMethodException
    {
        Iterator iterator;
        Method method = class1.getMethod(s, aclass);
        if(Modifier.isPublic(method.getDeclaringClass().getModifiers()))
            return method;
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(getAllInterfaces(class1));
        arraylist.addAll(getAllSuperclasses(class1));
        iterator = arraylist.iterator();
_L2:
        Class class2;
        if(!iterator.hasNext())
            break; /* Loop/switch isn't completed */
        class2 = (Class)iterator.next();
        if(!Modifier.isPublic(class2.getModifiers()))
            continue; /* Loop/switch isn't completed */
        Method method1 = class2.getMethod(s, aclass);
        if(Modifier.isPublic(method1.getDeclaringClass().getModifiers()))
            return method1;
        continue; /* Loop/switch isn't completed */
        NoSuchMethodException nosuchmethodexception;
        nosuchmethodexception;
        if(true) goto _L2; else goto _L1
_L1:
        throw new NoSuchMethodException((new StringBuilder()).append("Can't find a public method for ").append(s).append(" ").append(ArrayUtils.toString(aclass)).toString());
    }

    public static String getShortCanonicalName(Class class1)
    {
        if(class1 == null)
            return "";
        else
            return getShortCanonicalName(class1.getName());
    }

    public static String getShortCanonicalName(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return getShortCanonicalName(obj.getClass().getName());
    }

    public static String getShortCanonicalName(String s)
    {
        return getShortClassName(getCanonicalName(s));
    }

    public static String getShortClassName(Class class1)
    {
        if(class1 == null)
            return "";
        else
            return getShortClassName(class1.getName());
    }

    public static String getShortClassName(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return getShortClassName(obj.getClass());
    }

    public static String getShortClassName(String s)
    {
        if(s == null)
            return "";
        if(s.length() == 0)
            return "";
        StringBuilder stringbuilder = new StringBuilder();
        if(s.startsWith("["))
        {
            for(; s.charAt(0) == '['; stringbuilder.append("[]"))
                s = s.substring(1);

            if(s.charAt(0) == 'L' && s.charAt(-1 + s.length()) == ';')
                s = s.substring(1, -1 + s.length());
        }
        if(reverseAbbreviationMap.containsKey(s))
            s = (String)reverseAbbreviationMap.get(s);
        int i = s.lastIndexOf('.');
        int j = 0;
        int k;
        String s1;
        if(i != -1)
            j = i + 1;
        k = s.indexOf('$', j);
        s1 = s.substring(i + 1);
        if(k != -1)
            s1 = s1.replace('$', '.');
        return (new StringBuilder()).append(s1).append(stringbuilder).toString();
    }

    public static String getSimpleName(Class class1)
    {
        if(class1 == null)
            return "";
        else
            return class1.getSimpleName();
    }

    public static String getSimpleName(Object obj, String s)
    {
        if(obj == null)
            return s;
        else
            return getSimpleName(obj.getClass());
    }

    public static boolean isAssignable(Class class1, Class class2)
    {
        return isAssignable(class1, class2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }

    public static boolean isAssignable(Class class1, Class class2, boolean flag)
    {
        boolean flag1 = true;
        if(class2 != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        if(class1 == null)
        {
            if(class2.isPrimitive())
                flag1 = false;
            return flag1;
        }
        if(!flag)
            break; /* Loop/switch isn't completed */
        if(!class1.isPrimitive() || class2.isPrimitive())
            break; /* Loop/switch isn't completed */
        class1 = primitiveToWrapper(class1);
        if(class1 == null) goto _L1; else goto _L3
_L3:
        if(!class2.isPrimitive() || class1.isPrimitive())
            break; /* Loop/switch isn't completed */
        class1 = wrapperToPrimitive(class1);
        if(class1 == null) goto _L1; else goto _L4
_L4:
        if(class1.equals(class2))
            return flag1;
        if(!class1.isPrimitive())
            break MISSING_BLOCK_LABEL_378;
        if(class2.isPrimitive())
            if(Integer.TYPE.equals(class1))
            {
                if(Long.TYPE.equals(class2) || Float.TYPE.equals(class2) || Double.TYPE.equals(class2))
                    return flag1;
            } else
            {
                if(!Long.TYPE.equals(class1))
                    continue; /* Loop/switch isn't completed */
                if(Float.TYPE.equals(class2) || Double.TYPE.equals(class2))
                    return flag1;
            }
        continue; /* Loop/switch isn't completed */
        if(Boolean.TYPE.equals(class1) || Double.TYPE.equals(class1)) goto _L1; else goto _L5
_L5:
        if(Float.TYPE.equals(class1))
            return Double.TYPE.equals(class2);
        if(!Character.TYPE.equals(class1))
            break; /* Loop/switch isn't completed */
        if(Integer.TYPE.equals(class2) || Long.TYPE.equals(class2) || Float.TYPE.equals(class2) || Double.TYPE.equals(class2))
            return flag1;
        if(true) goto _L1; else goto _L6
_L6:
        if(!Short.TYPE.equals(class1))
            continue; /* Loop/switch isn't completed */
        if(!Integer.TYPE.equals(class2) && !Long.TYPE.equals(class2) && !Float.TYPE.equals(class2) && !Double.TYPE.equals(class2)) goto _L1; else goto _L7
_L7:
        return flag1;
        if(!Byte.TYPE.equals(class1) || !Short.TYPE.equals(class2) && !Integer.TYPE.equals(class2) && !Long.TYPE.equals(class2) && !Float.TYPE.equals(class2) && !Double.TYPE.equals(class2)) goto _L1; else goto _L8
_L8:
        return flag1;
        return class2.isAssignableFrom(class1);
    }

    public static transient boolean isAssignable(Class aclass[], Class aclass1[])
    {
        return isAssignable(aclass, aclass1, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
    }

    public static boolean isAssignable(Class aclass[], Class aclass1[], boolean flag)
    {
        if(ArrayUtils.isSameLength(aclass, aclass1)) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        if(aclass == null)
            aclass = ArrayUtils.EMPTY_CLASS_ARRAY;
        if(aclass1 == null)
            aclass1 = ArrayUtils.EMPTY_CLASS_ARRAY;
        int i = 0;
label0:
        do
        {
label1:
            {
                if(i >= aclass.length)
                    break label1;
                if(!isAssignable(aclass[i], aclass1[i], flag))
                    break label0;
                i++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isInnerClass(Class class1)
    {
        return class1 != null && class1.getEnclosingClass() != null;
    }

    public static boolean isPrimitiveOrWrapper(Class class1)
    {
        while(class1 == null || !class1.isPrimitive() && !isPrimitiveWrapper(class1)) 
            return false;
        return true;
    }

    public static boolean isPrimitiveWrapper(Class class1)
    {
        return wrapperPrimitiveMap.containsKey(class1);
    }

    public static Class primitiveToWrapper(Class class1)
    {
        Class class2 = class1;
        if(class1 != null && class1.isPrimitive())
            class2 = (Class)primitiveWrapperMap.get(class1);
        return class2;
    }

    public static transient Class[] primitivesToWrappers(Class aclass[])
    {
        if(aclass == null)
            aclass = null;
        else
        if(aclass.length != 0)
        {
            Class aclass1[] = new Class[aclass.length];
            for(int i = 0; i < aclass.length; i++)
                aclass1[i] = primitiveToWrapper(aclass[i]);

            return aclass1;
        }
        return aclass;
    }

    private static String toCanonicalName(String s)
    {
        String s1 = StringUtils.deleteWhitespace(s);
        if(s1 == null)
            throw new NullPointerException("className must not be null.");
        if(s1.endsWith("[]"))
        {
            StringBuilder stringbuilder = new StringBuilder();
            for(; s1.endsWith("[]"); stringbuilder.append("["))
                s1 = s1.substring(0, -2 + s1.length());

            String s2 = (String)abbreviationMap.get(s1);
            if(s2 != null)
                stringbuilder.append(s2);
            else
                stringbuilder.append("L").append(s1).append(";");
            s1 = stringbuilder.toString();
        }
        return s1;
    }

    public static transient Class[] toClass(Object aobj[])
    {
        if(aobj == null)
            return null;
        if(aobj.length == 0)
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        Class aclass[] = new Class[aobj.length];
        int i = 0;
        while(i < aobj.length) 
        {
            Class class1;
            if(aobj[i] == null)
                class1 = null;
            else
                class1 = aobj[i].getClass();
            aclass[i] = class1;
            i++;
        }
        return aclass;
    }

    public static Class wrapperToPrimitive(Class class1)
    {
        return (Class)wrapperPrimitiveMap.get(class1);
    }

    public static transient Class[] wrappersToPrimitives(Class aclass[])
    {
        if(aclass == null)
            aclass = null;
        else
        if(aclass.length != 0)
        {
            Class aclass1[] = new Class[aclass.length];
            for(int i = 0; i < aclass.length; i++)
                aclass1[i] = wrapperToPrimitive(aclass[i]);

            return aclass1;
        }
        return aclass;
    }

    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
    public static final char INNER_CLASS_SEPARATOR_CHAR = 36;
    public static final String PACKAGE_SEPARATOR = String.valueOf('.');
    public static final char PACKAGE_SEPARATOR_CHAR = 46;
    private static final Map abbreviationMap = new HashMap();
    private static final Map primitiveWrapperMap;
    private static final Map reverseAbbreviationMap = new HashMap();
    private static final Map wrapperPrimitiveMap;

    static 
    {
        primitiveWrapperMap = new HashMap();
        primitiveWrapperMap.put(Boolean.TYPE, java/lang/Boolean);
        primitiveWrapperMap.put(Byte.TYPE, java/lang/Byte);
        primitiveWrapperMap.put(Character.TYPE, java/lang/Character);
        primitiveWrapperMap.put(Short.TYPE, java/lang/Short);
        primitiveWrapperMap.put(Integer.TYPE, java/lang/Integer);
        primitiveWrapperMap.put(Long.TYPE, java/lang/Long);
        primitiveWrapperMap.put(Double.TYPE, java/lang/Double);
        primitiveWrapperMap.put(Float.TYPE, java/lang/Float);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        wrapperPrimitiveMap = new HashMap();
        Iterator iterator = primitiveWrapperMap.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Class class1 = (Class)iterator.next();
            Class class2 = (Class)primitiveWrapperMap.get(class1);
            if(!class1.equals(class2))
                wrapperPrimitiveMap.put(class2, class1);
        } while(true);
        addAbbreviation("int", "I");
        addAbbreviation("boolean", "Z");
        addAbbreviation("float", "F");
        addAbbreviation("long", "J");
        addAbbreviation("short", "S");
        addAbbreviation("byte", "B");
        addAbbreviation("double", "D");
        addAbbreviation("char", "C");
    }
}
