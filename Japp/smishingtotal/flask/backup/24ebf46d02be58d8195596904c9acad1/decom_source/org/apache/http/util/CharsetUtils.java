// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetUtils
{

    public CharsetUtils()
    {
    }

    public static Charset get(String s)
        throws UnsupportedEncodingException
    {
        if(s == null)
            return null;
        Charset charset;
        try
        {
            charset = Charset.forName(s);
        }
        catch(UnsupportedCharsetException unsupportedcharsetexception)
        {
            throw new UnsupportedEncodingException(s);
        }
        return charset;
    }

    public static Charset lookup(String s)
    {
        if(s == null)
            return null;
        Charset charset;
        try
        {
            charset = Charset.forName(s);
        }
        catch(UnsupportedCharsetException unsupportedcharsetexception)
        {
            return null;
        }
        return charset;
    }
}
