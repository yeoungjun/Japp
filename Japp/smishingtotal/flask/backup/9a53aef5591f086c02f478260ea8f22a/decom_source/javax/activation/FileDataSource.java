// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.activation;

import java.io.*;

// Referenced classes of package javax.activation:
//            DataSource, FileTypeMap

public class FileDataSource
    implements DataSource
{

    public FileDataSource(File file)
    {
        _file = null;
        typeMap = null;
        _file = file;
    }

    public FileDataSource(String s)
    {
        this(new File(s));
    }

    public String getContentType()
    {
        if(typeMap == null)
            return FileTypeMap.getDefaultFileTypeMap().getContentType(_file);
        else
            return typeMap.getContentType(_file);
    }

    public File getFile()
    {
        return _file;
    }

    public InputStream getInputStream()
        throws IOException
    {
        return new FileInputStream(_file);
    }

    public String getName()
    {
        return _file.getName();
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        return new FileOutputStream(_file);
    }

    public void setFileTypeMap(FileTypeMap filetypemap)
    {
        typeMap = filetypemap;
    }

    private File _file;
    private FileTypeMap typeMap;
}
