// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

// Referenced classes of package org.apache.http.impl.auth:
//            GGSSchemeBase

public class KerberosScheme extends GGSSchemeBase
{

    public KerberosScheme()
    {
        super(false);
    }

    public KerberosScheme(boolean flag)
    {
        super(flag);
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest, HttpContext httpcontext)
        throws AuthenticationException
    {
        return super.authenticate(credentials, httprequest, httpcontext);
    }

    protected byte[] generateToken(byte abyte0[], String s)
        throws GSSException
    {
        return generateGSSToken(abyte0, new Oid("1.2.840.113554.1.2.2"), s);
    }

    public String getParameter(String s)
    {
        Args.notNull(s, "Parameter name");
        return null;
    }

    public String getRealm()
    {
        return null;
    }

    public String getSchemeName()
    {
        return "Kerberos";
    }

    public boolean isConnectionBased()
    {
        return true;
    }

    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
}
