// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.io;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.LineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.io:
//            AbstractMessageParser

public class HttpRequestParser extends AbstractMessageParser
{

    public HttpRequestParser(SessionInputBuffer sessioninputbuffer, LineParser lineparser, HttpRequestFactory httprequestfactory, HttpParams httpparams)
    {
        super(sessioninputbuffer, lineparser, httpparams);
        requestFactory = (HttpRequestFactory)Args.notNull(httprequestfactory, "Request factory");
    }

    protected HttpMessage parseHead(SessionInputBuffer sessioninputbuffer)
        throws IOException, HttpException, ParseException
    {
        lineBuf.clear();
        if(sessioninputbuffer.readLine(lineBuf) == -1)
        {
            throw new ConnectionClosedException("Client closed connection");
        } else
        {
            ParserCursor parsercursor = new ParserCursor(0, lineBuf.length());
            org.apache.http.RequestLine requestline = lineParser.parseRequestLine(lineBuf, parsercursor);
            return requestFactory.newHttpRequest(requestline);
        }
    }

    private final CharArrayBuffer lineBuf = new CharArrayBuffer(128);
    private final HttpRequestFactory requestFactory;
}
