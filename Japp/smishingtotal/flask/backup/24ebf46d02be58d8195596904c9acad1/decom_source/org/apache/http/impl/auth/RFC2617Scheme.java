// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.*;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.*;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.auth:
//            AuthSchemeBase

public abstract class RFC2617Scheme extends AuthSchemeBase
{

    public RFC2617Scheme()
    {
        this(Consts.ASCII);
    }

    public RFC2617Scheme(Charset charset)
    {
        params = new HashMap();
        if(charset == null)
            charset = Consts.ASCII;
        credentialsCharset = charset;
    }

    public RFC2617Scheme(ChallengeState challengestate)
    {
        super(challengestate);
        params = new HashMap();
        credentialsCharset = Consts.ASCII;
    }

    String getCredentialsCharset(HttpRequest httprequest)
    {
        String s = (String)httprequest.getParams().getParameter("http.auth.credential-charset");
        if(s == null)
            s = getCredentialsCharset().name();
        return s;
    }

    public Charset getCredentialsCharset()
    {
        return credentialsCharset;
    }

    public String getParameter(String s)
    {
        if(s == null)
            return null;
        else
            return (String)params.get(s.toLowerCase(Locale.ENGLISH));
    }

    protected Map getParameters()
    {
        return params;
    }

    public String getRealm()
    {
        return getParameter("realm");
    }

    protected void parseChallenge(CharArrayBuffer chararraybuffer, int i, int j)
        throws MalformedChallengeException
    {
        HeaderElement aheaderelement[] = BasicHeaderValueParser.INSTANCE.parseElements(chararraybuffer, new ParserCursor(i, chararraybuffer.length()));
        if(aheaderelement.length == 0)
            throw new MalformedChallengeException("Authentication challenge is empty");
        params.clear();
        int k = aheaderelement.length;
        for(int l = 0; l < k; l++)
        {
            HeaderElement headerelement = aheaderelement[l];
            params.put(headerelement.getName(), headerelement.getValue());
        }

    }

    private final Charset credentialsCharset;
    private final Map params;
}
