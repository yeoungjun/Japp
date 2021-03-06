// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.lang3;

import java.io.File;
import java.io.PrintStream;

// Referenced classes of package org.apache.commons.lang3:
//            JavaVersion

public class SystemUtils
{

    public SystemUtils()
    {
    }

    public static File getJavaHome()
    {
        return new File(System.getProperty("java.home"));
    }

    public static File getJavaIoTmpDir()
    {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    private static boolean getJavaVersionMatches(String s)
    {
        return isJavaVersionMatch(JAVA_SPECIFICATION_VERSION, s);
    }

    private static boolean getOSMatches(String s, String s1)
    {
        return isOSMatch(OS_NAME, OS_VERSION, s, s1);
    }

    private static boolean getOSMatchesName(String s)
    {
        return isOSNameMatch(OS_NAME, s);
    }

    private static String getSystemProperty(String s)
    {
        String s1;
        try
        {
            s1 = System.getProperty(s);
        }
        catch(SecurityException securityexception)
        {
            System.err.println((new StringBuilder()).append("Caught a SecurityException reading the system property '").append(s).append("'; the SystemUtils property value will default to null.").toString());
            return null;
        }
        return s1;
    }

    public static File getUserDir()
    {
        return new File(System.getProperty("user.dir"));
    }

    public static File getUserHome()
    {
        return new File(System.getProperty("user.home"));
    }

    public static boolean isJavaAwtHeadless()
    {
        if(JAVA_AWT_HEADLESS != null)
            return JAVA_AWT_HEADLESS.equals(Boolean.TRUE.toString());
        else
            return false;
    }

    public static boolean isJavaVersionAtLeast(JavaVersion javaversion)
    {
        return JAVA_SPECIFICATION_VERSION_AS_ENUM.atLeast(javaversion);
    }

    static boolean isJavaVersionMatch(String s, String s1)
    {
        if(s == null)
            return false;
        else
            return s.startsWith(s1);
    }

    static boolean isOSMatch(String s, String s1, String s2, String s3)
    {
        while(s == null || s1 == null || !s.startsWith(s2) || !s1.startsWith(s3)) 
            return false;
        return true;
    }

    static boolean isOSNameMatch(String s, String s1)
    {
        if(s == null)
            return false;
        else
            return s.startsWith(s1);
    }

    public static final String AWT_TOOLKIT;
    public static final String FILE_ENCODING;
    public static final String FILE_SEPARATOR;
    public static final boolean IS_JAVA_1_1 = false;
    public static final boolean IS_JAVA_1_2 = false;
    public static final boolean IS_JAVA_1_3 = false;
    public static final boolean IS_JAVA_1_4 = false;
    public static final boolean IS_JAVA_1_5 = false;
    public static final boolean IS_JAVA_1_6 = false;
    public static final boolean IS_JAVA_1_7 = false;
    public static final boolean IS_OS_AIX = false;
    public static final boolean IS_OS_FREE_BSD = false;
    public static final boolean IS_OS_HP_UX = false;
    public static final boolean IS_OS_IRIX = false;
    public static final boolean IS_OS_LINUX = false;
    public static final boolean IS_OS_MAC = false;
    public static final boolean IS_OS_MAC_OSX = false;
    public static final boolean IS_OS_NET_BSD = false;
    public static final boolean IS_OS_OPEN_BSD = false;
    public static final boolean IS_OS_OS2 = false;
    public static final boolean IS_OS_SOLARIS = false;
    public static final boolean IS_OS_SUN_OS = false;
    public static final boolean IS_OS_UNIX = false;
    public static final boolean IS_OS_WINDOWS = false;
    public static final boolean IS_OS_WINDOWS_2000 = false;
    public static final boolean IS_OS_WINDOWS_2003 = false;
    public static final boolean IS_OS_WINDOWS_2008 = false;
    public static final boolean IS_OS_WINDOWS_7 = false;
    public static final boolean IS_OS_WINDOWS_95 = false;
    public static final boolean IS_OS_WINDOWS_98 = false;
    public static final boolean IS_OS_WINDOWS_ME = false;
    public static final boolean IS_OS_WINDOWS_NT = false;
    public static final boolean IS_OS_WINDOWS_VISTA = false;
    public static final boolean IS_OS_WINDOWS_XP = false;
    public static final String JAVA_AWT_FONTS;
    public static final String JAVA_AWT_GRAPHICSENV;
    public static final String JAVA_AWT_HEADLESS;
    public static final String JAVA_AWT_PRINTERJOB;
    public static final String JAVA_CLASS_PATH;
    public static final String JAVA_CLASS_VERSION;
    public static final String JAVA_COMPILER;
    public static final String JAVA_ENDORSED_DIRS;
    public static final String JAVA_EXT_DIRS;
    public static final String JAVA_HOME;
    private static final String JAVA_HOME_KEY = "java.home";
    public static final String JAVA_IO_TMPDIR;
    private static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
    public static final String JAVA_LIBRARY_PATH;
    public static final String JAVA_RUNTIME_NAME;
    public static final String JAVA_RUNTIME_VERSION;
    public static final String JAVA_SPECIFICATION_NAME;
    public static final String JAVA_SPECIFICATION_VENDOR;
    public static final String JAVA_SPECIFICATION_VERSION;
    private static final JavaVersion JAVA_SPECIFICATION_VERSION_AS_ENUM;
    public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY;
    public static final String JAVA_VENDOR;
    public static final String JAVA_VENDOR_URL;
    public static final String JAVA_VERSION;
    public static final String JAVA_VM_INFO;
    public static final String JAVA_VM_NAME;
    public static final String JAVA_VM_SPECIFICATION_NAME;
    public static final String JAVA_VM_SPECIFICATION_VENDOR;
    public static final String JAVA_VM_SPECIFICATION_VERSION;
    public static final String JAVA_VM_VENDOR;
    public static final String JAVA_VM_VERSION;
    public static final String LINE_SEPARATOR;
    public static final String OS_ARCH;
    public static final String OS_NAME;
    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
    public static final String OS_VERSION;
    public static final String PATH_SEPARATOR;
    public static final String USER_COUNTRY;
    public static final String USER_DIR;
    private static final String USER_DIR_KEY = "user.dir";
    public static final String USER_HOME;
    private static final String USER_HOME_KEY = "user.home";
    public static final String USER_LANGUAGE;
    public static final String USER_NAME;
    public static final String USER_TIMEZONE;

    static 
    {
label0:
        {
            AWT_TOOLKIT = getSystemProperty("awt.toolkit");
            FILE_ENCODING = getSystemProperty("file.encoding");
            FILE_SEPARATOR = getSystemProperty("file.separator");
            JAVA_AWT_FONTS = getSystemProperty("java.awt.fonts");
            JAVA_AWT_GRAPHICSENV = getSystemProperty("java.awt.graphicsenv");
            JAVA_AWT_HEADLESS = getSystemProperty("java.awt.headless");
            JAVA_AWT_PRINTERJOB = getSystemProperty("java.awt.printerjob");
            JAVA_CLASS_PATH = getSystemProperty("java.class.path");
            JAVA_CLASS_VERSION = getSystemProperty("java.class.version");
            JAVA_COMPILER = getSystemProperty("java.compiler");
            JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs");
            JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");
            JAVA_HOME = getSystemProperty("java.home");
            JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");
            JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");
            JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");
            JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");
            JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");
            JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");
            JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
            JAVA_SPECIFICATION_VERSION_AS_ENUM = JavaVersion.get(JAVA_SPECIFICATION_VERSION);
            JAVA_UTIL_PREFS_PREFERENCES_FACTORY = getSystemProperty("java.util.prefs.PreferencesFactory");
            JAVA_VENDOR = getSystemProperty("java.vendor");
            JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");
            JAVA_VERSION = getSystemProperty("java.version");
            JAVA_VM_INFO = getSystemProperty("java.vm.info");
            JAVA_VM_NAME = getSystemProperty("java.vm.name");
            JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");
            JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");
            JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");
            JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");
            JAVA_VM_VERSION = getSystemProperty("java.vm.version");
            LINE_SEPARATOR = getSystemProperty("line.separator");
            OS_ARCH = getSystemProperty("os.arch");
            OS_NAME = getSystemProperty("os.name");
            OS_VERSION = getSystemProperty("os.version");
            PATH_SEPARATOR = getSystemProperty("path.separator");
            String s;
            boolean flag;
            boolean flag1;
            if(getSystemProperty("user.country") == null)
                s = getSystemProperty("user.region");
            else
                s = getSystemProperty("user.country");
            USER_COUNTRY = s;
            USER_DIR = getSystemProperty("user.dir");
            USER_HOME = getSystemProperty("user.home");
            USER_LANGUAGE = getSystemProperty("user.language");
            USER_NAME = getSystemProperty("user.name");
            USER_TIMEZONE = getSystemProperty("user.timezone");
            IS_JAVA_1_1 = getJavaVersionMatches("1.1");
            IS_JAVA_1_2 = getJavaVersionMatches("1.2");
            IS_JAVA_1_3 = getJavaVersionMatches("1.3");
            IS_JAVA_1_4 = getJavaVersionMatches("1.4");
            IS_JAVA_1_5 = getJavaVersionMatches("1.5");
            IS_JAVA_1_6 = getJavaVersionMatches("1.6");
            IS_JAVA_1_7 = getJavaVersionMatches("1.7");
            IS_OS_AIX = getOSMatchesName("AIX");
            IS_OS_HP_UX = getOSMatchesName("HP-UX");
            IS_OS_IRIX = getOSMatchesName("Irix");
            if(getOSMatchesName("Linux") || getOSMatchesName("LINUX"))
                flag = true;
            else
                flag = false;
            IS_OS_LINUX = flag;
            IS_OS_MAC = getOSMatchesName("Mac");
            IS_OS_MAC_OSX = getOSMatchesName("Mac OS X");
            IS_OS_FREE_BSD = getOSMatchesName("FreeBSD");
            IS_OS_OPEN_BSD = getOSMatchesName("OpenBSD");
            IS_OS_NET_BSD = getOSMatchesName("NetBSD");
            IS_OS_OS2 = getOSMatchesName("OS/2");
            IS_OS_SOLARIS = getOSMatchesName("Solaris");
            IS_OS_SUN_OS = getOSMatchesName("SunOS");
            if(!IS_OS_AIX && !IS_OS_HP_UX && !IS_OS_IRIX && !IS_OS_LINUX && !IS_OS_MAC_OSX && !IS_OS_SOLARIS && !IS_OS_SUN_OS && !IS_OS_FREE_BSD && !IS_OS_OPEN_BSD)
            {
                boolean flag2 = IS_OS_NET_BSD;
                flag1 = false;
                if(!flag2)
                    break label0;
            }
            flag1 = true;
        }
        IS_OS_UNIX = flag1;
        IS_OS_WINDOWS = getOSMatchesName("Windows");
        IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
        IS_OS_WINDOWS_2003 = getOSMatches("Windows", "5.2");
        IS_OS_WINDOWS_2008 = getOSMatches("Windows Server 2008", "6.1");
        IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
        IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
        IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
        IS_OS_WINDOWS_NT = getOSMatchesName("Windows NT");
        IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");
        IS_OS_WINDOWS_VISTA = getOSMatches("Windows", "6.0");
        IS_OS_WINDOWS_7 = getOSMatches("Windows", "6.1");
    }
}
