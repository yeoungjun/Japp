// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.imap.protocol;

import com.sun.mail.iap.*;
import com.sun.mail.imap.*;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import javax.mail.Flags;
import javax.mail.Quota;
import javax.mail.internet.MimeUtility;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;

// Referenced classes of package com.sun.mail.imap.protocol:
//            BASE64MailboxEncoder, ListInfo, IMAPResponse, SearchSequence, 
//            INTERNALDATE, MessageSet, MailboxInfo, BODY, 
//            FetchResponse, BODYSTRUCTURE, RFC822DATA, UID, 
//            Namespaces, SaslAuthenticator, Status, UIDSet

public class IMAPProtocol extends Protocol
{

    public IMAPProtocol(String s, String s1, int i, boolean flag, PrintStream printstream, Properties properties, boolean flag1)
        throws IOException, ProtocolException
    {
        super(s1, i, flag, printstream, properties, (new StringBuilder("mail.")).append(s).toString(), flag1);
        connected = false;
        rev1 = false;
        capabilities = null;
        authmechs = null;
        name = s;
        if(capabilities == null)
            capability();
        if(hasCapability("IMAP4rev1"))
            rev1 = true;
        searchCharsets = new String[2];
        searchCharsets[0] = "UTF-8";
        searchCharsets[1] = MimeUtility.mimeCharset(MimeUtility.getDefaultJavaCharset());
        connected = true;
        if(!connected)
            disconnect();
        return;
        Exception exception;
        exception;
        if(!connected)
            disconnect();
        throw exception;
    }

    private void copy(String s, String s1)
        throws ProtocolException
    {
        String s2 = BASE64MailboxEncoder.encode(s1);
        Argument argument = new Argument();
        argument.writeAtom(s);
        argument.writeString(s2);
        simpleCommand("COPY", argument);
    }

    private String createFlagList(Flags flags)
    {
        StringBuffer stringbuffer;
        javax.mail.Flags.Flag aflag[];
        boolean flag;
        int i;
        stringbuffer = new StringBuffer();
        stringbuffer.append("(");
        aflag = flags.getSystemFlags();
        flag = true;
        i = 0;
_L3:
        if(i < aflag.length) goto _L2; else goto _L1
_L1:
        String as[];
        int j;
        as = flags.getUserFlags();
        j = 0;
_L7:
        if(j >= as.length)
        {
            stringbuffer.append(")");
            return stringbuffer.toString();
        }
        break MISSING_BLOCK_LABEL_189;
_L2:
        javax.mail.Flags.Flag flag1;
        String s;
        flag1 = aflag[i];
        if(flag1 == javax.mail.Flags.Flag.ANSWERED)
            s = "\\Answered";
        else
        if(flag1 == javax.mail.Flags.Flag.DELETED)
            s = "\\Deleted";
        else
        if(flag1 == javax.mail.Flags.Flag.DRAFT)
            s = "\\Draft";
        else
        if(flag1 == javax.mail.Flags.Flag.FLAGGED)
        {
            s = "\\Flagged";
        } else
        {
            if(flag1 != javax.mail.Flags.Flag.RECENT)
                continue; /* Loop/switch isn't completed */
            s = "\\Recent";
        }
_L6:
        if(flag)
            flag = false;
        else
            stringbuffer.append(' ');
        stringbuffer.append(s);
_L5:
        i++;
          goto _L3
        if(flag1 != javax.mail.Flags.Flag.SEEN) goto _L5; else goto _L4
_L4:
        s = "\\Seen";
          goto _L6
        if(flag)
            flag = false;
        else
            stringbuffer.append(' ');
        stringbuffer.append(as[j]);
        j++;
          goto _L7
    }

    private ListInfo[] doList(String s, String s1, String s2)
        throws ProtocolException
    {
        Response aresponse[];
        ListInfo alistinfo[];
        Response response;
        String s3 = BASE64MailboxEncoder.encode(s1);
        String s4 = BASE64MailboxEncoder.encode(s2);
        Argument argument = new Argument();
        argument.writeString(s3);
        argument.writeString(s4);
        aresponse = command(s, argument);
        alistinfo = (ListInfo[])null;
        response = aresponse[-1 + aresponse.length];
        if(!response.isOK()) goto _L2; else goto _L1
_L1:
        Vector vector;
        int i;
        int j;
        vector = new Vector(1);
        i = 0;
        j = aresponse.length;
_L6:
        if(i < j) goto _L4; else goto _L3
_L3:
        if(vector.size() > 0)
        {
            alistinfo = new ListInfo[vector.size()];
            vector.copyInto(alistinfo);
        }
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        return alistinfo;
_L4:
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L7:
        i++;
        if(true) goto _L6; else goto _L5
_L5:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(imapresponse.keyEquals(s))
        {
            vector.addElement(new ListInfo(imapresponse));
            aresponse[i] = null;
        }
          goto _L7
        if(true) goto _L6; else goto _L8
_L8:
    }

    private Response[] fetch(String s, String s1, boolean flag)
        throws ProtocolException
    {
        if(flag)
            return command((new StringBuilder("UID FETCH ")).append(s).append(" (").append(s1).append(")").toString(), null);
        else
            return command((new StringBuilder("FETCH ")).append(s).append(" (").append(s1).append(")").toString(), null);
    }

    private AppendUID getAppendUID(Response response)
    {
        if(response.isOK())
        {
            byte byte0;
            do
                byte0 = response.readByte();
            while(byte0 > 0 && byte0 != 91);
            if(byte0 != 0 && response.readAtom().equalsIgnoreCase("APPENDUID"))
                return new AppendUID(response.readLong(), response.readLong());
        }
        return null;
    }

