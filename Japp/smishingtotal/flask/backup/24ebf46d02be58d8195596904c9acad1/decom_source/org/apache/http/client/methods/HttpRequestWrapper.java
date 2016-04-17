// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.*;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.params.HttpParams;

// Referenced classes of package org.apache.http.client.methods:
//            HttpUriRequest

public class HttpRequestWrapper extends AbstractHttpMessage
    implements HttpUriRequest
{
    static class HttpEntityEnclosingRequestWrapper extends HttpRequestWrapper
        implements HttpEntityEnclosingRequest
    {

        public boolean expectContinue()
        {
            Header header = getFirstHeader("Expect");
            return header != null && "100-continue".equalsIgnoreCase(header.getValue());
        }

        public HttpEntity getEntity()
        {
            return entity;
        }

        public void setEntity(HttpEntity httpentity)
        {
            entity = httpentity;
        }

        private HttpEntity entity;

        public HttpEntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpentityenclosingrequest)
        {
            super(httpentityenclosingrequest, null);
            entity = httpentityenclosingrequest.getEntity();
        }
    }


    private HttpRequestWrapper(HttpRequest httprequest)
    {
        original = httprequest;
        version = original.getRequestLine().getProtocolVersion();
        method = original.getRequestLine().getMethod();
        if(httprequest instanceof HttpUriRequest)
            uri = ((HttpUriRequest)httprequest).getURI();
        else
            uri = null;
        setHeaders(httprequest.getAllHeaders());
    }


    public static HttpRequestWrapper wrap(HttpRequest httprequest)
    {
        if(httprequest == null)
            return null;
        if(httprequest instanceof HttpEntityEnclosingRequest)
            return new HttpEntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httprequest);
        else
            return new HttpRequestWrapper(httprequest);
    }

    public void abort()
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public String getMethod()
    {
        return method;
    }

    public HttpRequest getOriginal()
    {
        return original;
    }

    public HttpParams getParams()
    {
        if(params == null)
            params = original.getParams().copy();
        return params;
    }

    public ProtocolVersion getProtocolVersion()
    {
        if(version != null)
            return version;
        else
            return original.getProtocolVersion();
    }

    public RequestLine getRequestLine()
    {
        String s;
        if(uri != null)
            s = uri.toASCIIString();
        else
            s = original.getRequestLine().getUri();
        if(s == null || s.length() == 0)
            s = "/";
        return new BasicRequestLine(method, s, getProtocolVersion());
    }

    public URI getURI()
    {
        return uri;
    }

    public boolean isAborted()
    {
        return false;
    }

    public void setProtocolVersion(ProtocolVersion protocolversion)
    {
        version = protocolversion;
    }

    public void setURI(URI uri1)
    {
        uri = uri1;
    }

    public String toString()
    {
        return (new StringBuilder()).append(getRequestLine()).append(" ").append(headergroup).toString();
    }

    private final String method;
    private final HttpRequest original;
    private URI uri;
    private ProtocolVersion version;
}
