// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package com.sun.mail.smtp;

import com.sun.mail.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

// Referenced classes of package com.sun.mail.smtp:
//            DigestMD5, SMTPSendFailedException, SMTPOutputStream, SMTPMessage, 
//            SMTPAddressFailedException, SMTPAddressSucceededException

public class SMTPTransport extends Transport
{

    public SMTPTransport(Session session, URLName urlname)
    {
        this(session, urlname, "smtp", 25, false);
    }

    protected SMTPTransport(Session session, URLName urlname, String s, int i, boolean flag)
    {
        boolean flag1 = true;
        super(session, urlname);
        name = "smtp";
        defaultPort = 25;
        isSSL = false;
        sendPartiallyFailed = false;
        quitWait = false;
        saslRealm = "UNKNOWN";
        if(urlname != null)
            s = urlname.getProtocol();
        name = s;
        defaultPort = i;
        isSSL = flag;
        out = session.getDebugOut();
        String s1 = session.getProperty((new StringBuilder("mail.")).append(s).append(".quitwait").toString());
        boolean flag2;
        String s2;
        boolean flag3;
        String s3;
        boolean flag4;
        String s4;
        if(s1 != null && !s1.equalsIgnoreCase("true"))
            flag2 = false;
        else
            flag2 = flag1;
        quitWait = flag2;
        s2 = session.getProperty((new StringBuilder("mail.")).append(s).append(".reportsuccess").toString());
        if(s2 != null && s2.equalsIgnoreCase("true"))
            flag3 = flag1;
        else
            flag3 = false;
        reportSuccess = flag3;
        s3 = session.getProperty((new StringBuilder("mail.")).append(s).append(".starttls.enable").toString());
        if(s3 != null && s3.equalsIgnoreCase("true"))
            flag4 = flag1;
        else
            flag4 = false;
        useStartTLS = flag4;
        s4 = session.getProperty((new StringBuilder("mail.")).append(s).append(".userset").toString());
        if(s4 == null || !s4.equalsIgnoreCase("true"))
            flag1 = false;
        useRset = flag1;
    }

    private void closeConnection()
        throws MessagingException
    {
        if(serverSocket != null)
            serverSocket.close();
        serverSocket = null;
        serverOutput = null;
        serverInput = null;
        lineInputStream = null;
        if(super.isConnected())
            super.close();
        return;
        IOException ioexception;
        ioexception;
        throw new MessagingException("Server Close Failed", ioexception);
        Exception exception1;
        exception1;
        serverSocket = null;
        serverOutput = null;
        serverInput = null;
        lineInputStream = null;
        if(super.isConnected())
            super.close();
        throw exception1;
    }

    private boolean convertTo8Bit(MimePart mimepart)
    {
        boolean flag = false;
        boolean flag1 = mimepart.isMimeType("text/*");
        flag = false;
        if(!flag1) goto _L2; else goto _L1
_L1:
        String s = mimepart.getEncoding();
        flag = false;
        if(s == null) goto _L4; else goto _L3
_L3:
        if(s.equalsIgnoreCase("quoted-printable")) goto _L6; else goto _L5
_L5:
        boolean flag3 = s.equalsIgnoreCase("base64");
        flag = false;
        if(!flag3) goto _L4; else goto _L6
_L6:
        boolean flag2 = is8Bit(mimepart.getInputStream());
        flag = false;
        if(!flag2) goto _L4; else goto _L7
_L7:
        mimepart.setContent(mimepart.getContent(), mimepart.getContentType());
        mimepart.setHeader("Content-Transfer-Encoding", "8bit");
        return true;
_L2:
        boolean flag4 = mimepart.isMimeType("multipart/*");
        flag = false;
        if(!flag4) goto _L4; else goto _L8
_L8:
        MimeMultipart mimemultipart;
        int i;
        mimemultipart = (MimeMultipart)mimepart.getContent();
        i = mimemultipart.getCount();
        int j = 0;
_L9:
        if(j >= i)
            break; /* Loop/switch isn't completed */
        boolean flag5 = convertTo8Bit((MimePart)mimemultipart.getBodyPart(j));
        if(flag5)
            flag = true;
        j++;
        if(true) goto _L9; else goto _L4
        MessagingException messagingexception;
        messagingexception;
        return flag;
        IOException ioexception;
        ioexception;
_L4:
        return flag;
    }

