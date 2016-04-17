// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.client:
//            RedirectLocations

public class DefaultRedirectStrategy
    implements RedirectStrategy
{

    public DefaultRedirectStrategy()
    {
    }

    protected URI createLocationURI(String s)
        throws ProtocolException
    {
        URIBuilder uribuilder;
        String s1;
        URI uri;
        try
        {
            uribuilder = new URIBuilder((new URI(s)).normalize());
            s1 = uribuilder.getHost();
        }
        catch(URISyntaxException urisyntaxexception)
        {
            throw new ProtocolException((new StringBuilder()).append("Invalid redirect URI: ").append(s).toString(), urisyntaxexception);
        }
        if(s1 == null)
            break MISSING_BLOCK_LABEL_43;
        uribuilder.setHost(s1.toLowerCase(Locale.US));
        if(TextUtils.isEmpty(uribuilder.getPath()))
            uribuilder.setPath("/");
        uri = uribuilder.build();
        return uri;
    }

    public URI getLocationURI(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws ProtocolException
    {
        HttpClientContext httpclientcontext;
        RequestConfig requestconfig;
        URI uri;
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpresponse, "HTTP response");
        Args.notNull(httpcontext, "HTTP context");
        httpclientcontext = HttpClientContext.adapt(httpcontext);
        Header header = httpresponse.getFirstHeader("location");
        if(header == null)
            throw new ProtocolException((new StringBuilder()).append("Received redirect response ").append(httpresponse.getStatusLine()).append(" but no location header").toString());
        String s = header.getValue();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Redirect requested to location '").append(s).append("'").toString());
        requestconfig = httpclientcontext.getRequestConfig();
        uri = createLocationURI(s);
        URISyntaxException urisyntaxexception;
        if(uri.isAbsolute())
            break MISSING_BLOCK_LABEL_264;
        if(!requestconfig.isRelativeRedirectsAllowed())
            throw new ProtocolException((new StringBuilder()).append("Relative redirect location '").append(uri).append("' not allowed").toString());
        URI uri1;
        try
        {
            org.apache.http.HttpHost httphost = httpclientcontext.getTargetHost();
            Asserts.notNull(httphost, "Target host");
            uri1 = URIUtils.resolve(URIUtils.rewriteURI(new URI(httprequest.getRequestLine().getUri()), httphost, false), uri);
        }
        // Misplaced declaration of an exception variable
        catch(URISyntaxException urisyntaxexception)
        {
            throw new ProtocolException(urisyntaxexception.getMessage(), urisyntaxexception);
        }
        uri = uri1;
        RedirectLocations redirectlocations = (RedirectLocations)httpclientcontext.getAttribute("http.protocol.redirect-locations");
        if(redirectlocations == null)
        {
            redirectlocations = new RedirectLocations();
            httpcontext.setAttribute("http.protocol.redirect-locations", redirectlocations);
        }
        if(!requestconfig.isCircularRedirectsAllowed() && redirectlocations.contains(uri))
        {
            throw new CircularRedirectException((new StringBuilder()).append("Circular redirect to '").append(uri).append("'").toString());
        } else
        {
            redirectlocations.add(uri);
            return uri;
        }
    }

    public HttpUriRequest getRedirect(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws ProtocolException
    {
        URI uri = getLocationURI(httprequest, httpresponse, httpcontext);
        String s = httprequest.getRequestLine().getMethod();
        if(s.equalsIgnoreCase("HEAD"))
            return new HttpHead(uri);
        if(s.equalsIgnoreCase("GET"))
            return new HttpGet(uri);
        if(httpresponse.getStatusLine().getStatusCode() == 307)
            return RequestBuilder.copy(httprequest).setUri(uri).build();
        else
            return new HttpGet(uri);
    }

    protected boolean isRedirectable(String s)
    {
        String as[] = REDIRECT_METHODS;
        int i = as.length;
        for(int j = 0; j < i; j++)
            if(as[j].equalsIgnoreCase(s))
                return true;

        return false;
    }

    public boolean isRedirected(HttpRequest httprequest, HttpResponse httpresponse, HttpContext httpcontext)
        throws ProtocolException
    {
        boolean flag;
        int i;
        String s;
        Header header;
        flag = true;
        Args.notNull(httprequest, "HTTP request");
        Args.notNull(httpresponse, "HTTP response");
        i = httpresponse.getStatusLine().getStatusCode();
        s = httprequest.getRequestLine().getMethod();
        header = httpresponse.getFirstHeader("location");
        i;
        JVM INSTR tableswitch 301 307: default 96
    //                   301 118
    //                   302 102
    //                   303 99
    //                   304 96
    //                   305 96
    //                   306 96
    //                   307 118;
           goto _L1 _L2 _L3 _L4 _L1 _L1 _L1 _L2
_L1:
        flag = false;
_L4:
        return flag;
_L3:
        if(isRedirectable(s) && header != null) goto _L4; else goto _L5
_L5:
        return false;
_L2:
        return isRedirectable(s);
    }

    public static final DefaultRedirectStrategy INSTANCE = new DefaultRedirectStrategy();
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private static final String REDIRECT_METHODS[] = {
        "GET", "HEAD"
    };
    private final Log log = LogFactory.getLog(getClass());

}
