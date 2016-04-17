// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.OctalUnescaper;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;

// Referenced classes of package org.apache.commons.lang3:
//            StringUtils

public class StringEscapeUtils
{
    static class CsvEscaper extends CharSequenceTranslator
    {

        public int translate(CharSequence charsequence, int i, Writer writer)
            throws IOException
        {
            if(i != 0)
                throw new IllegalStateException("CsvEscaper should never reach the [1] index");
            if(StringUtils.containsNone(charsequence.toString(), CSV_SEARCH_CHARS))
            {
                writer.write(charsequence.toString());
            } else
            {
                writer.write(34);
                writer.write(StringUtils.replace(charsequence.toString(), CSV_QUOTE_STR, (new StringBuilder()).append(CSV_QUOTE_STR).append(CSV_QUOTE_STR).toString()));
                writer.write(34);
            }
            return charsequence.length();
        }

        private static final char CSV_DELIMITER = 44;
        private static final char CSV_QUOTE = 34;
        private static final String CSV_QUOTE_STR = String.valueOf('"');
        private static final char CSV_SEARCH_CHARS[] = {
            ',', '"', '\r', '\n'
        };


        CsvEscaper()
        {
        }
    }

    static class CsvUnescaper extends CharSequenceTranslator
    {

        public int translate(CharSequence charsequence, int i, Writer writer)
            throws IOException
        {
            if(i != 0)
                throw new IllegalStateException("CsvUnescaper should never reach the [1] index");
            if(charsequence.charAt(0) != '"' || charsequence.charAt(-1 + charsequence.length()) != '"')
            {
                writer.write(charsequence.toString());
                return charsequence.length();
            }
            String s = charsequence.subSequence(1, -1 + charsequence.length()).toString();
            if(StringUtils.containsAny(s, CSV_SEARCH_CHARS))
                writer.write(StringUtils.replace(s, (new StringBuilder()).append(CSV_QUOTE_STR).append(CSV_QUOTE_STR).toString(), CSV_QUOTE_STR));
            else
                writer.write(charsequence.toString());
            return charsequence.length();
        }

        private static final char CSV_DELIMITER = 44;
        private static final char CSV_QUOTE = 34;
        private static final String CSV_QUOTE_STR = String.valueOf('"');
        private static final char CSV_SEARCH_CHARS[] = {
            ',', '"', '\r', '\n'
        };


        CsvUnescaper()
        {
        }
    }


    public StringEscapeUtils()
    {
    }

    public static final String escapeCsv(String s)
    {
        return ESCAPE_CSV.translate(s);
    }

    public static final String escapeEcmaScript(String s)
    {
        return ESCAPE_ECMASCRIPT.translate(s);
    }

    public static final String escapeHtml3(String s)
    {
        return ESCAPE_HTML3.translate(s);
    }

    public static final String escapeHtml4(String s)
    {
        return ESCAPE_HTML4.translate(s);
    }

    public static final String escapeJava(String s)
    {
        return ESCAPE_JAVA.translate(s);
    }

    public static final String escapeXml(String s)
    {
        return ESCAPE_XML.translate(s);
    }

    public static final String unescapeCsv(String s)
    {
        return UNESCAPE_CSV.translate(s);
    }

    public static final String unescapeEcmaScript(String s)
    {
        return UNESCAPE_ECMASCRIPT.translate(s);
    }

    public static final String unescapeHtml3(String s)
    {
        return UNESCAPE_HTML3.translate(s);
    }

    public static final String unescapeHtml4(String s)
    {
        return UNESCAPE_HTML4.translate(s);
    }

    public static final String unescapeJava(String s)
    {
        return UNESCAPE_JAVA.translate(s);
    }

    public static final String unescapeXml(String s)
    {
        return UNESCAPE_XML.translate(s);
    }

    public static final CharSequenceTranslator ESCAPE_CSV = new CsvEscaper();
    public static final CharSequenceTranslator ESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator ESCAPE_HTML3;
    public static final CharSequenceTranslator ESCAPE_HTML4;
    public static final CharSequenceTranslator ESCAPE_JAVA;
    public static final CharSequenceTranslator ESCAPE_XML;
    public static final CharSequenceTranslator UNESCAPE_CSV = new CsvUnescaper();
    public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator UNESCAPE_HTML3;
    public static final CharSequenceTranslator UNESCAPE_HTML4;
    public static final CharSequenceTranslator UNESCAPE_JAVA;
    public static final CharSequenceTranslator UNESCAPE_XML;

