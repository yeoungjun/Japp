// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;

// Referenced classes of package javax.mail.internet:
//            ParseException, ContentType, AsciiOutputStream

public class MimeUtility
{
    class _cls1NullInputStream extends InputStream
    {

        public int read()
        {
            return 0;
        }

        _cls1NullInputStream()
        {
        }
    }


    private MimeUtility()
    {
    }

    static int checkAscii(InputStream inputstream, int i, boolean flag)
    {
        int j;
        boolean flag1;
        byte abyte0[];
        int k;
        boolean flag2;
        int l;
        boolean flag3;
        int i1;
        j = 4096;
        if(encodeEolStrict && flag)
            flag1 = true;
        else
            flag1 = false;
        abyte0 = (byte[])null;
        k = 0;
        flag2 = false;
        l = 0;
        flag3 = false;
        i1 = 0;
        if(i != 0)
        {
            if(i == -1)
                j = 4096;
            else
                j = Math.min(i, 4096);
            abyte0 = new byte[j];
        }
_L13:
        if(i != 0) goto _L2; else goto _L1
_L2:
        int j1 = inputstream.read(abyte0, 0, j);
        if(j1 == -1) goto _L1; else goto _L3
_L3:
        int k1;
        int l1;
        k1 = 0;
        l1 = 0;
          goto _L4
_L12:
        int i2 = 0xff & abyte0[k1];
          goto _L5
_L8:
        flag4 = nonascii(i2);
        if(!flag4) goto _L7; else goto _L6
_L15:
        if(++l > 998)
            flag3 = true;
          goto _L8
_L6:
        if(flag)
            return 3;
        i1++;
_L10:
        l1 = i2;
        k1++;
        continue; /* Loop/switch isn't completed */
_L7:
        k++;
        if(true) goto _L10; else goto _L9
_L9:
        IOException ioexception;
        ioexception;
_L1:
        boolean flag4;
        if(i == 0 && flag)
            return 3;
        if(i1 == 0)
        {
            if(flag2)
                return 3;
            return !flag3 ? 1 : 2;
        }
        return k <= i1 ? 3 : 2;
_L4:
        if(k1 < j1) goto _L12; else goto _L11
_L11:
        if(i != -1)
            i -= j1;
          goto _L13
_L5:
        if(flag1 && (l1 == 13 && i2 != 10 || l1 != 13 && i2 == 10))
            flag2 = true;
        if(i2 != 13 && i2 != 10) goto _L15; else goto _L14
_L14:
        l = 0;
          goto _L8
    }

    static int checkAscii(String s)
    {
        int i = 0;
        int j = 0;
        int k = s.length();
        int l = 0;
        do
        {
            if(l >= k)
            {
                if(j == 0)
                    return 1;
                break;
            }
            if(nonascii(s.charAt(l)))
                j++;
            else
                i++;
            l++;
        } while(true);
        return i <= j ? 3 : 2;
    }

    static int checkAscii(byte abyte0[])
    {
        int i = 0;
        int j = 0;
        int k = 0;
        do
        {
            if(k >= abyte0.length)
            {
                if(j == 0)
                    return 1;
                break;
            }
            if(nonascii(0xff & abyte0[k]))
                j++;
            else
                i++;
            k++;
        } while(true);
        return i <= j ? 3 : 2;
    }

    public static InputStream decode(InputStream inputstream, String s)
        throws MessagingException
    {
        if(s.equalsIgnoreCase("base64"))
        {
            inputstream = new BASE64DecoderStream(inputstream);
        } else
        {
            if(s.equalsIgnoreCase("quoted-printable"))
                return new QPDecoderStream(inputstream);
            if(s.equalsIgnoreCase("uuencode") || s.equalsIgnoreCase("x-uuencode") || s.equalsIgnoreCase("x-uue"))
                return new UUDecoderStream(inputstream);
            if(!s.equalsIgnoreCase("binary") && !s.equalsIgnoreCase("7bit") && !s.equalsIgnoreCase("8bit"))
                throw new MessagingException((new StringBuilder("Unknown encoding: ")).append(s).toString());
        }
        return inputstream;
    }

