// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LocaleUtils
{
    static class SyncAvoid
    {

        private static List AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(new ArrayList(Arrays.asList(Locale.getAvailableLocales())));
        private static Set AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet(LocaleUtils.availableLocaleList()));




        SyncAvoid()
        {
        }
    }


    public LocaleUtils()
    {
    }

    public static List availableLocaleList()
    {
        return SyncAvoid.AVAILABLE_LOCALE_LIST;
    }

    public static Set availableLocaleSet()
    {
        return SyncAvoid.AVAILABLE_LOCALE_SET;
    }

    public static List countriesByLanguage(String s)
    {
        List list;
        if(s == null)
        {
            list = Collections.emptyList();
        } else
        {
            list = (List)cCountriesByLanguage.get(s);
            if(list == null)
            {
                ArrayList arraylist = new ArrayList();
                List list1 = availableLocaleList();
                for(int i = 0; i < list1.size(); i++)
                {
                    Locale locale = (Locale)list1.get(i);
                    if(s.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().length() == 0)
                        arraylist.add(locale);
                }

                List list2 = Collections.unmodifiableList(arraylist);
                cCountriesByLanguage.putIfAbsent(s, list2);
                return (List)cCountriesByLanguage.get(s);
            }
        }
        return list;
    }

    public static boolean isAvailableLocale(Locale locale)
    {
        return availableLocaleList().contains(locale);
    }

    public static List languagesByCountry(String s)
    {
        List list;
        if(s == null)
        {
            list = Collections.emptyList();
        } else
        {
            list = (List)cLanguagesByCountry.get(s);
            if(list == null)
            {
                ArrayList arraylist = new ArrayList();
                List list1 = availableLocaleList();
                for(int i = 0; i < list1.size(); i++)
                {
                    Locale locale = (Locale)list1.get(i);
                    if(s.equals(locale.getCountry()) && locale.getVariant().length() == 0)
                        arraylist.add(locale);
                }

                List list2 = Collections.unmodifiableList(arraylist);
                cLanguagesByCountry.putIfAbsent(s, list2);
                return (List)cLanguagesByCountry.get(s);
            }
        }
        return list;
    }

    public static List localeLookupList(Locale locale)
    {
        return localeLookupList(locale, locale);
    }

    public static List localeLookupList(Locale locale, Locale locale1)
    {
        ArrayList arraylist = new ArrayList(4);
        if(locale != null)
        {
            arraylist.add(locale);
            if(locale.getVariant().length() > 0)
                arraylist.add(new Locale(locale.getLanguage(), locale.getCountry()));
            if(locale.getCountry().length() > 0)
                arraylist.add(new Locale(locale.getLanguage(), ""));
            if(!arraylist.contains(locale1))
                arraylist.add(locale1);
        }
        return Collections.unmodifiableList(arraylist);
    }

    public static Locale toLocale(String s)
    {
        if(s == null)
            return null;
        int i = s.length();
        if(i != 2 && i != 5 && i < 7)
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid locale format: ").append(s).toString());
        char c = s.charAt(0);
        char c1 = s.charAt(1);
        if(c < 'a' || c > 'z' || c1 < 'a' || c1 > 'z')
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid locale format: ").append(s).toString());
        if(i == 2)
            return new Locale(s, "");
        if(s.charAt(2) != '_')
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid locale format: ").append(s).toString());
        char c2 = s.charAt(3);
        if(c2 == '_')
            return new Locale(s.substring(0, 2), "", s.substring(4));
        char c3 = s.charAt(4);
        if(c2 < 'A' || c2 > 'Z' || c3 < 'A' || c3 > 'Z')
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid locale format: ").append(s).toString());
        if(i == 5)
            return new Locale(s.substring(0, 2), s.substring(3, 5));
        if(s.charAt(5) != '_')
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid locale format: ").append(s).toString());
        else
            return new Locale(s.substring(0, 2), s.substring(3, 5), s.substring(6));
    }

    private static final ConcurrentMap cCountriesByLanguage = new ConcurrentHashMap();
    private static final ConcurrentMap cLanguagesByCountry = new ConcurrentHashMap();

}
