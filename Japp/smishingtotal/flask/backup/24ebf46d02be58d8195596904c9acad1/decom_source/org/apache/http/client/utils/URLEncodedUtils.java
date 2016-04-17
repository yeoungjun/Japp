// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.*;
import org.apache.http.entity.ContentType;
import org.apache.http.message.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

public class URLEncodedUtils
{

    public URLEncodedUtils()
    {
    }

    private static String decodeFormFields(String s, String s1)
    {
        if(s == null)
            return null;
        Charset charset;
        if(s1 != null)
            charset = Charset.forName(s1);
        else
            charset = Consts.UTF_8;
        return urlDecode(s, charset, true);
    }

    private static String decodeFormFields(String s, Charset charset)
    {
        if(s == null)
            return null;
        if(charset == null)
            charset = Consts.UTF_8;
        return urlDecode(s, charset, true);
    }

    static String encPath(String s, Charset charset)
    {
        return urlEncode(s, charset, PATHSAFE, false);
    }

    static String encUric(String s, Charset charset)
    {
        return urlEncode(s, charset, URIC, false);
    }

    static String encUserInfo(String s, Charset charset)
    {
        return urlEncode(s, charset, USERINFO, false);
    }

    private static String encodeFormFields(String s, String s1)
    {
        if(s == null)
            return null;
        Charset charset;
        if(s1 != null)
            charset = Charset.forName(s1);
        else
            charset = Consts.UTF_8;
        return urlEncode(s, charset, URLENCODER, true);
    }

    private static String encodeFormFields(String s, Charset charset)
    {
        if(s == null)
            return null;
        if(charset == null)
            charset = Consts.UTF_8;
        return urlEncode(s, charset, URLENCODER, true);
    }

