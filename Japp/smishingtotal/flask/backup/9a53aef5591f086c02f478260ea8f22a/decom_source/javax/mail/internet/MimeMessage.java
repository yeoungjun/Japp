// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.util.SharedByteArrayInputStream;

// Referenced classes of package javax.mail.internet:
//            MimePart, MailDateFormat, InternetHeaders, InternetAddress, 
//            NewsAddress, MimeBodyPart, SharedInputStream, MimePartDataSource, 
//            MimeUtility, UniqueValue

public class MimeMessage extends Message
    implements MimePart
{
    public static class RecipientType extends javax.mail.Message.RecipientType
    {

        protected Object readResolve()
            throws ObjectStreamException
        {
            if(type.equals("Newsgroups"))
                return NEWSGROUPS;
            else
                return super.readResolve();
        }

        public static final RecipientType NEWSGROUPS = new RecipientType("Newsgroups");
        private static final long serialVersionUID = 0xb41cba943bbdee69L;


        protected RecipientType(String s)
        {
            super(s);
        }
    }


    protected MimeMessage(Folder folder, int i)
    {
        super(folder, i);
        modified = false;
        saved = false;
        strict = true;
        flags = new Flags();
        saved = true;
        initStrict();
    }

    protected MimeMessage(Folder folder, InputStream inputstream, int i)
        throws MessagingException
    {
        this(folder, i);
        initStrict();
        parse(inputstream);
    }

    protected MimeMessage(Folder folder, InternetHeaders internetheaders, byte abyte0[], int i)
        throws MessagingException
    {
        this(folder, i);
        headers = internetheaders;
        content = abyte0;
        initStrict();
    }

    public MimeMessage(Session session)
    {
        super(session);
        modified = false;
        saved = false;
        strict = true;
        modified = true;
        headers = new InternetHeaders();
        flags = new Flags();
        initStrict();
    }

    public MimeMessage(Session session, InputStream inputstream)
        throws MessagingException
    {
        super(session);
        modified = false;
        saved = false;
        strict = true;
        flags = new Flags();
        initStrict();
        parse(inputstream);
        saved = true;
    }

    public MimeMessage(MimeMessage mimemessage)
        throws MessagingException
    {
        super(mimemessage.session);
        modified = false;
        saved = false;
        strict = true;
        flags = mimemessage.getFlags();
        int i = mimemessage.getSize();
        ByteArrayOutputStream bytearrayoutputstream;
        if(i > 0)
            bytearrayoutputstream = new ByteArrayOutputStream(i);
        else
            bytearrayoutputstream = new ByteArrayOutputStream();
        try
        {
            strict = mimemessage.strict;
            mimemessage.writeTo(bytearrayoutputstream);
            bytearrayoutputstream.close();
            SharedByteArrayInputStream sharedbytearrayinputstream = new SharedByteArrayInputStream(bytearrayoutputstream.toByteArray());
            parse(sharedbytearrayinputstream);
            sharedbytearrayinputstream.close();
            saved = true;
            return;
        }
        catch(IOException ioexception)
        {
            throw new MessagingException("IOException while copying message", ioexception);
        }
    }

    private void addAddressHeader(String s, Address aaddress[])
        throws MessagingException
    {
        String s1 = InternetAddress.toString(aaddress);
        if(s1 == null)
        {
            return;
        } else
        {
            addHeader(s, s1);
            return;
        }
    }

    private Address[] eliminateDuplicates(Vector vector, Address aaddress[])
    {
        int i;
        int j;
        if(aaddress == null)
            return null;
        i = 0;
        j = 0;
_L5:
        if(j < aaddress.length) goto _L2; else goto _L1
_L1:
        if(i == 0) goto _L4; else goto _L3
_L3:
        Object aobj[];
        int i1;
        int j1;
        int k;
        int l;
        boolean flag;
        if(aaddress instanceof InternetAddress[])
            aobj = new InternetAddress[aaddress.length - i];
        else
            aobj = new Address[aaddress.length - i];
        i1 = 0;
        j1 = 0;
_L7:
        if(i1 < aaddress.length)
            break MISSING_BLOCK_LABEL_145;
        aaddress = ((Address []) (aobj));
_L4:
        return aaddress;
_L2:
        k = 0;
_L6:
        l = vector.size();
        flag = false;
        if(k < l)
        {
label0:
            {
                if(!((InternetAddress)vector.elementAt(k)).equals(aaddress[j]))
                    break label0;
                flag = true;
                i++;
                aaddress[j] = null;
            }
        }
        if(!flag)
            vector.addElement(aaddress[j]);
        j++;
          goto _L5
        k++;
          goto _L6
        if(aaddress[i1] != null)
        {
            int k1 = j1 + 1;
            aobj[j1] = aaddress[i1];
            j1 = k1;
        }
        i1++;
          goto _L7
    }

    private Address[] getAddressHeader(String s)
        throws MessagingException
    {
        String s1 = getHeader(s, ",");
        if(s1 == null)
            return null;
        else
            return InternetAddress.parseHeader(s1, strict);
    }

    private String getHeaderName(javax.mail.Message.RecipientType recipienttype)
        throws MessagingException
    {
        if(recipienttype == javax.mail.Message.RecipientType.TO)
            return "To";
        if(recipienttype == javax.mail.Message.RecipientType.CC)
            return "Cc";
        if(recipienttype == javax.mail.Message.RecipientType.BCC)
            return "Bcc";
        if(recipienttype == RecipientType.NEWSGROUPS)
            return "Newsgroups";
        else
            throw new MessagingException("Invalid Recipient Type");
    }

    private void initStrict()
    {
        if(session != null)
        {
            String s = session.getProperty("mail.mime.address.strict");
            boolean flag;
            if(s != null && s.equalsIgnoreCase("false"))
                flag = false;
            else
                flag = true;
            strict = flag;
        }
    }

    private void setAddressHeader(String s, Address aaddress[])
        throws MessagingException
    {
        String s1 = InternetAddress.toString(aaddress);
        if(s1 == null)
        {
            removeHeader(s);
            return;
        } else
        {
            setHeader(s, s1);
            return;
        }
    }

    public void addFrom(Address aaddress[])
        throws MessagingException
    {
        addAddressHeader("From", aaddress);
    }

    public void addHeader(String s, String s1)
        throws MessagingException
    {
        headers.addHeader(s, s1);
    }

    public void addHeaderLine(String s)
        throws MessagingException
    {
        headers.addHeaderLine(s);
    }

    public void addRecipients(javax.mail.Message.RecipientType recipienttype, String s)
        throws MessagingException
    {
        if(recipienttype == RecipientType.NEWSGROUPS)
        {
            if(s != null && s.length() != 0)
                addHeader("Newsgroups", s);
            return;
        } else
        {
            addAddressHeader(getHeaderName(recipienttype), InternetAddress.parse(s));
            return;
        }
    }

    public void addRecipients(javax.mail.Message.RecipientType recipienttype, Address aaddress[])
        throws MessagingException
    {
        if(recipienttype == RecipientType.NEWSGROUPS)
        {
            String s = NewsAddress.toString(aaddress);
            if(s != null)
                addHeader("Newsgroups", s);
            return;
        } else
        {
            addAddressHeader(getHeaderName(recipienttype), aaddress);
            return;
        }
    }

    protected InternetHeaders createInternetHeaders(InputStream inputstream)
        throws MessagingException
    {
        return new InternetHeaders(inputstream);
    }

    protected MimeMessage createMimeMessage(Session session)
        throws MessagingException
    {
        return new MimeMessage(session);
    }

    public Enumeration getAllHeaderLines()
        throws MessagingException
    {
        return headers.getAllHeaderLines();
    }

    public Enumeration getAllHeaders()
        throws MessagingException
    {
        return headers.getAllHeaders();
    }

    public Address[] getAllRecipients()
        throws MessagingException
    {
        Address aaddress[] = super.getAllRecipients();
        Address aaddress1[] = getRecipients(RecipientType.NEWSGROUPS);
        if(aaddress1 == null)
            return aaddress;
        if(aaddress == null)
        {
            return aaddress1;
        } else
        {
            Address aaddress2[] = new Address[aaddress.length + aaddress1.length];
            System.arraycopy(aaddress, 0, aaddress2, 0, aaddress.length);
            System.arraycopy(aaddress1, 0, aaddress2, aaddress.length, aaddress1.length);
            return aaddress2;
        }
    }

    public Object getContent()
        throws IOException, MessagingException
    {
        Object obj1;
        if(cachedContent != null)
        {
            obj1 = cachedContent;
        } else
        {
            Object obj;
            try
            {
                obj = getDataHandler().getContent();
            }
            catch(FolderClosedIOException folderclosedioexception)
            {
                throw new FolderClosedException(folderclosedioexception.getFolder(), folderclosedioexception.getMessage());
            }
            catch(MessageRemovedIOException messageremovedioexception)
            {
                throw new MessageRemovedException(messageremovedioexception.getMessage());
            }
            obj1 = obj;
            if(MimeBodyPart.cacheMultipart && ((obj1 instanceof Multipart) || (obj1 instanceof Message)) && (content != null || contentStream != null))
            {
                cachedContent = obj1;
                return obj1;
            }
        }
        return obj1;
    }

    public String getContentID()
        throws MessagingException
    {
        return getHeader("Content-Id", null);
    }

    public String[] getContentLanguage()
        throws MessagingException
    {
        return MimeBodyPart.getContentLanguage(this);
    }

    public String getContentMD5()
        throws MessagingException
    {
        return getHeader("Content-MD5", null);
    }

    protected InputStream getContentStream()
        throws MessagingException
    {
        if(contentStream != null)
            return ((SharedInputStream)contentStream).newStream(0L, -1L);
        if(content != null)
            return new SharedByteArrayInputStream(content);
        else
            throw new MessagingException("No content");
    }

    public String getContentType()
        throws MessagingException
    {
        String s = getHeader("Content-Type", null);
        if(s == null)
            s = "text/plain";
        return s;
    }

    public DataHandler getDataHandler()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        DataHandler datahandler;
        if(dh == null)
            dh = new DataHandler(new MimePartDataSource(this));
        datahandler = dh;
        this;
        JVM INSTR monitorexit ;
        return datahandler;
        Exception exception;
        exception;
        throw exception;
    }

    public String getDescription()
        throws MessagingException
    {
        return MimeBodyPart.getDescription(this);
    }

    public String getDisposition()
        throws MessagingException
    {
        return MimeBodyPart.getDisposition(this);
    }

    public String getEncoding()
        throws MessagingException
    {
        return MimeBodyPart.getEncoding(this);
    }

    public String getFileName()
        throws MessagingException
    {
        return MimeBodyPart.getFileName(this);
    }

    public Flags getFlags()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Flags flags1 = (Flags)flags.clone();
        this;
        JVM INSTR monitorexit ;
        return flags1;
        Exception exception;
        exception;
        throw exception;
    }

    public Address[] getFrom()
        throws MessagingException
    {
        Address aaddress[] = getAddressHeader("From");
        if(aaddress == null)
            aaddress = getAddressHeader("Sender");
        return aaddress;
    }

    public String getHeader(String s, String s1)
        throws MessagingException
    {
        return headers.getHeader(s, s1);
    }

    public String[] getHeader(String s)
        throws MessagingException
    {
        return headers.getHeader(s);
    }

    public InputStream getInputStream()
        throws IOException, MessagingException
    {
        return getDataHandler().getInputStream();
    }

    public int getLineCount()
        throws MessagingException
    {
        return -1;
    }

    public Enumeration getMatchingHeaderLines(String as[])
        throws MessagingException
    {
        return headers.getMatchingHeaderLines(as);
    }

    public Enumeration getMatchingHeaders(String as[])
        throws MessagingException
    {
        return headers.getMatchingHeaders(as);
    }

    public String getMessageID()
        throws MessagingException
    {
        return getHeader("Message-ID", null);
    }

    public Enumeration getNonMatchingHeaderLines(String as[])
        throws MessagingException
    {
        return headers.getNonMatchingHeaderLines(as);
    }

    public Enumeration getNonMatchingHeaders(String as[])
        throws MessagingException
    {
        return headers.getNonMatchingHeaders(as);
    }

    public InputStream getRawInputStream()
        throws MessagingException
    {
        return getContentStream();
    }

    public Date getReceivedDate()
        throws MessagingException
    {
        return null;
    }

    public Address[] getRecipients(javax.mail.Message.RecipientType recipienttype)
        throws MessagingException
    {
        if(recipienttype == RecipientType.NEWSGROUPS)
        {
            String s = getHeader("Newsgroups", ",");
            if(s == null)
                return null;
            else
                return NewsAddress.parse(s);
        } else
        {
            return getAddressHeader(getHeaderName(recipienttype));
        }
    }

    public Address[] getReplyTo()
        throws MessagingException
    {
        Address aaddress[] = getAddressHeader("Reply-To");
        if(aaddress == null)
            aaddress = getFrom();
        return aaddress;
    }

    public Address getSender()
        throws MessagingException
    {
        Address aaddress[] = getAddressHeader("Sender");
        if(aaddress == null || aaddress.length == 0)
            return null;
        else
            return aaddress[0];
    }

    public Date getSentDate()
        throws MessagingException
    {
        String s;
        s = getHeader("Date", null);
        if(s == null)
            break MISSING_BLOCK_LABEL_43;
        Date date;
        synchronized(mailDateFormat)
        {
            date = mailDateFormat.parse(s);
        }
        return date;
        exception;
        maildateformat;
        JVM INSTR monitorexit ;
        try
        {
            throw exception;
        }
        catch(ParseException parseexception)
        {
            return null;
        }
        return null;
    }

    public int getSize()
        throws MessagingException
    {
        if(content == null) goto _L2; else goto _L1
_L1:
        int j = content.length;
_L4:
        return j;
_L2:
        if(contentStream == null)
            break; /* Loop/switch isn't completed */
        int i = contentStream.available();
        j = i;
        if(j > 0) goto _L4; else goto _L3
_L3:
        return -1;
        IOException ioexception;
        ioexception;
        if(true) goto _L3; else goto _L5
_L5:
    }

    public String getSubject()
        throws MessagingException
    {
        String s = getHeader("Subject", null);
        if(s == null)
            return null;
        String s1;
        try
        {
            s1 = MimeUtility.decodeText(MimeUtility.unfold(s));
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            return s;
        }
        return s1;
    }

    public boolean isMimeType(String s)
        throws MessagingException
    {
        return MimeBodyPart.isMimeType(this, s);
    }

    public boolean isSet(javax.mail.Flags.Flag flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag1 = flags.contains(flag);
        this;
        JVM INSTR monitorexit ;
        return flag1;
        Exception exception;
        exception;
        throw exception;
    }

    protected void parse(InputStream inputstream)
        throws MessagingException
    {
        if(!(inputstream instanceof ByteArrayInputStream) && !(inputstream instanceof BufferedInputStream) && !(inputstream instanceof SharedInputStream))
            inputstream = new BufferedInputStream(inputstream);
        headers = createInternetHeaders(inputstream);
        if(inputstream instanceof SharedInputStream)
        {
            SharedInputStream sharedinputstream = (SharedInputStream)inputstream;
            contentStream = sharedinputstream.newStream(sharedinputstream.getPosition(), -1L);
        } else
        {
            try
            {
                content = ASCIIUtility.getBytes(inputstream);
            }
            catch(IOException ioexception)
            {
                throw new MessagingException("IOException", ioexception);
            }
        }
        modified = false;
    }

    public void removeHeader(String s)
        throws MessagingException
    {
        headers.removeHeader(s);
    }

    public Message reply(boolean flag)
        throws MessagingException
    {
        MimeMessage mimemessage = createMimeMessage(this.session);
        String s = getHeader("Subject", null);
        if(s != null)
        {
            if(!s.regionMatches(true, 0, "Re: ", 0, 4))
                s = (new StringBuilder("Re: ")).append(s).toString();
            mimemessage.setHeader("Subject", s);
        }
        Address aaddress[] = getReplyTo();
        mimemessage.setRecipients(javax.mail.Message.RecipientType.TO, aaddress);
        String s3;
        String s4;
        if(flag)
        {
            Vector vector = new Vector();
            InternetAddress internetaddress = InternetAddress.getLocalAddress(this.session);
            if(internetaddress != null)
                vector.addElement(internetaddress);
            Session session = this.session;
            String s1 = null;
            if(session != null)
                s1 = this.session.getProperty("mail.alternates");
            if(s1 != null)
                eliminateDuplicates(vector, InternetAddress.parse(s1, false));
            Session session1 = this.session;
            String s2 = null;
            if(session1 != null)
                s2 = this.session.getProperty("mail.replyallcc");
            boolean flag1;
            Address aaddress1[];
            Address aaddress2[];
            Address aaddress3[];
            if(s2 != null && s2.equalsIgnoreCase("true"))
                flag1 = true;
            else
                flag1 = false;
            eliminateDuplicates(vector, aaddress);
            aaddress1 = eliminateDuplicates(vector, getRecipients(javax.mail.Message.RecipientType.TO));
            if(aaddress1 != null && aaddress1.length > 0)
                if(flag1)
                    mimemessage.addRecipients(javax.mail.Message.RecipientType.CC, aaddress1);
                else
                    mimemessage.addRecipients(javax.mail.Message.RecipientType.TO, aaddress1);
            aaddress2 = eliminateDuplicates(vector, getRecipients(javax.mail.Message.RecipientType.CC));
            if(aaddress2 != null && aaddress2.length > 0)
                mimemessage.addRecipients(javax.mail.Message.RecipientType.CC, aaddress2);
            aaddress3 = getRecipients(RecipientType.NEWSGROUPS);
            if(aaddress3 != null && aaddress3.length > 0)
                mimemessage.setRecipients(RecipientType.NEWSGROUPS, aaddress3);
        }
        s3 = getHeader("Message-Id", null);
        if(s3 != null)
            mimemessage.setHeader("In-Reply-To", s3);
        s4 = getHeader("References", " ");
        if(s4 == null)
            s4 = getHeader("In-Reply-To", " ");
        if(s3 != null)
            if(s4 != null)
                s4 = (new StringBuilder(String.valueOf(MimeUtility.unfold(s4)))).append(" ").append(s3).toString();
            else
                s4 = s3;
        if(s4 != null)
            mimemessage.setHeader("References", MimeUtility.fold(12, s4));
        try
        {
            setFlags(answeredFlag, true);
        }
        catch(MessagingException messagingexception)
        {
            return mimemessage;
        }
        return mimemessage;
    }

    public void saveChanges()
        throws MessagingException
    {
        modified = true;
        saved = true;
        updateHeaders();
    }

    public void setContent(Object obj, String s)
        throws MessagingException
    {
        if(obj instanceof Multipart)
        {
            setContent((Multipart)obj);
            return;
        } else
        {
            setDataHandler(new DataHandler(obj, s));
            return;
        }
    }

    public void setContent(Multipart multipart)
        throws MessagingException
    {
        setDataHandler(new DataHandler(multipart, multipart.getContentType()));
        multipart.setParent(this);
    }

    public void setContentID(String s)
        throws MessagingException
    {
        if(s == null)
        {
            removeHeader("Content-ID");
            return;
        } else
        {
            setHeader("Content-ID", s);
            return;
        }
    }

    public void setContentLanguage(String as[])
        throws MessagingException
    {
        MimeBodyPart.setContentLanguage(this, as);
    }

    public void setContentMD5(String s)
        throws MessagingException
    {
        setHeader("Content-MD5", s);
    }

    public void setDataHandler(DataHandler datahandler)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        dh = datahandler;
        cachedContent = null;
        MimeBodyPart.invalidateContentHeaders(this);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setDescription(String s)
        throws MessagingException
    {
        setDescription(s, null);
    }

    public void setDescription(String s, String s1)
        throws MessagingException
    {
        MimeBodyPart.setDescription(this, s, s1);
    }

    public void setDisposition(String s)
        throws MessagingException
    {
        MimeBodyPart.setDisposition(this, s);
    }

    public void setFileName(String s)
        throws MessagingException
    {
        MimeBodyPart.setFileName(this, s);
    }

    public void setFlags(Flags flags1, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(!flag) goto _L2; else goto _L1
_L1:
        flags.add(flags1);
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        flags.remove(flags1);
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public void setFrom()
        throws MessagingException
    {
        InternetAddress internetaddress = InternetAddress.getLocalAddress(session);
        if(internetaddress != null)
        {
            setFrom(((Address) (internetaddress)));
            return;
        } else
        {
            throw new MessagingException("No From address");
        }
    }

    public void setFrom(Address address)
        throws MessagingException
    {
        if(address == null)
        {
            removeHeader("From");
            return;
        } else
        {
            setHeader("From", address.toString());
            return;
        }
    }

    public void setHeader(String s, String s1)
        throws MessagingException
    {
        headers.setHeader(s, s1);
    }

    public void setRecipients(javax.mail.Message.RecipientType recipienttype, String s)
        throws MessagingException
    {
        if(recipienttype == RecipientType.NEWSGROUPS)
        {
            if(s == null || s.length() == 0)
            {
                removeHeader("Newsgroups");
                return;
            } else
            {
                setHeader("Newsgroups", s);
                return;
            }
        } else
        {
            setAddressHeader(getHeaderName(recipienttype), InternetAddress.parse(s));
            return;
        }
    }

    public void setRecipients(javax.mail.Message.RecipientType recipienttype, Address aaddress[])
        throws MessagingException
    {
        if(recipienttype == RecipientType.NEWSGROUPS)
        {
            if(aaddress == null || aaddress.length == 0)
            {
                removeHeader("Newsgroups");
                return;
            } else
            {
                setHeader("Newsgroups", NewsAddress.toString(aaddress));
                return;
            }
        } else
        {
            setAddressHeader(getHeaderName(recipienttype), aaddress);
            return;
        }
    }

    public void setReplyTo(Address aaddress[])
        throws MessagingException
    {
        setAddressHeader("Reply-To", aaddress);
    }

    public void setSender(Address address)
        throws MessagingException
    {
        if(address == null)
        {
            removeHeader("Sender");
            return;
        } else
        {
            setHeader("Sender", address.toString());
            return;
        }
    }

    public void setSentDate(Date date)
        throws MessagingException
    {
        if(date == null)
        {
            removeHeader("Date");
            return;
        }
        synchronized(mailDateFormat)
        {
            setHeader("Date", mailDateFormat.format(date));
        }
        return;
        exception;
        maildateformat;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public void setSubject(String s)
        throws MessagingException
    {
        setSubject(s, null);
    }

    public void setSubject(String s, String s1)
        throws MessagingException
    {
        if(s == null)
        {
            removeHeader("Subject");
            return;
        }
        try
        {
            setHeader("Subject", MimeUtility.fold(9, MimeUtility.encodeText(s, s1, null)));
            return;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new MessagingException("Encoding error", unsupportedencodingexception);
        }
    }

    public void setText(String s)
        throws MessagingException
    {
        setText(s, null);
    }

    public void setText(String s, String s1)
        throws MessagingException
    {
        MimeBodyPart.setText(this, s, s1, "plain");
    }

    public void setText(String s, String s1, String s2)
        throws MessagingException
    {
        MimeBodyPart.setText(this, s, s1, s2);
    }

    protected void updateHeaders()
        throws MessagingException
    {
        MimeBodyPart.updateHeaders(this);
        setHeader("MIME-Version", "1.0");
        updateMessageID();
        if(cachedContent != null)
        {
            dh = new DataHandler(cachedContent, getContentType());
            cachedContent = null;
            content = null;
            if(contentStream != null)
                try
                {
                    contentStream.close();
                }
                catch(IOException ioexception) { }
            contentStream = null;
        }
    }

    protected void updateMessageID()
        throws MessagingException
    {
        setHeader("Message-ID", (new StringBuilder("<")).append(UniqueValue.getUniqueMessageIDValue(session)).append(">").toString());
    }

    public void writeTo(OutputStream outputstream)
        throws IOException, MessagingException
    {
        writeTo(outputstream, null);
    }

    public void writeTo(OutputStream outputstream, String as[])
        throws IOException, MessagingException
    {
        Enumeration enumeration;
        LineOutputStream lineoutputstream;
        if(!saved)
            saveChanges();
        if(modified)
        {
            MimeBodyPart.writeTo(this, outputstream, as);
            return;
        }
        enumeration = getNonMatchingHeaderLines(as);
        lineoutputstream = new LineOutputStream(outputstream);
_L5:
        if(enumeration.hasMoreElements()) goto _L2; else goto _L1
_L1:
        InputStream inputstream;
        byte abyte0[];
        lineoutputstream.writeln();
        if(content != null)
            break MISSING_BLOCK_LABEL_133;
        inputstream = getContentStream();
        abyte0 = new byte[8192];
_L6:
        int i = inputstream.read(abyte0);
        if(i > 0) goto _L4; else goto _L3
_L3:
        inputstream.close();
        (byte[])null;
_L7:
        outputstream.flush();
        return;
_L2:
        lineoutputstream.writeln((String)enumeration.nextElement());
          goto _L5
_L4:
        outputstream.write(abyte0, 0, i);
          goto _L6
        outputstream.write(content);
          goto _L7
    }

    private static final Flags answeredFlag;
    private static MailDateFormat mailDateFormat = new MailDateFormat();
    Object cachedContent;
    protected byte content[];
    protected InputStream contentStream;
    protected DataHandler dh;
    protected Flags flags;
    protected InternetHeaders headers;
    protected boolean modified;
    protected boolean saved;
    private boolean strict;

    static 
    {
        answeredFlag = new Flags(javax.mail.Flags.Flag.ANSWERED);
    }
}
