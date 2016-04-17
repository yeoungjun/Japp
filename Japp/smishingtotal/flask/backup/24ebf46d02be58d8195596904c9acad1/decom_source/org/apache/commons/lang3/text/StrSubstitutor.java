// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text;

import java.util.*;

// Referenced classes of package org.apache.commons.lang3.text:
//            StrMatcher, StrLookup, StrBuilder

public class StrSubstitutor
{

    public StrSubstitutor()
    {
        this((StrLookup)null, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(Map map)
    {
        this(StrLookup.mapLookup(map), DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(Map map, String s, String s1)
    {
        this(StrLookup.mapLookup(map), s, s1, '$');
    }

    public StrSubstitutor(Map map, String s, String s1, char c)
    {
        this(StrLookup.mapLookup(map), s, s1, c);
    }

    public StrSubstitutor(StrLookup strlookup)
    {
        this(strlookup, DEFAULT_PREFIX, DEFAULT_SUFFIX, '$');
    }

    public StrSubstitutor(StrLookup strlookup, String s, String s1, char c)
    {
        setVariableResolver(strlookup);
        setVariablePrefix(s);
        setVariableSuffix(s1);
        setEscapeChar(c);
    }

    public StrSubstitutor(StrLookup strlookup, StrMatcher strmatcher, StrMatcher strmatcher1, char c)
    {
        setVariableResolver(strlookup);
        setVariablePrefixMatcher(strmatcher);
        setVariableSuffixMatcher(strmatcher1);
        setEscapeChar(c);
    }

    private void checkCyclicSubstitution(String s, List list)
    {
        if(!list.contains(s))
        {
            return;
        } else
        {
            StrBuilder strbuilder = new StrBuilder(256);
            strbuilder.append("Infinite loop in property interpolation of ");
            strbuilder.append((String)list.remove(0));
            strbuilder.append(": ");
            strbuilder.appendWithSeparators(list, "->");
            throw new IllegalStateException(strbuilder.toString());
        }
    }

    public static String replace(Object obj, Map map)
    {
        return (new StrSubstitutor(map)).replace(obj);
    }

    public static String replace(Object obj, Map map, String s, String s1)
    {
        return (new StrSubstitutor(map, s, s1)).replace(obj);
    }

    public static String replace(Object obj, Properties properties)
    {
        if(properties == null)
            return obj.toString();
        HashMap hashmap = new HashMap();
        String s;
        for(Enumeration enumeration = properties.propertyNames(); enumeration.hasMoreElements(); hashmap.put(s, properties.getProperty(s)))
            s = (String)enumeration.nextElement();

        return replace(obj, ((Map) (hashmap)));
    }

    public static String replaceSystemProperties(Object obj)
    {
        return (new StrSubstitutor(StrLookup.systemPropertiesLookup())).replace(obj);
    }

    private int substitute(StrBuilder strbuilder, int i, int j, List list)
    {
        StrMatcher strmatcher = getVariablePrefixMatcher();
        StrMatcher strmatcher1 = getVariableSuffixMatcher();
        char c = getEscapeChar();
        boolean flag;
        boolean flag1;
        int k;
        char ac[];
        int l;
        int i1;
        if(list == null)
            flag = true;
        else
            flag = false;
        flag1 = false;
        k = 0;
        ac = strbuilder.buffer;
        l = i + j;
        i1 = i;
label0:
        do
        {
            if(i1 < l)
            {
                int j1 = strmatcher.isMatch(ac, i1, i, l);
                if(j1 == 0)
                {
                    i1++;
                    continue;
                }
                if(i1 > i && ac[i1 - 1] == c)
                {
                    strbuilder.deleteCharAt(i1 - 1);
                    ac = strbuilder.buffer;
                    k--;
                    flag1 = true;
                    l--;
                    continue;
                }
                int k1 = i1;
                i1 += j1;
                int l1 = 0;
                do
                {
                    if(i1 >= l)
                        continue label0;
                    if(isEnableSubstitutionInVariables())
                    {
                        int l3 = strmatcher.isMatch(ac, i1, i, l);
                        if(l3 != 0)
                        {
                            l1++;
                            i1 += l3;
                            continue;
                        }
                    }
                    int i2 = strmatcher1.isMatch(ac, i1, i, l);
                    if(i2 == 0)
                    {
                        i1++;
                    } else
                    {
                        if(l1 == 0)
                        {
                            int j2 = k1 + j1;
                            int k2 = i1 - k1 - j1;
                            String s = new String(ac, j2, k2);
                            if(isEnableSubstitutionInVariables())
                            {
                                StrBuilder strbuilder1 = new StrBuilder(s);
                                substitute(strbuilder1, 0, strbuilder1.length());
                                s = strbuilder1.toString();
                            }
                            i1 += i2;
                            int l2 = i1;
                            if(list == null)
                            {
                                list = new ArrayList();
                                String s1 = new String(ac, i, j);
                                list.add(s1);
                            }
                            checkCyclicSubstitution(s, list);
                            list.add(s);
                            String s2 = resolveVariable(s, strbuilder, k1, l2);
                            if(s2 != null)
                            {
                                int j3 = s2.length();
                                strbuilder.replace(k1, l2, s2);
                                flag1 = true;
                                int k3 = (j3 + substitute(strbuilder, k1, j3, list)) - (l2 - k1);
                                i1 += k3;
                                l += k3;
                                k += k3;
                                ac = strbuilder.buffer;
                            }
                            int i3 = -1 + list.size();
                            list.remove(i3);
                            continue label0;
                        }
                        l1--;
                        i1 += i2;
                    }
                } while(true);
            }
            if(flag)
                return !flag1 ? 0 : 1;
            return k;
        } while(true);
    }

    public char getEscapeChar()
    {
        return escapeChar;
    }

    public StrMatcher getVariablePrefixMatcher()
    {
        return prefixMatcher;
    }

    public StrLookup getVariableResolver()
    {
        return variableResolver;
    }

    public StrMatcher getVariableSuffixMatcher()
    {
        return suffixMatcher;
    }

    public boolean isEnableSubstitutionInVariables()
    {
        return enableSubstitutionInVariables;
    }

    public String replace(Object obj)
    {
        if(obj == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder = (new StrBuilder()).append(obj);
            substitute(strbuilder, 0, strbuilder.length());
            return strbuilder.toString();
        }
    }

    public String replace(String s)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            StrBuilder strbuilder = new StrBuilder(s);
            if(substitute(strbuilder, 0, s.length()))
                return strbuilder.toString();
        }
        return s;
    }

    public String replace(String s, int i, int j)
    {
        if(s == null)
            return null;
        StrBuilder strbuilder = (new StrBuilder(j)).append(s, i, j);
        if(!substitute(strbuilder, 0, j))
            return s.substring(i, i + j);
        else
            return strbuilder.toString();
    }

    public String replace(StringBuffer stringbuffer)
    {
        if(stringbuffer == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder = (new StrBuilder(stringbuffer.length())).append(stringbuffer);
            substitute(strbuilder, 0, strbuilder.length());
            return strbuilder.toString();
        }
    }

    public String replace(StringBuffer stringbuffer, int i, int j)
    {
        if(stringbuffer == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder = (new StrBuilder(j)).append(stringbuffer, i, j);
            substitute(strbuilder, 0, j);
            return strbuilder.toString();
        }
    }

    public String replace(StrBuilder strbuilder)
    {
        if(strbuilder == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder1 = (new StrBuilder(strbuilder.length())).append(strbuilder);
            substitute(strbuilder1, 0, strbuilder1.length());
            return strbuilder1.toString();
        }
    }

    public String replace(StrBuilder strbuilder, int i, int j)
    {
        if(strbuilder == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder1 = (new StrBuilder(j)).append(strbuilder, i, j);
            substitute(strbuilder1, 0, j);
            return strbuilder1.toString();
        }
    }

    public String replace(char ac[])
    {
        if(ac == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder = (new StrBuilder(ac.length)).append(ac);
            substitute(strbuilder, 0, ac.length);
            return strbuilder.toString();
        }
    }

    public String replace(char ac[], int i, int j)
    {
        if(ac == null)
        {
            return null;
        } else
        {
            StrBuilder strbuilder = (new StrBuilder(j)).append(ac, i, j);
            substitute(strbuilder, 0, j);
            return strbuilder.toString();
        }
    }

    public boolean replaceIn(StringBuffer stringbuffer)
    {
        if(stringbuffer == null)
            return false;
        else
            return replaceIn(stringbuffer, 0, stringbuffer.length());
    }

    public boolean replaceIn(StringBuffer stringbuffer, int i, int j)
    {
        StrBuilder strbuilder;
        if(stringbuffer != null)
            if(substitute(strbuilder = (new StrBuilder(j)).append(stringbuffer, i, j), 0, j))
            {
                stringbuffer.replace(i, i + j, strbuilder.toString());
                return true;
            }
        return false;
    }

    public boolean replaceIn(StrBuilder strbuilder)
    {
        if(strbuilder == null)
            return false;
        else
            return substitute(strbuilder, 0, strbuilder.length());
    }

    public boolean replaceIn(StrBuilder strbuilder, int i, int j)
    {
        if(strbuilder == null)
            return false;
        else
            return substitute(strbuilder, i, j);
    }

    protected String resolveVariable(String s, StrBuilder strbuilder, int i, int j)
    {
        StrLookup strlookup = getVariableResolver();
        if(strlookup == null)
            return null;
        else
            return strlookup.lookup(s);
    }

    public void setEnableSubstitutionInVariables(boolean flag)
    {
        enableSubstitutionInVariables = flag;
    }

    public void setEscapeChar(char c)
    {
        escapeChar = c;
    }

    public StrSubstitutor setVariablePrefix(char c)
    {
        return setVariablePrefixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariablePrefix(String s)
    {
        if(s == null)
            throw new IllegalArgumentException("Variable prefix must not be null!");
        else
            return setVariablePrefixMatcher(StrMatcher.stringMatcher(s));
    }

    public StrSubstitutor setVariablePrefixMatcher(StrMatcher strmatcher)
    {
        if(strmatcher == null)
        {
            throw new IllegalArgumentException("Variable prefix matcher must not be null!");
        } else
        {
            prefixMatcher = strmatcher;
            return this;
        }
    }

    public void setVariableResolver(StrLookup strlookup)
    {
        variableResolver = strlookup;
    }

    public StrSubstitutor setVariableSuffix(char c)
    {
        return setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }

    public StrSubstitutor setVariableSuffix(String s)
    {
        if(s == null)
            throw new IllegalArgumentException("Variable suffix must not be null!");
        else
            return setVariableSuffixMatcher(StrMatcher.stringMatcher(s));
    }

    public StrSubstitutor setVariableSuffixMatcher(StrMatcher strmatcher)
    {
        if(strmatcher == null)
        {
            throw new IllegalArgumentException("Variable suffix matcher must not be null!");
        } else
        {
            suffixMatcher = strmatcher;
            return this;
        }
    }

    protected boolean substitute(StrBuilder strbuilder, int i, int j)
    {
        return substitute(strbuilder, i, j, null) > 0;
    }

    public static final char DEFAULT_ESCAPE = 36;
    public static final StrMatcher DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
    public static final StrMatcher DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
    private boolean enableSubstitutionInVariables;
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private StrMatcher suffixMatcher;
    private StrLookup variableResolver;

}
