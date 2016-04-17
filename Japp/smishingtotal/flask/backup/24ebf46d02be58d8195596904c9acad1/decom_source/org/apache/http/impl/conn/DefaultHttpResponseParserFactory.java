// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import org.apache.http.HttpResponseFactory;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.io.*;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultHttpResponseParser

public class DefaultHttpResponseParserFactory
    implements HttpMessageParserFactory
{

    public DefaultHttpResponseParserFactory()
    {
        this(null, null);
    }

    public DefaultHttpResponseParserFactory(HttpResponseFactory httpresponsefactory)
    {
        this(null, httpresponsefactory);
    }

    public DefaultHttpResponseParserFactory(LineParser lineparser, HttpResponseFactory httpresponsefactory)
    {
        if(lineparser == null)
            lineparser = BasicLineParser.INSTANCE;
        lineParser = lineparser;
        if(httpresponsefactory == null)
            httpresponsefactory = DefaultHttpResponseFactory.INSTANCE;
        responseFactory = httpresponsefactory;
    }

    public HttpMessageParser create(SessionInputBuffer sessioninputbuffer, MessageConstraints messageconstraints)
    {
        return new DefaultHttpResponseParser(sessioninputbuffer, lineParser, responseFactory, messageconstraints);
    }

    public static final DefaultHttpResponseParserFactory INSTANCE = new DefaultHttpResponseParserFactory();
    private final LineParser lineParser;
    private final HttpResponseFactory responseFactory;

}
