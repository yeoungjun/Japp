// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.*;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.mail.imap.protocol.RFC822DATA;
import com.sun.mail.imap.protocol.RFC822SIZE;
import com.sun.mail.imap.protocol.UID;
import java.io.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPFolder, Utility, IMAPInputStream, IMAPMultipartDataSource, 
//            IMAPNestedMessage, IMAPStore

public class IMAPMessage extends MimeMessage
{
    class _cls1FetchProfileCondition
        implements Utility.Condition
    {

        public boolean test(IMAPMessage imapmessage)
        {
_L2:
            return true;
            if(needEnvelope && imapmessage._getEnvelope() == null || needFlags && imapmessage._getFlags() == null || needBodyStructure && imapmessage._getBodyStructure() == null || needUID && imapmessage.getUID() == -1L || needHeaders && !imapmessage.areHeadersLoaded() || needSize && imapmessage.size == -1) goto _L2; else goto _L1
_L1:
            int i = 0;
            do
            {
                if(i >= hdrs.length)
                    return false;
                if(!imapmessage.isHeaderLoaded(hdrs[i]))
                    continue;
                i++;
            } while(true);
            if(true) goto _L2; else goto _L3
_L3:
        }

        private String hdrs[];
        private boolean needBodyStructure;
        private boolean needEnvelope;
        private boolean needFlags;
        private boolean needHeaders;
        private boolean needSize;
        private boolean needUID;

        public _cls1FetchProfileCondition(FetchProfile fetchprofile)
        {
            needEnvelope = false;
            needFlags = false;
            needBodyStructure = false;
            needUID = false;
            needHeaders = false;
            needSize = false;
            hdrs = null;
            if(fetchprofile.contains(javax.mail.FetchProfile.Item.ENVELOPE))
                needEnvelope = true;
            if(fetchprofile.contains(javax.mail.FetchProfile.Item.FLAGS))
                needFlags = true;
            if(fetchprofile.contains(javax.mail.FetchProfile.Item.CONTENT_INFO))
                needBodyStructure = true;
            if(fetchprofile.contains(javax.mail.UIDFolder.FetchProfileItem.UID))
                needUID = true;
            if(fetchprofile.contains(IMAPFolder.FetchProfileItem.HEADERS))
                needHeaders = true;
            if(fetchprofile.contains(IMAPFolder.FetchProfileItem.SIZE))
                needSize = true;
            hdrs = fetchprofile.getHeaderNames();
        }
    }


    protected IMAPMessage(IMAPFolder imapfolder, int i, int j)
    {
        super(imapfolder, i);
        size = -1;
        uid = -1L;
        headersLoaded = false;
        seqnum = j;
        flags = null;
    }

    protected IMAPMessage(Session session)
    {
        super(session);
        size = -1;
        uid = -1L;
        headersLoaded = false;
    }

    private BODYSTRUCTURE _getBodyStructure()
    {
        return bs;
    }

    private ENVELOPE _getEnvelope()
    {
        return envelope;
    }

    private Flags _getFlags()
    {
        return flags;
    }

    private InternetAddress[] aaclone(InternetAddress ainternetaddress[])
    {
        if(ainternetaddress == null)
            return null;
        else
            return (InternetAddress[])ainternetaddress.clone();
    }

    private boolean areHeadersLoaded()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = headersLoaded;
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    private static String craftHeaderCmd(IMAPProtocol imapprotocol, String as[])
    {
        StringBuffer stringbuffer;
        int i;
        if(imapprotocol.isREV1())
            stringbuffer = new StringBuffer("BODY.PEEK[HEADER.FIELDS (");
        else
            stringbuffer = new StringBuffer("RFC822.HEADER.LINES (");
        i = 0;
        do
        {
            if(i >= as.length)
            {
                if(imapprotocol.isREV1())
                    stringbuffer.append(")]");
                else
                    stringbuffer.append(")");
                return stringbuffer.toString();
            }
            if(i > 0)
                stringbuffer.append(" ");
            stringbuffer.append(as[i]);
            i++;
        } while(true);
    }

