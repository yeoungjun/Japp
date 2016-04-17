// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.protocol;


public interface HttpContext
{

    public abstract Object getAttribute(String s);

    public abstract Object removeAttribute(String s);

    public abstract void setAttribute(String s, Object obj);

    public static final String RESERVED_PREFIX = "http.";
}
