// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public class NetscapeDraftHeaderParser
{

    public NetscapeDraftHeaderParser()
    {
    }

    private NameValuePair parseNameValuePair(CharArrayBuffer chararraybuffer, ParserCursor parsercursor)
    {
        int i;
        int j;
        int k;
        i = parsercursor.getPos();
        j = parsercursor.getPos();
        k = parsercursor.getUpperBound();
_L2:
        boolean flag;
        String s;
        flag = false;
        if(i < k)
        {
            char c = chararraybuffer.charAt(i);
            flag = false;
            if(c != '=')
            {
label0:
                {
                    if(c != ';')
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
            return new BasicNameValuePair(s, null);
        }
        break; /* Loop/switch isn't completed */
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        int l = i;
        int i1;
label1:
        do
        {
label2:
            {
                if(i < k)
                {
                    if(chararraybuffer.charAt(i) != ';')
                        break label2;
                    flag = true;
                }
                for(i1 = i; l < i1 && HTTP.isWhitespace(chararraybuffer.charAt(l)); l++);
                break label1;
            }
            i++;
        } while(true);
        for(; i1 > l && HTTP.isWhitespace(chararraybuffer.charAt(i1 - 1)); i1--);
        String s1 = chararraybuffer.substring(l, i1);
        if(flag)
            i++;
        parsercursor.updatePos(i);
        return new BasicNameValuePair(s, s1);
    }

    public HeaderElement parseHeader(CharArrayBuffer chararraybuffer, ParserCursor parsercursor)
        throws ParseException
    {
        Args.notNull(chararraybuffer, "Char array buffer");
        Args.notNull(parsercursor, "Parser cursor");
        NameValuePair namevaluepair = parseNameValuePair(chararraybuffer, parsercursor);
        ArrayList arraylist = new ArrayList();
        for(; !parsercursor.atEnd(); arraylist.add(parseNameValuePair(chararraybuffer, parsercursor)));
        return new BasicHeaderElement(namevaluepair.getName(), namevaluepair.getValue(), (NameValuePair[])arraylist.toArray(new NameValuePair[arraylist.size()]));
    }

    public static final NetscapeDraftHeaderParser DEFAULT = new NetscapeDraftHeaderParser();

}
