// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public abstract class StrMatcher
{
    static final class CharMatcher extends StrMatcher
    {

        public int isMatch(char ac[], int i, int j, int k)
        {
            return ch != ac[i] ? 0 : 1;
        }

        private final char ch;

        CharMatcher(char c)
        {
            ch = c;
        }
    }

    static final class CharSetMatcher extends StrMatcher
    {

        public int isMatch(char ac[], int i, int j, int k)
        {
            return Arrays.binarySearch(chars, ac[i]) < 0 ? 0 : 1;
        }

        private final char chars[];

        CharSetMatcher(char ac[])
        {
            chars = (char[])ac.clone();
            Arrays.sort(chars);
        }
    }

    static final class NoMatcher extends StrMatcher
    {

        public int isMatch(char ac[], int i, int j, int k)
        {
            return 0;
        }

        NoMatcher()
        {
        }
    }

    static final class StringMatcher extends StrMatcher
    {

        public int isMatch(char ac[], int i, int j, int k)
        {
            int l = chars.length;
            if(i + l > k)
            {
                l = 0;
            } else
            {
                int i1 = 0;
                while(i1 < chars.length) 
                {
                    if(chars[i1] != ac[i])
                        return 0;
                    i1++;
                    i++;
                }
            }
            return l;
        }

        private final char chars[];

        StringMatcher(String s)
        {
            chars = s.toCharArray();
        }
    }

    static final class TrimMatcher extends StrMatcher
    {

        public int isMatch(char ac[], int i, int j, int k)
        {
            return ac[i] > ' ' ? 0 : 1;
        }

        TrimMatcher()
        {
        }
    }


    protected StrMatcher()
    {
    }

    public static StrMatcher charMatcher(char c)
    {
        return new CharMatcher(c);
    }

    public static StrMatcher charSetMatcher(String s)
    {
        if(s == null || s.length() == 0)
            return NONE_MATCHER;
        if(s.length() == 1)
            return new CharMatcher(s.charAt(0));
        else
            return new CharSetMatcher(s.toCharArray());
    }

    public static transient StrMatcher charSetMatcher(char ac[])
    {
        if(ac == null || ac.length == 0)
            return NONE_MATCHER;
        if(ac.length == 1)
            return new CharMatcher(ac[0]);
        else
            return new CharSetMatcher(ac);
    }

    public static StrMatcher commaMatcher()
    {
        return COMMA_MATCHER;
    }

    public static StrMatcher doubleQuoteMatcher()
    {
        return DOUBLE_QUOTE_MATCHER;
    }

    public static StrMatcher noneMatcher()
    {
        return NONE_MATCHER;
    }

    public static StrMatcher quoteMatcher()
    {
        return QUOTE_MATCHER;
    }

    public static StrMatcher singleQuoteMatcher()
    {
        return SINGLE_QUOTE_MATCHER;
    }

    public static StrMatcher spaceMatcher()
    {
        return SPACE_MATCHER;
    }

    public static StrMatcher splitMatcher()
    {
        return SPLIT_MATCHER;
    }

    public static StrMatcher stringMatcher(String s)
    {
        if(StringUtils.isEmpty(s))
            return NONE_MATCHER;
        else
            return new StringMatcher(s);
    }

    public static StrMatcher tabMatcher()
    {
        return TAB_MATCHER;
    }

    public static StrMatcher trimMatcher()
    {
        return TRIM_MATCHER;
    }

    public int isMatch(char ac[], int i)
    {
        return isMatch(ac, i, 0, ac.length);
    }

    public abstract int isMatch(char ac[], int i, int j, int k);

    private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
    private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('"');
    private static final StrMatcher NONE_MATCHER = new NoMatcher();
    private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
    private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
    private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
    private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
    private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
    private static final StrMatcher TRIM_MATCHER = new TrimMatcher();

}
