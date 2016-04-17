// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.Clipboard;

public abstract class NativeClipboard extends Clipboard
{

    public NativeClipboard(String s)
    {
        super(s);
    }

    public void onRestart()
    {
    }

    public void onShutdown()
    {
    }

    protected static final int OPS_TIMEOUT = 10000;
}
