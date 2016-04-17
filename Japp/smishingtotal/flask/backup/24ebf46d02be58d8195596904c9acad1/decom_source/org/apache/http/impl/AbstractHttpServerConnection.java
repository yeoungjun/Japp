// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.io.IOException;
import org.apache.http.*;
import org.apache.http.impl.entity.DisallowIdentityContentLengthStrategy;
import org.apache.http.impl.entity.EntityDeserializer;
import org.apache.http.impl.entity.EntitySerializer;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.DefaultHttpRequestParser;
import org.apache.http.impl.io.HttpResponseWriter;
import org.apache.http.io.*;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl:
//            HttpConnectionMetricsImpl, DefaultHttpRequestFactory

public abstract class AbstractHttpServerConnection
    implements HttpServerConnection
{

    public AbstractHttpServerConnection()
    {
        inbuffer = null;
        outbuffer = null;
        eofSensor = null;
        requestParser = null;
        responseWriter = null;
        metrics = null;
    }

    protected abstract void assertOpen()
        throws IllegalStateException;

    protected HttpConnectionMetricsImpl createConnectionMetrics(HttpTransportMetrics httptransportmetrics, HttpTransportMetrics httptransportmetrics1)
    {
        return new HttpConnectionMetricsImpl(httptransportmetrics, httptransportmetrics1);
    }

    protected EntityDeserializer createEntityDeserializer()
    {
        return new EntityDeserializer(new DisallowIdentityContentLengthStrategy(new LaxContentLengthStrategy(0)));
    }

    protected EntitySerializer createEntitySerializer()
    {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }

    protected HttpRequestFactory createHttpRequestFactory()
    {
        return DefaultHttpRequestFactory.INSTANCE;
    }

    protected HttpMessageParser createRequestParser(SessionInputBuffer sessioninputbuffer, HttpRequestFactory httprequestfactory, HttpParams httpparams)
    {
        return new DefaultHttpRequestParser(sessioninputbuffer, null, httprequestfactory, httpparams);
    }

    protected HttpMessageWriter createResponseWriter(SessionOutputBuffer sessionoutputbuffer, HttpParams httpparams)
    {
        return new HttpResponseWriter(sessionoutputbuffer, null, httpparams);
    }

    protected void doFlush()
        throws IOException
    {
        outbuffer.flush();
    }

    public void flush()
        throws IOException
    {
        assertOpen();
        doFlush();
    }

    public HttpConnectionMetrics getMetrics()
    {
        return metrics;
    }

    protected void init(SessionInputBuffer sessioninputbuffer, SessionOutputBuffer sessionoutputbuffer, HttpParams httpparams)
    {
        inbuffer = (SessionInputBuffer)Args.notNull(sessioninputbuffer, "Input session buffer");
        outbuffer = (SessionOutputBuffer)Args.notNull(sessionoutputbuffer, "Output session buffer");
        if(sessioninputbuffer instanceof EofSensor)
            eofSensor = (EofSensor)sessioninputbuffer;
        requestParser = createRequestParser(sessioninputbuffer, createHttpRequestFactory(), httpparams);
        responseWriter = createResponseWriter(sessionoutputbuffer, httpparams);
        metrics = createConnectionMetrics(sessioninputbuffer.getMetrics(), sessionoutputbuffer.getMetrics());
    }

    protected boolean isEof()
    {
        return eofSensor != null && eofSensor.isEof();
    }

    public boolean isStale()
    {
        while(!isOpen() || isEof()) 
            return true;
        boolean flag;
        try
        {
            inbuffer.isDataAvailable(1);
            flag = isEof();
        }
        catch(IOException ioexception)
        {
            return true;
        }
        return flag;
    }

    public void receiveRequestEntity(HttpEntityEnclosingRequest httpentityenclosingrequest)
        throws HttpException, IOException
    {
        Args.notNull(httpentityenclosingrequest, "HTTP request");
        assertOpen();
        httpentityenclosingrequest.setEntity(entitydeserializer.deserialize(inbuffer, httpentityenclosingrequest));
    }

    public HttpRequest receiveRequestHeader()
        throws HttpException, IOException
    {
        assertOpen();
        HttpRequest httprequest = (HttpRequest)requestParser.parse();
        metrics.incrementRequestCount();
        return httprequest;
    }

    public void sendResponseEntity(HttpResponse httpresponse)
        throws HttpException, IOException
    {
        if(httpresponse.getEntity() == null)
        {
            return;
        } else
        {
            entityserializer.serialize(outbuffer, httpresponse, httpresponse.getEntity());
            return;
        }
    }

    public void sendResponseHeader(HttpResponse httpresponse)
        throws HttpException, IOException
    {
        Args.notNull(httpresponse, "HTTP response");
        assertOpen();
        responseWriter.write(httpresponse);
        if(httpresponse.getStatusLine().getStatusCode() >= 200)
            metrics.incrementResponseCount();
    }

    private final EntityDeserializer entitydeserializer = createEntityDeserializer();
    private final EntitySerializer entityserializer = createEntitySerializer();
    private EofSensor eofSensor;
    private SessionInputBuffer inbuffer;
    private HttpConnectionMetricsImpl metrics;
    private SessionOutputBuffer outbuffer;
    private HttpMessageParser requestParser;
    private HttpMessageWriter responseWriter;
}
