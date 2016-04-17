// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity;

import java.io.*;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.entity:
//            AbstractHttpEntity, ContentType

public class FileEntity extends AbstractHttpEntity
    implements Cloneable
{

    public FileEntity(File file1)
    {
        file = (File)Args.notNull(file1, "File");
    }

    public FileEntity(File file1, String s)
    {
        file = (File)Args.notNull(file1, "File");
        setContentType(s);
    }

    public FileEntity(File file1, ContentType contenttype)
    {
        file = (File)Args.notNull(file1, "File");
        if(contenttype != null)
            setContentType(contenttype.toString());
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }

    public InputStream getContent()
        throws IOException
    {
        return new FileInputStream(file);
    }

    public long getContentLength()
    {
        return file.length();
    }

    public boolean isRepeatable()
    {
        return true;
    }

    public boolean isStreaming()
    {
        return false;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        FileInputStream fileinputstream;
        Args.notNull(outputstream, "Output stream");
        fileinputstream = new FileInputStream(file);
        byte abyte0[] = new byte[4096];
_L1:
        int i = fileinputstream.read(abyte0);
        if(i == -1)
            break MISSING_BLOCK_LABEL_61;
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

    protected final File file;
}
