// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.reflect;

import java.lang.reflect.*;
import java.util.*;
import org.apache.commons.lang3.ClassUtils;

public class TypeUtils
{

    public TypeUtils()
    {
    }

    public static Map determineTypeArguments(Class class1, ParameterizedType parameterizedtype)
    {
        Class class2 = getRawType(parameterizedtype);
        if(!isAssignable(class1, class2))
            return null;
        if(class1.equals(class2))
            return getTypeArguments(parameterizedtype, class2, null);
        Type type = getClosestParentType(class1, class2);
        if(type instanceof Class)
        {
            return determineTypeArguments((Class)type, parameterizedtype);
        } else
        {
            ParameterizedType parameterizedtype1 = (ParameterizedType)type;
            Map map = determineTypeArguments(getRawType(parameterizedtype1), parameterizedtype);
            mapTypeVariablesToArguments(class1, parameterizedtype1, map);
            return map;
        }
    }

    public static Type getArrayComponentType(Type type)
    {
        Class class1;
        if(type instanceof Class)
        {
            Class class2 = (Class)type;
            boolean flag1 = class2.isArray();
            class1 = null;
            if(flag1)
                class1 = class2.getComponentType();
        } else
        {
            boolean flag = type instanceof GenericArrayType;
            class1 = null;
            if(flag)
                return ((GenericArrayType)type).getGenericComponentType();
        }
        return class1;
    }

    private static Type getClosestParentType(Class class1, Class class2)
    {
        if(class2.isInterface())
        {
            Type atype[] = class1.getGenericInterfaces();
            Type type = null;
            int i = atype.length;
            int j = 0;
            while(j < i) 
            {
                Type type1 = atype[j];
                Class class3;
                if(type1 instanceof ParameterizedType)
                    class3 = getRawType((ParameterizedType)type1);
                else
                if(type1 instanceof Class)
                    class3 = (Class)type1;
                else
                    throw new IllegalStateException((new StringBuilder()).append("Unexpected generic interface type found: ").append(type1).toString());
                if(isAssignable(class3, class2) && isAssignable(type, class3))
                    type = type1;
                j++;
            }
            if(type != null)
                return type;
        }
        return class1.getGenericSuperclass();
    }

    public static Type[] getImplicitBounds(TypeVariable typevariable)
    {
        Type atype[] = typevariable.getBounds();
        if(atype.length == 0)
            return (new Type[] {
                java/lang/Object
            });
        else
            return normalizeUpperBounds(atype);
    }

    public static Type[] getImplicitLowerBounds(WildcardType wildcardtype)
    {
        Type atype[] = wildcardtype.getLowerBounds();
        if(atype.length == 0)
        {
            atype = new Type[1];
            atype[0] = null;
        }
        return atype;
    }

    public static Type[] getImplicitUpperBounds(WildcardType wildcardtype)
    {
        Type atype[] = wildcardtype.getUpperBounds();
        if(atype.length == 0)
            return (new Type[] {
                java/lang/Object
            });
        else
            return normalizeUpperBounds(atype);
    }

    private static Class getRawType(ParameterizedType parameterizedtype)
    {
        Type type = parameterizedtype.getRawType();
        if(!(type instanceof Class))
            throw new IllegalStateException((new StringBuilder()).append("Wait... What!? Type of rawType: ").append(type).toString());
        else
            return (Class)type;
    }

    public static Class getRawType(Type type, Type type1)
    {
        if(type instanceof Class)
            return (Class)type;
        if(type instanceof ParameterizedType)
            return getRawType((ParameterizedType)type);
        if(type instanceof TypeVariable)
        {
            if(type1 == null)
                return null;
            java.lang.reflect.GenericDeclaration genericdeclaration = ((TypeVariable)type).getGenericDeclaration();
            if(!(genericdeclaration instanceof Class))
                return null;
            Map map = getTypeArguments(type1, (Class)genericdeclaration);
            if(map == null)
                return null;
            Type type2 = (Type)map.get(type);
            if(type2 == null)
                return null;
            else
                return getRawType(type2, type1);
        }
        if(type instanceof GenericArrayType)
            return Array.newInstance(getRawType(((GenericArrayType)type).getGenericComponentType(), type1), 0).getClass();
        if(type instanceof WildcardType)
            return null;
        else
            throw new IllegalArgumentException((new StringBuilder()).append("unknown type: ").append(type).toString());
    }

