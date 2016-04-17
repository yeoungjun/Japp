// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.util.*;

// Referenced classes of package javax.mail.internet:
//            ParseException, HeaderTokenizer, MimeUtility

public class ParameterList
{
    private static class MultiValue extends ArrayList
    {

        String value;

        private MultiValue()
        {
        }

        MultiValue(MultiValue multivalue)
        {
            this();
        }
    }

    private static class ParamEnum
        implements Enumeration
    {

        public boolean hasMoreElements()
        {
            return it.hasNext();
        }

        public Object nextElement()
        {
            return it.next();
        }

        private Iterator it;

        ParamEnum(Iterator iterator)
        {
            it = iterator;
        }
    }

    private static class ToStringBuffer
    {

        public void addNV(String s, String s1)
        {
            String s2 = ParameterList.quote(s1);
            sb.append("; ");
            used = 2 + used;
            if(1 + (s.length() + s2.length()) + used > 76)
            {
                sb.append("\r\n\t");
                used = 8;
            }
            sb.append(s).append('=');
            used = used + (1 + s.length());
            if(used + s2.length() > 76)
            {
                String s3 = MimeUtility.fold(used, s2);
                sb.append(s3);
                int i = s3.lastIndexOf('\n');
                if(i >= 0)
                {
                    used = used + (-1 + (s3.length() - i));
                    return;
                } else
                {
                    used = used + s3.length();
                    return;
                }
            } else
            {
                sb.append(s2);
                used = used + s2.length();
                return;
            }
        }

        public String toString()
        {
            return sb.toString();
        }

        private StringBuffer sb;
        private int used;

        public ToStringBuffer(int i)
        {
            sb = new StringBuffer();
            used = i;
        }
    }

    private static class Value
    {

        String charset;
        String encodedValue;
        String value;

        private Value()
        {
        }

        Value(Value value1)
        {
            this();
        }
    }


    public ParameterList()
    {
        list = new LinkedHashMap();
        lastName = null;
        if(decodeParameters)
        {
            multisegmentNames = new HashSet();
            slist = new HashMap();
        }
    }

    public ParameterList(String s)
        throws ParseException
    {
        HeaderTokenizer headertokenizer;
        this();
        headertokenizer = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
_L5:
        HeaderTokenizer.Token token;
        int i;
        token = headertokenizer.next();
        i = token.getType();
        if(i != -4) goto _L2; else goto _L1
_L1:
        if(decodeParameters)
            combineMultisegmentNames(false);
        return;
_L2:
        HeaderTokenizer.Token token1;
        if((char)i != ';')
            break; /* Loop/switch isn't completed */
        if((token1 = headertokenizer.next()).getType() != -4)
        {
            if(token1.getType() != -1)
                throw new ParseException((new StringBuilder("Expected parameter name, got \"")).append(token1.getValue()).append("\"").toString());
            String s2 = token1.getValue().toLowerCase(Locale.ENGLISH);
            HeaderTokenizer.Token token2 = headertokenizer.next();
            if((char)token2.getType() != '=')
                throw new ParseException((new StringBuilder("Expected '=', got \"")).append(token2.getValue()).append("\"").toString());
            HeaderTokenizer.Token token3 = headertokenizer.next();
            int j = token3.getType();
            if(j != -1 && j != -2)
                throw new ParseException((new StringBuilder("Expected parameter value, got \"")).append(token3.getValue()).append("\"").toString());
            String s3 = token3.getValue();
            lastName = s2;
            if(decodeParameters)
                putEncodedName(s2, s3);
            else
                list.put(s2, s3);
            continue; /* Loop/switch isn't completed */
        }
        if(true) goto _L1; else goto _L3
_L3:
        if(applehack && i == -1 && lastName != null && (lastName.equals("name") || lastName.equals("filename")))
        {
            String s1 = (new StringBuilder(String.valueOf((String)list.get(lastName)))).append(" ").append(token.getValue()).toString();
            list.put(lastName, s1);
        } else
        {
            throw new ParseException((new StringBuilder("Expected ';', got \"")).append(token.getValue()).append("\"").toString());
        }
        if(true) goto _L5; else goto _L4
_L4:
    }

