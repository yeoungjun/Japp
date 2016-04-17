// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.ProtocolException;

public interface SaslAuthenticator
{

    public abstract boolean authenticate(String as[], String s, String s1, String s2, String s3)
        throws ProtocolException;
}
