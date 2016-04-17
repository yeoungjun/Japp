// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import java.util.Enumeration;
import javax.mail.MessagingException;
import javax.mail.Part;

public interface MimePart
    extends Part
{

    public abstract void addHeaderLine(String s)
        throws MessagingException;

    public abstract Enumeration getAllHeaderLines()
        throws MessagingException;

    public abstract String getContentID()
        throws MessagingException;

    public abstract String[] getContentLanguage()
        throws MessagingException;

    public abstract String getContentMD5()
        throws MessagingException;

    public abstract String getEncoding()
        throws MessagingException;

    public abstract String getHeader(String s, String s1)
        throws MessagingException;

    public abstract Enumeration getMatchingHeaderLines(String as[])
        throws MessagingException;

    public abstract Enumeration getNonMatchingHeaderLines(String as[])
        throws MessagingException;

    public abstract void setContentLanguage(String as[])
        throws MessagingException;

    public abstract void setContentMD5(String s)
        throws MessagingException;

    public abstract void setText(String s)
        throws MessagingException;

    public abstract void setText(String s, String s1)
        throws MessagingException;

    public abstract void setText(String s, String s1, String s2)
        throws MessagingException;
}
