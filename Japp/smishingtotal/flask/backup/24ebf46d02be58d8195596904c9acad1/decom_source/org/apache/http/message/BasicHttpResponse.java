// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.message;

import java.util.Locale;
import org.apache.http.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.message:
//            AbstractHttpMessage, BasicStatusLine

public class BasicHttpResponse extends AbstractHttpMessage
    implements HttpResponse
{

    public BasicHttpResponse(ProtocolVersion protocolversion, int i, String s)
    {
        Args.notNegative(i, "Status code");
        statusline = null;
        ver = protocolversion;
        code = i;
        reasonPhrase = s;
        reasonCatalog = null;
        locale = null;
    }

    public BasicHttpResponse(StatusLine statusline1)
    {
        statusline = (StatusLine)Args.notNull(statusline1, "Status line");
        ver = statusline1.getProtocolVersion();
        code = statusline1.getStatusCode();
        reasonPhrase = statusline1.getReasonPhrase();
        reasonCatalog = null;
        locale = null;
    }

    public BasicHttpResponse(StatusLine statusline1, ReasonPhraseCatalog reasonphrasecatalog, Locale locale1)
    {
        statusline = (StatusLine)Args.notNull(statusline1, "Status line");
        ver = statusline1.getProtocolVersion();
        code = statusline1.getStatusCode();
        reasonPhrase = statusline1.getReasonPhrase();
        reasonCatalog = reasonphrasecatalog;
        locale = locale1;
    }

    public HttpEntity getEntity()
    {
        return entity;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public ProtocolVersion getProtocolVersion()
    {
        return ver;
    }

    protected String getReason(int i)
    {
        if(reasonCatalog != null)
        {
            ReasonPhraseCatalog reasonphrasecatalog = reasonCatalog;
            Locale locale1;
            if(locale != null)
                locale1 = locale;
            else
                locale1 = Locale.getDefault();
            return reasonphrasecatalog.getReason(i, locale1);
        } else
        {
            return null;
        }
    }

    public StatusLine getStatusLine()
    {
        if(statusline == null)
        {
            Object obj;
            if(ver != null)
                obj = ver;
            else
                obj = HttpVersion.HTTP_1_1;
            statusline = new BasicStatusLine(((ProtocolVersion) (obj)), code, reasonPhrase);
        }
        return statusline;
    }

    public void setEntity(HttpEntity httpentity)
    {
        entity = httpentity;
    }

    public void setLocale(Locale locale1)
    {
        locale = (Locale)Args.notNull(locale1, "Locale");
        statusline = null;
    }

    public void setReasonPhrase(String s)
    {
        statusline = null;
        reasonPhrase = s;
    }

    public void setStatusCode(int i)
    {
        Args.notNegative(i, "Status code");
        statusline = null;
        code = i;
    }

    public void setStatusLine(ProtocolVersion protocolversion, int i)
    {
        Args.notNegative(i, "Status code");
        statusline = null;
        ver = protocolversion;
        code = i;
        reasonPhrase = null;
    }

    public void setStatusLine(ProtocolVersion protocolversion, int i, String s)
    {
        Args.notNegative(i, "Status code");
        statusline = null;
        ver = protocolversion;
        code = i;
        reasonPhrase = s;
    }

    public void setStatusLine(StatusLine statusline1)
    {
        statusline = (StatusLine)Args.notNull(statusline1, "Status line");
        ver = statusline1.getProtocolVersion();
        code = statusline1.getStatusCode();
        reasonPhrase = statusline1.getReasonPhrase();
    }

    public String toString()
    {
        StatusLine statusline1 = getStatusLine();
        return (new StringBuilder()).append(statusline1).append(" ").append(headergroup).toString();
    }

    private int code;
    private HttpEntity entity;
    private Locale locale;
    private final ReasonPhraseCatalog reasonCatalog;
    private String reasonPhrase;
    private StatusLine statusline;
    private ProtocolVersion ver;
}
