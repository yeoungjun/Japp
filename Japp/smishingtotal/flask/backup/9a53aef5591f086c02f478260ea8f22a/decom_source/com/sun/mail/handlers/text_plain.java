// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.handlers;

import java.io.*;
import javax.activation.*;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import myjava.awt.datatransfer.DataFlavor;

public class text_plain
    implements DataContentHandler
{

    public text_plain()
    {
    }

    private String getCharset(String s)
    {
        String s1;
        String s2;
        try
        {
            s1 = (new ContentType(s)).getParameter("charset");
        }
        catch(Exception exception)
        {
            return null;
        }
        if(s1 == null)
            s1 = "us-ascii";
        s2 = MimeUtility.javaCharset(s1);
        return s2;
    }

    public Object getContent(DataSource datasource)
        throws IOException
    {
        InputStreamReader inputstreamreader;
        int i;
        char ac[];
        int j;
        String s1;
        String s = null;
        try
        {
            s = getCharset(datasource.getContentType());
            inputstreamreader = new InputStreamReader(datasource.getInputStream(), s);
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            throw new UnsupportedEncodingException(s);
        }
        i = 0;
        ac = new char[1024];
_L2:
        j = inputstreamreader.read(ac, i, ac.length - i);
        if(j != -1)
            break MISSING_BLOCK_LABEL_94;
        s1 = new String(ac, 0, i);
        Exception exception;
        int k;
        int l;
        char ac1[];
        try
        {
            inputstreamreader.close();
        }
        catch(IOException ioexception1)
        {
            return s1;
        }
        return s1;
        i += j;
        if(i < ac.length) goto _L2; else goto _L1
_L1:
        k = ac.length;
        if(k < 0x40000)
            l = k + k;
        else
            l = k + 0x40000;
        ac1 = new char[l];
        System.arraycopy(ac, 0, ac1, 0, i);
        ac = ac1;
          goto _L2
        exception;
        try
        {
            inputstreamreader.close();
        }
        catch(IOException ioexception) { }
        throw exception;
    }

    protected ActivationDataFlavor getDF()
    {
        return myDF;
    }

    public Object getTransferData(DataFlavor dataflavor, DataSource datasource)
        throws IOException
    {
        if(getDF().equals(dataflavor))
            return getContent(datasource);
        else
            return null;
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        DataFlavor adataflavor[] = new DataFlavor[1];
        adataflavor[0] = getDF();
        return adataflavor;
    }

    public void writeTo(Object obj, String s, OutputStream outputstream)
        throws IOException
    {
        if(!(obj instanceof String))
            throw new IOException((new StringBuilder("\"")).append(getDF().getMimeType()).append("\" DataContentHandler requires String object, ").append("was given object of type ").append(obj.getClass().toString()).toString());
        String s1 = null;
        OutputStreamWriter outputstreamwriter;
        String s2;
        try
        {
            s1 = getCharset(s);
            outputstreamwriter = new OutputStreamWriter(outputstream, s1);
        }
        catch(IllegalArgumentException illegalargumentexception)
        {
            throw new UnsupportedEncodingException(s1);
        }
        s2 = (String)obj;
        outputstreamwriter.write(s2, 0, s2.length());
        outputstreamwriter.flush();
    }

    private static ActivationDataFlavor myDF = new ActivationDataFlavor(java/lang/String, "text/plain", "Text String");

}