    static void fetch(IMAPFolder imapfolder, Message amessage[], FetchProfile fetchprofile)
        throws MessagingException
    {
        StringBuffer stringbuffer;
        boolean flag2;
        String as[];
        Object obj;
        com.sun.mail.imap.protocol.MessageSet amessageset[];
        stringbuffer = new StringBuffer();
        boolean flag = true;
        if(fetchprofile.contains(javax.mail.FetchProfile.Item.ENVELOPE))
        {
            stringbuffer.append(EnvelopeCmd);
            flag = false;
        }
        if(fetchprofile.contains(javax.mail.FetchProfile.Item.FLAGS))
        {
            boolean flag1;
            _cls1FetchProfileCondition _lcls1fetchprofilecondition;
            String s5;
            if(flag)
                s5 = "FLAGS";
            else
                s5 = " FLAGS";
            stringbuffer.append(s5);
            flag = false;
        }
        if(fetchprofile.contains(javax.mail.FetchProfile.Item.CONTENT_INFO))
        {
            String s4;
            if(flag)
                s4 = "BODYSTRUCTURE";
            else
                s4 = " BODYSTRUCTURE";
            stringbuffer.append(s4);
            flag = false;
        }
        if(fetchprofile.contains(javax.mail.UIDFolder.FetchProfileItem.UID))
        {
            String s3;
            if(flag)
                s3 = "UID";
            else
                s3 = " UID";
            stringbuffer.append(s3);
            flag = false;
        }
        flag1 = fetchprofile.contains(IMAPFolder.FetchProfileItem.HEADERS);
        flag2 = false;
        if(flag1)
        {
            flag2 = true;
            if(imapfolder.protocol.isREV1())
            {
                String s2;
                if(flag)
                    s2 = "BODY.PEEK[HEADER]";
                else
                    s2 = " BODY.PEEK[HEADER]";
                stringbuffer.append(s2);
            } else
            {
                String s1;
                if(flag)
                    s1 = "RFC822.HEADER";
                else
                    s1 = " RFC822.HEADER";
                stringbuffer.append(s1);
            }
            flag = false;
        }
        if(fetchprofile.contains(IMAPFolder.FetchProfileItem.SIZE))
        {
            String s;
            if(flag)
                s = "RFC822.SIZE";
            else
                s = " RFC822.SIZE";
            stringbuffer.append(s);
            flag = false;
        }
        as = (String[])null;
        if(!flag2)
        {
            as = fetchprofile.getHeaderNames();
            if(as.length > 0)
            {
                if(!flag)
                    stringbuffer.append(" ");
                stringbuffer.append(craftHeaderCmd(imapfolder.protocol, as));
            }
        }
        _lcls1fetchprofilecondition = new _cls1FetchProfileCondition(fetchprofile);
        obj = imapfolder.messageCacheLock;
        obj;
        JVM INSTR monitorenter ;
        amessageset = Utility.toMessageSet(amessage, _lcls1fetchprofilecondition);
        if(amessageset != null)
            break MISSING_BLOCK_LABEL_343;
        obj;
        JVM INSTR monitorexit ;
        return;
        Response aresponse[];
        Vector vector;
        aresponse = (Response[])null;
        vector = new Vector();
        Response aresponse2[] = imapfolder.protocol.fetch(amessageset, stringbuffer.toString());
        aresponse = aresponse2;
_L36:
        if(aresponse != null) goto _L2; else goto _L1
_L1:
        obj;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        FolderClosedException folderclosedexception = new FolderClosedException(imapfolder, connectionexception.getMessage());
        throw folderclosedexception;
        ProtocolException protocolexception;
        protocolexception;
        MessagingException messagingexception = new MessagingException(protocolexception.getMessage(), protocolexception);
        throw messagingexception;
_L37:
        int i;
        if(i < aresponse.length) goto _L4; else goto _L3
_L3:
        int j1 = vector.size();
        if(j1 == 0)
            break MISSING_BLOCK_LABEL_475;
        Response aresponse1[] = new Response[j1];
        vector.copyInto(aresponse1);
        imapfolder.handleResponses(aresponse1);
        obj;
        JVM INSTR monitorexit ;
        return;
_L4:
        if(aresponse[i] != null) goto _L6; else goto _L5
_L6:
        if(aresponse[i] instanceof FetchResponse) goto _L8; else goto _L7
_L7:
        vector.addElement(aresponse[i]);
          goto _L5
_L8:
        FetchResponse fetchresponse;
        IMAPMessage imapmessage;
        int j;
        fetchresponse = (FetchResponse)aresponse[i];
        imapmessage = imapfolder.getMessageBySeqNumber(fetchresponse.getNumber());
        j = fetchresponse.getItemCount();
        boolean flag3;
        int k;
        flag3 = false;
        k = 0;
_L38:
        if(k < j) goto _L10; else goto _L9
_L9:
        if(!flag3) goto _L5; else goto _L11
_L11:
        vector.addElement(fetchresponse);
          goto _L5
_L10:
        com.sun.mail.imap.protocol.Item item = fetchresponse.getItem(k);
        if(!(item instanceof Flags)) goto _L13; else goto _L12
_L12:
        if(fetchprofile.contains(javax.mail.FetchProfile.Item.FLAGS) && imapmessage != null) goto _L15; else goto _L14
_L15:
        imapmessage.flags = (Flags)item;
          goto _L16
_L13:
        if(!(item instanceof ENVELOPE)) goto _L18; else goto _L17
_L17:
        imapmessage.envelope = (ENVELOPE)item;
          goto _L16
_L18:
        if(!(item instanceof INTERNALDATE)) goto _L20; else goto _L19
_L19:
        imapmessage.receivedDate = ((INTERNALDATE)item).getDate();
          goto _L16
_L20:
        if(!(item instanceof RFC822SIZE)) goto _L22; else goto _L21
_L21:
        imapmessage.size = ((RFC822SIZE)item).size;
          goto _L16
_L22:
        if(!(item instanceof BODYSTRUCTURE)) goto _L24; else goto _L23
_L23:
        imapmessage.bs = (BODYSTRUCTURE)item;
          goto _L16
_L24:
        if(!(item instanceof UID)) goto _L26; else goto _L25
_L25:
        UID uid1 = (UID)item;
        imapmessage.uid = uid1.uid;
        if(imapfolder.uidTable == null)
            imapfolder.uidTable = new Hashtable();
        imapfolder.uidTable.put(new Long(uid1.uid), imapmessage);
          goto _L16
_L26:
        if(!(item instanceof RFC822DATA) && !(item instanceof BODY)) goto _L16; else goto _L27
_L27:
        if(!(item instanceof RFC822DATA)) goto _L29; else goto _L28
_L28:
        java.io.ByteArrayInputStream bytearrayinputstream = ((RFC822DATA)item).getByteArrayInputStream();
_L32:
        InternetHeaders internetheaders;
        internetheaders = new InternetHeaders();
        internetheaders.load(bytearrayinputstream);
        if(imapmessage.headers != null && !flag2) goto _L31; else goto _L30
_L30:
        imapmessage.headers = internetheaders;
_L33:
        if(!flag2)
            break MISSING_BLOCK_LABEL_990;
        imapmessage.setHeadersLoaded(true);
          goto _L16
_L29:
        bytearrayinputstream = ((BODY)item).getByteArrayInputStream();
          goto _L32
_L31:
        Enumeration enumeration = internetheaders.getAllHeaders();
        while(enumeration.hasMoreElements()) 
        {
            Header header = (Header)enumeration.nextElement();
            if(!imapmessage.isHeaderLoaded(header.getName()))
                imapmessage.headers.addHeader(header.getName(), header.getValue());
        }
          goto _L33
_L35:
        int i1 = as.length;
        int l;
        if(l >= i1) goto _L16; else goto _L34
_L34:
        imapmessage.setHeaderLoaded(as[l]);
        l++;
          goto _L35
        CommandFailedException commandfailedexception;
        commandfailedexception;
          goto _L36
_L2:
        i = 0;
          goto _L37
_L5:
        i++;
          goto _L37
_L14:
        flag3 = true;
_L16:
        k++;
          goto _L38
        l = 0;
          goto _L35
    }

