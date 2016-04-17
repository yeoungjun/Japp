// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.activation.registries;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogSupport
{

    private LogSupport()
    {
    }

    public static boolean isLoggable()
    {
        return debug || logger.isLoggable(level);
    }

    public static void log(String s)
    {
        if(debug)
            System.out.println(s);
        logger.log(level, s);
    }

    public static void log(String s, Throwable throwable)
    {
        if(debug)
            System.out.println((new StringBuilder(String.valueOf(s))).append("; Exception: ").append(throwable).toString());
        logger.log(level, s, throwable);
    }

    private static boolean debug = false;
    private static final Level level;
    private static Logger logger = Logger.getLogger("javax.activation");

    static 
    {
        level = Level.FINE;
        try
        {
            debug = Boolean.getBoolean("javax.activation.debug");
        }
        catch(Throwable throwable) { }
    }
}
