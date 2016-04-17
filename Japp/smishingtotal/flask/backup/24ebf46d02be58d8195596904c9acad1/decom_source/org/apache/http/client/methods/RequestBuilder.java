// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.methods;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.client.methods:
//            HttpUriRequest, Configurable, HttpRequestBase, HttpEntityEnclosingRequestBase

public class RequestBuilder
{
    static class InternalEntityEclosingRequest extends HttpEntityEnclosingRequestBase
    {

        public String getMethod()
        {
            return method;
        }

        private final String method;

        InternalEntityEclosingRequest(String s)
        {
            method = s;
        }
    }

    static class InternalRequest extends HttpRequestBase
    {

        public String getMethod()
        {
            return method;
        }

        private final String method;

        InternalRequest(String s)
        {
            method = s;
        }
    }


    RequestBuilder()
    {
        this(null);
    }

    RequestBuilder(String s)
    {
        method = s;
    }

    public static RequestBuilder copy(HttpRequest httprequest)
    {
        Args.notNull(httprequest, "HTTP request");
        return (new RequestBuilder()).doCopy(httprequest);
    }

    public static RequestBuilder create(String s)
    {
        Args.notBlank(s, "HTTP method");
        return new RequestBuilder(s);
    }

    public static RequestBuilder delete()
    {
        return new RequestBuilder("DELETE");
    }

    private RequestBuilder doCopy(HttpRequest httprequest)
    {
        if(httprequest == null)
            return this;
        method = httprequest.getRequestLine().getMethod();
        version = httprequest.getRequestLine().getProtocolVersion();
        if(httprequest instanceof HttpUriRequest)
            uri = ((HttpUriRequest)httprequest).getURI();
        else
            uri = URI.create(httprequest.getRequestLine().getMethod());
        if(headergroup == null)
            headergroup = new HeaderGroup();
        headergroup.clear();
        headergroup.setHeaders(httprequest.getAllHeaders());
        if(httprequest instanceof HttpEntityEnclosingRequest)
            entity = ((HttpEntityEnclosingRequest)httprequest).getEntity();
        else
            entity = null;
        if(httprequest instanceof Configurable)
            config = ((Configurable)httprequest).getConfig();
        else
            config = null;
        parameters = null;
        return this;
    }

    public static RequestBuilder get()
    {
        return new RequestBuilder("GET");
    }

    public static RequestBuilder head()
    {
        return new RequestBuilder("HEAD");
    }

    public static RequestBuilder options()
    {
        return new RequestBuilder("OPTIONS");
    }

    public static RequestBuilder post()
    {
        return new RequestBuilder("POST");
    }

    public static RequestBuilder put()
    {
        return new RequestBuilder("PUT");
    }

    public static RequestBuilder trace()
    {
        return new RequestBuilder("TRACE");
    }

    public RequestBuilder addHeader(String s, String s1)
    {
        if(headergroup == null)
            headergroup = new HeaderGroup();
        headergroup.addHeader(new BasicHeader(s, s1));
        return this;
    }

    public RequestBuilder addHeader(Header header)
    {
        if(headergroup == null)
            headergroup = new HeaderGroup();
        headergroup.addHeader(header);
        return this;
    }

    public RequestBuilder addParameter(String s, String s1)
    {
        return addParameter(((NameValuePair) (new BasicNameValuePair(s, s1))));
    }

    public RequestBuilder addParameter(NameValuePair namevaluepair)
    {
        Args.notNull(namevaluepair, "Name value pair");
        if(parameters == null)
            parameters = new LinkedList();
        parameters.add(namevaluepair);
        return this;
    }

    public transient RequestBuilder addParameters(NameValuePair anamevaluepair[])
    {
        int i = anamevaluepair.length;
        for(int j = 0; j < i; j++)
            addParameter(anamevaluepair[j]);

        return this;
    }

