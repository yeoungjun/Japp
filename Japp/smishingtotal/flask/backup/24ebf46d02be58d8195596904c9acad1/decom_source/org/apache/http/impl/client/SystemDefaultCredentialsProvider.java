// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.*;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.impl.client:
//            BasicCredentialsProvider

public class SystemDefaultCredentialsProvider
    implements CredentialsProvider
{

    public SystemDefaultCredentialsProvider()
    {
    }

    private static String translateScheme(String s)
    {
        String s1;
        if(s == null)
        {
            s1 = null;
        } else
        {
            s1 = (String)SCHEME_MAP.get(s);
            if(s1 == null)
                return s;
        }
        return s1;
    }

    public void clear()
    {
        internal.clear();
    }

    public Credentials getCredentials(AuthScope authscope)
    {
        Args.notNull(authscope, "Auth scope");
        Credentials credentials = internal.getCredentials(authscope);
        if(credentials != null)
            return credentials;
        if(authscope.getHost() != null)
        {
            PasswordAuthentication passwordauthentication = Authenticator.requestPasswordAuthentication(authscope.getHost(), null, authscope.getPort(), "http", null, translateScheme(authscope.getScheme()));
            if(passwordauthentication != null)
                return new UsernamePasswordCredentials(passwordauthentication.getUserName(), new String(passwordauthentication.getPassword()));
        }
        return null;
    }

    public void setCredentials(AuthScope authscope, Credentials credentials)
    {
        internal.setCredentials(authscope, credentials);
    }

    private static final Map SCHEME_MAP;
    private final BasicCredentialsProvider internal = new BasicCredentialsProvider();

    static 
    {
        SCHEME_MAP = new ConcurrentHashMap();
        SCHEME_MAP.put("Basic".toUpperCase(Locale.ENGLISH), "Basic");
        SCHEME_MAP.put("Digest".toUpperCase(Locale.ENGLISH), "Digest");
        SCHEME_MAP.put("NTLM".toUpperCase(Locale.ENGLISH), "NTLM");
        SCHEME_MAP.put("negotiate".toUpperCase(Locale.ENGLISH), "SPNEGO");
        SCHEME_MAP.put("Kerberos".toUpperCase(Locale.ENGLISH), "Kerberos");
    }
}
