// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import java.security.Principal;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.auth:
//            RFC2617Scheme

public class BasicScheme extends RFC2617Scheme
{

    public BasicScheme()
    {
        this(Consts.ASCII);
    }

    public BasicScheme(Charset charset)
    {
        super(charset);
        base64codec = new Base64(0);
        complete = false;
    }

    public BasicScheme(ChallengeState challengestate)
    {
        super(challengestate);
        base64codec = new Base64(0);
    }

    public static Header authenticate(Credentials credentials, String s, boolean flag)
    {
        Args.notNull(credentials, "Credentials");
        Args.notNull(s, "charset");
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(credentials.getUserPrincipal().getName());
        stringbuilder.append(":");
        String s1;
        byte abyte0[];
        CharArrayBuffer chararraybuffer;
        if(credentials.getPassword() == null)
            s1 = "null";
        else
            s1 = credentials.getPassword();
        stringbuilder.append(s1);
        abyte0 = Base64.encodeBase64(EncodingUtils.getBytes(stringbuilder.toString(), s), false);
        chararraybuffer = new CharArrayBuffer(32);
        if(flag)
            chararraybuffer.append("Proxy-Authorization");
        else
            chararraybuffer.append("Authorization");
        chararraybuffer.append(": Basic ");
        chararraybuffer.append(abyte0, 0, abyte0.length);
        return new BufferedHeader(chararraybuffer);
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest)
        throws AuthenticationException
    {
        return authenticate(credentials, httprequest, ((HttpContext) (new BasicHttpContext())));
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest, HttpContext httpcontext)
        throws AuthenticationException
    {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httprequest, "HTTP request");
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(credentials.getUserPrincipal().getName());
        stringbuilder.append(":");
        String s;
        byte abyte0[];
        CharArrayBuffer chararraybuffer;
        if(credentials.getPassword() == null)
            s = "null";
        else
            s = credentials.getPassword();
        stringbuilder.append(s);
        abyte0 = base64codec.encode(EncodingUtils.getBytes(stringbuilder.toString(), getCredentialsCharset(httprequest)));
        chararraybuffer = new CharArrayBuffer(32);
        if(isProxy())
            chararraybuffer.append("Proxy-Authorization");
        else
            chararraybuffer.append("Authorization");
        chararraybuffer.append(": Basic ");
        chararraybuffer.append(abyte0, 0, abyte0.length);
        return new BufferedHeader(chararraybuffer);
    }

    public String getSchemeName()
    {
        return "basic";
    }

    public boolean isComplete()
    {
        return complete;
    }

    public boolean isConnectionBased()
    {
        return false;
    }

    public void processChallenge(Header header)
        throws MalformedChallengeException
    {
        super.processChallenge(header);
        complete = true;
    }

    private final Base64 base64codec;
    private boolean complete;
}
