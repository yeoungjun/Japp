// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import myjava.awt.datatransfer.DataFlavor;

public class message_rfc822
    implements DataContentHandler
{

    public message_rfc822()
    {
        ourDataFlavor = new ActivationDataFlavor(javax/mail/Message, "message/rfc822", "Message");
    }

    public Object getContent(DataSource datasource)
        throws IOException
    {
        if(!(datasource instanceof MessageAware)) goto _L2; else goto _L1
_L1:
        Session session1 = ((MessageAware)datasource).getMessageContext().getSession();
_L3:
        return new MimeMessage(session1, datasource.getInputStream());
_L2:
        Session session = Session.getDefaultInstance(new Properties(), null);
        session1 = session;
          goto _L3
        MessagingException messagingexception;
        messagingexception;
        throw new IOException((new StringBuilder("Exception creating MimeMessage in message/rfc822 DataContentHandler: ")).append(messagingexception.toString()).toString());
    }

    public Object getTransferData(DataFlavor dataflavor, DataSource datasource)
        throws IOException
    {
        if(ourDataFlavor.equals(dataflavor))
            return getContent(datasource);
        else
            return null;
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        DataFlavor adataflavor[] = new DataFlavor[1];
        adataflavor[0] = ourDataFlavor;
        return adataflavor;
    }

    public void writeTo(Object obj, String s, OutputStream outputstream)
        throws IOException
    {
        if(obj instanceof Message)
        {
            Message message = (Message)obj;
            try
            {
                message.writeTo(outputstream);
                return;
            }
            catch(MessagingException messagingexception)
            {
                throw new IOException(messagingexception.toString());
            }
        } else
        {
            throw new IOException("unsupported object");
        }
    }

    ActivationDataFlavor ourDataFlavor;
}
