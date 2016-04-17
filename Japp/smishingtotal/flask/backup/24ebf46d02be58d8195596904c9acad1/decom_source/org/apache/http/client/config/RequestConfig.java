// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.config;

import java.net.InetAddress;
import java.util.Collection;
import org.apache.http.HttpHost;

public class RequestConfig
    implements Cloneable
{
    public static class Builder
    {

        public RequestConfig build()
        {
            return new RequestConfig(expectContinueEnabled, proxy, localAddress, staleConnectionCheckEnabled, cookieSpec, redirectsEnabled, relativeRedirectsAllowed, circularRedirectsAllowed, maxRedirects, authenticationEnabled, targetPreferredAuthSchemes, proxyPreferredAuthSchemes, connectionRequestTimeout, connectTimeout, socketTimeout);
        }

        public Builder setAuthenticationEnabled(boolean flag)
        {
            authenticationEnabled = flag;
            return this;
        }

        public Builder setCircularRedirectsAllowed(boolean flag)
        {
            circularRedirectsAllowed = flag;
            return this;
        }

        public Builder setConnectTimeout(int i)
        {
            connectTimeout = i;
            return this;
        }

        public Builder setConnectionRequestTimeout(int i)
        {
            connectionRequestTimeout = i;
            return this;
        }

        public Builder setCookieSpec(String s)
        {
            cookieSpec = s;
            return this;
        }

        public Builder setExpectContinueEnabled(boolean flag)
        {
            expectContinueEnabled = flag;
            return this;
        }

        public Builder setLocalAddress(InetAddress inetaddress)
        {
            localAddress = inetaddress;
            return this;
        }

        public Builder setMaxRedirects(int i)
        {
            maxRedirects = i;
            return this;
        }

        public Builder setProxy(HttpHost httphost)
        {
            proxy = httphost;
            return this;
        }

        public Builder setProxyPreferredAuthSchemes(Collection collection)
        {
            proxyPreferredAuthSchemes = collection;
            return this;
        }

        public Builder setRedirectsEnabled(boolean flag)
        {
            redirectsEnabled = flag;
            return this;
        }

        public Builder setRelativeRedirectsAllowed(boolean flag)
        {
            relativeRedirectsAllowed = flag;
            return this;
        }

        public Builder setSocketTimeout(int i)
        {
            socketTimeout = i;
            return this;
        }

        public Builder setStaleConnectionCheckEnabled(boolean flag)
        {
            staleConnectionCheckEnabled = flag;
            return this;
        }

        public Builder setTargetPreferredAuthSchemes(Collection collection)
        {
            targetPreferredAuthSchemes = collection;
            return this;
        }

        private boolean authenticationEnabled;
        private boolean circularRedirectsAllowed;
        private int connectTimeout;
        private int connectionRequestTimeout;
        private String cookieSpec;
        private boolean expectContinueEnabled;
        private InetAddress localAddress;
        private int maxRedirects;
        private HttpHost proxy;
        private Collection proxyPreferredAuthSchemes;
        private boolean redirectsEnabled;
        private boolean relativeRedirectsAllowed;
        private int socketTimeout;
        private boolean staleConnectionCheckEnabled;
        private Collection targetPreferredAuthSchemes;

        Builder()
        {
            staleConnectionCheckEnabled = true;
            redirectsEnabled = true;
            maxRedirects = 50;
            relativeRedirectsAllowed = true;
            authenticationEnabled = true;
            connectionRequestTimeout = -1;
            connectTimeout = -1;
            socketTimeout = -1;
        }
    }


    RequestConfig(boolean flag, HttpHost httphost, InetAddress inetaddress, boolean flag1, String s, boolean flag2, boolean flag3, 
            boolean flag4, int i, boolean flag5, Collection collection, Collection collection1, int j, int k, 
            int l)
    {
        expectContinueEnabled = flag;
        proxy = httphost;
        localAddress = inetaddress;
        staleConnectionCheckEnabled = flag1;
        cookieSpec = s;
        redirectsEnabled = flag2;
        relativeRedirectsAllowed = flag3;
        circularRedirectsAllowed = flag4;
        maxRedirects = i;
        authenticationEnabled = flag5;
        targetPreferredAuthSchemes = collection;
        proxyPreferredAuthSchemes = collection1;
        connectionRequestTimeout = j;
        connectTimeout = k;
        socketTimeout = l;
    }

    public static Builder copy(RequestConfig requestconfig)
    {
        return (new Builder()).setExpectContinueEnabled(requestconfig.isExpectContinueEnabled()).setProxy(requestconfig.getProxy()).setLocalAddress(requestconfig.getLocalAddress()).setStaleConnectionCheckEnabled(requestconfig.isStaleConnectionCheckEnabled()).setCookieSpec(requestconfig.getCookieSpec()).setRedirectsEnabled(requestconfig.isRedirectsEnabled()).setRelativeRedirectsAllowed(requestconfig.isRelativeRedirectsAllowed()).setCircularRedirectsAllowed(requestconfig.isCircularRedirectsAllowed()).setMaxRedirects(requestconfig.getMaxRedirects()).setAuthenticationEnabled(requestconfig.isAuthenticationEnabled()).setTargetPreferredAuthSchemes(requestconfig.getTargetPreferredAuthSchemes()).setProxyPreferredAuthSchemes(requestconfig.getProxyPreferredAuthSchemes()).setConnectionRequestTimeout(requestconfig.getConnectionRequestTimeout()).setConnectTimeout(requestconfig.getConnectTimeout()).setSocketTimeout(requestconfig.getSocketTimeout());
    }

    public static Builder custom()
    {
        return new Builder();
    }

    protected volatile Object clone()
        throws CloneNotSupportedException
    {
        return clone();
    }

    protected RequestConfig clone()
        throws CloneNotSupportedException
    {
        return (RequestConfig)super.clone();
    }

    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    public int getConnectionRequestTimeout()
    {
        return connectionRequestTimeout;
    }

    public String getCookieSpec()
    {
        return cookieSpec;
    }

    public InetAddress getLocalAddress()
    {
        return localAddress;
    }

    public int getMaxRedirects()
    {
        return maxRedirects;
    }

    public HttpHost getProxy()
    {
        return proxy;
    }

    public Collection getProxyPreferredAuthSchemes()
    {
        return proxyPreferredAuthSchemes;
    }

    public int getSocketTimeout()
    {
        return socketTimeout;
    }

    public Collection getTargetPreferredAuthSchemes()
    {
        return targetPreferredAuthSchemes;
    }

    public boolean isAuthenticationEnabled()
    {
        return authenticationEnabled;
    }

    public boolean isCircularRedirectsAllowed()
    {
        return circularRedirectsAllowed;
    }

    public boolean isExpectContinueEnabled()
    {
        return expectContinueEnabled;
    }

    public boolean isRedirectsEnabled()
    {
        return redirectsEnabled;
    }

    public boolean isRelativeRedirectsAllowed()
    {
        return relativeRedirectsAllowed;
    }

    public boolean isStaleConnectionCheckEnabled()
    {
        return staleConnectionCheckEnabled;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(", expectContinueEnabled=").append(expectContinueEnabled);
        stringbuilder.append(", proxy=").append(proxy);
        stringbuilder.append(", localAddress=").append(localAddress);
        stringbuilder.append(", staleConnectionCheckEnabled=").append(staleConnectionCheckEnabled);
        stringbuilder.append(", cookieSpec=").append(cookieSpec);
        stringbuilder.append(", redirectsEnabled=").append(redirectsEnabled);
        stringbuilder.append(", relativeRedirectsAllowed=").append(relativeRedirectsAllowed);
        stringbuilder.append(", maxRedirects=").append(maxRedirects);
        stringbuilder.append(", circularRedirectsAllowed=").append(circularRedirectsAllowed);
        stringbuilder.append(", authenticationEnabled=").append(authenticationEnabled);
        stringbuilder.append(", targetPreferredAuthSchemes=").append(targetPreferredAuthSchemes);
        stringbuilder.append(", proxyPreferredAuthSchemes=").append(proxyPreferredAuthSchemes);
        stringbuilder.append(", connectionRequestTimeout=").append(connectionRequestTimeout);
        stringbuilder.append(", connectTimeout=").append(connectTimeout);
        stringbuilder.append(", socketTimeout=").append(socketTimeout);
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    public static final RequestConfig DEFAULT = (new Builder()).build();
    private final boolean authenticationEnabled;
    private final boolean circularRedirectsAllowed;
    private final int connectTimeout;
    private final int connectionRequestTimeout;
    private final String cookieSpec;
    private final boolean expectContinueEnabled;
    private final InetAddress localAddress;
    private final int maxRedirects;
    private final HttpHost proxy;
    private final Collection proxyPreferredAuthSchemes;
    private final boolean redirectsEnabled;
    private final boolean relativeRedirectsAllowed;
    private final int socketTimeout;
    private final boolean staleConnectionCheckEnabled;
    private final Collection targetPreferredAuthSchemes;

}