    public HttpUriRequest build()
    {
        URI uri1;
        Object obj;
        if(uri != null)
            uri1 = uri;
        else
            uri1 = URI.create("/");
        obj = entity;
        if(parameters == null || parameters.isEmpty()) goto _L2; else goto _L1
_L1:
        if(obj != null || !"POST".equalsIgnoreCase(method) && !"PUT".equalsIgnoreCase(method)) goto _L4; else goto _L3
_L3:
        obj = new UrlEncodedFormEntity(parameters, HTTP.DEF_CONTENT_CHARSET);
_L2:
        Object obj1;
        URI uri2;
        if(obj == null)
        {
            obj1 = new InternalRequest(method);
        } else
        {
            InternalEntityEclosingRequest internalentityeclosingrequest = new InternalEntityEclosingRequest(method);
            internalentityeclosingrequest.setEntity(((HttpEntity) (obj)));
            obj1 = internalentityeclosingrequest;
        }
        ((HttpRequestBase) (obj1)).setProtocolVersion(version);
        ((HttpRequestBase) (obj1)).setURI(uri1);
        if(headergroup != null)
            ((HttpRequestBase) (obj1)).setHeaders(headergroup.getAllHeaders());
        ((HttpRequestBase) (obj1)).setConfig(config);
        return ((HttpUriRequest) (obj1));
_L4:
        uri2 = (new URIBuilder(uri1)).addParameters(parameters).build();
        uri1 = uri2;
        continue; /* Loop/switch isn't completed */
        URISyntaxException urisyntaxexception;
        urisyntaxexception;
        if(true) goto _L2; else goto _L5
_L5:
    }

    public RequestConfig getConfig()
    {
        return config;
    }

    public HttpEntity getEntity()
    {
        return entity;
    }

    public Header getFirstHeader(String s)
    {
        if(headergroup != null)
            return headergroup.getFirstHeader(s);
        else
            return null;
    }

    public Header[] getHeaders(String s)
    {
        if(headergroup != null)
            return headergroup.getHeaders(s);
        else
            return null;
    }

    public Header getLastHeader(String s)
    {
        if(headergroup != null)
            return headergroup.getLastHeader(s);
        else
            return null;
    }

    public String getMethod()
    {
        return method;
    }

    public List getParameters()
    {
        if(parameters != null)
            return new ArrayList(parameters);
        else
            return new ArrayList();
    }

    public URI getUri()
    {
        return uri;
    }

    public ProtocolVersion getVersion()
    {
        return version;
    }

    public RequestBuilder removeHeader(Header header)
    {
        if(headergroup == null)
            headergroup = new HeaderGroup();
        headergroup.removeHeader(header);
        return this;
    }

    public RequestBuilder removeHeaders(String s)
    {
        if(s != null && headergroup != null)
        {
            HeaderIterator headeriterator = headergroup.iterator();
            while(headeriterator.hasNext()) 
                if(s.equalsIgnoreCase(headeriterator.nextHeader().getName()))
                    headeriterator.remove();
        }
        return this;
    }

    public RequestBuilder setConfig(RequestConfig requestconfig)
    {
        config = requestconfig;
        return this;
    }

    public RequestBuilder setEntity(HttpEntity httpentity)
    {
        entity = httpentity;
        return this;
    }

    public RequestBuilder setHeader(String s, String s1)
    {
        if(headergroup != null)
            headergroup = new HeaderGroup();
        headergroup.updateHeader(new BasicHeader(s, s1));
        return this;
    }

    public RequestBuilder setHeader(Header header)
    {
        if(headergroup != null)
            headergroup = new HeaderGroup();
        headergroup.updateHeader(header);
        return this;
    }

    public RequestBuilder setUri(String s)
    {
        URI uri1;
        if(s != null)
            uri1 = URI.create(s);
        else
            uri1 = null;
        uri = uri1;
        return this;
    }

    public RequestBuilder setUri(URI uri1)
    {
        uri = uri1;
        return this;
    }

    public RequestBuilder setVersion(ProtocolVersion protocolversion)
    {
        version = protocolversion;
        return this;
    }

    private RequestConfig config;
    private HttpEntity entity;
    private HeaderGroup headergroup;
    private String method;
    private LinkedList parameters;
    private URI uri;
    private ProtocolVersion version;
}
