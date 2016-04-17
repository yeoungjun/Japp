// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.impl.client;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.util.Args;

public class BasicCredentialsProvider
    implements CredentialsProvider
{

    public BasicCredentialsProvider()
    {
    }

    private static Credentials matchCredentials(Map map, AuthScope authscope)
    {
        Credentials credentials = (Credentials)map.get(authscope);
        if(credentials == null)
        {
            int i = -1;
            AuthScope authscope1 = null;
            Iterator iterator = map.keySet().iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AuthScope authscope2 = (AuthScope)iterator.next();
                int j = authscope.match(authscope2);
                if(j > i)
                {
                    i = j;
                    authscope1 = authscope2;
                }
            } while(true);
            if(authscope1 != null)
                credentials = (Credentials)map.get(authscope1);
        }
        return credentials;
    }

    public void clear()
    {
        credMap.clear();
    }

    public Credentials getCredentials(AuthScope authscope)
    {
        Args.notNull(authscope, "Authentication scope");
        return matchCredentials(credMap, authscope);
    }

    public void setCredentials(AuthScope authscope, Credentials credentials)
    {
        Args.notNull(authscope, "Authentication scope");
        credMap.put(authscope, credentials);
    }

    public String toString()
    {
        return credMap.toString();
    }

    private final ConcurrentHashMap credMap = new ConcurrentHashMap();
}