    public static String format(Iterable iterable, char c, Charset charset)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = iterable.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            NameValuePair namevaluepair = (NameValuePair)iterator.next();
            String s = encodeFormFields(namevaluepair.getName(), charset);
            String s1 = encodeFormFields(namevaluepair.getValue(), charset);
            if(stringbuilder.length() > 0)
                stringbuilder.append(c);
            stringbuilder.append(s);
            if(s1 != null)
            {
                stringbuilder.append("=");
                stringbuilder.append(s1);
            }
        } while(true);
        return stringbuilder.toString();
    }

    public static String format(Iterable iterable, Charset charset)
    {
        return format(iterable, '&', charset);
    }

    public static String format(List list, char c, String s)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            NameValuePair namevaluepair = (NameValuePair)iterator.next();
            String s1 = encodeFormFields(namevaluepair.getName(), s);
            String s2 = encodeFormFields(namevaluepair.getValue(), s);
            if(stringbuilder.length() > 0)
                stringbuilder.append(c);
            stringbuilder.append(s1);
            if(s2 != null)
            {
                stringbuilder.append("=");
                stringbuilder.append(s2);
            }
        } while(true);
        return stringbuilder.toString();
    }

    public static String format(List list, String s)
    {
        return format(list, '&', s);
    }

    public static boolean isEncoded(HttpEntity httpentity)
    {
        Header header = httpentity.getContentType();
        boolean flag = false;
        if(header != null)
        {
            HeaderElement aheaderelement[] = header.getElements();
            int i = aheaderelement.length;
            flag = false;
            if(i > 0)
                flag = aheaderelement[0].getName().equalsIgnoreCase("application/x-www-form-urlencoded");
        }
        return flag;
    }

    public static List parse(String s, Charset charset)
    {
        return parse(s, charset, QP_SEPS);
    }

    public static transient List parse(String s, Charset charset, char ac[])
    {
        Object obj;
        if(s == null)
        {
            obj = Collections.emptyList();
        } else
        {
            BasicHeaderValueParser basicheadervalueparser = BasicHeaderValueParser.INSTANCE;
            CharArrayBuffer chararraybuffer = new CharArrayBuffer(s.length());
            chararraybuffer.append(s);
            ParserCursor parsercursor = new ParserCursor(0, chararraybuffer.length());
            obj = new ArrayList();
            while(!parsercursor.atEnd()) 
            {
                NameValuePair namevaluepair = basicheadervalueparser.parseNameValuePair(chararraybuffer, parsercursor, ac);
                if(namevaluepair.getName().length() > 0)
                    ((List) (obj)).add(new BasicNameValuePair(decodeFormFields(namevaluepair.getName(), charset), decodeFormFields(namevaluepair.getValue(), charset)));
            }
        }
        return ((List) (obj));
    }

    public static List parse(URI uri, String s)
    {
        String s1 = uri.getRawQuery();
        if(s1 != null && s1.length() > 0)
        {
            ArrayList arraylist = new ArrayList();
            parse(((List) (arraylist)), new Scanner(s1), QP_SEP_PATTERN, s);
            return arraylist;
        } else
        {
            return Collections.emptyList();
        }
    }

    public static List parse(HttpEntity httpentity)
        throws IOException
    {
        ContentType contenttype = ContentType.get(httpentity);
        if(contenttype != null && contenttype.getMimeType().equalsIgnoreCase("application/x-www-form-urlencoded"))
        {
            String s = EntityUtils.toString(httpentity, Consts.ASCII);
            if(s != null && s.length() > 0)
            {
                Charset charset = contenttype.getCharset();
                if(charset == null)
                    charset = HTTP.DEF_CONTENT_CHARSET;
                return parse(s, charset, QP_SEPS);
            }
        }
        return Collections.emptyList();
    }

    public static void parse(List list, Scanner scanner, String s)
    {
        parse(list, scanner, QP_SEP_PATTERN, s);
    }

    public static void parse(List list, Scanner scanner, String s, String s1)
    {
        scanner.useDelimiter(s);
        while(scanner.hasNext()) 
        {
            String s2 = scanner.next();
            int i = s2.indexOf("=");
            String s3;
            String s4;
            if(i != -1)
            {
                s3 = decodeFormFields(s2.substring(0, i).trim(), s1);
                s4 = decodeFormFields(s2.substring(i + 1).trim(), s1);
            } else
            {
                s3 = decodeFormFields(s2.trim(), s1);
                s4 = null;
            }
            list.add(new BasicNameValuePair(s3, s4));
        }
    }

    private static String urlDecode(String s, Charset charset, boolean flag)
    {
        if(s == null)
            return null;
        ByteBuffer bytebuffer = ByteBuffer.allocate(s.length());
        for(CharBuffer charbuffer = CharBuffer.wrap(s); charbuffer.hasRemaining();)
        {
            char c = charbuffer.get();
            if(c == '%' && charbuffer.remaining() >= 2)
            {
                char c1 = charbuffer.get();
                char c2 = charbuffer.get();
                int i = Character.digit(c1, 16);
                int j = Character.digit(c2, 16);
                if(i != -1 && j != -1)
                {
                    bytebuffer.put((byte)(j + (i << 4)));
                } else
                {
                    bytebuffer.put((byte)37);
                    bytebuffer.put((byte)c1);
                    bytebuffer.put((byte)c2);
                }
            } else
            if(flag && c == '+')
                bytebuffer.put((byte)32);
            else
                bytebuffer.put((byte)c);
        }

        bytebuffer.flip();
        return charset.decode(bytebuffer).toString();
    }

    private static String urlEncode(String s, Charset charset, BitSet bitset, boolean flag)
    {
        if(s == null)
            return null;
        StringBuilder stringbuilder = new StringBuilder();
        for(ByteBuffer bytebuffer = charset.encode(s); bytebuffer.hasRemaining();)
        {
            int i = 0xff & bytebuffer.get();
            if(bitset.get(i))
                stringbuilder.append((char)i);
            else
            if(flag && i == 32)
            {
                stringbuilder.append('+');
            } else
            {
                stringbuilder.append("%");
                char c = Character.toUpperCase(Character.forDigit(0xf & i >> 4, 16));
                char c1 = Character.toUpperCase(Character.forDigit(i & 0xf, 16));
                stringbuilder.append(c);
                stringbuilder.append(c1);
            }
        }

        return stringbuilder.toString();
    }

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String NAME_VALUE_SEPARATOR = "=";
    private static final BitSet PATHSAFE;
    private static final BitSet PUNCT;
    private static final char QP_SEPS[] = {
        '&', ';'
    };
    private static final char QP_SEP_A = 38;
    private static final String QP_SEP_PATTERN = (new StringBuilder()).append("[").append(new String(QP_SEPS)).append("]").toString();
    private static final char QP_SEP_S = 59;
    private static final int RADIX = 16;
    private static final BitSet RESERVED;
    private static final BitSet UNRESERVED;
    private static final BitSet URIC;
    private static final BitSet URLENCODER;
    private static final BitSet USERINFO;

    static 
    {
        UNRESERVED = new BitSet(256);
        PUNCT = new BitSet(256);
        USERINFO = new BitSet(256);
        PATHSAFE = new BitSet(256);
        URIC = new BitSet(256);
        RESERVED = new BitSet(256);
        URLENCODER = new BitSet(256);
        for(int i = 97; i <= 122; i++)
            UNRESERVED.set(i);

        for(int j = 65; j <= 90; j++)
            UNRESERVED.set(j);

        for(int k = 48; k <= 57; k++)
            UNRESERVED.set(k);

        UNRESERVED.set(95);
        UNRESERVED.set(45);
        UNRESERVED.set(46);
        UNRESERVED.set(42);
        URLENCODER.or(UNRESERVED);
        UNRESERVED.set(33);
        UNRESERVED.set(126);
        UNRESERVED.set(39);
        UNRESERVED.set(40);
        UNRESERVED.set(41);
        PUNCT.set(44);
        PUNCT.set(59);
        PUNCT.set(58);
        PUNCT.set(36);
        PUNCT.set(38);
        PUNCT.set(43);
        PUNCT.set(61);
        USERINFO.or(UNRESERVED);
        USERINFO.or(PUNCT);
        PATHSAFE.or(UNRESERVED);
        PATHSAFE.set(47);
        PATHSAFE.set(59);
        PATHSAFE.set(58);
        PATHSAFE.set(64);
        PATHSAFE.set(38);
        PATHSAFE.set(61);
        PATHSAFE.set(43);
        PATHSAFE.set(36);
        PATHSAFE.set(44);
        RESERVED.set(59);
        RESERVED.set(47);
        RESERVED.set(63);
        RESERVED.set(58);
        RESERVED.set(64);
        RESERVED.set(38);
        RESERVED.set(61);
        RESERVED.set(43);
        RESERVED.set(36);
        RESERVED.set(44);
        RESERVED.set(91);
        RESERVED.set(93);
        URIC.or(RESERVED);
        URIC.or(UNRESERVED);
    }
}
