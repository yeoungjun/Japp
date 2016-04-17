// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.util.StringTokenizer;

// Referenced classes of package org.apache.http.client.utils:
//            Idn

public class Rfc3492Idn
    implements Idn
{

    public Rfc3492Idn()
    {
    }

    private int adapt(int i, int j, boolean flag)
    {
        int k;
        int l;
        int i1;
        if(flag)
            k = i / 700;
        else
            k = i / 2;
        l = k + k / j;
        for(i1 = 0; l > 455; i1 += 36)
            l /= 35;

        return i1 + (l * 36) / (l + 38);
    }

    private int digit(char c)
    {
        if(c >= 'A' && c <= 'Z')
            return c - 65;
        if(c >= 'a' && c <= 'z')
            return c - 97;
        if(c >= '0' && c <= '9')
            return 26 + (c - 48);
        else
            throw new IllegalArgumentException((new StringBuilder()).append("illegal digit: ").append(c).toString());
    }

    protected String decode(String s)
    {
        String s1;
        int i;
        int j;
        StringBuilder stringbuilder;
        int l;
        s1 = s;
        i = 128;
        j = 72;
        stringbuilder = new StringBuilder(s1.length());
        int k = s1.lastIndexOf('-');
        l = 0;
        if(k != -1)
        {
            stringbuilder.append(s1.subSequence(0, k));
            s1 = s1.substring(k + 1);
        }
_L8:
        if(s1.length() <= 0) goto _L2; else goto _L1
_L1:
        int i1;
        int j1;
        int k1;
        i1 = l;
        j1 = 1;
        k1 = 36;
_L6:
        if(s1.length() != 0) goto _L4; else goto _L3
_L3:
        int j2 = l - i1;
        int k2 = 1 + stringbuilder.length();
        char c;
        int l1;
        int i2;
        boolean flag;
        int l2;
        if(i1 == 0)
            flag = true;
        else
            flag = false;
        j = adapt(j2, k2, flag);
        i += l / (1 + stringbuilder.length());
        l2 = l % (1 + stringbuilder.length());
        stringbuilder.insert(l2, (char)i);
        l = l2 + 1;
        continue; /* Loop/switch isn't completed */
_L4:
        c = s1.charAt(0);
        s1 = s1.substring(1);
        l1 = digit(c);
        l += l1 * j1;
        if(k1 <= j + 1)
            i2 = 1;
        else
        if(k1 >= j + 26)
            i2 = 26;
        else
            i2 = k1 - j;
        if(l1 < i2) goto _L3; else goto _L5
_L5:
        j1 *= 36 - i2;
        k1 += 36;
        if(true) goto _L6; else goto _L2
_L2:
        return stringbuilder.toString();
        if(true) goto _L8; else goto _L7
_L7:
    }

    public String toUnicode(String s)
    {
        StringBuilder stringbuilder = new StringBuilder(s.length());
        String s1;
        for(StringTokenizer stringtokenizer = new StringTokenizer(s, "."); stringtokenizer.hasMoreTokens(); stringbuilder.append(s1))
        {
            s1 = stringtokenizer.nextToken();
            if(stringbuilder.length() > 0)
                stringbuilder.append('.');
            if(s1.startsWith("xn--"))
                s1 = decode(s1.substring(4));
        }

        return stringbuilder.toString();
    }

    private static final String ACE_PREFIX = "xn--";
    private static final int base = 36;
    private static final int damp = 700;
    private static final char delimiter = 45;
    private static final int initial_bias = 72;
    private static final int initial_n = 128;
    private static final int skew = 38;
    private static final int tmax = 26;
    private static final int tmin = 1;
}