    private static String decodeInnerWords(String s)
        throws UnsupportedEncodingException
    {
        int i;
        StringBuffer stringbuffer;
        i = 0;
        stringbuffer = new StringBuffer();
_L7:
        int j = s.indexOf("=?", i);
        if(j >= 0) goto _L2; else goto _L1
_L1:
        if(i == 0)
            return s;
        break; /* Loop/switch isn't completed */
_L2:
        int k;
        stringbuffer.append(s.substring(i, j));
        k = s.indexOf('?', j + 2);
        if(k < 0) goto _L1; else goto _L3
_L3:
        int l = s.indexOf('?', k + 1);
        if(l < 0) goto _L1; else goto _L4
_L4:
        int i1 = s.indexOf("?=", l + 1);
        if(i1 < 0) goto _L1; else goto _L5
_L5:
        String s1 = s.substring(j, i1 + 2);
        String s2 = decodeWord(s1);
        s1 = s2;
_L8:
        stringbuffer.append(s1);
        i = i1 + 2;
        if(true) goto _L7; else goto _L6
_L6:
        if(i < s.length())
            stringbuffer.append(s.substring(i));
        return stringbuffer.toString();
        ParseException parseexception;
        parseexception;
          goto _L8
    }

    public static String decodeText(String s)
        throws UnsupportedEncodingException
    {
        StringTokenizer stringtokenizer;
        StringBuffer stringbuffer;
        StringBuffer stringbuffer1;
        boolean flag;
        if(s.indexOf("=?") == -1)
            return s;
        stringtokenizer = new StringTokenizer(s, " \t\n\r", true);
        stringbuffer = new StringBuffer();
        stringbuffer1 = new StringBuffer();
        flag = false;
_L2:
        String s1;
        if(!stringtokenizer.hasMoreTokens())
        {
            stringbuffer.append(stringbuffer1);
            return stringbuffer.toString();
        }
        s1 = stringtokenizer.nextToken();
        char c = s1.charAt(0);
        if(c == ' ' || c == '\t' || c == '\r' || c == '\n')
        {
            stringbuffer1.append(c);
            continue; /* Loop/switch isn't completed */
        }
        String s2 = decodeWord(s1);
        if(flag)
            break MISSING_BLOCK_LABEL_140;
        if(stringbuffer1.length() > 0)
            stringbuffer.append(stringbuffer1);
        flag = true;
_L3:
        stringbuffer.append(s2);
        stringbuffer1.setLength(0);
        if(true) goto _L2; else goto _L1
_L1:
        ParseException parseexception;
        parseexception;
        s2 = s1;
        if(!decodeStrict)
        {
            String s3 = decodeInnerWords(s2);
            if(s3 != s2)
            {
                if((!flag || !s2.startsWith("=?")) && stringbuffer1.length() > 0)
                    stringbuffer.append(stringbuffer1);
                flag = s2.endsWith("?=");
                s2 = s3;
            } else
            {
                if(stringbuffer1.length() > 0)
                    stringbuffer.append(stringbuffer1);
                flag = false;
            }
        } else
        {
            if(stringbuffer1.length() > 0)
                stringbuffer.append(stringbuffer1);
            flag = false;
        }
          goto _L3
    }