    static 
    {
        LookupTranslator lookuptranslator = new LookupTranslator(new String[][] {
            new String[] {
                "\"", "\\\""
            }, new String[] {
                "\\", "\\\\"
            }
        });
        CharSequenceTranslator acharsequencetranslator[] = new CharSequenceTranslator[1];
        acharsequencetranslator[0] = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE());
        CharSequenceTranslator charsequencetranslator = lookuptranslator.with(acharsequencetranslator);
        CharSequenceTranslator acharsequencetranslator1[] = new CharSequenceTranslator[1];
        acharsequencetranslator1[0] = UnicodeEscaper.outsideOf(32, 127);
        ESCAPE_JAVA = charsequencetranslator.with(acharsequencetranslator1);
        CharSequenceTranslator acharsequencetranslator2[] = new CharSequenceTranslator[3];
        acharsequencetranslator2[0] = new LookupTranslator(new String[][] {
            new String[] {
                "'", "\\'"
            }, new String[] {
                "\"", "\\\""
            }, new String[] {
                "\\", "\\\\"
            }, new String[] {
                "/", "\\/"
            }
        });
        acharsequencetranslator2[1] = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE());
        acharsequencetranslator2[2] = UnicodeEscaper.outsideOf(32, 127);
        ESCAPE_ECMASCRIPT = new AggregateTranslator(acharsequencetranslator2);
        CharSequenceTranslator acharsequencetranslator3[] = new CharSequenceTranslator[2];
        acharsequencetranslator3[0] = new LookupTranslator(EntityArrays.BASIC_ESCAPE());
        acharsequencetranslator3[1] = new LookupTranslator(EntityArrays.APOS_ESCAPE());
        ESCAPE_XML = new AggregateTranslator(acharsequencetranslator3);
        CharSequenceTranslator acharsequencetranslator4[] = new CharSequenceTranslator[2];
        acharsequencetranslator4[0] = new LookupTranslator(EntityArrays.BASIC_ESCAPE());
        acharsequencetranslator4[1] = new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE());
        ESCAPE_HTML3 = new AggregateTranslator(acharsequencetranslator4);
        CharSequenceTranslator acharsequencetranslator5[] = new CharSequenceTranslator[3];
        acharsequencetranslator5[0] = new LookupTranslator(EntityArrays.BASIC_ESCAPE());
        acharsequencetranslator5[1] = new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE());
        acharsequencetranslator5[2] = new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE());
        ESCAPE_HTML4 = new AggregateTranslator(acharsequencetranslator5);
        CharSequenceTranslator acharsequencetranslator6[] = new CharSequenceTranslator[4];
        acharsequencetranslator6[0] = new OctalUnescaper();
        acharsequencetranslator6[1] = new UnicodeUnescaper();
        acharsequencetranslator6[2] = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE());
        acharsequencetranslator6[3] = new LookupTranslator(new String[][] {
            new String[] {
                "\\\\", "\\"
            }, new String[] {
                "\\\"", "\""
            }, new String[] {
                "\\'", "'"
            }, new String[] {
                "\\", ""
            }
        });
        UNESCAPE_JAVA = new AggregateTranslator(acharsequencetranslator6);
        UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;
        CharSequenceTranslator acharsequencetranslator7[] = new CharSequenceTranslator[3];
        acharsequencetranslator7[0] = new LookupTranslator(EntityArrays.BASIC_UNESCAPE());
        acharsequencetranslator7[1] = new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE());
        acharsequencetranslator7[2] = new NumericEntityUnescaper(new org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION[0]);
        UNESCAPE_HTML3 = new AggregateTranslator(acharsequencetranslator7);
        CharSequenceTranslator acharsequencetranslator8[] = new CharSequenceTranslator[4];
        acharsequencetranslator8[0] = new LookupTranslator(EntityArrays.BASIC_UNESCAPE());
        acharsequencetranslator8[1] = new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE());
        acharsequencetranslator8[2] = new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE());
        acharsequencetranslator8[3] = new NumericEntityUnescaper(new org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION[0]);
        UNESCAPE_HTML4 = new AggregateTranslator(acharsequencetranslator8);
        CharSequenceTranslator acharsequencetranslator9[] = new CharSequenceTranslator[3];
        acharsequencetranslator9[0] = new LookupTranslator(EntityArrays.BASIC_UNESCAPE());
        acharsequencetranslator9[1] = new LookupTranslator(EntityArrays.APOS_UNESCAPE());
        acharsequencetranslator9[2] = new NumericEntityUnescaper(new org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION[0]);
        UNESCAPE_XML = new AggregateTranslator(acharsequencetranslator9);
    }
}
