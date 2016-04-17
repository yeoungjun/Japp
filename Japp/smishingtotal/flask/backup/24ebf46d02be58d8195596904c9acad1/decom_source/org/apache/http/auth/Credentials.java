// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.auth;

import java.security.Principal;

public interface Credentials
{

    public abstract String getPassword();

    public abstract Principal getUserPrincipal();
}