    public static String decodeWord(String s)
        throws ParseException, UnsupportedEncodingException
    {
        String s1;
        String s2;
        int i1;
        String s3;
        if(!s.startsWith("=?"))
            throw new ParseException((new StringBuilder("encoded word does not start with \"=?\": ")).append(s).toString());
        int i = s.indexOf('?', 2);
        if(i == -1)
            throw new ParseException((new StringBuilder("encoded word does not include charset: ")).append(s).toString());
        s1 = javaCharset(s.substring(2, i));
        int j = i + 1;
        int k = s.indexOf('?', j);
        if(k == -1)
            throw new ParseException((new StringBuilder("encoded word does not include encoding: ")).append(s).toString());
        s2 = s.substring(j, k);
        int l = k + 1;
        i1 = s.indexOf("?=", l);
        if(i1 == -1)
            throw new ParseException((new StringBuilder("encoded word does not end with \"?=\": ")).append(s).toString());
        s3 = s.substring(l, i1);
        if(s3.length() <= 0) goto _L2; else goto _L1
_L1:
        ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(ASCIIUtility.getBytes(s3));
        if(!s2.equalsIgnoreCase("B")) goto _L4; else goto _L3
_L3:
        Object obj = new BASE64DecoderStream(bytearrayinputstream);
_L10:
        byte abyte0[];
        int k1;
        int j1 = bytearrayinputstream.available();
        abyte0 = new byte[j1];
        k1 = ((InputStream) (obj)).read(abyte0, 0, j1);
        if(k1 > 0) goto _L6; else goto _L5
_L5:
        String s4 = "";
_L8:
        if(i1 + 2 < s.length())
        {
            String s5 = s.substring(i1 + 2);
            if(!decodeStrict)
                s5 = decodeInnerWords(s5);
            return (new StringBuilder(String.valueOf(s4))).append(s5).toString();
        }
        break; /* Loop/switch isn't completed */
_L4:
        if(s2.equalsIgnoreCase("Q"))
        {
            obj = new QDecoderStream(bytearrayinputstream);
            continue; /* Loop/switch isn't completed */
        } else
        {
            try
            {
                throw new UnsupportedEncodingException((new StringBuilder("unknown encoding: ")).append(s2).toString());
            }
            catch(UnsupportedEncodingException unsupportedencodingexception1)
            {
                throw unsupportedencodingexception1;
            }
            catch(IOException ioexception)
            {
                throw new ParseException(ioexception.toString());
            }
            catch(IllegalArgumentException illegalargumentexception)
            {
                UnsupportedEncodingException unsupportedencodingexception = new UnsupportedEncodingException(s1);
                throw unsupportedencodingexception;
            }
        }
_L6:
        s4 = new String(abyte0, 0, k1, s1);
        continue; /* Loop/switch isn't completed */
_L2:
        s4 = "";
        if(true) goto _L8; else goto _L7
_L7:
        return s4;
        if(true) goto _L10; else goto _L9
_L9:
    }

    private static void doEncode(String s, boolean flag, String s1, int i, String s2, boolean flag1, boolean flag2, StringBuffer stringbuffer)
        throws UnsupportedEncodingException
    {
        byte abyte0[] = s.getBytes(s1);
        int j;
        if(flag)
            j = BEncoderStream.encodedLength(abyte0);
        else
            j = QEncoderStream.encodedLength(abyte0, flag2);
        if(j > i)
        {
            int l = s.length();
            if(l > 1)
            {
                doEncode(s.substring(0, l / 2), flag, s1, i, s2, flag1, flag2, stringbuffer);
                doEncode(s.substring(l / 2, l), flag, s1, i, s2, false, flag2, stringbuffer);
                return;
            }
        }
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        Object obj;
        byte abyte1[];
        int k;
        if(flag)
            obj = new BEncoderStream(bytearrayoutputstream);
        else
            obj = new QEncoderStream(bytearrayoutputstream, flag2);
        try
        {
            ((OutputStream) (obj)).write(abyte0);
            ((OutputStream) (obj)).close();
        }
        catch(IOException ioexception) { }
        abyte1 = bytearrayoutputstream.toByteArray();
        if(!flag1)
            if(foldEncodedWords)
                stringbuffer.append("\r\n ");
            else
                stringbuffer.append(" ");
        stringbuffer.append(s2);
        k = 0;
        do
        {
            if(k >= abyte1.length)
            {
                stringbuffer.append("?=");
                return;
            }
            stringbuffer.append((char)abyte1[k]);
            k++;
        } while(true);
    }

