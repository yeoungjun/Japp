// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MimeTypeFile;
import java.io.*;
import java.util.Vector;

// Referenced classes of package javax.activation:
//            FileTypeMap, SecuritySupport

public class MimetypesFileTypeMap extends FileTypeMap
{

    public MimetypesFileTypeMap()
    {
        Vector vector;
        vector = new Vector(5);
        vector.addElement(null);
        LogSupport.log("MimetypesFileTypeMap: load HOME");
        String s = System.getProperty("user.home");
        if(s == null)
            break MISSING_BLOCK_LABEL_78;
        MimeTypeFile mimetypefile1 = loadFile((new StringBuilder(String.valueOf(s))).append(File.separator).append(".mime.types").toString());
        MimeTypeFile mimetypefile;
        if(mimetypefile1 != null)
            try
            {
                vector.addElement(mimetypefile1);
            }
            catch(SecurityException securityexception) { }
        LogSupport.log("MimetypesFileTypeMap: load SYS");
        mimetypefile = loadFile((new StringBuilder(String.valueOf(System.getProperty("java.home")))).append(File.separator).append("lib").append(File.separator).append("mime.types").toString());
        Exception exception;
        if(mimetypefile != null)
            try
            {
                vector.addElement(mimetypefile);
            }
            catch(SecurityException securityexception1) { }
        LogSupport.log("MimetypesFileTypeMap: load JAR");
        loadAllResources(vector, "mime.types");
        LogSupport.log("MimetypesFileTypeMap: load DEF");
        javax/activation/MimetypesFileTypeMap;
        JVM INSTR monitorenter ;
        if(defDB == null)
            defDB = loadResource("/mimetypes.default");
        javax/activation/MimetypesFileTypeMap;
        JVM INSTR monitorexit ;
        if(defDB != null)
            vector.addElement(defDB);
        DB = new MimeTypeFile[vector.size()];
        vector.copyInto(DB);
        return;
        exception;
        javax/activation/MimetypesFileTypeMap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public MimetypesFileTypeMap(InputStream inputstream)
    {
        this();
        try
        {
            DB[0] = new MimeTypeFile(inputstream);
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public MimetypesFileTypeMap(String s)
        throws IOException
    {
        this();
        DB[0] = new MimeTypeFile(s);
    }

    private void loadAllResources(Vector vector, String s)
    {
        boolean flag = false;
        ClassLoader classloader = SecuritySupport.getContextClassLoader();
        flag = false;
        if(classloader != null)
            break MISSING_BLOCK_LABEL_23;
        classloader = getClass().getClassLoader();
        flag = false;
        if(classloader == null) goto _L2; else goto _L1
_L1:
        java.net.URL aurl[] = SecuritySupport.getResources(classloader, s);
_L5:
        int i;
        flag = false;
        MimeTypeFile mimetypefile;
        int j;
        java.net.URL url;
        InputStream inputstream;
        Exception exception1;
        SecurityException securityexception;
        IOException ioexception1;
        IOException ioexception2;
        IOException ioexception3;
        if(aurl != null)
            try
            {
                if(LogSupport.isLoggable())
                    LogSupport.log("MimetypesFileTypeMap: getResources");
                break MISSING_BLOCK_LABEL_410;
            }
            catch(Exception exception)
            {
                if(LogSupport.isLoggable())
                    LogSupport.log((new StringBuilder("MimetypesFileTypeMap: can't load ")).append(s).toString(), exception);
            }
          goto _L3
_L8:
        j = aurl.length;
        if(i < j) goto _L4; else goto _L3
_L3:
        if(!flag)
        {
            LogSupport.log("MimetypesFileTypeMap: !anyLoaded");
            mimetypefile = loadResource((new StringBuilder("/")).append(s).toString());
            if(mimetypefile != null)
                vector.addElement(mimetypefile);
        }
        return;
_L2:
        aurl = SecuritySupport.getSystemResources(s);
          goto _L5
_L4:
        url = aurl[i];
        inputstream = null;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: URL ")).append(url).toString());
        inputstream = SecuritySupport.openStream(url);
        if(inputstream == null) goto _L7; else goto _L6
_L6:
        vector.addElement(new MimeTypeFile(inputstream));
        flag = true;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: successfully loaded mime types from URL: ")).append(url).toString());
_L10:
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_222;
        inputstream.close();
_L12:
        i++;
          goto _L8
_L7:
        if(!LogSupport.isLoggable()) goto _L10; else goto _L9
_L9:
        LogSupport.log((new StringBuilder("MimetypesFileTypeMap: not loading mime types from URL: ")).append(url).toString());
          goto _L10
        ioexception2;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: can't load ")).append(url).toString(), ioexception2);
        if(inputstream == null) goto _L12; else goto _L11
_L11:
        inputstream.close();
          goto _L12
        ioexception3;
          goto _L12
        securityexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: can't load ")).append(url).toString(), securityexception);
        if(inputstream == null) goto _L12; else goto _L13
_L13:
        inputstream.close();
          goto _L12
        ioexception1;
          goto _L12
        exception1;
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_365;
        try
        {
            inputstream.close();
        }
        catch(IOException ioexception) { }
        throw exception1;
        IOException ioexception4;
        ioexception4;
          goto _L12
        flag = false;
        i = 0;
          goto _L8
    }

    private MimeTypeFile loadFile(String s)
    {
        MimeTypeFile mimetypefile;
        try
        {
            mimetypefile = new MimeTypeFile(s);
        }
        catch(IOException ioexception)
        {
            return null;
        }
        return mimetypefile;
    }

    private MimeTypeFile loadResource(String s)
    {
        InputStream inputstream = null;
        inputstream = SecuritySupport.getResourceAsStream(getClass(), s);
        if(inputstream == null)
            break MISSING_BLOCK_LABEL_61;
        MimeTypeFile mimetypefile;
        mimetypefile = new MimeTypeFile(inputstream);
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: successfully loaded mime types file: ")).append(s).toString());
        Exception exception;
        SecurityException securityexception;
        IOException ioexception1;
        IOException ioexception2;
        IOException ioexception3;
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception4)
            {
                return mimetypefile;
            }
        return mimetypefile;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: not loading mime types file: ")).append(s).toString());
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception5) { }
        return null;
        ioexception2;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: can't load ")).append(s).toString(), ioexception2);
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception3) { }
        continue; /* Loop/switch isn't completed */
        securityexception;
        if(LogSupport.isLoggable())
            LogSupport.log((new StringBuilder("MimetypesFileTypeMap: can't load ")).append(s).toString(), securityexception);
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
        if(true) goto _L2; else goto _L1
_L1:
        break MISSING_BLOCK_LABEL_185;
_L2:
        break MISSING_BLOCK_LABEL_94;
        exception;
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception) { }
        throw exception;
    }

    public void addMimeTypes(String s)
    {
        this;
        JVM INSTR monitorenter ;
        if(DB[0] == null)
            DB[0] = new MimeTypeFile();
        DB[0].appendToRegistry(s);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public String getContentType(File file)
    {
        return getContentType(file.getName());
    }

    public String getContentType(String s)
    {
        this;
        JVM INSTR monitorenter ;
        int i = s.lastIndexOf(".");
        if(i >= 0) goto _L2; else goto _L1
_L1:
        String s1 = defaultType;
_L4:
        this;
        JVM INSTR monitorexit ;
        return s1;
_L2:
        int j = i + 1;
        String s2;
        s2 = s.substring(j);
        if(s2.length() != 0)
            break; /* Loop/switch isn't completed */
        s1 = defaultType;
        continue; /* Loop/switch isn't completed */
_L6:
        int k;
label0:
        {
            if(k < DB.length)
                break label0;
            s1 = defaultType;
        }
        if(true) goto _L4; else goto _L3
        if(DB[k] == null)
            break; /* Loop/switch isn't completed */
        String s3 = DB[k].getMIMETypeString(s2);
        s1 = s3;
        if(s1 == null) goto _L5; else goto _L4
        Exception exception;
        exception;
        throw exception;
_L3:
        k = 0;
          goto _L6
_L5:
        k++;
          goto _L6
    }

    private static final int PROG;
    private static MimeTypeFile defDB = null;
    private static String defaultType = "application/octet-stream";
    private MimeTypeFile DB[];

}