    private void combineMultisegmentNames(boolean flag)
        throws ParseException
    {
        Iterator iterator1 = multisegmentNames.iterator();
_L13:
        boolean flag1 = iterator1.hasNext();
        if(flag1) goto _L2; else goto _L1
_L1:
        if(!flag && false) goto _L4; else goto _L3
_L3:
        if(slist.size() <= 0) goto _L6; else goto _L5
_L5:
        Iterator iterator2 = slist.values().iterator();
_L32:
        if(iterator2.hasNext()) goto _L8; else goto _L7
_L7:
        list.putAll(slist);
_L6:
        multisegmentNames.clear();
        slist.clear();
_L4:
        return;
_L2:
        String s;
        StringBuffer stringbuffer;
        MultiValue multivalue;
        s = (String)iterator1.next();
        stringbuffer = new StringBuffer();
        multivalue = new MultiValue(null);
        int i;
        String s1;
        i = 0;
        s1 = null;
_L24:
        String s2;
        Object obj1;
        s2 = (new StringBuilder(String.valueOf(s))).append("*").append(i).toString();
        obj1 = slist.get(s2);
        if(obj1 != null) goto _L10; else goto _L9
_L9:
        if(i != 0) goto _L12; else goto _L11
_L11:
        list.remove(s);
          goto _L13
        Exception exception;
        exception;
        if(!flag && true) goto _L15; else goto _L14
_L14:
        if(slist.size() <= 0) goto _L17; else goto _L16
_L16:
        Iterator iterator = slist.values().iterator();
_L31:
        if(iterator.hasNext()) goto _L19; else goto _L18
_L18:
        list.putAll(slist);
_L17:
        multisegmentNames.clear();
        slist.clear();
_L15:
        throw exception;
_L10:
        multivalue.add(obj1);
        String s3 = null;
        boolean flag2 = obj1 instanceof Value;
        if(!flag2) goto _L21; else goto _L20
_L20:
        Value value2;
        String s5;
        value2 = (Value)obj1;
        s5 = value2.encodedValue;
        s3 = s5;
        if(i != 0) goto _L23; else goto _L22
_L22:
        String s4;
        Value value3;
        value3 = decodeValue(s5);
        s4 = value3.charset;
        value2.charset = s4;
        String s6;
        s6 = value3.value;
        value2.value = s6;
        s3 = s6;
_L28:
        stringbuffer.append(s3);
        slist.remove(s2);
        i++;
        s1 = s4;
          goto _L24
_L23:
        if(s1 != null) goto _L26; else goto _L25
_L25:
        multisegmentNames.remove(s);
          goto _L9
        NumberFormatException numberformatexception;
        numberformatexception;
        s4 = s1;
_L35:
        if(!decodeParametersStrict) goto _L28; else goto _L27
_L27:
        throw new ParseException(numberformatexception.toString());
_L26:
        String s7;
        s7 = decodeBytes(s5, s1);
        value2.value = s7;
        s3 = s7;
        s4 = s1;
          goto _L28
        UnsupportedEncodingException unsupportedencodingexception;
        unsupportedencodingexception;
        s4 = s1;
_L34:
        if(!decodeParametersStrict) goto _L28; else goto _L29
_L29:
        throw new ParseException(unsupportedencodingexception.toString());
_L33:
        if(!decodeParametersStrict) goto _L28; else goto _L30
_L30:
        StringIndexOutOfBoundsException stringindexoutofboundsexception;
        throw new ParseException(stringindexoutofboundsexception.toString());
_L21:
        s3 = (String)obj1;
        s4 = s1;
          goto _L28
_L12:
        multivalue.value = stringbuffer.toString();
        list.put(s, multivalue);
          goto _L13
_L19:
        Object obj = iterator.next();
        if(obj instanceof Value)
        {
            Value value = (Value)obj;
            Value value1 = decodeValue(value.encodedValue);
            value.charset = value1.charset;
            value.value = value1.value;
        }
          goto _L31
_L8:
        Object obj2 = iterator2.next();
        if(obj2 instanceof Value)
        {
            Value value4 = (Value)obj2;
            Value value5 = decodeValue(value4.encodedValue);
            value4.charset = value5.charset;
            value4.value = value5.value;
        }
          goto _L32
        stringindexoutofboundsexception;
          goto _L33
        unsupportedencodingexception;
          goto _L34
        numberformatexception;
          goto _L35
        stringindexoutofboundsexception;
        s4 = s1;
          goto _L33
    }

