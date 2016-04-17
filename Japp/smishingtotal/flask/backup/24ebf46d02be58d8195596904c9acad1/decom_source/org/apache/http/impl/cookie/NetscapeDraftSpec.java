// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.cookie.*;
import org.apache.http.message.BufferedHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.cookie:
//            CookieSpecBase, BasicPathHandler, NetscapeDomainHandler, BasicMaxAgeHandler, 
//            BasicSecureHandler, BasicCommentHandler, BasicExpiresHandler, NetscapeDraftHeaderParser

public class NetscapeDraftSpec extends CookieSpecBase
{

    public NetscapeDraftSpec()
    {
        this(null);
    }

    public NetscapeDraftSpec(String as[])
    {
        if(as != null)
            datepatterns = (String[])as.clone();
        else
            datepatterns = (new String[] {
                "EEE, dd-MMM-yy HH:mm:ss z"
            });
        registerAttribHandler("path", new BasicPathHandler());
        registerAttribHandler("domain", new NetscapeDomainHandler());
        registerAttribHandler("max-age", new BasicMaxAgeHandler());
        registerAttribHandler("secure", new BasicSecureHandler());
        registerAttribHandler("comment", new BasicCommentHandler());
        registerAttribHandler("expires", new BasicExpiresHandler(datepatterns));
    }

    public List formatCookies(List list)
    {
        Args.notEmpty(list, "List of cookies");
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(20 * list.size());
        chararraybuffer.append("Cookie");
        chararraybuffer.append(": ");
        for(int i = 0; i < list.size(); i++)
        {
            Cookie cookie = (Cookie)list.get(i);
            if(i > 0)
                chararraybuffer.append("; ");
            chararraybuffer.append(cookie.getName());
            String s = cookie.getValue();
            if(s != null)
            {
                chararraybuffer.append("=");
                chararraybuffer.append(s);
            }
        }

        ArrayList arraylist = new ArrayList(1);
        arraylist.add(new BufferedHeader(chararraybuffer));
        return arraylist;
    }

    public int getVersion()
    {
        return 0;
    }

    public Header getVersionHeader()
    {
        return null;
    }

    public List parse(Header header, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(header, "Header");
        Args.notNull(cookieorigin, "Cookie origin");
        if(!header.getName().equalsIgnoreCase("Set-Cookie"))
            throw new MalformedCookieException((new StringBuilder()).append("Unrecognized cookie header '").append(header.toString()).append("'").toString());
        NetscapeDraftHeaderParser netscapedraftheaderparser = NetscapeDraftHeaderParser.DEFAULT;
        CharArrayBuffer chararraybuffer;
        ParserCursor parsercursor;
        HeaderElement aheaderelement[];
        if(header instanceof FormattedHeader)
        {
            chararraybuffer = ((FormattedHeader)header).getBuffer();
            parsercursor = new ParserCursor(((FormattedHeader)header).getValuePos(), chararraybuffer.length());
        } else
        {
            String s = header.getValue();
            if(s == null)
                throw new MalformedCookieException("Header value is null");
            chararraybuffer = new CharArrayBuffer(s.length());
            chararraybuffer.append(s);
            parsercursor = new ParserCursor(0, chararraybuffer.length());
        }
        aheaderelement = new HeaderElement[1];
        aheaderelement[0] = netscapedraftheaderparser.parseHeader(chararraybuffer, parsercursor);
        return parse(aheaderelement, cookieorigin);
    }

    public String toString()
    {
        return "netscape";
    }

    protected static final String EXPIRES_PATTERN = "EEE, dd-MMM-yy HH:mm:ss z";
    private final String datepatterns[];
}
