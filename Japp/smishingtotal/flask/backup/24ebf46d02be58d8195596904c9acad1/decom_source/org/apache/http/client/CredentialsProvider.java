// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;

public interface CredentialsProvider
{

    public abstract void clear();

    public abstract Credentials getCredentials(AuthScope authscope);

    public abstract void setCredentials(AuthScope authscope, Credentials credentials);
}
