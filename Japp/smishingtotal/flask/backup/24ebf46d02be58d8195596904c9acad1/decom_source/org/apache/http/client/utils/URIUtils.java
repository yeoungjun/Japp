// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.http.HttpHost;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

// Referenced classes of package org.apache.http.client.utils:
//            URIBuilder

public class URIUtils
{

    private URIUtils()
    {
    }

    public static URI createURI(String s, String s1, int i, String s2, String s3, String s4)
        throws URISyntaxException
    {
        StringBuilder stringbuilder = new StringBuilder();
        if(s1 != null)
        {
            if(s != null)
            {
                stringbuilder.append(s);
                stringbuilder.append("://");
            }
            stringbuilder.append(s1);
            if(i > 0)
            {
                stringbuilder.append(':');
                stringbuilder.append(i);
            }
        }
        if(s2 == null || !s2.startsWith("/"))
            stringbuilder.append('/');
        if(s2 != null)
            stringbuilder.append(s2);
        if(s3 != null)
        {
            stringbuilder.append('?');
            stringbuilder.append(s3);
        }
        if(s4 != null)
        {
            stringbuilder.append('#');
            stringbuilder.append(s4);
        }
        return new URI(stringbuilder.toString());
    }

    public static HttpHost extractHost(URI uri)
    {
_L2:
        return null;
        if(uri == null || !uri.isAbsolute()) goto _L2; else goto _L1
_L1:
        int i;
        String s;
        i = uri.getPort();
        s = uri.getHost();
        if(s != null) goto _L4; else goto _L3
_L3:
        s = uri.getAuthority();
        if(s == null) goto _L4; else goto _L5
_L5:
        int k;
        int l;
        int j = s.indexOf('@');
        int j1;
        if(j >= 0)
            if(s.length() > j + 1)
                s = s.substring(j + 1);
            else
                s = null;
        if(s == null) goto _L4; else goto _L6
_L6:
        k = s.indexOf(':');
        if(k < 0) goto _L4; else goto _L7
_L7:
        int k1;
        l = k + 1;
        int i1 = 0;
        for(j1 = l; j1 < s.length() && Character.isDigit(s.charAt(j1)); j1++)
            i1++;

        if(i1 <= 0)
            break MISSING_BLOCK_LABEL_162;
        k1 = l + i1;
        int l1 = Integer.parseInt(s.substring(l, k1));
        i = l1;
_L8:
        s = s.substring(0, k);
_L4:
        String s1 = uri.getScheme();
        if(s != null)
            return new HttpHost(s, i, s1);
          goto _L2
        NumberFormatException numberformatexception;
        numberformatexception;
          goto _L8
    }

    private static URI normalizeSyntax(URI uri)
    {
        StringBuilder stringbuilder;
        if(uri.isOpaque())
            return uri;
        Args.check(uri.isAbsolute(), "Base URI must be absolute");
        String s;
        String as[];
        Stack stack;
        int i;
        int j;
        if(uri.getPath() == null)
            s = "";
        else
            s = uri.getPath();
        as = s.split("/");
        stack = new Stack();
        i = as.length;
        j = 0;
        while(j < i) 
        {
            String s2 = as[j];
            if(s2.length() != 0 && !".".equals(s2))
                if("..".equals(s2))
                {
                    if(!stack.isEmpty())
                        stack.pop();
                } else
                {
                    stack.push(s2);
                }
            j++;
        }
        stringbuilder = new StringBuilder();
        String s1;
        for(Iterator iterator = stack.iterator(); iterator.hasNext(); stringbuilder.append('/').append(s1))
            s1 = (String)iterator.next();

        if(s.lastIndexOf('/') == -1 + s.length())
            stringbuilder.append('/');
        URI uri1 = new URI(uri.getScheme().toLowerCase(), uri.getAuthority().toLowerCase(), stringbuilder.toString(), null, null);
        if(uri.getQuery() == null && uri.getFragment() == null)
            return uri1;
        URI uri2;
        try
        {
            StringBuilder stringbuilder1 = new StringBuilder(uri1.toASCIIString());
            if(uri.getQuery() != null)
                stringbuilder1.append('?').append(uri.getRawQuery());
            if(uri.getFragment() != null)
                stringbuilder1.append('#').append(uri.getRawFragment());
            uri2 = URI.create(stringbuilder1.toString());
        }
        catch(URISyntaxException urisyntaxexception)
        {
            throw new IllegalArgumentException(urisyntaxexception);
        }
        return uri2;
    }