    public static OutputStream encode(OutputStream outputstream, String s)
        throws MessagingException
    {
        if(s != null)
        {
            if(s.equalsIgnoreCase("base64"))
                return new BASE64EncoderStream(outputstream);
            if(s.equalsIgnoreCase("quoted-printable"))
                return new QPEncoderStream(outputstream);
            if(s.equalsIgnoreCase("uuencode") || s.equalsIgnoreCase("x-uuencode") || s.equalsIgnoreCase("x-uue"))
                return new UUEncoderStream(outputstream);
            if(!s.equalsIgnoreCase("binary") && !s.equalsIgnoreCase("7bit") && !s.equalsIgnoreCase("8bit"))
                throw new MessagingException((new StringBuilder("Unknown encoding: ")).append(s).toString());
        }
        return outputstream;
    }

    public static OutputStream encode(OutputStream outputstream, String s, String s1)
        throws MessagingException
    {
        if(s != null)
        {
            if(s.equalsIgnoreCase("base64"))
                return new BASE64EncoderStream(outputstream);
            if(s.equalsIgnoreCase("quoted-printable"))
                return new QPEncoderStream(outputstream);
            if(s.equalsIgnoreCase("uuencode") || s.equalsIgnoreCase("x-uuencode") || s.equalsIgnoreCase("x-uue"))
                return new UUEncoderStream(outputstream, s1);
            if(!s.equalsIgnoreCase("binary") && !s.equalsIgnoreCase("7bit") && !s.equalsIgnoreCase("8bit"))
                throw new MessagingException((new StringBuilder("Unknown encoding: ")).append(s).toString());
        }
        return outputstream;
    }

    public static String encodeText(String s)
        throws UnsupportedEncodingException
    {
        return encodeText(s, null, null);
    }

    public static String encodeText(String s, String s1, String s2)
        throws UnsupportedEncodingException
    {
        return encodeWord(s, s1, s2, false);
    }

    public static String encodeWord(String s)
        throws UnsupportedEncodingException
    {
        return encodeWord(s, null, null);
    }

    public static String encodeWord(String s, String s1, String s2)
        throws UnsupportedEncodingException
    {
        return encodeWord(s, s1, s2, true);
    }

    private static String encodeWord(String s, String s1, String s2, boolean flag)
        throws UnsupportedEncodingException
    {
        int i = checkAscii(s);
        if(i == 1)
            return s;
        String s3;
        boolean flag1;
        StringBuffer stringbuffer;
        if(s1 == null)
        {
            s3 = getDefaultJavaCharset();
            s1 = getDefaultMIMECharset();
        } else
        {
            s3 = javaCharset(s1);
        }
        if(s2 == null)
            if(i != 3)
                s2 = "Q";
            else
                s2 = "B";
        if(s2.equalsIgnoreCase("B"))
            flag1 = true;
        else
        if(s2.equalsIgnoreCase("Q"))
            flag1 = false;
        else
            throw new UnsupportedEncodingException((new StringBuilder("Unknown transfer encoding: ")).append(s2).toString());
        stringbuffer = new StringBuffer();
        doEncode(s, flag1, s3, 68 - s1.length(), (new StringBuilder("=?")).append(s1).append("?").append(s2).append("?").toString(), true, flag, stringbuffer);
        return stringbuffer.toString();
    }

