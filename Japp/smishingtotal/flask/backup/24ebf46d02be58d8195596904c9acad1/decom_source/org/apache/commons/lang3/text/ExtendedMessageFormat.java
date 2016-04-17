// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3.text;

import java.text.*;
import java.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

// Referenced classes of package org.apache.commons.lang3.text:
//            FormatFactory, StrMatcher

public class ExtendedMessageFormat extends MessageFormat
{

    public ExtendedMessageFormat(String s)
    {
        this(s, Locale.getDefault());
    }

    public ExtendedMessageFormat(String s, Locale locale)
    {
        this(s, locale, null);
    }

    public ExtendedMessageFormat(String s, Locale locale, Map map)
    {
        super("");
        setLocale(locale);
        registry = map;
        applyPattern(s);
    }

    public ExtendedMessageFormat(String s, Map map)
    {
        this(s, Locale.getDefault(), map);
    }

    private StringBuilder appendQuotedString(String s, ParsePosition parseposition, StringBuilder stringbuilder, boolean flag)
    {
        int i;
        char ac[];
        i = parseposition.getIndex();
        ac = s.toCharArray();
        if(!flag || ac[i] != '\'') goto _L2; else goto _L1
_L1:
        next(parseposition);
        if(stringbuilder != null) goto _L4; else goto _L3
_L3:
        return null;
_L4:
        return stringbuilder.append('\'');
_L2:
        int j = i;
        int k = parseposition.getIndex();
        while(k < s.length()) 
        {
            if(flag && s.substring(k).startsWith("''"))
            {
                stringbuilder.append(ac, j, parseposition.getIndex() - j).append('\'');
                parseposition.setIndex(k + "''".length());
                j = parseposition.getIndex();
            } else
            {
                switch(ac[parseposition.getIndex()])
                {
                default:
                    next(parseposition);
                    break;

                case 39: // '\''
                    next(parseposition);
                    if(stringbuilder != null)
                        return stringbuilder.append(ac, j, parseposition.getIndex() - j);
                    continue; /* Loop/switch isn't completed */
                }
            }
            k++;
        }
        throw new IllegalArgumentException((new StringBuilder()).append("Unterminated quoted string at position ").append(i).toString());
        if(true) goto _L3; else goto _L5
_L5:
    }

