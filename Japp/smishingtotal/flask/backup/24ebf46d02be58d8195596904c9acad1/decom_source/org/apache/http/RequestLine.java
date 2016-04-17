// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;


// Referenced classes of package org.apache.http:
//            ProtocolVersion

public interface RequestLine
{

    public abstract String getMethod();

    public abstract ProtocolVersion getProtocolVersion();

    public abstract String getUri();
}