    private void expandGroups()
    {
        Vector vector;
        int i;
        vector = null;
        i = 0;
_L8:
        InternetAddress internetaddress;
        if(i >= addresses.length)
        {
            if(vector != null)
            {
                InternetAddress ainternetaddress1[] = new InternetAddress[vector.size()];
                vector.copyInto(ainternetaddress1);
                addresses = ainternetaddress1;
            }
            return;
        }
        internetaddress = (InternetAddress)addresses[i];
        if(!internetaddress.isGroup())
            break MISSING_BLOCK_LABEL_160;
        if(vector != null) goto _L2; else goto _L1
_L1:
        int j;
        vector = new Vector();
        j = 0;
_L9:
        if(j < i) goto _L3; else goto _L2
_L2:
        InternetAddress ainternetaddress[] = internetaddress.getGroup(true);
        if(ainternetaddress == null) goto _L5; else goto _L4
_L4:
        int k = 0;
_L10:
        int l = ainternetaddress.length;
        if(k < l) goto _L7; else goto _L6
_L6:
        i++;
          goto _L8
_L3:
        vector.addElement(addresses[j]);
        j++;
          goto _L9
_L7:
        vector.addElement(ainternetaddress[k]);
        k++;
          goto _L10
_L5:
        try
        {
            vector.addElement(internetaddress);
        }
        catch(ParseException parseexception)
        {
            vector.addElement(internetaddress);
        }
          goto _L6
        if(vector != null)
            vector.addElement(internetaddress);
          goto _L6
    }

    private DigestMD5 getMD5()
    {
        this;
        JVM INSTR monitorenter ;
        if(md5support != null) goto _L2; else goto _L1
_L1:
        PrintStream printstream;
        if(!debug)
            break MISSING_BLOCK_LABEL_42;
        printstream = out;
_L3:
        md5support = new DigestMD5(printstream);
_L2:
        DigestMD5 digestmd5 = md5support;
        this;
        JVM INSTR monitorexit ;
        return digestmd5;
        printstream = null;
          goto _L3
        Exception exception1;
        exception1;
        throw exception1;
    }

    private void initStreams()
        throws IOException
    {
        Properties properties = session.getProperties();
        PrintStream printstream = session.getDebugOut();
        boolean flag = session.getDebug();
        String s = properties.getProperty("mail.debug.quote");
        boolean flag1;
        TraceInputStream traceinputstream;
        TraceOutputStream traceoutputstream;
        if(s != null && s.equalsIgnoreCase("true"))
            flag1 = true;
        else
            flag1 = false;
        traceinputstream = new TraceInputStream(serverSocket.getInputStream(), printstream);
        traceinputstream.setTrace(flag);
        traceinputstream.setQuote(flag1);
        traceoutputstream = new TraceOutputStream(serverSocket.getOutputStream(), printstream);
        traceoutputstream.setTrace(flag);
        traceoutputstream.setQuote(flag1);
        serverOutput = new BufferedOutputStream(traceoutputstream);
        serverInput = new BufferedInputStream(traceinputstream);
        lineInputStream = new LineInputStream(serverInput);
    }

    private boolean is8Bit(InputStream inputstream)
    {
        int i = 0;
        boolean flag = false;
        do
        {
            int j;
            int k;
            try
            {
                j = inputstream.read();
            }
            catch(IOException ioexception)
            {
                return false;
            }
            if(j < 0)
            {
                if(debug && flag)
                    out.println("DEBUG SMTP: found an 8bit part");
                return flag;
            }
            k = j & 0xff;
            if(k == 13 || k == 10)
            {
                i = 0;
            } else
            {
                if(k == 0)
                    return false;
                if(++i > 998)
                    return false;
            }
            if(k > 127)
                flag = true;
        } while(true);
    }

    private boolean isNotLastLine(String s)
    {
        return s != null && s.length() >= 4 && s.charAt(3) == '-';
    }

    private void issueSendCommand(String s, int i)
        throws MessagingException
    {
        sendCommand(s);
        int j = readServerResponse();
        if(j != i)
        {
            int k;
            int l;
            Address aaddress[];
            String s1;
            int i1;
            if(validSentAddr == null)
                k = 0;
            else
                k = validSentAddr.length;
            if(validUnsentAddr == null)
                l = 0;
            else
                l = validUnsentAddr.length;
            aaddress = new Address[k + l];
            if(k > 0)
                System.arraycopy(validSentAddr, 0, aaddress, 0, k);
            if(l > 0)
                System.arraycopy(validUnsentAddr, 0, aaddress, k, l);
            validSentAddr = null;
            validUnsentAddr = aaddress;
            if(debug)
                out.println((new StringBuilder("DEBUG SMTP: got response code ")).append(j).append(", with response: ").append(lastServerResponse).toString());
            s1 = lastServerResponse;
            i1 = lastReturnCode;
            if(serverSocket != null)
                issueCommand("RSET", 250);
            lastServerResponse = s1;
            lastReturnCode = i1;
            throw new SMTPSendFailedException(s, j, lastServerResponse, exception, validSentAddr, validUnsentAddr, invalidAddr);
        } else
        {
            return;
        }
    }