    private static Map getTypeArguments(Class class1, Class class2, Map map)
    {
        Object obj;
        if(!isAssignable(class1, class2))
        {
            obj = null;
        } else
        {
            if(class1.isPrimitive())
            {
                if(class2.isPrimitive())
                    return new HashMap();
                class1 = ClassUtils.primitiveToWrapper(class1);
            }
            if(map == null)
                obj = new HashMap();
            else
                obj = new HashMap(map);
            if(class1.getTypeParameters().length <= 0 && !class2.equals(class1))
                return getTypeArguments(getClosestParentType(class1, class2), class2, ((Map) (obj)));
        }
        return ((Map) (obj));
    }

    public static Map getTypeArguments(ParameterizedType parameterizedtype)
    {
        return getTypeArguments(parameterizedtype, getRawType(parameterizedtype), null);
    }

    private static Map getTypeArguments(ParameterizedType parameterizedtype, Class class1, Map map)
    {
        Class class2 = getRawType(parameterizedtype);
        Object obj;
        if(!isAssignable(class2, class1))
        {
            obj = null;
        } else
        {
            Type type = parameterizedtype.getOwnerType();
            Type atype[];
            TypeVariable atypevariable[];
            int i;
            if(type instanceof ParameterizedType)
            {
                ParameterizedType parameterizedtype1 = (ParameterizedType)type;
                obj = getTypeArguments(parameterizedtype1, getRawType(parameterizedtype1), map);
            } else
            if(map == null)
                obj = new HashMap();
            else
                obj = new HashMap(map);
            atype = parameterizedtype.getActualTypeArguments();
            atypevariable = class2.getTypeParameters();
            i = 0;
            while(i < atypevariable.length) 
            {
                Type type1 = atype[i];
                TypeVariable typevariable = atypevariable[i];
                Type type2;
                if(((Map) (obj)).containsKey(type1))
                    type2 = (Type)((Map) (obj)).get(type1);
                else
                    type2 = type1;
                ((Map) (obj)).put(typevariable, type2);
                i++;
            }
            if(!class1.equals(class2))
                return getTypeArguments(getClosestParentType(class2, class1), class1, ((Map) (obj)));
        }
        return ((Map) (obj));
    }

    public static Map getTypeArguments(Type type, Class class1)
    {
        return getTypeArguments(type, class1, null);
    }

    private static Map getTypeArguments(Type type, Class class1, Map map)
    {
        if(!(type instanceof Class)) goto _L2; else goto _L1
_L1:
        Map map1 = getTypeArguments((Class)type, class1, map);
_L4:
        return map1;
_L2:
        if(type instanceof ParameterizedType)
            return getTypeArguments((ParameterizedType)type, class1, map);
        if(type instanceof GenericArrayType)
        {
            Type type3 = ((GenericArrayType)type).getGenericComponentType();
            if(class1.isArray())
                class1 = class1.getComponentType();
            return getTypeArguments(type3, class1, map);
        }
        if(type instanceof WildcardType)
        {
            Type atype1[] = getImplicitUpperBounds((WildcardType)type);
            int k = atype1.length;
            int l = 0;
            do
            {
                map1 = null;
                if(l >= k)
                    continue; /* Loop/switch isn't completed */
                Type type2 = atype1[l];
                if(isAssignable(type2, class1))
                    return getTypeArguments(type2, class1, map);
                l++;
            } while(true);
        }
label0:
        {
            if(!(type instanceof TypeVariable))
                break label0;
            Type atype[] = getImplicitBounds((TypeVariable)type);
            int i = atype.length;
            int j = 0;
            do
            {
                map1 = null;
                if(j >= i)
                    break;
                Type type1 = atype[j];
                if(isAssignable(type1, class1))
                    return getTypeArguments(type1, class1, map);
                j++;
            } while(true);
        }
        if(true) goto _L4; else goto _L3
_L3:
        throw new IllegalStateException((new StringBuilder()).append("found an unhandled type: ").append(type).toString());
    }

