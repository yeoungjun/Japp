// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http;

import java.io.IOException;

public class ContentTooLongException extends IOException
{

    public ContentTooLongException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0xf32c453e51f4fce9L;
}
