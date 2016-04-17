// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import org.apache.harmony.awt.datatransfer.DTK;

public final class ContextStorage
{
    private class ContextLock
    {

        final ContextStorage this$0;

        private ContextLock()
        {
            this$0 = ContextStorage.this;
            super();
        }

        ContextLock(ContextLock contextlock)
        {
            this();
        }
    }


    public ContextStorage()
    {
        shutdownPending = false;
    }

    public static Object getContextLock()
    {
        return getCurrentContext().contextLock;
    }

    private static ContextStorage getCurrentContext()
    {
        return globalContext;
    }

    public static DTK getDTK()
    {
        return getCurrentContext().dtk;
    }

    public static Toolkit getDefaultToolkit()
    {
        return getCurrentContext().toolkit;
    }

    public static GraphicsEnvironment getGraphicsEnvironment()
    {
        return getCurrentContext().graphicsEnvironment;
    }

    public static void setDTK(DTK dtk1)
    {
        getCurrentContext().dtk = dtk1;
    }

    public static void setDefaultToolkit(Toolkit toolkit1)
    {
        getCurrentContext().toolkit = toolkit1;
    }

    public static void setGraphicsEnvironment(GraphicsEnvironment graphicsenvironment)
    {
        getCurrentContext().graphicsEnvironment = graphicsenvironment;
    }

    public static boolean shutdownPending()
    {
        return getCurrentContext().shutdownPending;
    }

    void shutdown()
    {
    }

    private static final ContextStorage globalContext = new ContextStorage();
    private final Object contextLock = new ContextLock(null);
    private DTK dtk;
    private GraphicsEnvironment graphicsEnvironment;
    private volatile boolean shutdownPending;
    private Toolkit toolkit;

}
