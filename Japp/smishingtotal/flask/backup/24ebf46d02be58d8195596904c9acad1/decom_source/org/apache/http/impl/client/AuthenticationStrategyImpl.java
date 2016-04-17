// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.client:
//            BasicAuthCache

abstract class AuthenticationStrategyImpl
    implements AuthenticationStrategy
{

    AuthenticationStrategyImpl(int i, String s)
    {
        challengeCode = i;
        headerName = s;
    }

    public void authFailed(HttpHost httphost, AuthScheme authscheme, HttpContext httpcontext)
    {
        Args.notNull(httphost, "Host");
        Args.notNull(httpcontext, "HTTP context");
        AuthCache authcache = HttpClientContext.adapt(httpcontext).getAuthCache();
        if(authcache != null)
        {
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Clearing cached auth scheme for ").append(httphost).toString());
            authcache.remove(httphost);
        }
    }

    public void authSucceeded(HttpHost httphost, AuthScheme authscheme, HttpContext httpcontext)
    {
        Args.notNull(httphost, "Host");
        Args.notNull(authscheme, "Auth scheme");
        Args.notNull(httpcontext, "HTTP context");
        HttpClientContext httpclientcontext = HttpClientContext.adapt(httpcontext);
        if(isCachable(authscheme))
        {
            Object obj = httpclientcontext.getAuthCache();
            if(obj == null)
            {
                obj = new BasicAuthCache();
                httpclientcontext.setAuthCache(((AuthCache) (obj)));
            }
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Caching '").append(authscheme.getSchemeName()).append("' auth scheme for ").append(httphost).toString());
            ((AuthCache) (obj)).put(httphost, authscheme);
        }
    }

    public Map getChallenges(HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        Args.notNull(httpresponse, "HTTP response");
        Header aheader[] = httpresponse.getHeaders(headerName);
        HashMap hashmap = new HashMap(aheader.length);
        int i = aheader.length;
        for(int j = 0; j < i; j++)
        {
            Header header = aheader[j];
            CharArrayBuffer chararraybuffer;
            int k;
            if(header instanceof FormattedHeader)
            {
                chararraybuffer = ((FormattedHeader)header).getBuffer();
            } else
            {
                String s = header.getValue();
                if(s == null)
                    throw new MalformedChallengeException("Header value is null");
                chararraybuffer = new CharArrayBuffer(s.length());
                chararraybuffer.append(s);
                k = 0;
            }
            for(k = ((FormattedHeader)header).getValuePos(); k < chararraybuffer.length() && HTTP.isWhitespace(chararraybuffer.charAt(k)); k++);
            int l = k;
            for(; k < chararraybuffer.length() && !HTTP.isWhitespace(chararraybuffer.charAt(k)); k++);
            hashmap.put(chararraybuffer.substring(l, k).toLowerCase(Locale.US), header);
        }

        return hashmap;
    }

    abstract Collection getPreferredAuthSchemes(RequestConfig requestconfig);

    public boolean isAuthenticationRequested(HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
    {
        Args.notNull(httpresponse, "HTTP response");
        return httpresponse.getStatusLine().getStatusCode() == challengeCode;
    }

    protected boolean isCachable(AuthScheme authscheme)
    {
        String s;
        if(authscheme != null && authscheme.isComplete())
            if((s = authscheme.getSchemeName()).equalsIgnoreCase("Basic") || s.equalsIgnoreCase("Digest"))
                return true;
        return false;
    }

    public Queue select(Map map, HttpHost httphost, HttpResponse httpresponse, HttpContext httpcontext)
        throws MalformedChallengeException
    {
        Args.notNull(map, "Map of auth challenges");
        Args.notNull(httphost, "Host");
        Args.notNull(httpresponse, "HTTP response");
        Args.notNull(httpcontext, "HTTP context");
        HttpClientContext httpclientcontext = HttpClientContext.adapt(httpcontext);
        LinkedList linkedlist = new LinkedList();
        Lookup lookup = httpclientcontext.getAuthSchemeRegistry();
        if(lookup == null)
        {
            log.debug("Auth scheme registry not set in the context");
        } else
        {
            CredentialsProvider credentialsprovider = httpclientcontext.getCredentialsProvider();
            if(credentialsprovider == null)
            {
                log.debug("Credentials provider not set in the context");
                return linkedlist;
            }
            Object obj = getPreferredAuthSchemes(httpclientcontext.getRequestConfig());
            if(obj == null)
                obj = DEFAULT_SCHEME_PRIORITY;
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Authentication schemes in the order of preference: ").append(obj).toString());
            Iterator iterator = ((Collection) (obj)).iterator();
            while(iterator.hasNext()) 
            {
                String s = (String)iterator.next();
                Header header = (Header)map.get(s.toLowerCase(Locale.US));
                if(header != null)
                {
                    AuthSchemeProvider authschemeprovider = (AuthSchemeProvider)lookup.lookup(s);
                    if(authschemeprovider == null)
                    {
                        if(log.isWarnEnabled())
                            log.warn((new StringBuilder()).append("Authentication scheme ").append(s).append(" not supported").toString());
                    } else
                    {
                        AuthScheme authscheme = authschemeprovider.create(httpcontext);
                        authscheme.processChallenge(header);
                        org.apache.http.auth.Credentials credentials = credentialsprovider.getCredentials(new AuthScope(httphost.getHostName(), httphost.getPort(), authscheme.getRealm(), authscheme.getSchemeName()));
                        if(credentials != null)
                        {
                            AuthOption authoption = new AuthOption(authscheme, credentials);
                            linkedlist.add(authoption);
                        }
                    }
                } else
                if(log.isDebugEnabled())
                    log.debug((new StringBuilder()).append("Challenge for ").append(s).append(" authentication scheme not available").toString());
            }
        }
        return linkedlist;
    }

    private static final List DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList(new String[] {
        "negotiate", "Kerberos", "NTLM", "Digest", "Basic"
    }));
    private final int challengeCode;
    private final String headerName;
    private final Log log = LogFactory.getLog(getClass());

}
