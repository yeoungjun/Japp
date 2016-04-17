// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.http.util.Args;

public final class DateUtils
{
    static final class DateFormatHolder
    {

        public static void clearThreadLocal()
        {
            THREADLOCAL_FORMATS.remove();
        }

        public static SimpleDateFormat formatFor(String s)
        {
            Object obj = (Map)((SoftReference)THREADLOCAL_FORMATS.get()).get();
            if(obj == null)
            {
                obj = new HashMap();
                THREADLOCAL_FORMATS.set(new SoftReference(obj));
            }
            SimpleDateFormat simpledateformat = (SimpleDateFormat)((Map) (obj)).get(s);
            if(simpledateformat == null)
            {
                simpledateformat = new SimpleDateFormat(s, Locale.US);
                simpledateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
                ((Map) (obj)).put(s, simpledateformat);
            }
            return simpledateformat;
        }

        private static final ThreadLocal THREADLOCAL_FORMATS = new ThreadLocal() {

            protected volatile Object initialValue()
            {
                return initialValue();
            }

            protected SoftReference initialValue()
            {
                return new SoftReference(new HashMap());
            }

        };


        DateFormatHolder()
        {
        }
    }


    private DateUtils()
    {
    }

    public static void clearThreadLocal()
    {
        DateFormatHolder.clearThreadLocal();
    }

    public static String formatDate(Date date)
    {
        return formatDate(date, "EEE, dd MMM yyyy HH:mm:ss zzz");
    }

    public static String formatDate(Date date, String s)
    {
        Args.notNull(date, "Date");
        Args.notNull(s, "Pattern");
        return DateFormatHolder.formatFor(s).format(date);
    }

    public static Date parseDate(String s)
    {
        return parseDate(s, null, null);
    }

    public static Date parseDate(String s, String as[])
    {
        return parseDate(s, as, null);
    }

    public static Date parseDate(String s, String as[], Date date)
    {
        Args.notNull(s, "Date value");
        String as1[];
        Date date1;
        String s1;
        String as2[];
        int i;
        if(as != null)
            as1 = as;
        else
            as1 = DEFAULT_PATTERNS;
        if(date != null)
            date1 = date;
        else
            date1 = DEFAULT_TWO_DIGIT_YEAR_START;
        s1 = s;
        if(s1.length() > 1 && s1.startsWith("'") && s1.endsWith("'"))
            s1 = s1.substring(1, -1 + s1.length());
        as2 = as1;
        i = as2.length;
        for(int j = 0; j < i; j++)
        {
            SimpleDateFormat simpledateformat = DateFormatHolder.formatFor(as2[j]);
            simpledateformat.set2DigitYearStart(date1);
            ParsePosition parseposition = new ParsePosition(0);
            Date date2 = simpledateformat.parse(s1, parseposition);
            if(parseposition.getIndex() != 0)
                return date2;
        }

        return null;
    }

    private static final String DEFAULT_PATTERNS[] = {
        "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"
    };
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final TimeZone GMT;
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    public static final String PATTERN_RFC1036 = "EEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    static 
    {
        GMT = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(GMT);
        calendar.set(2000, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
    }
}
