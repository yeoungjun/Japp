// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.message.BasicNameValuePair;

// Referenced classes of package org.apache.http.client.utils:
//            URLEncodedUtils

public class URIBuilder
{

    public URIBuilder()
    {
        port = -1;
    }

    public URIBuilder(String s)
        throws URISyntaxException
    {
        digestURI(new URI(s));
    }

    public URIBuilder(URI uri)
    {
        digestURI(uri);
    }

    private String buildString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        if(scheme != null)
            stringbuilder.append(scheme).append(':');
        if(encodedSchemeSpecificPart != null)
        {
            stringbuilder.append(encodedSchemeSpecificPart);
        } else
        {
            if(encodedAuthority != null)
                stringbuilder.append("//").append(encodedAuthority);
            else
            if(host != null)
            {
                stringbuilder.append("//");
                if(encodedUserInfo != null)
                    stringbuilder.append(encodedUserInfo).append("@");
                else
                if(userInfo != null)
                    stringbuilder.append(encodeUserInfo(userInfo)).append("@");
                if(InetAddressUtils.isIPv6Address(host))
                    stringbuilder.append("[").append(host).append("]");
                else
                    stringbuilder.append(host);
                if(port >= 0)
                    stringbuilder.append(":").append(port);
            }
            if(encodedPath != null)
                stringbuilder.append(normalizePath(encodedPath));
            else
            if(path != null)
                stringbuilder.append(encodePath(normalizePath(path)));
            if(encodedQuery != null)
                stringbuilder.append("?").append(encodedQuery);
            else
            if(queryParams != null)
                stringbuilder.append("?").append(encodeUrlForm(queryParams));
            else
            if(query != null)
                stringbuilder.append("?").append(encodeUric(query));
        }
        if(encodedFragment != null)
            stringbuilder.append("#").append(encodedFragment);
        else
        if(fragment != null)
            stringbuilder.append("#").append(encodeUric(fragment));
        return stringbuilder.toString();
    }

    private void digestURI(URI uri)
    {
        scheme = uri.getScheme();
        encodedSchemeSpecificPart = uri.getRawSchemeSpecificPart();
        encodedAuthority = uri.getRawAuthority();
        host = uri.getHost();
        port = uri.getPort();
        encodedUserInfo = uri.getRawUserInfo();
        userInfo = uri.getUserInfo();
        encodedPath = uri.getRawPath();
        path = uri.getPath();
        encodedQuery = uri.getRawQuery();
        queryParams = parseQuery(uri.getRawQuery(), Consts.UTF_8);
        encodedFragment = uri.getRawFragment();
        fragment = uri.getFragment();
    }

    private String encodePath(String s)
    {
        return URLEncodedUtils.encPath(s, Consts.UTF_8);
    }

    private String encodeUric(String s)
    {
        return URLEncodedUtils.encUric(s, Consts.UTF_8);
    }

    private String encodeUrlForm(List list)
    {
        return URLEncodedUtils.format(list, Consts.UTF_8);
    }

    private String encodeUserInfo(String s)
    {
        return URLEncodedUtils.encUserInfo(s, Consts.UTF_8);
    }

    private static String normalizePath(String s)
    {
        String s1 = s;
        if(s1 == null)
            return null;
        int i = 0;
        do
        {
            if(i >= s1.length() || s1.charAt(i) != '/')
            {
                if(i > 1)
                    s1 = s1.substring(i - 1);
                return s1;
            }
            i++;
        } while(true);
    }

    private List parseQuery(String s, Charset charset)
    {
        if(s != null && s.length() > 0)
            return URLEncodedUtils.parse(s, charset);
        else
            return null;
    }

    public URIBuilder addParameter(String s, String s1)
    {
        if(queryParams == null)
            queryParams = new ArrayList();
        queryParams.add(new BasicNameValuePair(s, s1));
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        query = null;
        return this;
    }

    public URIBuilder addParameters(List list)
    {
        if(queryParams == null)
            queryParams = new ArrayList();
        queryParams.addAll(list);
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        query = null;
        return this;
    }

    public URI build()
        throws URISyntaxException
    {
        return new URI(buildString());
    }

    public URIBuilder clearParameters()
    {
        queryParams = null;
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        return this;
    }

    public String getFragment()
    {
        return fragment;
    }

    public String getHost()
    {
        return host;
    }

    public String getPath()
    {
        return path;
    }

    public int getPort()
    {
        return port;
    }

    public List getQueryParams()
    {
        if(queryParams != null)
            return new ArrayList(queryParams);
        else
            return new ArrayList();
    }

    public String getScheme()
    {
        return scheme;
    }

    public String getUserInfo()
    {
        return userInfo;
    }

    public boolean isAbsolute()
    {
        return scheme != null;
    }

    public boolean isOpaque()
    {
        return path == null;
    }

    public URIBuilder removeQuery()
    {
        queryParams = null;
        query = null;
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        return this;
    }

    public URIBuilder setCustomQuery(String s)
    {
        query = s;
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        queryParams = null;
        return this;
    }

    public URIBuilder setFragment(String s)
    {
        fragment = s;
        encodedFragment = null;
        return this;
    }

    public URIBuilder setHost(String s)
    {
        host = s;
        encodedSchemeSpecificPart = null;
        encodedAuthority = null;
        return this;
    }

    public URIBuilder setParameter(String s, String s1)
    {
        if(queryParams == null)
            queryParams = new ArrayList();
        if(!queryParams.isEmpty())
        {
            Iterator iterator = queryParams.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                if(((NameValuePair)iterator.next()).getName().equals(s))
                    iterator.remove();
            } while(true);
        }
        queryParams.add(new BasicNameValuePair(s, s1));
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        query = null;
        return this;
    }

    public URIBuilder setParameters(List list)
    {
        if(queryParams == null)
            queryParams = new ArrayList();
        else
            queryParams.clear();
        queryParams.addAll(list);
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        query = null;
        return this;
    }

    public transient URIBuilder setParameters(NameValuePair anamevaluepair[])
    {
        int i;
        if(queryParams == null)
            queryParams = new ArrayList();
        else
            queryParams.clear();
        i = anamevaluepair.length;
        for(int j = 0; j < i; j++)
        {
            NameValuePair namevaluepair = anamevaluepair[j];
            queryParams.add(namevaluepair);
        }

        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        query = null;
        return this;
    }

    public URIBuilder setPath(String s)
    {
        path = s;
        encodedSchemeSpecificPart = null;
        encodedPath = null;
        return this;
    }

    public URIBuilder setPort(int i)
    {
        if(i < 0)
            i = -1;
        port = i;
        encodedSchemeSpecificPart = null;
        encodedAuthority = null;
        return this;
    }

    public URIBuilder setQuery(String s)
    {
        queryParams = parseQuery(s, Consts.UTF_8);
        query = null;
        encodedQuery = null;
        encodedSchemeSpecificPart = null;
        return this;
    }

    public URIBuilder setScheme(String s)
    {
        scheme = s;
        return this;
    }

    public URIBuilder setUserInfo(String s)
    {
        userInfo = s;
        encodedSchemeSpecificPart = null;
        encodedAuthority = null;
        encodedUserInfo = null;
        return this;
    }

    public URIBuilder setUserInfo(String s, String s1)
    {
        return setUserInfo((new StringBuilder()).append(s).append(':').append(s1).toString());
    }

    public String toString()
    {
        return buildString();
    }

    private String encodedAuthority;
    private String encodedFragment;
    private String encodedPath;
    private String encodedQuery;
    private String encodedSchemeSpecificPart;
    private String encodedUserInfo;
    private String fragment;
    private String host;
    private String path;
    private int port;
    private String query;
    private List queryParams;
    private String scheme;
    private String userInfo;
}
