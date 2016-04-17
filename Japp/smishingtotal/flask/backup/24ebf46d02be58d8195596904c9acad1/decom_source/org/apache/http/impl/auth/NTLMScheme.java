// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.*;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

// Referenced classes of package org.apache.http.impl.auth:
//            AuthSchemeBase, NTLMEngineImpl, NTLMEngine

public class NTLMScheme extends AuthSchemeBase
{
    static final class State extends Enum
    {

        public static State valueOf(String s)
        {
            return (State)Enum.valueOf(org/apache/http/impl/auth/NTLMScheme$State, s);
        }

        public static State[] values()
        {
            return (State[])$VALUES.clone();
        }

        private static final State $VALUES[];
        public static final State CHALLENGE_RECEIVED;
        public static final State FAILED;
        public static final State MSG_TYPE1_GENERATED;
        public static final State MSG_TYPE2_RECEVIED;
        public static final State MSG_TYPE3_GENERATED;
        public static final State UNINITIATED;

        static 
        {
            UNINITIATED = new State("UNINITIATED", 0);
            CHALLENGE_RECEIVED = new State("CHALLENGE_RECEIVED", 1);
            MSG_TYPE1_GENERATED = new State("MSG_TYPE1_GENERATED", 2);
            MSG_TYPE2_RECEVIED = new State("MSG_TYPE2_RECEVIED", 3);
            MSG_TYPE3_GENERATED = new State("MSG_TYPE3_GENERATED", 4);
            FAILED = new State("FAILED", 5);
            State astate[] = new State[6];
            astate[0] = UNINITIATED;
            astate[1] = CHALLENGE_RECEIVED;
            astate[2] = MSG_TYPE1_GENERATED;
            astate[3] = MSG_TYPE2_RECEVIED;
            astate[4] = MSG_TYPE3_GENERATED;
            astate[5] = FAILED;
            $VALUES = astate;
        }

        private State(String s, int i)
        {
            super(s, i);
        }
    }


    public NTLMScheme()
    {
        this(((NTLMEngine) (new NTLMEngineImpl())));
    }

    public NTLMScheme(NTLMEngine ntlmengine)
    {
        Args.notNull(ntlmengine, "NTLM engine");
        engine = ntlmengine;
        state = State.UNINITIATED;
        challenge = null;
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest)
        throws AuthenticationException
    {
        NTCredentials ntcredentials;
        try
        {
            ntcredentials = (NTCredentials)credentials;
        }
        catch(ClassCastException classcastexception)
        {
            throw new InvalidCredentialsException((new StringBuilder()).append("Credentials cannot be used for NTLM authentication: ").append(credentials.getClass().getName()).toString());
        }
        if(state == State.FAILED)
            throw new AuthenticationException("NTLM authentication failed");
        String s;
        CharArrayBuffer chararraybuffer;
        if(state == State.CHALLENGE_RECEIVED)
        {
            s = engine.generateType1Msg(ntcredentials.getDomain(), ntcredentials.getWorkstation());
            state = State.MSG_TYPE1_GENERATED;
        } else
        if(state == State.MSG_TYPE2_RECEVIED)
        {
            s = engine.generateType3Msg(ntcredentials.getUserName(), ntcredentials.getPassword(), ntcredentials.getDomain(), ntcredentials.getWorkstation(), challenge);
            state = State.MSG_TYPE3_GENERATED;
        } else
        {
            throw new AuthenticationException((new StringBuilder()).append("Unexpected state: ").append(state).toString());
        }
        chararraybuffer = new CharArrayBuffer(32);
        if(isProxy())
            chararraybuffer.append("Proxy-Authorization");
        else
            chararraybuffer.append("Authorization");
        chararraybuffer.append(": NTLM ");
        chararraybuffer.append(s);
        return new BufferedHeader(chararraybuffer);
    }

    public String getParameter(String s)
    {
        return null;
    }

    public String getRealm()
    {
        return null;
    }

    public String getSchemeName()
    {
        return "ntlm";
    }

    public boolean isComplete()
    {
        return state == State.MSG_TYPE3_GENERATED || state == State.FAILED;
    }

    public boolean isConnectionBased()
    {
        return true;
    }

    protected void parseChallenge(CharArrayBuffer chararraybuffer, int i, int j)
        throws MalformedChallengeException
    {
        challenge = chararraybuffer.substringTrimmed(i, j);
        if(challenge.length() != 0) goto _L2; else goto _L1
_L1:
        if(state != State.UNINITIATED) goto _L4; else goto _L3
_L3:
        state = State.CHALLENGE_RECEIVED;
_L6:
        return;
_L4:
        state = State.FAILED;
        return;
_L2:
        if(state.compareTo(State.MSG_TYPE1_GENERATED) < 0)
        {
            state = State.FAILED;
            throw new MalformedChallengeException("Out of sequence NTLM response message");
        }
        if(state == State.MSG_TYPE1_GENERATED)
        {
            state = State.MSG_TYPE2_RECEVIED;
            return;
        }
        if(true) goto _L6; else goto _L5
_L5:
    }

    private String challenge;
    private final NTLMEngine engine;
    private State state;
}
