// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

public class CharEncoding
{

    public CharEncoding()
    {
    }

    public static boolean isSupported(String s)
    {
        if(s == null)
            return false;
        boolean flag;
        try
        {
            flag = Charset.isSupported(s);
        }
        catch(IllegalCharsetNameException illegalcharsetnameexception)
        {
            return false;
        }
        return flag;
    }

    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String US_ASCII = "US-ASCII";
    public static final String UTF_16 = "UTF-16";
    public static final String UTF_16BE = "UTF-16BE";
    public static final String UTF_16LE = "UTF-16LE";
    public static final String UTF_8 = "UTF-8";
}
