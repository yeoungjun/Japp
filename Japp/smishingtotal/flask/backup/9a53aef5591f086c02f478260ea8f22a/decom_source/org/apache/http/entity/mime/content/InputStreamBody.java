// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime.content;

import java.io.*;

// Referenced classes of package org.apache.http.entity.mime.content:
//            AbstractContentBody

public class InputStreamBody extends AbstractContentBody
{

    public InputStreamBody(InputStream inputstream, String s)
    {
        this(inputstream, "application/octet-stream", s);
    }

    public InputStreamBody(InputStream inputstream, String s, String s1)
    {
        super(s);
        if(inputstream == null)
        {
            throw new IllegalArgumentException("Input stream may not be null");
        } else
        {
            in = inputstream;
            filename = s1;
            return;
        }
    }

    public String getCharset()
    {
        return null;
    }

    public long getContentLength()
    {
        return -1L;
    }

    public String getFilename()
    {
        return filename;
    }

    public InputStream getInputStream()
    {
        return in;
    }

    public String getTransferEncoding()
    {
        return "binary";
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        if(outputstream == null)
            throw new IllegalArgumentException("Output stream may not be null");
        byte abyte0[] = new byte[4096];
_L1:
        int i = in.read(abyte0);
        if(i == -1)
            break MISSING_BLOCK_LABEL_57;
        outputstream.write(abyte0, 0, i);
          goto _L1
        Exception exception;
        exception;
        in.close();
        throw exception;
        outputstream.flush();
        in.close();
        return;
    }

    private final String filename;
    private final InputStream in;
}
