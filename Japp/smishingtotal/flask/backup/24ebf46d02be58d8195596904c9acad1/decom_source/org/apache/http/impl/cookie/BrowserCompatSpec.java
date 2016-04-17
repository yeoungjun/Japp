// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.cookie.*;
import org.apache.http.message.*;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.cookie:
//            CookieSpecBase, BasicPathHandler, BasicDomainHandler, BasicMaxAgeHandler, 
//            BasicSecureHandler, BasicCommentHandler, BasicExpiresHandler, BrowserCompatVersionAttributeHandler, 
//            NetscapeDraftHeaderParser

public class BrowserCompatSpec extends CookieSpecBase
{

    public BrowserCompatSpec()
    {
        this(null, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpec(String as[])
    {
        this(as, BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpec(String as[], BrowserCompatSpecFactory.SecurityLevel securitylevel)
    {
        static class _cls2
        {

            static final int $SwitchMap$org$apache$http$impl$cookie$BrowserCompatSpecFactory$SecurityLevel[];

            static 
            {
                $SwitchMap$org$apache$http$impl$cookie$BrowserCompatSpecFactory$SecurityLevel = new int[BrowserCompatSpecFactory.SecurityLevel.values().length];
                try
                {
                    $SwitchMap$org$apache$http$impl$cookie$BrowserCompatSpecFactory$SecurityLevel[BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_DEFAULT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$apache$http$impl$cookie$BrowserCompatSpecFactory$SecurityLevel[BrowserCompatSpecFactory.SecurityLevel.SECURITYLEVEL_IE_MEDIUM.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1)
                {
                    return;
                }
            }
        }

        if(as != null)
            datepatterns = (String[])as.clone();
        else
            datepatterns = DEFAULT_DATE_PATTERNS;
        _cls2..SwitchMap.org.apache.http.impl.cookie.BrowserCompatSpecFactory.SecurityLevel[securitylevel.ordinal()];
        JVM INSTR tableswitch 1 2: default 48
    //                   1 68
    //                   2 164;
           goto _L1 _L2 _L3
_L1:
        throw new RuntimeException("Unknown security level");
_L2:
        registerAttribHandler("path", new BasicPathHandler());
_L5:
        registerAttribHandler("domain", new BasicDomainHandler());
        registerAttribHandler("max-age", new BasicMaxAgeHandler());
        registerAttribHandler("secure", new BasicSecureHandler());
        registerAttribHandler("comment", new BasicCommentHandler());
        registerAttribHandler("expires", new BasicExpiresHandler(datepatterns));
        registerAttribHandler("version", new BrowserCompatVersionAttributeHandler());
        return;
_L3:
        registerAttribHandler("path", new BasicPathHandler() {

            public void validate(Cookie cookie, CookieOrigin cookieorigin)
                throws MalformedCookieException
            {
            }

            final BrowserCompatSpec this$0;

            
            {
                this$0 = BrowserCompatSpec.this;
                super();
            }
        });
        if(true) goto _L5; else goto _L4
_L4:
    }

    public List formatCookies(List list)
    {
        Args.notEmpty(list, "List of cookies");
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(20 * list.size());
        chararraybuffer.append("Cookie");
        chararraybuffer.append(": ");
        int i = 0;
        while(i < list.size()) 
        {
            Cookie cookie = (Cookie)list.get(i);
            if(i > 0)
                chararraybuffer.append("; ");
            String s = cookie.getName();
            String s1 = cookie.getValue();
            if(cookie.getVersion() > 0 && (!s1.startsWith("\"") || !s1.endsWith("\"")))
            {
                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(chararraybuffer, new BasicHeaderElement(s, s1), false);
            } else
            {
                chararraybuffer.append(s);
                chararraybuffer.append("=");
                if(s1 != null)
                    chararraybuffer.append(s1);
            }
            i++;
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
        HeaderElement aheaderelement[] = header.getElements();
        boolean flag = false;
        boolean flag1 = false;
        HeaderElement aheaderelement1[] = aheaderelement;
        int i = aheaderelement1.length;
        for(int j = 0; j < i; j++)
        {
            HeaderElement headerelement = aheaderelement1[j];
            if(headerelement.getParameterByName("version") != null)
                flag = true;
            if(headerelement.getParameterByName("expires") != null)
                flag1 = true;
        }

        if(flag1 || !flag)
        {
            NetscapeDraftHeaderParser netscapedraftheaderparser = NetscapeDraftHeaderParser.DEFAULT;
            CharArrayBuffer chararraybuffer;
            ParserCursor parsercursor;
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
        }
        return parse(aheaderelement, cookieorigin);
    }

    public String toString()
    {
        return "compatibility";
    }

    private static final String DEFAULT_DATE_PATTERNS[] = {
        "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", 
        "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"
    };
    private final String datepatterns[];

}
