// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.*;

// Referenced classes of package javax.mail.internet:
//            UniqueValue, ContentType, SharedInputStream, InternetHeaders, 
//            MimeBodyPart

public class MimeMultipart extends Multipart
{

    public MimeMultipart()
    {
        this("mixed");
    }

    public MimeMultipart(String s)
    {
        ds = null;
        parsed = true;
        complete = true;
        preamble = null;
        String s1 = UniqueValue.getUniqueBoundaryValue();
        ContentType contenttype = new ContentType("multipart", s, null);
        contenttype.setParameter("boundary", s1);
        contentType = contenttype.toString();
    }

    public MimeMultipart(DataSource datasource)
        throws MessagingException
    {
        ds = null;
        parsed = true;
        complete = true;
        preamble = null;
        if(datasource instanceof MessageAware)
            setParent(((MessageAware)datasource).getMessageContext().getPart());
        if(datasource instanceof MultipartDataSource)
        {
            setMultipartDataSource((MultipartDataSource)datasource);
            return;
        } else
        {
            parsed = false;
            ds = datasource;
            contentType = datasource.getContentType();
            return;
        }
    }

    private void parsebm()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = parsed;
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        long l;
        long l1;
        l = 0L;
        l1 = 0L;
        Object obj;
        BufferedInputStream bufferedinputstream;
        obj = ds.getInputStream();
        if((obj instanceof ByteArrayInputStream) || (obj instanceof BufferedInputStream) || (obj instanceof SharedInputStream))
            break MISSING_BLOCK_LABEL_69;
        bufferedinputstream = new BufferedInputStream(((InputStream) (obj)));
        obj = bufferedinputstream;
        boolean flag1 = obj instanceof SharedInputStream;
        SharedInputStream sharedinputstream;
        sharedinputstream = null;
        if(!flag1)
            break MISSING_BLOCK_LABEL_91;
        sharedinputstream = (SharedInputStream)obj;
        String s = (new ContentType(contentType)).getParameter("boundary");
        if(s == null) goto _L4; else goto _L3
_L3:
        String s1 = (new StringBuilder("--")).append(s).toString();
        String s2 = s1;
_L10:
        LineInputStream lineinputstream = new LineInputStream(((InputStream) (obj)));
        StringBuffer stringbuffer;
        String s3;
        stringbuffer = null;
        s3 = null;
_L16:
        String s4 = lineinputstream.readLine();
        if(s4 != null) goto _L6; else goto _L5
_L5:
        if(s4 != null) goto _L8; else goto _L7
_L7:
        throw new MessagingException("Missing start boundary");
        IOException ioexception1;
        ioexception1;
        MessagingException messagingexception1 = new MessagingException("IO Error", ioexception1);
        throw messagingexception1;
        Exception exception2;
        exception2;
        Exception exception;
        Exception exception1;
        int i;
        int j;
        int k;
        String s5;
        int j1;
        int i2;
        boolean flag3;
        int l2;
        int k3;
        int k4;
        char c;
        boolean flag4;
        try
        {
            ((InputStream) (obj)).close();
        }
        catch(IOException ioexception) { }
        throw exception2;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        exception1;
        MessagingException messagingexception = new MessagingException("No inputstream from datasource", exception1);
        throw messagingexception;
_L4:
        flag4 = ignoreMissingBoundaryParameter;
        s2 = null;
        if(flag4) goto _L10; else goto _L9
_L9:
        throw new MessagingException("Missing boundary parameter");
_L6:
        i = -1 + s4.length();
          goto _L11
_L54:
        j = i + 1;
        s4 = s4.substring(0, j);
        if(s2 == null) goto _L13; else goto _L12
_L12:
        if(s4.equals(s2)) goto _L5; else goto _L14
_L14:
        k = s4.length();
        if(k <= 0) goto _L16; else goto _L15
_L15:
        if(s3 != null)
            break MISSING_BLOCK_LABEL_328;
        s5 = System.getProperty("line.separator", "\n");
        s3 = s5;
_L56:
        if(stringbuffer != null)
            break MISSING_BLOCK_LABEL_353;
        int i1 = 2 + s4.length();
        stringbuffer = new StringBuffer(i1);
        stringbuffer.append(s4).append(s3);
          goto _L16
_L55:
        c = s4.charAt(i);
          goto _L17
_L13:
        if(!s4.startsWith("--")) goto _L14; else goto _L18
_L18:
        s2 = s4;
          goto _L5
_L8:
        if(stringbuffer == null)
            break MISSING_BLOCK_LABEL_426;
        preamble = stringbuffer.toString();
        abyte0 = ASCIIUtility.getBytes(s2);
        j1 = abyte0.length;
        ai = new int[256];
        k1 = 0;
_L25:
        if(k1 < j1) goto _L20; else goto _L19
_L19:
        ai1 = new int[j1];
        i2 = j1;
_L60:
        if(i2 > 0) goto _L22; else goto _L21
_L21:
        ai1[j1 - 1] = 1;
        flag2 = false;
_L38:
        if(!flag2) goto _L24; else goto _L23
_L23:
        try
        {
            ((InputStream) (obj)).close();
        }
        catch(IOException ioexception2) { }
        parsed = true;
          goto _L1
_L20:
        ai[abyte0[k1]] = k1 + 1;
        k1++;
          goto _L25
_L58:
        if(abyte0[k4] != abyte0[k4 - i2]) goto _L27; else goto _L26
_L26:
        ai1[k4 - 1] = i2;
        k4--;
        continue; /* Loop/switch isn't completed */
_L59:
        k4--;
        ai1[k4] = i2;
        break; /* Loop/switch isn't completed */
_L24:
        if(sharedinputstream == null)
            break MISSING_BLOCK_LABEL_626;
        l = sharedinputstream.getPosition();
_L29:
        s6 = lineinputstream.readLine();
        if(s6 == null)
            break; /* Loop/switch isn't completed */
        if(s6.length() > 0) goto _L29; else goto _L28
_L28:
        internetheaders = null;
        if(s6 != null)
            break MISSING_BLOCK_LABEL_634;
        if(!ignoreMissingEndBoundary)
            throw new MessagingException("missing multipart end boundary");
        complete = false;
          goto _L23
        internetheaders = createInternetHeaders(((InputStream) (obj)));
        if(!((InputStream) (obj)).markSupported())
            throw new MessagingException("Stream doesn't support mark");
        if(sharedinputstream != null) goto _L31; else goto _L30
_L30:
        bytearrayoutputstream = new ByteArrayOutputStream();
_L35:
        abyte1 = new byte[j1];
        abyte2 = new byte[j1];
        j2 = 0;
        flag3 = true;
_L65:
        k2 = 1000 + (j1 + 4);
        ((InputStream) (obj)).mark(k2);
        l2 = 0;
        i3 = readFully(((InputStream) (obj)), abyte1, 0, j1);
        if(i3 >= j1) goto _L33; else goto _L32
_L32:
        if(!ignoreMissingEndBoundary)
            throw new MessagingException("missing multipart end boundary");
          goto _L34
_L31:
        l1 = sharedinputstream.getPosition();
        bytearrayoutputstream = null;
          goto _L35
_L34:
        if(sharedinputstream == null)
            break MISSING_BLOCK_LABEL_768;
        l1 = sharedinputstream.getPosition();
        complete = false;
        flag2 = true;
_L49:
        if(sharedinputstream == null) goto _L37; else goto _L36
_L36:
        mimebodypart = createMimeBodyPart(sharedinputstream.newStream(l, l1));
_L53:
        super.addBodyPart(mimebodypart);
          goto _L38
_L61:
        if(k3 >= 0) goto _L40; else goto _L39
_L39:
        l2 = 0;
        if(flag3) goto _L42; else goto _L41
_L41:
        byte0 = abyte2[j2 - 1];
        if(byte0 == 13) goto _L44; else goto _L43
_L43:
        l2 = 0;
        if(byte0 != 10) goto _L42; else goto _L44
_L44:
        l2 = 1;
        if(byte0 != 10 || j2 < 2) goto _L42; else goto _L45
_L45:
        if(abyte2[j2 - 2] == 13)
            l2 = 2;
          goto _L42
_L63:
        if(sharedinputstream == null)
            break MISSING_BLOCK_LABEL_901;
        l1 = sharedinputstream.getPosition() - (long)j1 - (long)l2;
        l3 = ((InputStream) (obj)).read();
        if(l3 != 45) goto _L47; else goto _L46
_L46:
        if(((InputStream) (obj)).read() != 45) goto _L47; else goto _L48
_L48:
        complete = true;
        flag2 = true;
          goto _L49
_L62:
        if(abyte1[k3] != abyte0[k3])
            break; /* Loop/switch isn't completed */
        k3--;
        continue; /* Loop/switch isn't completed */
_L51:
        l3 = ((InputStream) (obj)).read();
_L47:
        if(l3 == 32 || l3 == 9) goto _L51; else goto _L50
_L50:
        if(l3 == 10) goto _L49; else goto _L52
_L52:
        if(l3 != 13)
            break; /* Loop/switch isn't completed */
        ((InputStream) (obj)).mark(1);
        if(((InputStream) (obj)).read() != 10)
            ((InputStream) (obj)).reset();
          goto _L49
_L40:
        i4 = Math.max((k3 + 1) - ai[0x7f & abyte1[k3]], ai1[k3]);
        if(i4 >= 2)
            break MISSING_BLOCK_LABEL_1130;
        if(sharedinputstream != null || j2 <= 1)
            break MISSING_BLOCK_LABEL_1073;
        j4 = j2 - 1;
        bytearrayoutputstream.write(abyte2, 0, j4);
        ((InputStream) (obj)).reset();
        skipFully(((InputStream) (obj)), 1L);
        if(j2 < 1)
            break MISSING_BLOCK_LABEL_1116;
        abyte2[0] = abyte2[j2 - 1];
        abyte2[1] = abyte1[0];
        j2 = 2;
        break MISSING_BLOCK_LABEL_1348;
        abyte2[0] = abyte1[0];
        j2 = 1;
        break MISSING_BLOCK_LABEL_1348;
        if(j2 <= 0 || sharedinputstream != null)
            break MISSING_BLOCK_LABEL_1150;
        bytearrayoutputstream.write(abyte2, 0, j2);
        j2 = i4;
        ((InputStream) (obj)).reset();
        l4 = j2;
        skipFully(((InputStream) (obj)), l4);
        byte abyte4[] = abyte1;
        abyte1 = abyte2;
        abyte2 = abyte4;
        break MISSING_BLOCK_LABEL_1348;
_L37:
        if(j2 - l2 <= 0)
            break MISSING_BLOCK_LABEL_1212;
        j3 = j2 - l2;
        bytearrayoutputstream.write(abyte2, 0, j3);
        if(complete || i3 <= 0)
            break MISSING_BLOCK_LABEL_1234;
        bytearrayoutputstream.write(abyte1, 0, i3);
        byte abyte3[] = bytearrayoutputstream.toByteArray();
        mimebodypart1 = createMimeBodyPart(internetheaders, abyte3);
        mimebodypart = mimebodypart1;
          goto _L53
_L11:
        if(i >= 0) goto _L55; else goto _L54
_L17:
        byte abyte0[];
        int ai[];
        int k1;
        int ai1[];
        boolean flag2;
        String s6;
        InternetHeaders internetheaders;
        ByteArrayOutputStream bytearrayoutputstream;
        byte abyte1[];
        byte abyte2[];
        int j2;
        int k2;
        int i3;
        MimeBodyPart mimebodypart;
        MimeBodyPart mimebodypart1;
        int j3;
        byte byte0;
        int l3;
        int i4;
        long l4;
        int j4;
        if(c != ' ' && c != '\t')
            break; /* Loop/switch isn't completed */
        i--;
          goto _L11
        SecurityException securityexception;
        securityexception;
        s3 = "\n";
          goto _L56
_L22:
        k4 = j1 - 1;
        if(k4 >= i2) goto _L58; else goto _L57
_L57:
        if(k4 > 0) goto _L59; else goto _L27
_L27:
        i2--;
          goto _L60
_L33:
        k3 = j1 - 1;
        if(k3 >= 0) goto _L62; else goto _L61
_L42:
        if(!flag3 && l2 <= 0) goto _L64; else goto _L63
_L64:
        k3 = 0;
          goto _L40
        flag3 = false;
          goto _L65
    }

    private static int readFully(InputStream inputstream, byte abyte0[], int i, int j)
        throws IOException
    {
        if(j != 0) goto _L2; else goto _L1
_L1:
        int k = 0;
_L5:
        return k;
_L2:
        k = 0;
_L7:
        if(j > 0) goto _L4; else goto _L3
_L3:
        if(k <= 0)
            return -1;
          goto _L5
_L4:
        int l = inputstream.read(abyte0, i, j);
        if(l <= 0) goto _L3; else goto _L6
_L6:
        i += l;
        k += l;
        j -= l;
          goto _L7
    }

    private void skipFully(InputStream inputstream, long l)
        throws IOException
    {
        do
        {
            if(l <= 0L)
                return;
            long l1 = inputstream.skip(l);
            if(l1 <= 0L)
                throw new EOFException("can't skip");
            l -= l1;
        } while(true);
    }

    public void addBodyPart(BodyPart bodypart)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        parse();
        super.addBodyPart(bodypart);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void addBodyPart(BodyPart bodypart, int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        parse();
        super.addBodyPart(bodypart, i);
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected InternetHeaders createInternetHeaders(InputStream inputstream)
        throws MessagingException
    {
        return new InternetHeaders(inputstream);
    }

    protected MimeBodyPart createMimeBodyPart(InputStream inputstream)
        throws MessagingException
    {
        return new MimeBodyPart(inputstream);
    }

    protected MimeBodyPart createMimeBodyPart(InternetHeaders internetheaders, byte abyte0[])
        throws MessagingException
    {
        return new MimeBodyPart(internetheaders, abyte0);
    }

    public BodyPart getBodyPart(int i)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        BodyPart bodypart;
        parse();
        bodypart = super.getBodyPart(i);
        this;
        JVM INSTR monitorexit ;
        return bodypart;
        Exception exception;
        exception;
        throw exception;
    }

    public BodyPart getBodyPart(String s)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        parse();
        i = getCount();
        int j = 0;