    private boolean containsElements(Collection collection)
    {
        if(collection != null && !collection.isEmpty())
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext()) 
                if(iterator.next() != null)
                    return true;
        }
        return false;
    }

    private Format getFormat(String s)
    {
        if(registry != null)
        {
            String s1 = s;
            int i = s.indexOf(',');
            String s2 = null;
            if(i > 0)
            {
                s1 = s.substring(0, i).trim();
                s2 = s.substring(i + 1).trim();
            }
            FormatFactory formatfactory = (FormatFactory)registry.get(s1);
            if(formatfactory != null)
                return formatfactory.getFormat(s1, s2, getLocale());
        }
        return null;
    }

    private void getQuotedString(String s, ParsePosition parseposition, boolean flag)
    {
        appendQuotedString(s, parseposition, null, flag);
    }

    private String insertFormats(String s, ArrayList arraylist)
    {
        StringBuilder stringbuilder;
        ParsePosition parseposition;
        int i;
        int j;
        if(!containsElements(arraylist))
            return s;
        stringbuilder = new StringBuilder(2 * s.length());
        parseposition = new ParsePosition(0);
        i = -1;
        j = 0;
_L8:
        if(parseposition.getIndex() >= s.length()) goto _L2; else goto _L1
_L1:
        char c = s.charAt(parseposition.getIndex());
        c;
        JVM INSTR lookupswitch 3: default 100
    //                   39: 117
    //                   123: 130
    //                   125: 194;
           goto _L3 _L4 _L5 _L6
_L3:
        stringbuilder.append(c);
        next(parseposition);
        continue; /* Loop/switch isn't completed */
_L4:
        appendQuotedString(s, parseposition, stringbuilder, false);
        continue; /* Loop/switch isn't completed */
_L5:
        if(++j == 1)
        {
            i++;
            stringbuilder.append('{').append(readArgumentIndex(s, next(parseposition)));
            String s1 = (String)arraylist.get(i);
            if(s1 != null)
                stringbuilder.append(',').append(s1);
        }
        continue; /* Loop/switch isn't completed */
_L6:
        j--;
        if(true) goto _L3; else goto _L2
_L2:
        return stringbuilder.toString();
        if(true) goto _L8; else goto _L7
_L7:
    }

    private ParsePosition next(ParsePosition parseposition)
    {
        parseposition.setIndex(1 + parseposition.getIndex());
        return parseposition;
    }

    private String parseFormatDescription(String s, ParsePosition parseposition)
    {
        int i;
        int j;
        int k;
        i = parseposition.getIndex();
        seekNonWs(s, parseposition);
        j = parseposition.getIndex();
        k = 1;
_L6:
        if(parseposition.getIndex() >= s.length())
            break MISSING_BLOCK_LABEL_116;
        s.charAt(parseposition.getIndex());
        JVM INSTR lookupswitch 3: default 72
    //                   39: 106
    //                   123: 81
    //                   125: 87;
           goto _L1 _L2 _L3 _L4
_L2:
        break MISSING_BLOCK_LABEL_106;
_L1:
        break; /* Loop/switch isn't completed */
_L3:
        break; /* Loop/switch isn't completed */
_L7:
        next(parseposition);
        if(true) goto _L6; else goto _L5
_L5:
        k++;
          goto _L7
_L4:
        if(--k == 0)
            return s.substring(j, parseposition.getIndex());
          goto _L7
        getQuotedString(s, parseposition, false);
          goto _L7
        throw new IllegalArgumentException((new StringBuilder()).append("Unterminated format element at position ").append(i).toString());
    }

    private int readArgumentIndex(String s, ParsePosition parseposition)
    {
        int i;
        StringBuffer stringbuffer;
        boolean flag;
        i = parseposition.getIndex();
        seekNonWs(s, parseposition);
        stringbuffer = new StringBuffer();
        flag = false;
_L2:
        char c;
        if(flag || parseposition.getIndex() >= s.length())
            break MISSING_BLOCK_LABEL_164;
        c = s.charAt(parseposition.getIndex());
        if(!Character.isWhitespace(c))
            break; /* Loop/switch isn't completed */
        seekNonWs(s, parseposition);
        c = s.charAt(parseposition.getIndex());
        if(c == ',' || c == '}')
            break; /* Loop/switch isn't completed */
        flag = true;
_L3:
        next(parseposition);
        if(true) goto _L2; else goto _L1
_L1:
        if(c != ',' && c != '}' || stringbuffer.length() <= 0)
            break MISSING_BLOCK_LABEL_136;
        int j = Integer.parseInt(stringbuffer.toString());
        return j;
        NumberFormatException numberformatexception;
        numberformatexception;
        if(!Character.isDigit(c))
            flag = true;
        else
            flag = false;
        stringbuffer.append(c);
          goto _L3
        if(flag)
            throw new IllegalArgumentException((new StringBuilder()).append("Invalid format argument index at position ").append(i).append(": ").append(s.substring(i, parseposition.getIndex())).toString());
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Unterminated format element at position ").append(i).toString());
    }

    private void seekNonWs(String s, ParsePosition parseposition)
    {
        char ac[] = s.toCharArray();
        int i;
        do
        {
            i = StrMatcher.splitMatcher().isMatch(ac, parseposition.getIndex());
            parseposition.setIndex(i + parseposition.getIndex());
        } while(i > 0 && parseposition.getIndex() < s.length());
    }

    public final void applyPattern(String s)
    {
        if(registry != null) goto _L2; else goto _L1
_L1:
        super.applyPattern(s);
        toPattern = super.toPattern();
_L11:
        return;
_L2:
        ArrayList arraylist;
        ArrayList arraylist1;
        StringBuilder stringbuilder;
        ParsePosition parseposition;
        char ac[];
        int i;
        arraylist = new ArrayList();
        arraylist1 = new ArrayList();
        stringbuilder = new StringBuilder(s.length());
        parseposition = new ParsePosition(0);
        ac = s.toCharArray();
        i = 0;
_L8:
        if(parseposition.getIndex() >= s.length()) goto _L4; else goto _L3
_L3:
        ac[parseposition.getIndex()];
        JVM INSTR lookupswitch 2: default 116
    //                   39: 140
    //                   123: 154;
           goto _L5 _L6 _L7
_L5:
        stringbuilder.append(ac[parseposition.getIndex()]);
        next(parseposition);
          goto _L8
_L6:
        appendQuotedString(s, parseposition, stringbuilder, true);
          goto _L8
_L7:
        i++;
        seekNonWs(s, parseposition);
        int k = parseposition.getIndex();
        int l = readArgumentIndex(s, next(parseposition));
        stringbuilder.append('{').append(l);
        seekNonWs(s, parseposition);
        char c = ac[parseposition.getIndex()];
        Format format1 = null;
        String s1 = null;
        if(c == ',')
        {
            s1 = parseFormatDescription(s, next(parseposition));
            format1 = getFormat(s1);
            if(format1 == null)
                stringbuilder.append(',').append(s1);
        }
        arraylist.add(format1);
        if(format1 == null)
            s1 = null;
        arraylist1.add(s1);
        boolean flag;
        boolean flag1;
        if(arraylist.size() == i)
            flag = true;
        else
            flag = false;
        Validate.isTrue(flag);
        if(arraylist1.size() == i)
            flag1 = true;
        else
            flag1 = false;
        Validate.isTrue(flag1);
        if(ac[parseposition.getIndex()] == '}') goto _L5; else goto _L9
_L9:
        throw new IllegalArgumentException((new StringBuilder()).append("Unreadable format element at position ").append(k).toString());
_L4:
        super.applyPattern(stringbuilder.toString());
        toPattern = insertFormats(super.toPattern(), arraylist1);
        if(containsElements(arraylist))
        {
            Format aformat[] = getFormats();
            int j = 0;
            for(Iterator iterator = arraylist.iterator(); iterator.hasNext();)
            {
                Format format = (Format)iterator.next();
                if(format != null)
                    aformat[j] = format;
                j++;
            }

            super.setFormats(aformat);
            return;
        }
        if(true) goto _L11; else goto _L10
_L10:
    }

    public boolean equals(Object obj)
    {
        if(obj != this)
        {
            if(obj == null)
                return false;
            if(!super.equals(obj))
                return false;
            if(ObjectUtils.notEqual(getClass(), obj.getClass()))
                return false;
            ExtendedMessageFormat extendedmessageformat = (ExtendedMessageFormat)obj;
            if(ObjectUtils.notEqual(toPattern, extendedmessageformat.toPattern))
                return false;
            if(ObjectUtils.notEqual(registry, extendedmessageformat.registry))
                return false;
        }
        return true;
    }

    public int hashCode()
    {
        return 31 * (31 * super.hashCode() + ObjectUtils.hashCode(registry)) + ObjectUtils.hashCode(toPattern);
    }

    public void setFormat(int i, Format format)
    {
        throw new UnsupportedOperationException();
    }

    public void setFormatByArgumentIndex(int i, Format format)
    {
        throw new UnsupportedOperationException();
    }

    public void setFormats(Format aformat[])
    {
        throw new UnsupportedOperationException();
    }

    public void setFormatsByArgumentIndex(Format aformat[])
    {
        throw new UnsupportedOperationException();
    }

    public String toPattern()
    {
        return toPattern;
    }

    private static final String DUMMY_PATTERN = "";
    private static final char END_FE = 125;
    private static final String ESCAPED_QUOTE = "''";
    private static final int HASH_SEED = 31;
    private static final char QUOTE = 39;
    private static final char START_FE = 123;
    private static final char START_FMT = 44;
    private static final long serialVersionUID = 0xdf38519104e1c7e1L;
    private final Map registry;
    private String toPattern;
}
