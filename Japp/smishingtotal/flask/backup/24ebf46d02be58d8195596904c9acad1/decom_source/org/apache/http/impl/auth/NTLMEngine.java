// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;


// Referenced classes of package org.apache.http.impl.auth:
//            NTLMEngineException

public interface NTLMEngine
{

    public abstract String generateType1Msg(String s, String s1)
        throws NTLMEngineException;

    public abstract String generateType3Msg(String s, String s1, String s2, String s3, String s4)
        throws NTLMEngineException;
}
