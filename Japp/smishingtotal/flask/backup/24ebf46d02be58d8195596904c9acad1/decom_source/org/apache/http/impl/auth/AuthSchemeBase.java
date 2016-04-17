// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.util.Locale;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public abstract class AuthSchemeBase
    implements ContextAwareAuthScheme
{

    public AuthSchemeBase()
    {
    }

    public AuthSchemeBase(ChallengeState challengestate)
    {
        challengeState = challengestate;
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest, HttpContext httpcontext)
        throws AuthenticationException
    {
        return authenticate(credentials, httprequest);
    }

    public ChallengeState getChallengeState()
    {
        return challengeState;
    }

    public boolean isProxy()
    {
        return challengeState != null && challengeState == ChallengeState.PROXY;
    }

    protected abstract void parseChallenge(CharArrayBuffer chararraybuffer, int i, int j)
        throws MalformedChallengeException;

    public void processChallenge(Header header)
        throws MalformedChallengeException
    {
        Args.notNull(header, "Header");
        String s = header.getName();
        CharArrayBuffer chararraybuffer;
        int i;
        if(s.equalsIgnoreCase("WWW-Authenticate"))
            challengeState = ChallengeState.TARGET;
        else
        if(s.equalsIgnoreCase("Proxy-Authenticate"))
            challengeState = ChallengeState.PROXY;
        else
            throw new MalformedChallengeException((new StringBuilder()).append("Unexpected header name: ").append(s).toString());
        if(header instanceof FormattedHeader)
        {
            chararraybuffer = ((FormattedHeader)header).getBuffer();
        } else
        {
            String s1 = header.getValue();
            if(s1 == null)
                throw new MalformedChallengeException("Header value is null");
            chararraybuffer = new CharArrayBuffer(s1.length());
            chararraybuffer.append(s1);
            i = 0;
        }
        for(i = ((FormattedHeader)header).getValuePos(); i < chararraybuffer.length() && HTTP.isWhitespace(chararraybuffer.charAt(i)); i++);
        int j = i;
        for(; i < chararraybuffer.length() && !HTTP.isWhitespace(chararraybuffer.charAt(i)); i++);
        String s2 = chararraybuffer.substring(j, i);
        if(!s2.equalsIgnoreCase(getSchemeName()))
        {
            throw new MalformedChallengeException((new StringBuilder()).append("Invalid scheme identifier: ").append(s2).toString());
        } else
        {
            parseChallenge(chararraybuffer, i, chararraybuffer.length());
            return;
        }
    }

    public String toString()
    {
        String s = getSchemeName();
        if(s != null)
            return s.toUpperCase(Locale.US);
        else
            return super.toString();
    }

    private ChallengeState challengeState;
}
