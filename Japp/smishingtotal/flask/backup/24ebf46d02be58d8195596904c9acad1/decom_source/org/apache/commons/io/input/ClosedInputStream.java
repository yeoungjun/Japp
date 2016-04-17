// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.InputStream;

public class ClosedInputStream extends InputStream
{

    public ClosedInputStream()
    {
    }

    public int read()
    {
        return -1;
    }

    public static final ClosedInputStream CLOSED_INPUT_STREAM = new ClosedInputStream();

}
