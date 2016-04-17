// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;


// Referenced classes of package org.apache.http.client.utils:
//            JdkIdn, Rfc3492Idn, Idn

public class Punycode
{

    public Punycode()
    {
    }

    public static String toUnicode(String s)
    {
        return impl.toUnicode(s);
    }

    private static final Idn impl;

    static 
    {
        Object obj;
        try
        {
            obj = new JdkIdn();
        }
        catch(Exception exception)
        {
            obj = new Rfc3492Idn();
        }
        impl = ((Idn) (obj));
    }
}
