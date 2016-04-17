// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.util.Random;

public class RandomStringUtils
{

    public RandomStringUtils()
    {
    }

    public static String random(int i)
    {
        return random(i, false, false);
    }

    public static String random(int i, int j, int k, boolean flag, boolean flag1)
    {
        return random(i, j, k, flag, flag1, null, RANDOM);
    }

    public static transient String random(int i, int j, int k, boolean flag, boolean flag1, char ac[])
    {
        return random(i, j, k, flag, flag1, ac, RANDOM);
    }

    public static String random(int i, int j, int k, boolean flag, boolean flag1, char ac[], Random random1)
    {
        if(i == 0)
            return "";
        if(i < 0)
            throw new IllegalArgumentException((new StringBuilder()).append("Requested random string length ").append(i).append(" is less than 0.").toString());
        if(j == 0 && k == 0)
        {
            k = 123;
            j = 32;
            if(!flag && !flag1)
            {
                j = 0;
                k = 0x7fffffff;
            }
        }
        char ac1[] = new char[i];
        int l = k - j;
        int i1 = i;
        do
        {
            int j1 = i1 - 1;
            if(i1 != 0)
            {
                char c;
                if(ac == null)
                    c = (char)(j + random1.nextInt(l));
                else
                    c = ac[j + random1.nextInt(l)];
                if(flag && Character.isLetter(c) || flag1 && Character.isDigit(c) || !flag && !flag1)
                {
                    if(c >= '\uDC00' && c <= '\uDFFF')
                    {
                        if(j1 == 0)
                        {
                            j1++;
                        } else
                        {
                            ac1[j1] = c;
                            j1--;
                            ac1[j1] = (char)(55296 + random1.nextInt(128));
                        }
                    } else
                    if(c >= '\uD800' && c <= '\uDB7F')
                    {
                        if(j1 == 0)
                        {
                            j1++;
                        } else
                        {
                            ac1[j1] = (char)(56320 + random1.nextInt(128));
                            j1--;
                            ac1[j1] = c;
                        }
                    } else
                    if(c >= '\uDB80' && c <= '\uDBFF')
                        j1++;
                    else
                        ac1[j1] = c;
                } else
                {
                    j1++;
                }
                i1 = j1;
            } else
            {
                return new String(ac1);
            }
        } while(true);
    }

    public static String random(int i, String s)
    {
        if(s == null)
            return random(i, 0, 0, false, false, null, RANDOM);
        else
            return random(i, s.toCharArray());
    }

    public static String random(int i, boolean flag, boolean flag1)
    {
        return random(i, 0, 0, flag, flag1);
    }

    public static transient String random(int i, char ac[])
    {
        if(ac == null)
            return random(i, 0, 0, false, false, null, RANDOM);
        else
            return random(i, 0, ac.length, false, false, ac, RANDOM);
    }

    public static String randomAlphabetic(int i)
    {
        return random(i, true, false);
    }

    public static String randomAlphanumeric(int i)
    {
        return random(i, true, true);
    }

    public static String randomAscii(int i)
    {
        return random(i, 32, 127, false, false);
    }

    public static String randomNumeric(int i)
    {
        return random(i, false, true);
    }

    private static final Random RANDOM = new Random();

}
