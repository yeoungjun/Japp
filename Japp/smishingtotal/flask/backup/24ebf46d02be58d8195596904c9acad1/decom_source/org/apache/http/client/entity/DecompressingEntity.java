// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.entity;

import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.util.Args;

abstract class DecompressingEntity extends HttpEntityWrapper
{

    public DecompressingEntity(HttpEntity httpentity)
    {
        super(httpentity);
    }

    private InputStream getDecompressingStream()
        throws IOException
    {
        InputStream inputstream = wrappedEntity.getContent();
        InputStream inputstream1;
        try
        {
            inputstream1 = decorate(inputstream);
        }
        catch(IOException ioexception)
        {
            inputstream.close();
            throw ioexception;
        }
        return inputstream1;
    }

    abstract InputStream decorate(InputStream inputstream)
        throws IOException;

    public InputStream getContent()
        throws IOException
    {
        if(wrappedEntity.isStreaming())
        {
            if(content == null)
                content = getDecompressingStream();
            return content;
        } else
        {
            return getDecompressingStream();
        }
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        InputStream inputstream;
        Args.notNull(outputstream, "Output stream");
        inputstream = getContent();
        byte abyte0[] = new byte[2048];
_L1:
        int i = inputstream.read(abyte0);
        if(i == -1)
            break MISSING_BLOCK_LABEL_54;
        outputstream.write(abyte0, 0, i);
          goto _L1
        Exception exception;
        exception;
        inputstream.close();
        throw exception;
        inputstream.close();
        return;
    }

    private static final int BUFFER_SIZE = 2048;
    private InputStream content;
}