_L6:
        if(j < i) goto _L2; else goto _L1
_L1:
        Object obj = null;
_L4:
        this;
        JVM INSTR monitorexit ;
        return ((BodyPart) (obj));
_L2:
        String s1;
        obj = (MimeBodyPart)getBodyPart(j);
        s1 = ((MimeBodyPart) (obj)).getContentID();
        if(s1 == null)
            break; /* Loop/switch isn't completed */
        boolean flag = s1.equals(s);
        if(flag) goto _L4; else goto _L3
_L3:
        j++;
        if(true) goto _L6; else goto _L5
_L5:
        Exception exception;
        exception;
        throw exception;
    }

    public int getCount()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        parse();
        i = super.getCount();
        this;
        JVM INSTR monitorexit ;
        return i;
        Exception exception;
        exception;
        throw exception;
    }

    public String getPreamble()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        String s;
        parse();
        s = preamble;
        this;
        JVM INSTR monitorexit ;
        return s;
        Exception exception;
        exception;
        throw exception;
    }

    public boolean isComplete()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag;
        parse();
        flag = complete;
        this;
        JVM INSTR monitorexit ;
        return flag;
        Exception exception;
        exception;
        throw exception;
    }

    protected void parse()
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        boolean flag = parsed;
        if(!flag) goto _L2; else goto _L1
