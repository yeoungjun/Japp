// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.io.IOException;

public interface SpnegoTokenGenerator
{

    public abstract byte[] generateSpnegoDERObject(byte abyte0[])
        throws IOException;
}
