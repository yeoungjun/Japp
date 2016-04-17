// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.time;

import java.text.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class FormatCache
{
    private static class MultipartKey
    {

        public boolean equals(Object obj)
        {
            if(this == obj)
                return true;
            if(!(obj instanceof MultipartKey))
                return false;
            else
                return Arrays.equals(keys, ((MultipartKey)obj).keys);
        }

        public int hashCode()
        {
            if(hashCode == 0)
            {
                int i = 0;
                Object aobj[] = keys;
                int j = aobj.length;
                for(int k = 0; k < j; k++)
                {
                    Object obj = aobj[k];
                    if(obj != null)
                        i = i * 7 + obj.hashCode();
                }

                hashCode = i;
            }
            return hashCode;
        }

        private int hashCode;
        private final Object keys[];

        public transient MultipartKey(Object aobj[])
        {
            keys = aobj;
        }
    }


    FormatCache()
    {
    }

    protected abstract Format createInstance(String s, TimeZone timezone, Locale locale);

    public Format getDateTimeInstance(Integer integer, Integer integer1, TimeZone timezone, Locale locale)
    {
        MultipartKey multipartkey;
        String s;
        if(locale == null)
            locale = Locale.getDefault();
        multipartkey = new MultipartKey(new Object[] {
            integer, integer1, locale
        });
        s = (String)cDateTimeInstanceCache.get(multipartkey);
        if(s != null) goto _L2; else goto _L1
_L1:
        if(integer != null) goto _L4; else goto _L3
_L3:
        DateFormat dateformat1 = DateFormat.getTimeInstance(integer1.intValue(), locale);
_L5:
        String s1;
        s = ((SimpleDateFormat)dateformat1).toPattern();
        s1 = (String)cDateTimeInstanceCache.putIfAbsent(multipartkey, s);
        if(s1 != null)
            s = s1;
_L2:
        return getInstance(s, timezone, locale);
_L4:
label0:
        {
            if(integer1 != null)
                break label0;
            DateFormat dateformat;
            try
            {
                dateformat1 = DateFormat.getDateInstance(integer.intValue(), locale);
            }
            catch(ClassCastException classcastexception)
            {
                throw new IllegalArgumentException((new StringBuilder()).append("No date time pattern for locale: ").append(locale).toString());
            }
        }
          goto _L5
        dateformat = DateFormat.getDateTimeInstance(integer.intValue(), integer1.intValue(), locale);
        dateformat1 = dateformat;
          goto _L5
    }

    public Format getInstance()
    {
        return getDateTimeInstance(Integer.valueOf(3), Integer.valueOf(3), TimeZone.getDefault(), Locale.getDefault());
    }

    public Format getInstance(String s, TimeZone timezone, Locale locale)
    {
        if(s == null)
            throw new NullPointerException("pattern must not be null");
        if(timezone == null)
            timezone = TimeZone.getDefault();
        if(locale == null)
            locale = Locale.getDefault();
        MultipartKey multipartkey = new MultipartKey(new Object[] {
            s, timezone, locale
        });
        Format format = (Format)cInstanceCache.get(multipartkey);
        if(format == null)
        {
            format = createInstance(s, timezone, locale);
            Format format1 = (Format)cInstanceCache.putIfAbsent(multipartkey, format);
            if(format1 != null)
                format = format1;
        }
        return format;
    }

    static final int NONE = -1;
    private final ConcurrentMap cDateTimeInstanceCache = new ConcurrentHashMap(7);
    private final ConcurrentMap cInstanceCache = new ConcurrentHashMap(7);
}
