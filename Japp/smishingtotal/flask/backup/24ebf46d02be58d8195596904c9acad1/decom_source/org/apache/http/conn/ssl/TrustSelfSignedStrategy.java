// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

// Referenced classes of package org.apache.http.conn.ssl:
//            TrustStrategy

public class TrustSelfSignedStrategy
    implements TrustStrategy
{

    public TrustSelfSignedStrategy()
    {
    }

    public boolean isTrusted(X509Certificate ax509certificate[], String s)
        throws CertificateException
    {
        return ax509certificate.length == 1;
    }
}
