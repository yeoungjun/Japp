// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import myjava.awt.datatransfer.DataFlavor;

public class multipart_mixed
    implements DataContentHandler
{

    public multipart_mixed()
    {
        myDF = new ActivationDataFlavor(javax/mail/internet/MimeMultipart, "multipart/mixed", "Multipart");
    }

    public Object getContent(DataSource datasource)
        throws IOException
    {
        MimeMultipart mimemultipart;
        try
        {
            mimemultipart = new MimeMultipart(datasource);
        }
        catch(MessagingException messagingexception)
        {
            IOException ioexception = new IOException("Exception while constructing MimeMultipart");
            ioexception.initCause(messagingexception);
            throw ioexception;
        }
        return mimemultipart;
    }

    public Object getTransferData(DataFlavor dataflavor, DataSource datasource)
        throws IOException
    {
        if(myDF.equals(dataflavor))
            return getContent(datasource);
        else
            return null;
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        DataFlavor adataflavor[] = new DataFlavor[1];
        adataflavor[0] = myDF;
        return adataflavor;
    }

    public void writeTo(Object obj, String s, OutputStream outputstream)
        throws IOException
    {
        if(!(obj instanceof MimeMultipart))
            break MISSING_BLOCK_LABEL_15;
        ((MimeMultipart)obj).writeTo(outputstream);
        return;
        MessagingException messagingexception;
        messagingexception;
        throw new IOException(messagingexception.toString());
    }

    private ActivationDataFlavor myDF;
}
