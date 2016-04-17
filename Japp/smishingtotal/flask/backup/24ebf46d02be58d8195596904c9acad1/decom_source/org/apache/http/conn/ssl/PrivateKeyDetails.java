// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.apache.http.util.Args;

public final class PrivateKeyDetails
{

    public PrivateKeyDetails(String s, X509Certificate ax509certificate[])
    {
        type = (String)Args.notNull(s, "Private key type");
        certChain = ax509certificate;
    }

    public X509Certificate[] getCertChain()
    {
        return certChain;
    }

    public String getType()
    {
        return type;
    }

    public String toString()
    {
        return (new StringBuilder()).append(type).append(':').append(Arrays.toString(certChain)).toString();
    }

    private final X509Certificate certChain[];
    private final String type;
}
