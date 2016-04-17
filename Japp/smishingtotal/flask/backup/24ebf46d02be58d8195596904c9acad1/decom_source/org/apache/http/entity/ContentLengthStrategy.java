// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity;

import org.apache.http.HttpException;
import org.apache.http.HttpMessage;

public interface ContentLengthStrategy
{

    public abstract long determineLength(HttpMessage httpmessage)
        throws HttpException;

    public static final int CHUNKED = -2;
    public static final int IDENTITY = -1;
}