_L1:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if(!bmparse)
            break MISSING_BLOCK_LABEL_32;
        parsebm();
          goto _L1
        Exception exception;
        exception;
        throw exception;
        long l;
        long l1;
        l = 0L;
        l1 = 0L;
        Object obj;
        BufferedInputStream bufferedinputstream;
        obj = ds.getInputStream();
        if((obj instanceof ByteArrayInputStream) || (obj instanceof BufferedInputStream) || (obj instanceof SharedInputStream))
            break MISSING_BLOCK_LABEL_87;
        bufferedinputstream = new BufferedInputStream(((InputStream) (obj)));
        obj = bufferedinputstream;
        boolean flag1 = obj instanceof SharedInputStream;
        SharedInputStream sharedinputstream;
        sharedinputstream = null;
        if(!flag1)
            break MISSING_BLOCK_LABEL_109;
        sharedinputstream = (SharedInputStream)obj;
        String s = (new ContentType(contentType)).getParameter("boundary");
        if(s == null) goto _L4; else goto _L3
_L3:
        String s1 = (new StringBuilder("--")).append(s).toString();
        String s2 = s1;
_L10:
        LineInputStream lineinputstream = new LineInputStream(((InputStream) (obj)));
        StringBuffer stringbuffer;
        String s3;
        stringbuffer = null;
        s3 = null;
