// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class WordUtils
{

    public WordUtils()
    {
    }

    public static String capitalize(String s)
    {
        return capitalize(s, null);
    }

    public static transient String capitalize(String s, char ac[])
    {
        int i;
        if(ac == null)
            i = -1;
        else
            i = ac.length;
        if(StringUtils.isEmpty(s) || i == 0)
            return s;
        char ac1[] = s.toCharArray();
        boolean flag = true;
        int j = 0;
        while(j < ac1.length) 
        {
            char c = ac1[j];
            if(isDelimiter(c, ac))
                flag = true;
            else
            if(flag)
            {
                ac1[j] = Character.toTitleCase(c);
                flag = false;
            }
            j++;
        }
        return new String(ac1);
    }

    public static String capitalizeFully(String s)
    {
        return capitalizeFully(s, null);
    }

    public static transient String capitalizeFully(String s, char ac[])
    {
        int i;
        if(ac == null)
            i = -1;
        else
            i = ac.length;
        if(StringUtils.isEmpty(s) || i == 0)
            return s;
        else
            return capitalize(s.toLowerCase(), ac);
    }

    public static String initials(String s)
    {
        return initials(s, null);
    }

    public static transient String initials(String s, char ac[])
    {
        if(StringUtils.isEmpty(s))
            return s;
        if(ac != null && ac.length == 0)
            return "";
        int i = s.length();
        char ac1[] = new char[1 + i / 2];
        boolean flag = true;
        int j = 0;
        int k = 0;
        while(j < i) 
        {
            char c = s.charAt(j);
            int l;
            if(isDelimiter(c, ac))
            {
                flag = true;
                l = k;
            } else
            if(flag)
            {
                l = k + 1;
                ac1[k] = c;
                flag = false;
            } else
            {
                l = k;
            }
            j++;
            k = l;
        }
        return new String(ac1, 0, k);
    }

    private static boolean isDelimiter(char c, char ac[])
    {
        if(ac == null)
            return Character.isWhitespace(c);
        int i = ac.length;
        for(int j = 0; j < i; j++)
            if(c == ac[j])
                return true;

        return false;
    }

    public static String swapCase(String s)
    {
        if(StringUtils.isEmpty(s))
            return s;
        char ac[] = s.toCharArray();
        boolean flag = true;
        int i = 0;
        while(i < ac.length) 
        {
            char c = ac[i];
            if(Character.isUpperCase(c))
            {
                ac[i] = Character.toLowerCase(c);
                flag = false;
            } else
            if(Character.isTitleCase(c))
            {
                ac[i] = Character.toLowerCase(c);
                flag = false;
            } else
            if(Character.isLowerCase(c))
            {
                if(flag)
                {
                    ac[i] = Character.toTitleCase(c);
                    flag = false;
                } else
                {
                    ac[i] = Character.toUpperCase(c);
                }
            } else
            {
                flag = Character.isWhitespace(c);
            }
            i++;
        }
        return new String(ac);
    }

    public static String uncapitalize(String s)
    {
        return uncapitalize(s, null);
    }

    public static transient String uncapitalize(String s, char ac[])
    {
        int i;
        if(ac == null)
            i = -1;
        else
            i = ac.length;
        if(StringUtils.isEmpty(s) || i == 0)
            return s;
        char ac1[] = s.toCharArray();
        boolean flag = true;
        int j = 0;
        while(j < ac1.length) 
        {
            char c = ac1[j];
            if(isDelimiter(c, ac))
                flag = true;
            else
            if(flag)
            {
                ac1[j] = Character.toLowerCase(c);
                flag = false;
            }
            j++;
        }
        return new String(ac1);
    }

    public static String wrap(String s, int i)
    {
        return wrap(s, i, null, false);
    }

    public static String wrap(String s, int i, String s1, boolean flag)
    {
        if(s == null)
            return null;
        if(s1 == null)
            s1 = SystemUtils.LINE_SEPARATOR;
        if(i < 1)
            i = 1;
        int j = s.length();
        int k = 0;
        StringBuilder stringbuilder = new StringBuilder(j + 32);
        while(j - k > i) 
            if(s.charAt(k) == ' ')
            {
                k++;
            } else
            {
                int l = s.lastIndexOf(' ', i + k);
                if(l >= k)
                {
                    stringbuilder.append(s.substring(k, l));
                    stringbuilder.append(s1);
                    k = l + 1;
                } else
                if(flag)
                {
                    stringbuilder.append(s.substring(k, i + k));
                    stringbuilder.append(s1);
                    k += i;
                } else
                {
                    int i1 = s.indexOf(' ', i + k);
                    if(i1 >= 0)
                    {
                        stringbuilder.append(s.substring(k, i1));
                        stringbuilder.append(s1);
                        k = i1 + 1;
                    } else
                    {
                        stringbuilder.append(s.substring(k));
                        k = j;
                    }
                }
            }
        stringbuilder.append(s.substring(k));
        return stringbuilder.toString();
    }
}
