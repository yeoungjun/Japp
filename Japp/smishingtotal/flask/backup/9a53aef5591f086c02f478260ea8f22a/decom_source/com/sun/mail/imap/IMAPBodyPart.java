// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPMessage, IMAPInputStream, IMAPMultipartDataSource, IMAPNestedMessage

public class IMAPBodyPart extends MimeBodyPart
{

    protected IMAPBodyPart(BODYSTRUCTURE bodystructure, String s, IMAPMessage imapmessage)
    {
        headersLoaded = false;
        bs = bodystructure;
        sectionId = s;
        message = imapmessage;
        type = (new ContentType(bodystructure.type, bodystructure.subtype, bodystructure.cParams)).toString();
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
        if(headers == null)
            headers = new InternetHeaders();
        Object obj = message.getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        BODY body;
        IMAPProtocol imapprotocol = message.getProtocol();
        message.checkExpunged();
        if(!imapprotocol.isREV1())
            break MISSING_BLOCK_LABEL_209;
        body = imapprotocol.peekBody(message.getSequenceNumber(), (new StringBuilder(String.valueOf(sectionId))).append(".MIME").toString());
        if(body != null)
            break MISSING_BLOCK_LABEL_151;
        throw new MessagingException("Failed to fetch headers");
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedException(message.getFolder(), connectionexception.getMessage());
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
        java.io.ByteArrayInputStream bytearrayinputstream = body.getByteArrayInputStream();
        if(bytearrayinputstream != null)
            break MISSING_BLOCK_LABEL_190;
        throw new MessagingException("Failed to fetch headers");
        ProtocolException protocolexception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
        headers.load(bytearrayinputstream);
_L3:
        obj;
        JVM INSTR monitorexit ;
        headersLoaded = true;
          goto _L1
        headers.addHeader("Content-Type", type);
        headers.addHeader("Content-Transfer-Encoding", bs.encoding);
        if(bs.description != null)
            headers.addHeader("Content-Description", bs.description);
        if(bs.id != null)
            headers.addHeader("Content-ID", bs.id);
        if(bs.md5 != null)
            headers.addHeader("Content-MD5", bs.md5);
          goto _L3
    }

    public void addHeader(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void addHeaderLine(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public Enumeration getAllHeaderLines()
        throws MessagingException
    {
        loadHeaders();
        return super.getAllHeaderLines();
    }

    public Enumeration getAllHeaders()
        throws MessagingException
    {
        loadHeaders();
        return super.getAllHeaders();
    }

    public String getContentID()
        throws MessagingException
    {
        return bs.id;
    }

    public String getContentMD5()
        throws MessagingException
    {
        return bs.md5;
    }

    protected InputStream getContentStream()
        throws MessagingException
    {
        boolean flag = message.getPeek();
        if(true) goto _L2; else goto _L1
_L1:
        obj;
        JVM INSTR monitorenter ;
_L2:
        IMAPProtocol imapprotocol;
label0:
        {
            IMAPInputStream imapinputstream;
            synchronized(message.getMessageCacheLock())
            {
                imapprotocol = message.getProtocol();
                message.checkExpunged();
                if(!imapprotocol.isREV1() || message.getFetchBlockSize() == -1)
                    break label0;
                imapinputstream = new IMAPInputStream(message, sectionId, bs.size, flag);
            }
            return imapinputstream;
        }
        int i = message.getSequenceNumber();
        if(!flag) goto _L4; else goto _L3
_L3:
        BODY body = imapprotocol.peekBody(i, sectionId);
_L6:
        java.io.ByteArrayInputStream bytearrayinputstream;
        bytearrayinputstream = null;
        if(body == null)
            break MISSING_BLOCK_LABEL_128;
        java.io.ByteArrayInputStream bytearrayinputstream1 = body.getByteArrayInputStream();
        bytearrayinputstream = bytearrayinputstream1;
        obj;
        JVM INSTR monitorexit ;
        ProtocolException protocolexception;
        ConnectionException connectionexception;
        BODY body1;
        if(bytearrayinputstream == null)
            throw new MessagingException("No content");
        else
            return bytearrayinputstream;
_L4:
        body1 = imapprotocol.fetchBody(i, sectionId);
        body = body1;
        if(true) goto _L6; else goto _L5
_L5:
        connectionexception;
        throw new FolderClosedException(message.getFolder(), connectionexception.getMessage());
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        protocolexception;
        throw new MessagingException(protocolexception.getMessage(), protocolexception);
    }

    public String getContentType()
        throws MessagingException
    {
        return type;
    }

    public DataHandler getDataHandler()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        if(dh != null) goto _L2; else goto _L1
_L1:
        if(!bs.isMulti()) goto _L4; else goto _L3
_L3:
        dh = new DataHandler(new IMAPMultipartDataSource(this, bs.bodies, sectionId, message));
_L2:
        DataHandler datahandler = super.getDataHandler();
        this;
        JVM INSTR monitorexit ;
        return datahandler;
_L4:
        if(!bs.isNested() || !message.isREV1()) goto _L2; else goto _L5
_L5:
        dh = new DataHandler(new IMAPNestedMessage(message, bs.bodies[0], bs.envelope, sectionId), type);
          goto _L2
        Exception exception;
        exception;
        throw exception;
    }

    public String getDescription()
        throws MessagingException
    {
        if(description != null)
            return description;
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
        return bs.disposition;
    }

    public String getEncoding()
        throws MessagingException
    {
        return bs.encoding;
    }

    public String getFileName()
        throws MessagingException
    {
        ParameterList parameterlist = bs.dParams;
        String s = null;
        if(parameterlist != null)
            s = bs.dParams.get("filename");
        if(s == null && bs.cParams != null)
            s = bs.cParams.get("name");
        return s;
    }

    public String[] getHeader(String s)
        throws MessagingException
    {
        loadHeaders();
        return super.getHeader(s);
    }

    public int getLineCount()
        throws MessagingException
    {
        return bs.lines;
    }

    public Enumeration getMatchingHeaderLines(String as[])
        throws MessagingException
    {
        loadHeaders();
        return super.getMatchingHeaderLines(as);
    }

    public Enumeration getMatchingHeaders(String as[])
        throws MessagingException
    {
        loadHeaders();
        return super.getMatchingHeaders(as);
    }

    public Enumeration getNonMatchingHeaderLines(String as[])
        throws MessagingException
    {
        loadHeaders();
        return super.getNonMatchingHeaderLines(as);
    }

    public Enumeration getNonMatchingHeaders(String as[])
        throws MessagingException
    {
        loadHeaders();
        return super.getNonMatchingHeaders(as);
    }

    public int getSize()
        throws MessagingException
    {
        return bs.size;
    }

    public void removeHeader(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContent(Object obj, String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContent(Multipart multipart)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setContentMD5(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setDataHandler(DataHandler datahandler)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setDescription(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setDisposition(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setFileName(String s)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    public void setHeader(String s, String s1)
        throws MessagingException
    {
        throw new IllegalWriteException("IMAPBodyPart is read-only");
    }

    protected void updateHeaders()
    {
    }

    private BODYSTRUCTURE bs;
    private String description;
    private boolean headersLoaded;
    private IMAPMessage message;
    private String sectionId;
    private String type;
}