_L16:
        String s4 = lineinputstream.readLine();
        if(s4 != null) goto _L6; else goto _L5
_L5:
        if(s4 != null) goto _L8; else goto _L7
_L7:
        throw new MessagingException("Missing start boundary");
        IOException ioexception1;
        ioexception1;
        MessagingException messagingexception1 = new MessagingException("IO Error", ioexception1);
        throw messagingexception1;
        Exception exception2;
        exception2;
        Exception exception1;
        int i;
        int j;
        int k;
        String s5;
        int j1;
        boolean flag3;
        int k1;
        int i2;
        int j2;
        int i3;
        char c;
        boolean flag4;
        try
        {
            ((InputStream) (obj)).close();
        }
        catch(IOException ioexception) { }
        throw exception2;
        exception1;
        MessagingException messagingexception = new MessagingException("No inputstream from datasource", exception1);
        throw messagingexception;
_L4:
        flag4 = ignoreMissingBoundaryParameter;
        s2 = null;
        if(flag4) goto _L10; else goto _L9
_L9:
        throw new MessagingException("Missing boundary parameter");
_L6:
        i = -1 + s4.length();
          goto _L11
_L51:
        j = i + 1;
        s4 = s4.substring(0, j);
        if(s2 == null) goto _L13; else goto _L12
