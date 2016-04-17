// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.io.IOException;

public class MalformedChunkCodingException extends IOException
{

    public MalformedChunkCodingException()
    {
    }

    public MalformedChunkCodingException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0x1df4bf1ba9ac9ddcL;
}
