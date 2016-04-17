// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap;

import com.sun.mail.iap.*;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.*;

// Referenced classes of package com.sun.mail.imap:
//            IMAPMessage

public class IMAPInputStream extends InputStream
{

    public IMAPInputStream(IMAPMessage imapmessage, String s, int i, boolean flag)
    {
        msg = imapmessage;
        section = s;
        max = i;
        peek = flag;
        pos = 0;
        blksize = imapmessage.getFetchBlockSize();
    }

    private void checkSeen()
    {
        if(!peek) goto _L2; else goto _L1
_L1:
        return;
_L2:
        Folder folder;
        try
        {
            folder = msg.getFolder();
        }
        catch(MessagingException messagingexception)
        {
            return;
        }
        if(folder == null) goto _L1; else goto _L3
_L3:
        if(folder.getMode() == 1 || msg.isSet(javax.mail.Flags.Flag.SEEN)) goto _L1; else goto _L4
_L4:
        msg.setFlag(javax.mail.Flags.Flag.SEEN, true);
        return;
    }

    private void fill()
        throws IOException
    {
        if(max != -1 && pos >= max)
        {
            if(pos == 0)
                checkSeen();
            readbuf = null;
            return;
        }
        if(readbuf == null)
            readbuf = new ByteArray(64 + blksize);
        Object obj = msg.getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        IMAPProtocol imapprotocol;
        imapprotocol = msg.getProtocol();
        if(msg.isExpunged())
            throw new MessageRemovedIOException("No content for expunged message");
        break MISSING_BLOCK_LABEL_124;
        ProtocolException protocolexception;
        protocolexception;
        forceCheckExpunged();
        throw new IOException(protocolexception.getMessage());
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        int i;
        int j;
        i = msg.getSequenceNumber();
        j = blksize;
        if(max != -1 && pos + blksize > max)
            j = max - pos;
        if(!peek) goto _L2; else goto _L1
_L1:
        BODY body2 = imapprotocol.peekBody(i, section, pos, j, readbuf);
        BODY body1 = body2;
_L7:
        if(body1 == null) goto _L4; else goto _L3
_L3:
        ByteArray bytearray = body1.getByteArray();
        if(bytearray != null) goto _L5; else goto _L4
_L4:
        forceCheckExpunged();
        throw new IOException("No content");
_L2:
        BODY body = imapprotocol.fetchBody(i, section, pos, j, readbuf);
        body1 = body;
        continue; /* Loop/switch isn't completed */
        FolderClosedException folderclosedexception;
        folderclosedexception;
        throw new FolderClosedIOException(folderclosedexception.getFolder(), folderclosedexception.getMessage());
_L5:
        obj;
        JVM INSTR monitorexit ;
        if(pos == 0)
            checkSeen();
        buf = bytearray.getBytes();
        bufpos = bytearray.getStart();
        int k = bytearray.getCount();
        bufcount = k + bufpos;
        pos = k + pos;
        return;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private void forceCheckExpunged()
        throws MessageRemovedIOException, FolderClosedIOException
    {
        Object obj = msg.getMessageCacheLock();
        obj;
        JVM INSTR monitorenter ;
        msg.getProtocol().noop();
_L2:
        if(msg.isExpunged())
            throw new MessageRemovedIOException();
        break MISSING_BLOCK_LABEL_87;
        ConnectionException connectionexception;
        connectionexception;
        throw new FolderClosedIOException(msg.getFolder(), connectionexception.getMessage());
        Exception exception;
        exception;
        obj;
        JVM INSTR monitorexit ;
        throw exception;
        FolderClosedException folderclosedexception;
        folderclosedexception;
        throw new FolderClosedIOException(folderclosedexception.getFolder(), folderclosedexception.getMessage());
        return;
        ProtocolException protocolexception;
        protocolexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public int available()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        int j;
        i = bufcount;
        j = bufpos;
        int k = i - j;
        this;
        JVM INSTR monitorexit ;
        return k;
        Exception exception;
        exception;
        throw exception;
    }

    public int read()
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        if(bufpos < bufcount) goto _L2; else goto _L1
_L1:
        int k;
        int l;
        fill();
        k = bufpos;
        l = bufcount;
        if(k < l) goto _L2; else goto _L3
_L3:
        int j = -1;
_L5:
        this;
        JVM INSTR monitorexit ;
        return j;
_L2:
        byte byte0;
        byte abyte0[] = buf;
        int i = bufpos;
        bufpos = i + 1;
        byte0 = abyte0[i];
        j = byte0 & 0xff;
        if(true) goto _L5; else goto _L4
_L4:
        Exception exception;
        exception;
        throw exception;
    }

    public int read(byte abyte0[])
        throws IOException
    {
        return read(abyte0, 0, abyte0.length);
    }

    public int read(byte abyte0[], int i, int j)
        throws IOException
    {
        this;
        JVM INSTR monitorenter ;
        int k = bufcount - bufpos;
        if(k > 0) goto _L2; else goto _L1
_L1:
        int l;
        int i1;
        fill();
        l = bufcount;
        i1 = bufpos;
        k = l - i1;
        if(k > 0) goto _L2; else goto _L3
_L3:
        int j1 = -1;
_L5:
        this;
        JVM INSTR monitorexit ;
        return j1;
_L2:
        Exception exception;
        if(k < j)
            j1 = k;
        else
            j1 = j;
        System.arraycopy(buf, bufpos, abyte0, i, j1);
        bufpos = j1 + bufpos;
        if(true) goto _L5; else goto _L4
_L4:
        exception;
        throw exception;
    }

    private static final int slop = 64;
    private int blksize;
    private byte buf[];
    private int bufcount;
    private int bufpos;
    private int max;
    private IMAPMessage msg;
    private boolean peek;
    private int pos;
    private ByteArray readbuf;
    private String section;
}