    private String normalizeAddress(String s)
    {
        if(!s.startsWith("<") && !s.endsWith(">"))
            s = (new StringBuilder("<")).append(s).append(">").toString();
        return s;
    }

    private void openServer()
        throws MessagingException
    {
        int i;
        String s;
        i = -1;
        s = "UNKNOWN";
        int j;
        try
        {
            i = serverSocket.getPort();
            s = serverSocket.getInetAddress().getHostName();
            if(debug)
                out.println((new StringBuilder("DEBUG SMTP: starting protocol to host \"")).append(s).append("\", port ").append(i).toString());
            initStreams();
            j = readServerResponse();
        }
        catch(IOException ioexception)
        {
            throw new MessagingException((new StringBuilder("Could not start protocol to SMTP host: ")).append(s).append(", port: ").append(i).toString(), ioexception);
        }
        if(j == 220)
            break MISSING_BLOCK_LABEL_251;
        serverSocket.close();
        serverSocket = null;
        serverOutput = null;
        serverInput = null;
        lineInputStream = null;
        if(debug)
            out.println((new StringBuilder("DEBUG SMTP: got bad greeting from host \"")).append(s).append("\", port: ").append(i).append(", response: ").append(j).append("\n").toString());
        throw new MessagingException((new StringBuilder("Got bad greeting from SMTP host: ")).append(s).append(", port: ").append(i).append(", response: ").append(j).toString());
        if(debug)
            out.println((new StringBuilder("DEBUG SMTP: protocol started to host \"")).append(s).append("\", port: ").append(i).append("\n").toString());
        return;
    }

    private void openServer(String s, int i)
        throws MessagingException
    {
        if(debug)
            out.println((new StringBuilder("DEBUG SMTP: trying to connect to host \"")).append(s).append("\", port ").append(i).append(", isSSL ").append(isSSL).toString());
        int j;
        try
        {
            serverSocket = SocketFetcher.getSocket(s, i, session.getProperties(), (new StringBuilder("mail.")).append(name).toString(), isSSL);
            i = serverSocket.getPort();
            initStreams();
            j = readServerResponse();
        }
        catch(UnknownHostException unknownhostexception)
        {
            throw new MessagingException((new StringBuilder("Unknown SMTP host: ")).append(s).toString(), unknownhostexception);
        }
        catch(IOException ioexception)
        {
            throw new MessagingException((new StringBuilder("Could not connect to SMTP host: ")).append(s).append(", port: ").append(i).toString(), ioexception);
        }
        if(j == 220)
            break MISSING_BLOCK_LABEL_279;
        serverSocket.close();
        serverSocket = null;
        serverOutput = null;
        serverInput = null;
        lineInputStream = null;
        if(debug)
            out.println((new StringBuilder("DEBUG SMTP: could not connect to host \"")).append(s).append("\", port: ").append(i).append(", response: ").append(j).append("\n").toString());
        throw new MessagingException((new StringBuilder("Could not connect to SMTP host: ")).append(s).append(", port: ").append(i).append(", response: ").append(j).toString());
        if(debug)
            out.println((new StringBuilder("DEBUG SMTP: connected to host \"")).append(s).append("\", port: ").append(i).append("\n").toString());
        return;
    }