    public static URI resolve(URI uri, String s)
    {
        return resolve(uri, URI.create(s));
    }

    public static URI resolve(URI uri, URI uri1)
    {
        Args.notNull(uri, "Base URI");
        Args.notNull(uri1, "Reference URI");
        URI uri2 = uri1;
        String s = uri2.toString();
        if(s.startsWith("?"))
            return resolveReferenceStartingWithQueryString(uri, uri2);
        boolean flag;
        URI uri3;
        if(s.length() == 0)
            flag = true;
        else
            flag = false;
        if(flag)
            uri2 = URI.create("#");
        uri3 = uri.resolve(uri2);
        if(flag)
        {
            String s1 = uri3.toString();
            uri3 = URI.create(s1.substring(0, s1.indexOf('#')));
        }
        return normalizeSyntax(uri3);
    }

    public static URI resolve(URI uri, HttpHost httphost, List list)
        throws URISyntaxException
    {
        Args.notNull(uri, "Request URI");
        URIBuilder uribuilder;
        if(list == null || list.isEmpty())
        {
            uribuilder = new URIBuilder(uri);
        } else
        {
            uribuilder = new URIBuilder((URI)list.get(-1 + list.size()));
            String s = uribuilder.getFragment();
            for(int i = -1 + list.size(); s == null && i >= 0; i--)
                s = ((URI)list.get(i)).getFragment();

            uribuilder.setFragment(s);
        }
        if(uribuilder.getFragment() == null)
            uribuilder.setFragment(uri.getFragment());
        if(httphost != null && !uribuilder.isAbsolute())
        {
            uribuilder.setScheme(httphost.getSchemeName());
            uribuilder.setHost(httphost.getHostName());
            uribuilder.setPort(httphost.getPort());
        }
        return uribuilder.build();
    }

    private static URI resolveReferenceStartingWithQueryString(URI uri, URI uri1)
    {
        String s = uri.toString();
        if(s.indexOf('?') > -1)
            s = s.substring(0, s.indexOf('?'));
        return URI.create((new StringBuilder()).append(s).append(uri1.toString()).toString());
    }

    public static URI rewriteURI(URI uri)
        throws URISyntaxException
    {
        Args.notNull(uri, "URI");
        if(uri.isOpaque())
            return uri;
        URIBuilder uribuilder = new URIBuilder(uri);
        if(uribuilder.getUserInfo() != null)
            uribuilder.setUserInfo(null);
        if(TextUtils.isEmpty(uribuilder.getPath()))
            uribuilder.setPath("/");
        if(uribuilder.getHost() != null)
            uribuilder.setHost(uribuilder.getHost().toLowerCase(Locale.ENGLISH));
        uribuilder.setFragment(null);
        return uribuilder.build();
    }

    public static URI rewriteURI(URI uri, HttpHost httphost)
        throws URISyntaxException
    {
        return rewriteURI(uri, httphost, false);
    }

    public static URI rewriteURI(URI uri, HttpHost httphost, boolean flag)
        throws URISyntaxException
    {
        Args.notNull(uri, "URI");
        if(uri.isOpaque())
            return uri;
        URIBuilder uribuilder = new URIBuilder(uri);
        if(httphost != null)
        {
            uribuilder.setScheme(httphost.getSchemeName());
            uribuilder.setHost(httphost.getHostName());
            uribuilder.setPort(httphost.getPort());
        } else
        {
            uribuilder.setScheme(null);
            uribuilder.setHost(null);
            uribuilder.setPort(-1);
        }
        if(flag)
            uribuilder.setFragment(null);
        if(TextUtils.isEmpty(uribuilder.getPath()))
            uribuilder.setPath("/");
        return uribuilder.build();
    }
}