    private int[] issueSearch(String s, SearchTerm searchterm, String s1)
        throws ProtocolException, SearchException, IOException
    {
        Response aresponse[];
        int ai[];
        Vector vector;
        int i;
        int i1;
        String s2;
        Argument argument;
        Response response;
        int j;
        int l;
        if(s1 == null)
            s2 = null;
        else
            s2 = MimeUtility.javaCharset(s1);
        argument = SearchSequence.generateSequence(searchterm, s2);
        argument.writeAtom(s);
        if(s1 == null)
            aresponse = command("SEARCH", argument);
        else
            aresponse = command((new StringBuilder("SEARCH CHARSET ")).append(s1).toString(), argument);
        response = aresponse[-1 + aresponse.length];
        ai = (int[])null;
        if(!response.isOK()) goto _L2; else goto _L1
_L1:
        vector = new Vector();
        i = 0;
        j = aresponse.length;
_L7:
        if(i < j) goto _L4; else goto _L3
_L3:
        l = vector.size();
        ai = new int[l];
        i1 = 0;
_L9:
        if(i1 < l)
            break MISSING_BLOCK_LABEL_236;
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        return ai;
_L4:
        if(aresponse[i] instanceof IMAPResponse) goto _L6; else goto _L5
_L5:
        i++;
          goto _L7
_L6:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(!imapresponse.keyEquals("SEARCH")) goto _L5; else goto _L8
_L8:
        int k;
label0:
        {
            k = imapresponse.readNumber();
            if(k != -1)
                break label0;
            aresponse[i] = null;
        }
          goto _L5
        vector.addElement(new Integer(k));
          goto _L8
        ai[i1] = ((Integer)vector.elementAt(i1)).intValue();
        i1++;
          goto _L9
    }

    private Quota parseQuota(Response response)
        throws ParsingException
    {
        Quota quota = new Quota(response.readAtomString());
        response.skipSpaces();
        if(response.readByte() != 40)
            throw new ParsingException("parse error in QUOTA");
        Vector vector = new Vector();
        do
        {
            String s;
            do
            {
                if(response.peekByte() == 41)
                {
                    response.readByte();
                    quota.resources = new javax.mail.Quota.Resource[vector.size()];
                    vector.copyInto(quota.resources);
                    return quota;
                }
                s = response.readAtom();
            } while(s == null);
            vector.addElement(new javax.mail.Quota.Resource(s, response.readLong(), response.readLong()));
        } while(true);
    }

    private int[] search(String s, SearchTerm searchterm)
        throws ProtocolException, SearchException
    {
        if(!SearchSequence.isAscii(searchterm))
            break MISSING_BLOCK_LABEL_21;
        int ai1[] = issueSearch(s, searchterm, null);
        return ai1;
        IOException ioexception1;
        ioexception1;
        int i = 0;
_L2:
        if(i >= searchCharsets.length)
            throw new SearchException("Search failed");
        if(searchCharsets[i] != null)
            break; /* Loop/switch isn't completed */
_L3:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        int ai[] = issueSearch(s, searchterm, searchCharsets[i]);
        return ai;
        CommandFailedException commandfailedexception;
        commandfailedexception;
        searchCharsets[i] = null;
          goto _L3
        IOException ioexception;
        ioexception;
          goto _L3
        ProtocolException protocolexception;
        protocolexception;
        throw protocolexception;
        SearchException searchexception;
        searchexception;
        throw searchexception;
    }

    private void storeFlags(String s, Flags flags, boolean flag)
        throws ProtocolException
    {
        Response aresponse[];
        if(flag)
            aresponse = command((new StringBuilder("STORE ")).append(s).append(" +FLAGS ").append(createFlagList(flags)).toString(), null);
        else
            aresponse = command((new StringBuilder("STORE ")).append(s).append(" -FLAGS ").append(createFlagList(flags)).toString(), null);
        notifyResponseHandlers(aresponse);
        handleResult(aresponse[-1 + aresponse.length]);
    }

    public void append(String s, Flags flags, Date date, Literal literal)
        throws ProtocolException
    {
        appenduid(s, flags, date, literal, false);
    }

    public AppendUID appenduid(String s, Flags flags, Date date, Literal literal)
        throws ProtocolException
    {
        return appenduid(s, flags, date, literal, true);
    }

    public AppendUID appenduid(String s, Flags flags, Date date, Literal literal, boolean flag)
        throws ProtocolException
    {
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        if(flags != null)
        {
            if(flags.contains(javax.mail.Flags.Flag.RECENT))
            {
                Flags flags1 = new Flags(flags);
                flags1.remove(javax.mail.Flags.Flag.RECENT);
                flags = flags1;
            }
            argument.writeAtom(createFlagList(flags));
        }
        if(date != null)
            argument.writeString(INTERNALDATE.format(date));
        argument.writeBytes(literal);
        Response aresponse[] = command("APPEND", argument);
        notifyResponseHandlers(aresponse);
        handleResult(aresponse[-1 + aresponse.length]);
        if(flag)
            return getAppendUID(aresponse[-1 + aresponse.length]);
        else
            return null;
    }