_L12:
        if(s4.equals(s2)) goto _L5; else goto _L14
_L14:
        k = s4.length();
        if(k <= 0) goto _L16; else goto _L15
_L15:
        if(s3 != null)
            break MISSING_BLOCK_LABEL_341;
        s5 = System.getProperty("line.separator", "\n");
        s3 = s5;
_L53:
        if(stringbuffer != null)
            break MISSING_BLOCK_LABEL_366;
        int i1 = 2 + s4.length();
        stringbuffer = new StringBuffer(i1);
        stringbuffer.append(s4).append(s3);
          goto _L16
_L52:
        c = s4.charAt(i);
          goto _L17
_L13:
        if(!s4.startsWith("--")) goto _L14; else goto _L18
_L18:
        s2 = s4;
          goto _L5
_L8:
        if(stringbuffer == null)
            break MISSING_BLOCK_LABEL_439;
        preamble = stringbuffer.toString();
        abyte0 = ASCIIUtility.getBytes(s2);
        j1 = abyte0.length;
        flag2 = false;
_L36:
        if(!flag2) goto _L20; else goto _L19
_L19:
        try
        {
            ((InputStream) (obj)).close();
        }
        catch(IOException ioexception2) { }
        parsed = true;
          goto _L1
_L20:
        if(sharedinputstream == null)
            break MISSING_BLOCK_LABEL_537;
        l = sharedinputstream.getPosition();
_L22:
        s6 = lineinputstream.readLine();
        if(s6 == null)
            break; /* Loop/switch isn't completed */
        if(s6.length() > 0) goto _L22; else goto _L21
_L21:
        internetheaders = null;
        if(s6 != null)
            break MISSING_BLOCK_LABEL_545;
        if(!ignoreMissingEndBoundary)
            throw new MessagingException("missing multipart end boundary");
        complete = false;
          goto _L19
        internetheaders = createInternetHeaders(((InputStream) (obj)));
        if(!((InputStream) (obj)).markSupported())
            throw new MessagingException("Stream doesn't support mark");
        if(sharedinputstream != null) goto _L24; else goto _L23
