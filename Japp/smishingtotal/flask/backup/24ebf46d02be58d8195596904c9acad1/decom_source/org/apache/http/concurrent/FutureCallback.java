// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.concurrent;


public interface FutureCallback
{

    public abstract void cancelled();

    public abstract void completed(Object obj);

    public abstract void failed(Exception exception);
}
