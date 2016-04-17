// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io;

import java.io.File;
import java.util.*;

// Referenced classes of package org.apache.commons.io:
//            IOCase

public class FilenameUtils
{

    public FilenameUtils()
    {
    }

    public static String concat(String s, String s1)
    {
        int i = getPrefixLength(s1);
        if(i >= 0)
        {
            if(i > 0)
                return normalize(s1);
            if(s != null)
            {
                int j = s.length();
                if(j == 0)
                    return normalize(s1);
                if(isSeparator(s.charAt(j - 1)))
                    return normalize((new StringBuilder()).append(s).append(s1).toString());
                else
                    return normalize((new StringBuilder()).append(s).append('/').append(s1).toString());
            }
        }
        return null;
    }

    private static String doGetFullPath(String s, boolean flag)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            int i = getPrefixLength(s);
            if(i < 0)
                return null;
            if(i >= s.length())
            {
                if(flag)
                    return getPrefix(s);
            } else
            {
                int j = indexOfLastSeparator(s);
                if(j < 0)
                    return s.substring(0, i);
                int k;
                int l;
                if(flag)
                    k = 1;
                else
                    k = 0;
                l = j + k;
                if(l == 0)
                    l++;
                return s.substring(0, l);
            }
        }
        return s;
    }

    private static String doGetPath(String s, int i)
    {
        int j;
        if(s != null)
            if((j = getPrefixLength(s)) >= 0)
            {
                int k = indexOfLastSeparator(s);
                int l = k + i;
                if(j >= s.length() || k < 0 || j >= l)
                    return "";
                else
                    return s.substring(j, l);
            }
        return null;
    }

    private static String doNormalize(String s, char c, boolean flag)
    {
        if(s != null) goto _L2; else goto _L1
_L1:
        s = null;
_L4:
        return s;
_L2:
        int i = s.length();
        if(i == 0) goto _L4; else goto _L3
_L3:
        int j;
        char ac[];
        boolean flag1;
        int j1;
        j = getPrefixLength(s);
        if(j < 0)
            return null;
        ac = new char[i + 2];
        s.getChars(0, s.length(), ac, 0);
        char c1;
        int k;
        if(c == SYSTEM_SEPARATOR)
            c1 = OTHER_SEPARATOR;
        else
            c1 = SYSTEM_SEPARATOR;
        for(k = 0; k < ac.length; k++)
            if(ac[k] == c1)
                ac[k] = c;

        flag1 = true;
        if(ac[i - 1] != c)
        {
            int l1 = i + 1;
            ac[i] = c;
            flag1 = false;
            i = l1;
        }
        for(int l = j + 1; l < i; l++)
            if(ac[l] == c && ac[l - 1] == c)
            {
                System.arraycopy(ac, l, ac, l - 1, i - l);
                i--;
                l--;
            }

        for(int i1 = j + 1; i1 < i; i1++)
        {
            if(ac[i1] != c || ac[i1 - 1] != '.' || i1 != j + 1 && ac[i1 - 2] != c)
                continue;
            if(i1 == i - 1)
                flag1 = true;
            System.arraycopy(ac, i1 + 1, ac, i1 - 1, i - i1);
            i -= 2;
            i1--;
        }

        j1 = j + 2;
_L11:
        if(j1 >= i)
            break MISSING_BLOCK_LABEL_477;
        if(ac[j1] != c || ac[j1 - 1] != '.' || ac[j1 - 2] != '.' || j1 != j + 2 && ac[j1 - 3] != c) goto _L6; else goto _L5
_L5:
        int k1;
        if(j1 == j + 2)
            return null;
        if(j1 == i - 1)
            flag1 = true;
        k1 = j1 - 4;
_L9:
        if(k1 < j)
            break MISSING_BLOCK_LABEL_441;
        if(ac[k1] != c) goto _L8; else goto _L7
_L7:
        System.arraycopy(ac, j1 + 1, ac, k1 + 1, i - j1);
        i -= j1 - k1;
        j1 = k1 + 1;
_L6:
        j1++;
        continue; /* Loop/switch isn't completed */
_L8:
        k1--;
          goto _L9
        System.arraycopy(ac, j1 + 1, ac, j, i - j1);
        i -= (j1 + 1) - j;
        j1 = j + 1;
          goto _L6
        if(i <= 0)
            return "";
        if(i <= j)
            return new String(ac, 0, i);
        if(flag1 && flag)
            return new String(ac, 0, i);
        return new String(ac, 0, i - 1);
        if(true) goto _L11; else goto _L10
_L10:
    }

    public static boolean equals(String s, String s1)
    {
        return equals(s, s1, false, IOCase.SENSITIVE);
    }

    public static boolean equals(String s, String s1, boolean flag, IOCase iocase)
    {
        if(s == null || s1 == null)
            return s == null && s1 == null;
        if(flag)
        {
            s = normalize(s);
            s1 = normalize(s1);
            if(s == null || s1 == null)
                throw new NullPointerException("Error normalizing one or both of the file names");
        }
        if(iocase == null)
            iocase = IOCase.SENSITIVE;
        return iocase.checkEquals(s, s1);
    }

    public static boolean equalsNormalized(String s, String s1)
    {
        return equals(s, s1, true, IOCase.SENSITIVE);
    }

    public static boolean equalsNormalizedOnSystem(String s, String s1)
    {
        return equals(s, s1, true, IOCase.SYSTEM);
    }

    public static boolean equalsOnSystem(String s, String s1)
    {
        return equals(s, s1, false, IOCase.SYSTEM);
    }

    public static String getBaseName(String s)
    {
        return removeExtension(getName(s));
    }

    public static String getExtension(String s)
    {
        if(s == null)
            return null;
        int i = indexOfExtension(s);
        if(i == -1)
            return "";
        else
            return s.substring(i + 1);
    }

    public static String getFullPath(String s)
    {
        return doGetFullPath(s, true);
    }

    public static String getFullPathNoEndSeparator(String s)
    {
        return doGetFullPath(s, false);
    }

    public static String getName(String s)
    {
        if(s == null)
            return null;
        else
            return s.substring(1 + indexOfLastSeparator(s));
    }

    public static String getPath(String s)
    {
        return doGetPath(s, 1);
    }

    public static String getPathNoEndSeparator(String s)
    {
        return doGetPath(s, 0);
    }

    public static String getPrefix(String s)
    {
        int i;
        if(s != null)
            if((i = getPrefixLength(s)) >= 0)
                if(i > s.length())
                    return (new StringBuilder()).append(s).append('/').toString();
                else
                    return s.substring(0, i);
        return null;
    }

    public static int getPrefixLength(String s)
    {
        byte byte0 = 1;
        if(s != null) goto _L2; else goto _L1
_L1:
        byte0 = -1;
_L4:
        return byte0;
_L2:
        int i;
        char c;
        i = s.length();
        if(i == 0)
            return 0;
        c = s.charAt(0);
        if(c == ':')
            return -1;
        if(i != byte0)
            break; /* Loop/switch isn't completed */
        if(c == '~')
            return 2;
        if(!isSeparator(c))
            return 0;
        if(true) goto _L4; else goto _L3
_L3:
        if(c == '~')
        {
            int l = s.indexOf('/', byte0);
            int i1 = s.indexOf('\\', byte0);
            if(l == -1 && i1 == -1)
                return i + 1;
            if(l == -1)
                l = i1;
            if(i1 == -1)
                i1 = l;
            return 1 + Math.min(l, i1);
        }
        char c1 = s.charAt(byte0);
        if(c1 == ':')
        {
            char c2 = Character.toUpperCase(c);
            if(c2 >= 'A' && c2 <= 'Z')
                return i != 2 && isSeparator(s.charAt(2)) ? 3 : 2;
            else
                return -1;
        }
        if(isSeparator(c) && isSeparator(c1))
        {
            int j = s.indexOf('/', 2);
            int k = s.indexOf('\\', 2);
            if(j == -1 && k == -1 || j == 2 || k == 2)
                return -1;
            if(j == -1)
                j = k;
            if(k == -1)
                k = j;
            return 1 + Math.min(j, k);
        }
        if(!isSeparator(c))
            return 0;
        if(true) goto _L4; else goto _L5
_L5:
    }

    public static int indexOfExtension(String s)
    {
        if(s == null)
            return -1;
        int i = s.lastIndexOf('.');
        if(indexOfLastSeparator(s) > i)
            i = -1;
        return i;
    }

    public static int indexOfLastSeparator(String s)
    {
        if(s == null)
            return -1;
        else
            return Math.max(s.lastIndexOf('/'), s.lastIndexOf('\\'));
    }

    public static boolean isExtension(String s, String s1)
    {
        if(s != null)
            if(s1 == null || s1.length() == 0)
            {
                if(indexOfExtension(s) == -1)
                    return true;
            } else
            {
                return getExtension(s).equals(s1);
            }
        return false;
    }

    public static boolean isExtension(String s, Collection collection)
    {
        boolean flag = true;
        if(s != null)
        {
            if(collection == null || collection.isEmpty())
            {
                if(indexOfExtension(s) != -1)
                    flag = false;
                return flag;
            }
            String s1 = getExtension(s);
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) 
                if(s1.equals((String)iterator.next()))
                    return flag;
        }
        return false;
    }

    public static boolean isExtension(String s, String as[])
    {
        boolean flag = true;
        if(s != null)
        {
            if(as == null || as.length == 0)
            {
                if(indexOfExtension(s) != -1)
                    flag = false;
                return flag;
            }
            String s1 = getExtension(s);
            int i = as.length;
            int j = 0;
            while(j < i) 
            {
                if(s1.equals(as[j]))
                    return flag;
                j++;
            }
        }
        return false;
    }

    private static boolean isSeparator(char c)
    {
        return c == '/' || c == '\\';
    }

    static boolean isSystemWindows()
    {
        return SYSTEM_SEPARATOR == '\\';
    }

    public static String normalize(String s)
    {
        return doNormalize(s, SYSTEM_SEPARATOR, true);
    }

    public static String normalize(String s, boolean flag)
    {
        char c;
        if(flag)
            c = '/';
        else
            c = '\\';
        return doNormalize(s, c, true);
    }

    public static String normalizeNoEndSeparator(String s)
    {
        return doNormalize(s, SYSTEM_SEPARATOR, false);
    }

    public static String normalizeNoEndSeparator(String s, boolean flag)
    {
        char c;
        if(flag)
            c = '/';
        else
            c = '\\';
        return doNormalize(s, c, false);
    }

    public static String removeExtension(String s)
    {
        if(s == null)
        {
            s = null;
        } else
        {
            int i = indexOfExtension(s);
            if(i != -1)
                return s.substring(0, i);
        }
        return s;
    }

    public static String separatorsToSystem(String s)
    {
        if(s == null)
            return null;
        if(isSystemWindows())
            return separatorsToWindows(s);
        else
            return separatorsToUnix(s);
    }

    public static String separatorsToUnix(String s)
    {
        if(s == null || s.indexOf('\\') == -1)
            return s;
        else
            return s.replace('\\', '/');
    }

    public static String separatorsToWindows(String s)
    {
        if(s == null || s.indexOf('/') == -1)
            return s;
        else
            return s.replace('/', '\\');
    }

    static String[] splitOnTokens(String s)
    {
        if(s.indexOf('?') == -1 && s.indexOf('*') == -1)
            return (new String[] {
                s
            });
        char ac[] = s.toCharArray();
        ArrayList arraylist = new ArrayList();
        StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        while(i < ac.length) 
        {
            if(ac[i] == '?' || ac[i] == '*')
            {
                if(stringbuilder.length() != 0)
                {
                    arraylist.add(stringbuilder.toString());
                    stringbuilder.setLength(0);
                }
                if(ac[i] == '?')
                    arraylist.add("?");
                else
                if(arraylist.size() == 0 || i > 0 && !((String)arraylist.get(-1 + arraylist.size())).equals("*"))
                    arraylist.add("*");
            } else
            {
                stringbuilder.append(ac[i]);
            }
            i++;
        }
        if(stringbuilder.length() != 0)
            arraylist.add(stringbuilder.toString());
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public static boolean wildcardMatch(String s, String s1)
    {
        return wildcardMatch(s, s1, IOCase.SENSITIVE);
    }

    public static boolean wildcardMatch(String s, String s1, IOCase iocase)
    {
        if(s != null || s1 != null) goto _L2; else goto _L1
_L1:
        return true;
_L2:
        String as[];
        boolean flag;
        int i;
        int j;
        Stack stack;
        if(s == null || s1 == null)
            return false;
        if(iocase == null)
            iocase = IOCase.SENSITIVE;
        as = splitOnTokens(s1);
        flag = false;
        i = 0;
        j = 0;
        stack = new Stack();
_L9:
        if(stack.size() > 0)
        {
            int ai[] = (int[])stack.pop();
            j = ai[0];
            i = ai[1];
            flag = true;
        }
_L10:
        if(j >= as.length) goto _L4; else goto _L3
_L3:
        if(!as[j].equals("?")) goto _L6; else goto _L5
_L5:
        if(++i <= s.length()) goto _L7; else goto _L4
_L4:
        if(j == as.length && i == s.length()) goto _L1; else goto _L8
_L8:
        if(stack.size() <= 0)
            return false;
          goto _L9
_L7:
        flag = false;
_L11:
        j++;
          goto _L10
_L6:
label0:
        {
            if(!as[j].equals("*"))
                break label0;
            flag = true;
            if(j == -1 + as.length)
                i = s.length();
        }
          goto _L11
        if(!flag) goto _L13; else goto _L12
_L12:
        i = iocase.checkIndexOf(s, i, as[j]);
        if(i == -1) goto _L4; else goto _L14
_L14:
        int k = iocase.checkIndexOf(s, i + 1, as[j]);
        if(k >= 0)
            stack.push(new int[] {
                j, k
            });
_L15:
        i += as[j].length();
        flag = false;
          goto _L11
_L13:
        if(iocase.checkRegionMatches(s, i, as[j])) goto _L15; else goto _L4
    }

    public static boolean wildcardMatchOnSystem(String s, String s1)
    {
        return wildcardMatch(s, s1, IOCase.SYSTEM);
    }

    public static final char EXTENSION_SEPARATOR = 46;
    public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
    private static final char OTHER_SEPARATOR = 0;
    private static final char SYSTEM_SEPARATOR = 0;
    private static final char UNIX_SEPARATOR = 47;
    private static final char WINDOWS_SEPARATOR = 92;

    static 
    {
        SYSTEM_SEPARATOR = File.separatorChar;
        if(isSystemWindows())
            OTHER_SEPARATOR = '/';
        else
            OTHER_SEPARATOR = '\\';
    }
}
