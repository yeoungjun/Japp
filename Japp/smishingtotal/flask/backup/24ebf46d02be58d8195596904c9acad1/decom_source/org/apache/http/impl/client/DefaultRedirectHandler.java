// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

// Referenced classes of package org.apache.http.impl.client:
//            RedirectLocations

public class DefaultRedirectHandler
    implements RedirectHandler
{

    public DefaultRedirectHandler()
    {
    }

    public URI getLocationURI(HttpResponse httpresponse, HttpContext httpcontext)
        throws ProtocolException
    {
        Args.notNull(httpresponse, "HTTP response");
        Header header = httpresponse.getFirstHeader("location");
        if(header == null)
            throw new ProtocolException((new StringBuilder()).append("Received redirect response ").append(httpresponse.getStatusLine()).append(" but no location header").toString());
        String s = header.getValue();
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Redirect requested to location '").append(s).append("'").toString());
        URI uri;
        HttpParams httpparams;
        try
        {
            uri = new URI(s);
        }
        catch(URISyntaxException urisyntaxexception2)
        {
            throw new ProtocolException((new StringBuilder()).append("Invalid redirect URI: ").append(s).toString(), urisyntaxexception2);
        }
        httpparams = httpresponse.getParams();
        if(!uri.isAbsolute())
        {
            if(httpparams.isParameterTrue("http.protocol.reject-relative-redirect"))
                throw new ProtocolException((new StringBuilder()).append("Relative redirect location '").append(uri).append("' not allowed").toString());
            HttpHost httphost = (HttpHost)httpcontext.getAttribute("http.target_host");
            Asserts.notNull(httphost, "Target host");
            HttpRequest httprequest = (HttpRequest)httpcontext.getAttribute("http.request");
            URI uri2;
            URI uri3;
            try
            {
                uri3 = URIUtils.resolve(URIUtils.rewriteURI(new URI(httprequest.getRequestLine().getUri()), httphost, true), uri);
            }
            catch(URISyntaxException urisyntaxexception1)
            {
                throw new ProtocolException(urisyntaxexception1.getMessage(), urisyntaxexception1);
            }
            uri = uri3;
        }
        if(httpparams.isParameterFalse("http.protocol.allow-circular-redirects"))
        {
            RedirectLocations redirectlocations = (RedirectLocations)httpcontext.getAttribute("http.protocol.redirect-locations");
            if(redirectlocations == null)
            {
                redirectlocations = new RedirectLocations();
                httpcontext.setAttribute("http.protocol.redirect-locations", redirectlocations);
            }
            URI uri1;
            if(uri.getFragment() != null)
            {
                try
                {
                    uri2 = URIUtils.rewriteURI(uri, new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()), true);
                }
                catch(URISyntaxException urisyntaxexception)
                {
                    throw new ProtocolException(urisyntaxexception.getMessage(), urisyntaxexception);
                }
                uri1 = uri2;
            } else
            {
                uri1 = uri;
            }
            if(redirectlocations.contains(uri1))
                throw new CircularRedirectException((new StringBuilder()).append("Circular redirect to '").append(uri1).append("'").toString());
            redirectlocations.add(uri1);
        }
        return uri;
    }

    public boolean isRedirectRequested(HttpResponse httpresponse, HttpContext httpcontext)
    {
        Args.notNull(httpresponse, "HTTP response");
        httpresponse.getStatusLine().getStatusCode();
        JVM INSTR tableswitch 301 307: default 60
    //                   301 62
    //                   302 62
    //                   303 107
    //                   304 60
    //                   305 60
    //                   306 60
    //                   307 62;
           goto _L1 _L2 _L2 _L3 _L1 _L1 _L1 _L2
_L1:
        String s;
        return false;
_L2:
        if((s = ((HttpRequest)httpcontext.getAttribute("http.request")).getRequestLine().getMethod()).equalsIgnoreCase("GET") || s.equalsIgnoreCase("HEAD"))
            return true;
          goto _L1
_L3:
        return true;
    }

    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private final Log log = LogFactory.getLog(getClass());
}
