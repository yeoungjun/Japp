// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.internal.nls;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

public class Messages
{

    public Messages()
    {
    }

    public static String format(String s, Object aobj[])
    {
        StringBuilder stringbuilder;
        String as[];
        int i;
        stringbuilder = new StringBuilder(s.length() + 20 * aobj.length);
        as = new String[aobj.length];
        i = 0;
_L3:
        if(i < aobj.length) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        j = 0;
        k = s.indexOf('{', 0);
_L4:
        if(k < 0)
        {
            if(j < s.length())
                stringbuilder.append(s.substring(j, s.length()));
            return stringbuilder.toString();
        }
        break MISSING_BLOCK_LABEL_113;
_L2:
        if(aobj[i] == null)
            as[i] = "<null>";
        else
            as[i] = aobj[i].toString();
        i++;
          goto _L3
        if(k != 0 && s.charAt(k - 1) == '\\')
        {
            if(k != 1)
                stringbuilder.append(s.substring(j, k - 1));
            stringbuilder.append('{');
            j = k + 1;
        } else
        if(k > -3 + s.length())
        {
            stringbuilder.append(s.substring(j, s.length()));
            j = s.length();
        } else
        {
            byte byte0 = (byte)Character.digit(s.charAt(k + 1), 10);
            if(byte0 < 0 || s.charAt(k + 2) != '}')
            {
                stringbuilder.append(s.substring(j, k + 1));
                j = k + 1;
            } else
            {
                stringbuilder.append(s.substring(j, k));
                if(byte0 >= as.length)
                    stringbuilder.append("<missing argument>");
                else
                    stringbuilder.append(as[byte0]);
                j = k + 3;
            }
        }
        k = s.indexOf('{', j);
          goto _L4
    }

    public static String getString(String s)
    {
        if(bundle == null)
            return s;
        String s1;
        try
        {
            s1 = bundle.getString(s);
        }
        catch(MissingResourceException missingresourceexception)
        {
            return (new StringBuilder("Missing message: ")).append(s).toString();
        }
        return s1;
    }

    public static String getString(String s, char c)
    {
        Object aobj[] = new Object[1];
        aobj[0] = String.valueOf(c);
        return getString(s, aobj);
    }

    public static String getString(String s, int i)
    {
        Object aobj[] = new Object[1];
        aobj[0] = Integer.toString(i);
        return getString(s, aobj);
    }

    public static String getString(String s, Object obj)
    {
        return getString(s, new Object[] {
            obj
        });
    }

    public static String getString(String s, Object obj, Object obj1)
    {
        return getString(s, new Object[] {
            obj, obj1
        });
    }

    public static String getString(String s, Object aobj[])
    {
        String s1;
        s1 = s;
        if(bundle == null)
            break MISSING_BLOCK_LABEL_20;
        String s2 = bundle.getString(s);
        s1 = s2;
_L2:
        return format(s1, aobj);
        MissingResourceException missingresourceexception;
        missingresourceexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public static ResourceBundle setLocale(final Locale locale, final String resource)
    {
        ResourceBundle resourcebundle;
        try
        {
            resourcebundle = (ResourceBundle)AccessController.doPrivileged(new PrivilegedAction() {

                public Object run()
                {
                    String s = resource;
                    Locale locale1 = locale;
                    ClassLoader classloader;
                    if(loader != null)
                        classloader = loader;
                    else
                        classloader = ClassLoader.getSystemClassLoader();
                    return ResourceBundle.getBundle(s, locale1, classloader);
                }

                private final ClassLoader val$loader;
                private final Locale val$locale;
                private final String val$resource;

            
            {
                resource = s;
                locale = locale1;
                loader = classloader;
                super();
            }
            });
        }
        catch(MissingResourceException missingresourceexception)
        {
            return null;
        }
        return resourcebundle;
    }

    private static ResourceBundle bundle = null;

    static 
    {
        try
        {
            bundle = setLocale(Locale.getDefault(), "org.apache.harmony.awt.internal.nls.messages");
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}
