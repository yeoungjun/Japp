// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.*;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.logging.Log;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.config.MessageConstraints;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultManagedHttpClientConnection, Wire, LoggingInputStream, LoggingOutputStream

class LoggingManagedHttpClientConnection extends DefaultManagedHttpClientConnection
{

    public LoggingManagedHttpClientConnection(String s, Log log1, Log log2, Log log3, int i, int j, CharsetDecoder charsetdecoder, 
            CharsetEncoder charsetencoder, MessageConstraints messageconstraints, ContentLengthStrategy contentlengthstrategy, ContentLengthStrategy contentlengthstrategy1, HttpMessageWriterFactory httpmessagewriterfactory, HttpMessageParserFactory httpmessageparserfactory)
    {
        super(s, i, j, charsetdecoder, charsetencoder, messageconstraints, contentlengthstrategy, contentlengthstrategy1, httpmessagewriterfactory, httpmessageparserfactory);
        log = log1;
        headerlog = log2;
        wire = new Wire(log3, s);
    }

    public void close()
        throws IOException
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append(getId()).append(": Close connection").toString());
        super.close();
    }

    protected InputStream getSocketInputStream(Socket socket)
        throws IOException
    {
        Object obj = super.getSocketInputStream(socket);
        if(wire.enabled())
            obj = new LoggingInputStream(((InputStream) (obj)), wire);
        return ((InputStream) (obj));
    }

    protected OutputStream getSocketOutputStream(Socket socket)
        throws IOException
    {
        Object obj = super.getSocketOutputStream(socket);
        if(wire.enabled())
            obj = new LoggingOutputStream(((OutputStream) (obj)), wire);
        return ((OutputStream) (obj));
    }

    protected void onRequestSubmitted(HttpRequest httprequest)
    {
        if(httprequest != null && headerlog.isDebugEnabled())
        {
            headerlog.debug((new StringBuilder()).append(getId()).append(" >> ").append(httprequest.getRequestLine().toString()).toString());
            org.apache.http.Header aheader[] = httprequest.getAllHeaders();
            int i = aheader.length;
            for(int j = 0; j < i; j++)
            {
                org.apache.http.Header header = aheader[j];
                headerlog.debug((new StringBuilder()).append(getId()).append(" >> ").append(header.toString()).toString());
            }

        }
    }

    protected void onResponseReceived(HttpResponse httpresponse)
    {
        if(httpresponse != null && headerlog.isDebugEnabled())
        {
            headerlog.debug((new StringBuilder()).append(getId()).append(" << ").append(httpresponse.getStatusLine().toString()).toString());
            org.apache.http.Header aheader[] = httpresponse.getAllHeaders();
            int i = aheader.length;
            for(int j = 0; j < i; j++)
            {
                org.apache.http.Header header = aheader[j];
                headerlog.debug((new StringBuilder()).append(getId()).append(" << ").append(header.toString()).toString());
            }

        }
    }

    public void shutdown()
        throws IOException
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append(getId()).append(": Shutdown connection").toString());
        super.shutdown();
    }

    private final Log headerlog;
    private final Log log;
    private final Wire wire;
}
