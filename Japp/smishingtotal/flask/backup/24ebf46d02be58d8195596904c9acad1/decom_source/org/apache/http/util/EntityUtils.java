// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package org.apache.http.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.http.*;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;

// Referenced classes of package org.apache.http.util:
//            Args, ByteArrayBuffer, CharArrayBuffer

public final class EntityUtils
{

    private EntityUtils()
    {
    }

    public static void consume(HttpEntity httpentity)
        throws IOException
    {
        if(httpentity != null && httpentity.isStreaming())
        {
            InputStream inputstream = httpentity.getContent();
            if(inputstream != null)
            {
                inputstream.close();
                return;
            }
        }
    }

    public static void consumeQuietly(HttpEntity httpentity)
    {
        try
        {
            consume(httpentity);
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public static String getContentCharSet(HttpEntity httpentity)
        throws ParseException
    {
        Args.notNull(httpentity, "Entity");
        Header header = httpentity.getContentType();
        String s = null;
        if(header != null)
        {
            HeaderElement aheaderelement[] = httpentity.getContentType().getElements();
            int i = aheaderelement.length;
            s = null;
            if(i > 0)
            {
                NameValuePair namevaluepair = aheaderelement[0].getParameterByName("charset");
                s = null;
                if(namevaluepair != null)
                    s = namevaluepair.getValue();
            }
        }
        return s;
    }

    public static String getContentMimeType(HttpEntity httpentity)
        throws ParseException
    {
        Args.notNull(httpentity, "Entity");
        Header header = httpentity.getContentType();
        String s = null;
        if(header != null)
        {
            HeaderElement aheaderelement[] = httpentity.getContentType().getElements();
            int i = aheaderelement.length;
            s = null;
            if(i > 0)
                s = aheaderelement[0].getName();
        }
        return s;
    }

    public static byte[] toByteArray(HttpEntity httpentity)
        throws IOException
    {
        InputStream inputstream;
        Args.notNull(httpentity, "Entity");
        inputstream = httpentity.getContent();
        if(inputstream == null)
            return null;
        int i = httpentity.getContentLength() != 0x7fffffffL;
        boolean flag;
        flag = false;
        if(i <= 0)
            flag = true;
        int j;
        Args.check(flag, "HTTP entity too large to be buffered in memory");
        j = (int)httpentity.getContentLength();
        if(j < 0)
            j = 4096;
        ByteArrayBuffer bytearraybuffer;
        byte abyte0[];
        bytearraybuffer = new ByteArrayBuffer(j);
        abyte0 = new byte[4096];
_L1:
        int k = inputstream.read(abyte0);
        if(k == -1)
            break MISSING_BLOCK_LABEL_121;
        bytearraybuffer.append(abyte0, 0, k);
          goto _L1
        Exception exception;
        exception;
        inputstream.close();
        throw exception;
        byte abyte1[] = bytearraybuffer.toByteArray();
        inputstream.close();
        return abyte1;
    }

    public static String toString(HttpEntity httpentity)
        throws IOException, ParseException
    {
        return toString(httpentity, (Charset)null);
    }

    public static String toString(HttpEntity httpentity, String s)
        throws IOException, ParseException
    {
        Charset charset;
        if(s != null)
            charset = Charset.forName(s);
        else
            charset = null;
        return toString(httpentity, charset);
    }

    public static String toString(HttpEntity httpentity, Charset charset)
        throws IOException, ParseException
    {
        InputStream inputstream;
        Args.notNull(httpentity, "Entity");
        inputstream = httpentity.getContent();
        if(inputstream == null)
            return null;
        int i = httpentity.getContentLength() != 0x7fffffffL;
        boolean flag;
        flag = false;
        if(i <= 0)
            flag = true;
        long l;
        Args.check(flag, "HTTP entity too large to be buffered in memory");
        l = httpentity.getContentLength();
        int j;
        j = (int)l;
        if(j < 0)
            j = 4096;
        ContentType contenttype = ContentType.get(httpentity);
        Charset charset1;
        charset1 = null;
        if(contenttype == null)
            break MISSING_BLOCK_LABEL_98;
        Charset charset2 = contenttype.getCharset();
        charset1 = charset2;
        if(charset1 == null)
            charset1 = charset;
        if(charset1 != null)
            break MISSING_BLOCK_LABEL_116;
        charset1 = HTTP.DEF_CONTENT_CHARSET;
        InputStreamReader inputstreamreader;
        CharArrayBuffer chararraybuffer;
        char ac[];
        inputstreamreader = new InputStreamReader(inputstream, charset1);
        chararraybuffer = new CharArrayBuffer(j);
        ac = new char[1024];
_L1:
        int k = inputstreamreader.read(ac);
        if(k == -1)
            break MISSING_BLOCK_LABEL_198;
        chararraybuffer.append(ac, 0, k);
          goto _L1
        Exception exception;
        exception;
        inputstream.close();
        throw exception;
        UnsupportedCharsetException unsupportedcharsetexception;
        unsupportedcharsetexception;
        throw new UnsupportedEncodingException(unsupportedcharsetexception.getMessage());
        String s = chararraybuffer.toString();
        inputstream.close();
        return s;
    }

    public static void updateEntity(HttpResponse httpresponse, HttpEntity httpentity)
        throws IOException
    {
        Args.notNull(httpresponse, "Response");
        consume(httpresponse.getEntity());
        httpresponse.setEntity(httpentity);
    }
}