    public static String fold(int i, String s)
    {
        int j;
        if(!foldText)
            return s;
        j = -1 + s.length();
_L5:
        if(j >= 0) goto _L2; else goto _L1
_L1:
        char c;
        if(j != -1 + s.length())
            s = s.substring(0, j + 1);
        if(i + s.length() <= 76)
            return s;
        break; /* Loop/switch isn't completed */
_L2:
        if((c = s.charAt(j)) != ' ' && c != '\t' && c != '\r' && c != '\n') goto _L1; else goto _L3
_L3:
        j--;
        if(true) goto _L5; else goto _L4
_L4:
        StringBuffer stringbuffer;
        int k;
        stringbuffer = new StringBuffer(4 + s.length());
        k = 0;
_L7:
        int l;
        if(i + s.length() > 76)
        {
label0:
            {
                l = -1;
                int i1 = 0;
                while(false) 
                {
                    while(i1 < s.length() && (l == -1 || i + i1 <= 76)) 
                    {
                        char c1 = s.charAt(i1);
                        if((c1 == ' ' || c1 == '\t') && k != 32 && k != 9)
                            l = i1;
                        k = c1;
                        i1++;
                    }
                    if(l != -1)
                        break label0;
                    stringbuffer.append(s);
                    s = "";
                }
            }
        }
        stringbuffer.append(s);
        return stringbuffer.toString();
        stringbuffer.append(s.substring(0, l));
        stringbuffer.append("\r\n");
        k = s.charAt(l);
        stringbuffer.append(k);
        s = s.substring(l + 1);
        i = 1;
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static String getDefaultJavaCharset()
    {
        if(defaultJavaCharset != null) goto _L2; else goto _L1
_L1:
        String s1 = System.getProperty("mail.mime.charset");
        String s = s1;
_L5:
        if(s != null && s.length() > 0)
        {
            defaultJavaCharset = javaCharset(s);
            return defaultJavaCharset;
        }
        defaultJavaCharset = System.getProperty("file.encoding", "8859_1");
_L2:
        return defaultJavaCharset;
        SecurityException securityexception1;
        securityexception1;
        defaultJavaCharset = (new InputStreamReader(new _cls1NullInputStream())).getEncoding();
        if(defaultJavaCharset == null)
            defaultJavaCharset = "8859_1";
        if(true) goto _L2; else goto _L3
_L3:
        SecurityException securityexception;
        securityexception;
        s = null;
        if(true) goto _L5; else goto _L4
_L4:
    }

    static String getDefaultMIMECharset()
    {
        if(defaultMIMECharset == null)
            try
            {
                defaultMIMECharset = System.getProperty("mail.mime.charset");
            }
            catch(SecurityException securityexception) { }
        if(defaultMIMECharset == null)
            defaultMIMECharset = mimeCharset(getDefaultJavaCharset());
        return defaultMIMECharset;
    }

    public static String getEncoding(DataHandler datahandler)
    {
        if(datahandler.getName() != null)
            return getEncoding(datahandler.getDataSource());
        ContentType contenttype;
        AsciiOutputStream asciioutputstream;
        try
        {
            contenttype = new ContentType(datahandler.getContentType());
        }
        catch(Exception exception)
        {
            return "base64";
        }
        if(!contenttype.match("text/*")) goto _L2; else goto _L1
_L1:
        asciioutputstream = new AsciiOutputStream(false, false);
        String s;
        AsciiOutputStream asciioutputstream1;
        try
        {
            datahandler.writeTo(asciioutputstream);
        }
        catch(IOException ioexception) { }
        asciioutputstream.getAscii();
        JVM INSTR tableswitch 1 2: default 80
    //                   1 94
    //                   2 102;
           goto _L3 _L4 _L5
_L3:
        s = "base64";
_L7:
        return s;
_L4:
        s = "7bit";
        continue; /* Loop/switch isn't completed */
_L5:
        s = "quoted-printable";
        continue; /* Loop/switch isn't completed */
_L2:
        asciioutputstream1 = new AsciiOutputStream(true, encodeEolStrict);
        try
        {
            datahandler.writeTo(asciioutputstream1);
        }
        catch(IOException ioexception1) { }
        if(asciioutputstream1.getAscii() == 1)
            s = "7bit";
        else
            s = "base64";
        if(true) goto _L7; else goto _L6
_L6:
    }

    public static String getEncoding(DataSource datasource)
    {
        ContentType contenttype = new ContentType(datasource.getContentType());
        InputStream inputstream = datasource.getInputStream();
        boolean flag;
        String s;
        if(contenttype.match("text/*"))
            flag = false;
        else
            flag = true;
        checkAscii(inputstream, -1, flag);
        JVM INSTR tableswitch 1 2: default 64
    //                   1 88
    //                   2 96;
           goto _L1 _L2 _L3
_L1:
        s = "base64";
_L4:
        Exception exception1;
        try
        {
            inputstream.close();
        }
        catch(IOException ioexception) { }
        return s;
        exception1;
_L5:
        return "base64";
_L2:
        s = "7bit";
          goto _L4
_L3:
        s = "quoted-printable";
          goto _L4
        Exception exception;
        exception;
          goto _L5
    }

    private static int indexOfAny(String s, String s1)
    {
        return indexOfAny(s, s1, 0);
    }

    private static int indexOfAny(String s, String s1, int i)
    {
        int j;
        int k;
        int l;
        try
        {
            j = s.length();
        }
        catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
        {
            return -1;
        }
        k = i;
          goto _L1
_L3:
        l = s1.indexOf(s.charAt(k));
        if(l >= 0)
            break MISSING_BLOCK_LABEL_48;
        k++;
_L1:
        if(k < j) goto _L3; else goto _L2
_L2:
        k = -1;
        return k;
    }

    public static String javaCharset(String s)
    {
        String s1;
        if(mime2java != null && s != null)
            if((s1 = (String)mime2java.get(s.toLowerCase(Locale.ENGLISH))) != null)
                return s1;
        return s;
    }

    private static void loadMappings(LineInputStream lineinputstream, Hashtable hashtable)
    {
        do
        {
            String s;
            do
            {
                try
                {
                    s = lineinputstream.readLine();
                }
                catch(IOException ioexception)
                {
                    return;
                }
                while(s == null || s.startsWith("--") && s.endsWith("--")) 
                    return;
            } while(s.trim().length() == 0 || s.startsWith("#"));
            StringTokenizer stringtokenizer = new StringTokenizer(s, " \t");
            try
            {
                String s1 = stringtokenizer.nextToken();
                String s2 = stringtokenizer.nextToken();
                hashtable.put(s1.toLowerCase(Locale.ENGLISH), s2);
            }
            catch(NoSuchElementException nosuchelementexception) { }
        } while(true);
    }

    public static String mimeCharset(String s)
    {
        String s1;
        if(java2mime != null && s != null)
            if((s1 = (String)java2mime.get(s.toLowerCase(Locale.ENGLISH))) != null)
                return s1;
        return s;
    }

    static final boolean nonascii(int i)
    {
        return i >= 127 || i < 32 && i != 13 && i != 10 && i != 9;
    }

    public static String quote(String s, String s1)
    {
        int i = s.length();
        boolean flag = false;
        int j = 0;
        do
        {
            if(j >= i)
            {
                if(flag)
                {
                    StringBuffer stringbuffer1 = new StringBuffer(i + 2);
                    stringbuffer1.append('"').append(s).append('"');
                    s = stringbuffer1.toString();
                }
                return s;
            }
            char c = s.charAt(j);
            if(c == '"' || c == '\\' || c == '\r' || c == '\n')
            {
                StringBuffer stringbuffer = new StringBuffer(i + 3);
                stringbuffer.append('"');
                stringbuffer.append(s.substring(0, j));
                int k = 0;
                int l = j;
                do
                {
                    if(l >= i)
                    {
                        stringbuffer.append('"');
                        return stringbuffer.toString();
                    }
                    char c1 = s.charAt(l);
                    if((c1 == '"' || c1 == '\\' || c1 == '\r' || c1 == '\n') && (c1 != '\n' || k != 13))
                        stringbuffer.append('\\');
                    stringbuffer.append(c1);
                    k = c1;
                    l++;
                } while(true);
            }
            if(c < ' ' || c >= '\177' || s1.indexOf(c) >= 0)
                flag = true;
            j++;
        } while(true);
    }

    public static String unfold(String s)
    {
        if(foldText) goto _L2; else goto _L1
_L1:
        return s;
_L2:
        StringBuffer stringbuffer = null;
_L4:
        int i;
label0:
        {
            i = indexOfAny(s, "\r\n");
            if(i >= 0)
                break label0;
            if(stringbuffer != null)
            {
                stringbuffer.append(s);
                return stringbuffer.toString();
            }
        }
        if(true) goto _L1; else goto _L3
_L3:
        int j;
        int k;
        int l;
        j = s.length();
        k = i + 1;
        if(k < j && s.charAt(k - 1) == '\r' && s.charAt(k) == '\n')
            k++;
        if(i != 0 && s.charAt(i - 1) == '\\')
            break MISSING_BLOCK_LABEL_250;
        if(k >= j)
            break MISSING_BLOCK_LABEL_212;
        char c = s.charAt(k);
        if(c != ' ' && c != '\t')
            break MISSING_BLOCK_LABEL_212;
        l = k + 1;
_L5:
label1:
        {
            if(l < j)
            {
                char c1 = s.charAt(l);
                if(c1 == ' ' || c1 == '\t')
                    break label1;
            }
            if(stringbuffer == null)
                stringbuffer = new StringBuffer(s.length());
            if(i != 0)
            {
                stringbuffer.append(s.substring(0, i));
                stringbuffer.append(' ');
            }
            s = s.substring(l);
        }
          goto _L4
        l++;
          goto _L5
        if(stringbuffer == null)
            stringbuffer = new StringBuffer(s.length());
        stringbuffer.append(s.substring(0, k));
        s = s.substring(k);
          goto _L4
        if(stringbuffer == null)
            stringbuffer = new StringBuffer(s.length());
        stringbuffer.append(s.substring(0, i - 1));
        stringbuffer.append(s.substring(i, k));
        s = s.substring(k);
          goto _L4
    }

    public static final int ALL = -1;
    static final int ALL_ASCII = 1;
    static final int MOSTLY_ASCII = 2;
    static final int MOSTLY_NONASCII = 3;
    private static boolean decodeStrict;
    private static String defaultJavaCharset;
    private static String defaultMIMECharset;
    private static boolean encodeEolStrict;
    private static boolean foldEncodedWords;
    private static boolean foldText;
    private static Hashtable java2mime;
    private static Hashtable mime2java;

    static 
    {
        decodeStrict = true;
        encodeEolStrict = false;
        foldEncodedWords = false;
        foldText = true;
        String s = System.getProperty("mail.mime.decodetext.strict");
        if(s == null) goto _L2; else goto _L1
_L1:
        if(!s.equalsIgnoreCase("false")) goto _L2; else goto _L3
_L3:
        boolean flag = false;
_L18:
        String s1;
        decodeStrict = flag;
        s1 = System.getProperty("mail.mime.encodeeol.strict");
        if(s1 == null) goto _L5; else goto _L4
_L4:
        if(!s1.equalsIgnoreCase("true")) goto _L5; else goto _L6
_L6:
        boolean flag1 = true;
_L13:
        String s2;
        encodeEolStrict = flag1;
        s2 = System.getProperty("mail.mime.foldencodedwords");
        if(s2 == null) goto _L8; else goto _L7
_L7:
        if(!s2.equalsIgnoreCase("true")) goto _L8; else goto _L9
_L9:
        boolean flag2 = true;
_L14:
        String s3;
        foldEncodedWords = flag2;
        s3 = System.getProperty("mail.mime.foldtext");
        if(s3 == null) goto _L11; else goto _L10
_L10:
        boolean flag3 = s3.equalsIgnoreCase("false");
        boolean flag4 = false;
        if(!flag3) goto _L11; else goto _L12
_L12:
        Exception exception;
        InputStream inputstream;
        Object obj;
        LineInputStream lineinputstream;
        Exception exception1;
        Exception exception2;
        Exception exception3;
        try
        {
            foldText = flag4;
        }
        catch(SecurityException securityexception) { }
        java2mime = new Hashtable(40);
        mime2java = new Hashtable(10);
        inputstream = javax/mail/internet/MimeUtility.getResourceAsStream("/META-INF/javamail.charset.map");
        obj = inputstream;
        if(obj == null)
            break MISSING_BLOCK_LABEL_220;
        lineinputstream = new LineInputStream(((InputStream) (obj)));
        loadMappings((LineInputStream)lineinputstream, java2mime);
        loadMappings((LineInputStream)lineinputstream, mime2java);
        try
        {
            lineinputstream.close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception3) { }
        if(java2mime.isEmpty())
        {
            java2mime.put("8859_1", "ISO-8859-1");
            java2mime.put("iso8859_1", "ISO-8859-1");
            java2mime.put("iso8859-1", "ISO-8859-1");
            java2mime.put("8859_2", "ISO-8859-2");
            java2mime.put("iso8859_2", "ISO-8859-2");
            java2mime.put("iso8859-2", "ISO-8859-2");
            java2mime.put("8859_3", "ISO-8859-3");
            java2mime.put("iso8859_3", "ISO-8859-3");
            java2mime.put("iso8859-3", "ISO-8859-3");
            java2mime.put("8859_4", "ISO-8859-4");
            java2mime.put("iso8859_4", "ISO-8859-4");
            java2mime.put("iso8859-4", "ISO-8859-4");
            java2mime.put("8859_5", "ISO-8859-5");
            java2mime.put("iso8859_5", "ISO-8859-5");
            java2mime.put("iso8859-5", "ISO-8859-5");
            java2mime.put("8859_6", "ISO-8859-6");
            java2mime.put("iso8859_6", "ISO-8859-6");
            java2mime.put("iso8859-6", "ISO-8859-6");
            java2mime.put("8859_7", "ISO-8859-7");
            java2mime.put("iso8859_7", "ISO-8859-7");
            java2mime.put("iso8859-7", "ISO-8859-7");
            java2mime.put("8859_8", "ISO-8859-8");
            java2mime.put("iso8859_8", "ISO-8859-8");
            java2mime.put("iso8859-8", "ISO-8859-8");
            java2mime.put("8859_9", "ISO-8859-9");
            java2mime.put("iso8859_9", "ISO-8859-9");
            java2mime.put("iso8859-9", "ISO-8859-9");
            java2mime.put("sjis", "Shift_JIS");
            java2mime.put("jis", "ISO-2022-JP");
            java2mime.put("iso2022jp", "ISO-2022-JP");
            java2mime.put("euc_jp", "euc-jp");
            java2mime.put("koi8_r", "koi8-r");
            java2mime.put("euc_cn", "euc-cn");
            java2mime.put("euc_tw", "euc-tw");
            java2mime.put("euc_kr", "euc-kr");
        }
        if(mime2java.isEmpty())
        {
            mime2java.put("iso-2022-cn", "ISO2022CN");
            mime2java.put("iso-2022-kr", "ISO2022KR");
            mime2java.put("utf-8", "UTF8");
            mime2java.put("utf8", "UTF8");
            mime2java.put("ja_jp.iso2022-7", "ISO2022JP");
            mime2java.put("ja_jp.eucjp", "EUCJIS");
            mime2java.put("euc-kr", "KSC5601");
            mime2java.put("euckr", "KSC5601");
            mime2java.put("us-ascii", "ISO-8859-1");
            mime2java.put("x-us-ascii", "ISO-8859-1");
        }
        return;
_L2:
        flag = true;
        continue; /* Loop/switch isn't completed */
_L5:
        flag1 = false;
          goto _L13
_L8:
        flag2 = false;
          goto _L14
_L11:
        flag4 = true;
          goto _L12
        exception1;
_L16:
        try
        {
            ((InputStream) (obj)).close();
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception2) { }
        try
        {
            throw exception1;
        }
        // Misplaced declaration of an exception variable
        catch(Exception exception) { }
        break MISSING_BLOCK_LABEL_220;
        exception1;
        obj = lineinputstream;
        if(true) goto _L16; else goto _L15
_L15:
        if(true) goto _L18; else goto _L17
_L17:
    }
}