    public static boolean isArrayType(Type type)
    {
        return (type instanceof GenericArrayType) || (type instanceof Class) && ((Class)type).isArray();
    }

    private static boolean isAssignable(Type type, Class class1)
    {
        if(type != null) goto _L2; else goto _L1
_L1:
        boolean flag;
label0:
        {
            if(class1 != null)
            {
                boolean flag4 = class1.isPrimitive();
                flag = false;
                if(flag4)
                    break label0;
            }
            flag = true;
        }
_L4:
        return flag;
_L2:
        boolean flag3;
        flag = false;
        if(class1 == null)
            continue; /* Loop/switch isn't completed */
        if(class1.equals(type))
            return true;
        if(type instanceof Class)
            return ClassUtils.isAssignable((Class)type, class1);
        if(type instanceof ParameterizedType)
            return isAssignable(((Type) (getRawType((ParameterizedType)type))), class1);
        if(type instanceof TypeVariable)
        {
            Type atype[] = ((TypeVariable)type).getBounds();
            int i = atype.length;
            int j = 0;
            do
            {
                flag = false;
                if(j >= i)
                    continue; /* Loop/switch isn't completed */
                if(isAssignable(atype[j], class1))
                    return true;
                j++;
            } while(true);
        }
        if(!(type instanceof GenericArrayType))
            break MISSING_BLOCK_LABEL_187;
        if(class1.equals(java/lang/Object))
            break; /* Loop/switch isn't completed */
        boolean flag2 = class1.isArray();
        flag = false;
        if(!flag2)
            continue; /* Loop/switch isn't completed */
        flag3 = isAssignable(((GenericArrayType)type).getGenericComponentType(), class1.getComponentType());
        flag = false;
        if(!flag3) goto _L4; else goto _L3
_L3:
        return true;
        boolean flag1 = type instanceof WildcardType;
        flag = false;
        if(!flag1)
            throw new IllegalStateException((new StringBuilder()).append("found an unhandled type: ").append(type).toString());
        if(true) goto _L4; else goto _L5
_L5:
    }

    private static boolean isAssignable(Type type, GenericArrayType genericarraytype, Map map)
    {
        if(type != null) goto _L2; else goto _L1
_L1:
        return true;
_L2:
        Type type1;
        if(genericarraytype == null)
            return false;
        if(genericarraytype.equals(type))
            continue; /* Loop/switch isn't completed */
        type1 = genericarraytype.getGenericComponentType();
        if(!(type instanceof Class))
            break; /* Loop/switch isn't completed */
        Class class1 = (Class)type;
        if(!class1.isArray() || !isAssignable(((Type) (class1.getComponentType())), type1, map))
            return false;
        if(true) goto _L1; else goto _L3
_L3:
        if(type instanceof GenericArrayType)
            return isAssignable(((GenericArrayType)type).getGenericComponentType(), type1, map);
        if(type instanceof WildcardType)
        {
            Type atype1[] = getImplicitUpperBounds((WildcardType)type);
            int k = atype1.length;
            for(int l = 0; l < k; l++)
                if(isAssignable(atype1[l], ((Type) (genericarraytype))))
                    continue; /* Loop/switch isn't completed */

            return false;
        }
        if(!(type instanceof TypeVariable))
            break MISSING_BLOCK_LABEL_187;
        Type atype[] = getImplicitBounds((TypeVariable)type);
        int i = atype.length;
        int j = 0;
        do
        {
            if(j >= i)
                break; /* Loop/switch isn't completed */
            if(isAssignable(atype[j], ((Type) (genericarraytype))))
                continue; /* Loop/switch isn't completed */
            j++;
        } while(true);
        if(true) goto _L1; else goto _L4
_L4:
        return false;
        if(type instanceof ParameterizedType)
            return false;
        else
            throw new IllegalStateException((new StringBuilder()).append("found an unhandled type: ").append(type).toString());
    }

