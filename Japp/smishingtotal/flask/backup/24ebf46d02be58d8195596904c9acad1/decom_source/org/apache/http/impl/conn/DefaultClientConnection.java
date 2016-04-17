// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.io.*;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.conn:
//            DefaultHttpResponseParser, LoggingSessionInputBuffer, Wire, LoggingSessionOutputBuffer

public class DefaultClientConnection extends SocketHttpClientConnection
    implements OperatedClientConnection, ManagedHttpClientConnection, HttpContext
{

    public DefaultClientConnection()
    {
    }

    public void bind(Socket socket1)
        throws IOException
    {
        bind(socket1, ((HttpParams) (new BasicHttpParams())));
    }

    public void close()
        throws IOException
    {
        try
        {
            super.close();
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Connection ").append(this).append(" closed").toString());
            return;
        }
        catch(IOException ioexception)
        {
            log.debug("I/O error closing connection", ioexception);
        }
    }

    protected HttpMessageParser createResponseParser(SessionInputBuffer sessioninputbuffer, HttpResponseFactory httpresponsefactory, HttpParams httpparams)
    {
        return new DefaultHttpResponseParser(sessioninputbuffer, null, httpresponsefactory, httpparams);
    }

    protected SessionInputBuffer createSessionInputBuffer(Socket socket1, int i, HttpParams httpparams)
        throws IOException
    {
        Object obj;
        if(i <= 0)
            i = 8192;
        obj = super.createSessionInputBuffer(socket1, i, httpparams);
        if(wireLog.isDebugEnabled())
            obj = new LoggingSessionInputBuffer(((SessionInputBuffer) (obj)), new Wire(wireLog), HttpProtocolParams.getHttpElementCharset(httpparams));
        return ((SessionInputBuffer) (obj));
    }

    protected SessionOutputBuffer createSessionOutputBuffer(Socket socket1, int i, HttpParams httpparams)
        throws IOException
    {
        Object obj;
        if(i <= 0)
            i = 8192;
        obj = super.createSessionOutputBuffer(socket1, i, httpparams);
        if(wireLog.isDebugEnabled())
            obj = new LoggingSessionOutputBuffer(((SessionOutputBuffer) (obj)), new Wire(wireLog), HttpProtocolParams.getHttpElementCharset(httpparams));
        return ((SessionOutputBuffer) (obj));
    }

    public Object getAttribute(String s)
    {
        return attributes.get(s);
    }

    public String getId()
    {
        return null;
    }

    public SSLSession getSSLSession()
    {
        if(socket instanceof SSLSocket)
            return ((SSLSocket)socket).getSession();
        else
            return null;
    }

    public final Socket getSocket()
    {
        return socket;
    }

    public final HttpHost getTargetHost()
    {
        return targetHost;
    }

    public final boolean isSecure()
    {
        return connSecure;
    }

    public void openCompleted(boolean flag, HttpParams httpparams)
        throws IOException
    {
        Args.notNull(httpparams, "Parameters");
        assertNotOpen();
        connSecure = flag;
        bind(socket, httpparams);
    }

    public void opening(Socket socket1, HttpHost httphost)
        throws IOException
    {
        assertNotOpen();
        socket = socket1;
        targetHost = httphost;
        if(shutdown)
        {
            socket1.close();
            throw new InterruptedIOException("Connection already shutdown");
        } else
        {
            return;
        }
    }

    public HttpResponse receiveResponseHeader()
        throws HttpException, IOException
    {
        HttpResponse httpresponse = super.receiveResponseHeader();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Receiving response: ").append(httpresponse.getStatusLine()).toString());
        if(headerLog.isDebugEnabled())
        {
            headerLog.debug((new StringBuilder()).append("<< ").append(httpresponse.getStatusLine().toString()).toString());
            org.apache.http.Header aheader[] = httpresponse.getAllHeaders();
            int i = aheader.length;
            for(int j = 0; j < i; j++)
            {
                org.apache.http.Header header = aheader[j];
                headerLog.debug((new StringBuilder()).append("<< ").append(header.toString()).toString());
            }

        }
        return httpresponse;
    }

    public Object removeAttribute(String s)
    {
        return attributes.remove(s);
    }

    public void sendRequestHeader(HttpRequest httprequest)
        throws HttpException, IOException
    {
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Sending request: ").append(httprequest.getRequestLine()).toString());
        super.sendRequestHeader(httprequest);
        if(headerLog.isDebugEnabled())
        {
            headerLog.debug((new StringBuilder()).append(">> ").append(httprequest.getRequestLine().toString()).toString());
            org.apache.http.Header aheader[] = httprequest.getAllHeaders();
            int i = aheader.length;
            for(int j = 0; j < i; j++)
            {
                org.apache.http.Header header = aheader[j];
                headerLog.debug((new StringBuilder()).append(">> ").append(header.toString()).toString());
            }

        }
    }

    public void setAttribute(String s, Object obj)
    {
        attributes.put(s, obj);
    }

    public void shutdown()
        throws IOException
    {
        shutdown = true;
        Socket socket1;
        try
        {
            super.shutdown();
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Connection ").append(this).append(" shut down").toString());
            socket1 = socket;
        }
        catch(IOException ioexception)
        {
            log.debug("I/O error shutting down connection", ioexception);
            return;
        }
        if(socket1 == null)
            break MISSING_BLOCK_LABEL_67;
        socket1.close();
    }

    public void update(Socket socket1, HttpHost httphost, boolean flag, HttpParams httpparams)
        throws IOException
    {
        assertOpen();
        Args.notNull(httphost, "Target host");
        Args.notNull(httpparams, "Parameters");
        if(socket1 != null)
        {
            socket = socket1;
            bind(socket1, httpparams);
        }
        targetHost = httphost;
        connSecure = flag;
    }

    private final Map attributes = new HashMap();
    private boolean connSecure;
    private final Log headerLog = LogFactory.getLog("org.apache.http.headers");
    private final Log log = LogFactory.getLog(getClass());
    private volatile boolean shutdown;
    private volatile Socket socket;
    private HttpHost targetHost;
    private final Log wireLog = LogFactory.getLog("org.apache.http.wire");
}
