// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.activation.registries;

import java.util.NoSuchElementException;
import java.util.Vector;

class LineTokenizer
{

    public LineTokenizer(String s)
    {
        stack = new Vector();
        currentPosition = 0;
        str = s;
        maxPosition = s.length();
    }

    private void skipWhiteSpace()
    {
        do
        {
            if(currentPosition >= maxPosition || !Character.isWhitespace(str.charAt(currentPosition)))
                return;
            currentPosition = 1 + currentPosition;
        } while(true);
    }

    public boolean hasMoreTokens()
    {
        if(stack.size() <= 0)
        {
            skipWhiteSpace();
            if(currentPosition >= maxPosition)
                return false;
        }
        return true;
    }

    public String nextToken()
    {
        int j;
        char c;
        boolean flag;
        int i = stack.size();
        if(i > 0)
        {
            String s2 = (String)stack.elementAt(i - 1);
            stack.removeElementAt(i - 1);
            return s2;
        }
        skipWhiteSpace();
        if(currentPosition >= maxPosition)
            throw new NoSuchElementException();
        j = currentPosition;
        c = str.charAt(j);
        if(c != '"')
            break MISSING_BLOCK_LABEL_272;
        currentPosition = 1 + currentPosition;
        flag = false;
_L3:
        if(currentPosition < maxPosition) goto _L2; else goto _L1
_L1:
        return str.substring(j, currentPosition);
_L2:
        char c1;
        String s = str;
        int k = currentPosition;
        currentPosition = k + 1;
        c1 = s.charAt(k);
        if(c1 != '\\')
            continue; /* Loop/switch isn't completed */
        currentPosition = 1 + currentPosition;
        flag = true;
          goto _L3
        if(c1 != '"') goto _L3; else goto _L4
_L4:
        StringBuffer stringbuffer;
        int l;
        if(!flag)
            break MISSING_BLOCK_LABEL_251;
        stringbuffer = new StringBuffer();
        l = j + 1;
_L7:
        if(l < -1 + currentPosition) goto _L6; else goto _L5
_L5:
        String s1 = stringbuffer.toString();
_L8:
        return s1;
_L6:
        char c2 = str.charAt(l);
        if(c2 != '\\')
            stringbuffer.append(c2);
        l++;
          goto _L7
        s1 = str.substring(j + 1, -1 + currentPosition);
          goto _L8
        if("=".indexOf(c) >= 0)
            currentPosition = 1 + currentPosition;
        else
            for(; currentPosition < maxPosition && "=".indexOf(str.charAt(currentPosition)) < 0 && !Character.isWhitespace(str.charAt(currentPosition)); currentPosition = 1 + currentPosition);
          goto _L1
    }

    public void pushToken(String s)
    {
        stack.addElement(s);
    }

    private static final String singles = "=";
    private int currentPosition;
    private int maxPosition;
    private Vector stack;
    private String str;
}
