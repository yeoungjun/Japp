// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.conn;

import java.io.IOException;

public class UnsupportedSchemeException extends IOException
{

    public UnsupportedSchemeException(String s)
    {
        super(s);
    }

    private static final long serialVersionUID = 0x31eb9082e346c694L;
}
