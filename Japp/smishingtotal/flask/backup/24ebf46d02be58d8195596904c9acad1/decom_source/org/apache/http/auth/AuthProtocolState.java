// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;


public final class AuthProtocolState extends Enum
{

    private AuthProtocolState(String s, int i)
    {
        super(s, i);
    }

    public static AuthProtocolState valueOf(String s)
    {
        return (AuthProtocolState)Enum.valueOf(org/apache/http/auth/AuthProtocolState, s);
    }

    public static AuthProtocolState[] values()
    {
        return (AuthProtocolState[])$VALUES.clone();
    }

    private static final AuthProtocolState $VALUES[];
    public static final AuthProtocolState CHALLENGED;
    public static final AuthProtocolState FAILURE;
    public static final AuthProtocolState HANDSHAKE;
    public static final AuthProtocolState SUCCESS;
    public static final AuthProtocolState UNCHALLENGED;

    static 
    {
        UNCHALLENGED = new AuthProtocolState("UNCHALLENGED", 0);
        CHALLENGED = new AuthProtocolState("CHALLENGED", 1);
        HANDSHAKE = new AuthProtocolState("HANDSHAKE", 2);
        FAILURE = new AuthProtocolState("FAILURE", 3);
        SUCCESS = new AuthProtocolState("SUCCESS", 4);
        AuthProtocolState aauthprotocolstate[] = new AuthProtocolState[5];
        aauthprotocolstate[0] = UNCHALLENGED;
        aauthprotocolstate[1] = CHALLENGED;
        aauthprotocolstate[2] = HANDSHAKE;
        aauthprotocolstate[3] = FAILURE;
        aauthprotocolstate[4] = SUCCESS;
        $VALUES = aauthprotocolstate;
    }
}
