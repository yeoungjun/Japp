// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.auth;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

// Referenced classes of package org.apache.http.impl.auth:
//            GGSSchemeBase, SpnegoTokenGenerator

public class NegotiateScheme extends GGSSchemeBase
{

    public NegotiateScheme()
    {
        this(null, false);
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegotokengenerator)
    {
        this(spnegotokengenerator, false);
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegotokengenerator, boolean flag)
    {
        super(flag);
        log = LogFactory.getLog(getClass());
        spengoGenerator = spnegotokengenerator;
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest)
        throws AuthenticationException
    {
        return authenticate(credentials, httprequest, null);
    }

    public Header authenticate(Credentials credentials, HttpRequest httprequest, HttpContext httpcontext)
        throws AuthenticationException
    {
        return super.authenticate(credentials, httprequest, httpcontext);
    }

    protected byte[] generateToken(byte abyte0[], String s)
        throws GSSException
    {
        Oid oid;
        byte abyte1[];
        boolean flag;
        oid = new Oid("1.3.6.1.5.5.2");
        abyte1 = abyte0;
        flag = false;
        byte abyte3[] = generateGSSToken(abyte1, oid, s);
        abyte1 = abyte3;
_L2:
        if(flag)
        {
            log.debug("Using Kerberos MECH 1.2.840.113554.1.2.2");
            abyte1 = generateGSSToken(abyte1, new Oid("1.2.840.113554.1.2.2"), s);
            if(abyte1 != null && spengoGenerator != null)
            {
                GSSException gssexception;
                byte abyte2[];
                try
                {
                    abyte2 = spengoGenerator.generateSpnegoDERObject(abyte1);
                }
                catch(IOException ioexception)
                {
                    log.error(ioexception.getMessage(), ioexception);
                    return abyte1;
                }
                abyte1 = abyte2;
            }
        }
        return abyte1;
        gssexception;
        if(gssexception.getMajor() == 2)
        {
            log.debug("GSSException BAD_MECH, retry with Kerberos MECH");
            flag = true;
        } else
        {
            throw gssexception;
        }
        if(true) goto _L2; else goto _L1
_L1:
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
        return "Negotiate";
    }

    public boolean isConnectionBased()
    {
        return true;
    }

    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
    private final Log log;
    private final SpnegoTokenGenerator spengoGenerator;
}