    public void authlogin(String s, String s1)
        throws ProtocolException
    {
        this;
        JVM INSTR monitorenter ;
        Vector vector = new Vector();
        Response response;
        boolean flag;
        response = null;
        flag = false;
        String s4 = writeCommand("AUTHENTICATE LOGIN", null);
        String s2 = s4;
_L3:
        OutputStream outputstream;
        ByteArrayOutputStream bytearrayoutputstream;
        BASE64EncoderStream base64encoderstream;
        outputstream = getOutputStream();
        bytearrayoutputstream = new ByteArrayOutputStream();
        base64encoderstream = new BASE64EncoderStream(bytearrayoutputstream, 0x7fffffff);
        boolean flag1 = true;
_L1:
        if(!flag)
            break MISSING_BLOCK_LABEL_130;
        Response aresponse[] = new Response[vector.size()];
        vector.copyInto(aresponse);
        notifyResponseHandlers(aresponse);
        handleResult(response);
        setCapabilities(response);
        authenticated = true;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Response response1 = Response.byeResponse(exception1);
        response = response1;
        flag = true;
        s2 = null;
        continue; /* Loop/switch isn't completed */
        response = readResponse();
        if(!response.isContinuation())
            break MISSING_BLOCK_LABEL_226;
        Exception exception2;
        Response response2;
        String s3;
        if(flag1)
        {
            s3 = s;
            flag1 = false;
        } else
        {
            s3 = s1;
        }
        base64encoderstream.write(ASCIIUtility.getBytes(s3));
        base64encoderstream.flush();
        bytearrayoutputstream.write(CRLF);
        outputstream.write(bytearrayoutputstream.toByteArray());
        outputstream.flush();
        bytearrayoutputstream.reset();
          goto _L1
        exception2;
        response2 = Response.byeResponse(exception2);
        response = response2;
        flag = true;
          goto _L1
        if(!response.isTagged() || !response.getTag().equals(s2))
            break MISSING_BLOCK_LABEL_253;
        flag = true;
          goto _L1
        if(!response.isBYE())
            break MISSING_BLOCK_LABEL_267;
        flag = true;
          goto _L1
        vector.addElement(response);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void authplain(String s, String s1, String s2)
        throws ProtocolException
    {
        this;
        JVM INSTR monitorenter ;
        Vector vector = new Vector();
        Response response;
        boolean flag;
        response = null;
        flag = false;
        String s4 = writeCommand("AUTHENTICATE PLAIN", null);
        String s3 = s4;
_L3:
        OutputStream outputstream;
        ByteArrayOutputStream bytearrayoutputstream;
        BASE64EncoderStream base64encoderstream;
        outputstream = getOutputStream();
        bytearrayoutputstream = new ByteArrayOutputStream();
        base64encoderstream = new BASE64EncoderStream(bytearrayoutputstream, 0x7fffffff);
_L1:
        if(!flag)
            break MISSING_BLOCK_LABEL_130;
        Response aresponse[] = new Response[vector.size()];
        vector.copyInto(aresponse);
        notifyResponseHandlers(aresponse);
        handleResult(response);
        setCapabilities(response);
        authenticated = true;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        Response response1 = Response.byeResponse(exception1);
        response = response1;
        flag = true;
        s3 = null;
        continue; /* Loop/switch isn't completed */
        response = readResponse();
        if(!response.isContinuation())
            break MISSING_BLOCK_LABEL_241;
        base64encoderstream.write(ASCIIUtility.getBytes((new StringBuilder(String.valueOf(s))).append("\0").append(s1).append("\0").append(s2).toString()));
        base64encoderstream.flush();
        bytearrayoutputstream.write(CRLF);
        outputstream.write(bytearrayoutputstream.toByteArray());
        outputstream.flush();
        bytearrayoutputstream.reset();
          goto _L1
        Exception exception2;
        exception2;
        Response response2 = Response.byeResponse(exception2);
        response = response2;
        flag = true;
          goto _L1
        if(!response.isTagged() || !response.getTag().equals(s3))
            break MISSING_BLOCK_LABEL_268;
        flag = true;
          goto _L1
        if(!response.isBYE())
            break MISSING_BLOCK_LABEL_282;
        flag = true;
          goto _L1
        vector.addElement(response);
          goto _L1
        Exception exception;
        exception;
        throw exception;
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void capability()
        throws ProtocolException
    {
        Response aresponse[];
        int i;
        int j;
        aresponse = command("CAPABILITY", null);
        if(!aresponse[-1 + aresponse.length].isOK())
            throw new ProtocolException(aresponse[-1 + aresponse.length].toString());
        capabilities = new HashMap(10);
        authmechs = new ArrayList(5);
        i = 0;
        j = aresponse.length;
_L2:
        if(i >= j)
            return;
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L3:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(imapresponse.keyEquals("CAPABILITY"))
            parseCapabilities(imapresponse);
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    public void check()
        throws ProtocolException
    {
        simpleCommand("CHECK", null);
    }

    public void close()
        throws ProtocolException
    {
        simpleCommand("CLOSE", null);
    }

    public void copy(int i, int j, String s)
        throws ProtocolException
    {
        copy((new StringBuilder(String.valueOf(String.valueOf(i)))).append(":").append(String.valueOf(j)).toString(), s);
    }

    public void copy(MessageSet amessageset[], String s)
        throws ProtocolException
    {
        copy(MessageSet.toString(amessageset), s);
    }

    public void create(String s)
        throws ProtocolException
    {
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        simpleCommand("CREATE", argument);
    }

    public void delete(String s)
        throws ProtocolException
    {
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        simpleCommand("DELETE", argument);
    }

    public void deleteACL(String s, String s1)
        throws ProtocolException
    {
        if(!hasCapability("ACL"))
        {
            throw new BadCommandException("ACL not supported");
        } else
        {
            String s2 = BASE64MailboxEncoder.encode(s);
            Argument argument = new Argument();
            argument.writeString(s2);
            argument.writeString(s1);
            Response aresponse[] = command("DELETEACL", argument);
            Response response = aresponse[-1 + aresponse.length];
            notifyResponseHandlers(aresponse);
            handleResult(response);
            return;
        }
    }

    public void disconnect()
    {
        super.disconnect();
        authenticated = false;
    }

    public MailboxInfo examine(String s)
        throws ProtocolException
    {
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        Response aresponse[] = command("EXAMINE", argument);
        MailboxInfo mailboxinfo = new MailboxInfo(aresponse);
        mailboxinfo.mode = 1;
        notifyResponseHandlers(aresponse);
        handleResult(aresponse[-1 + aresponse.length]);
        return mailboxinfo;
    }

    public void expunge()
        throws ProtocolException
    {
        simpleCommand("EXPUNGE", null);
    }

    public Response[] fetch(int i, int j, String s)
        throws ProtocolException
    {
        return fetch((new StringBuilder(String.valueOf(String.valueOf(i)))).append(":").append(String.valueOf(j)).toString(), s, false);
    }

    public Response[] fetch(int i, String s)
        throws ProtocolException
    {
        return fetch(String.valueOf(i), s, false);
    }

    public Response[] fetch(MessageSet amessageset[], String s)
        throws ProtocolException
    {
        return fetch(MessageSet.toString(amessageset), s, false);
    }

    public BODY fetchBody(int i, String s)
        throws ProtocolException
    {
        return fetchBody(i, s, false);
    }

    public BODY fetchBody(int i, String s, int j, int k)
        throws ProtocolException
    {
        return fetchBody(i, s, j, k, false, null);
    }

    public BODY fetchBody(int i, String s, int j, int k, ByteArray bytearray)
        throws ProtocolException
    {
        return fetchBody(i, s, j, k, false, bytearray);
    }

    protected BODY fetchBody(int i, String s, int j, int k, boolean flag, ByteArray bytearray)
        throws ProtocolException
    {
        ba = bytearray;
        String s1;
        StringBuilder stringbuilder;
        String s2;
        Response aresponse[];
        Response response;
        if(flag)
            s1 = "BODY.PEEK[";
        else
            s1 = "BODY[";
        stringbuilder = new StringBuilder(String.valueOf(s1));
        if(s == null)
            s2 = "]<";
        else
            s2 = (new StringBuilder(String.valueOf(s))).append("]<").toString();
        aresponse = fetch(i, stringbuilder.append(s2).append(String.valueOf(j)).append(".").append(String.valueOf(k)).append(">").toString());
        notifyResponseHandlers(aresponse);
        response = aresponse[-1 + aresponse.length];
        if(response.isOK())
            return (BODY)FetchResponse.getItem(aresponse, i, com/sun/mail/imap/protocol/BODY);
        if(response.isNO())
        {
            return null;
        } else
        {
            handleResult(response);
            return null;
        }
    }

    protected BODY fetchBody(int i, String s, boolean flag)
        throws ProtocolException
    {
        Response aresponse[];
        Response response;
        if(flag)
        {
            StringBuilder stringbuilder = new StringBuilder("BODY.PEEK[");
            String s1;
            if(s == null)
                s1 = "]";
            else
                s1 = (new StringBuilder(String.valueOf(s))).append("]").toString();
            aresponse = fetch(i, stringbuilder.append(s1).toString());
        } else
        {
            StringBuilder stringbuilder1 = new StringBuilder("BODY[");
            String s2;
            if(s == null)
                s2 = "]";
            else
                s2 = (new StringBuilder(String.valueOf(s))).append("]").toString();
            aresponse = fetch(i, stringbuilder1.append(s2).toString());
        }
        notifyResponseHandlers(aresponse);
        response = aresponse[-1 + aresponse.length];
        if(response.isOK())
            return (BODY)FetchResponse.getItem(aresponse, i, com/sun/mail/imap/protocol/BODY);
        if(response.isNO())
        {
            return null;
        } else
        {
            handleResult(response);
            return null;
        }
    }

    public BODYSTRUCTURE fetchBodyStructure(int i)
        throws ProtocolException
    {
        Response aresponse[] = fetch(i, "BODYSTRUCTURE");
        notifyResponseHandlers(aresponse);
        Response response = aresponse[-1 + aresponse.length];
        BODYSTRUCTURE bodystructure;
        if(response.isOK())
        {
            bodystructure = (BODYSTRUCTURE)FetchResponse.getItem(aresponse, i, com/sun/mail/imap/protocol/BODYSTRUCTURE);
        } else
        {
            boolean flag = response.isNO();
            bodystructure = null;
            if(!flag)
            {
                handleResult(response);
                return null;
            }
        }
        return bodystructure;
    }

    public Flags fetchFlags(int i)
        throws ProtocolException
    {
        Flags flags;
        Response aresponse[];
        int j;
        int k;
        flags = null;
        aresponse = fetch(i, "FLAGS");
        j = 0;
        k = aresponse.length;
_L5:
        if(j < k) goto _L2; else goto _L1
_L1:
        notifyResponseHandlers(aresponse);
        handleResult(aresponse[-1 + aresponse.length]);
        return flags;
_L2:
        if(aresponse[j] != null && (aresponse[j] instanceof FetchResponse) && ((FetchResponse)aresponse[j]).getNumber() == i) goto _L4; else goto _L3
_L3:
        j++;
          goto _L5
_L4:
        if((flags = (Flags)((FetchResponse)aresponse[j]).getItem(javax/mail/Flags)) == null) goto _L3; else goto _L6
_L6:
        aresponse[j] = null;
          goto _L1
    }

    public RFC822DATA fetchRFC822(int i, String s)
        throws ProtocolException
    {
        String s1;
        Response aresponse[];
        Response response;
        if(s == null)
            s1 = "RFC822";
        else
            s1 = (new StringBuilder("RFC822.")).append(s).toString();
        aresponse = fetch(i, s1);
        notifyResponseHandlers(aresponse);
        response = aresponse[-1 + aresponse.length];
        if(response.isOK())
            return (RFC822DATA)FetchResponse.getItem(aresponse, i, com/sun/mail/imap/protocol/RFC822DATA);
        if(response.isNO())
        {
            return null;
        } else
        {
            handleResult(response);
            return null;
        }
    }

    public UID fetchSequenceNumber(long l)
        throws ProtocolException
    {
        UID uid;
        Response aresponse[];
        int i;
        int j;
        uid = null;
        aresponse = fetch(String.valueOf(l), "UID", true);
        i = 0;
        j = aresponse.length;
_L5:
        if(i < j) goto _L2; else goto _L1
_L1:
        notifyResponseHandlers(aresponse);
        handleResult(aresponse[-1 + aresponse.length]);
        return uid;
_L2:
        if(aresponse[i] != null && (aresponse[i] instanceof FetchResponse)) goto _L4; else goto _L3
_L3:
        i++;
          goto _L5
_L4:
        uid = (UID)((FetchResponse)aresponse[i]).getItem(com/sun/mail/imap/protocol/UID);
        if(uid == null) goto _L3; else goto _L6
_L6:
        if(uid.uid == l) goto _L1; else goto _L7
_L7:
        uid = null;
          goto _L3
    }

    public UID[] fetchSequenceNumbers(long l, long l1)
        throws ProtocolException
    {
        Response aresponse[];
        Vector vector;
        int i;
        StringBuilder stringbuilder = (new StringBuilder(String.valueOf(String.valueOf(l)))).append(":");
        String s;
        int j;
        UID auid[];
        if(l1 == -1L)
            s = "*";
        else
            s = String.valueOf(l1);
        aresponse = fetch(stringbuilder.append(s).toString(), "UID", true);
        vector = new Vector();
        i = 0;
        j = aresponse.length;
_L2:
        if(i >= j)
        {
            notifyResponseHandlers(aresponse);
            handleResult(aresponse[-1 + aresponse.length]);
            auid = new UID[vector.size()];
            vector.copyInto(auid);
            return auid;
        }
        if(aresponse[i] != null && (aresponse[i] instanceof FetchResponse))
            break; /* Loop/switch isn't completed */
_L3:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        UID uid = (UID)((FetchResponse)aresponse[i]).getItem(com/sun/mail/imap/protocol/UID);
        if(uid != null)
            vector.addElement(uid);
          goto _L3
        if(true) goto _L2; else goto _L4
_L4:
    }

    public UID[] fetchSequenceNumbers(long al[])
        throws ProtocolException
    {
        StringBuffer stringbuffer;
        int i;
        stringbuffer = new StringBuffer();
        i = 0;
_L3:
        if(i < al.length) goto _L2; else goto _L1
_L1:
        Response aresponse[];
        Vector vector;
        int j;
        int k;
        aresponse = fetch(stringbuffer.toString(), "UID", true);
        vector = new Vector();
        j = 0;
        k = aresponse.length;
_L4:
        if(j >= k)
        {
            notifyResponseHandlers(aresponse);
            handleResult(aresponse[-1 + aresponse.length]);
            UID auid[] = new UID[vector.size()];
            vector.copyInto(auid);
            return auid;
        }
        break MISSING_BLOCK_LABEL_121;
_L2:
        if(i > 0)
            stringbuffer.append(",");
        stringbuffer.append(String.valueOf(al[i]));
        i++;
          goto _L3
        if(aresponse[j] != null && (aresponse[j] instanceof FetchResponse))
        {
            UID uid = (UID)((FetchResponse)aresponse[j]).getItem(com/sun/mail/imap/protocol/UID);
            if(uid != null)
                vector.addElement(uid);
        }
        j++;
          goto _L4
    }

    public UID fetchUID(int i)
        throws ProtocolException
    {
        Response aresponse[] = fetch(i, "UID");
        notifyResponseHandlers(aresponse);
        Response response = aresponse[-1 + aresponse.length];
        UID uid;
        if(response.isOK())
        {
            uid = (UID)FetchResponse.getItem(aresponse, i, com/sun/mail/imap/protocol/UID);
        } else
        {
            boolean flag = response.isNO();
            uid = null;
            if(!flag)
            {
                handleResult(response);
                return null;
            }
        }
        return uid;
    }

    public ACL[] getACL(String s)
        throws ProtocolException
    {
        Response aresponse[];
        Response response;
        Vector vector;
        if(!hasCapability("ACL"))
            throw new BadCommandException("ACL not supported");
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        aresponse = command("GETACL", argument);
        response = aresponse[-1 + aresponse.length];
        vector = new Vector();
        if(!response.isOK()) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = 0;
        j = aresponse.length;
_L5:
        if(i < j) goto _L3; else goto _L2
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        ACL aacl[] = new ACL[vector.size()];
        vector.copyInto(aacl);
        return aacl;
_L3:
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L7:
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(!imapresponse.keyEquals("ACL")) goto _L7; else goto _L6
_L6:
        imapresponse.readAtomString();
_L11:
        String s2 = imapresponse.readAtomString();
        if(s2 != null) goto _L9; else goto _L8
_L8:
        String s3;
        aresponse[i] = null;
          goto _L7
_L9:
        if((s3 = imapresponse.readAtomString()) == null) goto _L8; else goto _L10
_L10:
        vector.addElement(new ACL(s2, new Rights(s3)));
          goto _L11
    }

    public Map getCapabilities()
    {
        return capabilities;
    }

    OutputStream getIMAPOutputStream()
    {
        return getOutputStream();
    }

    public Quota[] getQuota(String s)
        throws ProtocolException
    {
        Response aresponse[];
        Vector vector;
        Response response;
        if(!hasCapability("QUOTA"))
            throw new BadCommandException("QUOTA not supported");
        Argument argument = new Argument();
        argument.writeString(s);
        aresponse = command("GETQUOTA", argument);
        vector = new Vector();
        response = aresponse[-1 + aresponse.length];
        if(!response.isOK()) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = 0;
        j = aresponse.length;
_L5:
        if(i < j) goto _L3; else goto _L2
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        Quota aquota[] = new Quota[vector.size()];
        vector.copyInto(aquota);
        return aquota;
_L3:
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L6:
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(imapresponse.keyEquals("QUOTA"))
        {
            vector.addElement(parseQuota(imapresponse));
            aresponse[i] = null;
        }
          goto _L6
        if(true) goto _L5; else goto _L7
_L7:
    }

    public Quota[] getQuotaRoot(String s)
        throws ProtocolException
    {
        Response aresponse[];
        Response response;
        Hashtable hashtable;
        if(!hasCapability("QUOTA"))
            throw new BadCommandException("GETQUOTAROOT not supported");
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        aresponse = command("GETQUOTAROOT", argument);
        response = aresponse[-1 + aresponse.length];
        hashtable = new Hashtable();
        if(!response.isOK()) goto _L2; else goto _L1
_L1:
        int j;
        int k;
        j = 0;
        k = aresponse.length;
_L6:
        if(j < k) goto _L3; else goto _L2
_L2:
        Quota aquota[];
        Enumeration enumeration;
        int i;
        notifyResponseHandlers(aresponse);
        handleResult(response);
        aquota = new Quota[hashtable.size()];
        enumeration = hashtable.elements();
        i = 0;
_L8:
        if(!enumeration.hasMoreElements())
            return aquota;
        break MISSING_BLOCK_LABEL_288;
_L3:
        if(aresponse[j] instanceof IMAPResponse) goto _L5; else goto _L4
_L4:
        j++;
          goto _L6
_L5:
        IMAPResponse imapresponse;
        imapresponse = (IMAPResponse)aresponse[j];
        if(!imapresponse.keyEquals("QUOTAROOT"))
            break MISSING_BLOCK_LABEL_221;
        imapresponse.readAtomString();
_L7:
        String s2;
label0:
        {
            s2 = imapresponse.readAtomString();
            if(s2 != null)
                break label0;
            aresponse[j] = null;
        }
          goto _L4
        hashtable.put(s2, new Quota(s2));
          goto _L7
        if(imapresponse.keyEquals("QUOTA"))
        {
            Quota quota = parseQuota(imapresponse);
            Quota quota1 = (Quota)hashtable.get(quota.quotaRoot);
            if(quota1 != null)
            {
                quota1.resources;
            }
            hashtable.put(quota.quotaRoot, quota);
            aresponse[j] = null;
        }
          goto _L4
        aquota[i] = (Quota)enumeration.nextElement();
        i++;
          goto _L8
    }

    protected ByteArray getResponseBuffer()
    {
        ByteArray bytearray = ba;
        ba = null;
        return bytearray;
    }

    public boolean hasCapability(String s)
    {
        return capabilities.containsKey(s.toUpperCase(Locale.ENGLISH));
    }

    public void idleAbort()
        throws ProtocolException
    {
        OutputStream outputstream = getOutputStream();
        try
        {
            outputstream.write(DONE);
            outputstream.flush();
            return;
        }
        catch(IOException ioexception)
        {
            return;
        }
    }

    public void idleStart()
        throws ProtocolException
    {
        this;
        JVM INSTR monitorenter ;
        if(!hasCapability("IDLE"))
            throw new BadCommandException("IDLE not supported");
        break MISSING_BLOCK_LABEL_28;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        Response response2;
        idleTag = writeCommand("IDLE", null);
        response2 = readResponse();
        Response response1 = response2;
_L2:
        if(!response1.isContinuation())
            handleResult(response1);
        this;
        JVM INSTR monitorexit ;
        return;
        LiteralException literalexception;
        literalexception;
        response1 = literalexception.getResponse();
        continue; /* Loop/switch isn't completed */
        Exception exception1;
        exception1;
        Response response = Response.byeResponse(exception1);
        response1 = response;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public boolean isAuthenticated()
    {
        return authenticated;
    }

    public boolean isREV1()
    {
        return rev1;
    }

    public ListInfo[] list(String s, String s1)
        throws ProtocolException
    {
        return doList("LIST", s, s1);
    }

    public Rights[] listRights(String s, String s1)
        throws ProtocolException
    {
        Response aresponse[];
        Response response;
        Vector vector;
        if(!hasCapability("ACL"))
            throw new BadCommandException("ACL not supported");
        String s2 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s2);
        argument.writeString(s1);
        aresponse = command("LISTRIGHTS", argument);
        response = aresponse[-1 + aresponse.length];
        vector = new Vector();
        if(!response.isOK()) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = 0;
        j = aresponse.length;
_L5:
        if(i < j) goto _L3; else goto _L2
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        Rights arights[] = new Rights[vector.size()];
        vector.copyInto(arights);
        return arights;
_L3:
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L7:
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(!imapresponse.keyEquals("LISTRIGHTS")) goto _L7; else goto _L6
_L6:
        imapresponse.readAtomString();
        imapresponse.readAtomString();
_L8:
        String s3;
label0:
        {
            s3 = imapresponse.readAtomString();
            if(s3 != null)
                break label0;
            aresponse[i] = null;
        }
          goto _L7
        vector.addElement(new Rights(s3));
          goto _L8
    }

    public void login(String s, String s1)
        throws ProtocolException
    {
        Argument argument = new Argument();
        argument.writeString(s);
        argument.writeString(s1);
        Response aresponse[] = command("LOGIN", argument);
        notifyResponseHandlers(aresponse);
        handleResult(aresponse[-1 + aresponse.length]);
        setCapabilities(aresponse[-1 + aresponse.length]);
        authenticated = true;
    }

    public void logout()
        throws ProtocolException
    {
        Response aresponse[] = command("LOGOUT", null);
        authenticated = false;
        notifyResponseHandlers(aresponse);
        disconnect();
    }

    public ListInfo[] lsub(String s, String s1)
        throws ProtocolException
    {
        return doList("LSUB", s, s1);
    }

    public Rights myRights(String s)
        throws ProtocolException
    {
        Response aresponse[];
        Response response;
        boolean flag;
        Rights rights;
        if(!hasCapability("ACL"))
            throw new BadCommandException("ACL not supported");
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        aresponse = command("MYRIGHTS", argument);
        response = aresponse[-1 + aresponse.length];
        flag = response.isOK();
        rights = null;
        if(!flag) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = 0;
        j = aresponse.length;
_L5:
        if(i < j) goto _L3; else goto _L2
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        return rights;
_L3:
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L6:
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(imapresponse.keyEquals("MYRIGHTS"))
        {
            imapresponse.readAtomString();
            String s2 = imapresponse.readAtomString();
            if(rights == null)
                rights = new Rights(s2);
            aresponse[i] = null;
        }
          goto _L6
        if(true) goto _L5; else goto _L7
_L7:
    }

    public Namespaces namespace()
        throws ProtocolException
    {
        Response aresponse[];
        Response response;
        boolean flag;
        Namespaces namespaces;
        if(!hasCapability("NAMESPACE"))
            throw new BadCommandException("NAMESPACE not supported");
        aresponse = command("NAMESPACE", null);
        response = aresponse[-1 + aresponse.length];
        flag = response.isOK();
        namespaces = null;
        if(!flag) goto _L2; else goto _L1
_L1:
        int i;
        int j;
        i = 0;
        j = aresponse.length;
_L5:
        if(i < j) goto _L3; else goto _L2
_L2:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        return namespaces;
_L3:
        if(aresponse[i] instanceof IMAPResponse)
            break; /* Loop/switch isn't completed */
_L6:
        i++;
        if(true) goto _L5; else goto _L4
_L4:
        IMAPResponse imapresponse = (IMAPResponse)aresponse[i];
        if(imapresponse.keyEquals("NAMESPACE"))
        {
            if(namespaces == null)
                namespaces = new Namespaces(imapresponse);
            aresponse[i] = null;
        }
          goto _L6
        if(true) goto _L5; else goto _L7
_L7:
    }

    public void noop()
        throws ProtocolException
    {
        if(debug)
            out.println("IMAP DEBUG: IMAPProtocol noop");
        simpleCommand("NOOP", null);
    }

    protected void parseCapabilities(Response response)
    {
_L5:
        String s = response.readAtom(']');
        if(s != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if(s.length() != 0)
            break; /* Loop/switch isn't completed */
        if(response.peekByte() != 93)
        {
            response.skipToken();
            continue; /* Loop/switch isn't completed */
        }
        if(true) goto _L1; else goto _L3
_L3:
        capabilities.put(s.toUpperCase(Locale.ENGLISH), s);
        if(s.regionMatches(true, 0, "AUTH=", 0, 5))
        {
            authmechs.add(s.substring(5));
            if(debug)
                out.println((new StringBuilder("IMAP DEBUG: AUTH: ")).append(s.substring(5)).toString());
        }
        if(true) goto _L5; else goto _L4
_L4:
    }

    public BODY peekBody(int i, String s)
        throws ProtocolException
    {
        return fetchBody(i, s, true);
    }

    public BODY peekBody(int i, String s, int j, int k)
        throws ProtocolException
    {
        return fetchBody(i, s, j, k, true, null);
    }

    public BODY peekBody(int i, String s, int j, int k, ByteArray bytearray)
        throws ProtocolException
    {
        return fetchBody(i, s, j, k, true, bytearray);
    }

    protected void processGreeting(Response response)
        throws ProtocolException
    {
        super.processGreeting(response);
        if(response.isOK())
        {
            setCapabilities(response);
            return;
        }
        if(((IMAPResponse)response).keyEquals("PREAUTH"))
        {
            authenticated = true;
            setCapabilities(response);
            return;
        } else
        {
            throw new ConnectionException(this, response);
        }
    }

    public boolean processIdleResponse(Response response)
        throws ProtocolException
    {
        notifyResponseHandlers(new Response[] {
            response
        });
        boolean flag = response.isBYE();
        boolean flag1 = false;
        if(flag)
            flag1 = true;
        if(response.isTagged() && response.getTag().equals(idleTag))
            flag1 = true;
        if(flag1)
            idleTag = null;
        handleResult(response);
        return !flag1;
    }

    public void proxyauth(String s)
        throws ProtocolException
    {
        Argument argument = new Argument();
        argument.writeString(s);
        simpleCommand("PROXYAUTH", argument);
    }

    public Response readIdleResponse()
    {
        this;
        JVM INSTR monitorenter ;
        String s = idleTag;
        if(s != null) goto _L2; else goto _L1
_L1:
        Response response1 = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return response1;
_L2:
        Response response2 = readResponse();
        response1 = response2;
        continue; /* Loop/switch isn't completed */
        IOException ioexception;
        ioexception;
        response1 = Response.byeResponse(ioexception);
        continue; /* Loop/switch isn't completed */
        ProtocolException protocolexception;
        protocolexception;
        Response response = Response.byeResponse(protocolexception);
        response1 = response;
        if(true) goto _L4; else goto _L3
_L3:
        Exception exception;
        exception;
        throw exception;
    }

    public Response readResponse()
        throws IOException, ProtocolException
    {
        return IMAPResponse.readResponse(this);
    }

    public void rename(String s, String s1)
        throws ProtocolException
    {
        String s2 = BASE64MailboxEncoder.encode(s);
        String s3 = BASE64MailboxEncoder.encode(s1);
        Argument argument = new Argument();
        argument.writeString(s2);
        argument.writeString(s3);
        simpleCommand("RENAME", argument);
    }

    public void sasllogin(String as[], String s, String s1, String s2, String s3)
        throws ProtocolException
    {
        if(saslAuthenticator != null) goto _L2; else goto _L1
_L1:
        Constructor constructor;
        Object aobj[];
        Class class1 = Class.forName("com.sun.mail.imap.protocol.IMAPSaslAuthenticator");
        Class aclass[] = new Class[6];
        aclass[0] = com/sun/mail/imap/protocol/IMAPProtocol;
        aclass[1] = java/lang/String;
        aclass[2] = java/util/Properties;
        aclass[3] = Boolean.TYPE;
        aclass[4] = java/io/PrintStream;
        aclass[5] = java/lang/String;
        constructor = class1.getConstructor(aclass);
        aobj = new Object[6];
        aobj[0] = this;
        aobj[1] = name;
        aobj[2] = props;
        if(!debug) goto _L4; else goto _L3
_L3:
        Boolean boolean1 = Boolean.TRUE;
_L7:
        aobj[3] = boolean1;
        aobj[4] = out;
        aobj[5] = host;
        saslAuthenticator = (SaslAuthenticator)constructor.newInstance(aobj);
_L2:
        Object obj;
        int i;
        if(as == null || as.length <= 0)
            break MISSING_BLOCK_LABEL_301;
        obj = new ArrayList(as.length);
        i = 0;
_L10:
        if(i < as.length) goto _L6; else goto _L5
_L5:
        String as1[] = (String[])((List) (obj)).toArray(new String[((List) (obj)).size()]);
        if(saslAuthenticator.authenticate(as1, s, s1, s2, s3))
            authenticated = true;
_L9:
        return;
_L4:
        boolean1 = Boolean.FALSE;
          goto _L7
        Exception exception;
        exception;
        if(!debug) goto _L9; else goto _L8
_L8:
        out.println((new StringBuilder("IMAP DEBUG: Can't load SASL authenticator: ")).append(exception).toString());
        return;
_L6:
        if(authmechs.contains(as[i]))
            ((List) (obj)).add(as[i]);
        i++;
          goto _L10
        obj = authmechs;
          goto _L5
    }

    public int[] search(SearchTerm searchterm)
        throws ProtocolException, SearchException
    {
        return search("ALL", searchterm);
    }

    public int[] search(MessageSet amessageset[], SearchTerm searchterm)
        throws ProtocolException, SearchException
    {
        return search(MessageSet.toString(amessageset), searchterm);
    }

    public MailboxInfo select(String s)
        throws ProtocolException
    {
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        Response aresponse[] = command("SELECT", argument);
        MailboxInfo mailboxinfo = new MailboxInfo(aresponse);
        notifyResponseHandlers(aresponse);
        Response response = aresponse[-1 + aresponse.length];
        if(response.isOK())
            if(response.toString().indexOf("READ-ONLY") != -1)
                mailboxinfo.mode = 1;
            else
                mailboxinfo.mode = 2;
        handleResult(response);
        return mailboxinfo;
    }

    public void setACL(String s, char c, ACL acl)
        throws ProtocolException
    {
        if(!hasCapability("ACL"))
            throw new BadCommandException("ACL not supported");
        String s1 = BASE64MailboxEncoder.encode(s);
        Argument argument = new Argument();
        argument.writeString(s1);
        argument.writeString(acl.getName());
        String s2 = acl.getRights().toString();
        if(c == '+' || c == '-')
            s2 = (new StringBuilder(String.valueOf(c))).append(s2).toString();
        argument.writeString(s2);
        Response aresponse[] = command("SETACL", argument);
        Response response = aresponse[-1 + aresponse.length];
        notifyResponseHandlers(aresponse);
        handleResult(response);
    }

    protected void setCapabilities(Response response)
    {
        byte byte0;
        do
            byte0 = response.readByte();
        while(byte0 > 0 && byte0 != 91);
        while(byte0 == 0 || !response.readAtom().equalsIgnoreCase("CAPABILITY")) 
            return;
        capabilities = new HashMap(10);
        authmechs = new ArrayList(5);
        parseCapabilities(response);
    }

    public void setQuota(Quota quota)
        throws ProtocolException
    {
        Argument argument;
        Argument argument1;
        if(!hasCapability("QUOTA"))
            throw new BadCommandException("QUOTA not supported");
        argument = new Argument();
        argument.writeString(quota.quotaRoot);
        argument1 = new Argument();
        if(quota.resources == null) goto _L2; else goto _L1
_L1:
        int i = 0;
_L5:
        if(i < quota.resources.length) goto _L3; else goto _L2
_L2:
        argument.writeArgument(argument1);
        Response aresponse[] = command("SETQUOTA", argument);
        Response response = aresponse[-1 + aresponse.length];
        notifyResponseHandlers(aresponse);
        handleResult(response);
        return;
_L3:
        argument1.writeAtom(quota.resources[i].name);
        argument1.writeNumber(quota.resources[i].limit);
        i++;
        if(true) goto _L5; else goto _L4
_L4:
    }

    public void startTLS()
        throws ProtocolException
    {
        try
        {
            super.startTLS("STARTTLS");
            return;
        }
        catch(ProtocolException protocolexception)
        {
            throw protocolexception;
        }
        catch(Exception exception)
        {
            Response aresponse[] = new Response[1];
            aresponse[0] = Response.byeResponse(exception);
            notifyResponseHandlers(aresponse);
            disconnect();
            return;
        }
    }

    public Status status(String s, String as[])
        throws ProtocolException
    {
        Argument argument;
        Argument argument1;
        int i;
        int j;
        if(!isREV1() && !hasCapability("IMAP4SUNVERSION"))
            throw new BadCommandException("STATUS not supported");
        String s1 = BASE64MailboxEncoder.encode(s);
        argument = new Argument();
        argument.writeString(s1);
        argument1 = new Argument();
        if(as == null)
            as = Status.standardItems;
        i = 0;
        j = as.length;
_L5:
        if(i < j) goto _L2; else goto _L1
_L1:
        Response aresponse[];
        Response response;
        boolean flag;
        Status status1;
        argument.writeArgument(argument1);
        aresponse = command("STATUS", argument);
        response = aresponse[-1 + aresponse.length];
        flag = response.isOK();
        status1 = null;
        if(!flag) goto _L4; else goto _L3
_L3:
        int k;
        int l;
        k = 0;
        l = aresponse.length;
_L6:
        if(k < l)
            break MISSING_BLOCK_LABEL_167;
_L4:
        notifyResponseHandlers(aresponse);
        handleResult(response);
        return status1;
_L2:
        argument1.writeAtom(as[i]);
        i++;
          goto _L5
        if(aresponse[k] instanceof IMAPResponse)
        {
            IMAPResponse imapresponse = (IMAPResponse)aresponse[k];
            if(imapresponse.keyEquals("STATUS"))
            {
                if(status1 == null)
                    status1 = new Status(imapresponse);
                else
                    Status.add(status1, new Status(imapresponse));
                aresponse[k] = null;
            }
        }
        k++;
          goto _L6
    }

    public void storeFlags(int i, int j, Flags flags, boolean flag)
        throws ProtocolException
    {
        storeFlags((new StringBuilder(String.valueOf(String.valueOf(i)))).append(":").append(String.valueOf(j)).toString(), flags, flag);
    }

    public void storeFlags(int i, Flags flags, boolean flag)
        throws ProtocolException
    {
        storeFlags(String.valueOf(i), flags, flag);
    }

    public void storeFlags(MessageSet amessageset[], Flags flags, boolean flag)
        throws ProtocolException
    {
        storeFlags(MessageSet.toString(amessageset), flags, flag);
    }

    public void subscribe(String s)
        throws ProtocolException
    {
        Argument argument = new Argument();
        argument.writeString(BASE64MailboxEncoder.encode(s));
        simpleCommand("SUBSCRIBE", argument);
    }

    protected boolean supportsNonSyncLiterals()
    {
        return hasCapability("LITERAL+");
    }

    public void uidexpunge(UIDSet auidset[])
        throws ProtocolException
    {
        if(!hasCapability("UIDPLUS"))
        {
            throw new BadCommandException("UID EXPUNGE not supported");
        } else
        {
            simpleCommand((new StringBuilder("UID EXPUNGE ")).append(UIDSet.toString(auidset)).toString(), null);
            return;
        }
    }

    public void unsubscribe(String s)
        throws ProtocolException
    {
        Argument argument = new Argument();
        argument.writeString(BASE64MailboxEncoder.encode(s));
        simpleCommand("UNSUBSCRIBE", argument);
    }

    private static final byte CRLF[] = {
        13, 10
    };
    private static final byte DONE[] = {
        68, 79, 78, 69, 13, 10
    };
    private boolean authenticated;
    private List authmechs;
    private ByteArray ba;
    private Map capabilities;
    private boolean connected;
    private String idleTag;
    private String name;
    private boolean rev1;
    private SaslAuthenticator saslAuthenticator;
    private String searchCharsets[];

}