    private static String decodeBytes(String s, String s1)
        throws UnsupportedEncodingException
    {
        byte abyte0[] = new byte[s.length()];
        int i = 0;
        int j = 0;
        do
        {
            if(i >= s.length())
                return new String(abyte0, 0, j, MimeUtility.javaCharset(s1));
            char c = s.charAt(i);
            if(c == '%')
            {
                c = (char)Integer.parseInt(s.substring(i + 1, i + 3), 16);
                i += 2;
            }
            int k = j + 1;
            abyte0[j] = (byte)c;
            i++;
            j = k;
        } while(true);
    }

    private static Value decodeValue(String s)
        throws ParseException
    {
        Value value;
        value = new Value(null);
        value.encodedValue = s;
        value.value = s;
        int i = s.indexOf('\'');
        if(i <= 0)
        {
            String s1;
            int j;
            int k;
            String s2;
            try
            {
                if(decodeParametersStrict)
                    throw new ParseException((new StringBuilder("Missing charset in encoded value: ")).append(s).toString());
            }
            catch(NumberFormatException numberformatexception)
            {
                if(decodeParametersStrict)
                    throw new ParseException(numberformatexception.toString());
            }
            catch(UnsupportedEncodingException unsupportedencodingexception)
            {
                if(decodeParametersStrict)
                    throw new ParseException(unsupportedencodingexception.toString());
            }
            catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
            {
                if(decodeParametersStrict)
                    throw new ParseException(stringindexoutofboundsexception.toString());
            }
            break MISSING_BLOCK_LABEL_202;
        }
        s1 = s.substring(0, i);
        j = s.indexOf('\'', i + 1);
        if(j >= 0)
            break MISSING_BLOCK_LABEL_160;
        if(decodeParametersStrict)
            throw new ParseException((new StringBuilder("Missing language in encoded value: ")).append(s).toString());
        break MISSING_BLOCK_LABEL_202;
        k = i + 1;
        s.substring(k, j);
        s2 = s.substring(j + 1);
        value.charset = s1;
        value.value = decodeBytes(s2, s1);
        return value;
    }

    private static Value encodeValue(String s, String s1)
    {
        if(MimeUtility.checkAscii(s) == 1)
            return null;
        byte abyte0[];
        StringBuffer stringbuffer;
        int i;
        try
        {
            abyte0 = s.getBytes(MimeUtility.javaCharset(s1));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return null;
        }
        stringbuffer = new StringBuffer(2 + (abyte0.length + s1.length()));
        stringbuffer.append(s1).append("''");
        i = 0;
        do
        {
            if(i >= abyte0.length)
            {
                Value value = new Value(null);
                value.charset = s1;
                value.value = s;
                value.encodedValue = stringbuffer.toString();
                return value;
            }
            char c = (char)(0xff & abyte0[i]);
            if(c <= ' ' || c >= '\177' || c == '*' || c == '\'' || c == '%' || "()<>@,;:\\\"\t []/?=".indexOf(c) >= 0)
                stringbuffer.append('%').append(hex[c >> 4]).append(hex[c & 0xf]);
            else
                stringbuffer.append(c);
            i++;
        } while(true);
    }

    private void putEncodedName(String s, String s1)
        throws ParseException
    {
        int i = s.indexOf('*');
        if(i < 0)
        {
            list.put(s, s1);
            return;
        }
        if(i == -1 + s.length())
        {
            String s3 = s.substring(0, i);
            list.put(s3, decodeValue(s1));
            return;
        }
        String s2 = s.substring(0, i);
        multisegmentNames.add(s2);
        list.put(s2, "");
        Object obj;
        if(s.endsWith("*"))
        {
            obj = new Value(null);
            ((Value)obj).encodedValue = s1;
            ((Value)obj).value = s1;
            s = s.substring(0, -1 + s.length());
        } else
        {
            obj = s1;
        }
        slist.put(s, obj);
    }

    private static String quote(String s)
    {
        return MimeUtility.quote(s, "()<>@,;:\\\"\t []/?=");
    }

    public String get(String s)
    {
        Object obj = list.get(s.trim().toLowerCase(Locale.ENGLISH));
        if(obj instanceof MultiValue)
            return ((MultiValue)obj).value;
        if(obj instanceof Value)
            return ((Value)obj).value;
        else
            return (String)obj;
    }

    public Enumeration getNames()
    {
        return new ParamEnum(list.keySet().iterator());
    }

    public void remove(String s)
    {
        list.remove(s.trim().toLowerCase(Locale.ENGLISH));
    }

