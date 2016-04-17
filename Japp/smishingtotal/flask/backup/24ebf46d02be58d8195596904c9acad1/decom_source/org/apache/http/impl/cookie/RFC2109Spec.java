// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.*;
import org.apache.http.Header;
import org.apache.http.cookie.*;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.cookie:
//            CookieSpecBase, RFC2109VersionHandler, BasicPathHandler, RFC2109DomainHandler, 
//            BasicMaxAgeHandler, BasicSecureHandler, BasicCommentHandler, BasicExpiresHandler

public class RFC2109Spec extends CookieSpecBase
{

    public RFC2109Spec()
    {
        this(null, false);
    }

    public RFC2109Spec(String as[], boolean flag)
    {
        if(as != null)
            datepatterns = (String[])as.clone();
        else
            datepatterns = DATE_PATTERNS;
        oneHeader = flag;
        registerAttribHandler("version", new RFC2109VersionHandler());
        registerAttribHandler("path", new BasicPathHandler());
        registerAttribHandler("domain", new RFC2109DomainHandler());
        registerAttribHandler("max-age", new BasicMaxAgeHandler());
        registerAttribHandler("secure", new BasicSecureHandler());
        registerAttribHandler("comment", new BasicCommentHandler());
        registerAttribHandler("expires", new BasicExpiresHandler(datepatterns));
    }

    private List doFormatManyHeaders(List list)
    {
        ArrayList arraylist = new ArrayList(list.size());
        CharArrayBuffer chararraybuffer;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); arraylist.add(new BufferedHeader(chararraybuffer)))
        {
            Cookie cookie = (Cookie)iterator.next();
            int i = cookie.getVersion();
            chararraybuffer = new CharArrayBuffer(40);
            chararraybuffer.append("Cookie: ");
            chararraybuffer.append("$Version=");
            chararraybuffer.append(Integer.toString(i));
            chararraybuffer.append("; ");
            formatCookieAsVer(chararraybuffer, cookie, i);
        }

        return arraylist;
    }

    private List doFormatOneHeader(List list)
    {
        int i = 0x7fffffff;
        Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Cookie cookie1 = (Cookie)iterator.next();
            if(cookie1.getVersion() < i)
                i = cookie1.getVersion();
        } while(true);
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(40 * list.size());
        chararraybuffer.append("Cookie");
        chararraybuffer.append(": ");
        chararraybuffer.append("$Version=");
        chararraybuffer.append(Integer.toString(i));
        Cookie cookie;
        for(Iterator iterator1 = list.iterator(); iterator1.hasNext(); formatCookieAsVer(chararraybuffer, cookie, i))
        {
            cookie = (Cookie)iterator1.next();
            chararraybuffer.append("; ");
        }

        ArrayList arraylist = new ArrayList(1);
        arraylist.add(new BufferedHeader(chararraybuffer));
        return arraylist;
    }

    protected void formatCookieAsVer(CharArrayBuffer chararraybuffer, Cookie cookie, int i)
    {
        formatParamAsVer(chararraybuffer, cookie.getName(), cookie.getValue(), i);
        if(cookie.getPath() != null && (cookie instanceof ClientCookie) && ((ClientCookie)cookie).containsAttribute("path"))
        {
            chararraybuffer.append("; ");
            formatParamAsVer(chararraybuffer, "$Path", cookie.getPath(), i);
        }
        if(cookie.getDomain() != null && (cookie instanceof ClientCookie) && ((ClientCookie)cookie).containsAttribute("domain"))
        {
            chararraybuffer.append("; ");
            formatParamAsVer(chararraybuffer, "$Domain", cookie.getDomain(), i);
        }
    }

    public List formatCookies(List list)
    {
        Args.notEmpty(list, "List of cookies");
        Object obj;
        if(list.size() > 1)
        {
            obj = new ArrayList(list);
            Collections.sort(((List) (obj)), PATH_COMPARATOR);
        } else
        {
            obj = list;
        }
        if(oneHeader)
            return doFormatOneHeader(((List) (obj)));
        else
            return doFormatManyHeaders(((List) (obj)));
    }

    protected void formatParamAsVer(CharArrayBuffer chararraybuffer, String s, String s1, int i)
    {
label0:
        {
            chararraybuffer.append(s);
            chararraybuffer.append("=");
            if(s1 != null)
            {
                if(i <= 0)
                    break label0;
                chararraybuffer.append('"');
                chararraybuffer.append(s1);
                chararraybuffer.append('"');
            }
            return;
        }
        chararraybuffer.append(s1);
    }

    public int getVersion()
    {
        return 1;
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
        else
            return parse(header.getElements(), cookieorigin);
    }

    public String toString()
    {
        return "rfc2109";
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(cookie, "Cookie");
        String s = cookie.getName();
        if(s.indexOf(' ') != -1)
            throw new CookieRestrictionViolationException("Cookie name may not contain blanks");
        if(s.startsWith("$"))
        {
            throw new CookieRestrictionViolationException("Cookie name may not start with $");
        } else
        {
            super.validate(cookie, cookieorigin);
            return;
        }
    }

    private static final String DATE_PATTERNS[] = {
        "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy"
    };
    private static final CookiePathComparator PATH_COMPARATOR = new CookiePathComparator();
    private final String datepatterns[];
    private final boolean oneHeader;

}
