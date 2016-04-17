// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.io.PrintStream;
import java.text.*;
import java.util.*;

// Referenced classes of package javax.mail.internet:
//            MailDateParser

public class MailDateFormat extends SimpleDateFormat
{

    public MailDateFormat()
    {
        super("EEE, d MMM yyyy HH:mm:ss 'XXXXX' (z)", Locale.US);
    }

    private static Date ourUTC(int i, int j, int k, int l, int i1, int j1, int k1, boolean flag)
    {
        javax/mail/internet/MailDateFormat;
        JVM INSTR monitorenter ;
        Date date;
        cal.clear();
        cal.setLenient(flag);
        cal.set(1, i);
        cal.set(2, j);
        cal.set(5, k);
        cal.set(11, l);
        cal.set(12, i1 + k1);
        cal.set(13, j1);
        date = cal.getTime();
        javax/mail/internet/MailDateFormat;
        JVM INSTR monitorexit ;
        return date;
        Exception exception;
        exception;
        throw exception;
    }

    private static Date parseDate(char ac[], ParsePosition parseposition, boolean flag)
    {
        MailDateParser maildateparser;
        int i;
        int j;
        int k;
        maildateparser = new MailDateParser(ac);
        maildateparser.skipUntilNumber();
        i = maildateparser.parseNumber();
        if(!maildateparser.skipIfChar('-'))
            maildateparser.skipWhiteSpace();
        j = maildateparser.parseMonth();
        if(!maildateparser.skipIfChar('-'))
            maildateparser.skipWhiteSpace();
        k = maildateparser.parseNumber();
        if(k >= 50) goto _L2; else goto _L1
_L1:
        k += 2000;
_L6:
        int l;
        int i1;
        boolean flag1;
        maildateparser.skipWhiteSpace();
        l = maildateparser.parseNumber();
        maildateparser.skipChar(':');
        i1 = maildateparser.parseNumber();
        flag1 = maildateparser.skipIfChar(':');
        int j1;
        j1 = 0;
        if(!flag1)
            break MISSING_BLOCK_LABEL_118;
        int k1 = maildateparser.parseNumber();
        j1 = k1;
        int i2;
        maildateparser.skipWhiteSpace();
        i2 = maildateparser.parseTimeZone();
        int l1 = i2;
_L4:
        ParseException parseexception;
        boolean flag2;
        try
        {
            parseposition.setIndex(maildateparser.getIndex());
            return ourUTC(k, j, i, l, i1, j1, l1, flag);
        }
        catch(Exception exception)
        {
            if(debug)
            {
                System.out.println((new StringBuilder("Bad date: '")).append(new String(ac)).append("'").toString());
                exception.printStackTrace();
            }
        }
        break MISSING_BLOCK_LABEL_261;
        parseexception;
        flag2 = debug;
        l1 = 0;
        if(!flag2) goto _L4; else goto _L3
_L3:
        System.out.println((new StringBuilder("No timezone? : '")).append(new String(ac)).append("'").toString());
        l1 = 0;
          goto _L4
        parseposition.setIndex(1);
        return null;
_L2:
        if(k < 100)
            k += 1900;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public StringBuffer format(Date date, StringBuffer stringbuffer, FieldPosition fieldposition)
    {
        int i = stringbuffer.length();
        super.format(date, stringbuffer, fieldposition);
        int j = i + 25;
        do
        {
            if(stringbuffer.charAt(j) == 'X')
            {
                calendar.clear();
                calendar.setTime(date);
                int k = calendar.get(15) + calendar.get(16);
                int i1;
                int j1;
                int k1;
                int l1;
                int i2;
                int j2;
                int k2;
                if(k < 0)
                {
                    int l2 = j + 1;
                    stringbuffer.setCharAt(j, '-');
                    k = -k;
                    i1 = l2;
                } else
                {
                    int l = j + 1;
                    stringbuffer.setCharAt(j, '+');
                    i1 = l;
                }
                j1 = k / 60 / 1000;
                k1 = j1 / 60;
                l1 = j1 % 60;
                i2 = i1 + 1;
                stringbuffer.setCharAt(i1, Character.forDigit(k1 / 10, 10));
                j2 = i2 + 1;
                stringbuffer.setCharAt(i2, Character.forDigit(k1 % 10, 10));
                k2 = j2 + 1;
                stringbuffer.setCharAt(j2, Character.forDigit(l1 / 10, 10));
                int _tmp = k2 + 1;
                stringbuffer.setCharAt(k2, Character.forDigit(l1 % 10, 10));
                return stringbuffer;
            }
            j++;
        } while(true);
    }

    public Date parse(String s, ParsePosition parseposition)
    {
        return parseDate(s.toCharArray(), parseposition, isLenient());
    }

    public void setCalendar(Calendar calendar)
    {
        throw new RuntimeException("Method setCalendar() shouldn't be called");
    }

    public void setNumberFormat(NumberFormat numberformat)
    {
        throw new RuntimeException("Method setNumberFormat() shouldn't be called");
    }

    private static Calendar cal;
    static boolean debug = false;
    private static final long serialVersionUID = 0x8eebae2a0a637d55L;
    private static TimeZone tz;

    static 
    {
        debug = false;
        tz = TimeZone.getTimeZone("GMT");
        cal = new GregorianCalendar(tz);
    }
}
