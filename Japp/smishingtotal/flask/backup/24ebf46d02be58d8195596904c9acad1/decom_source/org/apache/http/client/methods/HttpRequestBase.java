// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.params.HttpProtocolParams;

// Referenced classes of package org.apache.http.client.methods:
//            AbstractExecutionAwareRequest, HttpUriRequest, Configurable

public abstract class HttpRequestBase extends AbstractExecutionAwareRequest
    implements HttpUriRequest, Configurable
{

    public HttpRequestBase()
    {
    }

    public RequestConfig getConfig()
    {
        return config;
    }

    public abstract String getMethod();

    public ProtocolVersion getProtocolVersion()
    {
        if(version != null)
            return version;
        else
            return HttpProtocolParams.getVersion(getParams());
    }

    public RequestLine getRequestLine()
    {
        String s = getMethod();
        ProtocolVersion protocolversion = getProtocolVersion();
        URI uri1 = getURI();
        String s1 = null;
        if(uri1 != null)
            s1 = uri1.toASCIIString();
        if(s1 == null || s1.length() == 0)
            s1 = "/";
        return new BasicRequestLine(s, s1, protocolversion);
    }

    public URI getURI()
    {
        return uri;
    }

    public void releaseConnection()
    {
        reset();
    }

    public void setConfig(RequestConfig requestconfig)
    {
        config = requestconfig;
    }

    public void setProtocolVersion(ProtocolVersion protocolversion)
    {
        version = protocolversion;
    }

    public void setURI(URI uri1)
    {
        uri = uri1;
    }

    public void started()
    {
    }

    public String toString()
    {
        return (new StringBuilder()).append(getMethod()).append(" ").append(getURI()).append(" ").append(getProtocolVersion()).toString();
    }

    private RequestConfig config;
    private URI uri;
    private ProtocolVersion version;
}
