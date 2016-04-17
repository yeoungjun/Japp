// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.time;

import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class DurationFormatUtils
{
    static class Token
    {

        static boolean containsTokenWithValue(Token atoken[], Object obj)
        {
            int i = atoken.length;
            for(int j = 0; j < i; j++)
                if(atoken[j].getValue() == obj)
                    return true;

            return false;
        }

        public boolean equals(Object obj)
        {
            Token token;
            if(obj instanceof Token)
            {
                token = (Token)obj;
                break MISSING_BLOCK_LABEL_12;
            }
_L1:
            return false;
            if(value.getClass() == token.value.getClass() && count == token.count)
            {
                if(value instanceof StringBuffer)
                    return value.toString().equals(token.value.toString());
                if(value instanceof Number)
                    return value.equals(token.value);
                if(value == token.value)
                    return true;
            }
              goto _L1
        }

        int getCount()
        {
            return count;
        }

        Object getValue()
        {
            return value;
        }

        public int hashCode()
        {
            return value.hashCode();
        }

        void increment()
        {
            count = 1 + count;
        }

        public String toString()
        {
            return StringUtils.repeat(value.toString(), count);
        }

        private int count;
        private final Object value;

        Token(Object obj)
        {
            value = obj;
            count = 1;
        }

        Token(Object obj, int i)
        {
            value = obj;
            count = i;
        }
    }


    public DurationFormatUtils()
    {
    }

    static String format(Token atoken[], int i, int j, int k, int l, int i1, int j1, int k1, 
            boolean flag)
    {
        StringBuffer stringbuffer = new StringBuffer();
        boolean flag1 = false;
        int l1 = atoken.length;
        int i2 = 0;
        do
        {
            if(i2 < l1)
            {
                Token token = atoken[i2];
                Object obj = token.getValue();
                int j2 = token.getCount();
                if(obj instanceof StringBuffer)
                    stringbuffer.append(obj.toString());
                else
                if(obj == y)
                {
                    String s8;
                    if(flag)
                        s8 = StringUtils.leftPad(Integer.toString(i), j2, '0');
                    else
                        s8 = Integer.toString(i);
                    stringbuffer.append(s8);
                    flag1 = false;
                } else
                if(obj == M)
                {
                    String s7;
                    if(flag)
                        s7 = StringUtils.leftPad(Integer.toString(j), j2, '0');
                    else
                        s7 = Integer.toString(j);
                    stringbuffer.append(s7);
                    flag1 = false;
                } else
                if(obj == d)
                {
                    String s6;
                    if(flag)
                        s6 = StringUtils.leftPad(Integer.toString(k), j2, '0');
                    else
                        s6 = Integer.toString(k);
                    stringbuffer.append(s6);
                    flag1 = false;
                } else
                if(obj == H)
                {
                    String s5;
                    if(flag)
                        s5 = StringUtils.leftPad(Integer.toString(l), j2, '0');
                    else
                        s5 = Integer.toString(l);
                    stringbuffer.append(s5);
                    flag1 = false;
                } else
                if(obj == m)
                {
                    String s4;
                    if(flag)
                        s4 = StringUtils.leftPad(Integer.toString(i1), j2, '0');
                    else
                        s4 = Integer.toString(i1);
                    stringbuffer.append(s4);
                    flag1 = false;
                } else
                if(obj == s)
                {
                    String s3;
                    if(flag)
                        s3 = StringUtils.leftPad(Integer.toString(j1), j2, '0');
                    else
                        s3 = Integer.toString(j1);
                    stringbuffer.append(s3);
                    flag1 = true;
                } else
                if(obj == S)
                {
                    if(flag1)
                    {
                        k1 += 1000;
                        String s2;
                        if(flag)
                            s2 = StringUtils.leftPad(Integer.toString(k1), j2, '0');
                        else
                            s2 = Integer.toString(k1);
                        stringbuffer.append(s2.substring(1));
                    } else
                    {
                        String s1;
                        if(flag)
                            s1 = StringUtils.leftPad(Integer.toString(k1), j2, '0');
                        else
                            s1 = Integer.toString(k1);
                        stringbuffer.append(s1);
                    }
                    flag1 = false;
                }
            } else
            {
                return stringbuffer.toString();
            }
            i2++;
        } while(true);
    }

    public static String formatDuration(long l, String s1)
    {
        return formatDuration(l, s1, true);
    }

    public static String formatDuration(long l, String s1, boolean flag)
    {
        Token atoken[] = lexx(s1);
        boolean flag1 = Token.containsTokenWithValue(atoken, d);
        int i = 0;
        if(flag1)
        {
            i = (int)(l / 0x5265c00L);
            l -= 0x5265c00L * (long)i;
        }
        boolean flag2 = Token.containsTokenWithValue(atoken, H);
        int j = 0;
        if(flag2)
        {
            j = (int)(l / 0x36ee80L);
            l -= 0x36ee80L * (long)j;
        }
        boolean flag3 = Token.containsTokenWithValue(atoken, m);
        int k = 0;
        if(flag3)
        {
            k = (int)(l / 60000L);
            l -= 60000L * (long)k;
        }
        boolean flag4 = Token.containsTokenWithValue(atoken, s);
        int i1 = 0;
        if(flag4)
        {
            i1 = (int)(l / 1000L);
            l -= 1000L * (long)i1;
        }
        boolean flag5 = Token.containsTokenWithValue(atoken, S);
        int j1 = 0;
        if(flag5)
            j1 = (int)l;
        return format(atoken, 0, 0, i, j, k, i1, j1, flag);
    }

    public static String formatDurationHMS(long l)
    {
        return formatDuration(l, "H:mm:ss.SSS");
    }

    public static String formatDurationISO(long l)
    {
        return formatDuration(l, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
    }

    public static String formatDurationWords(long l, boolean flag, boolean flag1)
    {
        String s1 = formatDuration(l, "d' days 'H' hours 'm' minutes 's' seconds'");
        if(flag)
        {
            s1 = (new StringBuilder()).append(" ").append(s1).toString();
            String s5 = StringUtils.replaceOnce(s1, " 0 days", "");
            if(s5.length() != s1.length())
            {
                s1 = s5;
                String s6 = StringUtils.replaceOnce(s1, " 0 hours", "");
                if(s6.length() != s1.length())
                {
                    String s7 = StringUtils.replaceOnce(s6, " 0 minutes", "");
                    s1 = s7;
                    if(s7.length() != s1.length())
                        s1 = StringUtils.replaceOnce(s7, " 0 seconds", "");
                }
            }
            if(s1.length() != 0)
                s1 = s1.substring(1);
        }
        if(flag1)
        {
            String s2 = StringUtils.replaceOnce(s1, " 0 seconds", "");
            if(s2.length() != s1.length())
            {
                s1 = s2;
                String s3 = StringUtils.replaceOnce(s1, " 0 minutes", "");
                if(s3.length() != s1.length())
                {
                    s1 = s3;
                    String s4 = StringUtils.replaceOnce(s1, " 0 hours", "");
                    if(s4.length() != s1.length())
                        s1 = StringUtils.replaceOnce(s4, " 0 days", "");
                }
            }
        }
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce((new StringBuilder()).append(" ").append(s1).toString(), " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriod(long l, long l1, String s1)
    {
        return formatPeriod(l, l1, s1, true, TimeZone.getDefault());
    }

    public static String formatPeriod(long l, long l1, String s1, boolean flag, TimeZone timezone)
    {
        Token atoken[] = lexx(s1);
        Calendar calendar = Calendar.getInstance(timezone);
        calendar.setTime(new Date(l));
        Calendar calendar1 = Calendar.getInstance(timezone);
        calendar1.setTime(new Date(l1));
        int i = calendar1.get(14) - calendar.get(14);
        int j = calendar1.get(13) - calendar.get(13);
        int k = calendar1.get(12) - calendar.get(12);
        int i1 = calendar1.get(11) - calendar.get(11);
        int j1 = calendar1.get(5) - calendar.get(5);
        int k1 = calendar1.get(2) - calendar.get(2);
        int i2 = calendar1.get(1) - calendar.get(1);
        while(i < 0) 
        {
            i += 1000;
            j--;
        }
        while(j < 0) 
        {
            j += 60;
            k--;
        }
        while(k < 0) 
        {
            k += 60;
            i1--;
        }
        while(i1 < 0) 
        {
            i1 += 24;
            j1--;
        }
        if(Token.containsTokenWithValue(atoken, M))
        {
            while(j1 < 0) 
            {
                j1 += calendar.getActualMaximum(5);
                k1--;
                calendar.add(2, 1);
            }
            while(k1 < 0) 
            {
                k1 += 12;
                i2--;
            }
            if(!Token.containsTokenWithValue(atoken, y) && i2 != 0)
                for(; i2 != 0; i2 = 0)
                    k1 += i2 * 12;

        } else
        {
            if(!Token.containsTokenWithValue(atoken, y))
            {
                int j2 = calendar1.get(1);
                if(k1 < 0)
                    j2--;
                while(calendar.get(1) != j2) 
                {
                    int k2 = j1 + (calendar.getActualMaximum(6) - calendar.get(6));
                    if((calendar instanceof GregorianCalendar) && calendar.get(2) == 1 && calendar.get(5) == 29)
                        k2++;
                    calendar.add(1, 1);
                    j1 = k2 + calendar.get(6);
                }
                i2 = 0;
            }
            for(; calendar.get(2) != calendar1.get(2); calendar.add(2, 1))
                j1 += calendar.getActualMaximum(5);

            k1 = 0;
            while(j1 < 0) 
            {
                j1 += calendar.getActualMaximum(5);
                k1--;
                calendar.add(2, 1);
            }
        }
        if(!Token.containsTokenWithValue(atoken, d))
        {
            i1 += j1 * 24;
            j1 = 0;
        }
        if(!Token.containsTokenWithValue(atoken, H))
        {
            k += i1 * 60;
            i1 = 0;
        }
        if(!Token.containsTokenWithValue(atoken, m))
        {
            j += k * 60;
            k = 0;
        }
        if(!Token.containsTokenWithValue(atoken, s))
        {
            i += j * 1000;
            j = 0;
        }
        return format(atoken, i2, k1, j1, i1, k, j, i, flag);
    }

    public static String formatPeriodISO(long l, long l1)
    {
        return formatPeriod(l, l1, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, TimeZone.getDefault());
    }

    static Token[] lexx(String s1)
    {
        char ac[];
        ArrayList arraylist;
        boolean flag;
        StringBuffer stringbuffer;
        Token token;
        int i;
        int j;
        ac = s1.toCharArray();
        arraylist = new ArrayList(ac.length);
        flag = false;
        stringbuffer = null;
        token = null;
        i = ac.length;
        j = 0;
_L2:
        char c;
        if(j >= i)
            break MISSING_BLOCK_LABEL_342;
        c = ac[j];
        if(!flag || c == '\'')
            break; /* Loop/switch isn't completed */
        stringbuffer.append(c);
_L12:
        j++;
        if(true) goto _L2; else goto _L1
_L1:
        Object obj = null;
        c;
        JVM INSTR lookupswitch 8: default 148
    //                   39: 215
    //                   72: 285
    //                   77: 269
    //                   83: 309
    //                   100: 277
    //                   109: 293
    //                   115: 301
    //                   121: 261;
           goto _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11
_L7:
        break MISSING_BLOCK_LABEL_309;
_L3:
        if(stringbuffer == null)
        {
            stringbuffer = new StringBuffer();
            arraylist.add(new Token(stringbuffer));
        }
        stringbuffer.append(c);
_L13:
        if(obj != null)
        {
            if(token != null && token.getValue() == obj)
            {
                token.increment();
            } else
            {
                Token token1 = new Token(obj);
                arraylist.add(token1);
                token = token1;
            }
            stringbuffer = null;
        }
          goto _L12
_L4:
        if(flag)
        {
            stringbuffer = null;
            flag = false;
            obj = null;
        } else
        {
            stringbuffer = new StringBuffer();
            arraylist.add(new Token(stringbuffer));
            flag = true;
            obj = null;
        }
          goto _L13
_L11:
        obj = y;
          goto _L13
_L6:
        obj = M;
          goto _L13
_L8:
        obj = d;
          goto _L13
_L5:
        obj = H;
          goto _L13
_L9:
        obj = m;
          goto _L13
_L10:
        obj = s;
          goto _L13
        obj = S;
          goto _L13
        return (Token[])arraylist.toArray(new Token[arraylist.size()]);
          goto _L12
    }

    static final Object H = "H";
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
    static final Object M = "M";
    static final Object S = "S";
    static final Object d = "d";
    static final Object m = "m";
    static final Object s = "s";
    static final Object y = "y";

}