    public void set(String s, String s1)
    {
        if(s != null || s1 == null || !s1.equals("DONE"))
            break MISSING_BLOCK_LABEL_42;
        if(!decodeParameters || multisegmentNames.size() <= 0)
            break MISSING_BLOCK_LABEL_41;
        combineMultisegmentNames(true);
        return;
        String s2 = s.trim().toLowerCase(Locale.ENGLISH);
        if(decodeParameters)
        {
            try
            {
                putEncodedName(s2, s1);
                return;
            }
            catch(ParseException parseexception)
            {
                list.put(s2, s1);
            }
            return;
        } else
        {
            list.put(s2, s1);
            return;
        }
        ParseException parseexception1;
        parseexception1;
    }

    public void set(String s, String s1, String s2)
    {
        if(encodeParameters)
        {
            Value value = encodeValue(s1, s2);
            if(value != null)
            {
                list.put(s.trim().toLowerCase(Locale.ENGLISH), value);
                return;
            } else
            {
                set(s, s1);
                return;
            }
        } else
        {
            set(s, s1);
            return;
        }
    }

    public int size()
    {
        return list.size();
    }

    public String toString()
    {
        return toString(0);
    }

    public String toString(int i)
    {
        ToStringBuffer tostringbuffer = new ToStringBuffer(i);
        Iterator iterator = list.keySet().iterator();
        do
        {
            if(!iterator.hasNext())
                return tostringbuffer.toString();
            String s = (String)iterator.next();
            Object obj = list.get(s);
            if(obj instanceof MultiValue)
            {
                MultiValue multivalue = (MultiValue)obj;
                String s1 = (new StringBuilder(String.valueOf(s))).append("*").toString();
                int j = 0;
                while(j < multivalue.size()) 
                {
                    Object obj1 = multivalue.get(j);
                    if(obj1 instanceof Value)
                        tostringbuffer.addNV((new StringBuilder(String.valueOf(s1))).append(j).append("*").toString(), ((Value)obj1).encodedValue);
                    else
                        tostringbuffer.addNV((new StringBuilder(String.valueOf(s1))).append(j).toString(), (String)obj1);
                    j++;
                }
            } else
            if(obj instanceof Value)
                tostringbuffer.addNV((new StringBuilder(String.valueOf(s))).append("*").toString(), ((Value)obj).encodedValue);
            else
                tostringbuffer.addNV(s, (String)obj);
        } while(true);
    }

    private static boolean applehack;
    private static boolean decodeParameters;
    private static boolean decodeParametersStrict;
    private static boolean encodeParameters;
    private static final char hex[];
    private String lastName;
    private Map list;
    private Set multisegmentNames;
    private Map slist;

    static 
    {
        boolean flag;
        flag = true;
        encodeParameters = false;
        decodeParameters = false;
        decodeParametersStrict = false;
        applehack = false;
        String s = System.getProperty("mail.mime.encodeparameters");
        if(s == null) goto _L2; else goto _L1
_L1:
        if(!s.equalsIgnoreCase("true")) goto _L2; else goto _L3
_L3:
        boolean flag1 = flag;
_L13:
        String s1;
        encodeParameters = flag1;
        s1 = System.getProperty("mail.mime.decodeparameters");
        if(s1 == null) goto _L5; else goto _L4
_L4:
        if(!s1.equalsIgnoreCase("true")) goto _L5; else goto _L6
_L6:
        boolean flag2 = flag;
_L14:
        String s2;
        decodeParameters = flag2;
        s2 = System.getProperty("mail.mime.decodeparameters.strict");
        if(s2 == null) goto _L8; else goto _L7
_L7:
        if(!s2.equalsIgnoreCase("true")) goto _L8; else goto _L9
_L9:
        boolean flag3 = flag;
_L15:
        String s3;
        decodeParametersStrict = flag3;
        s3 = System.getProperty("mail.mime.applefilenames");
        if(s3 == null) goto _L11; else goto _L10
_L10:
        if(!s3.equalsIgnoreCase("true")) goto _L11; else goto _L12
_L12:
        applehack = flag;
_L16:
        hex = (new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'A', 'B', 'C', 'D', 'E', 'F'
        });
        return;
_L2:
        flag1 = false;
          goto _L13
_L5:
        flag2 = false;
          goto _L14
_L8:
        flag3 = false;
          goto _L15
_L11:
        flag = false;
          goto _L12
        SecurityException securityexception;
        securityexception;
          goto _L16
    }

}
