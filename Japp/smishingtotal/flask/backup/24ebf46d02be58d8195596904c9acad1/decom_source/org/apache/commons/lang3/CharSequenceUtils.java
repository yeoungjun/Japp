// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;


public class CharSequenceUtils
{

    public CharSequenceUtils()
    {
    }

    static int indexOf(CharSequence charsequence, int i, int j)
    {
        if(!(charsequence instanceof String)) goto _L2; else goto _L1
_L1:
        int l = ((String)charsequence).indexOf(i, j);
_L4:
        return l;
_L2:
        int k = charsequence.length();
        if(j < 0)
            j = 0;
        l = j;
label0:
        do
        {
label1:
            {
                if(l >= k)
                    break label1;
                if(charsequence.charAt(l) == i)
                    break label0;
                l++;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    static int indexOf(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        return charsequence.toString().indexOf(charsequence1.toString(), i);
    }

    static int lastIndexOf(CharSequence charsequence, int i, int j)
    {
        if(!(charsequence instanceof String)) goto _L2; else goto _L1
_L1:
        int l = ((String)charsequence).lastIndexOf(i, j);
_L4:
        return l;
_L2:
        int k = charsequence.length();
        if(j < 0)
            return -1;
        if(j >= k)
            j = k - 1;
        l = j;
label0:
        do
        {
label1:
            {
                if(l < 0)
                    break label1;
                if(charsequence.charAt(l) == i)
                    break label0;
                l--;
            }
        } while(true);
        if(true) goto _L4; else goto _L3
_L3:
        return -1;
    }

    static int lastIndexOf(CharSequence charsequence, CharSequence charsequence1, int i)
    {
        return charsequence.toString().lastIndexOf(charsequence1.toString(), i);
    }

    static boolean regionMatches(CharSequence charsequence, boolean flag, int i, CharSequence charsequence1, int j, int k)
    {
        if((charsequence instanceof String) && (charsequence1 instanceof String))
            return ((String)charsequence).regionMatches(flag, i, (String)charsequence1, j, k);
        else
            return charsequence.toString().regionMatches(flag, i, charsequence1.toString(), j, k);
    }

    public static CharSequence subSequence(CharSequence charsequence, int i)
    {
        if(charsequence == null)
            return null;
        else
            return charsequence.subSequence(i, charsequence.length());
    }

    static char[] toCharArray(CharSequence charsequence)
    {
        char ac[];
        if(charsequence instanceof String)
        {
            ac = ((String)charsequence).toCharArray();
        } else
        {
            int i = charsequence.length();
            ac = new char[charsequence.length()];
            int j = 0;
            while(j < i) 
            {
                ac[j] = charsequence.charAt(j);
                j++;
            }
        }
        return ac;
    }
}
