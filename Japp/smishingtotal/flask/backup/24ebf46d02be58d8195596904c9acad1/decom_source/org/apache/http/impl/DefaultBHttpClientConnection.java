// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.http.*;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.io.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl:
//            BHttpConnectionBase

public class DefaultBHttpClientConnection extends BHttpConnectionBase
    implements HttpClientConnection
{

    public DefaultBHttpClientConnection(int i)
    {
        this(i, i, null, null, null, null, null, null, null);
    }

    public DefaultBHttpClientConnection(int i, int j, CharsetDecoder charsetdecoder, CharsetEncoder charsetencoder, MessageConstraints messageconstraints, ContentLengthStrategy contentlengthstrategy, ContentLengthStrategy contentlengthstrategy1, 
            HttpMessageWriterFactory httpmessagewriterfactory, HttpMessageParserFactory httpmessageparserfactory)
    {
        super(i, j, charsetdecoder, charsetencoder, messageconstraints, contentlengthstrategy, contentlengthstrategy1);
        if(httpmessagewriterfactory == null)
            httpmessagewriterfactory = DefaultHttpRequestWriterFactory.INSTANCE;
        requestWriter = httpmessagewriterfactory.create(getSessionOutputBuffer());
        if(httpmessageparserfactory == null)
            httpmessageparserfactory = DefaultHttpResponseParserFactory.INSTANCE;
        responseParser = httpmessageparserfactory.create(getSessionInputBuffer(), messageconstraints);
    }

    public DefaultBHttpClientConnection(int i, CharsetDecoder charsetdecoder, CharsetEncoder charsetencoder, MessageConstraints messageconstraints)
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

    public boolean isResponseAvailable(int i)
        throws IOException
    {
        ensureOpen();
        boolean flag;
        try
        {
            flag = awaitInput(i);
        }
        catch(SocketTimeoutException sockettimeoutexception)
        {
            return false;
        }
        return flag;
    }

    protected void onRequestSubmitted(HttpRequest httprequest)
    {
    }

    protected void onResponseReceived(HttpResponse httpresponse)
    {
    }

    public void receiveResponseEntity(HttpResponse httpresponse)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        ensureOpen();
        httpresponse.setEntity(prepareInput(httpresponse));
    }

    public HttpResponse receiveResponseHeader()
        throws HttpException, IOException
    {
        ensureOpen();
        HttpResponse httpresponse = (HttpResponse)responseParser.parse();
        onResponseReceived(httpresponse);
        if(httpresponse.getStatusLine().getStatusCode() >= 200)
            incrementResponseCount();
        return httpresponse;
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest httpentityenclosingrequest)
        throws HttpException, IOException
    {
        Args.notNull(httpentityenclosingrequest, "HTTP request");
        ensureOpen();
        HttpEntity httpentity = httpentityenclosingrequest.getEntity();
        if(httpentity == null)
        {
            return;
        } else
        {
            OutputStream outputstream = prepareOutput(httpentityenclosingrequest);
            httpentity.writeTo(outputstream);
            outputstream.close();
            return;
        }
    }

    public void sendRequestHeader(HttpRequest httprequest)
        throws HttpException, IOException
    {
        Args.notNull(httprequest, "HTTP request");
        ensureOpen();
        requestWriter.write(httprequest);
        onRequestSubmitted(httprequest);
        incrementRequestCount();
    }

    private final HttpMessageWriter requestWriter;
    private final HttpMessageParser responseParser;
}
