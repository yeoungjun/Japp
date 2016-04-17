// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.message.*;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.*;

// Referenced classes of package org.apache.http.impl.auth:
//            RFC2617Scheme, UnsupportedDigestAlgorithmException, HttpEntityDigester

public class DigestScheme extends RFC2617Scheme
{

    public DigestScheme()
    {
        this(Consts.ASCII);
    }

    public DigestScheme(Charset charset)
    {
        super(charset);
        complete = false;
    }

    public DigestScheme(ChallengeState challengestate)
    {
        super(challengestate);
    }

    public static String createCnonce()
    {
        SecureRandom securerandom = new SecureRandom();
        byte abyte0[] = new byte[8];
        securerandom.nextBytes(abyte0);
        return encode(abyte0);
    }

    private Header createDigestHeader(Credentials credentials, HttpRequest httprequest)
        throws AuthenticationException
    {
        String s;
        String s1;
        String s2;
        String s3;
        String s4;
        String s5;
        HashSet hashset;
        byte byte0;
        String s6;
        s = getParameter("uri");
        s1 = getParameter("realm");
        s2 = getParameter("nonce");
        s3 = getParameter("opaque");
        s4 = getParameter("methodname");
        s5 = getParameter("algorithm");
        if(s5 == null)
            s5 = "MD5";
        hashset = new HashSet(8);
        byte0 = -1;
        s6 = getParameter("qop");
        if(s6 == null) goto _L2; else goto _L1
_L1:
        for(StringTokenizer stringtokenizer = new StringTokenizer(s6, ","); stringtokenizer.hasMoreTokens(); hashset.add(stringtokenizer.nextToken().trim().toLowerCase(Locale.US)));
        if(!(httprequest instanceof HttpEntityEnclosingRequest) || !hashset.contains("auth-int")) goto _L4; else goto _L3
_L3:
        byte0 = 1;
_L6:
        if(byte0 == -1)
            throw new AuthenticationException((new StringBuilder()).append("None of the qop methods is supported: ").append(s6).toString());
        break; /* Loop/switch isn't completed */
_L4:
        if(hashset.contains("auth"))
            byte0 = 2;
        continue; /* Loop/switch isn't completed */
_L2:
        byte0 = 0;
        if(true) goto _L6; else goto _L5
_L5:
        MessageDigest messagedigest;
        StringBuilder stringbuilder;
        String s11;
        String s12;
        String s13;
        String s15;
        CharArrayBuffer chararraybuffer;
        String s7 = getParameter("charset");
        if(s7 == null)
            s7 = "ISO-8859-1";
        String s8 = s5;
        if(s8.equalsIgnoreCase("MD5-sess"))
            s8 = "MD5";
        String s9;
        String s10;
        Formatter formatter;
        Object aobj[];
        String s16;
        ArrayList arraylist;
        BasicNameValuePair basicnamevaluepair;
        BasicNameValuePair basicnamevaluepair1;
        BasicNameValuePair basicnamevaluepair2;
        BasicNameValuePair basicnamevaluepair3;
        BasicNameValuePair basicnamevaluepair4;
        BasicNameValuePair basicnamevaluepair5;
        BasicNameValuePair basicnamevaluepair6;
        int i;
        int j;
        BasicNameValuePair basicnamevaluepair7;
        String s17;
        BasicHeaderValueFormatter basicheadervalueformatter;
        BasicNameValuePair basicnamevaluepair8;
        BasicNameValuePair basicnamevaluepair9;
        try
        {
            messagedigest = createMessageDigest(s8);
        }
        catch(UnsupportedDigestAlgorithmException unsupporteddigestalgorithmexception)
        {
            throw new AuthenticationException((new StringBuilder()).append("Unsuppported digest algorithm: ").append(s8).toString());
        }
        s9 = credentials.getUserPrincipal().getName();
        s10 = credentials.getPassword();
        if(s2.equals(lastNonce))
        {
            nounceCount = 1L + nounceCount;
        } else
        {
            nounceCount = 1L;
            cnonce = null;
            lastNonce = s2;
        }
        stringbuilder = new StringBuilder(256);
        formatter = new Formatter(stringbuilder, Locale.US);
        aobj = new Object[1];
        aobj[0] = Long.valueOf(nounceCount);
        formatter.format("%08x", aobj);
        formatter.close();
        s11 = stringbuilder.toString();
        if(cnonce == null)
            cnonce = createCnonce();
        a1 = null;
        a2 = null;
        if(s5.equalsIgnoreCase("MD5-sess"))
        {
            stringbuilder.setLength(0);
            stringbuilder.append(s9).append(':').append(s1).append(':').append(s10);
            String s19 = encode(messagedigest.digest(EncodingUtils.getBytes(stringbuilder.toString(), s7)));
            stringbuilder.setLength(0);
            stringbuilder.append(s19).append(':').append(s2).append(':').append(cnonce);
            a1 = stringbuilder.toString();
        } else
        {
            stringbuilder.setLength(0);
            stringbuilder.append(s9).append(':').append(s1).append(':').append(s10);
            a1 = stringbuilder.toString();
        }
        s12 = encode(messagedigest.digest(EncodingUtils.getBytes(a1, s7)));
        if(byte0 == 2)
        {
            a2 = (new StringBuilder()).append(s4).append(':').append(s).toString();
        } else
        {
label0:
            {
                if(byte0 != 1)
                    break MISSING_BLOCK_LABEL_1335;
                boolean flag2 = httprequest instanceof HttpEntityEnclosingRequest;
                httpentity = null;
                if(flag2)
                    httpentity = ((HttpEntityEnclosingRequest)httprequest).getEntity();
                if(httpentity == null || httpentity.isRepeatable())
                    break label0;
                if(hashset.contains("auth"))
                {
                    byte0 = 2;
                    a2 = (new StringBuilder()).append(s4).append(':').append(s).toString();
                } else
                {
                    throw new AuthenticationException("Qop auth-int cannot be used with a non-repeatable entity");
                }
            }
        }
_L7:
        s13 = encode(messagedigest.digest(EncodingUtils.getBytes(a2, s7)));
        if(byte0 == 0)
        {
            stringbuilder.setLength(0);
            stringbuilder.append(s12).append(':').append(s2).append(':').append(s13);
            s15 = stringbuilder.toString();
        } else
        {
            stringbuilder.setLength(0);
            StringBuilder stringbuilder1 = stringbuilder.append(s12).append(':').append(s2).append(':').append(s11).append(':').append(cnonce).append(':');
            String s14;
            if(byte0 == 1)
                s14 = "auth-int";
            else
                s14 = "auth";
            stringbuilder1.append(s14).append(':').append(s13);
            s15 = stringbuilder.toString();
        }
        s16 = encode(messagedigest.digest(EncodingUtils.getAsciiBytes(s15)));
        chararraybuffer = new CharArrayBuffer(128);
        if(isProxy())
            chararraybuffer.append("Proxy-Authorization");
        else
            chararraybuffer.append("Authorization");
        chararraybuffer.append(": Digest ");
        arraylist = new ArrayList(20);
        basicnamevaluepair = new BasicNameValuePair("username", s9);
        arraylist.add(basicnamevaluepair);
        basicnamevaluepair1 = new BasicNameValuePair("realm", s1);
        arraylist.add(basicnamevaluepair1);
        basicnamevaluepair2 = new BasicNameValuePair("nonce", s2);
        arraylist.add(basicnamevaluepair2);
        basicnamevaluepair3 = new BasicNameValuePair("uri", s);
        arraylist.add(basicnamevaluepair3);
        basicnamevaluepair4 = new BasicNameValuePair("response", s16);
        arraylist.add(basicnamevaluepair4);
        if(byte0 != 0)
        {
            String s18;
            HttpEntity httpentity;
            HttpEntityDigester httpentitydigester;
            IOException ioexception;
            AuthenticationException authenticationexception;
            if(byte0 == 1)
                s18 = "auth-int";
            else
                s18 = "auth";
            basicnamevaluepair8 = new BasicNameValuePair("qop", s18);
            arraylist.add(basicnamevaluepair8);
            basicnamevaluepair9 = new BasicNameValuePair("nc", s11);
            arraylist.add(basicnamevaluepair9);
            arraylist.add(new BasicNameValuePair("cnonce", cnonce));
        }
        basicnamevaluepair5 = new BasicNameValuePair("algorithm", s5);
        arraylist.add(basicnamevaluepair5);
        if(s3 != null)
        {
            basicnamevaluepair6 = new BasicNameValuePair("opaque", s3);
            arraylist.add(basicnamevaluepair6);
        }
        i = 0;
        do
        {
            j = arraylist.size();
            if(i < j)
            {
                basicnamevaluepair7 = (BasicNameValuePair)arraylist.get(i);
                if(i > 0)
                    chararraybuffer.append(", ");
                s17 = basicnamevaluepair7.getName();
                boolean flag;
                boolean flag1;
                if("nc".equals(s17) || "qop".equals(s17) || "algorithm".equals(s17))
                    flag = true;
                else
                    flag = false;
                basicheadervalueformatter = BasicHeaderValueFormatter.INSTANCE;
                if(!flag)
                    flag1 = true;
                else
                    flag1 = false;
                basicheadervalueformatter.formatNameValuePair(chararraybuffer, basicnamevaluepair7, flag1);
                i++;
            } else
            {
                BufferedHeader bufferedheader = new BufferedHeader(chararraybuffer);
                return bufferedheader;
            }
        } while(true);
        httpentitydigester = new HttpEntityDigester(messagedigest);
        if(httpentity == null)
            break MISSING_BLOCK_LABEL_1264;
        httpentity.writeTo(httpentitydigester);
        httpentitydigester.close();
        a2 = (new StringBuilder()).append(s4).append(':').append(s).append(':').append(encode(httpentitydigester.getDigest())).toString();
          goto _L7
        ioexception;
        authenticationexception = new AuthenticationException("I/O error reading entity content", ioexception);
        throw authenticationexception;
        a2 = (new StringBuilder()).append(s4).append(':').append(s).toString();
          goto _L7
    }

