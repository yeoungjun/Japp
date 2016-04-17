// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package org.apache.commons.lang3:
//            CharSequenceUtils, ArrayUtils, CharUtils, ObjectUtils

public class StringUtils
{
    private static class InitStripAccents
    {

        private static final Throwable java6Exception;
        private static final Method java6NormalizeMethod;
        private static final Object java6NormalizerFormNFD;
        private static final Pattern java6Pattern;
        private static final Method sunDecomposeMethod;
        private static final Throwable sunException;
        private static final Pattern sunPattern;

        static 
        {
            Object obj;
            Method method;
            Object obj1;
            Object obj2;
            sunPattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            java6Pattern = sunPattern;
            obj = null;
            method = null;
            obj1 = null;
            obj2 = null;
            Method method3;
            Class class2 = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer$Form");
            obj = class2.getField("NFD").get(null);
            method3 = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer").getMethod("normalize", new Class[] {
                java/lang/CharSequence, class2
            });
            Method method1 = method3;
_L2:
            java6Exception = ((Throwable) (obj1));
            java6NormalizerFormNFD = obj;
            java6NormalizeMethod = method1;
            sunException = ((Throwable) (obj2));
            sunDecomposeMethod = method;
            return;
            Exception exception;
            exception;
            obj1 = exception;
            Method method2;
            Class class1 = Thread.currentThread().getContextClassLoader().loadClass("sun.text.Normalizer");
            Class aclass[] = new Class[3];
            aclass[0] = java/lang/String;
            aclass[1] = Boolean.TYPE;
            aclass[2] = Integer.TYPE;
            method2 = class1.getMethod("decompose", aclass);
            method = method2;
            method1 = null;
            obj2 = null;
            continue; /* Loop/switch isn't completed */
            Exception exception1;
            exception1;
            obj2 = exception1;
            method1 = null;
            method = null;
            if(true) goto _L2; else goto _L1
_L1:
        }








        private InitStripAccents()
        {
        }
    }


    public StringUtils()
    {
    }

    public static String abbreviate(String s, int i)
    {
        return abbreviate(s, 0, i);
    }

    public static String abbreviate(String s, int i, int j)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            if(j < 4)
                throw new IllegalArgumentException("Minimum abbreviation width is 4");
            if(s.length() > j)
            {
                if(i > s.length())
                    i = s.length();
                if(s.length() - i < j - 3)
                    i = s.length() - (j - 3);
                if(i <= 4)
                    return (new StringBuilder()).append(s.substring(0, j - 3)).append("...").toString();
                if(j < 7)
                    throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
                if(-3 + (i + j) < s.length())
                    return (new StringBuilder()).append("...").append(abbreviate(s.substring(i), j - 3)).toString();
                else
                    return (new StringBuilder()).append("...").append(s.substring(s.length() - (j - 3))).toString();
            }
        }
        return s;
    }

    public static String abbreviateMiddle(String s, String s1, int i)
    {
        while(isEmpty(s) || isEmpty(s1) || i >= s.length() || i < 2 + s1.length()) 
            return s;
        int j = i - s1.length();
        int k = j / 2 + j % 2;
        int l = s.length() - j / 2;
        StringBuilder stringbuilder = new StringBuilder(i);
        stringbuilder.append(s.substring(0, k));
        stringbuilder.append(s1);
        stringbuilder.append(s.substring(l));
        return stringbuilder.toString();
    }

    public static String capitalize(String s)
    {
        int i;
label0:
        {
            if(s != null)
            {
                i = s.length();
                if(i != 0)
                    break label0;
            }
            return s;
        }
        return (new StringBuilder(i)).append(Character.toTitleCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static String center(String s, int i)
    {
        return center(s, i, ' ');
    }

    public static String center(String s, int i, char c)
    {
        if(s != null && i > 0)
        {
            int j = s.length();
            int k = i - j;
            if(k > 0)
                return rightPad(leftPad(s, j + k / 2, c), i, c);
        }
        return s;
    }

    public static String center(String s, int i, String s1)
    {
        if(s != null && i > 0)
        {
            if(isEmpty(s1))
                s1 = " ";
            int j = s.length();
            int k = i - j;
            if(k > 0)
                return rightPad(leftPad(s, j + k / 2, s1), i, s1);
        }
        return s;
    }

    public static String chomp(String s)
    {
        if(!isEmpty(s)) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        char c1;
        if(s.length() != 1)
            break; /* Loop/switch isn't completed */
        if((c1 = s.charAt(0)) == '\r' || c1 == '\n')
            return "";
        if(true) goto _L1; else goto _L3
_L3:
        int i;
        char c;
        i = -1 + s.length();
        c = s.charAt(i);
        if(c != '\n') goto _L5; else goto _L4
_L4:
        if(s.charAt(i - 1) == '\r')
            i--;
_L7:
        return s.substring(0, i);
_L5:
        if(c != '\r')
            i++;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static String chomp(String s, String s1)
    {
        return removeEnd(s, s1);
    }

    public static String chop(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            int i = s.length();
            if(i < 2)
                return "";
            int j = i - 1;
            s1 = s.substring(0, j);
            if(s.charAt(j) == '\n' && s1.charAt(j - 1) == '\r')
                return s1.substring(0, j - 1);
        }
        return s1;
    }

    public static boolean contains(CharSequence charsequence, int i)
    {
        while(isEmpty(charsequence) || CharSequenceUtils.indexOf(charsequence, i, 0) < 0) 
            return false;
        return true;
    }

    public static boolean contains(CharSequence charsequence, CharSequence charsequence1)
    {
        while(charsequence == null || charsequence1 == null || CharSequenceUtils.indexOf(charsequence, charsequence1, 0) < 0) 
            return false;
        return true;
    }

    public static boolean containsAny(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence1 == null)
            return false;
        else
            return containsAny(charsequence, CharSequenceUtils.toCharArray(charsequence1));
    }

    public static transient boolean containsAny(CharSequence charsequence, char ac[])
    {
        boolean flag;
        flag = true;
        if(!isEmpty(charsequence) && !ArrayUtils.isEmpty(ac))
            break MISSING_BLOCK_LABEL_20;
        flag = false;
        return flag;
        int i;
        int j;
        int k;
        int l;
        int i1;
        char c;
        int j1;
        i = charsequence.length();
        j = ac.length;
        k = i - 1;
        l = j - 1;
        i1 = 0;
          goto _L1
        continue; /* Loop/switch isn't completed */
        i1++;
    }

    public static boolean containsIgnoreCase(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence != null && charsequence1 != null)
        {
            int i = charsequence1.length();
            int j = charsequence.length() - i;
            int k = 0;
            while(k <= j) 
            {
                if(CharSequenceUtils.regionMatches(charsequence, true, k, charsequence1, 0, i))
                    return true;
                k++;
            }
        }
        return false;
    }

    public static boolean containsNone(CharSequence charsequence, String s)
    {
        if(charsequence == null || s == null)
            return true;
        else
            return containsNone(charsequence, s.toCharArray());
    }

    public static transient boolean containsNone(CharSequence charsequence, char ac[])
    {
        if(charsequence != null && ac != null) goto _L2; else goto _L1
_L1:
        boolean flag = true;
_L6:
        return flag;
_L2:
        int i;
        int j;
        int k;
        int l;
        int i1;
        i = charsequence.length();
        j = i - 1;
        k = ac.length;
        l = k - 1;
        i1 = 0;
_L10:
        char c;
        int j1;
        if(i1 >= i)
            break MISSING_BLOCK_LABEL_143;
        c = charsequence.charAt(i1);
        j1 = 0;
_L9:
        if(j1 >= k) goto _L4; else goto _L3
_L3:
        boolean flag1;
        if(ac[j1] != c)
            continue; /* Loop/switch isn't completed */
        flag1 = Character.isHighSurrogate(c);
        flag = false;
        if(!flag1) goto _L6; else goto _L5
_L5:
        flag = false;
        if(j1 == l) goto _L6; else goto _L7
_L7:
        char c1;
        char c2;
        if(i1 >= j)
            continue; /* Loop/switch isn't completed */
        c1 = ac[j1 + 1];
        c2 = charsequence.charAt(i1 + 1);
        flag = false;
        if(c1 == c2) goto _L6; else goto _L8
_L8:
        j1++;
          goto _L9
_L4:
        i1++;
          goto _L10
        return true;
    }

    public static boolean containsOnly(CharSequence charsequence, String s)
    {
        if(charsequence == null || s == null)
            return false;
        else
            return containsOnly(charsequence, s.toCharArray());
    }

    public static transient boolean containsOnly(CharSequence charsequence, char ac[])
    {
        boolean flag = true;
        if(ac == null || charsequence == null)
            flag = false;
        else
        if(charsequence.length() != 0)
        {
            if(ac.length == 0)
                return false;
            if(indexOfAnyBut(charsequence, ac) != -1)
                return false;
        }
        return flag;
    }

    public static boolean containsWhitespace(CharSequence charsequence)
    {
        if(!isEmpty(charsequence))
        {
            int i = charsequence.length();
            int j = 0;
            while(j < i) 
            {
                if(Character.isWhitespace(charsequence.charAt(j)))
                    return true;
                j++;
            }
        }
        return false;
    }

    public static int countMatches(CharSequence charsequence, CharSequence charsequence1)
    {
        if(!isEmpty(charsequence) && !isEmpty(charsequence1)) goto _L2; else goto _L1
_L1:
        int i = 0;
_L4:
        return i;
_L2:
        i = 0;
        int j = 0;
        do
        {
            int k = CharSequenceUtils.indexOf(charsequence, charsequence1, j);
            if(k == -1)
                continue;
            i++;
            j = k + charsequence1.length();
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
    }

    public static CharSequence defaultIfBlank(CharSequence charsequence, CharSequence charsequence1)
    {
        if(isBlank(charsequence))
            return charsequence1;
        else
            return charsequence;
    }

    public static CharSequence defaultIfEmpty(CharSequence charsequence, CharSequence charsequence1)
    {
        if(isEmpty(charsequence))
            return charsequence1;
        else
            return charsequence;
    }

    public static String defaultString(String s)
    {
        if(s == null)
            s = "";
        return s;
    }

    public static String defaultString(String s, String s1)
    {
        if(s == null)
            return s1;
        else
            return s;
    }

    public static String deleteWhitespace(String s)
    {
        if(!isEmpty(s))
        {
            int i = s.length();
            char ac[] = new char[i];
            int j = 0;
            int k = 0;
            while(j < i) 
            {
                int l;
                if(!Character.isWhitespace(s.charAt(j)))
                {
                    l = k + 1;
                    ac[k] = s.charAt(j);
                } else
                {
                    l = k;
                }
                j++;
                k = l;
            }
            if(k != i)
                return new String(ac, 0, k);
        }
        return s;
    }

    public static String difference(String s, String s1)
    {
        if(s == null)
            return s1;
        if(s1 == null)
            return s;
        int i = indexOfDifference(s, s1);
        if(i == -1)
            return "";
        else
            return s1.substring(i);
    }

    public static boolean endsWith(CharSequence charsequence, CharSequence charsequence1)
    {
        return endsWith(charsequence, charsequence1, false);
    }

    private static boolean endsWith(CharSequence charsequence, CharSequence charsequence1, boolean flag)
    {
        boolean flag1;
        if(charsequence == null || charsequence1 == null)
        {
            flag1 = false;
            if(charsequence == null)
            {
                flag1 = false;
                if(charsequence1 == null)
                    flag1 = true;
            }
        } else
        {
            int i = charsequence1.length();
            int j = charsequence.length();
            flag1 = false;
            if(i <= j)
                return CharSequenceUtils.regionMatches(charsequence, flag, charsequence.length() - charsequence1.length(), charsequence1, 0, charsequence1.length());
        }
        return flag1;
    }

    public static transient boolean endsWithAny(CharSequence charsequence, CharSequence acharsequence[])
    {
        if(!isEmpty(charsequence) && !ArrayUtils.isEmpty(acharsequence))
        {
            int i = acharsequence.length;
            int j = 0;
            while(j < i) 
            {
                if(endsWith(charsequence, acharsequence[j]))
                    return true;
                j++;
            }
        }
        return false;
    }

    public static boolean endsWithIgnoreCase(CharSequence charsequence, CharSequence charsequence1)
    {
        return endsWith(charsequence, charsequence1, true);
    }

    public static boolean equals(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence == null)
            return charsequence1 == null;
        else
            return charsequence.equals(charsequence1);
    }

    public static boolean equalsIgnoreCase(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence == null || charsequence1 == null)
            return charsequence == charsequence1;
        else
            return CharSequenceUtils.regionMatches(charsequence, true, 0, charsequence1, 0, Math.max(charsequence.length(), charsequence1.length()));
    }

    public static transient String getCommonPrefix(String as[])
    {
        if(as == null || as.length == 0)
            return "";
        int i = indexOfDifference(as);
        if(i == -1)
            if(as[0] == null)
                return "";
            else
                return as[0];
        if(i == 0)
            return "";
        else
            return as[0].substring(0, i);
    }

    public static int getLevenshteinDistance(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence == null || charsequence1 == null)
            throw new IllegalArgumentException("Strings must not be null");
        int i = charsequence.length();
        int j = charsequence1.length();
        if(i == 0)
            return j;
        if(j == 0)
            return i;
        if(i > j)
        {
            CharSequence charsequence2 = charsequence;
            charsequence = charsequence1;
            charsequence1 = charsequence2;
            i = j;
            j = charsequence1.length();
        }
        int ai[] = new int[i + 1];
        int ai1[] = new int[i + 1];
        for(int k = 0; k <= i; k++)
            ai[k] = k;

        for(int l = 1; l <= j; l++)
        {
            char c = charsequence1.charAt(l - 1);
            ai1[0] = l;
            int i1 = 1;
            while(i1 <= i) 
            {
                int j1;
                if(charsequence.charAt(i1 - 1) == c)
                    j1 = 0;
                else
                    j1 = 1;
                ai1[i1] = Math.min(Math.min(1 + ai1[i1 - 1], 1 + ai[i1]), j1 + ai[i1 - 1]);
                i1++;
            }
            int ai2[] = ai;
            ai = ai1;
            ai1 = ai2;
        }

        return ai[i];
    }

    public static int getLevenshteinDistance(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        if(charsequence == null || charsequence1 == null)
            throw new IllegalArgumentException("Strings must not be null");
        if(i < 0)
            throw new IllegalArgumentException("Threshold must not be negative");
        int j = charsequence.length();
        int k = charsequence1.length();
        if(j == 0)
            if(k <= i)
                return k;
            else
                return -1;
        if(k == 0)
            if(j <= i)
                return j;
            else
                return -1;
        if(j > k)
        {
            CharSequence charsequence2 = charsequence;
            charsequence = charsequence1;
            charsequence1 = charsequence2;
            j = k;
            k = charsequence1.length();
        }
        int ai[] = new int[j + 1];
        int ai1[] = new int[j + 1];
        int l = 1 + Math.min(j, i);
        for(int i1 = 0; i1 < l; i1++)
            ai[i1] = i1;

        Arrays.fill(ai, l, ai.length, 0x7fffffff);
        Arrays.fill(ai1, 0x7fffffff);
        for(int j1 = 1; j1 <= k; j1++)
        {
            int k1 = j1 - 1;
            char c = charsequence1.charAt(k1);
            ai1[0] = j1;
            int l1 = Math.max(1, j1 - i);
            int i2 = Math.min(j, j1 + i);
            if(l1 > i2)
                return -1;
            if(l1 > 1)
                ai1[l1 - 1] = 0x7fffffff;
            int j2 = l1;
            while(j2 <= i2) 
            {
                if(charsequence.charAt(j2 - 1) == c)
                    ai1[j2] = ai[j2 - 1];
                else
                    ai1[j2] = 1 + Math.min(Math.min(ai1[j2 - 1], ai[j2]), ai[j2 - 1]);
                j2++;
            }
            int ai2[] = ai;
            ai = ai1;
            ai1 = ai2;
        }

        if(ai[j] <= i)
            return ai[j];
        else
            return -1;
    }

    public static int indexOf(CharSequence charsequence, int i)
    {
        if(isEmpty(charsequence))
            return -1;
        else
            return CharSequenceUtils.indexOf(charsequence, i, 0);
    }

    public static int indexOf(CharSequence charsequence, int i, int j)
    {
        if(isEmpty(charsequence))
            return -1;
        else
            return CharSequenceUtils.indexOf(charsequence, i, j);
    }

    public static int indexOf(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence == null || charsequence1 == null)
            return -1;
        else
            return CharSequenceUtils.indexOf(charsequence, charsequence1, 0);
    }

    public static int indexOf(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        if(charsequence == null || charsequence1 == null)
            return -1;
        else
            return CharSequenceUtils.indexOf(charsequence, charsequence1, i);
    }

    public static int indexOfAny(CharSequence charsequence, String s)
    {
        if(isEmpty(charsequence) || isEmpty(s))
            return -1;
        else
            return indexOfAny(charsequence, s.toCharArray());
    }

    public static transient int indexOfAny(CharSequence charsequence, char ac[])
    {
        int i;
        if(!isEmpty(charsequence) && !ArrayUtils.isEmpty(ac))
            break MISSING_BLOCK_LABEL_18;
        i = -1;
        return i;
        int j;
        int k;
        int l;
        int i1;
        char c;
        int j1;
        j = charsequence.length();
        k = j - 1;
        l = ac.length;
        i1 = l - 1;
        i = 0;
          goto _L1
        continue; /* Loop/switch isn't completed */
        i++;
    }

    public static transient int indexOfAny(CharSequence charsequence, CharSequence acharsequence[])
    {
        if(charsequence != null && acharsequence != null) goto _L2; else goto _L1
_L1:
        int i = -1;
_L8:
        return i;
_L2:
        int j;
        int k;
        j = acharsequence.length;
        i = 0x7fffffff;
        k = 0;
_L4:
        CharSequence charsequence1;
        if(k >= j)
            continue; /* Loop/switch isn't completed */
        charsequence1 = acharsequence[k];
        if(charsequence1 != null)
            break; /* Loop/switch isn't completed */
_L5:
        k++;
        if(true) goto _L4; else goto _L3
_L3:
        int l = CharSequenceUtils.indexOf(charsequence, charsequence1, 0);
        if(l != -1 && l < i)
            i = l;
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
        if(i != 0x7fffffff) goto _L8; else goto _L7
_L7:
        return -1;
    }

    public static int indexOfAnyBut(CharSequence charsequence, CharSequence charsequence1)
    {
        if(!isEmpty(charsequence) && !isEmpty(charsequence1)) goto _L2; else goto _L1
_L1:
        int i = -1;
_L6:
        return i;
_L2:
        int j;
        j = charsequence.length();
        i = 0;
_L7:
        boolean flag;
        if(i >= j)
            break MISSING_BLOCK_LABEL_114;
        char c = charsequence.charAt(i);
        char c1;
        if(CharSequenceUtils.indexOf(charsequence1, c, 0) >= 0)
            flag = true;
        else
            flag = false;
        if(i + 1 >= j || !Character.isHighSurrogate(c)) goto _L4; else goto _L3
_L3:
        c1 = charsequence.charAt(i + 1);
        if(flag && CharSequenceUtils.indexOf(charsequence1, c1, 0) < 0) goto _L6; else goto _L5
_L5:
        i++;
          goto _L7
_L4:
        if(flag) goto _L5; else goto _L8
_L8:
        return i;
        return -1;
    }

    public static transient int indexOfAnyBut(CharSequence charsequence, char ac[])
    {
        if(!isEmpty(charsequence) && !ArrayUtils.isEmpty(ac)) goto _L2; else goto _L1
_L1:
        int i = -1;
_L4:
        return i;
_L2:
        int j;
        int k;
        int l;
        int i1;
        j = charsequence.length();
        k = j - 1;
        l = ac.length;
        i1 = l - 1;
_L5:
        char c;
        int j1;
        for(i = 0; i >= j;)
            break MISSING_BLOCK_LABEL_126;

        c = charsequence.charAt(i);
        j1 = 0;
_L6:
        if(j1 >= l) goto _L4; else goto _L3
_L3:
label0:
        {
            if(ac[j1] != c || i < k && j1 < i1 && Character.isHighSurrogate(c) && ac[j1 + 1] != charsequence.charAt(i + 1))
                break label0;
            i++;
        }
          goto _L5
        j1++;
          goto _L6
        return -1;
          goto _L5
    }

    public static int indexOfDifference(CharSequence charsequence, CharSequence charsequence1)
    {
        int i;
        if(charsequence == charsequence1)
        {
            i = -1;
        } else
        {
label0:
            {
                if(charsequence == null || charsequence1 == null)
                    return 0;
                for(i = 0; i < charsequence.length() && i < charsequence1.length() && charsequence.charAt(i) == charsequence1.charAt(i); i++)
                    break label0;

                if(i >= charsequence1.length() && i >= charsequence.length())
                    return -1;
            }
        }
        return i;
    }

    public static transient int indexOfDifference(CharSequence acharsequence[])
    {
        if(acharsequence != null && acharsequence.length > 1) goto _L2; else goto _L1
_L1:
        int i = -1;
_L4:
        return i;
_L2:
        int j;
        int k;
        int i1;
        int j1;
        boolean flag = false;
        boolean flag1 = true;
        j = acharsequence.length;
        i = 0x7fffffff;
        k = 0;
        int l = 0;
        while(l < j) 
        {
            if(acharsequence[l] == null)
            {
                flag = true;
                i = 0;
            } else
            {
                i = Math.min(acharsequence[l].length(), i);
                k = Math.max(acharsequence[l].length(), k);
                flag1 = false;
            }
            l++;
        }
        if(flag1 || k == 0 && !flag)
            return -1;
        if(i == 0)
            return 0;
        i1 = -1;
        j1 = 0;
_L6:
        char c;
        int k1;
        if(j1 >= i)
            continue; /* Loop/switch isn't completed */
        c = acharsequence[0].charAt(j1);
        k1 = 1;
_L5:
        if(k1 < j)
        {
            if(acharsequence[k1].charAt(j1) == c)
                break MISSING_BLOCK_LABEL_186;
            i1 = j1;
        }
        if(i1 == -1)
            break MISSING_BLOCK_LABEL_192;
        if(i1 == -1 && i != k) goto _L4; else goto _L3
_L3:
        return i1;
        k1++;
          goto _L5
        j1++;
          goto _L6
    }

    public static int indexOfIgnoreCase(CharSequence charsequence, CharSequence charsequence1)
    {
        return indexOfIgnoreCase(charsequence, charsequence1, 0);
    }

    public static int indexOfIgnoreCase(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        if(charsequence != null && charsequence1 != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i < 0)
            i = 0;
        int k = 1 + (charsequence.length() - charsequence1.length());
        if(i > k)
            return -1;
        if(charsequence1.length() == 0)
            return i;
        j = i;
label0:
        do
        {
label1:
            {
                if(j >= k)
                    break label1;
                if(CharSequenceUtils.regionMatches(charsequence, true, j, charsequence1, 0, charsequence1.length()))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static boolean isAllLowerCase(CharSequence charsequence)
    {
        if(charsequence != null && !isEmpty(charsequence)) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isLowerCase(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isAllUpperCase(CharSequence charsequence)
    {
        if(charsequence != null && !isEmpty(charsequence)) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isUpperCase(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isAlpha(CharSequence charsequence)
    {
        if(charsequence != null && charsequence.length() != 0) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isLetter(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isAlphaSpace(CharSequence charsequence)
    {
        if(charsequence != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isLetter(charsequence.charAt(j)) && charsequence.charAt(j) != ' ')
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isAlphanumeric(CharSequence charsequence)
    {
        if(charsequence != null && charsequence.length() != 0) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isLetterOrDigit(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isAlphanumericSpace(CharSequence charsequence)
    {
        if(charsequence != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isLetterOrDigit(charsequence.charAt(j)) && charsequence.charAt(j) != ' ')
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isAsciiPrintable(CharSequence charsequence)
    {
        if(charsequence != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!CharUtils.isAsciiPrintable(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isBlank(CharSequence charsequence)
    {
        if(charsequence != null)
        {
            int i = charsequence.length();
            if(i != 0)
            {
                int j = 0;
                while(j < i) 
                {
                    if(!Character.isWhitespace(charsequence.charAt(j)))
                        return false;
                    j++;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(CharSequence charsequence)
    {
        return charsequence == null || charsequence.length() == 0;
    }

    public static boolean isNotBlank(CharSequence charsequence)
    {
        return !isBlank(charsequence);
    }

    public static boolean isNotEmpty(CharSequence charsequence)
    {
        return !isEmpty(charsequence);
    }

    public static boolean isNumeric(CharSequence charsequence)
    {
        if(charsequence != null && charsequence.length() != 0) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isDigit(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isNumericSpace(CharSequence charsequence)
    {
        if(charsequence != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isDigit(charsequence.charAt(j)) && charsequence.charAt(j) != ' ')
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static boolean isWhitespace(CharSequence charsequence)
    {
        if(charsequence != null) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        int i = charsequence.length();
        int j = 0;
label0:
        do
        {
label1:
            {
                if(j >= i)
                    break label1;
                if(!Character.isWhitespace(charsequence.charAt(j)))
                    break label0;
                j++;
            }
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
        return true;
    }

    public static String join(Iterable iterable, char c)
    {
        if(iterable == null)
            return null;
        else
            return join(iterable.iterator(), c);
    }

    public static String join(Iterable iterable, String s)
    {
        if(iterable == null)
            return null;
        else
            return join(iterable.iterator(), s);
    }

    public static String join(Iterator iterator, char c)
    {
        if(iterator == null)
            return null;
        if(!iterator.hasNext())
            return "";
        Object obj = iterator.next();
        if(!iterator.hasNext())
            return ObjectUtils.toString(obj);
        StringBuilder stringbuilder = new StringBuilder(256);
        if(obj != null)
            stringbuilder.append(obj);
        do
        {
            if(!iterator.hasNext())
                break;
            stringbuilder.append(c);
            Object obj1 = iterator.next();
            if(obj1 != null)
                stringbuilder.append(obj1);
        } while(true);
        return stringbuilder.toString();
    }

    public static String join(Iterator iterator, String s)
    {
        if(iterator == null)
            return null;
        if(!iterator.hasNext())
            return "";
        Object obj = iterator.next();
        if(!iterator.hasNext())
            return ObjectUtils.toString(obj);
        StringBuilder stringbuilder = new StringBuilder(256);
        if(obj != null)
            stringbuilder.append(obj);
        do
        {
            if(!iterator.hasNext())
                break;
            if(s != null)
                stringbuilder.append(s);
            Object obj1 = iterator.next();
            if(obj1 != null)
                stringbuilder.append(obj1);
        } while(true);
        return stringbuilder.toString();
    }

    public static transient String join(Object aobj[])
    {
        return join(aobj, ((String) (null)));
    }

    public static String join(Object aobj[], char c)
    {
        if(aobj == null)
            return null;
        else
            return join(aobj, c, 0, aobj.length);
    }

    public static String join(Object aobj[], char c, int i, int j)
    {
        if(aobj == null)
            return null;
        int k = j - i;
        if(k <= 0)
            return "";
        StringBuilder stringbuilder = new StringBuilder(k * 16);
        for(int l = i; l < j; l++)
        {
            if(l > i)
                stringbuilder.append(c);
            if(aobj[l] != null)
                stringbuilder.append(aobj[l]);
        }

        return stringbuilder.toString();
    }

    public static String join(Object aobj[], String s)
    {
        if(aobj == null)
            return null;
        else
            return join(aobj, s, 0, aobj.length);
    }

    public static String join(Object aobj[], String s, int i, int j)
    {
        if(aobj == null)
            return null;
        if(s == null)
            s = "";
        int k = j - i;
        if(k <= 0)
            return "";
        StringBuilder stringbuilder = new StringBuilder(k * 16);
        for(int l = i; l < j; l++)
        {
            if(l > i)
                stringbuilder.append(s);
            if(aobj[l] != null)
                stringbuilder.append(aobj[l]);
        }

        return stringbuilder.toString();
    }

    public static int lastIndexOf(CharSequence charsequence, int i)
    {
        if(isEmpty(charsequence))
            return -1;
        else
            return CharSequenceUtils.lastIndexOf(charsequence, i, charsequence.length());
    }

    public static int lastIndexOf(CharSequence charsequence, int i, int j)
    {
        if(isEmpty(charsequence))
            return -1;
        else
            return CharSequenceUtils.lastIndexOf(charsequence, i, j);
    }

    public static int lastIndexOf(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence == null || charsequence1 == null)
            return -1;
        else
            return CharSequenceUtils.lastIndexOf(charsequence, charsequence1, charsequence.length());
    }

    public static int lastIndexOf(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        if(charsequence == null || charsequence1 == null)
            return -1;
        else
            return CharSequenceUtils.lastIndexOf(charsequence, charsequence1, i);
    }

    public static transient int lastIndexOfAny(CharSequence charsequence, CharSequence acharsequence[])
    {
        int i;
        if(charsequence == null || acharsequence == null)
        {
            i = -1;
        } else
        {
            int j = acharsequence.length;
            i = -1;
            int k = 0;
            while(k < j) 
            {
                CharSequence charsequence1 = acharsequence[k];
                if(charsequence1 != null)
                {
                    int l = CharSequenceUtils.lastIndexOf(charsequence, charsequence1, charsequence.length());
                    if(l > i)
                        i = l;
                }
                k++;
            }
        }
        return i;
    }

    public static int lastIndexOfIgnoreCase(CharSequence charsequence, CharSequence charsequence1)
    {
        if(charsequence == null || charsequence1 == null)
            return -1;
        else
            return lastIndexOfIgnoreCase(charsequence, charsequence1, charsequence.length());
    }

    public static int lastIndexOfIgnoreCase(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        if(charsequence != null && charsequence1 != null) goto _L2; else goto _L1
_L1:
        int j = -1;
_L4:
        return j;
_L2:
        if(i > charsequence.length() - charsequence1.length())
            i = charsequence.length() - charsequence1.length();
        if(i < 0)
            return -1;
        if(charsequence1.length() == 0)
            return i;
        j = i;
label0:
        do
        {
label1:
            {
                if(j < 0)
                    break label1;
                if(CharSequenceUtils.regionMatches(charsequence, true, j, charsequence1, 0, charsequence1.length()))
                    break label0;
                j--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    public static int lastOrdinalIndexOf(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        return ordinalIndexOf(charsequence, charsequence1, i, true);
    }

    public static String left(String s, int i)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            if(i < 0)
                return "";
            if(s.length() > i)
                return s.substring(0, i);
        }
        return s;
    }

    public static String leftPad(String s, int i)
    {
        return leftPad(s, i, ' ');
    }

    public static String leftPad(String s, int i, char c)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            int j = i - s.length();
            if(j > 0)
                if(j > 8192)
                    return leftPad(s, i, String.valueOf(c));
                else
                    return repeat(c, j).concat(s);
        }
        return s;
    }

    public static String leftPad(String s, int i, String s1)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            if(isEmpty(s1))
                s1 = " ";
            int j = s1.length();
            int k = i - s.length();
            if(k > 0)
            {
                if(j == 1 && k <= 8192)
                    return leftPad(s, i, s1.charAt(0));
                if(k == j)
                    return s1.concat(s);
                if(k < j)
                    return s1.substring(0, k).concat(s);
                char ac[] = new char[k];
                char ac1[] = s1.toCharArray();
                for(int l = 0; l < k; l++)
                    ac[l] = ac1[l % j];

                return (new String(ac)).concat(s);
            }
        }
        return s;
    }

    public static int length(CharSequence charsequence)
    {
        if(charsequence == null)
            return 0;
        else
            return charsequence.length();
    }

    public static String lowerCase(String s)
    {
        if(s == null)
            return null;
        else
            return s.toLowerCase();
    }

    public static String lowerCase(String s, Locale locale)
    {
        if(s == null)
            return null;
        else
            return s.toLowerCase(locale);
    }

    public static String mid(String s, int i, int j)
    {
        if(s == null)
            return null;
        if(j < 0 || i > s.length())
            return "";
        if(i < 0)
            i = 0;
        if(s.length() <= i + j)
            return s.substring(i);
        else
            return s.substring(i, i + j);
    }

    public static String normalizeSpace(String s)
    {
        if(s == null)
            return null;
        else
            return WHITESPACE_BLOCK.matcher(trim(s)).replaceAll(" ");
    }

    public static int ordinalIndexOf(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        return ordinalIndexOf(charsequence, charsequence1, i, false);
    }

    private static int ordinalIndexOf(CharSequence charsequence, CharSequence charsequence1, int i, boolean flag)
    {
        int j = -1;
        if(charsequence != null && charsequence1 != null && i > 0) goto _L2; else goto _L1
_L1:
        return j;
_L2:
        int k;
        if(charsequence1.length() == 0)
        {
            int l;
            if(flag)
                l = charsequence.length();
            else
                l = 0;
            return l;
        }
        k = 0;
        if(flag)
            j = charsequence.length();
_L4:
        if(flag)
            j = CharSequenceUtils.lastIndexOf(charsequence, charsequence1, j - 1);
        else
            j = CharSequenceUtils.indexOf(charsequence, charsequence1, j + 1);
        if(j < 0) goto _L1; else goto _L3
_L3:
        if(++k >= i)
            return j;
          goto _L4
    }

    public static String overlay(String s, String s1, int i, int j)
    {
        if(s == null)
            return null;
        if(s1 == null)
            s1 = "";
        int k = s.length();
        if(i < 0)
            i = 0;
        if(i > k)
            i = k;
        if(j < 0)
            j = 0;
        if(j > k)
            j = k;
        if(i > j)
        {
            int l = i;
            i = j;
            j = l;
        }
        return (new StringBuilder(1 + (((k + i) - j) + s1.length()))).append(s.substring(0, i)).append(s1).append(s.substring(j)).toString();
    }

    public static String remove(String s, char c)
    {
        if(isEmpty(s) || s.indexOf(c) == -1)
            return s;
        char ac[] = s.toCharArray();
        int i = 0;
        for(int j = 0; j < ac.length; j++)
            if(ac[j] != c)
            {
                int k = i + 1;
                ac[i] = ac[j];
                i = k;
            }

        return new String(ac, 0, i);
    }

    public static String remove(String s, String s1)
    {
        if(isEmpty(s) || isEmpty(s1))
            return s;
        else
            return replace(s, s1, "", -1);
    }

    private static String removeAccentsJava6(CharSequence charsequence)
        throws IllegalAccessException, InvocationTargetException
    {
        if(InitStripAccents.java6NormalizeMethod == null || InitStripAccents.java6NormalizerFormNFD == null)
        {
            throw new IllegalStateException("java.text.Normalizer is not available", InitStripAccents.java6Exception);
        } else
        {
            Method method = InitStripAccents.java6NormalizeMethod;
            Object aobj[] = new Object[2];
            aobj[0] = charsequence;
            aobj[1] = InitStripAccents.java6NormalizerFormNFD;
            String s = (String)method.invoke(null, aobj);
            return InitStripAccents.java6Pattern.matcher(s).replaceAll("");
        }
    }

    private static String removeAccentsSUN(CharSequence charsequence)
        throws IllegalAccessException, InvocationTargetException
    {
        if(InitStripAccents.sunDecomposeMethod == null)
        {
            throw new IllegalStateException("sun.text.Normalizer is not available", InitStripAccents.sunException);
        } else
        {
            Method method = InitStripAccents.sunDecomposeMethod;
            Object aobj[] = new Object[3];
            aobj[0] = charsequence;
            aobj[1] = Boolean.FALSE;
            aobj[2] = Integer.valueOf(0);
            String s = (String)method.invoke(null, aobj);
            return InitStripAccents.sunPattern.matcher(s).replaceAll("");
        }
    }

    public static String removeEnd(String s, String s1)
    {
        while(isEmpty(s) || isEmpty(s1) || !s.endsWith(s1)) 
            return s;
        return s.substring(0, s.length() - s1.length());
    }

    public static String removeEndIgnoreCase(String s, String s1)
    {
        while(isEmpty(s) || isEmpty(s1) || !endsWithIgnoreCase(s, s1)) 
            return s;
        return s.substring(0, s.length() - s1.length());
    }

    public static String removeStart(String s, String s1)
    {
        while(isEmpty(s) || isEmpty(s1) || !s.startsWith(s1)) 
            return s;
        return s.substring(s1.length());
    }

    public static String removeStartIgnoreCase(String s, String s1)
    {
        while(isEmpty(s) || isEmpty(s1) || !startsWithIgnoreCase(s, s1)) 
            return s;
        return s.substring(s1.length());
    }

    public static String repeat(char c, int i)
    {
        char ac[] = new char[i];
        for(int j = i - 1; j >= 0; j--)
            ac[j] = c;

        return new String(ac);
    }

    public static String repeat(String s, int i)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            if(i <= 0)
                return "";
            int j = s.length();
            if(i != 1 && j != 0)
            {
                if(j == 1 && i <= 8192)
                    return repeat(s.charAt(0), i);
                int k = j * i;
                StringBuilder stringbuilder;
                switch(j)
                {
                default:
                    stringbuilder = new StringBuilder(k);
                    for(int i1 = 0; i1 < i; i1++)
                        stringbuilder.append(s);

                    break;

                case 1: // '\001'
                    return repeat(s.charAt(0), i);

                case 2: // '\002'
                    char c = s.charAt(0);
                    char c1 = s.charAt(1);
                    char ac[] = new char[k];
                    for(int l = -2 + i * 2; l >= 0; l = -1 + (l - 1))
                    {
                        ac[l] = c;
                        ac[l + 1] = c1;
                    }

                    return new String(ac);
                }
                return stringbuilder.toString();
            }
        }
        return s;
    }

    public static String repeat(String s, String s1, int i)
    {
        if(s == null || s1 == null)
            return repeat(s, i);
        else
            return removeEnd(repeat((new StringBuilder()).append(s).append(s1).toString(), i), s1);
    }

    public static String replace(String s, String s1, String s2)
    {
        return replace(s, s1, s2, -1);
    }

    public static String replace(String s, String s1, String s2, int i)
    {
        int j = 64;
        if(!isEmpty(s) && !isEmpty(s1) && s2 != null && i != 0)
        {
            int k = 0;
            int l = s.indexOf(s1, 0);
            if(l != -1)
            {
                int i1 = s1.length();
                int j1 = s2.length() - i1;
                if(j1 < 0)
                    j1 = 0;
                StringBuilder stringbuilder;
                if(i < 0)
                    j = 16;
                else
                if(i <= j)
                    j = i;
                stringbuilder = new StringBuilder(j1 * j + s.length());
                do
                {
label0:
                    {
                        if(l != -1)
                        {
                            stringbuilder.append(s.substring(k, l)).append(s2);
                            k = l + i1;
                            if(--i != 0)
                                break label0;
                        }
                        stringbuilder.append(s.substring(k));
                        return stringbuilder.toString();
                    }
                    l = s.indexOf(s1, k);
                } while(true);
            }
        }
        return s;
    }

    public static String replaceChars(String s, char c, char c1)
    {
        if(s == null)
            return null;
        else
            return s.replace(c, c1);
    }

    public static String replaceChars(String s, String s1, String s2)
    {
        if(!isEmpty(s) && !isEmpty(s1))
        {
            if(s2 == null)
                s2 = "";
            boolean flag = false;
            int i = s2.length();
            int j = s.length();
            StringBuilder stringbuilder = new StringBuilder(j);
            int k = 0;
            while(k < j) 
            {
                char c = s.charAt(k);
                int l = s1.indexOf(c);
                if(l >= 0)
                {
                    flag = true;
                    if(l < i)
                        stringbuilder.append(s2.charAt(l));
                } else
                {
                    stringbuilder.append(c);
                }
                k++;
            }
            if(flag)
                return stringbuilder.toString();
        }
        return s;
    }

    public static String replaceEach(String s, String as[], String as1[])
    {
        return replaceEach(s, as, as1, false, 0);
    }

    private static String replaceEach(String s, String as[], String as1[], boolean flag, int i)
    {
        if(s != null && s.length() != 0 && as != null && as.length != 0 && as1 != null && as1.length != 0) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        int j;
        boolean aflag[];
        int l;
        int i1;
        int j1;
        if(i < 0)
            throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
        j = as.length;
        int k = as1.length;
        if(j != k)
            throw new IllegalArgumentException((new StringBuilder()).append("Search and Replace array lengths don't match: ").append(j).append(" vs ").append(k).toString());
        aflag = new boolean[j];
        l = -1;
        i1 = -1;
        j1 = 0;
_L4:
        if(j1 >= j)
            continue; /* Loop/switch isn't completed */
        if(!aflag[j1] && as[j1] != null && as[j1].length() != 0 && as1[j1] != null)
            break; /* Loop/switch isn't completed */
_L5:
        j1++;
        if(true) goto _L4; else goto _L3
_L3:
        int l3 = s.indexOf(as[j1]);
        if(l3 == -1)
            aflag[j1] = true;
        else
        if(l == -1 || l3 < l)
        {
            l = l3;
            i1 = j1;
        }
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
        if(l == -1) goto _L1; else goto _L7
_L7:
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        while(i2 < as.length) 
        {
            if(as[i2] != null && as1[i2] != null)
            {
                int k3 = as1[i2].length() - as[i2].length();
                if(k3 > 0)
                    l1 += k3 * 3;
            }
            i2++;
        }
        StringBuilder stringbuilder = new StringBuilder(Math.min(l1, s.length() / 5) + s.length());
        while(l != -1) 
        {
            for(int l2 = k1; l2 < l; l2++)
                stringbuilder.append(s.charAt(l2));

            stringbuilder.append(as1[i1]);
            k1 = l + as[i1].length();
            l = -1;
            i1 = -1;
            int i3 = 0;
            while(i3 < j) 
            {
                if(!aflag[i3] && as[i3] != null && as[i3].length() != 0 && as1[i3] != null)
                {
                    int j3 = s.indexOf(as[i3], k1);
                    if(j3 == -1)
                        aflag[i3] = true;
                    else
                    if(l == -1 || j3 < l)
                    {
                        l = j3;
                        i1 = i3;
                    }
                }
                i3++;
            }
        }
        int j2 = s.length();
        for(int k2 = k1; k2 < j2; k2++)
            stringbuilder.append(s.charAt(k2));

        String s1 = stringbuilder.toString();
        if(!flag)
            return s1;
        else
            return replaceEach(s1, as, as1, flag, i - 1);
    }

    public static String replaceEachRepeatedly(String s, String as[], String as1[])
    {
        int i;
        if(as == null)
            i = 0;
        else
            i = as.length;
        return replaceEach(s, as, as1, true, i);
    }

    public static String replaceOnce(String s, String s1, String s2)
    {
        return replace(s, s1, s2, 1);
    }

    public static String reverse(String s)
    {
        if(s == null)
            return null;
        else
            return (new StringBuilder(s)).reverse().toString();
    }

    public static String reverseDelimited(String s, char c)
    {
        if(s == null)
        {
            return null;
        } else
        {
            String as[] = split(s, c);
            ArrayUtils.reverse(as);
            return join(as, c);
        }
    }

    public static String right(String s, int i)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            if(i < 0)
                return "";
            if(s.length() > i)
                return s.substring(s.length() - i);
        }
        return s;
    }

    public static String rightPad(String s, int i)
    {
        return rightPad(s, i, ' ');
    }

    public static String rightPad(String s, int i, char c)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            int j = i - s.length();
            if(j > 0)
                if(j > 8192)
                    return rightPad(s, i, String.valueOf(c));
                else
                    return s.concat(repeat(c, j));
        }
        return s;
    }

    public static String rightPad(String s, int i, String s1)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            if(isEmpty(s1))
                s1 = " ";
            int j = s1.length();
            int k = i - s.length();
            if(k > 0)
            {
                if(j == 1 && k <= 8192)
                    return rightPad(s, i, s1.charAt(0));
                if(k == j)
                    return s.concat(s1);
                if(k < j)
                    return s.concat(s1.substring(0, k));
                char ac[] = new char[k];
                char ac1[] = s1.toCharArray();
                for(int l = 0; l < k; l++)
                    ac[l] = ac1[l % j];

                return s.concat(new String(ac));
            }
        }
        return s;
    }

    public static String[] split(String s)
    {
        return split(s, null, -1);
    }

    public static String[] split(String s, char c)
    {
        return splitWorker(s, c, false);
    }

    public static String[] split(String s, String s1)
    {
        return splitWorker(s, s1, -1, false);
    }

    public static String[] split(String s, String s1, int i)
    {
        return splitWorker(s, s1, i, false);
    }

    public static String[] splitByCharacterType(String s)
    {
        return splitByCharacterType(s, false);
    }

    private static String[] splitByCharacterType(String s, boolean flag)
    {
        if(s == null)
            return null;
        if(s.length() == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        char ac[] = s.toCharArray();
        ArrayList arraylist = new ArrayList();
        int i = 0;
        int j = Character.getType(ac[0]);
        int k = 0 + 1;
        while(k < ac.length) 
        {
            int l = Character.getType(ac[k]);
            if(l != j)
            {
                if(flag && l == 2 && j == 1)
                {
                    int i1 = k - 1;
                    if(i1 != i)
                    {
                        arraylist.add(new String(ac, i, i1 - i));
                        i = i1;
                    }
                } else
                {
                    arraylist.add(new String(ac, i, k - i));
                    i = k;
                }
                j = l;
            }
            k++;
        }
        arraylist.add(new String(ac, i, ac.length - i));
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public static String[] splitByCharacterTypeCamelCase(String s)
    {
        return splitByCharacterType(s, true);
    }

    public static String[] splitByWholeSeparator(String s, String s1)
    {
        return splitByWholeSeparatorWorker(s, s1, -1, false);
    }

    public static String[] splitByWholeSeparator(String s, String s1, int i)
    {
        return splitByWholeSeparatorWorker(s, s1, i, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String s, String s1)
    {
        return splitByWholeSeparatorWorker(s, s1, -1, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String s, String s1, int i)
    {
        return splitByWholeSeparatorWorker(s, s1, i, true);
    }

    private static String[] splitByWholeSeparatorWorker(String s, String s1, int i, boolean flag)
    {
        if(s == null)
            return null;
        int j = s.length();
        if(j == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        if(s1 == null || "".equals(s1))
            return splitWorker(s, null, i, flag);
        int k = s1.length();
        ArrayList arraylist = new ArrayList();
        int l = 0;
        int i1 = 0;
        for(int j1 = 0; j1 < j;)
        {
            j1 = s.indexOf(s1, i1);
            if(j1 > -1)
            {
                if(j1 > i1)
                {
                    if(++l == i)
                    {
                        j1 = j;
                        arraylist.add(s.substring(i1));
                    } else
                    {
                        arraylist.add(s.substring(i1, j1));
                        i1 = j1 + k;
                    }
                } else
                {
                    if(flag)
                        if(++l == i)
                        {
                            j1 = j;
                            arraylist.add(s.substring(i1));
                        } else
                        {
                            arraylist.add("");
                        }
                    i1 = j1 + k;
                }
            } else
            {
                arraylist.add(s.substring(i1));
                j1 = j;
            }
        }

        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public static String[] splitPreserveAllTokens(String s)
    {
        return splitWorker(s, null, -1, true);
    }

    public static String[] splitPreserveAllTokens(String s, char c)
    {
        return splitWorker(s, c, true);
    }

    public static String[] splitPreserveAllTokens(String s, String s1)
    {
        return splitWorker(s, s1, -1, true);
    }

    public static String[] splitPreserveAllTokens(String s, String s1, int i)
    {
        return splitWorker(s, s1, i, true);
    }

    private static String[] splitWorker(String s, char c, boolean flag)
    {
        if(s == null)
            return null;
        int i = s.length();
        if(i == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        ArrayList arraylist = new ArrayList();
        int j = 0;
        int k = 0;
        boolean flag1 = false;
        boolean flag2 = false;
        while(j < i) 
            if(s.charAt(j) == c)
            {
                if(flag1 || flag)
                {
                    arraylist.add(s.substring(k, j));
                    flag1 = false;
                    flag2 = true;
                }
                k = ++j;
            } else
            {
                flag1 = true;
                j++;
                flag2 = false;
            }
        if(flag1 || flag && flag2)
            arraylist.add(s.substring(k, j));
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    private static String[] splitWorker(String s, String s1, int i, boolean flag)
    {
        int j;
        ArrayList arraylist;
        int k;
        int l;
        boolean flag1;
        boolean flag2;
        if(s == null)
            return null;
        j = s.length();
        if(j == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        arraylist = new ArrayList();
        k = 0;
        l = 0;
        flag1 = false;
        flag2 = false;
        if(s1 != null) goto _L2; else goto _L1
_L1:
        int i1 = 1;
        while(k < j) 
            if(Character.isWhitespace(s.charAt(k)))
            {
                int j1;
                char c;
                int k1;
                int l1;
                int i2;
                if(flag1 || flag)
                {
                    flag2 = true;
                    i2 = i1 + 1;
                    if(i1 == i)
                    {
                        k = j;
                        flag2 = false;
                    }
                    arraylist.add(s.substring(l, k));
                    flag1 = false;
                } else
                {
                    i2 = i1;
                }
                l = ++k;
                i1 = i2;
            } else
            {
                flag1 = true;
                k++;
                flag2 = false;
            }
          goto _L3
_L2:
        if(s1.length() != 1)
            break MISSING_BLOCK_LABEL_426;
        c = s1.charAt(0);
        k1 = 1;
        while(k < j) 
            if(s.charAt(k) == c)
            {
                if(flag1 || flag)
                {
                    flag2 = true;
                    l1 = k1 + 1;
                    if(k1 == i)
                    {
                        k = j;
                        flag2 = false;
                    }
                    arraylist.add(s.substring(l, k));
                    flag1 = false;
                } else
                {
                    l1 = k1;
                }
                l = ++k;
                k1 = l1;
            } else
            {
                flag1 = true;
                k++;
                flag2 = false;
            }
        k1;
        if(flag1 || flag && flag2)
            arraylist.add(s.substring(l, k));
        return (String[])arraylist.toArray(new String[arraylist.size()]);
_L5:
        while(k < j) 
            if(s1.indexOf(s.charAt(k)) >= 0)
            {
                if(flag1 || flag)
                {
                    flag2 = true;
                    j1 = i1 + 1;
                    if(i1 == i)
                    {
                        k = j;
                        flag2 = false;
                    }
                    arraylist.add(s.substring(l, k));
                    flag1 = false;
                } else
                {
                    j1 = i1;
                }
                l = ++k;
                i1 = j1;
            } else
            {
                flag1 = true;
                k++;
                flag2 = false;
            }
_L3:
        i1;
        break MISSING_BLOCK_LABEL_259;
        i1 = 1;
        k = 0;
        flag2 = false;
        flag1 = false;
        l = 0;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static boolean startsWith(CharSequence charsequence, CharSequence charsequence1)
    {
        return startsWith(charsequence, charsequence1, false);
    }

    private static boolean startsWith(CharSequence charsequence, CharSequence charsequence1, boolean flag)
    {
        boolean flag1;
        if(charsequence == null || charsequence1 == null)
        {
            flag1 = false;
            if(charsequence == null)
            {
                flag1 = false;
                if(charsequence1 == null)
                    flag1 = true;
            }
        } else
        {
            int i = charsequence1.length();
            int j = charsequence.length();
            flag1 = false;
            if(i <= j)
                return CharSequenceUtils.regionMatches(charsequence, flag, 0, charsequence1, 0, charsequence1.length());
        }
        return flag1;
    }

    public static transient boolean startsWithAny(CharSequence charsequence, CharSequence acharsequence[])
    {
        if(!isEmpty(charsequence) && !ArrayUtils.isEmpty(acharsequence))
        {
            int i = acharsequence.length;
            int j = 0;
            while(j < i) 
            {
                if(startsWith(charsequence, acharsequence[j]))
                    return true;
                j++;
            }
        }
        return false;
    }

    public static boolean startsWithIgnoreCase(CharSequence charsequence, CharSequence charsequence1)
    {
        return startsWith(charsequence, charsequence1, true);
    }

    public static String strip(String s)
    {
        return strip(s, null);
    }

    public static String strip(String s, String s1)
    {
        if(isEmpty(s))
            return s;
        else
            return stripEnd(stripStart(s, s1), s1);
    }

    public static String stripAccents(String s)
    {
        if(s == null)
            return null;
        try
        {
            if(InitStripAccents.java6NormalizeMethod != null)
                return removeAccentsJava6(s);
            if(InitStripAccents.sunDecomposeMethod != null)
                return removeAccentsSUN(s);
            else
                throw new UnsupportedOperationException((new StringBuilder()).append("The stripAccents(CharSequence) method requires at least Java6, but got: ").append(InitStripAccents.java6Exception).append("; or a Sun JVM: ").append(InitStripAccents.sunException).toString());
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            throw new RuntimeException("IllegalArgumentException occurred", illegalargumentexception);
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new RuntimeException("IllegalAccessException occurred", illegalaccessexception);
        }
        catch(InvocationTargetException invocationtargetexception)
        {
            throw new RuntimeException("InvocationTargetException occurred", invocationtargetexception);
        }
        catch(SecurityException securityexception)
        {
            throw new RuntimeException("SecurityException occurred", securityexception);
        }
    }

    public static transient String[] stripAll(String as[])
    {
        return stripAll(as, null);
    }

    public static String[] stripAll(String as[], String s)
    {
        if(as == null) goto _L2; else goto _L1
_L1:
        int i = as.length;
        if(i != 0) goto _L3; else goto _L2
_L2:
        String as1[] = as;
_L5:
        return as1;
_L3:
        as1 = new String[i];
        int j = 0;
        while(j < i) 
        {
            as1[j] = strip(as[j], s);
            j++;
        }
        if(true) goto _L5; else goto _L4
_L4:
    }

    public static String stripEnd(String s, String s1)
    {
        if(s == null) goto _L2; else goto _L1
_L1:
        int i = s.length();
        if(i != 0) goto _L3; else goto _L2
_L2:
        return s;
_L3:
        if(s1 == null)
        {
            for(; i != 0 && Character.isWhitespace(s.charAt(i - 1)); i--);
            break; /* Loop/switch isn't completed */
        }
        if(s1.length() == 0)
            continue; /* Loop/switch isn't completed */
        for(; i != 0 && s1.indexOf(s.charAt(i - 1)) != -1; i--);
        break; /* Loop/switch isn't completed */
        if(true) goto _L2; else goto _L4
_L4:
        return s.substring(0, i);
    }

    public static String stripStart(String s, String s1)
    {
        if(s == null) goto _L2; else goto _L1
_L1:
        int i = s.length();
        if(i != 0) goto _L3; else goto _L2
_L2:
        return s;
_L3:
        int j;
        j = 0;
        if(s1 == null)
        {
            for(; j != i && Character.isWhitespace(s.charAt(j)); j++);
            break; /* Loop/switch isn't completed */
        }
        int k = s1.length();
        j = 0;
        if(k == 0)
            continue; /* Loop/switch isn't completed */
        for(; j != i && s1.indexOf(s.charAt(j)) != -1; j++);
        break; /* Loop/switch isn't completed */
        if(true) goto _L2; else goto _L4
_L4:
        return s.substring(j);
    }

    public static String stripToEmpty(String s)
    {
        if(s == null)
            return "";
        else
            return strip(s, null);
    }

    public static String stripToNull(String s)
    {
        if(s == null)
            return null;
        String s1 = strip(s, null);
        if(s1.length() == 0)
            s1 = null;
        return s1;
    }

    public static String substring(String s, int i)
    {
        if(s == null)
            return null;
        if(i < 0)
            i += s.length();
        if(i < 0)
            i = 0;
        if(i > s.length())
            return "";
        else
            return s.substring(i);
    }

    public static String substring(String s, int i, int j)
    {
        if(s == null)
            return null;
        if(j < 0)
            j += s.length();
        if(i < 0)
            i += s.length();
        if(j > s.length())
            j = s.length();
        if(i > j)
            return "";
        if(i < 0)
            i = 0;
        if(j < 0)
            j = 0;
        return s.substring(i, j);
    }

    public static String substringAfter(String s, String s1)
    {
        if(isEmpty(s))
            return s;
        if(s1 == null)
            return "";
        int i = s.indexOf(s1);
        if(i == -1)
            return "";
        else
            return s.substring(i + s1.length());
    }

    public static String substringAfterLast(String s, String s1)
    {
        if(isEmpty(s))
            return s;
        if(isEmpty(s1))
            return "";
        int i = s.lastIndexOf(s1);
        if(i == -1 || i == s.length() - s1.length())
            return "";
        else
            return s.substring(i + s1.length());
    }

    public static String substringBefore(String s, String s1)
    {
        if(!isEmpty(s) && s1 != null)
        {
            if(s1.length() == 0)
                return "";
            int i = s.indexOf(s1);
            if(i != -1)
                return s.substring(0, i);
        }
        return s;
    }

    public static String substringBeforeLast(String s, String s1)
    {
        int i;
        if(!isEmpty(s) && !isEmpty(s1))
            if((i = s.lastIndexOf(s1)) != -1)
                return s.substring(0, i);
        return s;
    }

    public static String substringBetween(String s, String s1)
    {
        return substringBetween(s, s1, s1);
    }

    public static String substringBetween(String s, String s1, String s2)
    {
        int i;
        int j;
        if(s != null && s1 != null && s2 != null)
            if((i = s.indexOf(s1)) != -1 && (j = s.indexOf(s2, i + s1.length())) != -1)
                return s.substring(i + s1.length(), j);
        return null;
    }

    public static String[] substringsBetween(String s, String s1, String s2)
    {
        if(s != null && !isEmpty(s1) && !isEmpty(s2)) goto _L2; else goto _L1
_L1:
        return null;
_L2:
        int i;
        int j;
        int k;
        ArrayList arraylist;
        int l;
        i = s.length();
        if(i == 0)
            return ArrayUtils.EMPTY_STRING_ARRAY;
        j = s2.length();
        k = s1.length();
        arraylist = new ArrayList();
        l = 0;
_L7:
        if(l >= i - j) goto _L4; else goto _L3
_L3:
        int i1 = s.indexOf(s1, l);
        if(i1 >= 0) goto _L5; else goto _L4
_L4:
        if(!arraylist.isEmpty())
            return (String[])arraylist.toArray(new String[arraylist.size()]);
          goto _L1
_L5:
        int j1;
        int k1;
        j1 = i1 + k;
        k1 = s.indexOf(s2, j1);
        if(k1 < 0) goto _L4; else goto _L6
_L6:
        arraylist.add(s.substring(j1, k1));
        l = k1 + j;
          goto _L7
    }

    public static String swapCase(String s)
    {
        if(isEmpty(s))
            return s;
        char ac[] = s.toCharArray();
        int i = 0;
        while(i < ac.length) 
        {
            char c = ac[i];
            if(Character.isUpperCase(c))
                ac[i] = Character.toLowerCase(c);
            else
            if(Character.isTitleCase(c))
                ac[i] = Character.toLowerCase(c);
            else
            if(Character.isLowerCase(c))
                ac[i] = Character.toUpperCase(c);
            i++;
        }
        return new String(ac);
    }

    public static String toString(byte abyte0[], String s)
        throws UnsupportedEncodingException
    {
        if(s == null)
            return new String(abyte0);
        else
            return new String(abyte0, s);
    }

    public static String trim(String s)
    {
        if(s == null)
            return null;
        else
            return s.trim();
    }

    public static String trimToEmpty(String s)
    {
        if(s == null)
            return "";
        else
            return s.trim();
    }

    public static String trimToNull(String s)
    {
        String s1 = trim(s);
        if(isEmpty(s1))
            s1 = null;
        return s1;
    }

    public static String uncapitalize(String s)
    {
        int i;
label0:
        {
            if(s != null)
            {
                i = s.length();
                if(i != 0)
                    break label0;
            }
            return s;
        }
        return (new StringBuilder(i)).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static String upperCase(String s)
    {
        if(s == null)
            return null;
        else
            return s.toUpperCase();
    }

    public static String upperCase(String s, Locale locale)
    {
        if(s == null)
            return null;
        else
            return s.toUpperCase(locale);
    }

    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    private static final Pattern WHITESPACE_BLOCK = Pattern.compile("\\s+");

}
