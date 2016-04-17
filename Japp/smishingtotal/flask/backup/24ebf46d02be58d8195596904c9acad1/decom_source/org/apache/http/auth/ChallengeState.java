// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;


public final class ChallengeState extends Enum
{

    private ChallengeState(String s, int i)
    {
        super(s, i);
    }

    public static ChallengeState valueOf(String s)
    {
        return (ChallengeState)Enum.valueOf(org/apache/http/auth/ChallengeState, s);
    }

    public static ChallengeState[] values()
    {
        return (ChallengeState[])$VALUES.clone();
    }

    private static final ChallengeState $VALUES[];
    public static final ChallengeState PROXY;
    public static final ChallengeState TARGET;

    static 
    {
        TARGET = new ChallengeState("TARGET", 0);
        PROXY = new ChallengeState("PROXY", 1);
        ChallengeState achallengestate[] = new ChallengeState[2];
        achallengestate[0] = TARGET;
        achallengestate[1] = PROXY;
        $VALUES = achallengestate;
    }
}
