// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.entity;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.*;

// Referenced classes of package org.apache.http.client.entity:
//            GzipCompressingEntity, UrlEncodedFormEntity

public class EntityBuilder
{

    EntityBuilder()
    {
    }

    private void clearContent()
    {
        text = null;
        binary = null;
        stream = null;
        parameters = null;
        serializable = null;
        file = null;
    }

    public static EntityBuilder create()
    {
        return new EntityBuilder();
    }

    private ContentType getContentOrDefault(ContentType contenttype)
    {
        if(contentType != null)
            contenttype = contentType;
        return contenttype;
    }

    public HttpEntity build()
    {
        Object obj;
        if(text != null)
            obj = new StringEntity(text, getContentOrDefault(ContentType.DEFAULT_TEXT));
        else
        if(binary != null)
            obj = new ByteArrayEntity(binary, getContentOrDefault(ContentType.DEFAULT_BINARY));
        else
        if(stream != null)
            obj = new InputStreamEntity(stream, 1L, getContentOrDefault(ContentType.DEFAULT_BINARY));
        else
        if(parameters != null)
        {
            List list = parameters;
            java.nio.charset.Charset charset;
            if(contentType != null)
                charset = contentType.getCharset();
            else
                charset = null;
            obj = new UrlEncodedFormEntity(list, charset);
        } else
        if(serializable != null)
        {
            obj = new SerializableEntity(serializable);
            ((AbstractHttpEntity) (obj)).setContentType(ContentType.DEFAULT_BINARY.toString());
        } else
        if(file != null)
            obj = new FileEntity(file, getContentOrDefault(ContentType.DEFAULT_BINARY));
        else
            obj = new BasicHttpEntity();
        if(((AbstractHttpEntity) (obj)).getContentType() != null && contentType != null)
            ((AbstractHttpEntity) (obj)).setContentType(contentType.toString());
        ((AbstractHttpEntity) (obj)).setContentEncoding(contentEncoding);
        ((AbstractHttpEntity) (obj)).setChunked(chunked);
        if(gzipCompress)
            obj = new GzipCompressingEntity(((HttpEntity) (obj)));
        return ((HttpEntity) (obj));
    }

    public EntityBuilder chunked()
    {
        chunked = true;
        return this;
    }

    public byte[] getBinary()
    {
        return binary;
    }

    public String getContentEncoding()
    {
        return contentEncoding;
    }

    public ContentType getContentType()
    {
        return contentType;
    }

    public File getFile()
    {
        return file;
    }

    public List getParameters()
    {
        return parameters;
    }

    public Serializable getSerializable()
    {
        return serializable;
    }

    public InputStream getStream()
    {
        return stream;
    }

    public String getText()
    {
        return text;
    }

    public EntityBuilder gzipCompress()
    {
        gzipCompress = true;
        return this;
    }

    public boolean isChunked()
    {
        return chunked;
    }

    public boolean isGzipCompress()
    {
        return gzipCompress;
    }

    public EntityBuilder setBinary(byte abyte0[])
    {
        clearContent();
        binary = abyte0;
        return this;
    }

    public EntityBuilder setContentEncoding(String s)
    {
        contentEncoding = s;
        return this;
    }

    public EntityBuilder setContentType(ContentType contenttype)
    {
        contentType = contenttype;
        return this;
    }

    public EntityBuilder setFile(File file1)
    {
        clearContent();
        file = file1;
        return this;
    }

    public EntityBuilder setParameters(List list)
    {
        clearContent();
        parameters = list;
        return this;
    }

    public transient EntityBuilder setParameters(NameValuePair anamevaluepair[])
    {
        return setParameters(Arrays.asList(anamevaluepair));
    }

    public EntityBuilder setSerializable(Serializable serializable1)
    {
        clearContent();
        serializable = serializable1;
        return this;
    }

    public EntityBuilder setStream(InputStream inputstream)
    {
        clearContent();
        stream = inputstream;
        return this;
    }

    public EntityBuilder setText(String s)
    {
        clearContent();
        text = s;
        return this;
    }

    private byte binary[];
    private boolean chunked;
    private String contentEncoding;
    private ContentType contentType;
    private File file;
    private boolean gzipCompress;
    private List parameters;
    private Serializable serializable;
    private InputStream stream;
    private String text;
}
