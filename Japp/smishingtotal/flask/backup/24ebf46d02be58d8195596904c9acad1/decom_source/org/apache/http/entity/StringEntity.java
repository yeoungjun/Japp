// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.entity:
//            AbstractHttpEntity, ContentType

public class StringEntity extends AbstractHttpEntity
    implements Cloneable
{

    public StringEntity(String s)
        throws UnsupportedEncodingException
    {
        this(s, ContentType.DEFAULT_TEXT);
    }

    public StringEntity(String s, String s1)
        throws UnsupportedEncodingException
    {
        this(s, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), s1));
    }

    public StringEntity(String s, String s1, String s2)
        throws UnsupportedEncodingException
    {
        Args.notNull(s, "Source string");
        String s3;
        String s4;
        if(s1 != null)
            s3 = s1;
        else
            s3 = "text/plain";
        if(s2 != null)
            s4 = s2;
        else
            s4 = "ISO-8859-1";
        content = s.getBytes(s4);
        setContentType((new StringBuilder()).append(s3).append("; charset=").append(s4).toString());
    }

    public StringEntity(String s, Charset charset)
    {
        this(s, ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), charset));
    }

    public StringEntity(String s, ContentType contenttype)
    {
        Args.notNull(s, "Source string");
        Charset charset;
        if(contenttype != null)
            charset = contenttype.getCharset();
        else
            charset = null;
        if(charset == null)
            charset = HTTP.DEF_CONTENT_CHARSET;
        try
        {
            content = s.getBytes(charset.name());
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new UnsupportedCharsetException(charset.name());
        }
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
        return new ByteArrayInputStream(content);
    }

    public long getContentLength()
    {
        return (long)content.length;
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
        Args.notNull(outputstream, "Output stream");
        outputstream.write(content);
        outputstream.flush();
    }

    protected final byte content[];
}