_L23:
        bytearrayoutputstream = new ByteArrayOutputStream();
          goto _L25
_L48:
        if(!flag3) goto _L27; else goto _L26
_L26:
        l2 = 1000 + (j1 + 4);
        ((InputStream) (obj)).mark(l2);
        i3 = 0;
          goto _L28
_L54:
        if(i3 != j1) goto _L30; else goto _L29
_L29:
        j3 = ((InputStream) (obj)).read();
        if(j3 != 45) goto _L32; else goto _L31
_L31:
        if(((InputStream) (obj)).read() != 45) goto _L32; else goto _L33
_L33:
        complete = true;
        flag2 = true;
_L40:
        if(sharedinputstream == null) goto _L35; else goto _L34
_L34:
        mimebodypart = createMimeBodyPart(sharedinputstream.newStream(l, l1));
_L50:
        super.addBodyPart(mimebodypart);
          goto _L36
_L24:
        l1 = sharedinputstream.getPosition();
        bytearrayoutputstream = null;
          goto _L25
_L55:
        if(((InputStream) (obj)).read() != (0xff & abyte0[i3]))
            break; /* Loop/switch isn't completed */
        i3++;
        continue; /* Loop/switch isn't completed */
_L38:
        j3 = ((InputStream) (obj)).read();
_L32:
        if(j3 == 32 || j3 == 9) goto _L38; else goto _L37
_L37:
        if(j3 == 10) goto _L40; else goto _L39
_L39:
        if(j3 != 13) goto _L30; else goto _L41
_L41:
        ((InputStream) (obj)).mark(1);
        if(((InputStream) (obj)).read() != 10)
            ((InputStream) (obj)).reset();
          goto _L40
_L30:
        ((InputStream) (obj)).reset();
        if(bytearrayoutputstream == null || k1 == -1) goto _L27; else goto _L42
_L42:
        bytearrayoutputstream.write(k1);
        if(i2 == -1) goto _L44; else goto _L43
_L43:
        bytearrayoutputstream.write(i2);
          goto _L44
_L27:
        j2 = ((InputStream) (obj)).read();
        if(j2 >= 0) goto _L46; else goto _L45
_L45:
        if(!ignoreMissingEndBoundary)
            throw new MessagingException("missing multipart end boundary");
        complete = false;
        flag2 = true;
          goto _L40
_L56:
        flag3 = true;
        if(sharedinputstream == null)
            break MISSING_BLOCK_LABEL_871;
        l1 = sharedinputstream.getPosition() - 1L;
        k1 = j2;
        if(j2 != 13) goto _L48; else goto _L47
_L47:
        ((InputStream) (obj)).mark(1);
        k2 = ((InputStream) (obj)).read();
label0:
        {
            if(k2 != 10)
                break label0;
            i2 = k2;
        }
          goto _L48
        ((InputStream) (obj)).reset();
          goto _L48
_L57:
        flag3 = false;
        if(bytearrayoutputstream == null) goto _L48; else goto _L49
_L49:
        bytearrayoutputstream.write(j2);
        flag3 = false;
          goto _L48
_L35:
        byte abyte1[] = bytearrayoutputstream.toByteArray();
        mimebodypart1 = createMimeBodyPart(internetheaders, abyte1);
        mimebodypart = mimebodypart1;
          goto _L50
_L11:
        if(i >= 0) goto _L52; else goto _L51
_L17:
        byte abyte0[];
        boolean flag2;
        InternetHeaders internetheaders;
        ByteArrayOutputStream bytearrayoutputstream;
        MimeBodyPart mimebodypart;
        MimeBodyPart mimebodypart1;
        int k2;
        int l2;
        int j3;
        String s6;
        if(c != ' ' && c != '\t')
            break; /* Loop/switch isn't completed */
        i--;
          goto _L11
        SecurityException securityexception;
        securityexception;
        s3 = "\n";
          goto _L53
_L25:
        flag3 = true;
        k1 = -1;
        i2 = -1;
          goto _L48
