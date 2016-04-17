// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

// Referenced classes of package org.apache.http.auth:
//            Credentials, NTUserPrincipal

public class NTCredentials
    implements Credentials, Serializable
{

    public NTCredentials(String s)
    {
        Args.notNull(s, "Username:password string");
        int i = s.indexOf(':');
        String s1;
        int j;
        if(i >= 0)
        {
            s1 = s.substring(0, i);
            password = s.substring(i + 1);
        } else
        {
            s1 = s;
            password = null;
        }
        j = s1.indexOf('/');
        if(j >= 0)
            principal = new NTUserPrincipal(s1.substring(0, j).toUpperCase(Locale.ENGLISH), s1.substring(j + 1));
        else
            principal = new NTUserPrincipal(null, s1.substring(j + 1));
        workstation = null;
    }

    public NTCredentials(String s, String s1, String s2, String s3)
    {
        Args.notNull(s, "User name");
        principal = new NTUserPrincipal(s3, s);
        password = s1;
        if(s2 != null)
        {
            workstation = s2.toUpperCase(Locale.ENGLISH);
            return;
        } else
        {
            workstation = null;
            return;
        }
    }

    public boolean equals(Object obj)
    {
        NTCredentials ntcredentials;
        if(this != obj)
            if(!(obj instanceof NTCredentials) || (!LangUtils.equals(principal, (ntcredentials = (NTCredentials)obj).principal) || !LangUtils.equals(workstation, ntcredentials.workstation)))
                return false;
        return true;
    }

    public String getDomain()
    {
        return principal.getDomain();
    }

    public String getPassword()
    {
        return password;
    }

    public String getUserName()
    {
        return principal.getUsername();
    }

    public Principal getUserPrincipal()
    {
        return principal;
    }

    public String getWorkstation()
    {
        return workstation;
    }

    public int hashCode()
    {
        return LangUtils.hashCode(LangUtils.hashCode(17, principal), workstation);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[principal: ");
        stringbuilder.append(principal);
        stringbuilder.append("][workstation: ");
        stringbuilder.append(workstation);
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    private static final long serialVersionUID = 0x9980b99a99ef68ffL;
    private final String password;
    private final NTUserPrincipal principal;
    private final String workstation;
}
