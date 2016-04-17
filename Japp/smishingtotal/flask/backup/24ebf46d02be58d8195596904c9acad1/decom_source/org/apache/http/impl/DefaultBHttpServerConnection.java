// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.http.*;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.entity.DisallowIdentityContentLengthStrategy;
import org.apache.http.impl.io.DefaultHttpRequestParserFactory;
import org.apache.http.impl.io.DefaultHttpResponseWriterFactory;
import org.apache.http.io.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl:
//            BHttpConnectionBase

public class DefaultBHttpServerConnection extends BHttpConnectionBase
    implements HttpServerConnection
{

    public DefaultBHttpServerConnection(int i)
    {
        this(i, i, null, null, null, null, null, null, null);
    }

    public DefaultBHttpServerConnection(int i, int j, CharsetDecoder charsetdecoder, CharsetEncoder charsetencoder, MessageConstraints messageconstraints, ContentLengthStrategy contentlengthstrategy, ContentLengthStrategy contentlengthstrategy1, 
            HttpMessageParserFactory httpmessageparserfactory, HttpMessageWriterFactory httpmessagewriterfactory)
    {
        Object obj;
        org.apache.http.io.SessionInputBuffer sessioninputbuffer;
        org.apache.http.io.SessionOutputBuffer sessionoutputbuffer;
        if(contentlengthstrategy != null)
            obj = contentlengthstrategy;
        else
            obj = DisallowIdentityContentLengthStrategy.INSTANCE;
        super(i, j, charsetdecoder, charsetencoder, messageconstraints, ((ContentLengthStrategy) (obj)), contentlengthstrategy1);
        if(httpmessageparserfactory == null)
            httpmessageparserfactory = DefaultHttpRequestParserFactory.INSTANCE;
        sessioninputbuffer = getSessionInputBuffer();
        requestParser = httpmessageparserfactory.create(sessioninputbuffer, messageconstraints);
        if(httpmessagewriterfactory == null)
            httpmessagewriterfactory = DefaultHttpResponseWriterFactory.INSTANCE;
        sessionoutputbuffer = getSessionOutputBuffer();
        responseWriter = httpmessagewriterfactory.create(sessionoutputbuffer);
    }

    public DefaultBHttpServerConnection(int i, CharsetDecoder charsetdecoder, CharsetEncoder charsetencoder, MessageConstraints messageconstraints)
    {
        this(i, i, charsetdecoder, charsetencoder, messageconstraints, null, null, null, null);
    }

    public void bind(Socket socket)
        throws IOException
    {
        super.bind(socket);
    }

    public void flush()
        throws IOException
    {
        ensureOpen();
        doFlush();
    }

    protected void onRequestReceived(HttpRequest httprequest)
    {
    }

    protected void onResponseSubmitted(HttpResponse httpresponse)
    {
    }

    public void receiveRequestEntity(HttpEntityEnclosingRequest httpentityenclosingrequest)
        throws HttpException, IOException
    {
        Args.notNull(httpentityenclosingrequest, "HTTP request");
        ensureOpen();
        httpentityenclosingrequest.setEntity(prepareInput(httpentityenclosingrequest));
    }

    public HttpRequest receiveRequestHeader()
        throws HttpException, IOException
    {
        ensureOpen();
        HttpRequest httprequest = (HttpRequest)requestParser.parse();
        onRequestReceived(httprequest);
        incrementRequestCount();
        return httprequest;
    }

    public void sendResponseEntity(HttpResponse httpresponse)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        ensureOpen();
        HttpEntity httpentity = httpresponse.getEntity();
        if(httpentity == null)
        {
            return;
        } else
        {
            OutputStream outputstream = prepareOutput(httpresponse);
            httpentity.writeTo(outputstream);
            outputstream.close();
            return;
        }
    }

    public void sendResponseHeader(HttpResponse httpresponse)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        ensureOpen();
        responseWriter.write(httpresponse);
        onResponseSubmitted(httpresponse);
        if(httpresponse.getStatusLine().getStatusCode() >= 200)
            incrementResponseCount();
    }

    private final HttpMessageParser requestParser;
    private final HttpMessageWriter responseWriter;
}