    private static boolean isAssignable(Type type, ParameterizedType parameterizedtype, Map map)
    {
        if(type != null) goto _L2; else goto _L1
_L1:
        return true;
_L2:
        if(parameterizedtype == null)
            return false;
        if(parameterizedtype.equals(type))
            continue; /* Loop/switch isn't completed */
        Class class1 = getRawType(parameterizedtype);
        Map map1 = getTypeArguments(type, class1, null);
        if(map1 == null)
            return false;
        if(map1.isEmpty())
            continue; /* Loop/switch isn't completed */
        Iterator iterator = getTypeArguments(parameterizedtype, class1, map).entrySet().iterator();
        Type type1;
        Type type2;
        do
        {
            if(!iterator.hasNext())
                continue; /* Loop/switch isn't completed */
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            type1 = (Type)entry.getValue();
            type2 = (Type)map1.get(entry.getKey());
        } while(type2 == null || type1.equals(type2) || (type1 instanceof WildcardType) && isAssignable(type2, type1, map));
        break; /* Loop/switch isn't completed */
        if(true) goto _L1; else goto _L3
_L3:
        return false;
    }

    public static boolean isAssignable(Type type, Type type1)
    {
        return isAssignable(type, type1, null);
    }

    private static boolean isAssignable(Type type, Type type1, Map map)
    {
        if(type1 == null || (type1 instanceof Class))
            return isAssignable(type, (Class)type1);
        if(type1 instanceof ParameterizedType)
            return isAssignable(type, (ParameterizedType)type1, map);
        if(type1 instanceof GenericArrayType)
            return isAssignable(type, (GenericArrayType)type1, map);
        if(type1 instanceof WildcardType)
            return isAssignable(type, (WildcardType)type1, map);
        if(type1 instanceof TypeVariable)
            return isAssignable(type, (TypeVariable)type1, map);
        else
            throw new IllegalStateException((new StringBuilder()).append("found an unhandled type: ").append(type1).toString());
    }

    private static boolean isAssignable(Type type, TypeVariable typevariable, Map map)
    {
        if(type == null)
            return true;
        if(typevariable == null)
            return false;
        if(typevariable.equals(type))
            return true;
        if(type instanceof TypeVariable)
        {
            Type atype[] = getImplicitBounds((TypeVariable)type);
            int i = atype.length;
            for(int j = 0; j < i; j++)
                if(isAssignable(atype[j], typevariable, map))
                    return true;

        }
        if((type instanceof Class) || (type instanceof ParameterizedType) || (type instanceof GenericArrayType) || (type instanceof WildcardType))
            return false;
        else
            throw new IllegalStateException((new StringBuilder()).append("found an unhandled type: ").append(type).toString());
    }