    private void sendCommand(byte abyte0[])
        throws MessagingException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
            throw new AssertionError();
        try
        {
            serverOutput.write(abyte0);
            serverOutput.write(CRLF);
            serverOutput.flush();
            return;
        }
        catch(IOException ioexception)
        {
            throw new MessagingException("Can't send command to SMTP host", ioexception);
        }
    }

    protected static String xtext(String s)
    {
        StringBuffer stringbuffer;
        int i;
        stringbuffer = null;
        i = 0;
_L2:
        char c;
        if(i >= s.length())
        {
            if(stringbuffer != null)
                s = stringbuffer.toString();
            return s;
        }
        c = s.charAt(i);
        if(c >= '\200')
            throw new IllegalArgumentException((new StringBuilder("Non-ASCII character in SMTP submitter: ")).append(s).toString());
        if(c >= '!' && c <= '~' && c != '+' && c != '=')
            break; /* Loop/switch isn't completed */
        if(stringbuffer == null)
        {
            stringbuffer = new StringBuffer(4 + s.length());
            stringbuffer.append(s.substring(0, i));
        }
        stringbuffer.append('+');
        stringbuffer.append(hexchar[(c & 0xf0) >> 4]);
        stringbuffer.append(hexchar[c & 0xf]);
_L4:
        i++;
        if(true) goto _L2; else goto _L1
_L1:
        if(stringbuffer == null) goto _L4; else goto _L3
_L3:
        stringbuffer.append(c);
          goto _L4
    }

    protected void checkConnected()
    {
        if(!super.isConnected())
            throw new IllegalStateException("Not connected");
        else
            return;
    }

    public void close()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = super.isConnected();
        if(flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        int i;
        if(serverSocket == null)
            break MISSING_BLOCK_LABEL_80;
        sendCommand("QUIT");
        if(!quitWait)
            break MISSING_BLOCK_LABEL_80;
        i = readServerResponse();
        if(i == 221 || i == -1)
            break MISSING_BLOCK_LABEL_80;
        out.println((new StringBuilder("DEBUG SMTP: QUIT failed with ")).append(i).toString());
        closeConnection();
        if(true) goto _L1; else goto _L3
_L3:
        Exception exception1;
        exception1;
        throw exception1;
        Exception exception2;
        exception2;
        closeConnection();
        throw exception2;
    }

    public void connect(Socket socket)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        serverSocket = socket;
        super.connect();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception1;
        exception1;
        throw exception1;
    }

    protected OutputStream data()
        throws MessagingException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
        {
            throw new AssertionError();
        } else
        {
            issueSendCommand("DATA", 354);
            dataStream = new SMTPOutputStream(serverOutput);
            return dataStream;
        }
    }

    protected boolean ehlo(String s)
        throws MessagingException
    {
        boolean flag;
        String s2;
        String s1;
        int i;
        BufferedReader bufferedreader;
        boolean flag1;
        if(s != null)
            s1 = (new StringBuilder("EHLO ")).append(s).toString();
        else
            s1 = "EHLO";
        sendCommand(s1);
        i = readServerResponse();
        if(i != 250) goto _L2; else goto _L1
_L1:
        bufferedreader = new BufferedReader(new StringReader(lastServerResponse));
        extMap = new Hashtable();
        flag = true;
_L6:
        s2 = bufferedreader.readLine();
        if(s2 != null) goto _L3; else goto _L2
_L2:
        flag1 = false;
        if(i == 250)
            flag1 = true;
        return flag1;
_L3:
        if(!flag) goto _L5; else goto _L4
_L4:
        flag = false;
          goto _L6
_L5:
        if(s2.length() < 5) goto _L6; else goto _L7
_L7:
        String s3;
        int j;
        s3 = s2.substring(4);
        j = s3.indexOf(' ');
        String s4;
        s4 = "";
        if(j <= 0)
            break MISSING_BLOCK_LABEL_176;
        s4 = s3.substring(j + 1);
        s3 = s3.substring(0, j);
        if(debug)
            out.println((new StringBuilder("DEBUG SMTP: Found extension \"")).append(s3).append("\", arg \"").append(s4).append("\"").toString());
        extMap.put(s3.toUpperCase(Locale.ENGLISH), s4);
          goto _L6
        IOException ioexception;
        ioexception;
          goto _L2
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        try
        {
            closeConnection();
            return;
        }
        catch(MessagingException messagingexception)
        {
            return;
        }
    }

    protected void finishData()
        throws IOException, MessagingException
    {
        if(!$assertionsDisabled && !Thread.holdsLock(this))
        {
            throw new AssertionError();
        } else
        {
            dataStream.ensureAtBOL();
            issueSendCommand(".", 250);
            return;
        }
    }

    public String getExtensionParameter(String s)
    {
        if(extMap == null)
            return null;
        else
            return (String)extMap.get(s.toUpperCase(Locale.ENGLISH));
    }

    public int getLastReturnCode()
    {
        this;
        JVM INSTR monitorenter ;
        int i = lastReturnCode;
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception1;
        exception1;
        throw exception1;
    }

    public String getLastServerResponse()
    {
        this;
        JVM INSTR monitorenter ;
        String s = lastServerResponse;
        this;
        JVM INSTR monitorexit ;
        return s;
        Exception exception1;
        exception1;
        throw exception1;
    }

    public String getLocalHost()
    {
        this;
        JVM INSTR monitorenter ;
        Exception exception1;
        String s;
        try
        {
            if(localHostName == null || localHostName.length() <= 0)
                localHostName = session.getProperty((new StringBuilder("mail.")).append(name).append(".localhost").toString());
            if(localHostName == null || localHostName.length() <= 0)
                localHostName