    private boolean isHeaderLoaded(String s)
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = headersLoaded;
        if(!flag) goto _L2; else goto _L1
_L1:
        boolean flag1 = true;
_L4:
        this;
        JVM INSTR monitorexit ;
        return flag1;
_L2:
        boolean flag2;
        if(loadedHeaders == null)
            break MISSING_BLOCK_LABEL_49;
        flag2 = loadedHeaders.containsKey(s.toUpperCase(Locale.ENGLISH));
        flag1 = flag2;
        continue; /* Loop/switch isn't completed */
        flag1 = false;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    private void loadBODYSTRUCTURE()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        BODYSTRUCTURE bodystructure = bs;
        if(bodystructure == null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        Object obj = getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol = getProtocol();
        checkExpunged();
        bs = imapprotocol.fetchBodyStructure(getSequenceNumber());
        if(bs == null)
        {
            forceCheckExpunged();
            throw new MessagingException("Unable to load BODYSTRUCTURE");
        }
        break MISSING_BLOCK_LABEL_118;
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        obj;
        JVM INSTR monitorexit ;
          goto _L1
    }

    private void loadEnvelope()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        ENVELOPE envelope1 = envelope;
        if(envelope1 == null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        (Response[])null;
        Object obj = getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        int i;
        Response aresponse[];
        imapprotocol = getProtocol();
        checkExpunged();
        i = getSequenceNumber();
        aresponse = imapprotocol.fetch(i, EnvelopeCmd);
        int j = 0;
_L7:
        if(j < aresponse.length)
            break MISSING_BLOCK_LABEL_113;
        imapprotocol.notifyResponseHandlers(aresponse);
        imapprotocol.handleResult(aresponse[-1 + aresponse.length]);
        obj;
        JVM INSTR monitorexit ;
        if(envelope != null) goto _L1; else goto _L3
_L3:
        throw new MessagingException("Failed to load IMAP envelope");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        if(aresponse[j] != null && (aresponse[j] instanceof FetchResponse) && ((FetchResponse)aresponse[j]).getNumber() == i) goto _L5; else goto _L4
_L5:
        FetchResponse fetchresponse;
        int k;
        fetchresponse = (FetchResponse)aresponse[j];
        k = fetchresponse.getItemCount();
        int l = 0;
_L8:
        if(l >= k) goto _L4; else goto _L6
_L6:
        com.sun.mail.imap.protocol.Item item;
        item = fetchresponse.getItem(l);
        if(item instanceof ENVELOPE)
        {
            envelope = (ENVELOPE)item;
            break MISSING_BLOCK_LABEL_307;
        }
        if(item instanceof INTERNALDATE)
        {
            receivedDate = ((INTERNALDATE)item).getDate();
            break MISSING_BLOCK_LABEL_307;
        }
        break MISSING_BLOCK_LABEL_257;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        if(item instanceof RFC822SIZE)
            size = ((RFC822SIZE)item).size;
        break MISSING_BLOCK_LABEL_307;
        ProtocolException protocolexception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L4:
        j++;
          goto _L7
        l++;
          goto _L8
    }

