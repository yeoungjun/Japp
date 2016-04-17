// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.util;

import java.io.*;
import javax.activation.DataSource;
import javax.mail.internet.*;

// Referenced classes of package javax.mail.util:
//            SharedByteArrayInputStream

public class ByteArrayDataSource
    implements DataSource
{
    static class DSByteArrayOutputStream extends ByteArrayOutputStream
    {

        public byte[] getBuf()
        {
            return buf;
        }

        public int getCount()
        {
            return count;
        }

        DSByteArrayOutputStream()
        {
        }
    }


    public ByteArrayDataSource(InputStream inputstream, String s)
        throws IOException
    {
        len = -1;
        name = "";
        DSByteArrayOutputStream dsbytearrayoutputstream = new DSByteArrayOutputStream();
        byte abyte0[] = new byte[8192];
        do
        {
            int i = inputstream.read(abyte0);
            if(i <= 0)
            {
                data = dsbytearrayoutputstream.getBuf();
                len = dsbytearrayoutputstream.getCount();
                if(data.length - len > 0x40000)
                {
                    data = dsbytearrayoutputstream.toByteArray();
                    len = data.length;
                }
                type = s;
                return;
            }
            dsbytearrayoutputstream.write(abyte0, 0, i);
        } while(true);
    }

    public ByteArrayDataSource(String s, String s1)
        throws IOException
    {
        len = -1;
        name = "";
        String s3 = (new ContentType(s1)).getParameter("charset");
        String s2 = s3;
_L2:
        if(s2 == null)
            s2 = MimeUtility.getDefaultJavaCharset();
        data = s.getBytes(s2);
        type = s1;
        return;
        ParseException parseexception;
        parseexception;
        s2 = null;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public ByteArrayDataSource(byte abyte0[], String s)
    {
        len = -1;
        name = "";
        data = abyte0;
        type = s;
    }

    public String getContentType()
    {
        return type;
    }

    public InputStream getInputStream()
        throws IOException
    {
        if(data == null)
            throw new IOException("no data");
        if(len < 0)
            len = data.length;
        return new SharedByteArrayInputStream(data, 0, len);
    }

    public String getName()
    {
        return name;
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        throw new IOException("cannot do this");
    }

    public void setName(String s)
    {
        name = s;
    }

    private byte data[];
    private int len;
    private String name;
    private String type;
}
