// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.io.AbstractMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.LineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public class DefaultHttpResponseParser extends AbstractMessageParser
{

    public DefaultHttpResponseParser(SessionInputBuffer sessioninputbuffer)
    {
        this(sessioninputbuffer, null, null, MessageConstraints.DEFAULT);
    }

    public DefaultHttpResponseParser(SessionInputBuffer sessioninputbuffer, MessageConstraints messageconstraints)
    {
        this(sessioninputbuffer, null, null, messageconstraints);
    }

    public DefaultHttpResponseParser(SessionInputBuffer sessioninputbuffer, LineParser lineparser, HttpResponseFactory httpresponsefactory, MessageConstraints messageconstraints)
    {
        super(sessioninputbuffer, lineparser, messageconstraints);
        log = LogFactory.getLog(getClass());
        if(httpresponsefactory == null)
            httpresponsefactory = DefaultHttpResponseFactory.INSTANCE;
        responseFactory = httpresponsefactory;
        lineBuf = new CharArrayBuffer(128);
    }

    public DefaultHttpResponseParser(SessionInputBuffer sessioninputbuffer, LineParser lineparser, HttpResponseFactory httpresponsefactory, HttpParams httpparams)
    {
        super(sessioninputbuffer, lineparser, httpparams);
        log = LogFactory.getLog(getClass());
        Args.notNull(httpresponsefactory, "Response factory");
        responseFactory = httpresponsefactory;
        lineBuf = new CharArrayBuffer(128);
    }

    protected volatile HttpMessage parseHead(SessionInputBuffer sessioninputbuffer)
        throws IOException, HttpException, ParseException
    {
        return parseHead(sessioninputbuffer);
    }

    protected HttpResponse parseHead(SessionInputBuffer sessioninputbuffer)
        throws IOException, HttpException
    {
        int i = 0;
        do
        {
            lineBuf.clear();
            int j = sessioninputbuffer.readLine(lineBuf);
            if(j == -1 && i == 0)
                throw new NoHttpResponseException("The target server failed to respond");
            ParserCursor parsercursor = new ParserCursor(0, lineBuf.length());
            if(lineParser.hasProtocolVersion(lineBuf, parsercursor))
            {
                org.apache.http.StatusLine statusline = lineParser.parseStatusLine(lineBuf, parsercursor);
                return responseFactory.newHttpResponse(statusline, null);
            }
            if(j == -1 || reject(lineBuf, i))
                throw new ProtocolException("The server failed to respond with a valid HTTP response");
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Garbage in response: ").append(lineBuf.toString()).toString());
            i++;
        } while(true);
    }

    protected boolean reject(CharArrayBuffer chararraybuffer, int i)
    {
        return false;
    }

    private final CharArrayBuffer lineBuf;
    private final Log log;
    private final HttpResponseFactory responseFactory;
}
