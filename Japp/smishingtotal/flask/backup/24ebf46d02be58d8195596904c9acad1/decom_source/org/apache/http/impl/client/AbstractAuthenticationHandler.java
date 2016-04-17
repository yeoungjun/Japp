// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractAuthenticationHandler
    implements AuthenticationHandler
{

    public AbstractAuthenticationHandler()
    {
    }

    protected List getAuthPreferences()
    {
        return DEFAULT_SCHEME_PRIORITY;
    }

    protected List getAuthPreferences(HttpResponse httpresponse, HttpContext httpcontext)
    {
        return getAuthPreferences();
    }

    protected Map parseChallenges(Header aheader[])
        throws MalformedChallengeException
    {
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

    public AuthScheme selectScheme(Map map, HttpResponse httpresponse, HttpContext httpcontext)
        throws AuthenticationException
    {
        AuthSchemeRegistry authschemeregistry = (AuthSchemeRegistry)httpcontext.getAttribute("http.authscheme-registry");
        Asserts.notNull(authschemeregistry, "AuthScheme registry");
        List list = getAuthPreferences(httpresponse, httpcontext);
        if(list == null)
            list = DEFAULT_SCHEME_PRIORITY;
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Authentication schemes in the order of preference: ").append(list).toString());
        Iterator iterator = list.iterator();
        do
        {
label0:
            {
                boolean flag = iterator.hasNext();
                AuthScheme authscheme = null;
                String s;
                if(flag)
                {
                    s = (String)iterator.next();
                    if((Header)map.get(s.toLowerCase(Locale.ENGLISH)) == null)
                        break label0;
                    if(log.isDebugEnabled())
                        log.debug((new StringBuilder()).append(s).append(" authentication scheme selected").toString());
                    AuthScheme authscheme1;
                    try
                    {
                        authscheme1 = authschemeregistry.getAuthScheme(s, httpresponse.getParams());
                    }
                    catch(IllegalStateException illegalstateexception)
                    {
                        if(log.isWarnEnabled())
                            log.warn((new StringBuilder()).append("Authentication scheme ").append(s).append(" not supported").toString());
                        continue;
                    }
                    authscheme = authscheme1;
                }
                if(authscheme == null)
                    throw new AuthenticationException((new StringBuilder()).append("Unable to respond to any of these challenges: ").append(map).toString());
                else
                    return authscheme;
            }
            if(log.isDebugEnabled())
                log.debug((new StringBuilder()).append("Challenge for ").append(s).append(" authentication scheme not available").toString());
        } while(true);
    }

    private static final List DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList(new String[] {
        "negotiate", "NTLM", "Digest", "Basic"
    }));
    private final Log log = LogFactory.getLog(getClass());

}
