// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.harmony.misc;


public class SystemUtils
{

    public SystemUtils()
    {
    }

    public static int getOS()
    {
        if(os == 0)
        {
            String s = System.getProperty("os.name").substring(0, 3);
            if(s.compareToIgnoreCase("win") == 0)
                os = 1;
            else
            if(s.compareToIgnoreCase("lin") == 0)
                os = 2;
            else
                os = -1;
        }
        return os;
    }

    public static final int ARC_IA32 = 1;
    public static final int ARC_IA64 = 2;
    public static final int ARC_UNKNOWN = -1;
    public static final int OS_LINUX = 2;
    public static final int OS_UNKNOWN = -1;
    public static final int OS_WINDOWS = 1;
    private static int arc = 0;
    private static int os = 0;

}
