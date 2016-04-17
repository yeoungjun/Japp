// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.*;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.message:
//            ParserCursor, BasicHeaderValueParser

public class BufferedHeader
    implements FormattedHeader, Cloneable, Serializable
{

    public BufferedHeader(CharArrayBuffer chararraybuffer)
        throws ParseException
    {
        Args.notNull(chararraybuffer, "Char array buffer");
        int i = chararraybuffer.indexOf(58);
        if(i == -1)
            throw new ParseException((new StringBuilder()).append("Invalid header: ").append(chararraybuffer.toString()).toString());
        String s = chararraybuffer.substringTrimmed(0, i);
        if(s.length() == 0)
        {
            throw new ParseException((new StringBuilder()).append("Invalid header: ").append(chararraybuffer.toString()).toString());
        } else
        {
            buffer = chararraybuffer;
            name = s;
            valuePos = i + 1;
            return;
        }
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public CharArrayBuffer getBuffer()
    {
        return buffer;
    }

    public HeaderElement[] getElements()
        throws ParseException
    {
        ParserCursor parsercursor = new ParserCursor(0, buffer.length());
        parsercursor.updatePos(valuePos);
        return BasicHeaderValueParser.INSTANCE.parseElements(buffer, parsercursor);
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return buffer.substringTrimmed(valuePos, buffer.length());
    }

    public int getValuePos()
    {
        return valuePos;
    }

    public String toString()
    {
        return buffer.toString();
    }

    private static final long serialVersionUID = 0xd994d5f69af48418L;
    private final CharArrayBuffer buffer;
    private final String name;
    private final int valuePos;
}
