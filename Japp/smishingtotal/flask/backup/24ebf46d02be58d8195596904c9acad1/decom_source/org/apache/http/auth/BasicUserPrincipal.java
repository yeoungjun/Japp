// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

public final class BasicUserPrincipal
    implements Principal, Serializable
{

    public BasicUserPrincipal(String s)
    {
        Args.notNull(s, "User name");
        username = s;
    }

    public boolean equals(Object obj)
    {
        BasicUserPrincipal basicuserprincipal;
        if(this != obj)
            if(!(obj instanceof BasicUserPrincipal) || !LangUtils.equals(username, (basicuserprincipal = (BasicUserPrincipal)obj).username))
                return false;
        return true;
    }

    public String getName()
    {
        return username;
    }

    public int hashCode()
    {
        return LangUtils.hashCode(17, username);
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("[principal: ");
        stringbuilder.append(username);
        stringbuilder.append("]");
        return stringbuilder.toString();
    }

    private static final long serialVersionUID = 0xe08c7771113f019dL;
    private final String username;
}
