// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime.content;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;

// Referenced classes of package org.apache.http.entity.mime.content:
//            AbstractContentBody

public class StringBody extends AbstractContentBody
{

    public StringBody(String s)
        throws UnsupportedEncodingException
    {
        this(s, "text/plain", Consts.ASCII);
    }

    public StringBody(String s, String s1, Charset charset)
        throws UnsupportedEncodingException
    {
        this(s, ContentType.create(s1, charset));
    }

    public StringBody(String s, Charset charset)
        throws UnsupportedEncodingException
    {
        this(s, "text/plain", charset);
    }

    public StringBody(String s, ContentType contenttype)
    {
        super(contenttype);
        Charset charset = contenttype.getCharset();
        String s1;
        if(charset != null)
            s1 = charset.name();
        else
            s1 = Consts.ASCII.name();
        try
        {
            content = s.getBytes(s1);
            return;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new UnsupportedCharsetException(s1);
        }
    }

    public static StringBody create(String s)
        throws IllegalArgumentException
    {
        return create(s, null, null);
    }

    public static StringBody create(String s, String s1, Charset charset)
        throws IllegalArgumentException
    {
        StringBody stringbody;
        try
        {
            stringbody = new StringBody(s, s1, charset);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Charset ").append(charset).append(" is not supported").toString(), unsupportedencodingexception);
        }
        return stringbody;
    }

    public static StringBody create(String s, Charset charset)
        throws IllegalArgumentException
    {
        return create(s, null, charset);
    }

    public long getContentLength()
    {
        return (long)content.length;
    }

    public String getFilename()
    {
        return null;
    }

    public Reader getReader()
    {
        Charset charset = getContentType().getCharset();
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(content);
        if(charset == null)
            charset = Consts.ASCII;
        return new InputStreamReader(bytearrayinputstream, charset);
    }

    public String getTransferEncoding()
    {
        return "8bit";
    }

    public void writeTo(OutputStream outputstream)
        throws IOException
    {
        Args.notNull(outputstream, "Output stream");
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(content);
        byte abyte0[] = new byte[4096];
        do
        {
            int i = bytearrayinputstream.read(abyte0);
            if(i != -1)
            {
                outputstream.write(abyte0, 0, i);
            } else
            {
                outputstream.flush();
                return;
            }
        } while(true);
    }

    private final byte content[];
}
