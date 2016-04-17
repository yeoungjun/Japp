// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.protocol;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Lookup;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.cookie.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

// Referenced classes of package org.apache.http.client.protocol:
//            HttpClientContext

public class RequestAddCookies
    implements HttpRequestInterceptor
{

    public RequestAddCookies()
    {
    }

    public void process(HttpRequest httprequest, HttpContext httpcontext)
        throws HttpException, IOException
    {
        HttpClientContext httpclientcontext;
        CookieStore cookiestore;
        Lookup lookup;
        HttpHost httphost;
        RouteInfo routeinfo;
        String s;
        URI uri1;
label0:
        {
            Args.notNull(httprequest, "HTTP request");
            Args.notNull(httpcontext, "HTTP context");
            if(httprequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT"))
                return;
            httpclientcontext = HttpClientContext.adapt(httpcontext);
            cookiestore = httpclientcontext.getCookieStore();
            if(cookiestore == null)
            {
                log.debug("Cookie store not specified in HTTP context");
                return;
            }
            lookup = httpclientcontext.getCookieSpecRegistry();
            if(lookup == null)
            {
                log.debug("CookieSpec registry not specified in HTTP context");
                return;
            }
            httphost = httpclientcontext.getTargetHost();
            if(httphost == null)
            {
                log.debug("Target host not set in the context");
                return;
            }
            routeinfo = httpclientcontext.getHttpRoute();
            if(routeinfo == null)
            {
                log.debug("Connection route not set in the context");
                return;
            }
            s = httpclientcontext.getRequestConfig().getCookieSpec();
            if(s == null)
                s = "best-match";
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("CookieSpec selected: ").append(s).toString());
            if(httprequest instanceof HttpUriRequest)
            {
                uri1 = ((HttpUriRequest)httprequest).getURI();
                break label0;
            }
        }
        uri = new URI(httprequest.getRequestLine().getUri());
        uri1 = uri;
        continue; /* Loop/switch isn't completed */
_L2:
        URI uri;
        String s1;
        String s2;
        int i;
        boolean flag;
        CookieOrigin cookieorigin;
        CookieSpecProvider cookiespecprovider;
        if(uri1 != null)
            s1 = uri1.getPath();
        else
            s1 = null;
        s2 = httphost.getHostName();
        i = httphost.getPort();
        if(i < 0)
            i = routeinfo.getTargetHost().getPort();
        if(i < 0)
            i = 0;
        if(TextUtils.isEmpty(s1))
            s1 = "/";
        flag = routeinfo.isSecure();
        cookieorigin = new CookieOrigin(s2, i, s1, flag);
        cookiespecprovider = (CookieSpecProvider)lookup.lookup(s);
        if(cookiespecprovider == null)
            throw new HttpException((new StringBuilder()).append("Unsupported cookie policy: ").append(s).toString());
        CookieSpec cookiespec = cookiespecprovider.create(httpclientcontext);
        ArrayList arraylist = new ArrayList(cookiestore.getCookies());
        ArrayList arraylist1 = new ArrayList();
        Date date = new Date();
        Iterator iterator = arraylist.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Cookie cookie1 = (Cookie)iterator.next();
            if(!cookie1.isExpired(date))
            {
                if(cookiespec.match(cookie1, cookieorigin))
                {
                    if(log.isDebugEnabled())
                        log.debug((new StringBuilder()).append("Cookie ").append(cookie1).append(" match ").append(cookieorigin).toString());
                    arraylist1.add(cookie1);
                }
            } else
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Cookie ").append(cookie1).append(" expired").toString());
        } while(true);
        if(!arraylist1.isEmpty())
        {
            for(Iterator iterator2 = cookiespec.formatCookies(arraylist1).iterator(); iterator2.hasNext(); httprequest.addHeader((Header)iterator2.next()));
        }
        int j = cookiespec.getVersion();
        if(j > 0)
        {
            boolean flag1 = false;
            Iterator iterator1 = arraylist1.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                Cookie cookie = (Cookie)iterator1.next();
                if(j != cookie.getVersion() || !(cookie instanceof SetCookie2))
                    flag1 = true;
            } while(true);
            if(flag1)
            {
                Header header = cookiespec.getVersionHeader();
                if(header != null)
                    httprequest.addHeader(header);
            }
        }
        httpcontext.setAttribute("http.cookie-spec", cookiespec);
        httpcontext.setAttribute("http.cookie-origin", cookieorigin);
        return;
        URISyntaxException urisyntaxexception;
        urisyntaxexception;
        uri1 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    private final Log log = LogFactory.getLog(getClass());
}
