// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.nio.charset.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpConnection;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultManagedHttpClientConnection, DefaultHttpResponseParserFactory, LoggingManagedHttpClientConnection

public class ManagedHttpClientConnectionFactory
    implements HttpConnectionFactory
{

    public ManagedHttpClientConnectionFactory()
    {
        this(null, null);
    }

    public ManagedHttpClientConnectionFactory(HttpMessageParserFactory httpmessageparserfactory)
    {
        this(null, httpmessageparserfactory);
    }

    public ManagedHttpClientConnectionFactory(HttpMessageWriterFactory httpmessagewriterfactory, HttpMessageParserFactory httpmessageparserfactory)
    {
        log = LogFactory.getLog(org/apache/http/impl/conn/DefaultManagedHttpClientConnection);
        headerlog = LogFactory.getLog("org.apache.http.headers");
        wirelog = LogFactory.getLog("org.apache.http.wire");
        if(httpmessagewriterfactory == null)
            httpmessagewriterfactory = DefaultHttpRequestWriterFactory.INSTANCE;
        requestWriterFactory = httpmessagewriterfactory;
        if(httpmessageparserfactory == null)
            httpmessageparserfactory = DefaultHttpResponseParserFactory.INSTANCE;
        responseParserFactory = httpmessageparserfactory;
    }

    public volatile HttpConnection create(Object obj, ConnectionConfig connectionconfig)
    {
        return create((HttpRoute)obj, connectionconfig);
    }

    public ManagedHttpClientConnection create(HttpRoute httproute, ConnectionConfig connectionconfig)
    {
        ConnectionConfig connectionconfig1;
        Charset charset;
        CodingErrorAction codingerroraction;
        CodingErrorAction codingerroraction1;
        CharsetDecoder charsetdecoder;
        CharsetEncoder charsetencoder;
        if(connectionconfig != null)
            connectionconfig1 = connectionconfig;
        else
            connectionconfig1 = ConnectionConfig.DEFAULT;
        charset = connectionconfig1.getCharset();
        if(connectionconfig1.getMalformedInputAction() != null)
            codingerroraction = connectionconfig1.getMalformedInputAction();
        else
            codingerroraction = CodingErrorAction.REPORT;
        if(connectionconfig1.getUnmappableInputAction() != null)
            codingerroraction1 = connectionconfig1.getUnmappableInputAction();
        else
            codingerroraction1 = CodingErrorAction.REPORT;
        charsetdecoder = null;
        charsetencoder = null;
        if(charset != null)
        {
            charsetdecoder = charset.newDecoder();
            charsetdecoder.onMalformedInput(codingerroraction);
            charsetdecoder.onUnmappableCharacter(codingerroraction1);
            charsetencoder = charset.newEncoder();
            charsetencoder.onMalformedInput(codingerroraction);
            charsetencoder.onUnmappableCharacter(codingerroraction1);
        }
        return new LoggingManagedHttpClientConnection((new StringBuilder()).append("http-outgoing-").append(Long.toString(COUNTER.getAndIncrement())).toString(), log, headerlog, wirelog, connectionconfig1.getBufferSize(), connectionconfig1.getFragmentSizeHint(), charsetdecoder, charsetencoder, connectionconfig1.getMessageConstraints(), null, null, requestWriterFactory, responseParserFactory);
    }

    private static final AtomicLong COUNTER = new AtomicLong();
    public static final ManagedHttpClientConnectionFactory INSTANCE = new ManagedHttpClientConnectionFactory();
    private final Log headerlog;
    private final Log log;
    private final HttpMessageWriterFactory requestWriterFactory;
    private final HttpMessageParserFactory responseParserFactory;
    private final Log wirelog;

}
