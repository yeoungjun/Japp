// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

// Referenced classes of package org.apache.http.util:
//            Args

public class VersionInfo
{

    protected VersionInfo(String s, String s1, String s2, String s3, String s4)
    {
        Args.notNull(s, "Package identifier");
        infoPackage = s;
        if(s1 == null)
            s1 = "UNAVAILABLE";
        infoModule = s1;
        if(s2 == null)
            s2 = "UNAVAILABLE";
        infoRelease = s2;
        if(s3 == null)
            s3 = "UNAVAILABLE";
        infoTimestamp = s3;
        if(s4 == null)
            s4 = "UNAVAILABLE";
        infoClassloader = s4;
    }

    protected static VersionInfo fromMap(String s, Map map, ClassLoader classloader)
    {
        Args.notNull(s, "Package identifier");
        String s1 = null;
        String s2 = null;
        String s3 = null;
        if(map != null)
        {
            s1 = (String)map.get("info.module");
            if(s1 != null && s1.length() < 1)
                s1 = null;
            s2 = (String)map.get("info.release");
            if(s2 != null && (s2.length() < 1 || s2.equals("${pom.version}")))
                s2 = null;
            s3 = (String)map.get("info.timestamp");
            if(s3 != null && (s3.length() < 1 || s3.equals("${mvn.timestamp}")))
                s3 = null;
        }
        String s4 = null;
        if(classloader != null)
            s4 = classloader.toString();
        return new VersionInfo(s, s1, s2, s3, s4);
    }

    public static String getUserAgent(String s, String s1, Class class1)
    {
        VersionInfo versioninfo = loadVersionInfo(s1, class1.getClassLoader());
        String s2;
        String s3;
        if(versioninfo != null)
            s2 = versioninfo.getRelease();
        else
            s2 = "UNAVAILABLE";
        s3 = System.getProperty("java.version");
        return (new StringBuilder()).append(s).append("/").append(s2).append(" (Java 1.5 minimum; Java/").append(s3).append(")").toString();
    }

    public static VersionInfo loadVersionInfo(String s, ClassLoader classloader)
    {
        InputStream inputstream;
        Args.notNull(s, "Package identifier");
        ClassLoader classloader1;
        Properties properties;
        VersionInfo versioninfo;
        Properties properties1;
        if(classloader != null)
            classloader1 = classloader;
        else
            classloader1 = Thread.currentThread().getContextClassLoader();
        properties = null;
        inputstream = classloader1.getResourceAsStream((new StringBuilder()).append(s.replace('.', '/')).append("/").append("version.properties").toString());
        properties = null;
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_86;
        properties1 = new Properties();
        properties1.load(inputstream);
        properties = properties1;
        Exception exception;
        try
        {
            inputstream.close();
        }
        catch(IOException ioexception) { }
        versioninfo = null;
        if(properties != null)
            versioninfo = fromMap(s, properties, classloader1);
        return versioninfo;
        exception;
        inputstream.close();
        throw exception;
    }

    public static VersionInfo[] loadVersionInfo(String as[], ClassLoader classloader)
    {
        Args.notNull(as, "Package identifier array");
        ArrayList arraylist = new ArrayList(as.length);
        int i = as.length;
        for(int j = 0; j < i; j++)
        {
            VersionInfo versioninfo = loadVersionInfo(as[j], classloader);
            if(versioninfo != null)
                arraylist.add(versioninfo);
        }

        return (VersionInfo[])arraylist.toArray(new VersionInfo[arraylist.size()]);
    }

    public final String getClassloader()
    {
        return infoClassloader;
    }

    public final String getModule()
    {
        return infoModule;
    }

    public final String getPackage()
    {
        return infoPackage;
    }

    public final String getRelease()
    {
        return infoRelease;
    }

    public final String getTimestamp()
    {
        return infoTimestamp;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(20 + infoPackage.length() + infoModule.length() + infoRelease.length() + infoTimestamp.length() + infoClassloader.length());
        stringbuilder.append("VersionInfo(").append(infoPackage).append(':').append(infoModule);
        if(!"UNAVAILABLE".equals(infoRelease))
            stringbuilder.append(':').append(infoRelease);
        if(!"UNAVAILABLE".equals(infoTimestamp))
            stringbuilder.append(':').append(infoTimestamp);
        stringbuilder.append(')');
        if(!"UNAVAILABLE".equals(infoClassloader))
            stringbuilder.append('@').append(infoClassloader);
        return stringbuilder.toString();
    }

    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    private final String infoClassloader;
    private final String infoModule;
    private final String infoPackage;
    private final String infoRelease;
    private final String infoTimestamp;
}
