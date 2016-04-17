// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;

// Referenced classes of package javax.mail.internet:
//            MimePart, InternetHeaders, SharedInputStream, HeaderTokenizer, 
//            MimeUtility, ContentDisposition, ParseException, ContentType, 
//            MimeMultipart, MimeMessage, MimePartDataSource

public class MimeBodyPart extends BodyPart
    implements MimePart
{

    public MimeBodyPart()
    {
        headers = new InternetHeaders();
    }

    public MimeBodyPart(InputStream inputstream)
        throws MessagingException
    {
        if(!(inputstream instanceof ByteArrayInputStream) && !(inputstream instanceof BufferedInputStream) && !(inputstream instanceof SharedInputStream))
            inputstream = new BufferedInputStream(inputstream);
        headers = new InternetHeaders(inputstream);
        if(inputstream instanceof SharedInputStream)
        {
            SharedInputStream sharedinputstream = (SharedInputStream)inputstream;
            contentStream = sharedinputstream.newStream(sharedinputstream.getPosition(), -1L);
            return;
        }
        try
        {
            content = ASCIIUtility.getBytes(inputstream);
            return;
        }
        catch(IOException ioexception)
        {
            throw new MessagingException("Error reading input stream", ioexception);
        }
    }

    public MimeBodyPart(InternetHeaders internetheaders, byte abyte0[])
        throws MessagingException
    {
        headers = internetheaders;
        content = abyte0;
    }

    static String[] getContentLanguage(MimePart mimepart)
        throws MessagingException
    {
        String s = mimepart.getHeader("Content-Language", null);
        if(s != null) goto _L2; else goto _L1
_L1:
        return null;
_L2:
        HeaderTokenizer headertokenizer = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        Vector vector = new Vector();
        do
        {
            HeaderTokenizer.Token token;
            int i;
label0:
            {
                token = headertokenizer.next();
                i = token.getType();
                if(i != -4)
                    break label0;
                if(vector.size() != 0)
                {
                    String as[] = new String[vector.size()];
                    vector.copyInto(as);
                    return as;
                }
            }
            if(true)
                continue;
            if(i == -1)
                vector.addElement(token.getValue());
        } while(true);
        if(true) goto _L1; else goto _L3
_L3:
    }

    static String getDescription(MimePart mimepart)
        throws MessagingException
    {
        String s = mimepart.getHeader("Content-Description", null);
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

    static String getDisposition(MimePart mimepart)
        throws MessagingException
    {
        String s = mimepart.getHeader("Content-Disposition", null);
        if(s == null)
            return null;
        else
            return (new ContentDisposition(s)).getDisposition();
    }

    static String getEncoding(MimePart mimepart)
        throws MessagingException
    {
        String s = mimepart.getHeader("Content-Transfer-Encoding", null);
        if(s == null)
            return null;
        String s1 = s.trim();
        if(s1.equalsIgnoreCase("7bit") || s1.equalsIgnoreCase("8bit") || s1.equalsIgnoreCase("quoted-printable") || s1.equalsIgnoreCase("binary") || s1.equalsIgnoreCase("base64"))
            return s1;
        HeaderTokenizer headertokenizer = new HeaderTokenizer(s1, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token token;
        int i;
        do
        {
            token = headertokenizer.next();
            i = token.getType();
            if(i == -4)
                return s1;
        } while(i != -1);
        return token.getValue();
    }

    static String getFileName(MimePart mimepart)
        throws MessagingException
    {
        String s1;
        String s3;
        String s = mimepart.getHeader("Content-Disposition", null);
        s1 = null;
        if(s != null)
            s1 = (new ContentDisposition(s)).getParameter("filename");
        if(s1 != null)
            break MISSING_BLOCK_LABEL_69;
        s3 = mimepart.getHeader("Content-Type", null);
        if(s3 == null)
            break MISSING_BLOCK_LABEL_69;
        String s4 = (new ContentType(s3)).getParameter("name");
        s1 = s4;
_L2:
        if(decodeFileName && s1 != null)
        {
            String s2;
            try
            {
                s2 = MimeUtility.decodeText(s1);
            }
            catch(UnsupportedEncodingException unsupportedencodingexception)
            {
                throw new MessagingException("Can't decode filename", unsupportedencodingexception);
            }
            s1 = s2;
        }
        return s1;
        ParseException parseexception;
        parseexception;
        if(true) goto _L2; else goto _L1
_L1:
    }

    static void invalidateContentHeaders(MimePart mimepart)
        throws MessagingException
    {
        mimepart.removeHeader("Content-Type");
        mimepart.removeHeader("Content-Transfer-Encoding");
    }

    static boolean isMimeType(MimePart mimepart, String s)
        throws MessagingException
    {
        boolean flag;
        try
        {
            flag = (new ContentType(mimepart.getContentType())).match(s);
        }
        catch(ParseException parseexception)
        {
            return mimepart.getContentType().equalsIgnoreCase(s);
        }
        return flag;
    }

    static void setContentLanguage(MimePart mimepart, String as[])
        throws MessagingException
    {
        StringBuffer stringbuffer = new StringBuffer(as[0]);
        int i = 1;
        do
        {
            if(i >= as.length)
            {
                mimepart.setHeader("Content-Language", stringbuffer.toString());
                return;
            }
            stringbuffer.append(',').append(as[i]);
            i++;
        } while(true);
    }

    static void setDescription(MimePart mimepart, String s, String s1)
        throws MessagingException
    {
        if(s == null)
        {
            mimepart.removeHeader("Content-Description");
            return;
        }
        try
        {
            mimepart.setHeader("Content-Description", MimeUtility.fold(21, MimeUtility.encodeText(s, s1, null)));
            return;
        }
        catch(UnsupportedEncodingException unsupportedencodingexception)
        {
            throw new MessagingException("Encoding error", unsupportedencodingexception);
        }
    }

    static void setDisposition(MimePart mimepart, String s)
        throws MessagingException
    {
        if(s == null)
        {
            mimepart.removeHeader("Content-Disposition");
            return;
        }
        String s1 = mimepart.getHeader("Content-Disposition", null);
        if(s1 != null)
        {
            ContentDisposition contentdisposition = new ContentDisposition(s1);
            contentdisposition.setDisposition(s);
            s = contentdisposition.toString();
        }
        mimepart.setHeader("Content-Disposition", s);
    }

    static void setEncoding(MimePart mimepart, String s)
        throws MessagingException
    {
        mimepart.setHeader("Content-Transfer-Encoding", s);
    }

    static void setFileName(MimePart mimepart, String s)
        throws MessagingException
    {
        String s1;
        String s2;
        if(encodeFileName && s != null)
        {
            ContentDisposition contentdisposition;
            String s3;
            ContentType contenttype;
            String s4;
            try
            {
                s4 = MimeUtility.encodeText(s);
            }
            catch(UnsupportedEncodingException unsupportedencodingexception)
            {
                throw new MessagingException("Can't encode filename", unsupportedencodingexception);
            }
            s = s4;
        }
        s1 = mimepart.getHeader("Content-Disposition", null);
        if(s1 == null)
            s2 = "attachment";
        else
            s2 = s1;
        contentdisposition = new ContentDisposition(s2);
        contentdisposition.setParameter("filename", s);
        mimepart.setHeader("Content-Disposition", contentdisposition.toString());
        if(!setContentTypeFileName)
            break MISSING_BLOCK_LABEL_122;
        s3 = mimepart.getHeader("Content-Type", null);
        if(s3 == null)
            break MISSING_BLOCK_LABEL_122;
        contenttype = new ContentType(s3);
        contenttype.setParameter("name", s);
        mimepart.setHeader("Content-Type", contenttype.toString());
        return;
        ParseException parseexception;
        parseexception;
    }

    static void setText(MimePart mimepart, String s, String s1, String s2)
        throws MessagingException
    {
        if(s1 == null)
            if(MimeUtility.checkAscii(s) != 1)
                s1 = MimeUtility.getDefaultMIMECharset();
            else
                s1 = "us-ascii";
        mimepart.setContent(s, (new StringBuilder("text/")).append(s2).append("; charset=").append(MimeUtility.quote(s1, "()<>@,;:\\\"\t []/?=")).toString());
    }

    static void updateHeaders(MimePart mimepart)
        throws MessagingException
    {
        DataHandler datahandler = mimepart.getDataHandler();
        if(datahandler != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        String s = datahandler.getContentType();
        boolean flag;
        ContentType contenttype;
        boolean flag1;
        Object obj;
        String s4;
        MimeBodyPart mimebodypart;
        if(mimepart.getHeader("Content-Type") == null)
            flag = true;
        else
            flag = false;
        contenttype = new ContentType(s);
        if(!contenttype.match("multipart/*")) goto _L4; else goto _L3
_L3:
        flag1 = true;
        if(!(mimepart instanceof MimeBodyPart)) goto _L6; else goto _L5
_L5:
        mimebodypart = (MimeBodyPart)mimepart;
        if(mimebodypart.cachedContent == null) goto _L8; else goto _L7
_L7:
        obj = mimebodypart.cachedContent;
_L15:
        if(!(obj instanceof MimeMultipart)) goto _L10; else goto _L9
_L9:
        ((MimeMultipart)obj).updateHeaders();
_L16:
        if(flag1)
            continue; /* Loop/switch isn't completed */
        String s1;
        String s2;
        String s3;
        try
        {
            if(mimepart.getHeader("Content-Transfer-Encoding") == null)
                setEncoding(mimepart, MimeUtility.getEncoding(datahandler));
        }
        catch(IOException ioexception)
        {
            MessagingException messagingexception = new MessagingException("IOException updating headers", ioexception);
            throw messagingexception;
        }
        if(!flag)
            continue; /* Loop/switch isn't completed */
        if(!setDefaultTextCharset || !contenttype.match("text/*") || contenttype.getParameter("charset") != null)
            continue; /* Loop/switch isn't completed */
        s3 = mimepart.getEncoding();
        if(s3 == null) goto _L12; else goto _L11
_L11:
        if(!s3.equalsIgnoreCase("7bit")) goto _L12; else goto _L13
_L13:
        s4 = "us-ascii";
_L17:
        contenttype.setParameter("charset", s4);
        s = contenttype.toString();
        if(!flag) goto _L1; else goto _L14
_L14:
        s1 = mimepart.getHeader("Content-Disposition", null);
        if(s1 == null)
            break MISSING_BLOCK_LABEL_261;
        s2 = (new ContentDisposition(s1)).getParameter("filename");
        if(s2 == null)
            break MISSING_BLOCK_LABEL_261;
        contenttype.setParameter("name", s2);
        s = contenttype.toString();
        mimepart.setHeader("Content-Type", s);
        return;
_L8:
        obj = datahandler.getContent();
          goto _L15
_L6:
label0:
        {
            if(!(mimepart instanceof MimeMessage))
                break MISSING_BLOCK_LABEL_342;
            MimeMessage mimemessage = (MimeMessage)mimepart;
            if(mimemessage.cachedContent == null)
                break label0;
            obj = mimemessage.cachedContent;
        }
          goto _L15
        obj = datahandler.getContent();
          goto _L15
        obj = datahandler.getContent();
          goto _L15
_L10:
        throw new MessagingException((new StringBuilder("MIME part of type \"")).append(s).append("\" contains object of type ").append(obj.getClass().getName()).append(" instead of MimeMultipart").toString());
_L4:
        boolean flag2 = contenttype.match("message/rfc822");
        flag1 = false;
        if(flag2)
            flag1 = true;
          goto _L16
_L12:
        String s5 = MimeUtility.getDefaultMIMECharset();
        s4 = s5;
          goto _L17
    }

    static void writeTo(MimePart mimepart, OutputStream outputstream, String as[])
        throws IOException, MessagingException
    {
        LineOutputStream lineoutputstream;
        Enumeration enumeration;
        if(outputstream instanceof LineOutputStream)
            lineoutputstream = (LineOutputStream)outputstream;
        else
            lineoutputstream = new LineOutputStream(outputstream);
        enumeration = mimepart.getNonMatchingHeaderLines(as);
        do
        {
            if(!enumeration.hasMoreElements())
            {
                lineoutputstream.writeln();
                OutputStream outputstream1 = MimeUtility.encode(outputstream, mimepart.getEncoding());
                mimepart.getDataHandler().writeTo(outputstream1);
                outputstream1.flush();
                return;
            }
            lineoutputstream.writeln((String)enumeration.nextElement());
        } while(true);
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

    public void attachFile(File file)
        throws IOException, MessagingException
    {
        FileDataSource filedatasource = new FileDataSource(file);
        setDataHandler(new DataHandler(filedatasource));
        setFileName(filedatasource.getName());
    }

    public void attachFile(String s)
        throws IOException, MessagingException
    {
        attachFile(new File(s));
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
            if(cacheMultipart && ((obj1 instanceof Multipart) || (obj1 instanceof Message)) && (content != null || contentStream != null))
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
        return getContentLanguage(((MimePart) (this)));
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
            return new ByteArrayInputStream(content);
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
        if(dh == null)
            dh = new DataHandler(new MimePartDataSource(this));
        return dh;
    }

    public String getDescription()
        throws MessagingException
    {
        return getDescription(((MimePart) (this)));
    }

    public String getDisposition()
        throws MessagingException
    {
        return getDisposition(((MimePart) (this)));
    }

    public String getEncoding()
        throws MessagingException
    {
        return getEncoding(((MimePart) (this)));
    }

    public String getFileName()
        throws MessagingException
    {
        return getFileName(((MimePart) (this)));
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

    public boolean isMimeType(String s)
        throws MessagingException
    {
        return isMimeType(((MimePart) (this)), s);
    }

    public void removeHeader(String s)
        throws MessagingException
    {
        headers.removeHeader(s);
    }

    public void saveFile(File file)
        throws IOException, MessagingException
    {
        InputStream inputstream = null;
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
        byte abyte0[];
        inputstream = getInputStream();
        abyte0 = new byte[8192];
_L1:
        int i = inputstream.read(abyte0);
        Exception exception;
        BufferedOutputStream bufferedoutputstream1;
        if(i > 0)
            break MISSING_BLOCK_LABEL_60;
        IOException ioexception;
        IOException ioexception1;
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            catch(IOException ioexception3) { }
        if(bufferedoutputstream == null)
            break MISSING_BLOCK_LABEL_59;
        bufferedoutputstream.close();
        return;
        bufferedoutputstream.write(abyte0, 0, i);
          goto _L1
        exception;
        bufferedoutputstream1 = bufferedoutputstream;
_L3:
        if(inputstream != null)
            try
            {
                inputstream.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception1) { }
        if(bufferedoutputstream1 != null)
            try
            {
                bufferedoutputstream1.close();
            }
            // Misplaced declaration of an exception variable
            catch(IOException ioexception) { }
        throw exception;
        IOException ioexception2;
        ioexception2;
        return;
        exception;
        inputstream = null;
        bufferedoutputstream1 = null;
        if(true) goto _L3; else goto _L2
_L2:
    }

    public void saveFile(String s)
        throws IOException, MessagingException
    {
        saveFile(new File(s));
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
        setContentLanguage(((MimePart) (this)), as);
    }

    public void setContentMD5(String s)
        throws MessagingException
    {
        setHeader("Content-MD5", s);
    }

    public void setDataHandler(DataHandler datahandler)
        throws MessagingException
    {
        dh = datahandler;
        cachedContent = null;
        invalidateContentHeaders(this);
    }

    public void setDescription(String s)
        throws MessagingException
    {
        setDescription(s, null);
    }

    public void setDescription(String s, String s1)
        throws MessagingException
    {
        setDescription(((MimePart) (this)), s, s1);
    }

    public void setDisposition(String s)
        throws MessagingException
    {
        setDisposition(((MimePart) (this)), s);
    }

    public void setFileName(String s)
        throws MessagingException
    {
        setFileName(((MimePart) (this)), s);
    }

    public void setHeader(String s, String s1)
        throws MessagingException
    {
        headers.setHeader(s, s1);
    }

    public void setText(String s)
        throws MessagingException
    {
        setText(s, null);
    }

    public void setText(String s, String s1)
        throws MessagingException
    {
        setText(((MimePart) (this)), s, s1, "plain");
    }

    public void setText(String s, String s1, String s2)
        throws MessagingException
    {
        setText(((MimePart) (this)), s, s1, s2);
    }

    protected void updateHeaders()
        throws MessagingException
    {
        updateHeaders(((MimePart) (this)));
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

    public void writeTo(OutputStream outputstream)
        throws IOException, MessagingException
    {
        writeTo(((MimePart) (this)), outputstream, null);
    }

    static boolean cacheMultipart;
    private static boolean decodeFileName;
    private static boolean encodeFileName;
    private static boolean setContentTypeFileName;
    private static boolean setDefaultTextCharset;
    private Object cachedContent;
    protected byte content[];
    protected InputStream contentStream;
    protected DataHandler dh;
    protected InternetHeaders headers;

    static 
    {
        setDefaultTextCharset = true;
        setContentTypeFileName = true;
        encodeFileName = false;
        decodeFileName = false;
        cacheMultipart = true;
        String s = System.getProperty("mail.mime.setdefaulttextcharset");
        if(s == null) goto _L2; else goto _L1
_L1:
        if(!s.equalsIgnoreCase("false")) goto _L2; else goto _L3
_L3:
        boolean flag = false;
_L20:
        String s1;
        setDefaultTextCharset = flag;
        s1 = System.getProperty("mail.mime.setcontenttypefilename");
        if(s1 == null) goto _L5; else goto _L4
_L4:
        if(!s1.equalsIgnoreCase("false")) goto _L5; else goto _L6
_L6:
        boolean flag1 = false;
_L16:
        String s2;
        setContentTypeFileName = flag1;
        s2 = System.getProperty("mail.mime.encodefilename");
        if(s2 == null) goto _L8; else goto _L7
_L7:
        if(s2.equalsIgnoreCase("false")) goto _L8; else goto _L9
_L9:
        boolean flag2 = true;
_L17:
        String s3;
        encodeFileName = flag2;
        s3 = System.getProperty("mail.mime.decodefilename");
        if(s3 == null) goto _L11; else goto _L10
_L10:
        if(s3.equalsIgnoreCase("false")) goto _L11; else goto _L12
_L12:
        boolean flag3 = true;
_L18:
        String s4;
        decodeFileName = flag3;
        s4 = System.getProperty("mail.mime.cachemultipart");
        if(s4 == null) goto _L14; else goto _L13
_L13:
        boolean flag4 = s4.equalsIgnoreCase("false");
        boolean flag5 = false;
        if(!flag4) goto _L14; else goto _L15
_L2:
        flag = true;
        continue; /* Loop/switch isn't completed */
_L5:
        flag1 = true;
          goto _L16
_L8:
        flag2 = false;
          goto _L17
_L11:
        flag3 = false;
          goto _L18
_L14:
        flag5 = true;
_L15:
        try
        {
            cacheMultipart = flag5;
        }
        catch(SecurityException securityexception) { }
        if(true) goto _L20; else goto _L19
_L19:
    }
}
