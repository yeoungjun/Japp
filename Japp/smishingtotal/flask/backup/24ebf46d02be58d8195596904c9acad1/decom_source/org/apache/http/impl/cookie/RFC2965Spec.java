// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.cookie;

import java.util.*;
import org.apache.http.*;
import org.apache.http.cookie.*;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.cookie:
//            RFC2109Spec, RFC2965DomainAttributeHandler, RFC2965PortAttributeHandler, RFC2965CommentUrlAttributeHandler, 
//            RFC2965DiscardAttributeHandler, RFC2965VersionAttributeHandler, BasicClientCookie2

public class RFC2965Spec extends RFC2109Spec
{

    public RFC2965Spec()
    {
        this(null, false);
    }

    public RFC2965Spec(String as[], boolean flag)
    {
        super(as, flag);
        registerAttribHandler("domain", new RFC2965DomainAttributeHandler());
        registerAttribHandler("port", new RFC2965PortAttributeHandler());
        registerAttribHandler("commenturl", new RFC2965CommentUrlAttributeHandler());
        registerAttribHandler("discard", new RFC2965DiscardAttributeHandler());
        registerAttribHandler("version", new RFC2965VersionAttributeHandler());
    }

    private static CookieOrigin adjustEffectiveHost(CookieOrigin cookieorigin)
    {
        String s = cookieorigin.getHost();
        boolean flag = true;
        int i = 0;
        do
        {
label0:
            {
                if(i < s.length())
                {
                    char c = s.charAt(i);
                    if(c != '.' && c != ':')
                        break label0;
                    flag = false;
                }
                if(flag)
                    cookieorigin = new CookieOrigin((new StringBuilder()).append(s).append(".local").toString(), cookieorigin.getPort(), cookieorigin.getPath(), cookieorigin.isSecure());
                return cookieorigin;
            }
            i++;
        } while(true);
    }

    private List createCookies(HeaderElement aheaderelement[], CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        ArrayList arraylist = new ArrayList(aheaderelement.length);
        int i = aheaderelement.length;
        for(int j = 0; j < i; j++)
        {
            HeaderElement headerelement = aheaderelement[j];
            String s = headerelement.getName();
            String s1 = headerelement.getValue();
            if(s == null || s.length() == 0)
                throw new MalformedCookieException("Cookie name may not be empty");
            BasicClientCookie2 basicclientcookie2 = new BasicClientCookie2(s, s1);
            basicclientcookie2.setPath(getDefaultPath(cookieorigin));
            basicclientcookie2.setDomain(getDefaultDomain(cookieorigin));
            int ai[] = new int[1];
            ai[0] = cookieorigin.getPort();
            basicclientcookie2.setPorts(ai);
            NameValuePair anamevaluepair[] = headerelement.getParameters();
            HashMap hashmap = new HashMap(anamevaluepair.length);
            for(int k = -1 + anamevaluepair.length; k >= 0; k--)
            {
                NameValuePair namevaluepair1 = anamevaluepair[k];
                hashmap.put(namevaluepair1.getName().toLowerCase(Locale.ENGLISH), namevaluepair1);
            }

            Iterator iterator = hashmap.entrySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                NameValuePair namevaluepair = (NameValuePair)((java.util.Map.Entry)iterator.next()).getValue();
                String s2 = namevaluepair.getName().toLowerCase(Locale.ENGLISH);
                basicclientcookie2.setAttribute(s2, namevaluepair.getValue());
                CookieAttributeHandler cookieattributehandler = findAttribHandler(s2);
                if(cookieattributehandler != null)
                    cookieattributehandler.parse(basicclientcookie2, namevaluepair.getValue());
            } while(true);
            arraylist.add(basicclientcookie2);
        }

        return arraylist;
    }

    protected void formatCookieAsVer(CharArrayBuffer chararraybuffer, Cookie cookie, int i)
    {
        super.formatCookieAsVer(chararraybuffer, cookie, i);
        if(cookie instanceof ClientCookie)
        {
            String s = ((ClientCookie)cookie).getAttribute("port");
            if(s != null)
            {
                chararraybuffer.append("; $Port");
                chararraybuffer.append("=\"");
                if(s.trim().length() > 0)
                {
                    int ai[] = cookie.getPorts();
                    if(ai != null)
                    {
                        int j = ai.length;
                        for(int k = 0; k < j; k++)
                        {
                            if(k > 0)
                                chararraybuffer.append(",");
                            chararraybuffer.append(Integer.toString(ai[k]));
                        }

                    }
                }
                chararraybuffer.append("\"");
            }
        }
    }

    public int getVersion()
    {
        return 1;
    }

    public Header getVersionHeader()
    {
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(40);
        chararraybuffer.append("Cookie2");
        chararraybuffer.append(": ");
        chararraybuffer.append("$Version=");
        chararraybuffer.append(Integer.toString(getVersion()));
        return new BufferedHeader(chararraybuffer);
    }

    public boolean match(Cookie cookie, CookieOrigin cookieorigin)
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        return super.match(cookie, adjustEffectiveHost(cookieorigin));
    }

    public List parse(Header header, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(header, "Header");
        Args.notNull(cookieorigin, "Cookie origin");
        if(!header.getName().equalsIgnoreCase("Set-Cookie2"))
            throw new MalformedCookieException((new StringBuilder()).append("Unrecognized cookie header '").append(header.toString()).append("'").toString());
        else
            return createCookies(header.getElements(), adjustEffectiveHost(cookieorigin));
    }

    protected List parse(HeaderElement aheaderelement[], CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        return createCookies(aheaderelement, adjustEffectiveHost(cookieorigin));
    }

    public String toString()
    {
        return "rfc2965";
    }

    public void validate(Cookie cookie, CookieOrigin cookieorigin)
        throws MalformedCookieException
    {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieorigin, "Cookie origin");
        super.validate(cookie, adjustEffectiveHost(cookieorigin));
    }
}
