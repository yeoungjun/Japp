// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.io.*;
import java.net.UnknownServiceException;
import javax.activation.DataSource;
import javax.mail.*;

// Referenced classes of package javax.mail.internet:
//            ParseException, MimePart, ContentType, MimeBodyPart, 
//            MimeUtility, MimeMessage

public class MimePartDataSource
    implements DataSource, MessageAware
{

    public MimePartDataSource(MimePart mimepart)
    {
        part = mimepart;
    }

    private static String restrictEncoding(String s, MimePart mimepart)
        throws MessagingException
    {
_L2:
        return s;
        if(!ignoreMultipartEncoding || s == null || s.equalsIgnoreCase("7bit") || s.equalsIgnoreCase("8bit") || s.equalsIgnoreCase("binary")) goto _L2; else goto _L1
_L1:
        String s1 = mimepart.getContentType();
        if(s1 == null) goto _L2; else goto _L3
_L3:
        boolean flag;
        try
        {
            ContentType contenttype = new ContentType(s1);
            if(contenttype.match("multipart/*"))
                break; /* Loop/switch isn't completed */
            flag = contenttype.match("message/*");
        }
        catch(ParseException parseexception)
        {
            return s;
        }
        if(!flag) goto _L2; else goto _L4
_L4:
        return null;
    }

    public String getContentType()
    {
        String s;
        try
        {
            s = part.getContentType();
        }
        catch(MessagingException messagingexception)
        {
            return "application/octet-stream";
        }
        return s;
    }

    public InputStream getInputStream()
        throws IOException
    {
        InputStream inputstream;
        if(!(part instanceof MimeBodyPart))
            break MISSING_BLOCK_LABEL_48;
        inputstream = ((MimeBodyPart)part).getContentStream();
_L1:
        String s = restrictEncoding(part.getEncoding(), part);
        if(s == null)
            break MISSING_BLOCK_LABEL_95;
        return MimeUtility.decode(inputstream, s);
        if(!(part instanceof MimeMessage))
            break MISSING_BLOCK_LABEL_72;
        inputstream = ((MimeMessage)part).getContentStream();
          goto _L1
        try
        {
            throw new MessagingException("Unknown part");
        }
        catch(MessagingException messagingexception)
        {
            throw new IOException(messagingexception.getMessage());
        }
        return inputstream;
    }

    public MessageContext getMessageContext()
    {
        this;
        JVM INSTR monitorenter ;
        MessageContext messagecontext;
        if(context == null)
            context = new MessageContext(part);
        messagecontext = context;
        this;
        JVM INSTR monitorexit ;
        return messagecontext;
        Exception exception;
        exception;
        throw exception;
    }

    public String getName()
    {
        String s;
        if(!(part instanceof MimeBodyPart))
            break MISSING_BLOCK_LABEL_24;
        s = ((MimeBodyPart)part).getFileName();
        return s;
        MessagingException messagingexception;
        messagingexception;
        return "";
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        throw new UnknownServiceException();
    }

    private static boolean ignoreMultipartEncoding;
    private MessageContext context;
    protected MimePart part;

    static 
    {
        boolean flag;
        flag = true;
        ignoreMultipartEncoding = flag;
        String s = System.getProperty("mail.mime.ignoremultipartencoding");
        if(s == null)
            break MISSING_BLOCK_LABEL_27;
        if(s.equalsIgnoreCase("false"))
            flag = false;
        try
        {
            ignoreMultipartEncoding = flag;
        }
        catch(SecurityException securityexception) { }
    }
}
