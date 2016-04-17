// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime.content;

import java.io.*;

// Referenced classes of package org.apache.http.entity.mime.content:
//            AbstractContentBody

public class FileBody extends AbstractContentBody
{

    public FileBody(File file1)
    {
        this(file1, "application/octet-stream");
    }

    public FileBody(File file1, String s)
    {
        this(file1, s, null);
    }

    public FileBody(File file1, String s, String s1)
    {
        this(file1, null, s, s1);
    }

    public FileBody(File file1, String s, String s1, String s2)
    {
        super(s1);
        if(file1 == null)
            throw new IllegalArgumentException("File may not be null");
        file = file1;
        if(s != null)
            filename = s;
        else
            filename = file1.getName();
        charset = s2;
    }

    public String getCharset()
    {
        return charset;
    }

    public long getContentLength()
    {
        return file.length();
    }

    public File getFile()
    {
        return file;
    }

    public String getFilename()
    {
        return filename;
    }

    public InputStream getInputStream()
        throws IOException
    {
        return new FileInputStream(file);
    }

    public String getTransferEncoding()
    {
        return "binary";
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        FileInputStream fileinputstream;
        if(outputstream == null)
            throw new IllegalArgumentException("Output stream may not be null");
        fileinputstream = new FileInputStream(file);
        byte abyte0[] = new byte[4096];
_L1:
        int i = fileinputstream.read(abyte0);
        if(i == -1)
            break MISSING_BLOCK_LABEL_66;
        outputstream.write(abyte0, 0, i);
          goto _L1
        Exception exception;
        exception;
        fileinputstream.close();
        throw exception;
        outputstream.flush();
        fileinputstream.close();
        return;
    }

    private final String charset;
    private final File file;
    private final String filename;
}
