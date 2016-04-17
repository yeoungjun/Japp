// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.*;
import java.util.Enumeration;
import javax.activation.DataHandler;

// Referenced classes of package javax.mail:
//            MessagingException, Multipart

public interface Part
{

    public abstract void addHeader(String s, String s1)
        throws MessagingException;

    public abstract Enumeration getAllHeaders()
        throws MessagingException;

    public abstract Object getContent()
        throws IOException, MessagingException;

    public abstract String getContentType()
        throws MessagingException;

    public abstract DataHandler getDataHandler()
        throws MessagingException;

    public abstract String getDescription()
        throws MessagingException;

    public abstract String getDisposition()
        throws MessagingException;

    public abstract String getFileName()
        throws MessagingException;

    public abstract String[] getHeader(String s)
        throws MessagingException;

    public abstract InputStream getInputStream()
        throws IOException, MessagingException;

    public abstract int getLineCount()
        throws MessagingException;

    public abstract Enumeration getMatchingHeaders(String as[])
        throws MessagingException;

    public abstract Enumeration getNonMatchingHeaders(String as[])
        throws MessagingException;

    public abstract int getSize()
        throws MessagingException;

    public abstract boolean isMimeType(String s)
        throws MessagingException;

    public abstract void removeHeader(String s)
        throws MessagingException;

    public abstract void setContent(Object obj, String s)
        throws MessagingException;

    public abstract void setContent(Multipart multipart)
        throws MessagingException;

    public abstract void setDataHandler(DataHandler datahandler)
        throws MessagingException;

    public abstract void setDescription(String s)
        throws MessagingException;

    public abstract void setDisposition(String s)
        throws MessagingException;

    public abstract void setFileName(String s)
        throws MessagingException;

    public abstract void setHeader(String s, String s1)
        throws MessagingException;

    public abstract void setText(String s)
        throws MessagingException;

    public abstract void writeTo(OutputStream outputstream)
        throws IOException, MessagingException;

    public static final String ATTACHMENT = "attachment";
    public static final String INLINE = "inline";
}
