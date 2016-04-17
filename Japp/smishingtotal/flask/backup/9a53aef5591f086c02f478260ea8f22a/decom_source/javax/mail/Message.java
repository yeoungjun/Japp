// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail;

import java.io.*;
import java.util.Date;
import javax.mail.search.SearchTerm;

// Referenced classes of package javax.mail:
//            Part, Folder, Store, MessagingException, 
//            Address, Flags, MethodNotSupportedException, Session

public abstract class Message
    implements Part
{
    public static class RecipientType
        implements Serializable
    {

        protected Object readResolve()
            throws ObjectStreamException
        {
            if(type.equals("To"))
                return TO;
            if(type.equals("Cc"))
                return CC;
            if(type.equals("Bcc"))
                return BCC;
            else
                throw new InvalidObjectException((new StringBuilder("Attempt to resolve unknown RecipientType: ")).append(type).toString());
        }

        public String toString()
        {
            return type;
        }

        public static final RecipientType BCC = new RecipientType("Bcc");
        public static final RecipientType CC = new RecipientType("Cc");
        public static final RecipientType TO = new RecipientType("To");
        private static final long serialVersionUID = 0x983271084f364058L;
        protected String type;


        protected RecipientType(String s)
        {
            type = s;
        }
    }


    protected Message()
    {
        msgnum = 0;
        expunged = false;
        folder = null;
        session = null;
    }

    protected Message(Folder folder1, int i)
    {
        msgnum = 0;
        expunged = false;
        folder = null;
        session = null;
        folder = folder1;
        msgnum = i;
        session = folder1.store.session;
    }

    protected Message(Session session1)
    {
        msgnum = 0;
        expunged = false;
        folder = null;
        session = null;
        session = session1;
    }

    public abstract void addFrom(Address aaddress[])
        throws MessagingException;

    public void addRecipient(RecipientType recipienttype, Address address)
        throws MessagingException
    {
        addRecipients(recipienttype, new Address[] {
            address
        });
    }

    public abstract void addRecipients(RecipientType recipienttype, Address aaddress[])
        throws MessagingException;

    public Address[] getAllRecipients()
        throws MessagingException
    {
        Address aaddress[] = getRecipients(RecipientType.TO);
        Address aaddress1[] = getRecipients(RecipientType.CC);
        Address aaddress2[] = getRecipients(RecipientType.BCC);
        if(aaddress1 == null && aaddress2 == null)
            return aaddress;
        int i;
        int j;
        int k;
        int l;
        Address aaddress3[];
        int i1;
        if(aaddress != null)
            i = aaddress.length;
        else
            i = 0;
        if(aaddress1 != null)
            j = aaddress1.length;
        else
            j = 0;
        k = i + j;
        if(aaddress2 != null)
            l = aaddress2.length;
        else
            l = 0;
        aaddress3 = new Address[k + l];
        i1 = 0;
        if(aaddress != null)
        {
            System.arraycopy(aaddress, 0, aaddress3, 0, aaddress.length);
            i1 = 0 + aaddress.length;
        }
        if(aaddress1 != null)
        {
            System.arraycopy(aaddress1, 0, aaddress3, i1, aaddress1.length);
            i1 += aaddress1.length;
        }
        if(aaddress2 != null)
        {
            System.arraycopy(aaddress2, 0, aaddress3, i1, aaddress2.length);
            int _tmp = i1 + aaddress2.length;
        }
        return aaddress3;
    }

    public abstract Flags getFlags()
        throws MessagingException;

    public Folder getFolder()
    {
        return folder;
    }

    public abstract Address[] getFrom()
        throws MessagingException;

    public int getMessageNumber()
    {
        return msgnum;
    }

    public abstract Date getReceivedDate()
        throws MessagingException;

    public abstract Address[] getRecipients(RecipientType recipienttype)
        throws MessagingException;

    public Address[] getReplyTo()
        throws MessagingException
    {
        return getFrom();
    }

    public abstract Date getSentDate()
        throws MessagingException;

    public abstract String getSubject()
        throws MessagingException;

    public boolean isExpunged()
    {
        return expunged;
    }

    public boolean isSet(Flags.Flag flag)
        throws MessagingException
    {
        return getFlags().contains(flag);
    }

    public boolean match(SearchTerm searchterm)
        throws MessagingException
    {
        return searchterm.match(this);
    }

    public abstract Message reply(boolean flag)
        throws MessagingException;

    public abstract void saveChanges()
        throws MessagingException;

    protected void setExpunged(boolean flag)
    {
        expunged = flag;
    }

    public void setFlag(Flags.Flag flag, boolean flag1)
        throws MessagingException
    {
        setFlags(new Flags(flag), flag1);
    }

    public abstract void setFlags(Flags flags, boolean flag)
        throws MessagingException;

    public abstract void setFrom()
        throws MessagingException;

    public abstract void setFrom(Address address)
        throws MessagingException;

    protected void setMessageNumber(int i)
    {
        msgnum = i;
    }

    public void setRecipient(RecipientType recipienttype, Address address)
        throws MessagingException
    {
        setRecipients(recipienttype, new Address[] {
            address
        });
    }

    public abstract void setRecipients(RecipientType recipienttype, Address aaddress[])
        throws MessagingException;

    public void setReplyTo(Address aaddress[])
        throws MessagingException
    {
        throw new MethodNotSupportedException("setReplyTo not supported");
    }

    public abstract void setSentDate(Date date)
        throws MessagingException;

    public abstract void setSubject(String s)
        throws MessagingException;

    protected boolean expunged;
    protected Folder folder;
    protected int msgnum;
    protected Session session;
}
