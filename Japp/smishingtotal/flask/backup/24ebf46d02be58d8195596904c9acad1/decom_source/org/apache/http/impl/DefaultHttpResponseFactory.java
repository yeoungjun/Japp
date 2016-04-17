// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.*;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl:
//            EnglishReasonPhraseCatalog

public class DefaultHttpResponseFactory
    implements HttpResponseFactory
{

    public DefaultHttpResponseFactory()
    {
        this(((ReasonPhraseCatalog) (EnglishReasonPhraseCatalog.INSTANCE)));
    }

    public DefaultHttpResponseFactory(ReasonPhraseCatalog reasonphrasecatalog)
    {
        reasonCatalog = (ReasonPhraseCatalog)Args.notNull(reasonphrasecatalog, "Reason phrase catalog");
    }

    protected Locale determineLocale(HttpContext httpcontext)
    {
        return Locale.getDefault();
    }

    public HttpResponse newHttpResponse(ProtocolVersion protocolversion, int i, HttpContext httpcontext)
    {
        Args.notNull(protocolversion, "HTTP version");
        Locale locale = determineLocale(httpcontext);
        return new BasicHttpResponse(new BasicStatusLine(protocolversion, i, reasonCatalog.getReason(i, locale)));
    }

    public HttpResponse newHttpResponse(StatusLine statusline, HttpContext httpcontext)
    {
        Args.notNull(statusline, "Status line");
        return new BasicHttpResponse(statusline);
    }

    public static final DefaultHttpResponseFactory INSTANCE = new DefaultHttpResponseFactory();
    protected final ReasonPhraseCatalog reasonCatalog;

}
