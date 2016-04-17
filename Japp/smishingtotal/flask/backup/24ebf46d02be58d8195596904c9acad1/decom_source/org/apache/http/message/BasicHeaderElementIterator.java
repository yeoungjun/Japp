// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.*;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.message:
//            BasicHeaderValueParser, HeaderValueParser, ParserCursor

public class BasicHeaderElementIterator
    implements HeaderElementIterator
{

    public BasicHeaderElementIterator(HeaderIterator headeriterator)
    {
        this(headeriterator, ((HeaderValueParser) (BasicHeaderValueParser.INSTANCE)));
    }

    public BasicHeaderElementIterator(HeaderIterator headeriterator, HeaderValueParser headervalueparser)
    {
        currentElement = null;
        buffer = null;
        cursor = null;
        headerIt = (HeaderIterator)Args.notNull(headeriterator, "Header iterator");
        parser = (HeaderValueParser)Args.notNull(headervalueparser, "Parser");
    }

    private void bufferHeaderValue()
    {
        cursor = null;
        buffer = null;
        do
        {
            Header header;
label0:
            {
                if(headerIt.hasNext())
                {
                    header = headerIt.nextHeader();
                    if(!(header instanceof FormattedHeader))
                        break label0;
                    buffer = ((FormattedHeader)header).getBuffer();
                    cursor = new ParserCursor(0, buffer.length());
                    cursor.updatePos(((FormattedHeader)header).getValuePos());
                }
                return;
            }
            String s = header.getValue();
            if(s != null)
            {
                buffer = new CharArrayBuffer(s.length());
                buffer.append(s);
                cursor = new ParserCursor(0, buffer.length());
                return;
            }
        } while(true);
    }

    private void parseNextElement()
    {
        do
        {
label0:
            {
                if(headerIt.hasNext() || cursor != null)
                {
                    if(cursor == null || cursor.atEnd())
                        bufferHeaderValue();
                    if(cursor == null)
                        continue;
                    HeaderElement headerelement;
                    do
                    {
                        if(cursor.atEnd())
                            break label0;
                        headerelement = parser.parseHeaderElement(buffer, cursor);
                    } while(headerelement.getName().length() == 0 && headerelement.getValue() == null);
                    currentElement = headerelement;
                }
                return;
            }
            if(cursor.atEnd())
            {
                cursor = null;
                buffer = null;
            }
        } while(true);
    }

    public boolean hasNext()
    {
        if(currentElement == null)
            parseNextElement();
        return currentElement != null;
    }

    public final Object next()
        throws NoSuchElementException
    {
        return nextElement();
    }

    public HeaderElement nextElement()
        throws NoSuchElementException
    {
        if(currentElement == null)
            parseNextElement();
        if(currentElement == null)
        {
            throw new NoSuchElementException("No more header elements available");
        } else
        {
            HeaderElement headerelement = currentElement;
            currentElement = null;
            return headerelement;
        }
    }

    public void remove()
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException("Remove not supported");
    }

    private CharArrayBuffer buffer;
    private HeaderElement currentElement;
    private ParserCursor cursor;
    private final HeaderIterator headerIt;
    private final HeaderValueParser parser;
}
