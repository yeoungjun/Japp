// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn.ssl;

import javax.net.ssl.SSLException;

// Referenced classes of package org.apache.http.conn.ssl:
//            AbstractVerifier

public class StrictHostnameVerifier extends AbstractVerifier
{

    public StrictHostnameVerifier()
    {
    }

    public final String toString()
    {
        return "STRICT";
    }

    public final void verify(String s, String as[], String as1[])
        throws SSLException
    {
        verify(s, as, as1, true);
    }
}