    private void loadFlags()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Flags flags = this.flags;
        if(flags == null) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
        JVM INSTR monitorenter ;
_L2:
        synchronized(getMessageCacheLock())
        {
            IMAPProtocol imapprotocol = getProtocol();
            checkExpunged();
            this.flags = imapprotocol.fetchFlags(getSequenceNumber());
        }
        if(true) goto _L1; else goto _L3
_L3:
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        ProtocolException protocolexception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    private void loadHeaders()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = headersLoaded;
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        Object obj = getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        imapprotocol = getProtocol();
        checkExpunged();
        if(!imapprotocol.isREV1()) goto _L4; else goto _L3
_L3:
        BODY body = imapprotocol.peekBody(getSequenceNumber(), toSection("HEADER"));
        java.io.ByteArrayInputStream bytearrayinputstream;
        bytearrayinputstream = null;
        if(body == null)
            break MISSING_BLOCK_LABEL_76;
        java.io.ByteArrayInputStream bytearrayinputstream2 = body.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream2;
_L6:
        obj;
        JVM INSTR monitorexit ;
        if(bytearrayinputstream != null)
            break MISSING_BLOCK_LABEL_182;
        throw new MessagingException("Cannot load header");
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        RFC822DATA rfc822data = imapprotocol.fetchRFC822(getSequenceNumber(), "HEADER");
        bytearrayinputstream = null;
        if(rfc822data == null) goto _L6; else goto _L5
_L5:
        java.io.ByteArrayInputStream bytearrayinputstream1 = rfc822data.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream1;
          goto _L6
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        Exception exception1;
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        ProtocolException protocolexception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        headers = new InternetHeaders(bytearrayinputstream);
        headersLoaded = true;
          goto _L1
    }

    private void setHeaderLoaded(String s)
    {
        this;
        JVM INSTR monitorenter ;
        if(loadedHeaders == null)
            loadedHeaders = new Hashtable(1);
        loadedHeaders.put(s.toUpperCase(Locale.ENGLISH), s);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private void setHeadersLoaded(boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        headersLoaded = flag;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    private String toSection(String s)
    {
        if(sectionId == null)
            return s;
        else
            return (new StringBuilder(String.valueOf(sectionId))).append(".").append(s).toString();
    }

    Session _getSession()
    {
        return session;
    }

    void _setFlags(Flags flags)
    {
        this.flags = flags;
    }

    public void addFrom(Address aaddress[])
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void addHeader(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void addHeaderLine(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void addRecipients(javax.mail.Message.RecipientType recipienttype, Address aaddress[])
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void checkExpunged()
        throws MessageRemovedException
    {
        if(expunged)
            throw new MessageRemovedException();
        else
            return;
    }

    protected void forceCheckExpunged()
        throws MessageRemovedException, FolderClosedException
    {
        Object obj = getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        getProtocol().noop();
_L2:
        Exception exception;
        ConnectionException connectionexception;
        if(expunged)
            throw new MessageRemovedException();
        else
            return;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public Enumeration getAllHeaderLines()
        throws MessagingException
    {
        checkExpunged();
        loadHeaders();
        return super.getAllHeaderLines();
    }

    public Enumeration getAllHeaders()
        throws MessagingException
    {
        checkExpunged();
        loadHeaders();
        return super.getAllHeaders();
    }

    public String getContentID()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        return bs.id;
    }

    public String[] getContentLanguage()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        if(bs.language != null)
            return (String[])bs.language.clone();
        else
            return null;
    }

    public String getContentMD5()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        return bs.md5;
    }

    protected InputStream getContentStream()
        throws MessagingException
    {
        int i;
        boolean flag;
        i = -1;
        flag = getPeek();
        if(true) goto _L2; else goto _L1
_L1:
        obj;
        JVM INSTR monitorenter ;
_L2:
        IMAPProtocol imapprotocol;
label0:
        {
            IMAPInputStream imapinputstream;
            synchronized(getMessageCacheLock())
            {
                imapprotocol = getProtocol();
                checkExpunged();
                if(!imapprotocol.isREV1() || getFetchBlockSize() == i)
                    break label0;
                String s = toSection("TEXT");
                if(bs != null)
                    i = bs.size;
                imapinputstream = new IMAPInputStream(this, s, i, flag);
            }
            return imapinputstream;
        }
        if(!imapprotocol.isREV1()) goto _L4; else goto _L3
_L3:
        if(!flag) goto _L6; else goto _L5
_L5:
        BODY body = imapprotocol.peekBody(getSequenceNumber(), toSection("TEXT"));
_L7:
        java.io.ByteArrayInputStream bytearrayinputstream;
        bytearrayinputstream = null;
        if(body == null)
            break MISSING_BLOCK_LABEL_132;
        java.io.ByteArrayInputStream bytearrayinputstream1 = body.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream1;
_L9:
        obj;
        JVM INSTR monitorexit ;
        ProtocolException protocolexception;
        ConnectionException connectionexception;
        RFC822DATA rfc822data;
        java.io.ByteArrayInputStream bytearrayinputstream2;
        if(bytearrayinputstream == null)
            throw new MessagingException("No content");
        else
            return bytearrayinputstream;
_L6:
        body = imapprotocol.fetchBody(getSequenceNumber(), toSection("TEXT"));
          goto _L7
_L4:
        rfc822data = imapprotocol.fetchRFC822(getSequenceNumber(), "TEXT");
        bytearrayinputstream = null;
        if(rfc822data == null) goto _L9; else goto _L8
_L8:
        bytearrayinputstream2 = rfc822data.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream2;
          goto _L9
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
          goto _L7
    }

    public String getContentType()
        throws MessagingException
    {
        checkExpunged();
        if(type == null)
        {
            loadBODYSTRUCTURE();
            type = (new ContentType(bs.type, bs.subtype, bs.cParams)).toString();
        }
        return type;
    }

    public DataHandler getDataHandler()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        checkExpunged();
        if(dh != null) goto _L2; else goto _L1
_L1:
        loadBODYSTRUCTURE();
        if(type == null)
            type = (new ContentType(bs.type, bs.subtype, bs.cParams)).toString();
        if(!bs.isMulti()) goto _L4; else goto _L3
_L3:
        dh = new DataHandler(new IMAPMultipartDataSource(this, bs.bodies, sectionId, this));
_L2:
        DataHandler datahandler = super.getDataHandler();
        this;
        JVM INSTR monitorexit ;
        return datahandler;
_L4:
        if(!bs.isNested() || !isREV1()) goto _L2; else goto _L5
_L5:
        BODYSTRUCTURE bodystructure;
        ENVELOPE envelope1;
        bodystructure = bs.bodies[0];
        envelope1 = bs.envelope;
        if(sectionId != null) goto _L7; else goto _L6
_L6:
        String s = "1";
_L8:
        dh = new DataHandler(new IMAPNestedMessage(this, bodystructure, envelope1, s), type);
          goto _L2
        Exception exception;
        exception;
        throw exception;
_L7:
        String s1 = (new StringBuilder(String.valueOf(sectionId))).append(".1").toString();
        s = s1;
          goto _L8
    }

    public String getDescription()
        throws MessagingException
    {
        checkExpunged();
        if(description != null)
            return description;
        loadBODYSTRUCTURE();
        if(bs.description == null)
            return null;
        try
        {
            description = MimeUtility.decodeText(bs.description);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            description = bs.description;
        }
        return description;
    }

    public String getDisposition()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        return bs.disposition;
    }

    public String getEncoding()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        return bs.encoding;
    }

    protected int getFetchBlockSize()
    {
        return ((IMAPStore)folder.getStore()).getFetchBlockSize();
    }

    public String getFileName()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        ParameterList parameterlist = bs.dParams;
        String s = null;
        if(parameterlist != null)
            s = bs.dParams.get("filename");
        if(s == null && bs.cParams != null)
            s = bs.cParams.get("name");
        return s;
    }

    public Flags getFlags()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        Flags flags;
        checkExpunged();
        loadFlags();
        flags = super.getFlags();
        this;
        JVM INSTR monitorexit ;
        return flags;
        Exception exception;
        exception;
        throw exception;
    }

    public Address[] getFrom()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        return aaclone(envelope.from);
    }

    public String getHeader(String s, String s1)
        throws MessagingException
    {
        checkExpunged();
        if(getHeader(s) == null)
            return null;
        else
            return headers.getHeader(s, s1);
    }

    public String[] getHeader(String s)
        throws MessagingException
    {
        checkExpunged();
        if(isHeaderLoaded(s))
            return headers.getHeader(s);
        Object obj = getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        imapprotocol = getProtocol();
        checkExpunged();
        if(!imapprotocol.isREV1()) goto _L2; else goto _L1
_L1:
        BODY body = imapprotocol.peekBody(getSequenceNumber(), toSection((new StringBuilder("HEADER.FIELDS (")).append(s).append(")").toString()));
        java.io.ByteArrayInputStream bytearrayinputstream;
        bytearrayinputstream = null;
        if(body == null)
            break MISSING_BLOCK_LABEL_102;
        java.io.ByteArrayInputStream bytearrayinputstream2 = body.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream2;
_L4:
        obj;
        JVM INSTR monitorexit ;
        if(bytearrayinputstream == null)
            return null;
        break MISSING_BLOCK_LABEL_210;
_L2:
        RFC822DATA rfc822data = imapprotocol.fetchRFC822(getSequenceNumber(), (new StringBuilder("HEADER.LINES (")).append(s).append(")").toString());
        bytearrayinputstream = null;
        if(rfc822data == null) goto _L4; else goto _L3
_L3:
        java.io.ByteArrayInputStream bytearrayinputstream1 = rfc822data.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream1;
          goto _L4
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        if(headers == null)
            headers = new InternetHeaders();
        headers.load(bytearrayinputstream);
        setHeaderLoaded(s);
        return headers.getHeader(s);
    }

    public String getInReplyTo()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        return envelope.inReplyTo;
    }

    public int getLineCount()
        throws MessagingException
    {
        checkExpunged();
        loadBODYSTRUCTURE();
        return bs.lines;
    }

    public Enumeration getMatchingHeaderLines(String as[])
        throws MessagingException
    {
        checkExpunged();
        loadHeaders();
        return super.getMatchingHeaderLines(as);
    }

    public Enumeration getMatchingHeaders(String as[])
        throws MessagingException
    {
        checkExpunged();
        loadHeaders();
        return super.getMatchingHeaders(as);
    }

    protected Object getMessageCacheLock()
    {
        return ((IMAPFolder)folder).messageCacheLock;
    }

    public String getMessageID()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        return envelope.messageId;
    }

    public Enumeration getNonMatchingHeaderLines(String as[])
        throws MessagingException
    {
        checkExpunged();
        loadHeaders();
        return super.getNonMatchingHeaderLines(as);
    }

    public Enumeration getNonMatchingHeaders(String as[])
        throws MessagingException
    {
        checkExpunged();
        loadHeaders();
        return super.getNonMatchingHeaders(as);
    }

    public boolean getPeek()
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = peek;
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    protected IMAPProtocol getProtocol()
        throws ProtocolException, FolderClosedException
    {
        ((IMAPFolder)folder).waitIfIdle();
        IMAPProtocol imapprotocol = ((IMAPFolder)folder).protocol;
        if(imapprotocol == null)
            throw new FolderClosedException(folder);
        else
            return imapprotocol;
    }

    public Date getReceivedDate()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        if(receivedDate == null)
            return null;
        else
            return new Date(receivedDate.getTime());
    }

    public Address[] getRecipients(javax.mail.Message.RecipientType recipienttype)
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        if(recipienttype == javax.mail.Message.RecipientType.TO)
            return aaclone(envelope.to);
        if(recipienttype == javax.mail.Message.RecipientType.CC)
            return aaclone(envelope.cc);
        if(recipienttype == javax.mail.Message.RecipientType.BCC)
            return aaclone(envelope.bcc);
        else
            return super.getRecipients(recipienttype);
    }

    public Address[] getReplyTo()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        return aaclone(envelope.replyTo);
    }

    public Address getSender()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        if(envelope.sender != null)
            return envelope.sender[0];
        else
            return null;
    }

    public Date getSentDate()
        throws MessagingException
    {
        checkExpunged();
        loadEnvelope();
        if(envelope.date == null)
            return null;
        else
            return new Date(envelope.date.getTime());
    }

    protected int getSequenceNumber()
    {
        return seqnum;
    }

    public int getSize()
        throws MessagingException
    {
        checkExpunged();
        if(size == -1)
            loadEnvelope();
        return size;
    }

    public String getSubject()
        throws MessagingException
    {
        checkExpunged();
        if(subject != null)
            return subject;
        loadEnvelope();
        if(envelope.subject == null)
            return null;
        try
        {
            subject = MimeUtility.decodeText(envelope.subject);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            subject = envelope.subject;
        }
        return subject;
    }

    protected long getUID()
    {
        return uid;
    }

    public void invalidateHeaders()
    {
        this;
        JVM INSTR monitorenter ;
        headersLoaded = false;
        loadedHeaders = null;
        envelope = null;
        bs = null;
        receivedDate = null;
        size = -1;
        type = null;
        subject = null;
        description = null;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected boolean isREV1()
        throws FolderClosedException
    {
        IMAPProtocol imapprotocol = ((IMAPFolder)folder).protocol;
        if(imapprotocol == null)
            throw new FolderClosedException(folder);
        else
            return imapprotocol.isREV1();
    }

    public boolean isSet(javax.mail.Flags.Flag flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag1;
        checkExpunged();
        loadFlags();
        flag1 = super.isSet(flag);
        this;
        JVM INSTR monitorexit ;
        return flag1;
        Exception exception;
        exception;
        throw exception;
    }

    public void removeHeader(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setContentID(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setContentLanguage(String as[])
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setContentMD5(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setDataHandler(DataHandler datahandler)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setDescription(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setDisposition(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setExpunged(boolean flag)
    {
        super.setExpunged(flag);
        seqnum = -1;
    }

    public void setFileName(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setFlags(Flags flags, boolean flag)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(true) goto _L2; else goto _L1
_L1:
        obj;
        JVM INSTR monitorenter ;
_L2:
        synchronized(getMessageCacheLock())
        {
            IMAPProtocol imapprotocol = getProtocol();
            checkExpunged();
            imapprotocol.storeFlags(getSequenceNumber(), flags, flag);
        }
        this;
        JVM INSTR monitorexit ;
        return;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        exception1;
        obj;
        JVM INSTR monitorexit ;
        throw exception1;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public void setFrom(Address address)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setHeader(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setMessageNumber(int i)
    {
        super.setMessageNumber(i);
    }

    public void setPeek(boolean flag)
    {
        this;
        JVM INSTR monitorenter ;
        peek = flag;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setRecipients(javax.mail.Message.RecipientType recipienttype, Address aaddress[])
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setReplyTo(Address aaddress[])
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setSender(Address address)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    public void setSentDate(Date date)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setSequenceNumber(int i)
    {
        seqnum = i;
    }

    public void setSubject(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPMessage is read-only");
    }

    protected void setUID(long l)
    {
        uid = l;
    }

    public void writeTo(OutputStream outputstream)
        throws IOException, MessagingException
    {
        boolean flag = getPeek();
        Object obj = getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        imapprotocol = getProtocol();
        checkExpunged();
        if(!imapprotocol.isREV1()) goto _L2; else goto _L1
_L1:
        if(!flag) goto _L4; else goto _L3
_L3:
        BODY body = imapprotocol.peekBody(getSequenceNumber(), sectionId);
_L9:
        java.io.ByteArrayInputStream bytearrayinputstream;
        bytearrayinputstream = null;
        if(body == null)
            break MISSING_BLOCK_LABEL_68;
        java.io.ByteArrayInputStream bytearrayinputstream1 = body.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream1;
_L7:
        obj;
        JVM INSTR monitorexit ;
          goto _L5
_L4:
        body = imapprotocol.fetchBody(getSequenceNumber(), sectionId);
        continue; /* Loop/switch isn't completed */
_L2:
        rfc822data = imapprotocol.fetchRFC822(getSequenceNumber(), null);
        bytearrayinputstream = null;
        if(rfc822data == null) goto _L7; else goto _L6
_L6:
        bytearrayinputstream2 = rfc822data.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream2;
          goto _L7
        connectionexception;
        throw new FolderClosedException(folder, connectionexception.getMessage());
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        protocolexception;
        forceCheckExpunged();
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
_L5:
        ProtocolException protocolexception;
        Exception exception;
        ConnectionException connectionexception;
        RFC822DATA rfc822data;
        java.io.ByteArrayInputStream bytearrayinputstream2;
        if(bytearrayinputstream == null)
            throw new MessagingException("No content");
        byte abyte0[] = new byte[1024];
        do
        {
            int i = bytearrayinputstream.read(abyte0);
            if(i == -1)
                return;
            outputstream.write(abyte0, 0, i);
        } while(true);
        if(true) goto _L9; else goto _L8
_L8:
    }

    private static String EnvelopeCmd = "ENVELOPE INTERNALDATE RFC822.SIZE";
    protected BODYSTRUCTURE bs;
    private String description;
    protected ENVELOPE envelope;
    private boolean headersLoaded;
    private Hashtable loadedHeaders;
    private boolean peek;
    private Date receivedDate;
    protected String sectionId;
    private int seqnum;
    private int size;
    private String subject;
    private String type;
    private long uid;







}
