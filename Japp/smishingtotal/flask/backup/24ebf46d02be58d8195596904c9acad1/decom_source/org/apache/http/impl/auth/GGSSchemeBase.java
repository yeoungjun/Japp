// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.auth.*;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.ietf.jgss.*;

// Referenced classes of package org.apache.http.impl.auth:
//            AuthSchemeBase

public abstract class GGSSchemeBase extends AuthSchemeBase
{
    static final class State extends Enum
    {

        public static State valueOf(String s)
        {
            return (State)Enum.valueOf(org/apache/http/impl/auth/GGSSchemeBase$State, s);
        }

        public static State[] values()
        {
            return (State[])$VALUES.clone();
        }

        private static final State $VALUES[];
        public static final State CHALLENGE_RECEIVED;
        public static final State FAILED;
        public static final State TOKEN_GENERATED;
        public static final State UNINITIATED;

        static 
        {
            UNINITIATED = new State("UNINITIATED", 0);
            CHALLENGE_RECEIVED = new State("CHALLENGE_RECEIVED", 1);
            TOKEN_GENERATED = new State("TOKEN_GENERATED", 2);
            FAILED = new State("FAILED", 3);
            State astate[] = new State[4];
            astate[0] = UNINITIATED;
            astate[1] = CHALLENGE_RECEIVED;
            astate[2] = TOKEN_GENERATED;
            astate[3] = FAILED;
            $VALUES = astate;
        }

        private State(String s, int i)
        {
            super(s, i);
        }
    }


    GGSSchemeBase()
    {
        this(false);
    }

    GGSSchemeBase(boolean flag)
    {
        log = LogFactory.getLog(getClass());
        base64codec = new Base64(0);
        stripPort = flag;
        state = State.UNINITIATED;
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest)
        throws AuthenticationException
    {
        return authenticate(credentials, httprequest, null);
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest, HttpContext httpcontext)
        throws AuthenticationException
    {
        Args.notNull(httprequest, "HTTP request");
        static class _cls1
        {

            static final int $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[];

            static 
            {
                $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State = new int[State.values().length];
                try
                {
                    $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.UNINITIATED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.FAILED.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.CHALLENGE_RECEIVED.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.TOKEN_GENERATED.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3)
                {
                    return;
                }
            }
        }

        _cls1..SwitchMap.org.apache.http.impl.auth.GGSSchemeBase.State[state.ordinal()];
        JVM INSTR tableswitch 1 4: default 48
    //                   1 78
    //                   2 108
    //                   3 138
    //                   4 320;
           goto _L1 _L2 _L3 _L4 _L5
_L1:
        throw new IllegalStateException((new StringBuilder()).append("Illegal state: ").append(state).toString());
_L2:
        throw new AuthenticationException((new StringBuilder()).append(getSchemeName()).append(" authentication has not been initiated").toString());
_L3:
        throw new AuthenticationException((new StringBuilder()).append(getSchemeName()).append(" authentication has failed").toString());
_L4:
        HttpRoute httproute = (HttpRoute)httpcontext.getAttribute("http.route");
        if(httproute != null) goto _L7; else goto _L6
_L6:
        try
        {
            throw new AuthenticationException("Connection route is not available");
        }
        catch(GSSException gssexception)
        {
            state = State.FAILED;
            String s;
            HttpHost httphost;
            String s1;
            String s2;
            if(gssexception.getMajor() == 9 || gssexception.getMajor() == 8)
                throw new InvalidCredentialsException(gssexception.getMessage(), gssexception);
            if(gssexception.getMajor() == 13)
                throw new InvalidCredentialsException(gssexception.getMessage(), gssexception);
            if(gssexception.getMajor() == 10 || gssexception.getMajor() == 19 || gssexception.getMajor() == 20)
                throw new AuthenticationException(gssexception.getMessage(), gssexception);
            throw new AuthenticationException(gssexception.getMessage());
        }
          goto _L8
_L7:
        if(!isProxy()) goto _L10; else goto _L9
_L9:
        httphost = httproute.getProxyHost();
        if(httphost != null)
            break MISSING_BLOCK_LABEL_236;
        httphost = httproute.getTargetHost();
_L8:
        if(stripPort || httphost.getPort() <= 0) goto _L12; else goto _L11
_L11:
        s2 = httphost.toHostString();
_L13:
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("init ").append(s2).toString());
        token = generateToken(token, s2);
        state = State.TOKEN_GENERATED;
_L5:
        s = new String(base64codec.encode(token));
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Sending response '").append(s).append("' back to the auth server").toString());
        CharArrayBuffer chararraybuffer = new CharArrayBuffer(32);
        if(isProxy())
            chararraybuffer.append("Proxy-Authorization");
        else
            chararraybuffer.append("Authorization");
        chararraybuffer.append(": Negotiate ");
        chararraybuffer.append(s);
        return new BufferedHeader(chararraybuffer);
_L10:
        httphost = httproute.getTargetHost();
          goto _L8
_L12:
        s1 = httphost.getHostName();
        s2 = s1;
          goto _L13
    }

    protected byte[] generateGSSToken(byte abyte0[], Oid oid, String s)
        throws GSSException
    {
        byte abyte1[] = abyte0;
        if(abyte1 == null)
            abyte1 = new byte[0];
        GSSManager gssmanager = getManager();
        GSSContext gsscontext = gssmanager.createContext(gssmanager.createName((new StringBuilder()).append("HTTP@").append(s).toString(), GSSName.NT_HOSTBASED_SERVICE).canonicalize(oid), oid, null, 0);
        gsscontext.requestMutualAuth(true);
        gsscontext.requestCredDeleg(true);
        return gsscontext.initSecContext(abyte1, 0, abyte1.length);
    }

    protected abstract byte[] generateToken(byte abyte0[], String s)
        throws GSSException;

    protected GSSManager getManager()
    {
        return GSSManager.getInstance();
    }

    public boolean isComplete()
    {
        return state == State.TOKEN_GENERATED || state == State.FAILED;
    }

    protected void parseChallenge(CharArrayBuffer chararraybuffer, int i, int j)
        throws MalformedChallengeException
    {
        String s = chararraybuffer.substringTrimmed(i, j);
        if(log.isDebugEnabled())
            log.debug((new StringBuilder()).append("Received challenge '").append(s).append("' from the auth server").toString());
        if(state == State.UNINITIATED)
        {
            token = Base64.decodeBase64(s.getBytes());
            state = State.CHALLENGE_RECEIVED;
            return;
        } else
        {
            log.debug("Authentication already attempted");
            state = State.FAILED;
            return;
        }
    }

    private final Base64 base64codec;
    private final Log log;
    private State state;
    private final boolean stripPort;
    private byte token[];
}
