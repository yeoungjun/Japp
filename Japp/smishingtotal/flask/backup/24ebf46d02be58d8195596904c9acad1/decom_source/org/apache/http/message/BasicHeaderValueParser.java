// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.message:
//            HeaderValueParser, ParserCursor, BasicHeaderElement, BasicNameValuePair

public class BasicHeaderValueParser
    implements HeaderValueParser
{

    public BasicHeaderValueParser()
    {
    }

    private static boolean isOneOf(char c, char ac[])
    {
        if(ac != null)
        {
            int i = ac.length;
            for(int j = 0; j < i; j++)
                if(c == ac[j])
                    return true;

        }
        return false;
    }

    public static HeaderElement[] parseElements(String s, HeaderValueParser headervalueparser)
        throws ParseException
    {
        Args.notNull(s, "Value");
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(s.length());
        chararraybuffer.append(s);
        ParserCursor parsercursor = new ParserCursor(0, s.length());
        if(headervalueparser == null)
            headervalueparser = INSTANCE;
        return headervalueparser.parseElements(chararraybuffer, parsercursor);
    }

    public static HeaderElement parseHeaderElement(String s, HeaderValueParser headervalueparser)
        throws ParseException
    {
        Args.notNull(s, "Value");
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(s.length());
        chararraybuffer.append(s);
        ParserCursor parsercursor = new ParserCursor(0, s.length());
        if(headervalueparser == null)
            headervalueparser = INSTANCE;
        return headervalueparser.parseHeaderElement(chararraybuffer, parsercursor);
    }

    public static NameValuePair parseNameValuePair(String s, HeaderValueParser headervalueparser)
        throws ParseException
    {
        Args.notNull(s, "Value");
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(s.length());
        chararraybuffer.append(s);
        ParserCursor parsercursor = new ParserCursor(0, s.length());
        if(headervalueparser == null)
            headervalueparser = INSTANCE;
        return headervalueparser.parseNameValuePair(chararraybuffer, parsercursor);
    }

    public static NameValuePair[] parseParameters(String s, HeaderValueParser headervalueparser)
        throws ParseException
    {
        Args.notNull(s, "Value");
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(s.length());
        chararraybuffer.append(s);
        ParserCursor parsercursor = new ParserCursor(0, s.length());
        if(headervalueparser == null)
            headervalueparser = INSTANCE;
        return headervalueparser.parseParameters(chararraybuffer, parsercursor);
    }

    protected HeaderElement createHeaderElement(String s, String s1, NameValuePair anamevaluepair[])
    {
        return new BasicHeaderElement(s, s1, anamevaluepair);
    }

    protected NameValuePair createNameValuePair(String s, String s1)
    {
        return new BasicNameValuePair(s, s1);
    }

    public HeaderElement[] parseElements(CharArrayBuffer chararraybuffer, ParserCursor parsercursor)
    {
        Args.notNull(chararraybuffer, "Char array buffer");
        Args.notNull(parsercursor, "Parser cursor");
        ArrayList arraylist = new ArrayList();
        do
        {
            if(parsercursor.atEnd())
                break;
            HeaderElement headerelement = parseHeaderElement(chararraybuffer, parsercursor);
            if(headerelement.getName().length() != 0 || headerelement.getValue() != null)
                arraylist.add(headerelement);
        } while(true);
        return (HeaderElement[])arraylist.toArray(new HeaderElement[arraylist.size()]);
    }

    public HeaderElement parseHeaderElement(CharArrayBuffer chararraybuffer, ParserCursor parsercursor)
    {
        Args.notNull(chararraybuffer, "Char array buffer");
        Args.notNull(parsercursor, "Parser cursor");
        NameValuePair namevaluepair = parseNameValuePair(chararraybuffer, parsercursor);
        boolean flag = parsercursor.atEnd();
        NameValuePair anamevaluepair[] = null;
        if(!flag)
        {
            char c = chararraybuffer.charAt(-1 + parsercursor.getPos());
            anamevaluepair = null;
            if(c != ',')
                anamevaluepair = parseParameters(chararraybuffer, parsercursor);
        }
        return createHeaderElement(namevaluepair.getName(), namevaluepair.getValue(), anamevaluepair);
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer chararraybuffer, ParserCursor parsercursor)
    {
        return parseNameValuePair(chararraybuffer, parsercursor, ALL_DELIMITERS);
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer chararraybuffer, ParserCursor parsercursor, char ac[])
    {
        int i;
        int j;
        int k;
        Args.notNull(chararraybuffer, "Char array buffer");
        Args.notNull(parsercursor, "Parser cursor");
        i = parsercursor.getPos();
        j = parsercursor.getPos();
        k = parsercursor.getUpperBound();
_L2:
        boolean flag;
        String s;
        flag = false;
        if(i < k)
        {
            char c1 = chararraybuffer.charAt(i);
            flag = false;
            if(c1 != '=')
            {
label0:
                {
                    if(!isOneOf(c1, ac))
                        break label0;
                    flag = true;
                }
            }
        }
        if(i == k)
        {
            flag = true;
            s = chararraybuffer.substringTrimmed(j, k);
        } else
        {
            s = chararraybuffer.substringTrimmed(j, i);
            i++;
        }
        if(flag)
        {
            parsercursor.updatePos(i);
            return createNameValuePair(s, null);
        }
        break; /* Loop/switch isn't completed */
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        int l;
        boolean flag1;
        boolean flag2;
        l = i;
        flag1 = false;
        flag2 = false;
_L4:
        int i1;
        char c;
label1:
        {
            if(i < k)
            {
                c = chararraybuffer.charAt(i);
                if(c == '"' && !flag2)
                    if(!flag1)
                        flag1 = true;
                    else
                        flag1 = false;
                if(flag1 || flag2 || !isOneOf(c, ac))
                    break label1;
                flag = true;
            }
            for(i1 = i; l < i1 && HTTP.isWhitespace(chararraybuffer.charAt(l)); l++);
            break MISSING_BLOCK_LABEL_276;
        }
        if(!flag2)
            break; /* Loop/switch isn't completed */
        flag2 = false;
_L5:
        i++;
        if(true) goto _L4; else goto _L3
_L3:
        if(flag1 && c == '\\')
            flag2 = true;
        else
            flag2 = false;
          goto _L5
        if(true) goto _L4; else goto _L6
_L6:
        for(; i1 > l && HTTP.isWhitespace(chararraybuffer.charAt(i1 - 1)); i1--);
        if(i1 - l >= 2 && chararraybuffer.charAt(l) == '"' && chararraybuffer.charAt(i1 - 1) == '"')
        {
            l++;
            i1--;
        }
        String s1 = chararraybuffer.substring(l, i1);
        if(flag)
            i++;
        parsercursor.updatePos(i);
        return createNameValuePair(s, s1);
    }

    public NameValuePair[] parseParameters(CharArrayBuffer chararraybuffer, ParserCursor parsercursor)
    {
        Args.notNull(chararraybuffer, "Char array buffer");
        Args.notNull(parsercursor, "Parser cursor");
        int i = parsercursor.getPos();
        for(int j = parsercursor.getUpperBound(); i < j && HTTP.isWhitespace(chararraybuffer.charAt(i)); i++);
        parsercursor.updatePos(i);
        if(parsercursor.atEnd())
            return new NameValuePair[0];
        ArrayList arraylist = new ArrayList();
        do
        {
            if(parsercursor.atEnd())
                break;
            arraylist.add(parseNameValuePair(chararraybuffer, parsercursor));
        } while(chararraybuffer.charAt(-1 + parsercursor.getPos()) != ',');
        return (NameValuePair[])arraylist.toArray(new NameValuePair[arraylist.size()]);
    }

    private static final char ALL_DELIMITERS[] = {
        ';', ','
    };
    public static final BasicHeaderValueParser DEFAULT = new BasicHeaderValueParser();
    private static final char ELEM_DELIMITER = 44;
    public static final BasicHeaderValueParser INSTANCE = new BasicHeaderValueParser();
    private static final char PARAM_DELIMITER = 59;

}