    private static MessageDigest createMessageDigest(String s)
        throws UnsupportedDigestAlgorithmException
    {
        MessageDigest messagedigest;
        try
        {
            messagedigest = MessageDigest.getInstance(s);
        }
        catch(Exception exception)
        {
            throw new UnsupportedDigestAlgorithmException((new StringBuilder()).append("Unsupported algorithm in HTTP Digest authentication: ").append(s).toString());
        }
        return messagedigest;
    }

    static String encode(byte abyte0[])
    {
        int i = abyte0.length;
        char ac[] = new char[i * 2];
        for(int j = 0; j < i; j++)
        {
            int k = 0xf & abyte0[j];
            int l = (0xf0 & abyte0[j]) >> 4;
            ac[j * 2] = HEXADECIMAL[l];
            ac[1 + j * 2] = HEXADECIMAL[k];
        }

        return new String(ac);
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
        if(getParameter("realm") == null)
            throw new AuthenticationException("missing realm in challenge");
        if(getParameter("nonce") == null)
            throw new AuthenticationException("missing nonce in challenge");
        getParameters().put("methodname", httprequest.getRequestLine().getMethod());
        getParameters().put("uri", httprequest.getRequestLine().getUri());
        if(getParameter("charset") == null)
            getParameters().put("charset", getCredentialsCharset(httprequest));
        return createDigestHeader(credentials, httprequest);
    }

    String getA1()
    {
        return a1;
    }

    String getA2()
    {
        return a2;
    }

    String getCnonce()
    {
        return cnonce;
    }

    public String getSchemeName()
    {
        return "digest";
    }

    public boolean isComplete()
    {
        if("true".equalsIgnoreCase(getParameter("stale")))
            return false;
        else
            return complete;
    }

    public boolean isConnectionBased()
    {
        return false;
    }

    public void overrideParamter(String s, String s1)
    {
        getParameters().put(s, s1);
    }

    public void processChallenge(Header header)
        throws MalformedChallengeException
    {
        super.processChallenge(header);
        complete = true;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("DIGEST [complete=").append(complete).append(", nonce=").append(lastNonce).append(", nc=").append(nounceCount).append("]");
        return stringbuilder.toString();
    }

    private static final char HEXADECIMAL[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };
    private static final int QOP_AUTH = 2;
    private static final int QOP_AUTH_INT = 1;
    private static final int QOP_MISSING = 0;
    private static final int QOP_UNKNOWN = -1;
    private String a1;
    private String a2;
    private String cnonce;
    private boolean complete;
    private String lastNonce;
    private long nounceCount;

}
