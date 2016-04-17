// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.client.entity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

public class UrlEncodedFormEntity extends StringEntity
{

    public UrlEncodedFormEntity(Iterable iterable)
    {
        this(iterable, ((Charset) (null)));
    }

    public UrlEncodedFormEntity(Iterable iterable, Charset charset)
    {
        Charset charset1;
        if(charset != null)
            charset1 = charset;
        else
            charset1 = HTTP.DEF_CONTENT_CHARSET;
        super(URLEncodedUtils.format(iterable, charset1), ContentType.create("application/x-www-form-urlencoded", charset));
    }

    public UrlEncodedFormEntity(List list)
        throws UnsupportedEncodingException
    {
        this(((Iterable) (list)), (Charset)null);
    }

    public UrlEncodedFormEntity(List list, String s)
        throws UnsupportedEncodingException
    {
        String s1;
        if(s != null)
            s1 = s;
        else
            s1 = HTTP.DEF_CONTENT_CHARSET.name();
        super(URLEncodedUtils.format(list, s1), ContentType.create("application/x-www-form-urlencoded", s));
    }
}
