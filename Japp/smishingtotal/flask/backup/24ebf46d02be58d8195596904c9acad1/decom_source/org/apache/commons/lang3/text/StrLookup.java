// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text;

import java.util.Map;

public abstract class StrLookup
{
    static class MapStrLookup extends StrLookup
    {

        public String lookup(String s)
        {
            Object obj;
            if(map != null)
                if((obj = map.get(s)) != null)
                    return obj.toString();
            return null;
        }

        private final Map map;

        MapStrLookup(Map map1)
        {
            map = map1;
        }
    }


    protected StrLookup()
    {
    }

    public static StrLookup mapLookup(Map map)
    {
        return new MapStrLookup(map);
    }

    public static StrLookup noneLookup()
    {
        return NONE_LOOKUP;
    }

    public static StrLookup systemPropertiesLookup()
    {
        return SYSTEM_PROPERTIES_LOOKUP;
    }

    public abstract String lookup(String s);

    private static final StrLookup NONE_LOOKUP;
    private static final StrLookup SYSTEM_PROPERTIES_LOOKUP;

    static 
    {
        NONE_LOOKUP = new MapStrLookup(null);
        Object obj;
        try
        {
            obj = new MapStrLookup(System.getProperties());
        }
        catch(SecurityException securityexception)
        {
            obj = NONE_LOOKUP;
        }
        SYSTEM_PROPERTIES_LOOKUP = ((StrLookup) (obj));
    }
}
