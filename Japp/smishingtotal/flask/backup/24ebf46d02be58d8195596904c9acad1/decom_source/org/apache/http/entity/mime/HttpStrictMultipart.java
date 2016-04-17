// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.entity.mime;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package org.apache.http.entity.mime:
//            AbstractMultipartForm, FormBodyPart, Header, MinimalField

class HttpStrictMultipart extends AbstractMultipartForm
{

    public HttpStrictMultipart(String s, Charset charset, String s1, List list)
    {
        super(s, charset, s1);
        parts = list;
    }

    protected void formatMultipartHeader(FormBodyPart formbodypart, OutputStream outputstream)
        throws IOException
    {
        for(Iterator iterator = formbodypart.getHeader().iterator(); iterator.hasNext(); writeField((MinimalField)iterator.next(), outputstream));
    }

    public List getBodyParts()
    {
        return parts;
    }

    private final List parts;
}