    private static boolean isAssignable(Type type, WildcardType wildcardtype, Map map)
    {
        if(type == null)
            return true;
        if(wildcardtype == null)
            return false;
        if(wildcardtype.equals(type))
            return true;
        Type atype[] = getImplicitUpperBounds(wildcardtype);
        Type atype1[] = getImplicitLowerBounds(wildcardtype);
        if(type instanceof WildcardType)
        {
            WildcardType wildcardtype1 = (WildcardType)type;
            Type atype2[] = getImplicitUpperBounds(wildcardtype1);
            Type atype3[] = getImplicitLowerBounds(wildcardtype1);
            int i1 = atype.length;
            for(int j1 = 0; j1 < i1; j1++)
            {
                Type type2 = substituteTypeVariables(atype[j1], map);
                int k2 = atype2.length;
                for(int l2 = 0; l2 < k2; l2++)
                    if(!isAssignable(atype2[l2], type2, map))
                        return false;

            }

            int k1 = atype1.length;
            for(int l1 = 0; l1 < k1; l1++)
            {
                Type type1 = substituteTypeVariables(atype1[l1], map);
                int i2 = atype3.length;
                for(int j2 = 0; j2 < i2; j2++)
                    if(!isAssignable(type1, atype3[j2], map))
                        return false;

            }

            return true;
        }
        int i = atype.length;
        for(int j = 0; j < i; j++)
            if(!isAssignable(type, substituteTypeVariables(atype[j], map), map))
                return false;

        int k = atype1.length;
        for(int l = 0; l < k; l++)
            if(!isAssignable(substituteTypeVariables(atype1[l], map), type, map))
                return false;

        return true;
    }

    public static boolean isInstance(Object obj, Type type)
    {
        if(type != null)
            if(obj == null)
            {
                if(!(type instanceof Class) || !((Class)type).isPrimitive())
                    return true;
            } else
            {
                return isAssignable(obj.getClass(), type, null);
            }
        return false;
    }

    private static void mapTypeVariablesToArguments(Class class1, ParameterizedType parameterizedtype, Map map)
    {
        Type type = parameterizedtype.getOwnerType();
        if(type instanceof ParameterizedType)
            mapTypeVariablesToArguments(class1, (ParameterizedType)type, map);
        Type atype[] = parameterizedtype.getActualTypeArguments();
        TypeVariable atypevariable[] = getRawType(parameterizedtype).getTypeParameters();
        List list = Arrays.asList(class1.getTypeParameters());
        for(int i = 0; i < atype.length; i++)
        {
            TypeVariable typevariable = atypevariable[i];
            Type type1 = atype[i];
            if(list.contains(type1) && map.containsKey(typevariable))
                map.put((TypeVariable)type1, map.get(typevariable));
        }

    }

    public static Type[] normalizeUpperBounds(Type atype[])
    {
        if(atype.length < 2)
            return atype;
        HashSet hashset = new HashSet(atype.length);
        int i = atype.length;
        int j = 0;
label0:
        do
        {
            if(j < i)
            {
                Type type = atype[j];
                int k = atype.length;
                int l = 0;
                do
                {
label1:
                    {
                        boolean flag = false;
                        if(l < k)
                        {
                            Type type1 = atype[l];
                            if(type == type1 || !isAssignable(type1, type, null))
                                break label1;
                            flag = true;
                        }
                        if(!flag)
                            hashset.add(type);
                        j++;
                        continue label0;
                    }
                    l++;
                } while(true);
            }
            return (Type[])hashset.toArray(new Type[hashset.size()]);
        } while(true);
    }

    private static Type substituteTypeVariables(Type type, Map map)
    {
        Type type1;
        if((type instanceof TypeVariable) && map != null)
        {
            type1 = (Type)map.get(type);
            if(type1 == null)
                throw new IllegalArgumentException((new StringBuilder()).append("missing assignment type for type variable ").append(type).toString());
        } else
        {
            type1 = type;
        }
        return type1;
    }

    public static boolean typesSatisfyVariables(Map map)
    {
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            TypeVariable typevariable = (TypeVariable)entry.getKey();
            Type type = (Type)entry.getValue();
            Type atype[] = getImplicitBounds(typevariable);
            int i = atype.length;
            int j = 0;
            while(j < i) 
            {
                if(!isAssignable(type, substituteTypeVariables(atype[j], map), map))
                    return false;
                j++;
            }
        }

        return true;
    }
}
