// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.commons.io.input;

import java.io.IOException;

public class XmlStreamReaderException extends IOException
{

    public XmlStreamReaderException(String s, String s1, String s2, String s3)
    {
        this(s, null, null, s1, s2, s3);
    }

    public XmlStreamReaderException(String s, String s1, String s2, String s3, String s4, String s5)
    {
        super(s);
        contentTypeMime = s1;
        contentTypeEncoding = s2;
        bomEncoding = s3;
        xmlGuessEncoding = s4;
        xmlEncoding = s5;
    }

    public String getBomEncoding()
    {
        return bomEncoding;
    }

    public String getContentTypeEncoding()
    {
        return contentTypeEncoding;
    }

    public String getContentTypeMime()
    {
        return contentTypeMime;
    }

    public String getXmlEncoding()
    {
        return xmlEncoding;
    }

    public String getXmlGuessEncoding()
    {
        return xmlGuessEncoding;
    }

    private static final long serialVersionUID = 1L;
    private final String bomEncoding;
    private final String contentTypeEncoding;
    private final String contentTypeMime;
    private final String xmlEncoding;
    private final String xmlGuessEncoding;
}
