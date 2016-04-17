// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.File;

// Referenced classes of package javax.activation:
//            MimetypesFileTypeMap

public abstract class FileTypeMap
{

    public FileTypeMap()
    {
    }

    public static FileTypeMap getDefaultFileTypeMap()
    {
        if(defaultMap == null)
            defaultMap = new MimetypesFileTypeMap();
        return defaultMap;
    }

    public static void setDefaultFileTypeMap(FileTypeMap filetypemap)
    {
        SecurityManager securitymanager = System.getSecurityManager();
        if(securitymanager != null)
            try
            {
                securitymanager.checkSetFactory();
            }
            catch(SecurityException securityexception)
            {
                if(javax/activation/FileTypeMap.getClassLoader() != filetypemap.getClass().getClassLoader())
                    throw securityexception;
            }
        defaultMap = filetypemap;
    }

    public abstract String getContentType(File file);

    public abstract String getContentType(String s);

    private static FileTypeMap defaultMap = null;

}
