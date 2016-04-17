// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.*;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.Args;

public class RequestWrapper extends AbstractHttpMessage
    implements HttpUriRequest
{

    public RequestWrapper(HttpRequest httprequest)
        throws ProtocolException
    {
        Args.notNull(httprequest, "HTTP request");
        original = httprequest;
        setParams(httprequest.getParams());
        setHeaders(httprequest.getAllHeaders());
        if(httprequest instanceof HttpUriRequest)
        {
            uri = ((HttpUriRequest)httprequest).getURI();
            method = ((HttpUriRequest)httprequest).getMethod();
            version = null;
        } else
        {
            RequestLine requestline = httprequest.getRequestLine();
            try
            {
                uri = new URI(requestline.getUri());
            }
            catch(URISyntaxException urisyntaxexception)
            {
                throw new ProtocolException((new StringBuilder()).append("Invalid request URI: ").append(requestline.getUri()).toString(), urisyntaxexception);
            }
            method = requestline.getMethod();
            version = httprequest.getProtocolVersion();
        }
        execCount = 0;
    }

    public void abort()
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public int getExecCount()
    {
        return execCount;
    }

    public String getMethod()
    {
        return method;
    }

    public HttpRequest getOriginal()
    {
        return original;
    }

    public ProtocolVersion getProtocolVersion()
    {
        if(version == null)
            version = HttpProtocolParams.getVersion(getParams());
        return version;
    }

    public RequestLine getRequestLine()
    {
        String s = getMethod();
        ProtocolVersion protocolversion = getProtocolVersion();
        URI uri1 = uri;
        String s1 = null;
        if(uri1 != null)
            s1 = uri.toASCIIString();
        if(s1 == null || s1.length() == 0)
            s1 = "/";
        return new BasicRequestLine(s, s1, protocolversion);
    }

    public URI getURI()
    {
        return uri;
    }

    public void incrementExecCount()
    {
        execCount = 1 + execCount;
    }

    public boolean isAborted()
    {
        return false;
    }

    public boolean isRepeatable()
    {
        return true;
    }

    public void resetHeaders()
    {
        headergroup.clear();
        setHeaders(original.getAllHeaders());
    }

    public void setMethod(String s)
    {
        Args.notNull(s, "Method name");
        method = s;
    }

    public void setProtocolVersion(ProtocolVersion protocolversion)
    {
        version = protocolversion;
    }

    public void setURI(URI uri1)
    {
        uri = uri1;
    }

    private int execCount;
    private String method;
    private final HttpRequest original;
    private URI uri;
    private ProtocolVersion version;
}