_L28:
        if(i3 < j1) goto _L55; else goto _L54
_L44:
        i2 = -1;
        k1 = i2;
          goto _L27
_L46:
        if(j2 != 13 && j2 != 10) goto _L57; else goto _L56
    }

    public void removeBodyPart(int i)
        throws MessagingException
    {
        parse();
        super.removeBodyPart(i);
    }

    public boolean removeBodyPart(BodyPart bodypart)
        throws MessagingException
    {
        parse();
        return super.removeBodyPart(bodypart);
    }

    public void setPreamble(String s)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        preamble = s;
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    public void setSubType(String s)
        throws MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        ContentType contenttype = new ContentType(contentType);
        contenttype.setSubType(s);
        contentType = contenttype.toString();
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        throw exception;
    }

    protected void updateHeaders()
        throws MessagingException
    {
        int i = 0;
        do
        {
            if(i >= parts.size())
                return;
            ((MimeBodyPart)parts.elementAt(i)).updateHeaders();
            i++;
        } while(true);
    }

    public void writeTo(OutputStream outputstream)
        throws IOException, MessagingException
    {
        this;
        JVM INSTR monitorenter ;
        String s;
        LineOutputStream lineoutputstream;
        parse();
        s = (new StringBuilder("--")).append((new ContentType(contentType)).getParameter("boundary")).toString();
        lineoutputstream = new LineOutputStream(outputstream);
        if(preamble != null)
        {
            byte abyte0[] = ASCIIUtility.getBytes(preamble);
            lineoutputstream.write(abyte0);
            if(abyte0.length > 0 && abyte0[-1 + abyte0.length] != 13 && abyte0[-1 + abyte0.length] != 10)
                lineoutputstream.writeln();
        }
          goto _L1
_L3:
        int i;
        if(i < parts.size())
            break MISSING_BLOCK_LABEL_150;
        lineoutputstream.writeln((new StringBuilder(String.valueOf(s))).append("--").toString());
        this;
        JVM INSTR monitorexit ;
        return;
        lineoutputstream.writeln(s);
        ((MimeBodyPart)parts.elementAt(i)).writeTo(outputstream);
        lineoutputstream.writeln();
        i++;
        continue; /* Loop/switch isn't completed */
        Exception exception;
        exception;
        throw exception;
_L1:
        i = 0;
        if(true) goto _L3; else goto _L2
_L2:
    }

    private static boolean bmparse;
    private static boolean ignoreMissingBoundaryParameter;
    private static boolean ignoreMissingEndBoundary;
    private boolean complete;
    protected DataSource ds;
    protected boolean parsed;
    private String preamble;

    static 
    {
        ignoreMissingEndBoundary = true;
        ignoreMissingBoundaryParameter = true;
        bmparse = true;
        String s = System.getProperty("mail.mime.multipart.ignoremissingendboundary");
        if(s == null) goto _L2; else goto _L1
_L1:
        if(!s.equalsIgnoreCase("false")) goto _L2; else goto _L3
_L3:
        boolean flag = false;
_L12:
        String s1;
        ignoreMissingEndBoundary = flag;
        s1 = System.getProperty("mail.mime.multipart.ignoremissingboundaryparameter");
        if(s1 == null) goto _L5; else goto _L4
_L4:
        if(!s1.equalsIgnoreCase("false")) goto _L5; else goto _L6
_L6:
        boolean flag1 = false;
_L10:
        String s2;
        ignoreMissingBoundaryParameter = flag1;
        s2 = System.getProperty("mail.mime.multipart.bmparse");
        if(s2 == null) goto _L8; else goto _L7
_L7:
        boolean flag2 = s2.equalsIgnoreCase("false");
        boolean flag3 = false;
        if(!flag2) goto _L8; else goto _L9
_L2:
        flag = true;
        continue; /* Loop/switch isn't completed */
_L5:
        flag1 = true;
          goto _L10
_L8:
        flag3 = true;
_L9:
        try
        {
            bmparse = flag3;
        }
        catch(SecurityException securityexception) { }
        if(true) goto _L12; else goto _L11
_L11:
    }
}
